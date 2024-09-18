package me.bejosch.battleprogress.client.Game;

import java.util.HashMap;

import me.bejosch.battleprogress.client.Data.CreateMapData;
import me.bejosch.battleprogress.client.Data.LobbyData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.DiscordAPI.DiscordAPI;
import me.bejosch.battleprogress.client.Enum.SpielStatus;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Handler.CreateMapHandler;
import me.bejosch.battleprogress.client.Handler.LobbyHandler;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
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
	public static void switchTo_Menu_HauptMenu(boolean closeOTW) {
		
		DiscordAPI.setNewPresence("Mainmenu", "", "mainicon", "BattleProgress", "unranked", "Unranked", System.currentTimeMillis());
		MovementHandler.stopMovementTimer();
		
		if(closeOTW == true) {
			AnimationDisplay.stopAllAnimations();
			OnTopWindowHandler.closeOTW();
		}
		
		StandardData.spielStatus = SpielStatus.Menu;
		
		WindowData.Frame.requestFocus();
		
	}
	
//==========================================================================================================
	/**
	 * Switch to the Menu part
	 */
	public static void switchTo_Menu_CreateMap() {
		
		WindowData.Frame.requestFocus();
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
	public static void switchTo_Game_Lobby() {
		
		AnimationDisplay.stopAllAnimations();
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
		
		LobbyHandler.updateLobbyData();
		
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
		
		WindowData.Frame.requestFocus();
		
		if(firstInit) { GameHandler.initialiseGame(true, 1); }
		
	}
	
}
