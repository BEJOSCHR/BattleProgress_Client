package me.bejosch.battleprogress.client.Objects.OnTopWindow.TabGameInfo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;

public class OnTopWindow_TabGameInfo extends OnTopWindow {

	public OnTopWindow_TabGameInfo() {
		super("OTW_TabGameInfo", OnTopWindowData.tabGameInfo_width, OnTopWindowData.tabGameInfo_height);
		
		this.darkBackground = false;
		
	}
	
	
	private int getX() {
		return WindowData.FrameWidth/2-this.width/2;
	}
	private int getY() {
		return WindowData.FrameHeight/2-this.height/2;
	}
	
	@Override
	public void initOnOpen() {
		
	}
	
	@Override
	public void performClose() {
			
	}
	
	@Override
	public void onKeyRelease(int keyCode) {
		
		if(keyCode == KeyEvent.VK_TAB) {
			
			OnTopWindowHandler.closeOTW(true);
			
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(this.getX(), this.getY(), this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(this.getX(), this.getY(), this.width, this.height);
		
		
		
	}
}
