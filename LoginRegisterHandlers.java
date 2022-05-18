package bankinApp;

import java.sql.SQLException;
import java.util.Scanner;

public class LoginRegisterHandlers {
	public  void customerLogin() throws Exception 
	{
		BankMain bankMain=new BankMain();
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter username:");
		String username=scanner.next();
		System.out.println("Enter password:");
		String password=scanner.next();
		Customer customerObj = null;
		try {
			customerObj = Customer.getObject(username, password);
		} catch (CustomException e) {
			if(e.getMessage().equals("Username and password mismatch"))
			{
				System.out.println("Username and Password Mismatch");
			}
			if(e.getMessage().equals("Your account is blocked"))
			{
				System.out.println("You account is currently blocked");
				System.out.println("Press 1 to Request your account to be unblocked or press any other number to exit:");
				int choice1=scanner.nextInt();
				if(choice1==1)
				{
					Customer.requestUnblock(username);
					System.out.println("Your request has been submitted");
					System.out.println("Press any key to exit");
					scanner.next();
					System.out.println("Thank you");
					System.exit(0);
				}
				else
				{
					System.out.println("Thank you");
					System.exit(0);
				}
				
			}
			if(e.getMessage().equals("Your account no longer exists"))
			{
				System.out.println("Your account has been closed");
			}
			bankMain.CustomerPage();
		}
		CustomerLoginDriver customerLoginDriver = new CustomerLoginDriver();
		customerLoginDriver.loginSession(customerObj);
	}
	public  void customerRegister() throws Exception
	{
		Scanner scanner=new Scanner(System.in);
		System.out.println("Name:");
		String name=scanner.next();
		System.out.println("Address:");
		String address=scanner.next();
		System.out.println("Set Username:");
		String username=scanner.next();
		System.out.println("Set Password:");
		String pwd=scanner.next();
		System.out.println("Set Transaction Password:");
		String transactionPwd=scanner.next();
		System.out.println("Enter your phone number");
		String phoneNumber=scanner.next();
		int flag1;
		while(true)
		{
			flag1=1;
			if(!(phoneNumber.matches("(0/91)?[6-9][0-9]{9}")))
			{
				flag1=0;
				System.out.println("Enter the valid number:");
				scanner.nextLine();
				phoneNumber=scanner.next();
			}
			
			if(flag1==1) break;
		}
			
		System.out.println("Set an Beneficiary using their account Number:");
		int benId=scanner.nextInt();
		System.out.println("You have to deposit amount for creating an account");
		int amount=scanner.nextInt();
		int flag=1;
		while(true)
		{
			try {
				flag=1;
				Customer customer=new Customer(name,address,username,pwd,transactionPwd,benId,amount,phoneNumber);
			}
			catch(CustomException e)
			{
				if(e.getMessage().equals("Beneficiary Id doesn't exist"))
				{
					System.out.println("Account number doesn't exist");
					System.out.println("Enter a valid account number:");
					benId=scanner.nextInt();
					flag=0;
					continue;
				}
				if(e.getMessage().equals("Password already exists"))
				{
					System.out.println("Choose another password");
					pwd=scanner.next();
					flag=0;
					continue;
				}
				if(e.getMessage().equals("Invalid amount"))
				{
					System.out.println("Enter valid amount:");
					amount=scanner.nextInt();
					flag=0;
					continue;
				}
			}
			if(flag==1)
			{
				break;
			}
		}
		System.out.println("Account Created Successfully");
		System.out.println("Login with your username and password");
	}
	public static void adminLogin() throws Exception
	{
		BankMain bankMain=new BankMain();
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter your Id:");
		String adminId=scanner.next();
		System.out.println("Enter the password:");
		String password=scanner.next();
		Admin adminObj=null;
		
		try {
			adminObj=Admin.getObject(adminId, password);
		} catch (CustomException e) {
			if(e.getMessage().equals("Username and password mismatch"))
			{
				System.out.println("Username and Password Mismatch");
			}
			bankMain.adminPage();
		}
		AdminLoginDriver adminLoginDriver = new AdminLoginDriver();
		adminLoginDriver.loginSession(adminObj);
	}
}
