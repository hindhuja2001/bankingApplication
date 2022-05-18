package bankinApp;
import java.sql.*;
public class JDBC {
		public Connection connection()
		{
			Connection con = null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Bank","root","Hindhuja@2001");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return con;
		}
		public ResultSet queryResult(String sql)
		{
			Connection con = null;
			ResultSet rs=null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Bank","root","Hindhuja@2001");
				Statement st=con.createStatement();
				rs=st.executeQuery(sql);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return rs;
		}
	

}
