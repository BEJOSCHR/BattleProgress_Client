package me.bejosch.battleprogress.client.Objects.ExecuteTasks;

import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.Game.EconomicData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Enum.ExecuteTaskType;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Game.Handler.Game_UnitsHandler;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;

public class ExecuteTask_Remove extends ExecuteTask{

	public int playerID;
	
//==========================================================================================================
	/**
	 * Creates a new REMOVE ExecuteTask, which is used for executing the given task for every client in the game
	 * @param playerReference_ - {@link PlayerReference} - The player who has ordered this task
	 * @param executeCoordinate_ - {@link FieldCoordinates} - The start coordinates (where)
	 */
	public ExecuteTask_Remove(int playerID_, FieldCoordinates executeCoordinate_, boolean execSimulation) {
		super(ExecuteTaskType.Remove, execSimulation);
		
		this.playerID = playerID_;
		this.executeCoordinate = executeCoordinate_;
		
	}

	@Override
	public void performAnimation() {
		
		if(isVisible() == true) {
			//VISIBLE - SO ANIMATION EXECUTE
			Funktions.moveScreenToFieldCoordinates(executeCoordinate.X, executeCoordinate.Y);
			
			Building foundTargetBuilding = GameHandler.getBuildingByCoordinates(executeCoordinate.X, executeCoordinate.Y);
			Troup foundTargetTroup = GameHandler.getTroupByCoordinates(executeCoordinate.X, executeCoordinate.Y);
			if(foundTargetBuilding != null) {
				//BUILDING
				foundTargetBuilding.shouldBeDestroyedAtRoundEnd = true;
			}else if(foundTargetTroup != null) {
				//TROUP
				foundTargetTroup.shouldBeDeletedAtRoundEnd = true;
			}else {
				ConsoleOutput.printMessageInConsole("A REMOVE executeTask found no building or troup at the target to activate animation [X: "+executeCoordinate.X+"-Y: "+executeCoordinate.Y+"]", true);
			}
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					performAction();
				}
			}, 1000*2); //DELAY
		}else {
			//NOT VISIBLE - SO FAST EXECUTE
			performAction();
		}
		
	}
	
	@Override
	public void performAction() {
		
		if(this.performedAction == true) { return; }
		this.performedAction = true;
		
		Building foundTargetBuilding = GameHandler.getBuildingByCoordinates(executeCoordinate.X, executeCoordinate.Y);
		Troup foundTargetTroup = GameHandler.getTroupByCoordinates(executeCoordinate.X, executeCoordinate.Y);
		double cost = 0;
		
		if(foundTargetBuilding != null) {
			//BUILDING
			cost = Game_UnitsHandler.getUnitByName(foundTargetBuilding.name).kosten;
			foundTargetBuilding.destroy();
		}else if(foundTargetTroup != null) {
			//TROUP
			cost = Game_UnitsHandler.getUnitByName(foundTargetTroup.name).kosten;
			foundTargetTroup.delete(false);
		}else {
			ConsoleOutput.printMessageInConsole("A REMOVE executeTask found no building or troup at the target to remove [X: "+executeCoordinate.X+"-Y: "+executeCoordinate.Y+"]", true);
		}
		
		//REFUND
		if(this.playerID == ProfilData.thisClient.getID() && cost != 0) {
			int addAmount = (int) ( cost / 3.0 );
			EconomicData.materialAmount += addAmount;
			if(foundTargetBuilding != null) {
				RoundData.currentStatsContainer.addMassEntry(foundTargetBuilding, addAmount);
			}else if(foundTargetTroup != null) {
				RoundData.currentStatsContainer.addMassEntry(foundTargetTroup, addAmount);
			}
		}
		
		//FINISHED
		actionFinished();
		
	}
	
	@Override
	public boolean isVisible() {
		
		if(executeCoordinate.getConnectedField().visible == true) {
			//IF START IS VISIBLE
			return true;
		}else {
			//IF NO COORDINATE IS VISIBLE
			return false;
		}
		
	}
	
}
