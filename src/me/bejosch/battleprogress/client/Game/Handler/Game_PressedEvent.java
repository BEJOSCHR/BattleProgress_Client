package me.bejosch.battleprogress.client.Game.Handler;

import java.awt.event.MouseEvent;
import java.util.List;

import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class Game_PressedEvent {

//==========================================================================================================
	//EVENTS
	/**
	 * Called if the mouse has been pressed
	 */
	public static void mousePressedEvent(int mX, int mY, int clickType) {
		
		if(clickType == MouseEvent.BUTTON1) { //LEFT
			leftClick(mX, mY);
		}else if(clickType == MouseEvent.BUTTON2) { //MID
			//NONE
		}else if(clickType == MouseEvent.BUTTON3) { //RIGHT
			rightClick(mX, mY);
		}
		
	}
	
//==========================================================================================================
	/**
	 * Called if the mouse has been LEFT pressed
	 */
	public static void leftClick(int mX, int mY) {
		
		List<MouseActionArea> actionAreas = GameHandler.getMouseActionAreasFromScreenCoordinates(mX, mY);
		
		if(actionAreas.isEmpty() == false) {
			//ACTION AREA - LAYOUT PART
			for(MouseActionArea actionArea : actionAreas) {
				actionArea.performAction_LEFT_PRESS();
			}
			
		}else {
			//NOT BLOCKED - NORMAL FIELD

			if(Game_RoundHandler.blockedInput() == true) { return; } //IN ROUND CHANGE OR READY -> NO INPUT
			
			
			
		}
		
	}
	
//==========================================================================================================
	/**
	 * Called if the mouse has been RIGHT pressed
	 */
	public static void rightClick(int mX, int mY) {
		
		List<MouseActionArea> actionAreas = GameHandler.getMouseActionAreasFromScreenCoordinates(mX, mY);
			
		if(actionAreas.isEmpty() == false) {
			//ACTION AREA - LAYOUT PART
			for(MouseActionArea actionArea : actionAreas) {
				actionArea.performAction_RIGHT_PRESS();
			}
			
		}else {
			//NOT BLOCKED - NORMAL FIELD
			
			if(Game_RoundHandler.blockedInput() == true) { return; } //IN ROUND CHANGE OR READY -> NO INPUT
			
		}
		
	}
}
