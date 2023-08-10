package me.bejosch.battleprogress.client.Main;

import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.LobbyData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Debug.DebugHandler;
import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Enum.SpielStatus;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Objects.ClientPlayer;
import me.bejosch.battleprogress.client.ServerConnection.ServerConnection;

public class ConsoleOutput {

	public static Timer ConsoleInputScanner = new Timer();
	
//==========================================================================================================
	/**
	 * Print simple Message in the console
	 * @param text - String - The message to print
	 * @param prefix - boolean - Enable/Disable Prefix
	 */
	public static void printMessageInConsole(String text, boolean prefix) {
		
		if(prefix == true) {
			System.out.println(StandardData.messagePrefix+text);
		}else {
			System.out.println(text);
		}
		DebugHandler.printConsole(text);
		
	}
	
//==========================================================================================================
	/**
	 * Print a {@link StackTraceElement} as String error message in the console
	 * @param errorElement[] - {@link StackTraceElement} - The given error StackTraces
	 */
	public static void printErrorMessageFromStackTrace(StackTraceElement[] errorElements) {
		
		int durchlauf = 1;
		
		ConsoleOutput.printMessageInConsole("Error happend:", true);
		for(StackTraceElement errorElement : errorElements) {
			String line = durchlauf+". Class: "+errorElement.getClassName()+" - Method: "+errorElement.getMethodName()+" - Line: "+errorElement.getLineNumber()+" - File: "+errorElement.getFileName();
			ConsoleOutput.printMessageInConsole(line, false);
			durchlauf++;
		}
		
	}
	
	
//==========================================================================================================
	/**
	 * Is activated than user type command in console
	 * @param commands - ArrayList(String) - The command or command line the user typed
	 * @see startUserInputScanner
	 */
	public static void userInputCommand(List<String> commands) {
		
		if(commands.get(0).equalsIgnoreCase("/help")) {					//HELP
			
			printMessageInConsole("'/sent' - Shows all data witch has been sent", true);
			printMessageInConsole("'/players' - Shows all cached player", true);
			printMessageInConsole("'/game' - Shows all infos about the game", true);
			printMessageInConsole("'/ping' - Send a test packet to the Server, checking the connection", true);
			printMessageInConsole("'/stop' - Stops the program", true);
			
		}else if(commands.get(0).equalsIgnoreCase("/sent")) {		//PACKETS
			
			if(!ServerConnection.sentDataList.isEmpty()) {
				int i = 1;
				for(String data : ServerConnection.sentDataList) {
					printMessageInConsole("Data "+i+": "+data, true);
					i++;
				}
			}else {
				printMessageInConsole("No data has been sent!", true);
			}
			
		}else if(commands.get(0).equalsIgnoreCase("/players")) {		//PACKETS
			
			if(ProfilData.allCurrentClientPlayer.isEmpty()) {
				printMessageInConsole("No players are cached!", true);
			}else {
				printMessageInConsole("Showing "+ProfilData.allCurrentClientPlayer.size()+" cached player:", true);
				int i = 1;
				for(ClientPlayer player : ProfilData.allCurrentClientPlayer) {
					printMessageInConsole(i+". "+player.getID()+" - "+player.getName()+" - "+player.getOnlineMin()+" min online", true);
					i++;
				}
			}
			
		}else if(commands.get(0).equalsIgnoreCase("/game")) {		//GAMES
			
			if(StandardData.spielStatus == SpielStatus.Game) {
				printMessageInConsole("Game info: [ID: "+GameData.gameID+", Modus: "+GameData.gameMode+"]", true);
				printMessageInConsole("MapName: "+LobbyData.choosenMapName, true);
				printMessageInConsole("Player1: "+GameData.playingPlayer[0].getID()+", "+GameData.playingPlayer[0].getName(), true);
				printMessageInConsole("Player2: "+GameData.playingPlayer[1].getID()+", "+GameData.playingPlayer[1].getName(), true);
				if(SpielModus.isGameModus1v1() == false) {
					printMessageInConsole("Player3: "+GameData.playingPlayer[2].getID()+", "+GameData.playingPlayer[2].getName(), true);
					printMessageInConsole("Player4: "+GameData.playingPlayer[3].getID()+", "+GameData.playingPlayer[3].getName(), true);
				}
				printMessageInConsole("Round: "+RoundData.currentRound, true);
			}else {
				printMessageInConsole("Currently not in a game!", true);
			}
			
		}else if(commands.get(0).equalsIgnoreCase("/ping")) {		//PING
			
			printMessageInConsole("PING", true);
			ServerConnection.sendData(998, ServerConnection.getNewPacketId(), "Ping;1");
			
		}else if(commands.get(0).equalsIgnoreCase("/stop")) {		//STOP
			
			System.exit(0);
			
		}else {
			
			printMessageInConsole("Can't handle this input!", true);
		
		}
		
	}
	
	
//==========================================================================================================
	/**
	 * Start Scanner which checks for new user console input
	 * @see userInputCommand
	 */
	public static void startUserInputScanner() {
		
		ConsoleInputScanner.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				
				@SuppressWarnings("resource") //DARF NICHT GESCHLOSSEN WERDEN!
				Scanner ConsoleInput = new Scanner(System.in);
				
				if(ConsoleInput.hasNextLine()) {
					String Input = ConsoleInput.nextLine();
					List<String> Inputs = Funktions.ArrayFromPattern(Input.split(" "));
					
					userInputCommand(Inputs);
					
				}
				
			}
		}, 0, 60);
		
	}
	
	
}
