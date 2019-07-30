//	Version buildup Date_5.28

//	�ÿ��� �� ����(DB)���� �츮��,  ����(�������ڸ�)������ DB���� X


package test08_1_Final;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;




/*  [Ŭ���̾�Ʈ] - ���� - ConnectionPool - Oracle DB
 * 
 *  ������ [����]�� ������ �����ִ� �޴��� �����Ѵ�.
 *  ���ϴ� ������ ã������ �� �ܰ躰 �޴� ������ �Ϸ� �ϰ�
 *  ����� �޾Ƴ���.
 *   
 * */




class InputThread extends Thread {
	Socket sock = null;
	BufferedReader br = null;

	public InputThread(Socket sock, BufferedReader br) {
		this.sock = sock;
		this.br = br;
	}// ������

	public void run() { // ����ȭ�� �뵵?
		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.equals("0")) {
					System.out.println("�����κ��� ������ ������ϴ�.");
					System.out.println("[0]�� �Է��Ͻø� ����˴ϴ�.");
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
			// �б�
			InputThread in = new InputThread(sock, br);
			in.start();

			// ���� ������
			String line = null;
			while ((line = keyboard.readLine()) != null) {
				pw.println(line);
				pw.flush();
				if (line.equals("0")) { 
					System.out.println("���α׷��� ���� �Ǿ����ϴ�.");
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


