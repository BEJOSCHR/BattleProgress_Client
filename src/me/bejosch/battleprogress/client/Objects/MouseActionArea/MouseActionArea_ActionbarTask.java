package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Color;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Enum.SourceType;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;

public class MouseActionArea_ActionbarTask extends MouseActionArea{

	public int taskPlaceNumber = -1;
	public SourceType sourceType = null;
	
	public Color standardColor_Save = null, hoverColor_Save = null;
	public Color activeTaskColor = Color.GREEN, notActiveTaskColor = Color.RED;
	
//==========================================================================================================
	/**
	 * A special ActionArea for the tasks
	 * @param taskPlaceNumber - int - The number of the place of this task in the actionbar from left to right (start with 0)
	 * @see MouseActionArea
	 **/
	public MouseActionArea_ActionbarTask(int taskPlaceNumber_, Color standardColor_, Color hoverColor_) {
		super(0, 0, 0, 0, "ActionbarTask_"+taskPlaceNumber_, null, ShowBorderType.ShowAlways, standardColor_, hoverColor_); //HOVERTEXT OVERWRITTEN
		
		this.taskPlaceNumber = taskPlaceNumber_;
		this.standardColor_Save = standardColor_;
		this.hoverColor_Save = hoverColor_;
		
		int realX = GameData.actionbar_X+(taskPlaceNumber*(GameData.actionbar_WidthPerTask+GameData.actionbar_SpaceBetweenTask) ); 
		int realY = GameData.actionbar_Y;
		int width = GameData.actionbar_WidthPerTask, height = GameData.actionbar_Height;
		
		xTL = realX; yTL = realY;
		xBR = realX+width; yBR = realY+height;
		
	}
	
	@Override
	public boolean isActiv() {
		
		if(GameData.clickedField != null) {
			Building building = GameHandler.getBuildingByCoordinates(GameData.clickedField.X, GameData.clickedField.Y);
			Troup troup = GameHandler.getTroupByCoordinates(GameData.clickedField.X, GameData.clickedField.Y);
			if(building != null) {
				//BUILDING ON THIS FIELD
				try{
					if(building.actionTasks.get(taskPlaceNumber) != null && building.playerID == ProfilData.thisClient.getID()) { //Only if it is yours
						//CURRENT BUILDING HAVE TASK AT THIS PLACE
						this.sourceType = SourceType.FromBuilding;					//!!! UPDATE REQUIERED FOR EVERY NEW TYPE ADDED
						return true;
					}
				}catch(IndexOutOfBoundsException error) { }
			}else if(troup != null) {
				//TROUP ON THIS FIELD
				try{
					if(troup.actionTasks.get(taskPlaceNumber) != null && troup.playerID == ProfilData.thisClient.getID()) { //Only if it is yours
						//CURRENT TROUP HAVE TASK AT THIS PLACE
						this.sourceType = SourceType.FromTroup;						//!!! UPDATE REQUIERED FOR EVERY NEW TYPE ADDED
						return true;
					}
				}catch(IndexOutOfBoundsException error) { }
			}else {
				//NO BUILDING OR TROUP ON THIS FIELD!
				this.sourceType = SourceType.FromField; 							//!!! UPDATE REQUIERED FOR EVERY NEW TYPE ADDED
			}
		}else {
			//NO FIELD CLICKED
			this.sourceType = null; //RESETT SOURCE TYPE
		}
		return false;	
	}
	
	@Override
	public void draw(Graphics g) {
		
		try{ //SOURCE TYPE WILL BE SET IF BUILDING OR TROUP IS FOUND BY isActive() METHODE
			//UPDATE HOVER MESSAGE
			switch(sourceType) {
			case FromBuilding:
				Building building = GameHandler.getBuildingByCoordinates(GameData.clickedField.X, GameData.clickedField.Y);
				if(building.activeTask != null) {
					//THERE IS AN ACTIVE TASK
					if(building.activeTask == building.actionTasks.get(taskPlaceNumber)) {
						//THIS AREA IS THE ACTIVE TASK - SPEZIAL COLOR AND DIFFERENT HOVER TEXT
						String[] newHoverText = {"This is the choosen/active task","It will be executed at the end of the round","Right click to remove from active task"};
						this.hoverText = newHoverText;
						this.standardColor = activeTaskColor; this.hoverColor = activeTaskColor;
					}else {
						//THIS AREA IS NOT THE ACTIVE TASK
						this.hoverText = building.actionTasks.get(taskPlaceNumber).getHoverMessage();
						this.standardColor = notActiveTaskColor; this.hoverColor = notActiveTaskColor;
					}
				}else {
					//THIS AREA IS NOT THE ACTIVE TASK
					this.hoverText = building.actionTasks.get(taskPlaceNumber).getHoverMessage();
					this.standardColor = standardColor_Save; this.hoverColor = hoverColor_Save;
				}
				break;
			case FromTroup:
				Troup troup = GameHandler.getTroupByCoordinates(GameData.clickedField.X, GameData.clickedField.Y);
				if(troup.activeTask != null) {
					//THERE IS AN ACTIVE TASK
					if(troup.activeTask == troup.actionTasks.get(taskPlaceNumber)) {
						//THIS AREA IS THE ACTIVE TASK - SPEZIAL COLOR AND DIFFERENT HOVER TEXT
						String[] newHoverText = {"This is the choosen/active task","It will be executed at the end of the round","Right click to remove from active task"};
						this.hoverText = newHoverText;
						this.standardColor = activeTaskColor; this.hoverColor = activeTaskColor;
					}else {
						//THIS AREA IS NOT THE ACTIVE TASK
						this.hoverText = troup.actionTasks.get(taskPlaceNumber).getHoverMessage();
						this.standardColor = notActiveTaskColor; this.hoverColor = notActiveTaskColor;
					}
				}else {
					//THIS AREA IS NOT THE ACTIVE TASK
					this.hoverText = troup.actionTasks.get(taskPlaceNumber).getHoverMessage();
					this.standardColor = standardColor_Save; this.hoverColor = hoverColor_Save;
				}
				break;
			case FromField:
				//Nothing happens without building and troup
				break;
			default:
				break;
			
			}
		}catch(NullPointerException | IndexOutOfBoundsException error) {}
		
		super.draw(g);
	}
	
	@Override
	public void performAction_LEFT_PRESS() {
		
		switch(sourceType) {
		case FromBuilding:
			GameData.clickedField.building.actionTasks.get(taskPlaceNumber).action_Left_Press();
			break;
		case FromTroup:
			GameData.clickedField.troup.actionTasks.get(taskPlaceNumber).action_Left_Press();
			break;
		case FromField:
			//NO TASKS
			break;
		default:
			break;
		
		}
		
		super.performAction_LEFT_PRESS();
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		switch(sourceType) {
		case FromBuilding:
			GameData.clickedField.building.actionTasks.get(taskPlaceNumber).action_Left_Release();
			break;
		case FromTroup:
			GameData.clickedField.troup.actionTasks.get(taskPlaceNumber).action_Left_Release();
			break;
		case FromField:
			//NO TASKS
			break;
		default:
			break;
		
		}
		
		super.performAction_LEFT_RELEASE();
	}
	
	@Override
	public void performAction_RIGHT_PRESS() {
		
		switch(sourceType) {
		case FromBuilding:
			GameData.clickedField.building.actionTasks.get(taskPlaceNumber).action_Right_Press();
			break;
		case FromTroup:
			GameData.clickedField.troup.actionTasks.get(taskPlaceNumber).action_Right_Press();
			break;
		case FromField:
			//NO TASKS
			break;
		default:
			break;
		
		}
		
		super.performAction_RIGHT_PRESS();
	}
	
	@Override
	public void performAction_RIGHT_RELEASE() {
		
		switch(sourceType) {
		case FromBuilding:
			GameData.clickedField.building.actionTasks.get(taskPlaceNumber).action_Right_Release();
			break;
		case FromTroup:
			GameData.clickedField.troup.actionTasks.get(taskPlaceNumber).action_Right_Release();
			break;
		case FromField:
			//NO TASKS
			break;
		default:
			break;
		
		}
		
		super.performAction_RIGHT_RELEASE();
	}
	
}
