package bankinApp;
import java.sql.*;
public class Admin {
	private int adminId;
	private String name;
	private String password;
	
	//To access the database class
	static AdminDAOInterface admindao;
	
	Admin(int adminId,String name,String password)
	{
		this.adminId=adminId;
		this.name=name;
		this.password=password;
		admindao=new AdminDAO();
	}
	
	//Getting admin object through id and password to perform operations
	public static Admin getObject(String adminId,String password) throws SQLException, CustomException
	{
		AdminDAOInterface admindao=new AdminDAO();
		Admin admin=admindao.getAdminObject(adminId, password);
		return admin;
		
	}
	
	public ResultSet getClosedAccounts() throws Exception
	{
		return admindao.getClosedAccountDetails();
	}
	
	public ResultSet getCustomer() throws SQLException
	{
		return admindao.getAllCustomer();
	}
	
	public ResultSet getTransactions() throws SQLException
	{
		return admindao.getAllTransaction();
	}
	
	public ResultSet getBlockedCustomer()
	{
		return admindao.getBlockedCustomer();
	}
	
	public ResultSet getUnblockRequests()
	{
		return admindao.getRequestsToUnblock();
	}
	
	//To block customer by account number
	public void blockCustomer(int accountNumber) throws Exception
	{
		AdminDAOInterface admindao=new AdminDAO();
		admindao.blockCustomer(accountNumber);
	}
	
	public void unblockCustomer(int accountNumber) throws Exception
	{
		admindao.unblockCustomer(accountNumber);
	}
	
	//Get Transactions of a customer
	public ResultSet getTransactionOfCustomer(int accountNumber)
	{
		return admindao.getTransactionByAccountNumber(accountNumber);
	}
	
	//Get Transactions between two dates
	public ResultSet getTransactioninDate(String date1,String date2)
	{
		return admindao.getTransactionByDate(date1,date2);
	}
}
