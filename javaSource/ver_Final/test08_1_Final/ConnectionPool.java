//	Version buildup Date_5.28

//	�ÿ��� �� ����(DB)���� �츮��,  ����(�������ڸ�)������ DB���� X


package test08_1_Final;

import java.sql.*;
import java.util.*;

/**
 * copyright ����s                        ������ ���� =)
 * 
 * @author RHIE
 */

/*
 * Ŭ���̾�Ʈ - ���� - [ConnectionPool] - Oracle DB �ڸ� ����
 */


@SuppressWarnings("serial")
class ReachMaximum extends Exception {
	public ReachMaximum() {
		super("ConnectionPool�� ���� �غ� �� ��� ������ �Ѱ迡 �����߽��ϴ�.(15)");
	}
}


///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////


public class ConnectionPool {
	// JDBC ����̹� �غ�
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// DB������
	private String url;
	private String user;
	private String password;

	// ConnectionPool���
	/* Connection ������ ����Ȱ�� */
	private int initConnCnt = 5; // ConnectionPool ������ ó�� ���õǴ� Connection ����
	private int addConnCnt = 5; // Connect �Ѱ� �ʰ��� �߰� ConnectionPool ����
	private int maxConnCnt = 15; // �� ConnectionPool�� �ִ� ���� ������ 15���̴�.

	// Connection����
	private ArrayList<Connection> spare; // ������� Connection
	private ArrayList<Connection> connecting; // �������� Connection

	// ConnectionPool��ü����
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
         System.out.println("�����ڵ� CP-ccp5104 : " + e.getMessage());
		}
		return cp;
	}

	/**
	 * ������
	 * 
	 * @param url
	 * @param user
	 * @param password initialize spare and connecting spare�� initConnCnt��ŭ
	 *                 Connection���� ȣ�����: 1. createConnectionPool() - Synchronized
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
			// spare�� connection ����
			initSettingPool();
		} catch (Exception e) {
			System.out.println("�����ڵ� CP-contructor[spare] : " + e);
		}
	}

	/**
	 * initSettingPool() ConnectingPool�� ó�� ������ ���� �ʱ� ���� spare ������ 5���� ���� session��
	 * �غ��Ų��.
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
			System.out.println("�����ڵ� CP-isp474 : " + e);
		}
	}

	/**
	 * getConnection() ������ �����ϸ� DB�� ����� session�� ��ȯ������ �߸��� �Ķ���� ������ ���� ������ �߻��Ǹ�
	 * null�� �����Ѵ�. �׷��Ƿ� �� �޼��带 ���� ���� null�� ��ȯ�Ǵ� ��츦 �����ؾ��Ѵ�.
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
			System.out.println("�����ڵ� CP-limit : " + e.getMessage());
			return null;
		} catch (Exception e) {
			System.out.println("�����ڵ� CP-gc310 : " + e);
			return null;
		}
	}

	/**
	 * �̰����ʹ� ����� ���� ���� �۾��� �Ͼ��. ���� Thread�� ���� ������ �� ���� �����Ƿ� synchronized�� ����Ѵ�.(����
	 * ����) (�̰����ʹ� createConnectionPool�� ����ȭ �������� �����.)
	 * 
	 * getSession() : ���� ���۽� return�� Connection / ������ ���۽� return�� null �׷��Ƿ�, �� �޼��带
	 * ���� null�� �����صξ�� �Ѵ�. exitSession() :
	 */
	// ������ ���� �õ�
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
			System.out.println("�����ڵ� CP-gc36 : " + e);
			return null;
		}
	}

	// ������ ���� ����
	/**
	 * !���� : ���� Connection(���: conn���� ���� �ν��Ͻ�)�� null�� �ʱ�ȭ�ؾ��Ѵ�.
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
				throw new Exception("ConnectionPool�� ���� �ʽ��ϴ�.");
			}
		} catch (Exception e) {
			System.out.println("�����ڵ� CP-lc46 : " + e.getMessage());
		}
		return null;
	}

	// ��� Connection ����
	public synchronized void closeAll() {
		System.out.println("connecting.size() : " + connecting.size());
		System.out.println("spare.size() : " + spare.size());
		try {
			// connecting��� ����
			while (connecting.size() != 0) {
				System.out.println(connecting.get(connecting.size() - 1) + "����");
				(connecting.get(connecting.size() - 1)).close();
				connecting.remove(connecting.size() - 1);
			}
			// spare��� ����
			while (spare.size() != 0) {
				System.out.println(spare.get(spare.size() - 1) + "����");
				(spare.get(spare.size() - 1)).close();
				spare.remove(spare.size() - 1);
			}
		} catch (Exception e) {
			System.out.println("�����ڵ� CP-ca : " + e);
		}
	}

	/** to check information **/
	// ���� ������� Connection ���� ��ȯ
	public int getSpareSize() {
		return spare.size();
	}

	// ���� ������� Connection ���� ���
	public void getSpareInfo() {
		Iterator<Connection> itr = spare.iterator();
		while (itr.hasNext()) {
			System.out.println("ready for connecting : " + itr.next());
		}
	}

	// ���� �������� Connection ���� ��ȯ
	public int getConnectingSize() {
		return connecting.size();
	}

	// ���� �������� Connection ���� ���
	public void getConnectingInfo() {
		Iterator<Connection> itr = connecting.iterator();
		while (itr.hasNext()) {
			System.out.println("already connected : " + itr.next());
		}
	}
}


