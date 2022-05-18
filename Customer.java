package bankinApp;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer extends User implements CustomerFunctions {
	private int accountNumber;
	private String username;
	private int balance;
	private String transactionPassword;
	private String phoneNumber;
	static int lastID;
	static CustomerDAOInterface cdao;
	static BeneficiaryDAOInterface bdao;
	static TransactionDAOInterface tdao;
	
	//In order to fetch the last customer id from the customer table each time the program runs to assign to the static counter variable.
	static
	{
		cdao=new CustomerDAO();
		bdao=new BeneficiaryDAO();
		tdao=new TransactionDAO();
		
		try {
			
			CustomerDAO temporarydao=new CustomerDAO();
			lastID=temporarydao.getLastID();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	static int counter=lastID;
	
	//New Entry
	Customer(String name,String address,String username,String password,String transactionPassword,int benId,int amount,String phoneNumber) throws Exception
	{
		super(name,address,password);
		this.username=username;
		this.transactionPassword=transactionPassword;
		this.phoneNumber=phoneNumber;
		this.accountNumber=counter+1;
		counter+=1;
		this.deposit(amount);
		this.balance=amount;
		cdao.addCustomer(this.accountNumber,this.username,this.getPassword(),this.getName(),this.getAddress(),this.balance,this.transactionPassword,benId,this.phoneNumber);
		
	}
	
	//To create a customer object using username and password
	Customer(int accountNumber,String username,String password,String name,String address,int balance,String transactionPassword,String phoneNumber)
	{
		super(name,address,password);
		this.accountNumber=accountNumber;
		this.username=username;
		this.balance=balance;
		this.transactionPassword=transactionPassword;
		this.phoneNumber=phoneNumber;
	}
	
	//To get the customer object using username and password during login
	public static Customer getObject(String username,String password) throws SQLException, CustomException
	{
		Customer customer=cdao.getCustomerObject(username, password);
		return customer;
		
	}
	
	public static boolean verifyTransactPassword(Customer customer,String transactPwd)
	{
		if(customer.transactionPassword.equals(transactPwd))
		{
			return true;
		}
		return false;
	}
	
	public void deposit(int amount) throws CustomException
	{
		if(amount<=0)
		{
			throw new CustomException("Invalid amount");
		}
		this.balance+=amount;
		try {
			cdao.updateValue("balance",this.balance,this.accountNumber);
			Transaction transaction=new Transaction(this.accountNumber,amount,"deposit");
			tdao.addTransaction(transaction.getTransactionID(),transaction.getSenderID(),transaction.getReceipientID(),amount,"deposit");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void  withdrawal(int amount) throws Exception 
	{
			
			if(this.balance<amount)
			{
				throw new CustomException("Insufficient Balance");
				
			}
			if(amount<=0)
			{
				throw new CustomException("Invalid amount");
			}
			else 
			{
				this.balance-=amount;
				cdao.updateValue("balance", this.balance, this.accountNumber);
				Transaction transaction=new Transaction(this.accountNumber,amount,"withdrawal");
				tdao.addTransaction(transaction.getTransactionID(),transaction.getSenderID(),transaction.getReceipientID(),transaction.getAmount(),"withdrawal");
				System.out.println("Amount successfully withdrawn");
			}
		
	}
	
	public  void requestUnblock(int accountNumber) throws SQLException, CustomException
	{
		cdao.requestToUnblock(accountNumber);
	}
	public static void requestUnblock(String username) throws SQLException, CustomException
	{
		cdao.requestToUnblock(username);
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getTransactionPassword() {
		return transactionPassword;
	}
	public void setTransactionPassword(String transactionPassword) {
		this.transactionPassword = transactionPassword;
	}
	
	@Override
	public int viewBalance() {
		
		return cdao.viewAccountBalance(this.accountNumber);
	}
	@Override
	public ResultSet viewTransactions() {
		try {
			System.out.println("Account number:"+this.accountNumber);
			return tdao.getTransactionsForId(this.accountNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
		
	}
	@Override
	public void addBeneficiary(int id) throws Exception {
		bdao.addBeneficiaryDao(this.accountNumber,id);

		
		
	}
	@Override
	public void removeBeneficiary(int id) {
		try {
			bdao.removeBeneficiaryDao(this.accountNumber, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void transferAmountToBen(int benID,String transactPwd,int amount) throws Exception
	{
		if(Customer.verifyTransactPassword(this, transactPwd))
		{
			if(amount<=0)
			{
				throw new CustomException("Invalid amount");
			}
			if(this.balance<amount)
			{
				throw new CustomException("Insufficient Balance");
				
			}
			else 
			{
				bdao.transferAmountToBen(this.accountNumber,benID,amount);
				
			}
		}
		else
		{
			throw new CustomException("Wrong Transaction Password");
			
		}
	}
		
		
		
	@Override
	public ResultSet viewBeneficiary() throws SQLException {
		return bdao.viewBeneficiaries(this.accountNumber);
		
	}
	
	@Override
	public void closeAccount() throws Exception {
		cdao.removeUser(this.accountNumber);
		
	}
	
}
