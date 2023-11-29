package me.bejosch.battleprogress.client.Objects.OnTopWindow.TabGameInfo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;

import me.bejosch.battleprogress.client.Data.MenuData;
import me.bejosch.battleprogress.client.Data.OnTopWindowData;
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
		
		//TODO calculate ping on server and send results to client AND all other in the game to display
		if(SpielModus.isGameModus1v1()) {
			// 1V1
			if(GameData.playingPlayer[0] != null) {
				//PLAYER 1
				drawSection(g, GameData.playingPlayer[0], getPlayerDisplayCoords(0));
			}else {
				drawSection(g, null, getPlayerDisplayCoords(0));
			}
			drawSection(g, null, getPlayerDisplayCoords(1));
			drawSection(g, null, getPlayerDisplayCoords(2));
			if(GameData.playingPlayer[1] != null) {
				//PLAYER 2
				drawSection(g, GameData.playingPlayer[1], getPlayerDisplayCoords(2));
			}else {
				drawSection(g, null, getPlayerDisplayCoords(2));
			}
			
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
		
	}
	
	private void drawSection(Graphics g, ClientPlayer player, Point cords) {
		
		String name = "Empty";
		Color nameColor = Color.LIGHT_GRAY;
		Color standardColor = Color.LIGHT_GRAY;
		
		if(player != null) {
			name = player.getName();
			nameColor = player.getNameColor();
			standardColor = Color.WHITE;
			g.drawImage(player.getProfileImg(), cords.x, cords.y, null);
			g.setColor(Funktions.getColorByPlayerID(player.getID()));
			g.fillRect(cords.x+MenuData.mpd_profileImage_width, cords.y, OnTopWindowData.tabGameInfo_colorBlockWidth, MenuData.mpd_profileImage_height);
		}
		
		g.setColor(nameColor);
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.drawString(name, cords.x+10, cords.y+OnTopWindowData.tabGameInfo_sectionHeight-6);
		
		g.setColor(standardColor);
		g.drawRect(cords.x, cords.y, OnTopWindowData.tabGameInfo_sectionWidth, OnTopWindowData.tabGameInfo_sectionHeight);
		g.drawLine(cords.x, cords.y+MenuData.mpd_profileImage_height, cords.x+OnTopWindowData.tabGameInfo_sectionWidth, cords.y+MenuData.mpd_profileImage_height);

		
	}
	
	public static Point getPlayerDisplayCoords(int playerNumber) {
		
		if(playerNumber == 0) {
			return new Point(getX()+OnTopWindowData.tabGameInfo_border, getY()+OnTopWindowData.tabGameInfo_border);
		}else if(playerNumber == 1) {
			return new Point(getX()+OnTopWindowData.tabGameInfo_border, getY()+OnTopWindowData.tabGameInfo_border+OnTopWindowData.tabGameInfo_borderBetween+OnTopWindowData.tabGameInfo_sectionHeight);
		}else if(playerNumber == 2) {
			return new Point(getX()+OnTopWindowData.tabGameInfo_border+OnTopWindowData.tabGameInfo_borderBetween+OnTopWindowData.tabGameInfo_sectionWidth+OnTopWindowData.tabGameInfo_extraCenterWidth, getY()+OnTopWindowData.tabGameInfo_border+OnTopWindowData.tabGameInfo_borderBetween+OnTopWindowData.tabGameInfo_sectionHeight);
		}else if(playerNumber == 3) {
			return new Point(getX()+OnTopWindowData.tabGameInfo_border+OnTopWindowData.tabGameInfo_borderBetween+OnTopWindowData.tabGameInfo_extraCenterWidth+OnTopWindowData.tabGameInfo_sectionWidth, getY()+OnTopWindowData.tabGameInfo_border);
		}
		return null;
		
	}
	
}
