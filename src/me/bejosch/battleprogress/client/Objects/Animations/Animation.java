 package me.bejosch.battleprogress.client.Objects.Animations;

import java.awt.Graphics;
import java.awt.Image;

import me.bejosch.battleprogress.client.Enum.AnimationType;
import me.bejosch.battleprogress.client.Game.Tick;
import me.bejosch.battleprogress.client.Window.Animations.AnimationDisplay;

public class Animation extends Tick {

	public AnimationType type = null;
	public int speed = 0;
	public Image image = null;
	public String sentence = "";
	public int X = 0;
	public int Y = 0;
	public int faktor = 0;
	
	public boolean cancled = false;
	public int speedchange = 0;
	public int called = 0;
	
//==========================================================================================================
	/**
	 * Constructor of the animation
	 * @param type - AnimationType - The type of this Animation
	 */
	public Animation(AnimationType type) {
		
//		while(thisGet().inTickList() == false) {
//			try{
				thisGet().addToTickList();
//			}catch(ConcurrentModificationException error) {}
//		}
		
//		while(!AnimationDisplay.RunningAnimations.contains(thisGet())) {
//			try{
				AnimationDisplay.RunningAnimations.add(thisGet());
//			}catch(ConcurrentModificationException | ArrayIndexOutOfBoundsException error) {}
//		}
		
		this.type = type;
		
		getParametersFromType();
		
	}
	
//==========================================================================================================
	/**
	 * Defines some parameters throw the AnimationType
	 * @param type - AnimationType - The type of this Animation, witch defines the Parameteres
	 */
	public void getParametersFromType() { }
	
//==========================================================================================================
	/**
	 * Reset the calls, so the circal starts at his beginning
	 */
	public void resetCalls() {
		
		called = -1;
		
	}
	
//==========================================================================================================
	@Override
	public void timeTick() {
		
		if(speedchange >= speed) {
			
			changeAction();
			
			called++;
			speedchange = 0;
		}else {
			speedchange++;
		}
		
	}
	
//==========================================================================================================
	/**
	 * The action witch is called every animation tick
	 */
	public void changeAction() { }
	
//==========================================================================================================
	/**
	 * The draw part witch is called by the Display
	 * @param g - Graphics - The graphic there everything is drawn
	 */
	public void drawPart(Graphics g) { }
	
//==========================================================================================================
	/**
	 * Just returns this object
	 */
	public Animation thisGet() {
		return this;
	}
	
//==========================================================================================================
	/**
	 * Cancle this animation and removes it from all lists
	 */
	public void cancle() { this.cancle(true); }
	public void cancle(boolean withListRemove) {
		
		cancled = true;
		
		thisGet().removeFromTickList();
		
		AnimationDisplay.RunningAnimations.remove(thisGet());
		
	}

	
	
}
