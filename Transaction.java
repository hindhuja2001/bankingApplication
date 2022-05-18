package bankinApp;

public class Transaction {
	private int transactionID;
	private int senderID;
	private int receipientID;
	private int amount;
	private String type;
	static int lastID;
	
	//In order to fetch the last transaction id from the transaction table each time the program runs to assign to the static counter variable.
	static
	{
		try {
			
			TransactionDAO temporarydao=new TransactionDAO();
			lastID=temporarydao.getLastID();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	static int counter=lastID;
	Transaction(int senderID,int amount,String type)
	{
		this.senderID=senderID;
		this.receipientID=0;
		this.amount=amount;
		this.type=type;
		this.transactionID=counter+1;
		counter+=1;
	}
	Transaction(int senderID,int receipientID,int amount,String type)
	{
		this.senderID=senderID;
		this.receipientID=receipientID;
		this.amount=amount;
		this.type=type;
		this.transactionID=counter+1;
		counter+=1;
	}
	public int getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	public int getSenderID() {
		return senderID;
	}
	public void setSenderID(int senderID) {
		this.senderID = senderID;
	}
	public int getReceipientID() {
		return receipientID;
	}
	public void setReceipientID(int receipientID) {
		this.receipientID = receipientID;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

}
