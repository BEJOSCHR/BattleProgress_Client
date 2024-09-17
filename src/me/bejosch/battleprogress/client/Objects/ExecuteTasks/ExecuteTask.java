package me.bejosch.battleprogress.client.Objects.ExecuteTasks;

import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Enum.AnimationType;
import me.bejosch.battleprogress.client.Enum.ExecuteTaskType;
import me.bejosch.battleprogress.client.Game.Handler.Game_RoundHandler;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Window.Animations.AnimationDisplay;

public class ExecuteTask {

	public FieldCoordinates targetCoordinate = null;
	public FieldCoordinates executeCoordinate = null;
	
	public ExecuteTaskType type = null;
	public boolean animationIsRunning = false;
	public boolean performedAction = false;
	
	public boolean blockedTarget = false; //ONLY IS CHECKED AND SET FOR THE TASKS THAT NEED A FREE FIELD (BUILD, PRODUCE, MOVE)
	
	public boolean execSimulation = false;
	
//==========================================================================================================
	/**
	 * Creates a new ExecuteTask, which is used for executing the given task for every client in the game
	 * @param type - {@link ExecuteTaskType} - The type of the executeTask
	 * @param execSimulation - boolean - True means that this is not a real task but a task created and used udring game reconnection to simulate the execution
	 */
	public ExecuteTask(ExecuteTaskType type_, boolean execSimulation) {
		
		this.type = type_;
		this.execSimulation = execSimulation;
		if(!this.execSimulation) {
			addToTaskList();
		}
		
	}
	
//==========================================================================================================
	/**
	 * Called when this task should do his animation
	 */
	public void performAnimation() {}
	
//==========================================================================================================
	/**
	 * Called to skip the current Animation
	 */
	public void skipAnimation() {
		
		if(this.animationIsRunning == true) {
			AnimationDisplay.stopAnimationType(AnimationType.MovingCircleDisplay);
			this.performAction();
		}
		
	}
//==========================================================================================================
	/**
	 * Called when this task should do his action
	 */
	public void performAction() {}
	
//==========================================================================================================
	/**
	 * Gives an information whether the animation should take long or is skipped because the fields are not visible for this client
	 * @return boolean - true if it is visible, false if not - [Standard return if not overwritten: true]
	 */
	public boolean isVisible() {
		return true;
	}
	
//==========================================================================================================
	/**
	 * Called when this task had done his animation action
	 */
	public void actionFinished() {
		if(!this.execSimulation) {
			removeFromTaskList();
			//NEXT TASK - CIRCLE CLOSED
			Game_RoundHandler.performNextExecuteTask(true);
		}
	}
	
//==========================================================================================================
	/**
	 * Adds this task to the list
	 */
	public void addToTaskList() {
		switch(type) {
		case Attack:
			RoundData.allTasks_Attack.add( (ExecuteTask_Attack) this);
			break;
		case HealAndRepair:
			RoundData.allTasks_HealAndRepair.add( (ExecuteTask_HealAndRepair) this);
			break;
		case Build:
			RoundData.allTasks_Build.add( (ExecuteTask_Build) this);
			break;
		case Produce:
			RoundData.allTasks_Produce.add( (ExecuteTask_Produce) this);
			break;
		case Upgrade:
			RoundData.allTasks_Upgrade.add( (ExecuteTask_Upgrade) this);
			break;
		case Move:
			RoundData.allTasks_Move.add( (ExecuteTask_Move) this);
			break;
		case Remove:
			RoundData.allTasks_Remove.add( (ExecuteTask_Remove) this);
			break;
		}
	}
//==========================================================================================================
	/**
	 * Removes this task to the list
	 */
	public void removeFromTaskList() {
		switch(type) {
		case Attack:
			RoundData.allTasks_Attack.remove(this); //CASTING IS IN THE REMOVE METHODE - nice ;)
			break;
		case HealAndRepair:
			RoundData.allTasks_HealAndRepair.remove(this);
			break;
		case Build:
			RoundData.allTasks_Build.remove(this);
			break;
		case Produce:
			RoundData.allTasks_Produce.remove(this);
			break;
		case Upgrade:
			RoundData.allTasks_Upgrade.remove(this);
			break;
		case Move:
			RoundData.allTasks_Move.remove(this);
			break;
		case Remove:
			RoundData.allTasks_Remove.remove(this);
			break;
		}
	}
	
}
