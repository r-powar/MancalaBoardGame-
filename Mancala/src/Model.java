import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Model {	
// ---------------------- max's code ------------------------------------------	
	private int pits[];
    private int undopits[];
    
    private boolean turn;//playerA's turn = true
    private ArrayList<ChangeListener> listeners;
    private int aUndo,bUndo;
    
    private final int MAX_UNDO = 3;

    public Model() {
        turn = true;
        pits = new int[14];//index 0 is playerA pot, index 13 is playerB pot
        listeners = new ArrayList();
        aUndo = bUndo = MAX_UNDO;
    }
    public boolean whoTurn(){//return's whos turn it is
        return turn;
    }
    /**
     * sets the maximum number of stones put in the pits at initialization
     * @param max number of stones per pit at game start
     */
    public void setMax(int max) {
        for(int i=0; i<13;i++){
            pits[i]=max;
        }
        pits[6]=0;
		changenotify();
    }
    /**
     * Clones data for undo function 
     */
    public void save(){
        undopits = pits.clone();
    }
    /**
     * Called to undo and restore cloned data depending on player turn
     */
    public void preformUndo(){
    	nextTurn();
        if(turn && aUndo != 0){
            pits = undopits;
            aUndo--;
        }
        else if(!turn && bUndo !=0 ){
            pits = undopits;
            bUndo--;
        }
        
        changenotify();
    }
    /**
     * Main function to handle game logic for player's turn
     * @param index pit clicked that points to array position
     */
    public void makeMove(int index){
        save();
        if(turn){//playerA turn
            int StonesinHand = pits[index];
            pits[index]=0;//grab all stones so no more stones in target pit
            int loc = index;
            while (StonesinHand > 0){
                pits[loc+1]++;
            	loc++;
                StonesinHand--;
                if(loc > 12){//resets array position
                    loc=0;
                	if(StonesinHand == 0){
                		nextTurn();
                	}
                	else{
                		pits[loc]++;
                		StonesinHand--;
                	}
                }
            }
            if(pits[loc]==1 && loc < 6 && pits[12-loc] >0){//if the last stone was placed in an empty pit 
            	pits[6] += pits[12-loc]+1;				  //that belongs to playerA
                pits[12-loc]=0;							  //and the opposing pit has stones in it
				pits[loc]=0;
				nextTurn();
            }
            else{
                if(loc != 6){//if the last stone wasnt placed in your own Mancala, next turn
                    nextTurn();
                }
            }
        }
        else{//playerB turn
            int StonesinHand = pits[index];
            pits[index]=0;//grab all stones so no more stones in target pit
            int loc = index;
            while (StonesinHand > 0){
            	pits[loc+1]++;
            	loc++;
                StonesinHand--;
                if(loc >= 13){
                    loc=0;
                	if(StonesinHand == 0){
                		nextTurn();
                	}
                	else{
                		pits[loc]++;
                		StonesinHand--;
                	}
                }
            }
            if(pits[loc]==1 && loc > 7 && pits[12%loc] > 0 && loc != 13){
            	pits[13] += pits[12 % loc]+1;//take stones from opposing pit and put in pot plus the stone moved
                pits[12%loc]=0;//the pit is now empty
				pits[loc]=0;//the stone moved during the turn is gone
				nextTurn();
            }
            else{
                if(loc != 13){//if the last stone wasnt placed in your own Mancala, next turn
                    nextTurn();
                }
              
            }
			
			
        }
        changenotify();
            
                
    }
	/**
	 * Tallies the score and returns a "win" statement depending on the score
	 * @return the "win" statement and score
	 */
	public String tally(){
		int totalA = 0, totalB = 0, i=0;
		for(i=0; i<7;i++){//total up playerA's pits and Mancala
			totalA += pits[i];
		}
		for(; i<14; i++){//total up playerB's pits and Mancala
			totalB += pits[i];
		}
		if(totalA > totalB){//test for who wins and return the "win" statement
			return "Player A wins!\nPlayer A: "+totalA+" Player B: "+totalB;
		}
		else if(totalB > totalA){
			return "Player B wins!\nPlayer B: "+totalB+" Player A: "+totalA;
		}
		else{
			return "There is a tie!\nPlayer A: "+totalA+" Player B: "+totalB;
		}
	}
        
    /**
     * Changes the turn
     */
    public void nextTurn(){
        turn = !turn;
    }
    /**
     * Tests to see if one player's pits are empty
     * @return game over flag
     */
    public boolean gameover(){
        if(emptyCheck(0,6)){//if playerA pits are empty, then game over
            return true;
        }
		else if(emptyCheck(7,13)){//if playerB pits are empty, then game over
			return true;
		}
		else {
			return false;//no side is empty
		}
    }
	/**
	 * Checks a part of the array to see if all the values are zero
	 * @param start beginning of empty check process
	 * @param end end of the empty check process
	 * @return empty flag
	 */
	public boolean emptyCheck(int start, int end){
		int empty=0;
		for(; start<end; start++){
			if(pits[start]==0){
				empty++;
			}
		}
		if(empty ==6)
			return true;
		else {
			return false;
		}

	}
	
     public void attach(ChangeListener c)
    {
        listeners.add(c);
    }
     /**
      * Notifies all listeners of a model change
      */
     private void changenotify()
    {
        for (ChangeListener c : listeners)
        {
            c.stateChanged(new ChangeEvent(this));
        }
    }
     
 	/** given a pit number it returns the number of stones that pit has */
 	public Integer getValue(int pitNumber){
 		return pits[pitNumber];
 	}
}
