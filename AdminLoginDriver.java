package bankinApp;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
public class AdminLoginDriver {
	public void displayAdminFunctions()
	{
		System.out.println("1.View Customers \n2.View Transactions \n3.View Blocked Customers \n4.Block Customer \n5.Unblock Customer \n6.View Closed Accounts  \n7.View Requests of Unblocking accounts \n8.Exit");
		System.out.println("Select your choice:");
	}
	public  void loginSession(Admin admin) throws Exception
	{
		Scanner scanner=new Scanner(System.in);
		int choice=0;
		while(true)
		{
			displayAdminFunctions();
			
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
				System.out.println("Customers:");
				DataProcessingClass.printResultSet(admin.getCustomer());
				break;
			}
			case 2:
			{
				System.out.println("Transactions:");
				System.out.println("Choose an option\n1.View all Transctions\n2.View Transactions of a customer\n3.View Transactions by Date");
				int choice1 = 0;
				int flag;
				while(true)
				{
					flag=1;
					try
					{
						choice1=scanner.nextInt();
					}catch(InputMismatchException e)
					{
						flag=0;
						System.out.println("Enter valid input:");
						scanner.nextLine();
						continue;
					}
					switch(choice1)
					{
					case 1:
					{
						DataProcessingClass.printResultSet(admin.getTransactions());
						break;
					}
					case 2:
					{
						System.out.println("Account Number:");
						int id=scanner.nextInt();
						DataProcessingClass.printResultSet(admin.getTransactionOfCustomer(id));
						break;
					}
					case 3:
					{
						System.out.println("From date");
						System.out.println("Year:");
						int year1=scanner.nextInt();
						System.out.println("Month:");
						int month1=scanner.nextInt();
						System.out.println("Date:");
						int date1=scanner.nextInt();
						System.out.println("To date");
						System.out.println("Year:");
						int year2=scanner.nextInt();
						System.out.println("Month:");
						int month2=scanner.nextInt();
						System.out.println("Date:");
						int date2=scanner.nextInt()+1;
						DataProcessingClass.printResultSet(admin.getTransactioninDate(year1+"-"+month1+"-"+date1, year2+"-"+month2+"-"+date2));
						break;
					}
					
					
					}
					if(flag==1) break;
				}
				break;
			}
			case 3:
			{
				System.out.println("Blocked Customers:");
				DataProcessingClass.printResultSet(admin.getBlockedCustomer());
				break;
			}
			case 4:
			{
				System.out.print("Account Number of the customer:");
				int id=scanner.nextInt();
				try
				{
					admin.blockCustomer(id);
				}catch(CustomException e)
				{
					System.out.println("Account number doesn't exists");
					continue;
				}
				System.out.println("Account number:"+id+" blocked");
				break;
				
			}
			case 5:
			{
				System.out.print("Account Number of customer:");
				int id=scanner.nextInt();
				admin.unblockCustomer(id);
				System.out.println("Account Number:"+id+" unblocked");
				break;
			}
			case 6:
			{
				System.out.println("Closed Account Details");
				DataProcessingClass.printResultSet(admin.getClosedAccounts());
				break;
				
			}
			
			case 7:
			{
				DataProcessingClass.printResultSet(admin.getUnblockRequests());
				break;
			}
			case 8:
			{
				System.out.println("Thank you");
				System.exit(0);
			}
			default:
			{
				System.out.println("Enter a valid choice from 1 to 9");
			}
			}
		}
		
	}

}
