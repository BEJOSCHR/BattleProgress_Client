package me.bejosch.battleprogress.client.Handler;

import java.awt.event.KeyEvent;

import me.bejosch.battleprogress.client.Data.MenuData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_Menu_BottomButton;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_Menu_FriendListButton;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_Menu_FriendListSection;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_Menu_GamePickButtons;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_Menu_GroupLeaveButton;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_Menu_Quit;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_Menu_ShowHideFriendList;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_Menu_ShowHideMatchHistory;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.MenuMenu.OnTopWindow_MenuMenu;

public class MenuHandler {

	public static void initMenuMAAs() {
		
		//MENU GENERAL
		new MouseActionArea_Menu_ShowHideMatchHistory(true);
		new MouseActionArea_Menu_ShowHideMatchHistory(false);
		new MouseActionArea_Menu_ShowHideFriendList(true);
		new MouseActionArea_Menu_ShowHideFriendList(false);
		new MouseActionArea_Menu_GamePickButtons(0);
		new MouseActionArea_Menu_GamePickButtons(1);
		new MouseActionArea_Menu_GamePickButtons(2);
		new MouseActionArea_Menu_GamePickButtons(3);
		new MouseActionArea_Menu_Quit();
		new MouseActionArea_Menu_BottomButton(0, true);
		new MouseActionArea_Menu_BottomButton(1, true);
		new MouseActionArea_Menu_BottomButton(0, false);
		new MouseActionArea_Menu_BottomButton(1, false);
		new MouseActionArea_Menu_GroupLeaveButton();
		
		//FRIEND LIST
		for(int i = 0 ; i < MenuData.rfl_friendSectionCount ; i++ ) {
			new MouseActionArea_Menu_FriendListSection(i); //SECTIONS
		}
		for(int i = 0 ; i < MenuData.rfl_friendSectionCount ; i++ ) {
			for(int j = 0 ; j < MenuData.rfl_friendButtonCount ; j++) {
				new MouseActionArea_Menu_FriendListButton(j, i); //BUTTONS PER SECTION
			}
		}
		
	}
	
//==========================================================================================================
	//EVENTS
	/**
	 * Called if the mouse has been clicked
	 */
	public static void mouseClickedEvent(int mX, int mY, int clickType) {
		
		
		
	}
	
//==========================================================================================================
	//EVENTS
	/**
	 * Called if the mouse has been pressed
	 */
	public static void mousePressedEvent(int mX, int mY, int clickType) {
		
		
		
	}
	
//==========================================================================================================
	//EVENTS
	/**
	 * Called if the mouse has been moved
	 */
	public static void mouseMovedEvent(int mX, int mY) {
		
		
		
	}
	
//==========================================================================================================
	//EVENTS
	/**
	 * Called if a key has been released
	 */
	public static void keyReleaseEvent(int keyCode) {
		
		if(keyCode == KeyEvent.VK_ESCAPE) {
			
			OnTopWindowHandler.openOTW(new OnTopWindow_MenuMenu());
			
		}
		
	}
	
//==========================================================================================================
	//EVENTS
	/**
	 * Called if the mouseWheel has been turned
	 */
	public static void mouseWheelTurnEvent(boolean scrollUp) {
		
		if(MouseHandler.mouseX > WindowData.FrameWidth-MenuData.rfl_width && MouseHandler.mouseY > MenuData.til_height && MouseHandler.mouseY < MenuData.til_height+MenuData.rfl_height) {
			//FRIENDLIST
			
			if(scrollUp == true) {
				if(MenuData.friendList_scrollValue > 0) {
					MenuData.friendList_scrollValue--;
				}
			}else {
				if(MenuData.friendList_scrollValue < (ProfilData.friendList_online.size()+ProfilData.friendList_offline.size())-(MenuData.rfl_friendSectionCount-2-2-2)) { // 2 HEADER, 2 SPACER, 2 FOOTER
					MenuData.friendList_scrollValue++;
				}
			}
			
		}else if(true) {
			//GAMEHISTORY
			
			//TODO
			
		}
		
	}
	
}
