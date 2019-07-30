//	Version buildup Date_5.28


package test08_1_Final;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;



/* SelectMenu selectMenu = new SelectMenu(br, pw);
 * showMenu() �ٷ� �������� �Է°��� �ް� ����.
 * �����ܰ���� �����
 * */



class SelectMenu {
	String selectNum; // Ŭ���̾�Ʈ�� �Է��� ��ȣ run()���� �޾ư��� ���� ����
	PrintWriter pw;
	BufferedReader br;
	ShowMenu showMenu;

	// ***���������� ���� ����
	String guInfo;
	String oilInfo;
	String brandInfo;
	String strOilInfo; // Ŭ���̾�Ʈ ȭ�鿡 ���� �ѱ۷� ǥ���ϱ� ���� ����
	String roadNameInfo;

	public SelectMenu(BufferedReader br, PrintWriter pw) {
		this.br = br;
		this.pw = pw;
		showMenu = new ShowMenu(pw);
	}
//////////////////////////////////////////////////////////////     ���ʿ�
//	public void mainSelect() {  
//		try {
//			String inputNum = br.readLine();
//			switch (inputNum) {
//			case "1":
//				showMenu.localMenu();
//				localSelect();
//				break;
//			case "2":
//				break;
//			case "0":
//				pw.println("���񽺸� �̿����ּż� �����մϴ�!");
//				pw.flush();
//				// �����ϱ�
//				break;
//			default:
//				pw.println("�߸� �Է��ϼ̽��ϴ�. �ٽ� �������ּ���");
//				pw.flush();
//				showMenu.mainMenu();
//				mainSelect();
//				break;
//			}
//		} catch (Exception e) {
//			System.out.println(e);
//			e.printStackTrace();
//		}
//	}// mainSelect()/////////////////////////////////////////     ���ʿ�

	public void localSelect() {// �� ���� �޾Ƽ� guInfo�� ����
		String gu;
		try {
			String inputNum = br.readLine();
			this.selectNum = inputNum;
			switch (inputNum) {
			case "1":
				gu = "������";
				this.guInfo = gu;
				showMenu.choiceMenu();
				choiceSelect();
				break;
			case "2":
				gu = "������";
				this.guInfo = gu;
				showMenu.choiceMenu();
				choiceSelect();
				break;
			case "3":
				gu = "������";
				this.guInfo = gu;
				showMenu.choiceMenu();
				choiceSelect();
				break;
			case "4":
				gu = "���ϱ�";
				this.guInfo = gu;
				showMenu.choiceMenu();
				choiceSelect();
				break;
			default:
				pw.println("�߸� �Է��ϼ̽��ϴ�. �ٽ� �������ּ���");
				pw.flush();
				showMenu.localMenu();
				localSelect();
				break;

			}

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}// localSelect()

	public void choiceSelect() {
		try {
			String inputNum = br.readLine();
			this.selectNum = inputNum;
			switch (inputNum) {
			case "1":
				showMenu.oilChoiceMenu();
				oilchoiceSelect();
				break;
			case "2":
				showMenu.brandChoiceMenu();
				brandchoiceSelect();
				break;
			case "3": // self ������
				break;
			default:
				pw.println("�߸� �Է��ϼ̽��ϴ�. �ٽ� �������ּ���");
				pw.flush();
				showMenu.choiceMenu();
				choiceSelect();
				break;
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	} // choiceSelect()

	public void oilchoiceSelect() {// ���� ������ �޾Ƽ� oilInfo�� ����
		String oil;

		try {
			String inputNum = br.readLine();
			switch (inputNum) {// 1. ����� 2. �ֹ��� 3.���� 4.����
			case "1":
				oil = "pr_oil";
				this.oilInfo = oil;
				this.strOilInfo = "�����";
				break;
			case "2":
				oil = "oil";
				this.oilInfo = oil;
				this.strOilInfo = "�ֹ���";
				break;
			case "3":
				oil = "diesel";
				this.oilInfo = oil;
				this.strOilInfo = "����";
				break;
			case "4":
				oil = "kerosene";
				this.oilInfo = oil;
				this.strOilInfo = "����";
				break;
			default:
				pw.println("�߸� �Է��ϼ̽��ϴ�. �ٽ� �������ּ���");
				pw.flush();
				showMenu.oilChoiceMenu();
				oilchoiceSelect();
				break;
			}

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

	} // oilchoiceSelect()

	public void brandchoiceSelect() {// �귣�� ������ �޾Ƽ� brandInfo�� ����
		String brand;
		try {
			String inputNum = br.readLine();
			switch (inputNum) {// 1. SK������ 2. ������Ϲ�ũ 3. GSĮ�ؽ� 4.S-OIL
			case "1":
				brand = "SK������";
				this.brandInfo = brand;
				break;
			case "2":
				brand = "������Ϲ�ũ";
				this.brandInfo = brand;
				break;
			case "3":
				brand = "GSĮ�ؽ�";
				this.brandInfo = brand;
				break;
			case "4":
				brand = "S-OIL";
				this.brandInfo = brand;
				break;
			default:
				pw.println("�߸� �Է��ϼ̽��ϴ�. �ٽ� �������ּ���");
				pw.flush();
				showMenu.brandChoiceMenu();
				brandchoiceSelect();
				break;
			}

		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}

	} // brandchoiceSelect()

	public void roadSelect() {// ���̸�/���̸� �Է¹޾Ƽ� ����
		String roadName;
		try {
			roadName = br.readLine();
			this.roadNameInfo = roadName;

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}// SelectMenu Ŭ������ localSelect()

} // SelectMenu Ŭ����


