package me.bejosch.battleprogress.client.Objects.OnTopWindow.GameMenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.ConfirmSurrender.OnTopWindow_ConfirmSurrender;

public class MAA_OTW_GameMenu_4Surrender extends MouseActionArea {

private static int menuPosition = 3;
	
	public MAA_OTW_GameMenu_4Surrender() {
		super(WindowData.FrameWidth/2 - OnTopWindowData.menu_buttonWidth/2, WindowData.FrameHeight/2 - OnTopWindowData.menu_height/2+OnTopWindowData.menu_distanceToTopBorder+(menuPosition*OnTopWindowData.menu_distanceBetweenButton)
				, WindowData.FrameWidth/2 + OnTopWindowData.menu_buttonWidth/2, WindowData.FrameHeight/2 - OnTopWindowData.menu_height/2+OnTopWindowData.menu_distanceToTopBorder+(menuPosition*OnTopWindowData.menu_distanceBetweenButton)+OnTopWindowData.menu_buttonHeight
				, "OTW_GameMenu_Surrender", null, ShowBorderType.ShowAlways, Color.WHITE, Color.ORANGE);
		this.OTWMMA = true;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_GameMenu) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		OnTopWindowHandler.openOTW(new OnTopWindow_ConfirmSurrender(false));
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {  
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				//HOVER
				g.setColor(hoverColor);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				g.drawString("Surrender", this.xTL+71, this.yTL+31);
			}else {
				//NO HOVER
				g.setColor(standardColor);
				g.setFont(new Font("Arial", Font.PLAIN, 19));
				g.drawString("Surrender", this.xTL+70, this.yTL+30);
			}
		}
		
		super.draw(g);
		
	}
	
}
