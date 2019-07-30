//	Version buildup Date_5.28

//	시연할 때 서버(DB)측은 우리쪽,  유저(선생님자리)에서는 DB세팅 X




package test08_1_Final;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;




/*
 * SearchQuery searchQuery = new SearchQuery(pw, selectInfo);
 * 최종 단계의 메뉴에서 선택이 끝나면 원하는 [결과값 쿼리문으로 출력] 함.
 * ConnectionPool 과 접촉
 * Oracle DB
 * 
 * 
 * ** 현재 결과값 **
 * ~구의  ~기름 최저가
 * ~구의  ~브랜드 주유소
 * ~구의  셀프주유 가능 여부
 * 검색한 지역의 주유소 리스트
 * 
 * */



class SearchQuery {

	Statement stmt = null;
	ResultSet rs = null;
	String url = "jdbc:oracle:thin:@203.236.209.174:1521:xe"; //\/\/\/\/\/\ [ 변경  ] /\/\/\/\/\\ 
	String user = "bitjavadb";
	String password = "bitjavadb";

	ConnectionPool cp = null;

	Connection conn1 = null; // 최저가 찾는 객체
	Connection conn2 = null; // 브랜드 찾는 객체
	Connection conn3 = null; // self 주유소 찾는 객체
	Connection conn4 = null; // 길이름 동이름 찾는 객체
	SelectMenu selectInfo;

	PrintWriter pw;

	SearchQuery(PrintWriter pw, SelectMenu selectInfo) {
		this.pw = pw;
		this.selectInfo = selectInfo;
		cp = ConnectionPool.createConnectionPool(url, user, password);
	}// 생성자

	StringBuffer sb = new StringBuffer();

	public void searchMIN() { // 최저가 주유소 찾는 쿼리/////////////////////////////////////////////////////

		String gu = selectInfo.guInfo;
		String oil = selectInfo.oilInfo;
		String strOil = selectInfo.strOilInfo;

		conn1 = cp.getSession();

		try {
			pw.println(gu + "의 " + strOil + " 최저가를 조회합니다.");
			pw.println("① 상호 | ② 주소  | ③ 상표  | ④ 전화번호 | ⑤ 셀프여부 | ⑥ " + strOil + " ");
			pw.println("-------------------------------------------------");
			pw.flush();
			stmt = this.conn1.createStatement();
			rs = stmt.executeQuery("SELECT name,addr,brand,phone,self," + oil + " FROM oil_pro WHERE " + oil
					+ " = (SELECT MIN(" + oil + ") FROM oil_pro GROUP BY gu HAVING gu = '" + gu + "')");
			while (rs.next()) {
				pw.print("① " + rs.getString(1) + " | ");
				pw.print("② " + rs.getString(2) + " | ");
				pw.print("③ " + rs.getString(3) + " | ");
				pw.print("④ " + rs.getString(4) + " | ");
				pw.print("⑤ " + rs.getString(5) + " | ");
				pw.println("⑥ " + rs.getString(6) + "원 | ");
			}
			pw.println();
			pw.println("#------------------------------------#");
			pw.println("      처음으로 돌아가시려면 [9]번을 입력해주세요.  ");
			pw.println("    프로그램을 종료하시려면 [0]번을 입력해주세요.   ");
			pw.println("#------------------------------------#");
			pw.flush();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (this.conn1 != null) {
					this.conn1 = cp.exitSession(conn1);
				}

			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}// searchMIN()

	public void searchBrand() { // 브랜드별 주유소 리스트 뽑는 쿼리/////////////////////////////////////////////////////////

		String gu = selectInfo.guInfo;
		String brand = selectInfo.brandInfo;

		conn2 = cp.getSession();

		try {
			pw.println(gu + "의 " + brand + " 주유소 기름값을 조회합니다.");
			pw.println("① 상호 | ② 주소  | ③ 상표  | ④ 전화번호 | ⑤ 셀프여부 | ⑥ 고급휘발유 | ⑦ 휘발유  | ⑧ 경유   | ⑨ 실내등유");
			pw.println("-------------------------------------------------------------------------------");
			pw.flush();
			stmt = conn2.createStatement();
			rs = stmt.executeQuery(
					"SELECT name,loc,gu,addr,brand,phone,self, NVL(pr_oil,0), NVL(oil,0), NVL(diesel,0), NVL(kerosene,0) FROM oil_pro WHERE brand = '"
							+ brand + "' AND gu = '" + gu + "'");

			while (rs.next()) {
				pw.print("① " + rs.getString(1) + " | ");
				pw.print("② " + rs.getString(2) + " ");
				pw.print(rs.getString(3) + " ");
				pw.print(rs.getString(4) + " | ");
				pw.print("③ " + rs.getString(5) + " | ");
				pw.print("④ " + rs.getString(6) + " | ");
				pw.print("⑤ " + rs.getString(7) + " | ");
				pw.print("⑥ " + rs.getString(8) + "원| ");
				pw.print("⑦ " + rs.getString(9) + "원| ");
				pw.print("⑧ " + rs.getString(10) + "원| ");
				pw.println("⑨ " + rs.getString(11) + "원| ");
			}

			pw.println();
			pw.println("#------------------------------------#");
			pw.println("      처음으로 돌아가시려면 [9]번을 입력해주세요.  ");
			pw.println("    프로그램을 종료하시려면 [0]번을 입력해주세요. ");
			pw.println("#------------------------------------#");
			pw.flush();

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}

				if (conn2 != null) {
					conn2 = cp.exitSession(conn2);
				}

			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}// searchBrand()

	public void searchSelf() {
		String gu = selectInfo.guInfo;

		conn3 = cp.getSession();

		try {

			pw.println("\n" + gu + "의  self주유소를 조회합니다.\n");
			pw.println("① 상호 | ② 주소  | ③ 상표  | ④ 전화번호 | ⑤ 고급휘발유 | ⑥ 휘발유  | ⑦ 경유  | ⑧ 실내등유");
			pw.println("---------------------------------------------------------------------");
			pw.flush();
			stmt = conn3.createStatement();
			rs = stmt.executeQuery(
					"SELECT name, addr, brand, phone, NVL(pr_oil, 0), NVL(oil, 0), NVL(diesel,0) ,NVL(kerosene,0) FROM oil_pro WHERE self = 'Y' AND gu = '"
							+ gu + "'");

			while (rs.next()) {
				pw.print("① " + rs.getString(1) + " | ");
				pw.print("② " + rs.getString(2) + " | ");
				pw.print("③ " + rs.getString(3) + " | ");
				pw.print("④ " + rs.getString(4) + " | ");
				pw.print("⑤ " + rs.getString(5) + "원| ");
				pw.print("⑥ " + rs.getString(6) + "원| ");
				pw.print("⑦ " + rs.getString(7) + "원| ");
				pw.println("⑧ " + rs.getString(8) + "원| ");
			}

			pw.println();
			pw.println("#------------------------------------#");
			pw.println("      처음으로 돌아가시려면 [9]번을 입력해주세요.  ");
			pw.println("    프로그램을 종료하시려면 [0]번을 입력해주세요. ");
			pw.println("#------------------------------------#");
			pw.flush();

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}

				if (conn3 != null) {
					conn3 = cp.exitSession(conn3);
				}

			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}

	}// searchSelf()

	public void searchRoadname() { // 길이름 또는 동이름으로 주유소 검색하는 쿼리/////////////////////////////////////////////////////

		String roadName = selectInfo.roadNameInfo;

		conn4 = cp.getSession();

		try {
			stmt = conn4.createStatement();
			rs = stmt.executeQuery(
					"SELECT name,addr,brand,phone,self, NVL(pr_oil,0), NVL(oil,0), NVL(diesel,0), NVL(kerosene,0) FROM oil_pro WHERE addr LIKE '%"
							+ roadName + "%'");
			
			
			// 조회 데이터가 존재하지 않는다면.
//			if (rs.next()==false) {
//				pw.println("값이 존재하지 않습니다.");
//				pw.flush();
//			}
			
			
			pw.println(roadName + "의 주유소 기름값을 조회합니다.");
			pw.println("① 상호 | ② 주소  | ③ 상표  | ④ 전화번호 | ⑤ 셀프여부 | ⑥ 고급휘발유 | ⑦ 휘발유  | ⑧ 경유   | ⑨ 실내등유");
			pw.println("------------------------------------------------------------------------------");
			pw.flush();

			while (rs.next()) {
				
				pw.print("① " + rs.getString(1) + " | ");
				pw.print("② " + rs.getString(2) + " ");
				pw.print("③ " + rs.getString(3) + " ");
				pw.print("④ " + rs.getString(4) + " | ");
				pw.print("⑤ " + rs.getString(5) + " | ");
				pw.print("⑥ " + rs.getString(6) + "원| ");
				pw.print("⑦ " + rs.getString(7) + "원| ");
				pw.print("⑧ " + rs.getString(8) + "원| ");
				pw.println("⑨ " + rs.getString(9) + "원| ");
			}
			

			pw.println();// 개행
			pw.println("#------------------------------------#");
			pw.println("      처음으로 돌아가시려면 [9]번을 입력해주세요.  ");
			pw.println("    프로그램을 종료하시려면 [0]번을 입력해주세요. ");
			pw.println("#------------------------------------#");
			pw.flush();

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}

				if (conn4 != null) {
					conn4 = cp.exitSession(conn4);
				}

			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}// searchBrand()

}// SearchQuery




