package bankinApp;

import java.sql.*;

public class CustomerDAO implements DataAccessObject,CustomerDAOInterface {
	static Connection con;
	static
	{
		//con=new JDBC().connection();
		con=ConnectDatabase.getConnection();
	}
	
	public void addCustomer(int accountNumber,String username,String password,String name,String address,int balance,String transactionPwd,int benId,String phoneNumber) throws Exception
	{
		BeneficiaryDAO bdao=new BeneficiaryDAO();
		bdao.addBeneficiaryDao(accountNumber, benId);
		String query="insert into customer(account_number,username,password,name,address,balance,transaction_password,status,phoneNumber) values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement st=con.prepareStatement(query);
		st.setInt(1,accountNumber);
		st.setString(2,username);
		st.setString(3,password);
		st.setString(4, name);
		st.setString(5, address);
		st.setInt(6,balance);
		st.setString(7, transactionPwd);
		st.setString(8, "active");
		st.setString(9, phoneNumber);
		try {
			int count=st.executeUpdate();
		}catch(SQLIntegrityConstraintViolationException e)   // To warn if the password already taken
		{
			throw new CustomException("Password already exists");
		}
		
	}
	
	public int getLastID() throws Exception
	{
		String query1="select count(*) from customer";
		ResultSet rs=new JDBC().queryResult(query1);
		rs.next();
		if(rs.getInt(1)==0)
		{
			return 0;
		}
		String query="select max(account_number) from customer";
		ResultSet result=new JDBC().queryResult(query);
		result.next();
		return result.getInt(1);
		
		
	}
	
	public void updateValue(String attribute,int value,int id) throws Exception
	{
		String query="update customer set "+attribute+"="+value+" where account_number="+id;
		Statement st=con.createStatement();
		st.executeUpdate(query);
	}
	
	public void updateValue(String attribute,String value,int id) throws Exception
	{
		String query="update customer set "+attribute+"='"+value+"' where account_number="+id;
		Statement st=con.createStatement();
		st.executeUpdate(query);
	}
	
	public int viewAccountBalance(int id)
	{
		String query="select balance from customer where account_number="+id;
		ResultSet rs=new JDBC().queryResult(query);
		
		try {
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	//To check whether the account number is blocked
	public static boolean verifyBlockedCustomer(int accountNumber) throws SQLException, CustomException
	{
		String query="select * from blockedcustomer where account_number="+accountNumber;
		ResultSet rs=new JDBC().queryResult(query);
		if (!rs.isBeforeFirst() ) {    
		    return false;
		} 
		return true;
	}
	
	//To check whether the account number is closed
	public static boolean verifyClosedAccount(int accountNumber) throws SQLException
	{
		String query="select status from customer where account_number="+accountNumber;
		ResultSet rs=new JDBC().queryResult(query);
		rs.next();
		if(rs.getString(1).equals("closed"))
		{
			return true;
		}
		return false;
	}
	
	
	public Customer getCustomerObject(String username,String pwd) throws SQLException, CustomException
	{
		
		String query="select * from customer where username='"+username+"' and password='"+pwd+"'";
		ResultSet rs=new JDBC().queryResult(query);
		if (!rs.isBeforeFirst() ) {    
		    throw new  CustomException("Username and password mismatch");
		} 
		rs.next();
		int id=rs.getInt(1);
		String uname=rs.getString(2);
		String password=rs.getString(3);
		String name=rs.getString(4);
		String address=rs.getString(5);
		int amount=rs.getInt(6);
		String transactpwd=rs.getString(7);
		String phoneNumber=rs.getString(8);
		if(CustomerDAO.verifyBlockedCustomer(id))
		{
			throw new CustomException("Your account is blocked");
		}
		else if(CustomerDAO.verifyClosedAccount(id))
		{
			throw new CustomException("Your account no longer exists");
		}
		Customer customer=new Customer(id,uname,password,name,address,amount,transactpwd,phoneNumber);
		return customer;
		
		
	}
	
	public void requestToUnblock(int id) throws SQLException, CustomException
	{
		
		String query="insert into requestUnblock(accountNumber) values("+id+")";
		Statement st=con.createStatement();
		st.executeUpdate(query);
			
		
	}
	public void requestToUnblock(String username) throws SQLException, CustomException
	{
		String selectQuery="select account_number from customer where username='"+username+"'";
		ResultSet rs1=new JDBC().queryResult(selectQuery);
		rs1.next();
		int id=rs1.getInt(1);
		String insertQuery="insert into requestUnblock(accountNumber) values("+id+")";
		Statement st=con.createStatement();
		st.executeUpdate(insertQuery);
		
		
		
		
		
	}
	
	public void removeUser(int accountNumber) throws Exception
	{
		this.updateValue("status","closed", accountNumber);
	}
	
}
