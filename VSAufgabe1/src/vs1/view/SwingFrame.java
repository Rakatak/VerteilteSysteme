package vs1.view;

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

import vs1.data.NameThread;
import vs1.data.PhoneBook;
import vs1.data.PhoneThread;

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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchButton) {
			ArrayList<String> numResult = new ArrayList<String>();
			ArrayList<String> namResult = new ArrayList<String>();
			PhoneThread numThread = null;
			NameThread namThread = null;

			if (inputNumber.getText().isEmpty() && inputName.getText().isEmpty()) {
				System.err.println("Please Enter a Name or Number");
				return;
				}

			
			if (!inputNumber.getText().isEmpty()) {
				System.out.println("PhoneThread started");
				numThread = new PhoneThread(inputNumber.getText(), PhoneBook.list, numResult);
				numThread.start();	
				try {
					numThread.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} 
			
			if (!inputName.getText().isEmpty()) {
				System.out.println("NameThread started");
				namThread = new NameThread(inputName.getText(), PhoneBook.list, namResult);
				namThread.start();	
				try {
					namThread.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
			
			numResult.addAll(namResult);
			for (int i = 0; i < numResult.size() - 1 ; i += 2){
    			System.out.println("Name: " + numResult.get(i)+ " Phone Number: " + numResult.get(i + 1));
			}
			
		}
	}
}
