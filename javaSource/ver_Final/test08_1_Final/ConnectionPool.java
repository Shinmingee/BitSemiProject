//	Version buildup Date_5.28

//	시연할 때 서버(DB)측은 우리쪽,  유저(선생님자리)에서는 DB세팅 X


package test08_1_Final;

import java.sql.*;
import java.util.*;

/**
 * copyright 민형s                        민형아 고맙다 =)
 * 
 * @author RHIE
 */

/*
 * 클라이언트 - 서버 - [ConnectionPool] - Oracle DB 자리 마련
 */


@SuppressWarnings("serial")
class ReachMaximum extends Exception {
	public ReachMaximum() {
		super("ConnectionPool의 세션 준비 및 허용 개수가 한계에 도달했습니다.(15)");
	}
}


///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////


public class ConnectionPool {
	// JDBC 드라이버 준비
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// DB연결요소
	private String url;
	private String user;
	private String password;

	// ConnectionPool요소
	/* Connection 갯수의 정적활용 */
	private int initConnCnt = 5; // ConnectionPool 생성시 처음 세팅되는 Connection 갯수
	private int addConnCnt = 5; // Connect 한계 초과시 추가 ConnectionPool 생성
	private int maxConnCnt = 15; // 이 ConnectionPool의 최대 연결 갯수는 15개이다.

	// Connection관리
	private ArrayList<Connection> spare; // 대기중인 Connection
	private ArrayList<Connection> connecting; // 연결중인 Connection

	// ConnectionPool객체정보
	static ConnectionPool cp;

	/* createConnectionPool() */
	public synchronized static ConnectionPool createConnectionPool(String url, String user, String password) {
		try {
			if (cp == null) {
				cp = new ConnectionPool(url, user, password);
			} else {
				return cp;

			}
		} catch (Exception e) {
         System.out.println("에러코드 CP-ccp5104 : " + e.getMessage());
		}
		return cp;
	}

	/**
	 * 생성자
	 * 
	 * @param url
	 * @param user
	 * @param password initialize spare and connecting spare에 initConnCnt만큼
	 *                 Connection생성 호출순서: 1. createConnectionPool() - Synchronized
	 *                 with below Methods 2. ConnectionPool() 3. initSettingPool()
	 *                 4. getConnection()
	 */

	private ConnectionPool(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;

		spare = new ArrayList<Connection>(initConnCnt);
		connecting = new ArrayList<Connection>(maxConnCnt);

		try {
			// spare에 connection 생성
			initSettingPool();
		} catch (Exception e) {
			System.out.println("에러코드 CP-contructor[spare] : " + e);
		}
	}

	/**
	 * initSettingPool() ConnectingPool이 처음 생성될 때의 초기 세팅 spare 변수에 5개의 연결 session을
	 * 준비시킨다.
	 */
	private void initSettingPool() {
		Connection initSet = null;
		try {
			while (spare.size() < initConnCnt) {
				initSet = getConnection();
				if (initSet != null) {
					spare.add(initSet);
				}
			}
		} catch (Exception e) {
			System.out.println("에러코드 CP-isp474 : " + e);
		}
	}

	/**
	 * getConnection() 연결이 성공하면 DB와 연결된 session을 반환하지만 잘못된 파라미터 등으로 인한 오류가 발생되면
	 * null을 리턴한다. 그러므로 이 메서드를 받을 때는 null이 반환되는 경우를 생각해야한다.
	 */
	private Connection getConnection() {
		int currentCnt = spare.size() + connecting.size();
		try {
			if (currentCnt == maxConnCnt) {
				throw new ReachMaximum();
			}
			Connection conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (ReachMaximum e) {
			System.out.println("에러코드 CP-limit : " + e.getMessage());
			return null;
		} catch (Exception e) {
			System.out.println("에러코드 CP-gc310 : " + e);
			return null;
		}
	}

	/**
	 * 이곳부터는 사용자 연결 관리 작업이 일어난다. 다중 Thread가 동시 접근이 될 수도 있으므로 synchronized를 사용한다.(이하
	 * 생략) (이곳부터는 createConnectionPool의 동기화 영역에서 벗어났다.)
	 * 
	 * getSession() : 정상 동작시 return값 Connection / 비정상 동작시 return값 null 그러므로, 이 메서드를
	 * 사용시 null을 염두해두어야 한다. exitSession() :
	 */
	// 유저의 접속 시도
	public synchronized Connection getSession() {
		Connection toConnect = null;
		Connection addSpare = null;
		try {
			if (spare.size() == 0) {
				for (int i = 0; i < addConnCnt; i++) {
					addSpare = getConnection();
					if (addSpare != null) {
						spare.add(addSpare);
					} else {
						break;
					}
				}
			}

			toConnect = spare.get(spare.size() - 1);
			spare.remove(spare.size() - 1);
			connecting.add(toConnect);
			return toConnect;

		} catch (Exception e) {
			System.out.println("에러코드 CP-gc36 : " + e);
			return null;
		}
	}

	// 유저의 접속 중지
	/**
	 * !주의 : 사용된 Connection(대상: conn으로 받은 인스턴스)은 null로 초기화해야한다.
	 * 
	 * @param conn
	 */
	public synchronized Connection exitSession(Connection conn) {
		Connection toDisconnect = conn;
		try {
			if (connecting.contains(toDisconnect)) {
				connecting.remove(toDisconnect);
				spare.add(toDisconnect);
			} else {
				throw new Exception("ConnectionPool에 있지 않습니다.");
			}
		} catch (Exception e) {
			System.out.println("에러코드 CP-lc46 : " + e.getMessage());
		}
		return null;
	}

	// 모든 Connection 종료
	public synchronized void closeAll() {
		System.out.println("connecting.size() : " + connecting.size());
		System.out.println("spare.size() : " + spare.size());
		try {
			// connecting모두 종료
			while (connecting.size() != 0) {
				System.out.println(connecting.get(connecting.size() - 1) + "삭제");
				(connecting.get(connecting.size() - 1)).close();
				connecting.remove(connecting.size() - 1);
			}
			// spare모두 종료
			while (spare.size() != 0) {
				System.out.println(spare.get(spare.size() - 1) + "삭제");
				(spare.get(spare.size() - 1)).close();
				spare.remove(spare.size() - 1);
			}
		} catch (Exception e) {
			System.out.println("에러코드 CP-ca : " + e);
		}
	}

	/** to check information **/
	// 현재 대기중인 Connection 갯수 반환
	public int getSpareSize() {
		return spare.size();
	}

	// 현재 대기중인 Connection 정보 출력
	public void getSpareInfo() {
		Iterator<Connection> itr = spare.iterator();
		while (itr.hasNext()) {
			System.out.println("ready for connecting : " + itr.next());
		}
	}

	// 현재 연결중인 Connection 갯수 반환
	public int getConnectingSize() {
		return connecting.size();
	}

	// 현재 연결중인 Connection 정보 출력
	public void getConnectingInfo() {
		Iterator<Connection> itr = connecting.iterator();
		while (itr.hasNext()) {
			System.out.println("already connected : " + itr.next());
		}
	}
}


