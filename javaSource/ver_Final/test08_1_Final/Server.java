//	Version buildup Date_5.28

//	�ÿ��� �� ����(DB)���� �츮��,  ����(�������ڸ�)������ DB���� X

package test08_1_Final;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;



/*  Ŭ���̾�Ʈ - [����] - ConnectionPool - Oracle DB
 * 
 *  ������ �������� ������ ������ �ܰ躰 �޴� �������� �����Ѵ�. 
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
	}// ������

	
	public void run() {/////////////////////////////////////////////////////////////////////////////////////////////

		// �޴� �����ִ� Ŭ����
		ShowMenu showMenu = new ShowMenu(pw);
		// ���ð� �޴� Ŭ����
		SelectMenu selectMenu = new SelectMenu(br, pw);
		// ���� ��ȸ ����ϴ� Ŭ����
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
			showMenu.mainMenu(); // ���θ޴� 1. ������ ������ ã�� 2.����� ������ �˻��ϱ�(�� �̸�/�� �̸�) 0. �����ϱ�(quit)

			while ((line = br.readLine()) != null) { // if �ϳ��� �� �޴�
				if (line.equals("1")) { // ���� = 1�� ������ ������ ���ý�
					showMenu.localMenu(); // 1. ������ 2. ������ 3. ������ 4. ���ϱ� �޴� ������
					selectMenu.localSelect();// 1. ������ 2. ������ 3. ������ 4. ���ϱ� �� �Է� ����
					if (selectMenu.selectNum.equals("1")) { // 1�� ������ ���ý�
						if (selectMenu.selectNum.equals("3")) {// 3�� ���ý�
							searchQuery.searchSelf(); // �ش����� self������ ������ �ٷ� ���
						}
						if (selectMenu.selectNum.equals("1")) {// ���� 1���� ������ �˻� ����
							searchQuery.searchMIN();
						} else if (selectMenu.selectNum.equals("2")) { // ���� 2���� �귣�� �˻� ����
							searchQuery.searchBrand();
						}
					} else if (selectMenu.selectNum.equals("2")) { // 2�� ������ ���ý�
						if (selectMenu.selectNum.equals("3")) {// 3�� ���ý�
							searchQuery.searchSelf(); // �ش����� self������ ������ �ٷ� ���
						}

						if (selectMenu.selectNum.equals("1")) {// ���� 1���� ������ �˻� ����
							searchQuery.searchMIN();
						} else if (selectMenu.selectNum.equals("2")) { // ���� 2���� �귣�� �˻� ����
							searchQuery.searchBrand();
						}
					} else if (selectMenu.selectNum.equals("3")) { // 3�� ������ ����
						if (selectMenu.selectNum.equals("3")) {// 3�� ���ý�
							searchQuery.searchSelf(); // �ش����� self������ ������ �ٷ� ���
						}
						if (selectMenu.selectNum.equals("1")) {// ���� 1���� ������ �˻� ����
							searchQuery.searchMIN();
						} else if (selectMenu.selectNum.equals("2")) { // ���� 2���� �귣�� �˻� ����
							searchQuery.searchBrand();
						}
					} else if (selectMenu.selectNum.equals("4")) { // 4�� ���ϱ� ����
						if (selectMenu.selectNum.equals("3")) {// 3�� ���ý�
							searchQuery.searchSelf(); // �ش����� self������ ������ �ٷ� ���
						}
						if (selectMenu.selectNum.equals("1")) {// ���� 1���� ������ �˻� ����
							searchQuery.searchMIN();
						} else if (selectMenu.selectNum.equals("2")) { // ���� 2���� �귣�� �˻� ����
							searchQuery.searchBrand();
						}

					} else if (line.equals("9")) { // �빮���� ���ư���
						showMenu.mainMenu();
					}

				} else if (line.equals("2")) { // ���� �˻� ����/////////////////////////////////////////////
					showMenu.searchRoadMenu(); // �� �̸� �Ǵ� �� �̸��� �Է��ϼ���! (ex.�ø��ȷ�, ������) ��� �ȳ���.
					selectMenu.roadSelect(); // ���� �˻� �޼���()�� �Է� ����(Ű��)
					searchQuery.searchRoadname(); // �Է°��� ������ �־ �ٷ� ã��.
				} else if (line.equals("9")) {// �빮���� ���ư���
					showMenu.mainMenu();
				}else if(line.equals("0")) {
					pw.println("�̿��� �ּż� �����մϴ�.");
					pw.flush();
				} else { // main select ���� �ȽἭ else�� �߸��Է¹��� �߰�.
					pw.println("�߸� �Է��ϼ̽��ϴ�. �ٽ� �������ּ���");
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
			System.out.println("������ ��ٸ��ϴ�.");

			while (true) {
				Socket sock = server.accept();
				System.out.println("User has contact to our server.  IP:"+sock.getInetAddress());				
				ChatThread thread = new ChatThread(sock);

				thread.start();// �õ�

			} // while end

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}// main
	
}// Server



