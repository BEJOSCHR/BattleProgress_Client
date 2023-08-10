package me.bejosch.battleprogress.client.Objects.OnTopWindow.GameAccept;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.ServerConnection.ServerConnection;

public class MAA_OTW_GameAccept_Accept extends MouseActionArea {
	
	public MAA_OTW_GameAccept_Accept() {
		super(getX(), getY(), getX()+OnTopWindowData.gameAccept_buttonWidth, getY()+OnTopWindowData.gameAccept_buttonHeight, 
				"OTW_GameAccept_Accept", null, ShowBorderType.ShowAlways, Color.BLACK, Color.GREEN);
		this.OTWMMA = true;
	}
	
	private static int getX() {
		return WindowData.FrameWidth/2-OnTopWindowData.gameAccept_buttonWidth-OnTopWindowData.gameAccept_buttonBorderBetween/2;
	}
	private static int getY() {
		return WindowData.FrameHeight/2+OnTopWindowData.gameAccept_height/2-OnTopWindowData.gameAccept_buttonHeight-OnTopWindowData.gameAccept_buttonBottomBorder;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_GameAccept) {
				if( ((OnTopWindow_GameAccept) OnTopWindowData.onTopWindow).accepted == false ) {
					return true;
				}
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		//SEND ACCEPT
		OnTopWindow_GameAccept otw = ((OnTopWindow_GameAccept) OnTopWindowData.onTopWindow);
		ServerConnection.sendData(405, ServerConnection.getNewPacketId(), ""+otw.gameID);
		otw.accepted = true;
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) { 
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				//HOVER
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				String text = "Accept";
				int width = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-width/2, this.yBR-12);
			}else {
				//NO HOVER
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.PLAIN, 19));
				String text = "Accept";
				int width = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-width/2, this.yBR-12);
			}
		}
		
		super.draw(g);
		
	}
	
}
