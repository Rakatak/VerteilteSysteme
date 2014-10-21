import java.awt.Container;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
* This Class represents the GUI for the first Task.
* The GUI is designed with Swing
* @author  Robin Steller
*/


@SuppressWarnings("serial")
public class SwingFrame extends JFrame implements ActionListener {

	JPanel mypanel;
	JButton searchButton;
	JTextField inputNumber;
	JTextField inputName;
	JLabel numLabel;
	JLabel namLabel;


	public SwingFrame() {

		mypanel = new JPanel();
		searchButton = new JButton(
				"                                                     Search!                                                     ");

		Container con = this.getContentPane();
		con.add(mypanel);

		inputNumber = new JTextField(32);
		inputName = new JTextField(32);

		numLabel = new JLabel("Number");
		namLabel = new JLabel("Name    ");
		searchButton.addActionListener(this);

		mypanel.add(numLabel);
		mypanel.add(inputNumber);
		mypanel.add(namLabel);
		mypanel.add(inputName);
		mypanel.add(searchButton);

	}


    //Action for clicking on the Search Button, initiates the Threads for searching in PhoneBook array, if at least one textfield contains characters
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchButton) {
			ArrayList<String> numResult = new ArrayList<String>();
			ArrayList<String> namResult = new ArrayList<String>();
			PhoneThread numThread = null;
			NameThread namThread = null;

			//return error message for entering characters into at least one textfield
			if (inputNumber.getText().isEmpty() && inputName.getText().isEmpty()) {
				System.err.println("Please Enter a Name or Number");
				return;
				}

			//thread searching for phonenumber is started
			if (inputNumber.getText().matches(".*\\w.*")) {
				System.out.println("PhoneThread started");
				numThread = new PhoneThread(inputNumber.getText(), PhoneBook.list, numResult);
				numThread.start();	
			} 
			
			//thread searching for name is started
			if (inputName.getText().matches(".*\\w.*"))  {
				System.out.println("NameThread started");
				namThread = new NameThread(inputName.getText(), PhoneBook.list, namResult);
				namThread.start();	
			}
			
			//now both threads will be joined if initiated
			if (numThread != null)  {
				try {
					numThread.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

                         if (namThread != null) {
				try {
					namThread.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}


			// after threads are joined, both array are joined and printed on console
			numResult.addAll(namResult);
			if (numResult.size()==0){
			    System.out.println("Suche nach " + inputName.getText()+  "  " + inputNumber.getText() + "   war erfolglos");
			    	} else {
			System.out.println("-----------------Results---------------");
			for (int i = 0; i < numResult.size() - 1 ; i += 2){
    			System.out.println("Name: " + numResult.get(i) + "   Number: " + numResult.get(i + 1));
				}
			}
			
		}
	}
}
