package me.bejosch.battleprogress.client.Main;

import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.ConnectionData;
import me.bejosch.battleprogress.client.Data.FileData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.DiscordAPI.DiscordAPI;
import me.bejosch.battleprogress.client.Game.TimeManager;
import me.bejosch.battleprogress.client.Handler.ClientPlayerHandler;
import me.bejosch.battleprogress.client.Handler.FileHandler;
import me.bejosch.battleprogress.client.Handler.HoverHandler;
import me.bejosch.battleprogress.client.Handler.MenuHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_ShowLogin;
import me.bejosch.battleprogress.client.ServerConnection.MinaClient;
import me.bejosch.battleprogress.client.Window.Frame;
import me.bejosch.battleprogress.client.Window.Label;
import me.bejosch.battleprogress.client.Window.Buttons.Buttons;
import me.bejosch.battleprogress.client.Window.Images.Images;
import me.bejosch.battleprogress.client.Window.ScrollPanes.ScrollPanes;
import me.bejosch.battleprogress.client.Window.TextAreas.TextAreas;
import me.bejosch.battleprogress.client.Window.TextFields.TextFields;

public class BattleProgress_StartMain_Client {

//==========================================================================================================
	/**
	 * With this methode everything starts ;D
	 */
	public static void main(String[] args) {
		
		//TODO IP: ipcwup.no-ip.biz
		
		//PATHFINDING ALGORYTHEM:
		// https://www.youtube.com/watch?v=-L-WgKMFuhE
		// https://www.youtube.com/watch?v=mZfyt03LDH4 Pathfinding...
		
		ConsoleOutput.printMessageInConsole("Starting BattleProgress Client...", true);
		
		//CREATE FILES IF THEY ARE MISSING
		FileHandler.firstWrite();
		
		//USER INPUT SCANNER
		ConsoleOutput.startUserInputScanner();
		
		//CREATES WINDOW
		initialiseVisualDisplay();
		
		//LOADING IMAGES - PRE LOAD
		Images.preLoadImages();
		
		//STARTS TICK
		TimeManager.startTickTimer();
		
		//STARTS PLAYER ONLINE TIMER
		ClientPlayerHandler.startPlayerOnlineTimer();
		
		//START ANIMATION
		//TODO new Animation_BejoschGamingIntro();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				new Animation_ShowLogin();
			}
		}, 1000*2);
		
		//CREATE BUTTONS
		Buttons.loadButtons();
		
		//CREATE TEXTFIELDS
		TextFields.loadTextFields();
		
		//CREATE TEXTAREAS
		TextAreas.loadTextAreas();
		
		//CREATE SCROLLPANES
		ScrollPanes.loadScrollPanes();
		
		//INIT OTW (WITH MAAs)
		OnTopWindowHandler.initOTWMAA();
		
		//INIT MENU MAA
		MenuHandler.initMenuMAAs();
		
		//START HOVER CLEAR TIMER
		HoverHandler.startHoverClearTimer();
		
		//CONNECTING TO SERVER
		String serverAdresse = FileHandler.readOutData(FileData.file_Settings, "ServerAdresse");
		if(serverAdresse == null) { serverAdresse = ConnectionData.DEFAULT_IP; }
		int port = ConnectionData.DEFAULT_PORT;
		try{ port = Integer.parseInt(FileHandler.readOutData(FileData.file_Settings, "Port")); }catch(NumberFormatException error) {}
		MinaClient.connectToServer(serverAdresse, port);
		
		//INIT DISCORD API
		try {
			DiscordAPI.initAPI();
			StandardData.discordAPIloaded = true;
		}catch(Exception error) {
			//error.printStackTrace();
			ConsoleOutput.printMessageInConsole("Error while loading DiscordAPI! Proceed without it...", true);
		}
		
		//LOADING IMAGES - MAIN
		Images.loadImages();
		
		ConsoleOutput.printMessageInConsole("Type '/help' for console commands!", true);
		
	}
	
//==========================================================================================================
	/**
	 * Creats all parts of the visual display...
	 */
	public static void initialiseVisualDisplay() {
		
		//FRAME
		WindowData.Frame = Frame.createFrame();
		
		//LABEL
		WindowData.Label_Main = new Label(0, 0);
		
		ConsoleOutput.printMessageInConsole("Visual displays are now running!", true);
		
		//PREVENT GREY WINDOW:
		WindowData.Label_Main.requestFocus();
		WindowData.Frame.requestFocus();
//		new Timer().schedule(new TimerTask() {
//			@Override
//			public void run() {
//				WindowData.Label_Main.requestFocus();
//				WindowData.Frame.requestFocus();
//			}
//		}, 1000);
		
	}
	
}