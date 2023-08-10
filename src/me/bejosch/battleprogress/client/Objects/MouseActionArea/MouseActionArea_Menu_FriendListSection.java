package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.MenuData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.ClientPlayer;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.FriendAdd.OnTopWindow_FriendAdd;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.FriendRequests.OnTopWindow_FriendRequests;

public class MouseActionArea_Menu_FriendListSection extends MouseActionArea {

	public int position;
	
	public MouseActionArea_Menu_FriendListSection(int position) {
		super(getX(position), getY(position), getX(position)+getWidth(), getY(position)+getHeight(),
				"Menu_FriendListSection_"+position, null, ShowBorderType.ShowAlways, null, null);
		
		this.position = position;
		
	}

	public static int getX(int pos) {
		return WindowData.FrameWidth-MenuData.rfl_width+1+MenuData.rfl_friendSectionBorder;
	}
	public static int getY(int pos) {
		return MenuData.til_height+((pos+1)*MenuData.rfl_friendSectionHeight);
	}
	public static int getWidth() {
		return MenuData.rfl_friendSectionWidth-2;
	}
	public static int getHeight() {
		return MenuData.rfl_friendSectionHeight-1;
	}
	
	public ClientPlayer getRepresentedClientPlayer() {
		if(getRealScrollPos() < ProfilData.friendList_online.size()+1) {
			//ONLINE PLAYER
			if(getRealScrollPos()-1 >= 0 && getRealScrollPos()-1 < ProfilData.friendList_online.size()) {
				//IN BOUNCE
				return ProfilData.friendList_online.get(getRealScrollPos()-1); // 1 HEADER = 1
			}else {
				return null;
			}
		}else {
			//OFFLINE PLAYER
			if(getRealScrollPos()-2-1-ProfilData.friendList_online.size() >= 0 && getRealScrollPos()-2-1-ProfilData.friendList_online.size() < ProfilData.friendList_offline.size()) {
				//IN BOUNCE
				return ProfilData.friendList_offline.get(getRealScrollPos()-2-1-ProfilData.friendList_online.size()); //TOTAL 2 HEADER AND 1 SPACER = 2+1 = 3
			}else {
				return null;
			}
		}
	}
	public int getRealScrollPos() {
		return this.position+MenuData.friendList_scrollValue;
	}
	
	@Override
	public boolean isActiv() {
		
		if(MenuData.friendListOpened == false || MenuData.rfl_OpenCloseFactor != 1.0) {
			return false;
		}
		
		if(getRealScrollPos() == 0) {
			//ONLINE HEADER
			return true;
		}else if(getRealScrollPos() == ProfilData.friendList_online.size()+1) {
			//SPACER 1.
			return false;
		}else if(getRealScrollPos() == ProfilData.friendList_online.size()+2) {
			//OFFLINE HEADER
			return true;
		}else if(this.position == MenuData.rfl_friendSectionCount-3) {
			//SPACER 2.
			return false;
		}else if(this.position == MenuData.rfl_friendSectionCount-2) {
			//FOOTER 1. - ADD NEW FRIEND
			return true;
		}else if(this.position == MenuData.rfl_friendSectionCount-1) {
			//FOOTER 2. - MANAGE FRIEND REQUESTS
			return true;
		}else if(getRealScrollPos() < ProfilData.friendList_online.size()+1) { // 1 HEADER
			//ONLINE PLAYER
			return true;
		}else if(getRealScrollPos() < ProfilData.friendList_online.size()+2+1+ProfilData.friendList_offline.size()) { //TOTAL 2 HEADER AND 1 SPACER = 2+1 = 3
			//OFFLINE PLAYER
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public void performAction_HOVER() {
		
		
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		if(this.position == MenuData.rfl_friendSectionCount-2) {
			//FOOTER 1. - ADD NEW FRIEND
			OnTopWindowHandler.openOTW(new OnTopWindow_FriendAdd());
		}else if(this.position == MenuData.rfl_friendSectionCount-1) {
			//FOOTER 2. - MANAGE FRIEND REQUESTS
			OnTopWindowHandler.openOTW(new OnTopWindow_FriendRequests());
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(isActiv() == true) {
			if(getRealScrollPos() == 0) {
				//ONLINE HEADER
				this.standardColor = Color.WHITE;
				this.hoverColor = this.standardColor;
				//BACKGROUND
				g.setColor(Color.DARK_GRAY.brighter());
				g.fillRect(xTL, yTL, getWidth(), getHeight());
				//TITLE
				g.setColor(Color.GREEN.darker()); //GREEN
				g.setFont(new Font("Arial", Font.BOLD, 20));
				String text = "Online Player ("+ProfilData.friendList_online.size()+"/"+(ProfilData.friendList_online.size()+ProfilData.friendList_offline.size())+")";
				int textWidth = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+getWidth()/2-textWidth/2, this.yTL+(this.yBR-this.yTL)/2+8);
			}else if(getRealScrollPos() == ProfilData.friendList_online.size()+1) {
				//SPACER 1.
				//NOT ACTIVE ANYWAY
			}else if(getRealScrollPos() == ProfilData.friendList_online.size()+2) {
				//OFFLINE HEADER
				this.standardColor = Color.WHITE;
				this.hoverColor = this.standardColor;
				//BACKGROUND
				g.setColor(Color.DARK_GRAY.brighter());
				g.fillRect(xTL, yTL, getWidth(), getHeight());
				//TITLE
				g.setColor(Color.RED.darker()); //RED
				g.setFont(new Font("Arial", Font.BOLD, 20));
				String text = "Offline Player ("+ProfilData.friendList_offline.size()+"/"+(ProfilData.friendList_online.size()+ProfilData.friendList_offline.size())+")";
				int textWidth = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+getWidth()/2-textWidth/2, this.yTL+(this.yBR-this.yTL)/2+8);
			}else if(this.position == MenuData.rfl_friendSectionCount-3) {
				//SPACER 2.
				//NOT ACTIVE ANYWAY
			}else if(this.position == MenuData.rfl_friendSectionCount-2) {
				//FOOTER 1. - ADD NEW FRIEND
				this.standardColor = Color.CYAN.darker();
				this.hoverColor = Color.CYAN;
				//TITLE
				if(this.isHovered()) {
					g.setColor(hoverColor);
				}else {
					g.setColor(standardColor);
				}
				g.setFont(new Font("Arial", Font.BOLD, 20));
				String text = "Add new Friend";
				int textWidth = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+getWidth()/2-textWidth/2, this.yTL+(this.yBR-this.yTL)/2+8);
			}else if(this.position == MenuData.rfl_friendSectionCount-1) {
				//FOOTER 2. - MANAGE FRIEND REQUESTS
				this.standardColor = Color.CYAN.darker();
				this.hoverColor = Color.CYAN;
				//TITLE
				if(this.isHovered()) {
					g.setColor(hoverColor);
				}else {
					g.setColor(standardColor);
				}
				g.setFont(new Font("Arial", Font.BOLD, 20));
				String text = "Manage Friendrequests";
				int textWidth = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+getWidth()/2-textWidth/2, this.yTL+(this.yBR-this.yTL)/2+8);
			}else if(getRealScrollPos() < ProfilData.friendList_online.size()+1) {
				//ONLINE PLAYER
				this.standardColor = Color.LIGHT_GRAY;
				this.hoverColor = Color.WHITE;
				
				//NAME
				g.setColor(standardColor);
				g.setFont(new Font("Arial", Font.BOLD, 22));
				g.drawString(getRepresentedClientPlayer().getName(), this.xTL+10, this.yTL+getHeight()/2+g.getFontMetrics().getHeight()/3);
				
				if(this.isHovered() == false) {
					//NOT HOVERED
					
					//STATUS
					g.setColor(Color.GREEN.darker());
					g.setFont(new Font("Arial", Font.BOLD, 16));
					String activity = getRepresentedClientPlayer().getCurrentActivity();
					int activityWidth = g.getFontMetrics().stringWidth(activity);
					g.drawString(activity, this.xTL+getWidth()-10-activityWidth, this.yTL+getHeight()/2+g.getFontMetrics().getHeight()/3);
					
				}
			}else {
				//OFFLINE PLAYER
				this.standardColor = Color.LIGHT_GRAY.darker();
				this.hoverColor = Color.LIGHT_GRAY;
				
				//NAME
				g.setColor(standardColor);
				g.setFont(new Font("Arial", Font.BOLD, 22));
				g.drawString(getRepresentedClientPlayer().getName(), this.xTL+10, this.yTL+getHeight()/2+g.getFontMetrics().getHeight()/3);
				
				if(this.isHovered() == false) {
					//NOT HOVERED
					
					//STATUS
					g.setColor(standardColor);
					g.setFont(new Font("Arial", Font.BOLD, 16));
					String activity = getRepresentedClientPlayer().getCurrentActivity();
					int activityWidth = g.getFontMetrics().stringWidth(activity);
					g.drawString(activity, this.xTL+getWidth()-10-activityWidth, this.yTL+getHeight()/2+g.getFontMetrics().getHeight()/3);
					
				}
				
			}
		}
		
		super.draw(g);
	}
	
}
