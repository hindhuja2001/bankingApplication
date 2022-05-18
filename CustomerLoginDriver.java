package bankinApp;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerLoginDriver {
	public void displayCustomerFunctions()
	{
		System.out.println("1.Deposit \n2.Withdraw \n3.View Balance \n4.View Transactions \n5.Add Beneficiary \n6.Remove Beneficiary \n7.Transfer Amount to Beneficiary \n8.View Beneficiary \n9.Close Account \n10.Exit");
		System.out.println("Select your choice:");
	}
	public  void loginSession(Customer customer) throws Exception
	{
		Scanner scanner=new Scanner(System.in);
		int choice=0;
		while(true)
		{
			displayCustomerFunctions();
			try {
				choice=scanner.nextInt();
			}catch(InputMismatchException e)
			{
				System.out.println("Enter valid input:");  //Handling Invalid Inputs
				scanner.nextLine();
			}
			
			
			switch(choice)
			{
			case 1:
			{
				System.out.println("Enter the amount to deposit:");
				int amount=0;
				amount=scanner.nextInt();
				int flag=1;
				while(true)
				{
					flag=1;
					try {
						customer.deposit(amount);
					}catch(CustomException e)
					{
						if(e.getMessage().equals("Invalid amount"))  //If the amount is less than or equal to 0
						{
							System.out.println("Enter the valid amount:");
							amount=scanner.nextInt();
							flag=0;
							continue;
						}
					}
						if(flag==1) break;
					}
				
				System.out.println("Amount has been deposited");
				break;
			}
			case 2:
			{
				System.out.println("Enter the amount to withdraw:");
				System.out.println("Your account will be blocked if you enter the worng password more than three times");
				int flag1;
				int amount = 0;
				while(true)
				{
					flag1=1;
					try
					{
						amount=scanner.nextInt();
					}catch(InputMismatchException e)
					{
						System.out.println("Enter a valid amount:");
						scanner.nextLine();
						flag1=0;
					}
					if(flag1==1) break;
				}
				
				String pwd=null;
				for(int i=1;i<=3;i+=1)   //To block the customer if password wrongly entered  3 times
				{
					System.out.println("Enter your transaction Password correctly:");
					pwd=scanner.next();
					if(Customer.verifyTransactPassword(customer,pwd))
					{
						break;
					}
					else if(i==3)
					{
						System.out.println("Your account has been blocked");
						Admin admin = new Admin(0, null, null);
						admin.blockCustomer(customer.getAccountNumber());
						System.out.println("Please choose a choice:\n1.Request To Unblock the Account\n Or any number to exit");
						while(true)
						{
							
							int choice1=scanner.nextInt();
							if(choice1==1)
							{
								
								customer.requestUnblock(customer.getAccountNumber());
								System.out.println("Your request has been submitted");
								System.out.println("Click any number to exit:");
								scanner.nextInt();
								System.out.println("Thank you");
								System.exit(0);
								
							}
							if(choice==2)
							{
								System.out.println("Thank you");
								System.exit(0);
							}
						}
						
					
					}
				}
				
				int flag=1;
				while(true)
				{
					flag=1;
					try {
				customer.withdrawal(amount);
				}catch(CustomException e)
				{
					System.out.println("Invalid amount"); //If the amount is less than or equal to 0 or less than the account balance
					System.out.println("Your balance is Rs."+customer.viewBalance());
					System.out.println("Enter the amount:");
					amount=scanner.nextInt();
					flag=0;
					continue;
				}
					if(flag==1) break;
				}
				break;
			}
			case 3:
			{
				System.out.println("Your account Balance:"+customer.viewBalance());
				break;
			}
			case 4:
			{
				System.out.println("Your Transactions:");
				DataProcessingClass.printResultSet(customer.viewTransactions());
				break;
			}
			case 5:
			{
				
				System.out.println("Enter the beneficiary Id:");
				int benID=scanner.nextInt();
				int flag=1;
				while(true)
				{
					try {
						flag=1;
						customer.addBeneficiary(benID);
					}
					catch(CustomException e)
					{
						System.out.println("Account number doesn't exist");
						System.out.println("Enter a valid account number:");
						benID=scanner.nextInt();
						flag=0;
						continue;
					}
					if(flag==1) break;
				}
				System.out.println("Beneficiary added successfully");
				break;
			}
			case 6:
			{
				System.out.println("Enter the beneficiary Id:");
				int benID=scanner.nextInt();
				customer.removeBeneficiary(benID);
				System.out.println("Beneficiary removed");
				break;
			}
			case 7:
			{
				System.out.println("Enter the beneficiary Id:");
				int benID=scanner.nextInt();
				System.out.println("Enter the amount:");
				int flag1;
				int amount = 0;
				while(true)
				{
					flag1=1;
					try
					{
						amount=scanner.nextInt();
					}catch(InputMismatchException e)
					{
						System.out.println("Enter a valid amount:");
						scanner.nextLine();
						flag1=0;
					}
					if(flag1==1) break;
				}
				String pwd=null;
				for(int i=1;i<=3;i+=1)
				{
					System.out.println("Enter your transaction Password:");
					pwd=scanner.next();
					if(Customer.verifyTransactPassword(customer,pwd))
					{
						break;
					}
					else if(i==3)
					{
						System.out.println("Your account has been blocked");
						Admin admin2 = new Admin(0, null, null);
						admin2.blockCustomer(customer.getAccountNumber());
						System.out.println("Please choose a choice:\n1.Request To Unblock the Account\n Or any number to exit");
						while(true)
						{
							
							int choice1=scanner.nextInt();
							if(choice1==1)
							{
								try{
									customer.requestUnblock(customer.getAccountNumber());
								}
								catch(CustomException e)
								{
									if(e.getMessage().equals("Request submitted already"))
									{
										System.out.println("You have already submitted the request. Please wait until we process your request. ");
										System.out.println("Click any number to exit:");
										scanner.nextInt();
										System.out.println("Thank you");
										System.exit(0);
									}
								}
								System.out.println("Your request has been submitted");
								System.out.println("Click any number to exit:");
								scanner.nextInt();
								System.out.println("Thank you");
								System.exit(0);
								
							}
							if(choice==2)
							{
								System.out.println("Thank you");
								System.exit(0);
							}
						}
						
					}
				}
				
				
				int flag=1;
				while(true)
				{
					flag=1;
					try {
						customer.transferAmountToBen(benID,pwd, amount);
				}catch(CustomException e)
				{
					if(e.getMessage().equals("Insufficient Balance"))
					{
						System.out.println("Your account balance is insufficent");
						System.out.println("You have Rs."+customer.viewBalance()+" in your account.");
						System.out.println("Enter valid amount:");
						amount=scanner.nextInt();
						
					}
					else if(e.getMessage().equals("Invalid Beneficiary Id"))
					{
						System.out.println("Invalid Beneficiary");
						System.out.println("Enter the correct beneficiary ID:");
						benID=scanner.nextInt();
					}
					else if(e.getMessage().equals("Your beneficiary account has been closed or blocked"))
					{
						System.out.println("Your beneficiary account has been closed or blocked");
						flag=0;
						break;
						
					}
					else if(e.getMessage().equals("Invalid amount")) //if amount is negative or 0
					{
						System.out.println("Enter the valid amount:");
						amount=scanner.nextInt();
					}
					flag=0;
					continue;
				}
					if(flag==1) break;
				}
				if(flag==1) System.out.println("Amount transferred successfully");
				break;
			}
			case 8:
			{
				System.out.println("Your Beneficiaies are:");
				DataProcessingClass.printResultSet(customer.viewBeneficiary());
				break;
				
				
			}
			case 9:
			{
				System.out.println("Your account has been removed");
				customer.closeAccount();
				System.out.println("Enter any key to exit:");
				scanner.next();
			}
			case 10:
			{
				System.out.println("Thank you!");
				System.exit(0);
			}
			default:
			{
				System.out.println("Enter a choice from 1 to 10");
			}
			}
			
		}
		
	}

}
