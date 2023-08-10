package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.MenuData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Enum.SpielStatus;
import me.bejosch.battleprogress.client.Game.OverAllManager;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Settings.A_Gameplay.OnTopWindow_Settings_Gameplay;

public class MouseActionArea_Menu_BottomButton extends MouseActionArea {

	public boolean left;
	public int pos = -1;
	
//==========================================================================================================
	public MouseActionArea_Menu_BottomButton(int pos, boolean left) {
		super(0, 0, 0, 0, "Menu_BottomButton_"+pos+"_"+left, null, ShowBorderType.ShowAlways, Color.WHITE, Color.ORANGE);
		
		this.left = left;
		this.pos = pos;
		
		this.hoverText = getHoverTextByPosAndSide(pos, left);
		
		if(this.left == true) {
			//LEFT
			switch(this.pos) {
			case 0:
				this.xTL = 0+MenuData.bbs_buttonBorderSide+(0*(MenuData.bbs_buttonWidth+MenuData.bbs_buttonBorderBetween));
				this.xBR = this.xTL+MenuData.bbs_buttonWidth;
				break;
			case 1:
				this.xTL = 0+MenuData.bbs_buttonBorderSide+(1*(MenuData.bbs_buttonWidth+MenuData.bbs_buttonBorderBetween));
				this.xBR = this.xTL+MenuData.bbs_buttonWidth;
				break;
			}
		}else {
			//RIGHT
			switch(this.pos) {
			case 0:
				this.xTL = WindowData.FrameWidth-MenuData.bbs_buttonBorderSide-(1*(MenuData.bbs_buttonWidth+MenuData.bbs_buttonBorderBetween))-MenuData.bbs_buttonWidth;
				this.xBR = this.xTL+MenuData.bbs_buttonWidth;
				break;
			case 1:
				this.xTL = WindowData.FrameWidth-MenuData.bbs_buttonBorderSide-(0*(MenuData.bbs_buttonWidth+MenuData.bbs_buttonBorderBetween))-MenuData.bbs_buttonWidth;
				this.xBR = this.xTL+MenuData.bbs_buttonWidth;
				break;
			}
		}
		
		this.yTL = WindowData.FrameHeight-MenuData.bbs_height+MenuData.bbs_buttonBorderTopDown;
		this.yBR = this.yTL+MenuData.bbs_buttonHeight;
		
	}
	
	private String getNameByPosAndSide(int pos, boolean left) {
		
		if(this.left == true) {
			//LEFT
			switch(this.pos) {
			case 0:
				return "Mapeditor";
			case 1:
				return " Settings";
			}
		}else {
			//RIGHT
			switch(this.pos) {
			case 0:
				return "  Profile";
			case 1:
				return " Discord ";
			}
		}
		return "";
		
	}
	
	private String[] getHoverTextByPosAndSide(int pos, boolean left) {
		
//		if(this.left == true) {
//			//LEFT
//			switch(this.pos) {
//			case 0:
//				String[] hoverText1 = {"Enter the map editor"};
//				return hoverText1;
//			case 1:
//				String[] hoverText2 = {"Configure your settings"};
//				return hoverText2;
//			}
//		}else {
//			//RIGHT
//			switch(this.pos) {
//			case 0:
//				String[] hoverText1 = {"Configure your profile"};
//				return hoverText1;
//			case 1:
//				String[] hoverText2 = {"Open a link to our discord"};
//				return hoverText2;
//			}
//		}
		
		return null;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		if(this.left == true) {
			//LEFT
			switch(this.pos) {
			case 0: //MAPEDITOR
				OverAllManager.switchTo_Menu_CreateMap();
				break;
			case 1: //SETTINGS
				OnTopWindowHandler.openOTW(new OnTopWindow_Settings_Gameplay());
				break;
			}
		}else {
			//RIGHT
			switch(this.pos) {
			case 0: //PROFILE
				
				break;
			case 1: //DISCORD
				OnTopWindowHandler.openBrowserLink("https://discord.gg/nBxwBnNGVz");
				break;
			}
		}
		
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
			
			g.setColor(this.standardColor);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString(getNameByPosAndSide(pos, left), this.xTL+15, this.yTL+(this.yBR-this.yTL)/2+8);
		}
		
		super.draw(g);
	}
	
}
