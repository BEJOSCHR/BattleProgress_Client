package me.bejosch.battleprogress.client.Objects.MouseActionArea.MultiSwitch;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class MouseActionArea_MultiSwitch extends MouseActionArea {

	public String connectedOTWname;
	
	public int currentPosition = 0;
	
	public int textYOffSet;
	public int textSize, centerXCoordinate, centerYCoordiante, arrowXOffSet;
	
	public MouseActionArea_MultiSwitch(int xTL_, int yTL_, int xBR_, int yBR_, String idName_, String[] hoverText_
			, Color standardColor_, Color hoverColor_, int defaultPosition,  String connectedOTWname_, int textSize_, int textYOffSet_, int arrowXOffSet_, boolean isOTWMAA) {
		super(xTL_, yTL_, xBR_, yBR_, "MS_"+idName_, hoverText_, ShowBorderType.ShowAlways, standardColor_, hoverColor_);
		
		this.connectedOTWname = connectedOTWname_;
		
		this.currentPosition = defaultPosition;
		
		this.textSize = textSize_;
		
		this.textYOffSet = textYOffSet_;
		
		this.centerXCoordinate = this.xTL + ((this.xBR-this.xTL)/2);
		this.centerYCoordiante = this.yTL + ((this.yBR-this.yTL)/2);
		
		this.arrowXOffSet = arrowXOffSet_;
		
		this.OTWMMA = isOTWMAA;
		
	}

	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow.name.equalsIgnoreCase(this.connectedOTWname)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		//DONE BY INT OR STR CHILD
		
		if(this.idName.contains("Settings")) {
			OnTopWindowData.settingsHasBeenModified = true;
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		super.draw(g);
		
		//TEXT DONE BY CHILD
		
		if(isActiv() == true) {
			if(this.checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				if(MouseHandler.mouseX > this.centerXCoordinate) {
					//RECHTS
					g.setColor(Color.GREEN);
					g.drawRect(this.centerXCoordinate, this.yTL, this.centerXCoordinate-this.xBR, this.yBR-this.yTL);
					g.setFont(new Font("Arial", Font.CENTER_BASELINE, textSize));
					g.drawString(">>", this.xBR-24-this.arrowXOffSet, this.centerYCoordiante+this.textYOffSet);
				}else {
					//LINKS
					g.setColor(Color.GREEN);
					g.drawRect(this.xTL, this.yTL, this.xTL-this.centerXCoordinate, this.yBR-this.yTL);
					g.setFont(new Font("Arial", Font.CENTER_BASELINE, textSize));
					g.drawString("<<", this.xTL+this.arrowXOffSet, this.centerYCoordiante+this.textYOffSet);
				}
			}
		}
		
	}
	
}
