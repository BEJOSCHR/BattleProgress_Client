package me.bejosch.battleprogress.client.Data;

public class MenuData {
	
	public static double sideSectionAnimationSpeed = 0.04;
	
	//TOP INFO LINE
	public static int til_height = 80, til_buttonBorder = 20, til_buttonBorderSides = 20, til_buttonHeight = til_height-(2*til_buttonBorder), til_buttonWidth = 100;
	
	//GAME PICK MENU
	public static int gpm_buttonWidth = 158, gpm_buttonBorderBetween = 30, gpm_width = (gpm_buttonWidth*4)+(gpm_buttonBorderBetween*(4+1));
	public static int gpm_height = 70, gpm_buttonBorderTopDown = 13, gpm_buttonHeight = gpm_height-(gpm_buttonBorderTopDown*2);
	
	//BOTTOM BUTTON SECTION
	public static int bbs_height = 65, bbs_buttonWidth = 120, bbs_buttonHeight = 40, bbs_buttonBorderSide = 40, bbs_buttonBorderBetween = 40, bbs_buttonBorderTopDown = 12;
	public static int bbs_width_left = (bbs_buttonWidth*2)+(bbs_buttonBorderSide*2)+(bbs_buttonBorderBetween*1);
	public static int bbs_width_right = (bbs_buttonWidth*2)+(bbs_buttonBorderSide*2)+(bbs_buttonBorderBetween*1);
	
	public static int sideSectionsMAAwidth = 50;
	
	//LEFT GAME HISTORY
	public static boolean gameHistoryOpened = false;
	public static int lgh_width = 400, lgh_gameSectionBorder = 20, lgh_gameSectionWidth = lgh_width-(2*lgh_gameSectionBorder), lgh_gameSectionHeight = 50;
	public static double lgh_OpenCloseFactor = 0;
	
	//RIGHT FRIEND LIST
	public static boolean friendListOpened = false;
	public static int friendList_scrollValue = 0;
	public static int rfl_width = 400, rfl_height = WindowData.FrameHeight-MenuData.til_height-1-MenuData.bbs_height;
	public static int rfl_friendSectionBorder = 20, rfl_friendSectionWidth = rfl_width-(2*rfl_friendSectionBorder), rfl_friendSectionHeight = 50;
	public static int rfl_button_borderTopDown = 9, rfl_button_borderRight = rfl_button_borderTopDown, rfl_button_borderBetween = 10, rfl_button_maße = rfl_friendSectionHeight-(rfl_button_borderTopDown*2);
	public static int rfl_friendSectionCount = (rfl_height / MenuData.rfl_friendSectionHeight)-1;
	public static int rfl_friendButtonCount = 4;
	public static double rfl_OpenCloseFactor = 0;
	
	//MAIN PLAYER DISPLAY
	public static int mpd_width = 300, mpd_height = 370;
	public static int mpd_borderDown = WindowData.FrameHeight/2-mpd_height/2;
	public static int mpd_generalBorderInside = 8;
	public static int mpd_borderSide = 50, mpd_borderTop = 20;
	public static int mpd_profileImage_width = mpd_width-(mpd_borderSide*2), mpd_profileImage_height = mpd_profileImage_width/2;
	public static int mpd_underSection_borderTop = 5, mpd_underSection_spaceBetween = 25;
	public static int mpd_underSection_maße = (mpd_profileImage_width-mpd_underSection_spaceBetween)/2;
	public static int mpd_nameTopBorder = 50;
	public static int mpd_statusTopBorder = mpd_nameTopBorder+40;
	public static int mpd_spaceBetweenPlayerDisplay = 200;
	//	MPD Background
	public static int mpd_background_width = WindowData.FrameWidth-(2*sideSectionsMAAwidth);
	public static int mpd_background_height = WindowData.FrameHeight-til_height;
	
	//LEAVE GROUP BUTTON
	public static int lgb_width = 180, lgb_height = 50;
	public static int lgb_border = 10;
	public static int lgb_buttonWidth = lgb_width-(lgb_border*2), lgb_buttonHeight = lgb_height-(lgb_border*2); 
	
}
