package me.bejosch.battleprogress.client.Objects.OnTopWindow.GameAccept;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class MAA_OTW_GameAccept_Decline extends MouseActionArea {
	
	public MAA_OTW_GameAccept_Decline() {
		super(getX(), getY(), getX()+OnTopWindowData.gameAccept_buttonWidth, getY()+OnTopWindowData.gameAccept_buttonHeight, 
				"OTW_gameAccept_Decline", null, ShowBorderType.ShowAlways, Color.BLACK, Color.RED);
		this.OTWMMA = true;
	}
	
	private static int getX() {
		return WindowData.FrameWidth/2+OnTopWindowData.gameAccept_buttonBorderBetween/2;
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
		
		//DECLINE DONT NEED TO BE SEND
		OnTopWindowHandler.closeOTW();
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {  
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				//HOVER
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				String text = "Decline";
				int width = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-width/2, this.yBR-12);
			}else {
				//NO HOVER
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.PLAIN, 19));
				String text = "Decline";
				int width = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-width/2, this.yBR-12);
			}
		}
		
		super.draw(g);
		
	}
	
}
