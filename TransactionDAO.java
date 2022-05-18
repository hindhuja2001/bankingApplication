package bankinApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDAO implements TransactionDAOInterface {
	
	static Connection con;
	static
	{
		//con=new JDBC().connection();
		con=ConnectDatabase.getConnection();
	}
	public void addTransaction(int transactId,int senderId,int receipientId,int amount,String type) throws Exception
	{
		String query="insert into transaction(transactionID,senderID,receipientID,amount,type) values(?,?,?,?,?)";
		PreparedStatement st=con.prepareStatement(query);
		st.setInt(1, transactId);
		st.setInt(2, senderId);
		st.setInt(3, receipientId);
		st.setInt(4, amount);
		st.setString(5, type);
		int count=st.executeUpdate();
		
	}
	public int getLastID() throws Exception
	{
		String query1="select count(*) from transaction";
		ResultSet rs=new JDBC().queryResult(query1);
		rs.next();
		if(rs.getInt(1)==0)
		{
			return 0;
		}
		String query="select max(transactionID) from transaction";
		ResultSet result=new JDBC().queryResult(query);
		result.next();
		return result.getInt(1);
		
		
	}
	public ResultSet getTransactionsForId(int id) throws Exception
	{
		String query="select senderID,receipientID,amount,type,dateTime from transaction where senderID="+id+" or receipientID="+id;
		ResultSet rs=new JDBC().queryResult(query);
		return rs;
		
	}

}
