package me.bejosch.battleprogress.client.Objects.Chat;

import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;

public class ChatMessage {

	private int senderID;
	private String completeMessage;
	private List<String> messageRows = new ArrayList<String>();
	private int rows = 1;
	
	public ChatMessage(int senderID, String message) {
		
		this.senderID = senderID;
		
		String total = "";
		int maxWidth = (int) (OnTopWindowData.chat_widthPerMessage/10.0); //HOM MANY CHARs APROXIMATLY
		
		if(message.contains(" ") == false) {
			//HAS NO " "
			if(message.length() >= maxWidth) {
				message = message.substring(0, maxWidth-1);
			}
			messageRows.add(0, " "+message);
		}else {
			//HAS " "
			for(String messagePart : message.split(" ")) {
				int currentTotalLength = total.length()+1+messagePart.length();
				if( currentTotalLength >= maxWidth) {
					messageRows.add(0, " "+total);
					total = messagePart; //RESET
					rows++;
				}else {
					total = total+messagePart+" ";
				}
			}
			messageRows.add(0, " "+total);
		}
		
		this.completeMessage = " "+message;
		
		
	}
	
	public int getSenderID() {
		return senderID;
	}
	public List<String> getMessageRows() {
		return messageRows;
	}
	public String getCompleteMessage() {
		return completeMessage;
	}
	public int getRows() {
		return rows;
	}
	
}
