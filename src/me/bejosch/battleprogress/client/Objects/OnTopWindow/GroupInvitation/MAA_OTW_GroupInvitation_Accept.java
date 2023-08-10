package me.bejosch.battleprogress.client.Objects.OnTopWindow.GroupInvitation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.ServerConnection.ServerConnection;

public class MAA_OTW_GroupInvitation_Accept extends MouseActionArea {
	
	public MAA_OTW_GroupInvitation_Accept() {
		super(getX(), getY(), getX()+OnTopWindowData.groupInvitation_buttonWidth, getY()+OnTopWindowData.groupInvitation_buttonHeight, 
				"OTW_GroupInvitation_Accept", null, ShowBorderType.ShowAlways, Color.BLACK, Color.GREEN);
		this.OTWMMA = true;
	}
	
	private static int getX() {
		return WindowData.FrameWidth/2-OnTopWindowData.groupInvitation_buttonWidth-OnTopWindowData.groupInvitation_buttonBorderBetween/2;
	}
	private static int getY() {
		return WindowData.FrameHeight/2+OnTopWindowData.groupInvitation_height/2-OnTopWindowData.groupInvitation_buttonHeight-OnTopWindowData.groupInvitation_buttonBottomBorder;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_GroupInvitation) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		//SEND ACCEPT
		OnTopWindow_GroupInvitation otw = (OnTopWindow_GroupInvitation) OnTopWindowData.onTopWindow;
		ServerConnection.sendData(301, ServerConnection.getNewPacketId(), ""+otw.inviter.getID());
		OnTopWindowHandler.closeOTW();
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {  
			//TEXT
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 26));
			String text = "Accept";
			int textWidth = g.getFontMetrics().stringWidth(text);
			g.drawString(text, this.xTL+OnTopWindowData.groupInvitation_buttonWidth/2-textWidth/2, this.yTL+OnTopWindowData.groupInvitation_buttonHeight-15);
		}
		
		super.draw(g);
		
	}
	
}
