package bankinApp;

import java.sql.ResultSet;

public interface TransactionDAOInterface {
	public void addTransaction(int transactId,int senderId,int receipientId,int amount,String type) throws Exception;
	public ResultSet getTransactionsForId(int id) throws Exception;
	

}
