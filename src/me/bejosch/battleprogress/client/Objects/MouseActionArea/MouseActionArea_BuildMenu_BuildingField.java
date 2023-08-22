package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.Game.EconomicData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Enum.ImportanceType;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Game.Handler.Game_RoundHandler;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Field.FieldMessage;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage;
import me.bejosch.battleprogress.client.Objects.Tasks.BuildMenuTasks.BuildMenuTask;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;

public class MouseActionArea_BuildMenu_BuildingField extends MouseActionArea {

	public BuildMenuTask connectedBuildingTask = null;
	public int xNumber, yNumber;
	
	public MouseActionArea_BuildMenu_BuildingField(int xNumber, int yNumber) {
		super(0, 0, 0, 0, "BuildMenu_BuildingField_"+xNumber+":"+yNumber, null, ShowBorderType.ShowAlways, Color.WHITE, Color.ORANGE);
		
		int X = GameData.buildMenu_X, Y = GameData.buildMenu_Y;
		int realX = X+GameData.buildMenu_border+((xNumber-1)*GameData.buildMenu_sizePerBuilding)+((xNumber-1)*GameData.buildmenu_spaceBetweenBuildings);
		int realY = Y + GameData.buildMenu_YdistanceToFirstBuilding + GameData.buildMenu_border+((yNumber-1)*GameData.buildMenu_sizePerBuilding)+((yNumber-1)*GameData.buildmenu_spaceBetweenBuildings);
		
		this.xNumber = xNumber;
		this.yNumber = yNumber;
		this.xTL = realX; 
		this.yTL = realY;
		this.xBR = realX + GameData.buildMenu_sizePerBuilding;
		this.yBR = realY + GameData.buildMenu_sizePerBuilding;
		
	}
	
	@Override
	public boolean isActiv() {
		if(GameData.buildMenu_activated == true) {
			connectedBuildingTask = getConnectedBuildingTask();
			if(connectedBuildingTask != null) {
				this.hoverText = connectedBuildingTask.getHoverMessage();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void draw(Graphics g) {
		
		//OVERWRITE STANDARD COLOR
		if(this.isActiv() == true) {
			
			if(this.connectedBuildingTask.isLocked()) {
				//LOCKED
				this.standardColor = Color.LIGHT_GRAY;
			}else if(GameData.currentActive_MAA_BuildingTask != null) {
				if(GameData.currentActive_MAA_BuildingTask.xNumber == this.xNumber && GameData.currentActive_MAA_BuildingTask.yNumber == this.yNumber && this.connectedBuildingTask.name.equalsIgnoreCase(GameData.currentActive_MAA_BuildingTask.connectedBuildingTask.name)) {
					//THIS IS THE CURRENT ACTIVE TASK
					this.standardColor = Color.GREEN;
				}else {
					this.standardColor = Color.RED;
				}
			}else {
				this.standardColor = Color.WHITE;
			}
			
		}
		
		super.draw(g);
		
	}
	
	@Override
	public void performAction_LEFT_PRESS() {
		
		if(Game_RoundHandler.blockedInput() || this.connectedBuildingTask.isLocked()) { return; }
		
		if(GameData.currentActive_MAA_BuildingTask == null) {
			if(GameHandler.hasEnoughtMaterial(this.connectedBuildingTask.cost) == true) {
				//ENOUGHT MASS - START DRAG AND DROP
				setActiveTask();
			}else {
				List<String> message = new ArrayList<String>();
				message.add("You have not enought materials for this build");
				message.add( (this.connectedBuildingTask.cost-EconomicData.materialAmount)+" materials are missing   ( "+EconomicData.materialAmount+" / "+this.connectedBuildingTask.cost+" )");
				new InfoMessage(message, ImportanceType.HIGH, true);
			}
		}
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		if(Game_RoundHandler.blockedInput() || this.connectedBuildingTask.isLocked()) { return; }
		
		if(GameData.currentActive_MAA_BuildingTask != null && GameData.dragAndDropInputActive_BuildingMenu == true) {
			
			if(GameData.hoveredField != null) {
				Field targetField = GameData.hoveredField;
				//CHECK FIELD FOR USE
				if(checkFieldForBuildUse(targetField) == true) {
					//SET TARGET COORDINATE
					GameData.currentActive_MAA_BuildingTask.connectedBuildingTask.targetCoordinate = new FieldCoordinates(targetField);
					GameData.dragAndDropInputActive_BuildingMenu = false;
					return;
				}
			}
			//MISSING - SO CANCLE
			removeActiveTask();
		}
	}
	
	@Override
	public void performAction_RIGHT_RELEASE() {
		
		if(Game_RoundHandler.blockedInput() || this.connectedBuildingTask.isLocked()) { return; }
		
		if(GameData.dragAndDropInputActive_BuildingMenu == true) {
			//IS DRAG AND DROP CANCLE
			removeActiveTask();
		}else {
			if(GameData.currentActive_MAA_BuildingTask != null) {
				if(GameData.currentActive_MAA_BuildingTask.xNumber == this.xNumber && GameData.currentActive_MAA_BuildingTask.yNumber == this.yNumber && this.connectedBuildingTask.name.equalsIgnoreCase(GameData.currentActive_MAA_BuildingTask.connectedBuildingTask.name)) {
					//THIS IS THE CURRENT ACTIVE TASK
					//SO CANCLE
					removeActiveTask();
				}
			}
		}
		
	}
	
	public void setActiveTask() {
		GameData.currentActive_MAA_BuildingTask = this;
		GameData.currentActive_MAA_BuildingTask.connectedBuildingTask.targetCoordinate = null;
		GameData.dragAndDropInputActive_BuildingMenu = true;
		
		//REMOVE MASS
		EconomicData.materialAmount -= GameData.currentActive_MAA_BuildingTask.connectedBuildingTask.cost;
	}
	public void removeActiveTask() {
		//RE ADD MASS
		EconomicData.materialAmount += GameData.currentActive_MAA_BuildingTask.connectedBuildingTask.cost;
		
		GameData.currentActive_MAA_BuildingTask.connectedBuildingTask.targetCoordinate = null;
		GameData.currentActive_MAA_BuildingTask = null;
		GameData.dragAndDropInputActive_BuildingMenu = false;
	}
	
	public boolean checkFieldForBuildUse(Field checkedField) {
		
		//VISIBLE
		if(checkedField.visible == false) {
			new FieldMessage("Not visible", checkedField.X, checkedField.Y, 3);
			return false;
		}
		//BUILD AREA
		if(GameHandler.fieldIsInBuildArea(checkedField) == false) {
			new FieldMessage("Out of build area", checkedField.X, checkedField.Y, 3);
			return false;
		}
		//EMPTY
		if(checkedField.building != null || checkedField.troup != null) {
			new FieldMessage("No space", checkedField.X, checkedField.Y, 3);
			return false;
		}
		//BLOCKED
		if(targetFieldIsBlocked(checkedField) == true) {
			new FieldMessage("Already used", checkedField.X, checkedField.Y, 3);
			return false;
		}
		//FIELD TYPE
		if(this.connectedBuildingTask.name.equalsIgnoreCase("Mine")) {
			//MINE / RESSOURCE BUILDING
			if(checkedField.type != FieldType.Ressource) {
				//ONLY ON RESSOURCE BUILD ABLE
				new FieldMessage("Wrong terrain", checkedField.X, checkedField.Y, 3);
				return false;
			}
		}else {
			//NON MINE
			if(checkedField.type != FieldType.Gras && checkedField.type != FieldType.Path) {
				//ONLY GRASS AND PATH BUILD ABLE
				new FieldMessage("Wrong terrain", checkedField.X, checkedField.Y, 3);
				return false;
			}
		}
		
		return true;
		
	}
	
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
		return false;
	}
	
	public BuildMenuTask getTaskByNumbers(int checkX, int checkY) {
		
		int Xcords = 1, Ycords = 1;
		
		for(BuildMenuTask task : GameData.buildMenu_displayedBuildings) {
			
			if(checkX == Xcords && checkY == Ycords) {
				//Found
				return task;
			}
			
			if(Xcords == 1) {
				Xcords = 2;
			}else if(Xcords == 2) {
				Xcords = 1;
				Ycords++;
			}
			
		}
		return null;
	}
	
	public BuildMenuTask getConnectedBuildingTask() {
		return getTaskByNumbers(xNumber, yNumber);
	}
	
}
