package me.bejosch.battleprogress.client.Game.Handler;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.Game.EconomicData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Enum.ExecuteTaskType;
import me.bejosch.battleprogress.client.Enum.MovingCircleDisplayTypes;
import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.ClientPlayer;
import me.bejosch.battleprogress.client.Objects.ResourceProductionContainer;
import me.bejosch.battleprogress.client.Objects.RoundStatsContainer;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_MovingCircleDisplay;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Converter;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Headquarter;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Laboratory;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Mine;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Reactor;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Attack;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Build;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_HealAndRepair;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Move;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Produce;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Remove;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Upgrade;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.RoundSummary.OnTopWindow_RoundSummary;
import me.bejosch.battleprogress.client.Objects.Tasks.BuildMenuTasks.BuildMenuTask;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Attack;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Destroy;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Heal;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Produce;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Repair;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Attack;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Heal;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Move;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Remove;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Repair;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Upgrade;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Land.Troup_Land_Commander;
import me.bejosch.battleprogress.client.ServerConnection.MinaClient;

public class Game_RoundHandler {
	
//==========================================================================================================
	/**
	 * Set the status of this client to ready
	 */
	public static void setClientReady() {
		
		if(RoundData.clientIsReadyForThisRound == true) {
			return; //ALREADY READY
		}
		
		//RESET CURRENT DRAG AND DROP
		//	Building/Troup Task
		if(GameData.dragAndDropTaskInputActive == true) {
			if(GameData.clickedField.building != null) {
				//BUILDING
				GameData.clickedField.building.activeTask.removeFromActiveTask();
			}else if(GameData.clickedField.troup != null) {
				//TROUP
				GameData.clickedField.troup.activeTask.removeFromActiveTask();
			}
			GameData.dragAndDropTaskInputActive = false;
		}
		//	BuildMenuTask:
		if(GameData.dragAndDropInputActive_BuildingMenu == true) {
			if(GameData.currentActive_MAA_BuildingTask != null) { GameData.currentActive_MAA_BuildingTask.connectedBuildingTask.targetCoordinate = null; }
			GameData.currentActive_MAA_BuildingTask = null;
			GameData.dragAndDropInputActive_BuildingMenu = false;
		}
		
		RoundData.clientIsReadyForThisRound = true;
		RoundData.roundStatusInfo = "Waiting for other player";
		GameData.clickedField = null;
		GameData.hoveredField = null;
		
		//Sent ready packet
		MinaClient.sendData(650, ProfilData.thisClient.getID()+"; READY [Round: "+RoundData.currentRound+" | Player: "+ProfilData.thisClient.getID()+" | Name: "+ProfilData.thisClient.getName()+"]");
		
	}
//==========================================================================================================
	/**
	 * Set the status of this client to NOT ready
	 */
	public static void setClientNotReady() {
		
		if(RoundData.roundTime_Left <= 0) {
			//CANCLE NOT POSSIBLE, BECAUSE TIME RUN OUT
			return;
		}
		
		RoundData.clientIsReadyForThisRound = false;
		
		//Sent not ready packet
		MinaClient.sendData(651, ProfilData.thisClient.getID()+"; UNREADY [Round: "+RoundData.currentRound+" | Player: "+ProfilData.thisClient.getID()+" | Name: "+ProfilData.thisClient.getName()+"]");
		
	}
	
//==========================================================================================================
	/**
	 * Set the status of a player to ready
	 * @param reference - {@link PlayerReference} - The player reference of the player who is ready
	 */
	public static void setPlayerReady(int playerID) {
		if(playerID == GameData.playingPlayer[0].getID()) {
			RoundData.player_1_isReady = true;
		}else if(playerID == GameData.playingPlayer[1].getID()) {
			RoundData.player_2_isReady = true;
		}else if(playerID == GameData.playingPlayer[2].getID()) {
			RoundData.player_3_isReady = true;
		}else if(playerID == GameData.playingPlayer[3].getID()) {
			RoundData.player_4_isReady = true;
		}else {
			ConsoleOutput.printMessageInConsole("SetPlayerReady found no matching playerID ("+playerID+")", true);
		}
		
		RoundData.readyPlayerCount++;
	}
//==========================================================================================================
	/**
	 * Set the status of a player to NOT ready
	 * @param reference - {@link PlayerReference} - The player reference of the player who is NOT ready
	 */
	public static void setPlayerNotReady(int playerID) {
		if(playerID == GameData.playingPlayer[0].getID()) {
			RoundData.player_1_isReady = false;
		}else if(playerID == GameData.playingPlayer[1].getID()) {
			RoundData.player_2_isReady = false;
		}else if(playerID == GameData.playingPlayer[2].getID()) {
			RoundData.player_3_isReady = false;
		}else if(playerID == GameData.playingPlayer[3].getID()) {
			RoundData.player_4_isReady = false;
		}else {
			ConsoleOutput.printMessageInConsole("SetPlayerReady found no matching playerID ("+playerID+")", true);
		}
		
		RoundData.readyPlayerCount--;
	}
	
//==========================================================================================================
	/**
	 * Checks whther the round is changing or the player is ready
	 * @return boolean - true if it is blocked (so whther in round change or ready), false if not
	 */
	public static boolean blockedInput() {
		
		if(RoundData.clientIsReadyForThisRound == true || RoundData.roundIsChanging == true) {
			return true;
		}else {
			return false;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Start ending the round for this client btw. after all clients are ready
	 */
	public static void roundEnd() {
		
		stopRoundTimer();
		
		while(OnTopWindowData.onTopWindow != null) {
			OnTopWindowHandler.closeOTW();
		}
		
		RoundData.lastRoundMidField = new FieldCoordinates(GameHandler.get_MID_Field(false));
		RoundData.roundIsChanging = true;
		RoundData.roundStatusInfo = "All player are ready";
		GameData.clickedField = null;
		GameData.hoveredField = null;
		
		//Reset
		RoundData.clientSentBuildingTasks = 0;
		RoundData.clientSentTroupTasks = 0;
		RoundData.clientSentBuildMenuTasks = 0;
		RoundData.currentExecuteTask = null;
		RoundData.clientTasks_BuildingTasks.clear();
		RoundData.clientTasks_TroupTasks.clear();
		
		//Calculate Player execute order
		calculatePlayerExecuteOrder();
		//Start task transfer
		startTaskTransfer();
		
	}
	
//==========================================================================================================
	/**
	 * Manages the economic round ending
	 */
	private static int numberOfEcoAnimations = 0;
	public static void startRoundEconomicsUpdate() {
		
		numberOfEcoAnimations = 0;
		
		//MOVE SCREEN
		FieldCoordinates hqPosition = Funktions.getHQfieldCoordinatesByPlayerID(ProfilData.thisClient.getID());
		Funktions.moveScreenToFieldCoordinates(hqPosition.X, hqPosition.Y);
		
		//CHANGE INFO
		RoundData.roundStatusInfo = "Producing Resources";
		
		//CLEAR ENERGY
		EconomicData.energyAmount = 0;
		
		List<Building> playerBuildings = Funktions.getBuildingListByPlayerID(ProfilData.thisClient.getID());
		
		//COLLECT RESOURCES
		LinkedList<ResourceProductionContainer> rpcs_material = new LinkedList<ResourceProductionContainer>();
		LinkedList<ResourceProductionContainer> rpcs_energy = new LinkedList<ResourceProductionContainer>();
		LinkedList<ResourceProductionContainer> rpcs_research = new LinkedList<ResourceProductionContainer>();
		
		//1. MATERIAL
		for(Building building : playerBuildings) {
			if(building instanceof Building_Mine) {
				Building_Mine mine = (Building_Mine) building;
				int amount = mine.produceMass();
				RoundData.currentStatsContainer.addMassEntry(mine, amount);
				rpcs_material.add(new ResourceProductionContainer(new FieldCoordinates(mine.connectedField), amount, MovingCircleDisplayTypes.Material));
			}else if(building instanceof Building_Converter) {
				Building_Converter converter = (Building_Converter) building;
				int amount = converter.convertMaterial();
				RoundData.currentStatsContainer.addMassEntry(converter, amount);
				rpcs_material.add(new ResourceProductionContainer(new FieldCoordinates(converter.connectedField), amount, MovingCircleDisplayTypes.Material));
			}else if(building instanceof Building_Headquarter) {
				//HQ
				Building_Headquarter hq = (Building_Headquarter) building;
				int amount = hq.produceMass_HQ();
				RoundData.currentStatsContainer.addMassEntry(hq, amount);
				rpcs_material.add(new ResourceProductionContainer(new FieldCoordinates(hq.connectedField), amount, MovingCircleDisplayTypes.Material));
			}
		}
		//ENERGY
		for(Building building : playerBuildings) {
			if(building instanceof Building_Reactor) {
				Building_Reactor reactor = (Building_Reactor) building;
				int amount = reactor.produceEnergy();
				RoundData.currentStatsContainer.addEnergyEntry(reactor, amount);
				rpcs_energy.add(new ResourceProductionContainer(new FieldCoordinates(reactor.connectedField), amount, MovingCircleDisplayTypes.Energy));
			}else if(building instanceof Building_Headquarter) {
				//HQ
				Building_Headquarter hq = (Building_Headquarter) building;
				int amount = hq.produceEnergy_HQ();
				RoundData.currentStatsContainer.addEnergyEntry(hq, amount);
				rpcs_energy.add(new ResourceProductionContainer(new FieldCoordinates(hq.connectedField), amount, MovingCircleDisplayTypes.Energy));
			}
		}
		//RESEARCH
		for(Building building : playerBuildings) {
			if(building instanceof Building_Laboratory) {
				Building_Laboratory laboratory = (Building_Laboratory) building;
				int amount = laboratory.produceResearch();
				RoundData.currentStatsContainer.addResearchEntry(laboratory, amount);
				rpcs_research.add(new ResourceProductionContainer(new FieldCoordinates(laboratory.connectedField), amount, MovingCircleDisplayTypes.Research));
			}else if(building instanceof Building_Headquarter) {
				Building_Headquarter hq = (Building_Headquarter) building;
				int amount = hq.produceResearch_HQ();
				RoundData.currentStatsContainer.addResearchEntry(hq, amount);
				rpcs_research.add(new ResourceProductionContainer(new FieldCoordinates(hq.connectedField), amount, MovingCircleDisplayTypes.Research));
			}
		}
		
		//START ANIMATIONS
		numberOfEcoAnimations = rpcs_material.size()+rpcs_energy.size()+rpcs_research.size();
		int interval = 300, offset = 100;
		//RESEARCH
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				for(ResourceProductionContainer rpc : rpcs_research) {
					new Animation_MovingCircleDisplay(rpc.getType(), rpc.getAmount(), rpc.getCords(), rpc.getCords(), false);
				}
			}
		}, interval*3-offset);
		//ENERGY
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				for(ResourceProductionContainer rpc : rpcs_energy) {
					new Animation_MovingCircleDisplay(rpc.getType(), rpc.getAmount(), rpc.getCords(), rpc.getCords(), false);
				}
			}
		}, interval*2-offset);
		//MATERIAL
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				for(ResourceProductionContainer rpc : rpcs_material) {
					new Animation_MovingCircleDisplay(rpc.getType(), rpc.getAmount(), rpc.getCords(), rpc.getCords(), false);
				}
			}
		}, interval*1-offset);
		
		//THIS STARTED ALL THE ANIMATIONS CALLING THE NEXT FUNCTION TO PROCEED THE ROUND CHANGE CYCLE
		
		//FAIL SAVE - Change to new round after delay if animation didnt finish
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				startNewRound();
			}
		}, 1000*8);
		
	}
	
//==========================================================================================================
	/**
	 * Called everytime an Economics animation end
	 */
	public static void endRoundEconomicsUpdate() {
		
		numberOfEcoAnimations--;
		
		if(numberOfEcoAnimations == 0) {
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					
					Game_RoundHandler.startNewRound(); //AFTER ALL ANIMATIONS GO ON
					
				}
			}, 400); //DISPLAY DURATION
		}
		
	}
	
//==========================================================================================================
	/**
	 * Start a new round after all clients has performed their round action animations/display
	 */
	public static void startNewRound() {
		
		if(RoundData.roundIsChanging == true) {
			//ONLY IF ROUND IS IN CHANGING MODE
			RoundData.roundIsChanging = false;
			
			//CALL BUILDINGS AND TROUPS
			//	1 - DELETE
			for(Building building : Funktions.getAllBuildingList()) { building.roundEnd_1(); }
			for(Troup troup : Funktions.getAllTroupList()) { troup.roundEnd_1(); }
			//	2 - VIEW RANGE OF THE TROUP/BUILDING
			for(Building building : Funktions.getAllBuildingList()) { building.roundEnd_2(); }
			for(Troup troup : Funktions.getAllTroupList()) { troup.roundEnd_2(); }
			
			//CALCULATE VIEW RANGE
			calculateViewRange();
			//CALCULATE BUILD AREA
			calculateBuildArea();
			
			// 2 - OTHER RANGES OF THE TROUP/BUILDING
			for(Building building : Funktions.getAllBuildingList()) { building.roundEnd_3(); }
			for(Troup troup : Funktions.getAllTroupList()) { troup.roundEnd_3(); }
			
			//UPDATE STATS CONTAINER
			RoundData.statsContainer.put(RoundData.currentRound, RoundData.currentStatsContainer);
			RoundData.currentRound++;
			RoundData.currentStatsContainer = new RoundStatsContainer(RoundData.currentRound);
			
			RoundData.roundStatusInfo = "Round has finished";
			RoundData.currentlyPerformingTasks = false;
			
			RoundData.readyPlayerCount = 0;
			RoundData.clientIsReadyForThisRound = false;
			RoundData.player_1_isReady = false;
			RoundData.player_2_isReady = false;
			RoundData.player_3_isReady = false;
			RoundData.player_4_isReady = false;
			
			//Reset ExecuteTask
			RoundData.currentExecuteTask = null;
			
			//Reset BuildMenuTask:
			if(GameData.currentActive_MAA_BuildingTask != null) { GameData.currentActive_MAA_BuildingTask.connectedBuildingTask.targetCoordinate = null; }
			GameData.currentActive_MAA_BuildingTask = null;
			GameData.dragAndDropInputActive_BuildingMenu = false;
			
			//DONT!!! RESET SKIP ALL DISPLAYS
			//((MouseActionArea_Checkbox) GameHandler.getMouseActionAreaByName("Checkbox_SkipAllTaskDisplays")).checkState = false;
			
			startRoundTimer();
			
			ConsoleOutput.printMessageInConsole("Next round has started [Number: "+RoundData.currentRound+"]", true);
			Funktions.moveScreenToFieldCoordinates(RoundData.lastRoundMidField.X, RoundData.lastRoundMidField.Y-1);
			
			//CLOSE CHAT
			//WHY? GameHandler.getMouseActionAreaByName("Chat_Hide").performAction_LEFT_RELEASE();
			
			OnTopWindowHandler.openOTW(new OnTopWindow_RoundSummary());
			
		}
		
	}
	
//==========================================================================================================
	/**
	 * Starts the round timer
	 */
	public static void startRoundTimer() {
		
		if(RoundData.roundTime_Timer == null) {
			
			RoundData.roundTime_Left = GameData.roundDuration;
			
			RoundData.roundTime_Timer = new Timer();
			RoundData.roundTime_Timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					
					if(RoundData.roundTime_Left <= 0) {
						//FINISH
						setClientReady();
						stopRoundTimer();
					}else {
						//GO ON
						RoundData.roundTime_Left--;
					}
					
				}
			}, 0, 1000); //EVERY SEC
			
		}
		
	}
	
//==========================================================================================================
	/**
	 * Stop the round timer
	 */
	public static void stopRoundTimer() {
		
		if(RoundData.roundTime_Timer != null) {
			RoundData.roundTime_Left = 0;
			RoundData.roundTime_Timer.cancel();
			RoundData.roundTime_Timer = null;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Called to calculate the fields which are visible
	 */
	public static void calculateViewRange() {
		
//		ConsoleOutput.printMessageInConsole("Calculating viewRange for all...", true);
		
		GameData.visibleFields.clear();
		
		//SET ALL FALSE
		for(int x = 0 ; x < StandardData.mapWidth ; x++) {
			for(int y = 0 ; y < StandardData.mapHight ; y++) {
				GameData.gameMap_FieldList[x][y].visible = false;
			}
		}
		//BUILDINGS
		
		for(Building building : Funktions.getBuildingListByPlayerTeam(ProfilData.thisClient.getID())) {
			for(FieldCoordinates coordinate : building.viewAbleFields) {
				if(GameData.visibleFields.contains(coordinate.getConnectedField()) == false) {
					GameData.visibleFields.add(coordinate.getConnectedField());
					coordinate.getConnectedField().visible = true;
				}
			}
		}
		//TROUPS
		for(Troup troup : Funktions.getTroupListByPlayerTeam(ProfilData.thisClient.getID())) {
			for(FieldCoordinates coordinate : troup.viewAbleFields) {
				if(GameData.visibleFields.contains(coordinate.getConnectedField()) == false) {
					GameData.visibleFields.add(coordinate.getConnectedField());
					coordinate.getConnectedField().visible = true;
				}
			}
		}
		
//		ConsoleOutput.printMessageInConsole("...done!", true);
		
	}
	
//==========================================================================================================
	/**
	 * Called to calculate the fields which are buildable
	 */
	public static void calculateBuildArea() {
		
//		ConsoleOutput.printMessageInConsole("Calculating buildArea for all...", true);
		
		List<FieldCoordinates> buildArea_ = new ArrayList<FieldCoordinates>();
		
		//BUILDINGS
		for(Building building : Funktions.getBuildingListByPlayerTeam(ProfilData.thisClient.getID())) {
			//ONLY HQ
			if(building instanceof Building_Headquarter) {
				for(int i = -building.actionRange ; i <= building.actionRange ; i++) {
					for(int j = -building.actionRange ; j <= building.actionRange ; j++) {
						int x = building.connectedField.X+i;
						int y = building.connectedField.Y+j;
						if(x < 0 || x >= StandardData.mapWidth || y < 0 || y >= StandardData.mapHight) {
							//OUTOFBOUNCE
							continue;
						}else {
							buildArea_.add(new FieldCoordinates(x, y));
						}
					}
				}
			}
		}
		//TROUPS
		for(Troup troup : Funktions.getTroupListByPlayerTeam(ProfilData.thisClient.getID())) {
			//ONLY COMMANDER
			if(troup instanceof Troup_Land_Commander) {
				for(int i = -troup.actionRange ; i <= troup.actionRange ; i++) {
					for(int j = -troup.actionRange ; j <= troup.actionRange ; j++) {
						int x = troup.connectedField.X+i;
						int y = troup.connectedField.Y+j;
						if(x < 0 || x >= StandardData.mapWidth || y < 0 || y >= StandardData.mapHight) {	//OUTOFBOUNCE
							continue;
						}else {
							buildArea_.add(new FieldCoordinates(x, y));
						}
					}
				}
			}
		}

		GameData.buildArea = buildArea_;
//		ConsoleOutput.printMessageInConsole("...done!", true);
		
	}
	
//==========================================================================================================
	/**
	 * Called every time a task was synchronised from the server
	 */
	public static void receivedATask() {
		
		RoundData.roundStatusInfo = "Synchronising tasks ("+(RoundData.allTasks_Attack.size()+RoundData.allTasks_HealAndRepair.size()+RoundData.allTasks_Build.size()+RoundData.allTasks_Produce.size()+RoundData.allTasks_Move.size()+RoundData.allTasks_Remove.size())+")";
		
	}
	
//==========================================================================================================
	/**
	 * Calculates the order in which the task of the player are executed (it changes every round for one place as a circle)
	 */
	public static void calculatePlayerExecuteOrder() {
		
		int currentRound = RoundData.currentRound - 1;
		
		if(SpielModus.isGameModus1v1()) {
			switch(currentRound % 2) { //MODULO OF 2 [mod(2)] (SO CIRCLE OF 0,1,0,1,0,1...)
			case 0:
				// 1 , 2
				RoundData.firstPlayerForThisRound = GameData.playingPlayer[0];
				break;
			case 1:
				// 2 , 1
				RoundData.firstPlayerForThisRound = GameData.playingPlayer[1];
				break;
			}
		}else {
			switch(currentRound % 4) { //MODULO OF 4 [mod(4)] (SO CIRCLE OF 0,1,2,3,0,1...)
			case 0:
				// 1 , 2 , 3 , 4
				RoundData.firstPlayerForThisRound = GameData.playingPlayer[0];
				break;
			case 1:
				// 2 , 3 , 4 , 1
				RoundData.firstPlayerForThisRound = GameData.playingPlayer[1];
				break;
			case 2:
				// 3 , 4 , 1 , 2
				RoundData.firstPlayerForThisRound = GameData.playingPlayer[2];
				break;
			case 3:
				// 4 , 1 , 2 , 3
				RoundData.firstPlayerForThisRound = GameData.playingPlayer[3];
				break;
			}
		}
		
		//SET FRIST EXECUTE TASK TYPE
		RoundData.currentExecutedTasks = ExecuteTaskType.Attack;
		//SET FRIST EXECUTE PLAYER
		RoundData.switchedPlayerForThisTask = 0;
		RoundData.currentActivePlayer = RoundData.firstPlayerForThisRound;
		
	}
	
//==========================================================================================================
	/**
	 * Checks every task for already used targetCoordinates
	 */
	public static void checkTaskTargetCoordinates() {
		
		List<ExecuteTaskType> singleTargetUseTypes = new ArrayList<ExecuteTaskType>();
		//ATTENTION: THE ADD ORDER IS IMPORTANT!
		//IF THERE IS A DOUBLE USE... THE FIST ADDED TYPE HAS PRIORITY!
		singleTargetUseTypes.add(ExecuteTaskType.Build);
		singleTargetUseTypes.add(ExecuteTaskType.Produce);
		singleTargetUseTypes.add(ExecuteTaskType.Move);
		
		RoundData.blockedTasks = 0;
		
		ClientPlayer lastCheckedPlayer = RoundData.firstPlayerForThisRound;
		List<FieldCoordinates> alreadyUseTargetCoordinates = new ArrayList<FieldCoordinates>();
		
		while(true) {
			//SO LONG UNTIL ALL PLAYER TASKS ARE CHECKED
			for(ExecuteTaskType type : singleTargetUseTypes) {
				
				for(ExecuteTask task : getExecuteTasksByTypeAndPlayerReference(type, lastCheckedPlayer)) {
					//ALL TASK WITH THE TYPE AND THE PLAYER
					if(task.targetCoordinate != null) {
						//ZB REMOVE TASKS DOES NOT HAVE A TARGET SO IT'S NULL
						if(checkListContainsFieldCoordinate(alreadyUseTargetCoordinates, task.targetCoordinate) == true) {
							//IS BLOCKED!
							task.blockedTarget = true;
							RoundData.blockedTasks++;
						}else {
							//NOT BLOCKED - SO ADD TO LIST
							alreadyUseTargetCoordinates.add(task.targetCoordinate);
						}
					}
				}
				
			}
			lastCheckedPlayer = getNextExecutePlayer(lastCheckedPlayer);
			if(lastCheckedPlayer.getID() == RoundData.firstPlayerForThisRound.getID()) { break; } //IF THE START IS REACHED AGAIN (FULL TURN) END LOOP
		}
		
	}
	
//==========================================================================================================
	/**
	 * Checks if a fieldCoordinate is in a list (whether a fieldCoordinate with the same  X and Y coordinates is in the list)
	 * @param list - List(FieldCoordinates) - The list of coordinates which should be checked
	 * @param target - {@link FieldCoordinates} - The checked fieldCoordinate
	 * @return boolean - true if the same coordinates are found in the list
	 */
	public static boolean checkListContainsFieldCoordinate(List<FieldCoordinates> list, FieldCoordinates target) {
		
		for(FieldCoordinates fieldCoordinate : list) {
			if(fieldCoordinate.X == target.X && fieldCoordinate.Y == target.Y) {
				//HIT
				return true;
			}
		}
		return false;
		
	}
	
//==========================================================================================================
	/**
	 * Get the list of {@link ExecuteTask} with the given type and the given owner
	 * @param type - {@link ExecuteTaskType} - The type of output tasks
	 * @param clientPlayer - {@link PlayerReference} - The player who ownes the output tasks
	 * @return List(ExecuteTask) - The list with all tasks of the given type and the given owner
	 */
	public static List<ExecuteTask> getExecuteTasksByTypeAndPlayerReference(ExecuteTaskType type, ClientPlayer clientPlayer) {
		List<ExecuteTask> output = new ArrayList<ExecuteTask>();
		switch (type) {
		case Attack:
			for(ExecuteTask_Attack task : RoundData.allTasks_Attack) { 
				if(task.playerID == clientPlayer.getID()) {
					output.add( (ExecuteTask) task ); 
				}
			}
			break;
		case HealAndRepair:
			for(ExecuteTask_HealAndRepair task : RoundData.allTasks_HealAndRepair) {
				if(task.playerID == clientPlayer.getID()) {
					output.add( (ExecuteTask) task ); 
				}
			}
			break;
		case Build:
			for(ExecuteTask_Build task : RoundData.allTasks_Build) { 
				if(task.playerID == clientPlayer.getID()) {
					output.add( (ExecuteTask) task ); 
				}
			}
			break;
		case Produce:
			for(ExecuteTask_Produce task : RoundData.allTasks_Produce) { 
				if(task.playerID == clientPlayer.getID()) {
					output.add( (ExecuteTask) task ); 
				}
			}
			break;
		case Upgrade:
			for(ExecuteTask_Upgrade task : RoundData.allTasks_Upgrade) { 
				if(task.playerID == clientPlayer.getID()) {
					output.add( (ExecuteTask) task ); 
				}
			}
			break;
		case Move:
			for(ExecuteTask_Move task : RoundData.allTasks_Move) { 
				if(task.playerID == clientPlayer.getID()) {
					output.add( (ExecuteTask) task ); 
				}
			}
			break;
		case Remove:
			for(ExecuteTask_Remove task : RoundData.allTasks_Remove) { 
				if(task.playerID == clientPlayer.getID()) {
					output.add( (ExecuteTask) task ); 
				}
			}
			break;
		}
		return output;
	}
	
//==========================================================================================================
	/**
	 * Starts the transfer of all tasks from the clients to the server and back
	 */
	public static void startTaskTransfer() {
		
		RoundData.roundStatusInfo = "Synchronising tasks ("+(RoundData.allTasks_Attack.size()+RoundData.allTasks_HealAndRepair.size()+RoundData.allTasks_Build.size()+RoundData.allTasks_Produce.size()+RoundData.allTasks_Move.size()+RoundData.allTasks_Upgrade.size()+RoundData.allTasks_Remove.size())+")";
		
		//COLLECT TASKS
		collectBuildingTasks();
		collectTroupTasks();
		
		//SENT TASKS
		//========== BUILDINGS
		for(Task_Building task : RoundData.clientTasks_BuildingTasks) {
			if(task instanceof Task_Building_Attack) {
				//ATTACK
				// startX ; startY ; goalX ; goalY ; Count
				String data_Building_Attack = task.building.connectedField.X+";"+task.building.connectedField.Y+";"+task.targetCoordinates.X+";"+task.targetCoordinates.Y+";"+task.building.damage;
				MinaClient.sendData(600, data_Building_Attack);
			}else if(task instanceof Task_Building_Heal) {
				//HEAL
				// startX ; startY ; goalX ; goalY ; Count
				String data_Building_Heal = task.building.connectedField.X+";"+task.building.connectedField.Y+";"+task.targetCoordinates.X+";"+task.targetCoordinates.Y+";"+task.building.heal;
				MinaClient.sendData(601, data_Building_Heal);
			}else if(task instanceof Task_Building_Repair) {
				//REPAIR
				// startX ; startY ; goalX ; goalY ; Count
				String data_Building_Repair = task.building.connectedField.X+";"+task.building.connectedField.Y+";"+task.targetCoordinates.X+";"+task.targetCoordinates.Y+";"+task.building.repair;
				MinaClient.sendData(601, data_Building_Repair);
			}else if(task instanceof Task_Building_Produce) {
				//PRODUCE
				// troupName ; startX ; startY ; goalX ; goalY
				String troupName = ((Task_Building_Produce) task).troupName;
				String data_Building_Produce = troupName+";"+task.building.connectedField.X+";"+task.building.connectedField.Y+";"+task.targetCoordinates.X+";"+task.targetCoordinates.Y;
				MinaClient.sendData(603, data_Building_Produce);
			}else if(task instanceof Task_Building_Destroy) {
				//DESTROY
				// goalX ; goalY
				String data_Building_Destroy = task.building.connectedField.X+";"+task.building.connectedField.Y;
				MinaClient.sendData(605, data_Building_Destroy);
			}
			RoundData.clientSentBuildingTasks++;
		}
		//========== TROUPS
		for(Task_Troup task : RoundData.clientTasks_TroupTasks) {
			if(task instanceof Task_Troup_Attack) {
				//ATTACK
				// startX ; startY ; goalX ; goalY ; Count
				String data_Troup_Attack = task.troup.connectedField.X+";"+task.troup.connectedField.Y+";"+task.targetCoordinates.X+";"+task.targetCoordinates.Y+";"+task.troup.damage;
				MinaClient.sendData(600, data_Troup_Attack);
			}else if(task instanceof Task_Troup_Heal) {
				//HEAL
				// startX ; startY ; goalX ; goalY ; Count
				String data_Troup_Heal = task.troup.connectedField.X+";"+task.troup.connectedField.Y+";"+task.targetCoordinates.X+";"+task.targetCoordinates.Y+";"+task.troup.heal;
				MinaClient.sendData(601, data_Troup_Heal);
			}else if(task instanceof Task_Troup_Repair) {
				//REPAIR
				// startX ; startY ; goalX ; goalY ; Count
				String data_Troup_Repair = task.troup.connectedField.X+";"+task.troup.connectedField.Y+";"+task.targetCoordinates.X+";"+task.targetCoordinates.Y+";"+task.troup.repair;
				MinaClient.sendData(601, data_Troup_Repair);
			}else if(task instanceof Task_Troup_Move) {
				//MOVE
				// startX ; startY ; goalX ; goalY
				String data_Troup_Move = task.troup.connectedField.X+";"+task.troup.connectedField.Y+";"+task.targetCoordinates.X+";"+task.targetCoordinates.Y;
				MinaClient.sendData(604, data_Troup_Move);
			}else if(task instanceof Task_Troup_Remove) {
				//REMOVE
				// goalX ; goalY
				String data_Troup_Remove = task.troup.connectedField.X+";"+task.troup.connectedField.Y;
				MinaClient.sendData(605, data_Troup_Remove);
			}else if(task instanceof Task_Troup_Upgrade) {
				//UPGRADE
				// upgradeTroupName ; startX ; startY ; goalX ; goalY
				String data_Troup_Upgrade = ((Task_Troup_Upgrade) task).upgradeTroupName+";"+task.troup.connectedField.X+";"+task.troup.connectedField.Y+";"+task.targetCoordinates.X+";"+task.targetCoordinates.Y;
				MinaClient.sendData(606, data_Troup_Upgrade);
			}
			RoundData.clientSentTroupTasks++;
		}
		//========== BUILD MENU
		if(GameData.currentActive_MAA_BuildingTask != null) {
			//HAS TASK TO BUILD
			// buildingName ; goalX ; goalY
			BuildMenuTask activeTask = GameData.currentActive_MAA_BuildingTask.connectedBuildingTask;
			String data_Build = activeTask.name+";"+activeTask.targetCoordinate.X+";"+activeTask.targetCoordinate.Y;
			MinaClient.sendData(602, data_Build);
			//STATS
			Point hqCords = GameHandler.getHQCoordinates();
			Building hq = GameData.gameMap_FieldList[hqCords.x][hqCords.y].building;
			RoundData.currentStatsContainer.addMassEntry(hq, -activeTask.cost);
		}
		
		//Send finish packet
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				MinaClient.sendData(653, "All tasks sent [Round: "+RoundData.currentRound+" | Player: "+ProfilData.thisClient.getID()+" | Name: "+ProfilData.thisClient.getName()+"]");
			}
		}, 1000*1); //SAVE DELAY
		
	}
	
//==========================================================================================================
	/**
	 * Called after all tasks are transfered from the clients to the server and back
	 */
	public static void taskTransferComplete() {
		
		//CHECK FOR BLOCKED TASKS
		//Block task with an already used targetCoordinate (Of the type: BUILD,PRODUCE,MOVE)
		checkTaskTargetCoordinates();
		
		//SET TOTAL TASK COUNTS
		RoundData.totalAttackTasks = RoundData.allTasks_Attack.size();
		RoundData.totalHealAndRepairTasks = RoundData.allTasks_HealAndRepair.size();
		RoundData.totalBuildTasks = RoundData.allTasks_Build.size();
		RoundData.totalProduceTasks = RoundData.allTasks_Produce.size();
		RoundData.totalUpgradeTasks= RoundData.allTasks_Upgrade.size();
		RoundData.totalMoveTasks = RoundData.allTasks_Move.size();
		RoundData.totalRemoveTasks = RoundData.allTasks_Remove.size();
		
		ConsoleOutput.printMessageInConsole("Round ["+RoundData.currentRound+"] is ending:", true);
		ConsoleOutput.printMessageInConsole("This client sent: [buildingTasks: "+RoundData.clientSentBuildingTasks+"] [troupTasks: "+RoundData.clientSentTroupTasks+"]", true);
		ConsoleOutput.printMessageInConsole("This client receives:", true);
		ConsoleOutput.printMessageInConsole("- Attack: "+RoundData.totalAttackTasks+"", true);
		ConsoleOutput.printMessageInConsole("- HealAndRepair: "+RoundData.totalHealAndRepairTasks+"", true);
		ConsoleOutput.printMessageInConsole("- Build: "+RoundData.totalBuildTasks+"", true);
		ConsoleOutput.printMessageInConsole("- Produce: "+RoundData.totalProduceTasks+"", true);
		ConsoleOutput.printMessageInConsole("- Upgrade: "+RoundData.totalUpgradeTasks+"", true);
		ConsoleOutput.printMessageInConsole("- Move: "+RoundData.totalMoveTasks+"", true);
		ConsoleOutput.printMessageInConsole("- Remove: "+RoundData.totalRemoveTasks+"", true);
		ConsoleOutput.printMessageInConsole("Blocked tasks: "+RoundData.blockedTasks+"", true);
		
		//START TASK EXECUTION
		RoundData.currentlyPerformingTasks = true;
		RoundData.roundStatusInfo = "   ATTACK   ";
		performNextExecuteTask(false);
		
	}
	
//==========================================================================================================
	/**
	 * Called every time an execute task has finished his action
	 */
	public static void performNextExecuteTask(boolean withDelay) {
		
		int delay = 250;
		if(withDelay == true) { delay = 400; }
		
		//DELAY 
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				
				if(checkPlayerTaskTypeForEmpty(RoundData.currentActivePlayer, RoundData.currentExecutedTasks) == 0) {
					//NO TASKS FOR THIS TYPE AND PLAYER LEFT
					if(getMaxPlayerSwitchedPerTaskType() == RoundData.switchedPlayerForThisTask) {
						//NEXT TASK TYPE
						if(getNextExecuteTaskType(RoundData.currentExecutedTasks) == null) {
							//ALL TASKS ARE EXECUTED
							//Sent finish message to server
							RoundData.roundStatusInfo = "Waiting for other player";
							RoundData.currentActivePlayer = null;
							RoundData.currentExecuteTask = null;
							MinaClient.sendData(654, "All tasks executed [Round: "+RoundData.currentRound+" | Player: "+ProfilData.thisClient.getID()+" | Name: "+ProfilData.thisClient.getName()+"]");
							return;
						}else {
							//NEXT TYPE
							RoundData.switchedPlayerForThisTask = 0;
							RoundData.currentActivePlayer = RoundData.firstPlayerForThisRound;
							RoundData.currentExecutedTasks = getNextExecuteTaskType(RoundData.currentExecutedTasks);
							performNextExecuteTask(false); //RE CALL AFTER CHANGES
							return;
						}
					}else {
						//NEXT PLAYER
						RoundData.switchedPlayerForThisTask++;
						RoundData.currentActivePlayer = getNextExecutePlayer(RoundData.currentActivePlayer);
						performNextExecuteTask(false); // RE CALL AFTER CHANGES
						return;
					}
				} //NOTHING TO CHANGE
				
				switch (RoundData.currentExecutedTasks) {
				case Attack:
					//1. ATTACK TASKS
					for(ExecuteTask_Attack task : RoundData.allTasks_Attack) {
						if(task.playerID == RoundData.currentActivePlayer.getID()) {
							RoundData.roundStatusInfo = "Performing ATTACK tasks ("+(RoundData.totalAttackTasks-RoundData.allTasks_Attack.size()+1)+"/"+RoundData.totalAttackTasks+")";
							RoundData.currentExecuteTask = task;
							RoundData.currentExecuteTask.performAnimation();
							return;
						}
					}
					break;
				case HealAndRepair:
					//2. Heal/Repair TASKS
					for(ExecuteTask_HealAndRepair task : RoundData.allTasks_HealAndRepair) {
						if(task.playerID == RoundData.currentActivePlayer.getID()) {
							RoundData.roundStatusInfo = "Performing HEAL/REPAIR tasks ("+(RoundData.totalHealAndRepairTasks-RoundData.allTasks_HealAndRepair.size()+1)+"/"+RoundData.totalHealAndRepairTasks+")";
							RoundData.currentExecuteTask = task;
							RoundData.currentExecuteTask.performAnimation();
							return;
						}
					}
					break;
				case Build:
					//3. BUILD TASKS
					for(ExecuteTask_Build task : RoundData.allTasks_Build) {
						if(task.playerID == RoundData.currentActivePlayer.getID()) {
							RoundData.roundStatusInfo = "Performing BUILD tasks ("+(RoundData.totalBuildTasks-RoundData.allTasks_Build.size()+1)+"/"+RoundData.totalBuildTasks+")";
							RoundData.currentExecuteTask = task;
							RoundData.currentExecuteTask.performAnimation();
							return;
						}
					}
					break;
				case Produce:
					//4. PRODUCE TASKS
					for(ExecuteTask_Produce task : RoundData.allTasks_Produce) {
						if(task.playerID == RoundData.currentActivePlayer.getID()) {
							RoundData.roundStatusInfo = "Performing PRODUCE tasks ("+(RoundData.totalProduceTasks-RoundData.allTasks_Produce.size()+1)+"/"+RoundData.totalProduceTasks+")";
							RoundData.currentExecuteTask = task;
							RoundData.currentExecuteTask.performAnimation();
							return;
						}
					}
					break;
				case Upgrade:
					//5. UPGRADE TASKS
					for(ExecuteTask_Upgrade task : RoundData.allTasks_Upgrade) {
						if(task.playerID == RoundData.currentActivePlayer.getID()) {
							RoundData.roundStatusInfo = "Performing UPGRADE tasks ("+(RoundData.totalUpgradeTasks-RoundData.allTasks_Upgrade.size()+1)+"/"+RoundData.totalUpgradeTasks+")";
							RoundData.currentExecuteTask = task;
							RoundData.currentExecuteTask.performAnimation();
							return;
						}
					}
					break;
				case Move:
					//6. MOVE TASKS
					for(ExecuteTask_Move task : RoundData.allTasks_Move) {
						if(task.playerID == RoundData.currentActivePlayer.getID()) {
							RoundData.roundStatusInfo = "Performing MOVE tasks ("+(RoundData.totalMoveTasks-RoundData.allTasks_Move.size()+1)+"/"+RoundData.totalMoveTasks+")";
							RoundData.currentExecuteTask = task;
							RoundData.currentExecuteTask.performAnimation();
							return;
						}
					}
					break;
				case Remove:
					//7. REMOVE TASKS
					for(ExecuteTask_Remove task : RoundData.allTasks_Remove) {
						if(task.playerID == RoundData.currentActivePlayer.getID()) {
							RoundData.roundStatusInfo = "Performing REMOVE tasks ("+(RoundData.totalRemoveTasks-RoundData.allTasks_Remove.size()+1)+"/"+RoundData.totalRemoveTasks+")";
							RoundData.currentExecuteTask = task;
							RoundData.currentExecuteTask.performAnimation();
							return;
						}
					}
					break;
				}
				
			}
		}, delay); //DELAY BETWEEN EACH TASK
		
	}
	
//==========================================================================================================
	/**
	 * Gives back how many player switched are allowed for every task type
	 * @return int - The count how many player switched are allwed for every task type (1vs1 = 2 ; 2vs2 = 4)
	 */
	public static int getMaxPlayerSwitchedPerTaskType() {
		if(SpielModus.isGameModus1v1()) {
			return 2;
		}else {
			return 4;
		}
	}
	
//==========================================================================================================
	/**
	 * Gives back whether there are tasks of the given type for the given player left or not
	 * @param player - {@link PlayerProfil} - The player whos tasks should be checked
	 * @param type - {@link ExecuteTaskType} - The type of tasks which sould be checked
	 * @return int - The count of tasks which are left (For the given type and given player)
	 */
	public static int checkPlayerTaskTypeForEmpty(ClientPlayer player, ExecuteTaskType type) {
		
		int countedTasks = 0;
		
		switch(type) {
		case Attack:
			for(ExecuteTask_Attack task : RoundData.allTasks_Attack) { if(task.playerID == player.getID()) { countedTasks++; } }
			break;
		case HealAndRepair:
			for(ExecuteTask_HealAndRepair task : RoundData.allTasks_HealAndRepair) { if(task.playerID == player.getID()) { countedTasks++; } }
			break;
		case Build:
			for(ExecuteTask_Build task : RoundData.allTasks_Build) { if(task.playerID == player.getID()) { countedTasks++; } }
			break;
		case Produce:
			for(ExecuteTask_Produce task : RoundData.allTasks_Produce) { if(task.playerID == player.getID()) { countedTasks++; } }
			break;
		case Upgrade:
			for(ExecuteTask_Upgrade task : RoundData.allTasks_Upgrade) { if(task.playerID == player.getID()) { countedTasks++; } }
			break;
		case Move:
			for(ExecuteTask_Move task : RoundData.allTasks_Move) { if(task.playerID == player.getID()) { countedTasks++; } }
			break;
		case Remove:
			for(ExecuteTask_Remove task : RoundData.allTasks_Remove) { if(task.playerID == player.getID()) { countedTasks++; } }
			break;
		}
		
		return countedTasks;
		
	}
//==========================================================================================================
	/**
	 * Gives back the next ExecuteTaskType refered to the last ExecuteTaskType
	 * @param lastType - {@link ExecuteTaskType} - The last ExecuteTaskType
	 * @return {@link ExecuteTaskType} - The next ExecuteTaskType after the given one
	 */
	public static ExecuteTaskType getNextExecuteTaskType(ExecuteTaskType lastType) {
		
		switch(lastType) {
		case Attack:
			RoundData.roundStatusInfo = "HEAL / REPAIR";
			return ExecuteTaskType.HealAndRepair;
		case HealAndRepair:
			RoundData.roundStatusInfo = "    BUILD    ";
			return ExecuteTaskType.Build;
		case Build:
			RoundData.roundStatusInfo = "   PRODUCE   ";
			return ExecuteTaskType.Produce;
		case Produce:
			RoundData.roundStatusInfo = "   UPGRADE   ";
			return ExecuteTaskType.Upgrade;
		case Upgrade:
			RoundData.roundStatusInfo = "    MOVE    ";
			return ExecuteTaskType.Move;
		case Move:
			RoundData.roundStatusInfo = "   REMOVE   ";
			return ExecuteTaskType.Remove;
		case Remove:
			return null;
		}
		return null;
		
	}
//==========================================================================================================
	/**
	 * Gives back the next player refered to the last execute player
	 * @param lastPlayer - {@link PlayerProfil} - The last player who has executed his tasks
	 * @return {@link PlayerProfil} - The next player after the given one
	 */
	public static ClientPlayer getNextExecutePlayer(ClientPlayer lastPlayer) {
		
		if(lastPlayer.getID() == GameData.playingPlayer[0].getID()) {
			//Player1
			return GameData.playingPlayer[1];
		}else if(lastPlayer.getID() == GameData.playingPlayer[1].getID()) {
			//Player2
			if(SpielModus.isGameModus1v1()) {
				//1v1
				return GameData.playingPlayer[0];
			}else {
				//2v2
				return GameData.playingPlayer[2];
			}
		}else if(lastPlayer.getID() == GameData.playingPlayer[2].getID()) {
			//Player3
			return GameData.playingPlayer[3];
		}else if(lastPlayer.getID() == GameData.playingPlayer[3].getID()) {
			//Player4
			return GameData.playingPlayer[0];
		}else {
			ConsoleOutput.printMessageInConsole("getnextExecPlayer found no matching playerID ("+lastPlayer.getID()+")", true);
			return null;
		}
		
	}
	
	
	
	
	
//==========================================================================================================
	/**
	 * Collect all tasks - BUILDINGS
	 */
	public static void collectBuildingTasks() {
		
		for(Building building : Funktions.getBuildingListByPlayerID(ProfilData.thisClient.getID())) {
			Task_Building task = building.activeTask;
			if(task != null) {
				
				RoundData.clientTasks_BuildingTasks.add(task);
				
				//STATS
				if(task.costsMass) {
					if(task instanceof Task_Building_Produce) {
						Task_Building_Produce prodTask = (Task_Building_Produce) task;
						RoundData.currentStatsContainer.addMassEntry(building, -prodTask.troupProduceCost);
					}
				}
				if(task.costsEnergy) {
					RoundData.currentStatsContainer.addEnergyEntry(building, -building.energyCostPerAction);
				}
			}
		}
		
	}
//==========================================================================================================
	/**
	 * Collect all tasks - TROUPS
	 */
	public static void collectTroupTasks() {
		
		for(Troup troup : Funktions.getTroupListByPlayerID(ProfilData.thisClient.getID())) {
			Task_Troup task = troup.activeTask;
			if(task != null) {
				
				RoundData.clientTasks_TroupTasks.add(task);
				
				//STATS
				if(task.costsMass) {
					if(task instanceof Task_Troup_Upgrade) {
						Task_Troup_Upgrade upgTask = (Task_Troup_Upgrade) task;
						RoundData.currentStatsContainer.addMassEntry(troup, -upgTask.upgradeTroupCost);
					}
				}
				if(task.costsEnergy) {
					RoundData.currentStatsContainer.addEnergyEntry(troup, -troup.energyCostPerAction);
				}
			}
		}
		
	}
	
//==========================================================================================================
	/**
	 * Checks whther a player is ready or not
	 * @param playerNumber - int the number (1,2,3,4) of the checked player
	 * @return boolean - true if he is ready, false if not
	 */
	public static boolean checkPlayerReadyByNumber(int playerNumber) {
		
		switch(playerNumber) {
		case 1: return RoundData.player_1_isReady;
		case 2: return RoundData.player_2_isReady;
		case 3: return RoundData.player_3_isReady;
		case 4: return RoundData.player_4_isReady;
		}
		
		return false;
	}
}
