package me.bejosch.battleprogress.client.Objects.OnTopWindow.MenuMenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;

public class OnTopWindow_MenuMenu extends OnTopWindow {

	public OnTopWindow_MenuMenu() {
		super("OTW_MenuMenu", OnTopWindowData.menu_width, OnTopWindowData.menu_height);
	}

	@Override
	public void initOnOpen() {
		
		MovementHandler.cancleMovement();
		
	}
	
	@Override
	public void performClose() {
		
		MovementHandler.allowMovement();
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(WindowData.FrameWidth/2 - this.width/2, WindowData.FrameHeight/2 - this.height/2, width, height);
		
		g.setColor(Color.WHITE);
		g.drawRect(WindowData.FrameWidth/2 - this.width/2, WindowData.FrameHeight/2 - this.height/2, width, height);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 37));
		g.drawString("BattleProgress", WindowData.FrameWidth/2 - this.width/2 + 19, WindowData.FrameHeight/2 - this.height/2 + 48);
		
	}
	
}
