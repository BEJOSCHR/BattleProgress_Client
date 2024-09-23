package me.bejosch.battleprogress.client.Objects.Tasks.Building;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.Game.EconomicData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Enum.ImportanceType;
import me.bejosch.battleprogress.client.Enum.TroupType;
import me.bejosch.battleprogress.client.Enum.UpgradeType;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Game.Handler.Game_ResearchHandler;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Field.FieldMessage;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage_Located;
import me.bejosch.battleprogress.client.PathFinding.PathFinding_Algorithmus;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Task_Building_Produce extends Task_Building{

	public String troupName = null;
	public TroupType troupType = null;
	public int textSize = 10;
	public int troupProduceCost = 999;
	
	public UpgradeType researchDependency = null;
	
//==========================================================================================================
	/**
	 * Creates a new PRODUCE ExecuteTask, which is used for executing the given task for every client in the game
	 * @param connectedBuilding - {@link Building} - The building which is executing this production
	 * @param troupType_ - {@link TroupType} - The type of the troup which should be produced
	 * @param troupName_ - String - The name of the troup which should be produced
	 * @param troupProduceCost_ - int - The cost of the troup to be produced
	 */
	public Task_Building_Produce(Building connectedBuilding, TroupType troupType_, String troupName_, int troupProduceCost_, UpgradeType researchDependency_) {
		super(connectedBuilding, Images.taskIcon_Building_Produce, "Produce", 11, true, false, 3, null);
		
		String[] hoverText = {"This task produces a new troup","Troup: "+troupName_,"Cost: "+troupProduceCost_,"All tasks are executed at the end of the round"};
		this.hoverMessage = hoverText;
		this.troupName = troupName_;
		this.troupType = troupType_;
		this.troupProduceCost = troupProduceCost_;
		
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
		
		if(this.building.activeTask != null) { this.building.activeTask.action_Right_Release(); }
		
		if(GameHandler.hasEnoughtMaterial(troupProduceCost) == true) {
			//ENOUGHT MASS
			this.setToActiveTask();
			GameData.dragAndDropTaskInputActive = true;
		}else {
			List<String> message = new ArrayList<String>();
			message.add("You have not enought materials for this production!");
			message.add( (this.troupProduceCost-EconomicData.materialAmount)+" materials are missing   ( "+EconomicData.materialAmount+" / "+this.troupProduceCost+" )");
			new InfoMessage_Located(message, ImportanceType.HIGH, this.building.connectedField.X, this.building.connectedField.Y, true);
		}
		
	}
	@Override
	public void action_Left_Release() {
		
		if(GameData.dragAndDropTaskInputActive == true) {
			GameData.dragAndDropTaskInputActive = false; //RESET
			Field targetField = GameData.hoveredField;
			if(targetField != null) {
				
				//SPECIAL COMMANDER PRODUCE (DIFFERENCE: NO BUILD RANGE LIMIT)
				if(this.troupName.equalsIgnoreCase("Commander")) {
					if(GameData.gameMap_FieldList[targetField.X][targetField.Y].visible == false) {
						//NOT VISIBLE
						new FieldMessage("Not visible", targetField.X, targetField.Y, 3);
						this.removeFromActiveTask();
					}else if(GameHandler.fieldIsInBuildArea(targetField) == false) {
						//NOT IN BUILD AREA
						new FieldMessage("Out of build area", targetField.X, targetField.Y, 3);
						this.removeFromActiveTask();
					}else if(GameData.gameMap_FieldList[targetField.X][targetField.Y].building != null || GameData.gameMap_FieldList[targetField.X][targetField.Y].troup != null) {
						//NO SPACE
						new FieldMessage("No space", targetField.X, targetField.Y, 3);
						this.removeFromActiveTask();
					}else if(checkFieldFor_PRODUCE_Use(targetField) == false || targetField.type == FieldType.Ressource || targetField.type == FieldType.Consumed) {
						//WRONG TERRAIN
						new FieldMessage("Wrong terrain", targetField.X, targetField.Y, 3);
						this.removeFromActiveTask();
					}else {
						if(this.targetFieldIsBlocked(targetField) == false) {
							//NOT BLOCKED -> All fine
							
							EconomicData.materialAmount -= this.troupProduceCost; //REMOVE COST
							this.targetCoordinates = new FieldCoordinates(targetField); //SET TARGET FIELD
							GameData.clickedField = null; 
							if(troupType == TroupType.LAND) {
								//NO WATER
								this.targetPath = new PathFinding_Algorithmus(new FieldCoordinates(this.building.connectedField), new FieldCoordinates(targetField), true).getPath(this.building.viewDistance, false, false);
							}else if(troupType == TroupType.AIR) {
								//WITH WATER
								this.targetPath = new PathFinding_Algorithmus(new FieldCoordinates(this.building.connectedField), new FieldCoordinates(targetField), true).getPath(this.building.viewDistance, false, true);
							}
							
						}else {
							//BLOCKED BY OTHER TASK
							new FieldMessage("Already used", targetField.X, targetField.Y, 3);
							this.removeFromActiveTask();
						}
					}
				}else {
					//NO COMMANDER
					if(this.building.fieldIsIn_PRODUCE_Range(targetField) && checkFieldFor_PRODUCE_Use(targetField) == true) {
						//IN RANGE
						if(this.targetFieldIsBlocked(targetField) == false) {
							//NOT BLOCKED -> All fine
							
							EconomicData.materialAmount -= this.troupProduceCost; //REMOVE COST
							this.targetCoordinates = new FieldCoordinates(targetField); //SET TARGET FIELD
							if(troupType == TroupType.LAND) {
								//NO WATER
								this.targetPath = new PathFinding_Algorithmus(new FieldCoordinates(this.building.connectedField), new FieldCoordinates(targetField), true).getPath(this.building.viewDistance, false, false);
							}else if(troupType == TroupType.AIR) {
								//WITH WATER
								this.targetPath = new PathFinding_Algorithmus(new FieldCoordinates(this.building.connectedField), new FieldCoordinates(targetField), true).getPath(this.building.viewDistance, false, true);
							}
							
						}else {
							//BLOCKED BY OTHER TASK
							new FieldMessage("Already used", targetField.X, targetField.Y, 3);
							this.removeFromActiveTask();
						}
					}else {
						//NOT IN RANGE OR NO SPACE OR WRONG FIELDTYPE
						if(GameData.gameMap_FieldList[targetField.X][targetField.Y].visible == false) {
							//NOT VISIBLE
							new FieldMessage("Not visible", targetField.X, targetField.Y, 3);
						}else if(GameData.gameMap_FieldList[targetField.X][targetField.Y].building != null || GameData.gameMap_FieldList[targetField.X][targetField.Y].troup != null) {
							//NO SPACE
							new FieldMessage("No space", targetField.X, targetField.Y, 3);
						}else if(checkFieldFor_PRODUCE_Use(targetField) == false) {
							//WRONG TERRAIN
							new FieldMessage("Wrong terrain", targetField.X, targetField.Y, 3);
						}else {
							//OUT OF RANGE
							new FieldMessage("Out of range", targetField.X, targetField.Y, 3);
						}
						this.removeFromActiveTask();
					}
					
				}
				
			}else {
				//MISSING HOVERED FIELD (TARGET FIELD)
				new InfoMessage_Located("You need to drag and drop the task to the target field", ImportanceType.LOW, this.building.connectedField.X, this.building.connectedField.Y, true);
				this.removeFromActiveTask();
			}
		}
		
	}
	@Override
	public void action_Right_Release() {
		
		if(this.isActiveTask == true && this.targetCoordinates != null) {
			if(GameData.dragAndDropTaskInputActive == false) {
				//NO DRAG AND DROP
				//RE ADD COST
				EconomicData.materialAmount += this.troupProduceCost;
			}
		}
		
		super.action_Right_Release();
	}
	
//==========================================================================================================
	/**
	 * Check a field for a type to PRODUCE on it
	 * @param check - {@link Field} - The field which should be checked
	 * @return boolean - true if the type is okay to produce troup on it, false if not
	 */
	public boolean checkFieldFor_PRODUCE_Use(Field check) {
		
		FieldType type = check.type;
		if(troupType == TroupType.LAND) {
			//NO WATER
			if(type == FieldType.Flatland || type == FieldType.Path || type == FieldType.Ressource || type == FieldType.Consumed) {
				return true;
			}
		}else if(troupType == TroupType.AIR) {
			//WITH WATER
			if(type == FieldType.Flatland || type == FieldType.Path || type == FieldType.Ressource || type == FieldType.Consumed || type == FieldType.Ocean) {
				return true;
			}
		}
		return false;
		
	}
	
//==========================================================================================================
	/**
	 * Draws the name of the troup which will be produced
	 * @param X - int - The top left x-coordinate of the actionbar field
	 * @param Y - int - The top left y-coordinate of the actionbar field
	 */
	public void draw_Actionbar_producedTroupName(Graphics g, int X, int Y) {
		
		if(this.isActiveTask == true) {
			//ACTIVE TASK
			g.setColor(Color.RED);
		}else {
			//NOT ACTIVE TASK
			g.setColor(Color.BLACK);
		}
		g.setFont(new Font("Arial", Font.BOLD, textSize));
		g.drawString(troupName, X+5, Y+26);
		
	}
	
}
