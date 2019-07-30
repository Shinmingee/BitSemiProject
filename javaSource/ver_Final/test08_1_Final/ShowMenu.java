//	Version buildup Date_5.28


package test08_1_Final;

import java.io.PrintWriter;



/*	ShowMenu menu = new ShowMenu(pw);
 *  각 단계의  [안내 문구만 출력함]
 *  Server클래스  start() 메서드 실행
 * 
 * */



class ShowMenu { /// menu

	PrintWriter pw; 

	ShowMenu(PrintWriter pw) {
		this.pw = pw;
	}

	public void mainMenu() { // menu.대문
		pw.println("\n****** 주유소  안내 데스크 입니다 ******");
		pw.println("*** 원하시는 서비스 번호를 입력하세요! ***\n");
		pw.println("\n 1. 지역별 주유소 찾기    2. 가까운 주유소 검색하기(길 이름/동 이름)   0. 종료하기(quit)");
		pw.println("------------------------------------------------------------------");
		pw.flush();
	}

	public void localMenu() { /// 대문 > 1 > menu.localMenu()
		pw.println("\n검색할 지역 번호를 입력하세요!\n");
		pw.println("1. 강남구      2. 강서구      3. 강동구      4. 강북구");
		pw.println("--------------------------------------");
		pw.flush();
	}

	public void searchRoadMenu() { /// 대문 > 2 > menu.searchRoadMenu()
		pw.println("\n길 이름 또는 동 이름을 입력하세요!  (ex.올림픽로, 개포동)");
		pw.println("--------------------------------------------");
		pw.flush();
	}

	public void choiceMenu() { // 대문> menu.localMenu() > menu.choiceMenu()
		pw.println("\n원하시는 서비스 번호를 입력하세요!\n");
		pw.println("1. 최저가 주유소 찾기      2. 브랜드별 주유소 찾기     3. self 주유소 찾기"); // 3번만 민지 추가함.
		pw.println("-----------------------------------------------------");
		pw.flush();//////////////////////////////////////// 공통
	}

	public void oilChoiceMenu() { // 대문 > menu.localMenu() > menu.choiceMenu() > menu.oilChoiceMenu()
		pw.println("\n연료 종류 번호를 입력하세요!\n");
		pw.println("1. 고급유      2. 휘발유      3.경유      4.등유");
		pw.println("---------------------------------");
		pw.flush();///////////// searchMIN() 메서드로 이동
	}

	public void brandChoiceMenu() { // 대문 > menu.localMenu() > menu.choiceMenu() > menu.brandChoiceMenu()
		pw.println("\n주유소 브랜드 번호를 입력하세요!\n");
		pw.println("1. SK에너지      2. 현대오일뱅크      3. GS칼텍스      4.S-OIL");
		pw.println("---------------------------------------------");
		pw.flush();///////////// searchBrand() 메서드로 이동
	}

} // ShowMenu end



