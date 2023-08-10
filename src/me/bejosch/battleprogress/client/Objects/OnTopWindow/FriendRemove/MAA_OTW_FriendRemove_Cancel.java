package me.bejosch.battleprogress.client.Objects.OnTopWindow.FriendRemove;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class MAA_OTW_FriendRemove_Cancel extends MouseActionArea {
	
	public MAA_OTW_FriendRemove_Cancel() {
		super(getX(), getY(), getX()+OnTopWindowData.groupInvitation_buttonWidth, getY()+OnTopWindowData.groupInvitation_buttonHeight, 
				"OTW_FriendRemove_Cancel", null, ShowBorderType.ShowAlways, Color.BLACK, Color.LIGHT_GRAY.darker());
		this.OTWMMA = true;
	}
	
	private static int getX() {
		return WindowData.FrameWidth/2+OnTopWindowData.groupInvitation_buttonBorderBetween/2;
	}
	private static int getY() {
		return WindowData.FrameHeight/2+OnTopWindowData.groupInvitation_height/2-OnTopWindowData.groupInvitation_buttonHeight-OnTopWindowData.groupInvitation_buttonBottomBorder;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_FriendRemove) {
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
			//TEXT
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 26));
			String text = "Cancel";
			int textWidth = g.getFontMetrics().stringWidth(text);
			g.drawString(text, this.xTL+OnTopWindowData.groupInvitation_buttonWidth/2-textWidth/2, this.yTL+OnTopWindowData.groupInvitation_buttonHeight-15);
		}
		
		super.draw(g);
		
	}
	
}
