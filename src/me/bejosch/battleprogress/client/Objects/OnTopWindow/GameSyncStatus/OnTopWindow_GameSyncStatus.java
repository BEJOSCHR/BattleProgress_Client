package me.bejosch.battleprogress.client.Objects.OnTopWindow.GameSyncStatus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;

public class OnTopWindow_GameSyncStatus extends OnTopWindow {
	
	public String info = "";
	public String status = "Initialising...";
	
	public OnTopWindow_GameSyncStatus(String info) {
		super("OTW_GameSyncStatus", OnTopWindowData.gameSyncStatus_width, OnTopWindowData.gameSyncStatus_height);
		
		this.info = info;
		
	}

	public void updateInfo(String newInfo) {
		this.info = newInfo;
	}
	public void updateStatus(String newStatus) {
		this.status = newStatus;
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
		
		int x = WindowData.FrameWidth/2-this.width/2;
		int y = WindowData.FrameHeight/2-this.height/2;
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(x, y, this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 35));
		String text = this.info+"";
		int textWidth = g.getFontMetrics().stringWidth(text);
		g.drawString(text, WindowData.FrameWidth/2-textWidth/2, y + 50);
		
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 40));
		String text_2 = this.status;
		int textWidth_2 = g.getFontMetrics().stringWidth(text_2);
		g.drawString(text_2, WindowData.FrameWidth/2-textWidth_2/2, y + 100);
		
	}
	
}
