package me.bejosch.battleprogress.client.Data;

import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Objects.ClientPlayer;
import me.bejosch.battleprogress.client.Objects.FriendRequest;

public class ProfilData {
	
	public static ClientPlayer thisClient = null;
	public static ClientPlayer otherGroupClient = null;
	
	public static boolean successlogin = false;
	
	public static List<ClientPlayer> playerWaitingforDataReceive = new ArrayList<ClientPlayer>();
	public static List<ClientPlayer> allCurrentClientPlayer = new ArrayList<ClientPlayer>();
	
	public static List<ClientPlayer> friendList_online = new ArrayList<ClientPlayer>();
	public static List<ClientPlayer> friendList_offline = new ArrayList<ClientPlayer>();
	
	public static List<ClientPlayer> sendGroupInvite = new ArrayList<ClientPlayer>();
	public static List<FriendRequest> receivedFriendRequests = new ArrayList<FriendRequest>();
	
}
