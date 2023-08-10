package me.bejosch.battleprogress.client.Objects.OnTopWindow.QueueWaiting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.ServerConnection.ServerConnection;

public class MAA_OTW_QueueWaiting_LeaveQueue extends MouseActionArea {

	public MAA_OTW_QueueWaiting_LeaveQueue() {
		super(getX(), getY(), getX()+OnTopWindowData.queueWaiting_buttonWidth, getY()+OnTopWindowData.queueWaiting_buttonHeight
				, "OTW_QueueWaiting_LeaveQueue", null, ShowBorderType.ShowAlways, Color.WHITE, Color.ORANGE);
		this.OTWMMA = true;
	}
	
	private static int getX() {
		return WindowData.FrameWidth/2-OnTopWindowData.queueWaiting_buttonWidth/2;
	}
	private static int getY() {
		return WindowData.FrameHeight/2+OnTopWindowData.queueWaiting_height/2-OnTopWindowData.queueWaiting_buttonHeight-OnTopWindowData.queueWaiting_buttonDownBorder;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_QueueWaiting) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		ServerConnection.sendData(401, ServerConnection.getNewPacketId(), "Leave Queue");
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {  
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				//HOVER
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				String text = "Leave Queue";
				int width = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-width/2, this.yBR-12);
			}else {
				//NO HOVER
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.PLAIN, 19));
				String text = "Leave Queue";
				int width = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-width/2, this.yBR-12);
			}
		}
		
		super.draw(g);
		
	}
	
}
