/*2 寫一支 JAVA程式 Procedure1.java 包含兩個功能
  a. 輸入一筆電影 放映時間, 電影代號, 廳院 到 playlist 放映表格
  b. 參考廳院座位表 m_room, 產生(新增)指定場次的電影座次表 到 seats表格
提示
   a. 先執行 SQL-IMP作業2-0create table.sql scripts 建立相關表格 (playlist, m_room, seats)
      先暫不用建立客戶資料, 訂購明細 等表格
   b. JAVA程式中 呼叫 JDBC 輸入'2016-12-25 13:00', 1, 'A廳' 到 playlist 表格
      insert into playlist values ('2016-12-25 13:00', 1, 'A廳');
   c. JAVA程式中 查詢廳院座位表 m_room, 找出指定廳的座位數(row, col)
   d. 寫兩個迴圈 將該場次所有座位新增到 seats 表格 
      根據座位數 v_row, v_col 產生座位表 寫迴圈 insert 
      insert into seats values ('2016-12-25 13:00', 1, v_row-v_col組合, '0', NULL);
*/


package PRACTICE;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Procedure {

	public static void main(String[] args) {
		
		try (
			 Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=DB01",
		                       "sa","P@ssw0rd");
		     PreparedStatement ps1 = conn.prepareStatement("insert into playlist values (?,?,?)");
			 PreparedStatement ps2 = conn.prepareStatement("select * from m_room where roomid=? ");
			 PreparedStatement ps3 = conn.prepareStatement("insert into seats values (?,?,?,?,NULL)");
			 Statement st2 = conn.createStatement();
			 
				)
		{ 
			
			conn.setAutoCommit(false);
			
			// 2.建立A廳電影時刻表
			Calendar dt = new GregorianCalendar();
			dt.set(2016,	 12, 25, 13, 00, 00);
			Date mdt = new java.sql.Date(dt.getTimeInMillis());    //將日期設定成SQL日期格式
			
			ps1.setDate(1, mdt);
			ps1.setInt(2, 1);
			ps1.setString(3, "A廳");
			ps1.addBatch();
			ps1.execute();
			conn.commit();
			
			// 3.讀取特定廳座位行列數
			int row=0, col=0;
			String room = "A廳", seat="";           //輸入欲查詢的影廳
			ps2.setString(1, room);      
			ResultSet rs2 = ps2.executeQuery();   //回傳設定select指令
			
			while (rs2.next()) {
				row = rs2.getInt("seat_row");   //讀取select結果
				col = rs2.getInt("seat_col");   //讀取select結果
			}
			
			
			// 4.增添場次座位表('2016-12-25 13:00', 1, v_row-v_col組合, '0', NULL)
			conn.setAutoCommit(false);
			
			for(int i=1; i<=row; i++) {
				for(int j=1; j<=col; j++) {
					seat = i+"-"+j;
					ps3.setDate(  1, mdt);
					ps3.setInt(   2, 1);
					ps3.setString(3, seat);
					ps3.setString(4,"0");
//					ps3.setNull(5, java.sql.Types());
					ps3.addBatch();
					
					System.out.println("寫入場次1座位"+seat);
				}
				ps3.executeBatch();
				ps3.clearBatch();
			}
			
			
			conn.commit();
			
			
			
			
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}


	
}
