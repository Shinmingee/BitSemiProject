//	Version buildup Date_5.28


package test08_1_Final;

import java.io.PrintWriter;



/*	ShowMenu menu = new ShowMenu(pw);
 *  �� �ܰ���  [�ȳ� ������ �����]
 *  ServerŬ����  start() �޼��� ����
 * 
 * */



class ShowMenu { /// menu

	PrintWriter pw; 

	ShowMenu(PrintWriter pw) {
		this.pw = pw;
	}

	public void mainMenu() { // menu.�빮
		pw.println("\n****** ������  �ȳ� ����ũ �Դϴ� ******");
		pw.println("*** ���Ͻô� ���� ��ȣ�� �Է��ϼ���! ***\n");
		pw.println("\n 1. ������ ������ ã��    2. ����� ������ �˻��ϱ�(�� �̸�/�� �̸�)   0. �����ϱ�(quit)");
		pw.println("------------------------------------------------------------------");
		pw.flush();
	}

	public void localMenu() { /// �빮 > 1 > menu.localMenu()
		pw.println("\n�˻��� ���� ��ȣ�� �Է��ϼ���!\n");
		pw.println("1. ������      2. ������      3. ������      4. ���ϱ�");
		pw.println("--------------------------------------");
		pw.flush();
	}

	public void searchRoadMenu() { /// �빮 > 2 > menu.searchRoadMenu()
		pw.println("\n�� �̸� �Ǵ� �� �̸��� �Է��ϼ���!  (ex.�ø��ȷ�, ������)");
		pw.println("--------------------------------------------");
		pw.flush();
	}

	public void choiceMenu() { // �빮> menu.localMenu() > menu.choiceMenu()
		pw.println("\n���Ͻô� ���� ��ȣ�� �Է��ϼ���!\n");
		pw.println("1. ������ ������ ã��      2. �귣�庰 ������ ã��     3. self ������ ã��"); // 3���� ���� �߰���.
		pw.println("-----------------------------------------------------");
		pw.flush();//////////////////////////////////////// ����
	}

	public void oilChoiceMenu() { // �빮 > menu.localMenu() > menu.choiceMenu() > menu.oilChoiceMenu()
		pw.println("\n���� ���� ��ȣ�� �Է��ϼ���!\n");
		pw.println("1. �����      2. �ֹ���      3.����      4.����");
		pw.println("---------------------------------");
		pw.flush();///////////// searchMIN() �޼���� �̵�
	}

	public void brandChoiceMenu() { // �빮 > menu.localMenu() > menu.choiceMenu() > menu.brandChoiceMenu()
		pw.println("\n������ �귣�� ��ȣ�� �Է��ϼ���!\n");
		pw.println("1. SK������      2. ������Ϲ�ũ      3. GSĮ�ؽ�      4.S-OIL");
		pw.println("---------------------------------------------");
		pw.flush();///////////// searchBrand() �޼���� �̵�
	}

} // ShowMenu end



