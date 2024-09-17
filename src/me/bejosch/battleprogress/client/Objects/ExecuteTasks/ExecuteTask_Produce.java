package me.bejosch.battleprogress.client.Objects.ExecuteTasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
import me.bejosch.battleprogress.client.Objects.Animations.Animation_MovingCircleDisplay;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Field.FieldMessage;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage_Located;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.Checkbox.MouseActionArea_Checkbox_SkipAllTaskDisplays;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Air.Troup_Air_LightHelicopter;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Land.Troup_Land_Commander;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Land.Soldier.Troup_Land_LightSoldier;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Land.Vehicle.Troup_Land_LightTank;

public class ExecuteTask_Produce extends ExecuteTask{

	public String troupName = null;
	public int playerID;
	
//==========================================================================================================
	/**
	 * Creates a new PRODUCE ExecuteTask, which is used for executing the given task for every client in the game
	 * @param troupName_ - String - The name of the troup which should be produced/created
	 * @param playerReference_ - {@link PlayerReference} - The reference of the player who produces this troup
	 * @param executeCoordinate_ - {@link FieldCoordinates} - The start coordinates (From where)
	 * @param targetCoordinate_ - {@link FieldCoordinates} - The goal coordinates (Where it goes)
	 */
	public ExecuteTask_Produce(String troupName_, int playerID_, FieldCoordinates executeCoordinate_, FieldCoordinates targetCoordinate_, boolean execSimulation) {
		super(ExecuteTaskType.Produce, execSimulation);
		
		this.troupName = troupName_;
		this.playerID = playerID_;
		this.executeCoordinate = executeCoordinate_;
		this.targetCoordinate = targetCoordinate_;
		
	}

	@Override
	public void performAnimation() {
		
		if(this.blockedTarget == true) {
			//BLOCKED BY OTHER TASK
			if(this.playerID == ProfilData.thisClient.getID()) {
				//YOUR TASK SO MESSAGE
				Funktions.moveScreenToFieldCoordinates(targetCoordinate.X, targetCoordinate.Y);
				List<String> text = new ArrayList<String>();
				text.add("A PRODUCTION could not be done at "+targetCoordinate.X+":"+targetCoordinate.Y+"");
				text.add("The field has been already used by an other task!");
				new InfoMessage_Located(text, ImportanceType.HIGH, targetCoordinate.X, targetCoordinate.Y, false);
				new FieldMessage("Already used", targetCoordinate.X, targetCoordinate.Y, 5);
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						actionFinished();
					}
				}, 1200);
			}else {
				//NOT YOU TASK SO JUST SKIP
				actionFinished();
			}
			return;
		}
		
		if(isVisible() == true) {
			//VISIBLE - SO ANIMATION EXECUTE
			Funktions.moveScreenToFieldCoordinates(targetCoordinate.X, targetCoordinate.Y);
			this.animationIsRunning = true;
			new Animation_MovingCircleDisplay(MovingCircleDisplayTypes.Produce, 0, executeCoordinate, targetCoordinate, false);
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
		
		Troup newTroup = null;
		
		//CREATE NEW TROUP
		switch (troupName) {
		//================= LAND
		case "Commander":
			newTroup = new Troup_Land_Commander(playerID, targetCoordinate.getConnectedField());
			break;
		//================= VEHICLE
		case "Light Tank":
			newTroup = new Troup_Land_LightTank(playerID, targetCoordinate.getConnectedField());
			break;
		//================= SOLDIER
		case "Light Soldier":
			newTroup = new Troup_Land_LightSoldier(playerID, targetCoordinate.getConnectedField());
			break;
		//================= AIR
		case "Light Heli":
			newTroup = new Troup_Air_LightHelicopter(playerID, targetCoordinate.getConnectedField());
			break;
		default:
			ConsoleOutput.printMessageInConsole("A PRODUCE executeTask found no troup for the given troupName [TroupName: "+troupName+"]", true);
			break;
		}
		
		//SIMULATION COST REDUCTION (is normaly reduced by creating the task, sim doesnt have that)
		if(this.execSimulation && this.playerID == ProfilData.thisClient.getID()) {
			int cost = Game_UnitsHandler.getUnitByName(this.troupName).kosten;
			EconomicData.materialAmount -= cost; //No valid check requiered, we are just following the records (replay) so it was already approved
		}
		
		//STATS
		if(newTroup != null && newTroup.playerID == ProfilData.thisClient.getID()) {
			RoundData.currentStatsContainer.registerProduced(newTroup);
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
