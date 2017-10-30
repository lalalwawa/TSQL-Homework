/*5 寫一支 JAVA程式 Procedure2.java 包含兩個功能
  a 輸入一筆電影 放映時間, 電影代號, 廳院 到 playlist 放映表格
  b 呼叫 2-4 建立之 stored procedure: gen_seats (新增)指定場次的電影座次表 到 seats表格
提示
   a 先執行 scripts 建立表格
   b JAVA程式中 呼叫 JDBC 輸入'2016-12-25 13:00', 1, 'A廳' 到 playlist 表格
   c JAVA程式中 呼叫 gen_seats stored procedure(新增)指定場次的電影座次表 到 seats表格
 */


package PRACTICE;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Procedure2 {

	public static void main(String[] args) {
		
		try (	Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=DB01",
                "sa","P@ssw0rd");
				PreparedStatement st0 = conn.prepareStatement("insert into playlist values (?,?,?)");
				CallableStatement st1 = conn.prepareCall("{ Call dbo.gen_seats2(?,?,?) }");	
				){
			
			conn.setAutoCommit(false);
			
			Calendar dt = new GregorianCalendar();
			dt.set(2016,	 12, 25, 13, 00, 00);
			Date mdt = new java.sql.Date(dt.getTimeInMillis());    //將日期設定成SQL日期格式
			
			
			st0.setDate(1, mdt);                                
			st0.setInt(2, 4);
			st0.setString(3, "A廳");
			st0.execute();                                         //新增電影場次到playlist
			
			st1.setDate(1, mdt);
			st1.setInt(2, 4);
			st1.setString(3, "A廳");
			st1.execute();                                        //call sp, 新增座位到該場
			
			conn.commit();
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
