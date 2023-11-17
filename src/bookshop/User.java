package bookshop;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public abstract class User {
	
	enum Role{
		admin("admin"), customer("customer");
		
		private String type;
		
		Role(String type) {
			this.setType(type);
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}
	
	private int userID;
	private String username;
	private String surname;
	private int houseNo;
	private String postcode;
	private String city;
	private Role role;
	
	public User (int userID, String username, String surname, int houseNo, String postcode, String city, Role role) {
		if (userID < 0) {
			throw new IllegalArgumentException("ID cannot be negative");
		}
		this.userID = userID;
		this.username = username;
		this.surname = surname;
		this.houseNo = houseNo;
		this.postcode = postcode;
		this.city = city;
		this.role = role;
	}

	public int getUserID() {
		return userID;
	}

	public String getUsername() {
		return username;
	}

	public String getSurname() {
		return surname;
	}

	public int getHouseNo() {
		return houseNo;
	}

	public String getPostcode() {
		return postcode;
	}

	public String getCity() {
		return city;
	}

	public Role getRole() {
		return role;
	}
	
	public String toString() {
		String userString = ("ID: " + getUserID() + ", Username: " + getUsername() 
		+ ", Surname: " + getSurname());
		return userString;
	}
	
	public String getAddress() {
		return getHouseNo() +", "+ getPostcode() + ", " + getCity();
	}
	
	public abstract String getFormattedAttributesNoNL();
	
	public static ArrayList<User> getUsers() throws FileNotFoundException{
	    ArrayList<User> userList = new ArrayList<User>();
	    File inputFile = new File("UserAccounts.txt");
	    Scanner fileScanner = new Scanner(inputFile);
	    while (fileScanner.hasNextLine()) {
	        String line = fileScanner.nextLine();
	        String[] arrOfStr = line.split(",");
	        if (arrOfStr[7].trim().equals("admin")) { 
	            Role role = Role.valueOf(arrOfStr[7].trim());
	            userList.add(new Admin(Integer.parseInt(arrOfStr[0].trim()), arrOfStr[1].trim(), arrOfStr[2].trim(), Integer.parseInt(arrOfStr[3].trim()), arrOfStr[4].trim(), arrOfStr[5].trim(), role));
	        }
	        else if (arrOfStr[7].trim().equals("customer")) { 
	            Role role = Role.valueOf(arrOfStr[7].trim());
	            userList.add(new Customer(Integer.parseInt(arrOfStr[0].trim()), arrOfStr[1].trim(), arrOfStr[2].trim(), Integer.parseInt(arrOfStr[3].trim()), arrOfStr[4].trim(), arrOfStr[5].trim(), Double.parseDouble(arrOfStr[6].trim()) , role));
	        }
	    }
	    fileScanner.close();
	    return userList;
	} 

}
