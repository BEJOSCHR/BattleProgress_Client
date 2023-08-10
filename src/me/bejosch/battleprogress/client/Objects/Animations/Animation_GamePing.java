package me.bejosch.battleprogress.client.Objects.Animations;

import java.awt.Color;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Enum.AnimationType;
import me.bejosch.battleprogress.client.Objects.Field.Field;

public class Animation_GamePing extends Animation {

	private final int blinkTimes = 5;
	private Field pingedField = null;

	public boolean blink = true;
	
	
	public Animation_GamePing(Field pingedField) {
		super(AnimationType.Game_Ping);
		
		this.pingedField = pingedField;
		
	}

	@Override
	public void getParametersFromType() {
		
		speed = 150;
		faktor = 0;
		
		super.getParametersFromType();
	}
	
	@Override
	public void changeAction() {
		
		if(blink == true) {
			blink = false;
			faktor++;
		}else {
			blink = true;
		}
		
		if(faktor == blinkTimes) {
			this.cancle();
		}
		
	}
	
	@Override
	public void drawPart(Graphics g) {
		
		if(blink == true) {
			this.pingedField.drawHighlight(g, Color.YELLOW);
		}
		
	}
	
}
