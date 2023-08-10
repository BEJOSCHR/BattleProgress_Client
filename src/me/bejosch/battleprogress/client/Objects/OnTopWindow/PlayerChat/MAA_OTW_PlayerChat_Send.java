package me.bejosch.battleprogress.client.Objects.OnTopWindow.PlayerChat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Handler.ClientPlayerHandler;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Objects.ClientPlayer;
import me.bejosch.battleprogress.client.Objects.Chat.ChatHistory;
import me.bejosch.battleprogress.client.Objects.Chat.ChatMessage;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.ServerConnection.ServerConnection;
import me.bejosch.battleprogress.client.Window.TextFields.TextFields;

public class MAA_OTW_PlayerChat_Send extends MouseActionArea {

	public MAA_OTW_PlayerChat_Send() {
		super(WindowData.FrameWidth/2+OnTopWindowData.chat_messageFieldWidth/2+OnTopWindowData.chat_messageFieldBorders-OnTopWindowData.chat_sideButtonsBorder, WindowData.FrameHeight/2+OnTopWindowData.chat_height/2-OnTopWindowData.chat_messageFieldDownBorder-OnTopWindowData.chat_messageFieldHeight 
				, WindowData.FrameWidth/2+OnTopWindowData.chat_messageFieldWidth/2+OnTopWindowData.chat_messageFieldBorders-OnTopWindowData.chat_sideButtonsBorder+OnTopWindowData.chat_sideButtonsWidth, WindowData.FrameHeight/2+OnTopWindowData.chat_height/2-OnTopWindowData.chat_messageFieldDownBorder 
				, "OTW_PlayerChat_Send", null, ShowBorderType.ShowAlways, Color.WHITE, Color.GREEN.darker());
		this.OTWMMA = true;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_PlayerChat) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		String message = TextFields.textField_playerChat.getText();
		
		if(message.replace(" ", "").isEmpty() == false) {
			message = Funktions.checkStringForByteUse(message); //REPLACE NON SENDABLEs
			
			ChatHistory activeChat = ((OnTopWindow_PlayerChat) OnTopWindowData.onTopWindow).chat;
			ClientPlayer otherPlayer = ClientPlayerHandler.getNewClientPlayer(activeChat.getOtherPlayerID());
			if(otherPlayer.getOnlineMin() != -1) {
				//NOT OFFLINE
				ServerConnection.sendData(140, ServerConnection.getNewPacketId(), activeChat.getOtherPlayerID()+";"+message);
				activeChat.addMessage(new ChatMessage(ProfilData.thisClient.getID(), message));
			}else {
				//OFFLINE
				activeChat.addMessage(new ChatMessage(ProfilData.thisClient.getID(), otherPlayer.getName()+" went offline!"));
			}
			TextFields.textField_playerChat.setText("");
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
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-width/2, this.yBR-8);
			}else {
				//NO HOVER
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				String text = "Send";
				int width = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-width/2, this.yBR-8);
			}
		}
		
		super.draw(g);
		
	}
	
}
