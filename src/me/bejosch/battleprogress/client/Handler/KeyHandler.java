package me.bejosch.battleprogress.client.Handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Debug.DebugWindow;
import me.bejosch.battleprogress.client.Enum.SpielStatus;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.GameAccept.OnTopWindow_GameAccept;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.GameSyncStatus.OnTopWindow_GameSyncStatus;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.PlayerDisconnect.OnTopWindow_PlayerDisconnect;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.QueueWaiting.OnTopWindow_QueueWaiting;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.TabGameInfo.OnTopWindow_TabGameInfo;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class KeyHandler implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {
		
		MovementHandler.updateKeys_PRESS(e.getKeyCode()); //UPDATE STILL NEEDED
		
		if(OnTopWindowData.onTopWindow != null) {
			//HAS OTW OPEN
			
			OnTopWindowData.onTopWindow.onKeyPress(e.getKeyCode());
			
		}else {
			
			if(StandardData.spielStatus == SpielStatus.CreateMap) {
				
				CreateMapHandler.keyPressedEvent(e.getKeyCode());
				
			}else if(StandardData.spielStatus == SpielStatus.Game) {
				
				GameHandler.keyPressedEvent(e.getKeyCode());
				
			}
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		//============== DEBUG ==============
		if(e.getKeyCode() == KeyEvent.VK_I) {
			DebugWindow.startDebug();
		}else if(e.getKeyCode() == KeyEvent.VK_U) {
			ConsoleOutput.printMessageInConsole("Reloading all Images...", true);
			new Thread(new TimerTask() {
				@Override
				public void run() {
					Images.loadImages();
					ConsoleOutput.printMessageInConsole("... done!", true);
				}
			}, "ReloadImages").start();
		}else if(e.getKeyCode() == KeyEvent.VK_T) {
			
			Images.updateFieldImages(false);
		
		}else if(e.getKeyCode() == KeyEvent.VK_O) {
			
			DebugWindow.startCoordinatesDebug();
			
		}
		//============== DEBUG ==============
		
		//General
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {	
			if(StandardData.showGrid == false) {
				StandardData.showGrid = true;
			}else {
				StandardData.showGrid = false;
			}
		}
		
		//Movement Update
		MovementHandler.updateKeys_RELEASE(e.getKeyCode());
		
		if(OnTopWindowData.onTopWindow != null) { //GENERAL OTW PRIO
			//HAS OTW OPEN
			
			//SPECIAL ESC PRIO FOR CLOSING OTW
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE 
					&& (OnTopWindowData.onTopWindow instanceof OnTopWindow_QueueWaiting) == false 
					&& (OnTopWindowData.onTopWindow instanceof OnTopWindow_GameAccept) == false 
					&& (OnTopWindowData.onTopWindow instanceof OnTopWindow_TabGameInfo) == false
					&& (OnTopWindowData.onTopWindow instanceof OnTopWindow_GameSyncStatus) == false
					&& (OnTopWindowData.onTopWindow instanceof OnTopWindow_PlayerDisconnect) == false) {
				//ESC AND NOT QUEUE WAITING or GAME ACCEPT or TAB INFO or GameSyncStatus or PlayerDisconnect (Cant be closed by ESC)
				OnTopWindowHandler.closeOTW();
			}else {
				OnTopWindowData.onTopWindow.onKeyRelease(e.getKeyCode());
			}
			
		}else if(StandardData.spielStatus == SpielStatus.Menu) {

			MenuHandler.keyReleaseEvent(e.getKeyCode());
			
		}else if(StandardData.spielStatus == SpielStatus.CreateMap) {

			CreateMapHandler.keyReleaseEvent(e.getKeyCode());
			
		}else if(StandardData.spielStatus == SpielStatus.Game) {
			
			GameHandler.keyReleasedEvent(e.getKeyCode());
			
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
