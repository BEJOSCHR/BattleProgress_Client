package me.bejosch.battleprogress.client.Game.Handler;

import java.awt.event.MouseEvent;
import java.util.List;

import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Attack;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Heal;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Repair;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Attack;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Heal;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Move;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Repair;
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
				if(GameData.clickedField == null) {
					GameData.clickedField = choosenField;
				}else {
					Building building = GameHandler.getBuildingByCoordinates(GameData.clickedField.X, GameData.clickedField.Y);
					Building targetBuilding = choosenField.building;
					Troup troup = GameHandler.getTroupByCoordinates(GameData.clickedField.X, GameData.clickedField.Y);
					Troup targetTroup = choosenField.troup;
					if(building != null && building.playerID == ProfilData.thisClient.getID()) {
						boolean hit = false;
						for(Task_Building bTask : building.actionTasks) {
							if(bTask instanceof Task_Building_Attack) {
								//ATTACK - ON ENEMY BUILDING OR TROUP?
								if(targetBuilding != null && GameHandler.checkPlayerIDForAllied(ProfilData.thisClient.getID(), targetBuilding.playerID) == false) {
									bTask.action_Left_Press();
									bTask.action_Left_Release();
									hit = true; break;
								}else if(targetTroup != null && GameHandler.checkPlayerIDForAllied(ProfilData.thisClient.getID(), targetTroup.playerID) == false) {
									bTask.action_Left_Press();
									bTask.action_Left_Release();
									hit = true; break;
								}
							}
							if(bTask instanceof Task_Building_Heal) {
								//HEAL - ON ALLY TROUP?
								if(targetTroup != null && GameHandler.checkPlayerIDForAllied(ProfilData.thisClient.getID(), targetTroup.playerID) == true) {
									bTask.action_Left_Press();
									bTask.action_Left_Release();
									hit = true; break;
								}
							}
							if(bTask instanceof Task_Building_Repair) {
								//REPAIR - ON ALLY BUILDING?
								if(targetBuilding != null && GameHandler.checkPlayerIDForAllied(ProfilData.thisClient.getID(), targetBuilding.playerID) == true) {
									bTask.action_Left_Press();
									bTask.action_Left_Release();
									hit = true; break;
								}
							}
						}
						if(!hit) { GameData.clickedField = choosenField; }
					}else if(troup != null && troup.playerID == ProfilData.thisClient.getID()) {
						boolean hit = false;
						for(Task_Troup tTask : troup.actionTasks) {
							if(tTask instanceof Task_Troup_Attack) {
								//ATTACK - ON ENEMY BUILDING OR TROUP?
								if(targetBuilding != null && GameHandler.checkPlayerIDForAllied(ProfilData.thisClient.getID(), targetBuilding.playerID) == false) {
									tTask.action_Left_Press();
									tTask.action_Left_Release();
									hit = true; break;
								}else if(targetTroup != null && GameHandler.checkPlayerIDForAllied(ProfilData.thisClient.getID(), targetTroup.playerID) == false) {
									tTask.action_Left_Press();
									tTask.action_Left_Release();
									hit = true; break;
								}
							}
							if(tTask instanceof Task_Troup_Move) {
								//MOVE - TARGET FIELD EMPTY
								if(targetBuilding == null && targetTroup == null) {
									tTask.action_Left_Press();
									tTask.action_Left_Release();
									hit = true; break;
								}
							}
							if(tTask instanceof Task_Troup_Heal) {
								//HEAL - ON ALLY TROUP?
								if(targetTroup != null && GameHandler.checkPlayerIDForAllied(ProfilData.thisClient.getID(), targetTroup.playerID) == true) {
									tTask.action_Left_Press();
									tTask.action_Left_Release();
									hit = true; break;
								}
							}
							if(tTask instanceof Task_Troup_Repair) {
								//REPAIR - ON ALLY BUILDING?
								if(targetBuilding != null && GameHandler.checkPlayerIDForAllied(ProfilData.thisClient.getID(), targetBuilding.playerID) == true) {
									tTask.action_Left_Press();
									tTask.action_Left_Release();
									hit = true; break;
								}
							}
						}
						if(!hit) { GameData.clickedField = choosenField; }
					}else {
						GameData.clickedField = choosenField;
					}
				}
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
