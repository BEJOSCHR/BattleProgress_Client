package me.bejosch.battleprogress.client.Game;

import java.util.ConcurrentModificationException;

public abstract class Tick {

//==========================================================================================================
	/**
	 * Adding new tick methode into the tickList in the TimeManager
	 */
	public void addToTickList() {
		
		while(inTickList() == false) {
			try{
				TimeManager.tickList.add(this);
			}catch(ConcurrentModificationException | IndexOutOfBoundsException error) { }
		}
		
	}
	
//==========================================================================================================
	/**
	 * Removing old tick methode from the tickList in the TimeManager
	 */
	public void removeFromTickList() {
		
		while(inTickList() == true) {
			try{
				TimeManager.tickList.remove(this);
			}catch(ConcurrentModificationException | IndexOutOfBoundsException error) {}
		}
		
	}
	
//==========================================================================================================
	/**
	 * Checks if this object is in the tick list
	 * @return true if in the list, false if not
	 */
	public boolean inTickList() {
		
		if(TimeManager.tickList.contains(this)) {
			return true;
		}else {
			return false;
		}
		
	}
	
//==========================================================================================================
	/**
	 * The methode, called by the TimeManager for tick prosess
	 * @see addToList() - The methode have to be called for adding into the tick progress
	 */
	public abstract void timeTick();
	
	
	
}
