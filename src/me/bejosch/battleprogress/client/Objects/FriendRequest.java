package me.bejosch.battleprogress.client.Objects;

import me.bejosch.battleprogress.client.Enum.FriendRequestStatus;

public class FriendRequest {

	private int playerID;
	private String playerName;
	
	private FriendRequestStatus status = FriendRequestStatus.Waiting;
	
	public FriendRequest(int playerID, String playerName) {
		
		this.playerID = playerID;
		this.playerName = playerName;
		
	}
	
	public void setStatus(FriendRequestStatus status) {
		this.status = status;
	}
	
	public int getPlayerID() {
		return playerID;
	}
	public String getPlayerName() {
		return playerName;
	}
	public FriendRequestStatus getStatus() {
		return status;
	}
	
}
