package me.bejosch.battleprogress.client.Game.Handler;

import java.awt.event.KeyEvent;

import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.TabGameInfo.OnTopWindow_TabGameInfo;

public class Game_KeyPressedEvent {

//==========================================================================================================
	//EVENTS
	/**
	 * Called if a key has been pressed
	 */
	public static void keyPressedEvent(int keyCode) {
		
		if(keyCode == KeyEvent.VK_TAB) {
			
			OnTopWindowHandler.openOTW(new OnTopWindow_TabGameInfo(), true);
			
		}
		
	}
	
}
