package me.bejosch.battleprogress.client.Handler;

import java.awt.Color;
import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Enum.AnimationType;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_OTW_Close;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_OTW_Open;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MultiSwitch.MouseActionArea_MultiSwitch_Str;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.ConfirmSurrender.MAA_OTW_ConfSur_Cancel;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.ConfirmSurrender.MAA_OTW_ConfSur_Surrender;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.FriendAdd.MAA_OTW_FriendAdd_Add;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.FriendAdd.MAA_OTW_FriendAdd_Cancel;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.FriendRemove.MAA_OTW_FriendRemove_Cancel;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.FriendRemove.MAA_OTW_FriendRemove_Remove;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.FriendRequests.MAA_OTW_FriendRequests_Accept;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.FriendRequests.MAA_OTW_FriendRequests_Close;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.FriendRequests.MAA_OTW_FriendRequests_Decline;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.GameAccept.MAA_OTW_GameAccept_Accept;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.GameAccept.MAA_OTW_GameAccept_Decline;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.GameMenu.MAA_OTW_GameMenu_1Continue;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.GameMenu.MAA_OTW_GameMenu_2Settings;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.GameMenu.MAA_OTW_GameMenu_3Credits;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.GameMenu.MAA_OTW_GameMenu_4Surrender;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.GroupInvitation.MAA_OTW_GroupInvitation_Accept;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.GroupInvitation.MAA_OTW_GroupInvitation_Decline;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.InfoMessage.MAA_OTW_InfoMessage_Confirm;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.InfoMessage.MAA_OTW_InfoMessage_Discord;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Login.MAA_OTW_Login_Close;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Login.MAA_OTW_Login_Login;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Login.MAA_OTW_Login_Register;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.MenuMenu.MAA_OTW_MenuMenu_1Continue;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.MenuMenu.MAA_OTW_MenuMenu_2Settings;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.MenuMenu.MAA_OTW_MenuMenu_3Credits;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.MenuMenu.MAA_OTW_MenuMenu_4Exit;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.PlayerChat.MAA_OTW_PlayerChat_Close;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.PlayerChat.MAA_OTW_PlayerChat_Send;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.QueueWaiting.MAA_OTW_QueueWaiting_LeaveQueue;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Research.MAA_OTW_Research_Category_1Economic;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Research.MAA_OTW_Research_Category_2LandTroups;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Research.MAA_OTW_Research_Category_3AirTroups;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Research.MAA_OTW_Research_Close;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Research.MAA_OTW_Research_RPDisplay;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Research.MAA_OTW_Research_Slot;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.ResearchConfirm.MAA_OTW_ResearchConfirm_Close;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.ResearchConfirm.MAA_OTW_ResearchConfirm_Research;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.RoundSummary.MAA_OTW_RoundSum_Confirm;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.RoundSummary.MAA_OTW_RoundSum_Details_BuildProduceUpgrade;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.RoundSummary.MAA_OTW_RoundSum_Details_Damage;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.RoundSummary.MAA_OTW_RoundSum_Details_Energy;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.RoundSummary.MAA_OTW_RoundSum_Details_HealRepair;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.RoundSummary.MAA_OTW_RoundSum_Details_KillsDeaths;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.RoundSummary.MAA_OTW_RoundSum_Details_Mass;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.RoundSummary.MAA_OTW_RoundSum_Details_Research;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Settings.MAA_OTW_Settings_Cancel;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Settings.MAA_OTW_Settings_Save;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.UnitDetailInfo.MAA_OTW_UnitDetailInfo_Confirm;
import me.bejosch.battleprogress.client.Window.Animations.AnimationDisplay;

public class OnTopWindowHandler {
	
	//CREATE ALL OTW MMAs
	public static void initOTWMAA() {
		
		//GameMenu
		new MAA_OTW_GameMenu_1Continue();
		new MAA_OTW_GameMenu_2Settings();
		new MAA_OTW_GameMenu_3Credits();
		new MAA_OTW_GameMenu_4Surrender();
		
		//INFO MESSAGE
		new MAA_OTW_InfoMessage_Confirm();
		new MAA_OTW_InfoMessage_Discord();
		
		//LOGIN
		new MAA_OTW_Login_Login();
		new MAA_OTW_Login_Register();
		new MAA_OTW_Login_Close();
		
		//GROUP INVITATION
		new MAA_OTW_GroupInvitation_Accept();
		new MAA_OTW_GroupInvitation_Decline();
		
		//CONFIRM SURRENDER
		new MAA_OTW_ConfSur_Surrender();
		new MAA_OTW_ConfSur_Cancel();
		
		//ROUND SUMMARY
		new MAA_OTW_RoundSum_Confirm();
		new MAA_OTW_RoundSum_Details_Mass();
		new MAA_OTW_RoundSum_Details_Energy();
		new MAA_OTW_RoundSum_Details_Research();
		new MAA_OTW_RoundSum_Details_KillsDeaths();
		new MAA_OTW_RoundSum_Details_HealRepair();
		new MAA_OTW_RoundSum_Details_Damage();
		new MAA_OTW_RoundSum_Details_BuildProduceUpgrade();
		
		//SETTINGS
		new MAA_OTW_Settings_Cancel();
		new MAA_OTW_Settings_Save();
		
		List<String> possibleStates = new ArrayList<String>();
		possibleStates.add(" Slow "); possibleStates.add("Medium"); possibleStates.add(" Fast ");
		String[] hovermessage = {"Change the speed of moving over the map", "You can move faster with SHIFT as well!"};
		new MouseActionArea_MultiSwitch_Str(WindowData.FrameWidth/2+150, WindowData.FrameHeight/2-OnTopWindowData.settings_height/2+50
				, WindowData.FrameWidth/2+300, WindowData.FrameHeight/2-OnTopWindowData.settings_height/2+80
				, "Settings_Gameplay_MoveSpeed", hovermessage, Color.WHITE, Color.ORANGE, "Settings_Gameplay", possibleStates, 1
				, 22, 8, 8, true);
		
		//RESEARCH
		new MAA_OTW_Research_Category_1Economic();
		new MAA_OTW_Research_Category_2LandTroups();
		new MAA_OTW_Research_Category_3AirTroups();
		for(int x = 0 ; x <= 4 ; x++) {
			for(int y = 0 ; y < OnTopWindowData.research_maxShownLines ; y++) {
				new MAA_OTW_Research_Slot(x, y);
			}
		}
		new MAA_OTW_Research_RPDisplay();
		new MAA_OTW_Research_Close();
		
		//RESEARCH CONFIRM
		new MAA_OTW_ResearchConfirm_Research();
		new MAA_OTW_ResearchConfirm_Close();
		
		//UnitDetailInfo
		new MAA_OTW_UnitDetailInfo_Confirm();
		
		//PlayerChat
		new MAA_OTW_PlayerChat_Close();
		new MAA_OTW_PlayerChat_Send();
		
		//FriendAdd
		new MAA_OTW_FriendAdd_Add();
		new MAA_OTW_FriendAdd_Cancel();
		
		//FriendRequests
		new MAA_OTW_FriendRequests_Close();
		for(int i = 0 ; i < OnTopWindowData.friendRequests_totalSectionCount ; i++) {
			new MAA_OTW_FriendRequests_Accept(i);
			new MAA_OTW_FriendRequests_Decline(i);
		}
		
		//FriendRemove
		new MAA_OTW_FriendRemove_Remove();
		new MAA_OTW_FriendRemove_Cancel();
		
		//QueueWaiting
		new MAA_OTW_QueueWaiting_LeaveQueue();
		
		//GameAccept
		new MAA_OTW_GameAccept_Accept();
		new MAA_OTW_GameAccept_Decline();
		
		//MenuMenu
		new MAA_OTW_MenuMenu_1Continue();
		new MAA_OTW_MenuMenu_2Settings();
		new MAA_OTW_MenuMenu_3Credits();
		new MAA_OTW_MenuMenu_4Exit();
		
	}
	
	public static void openOTW(OnTopWindow otw) {
		
		if(RoundData.roundIsChanging == true) {
			//NO OTW DURING ROUND CHANGES
			return;
		}
		
		if(OnTopWindowData.onTopWindow != null) {
			OnTopWindowData.onTopWindow.performClose();
		}
		AnimationDisplay.stopAnimationType(AnimationType.OTW_Close);
		OnTopWindowData.onTopWindow = otw;
		new Animation_OTW_Open(otw.width, otw.height);
		//INIT ON OPEN CALL IN ANIMATION
		
	}
	
	public static void closeOTW() {
		
		boolean wasNotFullyOpened = OnTopWindowData.otwAnimationRunning;
		AnimationDisplay.stopAnimationType(AnimationType.OTW_Open);
		
		if(OnTopWindowData.onTopWindow != null) {
			String idName = OnTopWindowData.onTopWindow.name;
			if(idName.equalsIgnoreCase("OTW_Login") && ProfilData.successlogin == false) { return; } //LOGIN CANT BE CLOSED BEFORE SUCCESS
			OnTopWindowData.onTopWindow.performClose(); //COULD OPEN AN OTHER OTW (research as example)
			if(idName.equalsIgnoreCase(OnTopWindowData.onTopWindow.name)) {
				//NO NEW OTW SO DELETE OLD
				if(wasNotFullyOpened == false) { 
					new Animation_OTW_Close(OnTopWindowData.onTopWindow.width, OnTopWindowData.onTopWindow.height);
				}
				OnTopWindowData.onTopWindow = null;
			}
		}
		
	}
	
	//TRANSFORM ALL MAA SETTINGS INTO INGAME SETTINGS
	public static void performSettingsSave() {
		
		//MoveSpeed
		MouseActionArea_MultiSwitch_Str MAA_moveSpeed = (MouseActionArea_MultiSwitch_Str) GameHandler.getMouseActionAreaByName("MS_Settings_Gameplay_MoveSpeed");
		switch(MAA_moveSpeed.getCurrentState()) {
		case " Slow ":
			MovementHandler.moveSpeed = MovementHandler.moveSpeed_slow;
			break;
		case "Medium":
			MovementHandler.moveSpeed = MovementHandler.moveSpeed_medium;
			break;
		case " Fast ":
			MovementHandler.moveSpeed = MovementHandler.moveSpeed_fast;
			break;
		}
		
		
		
		//AFTER SAVE CHANGES ARE APPLIED
		OnTopWindowData.settingsHasBeenModified = false;
		
	}
	
	//RESETTS ALL MAA SETTINGS FROM INGAME SETTINGS
	public static void performCancleResett() {
		
		//MoveSpeed
		MouseActionArea_MultiSwitch_Str MAA_moveSpeed = (MouseActionArea_MultiSwitch_Str) GameHandler.getMouseActionAreaByName("MS_Settings_Gameplay_MoveSpeed");
		switch(MovementHandler.moveSpeed) {
		case MovementHandler.moveSpeed_slow:
			MAA_moveSpeed.setCurrentState(" Slow ");
			break;
		case MovementHandler.moveSpeed_medium:
			MAA_moveSpeed.setCurrentState("Medium");
			break;
		case MovementHandler.moveSpeed_fast:
			MAA_moveSpeed.setCurrentState(" Fast ");
			break;
		}
		
		
		
		//CHANGES HAS BENN RESETTED
		OnTopWindowData.settingsHasBeenModified = false;
		
	}
	
	public static boolean openBrowserLink(String url) {
		
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(new URI(url));
	            return true;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
		
	}
	
}
