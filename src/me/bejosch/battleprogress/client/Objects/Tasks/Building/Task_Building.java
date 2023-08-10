package me.bejosch.battleprogress.client.Objects.Tasks.Building;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Tasks.Task;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;
import me.bejosch.battleprogress.client.PathFinding.Path;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Task_Building implements Task{

	public Building building = null;
	public Image img = null;
	
	public FieldCoordinates targetCoordinates = null;
	public Path targetPath = null;
	
	public String title = null;
	public int textSize = 0; //MIN 8
	public int xMovingPerLetter = 0;
	public boolean isActiveTask = false;
	
	public String[] hoverMessage = null;
	
//==========================================================================================================
	/**
	 * The task category of all Building Tasks
	 * @param connectedBuilding - {@link Building} - The building, this task is revered to
	 * @param img_ - {@link Image} - The image which is displayed to represent this task
	 * @param title_ - String - The title of the task
	 * @param textSize_ - int - The textSize of the title
	 * @param xMovingPerLetter_ - int - The value which is multiplied per letter of the title to move it to the left for a center focus
	 * @param hoverMessage_ - String[] - The message/description which is shown on hover
	 */
	public Task_Building(Building connectedBuilding, Image img_, String title_, int textSize_, int xMovingPerLetter_, String[] hoverMessage_) {
		
		this.building = connectedBuilding;
		this.img = img_;
		this.title = title_;
		this.textSize = textSize_;
		this.xMovingPerLetter = xMovingPerLetter_;
		this.hoverMessage = hoverMessage_;
		
	}
	
	@Override
	public void action_Left_Press() {
		
	}



	@Override
	public void action_Left_Release() {
		
	}



	@Override
	public void action_Right_Press() {
		
	}



	@Override
	public void action_Right_Release() {
		
		if(this.isActiveTask == true) {
			this.removeFromActiveTask();
			GameData.dragAndDropTaskInputActive = false;
		}
		
	}
	
	@Override
	public void draw_Field(Graphics g, int fieldX, int fieldY) {
		
	}

	@Override
	public void draw_ActionBar(Graphics g, int displayedTaskNumber) {
		
		int realX = GameData.actionbar_X+(displayedTaskNumber*(GameData.actionbar_WidthPerTask+GameData.actionbar_SpaceBetweenTask) ); int realY = GameData.actionbar_Y;
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(realX, realY, GameData.actionbar_WidthPerTask, GameData.actionbar_Height);
		
		//CHECK IF RESEARCHED
		if(this instanceof Task_Building_Produce) {
			Task_Building_Produce prodTask = (Task_Building_Produce) this;
			if(prodTask.isLocked()) {
				g.drawImage(Images.noResearchLock, realX+GameData.task_lockImageBorder, realY+GameData.task_lockImageBorder, null);
				return;
			}
		}
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, textSize));
		g.drawString(title, realX+(GameData.actionbar_WidthPerTask/2)-(title.length()*xMovingPerLetter), realY+17);
		g.drawImage(img, realX+( (GameData.actionbar_WidthPerTask-GameData.actionbar_ImgSize) /2), realY+26, null);
		
		if(this instanceof Task_Building_Produce) {
			//PRODUCE COST DISPLAY
			((Task_Building_Produce) this).draw_Actionbar_producedTroupName(g, realX, realY);
			draw_Actionbar_extraCost(g, realX, realY, ((Task_Building_Produce) this).troupProduceCost, true);
		}else if(this instanceof Task_Building_Attack || this instanceof Task_Building_Heal || this instanceof Task_Building_Repair) {
			draw_Actionbar_extraCost(g, realX, realY, this.building.energyCostPerAction, false);
		}
		
	}
	
	public void draw_Actionbar_extraCost(Graphics g, int X, int Y, int cost, boolean materialCost) {
		
		Color background, foreground;
		
		if(materialCost == true) {
			//MATERIAL
			background = Color.YELLOW;
			if(GameHandler.hasEnoughtMaterial(cost) == true) {
				//ENOGHT MASS
				foreground = Color.BLACK;
			}else {
				//NOT ENOGHT
				foreground = Color.RED;
			}
		}else {
			//ENERGY
			background = new Color(102, 204, 255);
			if(GameHandler.hasEnoughtEnergy(cost) == true) {
				//ENOGHT Energy
				foreground = Color.BLACK;
			}else {
				//NOT ENOGHT
				foreground = Color.RED;
			}
		}
		
		int width, height = 15 + 2;
		if(cost < 10) {
			width = 10;
		}else if(cost < 100) {
			width = 20;
		}else if(cost < 1000) {
			width = 30;
		}else {
			width = 40;
		}
		
		g.setColor(background);
		g.fillRect(X + GameData.actionbar_WidthPerTask - width, Y + GameData.actionbar_Height - height, width, height);
		
		g.setColor(foreground);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString(""+cost, X + GameData.actionbar_WidthPerTask - width + 3 , Y + GameData.actionbar_Height - 2);
		
	}
	
	public void draw_ActiveTask(Graphics g) {
		
		int realX = GameData.activeTask_X; int realY = GameData.activeTask_Y;
		//BACKGOUND
		g.setColor(Color.DARK_GRAY);
		g.fillRect(realX, realY, GameData.activeTask_width, GameData.activeTask_height);
		//PREFIX
		g.setColor(GameData.activeTask_textColor);
		g.setFont(new Font("Arial", Font.BOLD, GameData.activeTask_textSize));
		g.drawString("Active task:", realX+GameData.actionbar_backgroundOverlappingSize, realY+20);
		g.drawString(title, realX+GameData.actionbar_backgroundOverlappingSize+110, realY+20);
		
	}

	@Override
	public boolean targetFieldIsBlocked(Field targetField) {
		for(Building building : Funktions.getBuildingListByPlayerID(ProfilData.thisClient.getID())) { //ALL BUILDINGS OF THIS CLIENT
			if(building.activeTask != null) {
				if(building.activeTask.targetCoordinates != null) {
					if(building.activeTask.targetCoordinates.compareToOtherField(targetField) == true) {
						//SAME COORDINATES AS TARGET! -> is blocked
						return true;
					}
				}
			}
		}
		for(Troup troup : Funktions.getTroupListByPlayerID(ProfilData.thisClient.getID())) { //ALL TROUPS OF THIS CLIENT
			if(troup.activeTask != null) {
				if(troup.activeTask.targetCoordinates != null) {
					if(troup.activeTask.targetCoordinates.compareToOtherField(targetField) == true) {
						//SAME COORDINATES AS TARGET! -> is blocked
						return true;
					}
				}
			}
		}
		if(GameData.currentActive_MAA_BuildingTask != null) { //BUILD MENU TASK
			if(GameData.currentActive_MAA_BuildingTask.connectedBuildingTask != null) {
				if(GameData.currentActive_MAA_BuildingTask.connectedBuildingTask.targetCoordinate.compareToOtherField(targetField) == true) {
					//SAME COORDINATES AS TARGET! -> is blocked
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void setToActiveTask() {
		isActiveTask = true;
		this.targetCoordinates = null;
		this.building.activeTask = this;
	}

	@Override
	public void removeFromActiveTask() {
		isActiveTask = false;
		this.building.activeTask = null;
	}

	@Override
	public boolean isActiveTask() {
		return isActiveTask;
	}

	@Override
	public String[] getHoverMessage() {
		if(this instanceof Task_Building_Produce && ((Task_Building_Produce) this).isLocked()) {
			String[] newHovermessage = {"You need to unlock the "+((Task_Building_Produce) this).troupName+"!"};
			return newHovermessage;
		}else {
			return hoverMessage;
		}
	}	
	
}
