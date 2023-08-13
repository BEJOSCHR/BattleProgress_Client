package me.bejosch.battleprogress.client.Game.Handler;

import java.awt.event.MouseEvent;
import java.util.List;

import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;
import me.bejosch.battleprogress.client.ServerConnection.MinaClient;

public class Game_ClickEvent {

//==========================================================================================================
	//EVENTS
	/**
	 * Called if the mouse has been clicked
	 */
	public static void mouseClickedEvent(int mX, int mY, int clickType) {
		
		if(clickType == MouseEvent.BUTTON1) { //LEFT
			leftClick(mX, mY);
		}else if(clickType == MouseEvent.BUTTON2) { //MID
			midClick(mX, mY);
		}else if(clickType == MouseEvent.BUTTON3) { //RIGHT
			rightClick(mX, mY);
		}
		
	}
	
//==========================================================================================================
	/**
	 * Called if the mouse has been LEFT clicked
	 */
	public static void leftClick(int mX, int mY) {
		
		List<MouseActionArea> actionAreas = GameHandler.getMouseActionAreasFromScreenCoordinates(mX, mY);
		
		if(GameData.dragAndDropTaskInputActive == true) {
			//TASK INPUT

			if(Game_RoundHandler.blockedInput() == true) { return; } //IN ROUND CHANGE OR READY -> NO INPUT
			
			Building building = GameHandler.getBuildingByCoordinates(GameData.clickedField.X, GameData.clickedField.Y);
			Troup troup = GameHandler.getTroupByCoordinates(GameData.clickedField.X, GameData.clickedField.Y);
			if(building != null) {
				//BUILDING TASK
				Task_Building task = building.activeTask;
				task.action_Left_Release();
			}else if(troup != null) {
				//TROUP TASK
				Task_Troup task = troup.activeTask;
				task.action_Left_Release();
			}
		
		}else if(GameData.dragAndDropInputActive_BuildingMenu == true) {
			//BUILDING TASK INPUT
			GameData.currentActive_MAA_BuildingTask.performAction_LEFT_RELEASE();
			
		}else if(actionAreas.isEmpty() == false) {
			//ACTION AREA - LAYOUT PART
			for(MouseActionArea actionArea : actionAreas) {
				actionArea.performAction_LEFT_RELEASE();
			}
			
		}else {
			//NOT BLOCKED - NORMAL FIELD
			
			if(Game_RoundHandler.blockedInput() == true) { return; } //IN ROUND CHANGE OR READY -> NO INPUT
			
			Field choosenField = GameHandler.getFieldByScreenCoordinates(mX, mY);
			if(choosenField != null) {
				GameData.clickedField = choosenField;
			}else {
				GameData.clickedField = null;
			}
			
		}
		
	}
	
	private static long antiSpamTimeStamp = System.currentTimeMillis();
	
//==========================================================================================================
	/**
	 * Called if the mouse has been MID clicked
	 */
	public static void midClick(int mX, int mY) {
		
		List<MouseActionArea> actionAreas = GameHandler.getMouseActionAreasFromScreenCoordinates(mX, mY);
		
		if(actionAreas.isEmpty() == false) {
			//POSSIBLE MID CLICK EVENT
//			for(MouseActionArea actionArea : actionAreas) {
//				actionArea.performAction_LEFT_RELEASE(); //MID CLICK?
//			}
		}else {
			//NO MAA
			//FIELD PING
			if(antiSpamTimeStamp+1*1000 <= System.currentTimeMillis()) {
				//ENOUGH TIME HAS PASSED
				antiSpamTimeStamp = System.currentTimeMillis();
				Field field = GameHandler.getFieldByScreenCoordinates(mX, mY);
				if(field != null) {
					MinaClient.sendData(661, field.X+";"+field.Y);
				}
			}
		}
		
	}
	
//==========================================================================================================
	/**
	 * Called if the mouse has been RIGHT clicked
	 */
	public static void rightClick(int mX, int mY) {
		
		List<MouseActionArea> actionAreas = GameHandler.getMouseActionAreasFromScreenCoordinates(mX, mY);
		
		if(GameData.dragAndDropTaskInputActive == true) {
			//TASK INPUT
 
			if(Game_RoundHandler.blockedInput() == true) { return; } //IN ROUND CHANGE OR READY -> NO INPUT
			
			Building building = GameHandler.getBuildingByCoordinates(GameData.clickedField.X, GameData.clickedField.Y);
			Troup troup = GameHandler.getTroupByCoordinates(GameData.clickedField.X, GameData.clickedField.Y);
			if(building != null) {
				//BUILDING TASK
				Task_Building task = building.activeTask;
				task.action_Right_Release();
			}else if(troup != null) {
				//TROUP TASK
				Task_Troup task = troup.activeTask;
				task.action_Right_Release();
			}
			
		}else if(GameData.dragAndDropInputActive_BuildingMenu == true) {
			//BUILDING TASK INPUT
			GameData.currentActive_MAA_BuildingTask.performAction_RIGHT_RELEASE();
			
		}else if(actionAreas.isEmpty() == false) {
			//ACTION AREA - LAYOUT PART
			for(MouseActionArea actionArea : actionAreas) {
				actionArea.performAction_RIGHT_RELEASE();
			}
			
		}else {
			//NOT BLOCKED - NORMAL FIELD

			if(Game_RoundHandler.blockedInput() == true) { return; } //IN ROUND CHANGE OR READY -> NO INPUT
			
			GameData.clickedField = null;
			
		}
		
	}
}
