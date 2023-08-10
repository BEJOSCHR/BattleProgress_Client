package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Color;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.HoverHandler;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage_Located;

public class MouseActionArea_InfoMessages extends MouseActionArea{

	public int infoMessageNumber = -1;
	
//==========================================================================================================
	/**
	 * A spacial ActionArea for the InfoMessages in the top right corner
	 **/
	public MouseActionArea_InfoMessages(int infoMessageNumber_) {
		super(0, 0, 0, 0, "InfoMessage_"+infoMessageNumber_, null, ShowBorderType.ShowOnHover, null, Color.WHITE); //HOVER TEXT IS OVERWRITTEN
		
		this.infoMessageNumber = infoMessageNumber_;
		
		//REQUEST COORDS UPDATE FOR ACTION AREAS
		GameData.coordsUpdatedNeeded = true;
		
	}
	
//==========================================================================================================
	/**
	 * Updates the coordinates
	 **/
	public void updateCoordinates() {
		
		if(isActiv() == true) {
			InfoMessage connectedInfoMessage = GameData.NotificationList.get(infoMessageNumber);
			xTL = connectedInfoMessage.current_TR_X-connectedInfoMessage.getWidth();
			xBR = connectedInfoMessage.current_TR_X;
			yTL = connectedInfoMessage.current_TR_Y;
			yBR = connectedInfoMessage.current_TR_Y+connectedInfoMessage.getHeight();
		}
		
	}
	
	@Override
	public void performAction_HOVER() {
		
		if(GameData.NotificationList.get(infoMessageNumber) instanceof InfoMessage_Located) {
			String[] hoverText_ = {"Left click - Jump to location", "Right click - Remove"};
			HoverHandler.updateHoverMessage(this.idName, hoverText_);
//		}else if( ### [TODO OTHER SPECIAL INFOMESSAGES] ) {
//			
		}else {
			String[] hoverText_ = {"Right click - Remove"};
			HoverHandler.updateHoverMessage(this.idName, hoverText_);
		}
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		InfoMessage connectedInfoMessage = GameData.NotificationList.get(infoMessageNumber);
		if(connectedInfoMessage instanceof InfoMessage_Located) {
			InfoMessage_Located infoMessage_Located = (InfoMessage_Located) connectedInfoMessage;
			infoMessage_Located.jumpToConnectedLocation();
		}
		
	}
	
	@Override
	public void performAction_RIGHT_RELEASE() {
		
		InfoMessage connectedInfoMessage = GameData.NotificationList.get(infoMessageNumber);
		connectedInfoMessage.remove();
		
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
	}
	
	@Override
	public boolean isActiv() {
		try{
			if(GameData.NotificationList.get(infoMessageNumber) != null) {
				return true;
			}else {
				return false;
			}
		}catch(IndexOutOfBoundsException error) {
			return false;
		}
	}
	

}
