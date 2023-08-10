package me.bejosch.battleprogress.client.Handler;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.DiscordAPI.DiscordAPI;
import me.bejosch.battleprogress.client.Enum.SpielStatus;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;

public class WindowHandler implements WindowListener {

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent evt) {
		
	}

	@Override
	public void windowClosing(WindowEvent evt) {
		
		closingAction();
		System.exit(0);
		
	}

	public static void closingAction() {
		
		//STOP DISCORD API
		if(StandardData.discordAPIloaded == true) {
			try {
				DiscordAPI.stopAPI();
			}catch(Exception error) {
				error.printStackTrace();
				ConsoleOutput.printMessageInConsole("Error while stopping DiscordAPI! Proceed without stopping...", true);
			}
		}
		
		//IF CURRENTLY IN GAME LOBBY OR IN A GAME OR AT THE FINISH - NO NEED!!!!!!! Server notice disconnect on its own
		if(StandardData.spielStatus == SpielStatus.GameLobby || StandardData.spielStatus == SpielStatus.Game || StandardData.spielStatus == SpielStatus.GameFinish) {
//			ServerConnection.sendData(104, ServerConnection.getNewPacketId(), "Quit from game");
//			ConsoleOutput.printMessageInConsole("Send disconnect to server", true);
		}
		
		//STOPS PLAYER ONLINE TIMER
		ClientPlayerHandler.stopPlayerOnlineTimer();
		
		ConsoleOutput.printMessageInConsole("Shutting down...", true);
		
	}
	
	@Override
	public void windowDeactivated(WindowEvent arg0) {
		
		MovementHandler.press_w = false;
		MovementHandler.press_a = false;
		MovementHandler.press_s = false;
		MovementHandler.press_d = false;
		MovementHandler.press_speed = false;
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
