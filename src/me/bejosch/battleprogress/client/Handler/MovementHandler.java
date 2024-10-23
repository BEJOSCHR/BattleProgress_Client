package me.bejosch.battleprogress.client.Handler;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.CreateMapData;
import me.bejosch.battleprogress.client.Data.SpectateData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.SpielStatus;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;

public class MovementHandler {

	public static final int moveSpeed_slow = 3, moveSpeed_medium = 5, moveSpeed_fast = 7, moveSpeed_addSprint = 3;
	
	public static Timer movementTimer = null;
	public static int moveSpeed = moveSpeed_medium;
	
	public static boolean press_speed = false;
	
	public static boolean press_w = false;
	public static boolean press_a = false;
	public static boolean press_s = false;
	public static boolean press_d = false;
	
	
//==========================================================================================================
	/**
	 * startsTheMovementTimer which handles the movement via keys
	 */
	public static void startMovementTimer() {
		
		if(movementTimer == null) {
			
			movementTimer = new Timer();
			movementTimer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					
					@SuppressWarnings("unused")
					boolean moved = false;
					
					if(StandardData.spielStatus == SpielStatus.CreateMap) {
						
						if(press_w == true && CreateMapHandler.top_SpaceFree()) {
							CreateMapData.scroll_CM_UD_count += moveSpeed;
							moved = true;
						}else if(press_s == true && CreateMapHandler.bottom_SpaceFree()) {
							CreateMapData.scroll_CM_UD_count -= moveSpeed;
							moved = true;							
						}
						if(press_a == true && CreateMapHandler.left_SpaceFree()) {
							CreateMapData.scroll_CM_LR_count += moveSpeed;
							moved = true;							
						}else if(press_d == true && CreateMapHandler.right_SpaceFree()) {
							CreateMapData.scroll_CM_LR_count -= moveSpeed;
							moved = true;							
						}
						
					}else if(StandardData.spielStatus == SpielStatus.Game) {
						
						if(press_w == true && GameHandler.top_SpaceFree()) {
							GameData.scroll_UD_count += moveSpeed;
							moved = true;
						}else if(press_s == true && GameHandler.bottom_SpaceFree()) {
							GameData.scroll_UD_count -= moveSpeed;
							moved = true;
						}
						if(press_a == true && GameHandler.left_SpaceFree()) {
							GameData.scroll_LR_count += moveSpeed;
							moved = true;
						}else if(press_d == true && GameHandler.right_SpaceFree()) {
							GameData.scroll_LR_count -= moveSpeed;
							moved = true;
						}
						
					}else if(StandardData.spielStatus == SpielStatus.Spectate) {
						
						if(press_w == true && SpectateHandler.top_SpaceFree()) {
							SpectateData.scroll_UD_count += moveSpeed;
							moved = true;
						}else if(press_s == true && SpectateHandler.bottom_SpaceFree()) {
							SpectateData.scroll_UD_count -= moveSpeed;
							moved = true;
						}
						if(press_a == true && SpectateHandler.left_SpaceFree()) {
							SpectateData.scroll_LR_count += moveSpeed;
							moved = true;
						}else if(press_d == true && SpectateHandler.right_SpaceFree()) {
							SpectateData.scroll_LR_count -= moveSpeed;
							moved = true;
						}
						
					}
					
					//TODO WORKS????? -> NO TO LAGGY -  JUST ONE TIME !!! IF MOVE HAPPEND - STILL LAGGY
//					if(moved == true) {
//						try { MouseHandler.mouseMove(WindowData.Frame.getMousePosition().x+MouseHandler.mouseXausgleich, WindowData.Frame.getMousePosition().y+MouseHandler.mouseYausgleich); }catch(NullPointerException error) {}
//					}
					
				}
			}, 0, 10);
			
		}
		
	}
	
//==========================================================================================================
	/**
	 * called if a key is pressed
	 */
	public static void updateKeys_PRESS(int keyCode) {
		if(StandardData.cancleMovement == false) {
			if(keyCode == KeyEvent.VK_W) {
				if(MovementHandler.press_w == false) {
					MovementHandler.press_w = true;
				}
			}else if(keyCode == KeyEvent.VK_A) {
				if(MovementHandler.press_a == false) {
					MovementHandler.press_a = true;
				}
			}else if(keyCode == KeyEvent.VK_S) {
				if(MovementHandler.press_s == false) {
					MovementHandler.press_s = true;
				}
			}else if(keyCode == KeyEvent.VK_D) {
				if(MovementHandler.press_d == false) {
					MovementHandler.press_d = true;
				}
			}else if(keyCode == KeyEvent.VK_SHIFT) {
				if(MovementHandler.press_speed == false) {
					MovementHandler.press_speed = true;
					MovementHandler.moveSpeed += MovementHandler.moveSpeed_addSprint;
				}
			}
		}
	}
	
//==========================================================================================================
	/**
	 * called if a key is released
	 */
	public static void updateKeys_RELEASE(int keyCode) {
		if(StandardData.cancleMovement == false) {
			if(keyCode == KeyEvent.VK_W) {
				if(MovementHandler.press_w == true) {
					MovementHandler.press_w = false;
				}
			}else if(keyCode == KeyEvent.VK_A) {
				if(MovementHandler.press_a == true) {
					MovementHandler.press_a = false;
				}
			}else if(keyCode == KeyEvent.VK_S) {
				if(MovementHandler.press_s == true) {
					MovementHandler.press_s = false;
				}
			}else if(keyCode == KeyEvent.VK_D) {
				if(MovementHandler.press_d == true) {
					MovementHandler.press_d = false;
				}
			}else if(keyCode == KeyEvent.VK_SHIFT) {
				if(MovementHandler.press_speed == true) {
					MovementHandler.press_speed = false;
					MovementHandler.moveSpeed -= MovementHandler.moveSpeed_addSprint;
				}
			}
		}
	}
	
//==========================================================================================================
	/**
	 * stoppsTheMovementTimer which handles the movement via keys
	 */
	public static void stopMovementTimer() {
		
		press_w = false;
		press_a = false;
		press_s = false;
		press_d = false;
		
		if(press_speed == true) {
			MovementHandler.moveSpeed -= MovementHandler.moveSpeed_addSprint;
		}
		press_speed = false;
		
		if(movementTimer != null) {
			movementTimer.cancel();
			movementTimer = null;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Cancled every movement until it is allowed again
	 */
	public static void cancleMovement() {
		
		StandardData.cancleMovement = true;
		
		press_w = false;
		press_a = false;
		press_s = false;
		press_d = false;
		
		if(press_speed == true) {
			MovementHandler.moveSpeed -= MovementHandler.moveSpeed_addSprint;
		}
		press_speed = false;
		
	}
	
//==========================================================================================================
	/**
	 * Allow every movement after it got blocked/cancled
	 */
	public static void allowMovement() {
		
		StandardData.cancleMovement = false;
		
	}
	
	
}
