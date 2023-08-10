package me.bejosch.battleprogress.client.Objects.OnTopWindow.RoundSummary;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;

public class OnTopWindow_RoundSummary extends OnTopWindow{

	public OnTopWindow_RoundSummary() {
		super("OTW_RoundSum", OnTopWindowData.roundSum_width, OnTopWindowData.roundSum_height);
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
		g.fillRect(WindowData.FrameWidth/2-this.width/2, WindowData.FrameHeight/2-this.height/2, this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(WindowData.FrameWidth/2-this.width/2, WindowData.FrameHeight/2-this.height/2, this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 30));
		g.drawString("Round "+(RoundData.currentRound-1)+" Summary", WindowData.FrameWidth/2 - this.width/2 + 30, WindowData.FrameHeight/2 - this.height/2 + 45);
		
	}
	
}
