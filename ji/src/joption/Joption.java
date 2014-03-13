package joption;

import javax.swing.JOptionPane;

public class Joption {

	public void run(){
		String inputValue = JOptionPane.showInputDialog("Please input a value");
		System.out.println(inputValue);
		
	}
	public static void main(String[] args) {
		Joption test = new Joption();
		
		test.run();
		
		
	}
}
