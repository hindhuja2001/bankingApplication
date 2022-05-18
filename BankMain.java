package bankinApp;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankMain {
	
	
	public void CustomerPage() throws Exception
	{
		LoginRegisterHandlers loginRegister=new LoginRegisterHandlers();
		
		Scanner scanner=new Scanner(System.in);
		int choice=0;
		while(true)
		{
			System.out.println("Please pick an option:\n1.Login\n2.Register\n3.Exit");
			System.out.println("Select your choice:");
			try {
				choice=scanner.nextInt();
			}catch(InputMismatchException e)      //Handling InputMismatchException when string is given as input
			{
				System.out.println("Enter the valid input 1,2,3");
				scanner.nextLine();
				continue;
			}
			switch(choice)
			{
			case 1:
			{
				loginRegister.customerLogin();
				break;
			
			}
			case 2:
			{
				loginRegister.customerRegister();
				LoginRegisterHandlers loginRegisterHandlers = new LoginRegisterHandlers();
				loginRegisterHandlers.customerLogin();
				break;
			
			}
			case 3:
			{
				System.out.println("Thank you");
				System.exit(0);
			}
			default:
			{
				System.out.println("Pick a number from 1 to 3");
			}
		}
		}
		
	}
	public void adminPage() throws Exception
	{
		LoginRegisterHandlers.adminLogin();
	}
	public static void main(String[] args) throws Exception {
		BankMain mainObj=new BankMain();
		Scanner scanner=new Scanner(System.in);
		System.out.println("1.Customer 2.Admin");
		System.out.println("Enter a choice:");
		int choice=0;
		int flag=1;
		
		//Handling InputMismatchException if string is given as input
		for(int i=1;i<=5;i+=1) {
			flag=1;
	         try {
	            
	        	 System.out.println("Pick 1 or 2:");
	            choice = scanner.nextInt();

	         }

	         catch (InputMismatchException e) {
	        	 if(i==5)
	        	 {
	        		 System.out.println("Try again after sometime");
	        		 System.exit(0);
	        	 }
	        	 flag=0;
	             scanner.nextLine();
	         }
	         if(flag==1) break;
	         
	     } 
		
		
		while(true)
		{
			switch(choice)
			{
			case 1:
			{
				mainObj.CustomerPage();
				break;
			}
			case 2:
			{
				mainObj.adminPage();
				break;
			}
			default:
			{
				System.out.println("Enter a valid choice:");
				choice=scanner.nextInt();
			}
			}
		}
		
		
		
		

	}

}
