package me.bejosch.battleprogress.client.Objects.OnTopWindow.GameAccept;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;

public class OnTopWindow_GameAccept extends OnTopWindow {
	
	public int gameID;
	public int waitTime = 8;
	
	public boolean accepted = false;
	
	private Timer timer = null;
	
	public OnTopWindow_GameAccept(int gameID) {
		super("OTW_GameAccept", OnTopWindowData.gameAccept_width, OnTopWindowData.gameAccept_height);
		
		this.gameID = gameID;
	}

	@Override
	public void initOnOpen() {
		
		MovementHandler.cancleMovement();
		
		startTimer();
		
	}
	
	
	@Override
	public void performClose() {
		
		MovementHandler.allowMovement();
		
		stopTimer();
		
	}

	private void startTimer() {
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				
				if(waitTime == 0) {
					stopTimer();
					if(accepted == false) {
						//NOT ACCEPTED
						OnTopWindowHandler.closeOTW();
					}else {
						//CHECK AFTER DELAY IF GAME HAS NO/ STARTED - CLOSE OTW IF NOT
						//Happens if one group member declines check and this one accept, so he is stuck because no game starts
						new Timer().schedule(new TimerTask() {	
							@Override
							public void run() {
								if(GameData.gameIsRunning == false) {
									if(OnTopWindowData.onTopWindow == thisGet()) {
										OnTopWindowHandler.closeOTW();
									}
								}
							}
						}, 1000*3);
					}
				}else {
					waitTime--;
				}
				
			}
		}, 1000, 1000);
		
	}
	
	public void stopTimer() {
		
		timer.cancel();
		waitTime = 0;
		
	}
	
	private OnTopWindow_GameAccept thisGet() {
		return this;
	}
	
	@Override
	public void draw(Graphics g) {
		
		int x = WindowData.FrameWidth/2-this.width/2;
		int y = WindowData.FrameHeight/2-this.height/2;
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(x, y, this.width, this.height);
		
		//TITLE
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 32));
		String text = "Game found";
		int textWidth = g.getFontMetrics().stringWidth(text);
		g.drawString(text, WindowData.FrameWidth/2-textWidth/2, y + 50);
		
		//TIME
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 22));
		String text2 = ""+waitTime;
		int textWidth2 = g.getFontMetrics().stringWidth(text2);
		g.drawString(text2, WindowData.FrameWidth/2-textWidth2/2, y + 95);
		
		//ACCEPTED
		if(this.accepted == true) {
			g.setColor(Color.GREEN.darker());
			g.setFont(new Font("Arial", Font.CENTER_BASELINE, 30));
			String text3 = "Accepted";
			int textWidth3 = g.getFontMetrics().stringWidth(text3);
			g.drawString(text3, WindowData.FrameWidth/2-textWidth3/2, WindowData.FrameHeight/2+this.height/2-OnTopWindowData.gameAccept_buttonBottomBorder-12);
		}
		
	}
	
}
