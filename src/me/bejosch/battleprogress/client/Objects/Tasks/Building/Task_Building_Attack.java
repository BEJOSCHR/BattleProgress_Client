package me.bejosch.battleprogress.client.Objects.Tasks.Building;

import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.Game.EconomicData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.ImportanceType;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Field.FieldMessage;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage_Located;
import me.bejosch.battleprogress.client.PathFinding.PathFinding_Algorithmus;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Task_Building_Attack extends Task_Building{

	public Task_Building_Attack(Building connectedBuilding) {
		super(connectedBuilding, Images.taskIcon_Building_Attack, "Attack", 11, 3, null);
		String[] hoverText = {"This task attacks an enemy troup or building","All tasks are executed at the end of the round"};
		this.hoverMessage = hoverText;
	}
	
	@Override
	public void action_Left_Press() {
		
		if(this.building.activeTask != null) { this.building.activeTask.action_Right_Release(); }
			
		if(GameHandler.hasEnoughtEnergy(this.building.energyCostPerAction) == true) {
			//ENOUGHT ENERGY
			this.setToActiveTask();
			GameData.dragAndDropTaskInputActive = true;
		}else {
			List<String> message = new ArrayList<String>();
			message.add("You have not enought energy for this action!");
			message.add( (this.building.energyCostPerAction-EconomicData.energyAmount)+" energy is missing   ( "+EconomicData.energyAmount+" / "+this.building.energyCostPerAction+" )");
			new InfoMessage_Located(message, ImportanceType.HIGH, this.building.connectedField.X, this.building.connectedField.Y, true);
		}
		
	}
	@Override
	public void action_Left_Release() {
		
		if(GameData.dragAndDropTaskInputActive == true) {
			GameData.dragAndDropTaskInputActive = false; //RESET
			Field targetField = GameData.hoveredField;
			if(targetField != null) {
				if(this.building.fieldIsIn_SHOT_Range(targetField)) {
					//IN RANGE
					
					EconomicData.energyAmount -= this.building.energyCostPerAction; //REMOVE COST
					this.targetCoordinates = new FieldCoordinates(targetField); //SET TARGET FIELD
					this.targetPath = new PathFinding_Algorithmus(new FieldCoordinates(this.building.connectedField), new FieldCoordinates(targetField), true).getPath(this.building.viewDistance, true, true);
					
				}else {
					if(GameData.gameMap_FieldList[targetField.X][targetField.Y].visible == false) {
						//NOT VISIBLE
						new FieldMessage("Not visible", targetField.X, targetField.Y, 3);
					}else if(GameData.gameMap_FieldList[targetField.X][targetField.Y].building == null && GameData.gameMap_FieldList[targetField.X][targetField.Y].troup == null) {
						//NO TARGET
						new FieldMessage("No target", targetField.X, targetField.Y, 3);
					}else {
						//OUT OF RANGE
						new FieldMessage("Out of range", targetField.X, targetField.Y, 3);
					}
					this.removeFromActiveTask();
				}
			}else {
				//MISSING HOVERED FIELD (TARGET FIELD)
				new InfoMessage_Located("Not a valid target field!", ImportanceType.LOW, this.building.connectedField.X, this.building.connectedField.Y, true);
				this.removeFromActiveTask();
			}
		}
		
	}
	
	@Override
	public void action_Right_Release() {
		
		if(this.isActiveTask == true) {
			//RE ADD COST
			EconomicData.energyAmount += this.building.energyCostPerAction;
		}
		
		super.action_Right_Release();
		
	}
	
}
