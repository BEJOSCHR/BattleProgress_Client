package me.bejosch.battleprogress.client.Objects.MouseActionArea.MultiSwitch;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Handler.MouseHandler;

public class MouseActionArea_MultiSwitch_Int extends MouseActionArea_MultiSwitch {
	
	public List<Integer> possibleStates = new ArrayList<Integer>();
	
	
	public MouseActionArea_MultiSwitch_Int(int xTL_, int yTL_, int xBR_, int yBR_, String idName_, String[] hoverText_
			, Color standardColor_, Color hoverColor_, String connectedOTWname_, List<Integer> possibleStates_, int defaultPosition, int textSize_, int textYOffSet_, int arrowXOffSet_, boolean isOTWMAA) {
		super(xTL_, yTL_, xBR_, yBR_, idName_, hoverText_, standardColor_, hoverColor_, defaultPosition, connectedOTWname_, textSize_, textYOffSet_, arrowXOffSet_, isOTWMAA);
		
		this.possibleStates = possibleStates_;
		
	}

	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		if(MouseHandler.mouseX > this.centerXCoordinate) {
			//RECHTS
			if(this.currentPosition == this.possibleStates.size()-1) {
				this.currentPosition = 0;
			}else {
				this.currentPosition += 1;
			}
		}else {
			//LINKS
			if(this.currentPosition == 0) {
				this.currentPosition = this.possibleStates.size()-1;
			}else {
				this.currentPosition -= 1;
			}
		}
		
		super.performAction_LEFT_RELEASE();
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(isActiv() == true) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.CENTER_BASELINE, textSize));
			String text = ""+this.getCurrentState();
			int textWidth = g.getFontMetrics().stringWidth(text);
			g.drawString(text, this.centerXCoordinate-textWidth/2, this.centerYCoordiante+this.textYOffSet);
			
			super.draw(g);
		}
		
	}
	
	public void setCurrentState(Integer state) {
		if(this.possibleStates.contains(state)) {
			this.currentPosition = this.possibleStates.indexOf(state);
		}
	}
	
	public Integer getCurrentState() {
		return this.possibleStates.get(this.currentPosition);
	}
	
}
