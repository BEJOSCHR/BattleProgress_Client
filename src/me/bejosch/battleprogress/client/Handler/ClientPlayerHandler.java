package me.bejosch.battleprogress.client.Handler;

import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Enum.PlayerRanking;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.ClientPlayer;

public class ClientPlayerHandler {

	private static Timer playerOnlineTimer = null;
	
	public static ClientPlayer getNewClientPlayer(int ID, String name, int level, int XP, int onlineMin, int PIN, int BIN, int NCN, int SN, PlayerRanking ranking, int RP, String currentActivity) {
		
		//PLAYER ALREADY EXISTS
		for(ClientPlayer player : ProfilData.allCurrentClientPlayer) {
			if(player.getID() == ID) {
				return player;
			}
		}
		
		//LOAD PLAYER
		ClientPlayer player = new ClientPlayer(ID, name, level, XP, onlineMin, PIN, BIN, NCN, SN, ranking, RP, currentActivity);
		ProfilData.allCurrentClientPlayer.add(player);
		return player;
	}
	
	public static ClientPlayer getNewClientPlayer(int ID) {
		
		//PLAYER ALREADY EXISTS
		for(ClientPlayer player : ProfilData.allCurrentClientPlayer) {
			if(player.getID() == ID) {
				return player;
			}
		}
		
		//LOAD PLAYER
		ClientPlayer player = new ClientPlayer(ID);
		ProfilData.allCurrentClientPlayer.add(player);
		return player;
	}
	
	public static boolean unloadClientPlayer(int ID) {
		
		for(ClientPlayer player : ProfilData.allCurrentClientPlayer) {
			if(player.getID() == ID) {
				ProfilData.allCurrentClientPlayer.remove(player);
				return true;
			}
		}
		
		return false;
	}
	
	public static void startPlayerOnlineTimer() {
		
		if(playerOnlineTimer == null) {
			playerOnlineTimer = new Timer();
			playerOnlineTimer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					
					for(ClientPlayer player : ProfilData.allCurrentClientPlayer) {
						player.onlineMinHasPassed();
					}
					
				}
			}, 0, 1000*60); //JEDE MIN
			ConsoleOutput.printMessageInConsole("Started PlayerOnlineTimer", true);
		}
		
	}
	
	public static void stopPlayerOnlineTimer() {
		
		if(playerOnlineTimer != null) {
			playerOnlineTimer.cancel();
			playerOnlineTimer = null;
			ConsoleOutput.printMessageInConsole("Stopped PlayerOnlineTimer", true);
		}
		
	}
	
}
