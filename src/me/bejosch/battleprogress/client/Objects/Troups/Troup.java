package me.bejosch.battleprogress.client.Objects.Troups;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.CreateMapData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.SpectateData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Enum.TroupType;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Handler.SpectateHandler;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Attack;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Heal;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Move;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Repair;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Upgrade;
import me.bejosch.battleprogress.client.PathFinding.Path;
import me.bejosch.battleprogress.client.PathFinding.PathFinding_Algorithmus;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Troup {

	public Image img = Images.troup_Land_missingTexture;
	
	public Field connectedField;
	public int playerID;
	public TroupType troupType = null;

	public List<FieldCoordinates> viewAbleFields = new ArrayList<FieldCoordinates>();
	public List<FieldCoordinates> moveAbleFields = new ArrayList<FieldCoordinates>();
	public List<FieldCoordinates> shotAbleFields = new ArrayList<FieldCoordinates>();
	public List<FieldCoordinates> repairAbleFields = new ArrayList<FieldCoordinates>();
	public List<FieldCoordinates> healAbleFields = new ArrayList<FieldCoordinates>();
	public List<FieldCoordinates> upgradeAbleFields = new ArrayList<FieldCoordinates>();
	
	public List<Task_Troup> actionTasks = new ArrayList<Task_Troup>();
	public Task_Troup activeTask = null;
	
	public String name = "missingName";
	public int textSize_nameField = 12;
	public int textSize_nameActionbar = 12;
	public String[] hoverDescription = {"missingDescription"};
	
	public int viewDistance = 1; //The distance the troup can "view"
	public int moveDistance = 1; //The distance the troup can walk/drive/fly
	public int actionRange = 0; //The distance the troup can perfrom actions (attack, heal, repair...)
	public int maxHealth = 1, totalHealth = 1; //The max/start lifepoints and the current rest lifepoints of this troup
	public int energyCostPerAction = 0; //How much energy this troup will consume for each action
	public int damage = 0; //The count how many damage this troup could deal per attack
	public int heal = 0; //The count how many heal this troup could deal per action
	public int repair = 0; //The count how many repair this troup could deal per action
	
	public boolean shouldBeDeletedAtRoundEnd = false; //If true this troup will be deleted at the next round end (by damage taken or delete dask)
	public FieldCoordinates targetUpgradePosition = null; //If not null an other troup at this cords has this troup as an upgrade target so this troup should not be allowed to do anything
	
	public boolean canFly = false;
	
	
//==========================================================================================================
	/**
	 * The constructor to the troup head class
	 * @param playerID - {@link Integer} - Give the owner of this troup
	 * @param connectedField - {@link Field} - The field where this troup is placed
	 */
	public Troup(int playerID_, Field connectedField_, TroupType troupType_) {
		
		//if playerReference is null, it is the CreateMap part, so just non player
		if(connectedField_ == null) {
			//JUST A DUMMY TROUP FOR LOADING STATS
			load_TypeSettings();
			return;
		}
		
		if(connectedField_.troup != null) {
			/*SCHON BESETZT*/ ConsoleOutput.printMessageInConsole("A Troup was created on a Field with another Troup on it!", true);
		}else if(connectedField_.building != null) {
			/*SCHON BESETZT*/ ConsoleOutput.printMessageInConsole("A Troup was created on a Field with a Building on it!", true);
		}
		
		this.playerID = playerID_;
		this.connectedField = connectedField_;
		this.troupType = troupType_;
		connectedField.troup = this;
		
		switch(StandardData.spielStatus) {
		case Game:
			if(SpielModus.isGameModus1v1()) {
				//1v1
				if(GameData.playingPlayer[0].getID() == playerID) {
					//Player1
					GameData.player1_troups.add(this);
				}else if(GameData.playingPlayer[1].getID() == playerID) {
					//Player2
					GameData.player2_troups.add(this);
				}else {
					ConsoleOutput.printMessageInConsole("Adding troup to player list found no matching player! (1v1 - ID: "+playerID+")", true);
				}
			}else {
				//2v2
				if(GameData.playingPlayer[0].getID() == playerID) {
					//Player1
					GameData.player1_troups.add(this);
				}else if(GameData.playingPlayer[1].getID() == playerID) {
					//Player2
					GameData.player2_troups.add(this);
				}else if(GameData.playingPlayer[2].getID() == playerID) {
					//Player3
					GameData.player3_troups.add(this);
				}else if(GameData.playingPlayer[3].getID() == playerID) {
					//Player4
					GameData.player4_troups.add(this);
				}else {
					ConsoleOutput.printMessageInConsole("Adding troup to player list found no matching player! (2v2 - ID: "+playerID+")", true);
				}
			}
			load_TypeSettings();
			load_ActionTasks();
			break;
		case CreateMap:
			break;
		case Replay:
			load_TypeSettings();
			break;
		case Spectate:
			load_TypeSettings();
			break;
		default:
			break;
		
		}
		
		//CALCULATED ON ROUND CHANGE
//		calculate_ViewRange();
//		calculate_MoveRange();
//		calculate_ShotRange();
		
	}
	
//==========================================================================================================
	/**
	 * Loads/Sets the data for each type of troup
	 */
	public void load_TypeSettings() {}
	
//==========================================================================================================
	/**
	 * Loads/Sets the possible actions for this troup
	 */
	public void load_ActionTasks() {}

//==========================================================================================================
	/**
	 * Calculates the list with all visible fields for this troup
	 */
	public void calculate_ViewRange() {
		
		this.viewAbleFields.clear();
		for(int x = this.connectedField.X-viewDistance ; x <= this.connectedField.X+viewDistance ; x++) {
			for(int y = this.connectedField.Y-viewDistance ; y <= this.connectedField.Y+viewDistance ; y++) {
				try{
					Field targetField = GameData.gameMap_FieldList[x][y];
					if(targetField.type == FieldType.Mountain) { continue; } //STONE IS NOT VISIBLE
					Path path = new PathFinding_Algorithmus(this.connectedField, targetField, false).getPath(this.viewDistance, true, true);
					if(path.getUsedSteps() <= viewDistance && path.getUsedSteps() > 0) { // IS 1 BEI ERROR / BLOCK / 1 Step
						//WITH PATH STILL IN RANGE
						this.viewAbleFields.add(new FieldCoordinates(targetField));
					}
				}catch(NullPointerException | IndexOutOfBoundsException error) { error.printStackTrace();}
			}
		}
		
	}
	
//==========================================================================================================
	/**
	 * Calculates the list with all moveable fields for this troup
	 */
	public void calculate_MoveRange() {
		
		int saveFactor = StandardData.taskCalculateReachableFieldsSaveAdditionFactor;
		int additionalMoveRange = 0;
		
		if(this.troupType == TroupType.LAND && this.connectedField.type == FieldType.Path) {
			//LAND PATH BONUS
			additionalMoveRange += 1;
		}
		
		this.moveAbleFields.clear();
		for(int x = this.connectedField.X-moveDistance-additionalMoveRange-saveFactor ; x <= this.connectedField.X+moveDistance+additionalMoveRange+saveFactor ; x++) {
			for(int y = this.connectedField.Y-moveDistance-additionalMoveRange-saveFactor ; y <= this.connectedField.Y+moveDistance+additionalMoveRange+saveFactor ; y++) {
				try{
					Field targetField = GameData.gameMap_FieldList[x][y];
					if(targetField.visible == true) {
						//IS VISIBLE
						if(targetField.building == null && targetField.troup == null) {
							//NOCH NICHT BELEGT
							if(targetField.type != FieldType.Mountain) {
								//STONE KANN NICHT BETRETTEN WERDEN
								if(troupType == TroupType.LAND) {
									if(targetField.type != FieldType.Ocean) {
										//LAND MUSS WASSER AUSSCHLIE�EN
										this.moveAbleFields.add(new FieldCoordinates(targetField));
									}
								}else {
									//LUFT KANN AUF WASSER
									this.moveAbleFields.add(new FieldCoordinates(targetField));
								}
							}
						}
					}
				}catch(NullPointerException | IndexOutOfBoundsException error) {}
			}
		}
		
	}
	
//==========================================================================================================
	/**
	 * Calculates the list with all shotable fields for this troup
	 */
	public void calculate_ShotRange() {
		
		int saveFactor = StandardData.taskCalculateReachableFieldsSaveAdditionFactor;
		
		this.shotAbleFields.clear();
		this.repairAbleFields.clear();
		this.healAbleFields.clear();
		this.upgradeAbleFields.clear();
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
							//OWN TYPE - UPGRADE
							if(targetField.troup.playerID == ProfilData.thisClient.getID()) {
								if(targetField.troup.name.equals(this.name) && (targetField.X != this.connectedField.X || targetField.Y != this.connectedField.Y) ) {
									//GLEICHER TYPE
									this.upgradeAbleFields.add(new FieldCoordinates(targetField));
								}
							}
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
		
		if(shouldBeDeletedAtRoundEnd == true) {
			this.delete(false);
		}
		
	}
//==========================================================================================================
	/**
	 * This method is called at each round end - 2 - VIEW RANGE OF THIS TROUP
	 */
	public void roundEnd_2() {
		
		if(shouldBeDeletedAtRoundEnd == false) {
			
			if(this.activeTask != null) {
				this.activeTask.removeFromActiveTask();
			}
			calculate_ViewRange();
			
		}
		
	}
	
//==========================================================================================================
	/**
	 * This method is called at each round end - 3 - OTHER RANGES OF THIS TROUP
	 */
	public void roundEnd_3() {
		
		if(shouldBeDeletedAtRoundEnd == false) {
			
			calculate_MoveRange();
			calculate_ShotRange();
			
		}
		
	}
	
//==========================================================================================================
	/**
	 * Draws this troup on his field
	 * @param createMapModus - boolean - should be only true if the troup should be drawn in the createMapEditor
	 */
	public void draw_Field(Graphics g, boolean createMapModus) {
		
		switch(StandardData.spielStatus) {
		case Game:
			int realX = (this.connectedField.X * StandardData.fieldSize)+GameData.scroll_LR_count;
			int realY = (this.connectedField.Y * StandardData.fieldSize)+GameData.scroll_UD_count;
			
			//IMG
			g.drawImage(img, realX+Images.troupFactor, realY+Images.troupFactor, null);
			//NAME
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, this.textSize_nameField));
			g.drawString(name, realX+5, realY+(StandardData.fieldSize-17));
			//HEALTH
			Funktions.drawHealthbar(g, realX+GameData.healthBar_abstandX, realY+GameData.healthBar_abstandY, StandardData.fieldSize-GameData.healthBar_abstandX*2, GameData.healthBar_height, maxHealth, totalHealth);
			//COLOR
			g.setColor(Funktions.getColorByPlayerID(this.playerID));
			g.drawRoundRect(realX+2, realY+2, StandardData.fieldSize-4, StandardData.fieldSize-4, 6, 6);
			//CROSS IF DEAD
			if(shouldBeDeletedAtRoundEnd == true) {
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
			break;
		case CreateMap:
			int realX_cm = (this.connectedField.X * StandardData.fieldSize)+CreateMapData.scroll_CM_LR_count;
			int realY_cm = (this.connectedField.Y * StandardData.fieldSize)+CreateMapData.scroll_CM_UD_count;
			
			g.drawImage(img, realX_cm+Images.troupFactor, realY_cm+Images.troupFactor, null);
			break;
		case Replay:
			//TODO
			break;
		case Spectate:
			int realX_spec = (this.connectedField.X * StandardData.fieldSize)+SpectateData.scroll_LR_count;
			int realY_spec = (this.connectedField.Y * StandardData.fieldSize)+SpectateData.scroll_UD_count;
			
			//IMG
			g.drawImage(img, realX_spec+Images.troupFactor, realY_spec+Images.troupFactor, null);
			//NAME
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, this.textSize_nameField));
			g.drawString(name, realX_spec+5, realY_spec+(StandardData.fieldSize-17));
			//HEALTH
			Funktions.drawHealthbar(g, realX_spec+GameData.healthBar_abstandX, realY_spec+GameData.healthBar_abstandY, StandardData.fieldSize-GameData.healthBar_abstandX*2, GameData.healthBar_height, maxHealth, totalHealth);
			//COLOR
			g.setColor(SpectateHandler.getColorByPlayerID(this.playerID));
			g.drawRoundRect(realX_spec+2, realY_spec+2, StandardData.fieldSize-4, StandardData.fieldSize-4, 6, 6);
			
			
			break;
		default:
			break;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Draws the action range (for repair, heal and attack and so on...) for this troup
	 */
	public void draw_actionRange(Graphics g) {
		
		boolean canHeal = false, canRepair = false, canAttack = false;
		for(Task_Troup task : this.actionTasks) {
			if(task instanceof Task_Troup_Attack) {
				canAttack = true;
			}else if(task instanceof Task_Troup_Heal) {
				canHeal = true;
			}else if(task instanceof Task_Troup_Repair) {
				canRepair = true;
			}
		}
		
		//MOVE RANGE
		Funktions.drawBorderAroundFieldArea(g, this.moveAbleFields, Color.WHITE, 3);
		
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
			}else if(this.upgradeAbleFields.contains(fieldCoordinates_1)) {
				Funktions.drawBorderAroundFields(g, fieldCoordinates_1.getConnectedField(), new Color(160, 032, 240), 0, 3); //PURPLE
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
							if(this.activeTask instanceof Task_Troup_Attack) {
								//ATTACK - �BER WASSER
								lastHoverFieldPath = new PathFinding_Algorithmus(new FieldCoordinates(this.connectedField), new FieldCoordinates(GameData.hoveredField), true).getPath(this.viewDistance, true, true);
							}else if(this.activeTask instanceof Task_Troup_Move) {
								//MOVE POSSIBLE �BER WASSER
								lastHoverFieldPath = new PathFinding_Algorithmus(new FieldCoordinates(this.connectedField), new FieldCoordinates(GameData.hoveredField), true).getPath(this.viewDistance, false, this.canFly);
							}else if(this.activeTask instanceof Task_Troup_Upgrade) {
								//UPGRADE POSSIBLE �BER WASSER UND AUF EINHEITEN
								lastHoverFieldPath = new PathFinding_Algorithmus(new FieldCoordinates(this.connectedField), new FieldCoordinates(GameData.hoveredField), true).getPath(this.viewDistance, true, this.canFly);
							}else {
								//REST - NICHT �BER WASSER
								lastHoverFieldPath = new PathFinding_Algorithmus(new FieldCoordinates(this.connectedField), new FieldCoordinates(GameData.hoveredField), true).getPath(this.viewDistance, true, false);
							}
							lastHoverFieldPath.drawWay(g, Color.ORANGE);
						}
					}
				}
			}
		}
		
	}
	
//==========================================================================================================
	/**
	 * Draws the actions (Tasks) and infos of this troup into the actionbar
	 * @param X - int - The X-Coordinate of the ActionBarPart
	 * @param Y - int - The Y-Coordinate of the ActionBarPart
	 */
	public void draw_ActionBar(Graphics g, int X, int Y) {
		
		int currentDrawNumber = 0;
		for(Task_Troup task : actionTasks) {
			
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
	 * @return ArrayList(FieldCoordinates) - The List with all action fields of this troup
	 */
	public List<FieldCoordinates> getActionFields() {
		
		boolean canHeal = false, canRepair = false, canAttack = false, canUpgrade = false;
		for(Task_Troup task : this.actionTasks) {
			if(task instanceof Task_Troup_Attack) {
				canAttack = true;
			}else if(task instanceof Task_Troup_Heal) {
				canHeal = true;
			}else if(task instanceof Task_Troup_Repair) {
				canRepair = true;
			}else if(task instanceof Task_Troup_Upgrade) {
				canUpgrade = true;
			}
		}
		
		List<FieldCoordinates> output = new ArrayList<>();
		if(canAttack == true) { output.addAll(shotAbleFields); }
		if(canHeal == true) { output.addAll(healAbleFields); }
		if(canRepair == true) { output.addAll(repairAbleFields); }
		if(canUpgrade == true) { output.addAll(upgradeAbleFields); }
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
	 * Checks a field whether it is moveable or not
	 * @return boolean - true if it is in move range, false if not
	 */
	public boolean fieldIsIn_MOVE_Range(Field targetField) {
		
		for(FieldCoordinates fieldCoordinates : this.moveAbleFields) {
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
	 * Checks a field whether it is upgradeable or not (The troup on this field)
	 * @return boolean - true if it is in upgrade range, false if not
	 */
	public boolean fieldIsIn_UPGRADE_Range(Field targetField) {
		
		for(FieldCoordinates fieldCoordinates : this.upgradeAbleFields) {
			if(fieldCoordinates.compareToOtherField(targetField) == true) {
				return true;
			}
		}
		return false;
	}
	
//==========================================================================================================
	/**
	 * Checks if this troup has a task of the given type
	 * @return boolean - true if it has the given task type, false if not
	 */
	public boolean hasTask(Task_Troup task) {
		
		for(Task_Troup t : this.actionTasks) {
			if(t.title.equals(task.title)) {
				return true;
			}
		}
		return false;
	}
	
//==========================================================================================================
	/**
	 * Deals the given damage to this troup
	 */
	public void damage(int damageCount) {
		
		totalHealth -= damageCount;
		
		if(!shouldBeDeletedAtRoundEnd && totalHealth <= 0) {
			totalHealth = 0;
			shouldBeDeletedAtRoundEnd = true;
			
			//ProgressPointAdd
			GameHandler.addProgressPoints(GameData.progressPoints_kill_troup, this.playerID, new FieldCoordinates(this.connectedField));
			
		}
		
	}
	
//==========================================================================================================
	/**
	 * Heilt die Truppe
	 * @param healCount Heal amount
	 */
	public void heal(int healCount) {
		
		if(shouldBeDeletedAtRoundEnd == true) {
			return; //IS DELETED SO NO HEAL CHANCE
		}
		
		totalHealth += healCount;
		int realHealAmount = healCount;
		
		if(totalHealth > maxHealth) {
			realHealAmount = maxHealth-totalHealth;
			totalHealth = maxHealth;
		}
		
		if(this.playerID == ProfilData.thisClient.getID()) {
			RoundData.currentStatsContainer.registerTroupHeal(this, realHealAmount);
		}
		
	}
	
//==========================================================================================================
	/**
	 * Deletes this troup
	 */
	public void delete(boolean fromUpgrade) {
		
		if(SpielModus.isGameModus1v1()) {
			//1v1
			if(GameData.playingPlayer[0].getID() == playerID) {
				//Player1
				GameData.player1_troups.remove(this);
			}else if(GameData.playingPlayer[1].getID() == playerID) {
				//Player2
				GameData.player2_troups.remove(this);
			}else {
				ConsoleOutput.printMessageInConsole("Removing troup to player list found no matching player! (1v1 - ID: "+playerID+")", true);
			}
		}else {
			//2v2
			if(GameData.playingPlayer[0].getID() == playerID) {
				//Player1
				GameData.player1_troups.remove(this);
			}else if(GameData.playingPlayer[1].getID() == playerID) {
				//Player2
				GameData.player2_troups.remove(this);
			}else if(GameData.playingPlayer[2].getID() == playerID) {
				//Player3
				GameData.player3_troups.remove(this);
			}else if(GameData.playingPlayer[3].getID() == playerID) {
				//Player4
				GameData.player4_troups.remove(this);
			}else {
				ConsoleOutput.printMessageInConsole("Removing troup to player list found no matching player! (2v2 - ID: "+playerID+")", true);
			}
		}
		
		//STATS
		if(fromUpgrade == false) {
			if(this.playerID == ProfilData.thisClient.getID()) {
				RoundData.currentStatsContainer.registerDeath(this);
			}else if(GameHandler.checkPlayerIDForAllied(this.playerID, ProfilData.thisClient.getID()) == false) {
				//ENEMY
				RoundData.currentStatsContainer.registerKill(this);
			}
		}
		
		this.connectedField.troup = null;
		
	}
	
}
