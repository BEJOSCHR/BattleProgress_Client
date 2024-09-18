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
import me.bejosch.battleprogress.client.Objects.Field.FieldMessage;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage_Located;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.Checkbox.MouseActionArea_Checkbox_SkipAllTaskDisplays;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;

public class ExecuteTask_Move extends ExecuteTask{

	public int playerID;
	public Troup movingTroup = null;
	
//==========================================================================================================
	/**
	 * Creates a new MOVE ExecuteTask, which is used for executing the given task for every client in the game
	 * @param playerReference_ - {@link PlayerReference} - The player who has ordered this task
	 * @param executeCoordinate_ - {@link FieldCoordinates} - The start coordinates (From where)
	 * @param targetCoordinate_ - {@link FieldCoordinates} - The goal coordinates (Where it goes)
	 */
	public ExecuteTask_Move(int playerID_, FieldCoordinates executeCoordinate_, FieldCoordinates targetCoordinate_, boolean execSimulation) {
		super(ExecuteTaskType.Move, execSimulation);
		
		this.playerID = playerID_;
		this.executeCoordinate = executeCoordinate_;
		this.targetCoordinate = targetCoordinate_;
		this.movingTroup = GameHandler.getTroupByCoordinates(executeCoordinate.X, executeCoordinate.Y);
		
		if(this.movingTroup == null) { ConsoleOutput.printMessageInConsole("A MOVE executeTask found no troup at the execute to move [X: "+executeCoordinate.X+" - Y: "+executeCoordinate.Y+"]", true); }
		
	}

	@Override
	public void performAnimation() {
		
		if(this.blockedTarget == true) {
			//BLOCKED BY OTHER TASK
			if(this.playerID == ProfilData.thisClient.getID()) {
				//YOUR TASK SO MESSAGE
				Funktions.moveScreenToFieldCoordinates(targetCoordinate.X, targetCoordinate.Y);
				List<String> text = new ArrayList<String>();
				text.add("A MOVE could not be done at "+targetCoordinate.X+":"+targetCoordinate.Y+"");
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
			new Animation_MovingCircleDisplay(MovingCircleDisplayTypes.Move, 0, executeCoordinate, targetCoordinate, false);
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
		
		//SWITCH COORDINATES
		this.executeCoordinate.getConnectedField().troup = null;
		movingTroup.connectedField = targetCoordinate.getConnectedField();
		this.targetCoordinate.getConnectedField().troup = movingTroup;
		
		//SIMULATION ENERGY COST (is normaly done by creating the task, sim doesnt have that) and exclude HQ
		if(this.execSimulation && this.playerID == ProfilData.thisClient.getID()) {
			if(this.movingTroup != null && this.movingTroup.energyCostPerAction > 0) {
				RoundData.currentStatsContainer.addEnergyEntry(this.movingTroup, -this.movingTroup.energyCostPerAction);
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
