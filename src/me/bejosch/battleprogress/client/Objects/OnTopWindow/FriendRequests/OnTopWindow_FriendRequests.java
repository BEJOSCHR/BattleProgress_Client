package me.bejosch.battleprogress.client.Objects.OnTopWindow.FriendRequests;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.FriendRequestStatus;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Objects.FriendRequest;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;

public class OnTopWindow_FriendRequests extends OnTopWindow {
	
	public OnTopWindow_FriendRequests() {
		super("Menu_FriendRequests", OnTopWindowData.friendRequests_width, OnTopWindowData.friendRequests_height);
		
	}

	private int getX() {
		return WindowData.FrameWidth/2-this.width/2;
	}
	private int getY() {
		return WindowData.FrameHeight/2-this.height/2;
	}
	
	@Override
	public void initOnOpen() {
		
		MovementHandler.cancleMovement();
		
	}
	
	@Override
	public void performClose() {
		
		MovementHandler.allowMovement();
		
		//REMOVE HANDLED REQUESTS
		List<FriendRequest> toRemove = new ArrayList<>();
		for(FriendRequest request : ProfilData.receivedFriendRequests) {
			if(request.getStatus() != FriendRequestStatus.Waiting) {
				toRemove.add(request);
			}
		}
		for(FriendRequest removeRequest : toRemove) { ProfilData.receivedFriendRequests.remove(removeRequest); }
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(this.getX(), this.getY(), this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(this.getX(), this.getY(), this.width, this.height);
		
		//TITLE
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Arial", Font.BOLD, 32));
		String text = "Friendrequests";
		int width = g.getFontMetrics().stringWidth(text);
		g.drawString(text, this.getX()+this.width/2-width/2, this.getY()+50);
		
		//DISPLAY REQUESTS
		if(ProfilData.receivedFriendRequests.isEmpty()) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 22));
			String text2 = "You have no friendrequests!";
			int width2 = g.getFontMetrics().stringWidth(text2);
			g.drawString(text2, this.getX()+this.width/2-width2/2, this.getY()+OnTopWindowData.friendRequests_topBorder+OnTopWindowData.friendRequests_sectionHeight/2+g.getFontMetrics().getHeight()/3);
		}else {
			int startY = this.getY()+OnTopWindowData.friendRequests_topBorder;
			for(int i = 0 ; i < OnTopWindowData.friendRequests_totalSectionCount ; i++) {
				if(ProfilData.receivedFriendRequests.size() <= i) { break; }
				g.setColor(Color.LIGHT_GRAY);
				int thisX = getX()+OnTopWindowData.friendRequests_sideBorder;
				int thisY = startY+(i*OnTopWindowData.friendRequests_sectionHeight);
				g.drawRect(thisX, thisY, OnTopWindowData.friendRequests_sectionWidth, OnTopWindowData.friendRequests_sectionHeight);
				FriendRequest connectedRequest = ProfilData.receivedFriendRequests.get(i);
				switch(connectedRequest.getStatus()) {
				case Waiting:
					g.setColor(Color.WHITE);
					g.setFont(new Font("Arial", Font.BOLD, 20));
					g.drawString((i+1)+". "+connectedRequest.getPlayerName(), thisX+30, thisY+OnTopWindowData.friendRequests_sectionHeight/2+g.getFontMetrics().getHeight()/3);
					break;
				case Accepted:
					g.setColor(Color.GREEN.darker());
					g.setFont(new Font("Arial", Font.BOLD, 20));
					g.drawString(connectedRequest.getPlayerName()+" - Accepted", thisX+30, thisY+OnTopWindowData.friendRequests_sectionHeight/2+g.getFontMetrics().getHeight()/3);
					break;
				case Declined:
					g.setColor(Color.RED.darker());
					g.setFont(new Font("Arial", Font.BOLD, 20));
					g.drawString(connectedRequest.getPlayerName()+" - Declined", thisX+30, thisY+OnTopWindowData.friendRequests_sectionHeight/2+g.getFontMetrics().getHeight()/3);
					break;
				}
			}
		}
		
		
	}
	
}
