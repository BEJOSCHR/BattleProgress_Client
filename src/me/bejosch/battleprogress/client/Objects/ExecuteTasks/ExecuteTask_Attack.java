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

public class ExecuteTask_Attack extends ExecuteTask{

	public int playerID;
	public int attackCount = 0;
	
//==========================================================================================================
	/**
	 * Creates a new ATTACK ExecuteTask, which is used for executing the given task for every client in the game
	 * @param playerReference_ - {@link PlayerReference} - The player who has ordered this task
	 * @param attackCount_ - int - The value of damage which should be done
	 * @param executeCoordinate_ - {@link FieldCoordinates} - The start coordinates (From where)
	 * @param targetCoordinate_ - {@link FieldCoordinates} - The goal coordinates (Where it goes)
	 */
	public ExecuteTask_Attack(int playerID_, int attackCount_, FieldCoordinates executeCoordinate_, FieldCoordinates targetCoordinate_) {
		super(ExecuteTaskType.Attack);
		
		this.playerID = playerID_;
		this.attackCount = attackCount_;
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
				text.add("An ATTACK could not be done at "+targetCoordinate.X+":"+targetCoordinate.Y+"");
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
			this.animationIsRunning = true;
			new Animation_MovingCircleDisplay(MovingCircleDisplayTypes.Damage, this.attackCount, executeCoordinate, targetCoordinate, false);
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
		
		Building foundTargetBuilding = GameHandler.getBuildingByCoordinates(targetCoordinate.X, targetCoordinate.Y);
		Troup foundTargetTroup = GameHandler.getTroupByCoordinates(targetCoordinate.X, targetCoordinate.Y);
		
		if(foundTargetBuilding != null) {
			//BUILDING
			foundTargetBuilding.damage(attackCount);
			
			//STATS
			if(foundTargetBuilding.playerID == ProfilData.thisClient.getID()) { 
				RoundData.currentStatsContainer.registerDamageReceived(foundTargetBuilding, attackCount);
			}
			
		}else if(foundTargetTroup != null) {
			//TROUP
			foundTargetTroup.damage(attackCount);
			
			//STATS
			if(foundTargetTroup.playerID == ProfilData.thisClient.getID()) { 
				RoundData.currentStatsContainer.registerDamageReceived(foundTargetTroup, attackCount);
			}
		}else {
			ConsoleOutput.printMessageInConsole("An ATTACK executeTask found no building or troup at the target to damage [X: "+targetCoordinate.X+"-Y: "+targetCoordinate.Y+"]", true);
		}
		
		//STATS
		Building damageExecuteBuilding = GameHandler.getBuildingByCoordinates(executeCoordinate.X, executeCoordinate.Y);
		Troup damageExecuteTroup = GameHandler.getTroupByCoordinates(executeCoordinate.X, executeCoordinate.Y);
		if(damageExecuteBuilding != null) {
			if(damageExecuteBuilding.playerID == ProfilData.thisClient.getID()) { 
				RoundData.currentStatsContainer.registerDamageDealt(damageExecuteBuilding, attackCount);
			}
		}else if(damageExecuteTroup != null) {
			if(damageExecuteTroup.playerID == ProfilData.thisClient.getID()) { 
				RoundData.currentStatsContainer.registerDamageDealt(damageExecuteTroup, attackCount);
			}
		}else {
			ConsoleOutput.printMessageInConsole("An ATTACK executeTask found no building or troup as damage source [X: "+executeCoordinate.X+"-Y: "+executeCoordinate.Y+"]", true);
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
