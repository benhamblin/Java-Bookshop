package bookshop;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class CustomerFrame extends JFrame {

	private Basket basket;
	private ArrayList<Book> bookList;
    private ArrayList<Audiobook> audiobookList;
    private ArrayList<Audiobook> audiobookTable;
    private JPanel contentPane;
    private JTable bookTable;
    private JTable basketTable;
    private DefaultTableModel dtmBook;
    private DefaultTableModel dtmBasket;
    private JTextField txtSearch;
    JPanel basketPanel;
    private boolean filterApplied;
	

	public CustomerFrame(Customer user) throws IOException {
		ArrayList<User> userList = User.getUsers();
		this.bookList = Book.getBooks();
		this.audiobookList = Audiobook.getAudiobooks();
		this.setTitle("CUSTOMER MENU - " + user.getSurname());
		audiobookTable = new ArrayList<Audiobook>();
		filterApplied = false;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 907, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 871, 289);
		contentPane.add(tabbedPane);
		
		JPanel viewBooksPanel = new JPanel();
		tabbedPane.addTab("View Books", null, viewBooksPanel, null);
		viewBooksPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 846, 204);
		viewBooksPanel.add(scrollPane);
		//create the table
		createTable(scrollPane);
		
		JLabel lblSearch = new JLabel("Quick Lookup");
		lblSearch.setBounds(10, 236, 114, 14);
		viewBooksPanel.add(lblSearch);
		
		txtSearch = new JTextField();
		txtSearch.setBounds(134, 233, 150, 20);
		viewBooksPanel.add(txtSearch);
		txtSearch.setColumns(10);
		
		//search for barcode
		JButton btnBarcode = new JButton("Try Barcode");
		btnBarcode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = txtSearch.getText();
				if (value.length() != 8 || AdminFrame.isInteger(value) == false) {
					JOptionPane.showMessageDialog(null, "Invalid Barcode - Must be an 8 Digit Integer");
				}
				else {
					int intvalue = Integer.parseInt(value);
					boolean bookFound = false;
					for (Book book : bookList) {
			            if (book.getBarcode() == intvalue) {
			            	bookFound = true;
			                String[] options = { "Yes, please", "No, thanks"};
			                String message = "Details:" + '\n' + book.toString() + '\n';
			                message += "Would you like to add the book " + book.getTitle() + " to the basket?";
			                int n = JOptionPane.showOptionDialog(null, message, "Book found!", JOptionPane.YES_NO_CANCEL_OPTION,
			                JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			                if (n==0) {
			                	basket.addBook(book);
					            Object[] rowData = new Object[] {book.getBarcode(), book.getTitle(),book.getLanguage(), book.getGenre(), book.getRdate(), book.getStockNum(), book.getPrice()}; 
					            dtmBasket.addRow(rowData);
			                }
			                break;
			            }     
			        }
					if(!bookFound){ 
				        JOptionPane.showMessageDialog(null, "No book found with the given barcode.");
				    }
				}
			}
		});
		btnBarcode.setBounds(294, 232, 110, 23);
		viewBooksPanel.add(btnBarcode);
		
		JSpinner spnAudio = new JSpinner();
		spnAudio.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		spnAudio.setBounds(660, 233, 43, 20);
		viewBooksPanel.add(spnAudio);
		
		JLabel lblAudioHours = new JLabel("Search books with minimum hours");
		lblAudioHours.setBounds(454, 236, 215, 14);
		viewBooksPanel.add(lblAudioHours);
		
		//search for audio books
		JButton btnAudioHours = new JButton("Find Audiobooks");
		btnAudioHours.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filterApplied = true;
				int hours = (int) spnAudio.getValue();
				boolean bookFound = false;
				dtmBook.setRowCount(0);
				for (Audiobook book : audiobookList) {
		            if (book.getLength() >= hours) {
		            	bookFound = true;
		            	audiobookTable.add(book);
		            	Object[] rowData = new Object[] {book.getBarcode(), book.getTitle(),book.getLanguage(), book.getGenre(), book.getRdate()
		    					, book.getStockNum(), book.getPrice()}; 
		    			dtmBook.addRow(rowData);
		       
		        
		            }     
		        }
				if(!bookFound){ 
			        JOptionPane.showMessageDialog(null, "No audiobook found with the given length.");
			    }
			}
		});
		btnAudioHours.setBounds(713, 215, 127, 23);
		viewBooksPanel.add(btnAudioHours);
		
		//reset audio filter
		JButton btnReset = new JButton("Reset filter");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filterApplied = false;
				spnAudio.setValue(0);
				dtmBook.setRowCount(0);
				Book.sortBooksPrice(bookList);
				for (Book book : bookList) {
					Object[] rowData = new Object[] {book.getBarcode(), book.getTitle(),book.getLanguage(), book.getGenre(), book.getRdate()
							, book.getStockNum(), book.getPrice()}; 
					dtmBook.addRow(rowData);
				}
			}
		});
		btnReset.setBounds(713, 238, 127, 23);
		viewBooksPanel.add(btnReset);
		
		basketPanel = new JPanel();
		tabbedPane.addTab("View Basket", null, basketPanel, null);
		basketPanel.setLayout(null);
		JScrollPane scrollPaneBasket = new JScrollPane();
		scrollPaneBasket.setBounds(10, 11, 846, 204);
		basketPanel.add(scrollPaneBasket);
		
		
		//CREATE BASKET TABLE
		basketTable = new JTable();
		scrollPaneBasket.setViewportView(basketTable);
		basketTable.setDefaultEditor(Object.class, null);
		
		dtmBasket = new DefaultTableModel();
		dtmBasket.setColumnIdentifiers(new Object[] {"Barcode", "Name", "Language", "Genre", "Release Date", "Stock", "Price"});
		
		basketTable.setModel(dtmBasket);
		
		basketTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnModel columnModel = basketTable.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100); 
		columnModel.getColumn(1).setPreferredWidth(200); 
		columnModel.getColumn(2).setPreferredWidth(100);
		columnModel.getColumn(3).setPreferredWidth(150);
		columnModel.getColumn(4).setPreferredWidth(100); 
		columnModel.getColumn(5).setPreferredWidth(50);
		columnModel.getColumn(6).setPreferredWidth(50);
		
		basket = new Basket();
		
		//pay for books 
		JButton payButton = new JButton("Checkout");
		payButton.setBackground(new Color(255, 128, 128));
		payButton.setBounds(381, 226, 146, 23);
		basketPanel.add(payButton);
		payButton.addActionListener(e -> {
			//CHECK IF OK TO CHECKOUT 
		    double totalCost = Basket.getTotalCost();
		    if (totalCost == 0) {
		    	JOptionPane.showMessageDialog(null, "Basket is empty");
		    }
		    else if (user.getCreditBalance() >= totalCost) {
		    	
		    	for (Book book : Basket.getBooks()) {
		            if (book.getStockNum() < Basket.getQuantity(book)) {
		                JOptionPane.showMessageDialog(null, "There is not enough stock for " + book.getTitle());
		                return;
		            }
		        }
		    	
		    	//SUCCESSFUL CHECKOUT
		    	//remove credit, adjust local stock and adjust stock.txt
		        user.updateCreditBalance(user.getCreditBalance() - totalCost); 
		        for (Book newbook : Basket.getBooks()) {
		            newbook.setStockNum(newbook.getStockNum() - 1);
		        }
		        
		        BufferedWriter writer;
		        BufferedWriter writer2;
		      //rewrite stock file
		        try {
		            writer = new BufferedWriter(new FileWriter("Stock.txt"));
		            int lastIndex = bookList.size() - 1;
		            for (int i = 0; i < bookList.size(); i++) {
		                Book book = bookList.get(i);
		                writer.write(book.getFormattedAttributesNoNL());
		                if (i < lastIndex) {
		                    writer.newLine();
		                }
		            }
		            writer.close();
		            //rewrite user file with new credit
		            writer2 = new BufferedWriter(new FileWriter("UserAccounts.txt"));
		            int lastBookIndex = userList.size() - 1;
		            for (int i = 0; i < userList.size(); i++) {
		                User newuser = userList.get(i);
		                if (newuser instanceof Customer) { //if customer
		                	Customer customer = (Customer) newuser; //downcast
		                	if (customer.getUserID() == user.getUserID()) { //if id is the selected user
		                		customer.updateCreditBalance(user.getCreditBalance()); //readjust credit
		                	}
		                	writer2.write(customer.getFormattedAttributesNoNL()); //write user
		                }
		                else {
		                	 writer2.write(newuser.getFormattedAttributesNoNL()); //if admin write anyway
		                }
		                if (i < lastBookIndex) {
		                    writer2.newLine();
		                }
		            }
		         
		            writer2.close();
		        }
		        catch (IOException e1) {
					e1.printStackTrace();
				}
		        
		        Basket.getBooks().clear();
			    dtmBasket.setRowCount(0);
			    
		        String receipt = String.format(
		            "Thank you for the purchase! £%.2f paid and your remaining credit balance is £%.2f. Your delivery address is %s.",
		            totalCost, user.getCreditBalance(), user.getAddress()
		        );
		        JOptionPane.showMessageDialog(null, receipt);
		        DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
		        model.setRowCount(0); //clear the table
		        for (Book book : bookList) {
		            model.addRow(new Object[]{book.getBarcode(), book.getTitle(),book.getLanguage(), book.getGenre(), book.getRdate()
							, book.getStockNum(), book.getPrice()});
		        }

		    } else {
		        JOptionPane.showMessageDialog(null, "You don't have enough credit.");
		    }
		});
		
		//clear basket
		JButton btnClear = new JButton("Clear Basket");
		btnClear.setBounds(630, 226, 133, 23);
		basketPanel.add(btnClear);
		btnClear.addActionListener(e -> {
		    Basket.getBooks().clear();
		    dtmBasket.setRowCount(0);
		});
		
		//check account balance
		JButton btnBalance = new JButton("Check Balance");
		btnBalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DecimalFormat f = new DecimalFormat("##.00");
				JOptionPane.showMessageDialog(null, "Credit: £" + f.format(user.getCreditBalance()));
			}
		});
		btnBalance.setBounds(130, 226, 133, 23);
		basketPanel.add(btnBalance);

	}
	
	public void createTable(JScrollPane scrollPane) throws FileNotFoundException, IOException {
		bookTable = new JTable();
		scrollPane.setViewportView(bookTable);
		bookTable.setDefaultEditor(Object.class, null);
		
		dtmBook = new DefaultTableModel();
		dtmBook.setColumnIdentifiers(new Object[] {"Barcode", "Name", "Language", "Genre", "Release Date", "Stock", "Price", "Add to Basket"});

		bookTable.setModel(dtmBook);
		
		bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//setting the column widths for each column
		TableColumnModel columnModel = bookTable.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100); 
		columnModel.getColumn(1).setPreferredWidth(177); 
		columnModel.getColumn(2).setPreferredWidth(100);
		columnModel.getColumn(3).setPreferredWidth(150);
		columnModel.getColumn(4).setPreferredWidth(100); 
		columnModel.getColumn(5).setPreferredWidth(50);
		columnModel.getColumn(6).setPreferredWidth(50);
		columnModel.getColumn(7).setPreferredWidth(100);
		
		Book.sortBooksPrice(bookList);
		for (Book book : bookList) {
			Object[] rowData = new Object[] {book.getBarcode(), book.getTitle(),book.getLanguage(), book.getGenre(), book.getRdate()
					, book.getStockNum(), book.getPrice()}; 
			dtmBook.addRow(rowData);
		}
		Basket basket = new Basket();
		
		//adding book to basket
		bookTable.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		    	if (filterApplied == false) {
		    		int column = bookTable.columnAtPoint(e.getPoint());
		    		int row = bookTable.rowAtPoint(e.getPoint());
		    		if (column == 7) {
		    			Book book = bookList.get(row);
		    			basket.addBook(book);
		    			Object[] rowData = new Object[] {book.getBarcode(), book.getTitle(),book.getLanguage(), book.getGenre(), book.getRdate(), book.getStockNum(), book.getPrice()}; 
		    			dtmBasket.addRow(rowData);
		    			JOptionPane.showMessageDialog(null, "Book added to basket.");
		            
		        	}
		    	}
		    	else {
		    		int column = bookTable.columnAtPoint(e.getPoint());
			        int row = bookTable.rowAtPoint(e.getPoint());
			        if (column == 7) {
			            Book book = audiobookTable.get(row);
			            basket.addBook(book);
			            Object[] rowData = new Object[] {book.getBarcode(), book.getTitle(),book.getLanguage(), book.getGenre(), book.getRdate(), book.getStockNum(), book.getPrice()}; 
			            dtmBasket.addRow(rowData);
			            JOptionPane.showMessageDialog(null, "Book added to basket.");
			            
			        	}
		    	}
		    }
		});

	}
}
