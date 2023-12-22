package me.bejosch.battleprogress.client.Window.LabelParts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.LobbyData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Display_4GameLobby {

//==========================================================================================================
	/**
	 * The methode, called by the Label for display this part
	 */
	public static void draw(Graphics g) {
		
		g.drawImage(Images.backgroundImages.get(0), 0, 0, null); //TODO
		
		//STATUS
		draw_Status(g, LobbyData.status_x, LobbyData.status_y);
		
		//PLAYER
		draw_Player(g, LobbyData.player_x, LobbyData.player_y);
		
		//INFO
		draw_Info(g, LobbyData.info_x, LobbyData.info_y);
		
		//SETTINGS
		draw_Settings(g, LobbyData.settings_x, LobbyData.settings_y);
		
		//MAP PREVIEW
		draw_MiniMap(g, LobbyData.map_x, LobbyData.map_y);
		
		//CHAT
		draw_Chat(g, LobbyData.chat_x, LobbyData.chat_y);
		
	}
	
//==========================================================================================================
	/**
	 * Draws the GameStatus in the Lobby
	 */
	public static void draw_Status(Graphics g, int x, int y) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, LobbyData.status_width, LobbyData.status_height);
		
		if(LobbyData.readyToPlay == false) {
			g.setColor(Color.ORANGE);
			g.setFont(new Font("Arial", Font.BOLD, 16));
			g.drawString(LobbyData.whyNotPlayable, LobbyData.status_startButtonAndStatusTextX+5, LobbyData.status_buttonY+20);
		}
//		}else if(ProfilData.clientIsGameHost == false) {
//			g.setColor(Color.ORANGE);
//			g.setFont(new Font("Arial", Font.BOLD, 16));
//			g.drawString("Waiting for the host to start!", LobbyData.status_startButtonAndStatusTextX+5, LobbyData.status_buttonY+20);
//		}
		
	}
	
//==========================================================================================================
	/**
	 * Draws the Player in the Lobby
	 */
	public static void draw_Player(Graphics g, int x, int y) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, LobbyData.player_width, LobbyData.player_height);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 22));
		g.drawString("VS", x+(LobbyData.player_width/2)-12, y+(LobbyData.player_height/2)+7);
		
		if(SpielModus.isGameModus1v1() == false) {
			//2v2 (3 or 4 player)
			
			g.setColor(Funktions.getColorByPlayerNumber(1));
			g.setFont(new Font("Arial", Font.BOLD, 23));
			String displayedString_1 = (GameData.playingPlayer[0] == null ? LobbyData.player_noPlayerDisplay : GameData.playingPlayer[0].getName());
			g.drawString(displayedString_1, x+((LobbyData.player_width/4)*1)-20-LobbyData.player_xNameMove-( (displayedString_1.length()-6) *LobbyData.player_multiplayPerLetter), y+(LobbyData.player_height/2)-LobbyData.player_yDistanceToMid);
			
			g.setColor(Funktions.getColorByPlayerNumber(2));
			g.setFont(new Font("Arial", Font.BOLD, 23));
			String displayedString_2 = (GameData.playingPlayer[1] == null ? LobbyData.player_noPlayerDisplay : GameData.playingPlayer[1].getName());
			g.drawString(displayedString_2, x+((LobbyData.player_width/4)*1)-20-LobbyData.player_xNameMove-( (displayedString_2.length()-6) *LobbyData.player_multiplayPerLetter), y+(LobbyData.player_height/2)+LobbyData.player_yDistanceToMid+7);
			
			g.setColor(Funktions.getColorByPlayerNumber(3));
			g.setFont(new Font("Arial", Font.BOLD, 23));
			String displayedString_3 = (GameData.playingPlayer[2] == null ? LobbyData.player_noPlayerDisplay : GameData.playingPlayer[2].getName());
			g.drawString(displayedString_3, x+((LobbyData.player_width/4)*3)-LobbyData.player_xNameMove-( (displayedString_3.length()-6) *LobbyData.player_multiplayPerLetter), y+(LobbyData.player_height/2)-LobbyData.player_yDistanceToMid);
			
			g.setColor(Funktions.getColorByPlayerNumber(4));
			g.setFont(new Font("Arial", Font.BOLD, 23));
			String displayedString_4 = (GameData.playingPlayer[3] == null ? LobbyData.player_noPlayerDisplay : GameData.playingPlayer[3].getName());
			g.drawString(displayedString_4, x+((LobbyData.player_width/4)*3)-LobbyData.player_xNameMove-( (displayedString_4.length()-6) *LobbyData.player_multiplayPerLetter), y+(LobbyData.player_height/2)+LobbyData.player_yDistanceToMid+7);
			
		}else {
			//1v1 (1 or 2 player)
			
			g.setColor(Funktions.getColorByPlayerNumber(1));
			g.setFont(new Font("Arial", Font.BOLD, 23));
			String displayedString_1 = (GameData.playingPlayer[0] == null ? LobbyData.player_noPlayerDisplay : GameData.playingPlayer[0].getName());
			g.drawString(displayedString_1, x+((LobbyData.player_width/4)*1)-20-LobbyData.player_xNameMove-( (displayedString_1.length()-6) *LobbyData.player_multiplayPerLetter), y+(LobbyData.player_height/2)+7);
			
			g.setColor(Funktions.getColorByPlayerNumber(2));
			g.setFont(new Font("Arial", Font.BOLD, 23));
			String displayedString_2 = (GameData.playingPlayer[1] == null ? LobbyData.player_noPlayerDisplay : GameData.playingPlayer[1].getName());
			g.drawString(displayedString_2, x+((LobbyData.player_width/4)*3)-LobbyData.player_xNameMove-( (displayedString_2.length()-6) *LobbyData.player_multiplayPerLetter), y+(LobbyData.player_height/2)+7);
			
		}
		
	}
	
//==========================================================================================================
	/**
	 * Draws the GameInfo in the Lobby
	 */
	public static void draw_Info(Graphics g, int x, int y) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, LobbyData.info_width, LobbyData.info_height);
		
		//0/4
		g.setColor(Color.WHITE);
		g.drawLine(x, y+((LobbyData.info_height/4)*0)+LobbyData.map_rahmen, x+LobbyData.info_width-2, y+((LobbyData.info_height/4)*0)+LobbyData.map_rahmen);
		
		//GameID
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 11));
		g.drawString("GameID", x+LobbyData.info_smallTextXadd, y+((LobbyData.info_height/4)*0)+LobbyData.map_rahmen+LobbyData.info_smallTextYadd+2);
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.drawString(""+GameData.gameID, x+LobbyData.info_largeTextXadd, y+((LobbyData.info_height/4)*0)+LobbyData.map_rahmen+LobbyData.info_largeTextYadd+2);
		
		//1/4
		g.setColor(Color.WHITE);
		g.drawLine(x, y+((LobbyData.info_height/4)*1), x+LobbyData.info_width-2, y+((LobbyData.info_height/4)*1));
				
		//Modus (1v1 or 2v2)
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 11));
		g.drawString("Modus", x+LobbyData.info_smallTextXadd, y+((LobbyData.info_height/4)*1)+LobbyData.map_rahmen+LobbyData.info_smallTextYadd);
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Arial", Font.BOLD, 18));
		if(SpielModus.isGameModus1v1() == false) {
			//2v2 (3 or 4 player)
			g.drawString("2 vs 2", x+LobbyData.info_largeTextXadd, y+((LobbyData.info_height/4)*1)+LobbyData.map_rahmen+LobbyData.info_largeTextYadd);
		}else {
			//1v1 (1 or 2 player)
			g.drawString("1 vs 1", x+LobbyData.info_largeTextXadd, y+((LobbyData.info_height/4)*1)+LobbyData.map_rahmen+LobbyData.info_largeTextYadd);
		}
		
		//2/4
		g.setColor(Color.WHITE);
		g.drawLine(x, y+((LobbyData.info_height/4)*2), x+LobbyData.info_width-2, y+((LobbyData.info_height/4)*2));
		
		//Status (Ready or Not ready)
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 11));
		g.drawString("Status", x+LobbyData.info_smallTextXadd, y+((LobbyData.info_height/4)*2)+LobbyData.map_rahmen+LobbyData.info_smallTextYadd);
		g.setFont(new Font("Arial", Font.BOLD, 18));
		if(LobbyData.readyToPlay == true) {
			g.setColor(Color.GREEN);
			g.drawString("Ready", x+LobbyData.info_largeTextXadd, y+((LobbyData.info_height/4)*2)+LobbyData.map_rahmen+LobbyData.info_largeTextYadd);
		}else {
			g.setColor(Color.RED);
			g.drawString("Not Ready", x+LobbyData.info_largeTextXadd, y+((LobbyData.info_height/4)*2)+LobbyData.map_rahmen+LobbyData.info_largeTextYadd);
		}
		
		//3/4
		g.setColor(Color.WHITE);
		g.drawLine(x, y+((LobbyData.info_height/4)*3), x+LobbyData.info_width-2, y+((LobbyData.info_height/4)*3));
		
		//Player Anzahl
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 11));
		g.drawString("Player", x+LobbyData.info_smallTextXadd, y+((LobbyData.info_height/4)*3)+LobbyData.map_rahmen+LobbyData.info_smallTextYadd-2);
		g.setFont(new Font("Arial", Font.BOLD, 18));
		if(SpielModus.isGameModus1v1() == false) {
			//2v2 (3 or 4 player)
			if(GameData.playingPlayer[3] != null) {
				g.setColor(Color.GREEN);
				g.drawString("4/4", x+LobbyData.info_largeTextXadd, y+((LobbyData.info_height/4)*3)+LobbyData.map_rahmen+LobbyData.info_largeTextYadd-2);
			}else {
				g.setColor(Color.RED);
				g.drawString("3/4", x+LobbyData.info_largeTextXadd, y+((LobbyData.info_height/4)*3)+LobbyData.map_rahmen+LobbyData.info_largeTextYadd-2);
			}
		}else {
			//1v1 (1 or 2 player)
			if(GameData.playingPlayer[1] != null) {
				g.setColor(Color.GREEN);
				g.drawString("2/2", x+LobbyData.info_largeTextXadd, y+((LobbyData.info_height/4)*3)+LobbyData.map_rahmen+LobbyData.info_largeTextYadd-2);
			}else {
				g.setColor(Color.RED);
				g.drawString("1/2", x+LobbyData.info_largeTextXadd, y+((LobbyData.info_height/4)*3)+LobbyData.map_rahmen+LobbyData.info_largeTextYadd-2);
			}
		}
		
		//4/4
		g.setColor(Color.WHITE);
		g.drawLine(x, y+((LobbyData.info_height/4)*4)-LobbyData.map_rahmen, x+LobbyData.info_width-2, y+((LobbyData.info_height/4)*4)-LobbyData.map_rahmen);
		
	}
	
//==========================================================================================================
	/**
	 * Draws the Settings in the Lobby
	 */
	public static void draw_Settings(Graphics g, int x, int y) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, LobbyData.settings_width, LobbyData.settings_height);
		
		g.setColor(Color.WHITE);
		g.drawLine(x, y+((LobbyData.settings_height/4)*0)+LobbyData.map_rahmen, x+LobbyData.settings_width-2, y+((LobbyData.settings_height/4)*0)+LobbyData.map_rahmen); // 0/4tel
		
		//MapName
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 18));
		if(LobbyData.choosenMapName == null) { LobbyData.choosenMapName = "Loading..."; }
		g.drawString("Map: "+LobbyData.choosenMapName, x+(LobbyData.settings_width/4)-(LobbyData.choosenMapName.length()*3), y+((LobbyData.settings_height/4)*1)-13); // 1/4tel
		
		g.setColor(Color.WHITE);
		g.drawLine(x, y+((LobbyData.settings_height/4)*2), x+LobbyData.settings_width-2, y+((LobbyData.settings_height/4)*2)); // 2/4tel
		
		//TeamOrder
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.drawString("Team Order: "+LobbyData.teamChangeNumber, x+(LobbyData.settings_width/4)-20, y+((LobbyData.settings_height/4)*3)-13); // 3/4tel
		
		g.setColor(Color.WHITE);
		g.drawLine(x, y+((LobbyData.settings_height/4)*4)-LobbyData.map_rahmen, x+LobbyData.settings_width-2, y+((LobbyData.settings_height/4)*4)-LobbyData.map_rahmen); // 4/4tel
		
	}
	
//==========================================================================================================
	/**
	 * Draws the MiniMap in the Lobby
	 */
	public static void draw_MiniMap(Graphics g, int x, int y) {
		
		//MINIMAP - BACKGROUND
		int maße = LobbyData.map_maße; int rahmen = LobbyData.map_rahmen;
		int X = x; /*625*/ int Y = y;
		int normaliseFactorX = maße/StandardData.mapWidth; int normaliseFactorY = maße/StandardData.mapHight;
		g.setColor(Color.DARK_GRAY);
		g.fillRect(X-rahmen, Y-rahmen, LobbyData.map_maßeWithBorder, LobbyData.map_maßeWithBorder);
		g.setColor(Color.WHITE);
		g.drawRect(X-1, Y-1, maße+1, maße+1);
		g.setColor(FieldType.getMiniMapColorForFieldType(FieldType.Flatland));
		g.fillRect(X, Y, maße, maße);
		//MINIMAP - FIELDS
		if(LobbyData.choosenMapName != null) {
			try{
				for(int xCoords = 0 ; xCoords < StandardData.mapWidth ; xCoords++) {
					for(int yCoords = 0 ; yCoords < StandardData.mapHight ; yCoords++) {
						Field field = LobbyData.lobbyDisplay_FieldList[xCoords][yCoords];
						if(field.type != FieldType.Flatland) {
							int mmX = field.X*normaliseFactorX; int mmY = field.Y*normaliseFactorY;
							g.setColor(FieldType.getMiniMapColorForFieldType(field.type));
							g.fillRect(X+mmX, Y+mmY, normaliseFactorX, normaliseFactorY);
						}
					}
				}
			}catch(NullPointerException error) { }
		}
		
	}
	
//==========================================================================================================
	/**
	 * Draws the Chat of the Lobby
	 */
	public static void draw_Chat(Graphics g, int x, int y) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, 50, 20);
		
	}
	
}
