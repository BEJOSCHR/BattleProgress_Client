package me.bejosch.battleprogress.client.Window.LabelParts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ConcurrentModificationException;

import me.bejosch.battleprogress.client.Data.SpectateData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Game.Draw.Game_DrawMap;
import me.bejosch.battleprogress.client.Handler.SpectateHandler;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class Display_5Spectate {

//==========================================================================================================
	/**
	 * The methode, called by the Label for display this part
	 */
	public static void draw(Graphics g) {
		
		if(SpectateData.finishedInitLoading == true) {
			
			Field midField = SpectateHandler.get_MID_Field(false);
			Game_DrawMap.draw_Fields(g, SpectateData.gameMap_FieldList, midField, SpectateData.clickedField, SpectateData.hoveredField, null);
			Game_DrawMap.draw_Buildings(g, SpectateData.buildings, midField);
			Game_DrawMap.draw_Troups(g, SpectateData.troups, midField);
		
			draw_UI(g);
			draw_MouseActionAreas(g, false);
			
		}
		
	}

	private static void draw_UI(Graphics g) {
		
		int X = GameData.readyButton_X, Y = GameData.readyButton_Y, maße = GameData.readyButton_maße;
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(X, Y, maße, maße);
		g.setColor(Color.WHITE);
		g.drawRect(X, Y, maße, maße);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		int timeLeft = SpectateData.roundTime;
		int min = 9, sec = 59;
		if(timeLeft >= 240) {
			min = 4; sec = timeLeft-240;
		}else if(timeLeft >= 180) {
			min = 3; sec = timeLeft-180;
		}else if(timeLeft >= 120) {
			min = 2; sec = timeLeft-120;
		}else if(timeLeft >= 60) {
			min = 1; sec = timeLeft-60;
		}else {
			min = 0; sec = timeLeft;
		}
		String time = min+":"+Funktions.getDoubleWritenNumber(sec);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.drawString(time, X+(maße/2)-19, Y+56);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		String roundText = "Round "+SpectateData.round;
		int roundTextWidth = g.getFontMetrics().stringWidth(roundText);
		g.drawString(roundText, X+(maße/2)-roundTextWidth/2, Y+77);
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.drawString("Stop spectating", X+(maße/2)-44, Y+114);
		
	}
	
//==========================================================================================================
	/**
	 * Draws the MouseActionAreas (Buttons)
	 */
	public static void draw_MouseActionAreas(Graphics g, boolean OTWactive) {
		
		try{
			for(MouseActionArea actionArea : GameData.mouseActionAreas) {
				if(OTWactive == true) {
					if(actionArea.OTWMMA == true) {
						actionArea.draw(g);
					}
				}else {
					if(actionArea.OTWMMA == false) {
						actionArea.draw(g);
					}
				}
			}
		}catch(ConcurrentModificationException error) {}
		
	}
	
}
