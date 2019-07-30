//	Version buildup Date_5.28

//	�ÿ��� �� ����(DB)���� �츮��,  ����(�������ڸ�)������ DB���� X




package test08_1_Final;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;




/*
 * SearchQuery searchQuery = new SearchQuery(pw, selectInfo);
 * ���� �ܰ��� �޴����� ������ ������ ���ϴ� [����� ���������� ���] ��.
 * ConnectionPool �� ����
 * Oracle DB
 * 
 * 
 * ** ���� ����� **
 * ~����  ~�⸧ ������
 * ~����  ~�귣�� ������
 * ~����  �������� ���� ����
 * �˻��� ������ ������ ����Ʈ
 * 
 * */



class SearchQuery {

	Statement stmt = null;
	ResultSet rs = null;
	String url = "jdbc:oracle:thin:@203.236.209.174:1521:xe"; //\/\/\/\/\/\ [ ����  ] /\/\/\/\/\\ 
	String user = "bitjavadb";
	String password = "bitjavadb";

	ConnectionPool cp = null;

	Connection conn1 = null; // ������ ã�� ��ü
	Connection conn2 = null; // �귣�� ã�� ��ü
	Connection conn3 = null; // self ������ ã�� ��ü
	Connection conn4 = null; // ���̸� ���̸� ã�� ��ü
	SelectMenu selectInfo;

	PrintWriter pw;

	SearchQuery(PrintWriter pw, SelectMenu selectInfo) {
		this.pw = pw;
		this.selectInfo = selectInfo;
		cp = ConnectionPool.createConnectionPool(url, user, password);
	}// ������

	StringBuffer sb = new StringBuffer();

	public void searchMIN() { // ������ ������ ã�� ����/////////////////////////////////////////////////////

		String gu = selectInfo.guInfo;
		String oil = selectInfo.oilInfo;
		String strOil = selectInfo.strOilInfo;

		conn1 = cp.getSession();

		try {
			pw.println(gu + "�� " + strOil + " �������� ��ȸ�մϴ�.");
			pw.println("�� ��ȣ | �� �ּ�  | �� ��ǥ  | �� ��ȭ��ȣ | �� �������� | �� " + strOil + " ");
			pw.println("-------------------------------------------------");
			pw.flush();
			stmt = this.conn1.createStatement();
			rs = stmt.executeQuery("SELECT name,addr,brand,phone,self," + oil + " FROM oil_pro WHERE " + oil
					+ " = (SELECT MIN(" + oil + ") FROM oil_pro GROUP BY gu HAVING gu = '" + gu + "')");
			while (rs.next()) {
				pw.print("�� " + rs.getString(1) + " | ");
				pw.print("�� " + rs.getString(2) + " | ");
				pw.print("�� " + rs.getString(3) + " | ");
				pw.print("�� " + rs.getString(4) + " | ");
				pw.print("�� " + rs.getString(5) + " | ");
				pw.println("�� " + rs.getString(6) + "�� | ");
			}
			pw.println();
			pw.println("#------------------------------------#");
			pw.println("      ó������ ���ư��÷��� [9]���� �Է����ּ���.  ");
			pw.println("    ���α׷��� �����Ͻ÷��� [0]���� �Է����ּ���.   ");
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

	public void searchBrand() { // �귣�庰 ������ ����Ʈ �̴� ����/////////////////////////////////////////////////////////

		String gu = selectInfo.guInfo;
		String brand = selectInfo.brandInfo;

		conn2 = cp.getSession();

		try {
			pw.println(gu + "�� " + brand + " ������ �⸧���� ��ȸ�մϴ�.");
			pw.println("�� ��ȣ | �� �ּ�  | �� ��ǥ  | �� ��ȭ��ȣ | �� �������� | �� ����ֹ��� | �� �ֹ���  | �� ����   | �� �ǳ�����");
			pw.println("-------------------------------------------------------------------------------");
			pw.flush();
			stmt = conn2.createStatement();
			rs = stmt.executeQuery(
					"SELECT name,loc,gu,addr,brand,phone,self, NVL(pr_oil,0), NVL(oil,0), NVL(diesel,0), NVL(kerosene,0) FROM oil_pro WHERE brand = '"
							+ brand + "' AND gu = '" + gu + "'");

			while (rs.next()) {
				pw.print("�� " + rs.getString(1) + " | ");
				pw.print("�� " + rs.getString(2) + " ");
				pw.print(rs.getString(3) + " ");
				pw.print(rs.getString(4) + " | ");
				pw.print("�� " + rs.getString(5) + " | ");
				pw.print("�� " + rs.getString(6) + " | ");
				pw.print("�� " + rs.getString(7) + " | ");
				pw.print("�� " + rs.getString(8) + "��| ");
				pw.print("�� " + rs.getString(9) + "��| ");
				pw.print("�� " + rs.getString(10) + "��| ");
				pw.println("�� " + rs.getString(11) + "��| ");
			}

			pw.println();
			pw.println("#------------------------------------#");
			pw.println("      ó������ ���ư��÷��� [9]���� �Է����ּ���.  ");
			pw.println("    ���α׷��� �����Ͻ÷��� [0]���� �Է����ּ���. ");
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

			pw.println("\n" + gu + "��  self�����Ҹ� ��ȸ�մϴ�.\n");
			pw.println("�� ��ȣ | �� �ּ�  | �� ��ǥ  | �� ��ȭ��ȣ | �� ����ֹ��� | �� �ֹ���  | �� ����  | �� �ǳ�����");
			pw.println("---------------------------------------------------------------------");
			pw.flush();
			stmt = conn3.createStatement();
			rs = stmt.executeQuery(
					"SELECT name, addr, brand, phone, NVL(pr_oil, 0), NVL(oil, 0), NVL(diesel,0) ,NVL(kerosene,0) FROM oil_pro WHERE self = 'Y' AND gu = '"
							+ gu + "'");

			while (rs.next()) {
				pw.print("�� " + rs.getString(1) + " | ");
				pw.print("�� " + rs.getString(2) + " | ");
				pw.print("�� " + rs.getString(3) + " | ");
				pw.print("�� " + rs.getString(4) + " | ");
				pw.print("�� " + rs.getString(5) + "��| ");
				pw.print("�� " + rs.getString(6) + "��| ");
				pw.print("�� " + rs.getString(7) + "��| ");
				pw.println("�� " + rs.getString(8) + "��| ");
			}

			pw.println();
			pw.println("#------------------------------------#");
			pw.println("      ó������ ���ư��÷��� [9]���� �Է����ּ���.  ");
			pw.println("    ���α׷��� �����Ͻ÷��� [0]���� �Է����ּ���. ");
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

	public void searchRoadname() { // ���̸� �Ǵ� ���̸����� ������ �˻��ϴ� ����/////////////////////////////////////////////////////

		String roadName = selectInfo.roadNameInfo;

		conn4 = cp.getSession();

		try {
			stmt = conn4.createStatement();
			rs = stmt.executeQuery(
					"SELECT name,addr,brand,phone,self, NVL(pr_oil,0), NVL(oil,0), NVL(diesel,0), NVL(kerosene,0) FROM oil_pro WHERE addr LIKE '%"
							+ roadName + "%'");
			
			
			// ��ȸ �����Ͱ� �������� �ʴ´ٸ�.
//			if (rs.next()==false) {
//				pw.println("���� �������� �ʽ��ϴ�.");
//				pw.flush();
//			}
			
			
			pw.println(roadName + "�� ������ �⸧���� ��ȸ�մϴ�.");
			pw.println("�� ��ȣ | �� �ּ�  | �� ��ǥ  | �� ��ȭ��ȣ | �� �������� | �� ����ֹ��� | �� �ֹ���  | �� ����   | �� �ǳ�����");
			pw.println("------------------------------------------------------------------------------");
			pw.flush();

			while (rs.next()) {
				
				pw.print("�� " + rs.getString(1) + " | ");
				pw.print("�� " + rs.getString(2) + " ");
				pw.print("�� " + rs.getString(3) + " ");
				pw.print("�� " + rs.getString(4) + " | ");
				pw.print("�� " + rs.getString(5) + " | ");
				pw.print("�� " + rs.getString(6) + "��| ");
				pw.print("�� " + rs.getString(7) + "��| ");
				pw.print("�� " + rs.getString(8) + "��| ");
				pw.println("�� " + rs.getString(9) + "��| ");
			}
			

			pw.println();// ����
			pw.println("#------------------------------------#");
			pw.println("      ó������ ���ư��÷��� [9]���� �Է����ּ���.  ");
			pw.println("    ���α׷��� �����Ͻ÷��� [0]���� �Է����ּ���. ");
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




