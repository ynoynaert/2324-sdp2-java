package domain;

public class User {
	
	private static User uniqueInstance = new User();
	
	private Account a;
	
	private User() {
	}
	
	public static User getInstance() {
		if(uniqueInstance == null) {
			uniqueInstance = new User();
		}
		return uniqueInstance;
	}
	
	public void setAccountInfo(Account a) {
		this.a = a;
	}
	
	public String getName() {
		return a.getFirstname() + " " + a.getLastname();
	}
	
	public Account getAccount() {
		return a;
	}
	
	public boolean isAdmin() {
		if(a instanceof Admin)
			return true;
		else return false;
	}
	
	public String getAddress() {
		return (((Supplier) a).getBusiness().getStreet()) + " " + (((Supplier) a).getBusiness().getStreetNr()) + "\n" + (((Supplier) a).getBusiness().getZipcode()) + " " + (((Supplier) a).getBusiness().getCountry());
	}
	
	public void logout() {
		uniqueInstance = null;
	}
}
