package me.bejosch.battleprogress.client.Game;

import me.bejosch.battleprogress.client.Data.CreateMapData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.DiscordAPI.DiscordAPI;
import me.bejosch.battleprogress.client.Enum.SpielStatus;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Handler.CreateMapHandler;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Handler.SpectateHandler;
import me.bejosch.battleprogress.client.Window.Animations.AnimationDisplay;
import me.bejosch.battleprogress.client.Window.Buttons.Buttons;
import me.bejosch.battleprogress.client.Window.ScrollPanes.ScrollPanes;
import me.bejosch.battleprogress.client.Window.TextAreas.TextAreas;
import me.bejosch.battleprogress.client.Window.TextFields.TextFields;

public class OverAllManager {
	
	//MENU
	
//==========================================================================================================
	/**
	 * Switch to the Menu part
	 */
	public static void switchTo_Menu(boolean closeOTW) {
		
		if(StandardData.spielStatus == SpielStatus.GameFinish || StandardData.spielStatus == SpielStatus.Game) {
			//Return from game
			GameHandler.resetAllGameData();
		}else if(StandardData.spielStatus == SpielStatus.Spectate) {
			//Return from spectate
			SpectateHandler.stopSpectate();
		}else if(StandardData.spielStatus == SpielStatus.CreateMap) {
			//Return from CreateMap
			//TODO
		}else if(StandardData.spielStatus == SpielStatus.Replay) {
			//Return from Replay
			//TODO
		}else if(StandardData.spielStatus == SpielStatus.GameLobby) {
			//Return from Lobby (custome game create)
			//TODO
		}
		
		DiscordAPI.setNewPresence("Menu", "", "mainicon", "BattleProgress", "unranked", "Unranked", System.currentTimeMillis());
		MovementHandler.stopMovementTimer();
		
		if(closeOTW == true) {
			AnimationDisplay.stopAllAnimations();
			Buttons.hideAllButtons();
			TextFields.hideAlltextFields();
			TextAreas.hideAlltextAreas();
			ScrollPanes.hideAllScrollPanes();
			OnTopWindowHandler.closeOTW();
		}
		
		StandardData.spielStatus = SpielStatus.Menu;
		
		WindowData.Frame.requestFocus();
		
	}
	
//==========================================================================================================
	/**
	 * Switch to the Menu part
	 */
	public static void switchTo_CreateMap() {
		
		AnimationDisplay.stopAllAnimations();
		Buttons.hideAllButtons();
		TextFields.hideAlltextFields();
		TextAreas.hideAlltextAreas();
		ScrollPanes.hideAllScrollPanes();
		
		DiscordAPI.setNewPresence("Mapeditor", "", "mainicon", "BattleProgress", "unranked", "Unranked", System.currentTimeMillis());
		
		CreateMapHandler.startCreateMapHandler();
		
		OnTopWindowHandler.closeOTW();
		
		StandardData.spielStatus = SpielStatus.CreateMap;
		
		CreateMapData.overlayedInput = false;
		CreateMapData.overlayInputFinished = false;
		CreateMapData.loadingMap = false;
		CreateMapData.clearingMap = false;
		CreateMapData.whySaveEnded = "";
		
		CreateMapHandler.showHUD();
		
		MouseHandler.mousePressPoint = null;
		MouseHandler.mouseReleasePoint = null;
		
		WindowData.Frame.requestFocus();
		
	}
	
	//GAME
	
//==========================================================================================================
	/**
	 * Switch to the Game part
	 */
	public static void switchTo_CustomGame_Lobby() {
		
		//TODO
		
		/*AnimationDisplay.stopAllAnimations();
		Buttons.hideAllButtons();
		TextFields.hideAlltextFields();
		TextAreas.hideAlltextAreas();
		ScrollPanes.hideAllScrollPanes();
		
		OnTopWindowHandler.closeOTW();
		
		GameData.chatList = new HashMap<Integer, String>();
		TextAreas.textArea_Chat.setText("");
		
		LobbyHandler.startLobbyHandler();
		
		StandardData.spielStatus = SpielStatus.GameLobby;
		
		LobbyData.createTimeStamp = System.currentTimeMillis();
		DiscordAPI.setNewPresence("Lobby (1 of 4)", "Custom Game", "mainicon", "BattleProgress", "unranked", "Unranked", LobbyData.createTimeStamp);
		
		LobbyHandler.showChat(LobbyData.chat_x, LobbyData.chat_y);
		
		WindowData.Frame.requestFocus();
		
		LobbyHandler.updateLobbyData();*/
		
	}
	
//==========================================================================================================
	/**
	 * Switch to the Spectate part
	 */
	public static void switchTo_Spectate() {
		
		AnimationDisplay.stopAllAnimations();
		Buttons.hideAllButtons();
		TextFields.hideAlltextFields();
		TextAreas.hideAlltextAreas();
		ScrollPanes.hideAllScrollPanes();
		
		OnTopWindowHandler.closeOTW();
		
		DiscordAPI.setNewPresence("Spectating", "", "mainicon", "BattleProgress", "unranked", "Unranked", System.currentTimeMillis());
		
		//
		
		StandardData.spielStatus = SpielStatus.Spectate;
		
	}
	
//==========================================================================================================
	/**
	 * Switch to the Replay part
	 */
	public static void switchTo_Replay() {
		
		AnimationDisplay.stopAllAnimations();
		Buttons.hideAllButtons();
		TextFields.hideAlltextFields();
		TextAreas.hideAlltextAreas();
		ScrollPanes.hideAllScrollPanes();
		
		OnTopWindowHandler.closeOTW();
		
		DiscordAPI.setNewPresence("Watching replay", "", "mainicon", "BattleProgress", "unranked", "Unranked", System.currentTimeMillis());
		
		//
		
		StandardData.spielStatus = SpielStatus.Replay;
		
	}
	
//==========================================================================================================
	/**
	 * Switch to the Game part
	 */
	public static void switchTo_Game(boolean firstInit) {
		
		//Animation running
		Buttons.hideAllButtons();
		TextFields.hideAlltextFields();
		TextAreas.hideAlltextAreas();
		ScrollPanes.hideAllScrollPanes();
		
		if(firstInit) { OnTopWindowHandler.closeOTW(); }
		
		StandardData.spielStatus = SpielStatus.Game;
		StandardData.showGrid = false;
		
		//DC UPDATE IN GAME INIT
		
		WindowData.Frame.requestFocus();
		
		if(firstInit) { GameHandler.initialiseGame(true, 1); }
		
	}
	
}
