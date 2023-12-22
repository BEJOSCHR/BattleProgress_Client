package me.bejosch.battleprogress.client.Handler;

import java.util.List;

import me.bejosch.battleprogress.client.Data.FileData;
import me.bejosch.battleprogress.client.Data.LobbyData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.ServerConnection.MinaClient;
import me.bejosch.battleprogress.client.Window.ScrollPanes.ScrollPanes;
import me.bejosch.battleprogress.client.Window.TextAreas.TextAreas;
import me.bejosch.battleprogress.client.Window.TextFields.TextFields;

public class LobbyHandler {
	
//==========================================================================================================
	/**
	 * Start all parts that are needed to manage the lobby display
	 */
	public static void startLobbyHandler() {
		
		//TODO REDO GAME LOBBY (custom game) TOTALY
		
//		//Set clientReference
//		GameData.playingPlayer[0] = ProfilData.thisClient;
//		ConsoleOutput.printMessageInConsole("LobbyHandler startet... (You are "+GameData.clientReference+" ; Host: "+ProfilData.clientIsGameHost+" ; GameID: "+GameData.GameID+")", true);
//		
//		if(LobbyData.lobbyDisplay_FieldList == null) {
//			if(ProfilData.clientIsGameHost == true) {
//				//only load map from file if this is the host client, else the map come from the server
//				LobbyData.choosenMapNumber = 0;
//				LobbyData.choosenMapName = null;
//				loadMapFromFile(true);
//			}
//		}
		
	}
	
//==========================================================================================================
	/**
	 * Show the chat in the lobby
	 */
	public static void showChat(int x, int y) {
		//1100 420 50 20
		
//		Buttons.showButton(Buttons.button_SendChatMessage, "Enter", 14, x, y, 50, 20);
		TextFields.showTextField(TextFields.textField_Chat, "", 14, x+60, y, LobbyData.chat_width-60, 20);
		TextAreas.showTextArea(TextAreas.textArea_Chat);
		ScrollPanes.showScrollPane(ScrollPanes.scrollPane_Chat, 13, x, y+30, LobbyData.chat_width, LobbyData.chat_height-30);
		
	}
//==========================================================================================================
	/**
	 * Hide the chat in the lobby
	 */
	public static void hideChat() {
		
//		Buttons.hideButton(Buttons.button_SendChatMessage);
		TextFields.hideTextField(TextFields.textField_Chat);
		TextAreas.hideTextArea(TextAreas.textArea_Chat);
		ScrollPanes.hideScrollPane(ScrollPanes.scrollPane_Chat);
		
	}
	
//==========================================================================================================
	/**
	 * First preset fill fieldList with Grass
	 */
	public static void fillFieldList() {
		
		for(int x = 0 ; x < StandardData.mapWidth ; x++) {
			for(int y = 0 ; y < StandardData.mapHight ; y++) {
				LobbyData.lobbyDisplay_FieldList[x][y] = new Field(FieldType.Flatland, x, y);
			}
		}
		
	}
	
//==========================================================================================================
	/**
	 * Updates the playerreferences of the player because of the sort function in the lobby
	 */
	public static void updatePlayerReferences() {
		
//		if(GameData.player1 != null) {
//			GameData.player1.playerReference = PlayerReference.Player1;
//			if(GameData.player1.profilID == ProfilData.profilID) {
//				//THIS CLIENT
//				GameData.clientReference = PlayerReference.Player1;
//			}
//		}
//		if(GameData.player2 != null) {
//			GameData.player2.playerReference = PlayerReference.Player2;
//			if(GameData.player2.profilID == ProfilData.profilID) {
//				//THIS CLIENT
//				GameData.clientReference = PlayerReference.Player2;
//			}
//		}
//		if(GameData.player3 != null) {
//			GameData.player3.playerReference = PlayerReference.Player3;
//			if(GameData.player3.profilID == ProfilData.profilID) {
//				//THIS CLIENT
//				GameData.clientReference = PlayerReference.Player3;
//			}
//		}
//		if(GameData.player4 != null) {
//			GameData.player4.playerReference = PlayerReference.Player4;
//			if(GameData.player4.profilID == ProfilData.profilID) {
//				//THIS CLIENT
//				GameData.clientReference = PlayerReference.Player4;
//			}
//		}
		
	}
	
//==========================================================================================================
	/**
	 * Updates the lobby data from the server
	 */
	public static void updateLobbyData() {
		
//		if(StandardData.spielStatus != SpielStatus.GameLobby) {
//			new Timer().schedule(new TimerTask() {
//				@Override
//				public void run() {
//					OverAllManager.switchTo_Game_Lobby();
//				}
//			}, 1000);
//		}else {
//			//UPDATE HOST
//			if(Funktions.getProfilIdByNumber(1) == ProfilData.profilID) { //PLAYER 1 / HOST IS THIS CLIENT
//				if(ProfilData.clientIsGameHost == false) {
//					//change from false to true first time
//					LobbyData.lobbyDisplay_FieldList = null;
//					LobbyData.choosenMapName = "Loading...";
//					ProfilData.clientIsGameHost = true; 
//					LobbyHandler.startLobbyHandler();
//				}
//				Buttons.showButton(Buttons.button_NextMap, ">>", 18, LobbyData.settings_x+(LobbyData.settings_width/2)+10, LobbyData.settings_y+((LobbyData.settings_height/4)*1)+5, LobbyData.settings_button_width, LobbyData.settings_button_height);
//				Buttons.showButton(Buttons.button_LaterMap, "<<", 18, LobbyData.settings_x+(LobbyData.settings_width/2)-LobbyData.settings_button_width-10, LobbyData.settings_y+((LobbyData.settings_height/4)*1)+5, LobbyData.settings_button_width, LobbyData.settings_button_height);
//				if(LobbyHandler.getPlayerCount() == 4) {
//					Buttons.showButton(Buttons.button_NextTeam, ">>", 18, LobbyData.settings_x+(LobbyData.settings_width/2)+10, LobbyData.settings_y+((LobbyData.settings_height/4)*3)+5, LobbyData.settings_button_width, LobbyData.settings_button_height);
//					Buttons.showButton(Buttons.button_LaterTeam, "<<", 18, LobbyData.settings_x+(LobbyData.settings_width/2)-LobbyData.settings_button_width-10, LobbyData.settings_y+((LobbyData.settings_height/4)*3)+5, LobbyData.settings_button_width, LobbyData.settings_button_height);
//				}else {
//					Buttons.hideButton(Buttons.button_NextTeam);
//					Buttons.hideButton(Buttons.button_LaterTeam);
//				}
//				//update start button
//				if(LobbyData.readyToPlay == true) {
//					Buttons.showButton(Buttons.button_StartGame, "Start", 18, LobbyData.status_startButtonAndStatusTextX, LobbyData.status_buttonY, LobbyData.status_startButtonWidth, LobbyData.status_buttonHeight);
//				}else {
//					Buttons.hideButton(Buttons.button_StartGame);
//				}
//			}else {
//				ProfilData.clientIsGameHost = false; 
//				Buttons.hideButton(Buttons.button_NextMap);
//				Buttons.hideButton(Buttons.button_LaterMap);
//				Buttons.hideButton(Buttons.button_NextTeam);
//				Buttons.hideButton(Buttons.button_LaterTeam);
//				Buttons.hideButton(Buttons.button_StartGame);
//			}
//			//update DC status
//			DiscordAPI.setNewPresence("Lobby ("+getPlayerCount()+" of 4)", "Custom Game", "mainicon", "BattleProgress", "unranked", "Unranked", LobbyData.createTimeStamp);
//			
//		}
		
	}
	
//==========================================================================================================
	/**
	 * Get the number of player in this game
	 * @return int - The number of player
	 */
//	public static int getPlayerCount() {
//		
//		int output = 0;
//		if(GameData.player1 != null) { output++; }
//		if(GameData.player2 != null) { output++; }
//		if(GameData.player3 != null) { output++; }
//		if(GameData.player4 != null) { output++; }
//		return output;
//		
//	}
	
//==========================================================================================================
	/**
	 * Loads a map by his position number in the gamelist 
	 * @param up - boolean - if true the map rotation is up, if false it's down
	 */
	public static void loadMapFromFile(boolean up) {
		
		List<String> currentMapList = Funktions.getStringListFromString(FileHandler.readOutData(FileData.file_Maps, "MapList"));
		if(currentMapList.contains("[]")) {
			currentMapList.remove("[]");
		}
		
		if(up == true) {
			//number counts up
			LobbyData.choosenMapNumber++;
		}else {
			//number counts down
			LobbyData.choosenMapNumber--;
		}
		
		if(LobbyData.choosenMapNumber >= currentMapList.size()) {
			//end reached so it beginns at the start
			LobbyData.choosenMapNumber = 0;
		}else if(LobbyData.choosenMapNumber < 0) {
			//start reached so go to the end
			LobbyData.choosenMapNumber = currentMapList.size()-1;
		}
		
		String choosenMapName = currentMapList.get(LobbyData.choosenMapNumber);
//		readOutFieldDataToLoadedMap(FileHandler.readOutData(FileData.file_Maps, choosenMap+"_Fields"));
		
		MinaClient.sendData(106, choosenMapName+";"+FileHandler.readOutData(FileData.file_Maps, choosenMapName+"_Fields"));
		
	}
	
//==========================================================================================================
	/**
	 * Updates the teams in the lobby
	 * @param up - boolean - if true the team rotation is up, if false it's down
	 */
	public static void updateTeams(boolean up) {
		
		MinaClient.sendData(108, GameData.gameID+";"+up);
		
	}
	
//==========================================================================================================
	/**
	 * Load the field data out of the String
	 * @param fieldData - String - The data of the fields
	 */
	public static void readOutFieldDataToLoadedMap(String fieldData) {
		
		LobbyData.lobbyDisplay_FieldList = new Field[StandardData.mapWidth][StandardData.mapHight];
		
		fillFieldList();
		
		String[] data = fieldData.split("-");
		for(String field : data) {
			String[] splitData = field.split(":");
			FieldType type = FieldType.getFieldTypeFromSignal(splitData[0]);
			int X = Integer.parseInt(splitData[1]);
			int Y = Integer.parseInt(splitData[2]);
			LobbyData.lobbyDisplay_FieldList[X][Y] = new Field(type, X, Y);
		}
		
	}
		
}
