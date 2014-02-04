import java.awt.FlowLayout;

import javax.swing.JFrame;
/** authors: Raj Powar, Surafel Defar, Maxwell Gregory*/

public class Driver {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Model model = new Model();
		View mancala = new View(model); // creates a 9*9 buttons in a grid layout
		model.attach(mancala); // register observers
		
/* --------------- frame boilerplate -------------- */
		
		JFrame newJFrame = new JFrame();
		newJFrame.setSize(1300, 500);
	
		newJFrame.setLayout(new FlowLayout());
		newJFrame.add(mancala);
		newJFrame.setDefaultCloseOperation(3);
		newJFrame.setVisible(true);
		newJFrame.setLocationRelativeTo(null); //center on screen
		
		mancala.displayNumOfStonesDialog(model);
		
	}
}
