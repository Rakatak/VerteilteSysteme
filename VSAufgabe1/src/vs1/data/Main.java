import java.util.ArrayList;

import javax.swing.JFrame;

/**
* This Class represents the Main Method for the PhoneSearch GUI Program
* The Main method initiates the SwingFrame as a GUI
* @author  Robin Steller
*/


public class Main {
	
	public static void main(String[] args){
		
		SwingFrame frame = new SwingFrame();
		frame.setTitle("Awesome Search GUI");
		frame.setSize(450, 360);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		
	}
}
