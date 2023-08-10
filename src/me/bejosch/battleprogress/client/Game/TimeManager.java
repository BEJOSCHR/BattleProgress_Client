package me.bejosch.battleprogress.client.Game;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;

public class TimeManager {

	//TICK TIMER
	public static int tickDelayInMSec = 3; //STANDARD 3
	public static Timer tickTimer = null;
	public static List<Tick> tickList = new ArrayList<Tick>();
	
	//GAME DURATION TIMER
	public static Timer durationTimer = null;
	public static int gameDuration_Min = 0;
	public static int gameDuration_Sec = 0;
	public static int gameDuration_Hour = 0;
	
//==========================================================================================================
	/**
	 * Starts the tickTimer which calls every tick object every tick
	 * @see - tickDelayInMSec - int - The time delay of one tick
	 */
	public static void startTickTimer() {
		
		tickTimer = new Timer();
		tickTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				
				try{
					for(Tick tick : tickList) {
						tick.timeTick();
					}
				}catch(ConcurrentModificationException | NoSuchElementException | NullPointerException error) {}
				
			}
		}, 0, tickDelayInMSec);
		
	}
	
//==========================================================================================================
	/**
	 * Stops the tickTimer which calls every tick object every tick
	 */
	public static void stopTickTimer() {
		
		if(tickTimer != null) {
			tickTimer.cancel();
			tickTimer = null;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Starts the durationTimer which calls every second
	 */
	public static void startDurationTimer() {
		
		durationTimer = new Timer();
		durationTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				
				gameDuration_Sec++;
				if(gameDuration_Sec >= 60) {
					gameDuration_Sec = 0;
					gameDuration_Min++;
					if(gameDuration_Min >= 60) {
						gameDuration_Min = 0;
						gameDuration_Hour++;
					}
				}
				
			}
		}, 0, 1000); //JEDE SEC
		
	}
	
//==========================================================================================================
	/**
	 * Stops the durationTimer which calls every second
	 */
	public static void stopDurationTimer() {
		
		if(durationTimer != null) {
			durationTimer.cancel();
			durationTimer = null;
		}
		
	}
	
}
