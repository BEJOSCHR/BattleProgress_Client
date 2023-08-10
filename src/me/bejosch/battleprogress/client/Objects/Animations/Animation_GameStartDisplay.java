package me.bejosch.battleprogress.client.Objects.Animations;

import java.awt.Color;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.AnimationType;
import me.bejosch.battleprogress.client.Game.OverAllManager;
import me.bejosch.battleprogress.client.Window.Buttons.Buttons;
import me.bejosch.battleprogress.client.Window.ScrollPanes.ScrollPanes;
import me.bejosch.battleprogress.client.Window.TextAreas.TextAreas;
import me.bejosch.battleprogress.client.Window.TextFields.TextFields;

public class Animation_GameStartDisplay extends Animation {
	
	public Animation_GameStartDisplay() {
		super(AnimationType.Game_StartDisplay);
	}

	@Override
	public void getParametersFromType() {
		speed = 20;
		faktor = 0;
		super.getParametersFromType();
	}
	
	@Override
	public void changeAction() {
		if(called == 0) {
			faktor = 55;
			Buttons.hideAllButtons();
			TextFields.hideAlltextFields();
			TextAreas.hideAlltextAreas();
			ScrollPanes.hideAllScrollPanes();
		}else if(called <= 20) {
			faktor += 8;
		}else if(called == 21) {
			//Switch behind the animation
			OverAllManager.switchTo_Game();
			faktor = 255;
		}else if(called <= 25) {
			faktor = 255;
		}else if(called <= 45) {
			faktor -= 8;
		}else {
			//finished
			this.cancle();
		}
		super.changeAction();
	}
	
	@Override
	public void drawPart(Graphics g) {
		
		if(cancled == true) { return; }
		
		if(faktor > 255) { faktor = 255; }
		else if(faktor < 0) { faktor = 0; }
		
		//ABDUNKELN
		g.setColor(new Color(0, 0, 0, faktor));
		g.fillRect(0, 0, WindowData.FrameWidth+2*WindowData.rahmen, WindowData.FrameHeight+2*WindowData.rahmen);
		
		super.drawPart(g);
	}
	
	@Override
	public void cancle() {
		super.cancle();
	}
	
}
