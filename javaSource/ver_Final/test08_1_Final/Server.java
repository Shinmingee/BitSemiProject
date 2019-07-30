//	Version buildup Date_5.28

//	시연할 때 서버(DB)측은 우리쪽,  유저(선생님자리)에서는 DB세팅 X

package test08_1_Final;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;



/*  클라이언트 - [서버] - ConnectionPool - Oracle DB
 * 
 *  접속한 유저에게 제공할 정보를 단계별 메뉴 선택으로 유도한다. 
 * 
 * */



class ChatThread extends Thread {
	Socket sock;
	InputStream input;
	OutputStream output;
	PrintWriter pw;
	BufferedReader br;

	ChatThread(Socket sock) {
		this.sock = sock;
		try {
			input = sock.getInputStream();
			output = sock.getOutputStream();
			pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}// 생성자

	
	public void run() {/////////////////////////////////////////////////////////////////////////////////////////////

		// 메뉴 보여주는 클래스
		ShowMenu showMenu = new ShowMenu(pw);
		// 선택값 받는 클래스
		SelectMenu selectMenu = new SelectMenu(br, pw);
		// 쿼리 조회 출력하는 클래스
		SearchQuery searchQuery = new SearchQuery(pw, selectMenu);

		try {
			pw.println("uuuuuuuuuuuuuu     uuuuuuuuuuuuuu    ,uuuuuuuuuuuuu,    uuuuuuuuuuuuuu\r\n" + 
					"                                                                      \r\n" + 
					"KWWWWWWWWWWWWK    ,WWWWWWWWWWWWWu    uWWWWWWWWWWWWW,    uWWWWWWWWWWWWK\r\n" + 
					"                                                                      \r\n" + 
					"                                                          WXDzzDXX    \r\n" + 
					"                                                          G5  , ue #z \r\n" + 
					"                                                          9,     9  #W\r\n" + 
					"                                       KeeeeeeeX          #Dz###D#    \r\n" + 
					"                                       5e     eD e9       zyyyyyyz , 5\r\n" + 
					"                     #9EGGE#z          We uuu eD  e       zyyyyyyz z y\r\n" + 
					"                     e     uG 9#       WeEeeeEeX  X       zyyyyyyz D y\r\n" + 
					"                     E     KE  e,      KeDDDDDEWW e       zyyyyyyz Xyu\r\n" + 
					"  W#9GGE##           9XDzzDX9  u       KeDDDDDEuE e       zyyyyyyz    \r\n" + 
					"  ee KKuKeKEe        #yyyyyy#  Wu      KeDDDDDEuE e       zyyyyyyz    \r\n" + 
					"  e5     e  Ge       #yyyyyy# KKu      KeDDDDDEy X#       zyyyyyyz    \r\n" + 
					"  eeeeeeeeu  X       #yyyyyy# uuu      KeDDDDDEX          zyyyyyyz    \r\n" + 
					"  G#DDDDDe   e       #yyyyyy# XX       KeDDDDDED          zyyyyyyz    \r\n" + 
					"  G#DDDDDe e e       #yyyyyy9          KeDDDDDED          zyyyyyyz    \r\n" + 
					"  eG99999e G e       E555555E          ye99999e#          9X55555#    \r\n" + 
					"  XX55555z 9XG       XKKKKKKX          ,zyyyyyDW          5WKKKKK5    \r\n" + 
					" 9EDDDDDD#E         z5WWWWWW5X        XGzDDDDDzGy        XXyWWWWW5D   \r\n" + 
					"                                                                      \r\n" + 
					"KWWWWWWWWWWWWK    ,WWWWWWWWWWWWWu    uWWWWWWWWWWWWW,    uWWWWWWWWWWWWK\r\n" + 
					",,,,,,,,,,,,,,     ,,,,,,,,,,,,,,    ,,,,,,,,,,,,,,     ,,,,,,,,,,,,,,\r\n" + 
					"");
			pw.flush();
			String line;
			showMenu.mainMenu(); // 메인메뉴 1. 지역별 주유소 찾기 2.가까운 주유소 검색하기(길 이름/동 이름) 0. 종료하기(quit)

			while ((line = br.readLine()) != null) { // if 하나당 한 메뉴
				if (line.equals("1")) { // 메인 = 1번 지역별 주유소 선택시
					showMenu.localMenu(); // 1. 강남구 2. 강서구 3. 강동구 4. 강북구 메뉴 보여줌
					selectMenu.localSelect();// 1. 강남구 2. 강서구 3. 강동구 4. 강북구 중 입력 받음
					if (selectMenu.selectNum.equals("1")) { // 1번 강남구 선택시
						if (selectMenu.selectNum.equals("3")) {// 3번 선택시
							searchQuery.searchSelf(); // 해당지역 self주유소 쿼리문 바로 출력
						}
						if (selectMenu.selectNum.equals("1")) {// 여기 1번은 최저가 검색 선택
							searchQuery.searchMIN();
						} else if (selectMenu.selectNum.equals("2")) { // 여기 2번은 브랜드 검색 선택
							searchQuery.searchBrand();
						}
					} else if (selectMenu.selectNum.equals("2")) { // 2번 강서구 선택시
						if (selectMenu.selectNum.equals("3")) {// 3번 선택시
							searchQuery.searchSelf(); // 해당지역 self주유소 쿼리문 바로 출력
						}

						if (selectMenu.selectNum.equals("1")) {// 여기 1번은 최저가 검색 선택
							searchQuery.searchMIN();
						} else if (selectMenu.selectNum.equals("2")) { // 여기 2번은 브랜드 검색 선택
							searchQuery.searchBrand();
						}
					} else if (selectMenu.selectNum.equals("3")) { // 3번 강동구 선택
						if (selectMenu.selectNum.equals("3")) {// 3번 선택시
							searchQuery.searchSelf(); // 해당지역 self주유소 쿼리문 바로 출력
						}
						if (selectMenu.selectNum.equals("1")) {// 여기 1번은 최저가 검색 선택
							searchQuery.searchMIN();
						} else if (selectMenu.selectNum.equals("2")) { // 여기 2번은 브랜드 검색 선택
							searchQuery.searchBrand();
						}
					} else if (selectMenu.selectNum.equals("4")) { // 4번 강북구 선택
						if (selectMenu.selectNum.equals("3")) {// 3번 선택시
							searchQuery.searchSelf(); // 해당지역 self주유소 쿼리문 바로 출력
						}
						if (selectMenu.selectNum.equals("1")) {// 여기 1번은 최저가 검색 선택
							searchQuery.searchMIN();
						} else if (selectMenu.selectNum.equals("2")) { // 여기 2번은 브랜드 검색 선택
							searchQuery.searchBrand();
						}

					} else if (line.equals("9")) { // 대문으로 돌아가기
						showMenu.mainMenu();
					}

				} else if (line.equals("2")) { // 지명 검색 쿼리/////////////////////////////////////////////
					showMenu.searchRoadMenu(); // 길 이름 또는 동 이름을 입력하세요! (ex.올림픽로, 개포동) 라고 안내함.
					selectMenu.roadSelect(); // 지명 검색 메서드()가 입력 받음(키값)
					searchQuery.searchRoadname(); // 입력값을 쿼리에 넣어서 바로 찾음.
				} else if (line.equals("9")) {// 대문으로 돌아가기
					showMenu.mainMenu();
				}else if(line.equals("0")) {
					pw.println("이용해 주셔서 감사합니다.");
					pw.flush();
				} else { // main select 구문 안써서 else에 잘못입력문구 추가.
					pw.println("잘못 입력하셨습니다. 다시 선택해주세요");
					pw.flush();
					showMenu.mainMenu();
					continue;
				}
			} // while end

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}// run()

}

public class Server {
	public static void main(String[] args) {

		try {
			ServerSocket server = new ServerSocket(10001);
			System.out.println("접속을 기다립니다.");

			while (true) {
				Socket sock = server.accept();
				System.out.println("User has contact to our server.  IP:"+sock.getInetAddress());				
				ChatThread thread = new ChatThread(sock);

				thread.start();// 시동

			} // while end

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}// main
	
}// Server



