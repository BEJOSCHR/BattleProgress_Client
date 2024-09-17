package me.bejosch.battleprogress.client.Game.Handler;

import java.awt.event.KeyEvent;

import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Debug.DebugWindow;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.GameMenu.OnTopWindow_GameMenu;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Game_KeyReleasedEvent {
	
//==========================================================================================================
	//EVENTS
	/**
	 * Called if a key has been released
	 */
	public static void keyReleasedEvent(int keyCode) {
		
		//ESC
		if(keyCode == KeyEvent.VK_ESCAPE) {
			
			//NOTHING SPECIAL OPEN, SO OPEN MENU
			OnTopWindowHandler.openOTW(new OnTopWindow_GameMenu());
			
		}else if(keyCode == KeyEvent.VK_SPACE) {
			
			if(StandardData.showGrid == false) {
				StandardData.showGrid = true;
			}else {
				StandardData.showGrid = false;
			}
		
		//============== DEBUG ==============
		}else if(keyCode == KeyEvent.VK_T) {
			
			Images.updateFieldImages(false);
		
		//============== DEBUG ==============
		}else if(keyCode == KeyEvent.VK_O) {
			
			DebugWindow.startCoordinatesDebug();
			
		}
		
	}
		
		
	
	
}
