package me.bejosch.battleprogress.client.Handler;

import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.HoverData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class HoverHandler {

//==========================================================================================================
	/**
	 * Starts the hoverClearTimer, which clears the hover message if it doesn't get set again
	 */
	public static void startHoverClearTimer() {
		
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				
				List<MouseActionArea> maas = GameHandler.getMouseActionAreasFromScreenCoordinates(MouseHandler.mouseX, MouseHandler.mouseY);
				if( (maas.isEmpty() || maas.get(0).idName.equalsIgnoreCase(HoverData.hoverMaaName) == false ) && HoverData.hoverMessage != null) {
					//KEINE MAA MEHR oder EINE ANDERE DIE NICHT DEN HOVER GESETZT HAT
					HoverData.hoverMaaName = "null";
					HoverData.hoverMessage = null;
				}
				
			}
		}, 0, 10); //ALL 10 MS
		
	}
	
//==========================================================================================================
	/**
	 * Sets the current hover message to a new one
	 * @param maaName - String - The name of the MAA to identify it
	 * @param text - String[] - The text line(s)
	 */
	public static void updateHoverMessage(String maaName, String[] text) {
		HoverData.hoverMaaName = maaName;
		HoverData.hoverMessage = text;
	}
	
//==========================================================================================================
	/**
	 * Draws the HoverMessages
	 */
	public static void draw_HoverMessage(Graphics g) {
		
		if(HoverData.hoverMessage != null) {
			int mouseX = MouseHandler.mouseX, mouseY = MouseHandler.mouseY;
			int linesCount = HoverData.hoverMessage.length, longestLine = 0;
			for(String line : HoverData.hoverMessage) { if(line.length() > longestLine) { longestLine = line.length(); } } //CALCULATE LONGEST LINE
			int height = (linesCount*HoverData.space_hover)+HoverData.border_hover*2, width = (longestLine*6)+HoverData.border_hover*2;
			if(longestLine <= 15) { width += 10; }
			
			int realX, realY;
			if(mouseY < WindowData.FrameHeight/2) {
				//OBERE HÄLFTE
				if(mouseX < WindowData.FrameWidth/2) {
					//OBEN LINKS
					realX = mouseX;
					realY = mouseY-height;
				}else {
					//OBEN RECHTS
					realX = mouseX-width; 
					realY = mouseY-height;
				}
			}else {
				//UNTERE HÄLFTE
				if(mouseX < WindowData.FrameWidth/2) {
					//UNTEN LINKS
					realX = mouseX; 
					realY = mouseY-height;
				}else {
					//UNTEN RECHTS
					realX = mouseX-width;
					realY = mouseY-height;
				}
			}
			g.setColor(HoverData.backgroundColor_hover);
			g.fillRoundRect(realX, realY, width, height, 5, 5);
			g.setColor(HoverData.borderColor_hover);
			g.drawRoundRect(realX, realY, width, height, 5, 5);
			g.setColor(HoverData.foregroundColor_hover);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			try{
				for(int i = 0 ; i < HoverData.hoverMessage.length ; i++) {
					g.drawString(HoverData.hoverMessage[i], realX+HoverData.border_hover, realY+(HoverData.space_hover*(i+1))+HoverData.border_hover);
				}
			}catch(NullPointerException error) {}
		}
		
	}
	
}
