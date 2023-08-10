package me.bejosch.battleprogress.client.Objects.Chat;

import java.util.HashMap;
import java.util.LinkedHashMap;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;

public class ChatHistory {

	private int otherPlayerID;
	private LinkedHashMap<Long, ChatMessage> messages;
	private int totalRowsOfText = 0;
	
	public ChatHistory(int otherPlayerID) {
		
		this.otherPlayerID = otherPlayerID;
		this.messages = new LinkedHashMap<Long, ChatMessage>();
		
		OnTopWindowData.cachedChats.add(this);
		
	}
	
	public void addMessage(ChatMessage chatMessage) {
		long millis = System.currentTimeMillis();
		while(messages.containsKey(millis)) {
			millis++; //NEXT FREE MILLISEC
		}
		this.messages.put(millis, chatMessage);
		this.totalRowsOfText += chatMessage.getRows();
	}
	
	public HashMap<Long, ChatMessage> getMessages() {
		return messages;
	}
	public int getTotalRowsOfText() {
		return totalRowsOfText;
	}
	public int getOtherPlayerID() {
		return otherPlayerID;
	}
	
}
