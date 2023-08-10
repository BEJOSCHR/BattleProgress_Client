package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.MenuData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Enum.SpielStatus;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_GameHistoryOpenClose;

public class MouseActionArea_Menu_ShowHideMatchHistory extends MouseActionArea {

	public boolean positionAsShowButton = true;
	
//==========================================================================================================
	/**
	 * A spacial ActionArea for the Chat handeling (hide/show)
	 * @param positionAsShowButton - boolean - if this is true it works as show button, if false it is the hide button
	 **/
	public MouseActionArea_Menu_ShowHideMatchHistory(boolean positionAsShowButton_) {
		super(0, 0, 0, 0, null, null, ShowBorderType.ShowOnHover, null, Color.WHITE);
		
		this.positionAsShowButton = positionAsShowButton_;
		
		if(positionAsShowButton == true) {
			
			//CURRENT HIDDEN - SO SHOW BUTTON POSITION
			int buttonX = 0, buttonY = MenuData.til_height+1;
			
			this.xTL = buttonX; this.yTL = buttonY;
			this.xBR = buttonX+MenuData.sideSectionsMAAwidth; this.yBR = WindowData.FrameHeight-MenuData.bbs_height;
			this.idName = "Menu_ShowMatchHistory";
//			String[] hoverText_ = {"Show MatchHistory"};
//			this.hoverText = hoverText_;
			
		}else {
			
			//CURRENT SHOWN - SO HIDE BUTTON POSITION
			int buttonX = MenuData.lgh_width+1, buttonY = MenuData.til_height+1;
			
			this.xTL = buttonX; this.yTL = buttonY;
			this.xBR = buttonX+MenuData.sideSectionsMAAwidth; this.yBR = WindowData.FrameHeight-MenuData.bbs_height;
			this.idName = "Menu_HideMatchHistory";
//			String[] hoverText_ = {"Hide MatchHistory"};
//			this.hoverText = hoverText_;
			
		}
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		if(positionAsShowButton == true) {
			//CURRENT HIDE - NOW SHOW
			new Animation_GameHistoryOpenClose(true);
			MenuData.gameHistoryOpened = true;
		}else {
			//CURRENT SHOW - NOW HIDE
			new Animation_GameHistoryOpenClose(false);
			MenuData.gameHistoryOpened = false;
		}
		
		super.performAction_LEFT_RELEASE();
	}
	
	@Override
	public boolean isActiv() {
		
		if(StandardData.spielStatus == SpielStatus.Menu) {
			if(positionAsShowButton == true) {
				//ONLY WORK IF CHAT IS HIDDEN
				if(MenuData.gameHistoryOpened == false && MenuData.lgh_OpenCloseFactor == 0.0) {
					return true;
				}
			}else {
				//ONLY WORK IF CHAT IS SHOWN
				if(MenuData.gameHistoryOpened == true && MenuData.lgh_OpenCloseFactor == 1.0) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(xTL, yTL, xBR-xTL, yBR-yTL);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			String text = "";
			if(this.positionAsShowButton) { text = ">"; }else { text = "<"; }
			g.drawString(text, this.xTL+18, this.yTL+(this.yBR-this.yTL)/2+5);
		}
		
		super.draw(g);
	}
	
}
