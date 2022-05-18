package bankinApp;

import java.sql.*;

public class BeneficiaryDAO implements BeneficiaryDAOInterface{
	static Connection con;
	static
	{
		//con=new JDBC().connection();
		con=ConnectDatabase.getConnection();
	}
	public void addBeneficiaryDao(int accountNumber,int benId) throws Exception
	{
		if(checkBeneficiary(benId))
		{
			String query="insert into beneficiary values(?,?)";
			PreparedStatement st=con.prepareStatement(query);
			st.setInt(1,accountNumber);
			st.setInt(2,benId);
			int count=st.executeUpdate();
		}
		else
		{
			throw new CustomException("Beneficiary Id doesn't exist");
		}
	}
	
	// To check the account number exists and make sure the account number is not blocked or closed
	public boolean checkBeneficiary(int id) throws Exception
	{
		String query="select count(*) from customer where account_number="+id;  //whether the account number exists
		ResultSet rs=new JDBC().queryResult(query);
		rs.next();
		if(rs.getInt(1)==0)
		{
			return false;
		}
		if(CustomerDAO.verifyClosedAccount(id) || CustomerDAO.verifyBlockedCustomer(id))  //whether the account is closed or blocked
		{
			return false;
		}
		return true;
		
	}
	
	
	public void removeBeneficiaryDao(int accountNumber,int benId) throws SQLException
	{
		String query="delete from beneficiary where accountNumber="+accountNumber+" and beneficiaryId="+benId;
		Statement st=con.createStatement();
		st.executeUpdate(query);
	}
	
	
	public ResultSet viewBeneficiaries(int accountNumber) throws SQLException
	{
		String query="select beneficiary.beneficiaryId,customer.name,customer.phoneNumber from beneficiary inner join customer on beneficiary.beneficiaryId=customer.account_number where beneficiary.accountNumber="+accountNumber+" group by beneficiaryId";
		ResultSet rs=new JDBC().queryResult(query);
		return rs;
	}
	
	//To check the whether the account number holds benID as beneficiary
	public boolean checkValidBen(int accountNumber,int benID) throws SQLException, CustomException
	{
		String query="select beneficiaryId from beneficiary where accountNumber="+accountNumber;
		ResultSet rs=new JDBC().queryResult(query);
		while(rs.next())
		{
			if(rs.getInt(1)==benID)
			{
				return true;
			}
		}
		return false;
		
		
	}
	
	
	public void transferAmountToBen(int accountNumber,int benID,int amount) throws Exception
	{
		if(!checkBeneficiary(benID))
		{
			throw new CustomException("Invalid Beneficiary Id");
		}
		if(CustomerDAO.verifyClosedAccount(benID) || CustomerDAO.verifyBlockedCustomer(benID))
		{
			throw new CustomException("Your beneficiary account has been closed or blocked");
		}
		if(checkValidBen(accountNumber,benID))
		{
			String queryForUser="select balance from customer where account_number="+accountNumber;
			String queryForBenId="select balance from customer where account_number="+benID;
			ResultSet rs1=new JDBC().queryResult(queryForUser);
			ResultSet rs2=new JDBC().queryResult(queryForBenId);
			rs1.next();
			rs2.next();
			int userBalance=rs1.getInt(1)-amount;
			int benBalance=rs2.getInt(1)+amount;
			String query="update customer set balance="+userBalance+" where account_number="+accountNumber;
			String query1="update customer set balance="+benBalance+" where account_number="+benID;
			TransactionDAO tdao=new TransactionDAO();
			int transactionID=tdao.getLastID()+1;
			String queryForTransactionEntry="insert into transaction(transactionID,senderID,receipientID,amount,type) values("+transactionID+","+accountNumber+","+benID+","+amount+",'transfer')";
			Statement st=con.createStatement();
			st.executeUpdate(query);
			st.executeUpdate(query1);
			st.executeUpdate(queryForTransactionEntry);
		}
		else
		{
			throw new CustomException("Invalid Beneficiary Id");
		}
	}

}
