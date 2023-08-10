package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.MenuData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Enum.SpielStatus;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_FriendListOpenClose;

public class MouseActionArea_Menu_ShowHideFriendList extends MouseActionArea {

	public boolean positionAsShowButton = true;
	
//==========================================================================================================
	/**
	 * A spacial ActionArea for the Chat handeling (hide/show)
	 * @param positionAsShowButton - boolean - if this is true it works as show button, if false it is the hide button
	 **/
	public MouseActionArea_Menu_ShowHideFriendList(boolean positionAsShowButton_) {
		super(0, 0, 0, 0, null, null, ShowBorderType.ShowOnHover, null, Color.WHITE);
		
		this.positionAsShowButton = positionAsShowButton_;
		
		if(positionAsShowButton == true) {
			
			//CURRENT HIDDEN - SO SHOW BUTTON POSITION
			int buttonX = WindowData.FrameWidth-MenuData.sideSectionsMAAwidth, buttonY = MenuData.til_height+1;
			
			this.xTL = buttonX; this.yTL = buttonY;
			this.xBR = buttonX+MenuData.sideSectionsMAAwidth; this.yBR = WindowData.FrameHeight-MenuData.bbs_height;
			this.idName = "Menu_ShowFriendList";
//			String[] hoverText_ = {"Show FriendList"};
//			this.hoverText = hoverText_;
			
		}else {
			
			//CURRENT SHOWN - SO HIDE BUTTON POSITION
			int buttonX = WindowData.FrameWidth-MenuData.sideSectionsMAAwidth-MenuData.rfl_width-1, buttonY = MenuData.til_height+1;
			
			this.xTL = buttonX; this.yTL = buttonY;
			this.xBR = buttonX+50; this.yBR = WindowData.FrameHeight-MenuData.bbs_height;
			this.idName = "Menu_HideFriendList";
//			String[] hoverText_ = {"Hide FriendList"};
//			this.hoverText = hoverText_;
			
		}
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		if(positionAsShowButton == true) {
			//CURRENT HIDE - NOW SHOW
			new Animation_FriendListOpenClose(true);
			MenuData.friendListOpened = true;
		}else {
			//CURRENT SHOW - NOW HIDE
			new Animation_FriendListOpenClose(false);
			MenuData.friendListOpened = false;
		}
		
		super.performAction_LEFT_RELEASE();
	}
	
	@Override
	public boolean isActiv() {
		
		if(StandardData.spielStatus == SpielStatus.Menu) {
			if(positionAsShowButton == true) {
				//ONLY WORK IF HIDDEN
				if(MenuData.friendListOpened == false && MenuData.rfl_OpenCloseFactor == 0.0) {
					return true;
				}
			}else {
				//ONLY WORK IF SHOWN
				if(MenuData.friendListOpened == true && MenuData.rfl_OpenCloseFactor == 1.0) {
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
			
			//ARROW
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			String text = "";
			if(this.positionAsShowButton) { text = "<"; }else { text = ">"; }
			g.drawString(text, this.xTL+18, this.yTL+(this.yBR-this.yTL)/2+5);
			
			//ONLINE PLAYER INFO
			if(this.positionAsShowButton) {
				if(ProfilData.friendList_online.isEmpty() == false) {
					g.setFont(new Font("Arial", Font.BOLD, 17));
					int friendCount = (ProfilData.friendList_online.size() > 99 ? 99 : ProfilData.friendList_online.size());
					String onlineText = ""+friendCount;
					int onlineTextWidth = g.getFontMetrics().stringWidth(onlineText);
					int durchmesser = MenuData.sideSectionsMAAwidth-(8*2);
//					g.setColor(Color.LIGHT_GRAY.darker());
//					g.fillOval(this.xTL+(this.xBR-this.xTL)/2-radius, this.yTL+(this.yBR-this.yTL)/2-radius-45, radius*2, radius*2);
					g.setColor(Color.WHITE);
					g.drawOval(this.xTL+(this.xBR-this.xTL)/2-durchmesser/2, this.yTL+30-durchmesser/2, durchmesser, durchmesser);
					g.setColor(Color.GREEN.darker());
					g.drawString(onlineText, this.xTL+(this.xBR-this.xTL)/2-onlineTextWidth/2, this.yTL+30+6);
				}
			}
			
		}
		
		super.draw(g);
	}
	
}
