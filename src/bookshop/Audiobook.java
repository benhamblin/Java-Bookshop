package bookshop;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Audiobook extends Book {
    
    enum Format {
        MP3("mp3"), WMA("wma"), AAC("aac");
        
        private String type;
        
        private Format(String type) {
            this.type = type;
        }
        
        public String getType() {
            return type;
        }
    }
    
    private double length; // in hours
    private Format format;
    
    public Audiobook(int barcode, String title, Language language, Genre genre, 
    		LocalDate rdate, int stockNum, double price, double length, Format format) {
        super(barcode, title, language, genre, rdate, stockNum, price);
        this.length = length;
        this.format = format;
    }
    
    public double getLength() {
        return length;
    }
    
    public Format getFormat() {
        return format;
    }
    
    public static String[] getAllFormats() {
        Format[] Formats = Format.values();
        String[] FormatStrings = new String[Formats.length];

        for (int i = 0; i < Formats.length; i++) {
        	FormatStrings[i] = Formats[i].getType();
        }

        return FormatStrings;
    }
    
    public static ArrayList<Audiobook> getAudiobooks() throws FileNotFoundException{
        ArrayList<Book> bookList = getBooks();
        ArrayList<Audiobook> audiobookList = new ArrayList<>();
        for (Book book : bookList) {
            if (book instanceof Audiobook) {
                audiobookList.add((Audiobook) book);
            }
        }
        return audiobookList;   
    }
    
    @Override
    public String toString() {
        String bookString = super.toString() + " | Length: " + getLength() + " | Format: " + getFormat();
        return bookString;
    }
    
    @Override
    public String getType() {
        return "audiobook";
    }
    
    @Override
    public String getAdditionalAttributes() {
        return length + ", " + format;
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
