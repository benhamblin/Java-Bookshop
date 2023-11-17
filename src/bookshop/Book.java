package bookshop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import bookshop.Audiobook.Format;
import bookshop.Ebook.eFormat;
import bookshop.Paperback.Condition;

public abstract class Book {
	
	enum Language{
		English("English"), French("French");

		private String type;
		
		private Language(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}

	}
	
	enum Genre{
		Politics("Politics"), Business("Business"), Computer_Science("Computer_Science"), Biography("Biography");
		private String type;
		
		private Genre(String type) {
			this.setType(type);
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}
	
	private int barcode;
	private String title;
	private Language language;
	private Genre genre;
	private LocalDate rdate;
	private int stockNum;
	private double price;
	
	public Book (int barcode, String title, Language language, Genre genre, LocalDate rdate, int stockNum, double price) {
		this.barcode = barcode;
		this.title = title;
		this.language = language;
		this.genre = genre;
		this.rdate = rdate;
		this.stockNum = stockNum;
		this.price = price;
	}
	public int getBarcode() {
		return barcode;
	}

	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}
	public String getTitle() {
		return title;
	}

	public Language getLanguage() {
		return language;
	}

	public Genre getGenre() {
		return genre;
	}

	public LocalDate getRdate() {
		return rdate;
	}

	public int getStockNum() {
		return stockNum;
	}

	public double getPrice() {
		return price;
	}
	public String toString() {
		String userString  = "Barcode: " + getBarcode() + " | Title: " + getTitle() + " | Language: " + getLanguage() +
		        " | Genre: " + getGenre() + " | Release Date: " + getRdate() + " | Stock Number: " + getStockNum() +
		        " | Price: " + getPrice();

		return userString;
	}
	
	public static String[] getAllGenres() {
        Genre[] genres = Genre.values();
        String[] genreStrings = new String[genres.length];

        for (int i = 0; i < genres.length; i++) {
            genreStrings[i] = genres[i].getType();
        }

        return genreStrings;
    }
	
	public static ArrayList<Book> getBooks() throws FileNotFoundException{
		ArrayList<Book> bookList = new ArrayList<Book>();
		File inputFile = new File("Stock.txt"); //constructor: public File(String pathname)
		Scanner fileScanner = new Scanner(inputFile);
		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			String[] arrOfStr = line.split(",");
			String genreName = arrOfStr[4].trim().replace(" ", "_");
			Genre genre = Genre.valueOf(genreName);
			Language language = Language.valueOf(arrOfStr[3].trim());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			//need to split line into array so we can make user class to add to array.
			if (arrOfStr[1].trim().equals("paperback")) {
				Condition condition = Condition.valueOf((arrOfStr[9].trim()).toUpperCase());
				bookList.add(new Paperback(Integer.parseInt(arrOfStr[0].trim()), arrOfStr[2].trim(), language, genre, LocalDate.parse(arrOfStr[5].trim(), formatter), Integer.parseInt(arrOfStr[6].trim()), Double.parseDouble(arrOfStr[7].trim()) , Integer.parseInt(arrOfStr[8].trim()), condition));
			}
			
			else if (arrOfStr[1].trim().equals("audiobook")) { 
				Format format = Format.valueOf(arrOfStr[9].trim());
				bookList.add(new Audiobook(Integer.parseInt(arrOfStr[0].trim()), arrOfStr[2].trim(), language, genre, LocalDate.parse(arrOfStr[5].trim(), formatter), Integer.parseInt(arrOfStr[6].trim()), Double.parseDouble(arrOfStr[7].trim()) , Double.parseDouble(arrOfStr[8].trim()), format));
			}
			else if (arrOfStr[1].trim().equals("ebook")) { 
				eFormat format = eFormat.valueOf(arrOfStr[9].trim());
				bookList.add(new Ebook(Integer.parseInt(arrOfStr[0].trim()), arrOfStr[2].trim(), language, genre, LocalDate.parse(arrOfStr[5].trim(), formatter), Integer.parseInt(arrOfStr[6].trim()), Double.parseDouble(arrOfStr[7].trim()) , Integer.parseInt(arrOfStr[8].trim()), format));
			}
			}
			fileScanner.close();
		return bookList;	
	}
	
	public static ArrayList<Book> sortBooks(ArrayList<Book> unsortedBooks){
		 Collections.sort(unsortedBooks, (b1, b2) -> b1.getStockNum() - b2.getStockNum());
		return unsortedBooks;
	}
	public static ArrayList<Book> sortBooksPrice(ArrayList<Book> unsortedBooks){
	    Collections.sort(unsortedBooks, (b1, b2) -> Double.compare(b1.getPrice(), b2.getPrice()));
	    return unsortedBooks;
	}
	
	 public abstract String getType();
	 public abstract String getAdditionalAttributes(); //gets extra information for each child (audio/ebook/paper)
	 public abstract String getFormattedAttributes(); //formats book attributes for file writing
	 public abstract String getFormattedAttributesNoNL(); //same as above but no new line for formatting
	
	 public static void addBookToStock(Book book) throws IOException {
		    String newBookLine = book.getFormattedAttributes();
		    FileWriter filewriter = new FileWriter("Stock.txt", true);
		    PrintWriter printw = new PrintWriter(filewriter);
		    printw.print(newBookLine);
		    printw.close();
		}
	 
	}
	
