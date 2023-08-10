package me.bejosch.battleprogress.client.Objects.OnTopWindow.GroupInvitation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Objects.ClientPlayer;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;

public class OnTopWindow_GroupInvitation extends OnTopWindow {
	
	public ClientPlayer inviter;
	
	public OnTopWindow_GroupInvitation(ClientPlayer inviter) {
		super("OTW_GroupInvitation", OnTopWindowData.groupInvitation_width, OnTopWindowData.groupInvitation_height);
		
		this.inviter = inviter;
		
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
		
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 35));
		String text = this.inviter.getName()+" invites you to his group";
		int textWidth = g.getFontMetrics().stringWidth(text);
		g.drawString(text, WindowData.FrameWidth/2-textWidth/2, y + 50);
		
	}
	
}
