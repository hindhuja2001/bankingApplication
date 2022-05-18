package bankinApp;

import java.sql.SQLException;

public interface CustomerDAOInterface {
	
	public void addCustomer(int accountNumber,String username,String password,String name,String address,int balance,String transactionPwd,int benId,String phoneNumber) throws Exception;
	public void updateValue(String attribute,int value,int id) throws Exception;
	public void updateValue(String attribute,String value,int id) throws Exception;
	public int viewAccountBalance(int id);
	public Customer getCustomerObject(String username,String pwd) throws SQLException, CustomException;
	public void removeUser(int accountNumber) throws SQLException, Exception;
	public void requestToUnblock(int accountNumber) throws SQLException, CustomException;
	public void requestToUnblock(String username) throws SQLException, CustomException;

}
