package me.bejosch.battleprogress.client.Objects.OnTopWindow.PlayerChat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class MAA_OTW_PlayerChat_Close extends MouseActionArea {

	public MAA_OTW_PlayerChat_Close() {
		super(WindowData.FrameWidth/2-OnTopWindowData.chat_messageFieldWidth/2-OnTopWindowData.chat_messageFieldBorders+OnTopWindowData.chat_sideButtonsBorder-OnTopWindowData.chat_sideButtonsWidth, WindowData.FrameHeight/2+OnTopWindowData.chat_height/2-OnTopWindowData.chat_messageFieldDownBorder-OnTopWindowData.chat_messageFieldHeight 
				, WindowData.FrameWidth/2-OnTopWindowData.chat_messageFieldWidth/2-OnTopWindowData.chat_messageFieldBorders+OnTopWindowData.chat_sideButtonsBorder, WindowData.FrameHeight/2+OnTopWindowData.chat_height/2-OnTopWindowData.chat_messageFieldDownBorder 
				, "OTW_PlayerChat_Close", null, ShowBorderType.ShowAlways, Color.WHITE, Color.LIGHT_GRAY.darker());
		this.OTWMMA = true;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_PlayerChat) {
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
				String text = "Close";
				int width = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-width/2, this.yBR-8);
			}else {
				//NO HOVER
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				String text = "Close";
				int width = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-width/2, this.yBR-8);
			}
		}
		
		super.draw(g);
		
	}
	
}
