package me.bejosch.battleprogress.client.Handler;

import java.awt.Color;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.MenuData;
import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.SpectateData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Data.Game.UnitData;
import me.bejosch.battleprogress.client.Enum.GameActionType;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Enum.SpielStatus;
import me.bejosch.battleprogress.client.Game.OverAllManager;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Game.Handler.Game_DictionaryHandler;
import me.bejosch.battleprogress.client.Game.Handler.Game_FieldDataHandler;
import me.bejosch.battleprogress.client.Game.Handler.Game_ResearchHandler;
import me.bejosch.battleprogress.client.Game.Handler.Game_UnitsHandler;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.ClientPlayer;
import me.bejosch.battleprogress.client.Objects.GameAction;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.GameSyncStatus.OnTopWindow_GameSyncStatus;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;
import me.bejosch.battleprogress.client.ServerConnection.MinaClient;

public class SpectateHandler {

	public static void startSpectate(ClientPlayer spectateTarget) {
		
		OverAllManager.switchTo_Spectate();
		
		//Open info loading oth
		OnTopWindowHandler.openOTW(new OnTopWindow_GameSyncStatus("Loading!"), false);
		//Request spectate data from server
		MinaClient.sendData(750, spectateTarget.getID()+"");
		
		//MAAS
		if(SpectateData.initSpectateMaas == false) {
			
			int X = GameData.readyButton_X, Y = GameData.readyButton_Y, maße = GameData.readyButton_maße;
			new MouseActionArea(X+25, Y+110-15, X+maße-25, Y+110+15, "MAA_Spectate_Exit", null, ShowBorderType.ShowAlways, Color.WHITE, Color.ORANGE) {
				@Override
				public boolean isActiv() {
					if(StandardData.spielStatus == SpielStatus.Spectate && SpectateData.finishedInitLoading == true) {
						return true;
					}else {
						return false;
					}
				}
				@Override
				public void performAction_LEFT_RELEASE() {
					//Stop spectate is called from the overall handler below
					OverAllManager.switchTo_Menu(true);
				}
			};
			
			SpectateData.initSpectateMaas = true;
		}
		
	}
	
	public static void stopSpectate() {
		
		//Inform server
				MinaClient.sendData(753, SpectateData.gameID+"");
		
		//Reset spectate data
		SpectateData.lastExecuteIndex = 0;
		SpectateData.actions.clear();
		SpectateData.buildings.clear();
		SpectateData.clickedField = null;
		SpectateData.hoveredField = null;
		SpectateData.finishedInitLoading = false;
		SpectateData.gameID = -1;
		SpectateData.targetExecuteID = -1;
		SpectateData.gameIsRunning = false;
		SpectateData.gameMap_FieldList = null;
		SpectateData.gameMode = null;
		SpectateData.mapName = null;
		SpectateData.playingPlayer = null;
		SpectateData.round = 1;
		SpectateData.scroll_LR_count = 0;
		SpectateData.scroll_UD_count = 0;
		
		//RoundTimer
		if(SpectateData.roundTimer != null) {
			SpectateData.roundTimer.cancel();
			SpectateData.roundTimer = null;
			SpectateData.roundTime = 0;
		}
		
		MovementHandler.stopMovementTimer();
		
	}
	
//=====================================================================================
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
		
		// --- MIMIC GAME START ---
		//CLOSE FRIEND LIST
		if(MenuData.friendListOpened == true) {
			MenuData.friendListOpened = false;
		}
		SpectateData.gameID = startGameID;
		SpectateData.gameMode = startGameMode;
		if(SpielModus.isModus1v1(SpectateData.gameMode)) {
			//1v1
			SpectateData.playingPlayer = new ClientPlayer[2];
			SpectateData.playingPlayer[0] = ClientPlayerHandler.getNewClientPlayer(startPlayerID_1);
			SpectateData.playingPlayer[1] = ClientPlayerHandler.getNewClientPlayer(startPlayerID_2);
		}else {
			//2v2
			SpectateData.playingPlayer = new ClientPlayer[4];
			SpectateData.playingPlayer[0] = ClientPlayerHandler.getNewClientPlayer(startPlayerID_1);
			SpectateData.playingPlayer[1] = ClientPlayerHandler.getNewClientPlayer(startPlayerID_2);
			SpectateData.playingPlayer[2] = ClientPlayerHandler.getNewClientPlayer(startPlayerID_3);
			SpectateData.playingPlayer[3] = ClientPlayerHandler.getNewClientPlayer(startPlayerID_4);
		}
		//LOAD UNITS
		Game_UnitsHandler.requestUnitsUpdate();
		//LOAD UPGRADE DATA CONTAINER
		Game_ResearchHandler.loadUpgradeDataContainer();
		//LOAD DictionaryInfoDescriptions
		Game_DictionaryHandler.loadDictionaryInfoDescriptions();
		//LOAD FIELD DATA
		Game_FieldDataHandler.loadFieldData();
		//MAP TRANSFER
		SpectateData.mapName = startMapName;
		SpectateData.gameMap_FieldList = GameHandler.readOutFieldDataToLoadedMap(startMapData);
		for(int x = 0 ; x < StandardData.mapWidth ; x++) { //Make all fields visible
			for(int y = 0 ; y < StandardData.mapHight ; y++) {
				SpectateData.gameMap_FieldList[x][y].visible = true;
			}
		}
		//START MOVEMENT HANDLER
		MovementHandler.startMovementTimer();
		
		UnitData.lastDataContainerReceived = System.currentTimeMillis();
		
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				
				long millis = System.currentTimeMillis();
				if(millis-UnitData.lastDataContainerReceived >= 1000*2) { //Last receive longer ago than 2 sec
					ConsoleOutput.printMessageInConsole("Spectate - General done", true);
					this.cancel();
					MinaClient.sendData(751, SpectateData.gameID+""); //Inform server to continue to next step
				}
				
			}
		}, 0, 500);
		
	}
	
	public static void setRoundAndExecuteID(String[] content) {
		
		updateGameSyncOTW("Loading game meta data");
		
		//Already switched to spectate
		
		// roundNumber;executeID
		SpectateData.round = Integer.parseInt(content[0]);
		SpectateData.targetExecuteID = Integer.parseInt(content[1]);
		
		//Simulate game init
		//Nothing realy to do from that stuff
		
		SpectateData.actions.clear(); //Reset for next loading step
		SpectateData.buildings.clear();
		SpectateData.troups.clear();
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				ConsoleOutput.printMessageInConsole("Spectate - Meta done", true);
				MinaClient.sendData(752, SpectateData.gameID+""); //Inform server to continue to next step
			}
		}, 500);
		
	}
	
	//Called for every action receivved, also the actions after firstinit
	public static void addGameAction(String[] content) {
		
		// action - via execute id order on add
		//playerID;type;round;x;y;newX;newY;amount;text;executeID
		GameAction action = new GameAction(content);
		SpectateData.actions.add(action);
		
		if(SpectateData.finishedInitLoading == false) {
			//CHECK IF LAST EXECID RECEIVED aka COMPLETE
			updateGameSyncOTW("Loading game progress ("+SpectateData.actions.size()+")");
			if(action.executeID == SpectateData.targetExecuteID-1) {
				//DONE
				ConsoleOutput.printMessageInConsole("Game sync completely received! [Spectate]", true);
				//SORT BY EXECUTE ID (Could be wrongly ordered during server client transfer)
				SpectateData.actions.sort(new Comparator<GameAction>() {
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
				simulateActions(true);
				
				//FNISH UP
				finishInitLoading();
				
			}
		}else {
			//On the fly update - simulate everytime a round end is detected (so sym round by round with ordering!)
			
			if(action.type == GameActionType.ROUND_END) {
				ConsoleOutput.printMessageInConsole("Syncing new round ("+action.round+")! [Spectate]", true);
				//SORT BY EXECUTE ID (Could be wrongly ordered during server client transfer)
				SpectateData.actions.sort(new Comparator<GameAction>() {
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
				//THEN SIMULATE (methode includes, that nothing is double simulated)
				simulateActions(false);
			}
			
		}
		
		
	}
	
	public static void simulateActions(boolean withOtwUpdate) {
		
		ConsoleOutput.printMessageInConsole("Sim: "+SpectateData.lastExecuteIndex+" -> "+(SpectateData.actions.size()-1), true);
		for(int i = SpectateData.lastExecuteIndex ; i < SpectateData.actions.size() ; i++) {
			
			if(withOtwUpdate == true) {
				updateGameSyncOTW("Simulating game progress ("+(i+1)+"/"+SpectateData.actions.size()+")");
			}
			
			GameAction action = SpectateData.actions.get(i);
			
			//Detect round changed by "new round" action if necessary
			
			simulateAction(action);
			
		}
		
		SpectateData.lastExecuteIndex = SpectateData.actions.size();
		
	}
	
	public static void simulateAction(GameAction action) {
		
		if(action.round > SpectateData.round && action.type != GameActionType.ROUND_END) {
			//Dont sim actions which are in the future of the current round, except round change obviously
			//This can happen on join during round change of the game on server side, then actions are send
			//which are synced in a later step again and only this later step is the correct moment to sync them
			return;
		}
		
		switch(action.type) {
		
		case ATTACK:
			Building target_b = getBuilding(action.newX, action.newY);
			if(target_b != null) {
				target_b.damage(action.amount);
			}else {
				Troup target_t = getTroup(action.newX, action.newY);
				target_t.damage(action.amount);
			}
			break;
		case BUILD:
			SpectateData.buildings.add(Game_UnitsHandler.createNewBuilding(action.playerId, new FieldCoordinates(action.x, action.y), action.text));
			break;
		case HEAL:
			Building target_b2 = getBuilding(action.newX, action.newY);
			if(target_b2 != null) {
				target_b2.repair(action.amount);
			}else {
				Troup target_t2 = getTroup(action.newX, action.newY);
				target_t2.heal(action.amount);
			}
			break;
		case MOVE:
			Field oldField = SpectateData.gameMap_FieldList[action.x][action.y];
			Field newField = SpectateData.gameMap_FieldList[action.newX][action.newY];
			oldField.troup.connectedField = newField;
			newField.troup = oldField.troup;
			oldField.troup = null;
			break;
		case PRODUCE:
			SpectateData.troups.add(Game_UnitsHandler.createNewTroup(action.playerId, new FieldCoordinates(action.newX, action.newY), action.text));
			break;
		case DEATH: //Death and remove here the same, just delete the thing
		case REMOVE:
			Building target_b3 = getBuilding(action.x, action.y);
			if(target_b3 != null) {
				SpectateData.buildings.remove(target_b3);
				target_b3.connectedField.building = null;
				target_b3.connectedField = null;
			}else {
				Troup target_t3 = getTroup(action.x, action.y);
				SpectateData.troups.remove(target_t3);
				target_t3.connectedField.troup = null;
				target_t3.connectedField = null;
			}
			break;
		case UPGRADE:
			Field oldField_2 = SpectateData.gameMap_FieldList[action.x][action.y];
			Field newField_2 = SpectateData.gameMap_FieldList[action.newX][action.newY];
			int maxHealthOldCombined = oldField_2.troup.maxHealth+newField_2.troup.maxHealth;
			int totalHealthOldCombined = oldField_2.troup.totalHealth+newField_2.troup.totalHealth;
			double percentageLeft = ((double) totalHealthOldCombined) / ((double) maxHealthOldCombined);
			SpectateData.troups.remove(oldField_2.troup);
			oldField_2.troup.connectedField = null;
			oldField_2.troup = null;
			SpectateData.troups.remove(newField_2.troup);
			newField_2.troup.connectedField = null;
			newField_2.troup = Game_UnitsHandler.createNewUpgradeTroup(action.playerId, new FieldCoordinates(action.newX, action.newY), action.text);
			if(newField_2.troup != null) { newField_2.troup.totalHealth = (int) Math.round(newField_2.troup.totalHealth*percentageLeft); } //SET DAMAGED HP
			
			break;
			
		case ROUND_END: //Do nothing realy (just needed to have min 1 action per round to sim round endings)
			ConsoleOutput.printMessageInConsole(" > Round "+action.round+" done [Spec]", true);
			SpectateData.round = action.round+1;
			SpectateData.roundTime = GameData.roundDuration;
			break;
		case CHATMESSAGE: //NOT SYNCED (YET)
			break;
		case FIELDPING: //NOT SYNCED
			break;
		case RESEARCH: //HANDLED SPERATLY HERE
			//Not tracked and shown yet, later?
			break;
		default: 
			ConsoleOutput.printMessageInConsole("WARNING! Simulating action with no fitting type: "+action.type+"! [Spec] Continue...", true);
			break;
		}
		
		
	}
	
	public static void finishInitLoading() {
		
		SpectateData.finishedInitLoading = true;
		
		//No area update required (will be needed with separate visibilities and updated each round change)
		
		//RoundTimer
		SpectateData.roundTime = GameData.roundDuration;
		if(SpectateData.roundTimer == null) {
			SpectateData.roundTimer = new Timer();
			SpectateData.roundTimer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					if(SpectateData.roundTime > 0) {
						SpectateData.roundTime--;
					}
				}
			}, 0, 1000);
		}
		
		OnTopWindowHandler.closeOTW(); //JUST CLOSE WINDOW, GAME IS LOADED/SYNCED
		ConsoleOutput.printMessageInConsole("Game sync finished [Spectate]", true);
		
		//No confirmation to server
		
	}
	
	private static void updateGameSyncOTW(String status) {
		
		if(OnTopWindowData.onTopWindow != null && OnTopWindowData.onTopWindow instanceof OnTopWindow_GameSyncStatus) {
			OnTopWindow_GameSyncStatus gss = (OnTopWindow_GameSyncStatus) OnTopWindowData.onTopWindow;
			gss.updateStatus(status);
		}else {
			ConsoleOutput.printMessageInConsole("WARNING! Wrong or none otw open during game sync! [SPEC] ["+status+"]", true);
		}
		
		
	}
	
//==========================================================================================================
	/**
	 * Get the Color of a player by his ID
	 * @param playerID - int - The ID of the player
	 * @return Color - The color which represents this player
	 */
	public static Color getColorByPlayerID(int playerID) {
		
		if(playerID == SpectateData.playingPlayer[0].getID()) {
			return Color.RED;
		}else if(playerID == SpectateData.playingPlayer[1].getID()) {
			return Color.BLUE;
		}else if(playerID == SpectateData.playingPlayer[2].getID()) {
			return Color.GREEN;
		}else if(playerID == SpectateData.playingPlayer[3].getID()) {
			return Color.YELLOW;
		}else {
			return Color.ORANGE;
		}
		
	}
	
//==================================================================================================
	public static int getPixlesByCoordinateX(int fieldCoordinate) {
		
		return (fieldCoordinate * StandardData.fieldSize)+SpectateData.scroll_LR_count;
		
	}
	public static int getPixlesByCoordinateY(int fieldCoordinate) {
		
		return (fieldCoordinate * StandardData.fieldSize)+SpectateData.scroll_UD_count;
		
	}
	
//==================================================================================================
	public static Building getBuilding(int X, int Y) {
		try {
			Field f = SpectateData.gameMap_FieldList[X][Y];
			if(f == null) {
				return null;
			}else {
				return f.building;
			}
		}catch(IndexOutOfBoundsException error) {
			return null;
		}
	}
	public static Troup getTroup(int X, int Y) {
		try {
			Field f = SpectateData.gameMap_FieldList[X][Y];
			if(f == null) {
				return null;
			}else {
				return f.troup;
			}
		}catch(IndexOutOfBoundsException error) {
			return null;
		}
	}
	
//==========================================================================================================
	/**
	 * Get MID field
	 * @param couldReturnNull - boolean - if true, null could be returned if no midField could be found, if false the last midField will be returned in this case
	 * @return {@link Field} - The field in the middle or if null: 1) couldReturnNull==true -> null 2) if couldReturnNull==false -> The last midField, but remember that it could be still null if the last midField is null too
	 */
	private static Field last_midField = null;
	public static Field get_MID_Field(boolean couldReturnNull) {
		
		Field field = getFieldByScreenCoordinates(WindowData.FrameWidth/2, WindowData.FrameHeight/2);
		
		if(field != null) {
			last_midField = field;
			return field;
		}else {
			if(couldReturnNull == true) {
				return null;
			}else {
				if(last_midField == null) {
					last_midField = SpectateData.gameMap_FieldList[StandardData.mapWidth/2][StandardData.mapHight/2];
				}
				return last_midField;
			}
		}
	}
	
//==========================================================================================================
	/**
	 * Get a field of the GameMap by the given coordinates
	 * @param screenX - int - The X coordinate on the screen
	 * @param screenY - int - The Y coordinate on the screen
	 * @return {@link Field} - The found field or null
	 */
	public static Field getFieldByScreenCoordinates(int screenX, int screenY) {
		
		int realX = (screenX - SpectateData.scroll_LR_count) / (StandardData.fieldSize);
		int realY = (screenY - SpectateData.scroll_UD_count) / (StandardData.fieldSize);
		
		try{
			return SpectateData.gameMap_FieldList[realX][realY];
		}catch(IndexOutOfBoundsException | NullPointerException error) {
			return null;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Shows whether there is space to the LEFT or not
	 * @return boolean - true - there is space, false - there isn't
	 */
	public static boolean left_SpaceFree() {
		if(getFieldByScreenCoordinates((WindowData.FrameWidth/2) - 10, (WindowData.FrameHeight/2)) == null) { return false; }else { return true; }
	}
	/**
	 * Shows whether there is space to the RIGHT or not
	 * @return boolean - true - there is space, false - there isn't
	 */
	public static boolean right_SpaceFree() {
		if(getFieldByScreenCoordinates((WindowData.FrameWidth/2) + 10, (WindowData.FrameHeight/2)) == null) { return false; }else { return true; }
	}
	/**
	 * Shows whether there is space to the TOP or not
	 * @return boolean - true - there is space, false - there isn't
	 */
	public static boolean top_SpaceFree() {
		if(getFieldByScreenCoordinates((WindowData.FrameWidth/2), (WindowData.FrameHeight/2) - 10) == null) { return false; }else { return true; }
	}
	/**
	 * Shows whether there is space to the BOTTOM or not
	 * @return boolean - true - there is space, false - there isn't
	 */
	public static boolean bottom_SpaceFree() {
		if(getFieldByScreenCoordinates((WindowData.FrameWidth/2), (WindowData.FrameHeight/2) + 10) == null) { return false; }else { return true; }
	}
		
	
}
