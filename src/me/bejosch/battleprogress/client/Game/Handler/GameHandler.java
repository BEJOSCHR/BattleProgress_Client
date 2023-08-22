package me.bejosch.battleprogress.client.Game.Handler;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.MenuData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.EconomicData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.DiscordAPI.DiscordAPI;
import me.bejosch.battleprogress.client.Enum.BuildMenuType;
import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Enum.GenerelIconType;
import me.bejosch.battleprogress.client.Enum.ImportanceType;
import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Game.TimeManager;
import me.bejosch.battleprogress.client.Handler.ClientPlayerHandler;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Handler.UnitsHandler;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.ClientPlayer;
import me.bejosch.battleprogress.client.Objects.RoundStatsContainer;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_GameStartDisplay;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Headquarter;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Field.Field_Ressource;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage_Located;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_ActionbarMainBox;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_ActionbarTask;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_ActiveTaskSkip;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_BuildMenu_BuildingField;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_BuildMenu_CategorySwitch;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_Chat_Send;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_Chat_ShowHide;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_EconomicDisplay;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_InfoMessages;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_ReadyButton;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_generalIconButtons;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.Checkbox.MouseActionArea_Checkbox;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.Checkbox.MouseActionArea_Checkbox_SkipAllTaskDisplays;
import me.bejosch.battleprogress.client.Objects.Tasks.BuildMenuTasks.BuildMenuTask;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;
import me.bejosch.battleprogress.client.ServerConnection.MinaClient;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class GameHandler {

//==========================================================================================================
	//EVENTS
	/**
	 * Called if the mouse has been clicked
	 */
	public static void mouseClickedEvent(int mX, int mY, int clickType) {
		Game_ClickEvent.mouseClickedEvent(mX, mY, clickType);
	}
	
	
//==========================================================================================================
	//EVENTS
	/**
	 * Called if the mouse has been pressed
	 */
	public static void mousePressedEvent(int mX, int mY, int clickType) {
		Game_PressedEvent.mousePressedEvent(mX, mY, clickType);
	}
		
		
//==========================================================================================================
	//EVENTS
	/**
	 * Called if the mouse has been moved
	 */
	public static void mouseMovedEvent(int mX, int mY) {
		Game_MoveEvent.mouseMovedEvent(mX, mY);
	}
	
	
//==========================================================================================================
	//EVENTS
	/**
	 * Called a key has been pressed
	 */
	public static void keyPressedEvent(int keyCode) {
		Game_KeyPressedEvent.keyPressedEvent(keyCode);
	}
	
	
//==========================================================================================================
	//EVENTS
	/**
	 * Called a key has been released
	 */
	public static void keyReleasedEvent(int keyCode) {
		Game_KeyReleasedEvent.keyReleasedEvent(keyCode);
	}
	
	
	
	
//==========================================================================================================
	/**
	 * Called if this client is the host and is going to start the game
	 */
	public static void requestStartGame() {
		
		//ONLY CUSTOM GAME
		MinaClient.sendData(107, GameData.gameID+"");
		
	}
	
//==========================================================================================================
	/**
	 * Called if the game starts from the server (Before Animation)
	 */
	public static void startGame(int gameID, SpielModus modus, int playerID_1, int playerID_2, int playerID_3, int playerID_4, String mapName, String mapData) {
		
		//CLOSE ALL OTW
		OnTopWindowHandler.closeOTW();
		
		//CLOSE FRIEND LIST
		if(MenuData.friendListOpened == true) {
			MenuData.friendListOpened = false;
		}
		
		GameData.gameID = gameID;
		GameData.gameMode = modus;
		
		if(SpielModus.isGameModus1v1()) {
			//1v1
			ConsoleOutput.printMessageInConsole("GAME START 1v1 ("+gameID+" - "+modus+" - "+playerID_1+" - "+playerID_2+" - "+mapName+")", true);
			
			GameData.playingPlayer = new ClientPlayer[2];
			GameData.playingPlayer[0] = ClientPlayerHandler.getNewClientPlayer(playerID_1);
			GameData.playingPlayer[1] = ClientPlayerHandler.getNewClientPlayer(playerID_2);
		}else {
			//2v2
			ConsoleOutput.printMessageInConsole("GAME START 2v2 ("+gameID+" - "+modus+" - "+playerID_1+" - "+playerID_2+" - "+playerID_3+" - "+playerID_4+" - "+mapName+")", true);
			
			GameData.playingPlayer = new ClientPlayer[4];
			GameData.playingPlayer[0] = ClientPlayerHandler.getNewClientPlayer(playerID_1);
			GameData.playingPlayer[1] = ClientPlayerHandler.getNewClientPlayer(playerID_2);
			GameData.playingPlayer[2] = ClientPlayerHandler.getNewClientPlayer(playerID_3);
			GameData.playingPlayer[3] = ClientPlayerHandler.getNewClientPlayer(playerID_4);
		}
		
		//SPIEL AUF running SETZEN
		GameData.gameIsRunning = true;
		
		//UPDATE DC API
		String displayModus = modus.toString().replace("_", " ");
		DiscordAPI.setNewPresence("In Game", displayModus, "mainicon", "BattleProgress", "unranked", "Unranked", System.currentTimeMillis());
		
		//LOAD UNITS
		UnitsHandler.requestUnitsUpdate();
		
		//LOAD UPGRADE DATA CONTAINER
		Game_ResearchHandler.loadUpgradeDataContainer();
		
		//GAME DURATION TIMER START
		TimeManager.startDurationTimer();
		
		//MAP TRANSFER
		GameData.mapName = mapName;
		readOutFieldDataToLoadedMap(mapData);
		
		//START MOVEMENT HANDLER
		MovementHandler.startMovementTimer();
		
		//Animation (which switchs the GameStatus to Game!)
		new Animation_GameStartDisplay();
		
	}
	
//==========================================================================================================
	/**
	 * Called if the game starts after the animation, so it's finaly started (Atfer animation)
	 */
	public static void initialiseGame() {
		
		//CreateOwnHQ
		Point hqCords = getHQCoordinates();
		MinaClient.sendData(621, "Headquarter"+";"+hqCords.x+";"+hqCords.y);
		Funktions.moveScreenToFieldCoordinates(hqCords.x, hqCords.y); //MOVE SCREEN TO FOCUS HQ
		
		//Setup the research after loaded UpgradeDataContainer earlyer
		Game_ResearchHandler.initUpgrades();
		
		//Preset BuildMenu (NEED SOME TIME AFTER UNITS LOAD SO IT IS LOADED THEN BUILDMENU TRIES TO INIT)
		presetBuildMenu();
		
//--------------------------------------------------------------------------------------------------------
		//Start MouseActionAreas
		//General Buttons
		String[] hoverMessage_1 = {"Menu"};
		new MouseActionArea_generalIconButtons(0, "gIcon_HomeMenu", hoverMessage_1, GenerelIconType.HomeMenu, Images.generalIcon_Home);
		String[] hoverMessage_2 = {"Settings"};
		new MouseActionArea_generalIconButtons(1, "gIcon_Settings", hoverMessage_2, GenerelIconType.Settings, Images.generalIcon_Gear);
		String[] hoverMessage_3 = {"Material"};
		new MouseActionArea_generalIconButtons(2, "gIcon_Material", hoverMessage_3, GenerelIconType.Material, Images.generalIcon_Material);
		String[] hoverMessage_4 = {"Energy"};
		new MouseActionArea_generalIconButtons(3, "gIcon_Energy", hoverMessage_4, GenerelIconType.Energy, Images.generalIcon_Energy);
		String[] hoverMessage_5 = {"Research"};
		new MouseActionArea_generalIconButtons(4, "gIcon_Research", hoverMessage_5, GenerelIconType.Research, Images.generalIcon_ResearchGlas);
		//	InfoMessages
		for(int i = 0 ; i < GameData.maxDisplayedInfoMessages ; i++) {
			new MouseActionArea_InfoMessages(i);
		}
		//	MiniMap Settings
		int borderDistance_Left = 26, borderDistance_Down = 50, size = 16, space = size+5, startX = WindowData.FrameWidth-borderDistance_Left, startY = WindowData.FrameHeight-borderDistance_Down-(space*5);
		String[] hoverMessage_6 = {"Show/Hide all"};
		new MouseActionArea_Checkbox(startX, startY+(space*0), startX+size, startY+(space*0)+size, "MiniMap_ShowAll", hoverMessage_6, Color.BLACK, Color.WHITE, Color.GREEN, true, true, Color.DARK_GRAY);
		String[] hoverMessage_7 = {"Show/Hide map"};
		new MouseActionArea_Checkbox(startX, startY+(space*1), startX+size, startY+(space*1)+size, "MiniMap_ShowMap", hoverMessage_7, Color.BLACK, Color.WHITE, Color.GREEN, true, true, Color.DARK_GRAY);
		String[] hoverMessage_8 = {"Show/Hide visible Area"};
		new MouseActionArea_Checkbox(startX, startY+(space*2), startX+size, startY+(space*2)+size, "MiniMap_ShowVisible", hoverMessage_8, Color.BLACK, Color.WHITE, Color.GREEN, true, true, Color.DARK_GRAY);
		String[] hoverMessage_9 = {"Show/Hide buildings"};
		new MouseActionArea_Checkbox(startX, startY+(space*3), startX+size, startY+(space*3)+size, "MiniMap_ShowBuildings", hoverMessage_9, Color.BLACK, Color.WHITE, Color.GREEN, true, true, Color.DARK_GRAY);
		String[] hoverMessage_10 = {"Show/Hide troups"};
		new MouseActionArea_Checkbox(startX, startY+(space*4), startX+size, startY+(space*4)+size, "MiniMap_ShowTroups", hoverMessage_10, Color.BLACK, Color.WHITE, Color.GREEN, true, true, Color.DARK_GRAY);
		//	ChatHandler
		new MouseActionArea_Chat_ShowHide(true);
		new MouseActionArea_Chat_ShowHide(false);
		new MouseActionArea_Chat_Send();
		//	Actionbar - Tasks
		for(int i = 0 ; i < GameData.maxActionbarTasks ; i++) {
			new MouseActionArea_ActionbarTask(i, Color.WHITE, Color.ORANGE);
		}
		//	Actionbar - MainDisplayBox
		new MouseActionArea_ActionbarMainBox();
		//	ReadyButton
		new MouseActionArea_ReadyButton();
		//	BuildMenu - BuildingFields
		for(int yCords = 1 ; yCords <= GameData.buildMenu_buildingsPerHeight ; yCords++) {
			for(int xCords = 1 ; xCords <= GameData.buildMenu_buildingsPerWidth ; xCords++) {
				new MouseActionArea_BuildMenu_BuildingField(xCords, yCords);
			}
		}
		//	BuildMenu - Category
		for(int i = 0 ; i < GameData.buildMenu_possibleCategory.size() ; i++) {
			new MouseActionArea_BuildMenu_CategorySwitch(i);
		}
		//	ActiveTaskSkip
		new MouseActionArea_ActiveTaskSkip();
		new MouseActionArea_Checkbox_SkipAllTaskDisplays();
		//EcoDisp
		new MouseActionArea_EconomicDisplay(1);
		new MouseActionArea_EconomicDisplay(2);
		new MouseActionArea_EconomicDisplay(3);
				
//--------------------------------------------------------------------------------------------------------
		
		//CALCULATE VIEW RANGE
		//IS CALLED BY THE CREATE HQ PART ON THE SERVER RECEIVER DOWN BELOW
		
		//STATS CONTAINER
		RoundData.currentRound = 1;
		RoundData.currentStatsContainer = new RoundStatsContainer(RoundData.currentRound);
		
		//START ROUND TIMER
		Game_RoundHandler.startRoundTimer();
		
	}
	
	public static Point getHQCoordinates() { return getHQCoordinates(ProfilData.thisClient.getID()); }
	public static Point getHQCoordinates(int playerID) {
		
		if(SpielModus.isGameModus1v1()) {
			if(GameData.playingPlayer[0].getID() == playerID) {
				return StandardData.HQ_1_1vs1;
			}else if(GameData.playingPlayer[1].getID() == playerID) {
				return StandardData.HQ_2_1vs1;
			}else {
				ConsoleOutput.printMessageInConsole("Could not get HQ on game start (1vs1)! PlayerID: "+playerID, true);
			}
		//2vs2
		}else {
			if(GameData.playingPlayer[0].getID() == playerID) {
				return StandardData.HQ_1_2vs2;
			}else if(GameData.playingPlayer[1].getID() == playerID) {
				return StandardData.HQ_2_2vs2;
			}else if(GameData.playingPlayer[2].getID() == playerID) {
				return StandardData.HQ_3_2vs2;
			}else if(GameData.playingPlayer[3].getID() == playerID) {
				return StandardData.HQ_4_2vs2;
			}else {
				ConsoleOutput.printMessageInConsole("Could not get HQ on game start (2vs2)! PlayerID: "+playerID, true);
			}
		}
		
		return null;
		
	}
	
//==========================================================================================================
	/**
	 * Updates the View- and Build Area if called with a 1 sek delay in total (0.5 sek each)
	 */
	public static void updateViewAndBuildArea() {
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				Game_RoundHandler.calculateViewRange();
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						Game_RoundHandler.calculateBuildArea();
					}
				}, 1000);
			}
		}, 1000);
		
	}

	
//==========================================================================================================
	/**
	 * Load the field data out of the String
	 * @param fieldData - String - The data of the fields
	 */
	public static void readOutFieldDataToLoadedMap(String fieldData) {
		
		GameData.gameMap_FieldList = new Field[StandardData.mapWidth][StandardData.mapHight];
		
		//FILL DEFAULT
		for(int x = 0 ; x < StandardData.mapWidth ; x++) {
			for(int y = 0 ; y < StandardData.mapHight ; y++) {
				GameData.gameMap_FieldList[x][y] = new Field(FieldType.Gras, x, y);
			}
		}
		
		//SET SPECIAL FIELDS (all none grass)
		String[] data = fieldData.split("-");
		for(String field : data) {
			String[] splitData = field.split(":");
			FieldType type = FieldType.getFieldTypeFromSignal(splitData[0]);
			int X = Integer.parseInt(splitData[1]);
			int Y = Integer.parseInt(splitData[2]);
			if(type == FieldType.Ressource) {
				GameData.gameMap_FieldList[X][Y] = new Field_Ressource(X, Y);
			}else {
				GameData.gameMap_FieldList[X][Y] = new Field(type, X, Y);
			}

		}
		
	}
	
//==========================================================================================================
	/**
	 * Checks if a pixle point (mostly mouse Coordinates) is in a actionArea AND has to be an OTW MMA
	 * @return boolean - true if it is in an area, false if not
	 */
	public static List<MouseActionArea> getOnlyOTWMouseActionAreasFromScreenCoordinates(int pixleX, int pixleY) {
		
		while(true) {
			try{
				List<MouseActionArea> output = new ArrayList<MouseActionArea>();
				for(MouseActionArea actionArea : GameData.mouseActionAreas) {
					if(actionArea.checkArea(pixleX, pixleY) && actionArea.OTWMMA == true && actionArea.isActiv()) {
						output.add(actionArea);
					}
				}
				return output;
			}catch(ConcurrentModificationException error) { }
		}
		
	}
//==========================================================================================================
	/**
	 * Checks if a pixle point (mostly mouse Coordinates) is in a actionArea
	 * @return boolean - true if it is in an area, false if not
	 */
	public static List<MouseActionArea> getMouseActionAreasFromScreenCoordinates(int pixleX, int pixleY) {
		
		while(true) {
			try{
				List<MouseActionArea> output = new ArrayList<MouseActionArea>();
				for(MouseActionArea actionArea : GameData.mouseActionAreas) {
					if(actionArea.checkArea(pixleX, pixleY) && actionArea.isActiv()) {
						output.add(actionArea);
					}
				}
				return output;
			}catch(ConcurrentModificationException error) { }
		}
		
	}
//==========================================================================================================
	/**
	 * Returns a mouseActionArea by it name
	 * @return {@link MouseActionArea} - The found MouseActionArea or null
	 */
	public static MouseActionArea getMouseActionAreaByName(String idName) {
		
		while(true) {
			try{
				for(MouseActionArea actionArea : GameData.mouseActionAreas) {
					if(actionArea.idName.equalsIgnoreCase(idName)) {
						return actionArea;
					}
				}
				return null;
			}catch(ConcurrentModificationException error) { }
		}
		
	}
	
	/**
	 * Creates a Building - Only Used for HQ placement at the start of the game (Normal build method: ExecuteTask_Build)
	 * @param playerID - {@link Integer} - The id of the player who build it
	 * @param buildingName - String - The name of the building, is used for identification which building will be created 
	 * @param X - int - The X-Coordinate of the field
	 * @param Y - int - The Y-Coordinate of the field
	 */
	public static void createBuilding(int playerID, String buildingName, int X, int Y) {
		
		Field targetField = GameData.gameMap_FieldList[X][Y];
		if(targetField == null) { 
			ConsoleOutput.printMessageInConsole("A createBuilding packet found no targetField for the coordinates '"+X+":"+Y+"'!", true);
			return; 
		}
		
		new InfoMessage_Located(buildingName+" has been build", ImportanceType.NORMAL, targetField.X, targetField.Y, true);
		
		switch (buildingName) {
		case "Headquarter":
			new Building_Headquarter(playerID, targetField);
			break;
		default:
			ConsoleOutput.printMessageInConsole("A createBuilding packet found no building for the buildingName '"+buildingName+"'!", true);
			break;
		}
		
		GameHandler.updateViewAndBuildArea();
		
	}
	
//==========================================================================================================
	/**
	 * Sets the start parameter for the build menu on the left
	 */
	public static void presetBuildMenu() {
		
		GameData.buildMenu_possibleCategory.clear();
		GameData.buildMenu_possibleCategory.add(BuildMenuType.Fight);
		GameData.buildMenu_possibleCategory.add(BuildMenuType.Economic);
		GameData.buildMenu_possibleCategory.add(BuildMenuType.Production);
		GameData.buildMenu_possibleCategory.add(BuildMenuType.Special);
		
		GameData.buildMenu_activated = true;
		switchBuildMenuCategory(BuildMenuType.Economic);
		
	}
	
//==========================================================================================================
	/**
	 * Switchs the current buildmenu to the new category
	 * @param newCategory - {@link BuildMenuTask} - The new category
	 */
	public static void switchBuildMenuCategory(BuildMenuType newCategory) {
		
		GameData.buildMenu_displayedType = newCategory;
		GameData.buildMenu_displayedBuildings = BuildMenuTask.loadAllBuildingTasks(newCategory);
		
	}
	
//==========================================================================================================
	/**
	 * Checks whether a field is in the build-area or not
	 * @return boolean - true if it is int the area, false if not
	 */
	public static boolean fieldIsInBuildArea(Field targetField) {
		
		for(FieldCoordinates fieldCoordinates : GameData.buildArea) {
			if(fieldCoordinates.compareToOtherField(targetField) == true) {
				return true;
			}
		}
		return false;
		
	}
	
//==========================================================================================================
	/**
	 * Checks whether a massValue could be spent or that it is not enought mass
	 * @return boolean - true if enought mass, false if not enought
	 */
	public static boolean hasEnoughtMaterial(int neededMassValue) {
		
		if(EconomicData.materialAmount >= neededMassValue) {
			return true;
		}else {
			return false;
		}
		
	}
//==========================================================================================================
	/**
	 * Checks whether a energyValue could be spent or that it is not enought energy
	 * @return boolean - true if enought energy, false if not enought
	 */
	public static boolean hasEnoughtEnergy(int neededEnergyValue) {
		
		if(EconomicData.energyAmount >= neededEnergyValue) {
			return true;
		}else {
			return false;
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
		
		int realX = (screenX - GameData.scroll_LR_count) / (StandardData.fieldSize);
		int realY = (screenY - GameData.scroll_UD_count) / (StandardData.fieldSize);
		
		try{
			return GameData.gameMap_FieldList[realX][realY];
		}catch(IndexOutOfBoundsException | NullPointerException error) {
			return null;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Get a building by his coordinates
	 * @param fieldX - int - The X coordinate of the field
	 * @param fieldY - int - The Y coordinate of the field
	 * @return {@link Building} - A found building or null
	 */
	public static Building getBuildingByCoordinates(int fieldX, int fieldY) {
		
		try{
			return GameData.gameMap_FieldList[fieldX][fieldY].building;
		}catch(IndexOutOfBoundsException | NullPointerException error) { error.printStackTrace(); }
		return null;
		
	}
	
//==========================================================================================================
	/**
	 * Get a troup by his coordinates
	 * @param fieldX - int - The X coordinate of the field
	 * @param fieldY - int - The Y coordinate of the field
	 * @return {@link Troup} - A found troup or null
	 */
	public static Troup getTroupByCoordinates(int fieldX, int fieldY) {
		
		try{
			return GameData.gameMap_FieldList[fieldX][fieldY].troup;
		}catch(IndexOutOfBoundsException | NullPointerException error) { error.printStackTrace(); }
		return null;
		
	}
	
//==========================================================================================================
	/**
	 * Compare two PlayerIDs with each other
	 * @param playerID1 - {@link Integer} - The first ID
	 * @param playerID2 - {@link Integer} - The second ID
	 * @return boolean - True if they are allied, false if they are enemy
	 */
	public static boolean checkPlayerIDForAllied(int playerID1, int playerID2) {
		
		if(getAlliedPlayersByID(playerID1).contains(playerID2)) {
			return true;
		}
		return false;
		
	}
	
//==========================================================================================================
	/**
	 * Gets all PlayerID which are allied to each other, including the given one
	 * @param referenceID - {@link Integer} - The reference which is in the team
	 * @return ArrayList(Integer) - The filled list of allied PlayerIDs
	 */
	public static List<Integer> getAlliedPlayersByID(int playerID) {
		
		if(GameData.gameMode != null) {
			List<Integer> output = new ArrayList<>();
			if(SpielModus.isGameModus1v1()) {
				//1v1
				if(GameData.playingPlayer[0].getID() == playerID) {
					//Player1
					output.add(GameData.playingPlayer[0].getID());
				}else if(GameData.playingPlayer[1].getID() == playerID) {
					//Player2
					output.add(GameData.playingPlayer[1].getID());
				}else {
					ConsoleOutput.printMessageInConsole("Getting allied player found no matching player! (1v1 - ID: "+playerID+")", true);
				}
			}else {
				//2v2
				if(GameData.playingPlayer[0].getID() == playerID) {
					//Player1
					output.add(GameData.playingPlayer[0].getID());
					output.add(GameData.playingPlayer[1].getID());
				}else if(GameData.playingPlayer[1].getID() == playerID) {
					//Player2
					output.add(GameData.playingPlayer[0].getID());
					output.add(GameData.playingPlayer[1].getID());
				}else if(GameData.playingPlayer[2].getID() == playerID) {
					//Player3
					output.add(GameData.playingPlayer[2].getID());
					output.add(GameData.playingPlayer[3].getID());
				}else if(GameData.playingPlayer[3].getID() == playerID) {
					//Player4
					output.add(GameData.playingPlayer[2].getID());
					output.add(GameData.playingPlayer[3].getID());
				}else {
					ConsoleOutput.printMessageInConsole("Getting allied player found no matching player! (2v2 - ID: "+playerID+")", true);
				}
			}
			return output;
		}
		return null;
		
	}
	
//==========================================================================================================
	/**
	 * Get TOP LEFT corner field
	 * @return {@link Field} - The field in this corner or null
	 */
	public static Field get_TOP_LEFT_CornerField() {
		
		Field field = getFieldByScreenCoordinates(0, 0);
		
		if(field != null) {
			return field;
		}else {
			return GameData.gameMap_FieldList[0][0];
		}
	}
	
	private static Field last_midField = null;
//==========================================================================================================
	/**
	 * Get MID field
	 * @param couldReturnNull - boolean - if true, null could be returned if no midField could be found, if false the last midField will be returned in this case
	 * @return {@link Field} - The field in the middle or if null: 1) couldReturnNull==true -> null 2) if couldReturnNull==false -> The last midField, but remember that it could be still null if the last midField is null too
	 */
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
					last_midField = GameData.gameMap_FieldList[StandardData.mapWidth/2][StandardData.mapHight/2];
				}
				return last_midField;
			}
		}
	}
	
//==========================================================================================================
	/**
	 * Get BOTTOM RIGHT corner field
	 * @return {@link Field} - The field in this corner or null
	 */
	public static Field get_BOTTOM_RIGHT_CornerField() {
		
		Field field = getFieldByScreenCoordinates(WindowData.FrameWidth, WindowData.FrameHeight);
		
		if(field != null) {
			return field;
		}else {
			return GameData.gameMap_FieldList[StandardData.mapWidth-1][StandardData.mapHight-1];
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
