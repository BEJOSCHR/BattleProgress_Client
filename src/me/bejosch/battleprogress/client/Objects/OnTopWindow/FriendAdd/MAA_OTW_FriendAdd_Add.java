package me.bejosch.battleprogress.client.Objects.OnTopWindow.FriendAdd;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Objects.ClientPlayer;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.ServerConnection.ServerConnection;
import me.bejosch.battleprogress.client.Window.TextFields.TextFields;

public class MAA_OTW_FriendAdd_Add extends MouseActionArea {

	public MAA_OTW_FriendAdd_Add() {
		super(getX(), getY(), getX()+OnTopWindowData.friendAdd_buttonWidth, getY()+OnTopWindowData.friendAdd_buttonHeight
				, "OTW_FriendAdd_Add", null, ShowBorderType.ShowAlways, Color.WHITE, Color.GREEN);
		this.OTWMMA = true;
	}
	
	private static int getX() {
		return WindowData.FrameWidth/2-OnTopWindowData.friendAdd_buttonWidth-OnTopWindowData.friendAdd_buttonBorderBetween/2;
	}
	private static int getY() {
		return WindowData.FrameHeight/2+OnTopWindowData.friendAdd_height/2-OnTopWindowData.friendAdd_buttonHeight-OnTopWindowData.friendAdd_buttonBottomBorder;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_FriendAdd) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		String username = TextFields.textField_friendAdd.getText();
		
		if(username.equalsIgnoreCase(ProfilData.thisClient.getName())) {
			//TROLL
			((OnTopWindow_FriendAdd) OnTopWindowData.onTopWindow).nameErrorMessage = "You can't be a friend to yourself :(";
			return;
		}
		
		if(username.replace(" ", "").isEmpty() == false) {
			
			String usernameValidation = Funktions.checkForUsernameValidation(username);
			if(usernameValidation == null) {
				//VALID
				for(ClientPlayer clientPlayer : ProfilData.friendList_online) {
					if(clientPlayer.getName().equalsIgnoreCase(username)) {
						//ALREADY A FRIEND
						((OnTopWindow_FriendAdd) OnTopWindowData.onTopWindow).nameErrorMessage = username+" is already your friend!";
						return;
					}
				}
				for(ClientPlayer clientPlayer : ProfilData.friendList_offline) {
					if(clientPlayer.getName().equalsIgnoreCase(username)) {
						//ALREADY A FRIEND
						((OnTopWindow_FriendAdd) OnTopWindowData.onTopWindow).nameErrorMessage = username+" is already your friend!";
						return;
					}
				}
				ServerConnection.sendData(136, ServerConnection.getNewPacketId(), username+"");
				((OnTopWindow_FriendAdd) OnTopWindowData.onTopWindow).nameErrorMessage = "";
			}else {
				//INVALID
				((OnTopWindow_FriendAdd) OnTopWindowData.onTopWindow).nameErrorMessage = usernameValidation;
			}
			
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {  
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				//HOVER
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.PLAIN, 21));
				String text = "Send";
				int width = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-width/2, this.yBR-12);
			}else {
				//NO HOVER
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				String text = "Send";
				int width = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-width/2, this.yBR-12);
			}
		}
		
		super.draw(g);
		
	}
	
}
