package me.bejosch.battleprogress.client.Objects.OnTopWindow.InfoMessage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class MAA_OTW_InfoMessage_Discord extends MouseActionArea {

	public MAA_OTW_InfoMessage_Discord() {
		super(WindowData.FrameWidth/2+OnTopWindowData.infoMessage_width/2-110-20, WindowData.FrameHeight/2-OnTopWindowData.infoMessage_height/2+10
				, WindowData.FrameWidth/2+OnTopWindowData.infoMessage_width/2-20, WindowData.FrameHeight/2-OnTopWindowData.infoMessage_height/2+40+10
				, "OTW_InfoMessage_Discord", null, ShowBorderType.ShowAlways, Color.WHITE, Color.ORANGE);
		this.OTWMMA = true;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_InfoMessage) {
				OnTopWindow_InfoMessage infoOTW = (OnTopWindow_InfoMessage) OnTopWindowData.onTopWindow;
				if(infoOTW.criticalErrorDisplay == true) {
					//IS CRITICAL
					return true;
				}
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		OnTopWindowHandler.openBrowserLink("https://discord.gg/nBxwBnNGVz");
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {  
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				//HOVER
				g.setColor(hoverColor);
				g.setFont(new Font("Arial", Font.PLAIN, 21));
				g.drawString("Discord", this.xTL+20, this.yBR-13);
			}else {
				//NO HOVER
				g.setColor(standardColor);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				g.drawString("Discord", this.xTL+21, this.yBR-12);
			}
		}
		
		super.draw(g);
		
	}
	
}
