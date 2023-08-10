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
import me.bejosch.battleprogress.client.ServerConnection.ServerConnection;

public class MouseActionArea_Menu_GroupLeaveButton extends MouseActionArea {
	
//==========================================================================================================
	public MouseActionArea_Menu_GroupLeaveButton() {
		super(0, 0, 0, 0, "Menu_GroupLeaveButton", null, ShowBorderType.ShowAlways, Color.WHITE, Color.RED);
		
//		String[] hoverText = {"Leave the group"};
//		this.hoverText = hoverText;
		
		this.xTL = WindowData.FrameWidth/2-MenuData.lgb_buttonWidth/2;
		this.yTL = WindowData.FrameHeight-MenuData.lgb_buttonHeight-MenuData.lgb_border;
		this.xBR = this.xTL+MenuData.lgb_buttonWidth;
		this.yBR = this.yTL+MenuData.lgb_buttonHeight;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		ServerConnection.sendData(304, ServerConnection.getNewPacketId(), "");
		
		super.performAction_LEFT_RELEASE();
	}
	
	@Override
	public boolean isActiv() {
		
		if(StandardData.spielStatus == SpielStatus.Menu && ProfilData.otherGroupClient != null) {
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
			g.setFont(new Font("Arial", Font.BOLD, 16));
			String text = "Leave group";
			int textWidth = g.getFontMetrics().stringWidth(text);
			g.drawString(text, this.xTL+MenuData.lgb_buttonWidth/2-textWidth/2, this.yTL+(this.yBR-this.yTL)/2+5);
		}
		
		super.draw(g);
	}
	
}
