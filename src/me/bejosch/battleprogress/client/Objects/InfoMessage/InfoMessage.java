package me.bejosch.battleprogress.client.Objects.InfoMessage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.ImportanceType;

public class InfoMessage {

	public int textBorderY = 18, lineSpace = 11;
	
	public Color backgroundColor = new Color(45, 45, 45, 255); //DARK GREY
	public Color foregroundColor = null;
	public List<String> textLines = null;
	public int showTime = 0;
	public ImportanceType importance = ImportanceType.LOW;
	
	public int current_TR_X = 0, current_TR_Y = 0; 
	
//==========================================================================================================
	/**
	 * This message is shown in the top right corner as notification - LONG MESSAGE
	 * @param textLines_ - String[] - The displayed text (MANY lines)
	 * @param importance - {@link ImportanceType} - The level of importance
	 * @param removeAfterTime - boolean - true causes a remove after the importance specific time, false means a timeless display until it is removed by the user 
	 */
	public InfoMessage(List<String> textLines_, ImportanceType importance_, boolean removeAfterTime) {
		
		this.textLines = textLines_;
		this.importance = importance_;
		
		loadTypeSettings(importance_);
		
		//ADD
		while(!GameData.NotificationList.contains(thisGet())) {
			try{
				GameData.NotificationList.add(thisGet());
			}catch(ConcurrentModificationException error) {}
		}
		//REQUEST COORDS UPDATE FOR ACTION AREAS
		GameData.coordsUpdatedNeeded = true;
		
		if(removeAfterTime == true) {
			removeTimer();
		}
		
	}
//==========================================================================================================
	/**
	 * This message is shown in the top right corner as notification - SHORT MESSAGE
	 * @param text - String - The displayed text (ONE line)
	 * @param importance - {@link ImportanceType} - The level of importance 
	 * @param removeAfterTime - boolean - true causes a remove after the importance specific time, false means a timeless display until it is removed by the user 
	 */
	public InfoMessage(String text, ImportanceType importance_, boolean removeAfterTime) {
		
		List<String> line = new ArrayList<>();
		line.add(text);
		this.textLines = line;
		this.importance = importance_;
		
		loadTypeSettings(importance_);
		
		//ADD
		while(!GameData.NotificationList.contains(thisGet())) {
			try{
				GameData.NotificationList.add(thisGet());
			}catch(ConcurrentModificationException error) {}
		}
		//REQUEST COORDS UPDATE FOR ACTION AREAS
		GameData.coordsUpdatedNeeded = true;
		
		if(removeAfterTime == true) {
			removeTimer();
		}
		
	}
	
//==========================================================================================================
	/**
	 * Loads/Sets the parameter for the spezific type
	 * @param importance - {@link ImportanceType} - The given type
	 */
	public void loadTypeSettings(ImportanceType importance_) {
		
		foregroundColor = ImportanceType.getColor(importance_);
		showTime = ImportanceType.getShowTimeInMS(importance_);
		
	}
	
//==========================================================================================================
	/**
	 * Loads/Sets the parameter for the spezific type
	 * @param g - {@link Graphics} - The graphics it is drawed on
	 * @param TRpixle_x - int - ATTENTION: It's the Top Right corner Xpixle that is given!
	 * @param TRpixle_y - int - ATTENTION: It's the Top Right corner Ypixle that is given!
	 */
	public void draw(Graphics g, int TRpixle_x, int TRpixle_y) {
		
		current_TR_X = TRpixle_x; current_TR_Y = TRpixle_y;
		
		int height = getHeight();
		int width = getWidth();
		
		g.setColor(backgroundColor);
		g.fillRoundRect(TRpixle_x-width, TRpixle_y, width, height, 15, height/2);
		g.setColor(foregroundColor);
		g.drawRoundRect(TRpixle_x-width, TRpixle_y, width, height, 15, height/2);
		
		int durchlauf = 0;
		int textX = TRpixle_x-width+7, textY = TRpixle_y+textBorderY;
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 10));
		for(String line : textLines) {
			int Yadd = durchlauf*lineSpace;
			g.drawString(line, textX, textY+Yadd);
			durchlauf++;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Gives back the height
	 * @param textBorder - int - The borderSize to the top
	 * @param lineSpace - int - The distance between the lines
	 * @return int - The count of the height
	 */
	public int getHeight() {
		int output = 0, lineCount = textLines.size();
		output = lineCount * lineSpace + textBorderY;
		return output;
	}
//==========================================================================================================
	/**
	 * Gives back the width
	 * @return int - The count of the width
	 */
	public int getWidth() {
		int output = 0, longestLine = 0;
		for(String line : textLines)  {
			if(line.length() > longestLine) { longestLine=line.length(); }
		}
		output = longestLine * 6 ; //<<< The factor of textSize
		if(longestLine <= 30) {
			output += 15;
		}
		return output;
	}
	
//==========================================================================================================
	/**
	 * Starts the time which removes the notification after time
	 */
	public void removeTimer() {
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				thisGet().remove();
			}
		}, showTime);
		
	}
	
//==========================================================================================================
	/**
	 * Just returns this object
	 */
	public InfoMessage thisGet() {
		return this;
	}
	
//==========================================================================================================
	/**
	 * Removes this object
	 */
	public void remove() {
		
		//REMOVE
		while(GameData.NotificationList.contains(thisGet())) {
			try{
				GameData.NotificationList.remove(thisGet());
			}catch(ConcurrentModificationException error) {}
		}
		//REQUEST COORDS UPDATE FOR ACTION AREAS
		GameData.coordsUpdatedNeeded = true;
		
	}
	
}
