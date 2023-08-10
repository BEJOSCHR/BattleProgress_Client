package me.bejosch.battleprogress.client.Objects.OnTopWindow.FriendRemove;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Objects.ClientPlayer;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;

public class OnTopWindow_FriendRemove extends OnTopWindow {
	
	public ClientPlayer removed;
	
	public OnTopWindow_FriendRemove(ClientPlayer removed) { //SAME DATA AS GROUPINVITATION!
		super("OTW_FriendRemove", OnTopWindowData.groupInvitation_width, OnTopWindowData.groupInvitation_height);
		
		this.removed = removed;
		
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
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 26));
		String text = "Do you realy want to remove "+this.removed.getName()+" as your friend?";
		int textWidth = g.getFontMetrics().stringWidth(text);
		g.drawString(text, WindowData.FrameWidth/2-textWidth/2, y + 50);
		
	}
	
}
