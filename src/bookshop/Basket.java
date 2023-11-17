package bookshop;

import java.util.ArrayList;
import java.util.List;

public class Basket {
	
    private static ArrayList<Book> books;

    public Basket() {
        this.books = new ArrayList<>();
    }

    public static List<Book> getBooks() {
		return books;
	}

	public void addBook(Book book) {
        this.books.add(book);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
    }

    public static double getTotalCost() {
        double total = 0.0;
        for (Book book : books) {
            total += book.getPrice();
        }
        return total;
    }
    public static int getQuantity(Book book) {
        int count = 0;
        for (Book b : books) {
            if (b.equals(book)) {
                count++;
            }
        }
        return count;
    }
}

