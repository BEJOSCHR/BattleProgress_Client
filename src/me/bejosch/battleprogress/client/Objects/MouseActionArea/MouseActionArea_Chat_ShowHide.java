package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Color;

import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.AnimationType;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Window.Animations.AnimationDisplay;
import me.bejosch.battleprogress.client.Window.ScrollPanes.ScrollPanes;
import me.bejosch.battleprogress.client.Window.TextAreas.TextAreas;
import me.bejosch.battleprogress.client.Window.TextFields.TextFields;

public class MouseActionArea_Chat_ShowHide extends MouseActionArea{

	public boolean positionAsShowButton = true;
	
//==========================================================================================================
	/**
	 * A spacial ActionArea for the Chat handeling (hide/show)
	 * @param positionAsShowButton - boolean - if this is true it works as show button, if false it is the hide button
	 **/
	public MouseActionArea_Chat_ShowHide(boolean positionAsShowButton_) {
		super(0, 0, 0, 0, null, null, ShowBorderType.ShowOnHover, null, Color.WHITE);
		
		this.positionAsShowButton = positionAsShowButton_;
		
		if(positionAsShowButton == true) {
			
			//CURRENT HIDDEN - SO SHOW BUTTON POSITION
			int buttonX = GameData.chatX_hide, buttonY = GameData.chatY_hide+GameData.chat_height;
			
			this.xTL = buttonX-GameData.chatButton_width; this.yTL = buttonY-GameData.chatButton_height;
			this.xBR = this.xTL+GameData.chatButton_width; this.yBR = this.yTL+GameData.chatButton_height;
			this.idName = "Chat_Show";
			String[] hoverText_ = {"Show chat"};
			this.hoverText = hoverText_;
			
		}else {
			
			//CURRENT SHOWN - SO HIDE BUTTON POSITION
			int buttonX = GameData.chatX_show+GameData.chatbutton_showXausgleich, buttonY = GameData.chatY_show+GameData.chat_height;
			
			this.xTL = buttonX-GameData.chatButton_width; this.yTL = buttonY-GameData.chatButton_height;
			this.xBR = this.xTL+GameData.chatButton_width; this.yBR = this.yTL+GameData.chatButton_height;
			this.idName = "Chat_Hide";
			String[] hoverText_ = {"Hide chat"};
			this.hoverText = hoverText_;
			
		}
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		if(positionAsShowButton == true) {
			//CURRENT HIDE - NOW SHOW
			GameData.chatIsShown = true;
			TextFields.showTextField(TextFields.textField_Chat, "", 14, GameData.chatX_show+60, GameData.chatY_show, WindowData.FrameWidth-GameData.chat_width-75, 20);
			TextAreas.showTextArea(TextAreas.textArea_Chat);
			ScrollPanes.showScrollPane(ScrollPanes.scrollPane_Chat, 13, GameData.chatX_show, GameData.chatY_show+30, WindowData.FrameWidth-GameData.chat_width, GameData.chat_height);
			//STOP NOTIFY ANIMATION
			AnimationDisplay.stopAnimationType(AnimationType.Game_ChatNotification);
			//Cancle moving
			MovementHandler.cancleMovement();
			//WindowFokus:
			TextFields.textField_Chat.requestFocusInWindow();
		}else {
			//CURRENT SHOW - NOW HIDE
			GameData.chatIsShown = false;
			TextFields.hideTextField(TextFields.textField_Chat);
			ScrollPanes.hideScrollPane(ScrollPanes.scrollPane_Chat);
			TextAreas.hideTextArea(TextAreas.textArea_Chat);
			//Allow moving
			MovementHandler.allowMovement();
			//WindowFokus:
			WindowData.Frame.requestFocusInWindow();
		}
		
		super.performAction_LEFT_RELEASE();
	}
	
	@Override
	public boolean isActiv() {
		
		if(positionAsShowButton == true) {
			//ONLY WORK IF CHAT IS HIDDEN
			if(GameData.chatIsShown == false) {
				return true;
			}
		}else {
			//ONLY WORK IF CHAT IS SHOWN
			if(GameData.chatIsShown == true) {
				return true;
			}
		}
		
		return false;
	}
	
}
