package me.bejosch.battleprogress.client.Window.Animations;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import me.bejosch.battleprogress.client.Enum.AnimationType;
import me.bejosch.battleprogress.client.Objects.Animations.Animation;

public class AnimationDisplay {

	public static CopyOnWriteArrayList<Animation> RunningAnimations = new CopyOnWriteArrayList<Animation>();
	
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
	 * Returns all animations of this typ that are currently running
	 * @return {@link Animation} - An animation of the given type or null if no animation of this type is running
	 */
	public static List<Animation> getRunningAnimationOfType(AnimationType animationType) {
		
		List<Animation> animations = new ArrayList<>();
		for(Animation animation : RunningAnimations) {
			if(animation.type == animationType) {
				animations.add(animation);
			}
		}
		return animations;
		
	}
	
//==========================================================================================================
	/**
	 * Stopping all current animations
	 */
	public static void stopAllAnimations() {
		
		for(Animation animation : RunningAnimations) {
			animation.cancle(false);
		}
		RunningAnimations.clear();
		
	}
	
//==========================================================================================================
	/**
	 * Stopping given animationType
	 * @param animationType - Animation (object) - The animationType which should be cancled
	 */
	public static void stopAnimationType(AnimationType animationType) {
		
		List<Animation> toRemove = new ArrayList<>();
		
		for(Animation animation : RunningAnimations) {
			if(animation.type == animationType) {
				animation.cancle(false);
				toRemove.add(animation);
			}
		}
		
		if(!toRemove.isEmpty()) {
			RunningAnimations.removeAll(toRemove);
		}
		
	}
	
//==========================================================================================================
	/**
	 * Stopping all current animations
	 */
	public static void drawAnimations(Graphics g) {
		
//		try{
			for(Animation animation : RunningAnimations) {
				animation.drawPart(g);
			}
//		}catch(ConcurrentModificationException | NoSuchElementException | NullPointerException error) {}
		
	}
	
}
