package me.bejosch.battleprogress.client.Objects.OnTopWindow.FriendAdd;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class MAA_OTW_FriendAdd_Cancel extends MouseActionArea {

	public MAA_OTW_FriendAdd_Cancel() {
		super(getX(), getY(), getX()+OnTopWindowData.friendAdd_buttonWidth, getY()+OnTopWindowData.friendAdd_buttonHeight
				, "OTW_FriendAdd_Cancel", null, ShowBorderType.ShowAlways, Color.WHITE, Color.LIGHT_GRAY.darker());
		this.OTWMMA = true;
	}
	
	private static int getX() {
		return WindowData.FrameWidth/2+OnTopWindowData.friendAdd_buttonBorderBetween/2;
	}
	private static int getY() {
		return WindowData.FrameHeight/2+OnTopWindowData.friendAdd_height/2-OnTopWindowData.friendAdd_buttonHeight-OnTopWindowData.friendAdd_buttonBottomBorder;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_FriendAdd) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		OnTopWindowHandler.closeOTW();
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {  
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				//HOVER
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.PLAIN, 21));
				String text = "Cancel";
				int width = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-width/2, this.yBR-12);
			}else {
				//NO HOVER
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				String text = "Cancel";
				int width = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-width/2, this.yBR-12);
			}
		}
		
		super.draw(g);
		
	}
	
}
