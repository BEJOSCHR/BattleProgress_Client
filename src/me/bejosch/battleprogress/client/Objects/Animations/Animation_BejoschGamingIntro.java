package me.bejosch.battleprogress.client.Objects.Animations;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Calendar;

import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.AnimationType;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Animation_BejoschGamingIntro extends Animation {
	
	Image img = Images.intro_BejoschGaming;
	
	public Animation_BejoschGamingIntro() {
		super(AnimationType.Intro_BejoschGaming);
		
		
	}

	@Override
	public void getParametersFromType() {
		speed = 10;
		faktor = 255;
		super.getParametersFromType();
	}
	
	@Override
	public void changeAction() {
		if(called == 0) {
			faktor = 255;
		}else if(called <= 40) {
			faktor -= 7;
		}else if(called <= 60) {
			faktor = 0;
		}else if(called <= 95) {
			faktor += 7;
		}else if(called <= 170) {
			//finished
			this.cancle();
			new Animation_BattleProgressIntro();
		}
		super.changeAction();
	}
	
	@Override
	public void drawPart(Graphics g) {
		
		if(cancled == true) { return; }
		
		if(faktor > 255) { faktor = 255; }
		if(faktor < 0) { faktor = 0; }
		
		//BACKGROUND
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WindowData.FrameWidth+2*WindowData.rahmen, WindowData.FrameHeight+2*WindowData.rahmen);
		
		//IMAGE
		g.drawImage(img, WindowData.FrameWidth/2-(WindowData.FrameWidth/2), WindowData.FrameHeight/2-( (WindowData.FrameWidth/3) /2), null);
		
		//TEXT
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString("BattleProgress by BejoschGaming © "+Calendar.getInstance().get(Calendar.YEAR), WindowData.FrameWidth/2 - 125 , WindowData.FrameHeight-80);
		
		//ABDUNKELN
		try {
			g.setColor(new Color(0, 0, 0, faktor));
			g.fillRect(0, 0, WindowData.FrameWidth+2*WindowData.rahmen, WindowData.FrameHeight+2*WindowData.rahmen);
		}catch(IllegalArgumentException error) {}
		
		super.drawPart(g);
	}
	
	@Override
	public void cancle() {
		super.cancle();
	}
	
}
