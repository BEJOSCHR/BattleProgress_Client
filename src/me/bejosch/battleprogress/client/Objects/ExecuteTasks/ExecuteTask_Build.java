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
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_MovingCircleDisplay;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Airport;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Artillery;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Barracks;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Converter;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Garage;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Headquarter;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Hospital;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Laboratory;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Mine;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Reactor;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Turret;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Workshop;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Field.FieldMessage;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage_Located;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.Checkbox.MouseActionArea_Checkbox_SkipAllTaskDisplays;

public class ExecuteTask_Build extends ExecuteTask{

	public String buildingName = null;
	public int playerID;
	
//==========================================================================================================
	/**
	 * Creates a new BUILD ExecuteTask, which is used for executing the given task for every client in the game
	 * @param buildingName_ - String - The name of the troup which should be produced/created
	 * @param playerReference_ - {@link PlayerReference} - The reference of the player who produces this troup
	 * @param targetCoordinate_ - {@link FieldCoordinates} - The goal coordinates (Where it goes)
	 */
	public ExecuteTask_Build(String buildingName_, int playerID_, FieldCoordinates targetCoordinate_, boolean execSimulation) {
		super(ExecuteTaskType.Build, execSimulation);
		
		this.buildingName = buildingName_;
		this.playerID = playerID_;
		//EXECUTE COORDINATE IS THE HQ PLACE FOR THE CREATE PLAYER
		this.targetCoordinate = targetCoordinate_;
		
		//CALCULATE HQ COORDINATES BZW. EXECUTE COORDINATES
		this.executeCoordinate = Funktions.getHQfieldCoordinatesByPlayerID(playerID);
		
	}

	@Override
	public void performAnimation() {
		
		if(this.blockedTarget == true) {
			//BLOCKED BY OTHER TASK
			if(this.playerID == ProfilData.thisClient.getID()) {
				//YOUR TASK SO MESSAGE
				Funktions.moveScreenToFieldCoordinates(targetCoordinate.X, targetCoordinate.Y);
				List<String> text = new ArrayList<String>();
				text.add("A BUILD could not be done at "+targetCoordinate.X+":"+targetCoordinate.Y+"");
				text.add("The field has been already used by an other task!");
				text.add("Target Building: "+buildingName);
				new InfoMessage_Located(text, ImportanceType.HIGH, targetCoordinate.X, targetCoordinate.Y, false);
				new FieldMessage("Already used", targetCoordinate.X, targetCoordinate.Y, 5);
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						actionFinished();
					}
				}, 1200);
//				actionFinished();
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
			new Animation_MovingCircleDisplay(MovingCircleDisplayTypes.Build, 0, executeCoordinate, targetCoordinate, false);
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
		
		Building newBuilding = null;
		
		//CREATE NEW BUILDING
		switch (buildingName) {
		case "Headquarter":
			newBuilding = new Building_Headquarter(playerID, targetCoordinate.getConnectedField());
			break;
		case "Mine":
			newBuilding = new Building_Mine(playerID, targetCoordinate.getConnectedField());
			break;
		case "Reactor":
			newBuilding = new Building_Reactor(playerID, targetCoordinate.getConnectedField());
			break;
		case "Turret":
			newBuilding = new Building_Turret(playerID, targetCoordinate.getConnectedField());
			break;
		case "Artillery":
			newBuilding = new Building_Artillery(playerID, targetCoordinate.getConnectedField());
			break;
		case "Hospital":
			newBuilding = new Building_Hospital(playerID, targetCoordinate.getConnectedField());
			break;
		case "Workshop":
			newBuilding = new Building_Workshop(playerID, targetCoordinate.getConnectedField());
			break;
		case "Barracks":
			newBuilding = new Building_Barracks(playerID, targetCoordinate.getConnectedField());
			break;
		case "Garage":
			newBuilding = new Building_Garage(playerID, targetCoordinate.getConnectedField());
			break;
		case "Airport":
			newBuilding = new Building_Airport(playerID, targetCoordinate.getConnectedField());
			break;
		case "Laboratory":
			newBuilding = new Building_Laboratory(playerID, targetCoordinate.getConnectedField());
			break;
		case "Converter":
			newBuilding = new Building_Converter(playerID, targetCoordinate.getConnectedField());
			break;
		default:
			ConsoleOutput.printMessageInConsole("A BUILD executeTask found no building for the given buildingName [BuildingName: "+buildingName+"]", true);
			break;
		}
		
		//SIMULATION COST REDUCTION (is normaly reduced by creating the task, sim doesnt have that) and exclude HQ
		if(newBuilding != null && this.execSimulation && this.playerID == ProfilData.thisClient.getID() && !this.buildingName.equalsIgnoreCase("Headquarter")) {
			UnitStatsContainer c = Game_UnitsHandler.getUnitByName(this.buildingName);
			EconomicData.materialAmount -= c.kosten; //No valid check requiered, we are just following the records (replay) so it was already approved
			RoundData.currentStatsContainer.addMassEntry(newBuilding, -c.kosten);
		}
		
		//STATS
		if(newBuilding != null && RoundData.currentStatsContainer != null && newBuilding.playerID == ProfilData.thisClient.getID()) {
			RoundData.currentStatsContainer.registerBuild(newBuilding);
		}
		
		//FINISHED
		actionFinished();
		
	}
	
	@Override
	public boolean isVisible() {
		
		if(targetCoordinate.getConnectedField().visible == true) {
			//IF TARGET IS VISIBLE
			return true;
		}else {
			//IF BOTH COORDINATES ARE NOT VISIBLE
			return false;
		}
		
	}
	
}
