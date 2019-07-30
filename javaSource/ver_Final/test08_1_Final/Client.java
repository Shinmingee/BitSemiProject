//	Version buildup Date_5.28

//	시연할 때 서버(DB)측은 우리쪽,  유저(선생님자리)에서는 DB세팅 X


package test08_1_Final;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;




/*  [클라이언트] - 서버 - ConnectionPool - Oracle DB
 * 
 *  접속한 [유저]는 서버가 보여주는 메뉴를 선택한다.
 *  원하는 정보를 찾기위해 각 단계별 메뉴 선택을 완료 하고
 *  결과값 받아낸다.
 *   
 * */




class InputThread extends Thread {
	Socket sock = null;
	BufferedReader br = null;

	public InputThread(Socket sock, BufferedReader br) {
		this.sock = sock;
		this.br = br;
	}// 생성자

	public void run() { // 유저화면 용도?
		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.equals("0")) {
					System.out.println("서버로부터 연결이 끊겼습니다.");
					System.out.println("[0]을 입력하시면 종료됩니다.");
					break;
				}
				System.out.println(line);
			} // while

			br.close();
			sock.close();
		} catch (Exception e) {
			System.out.println(e);
		} // try-catch
	}// run
	
}// InputThread



public class Client {
	public static void main(String[] args) {
		try {
			Socket sock = new Socket();
			InetSocketAddress ipep = new InetSocketAddress("127.0.0.1", 10001);
			sock.connect(ipep);

			InputStream input = sock.getInputStream();
			OutputStream output = sock.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(input));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(output));
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

			
			
			// Thread Class
			// 읽기
			InputThread in = new InputThread(sock, br);
			in.start();

			// 쓰기 보내기
			String line = null;
			while ((line = keyboard.readLine()) != null) {
				pw.println(line);
				pw.flush();
				if (line.equals("0")) { 
					System.out.println("프로그램이 종료 되었습니다.");
					break;
				}
			} // while

			pw.close();

			if (keyboard != null) {
				keyboard.close();
			}
			if (sock != null) {
				sock.close();
			}
			if (br != null) {
				br.close();
			}

			System.out.println("[ Program is terminated. ] ");
		} catch (Exception e) {
			System.out.println(e);
		} // try-catch

	}// main end

} // Client end


