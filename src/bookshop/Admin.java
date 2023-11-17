package bookshop;

public class Admin extends User {
	
	public Admin(int userID, String username, String surname, int houseNo, String postcode, String city, Role role) {
		super(userID, username, surname, houseNo, postcode, city, role);
	}
	
	
	@Override
    public String getFormattedAttributesNoNL() {
        return getUserID() + ", " + getUsername() + ", " + getSurname() + ", " + getAddress() + ", , " + getRole();
             
    }
	
}
