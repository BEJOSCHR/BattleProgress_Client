package me.bejosch.battleprogress.client.Objects.ExecuteTasks;

import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.Game.EconomicData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Enum.ExecuteTaskType;
import me.bejosch.battleprogress.client.Enum.ImportanceType;
import me.bejosch.battleprogress.client.Enum.MovingCircleDisplayTypes;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Game.Handler.Game_UnitsHandler;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_MovingCircleDisplay;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage_Located;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.Checkbox.MouseActionArea_Checkbox_SkipAllTaskDisplays;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Air.Troup_Air_HeavyHelicopter;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Air.Troup_Air_MediumHelicopter;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Land.Soldier.Troup_Land_HeavySoldier;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Land.Soldier.Troup_Land_MediumSoldier;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Land.Vehicle.Troup_Land_HeavyTank;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Land.Vehicle.Troup_Land_MediumTank;

public class ExecuteTask_Upgrade extends ExecuteTask{

	public int playerID;
	public String upgradeTroupName = null;
	
//==========================================================================================================
	/**
	 * Creates a new UPGRADE ExecuteTask, which is used for executing the given task for every client in the game
	 * @param playerReference_ - {@link PlayerReference} - The reference of the player who upgrade this troup
	 * @param upgradeTroupName - String - The name of the new upgraded troup
	 * @param executeCoordinate_ - {@link FieldCoordinates} - The start coordinates (From where)
	 * @param targetCoordinate_ - {@link FieldCoordinates} - The goal coordinates (Where it goes)
	 */
	public ExecuteTask_Upgrade(int playerID_, String upgradeTroupName, FieldCoordinates executeCoordinate_, FieldCoordinates targetCoordinate_, boolean execSimulation) {
		super(ExecuteTaskType.Upgrade, execSimulation);
		
		this.playerID = playerID_;
		this.upgradeTroupName = upgradeTroupName;
		this.executeCoordinate = executeCoordinate_;
		this.targetCoordinate = targetCoordinate_;
		
	}

	@Override
	public void performAnimation() {
		
		if(isVisible() == true) {
			//VISIBLE - SO ANIMATION EXECUTE
			Funktions.moveScreenToFieldCoordinates(targetCoordinate.X, targetCoordinate.Y);
			this.animationIsRunning = true;
			new Animation_MovingCircleDisplay(MovingCircleDisplayTypes.Upgrade, 0, executeCoordinate, targetCoordinate, false);
			//SKIP ALL IS ACTIVE
			if( ((MouseActionArea_Checkbox_SkipAllTaskDisplays) GameHandler.getMouseActionAreaByName("Checkbox_SkipAllTaskDisplays")).getCurrentState() == true ) {
				this.skipAnimation();
			}
		}else {
			//NOT VISIBLE - SO FAST EXECUTE
			performAction();
		}
		
	}
	
	@Override
	public void performAction() {
		
		if(this.performedAction == true) { return; }
		this.performedAction = true;
		this.animationIsRunning = false;
		
		if(this.executeCoordinate.getConnectedField().troup.shouldBeDeletedAtRoundEnd == true || this.targetCoordinate.getConnectedField().troup.shouldBeDeletedAtRoundEnd == true) {
			//ONE OF THE TROUPS IS DEAD
			List<String> text = new ArrayList<String>();
			text.add("An UPGRADE could not be done at "+targetCoordinate.X+":"+targetCoordinate.Y+"");
			text.add("One of the troups got killed!");
			new InfoMessage_Located(text, ImportanceType.HIGH, targetCoordinate.X, targetCoordinate.Y, false);
			actionFinished();
			return;
		}
		
		//HEALTH FIT
		Troup troupOld1 = this.executeCoordinate.getConnectedField().troup;
		Troup troupOld2 = this.targetCoordinate.getConnectedField().troup;
		int maxHealthOldCombined = troupOld1.maxHealth+troupOld2.maxHealth;
		int totalHealthOldCombined = troupOld1.totalHealth+troupOld2.totalHealth;
		double percentageLeft = ((double) totalHealthOldCombined) / ((double) maxHealthOldCombined);
		
		//DESTROY OLD TROUPS
		troupOld1.delete(true);
		troupOld2.delete(true);
		
		//CREATE NEW TROUP
		Troup troup = null;
		switch (this.upgradeTroupName) {
		//================= VEHICLE
		case "Medium Tank":
			troup = new Troup_Land_MediumTank(playerID, targetCoordinate.getConnectedField());
			break;
		case "Heavy Tank":
			troup = new Troup_Land_HeavyTank(playerID, targetCoordinate.getConnectedField());
			break;
		//================= SOLDIER
		case "Medium Soldier":
			troup = new Troup_Land_MediumSoldier(playerID, targetCoordinate.getConnectedField());
			break;
		case "Heavy Soldier":
			troup = new Troup_Land_HeavySoldier(playerID, targetCoordinate.getConnectedField());
			break;
		//================= AIR
		case "Medium Heli":
			troup = new Troup_Air_MediumHelicopter(playerID, targetCoordinate.getConnectedField());
			break;
		case "Heavy Heli":
			troup = new Troup_Air_HeavyHelicopter(playerID, targetCoordinate.getConnectedField());
			break;
		default:
			ConsoleOutput.printMessageInConsole("A UPGRADE executeTask found no upgrade troup for the given troupName [TroupName: "+this.upgradeTroupName+"]", true);
			break;
		}
		if(troup != null) { troup.totalHealth = (int) Math.round(troup.totalHealth*percentageLeft); } //SET DAMAGED HP
		
		//SIMULATION COST REDUCTION (is normaly reduced by creating the task, sim doesnt have that)
		if(troup != null && this.execSimulation && this.playerID == ProfilData.thisClient.getID()) {
			UnitStatsContainer c = Game_UnitsHandler.getUnitByName(this.upgradeTroupName);
			EconomicData.materialAmount -= c.kosten; //No valid check requiered, we are just following the records (replay) so it was already approved
			RoundData.currentStatsContainer.addMassEntry(troup, -c.kosten);
		}
		
		//STATS
		if(troup != null && troup.playerID == ProfilData.thisClient.getID()) {
			RoundData.currentStatsContainer.registerUpgrade(troup);
		}
		
		//FINISHED
		actionFinished();
		
	}
	
	@Override
	public boolean isVisible() {
		
		if(executeCoordinate.getConnectedField().visible == true || targetCoordinate.getConnectedField().visible == true) {
			//IF START OR TARGET ARE VISIBLE
			return true;
		}else {
			//IF BOTH COORDINATES ARE NOT VISIBLE
			return false;
		}
		
	}
	
}
