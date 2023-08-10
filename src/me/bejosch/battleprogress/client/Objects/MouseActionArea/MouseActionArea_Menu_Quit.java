package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.MenuData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Enum.SpielStatus;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Handler.WindowHandler;

public class MouseActionArea_Menu_Quit extends MouseActionArea {

//==========================================================================================================
	public MouseActionArea_Menu_Quit() {
		super(WindowData.FrameWidth-MenuData.til_buttonWidth-MenuData.til_buttonBorderSides, MenuData.til_buttonBorder,
				WindowData.FrameWidth-MenuData.til_buttonBorderSides, MenuData.til_buttonBorder+MenuData.til_buttonHeight,
				"Menu_Quit", null, ShowBorderType.ShowAlways, Color.WHITE, Color.RED);
//		String[] hoverText_ = {"Quit the game"};
//		this.hoverText = hoverText_;
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		WindowHandler.closingAction();
		System.exit(0);
		
		super.performAction_LEFT_RELEASE();
	}
	
	@Override
	public boolean isActiv() {
		
		if(StandardData.spielStatus == SpielStatus.Menu) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {
			g.setColor(Color.DARK_GRAY.brighter());
			g.fillRect(xTL, yTL, xBR-xTL, yBR-yTL);
			
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				g.setColor(this.hoverColor);
			}else {
				g.setColor(this.standardColor);
			}
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString("Quit", this.xTL+30, this.yTL+(this.yBR-this.yTL)/2+8);
		}
		
		super.draw(g);
	}
	
}
