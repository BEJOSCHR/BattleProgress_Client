package me.bejosch.battleprogress.client.Objects.OnTopWindow.TabGameInfo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;

import me.bejosch.battleprogress.client.Data.MenuData;
import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.ClientPlayer;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;

public class OnTopWindow_TabGameInfo extends OnTopWindow {

	public OnTopWindow_TabGameInfo() {
		super("OTW_TabGameInfo", OnTopWindowData.tabGameInfo_width, OnTopWindowData.tabGameInfo_height);
		
		this.darkBackground = false;
		
	}
	
	
	private static int getX() {
		return WindowData.FrameWidth/2-OnTopWindowData.tabGameInfo_width/2;
	}
	private static int getY() {
		return OnTopWindowData.tabGameInfo_y;
	}
	
	@Override
	public void initOnOpen() {
		
	}
	
	@Override
	public void performClose() {
			
	}
	
	@Override
	public void onKeyRelease(int keyCode) {
		
		if(keyCode == KeyEvent.VK_TAB) {
			
			OnTopWindowHandler.closeOTW(true);
			
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(getX(), getY(), this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(getX(), getY(), this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 32));
		g.drawString("VS", getX()+this.width/2-20, getY()+this.height/2-8);
		
		if(SpielModus.isGameModus1v1()) {
			// 1V1
			if(GameData.playingPlayer[0] != null) {
				//PLAYER 1
				drawSection(g, GameData.playingPlayer[0], getPlayerDisplayCoords(0));
			}else {
				drawSection(g, null, getPlayerDisplayCoords(0));
			}
			if(GameData.playingPlayer[1] != null) {
				//PLAYER 2
				drawSection(g, GameData.playingPlayer[1], getPlayerDisplayCoords(1));
			}else {
				drawSection(g, null, getPlayerDisplayCoords(1));
			}
			drawSection(g, null, getPlayerDisplayCoords(2));
			drawSection(g, null, getPlayerDisplayCoords(3));
			
		}else {
			// 2V2
			g.setColor(Color.WHITE);
			g.drawLine(getX()+this.width/2, getY()+OnTopWindowData.tabGameInfo_border, getX()+this.width/2, getY()+OnTopWindowData.tabGameInfo_border+OnTopWindowData.tabGameInfo_sectionHeight);
			g.drawLine(getX()+this.width/2, getY()+OnTopWindowData.tabGameInfo_border+OnTopWindowData.tabGameInfo_borderBetween+OnTopWindowData.tabGameInfo_sectionHeight, getX()+this.width/2, getY()+OnTopWindowData.tabGameInfo_border+OnTopWindowData.tabGameInfo_borderBetween+OnTopWindowData.tabGameInfo_sectionHeight*2);
			
			if(GameData.playingPlayer[0] != null) {
				//PLAYER 1
				drawSection(g, GameData.playingPlayer[0], getPlayerDisplayCoords(0));
			}else {
				drawSection(g, null, getPlayerDisplayCoords(0));
			}
			if(GameData.playingPlayer[1] != null) {
				//PLAYER 2
				drawSection(g, GameData.playingPlayer[1], getPlayerDisplayCoords(1));
			}else {
				drawSection(g, null, getPlayerDisplayCoords(1));
			}
			if(GameData.playingPlayer[2] != null) {
				//PLAYER 3
				drawSection(g, GameData.playingPlayer[2], getPlayerDisplayCoords(2));
			}else {
				drawSection(g, null, getPlayerDisplayCoords(2));
			}
			if(GameData.playingPlayer[3] != null) {
				//PLAYER 4
				drawSection(g, GameData.playingPlayer[3], getPlayerDisplayCoords(3));
			}else {
				drawSection(g, null, getPlayerDisplayCoords(3));
			}
			
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		
		String mode = GameData.gameMode.toString()+" ("+GameData.gameID+")";
		g.drawString(mode, getX()+OnTopWindowData.tabGameInfo_border, getY()+this.height-10);
		
		String thisPing = "Ping: "+ProfilData.thisClient.getPing()+"ms";
		int ping_width = g.getFontMetrics().stringWidth(thisPing);
		g.drawString(thisPing, getX()+this.width-ping_width-OnTopWindowData.tabGameInfo_border, getY()+this.height-10);
		
	}
	
	private void drawSection(Graphics g, ClientPlayer player, Point cords) {
		
		String name = "Empty";
		Color nameColor = Color.LIGHT_GRAY;
		Color standardColor = Color.LIGHT_GRAY;
		int ping = 0;
		
		if(player != null) {
			name = player.getName();
			nameColor = player.getNameColor();
			standardColor = Color.WHITE;
			ping = player.getPing();
			g.drawImage(player.getProfileImg(), cords.x, cords.y, null);
			g.setColor(Funktions.getColorByPlayerID(player.getID()));
			g.fillRect(cords.x+MenuData.mpd_profileImage_width, cords.y, OnTopWindowData.tabGameInfo_colorBlockWidth, MenuData.mpd_profileImage_height);
			drawPingDisplay(g, cords, ping);
		}
		
		g.setColor(nameColor);
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.drawString(name, cords.x+5, cords.y+OnTopWindowData.tabGameInfo_sectionHeight-6);
		
		g.setColor(standardColor);
		g.drawRect(cords.x, cords.y, OnTopWindowData.tabGameInfo_sectionWidth, OnTopWindowData.tabGameInfo_sectionHeight);
		g.drawLine(cords.x, cords.y+MenuData.mpd_profileImage_height, cords.x+OnTopWindowData.tabGameInfo_sectionWidth, cords.y+MenuData.mpd_profileImage_height);
		
	}
	
	private void drawPingDisplay(Graphics g, Point cords, int ping) {
		
		int space = 2, width = 4, height1 = 5, height2 = 10, height3 = 15, height4 = 20;
		
		Color color = Color.RED; //OVER 100
		if(ping <= 25) { color = Color.GREEN; } //UNDER 25
		else if(ping <= 50) { color = Color.GREEN.darker().darker(); } //UNDER 50
		else if(ping <= 100) { color = Color.YELLOW; } //UNDER 75
		
		// 1. RECT
		g.setColor(color);
		g.fillRect(cords.x+OnTopWindowData.tabGameInfo_sectionWidth-5-((width+space)*1), cords.y+OnTopWindowData.tabGameInfo_sectionHeight-5-height1, width, height1);
		g.setColor(Color.WHITE);
		g.drawRect(cords.x+OnTopWindowData.tabGameInfo_sectionWidth-5-((width+space)*1), cords.y+OnTopWindowData.tabGameInfo_sectionHeight-5-height1, width, height1);
		
		// 2. RECT
		g.setColor(color);
		if(ping < 100) { g.fillRect(cords.x+OnTopWindowData.tabGameInfo_sectionWidth-5-((width+space)*2), cords.y+OnTopWindowData.tabGameInfo_sectionHeight-5-height2, width, height2); }
		g.setColor(Color.WHITE);
		g.drawRect(cords.x+OnTopWindowData.tabGameInfo_sectionWidth-5-((width+space)*2), cords.y+OnTopWindowData.tabGameInfo_sectionHeight-5-height2, width, height2);
		
		// 3. RECT
		g.setColor(color);
		if(ping < 50) { g.fillRect(cords.x+OnTopWindowData.tabGameInfo_sectionWidth-5-((width+space)*3), cords.y+OnTopWindowData.tabGameInfo_sectionHeight-5-height3, width, height3); }
		g.setColor(Color.WHITE);
		g.drawRect(cords.x+OnTopWindowData.tabGameInfo_sectionWidth-5-((width+space)*3), cords.y+OnTopWindowData.tabGameInfo_sectionHeight-5-height3, width, height3);
				
		// 4. RECT
		g.setColor(color);
		if(ping < 25) { g.fillRect(cords.x+OnTopWindowData.tabGameInfo_sectionWidth-5-((width+space)*4), cords.y+OnTopWindowData.tabGameInfo_sectionHeight-5-height4, width, height4); }
		g.setColor(Color.WHITE);
		g.drawRect(cords.x+OnTopWindowData.tabGameInfo_sectionWidth-5-((width+space)*4), cords.y+OnTopWindowData.tabGameInfo_sectionHeight-5-height4, width, height4);
		
	}
	
	public static Point getPlayerDisplayCoords(int playerNumber) {
		
		if(SpielModus.isGameModus1v1()) {
			if(playerNumber == 0) {
				return new Point(getX()+OnTopWindowData.tabGameInfo_border, getY()+OnTopWindowData.tabGameInfo_border);
			}else if(playerNumber == 2) {
				return new Point(getX()+OnTopWindowData.tabGameInfo_border, getY()+OnTopWindowData.tabGameInfo_border+OnTopWindowData.tabGameInfo_borderBetween+OnTopWindowData.tabGameInfo_sectionHeight);
			}else if(playerNumber == 1) { //DIFFERENT ORDER SO THAT PLAYER 2 IS AT POS 2 BUT STILL WITH INPUT 1
				return new Point(getX()+OnTopWindowData.tabGameInfo_border+OnTopWindowData.tabGameInfo_borderBetween+OnTopWindowData.tabGameInfo_sectionWidth+OnTopWindowData.tabGameInfo_extraCenterWidth, getY()+OnTopWindowData.tabGameInfo_border+OnTopWindowData.tabGameInfo_borderBetween+OnTopWindowData.tabGameInfo_sectionHeight);
			}else if(playerNumber == 3) {
				return new Point(getX()+OnTopWindowData.tabGameInfo_border+OnTopWindowData.tabGameInfo_borderBetween+OnTopWindowData.tabGameInfo_extraCenterWidth+OnTopWindowData.tabGameInfo_sectionWidth, getY()+OnTopWindowData.tabGameInfo_border);
			}
		}else {
			if(playerNumber == 0) {
				return new Point(getX()+OnTopWindowData.tabGameInfo_border, getY()+OnTopWindowData.tabGameInfo_border);
			}else if(playerNumber == 1) {
				return new Point(getX()+OnTopWindowData.tabGameInfo_border, getY()+OnTopWindowData.tabGameInfo_border+OnTopWindowData.tabGameInfo_borderBetween+OnTopWindowData.tabGameInfo_sectionHeight);
			}else if(playerNumber == 2) {
				return new Point(getX()+OnTopWindowData.tabGameInfo_border+OnTopWindowData.tabGameInfo_borderBetween+OnTopWindowData.tabGameInfo_sectionWidth+OnTopWindowData.tabGameInfo_extraCenterWidth, getY()+OnTopWindowData.tabGameInfo_border+OnTopWindowData.tabGameInfo_borderBetween+OnTopWindowData.tabGameInfo_sectionHeight);
			}else if(playerNumber == 3) {
				return new Point(getX()+OnTopWindowData.tabGameInfo_border+OnTopWindowData.tabGameInfo_borderBetween+OnTopWindowData.tabGameInfo_extraCenterWidth+OnTopWindowData.tabGameInfo_sectionWidth, getY()+OnTopWindowData.tabGameInfo_border);
			}
		}
		return null;
		
	}
	
}
