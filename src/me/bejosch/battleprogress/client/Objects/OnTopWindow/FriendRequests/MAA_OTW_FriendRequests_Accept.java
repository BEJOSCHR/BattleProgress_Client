package me.bejosch.battleprogress.client.Objects.OnTopWindow.FriendRequests;

import java.awt.Color;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.FriendRequestStatus;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Objects.FriendRequest;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.ServerConnection.ServerConnection;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class MAA_OTW_FriendRequests_Accept extends MouseActionArea {

	public int pos;
	
	public MAA_OTW_FriendRequests_Accept(int pos) {
		super(getX(), getY(pos), getX()+OnTopWindowData.friendRequests_smallButtonMaße, getY(pos)+OnTopWindowData.friendRequests_smallButtonMaße
				, "OTW_FriendRequests_Accept_"+pos, null, ShowBorderType.ShowAlways, Color.WHITE, Color.GREEN.darker());
		this.OTWMMA = true;
		this.pos = pos;
	}
	
	private static int getX() {
		int sideOffSetFactor = 2;
		return WindowData.FrameWidth/2+OnTopWindowData.friendRequests_width/2-OnTopWindowData.friendRequests_sideBorder-sideOffSetFactor*(OnTopWindowData.friendRequests_smallButtonMaße+OnTopWindowData.friendRequests_smallButtonBorder);
	}
	private static int getY(int pos) {
		return WindowData.FrameHeight/2-OnTopWindowData.friendRequests_height/2+OnTopWindowData.friendRequests_topBorder+OnTopWindowData.friendRequests_smallButtonBorder+pos*OnTopWindowData.friendRequests_sectionHeight;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_FriendRequests) {
				if(ProfilData.receivedFriendRequests.size() > this.pos && ProfilData.receivedFriendRequests.get(pos).getStatus() == FriendRequestStatus.Waiting) {
					return true;
				}
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		FriendRequest request =  ProfilData.receivedFriendRequests.get(pos);
		ServerConnection.sendData(137, ServerConnection.getNewPacketId(), request.getPlayerID()+""); //ANNEHMEN
		request.setStatus(FriendRequestStatus.Accepted);
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {  
			g.drawImage(Images.menuIcon_friendRequest_Accept, this.xTL+1, this.yTL+1, null);
		}
		
		super.draw(g);
		
	}
	
}
