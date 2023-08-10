package me.bejosch.battleprogress.client.Objects.Animations;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.AnimationType;
import me.bejosch.battleprogress.client.Game.OverAllManager;
import me.bejosch.battleprogress.client.Window.Images.Images;
import me.bejosch.battleprogress.client.Window.LabelParts.Display_2Menu;

public class Animation_ShowMainMenu extends Animation {
	
	public Animation_ShowMainMenu() {
		super(AnimationType.ShowMainMenu);
		
		
	}

	@Override
	public void getParametersFromType() {
		speed = 15;
		faktor = 0;
		super.getParametersFromType();
	}
	
	@Override
	public void changeAction() {
		if(called == 0) {
			faktor = 0;
		}else if(called <= 40) {
			faktor += 7;
		}else if(called <= 70) {
			faktor = 255;
			//LOAD IMAGES DOWN BELOW
		}else if(called == 80) {
			faktor = 255;
			OverAllManager.switchTo_Menu_HauptMenu(false);
			Display_2Menu.playerLoadingDelay = false;
		}else if(called <= 81+50) {
			faktor = 255;
		}else if(called <= 105+50) {
			faktor -= 7;
		}else if(called <= 150+50) {
			//finished
			this.cancle();
		}
		super.changeAction();
	}
	
	@Override
	public void drawPart(Graphics g) {
		
		if(cancled == true) { return; }
		
		if(faktor > 255) { faktor = 255; }
		if(faktor < 0) { faktor = 0; }
				
		//PRE DISPLAY PROFIL IMAGES
		if(faktor == 255) {
//			ConsoleOutput.printMessageInConsole("DEBUG - 1. DRAW PRE IMAGE LOAD", true);
			for(Image img : Images.profileImages) {
				g.drawImage(img, WindowData.FrameWidth/2, WindowData.FrameHeight/2, null);
			}
			for(Image img : Images.backgroundImages) {
				g.drawImage(img, WindowData.FrameWidth/2, WindowData.FrameHeight/2, null);
			}
//			ConsoleOutput.printMessageInConsole("DEBUG - 2. DRAW PRE IMAGE LOAD", true);
		}
		
		//ABDUNKELN
		try {
			g.setColor(new Color(0, 0, 0, faktor));
			g.fillRect(0, 0, WindowData.FrameWidth+2*WindowData.rahmen, WindowData.FrameHeight+2*WindowData.rahmen);
		
			//TEXT
			g.setColor(new Color(255, 255, 255, faktor));
			g.setFont(new Font("Arial", Font.BOLD, 25));
			int textWidth = g.getFontMetrics().stringWidth("Loading...");
			g.drawString("Loading...", WindowData.FrameWidth/2 - textWidth/2 , WindowData.FrameHeight-80);
		}catch(IllegalArgumentException error) {}
		
		super.drawPart(g);
	}
	
	@Override
	public void cancle() {
		super.cancle();
	}
	
}
