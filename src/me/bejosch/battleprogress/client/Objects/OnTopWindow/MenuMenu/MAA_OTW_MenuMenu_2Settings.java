package me.bejosch.battleprogress.client.Objects.OnTopWindow.MenuMenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Settings.A_Gameplay.OnTopWindow_Settings_Gameplay;

public class MAA_OTW_MenuMenu_2Settings extends MouseActionArea {

	private static int menuPosition = 1;
	
	public MAA_OTW_MenuMenu_2Settings() {
		super(WindowData.FrameWidth/2 - OnTopWindowData.menu_buttonWidth/2, WindowData.FrameHeight/2 - OnTopWindowData.menu_height/2+OnTopWindowData.menu_distanceToTopBorder+(menuPosition*OnTopWindowData.menu_distanceBetweenButton)
				, WindowData.FrameWidth/2 + OnTopWindowData.menu_buttonWidth/2, WindowData.FrameHeight/2 - OnTopWindowData.menu_height/2+OnTopWindowData.menu_distanceToTopBorder+(menuPosition*OnTopWindowData.menu_distanceBetweenButton)+OnTopWindowData.menu_buttonHeight
				, "OTW_MenuMenu_Settings", null, ShowBorderType.ShowAlways, Color.WHITE, Color.ORANGE);
		this.OTWMMA = true;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_MenuMenu) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		OnTopWindowHandler.openOTW(new OnTopWindow_Settings_Gameplay());
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {  
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				//HOVER
				g.setColor(hoverColor);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				String text = "Settings";
				int width = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-width/2, this.yTL+31);
			}else {
				//NO HOVER
				g.setColor(standardColor);
				g.setFont(new Font("Arial", Font.PLAIN, 19));
				String text = "Settings";
				int width = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-width/2, this.yTL+30);
			}
		}
		
		super.draw(g);
		
	}
	
}
