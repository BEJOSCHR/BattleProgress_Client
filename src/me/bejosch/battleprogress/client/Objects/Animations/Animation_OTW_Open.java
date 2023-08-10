package me.bejosch.battleprogress.client.Objects.Animations;

import java.awt.Color;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.AnimationType;

public class Animation_OTW_Open extends Animation {
	
	int startWidth, startHeight;
	double factor = 0.0;
	
	public Animation_OTW_Open(int startWidth, int startHeight) {
		super(AnimationType.OTW_Open);
		
		this.startWidth = startWidth;
		this.startHeight = startHeight;
		
		OnTopWindowData.otwAnimationRunning = true;
		
	}

	@Override
	public void getParametersFromType() {
		speed = 1;
		this.factor = 0.0;
		super.getParametersFromType();
	}
	
	@Override
	public void changeAction() {
		if(factor >= 1) {
			factor = 1;
			this.cancle();
		}else {
			factor += OnTopWindowData.animationSpeed;
		}
		super.changeAction();
	}
	
	@Override
	public void drawPart(Graphics g) {
		
		if(cancled == true) { return; }
		
		int totalWidth = (int) (startWidth*factor);
		int totalHeight = (int) (startHeight*factor);
		int colorFactor = (int) (200*factor);
		if(colorFactor <= 0) { colorFactor = 1; }
		if(colorFactor >= 255) { colorFactor = 254; }
		
		//DAKR BACKGROUND
		g.setColor(new Color(0, 0, 0, colorFactor));
		g.fillRect(0, 0, WindowData.FrameWidth+2*WindowData.rahmen, WindowData.FrameHeight+2*WindowData.rahmen);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(WindowData.FrameWidth/2-totalWidth/2, WindowData.FrameHeight/2-totalHeight/2, totalWidth, totalHeight);
		
		g.setColor(Color.WHITE);
		g.drawRect(WindowData.FrameWidth/2-totalWidth/2, WindowData.FrameHeight/2-totalHeight/2, totalWidth, totalHeight);
		
		super.drawPart(g);
	}
	
	@Override
	public void cancle() {
		
		OnTopWindowData.onTopWindow.initOnOpen();
		
		OnTopWindowData.otwAnimationRunning = false;
		
		super.cancle();
	}
	
}
