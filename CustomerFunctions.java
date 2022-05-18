package bankinApp;

import java.sql.ResultSet;

public interface CustomerFunctions {
	public void deposit(int amount) throws Exception;
	public void withdrawal(int amount) throws Exception;
	public int viewBalance();
	public ResultSet viewTransactions();
	public void addBeneficiary(int id) throws Exception;
	public void removeBeneficiary(int id);
	public void transferAmountToBen(int benID,String transactPwd,int amount) throws Exception;
	public ResultSet viewBeneficiary() throws Exception;
	public void closeAccount() throws Exception;

}
