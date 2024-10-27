package me.bejosch.battleprogress.client.Game.Handler;

import java.awt.event.KeyEvent;

import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.GameMenu.OnTopWindow_GameMenu;

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
			
		}
		
	}
		
		
	
	
}
