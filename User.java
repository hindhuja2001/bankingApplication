package bankinApp;

public class User {
	private String name;
	private String address;
	private String password;
	User(String name,String address,String password)
	{
		this.name=name;
		this.address=address;
		this.password=password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
