package me.bejosch.battleprogress.client.Objects.OnTopWindow.PlayerDisconnect;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Game.Handler.Game_RoundHandler;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Objects.ClientPlayer;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;

public class OnTopWindow_PlayerDisconnect extends OnTopWindow {
	
	public ClientPlayer disconnected;
	public int reconnectTime;
	
	public OnTopWindow_PlayerDisconnect(ClientPlayer disconnected, int reconnectTime) {
		super("OTW_PlayerDisconnect", OnTopWindowData.playerDisconnect_width, OnTopWindowData.playerDisconnect_height);
		
		this.disconnected = disconnected;
		this.reconnectTime = reconnectTime;
		
	}

	@Override
	public void initOnOpen() {
		
		MovementHandler.cancleMovement();
		Game_RoundHandler.deactivateRoundChanging();
		
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(reconnectTime > 0) {
					reconnectTime--;
				}else {
					this.cancel();
				}
			}
		}, 0, 1000);
		
	}
	
	
	@Override
	public void performClose() {
		
		MovementHandler.allowMovement();
		Game_RoundHandler.activateRoundChanging();
		
	}

	@Override
	public void draw(Graphics g) {
		
		int x = WindowData.FrameWidth/2-this.width/2;
		int y = WindowData.FrameHeight/2-this.height/2;
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(x, y, this.width, this.height);
		
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 35));
		String text = this.disconnected.getName()+" disconnected from the game!";
		int textWidth = g.getFontMetrics().stringWidth(text);
		g.drawString(text, WindowData.FrameWidth/2-textWidth/2, y + 50);
		
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 40));
		String text_2 = this.reconnectTime+"";
		int textWidth_2 = g.getFontMetrics().stringWidth(text_2);
		g.drawString(text_2, WindowData.FrameWidth/2-textWidth_2/2, y + 100);
		
	}
	
}
