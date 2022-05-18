package bankinApp;

import java.sql.*;

public class AdminDAO implements AdminDAOInterface {
	static Connection con;
	static
	{
		//con=new JDBC().connection();
		con=ConnectDatabase.getConnection();
	}
	

	public ResultSet getAllCustomer() throws SQLException
	{
		String query="select customer.account_number,customer.name,customer.address,customer.username,customer.balance,customer.phoneNumber,customer.status,COALESCE(blockedcustomer.blockedDateTime, 'Unblocked') as BlockedTime from customer left join blockedCustomer on customer.account_number=blockedCustomer.account_number";
		ResultSet rs=new JDBC().queryResult(query);
		return rs;
	}
	
	public ResultSet getAllTransaction() throws SQLException
	{
		String query="select * from transaction";
		ResultSet rs=new JDBC().queryResult(query);
		return rs;
	}
	
	public void blockCustomer(int accountNumber) throws Exception
	{ 
		String query="insert into blockedcustomer(account_number) values("+accountNumber+")";
		Statement st=con.createStatement();
		try {
			st.executeUpdate(query);
		}catch(java.sql.SQLIntegrityConstraintViolationException e)
		{
			throw new CustomException("Account number doesn't exists");
		}
		st.close();
	}
	
	public void unblockCustomer(int accountNumber) throws Exception
	{
		String query="delete from blockedcustomer where account_number="+accountNumber;
		Statement st=con.createStatement();
		st.executeUpdate(query);
		st.close();
	}
	
	public ResultSet getBlockedCustomer()
	{
		String query="select blockedcustomer.account_number,blockedcustomer.blockedDateTime,customer.name,customer.address,customer.balance from blockedcustomer inner join customer on blockedcustomer.account_number=customer.account_number";
		ResultSet rs=new JDBC().queryResult(query);
		return rs;
	}
	 
	public ResultSet getRequestsToUnblock()
	{
		String query="select requestunblock.requestDate,requestunblock.accountNumber,blockedcustomer.blockedDateTime,customer.name,customer.address,customer.phoneNumber,customer.username,customer.balance from requestunblock inner join blockedcustomer on requestunblock.accountNumber=blockedcustomer.account_number join customer on customer.account_number=blockedcustomer.account_number group by accountNumber";
		ResultSet rs=new JDBC().queryResult(query);
		return rs;
	}
	
	public ResultSet getTransactionByAccountNumber(int id)
	{
		String query="select * from transaction where senderID="+id+" or receipientID="+id;
		ResultSet rs=new JDBC().queryResult(query);
		return rs;
	}
	
	public ResultSet getTransactionByDate(String date1,String date2)
	{
		String query="select * from transaction where dateTime between '"+date1+"' and '"+date2+"'";
		ResultSet rs=new JDBC().queryResult(query);
		return rs;
	}
	
	public ResultSet getClosedAccountDetails() throws Exception
	{
		String query="select account_number,username,name,address,balance from customer where status='closed'";
		ResultSet rs=new JDBC().queryResult(query);
		return rs;
	}
	
	public Admin getAdminObject(String adminId,String pwd) throws SQLException, CustomException
	{
		
		String query="select * from admin where admin_id='"+adminId+"' and password='"+pwd+"'";
		ResultSet rs=new JDBC().queryResult(query);
		if (!rs.isBeforeFirst() ) {    
		    throw new  CustomException("Username and password mismatch");
		} 
		rs.next();
		int id=rs.getInt(1);
		String name=rs.getString(2);
		String password=rs.getString(3);
		Admin admin=new Admin(id,name,password);
		return admin;
	}
	
}
