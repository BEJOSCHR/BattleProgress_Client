package me.bejosch.battleprogress.client.Game.Handler;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Data.Game.UnitData;
import me.bejosch.battleprogress.client.Enum.GameActionType;
import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Enum.UpgradeType;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Game.OverAllManager;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.GameAction;
import me.bejosch.battleprogress.client.Objects.RoundStatsContainer;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Attack;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Build;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_HealAndRepair;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Move;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Produce;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Remove;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Upgrade;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.GameSyncStatus.OnTopWindow_GameSyncStatus;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.RoundSummary.OnTopWindow_RoundSummary;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;
import me.bejosch.battleprogress.client.ServerConnection.MinaClient;
import me.bejosch.battleprogress.client.Window.Animations.AnimationDisplay;

public class Game_ReconnectHandler {

	public static int targetExecuteID = -99;
	public static LinkedList<GameAction> actions = new LinkedList<>();
	
	public static void openStatusOTW() {
		
		OnTopWindowHandler.openOTW(new OnTopWindow_GameSyncStatus("Reconnecting!"), false);
		
	}
	
	public static void startReconnection() {
		
		AnimationDisplay.stopAllAnimations(); //Cancel loading/switch to menu animation
		openStatusOTW();
		ConsoleOutput.printMessageInConsole("Reconnecting to a game...", true);
		
	}
	
	public static void setGeneralGameData(String[] content) {
		
		updateGameSyncOTW("Loading player and map");
		
		// GameID ; GameModus ; PID1 ; PID2 ; PID3 ; PID4 ; MapName ; MapData
		int startGameID = Integer.parseInt(content[0]);
		SpielModus startGameMode = SpielModus.valueOf(content[1]);
		int startPlayerID_1 = Integer.parseInt(content[2]);
		int startPlayerID_2 = Integer.parseInt(content[3]);
		int startPlayerID_3 = Integer.parseInt(content[4]);
		int startPlayerID_4 = Integer.parseInt(content[5]);
		String startMapName = content[6];
		String startMapData = content[7];
		GameHandler.startGame(false, startGameID, startGameMode, startPlayerID_1, startPlayerID_2, startPlayerID_3, startPlayerID_4, startMapName, startMapData);
		
		UnitData.lastDataContainerReceived = System.currentTimeMillis();
		
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				
				long millis = System.currentTimeMillis();
				if(millis-UnitData.lastDataContainerReceived >= 1000*2) { //Last receive longer ago than 2 sec
					ConsoleOutput.printMessageInConsole("Reconnect - General done", true);
					this.cancel();
					MinaClient.sendData(698, GameData.gameID+""); //Inform server to continue to next step
				}
				
			}
		}, 0, 500);
		
	}
	
	public static void setRoundAndExecuteID(String[] content) {
		
		updateGameSyncOTW("Loading game meta data");
		
		//Between start and init of game witch to state GAME
		OverAllManager.switchTo_Game(false);
		
		// roundNumber;executeID
		int round = Integer.parseInt(content[0]);
		targetExecuteID = Integer.parseInt(content[1]);
		GameHandler.initialiseGame(false, round);
		
		actions.clear(); //Reset for next loading step
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				ConsoleOutput.printMessageInConsole("Reconnect - Meta done", true);
				MinaClient.sendData(699, GameData.gameID+""); //Inform server to continue to next step
			}
		}, 500);
		
	}
	
	public static void addGameAction(String[] content) {
		
		// action - via execute id order on add
		//playerID;type;round;x;y;newX;newY;amount;text;executeID
		GameAction action = new GameAction(content);
		actions.add(action);
		updateGameSyncOTW("Loading game progress ("+actions.size()+")");
		
		//CHECK IF LAST EXECID RECEIVED aka COMPLETE
		if(action.executeID == targetExecuteID-1) {
			//DONE - continue with delay so possible send of the last X packets are still registered
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					ConsoleOutput.printMessageInConsole("Game sync completely received!", true);
					//SORT BY EXECUTE ID
					actions.sort(new Comparator<GameAction>() {
						@Override
						public int compare(GameAction o1, GameAction o2) {
							if(o1.executeID < o2.executeID) {
								return -1;
							}else if(o1.executeID > o2.executeID) {
								return +1;
							}else {
								return 0;
							}
						}
					});
					//EXECUTE
					simulateActions();
					
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							ConsoleOutput.printMessageInConsole("Reconnect - Actions done", true);
							MinaClient.sendData(700, GameData.gameID+""); //Inform server to continue to next step
						}
					}, 500);
				}
			}, 1000);
			
		}
		
		
	}
	
	public static void simulateActions() {
		
		RoundData.currentStatsContainer = null;
		
		for(int i = 0 ; i < actions.size() ; i++) {
			
			updateGameSyncOTW("Simulating game progress ("+(i+1)+"/"+actions.size()+")");
			
			GameAction action = actions.get(i);
			
			//HANDLE RESEARCH (can happen in the middle of a round), CHAT AND FIELDPING SEPERATLY FROM ROUNDSIM
			if(action.type != GameActionType.FIELDPING && action.type != GameActionType.CHATMESSAGE && action.type != GameActionType.RESEARCH) {
				//CHECK, CREATE AND UPDATE STATSCONTAINER FOR CURRENT ROUND
				int lastRound = ( i == 0 ? 0 : actions.get(i-1).round );
				if(lastRound < action.round) {
					//NEW ROUND ( round 0 aka HQ build gets no stats container, first is 1 to be saved)
					if(lastRound != 0) { simulateRessourceProduction(); } //First round dont produce mass (hq build round only)
					if(RoundData.currentStatsContainer != null) {
						RoundData.statsContainer.put(lastRound, RoundData.currentStatsContainer);
					}
					RoundData.currentStatsContainer = new RoundStatsContainer(action.round);
				}
			}
			
			//TRANSFORM INTO EXECUTE TASKS
			ExecuteTask et = createExecuteTaskFromGameAction(action);
			
			//EXECUTE TASK
			if(et != null) {
				et.performAction();
			}else {
				//RoundEnd, FieldPing, ChatMessage, Research, Death
				switch(action.type) {
				case ROUND_END: //Do nothing realy (just needed to have min 1 action per round to sim round endings)
					ConsoleOutput.printMessageInConsole(" > Round "+action.round+" done", true);
					break;
				case CHATMESSAGE: //NOT SYNCED (YET)
					break;
				case FIELDPING: //NOT SYNCED
					break;
				case DEATH: //CALLED DIRECTLY, stats are in the b/t functions
					Building b = GameHandler.getBuildingByCoordinates(action.x, action.y);
					if(b != null) {
						b.destroy();
						break;
					}
					Troup t = GameHandler.getTroupByCoordinates(action.x, action.y);
					if(t != null) {
						t.delete(false);
					}
					break;
				case RESEARCH: //HANDLED SPERATLY HERE
					//Includes reduction of research points
					if(action.playerId == ProfilData.thisClient.getID()) {
						Game_ResearchHandler.researchUpgrade(UpgradeType.valueOf(action.text));
					}
					break;
				default: 
					ConsoleOutput.printMessageInConsole("WARNING! Simulating action with no fitting type: "+action.type+"! Continue...", true);
					break;
				}
			}
			
		}
		
	}
	
	public static void reconnectFinished() {
		
		// > Calulate ranges/areas like vision, attack, heal, build, ...
		// 2 - VIEW RANGE OF THE TROUP/BUILDING
		for(Building building : Funktions.getAllBuildingList()) { building.roundEnd_2(); }
		for(Troup troup : Funktions.getAllTroupList()) { troup.roundEnd_2(); }
		//CALCULATE VIEW RANGE
		Game_RoundHandler.calculateViewRange();
		//CALCULATE BUILD AREA
		Game_RoundHandler.calculateBuildArea();
		// 3 - OTHER RANGES OF THE TROUP/BUILDING
		for(Building building : Funktions.getAllBuildingList()) { building.roundEnd_3(); }
		for(Troup troup : Funktions.getAllTroupList()) { troup.roundEnd_3(); }
		
		//Do final eco sim
		simulateRessourceProduction();
		//Save last stats container from last simulation round
		RoundData.statsContainer.put(RoundData.currentRound-1, RoundData.currentStatsContainer);
		RoundData.currentStatsContainer = new RoundStatsContainer(RoundData.currentRound);
		//Start timer (Always the full round timer, not synced to others, they will have to wait - seems fair enough ^^)
		Game_RoundHandler.startRoundTimer();
		
		OnTopWindowHandler.closeOTW(); //JUST CLOSE WINDOW, GAME IS LOADED/SYNCED
		ConsoleOutput.printMessageInConsole("Game sync finished", true);
		
		//Open last round info panel
		OnTopWindowHandler.openOTW(new OnTopWindow_RoundSummary());
		
	}
	
	private static ExecuteTask createExecuteTaskFromGameAction(GameAction action) {
		
		switch(action.type) {
		case ROUND_END: //Handled directly
			break;
		case ATTACK:
			return new ExecuteTask_Attack(action.playerId, action.amount, new FieldCoordinates(action.x, action.y), new FieldCoordinates(action.newX, action.newY), true);
		case BUILD:
			return new ExecuteTask_Build(action.text, action.playerId, new FieldCoordinates(action.x, action.y), true);
		case CHATMESSAGE: //NOT SYNCED (YET)
			break;
		case DEATH: //Handled directly
			break;
		case FIELDPING: //NOT SYNCED
			break;
		case HEAL:
			return new ExecuteTask_HealAndRepair(action.playerId, action.amount, new FieldCoordinates(action.x, action.y), new FieldCoordinates(action.newX, action.newY), true);
		case MOVE:
			return new ExecuteTask_Move(action.playerId, new FieldCoordinates(action.x, action.y), new FieldCoordinates(action.newX, action.newY), true);
		case PRODUCE:
			return new ExecuteTask_Produce(action.text, action.playerId, new FieldCoordinates(action.x, action.y), new FieldCoordinates(action.newX, action.newY), true);
		case REMOVE:
			return new ExecuteTask_Remove(action.playerId, new FieldCoordinates(action.x, action.y), true);
		case RESEARCH: //Handled directly
			break;
		case UPGRADE:
			return new ExecuteTask_Upgrade(action.playerId, action.text, new FieldCoordinates(action.x, action.y), new FieldCoordinates(action.newX, action.newY), true);
		}
		
		return null;
	}
	
	private static void simulateRessourceProduction() {
		
		//The reduction of build, produce and upgrade cost is done in the execute tasks by a separate "execSim" routine
		//Energy cost is not synced!!!!!
		
		Game_RoundHandler.startRoundEconomicsUpdate(true);
		
	}
	
	private static void updateGameSyncOTW(String status) {
		
		if(OnTopWindowData.onTopWindow != null && OnTopWindowData.onTopWindow instanceof OnTopWindow_GameSyncStatus) {
			OnTopWindow_GameSyncStatus gss = (OnTopWindow_GameSyncStatus) OnTopWindowData.onTopWindow;
			gss.updateStatus(status);
		}else {
			ConsoleOutput.printMessageInConsole("WARNING! Wrong or none otw open during game sync! [REC] ["+status+"]", true);
		}
		
		
	}
	
}
