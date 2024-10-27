package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Color;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.MenuData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Handler.SpectateHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.FriendRemove.OnTopWindow_FriendRemove;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.PlayerChat.OnTopWindow_PlayerChat;
import me.bejosch.battleprogress.client.ServerConnection.MinaClient;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class MouseActionArea_Menu_FriendListButton extends MouseActionArea {

	public int xPos, yPos;
	public MouseActionArea_Menu_FriendListSection connectedFriendListSection;
	
	public MouseActionArea_Menu_FriendListButton(int xPos, int yPos) {
		super(getX(xPos, yPos), getY(xPos, yPos), getX(xPos, yPos)+getWidth(), getY(xPos, yPos)+getHeight(),
				"Menu_FriendListButton_"+xPos+"_"+yPos, null, ShowBorderType.ShowAlways, Color.WHITE, Color.ORANGE);
		
		this.xPos = xPos;
		this.yPos = yPos;
		this.connectedFriendListSection = getConnectedFriendListSection(yPos);
		
	}

	public static int getX(int xPos, int yPos) {
		return MouseActionArea_Menu_FriendListSection.getX(getConnectedFriendListSection(yPos).position)+MouseActionArea_Menu_FriendListSection.getWidth()
			-MenuData.rfl_button_maße-MenuData.rfl_button_borderRight-(((MenuData.rfl_friendButtonCount-1)-xPos)*(MenuData.rfl_button_borderBetween+MenuData.rfl_button_maße));
	}
	public static int getY(int xPos, int yPos) {
		return MouseActionArea_Menu_FriendListSection.getY(getConnectedFriendListSection(yPos).position)+MenuData.rfl_button_borderTopDown;
	}
	public static int getWidth() {
		return MenuData.rfl_button_maße;
	}
	public static int getHeight() {
		return MenuData.rfl_button_maße;
	}
	
	public static MouseActionArea_Menu_FriendListSection getConnectedFriendListSection(int yPos) {
		
		for(MouseActionArea maa : GameData.mouseActionAreas) {
			if(maa.idName.equalsIgnoreCase("Menu_FriendListSection_"+yPos)) {
				return (MouseActionArea_Menu_FriendListSection) maa;
			}
		}
		
		return null;
	}
	
	@Override
	public boolean isActiv() {
		
		if(connectedFriendListSection.isHovered() && connectedFriendListSection.getRepresentedClientPlayer() != null) {
			//DISPLAYS A PLAYER (offline or online) AND IS HOVERED
			if(connectedFriendListSection.getRepresentedClientPlayer().getOnlineMin() != -1) {
				//ONLINE
				switch(this.xPos) {
				case 0:
					//INVITE OR SPECTATE
					return !connectedFriendListSection.getRepresentedClientPlayer().isInRankedGame();
				case 1:
					//CHAT
					return true;
				case 2:
					//PROFILE
					return true;
				case 3:
					//REMOVE
					return true;
				}
			}else {
				//OFFLINE
				switch(this.xPos) {
				case 0:
					//INVITE OR SPECTATE
					return false;
				case 1:
					//CHAT
					return false;
				case 2:
					//PROFILE
					return true;
				case 3:
					//REMOVE
					return true;
				}
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		//TODO OPEN OTWs
		switch(this.xPos) {
		case 0:
			//INVITE OR SPECTATE
			if(connectedFriendListSection.getRepresentedClientPlayer().isInGame()) {
				//INGAME - Spectate
				SpectateHandler.startSpectate(connectedFriendListSection.getRepresentedClientPlayer());
			}else {
				//Invite
				if(ProfilData.sendGroupInvite.contains(connectedFriendListSection.getRepresentedClientPlayer()) == false && (ProfilData.otherGroupClient == null || ProfilData.otherGroupClient.getID() != connectedFriendListSection.getRepresentedClientPlayer().getID()) ) {
					//NOT INVITED and NOT IN GROUP
					MinaClient.sendData(300, ""+connectedFriendListSection.getRepresentedClientPlayer().getID());
					ProfilData.sendGroupInvite.add(connectedFriendListSection.getRepresentedClientPlayer());
				} //ELSE DO NOTHING
			}
			break;
		case 1:
			//CHAT
			OnTopWindowHandler.openOTW(new OnTopWindow_PlayerChat(this.connectedFriendListSection.getRepresentedClientPlayer().getID()));
			break;
		case 2:
			//PROFILE
			
			break;
		case 3:
			//REMOVE
			OnTopWindowHandler.openOTW(new OnTopWindow_FriendRemove(connectedFriendListSection.getRepresentedClientPlayer()));
			break;
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(isActiv() == true) {
			g.setColor(Color.LIGHT_GRAY.darker());
			g.fillRect(this.xTL, this.yTL, getWidth(), getHeight());
			switch(this.xPos) {
			case 0:
				//INVITE OR SPECTATE
				if(connectedFriendListSection.getRepresentedClientPlayer().isInGame()) {
					//Spectate
					g.drawImage(Images.menuIcon_friendSpectate, this.xTL+1, this.yTL+1, null);
				}else {
					if(ProfilData.sendGroupInvite.contains(connectedFriendListSection.getRepresentedClientPlayer()) || (ProfilData.otherGroupClient != null && ProfilData.otherGroupClient.getID() == connectedFriendListSection.getRepresentedClientPlayer().getID()) ) {
						//ALLREADY INVITED or ALLREADY IN GROUP
						g.setColor(Color.DARK_GRAY.brighter());
						g.fillRect(this.xTL, this.yTL, getWidth(), getHeight());
						this.standardColor = Color.BLACK;
						this.hoverColor = Color.BLACK;
					}else {
						this.standardColor = Color.WHITE;
						this.hoverColor = Color.ORANGE;
					}
					//Invite
					g.drawImage(Images.menuIcon_friendInvite, this.xTL+1, this.yTL+1, null);
				}
				break;
			case 1:
				//CHAT
				g.drawImage(Images.menuIcon_friendChat, this.xTL+1, this.yTL+1, null);
				break;
			case 2:
				//PROFILE
				g.drawImage(Images.menuIcon_friendProfile, this.xTL+1, this.yTL+1, null);
				break;
			case 3:
				//REMOVE
				g.drawImage(Images.menuIcon_friendRemove, this.xTL+1, this.yTL+1, null);
				break;
			}
		}
		
		super.draw(g);
	}
	
}
