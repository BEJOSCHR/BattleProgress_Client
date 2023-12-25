package me.bejosch.battleprogress.client.Objects.Buildings;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.CreateMapData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Enum.TroupType;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Attack;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Heal;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Produce;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Repair;
import me.bejosch.battleprogress.client.PathFinding.Path;
import me.bejosch.battleprogress.client.PathFinding.PathFinding_Algorithmus;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Building {
	
	public Image img = Images.building_missingTexture;
	
	public int playerID;
	public Field connectedField;
	
	public List<FieldCoordinates> viewAbleFields = new ArrayList<FieldCoordinates>();
	public List<FieldCoordinates> shotAbleFields = new ArrayList<FieldCoordinates>();
	public List<FieldCoordinates> repairAbleFields = new ArrayList<FieldCoordinates>();
	public List<FieldCoordinates> healAbleFields = new ArrayList<FieldCoordinates>();
	public List<FieldCoordinates> produceAbleFields = new ArrayList<FieldCoordinates>();
	
	public List<Task_Building> actionTasks = new ArrayList<Task_Building>();
	public Task_Building activeTask = null;
	
	public String name = "missingName";
	public int textSize_nameField = 12;
	public int textSize_nameActionbar = 12;
	public String[] hoverDescription = {"missingDescription"};
	
	public int viewDistance = 1; //The distance the building can "view"
	public int actionRange = 0; //The range how far the building can perform actions (attack, heal, repair...)
	public int maxHealth = 1, totalHealth = 1; //The max/start lifepoints and the current rest lifepoints of this building
	public int energyCostPerAction = 0; //How much energy this building will consume for each action
	public int damage = 0; //The count how many damage this building could deal per attack
	public int heal = 0; //The count how many heal this building could deal per action
	public int repair = 0; //The count how many repair this building could deal per action
	
	public boolean shouldBeDestroyedAtRoundEnd = false; //If true this building will be destroyed at the next round end (by damage taken or destroy dask)
	
	
//==========================================================================================================
	/**
	 * The constructor to the building head class
	 * @param playerReference - {@link PlayerReference} - Give the owner of this building
	 * @param connectedField - {@link Field} - The field where this building is placed
	 */
	public Building(int playerID_, Field connectedField_) {
		
		//if playerReference is null, it is the CreateMap part, so just non player
		if(connectedField_ == null) {
			//JUST A DUMMY BUILDING FOR LOADING STATS
			load_TypeSettings();
			return;
		}
		
		if(connectedField_.building != null) {
			/*SCHON BESETZT*/ ConsoleOutput.printMessageInConsole("A Building was placed on a Field with another Building on it!", true);
		}else if(connectedField_.troup != null) {
			/*SCHON BESETZT*/ ConsoleOutput.printMessageInConsole("A Building was created on a Field with a Troup on it!", true);
		}
		
		this.playerID = playerID_;
		this.connectedField = connectedField_;
		connectedField.building = this;
		
		if(GameData.gameMode != null) {
			if(SpielModus.isGameModus1v1()) {
				//1v1
				if(GameData.playingPlayer[0].getID() == playerID) {
					//Player1
					GameData.player1_buildings.add(this);
				}else if(GameData.playingPlayer[1].getID() == playerID) {
					//Player2
					GameData.player2_buildings.add(this);
				}else {
					ConsoleOutput.printMessageInConsole("Adding building to player list found no matching player! (1v1 - ID: "+playerID+")", true);
				}
			}else {
				//2v2
				if(GameData.playingPlayer[0].getID() == playerID) {
					//Player1
					GameData.player1_buildings.add(this);
				}else if(GameData.playingPlayer[1].getID() == playerID) {
					//Player2
					GameData.player2_buildings.add(this);
				}else if(GameData.playingPlayer[2].getID() == playerID) {
					//Player3
					GameData.player3_buildings.add(this);
				}else if(GameData.playingPlayer[3].getID() == playerID) {
					//Player4
					GameData.player4_buildings.add(this);
				}else {
					ConsoleOutput.printMessageInConsole("Adding building to player list found no matching player! (2v2 - ID: "+playerID+")", true);
				}
			}
		}else {
			//CreateMap modus
			CreateMapData.HQdisplayList.add(this);
		}
		
		load_TypeSettings();
		load_ActionTasks();
		
		//CALCULATED ON ROUND CHANGE
//		calculate_ViewRange();
//		calculate_ShotRange();
//		calculate_ProduceRange();
		
	}

//==========================================================================================================
	/**
	 * Loads/Sets the data for each type of building
	 */
	public void load_TypeSettings() {}
	
//==========================================================================================================
	/**
	 * Loads/Sets the possible actions for this building
	 */
	public void load_ActionTasks() {}

//==========================================================================================================
	/**
	 * Calculates the list with all visible fields for this troup
	 */
	public void calculate_ViewRange() {
		
		//BUILDINGS CAN NOT MOVE SO ONE TIME CALCULATE SHOULD BE ENOUGHT
		if(this.viewAbleFields.isEmpty() == false) { return; }
		
//		if(this instanceof Building_Headquarter) {
//			((Building_Headquarter) this).calculate_ViewRange_HQ();
//			return;
//		}
		
		this.viewAbleFields.clear();
		for(int x = this.connectedField.X-viewDistance ; x <= this.connectedField.X+viewDistance ; x++) {
			for(int y = this.connectedField.Y-viewDistance ; y <= this.connectedField.Y+viewDistance ; y++) {
				try{
					Field targetField = GameData.gameMap_FieldList[x][y];
					if(targetField.type == FieldType.Mountain) { continue; } //STONE IS NOT VISIBLE
					Path path = new PathFinding_Algorithmus(this.connectedField, targetField, false).getPath(this.viewDistance, true, true);
					if(path.getUsedSteps() <= viewDistance && path.getUsedSteps() > 0) {
						//WITH PATH STILL IN RANGE
						this.viewAbleFields.add(new FieldCoordinates(targetField));
					}
				}catch(NullPointerException | IndexOutOfBoundsException error) { error.printStackTrace(); }
			}
		}
		
	}
	
//==========================================================================================================
	/**
	 * Calculates the list with all shotable fields for this building
	 */
	public void calculate_ShotRange() {
		
		int saveFactor = StandardData.taskCalculateReachableFieldsSaveAdditionFactor;
		
		this.shotAbleFields.clear();
		this.repairAbleFields.clear();
		this.healAbleFields.clear();
		for(int x = this.connectedField.X-actionRange-saveFactor ; x <= this.connectedField.X+actionRange+saveFactor ; x++) {
			for(int y = this.connectedField.Y-actionRange-saveFactor ; y <= this.connectedField.Y+actionRange+saveFactor ; y++) {
				try{
					Field targetField = GameData.gameMap_FieldList[x][y];
					if(targetField.visible == true) {
						//IS VISIBLE
						if(targetField.building != null ) {
							if(GameHandler.checkPlayerIDForAllied(ProfilData.thisClient.getID(), targetField.building.playerID) == false) {
								//NO ALLIED
								this.shotAbleFields.add(new FieldCoordinates(targetField));
							}else {
								//ALLIED
								this.repairAbleFields.add(new FieldCoordinates(targetField));
							}
						}else if(targetField.troup != null) {
							if(GameHandler.checkPlayerIDForAllied(ProfilData.thisClient.getID(), targetField.troup.playerID) == false) {
								//NO ALLIED
								this.shotAbleFields.add(new FieldCoordinates(targetField));
							}else {
								//ALLIED
								this.healAbleFields.add(new FieldCoordinates(targetField));
							}
						}
					}
				}catch(NullPointerException | IndexOutOfBoundsException error) {}
			}
		}
	}
//==========================================================================================================
	/**
	 * Calculates the list with all produceable fields for this building
	 */
	public void calculate_ProduceRange() {
		
		int saveFactor = StandardData.taskCalculateReachableFieldsSaveAdditionFactor;
		
		this.produceAbleFields.clear();
		for(int x = this.connectedField.X-actionRange-saveFactor ; x <= this.connectedField.X+actionRange+saveFactor ; x++) {
			for(int y = this.connectedField.Y-actionRange-saveFactor ; y <= this.connectedField.Y+actionRange+saveFactor ; y++) {
				try{
					Field targetField = GameData.gameMap_FieldList[x][y];
					if(targetField.visible == true) {
						//IS VISIBLE
						if(targetField.building == null && targetField.troup == null) {
							//NO TROUP OR BUILDING ON IT
							
							this.produceAbleFields.add(new FieldCoordinates(targetField));
							
						}
					}
				}catch(NullPointerException | IndexOutOfBoundsException error) {}
			}
		}
	}
	
//==========================================================================================================
	/**
	 * This method is called at each round end - 1 - DELETE CHECK
	 */
	public void roundEnd_1() {
		
		if(shouldBeDestroyedAtRoundEnd == true) {
			this.destroy();
		}
		
	}
//==========================================================================================================
	/**
	 * This method is called at each round end - 2 - VIEW RANGE OF THIS BUILDING
	 */
	public void roundEnd_2() {
		
		if(shouldBeDestroyedAtRoundEnd == false) {
		
			if(this.activeTask != null) {
				this.activeTask.removeFromActiveTask();
			}
			calculate_ViewRange();
			
		}
		
	}
	
//==========================================================================================================
	/**
	 * This method is called at each round end - 3 - OTHER RANGES OF THIS BUILDING
	 */
	public void roundEnd_3() {
		
		if(shouldBeDestroyedAtRoundEnd == false) {
			
			calculate_ShotRange();
			calculate_ProduceRange();
			
		}
		
	}
	
//==========================================================================================================
	/**
	 * Draws this building on his field
	 * @param createMapModus - boolean - should be only true if the building should be drawn in the createMapEditor
	 */
	public void draw_Field(Graphics g, boolean createMapModus) {
		
		if(createMapModus == false) {
			//GAME MODUS
			int realX = (this.connectedField.X * StandardData.fieldSize)+GameData.scroll_LR_count;
			int realY = (this.connectedField.Y * StandardData.fieldSize)+GameData.scroll_UD_count;
			
			//IMG
			g.drawImage(img, realX+Images.buildingFactor, realY+Images.buildingFactor, null);
			//HEALTH
			int abstandX = 9, abstandY = StandardData.fieldSize-12, height = 4;
			Funktions.drawHealthbar(g, realX+abstandX, realY+abstandY, StandardData.fieldSize-abstandX*2, height, maxHealth, totalHealth);
			//COLOR
			g.setColor(Funktions.getColorByPlayerID(this.playerID));
			g.drawRoundRect(realX+2, realY+2, StandardData.fieldSize-4, StandardData.fieldSize-4, 6, 6);
			//CROSS IF DELETE
			if(shouldBeDestroyedAtRoundEnd == true) {
				g.setColor(new Color(255, 0, 0, 130)); //DURCHSCHEINENDES ROT
				g.fillRect(realX+3, realY+3, StandardData.fieldSize-6, StandardData.fieldSize-6);
			}
			//ACTION RANGE
			if(GameData.clickedField != null) {
				if(GameData.clickedField == this.connectedField) {
					draw_actionRange(g);
				}
			}
			//TARGET FIELD
			draw_targetField(g);
			
		}else {
			//CREATE MAP MODUS
			int realX = (this.connectedField.X * StandardData.fieldSize)+CreateMapData.scroll_CM_LR_count;
			int realY = (this.connectedField.Y * StandardData.fieldSize)+CreateMapData.scroll_CM_UD_count;
			
			g.drawImage(img, realX+Images.buildingFactor, realY+Images.buildingFactor, null);
		}
		
	}
	
//==========================================================================================================
	/**
	 * Draws the action range (for repair, heal and attack and so on...) for this building
	 */
	public void draw_actionRange(Graphics g) {
		
		boolean canHeal = false, canRepair = false, canAttack = false, canProduce = false;
		for(Task_Building task : this.actionTasks) {
			if(task instanceof Task_Building_Attack) {
				canAttack = true;
			}else if(task instanceof Task_Building_Heal) {
				canHeal = true;
			}else if(task instanceof Task_Building_Repair) {
				canRepair = true;
			}else if(task instanceof Task_Building_Produce) {
				canProduce = true;
			}
		}
		
		//PRODUCE RANGE
		if(canProduce == true) {
			
			Funktions.drawBorderAroundFieldArea(g, this.produceAbleFields, Color.WHITE, 3);
			
		}
		//OTHER RANGE
		if(canAttack == true || canHeal == true || canRepair == true) {
			
			List<FieldCoordinates> availableFields = new ArrayList<>();
			for(int x = this.connectedField.X-actionRange ; x <= this.connectedField.X+actionRange ; x++) {
				for(int y = this.connectedField.Y-actionRange ; y <= this.connectedField.Y+actionRange ; y++) {
					try{
						availableFields.add(new FieldCoordinates(GameData.gameMap_FieldList[x][y]));
					}catch(NullPointerException | IndexOutOfBoundsException error) {}
				}
			}
			Funktions.drawBorderAroundFieldArea(g, availableFields, Color.ORANGE, 1);
			
		}
		
		//ACTION FIELDS
		for(FieldCoordinates fieldCoordinates_1 : this.getActionFields()) {
			if(fieldCoordinates_1.getConnectedField().visible == false) { continue; } //SKIP IF NOT VISIBLE
			if(this.shotAbleFields.contains(fieldCoordinates_1)) {
				Funktions.drawBorderAroundFields(g, fieldCoordinates_1.getConnectedField(), Color.RED, 0, 3);
			}else {
				Funktions.drawBorderAroundFields(g, fieldCoordinates_1.getConnectedField(), Color.GREEN, 0, 3);
			}
		}
		
	}
	
//==========================================================================================================
	private Path lastHoverFieldPath = null;
	/**
	 * Draws the target Field (for move and attack and so on...) for this troup
	 */
	public void draw_targetField(Graphics g) {
		
		if(this.activeTask != null) {
			//HAT ACTIVE TASK
			if(this.activeTask.targetCoordinates != null) {
				//HAT ZIEL COORDINATEN
				Funktions.drawBorderAroundFields(g, this.activeTask.targetCoordinates.getConnectedField(), Color.WHITE, 8, 2);
				//Funktions.drawLineBetweenFields(g, this.connectedField, this.activeTask.targetCoordinates.getConnectedField(), Color.CYAN);
				this.activeTask.targetPath.drawWay(g, Color.WHITE);
			}
			if(GameData.dragAndDropTaskInputActive == true) {
				//HAT ACTIVEN TASK DER GERADE BEWEGT WIRD
				if(GameData.clickedField == this.connectedField) {
					//EIN TASK VON DIESEM FIELD WIRD BEWEGT
					if(GameData.hoveredField != null) {
						if(lastHoverFieldPath != null && lastHoverFieldPath.finish.X == new FieldCoordinates(GameData.hoveredField).X && lastHoverFieldPath.finish.Y == new FieldCoordinates(GameData.hoveredField).Y) {
							lastHoverFieldPath.drawWay(g, Color.ORANGE);
						}else {
							if(this.activeTask instanceof Task_Building_Attack) {
								//ATTACK - �BER WASSER
								new PathFinding_Algorithmus(new FieldCoordinates(this.connectedField), new FieldCoordinates(GameData.hoveredField), true)
								.getPath(this.viewDistance, true, true)
								.drawWay(g, Color.ORANGE);
							}else if(this.activeTask instanceof Task_Building_Produce) {
								//PRODUCE - JE NACH TROUP �BER WASSER
								Task_Building_Produce produceTask = (Task_Building_Produce) this.activeTask;
								new PathFinding_Algorithmus(new FieldCoordinates(this.connectedField), new FieldCoordinates(GameData.hoveredField), true)
								.getPath(this.viewDistance, false, produceTask.troupType == TroupType.AIR)
								.drawWay(g, Color.ORANGE);
							}else {
								//REST - NICHT �BER WASSER
								new PathFinding_Algorithmus(new FieldCoordinates(this.connectedField), new FieldCoordinates(GameData.hoveredField), true)
								.getPath(this.viewDistance, true, false)
								.drawWay(g, Color.ORANGE);
							}
						}
					}
				}
			}
		}
		
	}
	
//==========================================================================================================
	/**
	 * Draws the actions (Tasks) and infos of this building into the actionbar
	 * @param X - int - The X-Coordinate of the ActionBarPart
	 * @param Y - int - The Y-Coordinate of the ActionBarPart
	 */
	public void draw_ActionBar(Graphics g, int X, int Y) {
		
		int currentDrawNumber = 0;
		for(Task_Building task : actionTasks) {
			
			task.draw_ActionBar(g, currentDrawNumber);
			
			currentDrawNumber++;
		}
		//DRAW ACTIVE TASK
		if(this.activeTask != null) {
			//HAS TASK
			this.activeTask.draw_ActiveTask(g);
			if(this.activeTask.targetCoordinates != null) {
				this.activeTask.targetPath.drawWay(g, Color.ORANGE);
			}
		}else {
			//HAS NO TASK -> DRAW NOTHING
		}
		
	}
	
//==========================================================================================================
	/**
	 * Gets all fields with an action on it
	 * @return ArrayList(FieldCoordinates) - The List with all action fields of this building
	 */
	public List<FieldCoordinates> getActionFields() {
		
		boolean canHeal = false, canRepair = false, canAttack = false;
		for(Task_Building task : this.actionTasks) {
			if(task instanceof Task_Building_Attack) {
				canAttack = true;
			}else if(task instanceof Task_Building_Heal) {
				canHeal = true;
			}else if(task instanceof Task_Building_Repair) {
				canRepair = true;
			}
		}
		
		List<FieldCoordinates> output = new ArrayList<>();
		if(canAttack == true) { output.addAll(shotAbleFields); }
		if(canHeal == true) { output.addAll(healAbleFields); }
		if(canRepair == true) { output.addAll(repairAbleFields); }
		return output;
	}
	
//==========================================================================================================
	/**
	 * Checks a field whether it is viewable or not
	 * @return boolean - true if it is in view range, false if not
	 */
	public boolean fieldIsIn_VIEW_Range(Field targetField) {
		
		for(FieldCoordinates fieldCoordinates : this.viewAbleFields) {
			if(fieldCoordinates.compareToOtherField(targetField) == true) {
				return true;
			}
		}
		return false;
	}
//==========================================================================================================
	/**
	 * Checks a field whether it is reachable or not
	 * @return boolean - true if it is in attack range, false if not
	 */
	public boolean fieldIsIn_SHOT_Range(Field targetField) {
		
		for(FieldCoordinates fieldCoordinates : this.shotAbleFields) {
			if(fieldCoordinates.compareToOtherField(targetField) == true) {
				return true;
			}
		}
		return false;
	}
//==========================================================================================================
	/**
	 * Checks a field whether it is repairable or not
	 * @return boolean - true if it is in repair range, false if not
	 */
	public boolean fieldIsIn_REPAIR_Range(Field targetField) {
		
		for(FieldCoordinates fieldCoordinates : this.repairAbleFields) {
			if(fieldCoordinates.compareToOtherField(targetField) == true) {
				return true;
			}
		}
		return false;
	}
//==========================================================================================================
	/**
	 * Checks a field whether it is healable or not
	 * @return boolean - true if it is in heal range, false if not
	 */
	public boolean fieldIsIn_HEAL_Range(Field targetField) {
		
		for(FieldCoordinates fieldCoordinates : this.healAbleFields) {
			if(fieldCoordinates.compareToOtherField(targetField) == true) {
				return true;
			}
		}
		return false;
	}
//==========================================================================================================
	/**
	 * Checks a field whether it is produceable or not
	 * @return boolean - true if it is in range and empty, false if not
	 */
	public boolean fieldIsIn_PRODUCE_Range(Field targetField) {
		
		for(FieldCoordinates fieldCoordinates : this.produceAbleFields) {
			if(fieldCoordinates.compareToOtherField(targetField) == true) {
				return true;
			}
		}
		return false;
	}
	
//==========================================================================================================
	/**
	 * Checks if this building has a task of the given type
	 * @return boolean - true if it has the given task type, false if not
	 */
	public Task_Building hasTask(Task_Building task) {
		
		for(Task_Building t : this.actionTasks) {
			if(t.title.equals(task.title)) {
				return t;
			}
		}
		return null;
	}
	
//==========================================================================================================
	/**
	 * Deals the given damage to this building
	 */
	public void damage(int damageCount) {
		
		totalHealth -= damageCount;
		
		if(totalHealth <= 0) {
			totalHealth = 0;
			shouldBeDestroyedAtRoundEnd = true;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Repairs given damage by the repairCount
	 */
	public void repair(int repairCount) {
		
		if(shouldBeDestroyedAtRoundEnd == true) {
			return; //IS DESTROYED SO NO REPAIR CHANCE
		}
		
		totalHealth += repairCount;
		int realRepairAmount = repairCount;
		
		if(totalHealth > maxHealth) {
			realRepairAmount = maxHealth-totalHealth;
			totalHealth = maxHealth;
		}
		
		if(this.playerID == ProfilData.thisClient.getID()) {
			RoundData.currentStatsContainer.registerBuildingRepair(this, realRepairAmount);
		}
		
	}
	
//==========================================================================================================
	/**
	 * Destroys this building
	 */
	public void destroy() {
		
		if(GameData.gameMode != null) {
			if(SpielModus.isGameModus1v1()) {
				//1v1
				if(GameData.playingPlayer[0].getID() == playerID) {
					//Player1
					GameData.player1_buildings.remove(this);
				}else if(GameData.playingPlayer[1].getID() == playerID) {
					//Player2
					GameData.player2_buildings.remove(this);
				}else {
					ConsoleOutput.printMessageInConsole("Removing building to player list found no matching player! (1v1 - ID: "+playerID+")", true);
				}
			}else {
				//2v2
				if(GameData.playingPlayer[0].getID() == playerID) {
					//Player1
					GameData.player1_buildings.remove(this);
				}else if(GameData.playingPlayer[1].getID() == playerID) {
					//Player2
					GameData.player2_buildings.remove(this);
				}else if(GameData.playingPlayer[2].getID() == playerID) {
					//Player3
					GameData.player3_buildings.remove(this);
				}else if(GameData.playingPlayer[3].getID() == playerID) {
					//Player4
					GameData.player4_buildings.remove(this);
				}else {
					ConsoleOutput.printMessageInConsole("Removing building to player list found no matching player! (2v2 - ID: "+playerID+")", true);
				}
			}
			
			//STATS
			if(this.playerID == ProfilData.thisClient.getID()) {
				RoundData.currentStatsContainer.registerDeath(this);
			}else if(GameHandler.checkPlayerIDForAllied(this.playerID, ProfilData.thisClient.getID()) == false) {
				//ENEMY
				RoundData.currentStatsContainer.registerKill(this);
			}
			
		}else {
			//CreateMap modus
			CreateMapData.HQdisplayList.remove(this);
		}
		
		this.connectedField.building = null;
		
	}
	
}
