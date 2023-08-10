package me.bejosch.battleprogress.client.Objects.OnTopWindow.Settings;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class MAA_OTW_Settings_Cancel extends MouseActionArea {

	public MAA_OTW_Settings_Cancel() {
		super(WindowData.FrameWidth/2+OnTopWindowData.settings_buttonBorder-OnTopWindowData.settings_buttonWidth, WindowData.FrameHeight/2+OnTopWindowData.settings_height/2-OnTopWindowData.settings_buttonBorder-OnTopWindowData.settings_buttonHeight
				, WindowData.FrameWidth/2+OnTopWindowData.settings_buttonBorder, WindowData.FrameHeight/2+OnTopWindowData.settings_height/2-OnTopWindowData.settings_buttonBorder
				, "OTW_Settings_Cancel", null, ShowBorderType.ShowAlways, null, null);
		this.OTWMMA = true;
		
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow.name.contains("Settings")) {
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
		
		if(OnTopWindowData.settingsHasBeenModified == true) {
			String[] hoverText = {"You have to save if you want to apply your changes!"};
			this.hoverText = hoverText;
		}else {
			this.hoverText = null;
		}
		
		if(this.isActiv() == true) {  
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				//HOVER
				if(OnTopWindowData.settingsHasBeenModified == true) {
					g.setColor(Color.RED);
					g.setFont(new Font("Arial", Font.PLAIN, 21));
					g.drawString("Cancel", this.xTL+33, this.yTL+27);
				}else {
					g.setColor(Color.ORANGE);
					g.setFont(new Font("Arial", Font.PLAIN, 21));
					g.drawString("Close", this.xTL+35, this.yTL+27);
				}
			}else {
				//NO HOVER
				if(OnTopWindowData.settingsHasBeenModified == true) {
					g.setColor(Color.RED);
					g.setFont(new Font("Arial", Font.PLAIN, 20));
					g.drawString("Cancel", this.xTL+34, this.yTL+27);
				}else {
					g.setColor(Color.WHITE);
					g.setFont(new Font("Arial", Font.PLAIN, 20));
					g.drawString("Close", this.xTL+36, this.yTL+27);
				}
			}
			
			//super.draw(g); \/
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				if(OnTopWindowData.settingsHasBeenModified == true) {
					g.setColor(Color.RED);
				}else {
					g.setColor(Color.ORANGE);
				}
				g.drawRect(xTL, yTL, xBR-xTL, yBR-yTL);
			}else {
				g.setColor(Color.WHITE);
				g.drawRect(xTL, yTL, xBR-xTL, yBR-yTL);
			}
			
		}
		
	}
	
}
