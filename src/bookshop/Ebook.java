package bookshop;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ebook extends Book {

	enum eFormat{
		EPUB("epub"), MOBI("mobi"), PDF("pdf");
		
		private String type;
		
		private eFormat(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}
	
	private int numPages;
	private eFormat format;
	
	public Ebook(int barcode, String title, Language language, Genre genre, LocalDate rdate, int stockNum, double price, int numPages, eFormat format) {
		super(barcode, title, language, genre, rdate, stockNum, price);
		this.numPages = numPages;
		this.format = format;
	}


	public int getNumPages() {
		return numPages;
	}
	public eFormat getFormat() {
		return format;
	}
	
	public static String[] getAllEformats() {
        eFormat[] eformats = eFormat.values();
        String[] eformatStrings = new String[eformats.length];

        for (int i = 0; i < eformats.length; i++) {
        	eformatStrings[i] = eformats[i].getType();
        }

        return eformatStrings;
    }
	
	@Override
	public String toString() {
	    String bookString = super.toString() + " | Num. of Pages: " + getNumPages() + " | Format: " + getFormat();
	    return bookString;
	}
	
	@Override
    public String getType() {
        return "ebook";
    }
	@Override
    public String getAdditionalAttributes() {
        return numPages + ", " + format;
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
