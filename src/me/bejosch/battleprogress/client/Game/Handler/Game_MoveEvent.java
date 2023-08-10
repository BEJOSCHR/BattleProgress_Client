package me.bejosch.battleprogress.client.Game.Handler;

import java.util.List;

import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class Game_MoveEvent {

//==========================================================================================================
	//EVENTS
	/**
	 * Called if the mouse has been moved
	 */
	public static void mouseMovedEvent(int mX, int mY) {
		
		List<MouseActionArea> actionAreas = GameHandler.getMouseActionAreasFromScreenCoordinates(mX, mY);
		
		if(actionAreas.isEmpty() == false) {
			//ACTION AREA - LAYOUT PART
			for(MouseActionArea actionArea : actionAreas) {
				actionArea.performAction_HOVER();
			}
			
		}else {
			//NOT BLOCKED - NORMAL FIELD
			
			if(Game_RoundHandler.blockedInput() == true) { return; } //IN ROUND CHANGE OR READY -> NO INPUT
			
			Field choosenField = GameHandler.getFieldByScreenCoordinates(mX, mY);
			if(choosenField != null) {
				GameData.hoveredField = choosenField;
			}
			
		}
		
	}
	
}
