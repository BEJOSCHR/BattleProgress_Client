package me.bejosch.battleprogress.client.Objects.Tasks.Troup;

import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.ImportanceType;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Field.FieldMessage;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage_Located;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;
import me.bejosch.battleprogress.client.PathFinding.PathFinding_Algorithmus;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Task_Troup_Move extends Task_Troup{

	public Task_Troup_Move(Troup connectedTroup) {
		super(connectedTroup, Images.taskIcon_Troup_Move, "Move", 11, 3, null);
		String[] hoverText = {"This task moves the troup","All tasks are executed at the end of the round"};
		this.hoverMessage = hoverText;
	}
	
	@Override
	public void action_Left_Press() {
		
		if(this.troup.activeTask == null) {
			//ONLY SET TASK IF THERE IS NO ACTIVE TASK YET
			this.setToActiveTask();
			GameData.dragAndDropTaskInputActive = true;
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
					if(this.troup.fieldIsIn_MOVE_Range(targetField)) {
						//IN RANGE
						if(this.targetFieldIsBlocked(targetField) == false) {
							//NOT BLOCKED -> All fine
							
							this.targetCoordinates = new FieldCoordinates(targetField); //SET TARGET FIELD
							this.targetPath = new PathFinding_Algorithmus(new FieldCoordinates(this.troup.connectedField), new FieldCoordinates(targetField), true).getPath(this.troup.viewDistance, false, this.troup.canFly);
							
						}else {
							//BLOCKED BY OTHER TASK
							new FieldMessage("Already used", targetField.X, targetField.Y, 3);
							this.removeFromActiveTask();
						}
					}else {
						//NOT IN RANGE
						if(GameData.gameMap_FieldList[targetField.X][targetField.Y].visible == false) {
							//NOT VISIBLE
							new FieldMessage("Not visible", targetField.X, targetField.Y, 3);
						}else if(GameData.gameMap_FieldList[targetField.X][targetField.Y].building != null || GameData.gameMap_FieldList[targetField.X][targetField.Y].troup != null) {
							//NO SPACE
							new FieldMessage("No space", targetField.X, targetField.Y, 3);
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
				new InfoMessage_Located("You need to drag and drop the task to the target field", ImportanceType.LOW, this.troup.connectedField.X, this.troup.connectedField.Y, true);
				this.removeFromActiveTask();
			}
		}
		
	}
	
}
