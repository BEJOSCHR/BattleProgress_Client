package me.bejosch.battleprogress.client.Objects.OnTopWindow.ConfirmSurrender;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;

public class OnTopWindow_ConfirmSurrender extends OnTopWindow {

	public boolean surrenderRequest = false;
	
	public OnTopWindow_ConfirmSurrender(boolean surrenderRequest) {
		super("OTW_ConfirmSurrender", OnTopWindowData.confSur_width, OnTopWindowData.confSur_height);
		
		this.surrenderRequest = surrenderRequest;
		
	}

	@Override
	public void initOnOpen() {
		
		//EPIC WARN SOUND RIGHT HERE ^^
		MovementHandler.cancleMovement();
		
	}
	
	@Override
	public void performClose() {
		
		MovementHandler.allowMovement();
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(WindowData.FrameWidth/2-this.width/2, WindowData.FrameHeight/2-this.height/2, this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(WindowData.FrameWidth/2-this.width/2, WindowData.FrameHeight/2-this.height/2, this.width, this.height);
		
		if(SpielModus.isGameModus1v1()) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.CENTER_BASELINE, 23));
			g.drawString("Do you really want to surrender and lose the game?", WindowData.FrameWidth/2 - this.width/2 + 30, WindowData.FrameHeight/2 - this.height/2 + 40);
		}else {
			if(this.surrenderRequest == true) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 23));
				g.drawString("Your teammate wants to surrender, do you agree?", WindowData.FrameWidth/2 - this.width/2 + 30, WindowData.FrameHeight/2 - this.height/2 + 40);
			}else {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 23));
				g.drawString("Do you really want to ask your teammate to surrender?", WindowData.FrameWidth/2 - this.width/2 + 30, WindowData.FrameHeight/2 - this.height/2 + 40);
			}
		}
		
	}
	
}
