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

public class MAA_OTW_InfoMessage_Confirm extends MouseActionArea {

	public MAA_OTW_InfoMessage_Confirm() {
		super(WindowData.FrameWidth/2-OnTopWindowData.generalConfirm_MAA_width/2, WindowData.FrameHeight/2+OnTopWindowData.infoMessage_height/2-OnTopWindowData.generallConfirm_MAA_height-20
				, WindowData.FrameWidth/2+OnTopWindowData.generalConfirm_MAA_width/2, WindowData.FrameHeight/2+OnTopWindowData.infoMessage_height/2-20
				, "OTW_InfoMessage_Confirm", null, ShowBorderType.ShowAlways, Color.WHITE, Color.ORANGE);
		this.OTWMMA = true;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_InfoMessage) {
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
				g.setColor(hoverColor);
				g.setFont(new Font("Arial", Font.PLAIN, 21));
				g.drawString("Confirm", this.xTL+44, this.yBR-16);
			}else {
				//NO HOVER
				g.setColor(standardColor);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				g.drawString("Confirm", this.xTL+45, this.yBR-15);
			}
		}
		
		super.draw(g);
		
	}
	
}
