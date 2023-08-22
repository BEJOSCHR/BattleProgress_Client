package me.bejosch.battleprogress.client.Objects.Tasks.Troup;

import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.Game.EconomicData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.ImportanceType;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Field.FieldMessage;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage_Located;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;
import me.bejosch.battleprogress.client.PathFinding.PathFinding_Algorithmus;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Task_Troup_Repair extends Task_Troup{

	public Task_Troup_Repair(Troup connectedTroup) {
		super(connectedTroup, Images.taskIcon_Troup_Repair, "Repair", 11, false, true, 3, null);
		String[] hoverText = {"This task repairs an allied building","All tasks are executed at the end of the round"};
		this.hoverMessage = hoverText;
	}
	
	@Override
	public void action_Left_Press() {
		
		if(this.troup.activeTask != null) { this.troup.activeTask.action_Right_Release(); }
		
		if(GameHandler.hasEnoughtEnergy(this.troup.energyCostPerAction) == true) {
			//ENOUGHT ENERGY
			this.setToActiveTask();
			GameData.dragAndDropTaskInputActive = true;
		}else {
			List<String> message = new ArrayList<String>();
			message.add("You have not enought energy for this action!");
			message.add( (this.troup.energyCostPerAction-EconomicData.energyAmount)+" energy is missing   ( "+EconomicData.energyAmount+" / "+this.troup.energyCostPerAction+" )");
			new InfoMessage_Located(message, ImportanceType.HIGH, this.troup.connectedField.X, this.troup.connectedField.Y, true);
		}
		
	}
	
	@Override
	public void action_Left_Release() {
		
		if(GameData.dragAndDropTaskInputActive == true) {
			GameData.dragAndDropTaskInputActive = false; //RESETT
			Field targetField = GameData.hoveredField;
			if(targetField != null) {
				if(this.troup.targetUpgradePosition == null) {
					//NOT TARGETED AS AN UPGRADE PARTNER
					if(this.troup.fieldIsIn_REPAIR_Range(targetField)) {
						//IN RANGE
	
						EconomicData.energyAmount -= this.troup.energyCostPerAction; //REMOVE COST
						this.targetCoordinates = new FieldCoordinates(targetField); //SET TARGET FIELD
						this.targetPath = new PathFinding_Algorithmus(new FieldCoordinates(this.troup.connectedField), new FieldCoordinates(targetField), true).getPath(this.troup.viewDistance, true, this.troup.canFly);
						
					}else {
						//NOT IN RANGE
						if(GameData.gameMap_FieldList[targetField.X][targetField.Y].visible == false) {
							//NOT VISIBLE
							new FieldMessage("Not visible", targetField.X, targetField.Y, 3);
						}else if(GameData.gameMap_FieldList[targetField.X][targetField.Y].building == null) {
							//NO TARGET
							new FieldMessage("No target", targetField.X, targetField.Y, 3);
						}else {
							//OUT OF RANGE
							new FieldMessage("Out of range", targetField.X, targetField.Y, 3);
						}
						this.removeFromActiveTask();
					}
				}else {
					//TARGET OF AN UPGRADE
					new FieldMessage("Blocked by an upgrade", this.troup.targetUpgradePosition.X, this.troup.targetUpgradePosition.Y, 3);
//					new FieldMessage("Blocked by an upgrade", targetField.X, targetField.Y, 3);
					this.removeFromActiveTask();
				}
			}else {
				//MISSING HOVERED FIELD (TARGET FIELD)
				new InfoMessage_Located("You need to drag and drop the task to a target building", ImportanceType.LOW, this.troup.connectedField.X, this.troup.connectedField.Y, true);
				this.removeFromActiveTask();
			}
		}
		
	}
	
	@Override
	public void action_Right_Release() {
		
		if(this.isActiveTask == true) {
			//RE ADD COST
			EconomicData.energyAmount += this.troup.energyCostPerAction;
		}
		
		super.action_Right_Release();
		
	}
	
}
