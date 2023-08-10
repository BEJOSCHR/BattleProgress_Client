package me.bejosch.battleprogress.client.Window.LabelParts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.MenuData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Game.Draw.Game_DrawOverlay;
import me.bejosch.battleprogress.client.Objects.ClientPlayer;

public class Display_2Menu {

	public static boolean playerLoadingDelay = true;
	
//==========================================================================================================
	/**
	 * The methode, called by the Label for display this part
	 */
	public static void draw(Graphics g) {
		
		//BACKGROUND
		g.drawImage(ProfilData.thisClient.getBackgroundImg(), MenuData.sideSectionsMAAwidth, MenuData.til_height, null);
		
		//TOP INFO LINE
		draw_TopInfoLine(g);
		
		//TOP GAME PICK MENU
		draw_TopGamePickMenu(g);
		
		//LEFT GAME HISTORY
		draw_LeftGameHistory(g);
		
		//RIGHT FRIEND LIST
		draw_RightFriendList(g);
		
		//BOTTOM BUTTON SECTION
		draw_BottomButtonSection(g);
		
		//MAIN PLAYERDISPLAY
		if(playerLoadingDelay == false) {
			draw_PlayerDisplay(g);
		}
		
		
		Game_DrawOverlay.draw_MouseActionAreas(g, false);
		
	}
	
//==========================================================================================================
	public static void draw_TopInfoLine(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, WindowData.FrameWidth, MenuData.til_height);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 60));
		g.drawString("BattleProgress", 30, MenuData.til_height-MenuData.til_buttonBorder);
		
		g.setColor(Color.WHITE);
		g.drawLine(0, MenuData.til_height, WindowData.FrameWidth, MenuData.til_height);
		
	}
	
//==========================================================================================================
	public static void draw_TopGamePickMenu(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(WindowData.FrameWidth/2-(MenuData.gpm_width/2), MenuData.til_height+1, MenuData.gpm_width, MenuData.gpm_height);
		
		g.setColor(Color.WHITE);
		g.drawLine(WindowData.FrameWidth/2-(MenuData.gpm_width/2), MenuData.til_height+1, WindowData.FrameWidth/2-(MenuData.gpm_width/2), MenuData.til_height+MenuData.gpm_height+1);
		g.drawLine(WindowData.FrameWidth/2+(MenuData.gpm_width/2), MenuData.til_height+1, WindowData.FrameWidth/2+(MenuData.gpm_width/2), MenuData.til_height+MenuData.gpm_height+1);
		g.drawLine(WindowData.FrameWidth/2-(MenuData.gpm_width/2), MenuData.til_height+MenuData.gpm_height+1, WindowData.FrameWidth/2+(MenuData.gpm_width/2), MenuData.til_height+MenuData.gpm_height+1);
		
	}
	
//==========================================================================================================
	public static void draw_LeftGameHistory(Graphics g) {
		
		if(MenuData.gameHistoryOpened == true || MenuData.lgh_OpenCloseFactor != 0.0) {
			
			int realWidthFactor = (int) (MenuData.lgh_width*MenuData.lgh_OpenCloseFactor);
			
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, MenuData.til_height+1, realWidthFactor, WindowData.FrameHeight-MenuData.til_height-MenuData.bbs_height);
			
			g.setColor(Color.WHITE);
			g.drawLine(realWidthFactor, MenuData.til_height+1, realWidthFactor, WindowData.FrameHeight-MenuData.bbs_height);
		}
		
		//FILL WHITE AREA BEHIND OPEN BUTTON
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, MenuData.til_height+1, MenuData.sideSectionsMAAwidth, (WindowData.FrameHeight-MenuData.bbs_height)-(MenuData.til_height+1));
		
		
	}
	
//==========================================================================================================
	public static void draw_RightFriendList(Graphics g) {
		 
		if(MenuData.friendListOpened == true || MenuData.rfl_OpenCloseFactor != 0.0) {
			
			int realWidthFactor = (int) (MenuData.rfl_width*MenuData.rfl_OpenCloseFactor);
			
			g.setColor(Color.DARK_GRAY);
			g.fillRect(WindowData.FrameWidth-realWidthFactor, MenuData.til_height+1, MenuData.rfl_width, MenuData.rfl_height);
			
			g.setColor(Color.WHITE);
			g.drawLine(WindowData.FrameWidth-realWidthFactor, MenuData.til_height+1, WindowData.FrameWidth-realWidthFactor, WindowData.FrameHeight-MenuData.bbs_height);
		}
		
		//FILL WHITE AREA BEHIND OPEN BUTTON
		g.setColor(Color.DARK_GRAY);
		g.fillRect(WindowData.FrameWidth-MenuData.sideSectionsMAAwidth, MenuData.til_height+1, MenuData.sideSectionsMAAwidth, (WindowData.FrameHeight-MenuData.bbs_height)-(MenuData.til_height+1));
		
	}
	
//==========================================================================================================
	public static void draw_BottomButtonSection(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, WindowData.FrameHeight-MenuData.bbs_height, MenuData.bbs_width_left, MenuData.bbs_height);
		g.fillRect(WindowData.FrameWidth-MenuData.bbs_width_right, WindowData.FrameHeight-MenuData.bbs_height, MenuData.bbs_width_right, MenuData.bbs_height);
		
		g.setColor(Color.WHITE);
		g.drawLine(0, WindowData.FrameHeight-MenuData.bbs_height, 0+MenuData.bbs_width_left, WindowData.FrameHeight-MenuData.bbs_height);
		g.drawLine(0+MenuData.bbs_width_left, WindowData.FrameHeight-MenuData.bbs_height, 0+MenuData.bbs_width_left, WindowData.FrameHeight);
		g.drawLine(WindowData.FrameWidth-MenuData.bbs_width_right, WindowData.FrameHeight-MenuData.bbs_height, WindowData.FrameWidth, WindowData.FrameHeight-MenuData.bbs_height);
		g.drawLine(WindowData.FrameWidth-MenuData.bbs_width_right, WindowData.FrameHeight-MenuData.bbs_height, WindowData.FrameWidth-MenuData.bbs_width_right, WindowData.FrameHeight);
		
		if(ProfilData.otherGroupClient != null) {
			//HAS OTHER PLAYER IN GROUP
			g.setColor(Color.DARK_GRAY);
			g.fillRect(WindowData.FrameWidth/2-MenuData.lgb_width/2, WindowData.FrameHeight-MenuData.lgb_height, MenuData.lgb_width, MenuData.lgb_height);
			g.setColor(Color.WHITE);
			g.drawRect(WindowData.FrameWidth/2-MenuData.lgb_width/2, WindowData.FrameHeight-MenuData.lgb_height, MenuData.lgb_width, MenuData.lgb_height);
		}
		
	}
	
//==========================================================================================================
	public static void draw_PlayerDisplay(Graphics g) {
		
		try {
			if(ProfilData.otherGroupClient != null) {
				drawOnePlayerSection(g, WindowData.FrameWidth/2-MenuData.mpd_width-(MenuData.mpd_spaceBetweenPlayerDisplay/2), WindowData.FrameHeight-MenuData.mpd_height-MenuData.mpd_borderDown, ProfilData.thisClient);
				drawOnePlayerSection(g, WindowData.FrameWidth/2+(MenuData.mpd_spaceBetweenPlayerDisplay/2), WindowData.FrameHeight-MenuData.mpd_height-MenuData.mpd_borderDown, ProfilData.otherGroupClient);
			}else {
				drawOnePlayerSection(g, WindowData.FrameWidth/2-MenuData.mpd_width/2, WindowData.FrameHeight-MenuData.mpd_height-MenuData.mpd_borderDown, ProfilData.thisClient);
			}
		}catch(Exception error) {} //HAPPENS ON JOIN/LEAVE GROUP SOMETIMES
		
	}
	
	public static void drawOnePlayerSection(Graphics g, int x, int y, ClientPlayer player) {
		
		//BACKGROUND
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, MenuData.mpd_width, MenuData.mpd_height);
		g.setColor(Color.WHITE);
		g.drawRect(x, y, MenuData.mpd_width, MenuData.mpd_height);
		
		//PROFILE IMAGE
		g.drawImage(player.getProfileImg(), x+MenuData.mpd_borderSide+MenuData.mpd_generalBorderInside, y+MenuData.mpd_borderTop+MenuData.mpd_generalBorderInside, null);
		g.setColor(Color.WHITE);
		g.drawRoundRect(x+MenuData.mpd_borderSide, y+MenuData.mpd_borderTop, MenuData.mpd_profileImage_width, MenuData.mpd_profileImage_height, 10, 10);
		
		int underSectionY = y+MenuData.mpd_profileImage_height+MenuData.mpd_borderTop+MenuData.mpd_underSection_borderTop;
		//LEVEL
		g.setColor(Color.WHITE);
		int xOffSet = 0; //3
		if(player.getLevel() < 10 ) { xOffSet = 25; } // 25
		else if(player.getLevel() < 100) { xOffSet = 14; } //14
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString(""+player.getLevel(), x+MenuData.mpd_borderSide+MenuData.mpd_generalBorderInside+xOffSet, underSectionY+MenuData.mpd_underSection_maße/2+15);
		g.setColor(Color.WHITE);
		g.drawRoundRect(x+MenuData.mpd_borderSide, underSectionY, MenuData.mpd_underSection_maße, MenuData.mpd_underSection_maße, 8, 8);
		
		//RANK IMAGE
		g.drawImage(player.getRankImg(), x+MenuData.mpd_borderSide+MenuData.mpd_underSection_maße+MenuData.mpd_underSection_spaceBetween+MenuData.mpd_generalBorderInside, underSectionY+MenuData.mpd_generalBorderInside, null);
		g.setColor(Color.WHITE);
		g.drawRoundRect(x+MenuData.mpd_borderSide+MenuData.mpd_underSection_maße+MenuData.mpd_underSection_spaceBetween, underSectionY, MenuData.mpd_underSection_maße, MenuData.mpd_underSection_maße, 8, 8);
		
		//NAME
		g.setColor(player.getNameColor());
//		g.setColor(Color.ORANGE);
		g.setFont(new Font("Arial", Font.BOLD, 28));
		int textWidth = g.getFontMetrics().stringWidth(player.getName());
		g.drawString(player.getName(), x+(MenuData.mpd_width/2)-(textWidth/2), underSectionY+MenuData.mpd_underSection_maße+MenuData.mpd_nameTopBorder);
		
		//STATUS
//		g.setColor(player.getNameColor());
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 18));
		int statusWidth = g.getFontMetrics().stringWidth(player.getStatus());
		g.drawString(player.getStatus(), x+(MenuData.mpd_width/2)-(statusWidth/2), underSectionY+MenuData.mpd_underSection_maße+MenuData.mpd_statusTopBorder);
		
	}
	
}