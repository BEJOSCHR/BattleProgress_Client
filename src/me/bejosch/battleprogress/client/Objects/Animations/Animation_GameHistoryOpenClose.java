package me.bejosch.battleprogress.client.Objects.Animations;

import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.MenuData;
import me.bejosch.battleprogress.client.Enum.AnimationType;

public class Animation_GameHistoryOpenClose extends Animation {
	
	boolean opening;
	
	public Animation_GameHistoryOpenClose(boolean opening) {
		super(AnimationType.Menu_GameHistoryOpenClose);
		
		this.opening = opening;
		
		if(this.opening == true) {
			//FROM 0 TO 1
			MenuData.lgh_OpenCloseFactor = 0.0;
		}else {
			//FROM 1 TO 0
			MenuData.lgh_OpenCloseFactor = 1.0;
		}
		
	}

	@Override
	public void getParametersFromType() {
		speed = 1;
		super.getParametersFromType();
	}
	
	@Override
	public void changeAction() {
		if(this.opening == true) {
			//FROM 0 TO 1
			if(MenuData.lgh_OpenCloseFactor >= 1.0) {
				MenuData.lgh_OpenCloseFactor = 1.0;
				this.cancle();
			}else {
				MenuData.lgh_OpenCloseFactor += MenuData.sideSectionAnimationSpeed;
			}
		}else {
			//FROM 1 TO 0
			if(MenuData.lgh_OpenCloseFactor <= 0.0) {
				MenuData.lgh_OpenCloseFactor = 0.0;
				this.cancle();
			}else {
				MenuData.lgh_OpenCloseFactor -= MenuData.sideSectionAnimationSpeed;
			}
		}
		super.changeAction();
	}
	
	@Override
	public void drawPart(Graphics g) {
		
		//
		
	}
	
	@Override
	public void cancle() {
		
		
		
		super.cancle();
	}
	
}
