package me.bejosch.battleprogress.client.Handler;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Objects.Chat.ChatHistory;

public class ChatHandler {

	public static ChatHistory getChatHistoy(int playerID) {
		
		for(ChatHistory chatHistory : OnTopWindowData.cachedChats) {
			if(chatHistory.getOtherPlayerID() == playerID) {
				return chatHistory;
			}
		}
		
		return new ChatHistory(playerID);
	}
	
}
