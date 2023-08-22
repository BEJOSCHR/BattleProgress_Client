package me.bejosch.battleprogress.client.Objects.Animations;

import java.awt.Graphics;

import me.bejosch.battleprogress.client.Enum.AnimationType;

public class Animation_ShowRoundSummary extends Animation {
	
	public Animation_ShowRoundSummary() {
		super(AnimationType.Game_ShowRoundSummary);
		
		
	}

	@Override
	public void getParametersFromType() {
		speed = 22;
		super.getParametersFromType();
	}
	
	@Override
	public void changeAction() {
		if(called > 150) {
			this.cancle();
		}
	}
	
	@Override
	public void drawPart(Graphics g) {}
	
	@Override
	public void cancle() {
		super.cancle();
	}
	
}
