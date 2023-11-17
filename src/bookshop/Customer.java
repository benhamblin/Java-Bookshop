package bookshop;

public class Customer extends User{

	private double creditBalance;
	
	public Customer(int userID, String username, String surname, int houseNo, String postcode, String city, double creditBalance, Role role) {
		super(userID, username, surname, houseNo, postcode, city, role);
		this.creditBalance = creditBalance;
	}

	public double getCreditBalance() {
		return creditBalance;
	}

	public void updateCreditBalance(double creditBalance) {
		this.creditBalance = creditBalance;
	}
	
	
	@Override
    public String getFormattedAttributesNoNL() {
        return getUserID() + ", " + getUsername() + ", " + getSurname() + ", " + getAddress() + ", " + getCreditBalance() + ", " + getRole();
             
    }

}
