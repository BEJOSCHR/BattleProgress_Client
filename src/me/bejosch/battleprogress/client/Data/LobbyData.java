package me.bejosch.battleprogress.client.Data;

import me.bejosch.battleprogress.client.Objects.Field.Field;

public class LobbyData {
	
	public static long createTimeStamp = 0;
	public static boolean readyToPlay = false;
	public static String whyNotPlayable = "";
	
	//mapSection
	public static int map_rahmen = 5, map_maße = StandardData.minimapMaße, map_maßeWithBorder = map_maße+(map_rahmen*2), map_distanceRight = 50, map_distanceDown = 50;
	public static int map_x = WindowData.FrameWidth-map_maßeWithBorder-map_distanceRight, map_y = WindowData.FrameHeight-map_maßeWithBorder-map_distanceDown;
	public static Field lobbyDisplay_FieldList[][] = null;
	public static String choosenMapName = "Loading...";
	public static int choosenMapNumber = 0;
	//TODO MAP CHANGE LOADING ANIMATION!
	
	//settingSection (mapChange + teamChange [TeamChange nur bei 4 spielern])
	public static int settings_button_width = 30, settings_button_height = 30;
	public static int settings_spaceToMap = 20, settings_borderRight = map_distanceRight+map_maßeWithBorder+settings_spaceToMap, settings_borderDown = map_distanceDown+map_rahmen, settings_width = 200, settings_height = map_maßeWithBorder;
	public static int settings_x = WindowData.FrameWidth-settings_borderRight-settings_width, settings_y = WindowData.FrameHeight-settings_borderDown-settings_height;
	public static int teamChangeNumber = 1;
	
	//statusSection (start+leave button+status)
	public static int status_spaceDown = settings_borderDown, status_spaceLeft = map_distanceRight;
	public static int status_width = 450, status_height = 50;
	public static int status_x = status_spaceLeft, status_y = WindowData.FrameHeight-status_spaceDown-status_height;
	public static int status_buttonBorderLR = 8, status_buttonBorderTD = 8, status_buttonHeight = status_height-(status_buttonBorderTD*2), status_leaveButtonWidth = 75, status_startButtonWidth = 75;
	public static int status_startButtonAndStatusTextX = status_x+status_buttonBorderLR, status_leaveButtonX = status_x+status_width-status_leaveButtonWidth-status_buttonBorderLR, status_buttonY = status_y+status_buttonBorderTD;
	
	//playerSection (1vs1 und 2vs2 unterschiedliche schrifft anordnung) 
	public static String player_noPlayerDisplay = "> Empty <";
	public static int player_multiplayPerLetter = 2, player_yDistanceToMid = 28, player_xNameMove = 40;
	public static int player_borderToStatus = settings_spaceToMap, player_spaceDown = status_height+status_spaceDown+player_borderToStatus, player_spaceLeft = status_spaceLeft;
	public static int player_width = status_width, player_height = map_maßeWithBorder-status_height-player_borderToStatus;
	public static int player_x = player_spaceLeft, player_y = WindowData.FrameHeight-player_spaceDown-player_height;
	
	//infoSection (gameid, host, player anzeige...)
	public static int info_distanceToPlayer = settings_spaceToMap, info_spaceDown = settings_borderDown, info_spaceLeft = player_spaceLeft+player_width+info_distanceToPlayer;
	public static int info_width = settings_width, info_height = settings_height;
	public static int info_x = info_spaceLeft, info_y = WindowData.FrameHeight-info_spaceDown-info_height;
	public static int info_smallTextXadd = ((LobbyData.info_width/10)*1), info_smallTextYadd = ((LobbyData.info_height/4)*1)/2-2, info_largeTextXadd = ((LobbyData.info_width/10)*4), info_largeTextYadd = ((LobbyData.info_height/4)*1)/2+0;
	
	//chatSection
	public static int chat_distanceToMap = 50, chat_borderRight = map_distanceRight, chat_borderDown = chat_distanceToMap+map_maßeWithBorder+map_distanceDown, chat_width = WindowData.FrameWidth-settings_x-chat_borderRight, chat_height = 300;
	public static int chat_x = WindowData.FrameWidth-chat_width-chat_borderRight, chat_y = WindowData.FrameHeight-chat_borderDown-chat_height;
	
}
