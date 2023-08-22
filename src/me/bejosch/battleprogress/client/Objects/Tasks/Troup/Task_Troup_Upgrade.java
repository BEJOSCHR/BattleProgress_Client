package me.bejosch.battleprogress.client.Objects.Tasks.Troup;

import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.Game.EconomicData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.ImportanceType;
import me.bejosch.battleprogress.client.Enum.UpgradeType;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Game.Handler.Game_ResearchHandler;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Field.FieldMessage;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage_Located;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;
import me.bejosch.battleprogress.client.PathFinding.PathFinding_Algorithmus;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Task_Troup_Upgrade extends Task_Troup {
	
	public String upgradeTroupName = null;
	public int upgradeTroupCost = 0;
	
	public UpgradeType researchDependency = null;
	
	public Task_Troup_Upgrade(Troup connectedTroup, String upgradeTroupName, int upgradeTroupCost, UpgradeType researchDependency_) {
		super(connectedTroup, Images.taskIcon_Troup_Upgrade, "Upgrade", 11, true, false, 3, null);
		
		String[] hoverText = {"This task upgrades this and another troup of the same type to a better one","All tasks are executed at the end of the round"};
		this.hoverMessage = hoverText;
		this.upgradeTroupName = upgradeTroupName;
		this.upgradeTroupCost = upgradeTroupCost;
		
		this.researchDependency = researchDependency_;
		
	}
	
//==========================================================================================================
	/**
	 * Checks foe the research criteria. If no limit (dependency=null) it is never locked, if not null it is checked for the researched status {@link Game_ResearchHandler}
	 * @return false if no dependency is set or the upgrade has been researched, true if dependency is not researched
	 */
	public boolean isLocked() {
		
		if(this.researchDependency == null) {
			return false;
		}else {
			return !Game_ResearchHandler.hasResearched(this.researchDependency);
		}
		
	}
	
	@Override
	public void action_Left_Press() {
		
		if(this.isLocked()) { return; }
		
		if(this.troup.activeTask != null) { this.troup.activeTask.action_Right_Release(); }
		
		if(GameHandler.hasEnoughtMaterial(this.upgradeTroupCost) == true) {
			//ENOUGHT MASS
			this.setToActiveTask();
			GameData.dragAndDropTaskInputActive = true;
		}else {
			List<String> message = new ArrayList<String>();
			message.add("You have not enought materials for this upgrade!");
			message.add( (this.upgradeTroupCost-EconomicData.materialAmount)+" materials are missing   ( "+EconomicData.materialAmount+" / "+this.upgradeTroupCost+" )");
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
					if(this.troup.fieldIsIn_UPGRADE_Range(targetField)) { //UPGRADE RANGE ONLY MARKS TROUPS OF THIS PLAYER WHICH HAVE THE SAME NAME AS THIS TROUP
						//IN RANGE
						if(this.targetFieldIsBlocked(targetField) == false) {
							//NOT BLOCKED -> All fine
							
							if(targetField.troup.activeTask == null) {
								//TARGET TROUP HAS NOT ALLREADY A TASK
								
								EconomicData.materialAmount -= this.upgradeTroupCost; //REMOVE COST
								this.targetCoordinates = new FieldCoordinates(targetField); //SET TARGET FIELD
								this.targetPath = new PathFinding_Algorithmus(new FieldCoordinates(this.troup.connectedField), new FieldCoordinates(targetField), true).getPath(this.troup.actionRange, true, this.troup.canFly);
								GameData.clickedField = null; 
								
								targetField.troup.targetUpgradePosition = new FieldCoordinates(this.troup.connectedField);
								
							}else {
								//TARGET TROUP HAS ALREADY A TASK
								new FieldMessage("Has already a task", targetField.X, targetField.Y, 3);
								this.removeFromActiveTask();
							}
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
						}else if(GameData.gameMap_FieldList[targetField.X][targetField.Y].troup == null) {
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
				new InfoMessage_Located("You need to drag and drop the task to a target troup", ImportanceType.LOW, this.troup.connectedField.X, this.troup.connectedField.Y, true);
				this.removeFromActiveTask();
			}
		}
		
	}
	
	@Override
	public void action_Right_Release() {
		
		if(this.isActiveTask == true) {
			if(GameData.dragAndDropTaskInputActive == false) {
				//NO DRAG AND DROP
				//RE ADD COST
				EconomicData.materialAmount += this.upgradeTroupCost;
				//REMOVE UPGRADE BLOCK ON OTHER TROUP
				this.targetCoordinates.getConnectedField().troup.targetUpgradePosition = null;
			}
			//REMOVE
			this.removeFromActiveTask();
			GameData.dragAndDropTaskInputActive = false;
		}
		
	}
	
}
