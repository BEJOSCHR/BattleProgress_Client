package me.bejosch.battleprogress.client.Objects.MouseActionArea.Checkbox;

import java.awt.Color;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class MouseActionArea_Checkbox extends MouseActionArea{

	public boolean checkState = false;
	public Color standardColor = null, hoverColor = null, crossColor = null;
	
	public boolean backgroundFill = true;
	public Color backgroundColor = Color.DARK_GRAY;
	
//==========================================================================================================
	/**
	 * A special ActionArea for a Checkbox
	 * @param standardColor_ - {@link Color} - The color which is normaly shown
	 * @param hoverColor_ - {@link Color} - The color which is shown on hover (on activation)
	 * @param crossColor_ - {@link Color} - The color of the displayed cross
	 * @param startCheckState - boolean - The checkState (if it is crossed or not) at the beginning
	 * @param backgroundFill_ - boolean - True means with background, false without
	 * @param backgroundColor_ - {@link Color} - The color of the background (if background is enabled)
	 * @see MouseActionArea
	 **/
	public MouseActionArea_Checkbox(int xTL, int yTL, int xBR, int yBR, String idName, String[] hoverText_, Color standardColor_, Color hoverColor_, Color crossColor_, boolean startCheckState, boolean backgroundFill_, Color backgroundColor_) {
		super(xTL, yTL, xBR, yBR, "Checkbox_"+idName, hoverText_, ShowBorderType.ShowAlways, standardColor_, hoverColor_);
		
		this.checkState = startCheckState;
		this.crossColor = crossColor_;
		
		this.backgroundFill = backgroundFill_;
		this.backgroundColor = backgroundColor_;
		
	}
	
	@Override
	public boolean isActiv() {
		//POSSIBLE CHANGES: OverlayDisableFeature / Overlapping Menu / etc
		return super.isActiv();
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		if(checkState == true) {
			checkState = false;
		}else {
			checkState = true;
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(isActiv() == true) {
			
			//BACKGROUND
			if(backgroundFill == true) {
				g.setColor(backgroundColor);
				g.fillRect(xTL, yTL, xBR-xTL, yBR-yTL);
			}
			//CROSS
			if(checkState == true) {
				g.setColor(crossColor);
				g.drawLine(xTL, yTL, xBR, yBR);
				g.drawLine(xTL, yBR, xBR, yTL);
			}
			
		}
		
		//BORDER
		super.draw(g);
		
	}
	
	
//==========================================================================================================
	/**
	 * Gives the current checkState
	 * @return boolean - true if the box is checked (crossed/activated), false if not
	 **/
	public boolean getCurrentState() {
		return checkState;
	}
	
}
