package me.bejosch.battleprogress.client.Objects.OnTopWindow.PlayerChat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Handler.ChatHandler;
import me.bejosch.battleprogress.client.Handler.ClientPlayerHandler;
import me.bejosch.battleprogress.client.Objects.Chat.ChatHistory;
import me.bejosch.battleprogress.client.Objects.Chat.ChatMessage;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;
import me.bejosch.battleprogress.client.Window.TextFields.TextFields;

public class OnTopWindow_PlayerChat extends OnTopWindow {

	public ChatHistory chat;
	private int scrollValue = 0;
	
	public OnTopWindow_PlayerChat(int playerID) {
		super("Menu_PlayerChat", OnTopWindowData.chat_width, OnTopWindowData.chat_height);
		
		this.chat = ChatHandler.getChatHistoy(playerID);
		
	}

	private int getX() {
		return WindowData.FrameWidth/2-this.width/2;
	}
	private int getY() {
		return WindowData.FrameHeight/2-this.height/2;
	}
	
	private int getTotalMessageHeight() {
		int messages = chat.getMessages().size();
		return OnTopWindowData.chat_borderBetweenMessages*(messages-1)+OnTopWindowData.chat_heightPerMessageRow*chat.getTotalRowsOfText();
	}
	
	@Override
	public void initOnOpen() {
		
		TextFields.showTextField(TextFields.textField_playerChat, "", 20, WindowData.FrameWidth/2-OnTopWindowData.chat_messageFieldWidth/2, WindowData.FrameHeight/2+OnTopWindowData.chat_height/2-OnTopWindowData.chat_messageFieldDownBorder-OnTopWindowData.chat_messageFieldHeight, OnTopWindowData.chat_messageFieldWidth, OnTopWindowData.chat_messageFieldHeight);
		TextFields.textField_playerChat.requestFocus();
		
	}
	
	@Override
	public void performClose() {
		
		TextFields.hideTextField(TextFields.textField_playerChat);
		WindowData.Frame.requestFocus();
		
	}
	
	@Override
	public void onMouseWheelTurned(boolean scrollUp) {
		
		if(OnTopWindowData.chat_messageArea_height >= getTotalMessageHeight()) { return; } //ALL FITS SO NO SCROLL
		
		if(scrollUp) {
			scrollValue -= 12;
			if(scrollValue <= OnTopWindowData.chat_messageArea_height-getTotalMessageHeight()-3) { scrollValue = OnTopWindowData.chat_messageArea_height-getTotalMessageHeight()-3; }
		}else {
			scrollValue += 12;
			if(scrollValue > 0) { scrollValue = 0; }
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(this.getX(), this.getY(), this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(this.getX(), this.getY(), this.width, this.height);
		
		//SIDE LINE
		g.setColor(Color.DARK_GRAY.brighter());
		g.drawLine(this.getX()+OnTopWindowData.chat_sideBorders, this.getY()+1, this.getX()+OnTopWindowData.chat_sideBorders, this.getY()+OnTopWindowData.chat_messageArea_height);
		g.drawLine(this.getX()+OnTopWindowData.chat_sideBorders+OnTopWindowData.chat_messageArea_width, this.getY()+1, this.getX()+OnTopWindowData.chat_sideBorders+OnTopWindowData.chat_messageArea_width, this.getY()+OnTopWindowData.chat_messageArea_height);
		g.drawLine(this.getX()+OnTopWindowData.chat_sideBorders, this.getY()+OnTopWindowData.chat_messageArea_height, this.getX()+OnTopWindowData.chat_sideBorders+OnTopWindowData.chat_messageArea_width, this.getY()+OnTopWindowData.chat_messageArea_height);
		
		//NAMES
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 22));
		String otherPlayerName = ClientPlayerHandler.getNewClientPlayer(this.chat.getOtherPlayerID()).getName(), yourName = ProfilData.thisClient.getName();
		g.drawString(otherPlayerName, getX()+OnTopWindowData.chat_sideBorders, this.getY()+OnTopWindowData.chat_messageArea_height+8+g.getFontMetrics().getHeight());
		g.drawString(yourName, getX()+this.width-OnTopWindowData.chat_sideBorders-g.getFontMetrics().stringWidth(yourName), this.getY()+OnTopWindowData.chat_messageArea_height+8+g.getFontMetrics().getHeight());
		
		//MESSAGES
		int currentRow = 0, currentMessage = 0;
		List<Long> timestamps = new LinkedList<Long>(this.chat.getMessages().keySet());
		Collections.reverse(timestamps);
		for(long messageDate : timestamps) {
			ChatMessage message = this.chat.getMessages().get(messageDate);
			int messageX; Color backGroundColor;
			if(ProfilData.thisClient.getID() == message.getSenderID()) {
				//THIS CLIENT -> RIGHT
				messageX = WindowData.FrameWidth/2+OnTopWindowData.chat_middleBorder/2-1;
				backGroundColor = Color.LIGHT_GRAY.darker();
			}else {
				//OTHER CLIENT -> LEFT
				messageX = getX()+OnTopWindowData.chat_sideBorders+1;
				backGroundColor = Color.LIGHT_GRAY.darker().darker();
			}
			for(String row : message.getMessageRows()) {
				int rowY = (getY()+this.height-OnTopWindowData.chat_downBorder)-(currentMessage*OnTopWindowData.chat_borderBetweenMessages)-((currentRow+1)*OnTopWindowData.chat_heightPerMessageRow)-1-scrollValue; //SCROLLVALUE IS NEGATIV
				if(rowY > getY() && (rowY+OnTopWindowData.chat_heightPerMessageRow) < (getY()+this.height-OnTopWindowData.chat_downBorder)) {
					//VISIBLE
					g.setColor(backGroundColor);
					g.fillRoundRect(messageX, rowY, OnTopWindowData.chat_widthPerMessage, OnTopWindowData.chat_heightPerMessageRow, 5, 5);
					g.setColor(Color.WHITE);
					g.setFont(new Font("Arial", Font.CENTER_BASELINE, 18));
					g.drawString(row, messageX, rowY+OnTopWindowData.chat_heightPerMessageRow-5);
				}
				currentRow++;
			}
			currentMessage++;
		}
		
	}
	
}
