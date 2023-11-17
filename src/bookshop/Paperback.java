package bookshop;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Paperback extends Book {

	enum Condition{
		NEW("new"), USED("used");
		private String type;
		
		private Condition(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
		
	}
	
	private int numPages;
	private Condition condition;
	
	public Paperback(int barcode, String title, Language language, Genre genre, LocalDate
			rdate, int stockNum,
			double price, int numPages, Condition condition) {
		super(barcode, title, language, genre, rdate, stockNum, price);
		this.numPages = numPages;
		this.condition = condition;
	}

	public int getNumPages() {
		return numPages;
	}
	
	public Condition getCondition() {
        return condition;
    }
	
	public static String[] getAllConditions() {
        Condition[] conditions = Condition.values();
        String[] conditionStrings = new String[conditions.length];

        for (int i = 0; i < conditions.length; i++) {
        	conditionStrings[i] = conditions[i].getType();
        }

        return conditionStrings;
    }
	
	@Override
	public String toString() {
	    String bookString = super.toString() + " | Num. of pages: " + getNumPages() + " | Condition: " + getCondition();
	    return bookString;
	}
	
	@Override
    public String getType() {
        return "paperback";
    }
	@Override
    public String getAdditionalAttributes() {
        return numPages + ", " + condition.getType();
    }

    @Override
    public String getFormattedAttributes() {
        return "\n" + getBarcode() + ", " + getType() + ", " + getTitle() + ", " + getLanguage().getType() + ", " + getGenre().getType().replace("_", " ") 
               + ", " + getRdate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ", " + getStockNum() + ", " + getPrice() + ", " + getAdditionalAttributes();
    }
    @Override
    public String getFormattedAttributesNoNL() {
        return getBarcode() + ", " + getType() + ", " + getTitle() + ", " + getLanguage().getType() + ", " + getGenre().getType().replace("_", " ") 
               + ", " + getRdate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ", " + getStockNum() + ", " + getPrice() + ", " + getAdditionalAttributes();
    }
}
