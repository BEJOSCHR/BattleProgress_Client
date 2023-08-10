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

public class MAA_OTW_Settings_Save extends MouseActionArea {

	public MAA_OTW_Settings_Save() {
		super(WindowData.FrameWidth/2+OnTopWindowData.settings_width/8, WindowData.FrameHeight/2+OnTopWindowData.settings_height/2-OnTopWindowData.settings_buttonBorder-OnTopWindowData.settings_buttonHeight
				, WindowData.FrameWidth/2+OnTopWindowData.settings_width/8+OnTopWindowData.settings_buttonWidth, WindowData.FrameHeight/2+OnTopWindowData.settings_height/2-OnTopWindowData.settings_buttonBorder
				, "OTW_Settings_Save", null, ShowBorderType.ShowAlways, null, null);
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
		
		OnTopWindowHandler.performSettingsSave();
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				//HOVER
				if(OnTopWindowData.settingsHasBeenModified == true) {
					g.setColor(Color.GREEN);
				}else {
					g.setColor(Color.LIGHT_GRAY);
				}
				g.setFont(new Font("Arial", Font.PLAIN, 21));
				g.drawString("Save", this.xTL+42, this.yTL+27);
			}else {
				//NO HOVER
				if(OnTopWindowData.settingsHasBeenModified == true) {
					g.setColor(Color.GREEN);
				}else {
					g.setColor(Color.LIGHT_GRAY);
				}
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				g.drawString("Save", this.xTL+43, this.yTL+27);
			}
		
			//super.draw(g); \/
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				if(OnTopWindowData.settingsHasBeenModified == true) {
					g.setColor(Color.GREEN);
				}else {
					g.setColor(Color.LIGHT_GRAY);
				}
				g.drawRect(xTL, yTL, xBR-xTL, yBR-yTL);
			}else {
				if(OnTopWindowData.settingsHasBeenModified == true) {
					g.setColor(Color.WHITE);
				}else {
					g.setColor(Color.LIGHT_GRAY);
				}
				g.drawRect(xTL, yTL, xBR-xTL, yBR-yTL);
			}
			
		}
		
	}
	
}
