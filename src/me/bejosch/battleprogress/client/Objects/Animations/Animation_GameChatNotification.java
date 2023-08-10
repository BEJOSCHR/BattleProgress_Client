package me.bejosch.battleprogress.client.Objects.Animations;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.AnimationType;

public class Animation_GameChatNotification extends Animation {

	public int newMessagesCount = 1; //ANZAHL AN NEUEN NACHRICHTEN
	
	public final int distanceToButton = 7;
	public int radiusOfRedCircle = 12;
	public boolean blink = true;
	
	public Animation_GameChatNotification() {
		super(AnimationType.Game_ChatNotification);
		
	}
	
	@Override
	public void getParametersFromType() {
		
		speed = 150;
		
	}
	
	@Override
	public void changeAction() {
		
		if(blink == true) {
			blink = false;
		}else {
			blink = true;
		}
		
	}
	
	@Override
	public void drawPart(Graphics g) {
		
		if(GameData.chatIsShown == false) {
			if(blink == true) {
				g.setColor(Color.ORANGE);
				g.drawRect(GameData.chatX_hide-GameData.chatButton_width-distanceToButton, GameData.chatY_hide+GameData.chat_height-GameData.chatButton_height-distanceToButton, GameData.chatButton_width+(distanceToButton*2), GameData.chatButton_height+(distanceToButton*2));
			}
			
			if(newMessagesCount < 10) {
				g.setColor(Color.RED);
				g.fillOval(GameData.chatX_hide-(radiusOfRedCircle*2)-8, GameData.chatY_hide+GameData.chat_height-GameData.chatButton_height-2, radiusOfRedCircle*2, radiusOfRedCircle*2);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.PLAIN, 18));
				g.drawString(""+newMessagesCount, GameData.chatX_hide-(radiusOfRedCircle*2)-1, GameData.chatY_hide+GameData.chat_height-GameData.chatButton_height+radiusOfRedCircle*2-8);
			}else {
				if(newMessagesCount >= 100) { newMessagesCount = 99; }
				g.setColor(Color.RED);
				g.fillOval(GameData.chatX_hide-(radiusOfRedCircle*2)-8, GameData.chatY_hide+GameData.chat_height-GameData.chatButton_height-2, radiusOfRedCircle*2, radiusOfRedCircle*2);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 17));
				g.drawString(""+newMessagesCount, GameData.chatX_hide-(radiusOfRedCircle*2)-5, GameData.chatY_hide+GameData.chat_height-GameData.chatButton_height+radiusOfRedCircle*2-2-3-4);
			}
			
		}
		
	}
	
	@Override
	public void cancle() {
		super.cancle();
	}
	
}
