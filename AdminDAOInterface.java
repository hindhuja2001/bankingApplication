package bankinApp;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface AdminDAOInterface {
	public ResultSet getAllCustomer() throws SQLException;
	public ResultSet getAllTransaction() throws SQLException;
	public void blockCustomer(int accountNumber) throws Exception;
	public void unblockCustomer(int accountNumber) throws Exception;
	public ResultSet getTransactionByAccountNumber(int accountNumber);
	public ResultSet getClosedAccountDetails() throws Exception;
	public ResultSet getBlockedCustomer();
	public ResultSet getRequestsToUnblock();
	public ResultSet getTransactionByDate(String date1, String date2);
	public Admin getAdminObject(String adminId, String password) throws SQLException, CustomException;
	
	

}
