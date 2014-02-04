import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class View extends JPanel implements ChangeListener{
	private Model model; // a reference to the model, how you are going to access model functions
	private JPanel[] pits; // the pots of the mancala
	JPanel centralPanel = new JPanel();
	JLabel playerTurnLabel = new JLabel();
	private int layoutType;
	
	public View(final Model model){
		this.model = model; // getting the reference
		
		pits = new JPanel[14];//reference
		
//		if (layoutType == 1){
			setLayout(new BorderLayout()); // layout for the big jpanel
//		}else{
//			setLayout(new FlowLayout());
//		}
		// go through each pot reference, create a panel for it
		for (Integer i = 0; i < 14; i++){ 
			final int index = i;
			pits[i] = new JPanel();//create actual panels
			
			// to display the index number for the pits
			JLabel pitNumber = new JLabel(i.toString());
			pits[i].add(pitNumber);
			// end of to display the index number for the pits
			
			if (i != 6 && i != 13){ //if they are not the mancalas
				// what happens when the user clicks on one of the pits
				pits[i].addMouseListener(new MouseAdapter() { 
				      public void mousePressed(MouseEvent me) { 
				    	  System.out.println("it works");
				    	  if(pits[index].isEnabled()){
				    		  
				    		  model.makeMove(index); //call models function responsible when the user clicks a pit
				    	  }
				        } 
				      }); 
			}
			
			
			
			pits[i].setPreferredSize(new Dimension(100, 150));
			
			Border line = BorderFactory.createLineBorder(Color.black);
			pits[i].setBorder(line);

		}   
		
		

		//create a panel to hold the 12 center pots ------
		
		centralPanel.setLayout(new GridLayout(2, 6));
		
		
		centralPanel.add(pits[12]);
		centralPanel.add(pits[11]);
		centralPanel.add(pits[10]);
		centralPanel.add(pits[9]);
		centralPanel.add(pits[8]);
		centralPanel.add(pits[7]);
		
		centralPanel.add(pits[0]);
		centralPanel.add(pits[1]);
		centralPanel.add(pits[2]);
		centralPanel.add(pits[3]);
		centralPanel.add(pits[4]);
		centralPanel.add(pits[5]);
		

		add(pits[6], BorderLayout.EAST);
		add(centralPanel, BorderLayout.CENTER);
		add(pits[13], BorderLayout.WEST);
		add(playerTurnLabel, BorderLayout.NORTH);
		// end of create panel to hold the 12 cneter pots ---
		
		addUndoButton();//add undo button to the panel
	}

	private void addUndoButton() {
		// code for the undo button -----------------------
		JButton undoBtn = new JButton("undo");
		undoBtn.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
			    model.preformUndo();
			  } 
			} );
		
		add(undoBtn, BorderLayout.SOUTH);
		// end of code for the undo button ----------------
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
		
		//first clear the previous stones from the pits
		for (int z = 0; z < 14; z++){
			//remove all components in panel.
			pits[z].removeAll(); 
			// refresh the panel.
			pits[z].updateUI();
		}
		
		
		
		// go through the two players pits including the mancala and add the stones
		for (int x = 0; x < 14; x++){//playerA's pits 
			int numOfStones = model.getValue(x);
			
			for (int j = 0; j < numOfStones; j++){//for each pit add all the stones
				 pits[x].add(new Stone());
			}
		}		
		
		disableTurn(); //disables the pits
		if (model.whoTurn()){
			playerTurnLabel.setText("Player A's Turn");
		}else{
			playerTurnLabel.setText("Player B's Turn");
		}
		revalidate(); //very important check container.add api: 
		

		if (model.gameover()){
			JOptionPane.showMessageDialog(getParent(), model.tally());//for game over logic
		}
	}
	
	/** checks which players turn it is and disables the pits for the other player */
	private void disableTurn() { 

		
		if (model.whoTurn()){	// if it is player A's turn
			for (int i = 7; i < 13; i++ ){
				pits[i].setEnabled(false);
				pits[i].setToolTipText("disabled");
				
			}
			for (int i = 0; i < 7; i++){
				pits[i].setEnabled(true);
				pits[i].setToolTipText("enabled");
			}
		}else if (!model.whoTurn()){ //if it is player B's turn
			for (int i = 7; i < 13; i++ ){
				pits[i].setEnabled(true);
				pits[i].setToolTipText("enabled");
			}
			for (int i = 0; i < 7; i++){
				pits[i].setEnabled(false);
				pits[i].setToolTipText("disabled");
			}
		}
		
		
		
	}
	
	
	/** displays the modal dialog box which lets players choose number of stones in each pit*/
	public void displayNumOfStonesDialog(Model model) {
		/** number of pits option pane */
		Object[] options = {"3", "4"};
		int userInput = JOptionPane.showOptionDialog(
		    getParent(), "Please choose the number of stones that will be placed in each pit",
		    "number of stones",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[1]);
		
		if(userInput == 0){
			model.setMax(3);
			layoutType = 1;
		}else if(userInput == 1){
			model.setMax(4);
			layoutType = 2;
		}
	}


}
