package bankinApp;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BeneficiaryDAOInterface {
	public void addBeneficiaryDao(int accountNumber,int benId) throws Exception;
	public boolean checkBeneficiary(int id) throws Exception;
	public void removeBeneficiaryDao(int accountNumber,int benId) throws SQLException;
	public ResultSet viewBeneficiaries(int accountNumber) throws SQLException;
	public boolean checkValidBen(int accountNumber,int benID) throws SQLException, CustomException;
	public void transferAmountToBen(int accountNumber,int benID,int amount) throws Exception;
	

}
