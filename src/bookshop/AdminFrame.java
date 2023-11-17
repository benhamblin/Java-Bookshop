package bookshop;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import bookshop.Audiobook.Format;
import bookshop.Book.Genre;
import bookshop.Book.Language;
import bookshop.Ebook.eFormat;
import bookshop.Paperback.Condition;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class AdminFrame extends JFrame {

	private JPanel contentPane;
	private JTextField newBarcode;
	private JTextField newTitle;
	final static String FRENCH = "French";
    final static String ENGLISH = "English";
    final static String PAPERBACK = "Paperback";
    final static String AUDIOBOOK = "Audiobook";
    final static String EBOOK = "Ebook";
    final static String SELECT = "Please select type";
    private JTextField txtNewDate;
    private JTextField txtNewStock;
    private JTextField txtNewRetail;
    private JTextField txtPageNum;
    private JTextField txtFormat;
    private JTextField txtLength;
    private JLabel lblPages;
    private JLabel lblCond;
    private JLabel lblLength;
    private JLabel lblFormat;
    private JTable bookTable;
    private DefaultTableModel dtmBook;
    private JComboBox cbCondition;
    private JComboBox cbEformat;
    private JComboBox cbformat;
 
	public AdminFrame(Admin selectedPerson) throws FileNotFoundException, IOException {
		this.setTitle("ADMIN MENU - " + selectedPerson.getSurname());

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 664, 254);
		contentPane.add(tabbedPane);
		
		JPanel viewBooksPanel = new JPanel();
		tabbedPane.addTab("View Books", null, viewBooksPanel, null);
		viewBooksPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 646, 204);
		viewBooksPanel.add(scrollPane);
		//create the table
		createTable(scrollPane);
		
		JPanel addStockPanel = new JPanel();
		tabbedPane.addTab("Add Stock", null, addStockPanel, null);
		addStockPanel.setLayout(null);
		
		JLabel lblBarcode = new JLabel("Barcode");
		lblBarcode.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblBarcode.setBounds(21, 8, 102, 14);
		addStockPanel.add(lblBarcode);
		
		newBarcode = new JTextField();
		newBarcode.setBounds(100, 5, 119, 20);
		addStockPanel.add(newBarcode);
		newBarcode.setColumns(10);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTitle.setBounds(25, 33, 119, 14);
		addStockPanel.add(lblTitle);
		
		newTitle = new JTextField();
		newTitle.setBounds(100, 30, 119, 20);
		addStockPanel.add(newTitle);
		newTitle.setColumns(10);
		
		String languagePanes[] = { ENGLISH, FRENCH };
		JComboBox newLanguage = new JComboBox(languagePanes);
		newLanguage.setBounds(100, 54, 119, 22);
		addStockPanel.add(newLanguage);
		
		String[] genrePanes = Book.getAllGenres();
		JComboBox cbGenre = new JComboBox(genrePanes);
		cbGenre.setBounds(99, 79, 120, 22);
		addStockPanel.add(cbGenre);
		
		
		JLabel lblLanguage = new JLabel("Language");
		lblLanguage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblLanguage.setBounds(15, 50, 119, 26);
		addStockPanel.add(lblLanguage);
		
		JLabel lblGenre = new JLabel("Genre");
		lblGenre.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblGenre.setBounds(20, 75, 119, 26);
		addStockPanel.add(lblGenre);
		
		JLabel lblDate = new JLabel("Release Date");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDate.setBounds(4, 100, 119, 26);
		addStockPanel.add(lblDate);
		
		JLabel lblDateInfo = new JLabel("YYYY-MM-DD");
		lblDateInfo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDateInfo.setBounds(220, 100, 119, 26);
		addStockPanel.add(lblDateInfo);
		
		JLabel lblStock = new JLabel("Stock Quantity");
		lblStock.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblStock.setBounds(4, 125, 119, 26);
		addStockPanel.add(lblStock);
		
		txtNewDate = new JTextField();
		txtNewDate.setToolTipText("");
		txtNewDate.setColumns(10);
		txtNewDate.setBounds(100, 105, 119, 20);
		addStockPanel.add(txtNewDate);
		
		txtNewStock = new JTextField();
		txtNewStock.setToolTipText("");
		txtNewStock.setColumns(10);
		txtNewStock.setBounds(100, 130, 119, 20);
		addStockPanel.add(txtNewStock);
		
		txtNewRetail = new JTextField();
		txtNewRetail.setToolTipText("");
		txtNewRetail.setColumns(10);
		txtNewRetail.setBounds(100, 155, 119, 20);
		addStockPanel.add(txtNewRetail);
		
		JLabel lblPrice = new JLabel("Retail Price");
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPrice.setBounds(14, 150, 119, 26);
		addStockPanel.add(lblPrice);
		
		String bookTypes[] = { SELECT, PAPERBACK, EBOOK, AUDIOBOOK };
		JComboBox<String> newBookType = new JComboBox(bookTypes);
		newBookType.setBounds(244, 31, 138, 22);
		addStockPanel.add(newBookType);
		
		JButton btnAddBook = new JButton("Add Book");
		btnAddBook.setBackground(new Color(255, 255, 255));
		btnAddBook.setBounds(244, 180, 180, 23);
		addStockPanel.add(btnAddBook);
		
		//ADD BOOK TO BOOKLIST
		btnAddBook.addActionListener(new ActionListener()  {
			public void actionPerformed(ActionEvent e) {
				 
				 if (!checkTextFields()) {
			            JOptionPane.showMessageDialog(null, "Please fill in all text fields.");
			            return;
			        }
			        
			        if (!isInteger(txtNewStock.getText()) || !isInteger(newBarcode.getText())) {
			            JOptionPane.showMessageDialog(null, "Stock quantity and barcode must be valid integers.");
			            return;
			        }
			        
			        if (newBarcode.getText().length() != 8 || !isNumeric(newBarcode.getText())) {
			            JOptionPane.showMessageDialog(null, "Barcode must be an 8-digit integer.");
			            return;
			        }
		
			        if (!isNumeric(txtNewRetail.getText())) {
			            JOptionPane.showMessageDialog(null, "Retail price must be a valid number.");
			            return;
			        }
			        
			        double retailPrice = Double.parseDouble(txtNewRetail.getText());
			        if (retailPrice <= 0) {
			            JOptionPane.showMessageDialog(null, "Retail price must be greater than 0.");
			            return;
			        }
			        
			        
			        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			        try {
			            LocalDate.parse(txtNewDate.getText(), dateFormatter);
			        } catch (DateTimeParseException ex) {
			            JOptionPane.showMessageDialog(null, "Date must be in the format YYYY-MM-DD.");
			            return;
			        }
			        
			        ArrayList<Book> bookList = null;
					try {
						bookList = Book.getBooks();
					} catch (FileNotFoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					Book.sortBooks(bookList);
					for (Book book : bookList) {
						if (book.getBarcode() == Integer.parseInt(newBarcode.getText()) ) {
							JOptionPane.showMessageDialog(null, "Barcode already exists..");
				            return;
						}
					}

			        
			        String bookType = (String) newBookType.getSelectedItem();
			        if (bookType.equals(SELECT)) {
			            JOptionPane.showMessageDialog(null, "Please select a book type.");
			            return;
			        } else if (bookType.equals(PAPERBACK)) {
			            if (txtPageNum.getText().isEmpty()) {
			                JOptionPane.showMessageDialog(null, "Please enter the number of pages.");
			                return;
			            }
			            if (!isNumeric(txtPageNum.getText())) {
			                JOptionPane.showMessageDialog(null, "Number of pages must be an integer.");
			                return;
			            }
			            
			             Paperback newBook = new Paperback(Integer.parseInt(newBarcode.getText()), newTitle.getText(), 
			             	Language.valueOf((String) newLanguage.getSelectedItem()), 
			             	Genre.valueOf((String) cbGenre.getSelectedItem()), 
			             	LocalDate.parse(txtNewDate.getText(), dateFormatter), 
			             	Integer.parseInt(txtNewStock.getText()), 
			             	Double.parseDouble(txtNewRetail.getText()), 
			             	Integer.parseInt(txtPageNum.getText()), 
			             	Condition.valueOf(((String) cbCondition.getSelectedItem()).toUpperCase()));
			 
			            JOptionPane.showMessageDialog(null, "Book added.");
            			try {
							Book.addBookToStock(newBook);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			        } else if (bookType.equals(EBOOK)) {
			            if (txtPageNum.getText().isEmpty()) {
			                JOptionPane.showMessageDialog(null, "Please enter the number of pages.");
			                return;
			            }
			            if (!isNumeric(txtPageNum.getText())) {
			                JOptionPane.showMessageDialog(null, "Number of pages must be an integer.");
			                return;
			            }
			            Ebook newBook = new Ebook(Integer.parseInt(newBarcode.getText()), newTitle.getText(), 
			            	Language.valueOf((String) newLanguage.getSelectedItem()), 
			            	Genre.valueOf((String) cbGenre.getSelectedItem()), 
			            	LocalDate.parse(txtNewDate.getText(), dateFormatter), 
			            	Integer.parseInt(txtNewStock.getText()), 
			            	Double.parseDouble(txtNewRetail.getText()), 
			            	Integer.parseInt(txtPageNum.getText()), 
			            	eFormat.valueOf(((String) cbEformat.getSelectedItem()).toUpperCase()));
			            
			            JOptionPane.showMessageDialog(null, "Book added.");

            			try {
							Book.addBookToStock(newBook);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
            			
			        } else if (bookType.equals(AUDIOBOOK)) {
			            if (txtLength.getText().isEmpty()) {
			                JOptionPane.showMessageDialog(null, "Please enter the length.");
			                return;
			            }
			            if (!isDouble(txtLength.getText())) {
			                JOptionPane.showMessageDialog(null, "Length must be a double.");
			                return;
			            }
			            
			            
						Audiobook newBook = new Audiobook(Integer.parseInt(newBarcode.getText()), newTitle.getText(), 
			            	Language.valueOf((String) newLanguage.getSelectedItem()), 
			            	Genre.valueOf((String) cbGenre.getSelectedItem()), 
			            	LocalDate.parse(txtNewDate.getText(), dateFormatter), 
			            	Integer.parseInt(txtNewStock.getText()), 
			            	Double.parseDouble(txtNewRetail.getText()), 
			            	Double.parseDouble(txtLength.getText()), 
			            	Format.valueOf(((String) cbformat.getSelectedItem()).toUpperCase()));
			            
			            JOptionPane.showMessageDialog(null, "Book added.");
			            
            			try {
							Book.addBookToStock(newBook);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
			        }
			        
			    }
		});
		
	
		//CHANGES THE OPTIONS THAT COME UP DEPENDING ON IF IT IS PAPERBACK, EBOOK OR AUDIOBOOK
		newBookType.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String selectedBookType = (String) newBookType.getSelectedItem();
		        switch (selectedBookType) {
		        case SELECT:
		        	checkNullText(txtPageNum);
		            checkNullLabel(lblPages);
		            checkNullLabel(lblFormat);
		            checkNullLabel(lblCond);
		            checkNullText(txtFormat);
		            checkNullLabel(lblLength);
		            checkNullText(txtLength);
		            break;
		        case "Paperback":
		        	checkNullCB(cbEformat);
		  			checkNullCB(cbCondition);
		  			checkNullCB(cbformat);
		        	checkNullText(txtFormat);
		        	checkNullText(txtFormat);
		        	checkNullText(txtPageNum);
		        	checkNullLabel(lblFormat);
		        	checkNullLabel(lblFormat);
		        	checkNullLabel(lblPages);
		            checkNullLabel(lblCond);
		            checkNullLabel(lblLength);
		            checkNullText(txtLength);
		            lblPages = new JLabel("Num of Pages");
		            lblPages.setFont(new Font("Tahoma", Font.PLAIN, 16));
		            lblPages.setBounds(400, 18, 119, 26);
		            lblCond = new JLabel("Condition:");
		            lblCond.setFont(new Font("Tahoma", Font.PLAIN, 16));
		            lblCond.setBounds(400, 40, 119, 26);
		            addStockPanel.add(lblPages);
		            addStockPanel.add(lblCond);
		            String[] conditionPanes = Paperback.getAllConditions();
		    		cbCondition = new JComboBox(conditionPanes);
		    		cbCondition.setBounds(500, 42, 119, 20);
		            txtPageNum = new JTextField();
		            txtPageNum.setToolTipText("");
		            txtPageNum.setColumns(10);
		            txtPageNum.setBounds(500, 20, 119, 20);
		            addStockPanel.add(txtPageNum);
		            addStockPanel.add(cbCondition);
		            addStockPanel.repaint();
		            break;
		        case "Ebook":
		        	checkNullCB(cbEformat);
		  			checkNullCB(cbCondition);
		  			checkNullCB(cbformat);
		        	checkNullText(txtPageNum);
		            checkNullLabel(lblPages);
		            checkNullLabel(lblCond);
		            checkNullLabel(lblLength);
		            checkNullText(txtLength);
		            lblPages = new JLabel("Num of Pages");
		            lblPages.setFont(new Font("Tahoma", Font.PLAIN, 16));
		            lblPages.setBounds(400, 18, 119, 26);
		            lblFormat = new JLabel("Format");
		            lblFormat.setFont(new Font("Tahoma", Font.PLAIN, 16));
		            lblFormat.setBounds(400, 40, 119, 26);
		            addStockPanel.add(lblPages);
		            addStockPanel.add(lblFormat);
		            String[] eformatPanes = Ebook.getAllEformats();
		    		cbEformat = new JComboBox(eformatPanes);
		    		cbEformat.setBounds(500, 42, 119, 20);
		    		addStockPanel.add(cbEformat);
		            txtPageNum = new JTextField();
		            txtPageNum.setToolTipText("");
		            txtPageNum.setColumns(10);
		            txtPageNum.setBounds(500, 20, 119, 20);
		            addStockPanel.add(txtPageNum);
		            addStockPanel.repaint();
		            break;
		        case "Audiobook":
		        	checkNullText(txtPageNum);
		            checkNullLabel(lblPages);
		            checkNullText(txtPageNum);
		            checkNullLabel(lblFormat);
		  			checkNullCB(cbEformat);
		  			checkNullCB(cbCondition);
		  			checkNullCB(cbformat);
		            checkNullLabel(lblCond);
		            checkNullText(txtFormat);
		            lblLength = new JLabel("Length (hrs)");
		            lblLength.setFont(new Font("Tahoma", Font.PLAIN, 16));
		            lblLength.setBounds(400, 18, 119, 26);
		            lblFormat = new JLabel("Format");
		            lblFormat.setFont(new Font("Tahoma", Font.PLAIN, 16));
		            lblFormat.setBounds(400, 40, 119, 26);
		            addStockPanel.add(lblLength);
		            addStockPanel.add(lblFormat);
		            String[] formatPanes = Audiobook.getAllFormats();
		    		cbformat = new JComboBox(formatPanes);
		    		cbformat.setBounds(500, 42, 119, 20);
		    		addStockPanel.add(cbformat);
		            txtLength = new JTextField();
		            txtLength.setToolTipText("");
		            txtLength.setColumns(10);
		            txtLength.setBounds(500, 20, 119, 20);
		            addStockPanel.add(txtLength);
		            addStockPanel.repaint();
		            break;
		    }

		    }
		});
	}
	
	
	public void checkNullText(JTextField text) {
		if (text != null) {
            text.setVisible(false);
        }
	}
	public void checkNullLabel(JLabel label) {
		if (label != null) {
            label.setVisible(false);
        }
	}
	public void checkNullCB(JComboBox cb) {
		if (cb != null) {
            cb.setVisible(false);
        }
	}
	
	public void createTable(JScrollPane scrollPane) throws FileNotFoundException, IOException {
		bookTable = new JTable();
		scrollPane.setViewportView(bookTable);
		bookTable.setDefaultEditor(Object.class, null);
		
		dtmBook = new DefaultTableModel();
		dtmBook.setColumnIdentifiers(new Object[] {"Barcode", "Name", "Language", "Genre", "Release Date", "Stock", "Price"});
		bookTable.setModel(dtmBook);
		
		bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// set the column widths for each column
		TableColumnModel columnModel = bookTable.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100); 
		columnModel.getColumn(1).setPreferredWidth(200); 
		columnModel.getColumn(2).setPreferredWidth(100);
		columnModel.getColumn(3).setPreferredWidth(150);
		columnModel.getColumn(4).setPreferredWidth(100); 
		columnModel.getColumn(5).setPreferredWidth(50);
		columnModel.getColumn(6).setPreferredWidth(75);
		
		ArrayList<Book> bookList = Book.getBooks();
		Book.sortBooks(bookList);
		for (Book book : bookList) {
			Object[] rowData = new Object[] {book.getBarcode(), book.getTitle(),book.getLanguage(), book.getGenre(), book.getRdate()
					, book.getStockNum(), book.getPrice()}; 
			dtmBook.addRow(rowData);
		}
	}
	
	public static boolean isInteger(String str) {
	    try {
	        Integer.parseInt(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	private boolean checkTextFields() {
	    if (newBarcode.getText().isEmpty() ||
	        newTitle.getText().isEmpty() ||
	        txtNewDate.getText().isEmpty() ||
	        txtNewDate.getText().isEmpty() ||
	        txtNewStock.getText().isEmpty() ||
	        txtNewRetail.getText().isEmpty()) {
	        return false; // at least one text field is empty
	    }
	    return true; // all text fields are non-empty
	}
	
	public static boolean isNumeric(String str) {
	    try {
	        if (str == null) {
	            return false;
	        }
	        double d = Double.parseDouble(str);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	private boolean isDouble(String str) {
	    try {
	        Double.parseDouble(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}



}
