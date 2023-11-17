package bookshop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JRadioButton;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	int i = 0;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public MainFrame() throws FileNotFoundException {
		this.setTitle("User Select Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		int height = 40;
		ArrayList<User> testList = User.getUsers();
		ButtonGroup radioUsers = new ButtonGroup();
		for (User user : testList) {
		    JRadioButton radioButton = new JRadioButton(user.toString() + ", Role: " + user.getRole());
		    radioUsers.add(radioButton);
		    contentPane.add(radioButton);
		    radioButton.setBounds(25, height, 500, 23);
		    height = height + 20;
		    
		}
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(179, 227, 89, 23);
		contentPane.add(btnSubmit);
		
		JLabel lbUser = new JLabel("Select User");
		lbUser.setBounds(189, 11, 500, 14);
		contentPane.add(lbUser);
		
		btnSubmit.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String selectedUser = null;
		        for (Enumeration<AbstractButton> buttons = radioUsers.getElements(); buttons.hasMoreElements();) {
		            AbstractButton button = buttons.nextElement();
		            i++;
		            if (button.isSelected()) {
		                selectedUser = button.getText();
		                break;
		            }
		        }
		        if (selectedUser == null) {
		        	JLabel lbError = new JLabel("No user selected");
		        	i = 0;
		    		contentPane.add(lbError);
		    		lbError.setBounds(179, 180, 150, 50);
		        	
		        }
		        else {
		        	User selectedPerson = testList.get(i-1);
		        	if (selectedPerson.getRole() == User.Role.admin) {
		        		AdminFrame adminwindow;
						try {
							adminwindow = new AdminFrame((Admin) selectedPerson);
							adminwindow.setVisible(true);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		        		
//		        		contentPane.setVisible(false);
		        	
		        	}
		        	else if (selectedPerson.getRole() == User.Role.customer) {
		        		try {
							CustomerFrame customerwindow = new CustomerFrame((Customer) selectedPerson);
							customerwindow.setVisible(true);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		        	}
		        	else {
		        		//not valid role
		        	}
		        	
		        	i = 0;	        	
		        }
		    }
		});
	}
}
