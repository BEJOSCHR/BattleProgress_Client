package me.bejosch.battleprogress.client.Objects.ExecuteTasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Enum.ExecuteTaskType;
import me.bejosch.battleprogress.client.Enum.ImportanceType;
import me.bejosch.battleprogress.client.Enum.MovingCircleDisplayTypes;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_MovingCircleDisplay;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage_Located;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.Checkbox.MouseActionArea_Checkbox_SkipAllTaskDisplays;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;

public class ExecuteTask_HealAndRepair extends ExecuteTask{

	public int playerID;
	public int healORrepairCount = 0;
	
//==========================================================================================================
	/**
	 * Creates a new HEAl/REPAIR ExecuteTask, which is used for executing the given task for every client in the game
	 * @param playerReference_ - {@link PlayerReference} - The player who has ordered this task
	 * @param healORrepairCount_ - int - The value of heal/repair which should be done
	 * @param executeCoordinate_ - {@link FieldCoordinates} - The start coordinates (From where)
	 * @param targetCoordinate_ - {@link FieldCoordinates} - The goal coordinates (Where it goes)
	 */
	public ExecuteTask_HealAndRepair(int playerID_, int healORrepairCount_, FieldCoordinates executeCoordinate_, FieldCoordinates targetCoordinate_, boolean execSimulation) {
		super(ExecuteTaskType.HealAndRepair, execSimulation);
		
		this.playerID = playerID_;
		this.healORrepairCount = healORrepairCount_;
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
				text.add("A HEAL/REPAIR could not be done at "+targetCoordinate.X+":"+targetCoordinate.Y+"");
				text.add("The field has been already used by an other task!");
				new InfoMessage_Located(text, ImportanceType.HIGH, targetCoordinate.X, targetCoordinate.Y, false);
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
			Building foundTargetBuilding = GameHandler.getBuildingByCoordinates(targetCoordinate.X, targetCoordinate.Y);
			Troup foundTargetTroup = GameHandler.getTroupByCoordinates(targetCoordinate.X, targetCoordinate.Y);
			if(foundTargetBuilding != null) {
				//BUILDING
				this.animationIsRunning = true;
				new Animation_MovingCircleDisplay(MovingCircleDisplayTypes.Repair, this.healORrepairCount, executeCoordinate, targetCoordinate, false);
				//SKIP ALL IS ACTIVE
				if( ((MouseActionArea_Checkbox_SkipAllTaskDisplays) GameHandler.getMouseActionAreaByName("Checkbox_SkipAllTaskDisplays")).getCurrentState() == true ) {
					this.skipAnimation();
				}
			}else if(foundTargetTroup != null) {
				//TROUP
				this.animationIsRunning = true;
				new Animation_MovingCircleDisplay(MovingCircleDisplayTypes.Heal, this.healORrepairCount, executeCoordinate, targetCoordinate, false);
				//SKIP ALL IS ACTIVE
				if( ((MouseActionArea_Checkbox_SkipAllTaskDisplays) GameHandler.getMouseActionAreaByName("Checkbox_SkipAllTaskDisplays")).getCurrentState() == true ) {
					this.skipAnimation();
				}
			}else {
				ConsoleOutput.printMessageInConsole("An HEAL/REPAIR executeTask found no building or troup to perform the animation [X: "+targetCoordinate.X+"-Y: "+targetCoordinate.Y+"]", true);
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
		
		Building foundTargetBuilding = GameHandler.getBuildingByCoordinates(targetCoordinate.X, targetCoordinate.Y);
		Troup foundTargetTroup = GameHandler.getTroupByCoordinates(targetCoordinate.X, targetCoordinate.Y);
		
		if(foundTargetBuilding != null) {
			//BUILDING
			foundTargetBuilding.repair(healORrepairCount);
		}else if(foundTargetTroup != null) {
			//TROUP
			foundTargetTroup.heal(healORrepairCount);
		}else {
			ConsoleOutput.printMessageInConsole("An HEAL/REPAIR executeTask found no building or troup at the target to heal/repair [X: "+targetCoordinate.X+"-Y: "+targetCoordinate.Y+"]", true);
		}
		
		//SIMULATION ENERGY COST (is normaly done by creating the task, sim doesnt have that) and exclude HQ
		if(this.execSimulation && this.playerID == ProfilData.thisClient.getID()) {
			Building foundExecuteBuilding = GameHandler.getBuildingByCoordinates(executeCoordinate.X, executeCoordinate.Y);
			Troup foundExecuteTroup = GameHandler.getTroupByCoordinates(executeCoordinate.X, executeCoordinate.Y);
			if(foundExecuteBuilding != null && foundExecuteBuilding.energyCostPerAction > 0) {
				RoundData.currentStatsContainer.addEnergyEntry(foundExecuteBuilding, -foundExecuteBuilding.energyCostPerAction);
			}else if(foundExecuteTroup != null && foundExecuteTroup.energyCostPerAction > 0) {
				RoundData.currentStatsContainer.addEnergyEntry(foundExecuteTroup, -foundExecuteTroup.energyCostPerAction);
			}
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
