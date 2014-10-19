package vs1.data;

import java.util.ArrayList;

import javax.swing.JFrame;

import vs1.view.SwingFrame;

public class Main {
	
	public static void main(String[] args){
		
		SwingFrame frame = new SwingFrame();
		frame.setTitle("Awesome Search GUI");
		frame.setSize(450, 360);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		
	}
}
