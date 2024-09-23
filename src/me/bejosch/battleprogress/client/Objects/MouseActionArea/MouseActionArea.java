package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Color;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.HoverHandler;
import me.bejosch.battleprogress.client.Handler.MouseHandler;

public class MouseActionArea {

	public int xTL = 0, yTL = 0, xBR = 0, yBR = 0;
	public String idName = "missingName";
	public ShowBorderType showBorderType = ShowBorderType.ShowOnHover;
	public Color standardColor = Color.WHITE;
	public Color hoverColor = Color.WHITE;
	public String[] hoverText = null;
	
	public boolean OTWMMA = false;
	
//==========================================================================================================
	/**
	 * An area which represents an new mouse action area (usw: layout buttons/clicks)
	 * @param xTL - int - The x-coordinate of the left top corner pixle
	 * @param yTL - int - The y-coordinate of the left top corner pixle
	 * @param xBR - int - The x-coordinate of the right bottom corner pixle
	 * @param yBR - int - The y-coordinate of the right bottom corner pixle
	 * @param idName - String - The name of this actionArea, by this it is identified
	 * @param hoverText_ - String[] - The text that will be displayed on hover
	 * @param showBorderType_ - {@link ShowBorderType} - Tells, how and when the borders should be shown
	 * @param standardColor_ - {@link Color} - The color of the border if the mouse is NOT in the area
	 * @param hoverColor_ - {@link Color} - The color of the border if the mouse is in the area
	 **/
	public MouseActionArea(int xTL_, int yTL_, int xBR_, int yBR_, String idName_, String[] hoverText_, ShowBorderType showBorderType_, Color standardColor_, Color hoverColor_) {
		
		this.xTL = xTL_;
		this.yTL = yTL_;
		this.xBR = xBR_;
		this.yBR = yBR_;
		this.idName = idName_;
		this.hoverText = hoverText_;
		this.showBorderType = showBorderType_;
		this.standardColor = standardColor_;
		this.hoverColor = hoverColor_;
		
//		while(!GameData.mouseActionAreas.contains(this)) {
//			try{
				GameData.mouseActionAreas.add(this);
//			}catch(ConcurrentModificationException error) {}
//		}
		
	}
	
//==========================================================================================================
	/**
	 * Checks if the mouse is in the area (Only if the area is active)
	 * @param mouseX - int - The X-Coordinate of the mouse
	 * @param mouseY - int - The Y-Coordinate of the mouse
	 * @return boolean - if true the mouse is in the area, false if not
	 **/
	public boolean checkArea(int mouseX, int mouseY) {
		
		if(isActiv() == true) {
			if(mouseX >= xTL && mouseX <= xBR  &&  mouseY >= yTL && mouseY <= yBR) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Checks if the mouse is in the area, with default mouse cords (Only if the area is active)
	 * @return boolean - if true the mouse is in the area, false if not
	 **/
	public boolean isHovered() {
		
		if(isActiv() == true) {
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Checks if this action area is activ (Possible it's only active in a special case)
	 * @return boolean - If true the area is active, false if not
	 **/
	public boolean isActiv() {
		return true;
	}
	
//==========================================================================================================
	/**
	 * The action which is performed if the mouse is in the area - LEFT PRESS
	 **/
	public void performAction_LEFT_PRESS() { }
//==========================================================================================================
	/**
	 * The action which is performed if the mouse is in the area - RIGHT PRESS
	 **/
	public void performAction_RIGHT_PRESS() { }
//==========================================================================================================
	/**
	 * The action which is performed if the mouse is in the area - LEFT RELEASE
	 **/
	public void performAction_LEFT_RELEASE() { }
//==========================================================================================================
	/**
	 * The action which is performed if the mouse is in the area - RIGHT RELEASE
	 **/
	public void performAction_RIGHT_RELEASE() { }
//==========================================================================================================
	/**
	 * The action which is performed if the mouse is in the area - HOVER
	 **/
	public void performAction_HOVER() {
		
		if(hoverText != null) {
			HoverHandler.updateHoverMessage(this.idName, this.hoverText);
		}
		
	}
	
//==========================================================================================================
	/**
	 * Draws a squar if area is active and the mouse is in the area and if it's enabled
	 * @param g - {@link Graphics} - The graphics everything is drawn on
	 **/
	public void draw(Graphics g) { 
		
		if(this.isActiv() == true) {
			switch(showBorderType) {
			case ShowAlways:
				if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true && (OnTopWindowData.onTopWindow == null || this.OTWMMA == true) ) { //WENN OTW DA IST UND KEIN OTW MAA DANN KEIN HOVER M�GLICH
					g.setColor(hoverColor);
				}else {
					g.setColor(standardColor);
				}
				g.drawRect(xTL, yTL, xBR-xTL, yBR-yTL);
				break;
			case ShowOnHover:
				if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true && (OnTopWindowData.onTopWindow == null || this.OTWMMA == true) ) { //WENN OTW DA IST UND KEIN OTW MAA DANN KEIN HOVER M�GLICH
					g.setColor(hoverColor);
					g.drawRect(xTL, yTL, xBR-xTL, yBR-yTL);
				}
				break;
			case ShowNever:
				//NEVER EVER
				break;
			default:
				break;
			
			}
		}
		
	}
	
//==========================================================================================================
	/**
	 * Removes/Delete this actionArea
	 **/
	public void remove() {
		
//		while(GameData.mouseActionAreas.contains(this)) {
//			try{
				GameData.mouseActionAreas.remove(this);
//			}catch(ConcurrentModificationException error) {}
//		}
		
	}
	
}
