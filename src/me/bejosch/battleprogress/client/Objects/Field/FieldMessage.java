package me.bejosch.battleprogress.client.Objects.Field;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.ServerConnection.ServerConnection;

public class FieldMessage {

	public int ID = 0; //JUST AN ID FOR COMPARING FIELD MESSAGES
	
	public String message = null;
	public int fieldX = 0, fieldY = 0;
	public int displayDurationInSec = 3;
	
	public Timer deleteTimer = null;
	
//==========================================================================================================
	/**
	 * The FieldMessage object which is displayed as message on a field
	 */
	public FieldMessage(String message_, int fieldX_, int fieldY_, int displayDurationInSec_) {
		
		this.ID = ServerConnection.getNewPacketId();
		this.message = message_;
		this.fieldX = fieldX_;
		this.fieldY = fieldY_;
		this.displayDurationInSec = displayDurationInSec_;
		
		//SetActive
		if(GameData.activeMessage != null) { GameData.activeMessage.interrupt(); }
		GameData.activeMessage = this;
		//Start deleteTimer
		startDeleteTimer();
		
	}
	
//==========================================================================================================
	/**
	 * Draws the message on the field
	 */
	public void draw(Graphics g) {
		
		int realX = Funktions.getPixlesByCoordinate(fieldX, true, false);
		int realY = Funktions.getPixlesByCoordinate(fieldY, false, false);
		int fieldWidth = StandardData.fieldSize;
		
		//FIELD MARKER
		Funktions.drawBorderAroundFields(g, GameData.gameMap_FieldList[fieldX][fieldY], Color.ORANGE, 0, 5);
		//MESSAGE
		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString(message, realX+(fieldWidth/2)-(message.length()*3), realY-7);
		
	}
	
//==========================================================================================================
	/**
	 * Starts the timer which deletes this message after the display time is over
	 */
	public void startDeleteTimer() {
		
		if(deleteTimer == null) {
			deleteTimer = new Timer();
			deleteTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					delete();
				}
			}, 1000*displayDurationInSec);
		}
		
	}
	
//==========================================================================================================
	/**
	 * Stops the timer which deletes this message after the display time is over
	 */
	public void stopDeleteTimer() {
		
		if(deleteTimer != null) {
			deleteTimer.cancel();
			deleteTimer = null;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Removes this message from the active status
	 */
	public void delete() {
		
		GameData.activeMessage = null;
		
	}
	
//==========================================================================================================
	/**
	 * Called when an other message overwrittes this object as active message, it cancles this duration Timer
	 */
	public void interrupt() {
		
		stopDeleteTimer();
		delete();
		
	}
	
}
