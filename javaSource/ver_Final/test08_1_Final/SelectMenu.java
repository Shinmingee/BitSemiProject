//	Version buildup Date_5.28


package test08_1_Final;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;



/* SelectMenu selectMenu = new SelectMenu(br, pw);
 * showMenu() 바로 다음으로 입력값만 받고 리턴.
 * 다음단계로의 연결고리
 * */



class SelectMenu {
	String selectNum; // 클라이언트가 입력할 번호 run()에서 받아가기 위한 변수
	PrintWriter pw;
	BufferedReader br;
	ShowMenu showMenu;

	// ***쿼리문으로 보낼 변수
	String guInfo;
	String oilInfo;
	String brandInfo;
	String strOilInfo; // 클라이언트 화면에 유종 한글로 표시하기 위한 변수
	String roadNameInfo;

	public SelectMenu(BufferedReader br, PrintWriter pw) {
		this.br = br;
		this.pw = pw;
		showMenu = new ShowMenu(pw);
	}
//////////////////////////////////////////////////////////////     불필요
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
//				pw.println("서비스를 이용해주셔서 감사합니다!");
//				pw.flush();
//				// 종료하기
//				break;
//			default:
//				pw.println("잘못 입력하셨습니다. 다시 선택해주세요");
//				pw.flush();
//				showMenu.mainMenu();
//				mainSelect();
//				break;
//			}
//		} catch (Exception e) {
//			System.out.println(e);
//			e.printStackTrace();
//		}
//	}// mainSelect()/////////////////////////////////////////     불필요

	public void localSelect() {// 구 정보 받아서 guInfo에 저장
		String gu;
		try {
			String inputNum = br.readLine();
			this.selectNum = inputNum;
			switch (inputNum) {
			case "1":
				gu = "강남구";
				this.guInfo = gu;
				showMenu.choiceMenu();
				choiceSelect();
				break;
			case "2":
				gu = "강서구";
				this.guInfo = gu;
				showMenu.choiceMenu();
				choiceSelect();
				break;
			case "3":
				gu = "강동구";
				this.guInfo = gu;
				showMenu.choiceMenu();
				choiceSelect();
				break;
			case "4":
				gu = "강북구";
				this.guInfo = gu;
				showMenu.choiceMenu();
				choiceSelect();
				break;
			default:
				pw.println("잘못 입력하셨습니다. 다시 선택해주세요");
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
			case "3": // self 주유소
				break;
			default:
				pw.println("잘못 입력하셨습니다. 다시 선택해주세요");
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

	public void oilchoiceSelect() {// 오일 정보를 받아서 oilInfo에 저장
		String oil;

		try {
			String inputNum = br.readLine();
			switch (inputNum) {// 1. 고급유 2. 휘발유 3.경유 4.등유
			case "1":
				oil = "pr_oil";
				this.oilInfo = oil;
				this.strOilInfo = "고급유";
				break;
			case "2":
				oil = "oil";
				this.oilInfo = oil;
				this.strOilInfo = "휘발유";
				break;
			case "3":
				oil = "diesel";
				this.oilInfo = oil;
				this.strOilInfo = "경유";
				break;
			case "4":
				oil = "kerosene";
				this.oilInfo = oil;
				this.strOilInfo = "등유";
				break;
			default:
				pw.println("잘못 입력하셨습니다. 다시 선택해주세요");
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

	public void brandchoiceSelect() {// 브랜드 정보를 받아서 brandInfo에 저장
		String brand;
		try {
			String inputNum = br.readLine();
			switch (inputNum) {// 1. SK에너지 2. 현대오일뱅크 3. GS칼텍스 4.S-OIL
			case "1":
				brand = "SK에너지";
				this.brandInfo = brand;
				break;
			case "2":
				brand = "현대오일뱅크";
				this.brandInfo = brand;
				break;
			case "3":
				brand = "GS칼텍스";
				this.brandInfo = brand;
				break;
			case "4":
				brand = "S-OIL";
				this.brandInfo = brand;
				break;
			default:
				pw.println("잘못 입력하셨습니다. 다시 선택해주세요");
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

	public void roadSelect() {// 길이름/동이름 입력받아서 저장
		String roadName;
		try {
			roadName = br.readLine();
			this.roadNameInfo = roadName;

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}// SelectMenu 클래스의 localSelect()

} // SelectMenu 클래스


