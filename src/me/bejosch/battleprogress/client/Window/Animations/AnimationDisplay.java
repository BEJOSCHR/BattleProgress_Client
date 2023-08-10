package me.bejosch.battleprogress.client.Window.Animations;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.NoSuchElementException;

import me.bejosch.battleprogress.client.Enum.AnimationType;
import me.bejosch.battleprogress.client.Objects.Animations.Animation;

public class AnimationDisplay {

	public static List<Animation> RunningAnimations = new ArrayList<Animation>();
	
	public static String ConnectionAnimation = ""; 

//==========================================================================================================
	/**
	 * Checks if an animation of the given type is running
	 * @param animationType - Animation (object) - The animationType which should be checked if running
	 * @return boolean - true if there is an animation running, false if not
	 */
	public static boolean hasRunningAnimationOfType(AnimationType animationType) {
		
		for(Animation animation : RunningAnimations) {
			if(animation.type == animationType) {
				return true;
			}
		}
		return false;
		
	}
	
//==========================================================================================================
	/**
	 * Checks if an animation of the given type is running and returns the first found animation
	 * NOTE: If multiple animations of the same type are running only the first found animation of this type is returned!
	 * @param animationType - Animation (object) - The animationType which should be checked if running
	 * @return {@link Animation} - An animation of the given type or null if no animation of this type is running
	 */
	public static Animation getRunningAnimationOfType(AnimationType animationType) {
		
		for(Animation animation : RunningAnimations) {
			if(animation.type == animationType) {
				return animation;
			}
		}
		return null;
		
	}
	
//==========================================================================================================
	/**
	 * Stopping all current animations
	 */
	public static void stopAllAnimations() {
		
		while(RunningAnimations.isEmpty() == false) {
			try{
				for(Animation animation : RunningAnimations) {
					animation.cancle();
				}
			}catch(ConcurrentModificationException | NoSuchElementException error) {}
		}
		
	}
	
//==========================================================================================================
	/**
	 * Stopping given animationType
	 * @param animationType - Animation (object) - The animationType which should be cancled
	 */
	public static void stopAnimationType(AnimationType animationType) {
		
		while(true) {
			boolean removedOne = false;
			try{
				for(Animation animation : RunningAnimations) {
					if(animation.type == animationType) {
						animation.cancle();
						removedOne = true;
					}
				}
				if(removedOne == false) { break;}
			}catch(ConcurrentModificationException error) {}
		}
		
	}
	
//==========================================================================================================
	/**
	 * Stopping all current animations
	 */
	public static void drawAnimations(Graphics g) {
		
		try{
			for(Animation animation : RunningAnimations) {
				animation.drawPart(g);
			}
		}catch(ConcurrentModificationException | NoSuchElementException error) {}
		
	}
	
}
