package me.bejosch.battleprogress.client.ServerConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.MenuData;
import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Data.Game.ResearchData;
import me.bejosch.battleprogress.client.Data.Game.UnitData;
import me.bejosch.battleprogress.client.Enum.AnimationType;
import me.bejosch.battleprogress.client.Enum.ImportanceType;
import me.bejosch.battleprogress.client.Enum.PlayerRanking;
import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Enum.SpielStatus;
import me.bejosch.battleprogress.client.Enum.UpgradeType;
import me.bejosch.battleprogress.client.Game.TimeManager;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Game.Handler.Game_RoundHandler;
import me.bejosch.battleprogress.client.Handler.ChatHandler;
import me.bejosch.battleprogress.client.Handler.ClientPlayerHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Main.BattleProgress_StartMain_Client;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.ClientPlayer;
import me.bejosch.battleprogress.client.Objects.FriendRequest;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_GameChatNotification;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_GamePing;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_ShowMainMenu;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Headquarter;
import me.bejosch.battleprogress.client.Objects.Chat.ChatMessage;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Attack;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Build;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_HealAndRepair;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Move;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Produce;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Remove;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Upgrade;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage_Located;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.FriendAdd.OnTopWindow_FriendAdd;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.GameAccept.OnTopWindow_GameAccept;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.GroupInvitation.OnTopWindow_GroupInvitation;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.InfoMessage.OnTopWindow_InfoMessage;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.QueueWaiting.OnTopWindow_QueueWaiting;
import me.bejosch.battleprogress.client.Objects.Research.UpgradeDataContainer;
import me.bejosch.battleprogress.client.Window.Label;
import me.bejosch.battleprogress.client.Window.Animations.AnimationDisplay;
import me.bejosch.battleprogress.client.Window.TextAreas.TextAreas;
import me.bejosch.battleprogress.client.Window.TextFields.TextFields;

public class ServerConnection {
	
	public static Socket socket;
	public static InetAddress ipAddress;
	public static String ipAddressString;
	public static int port;
	public static int PackageIdLength = 8;
	
	public static List<String> sentDataList = new ArrayList<String>();
	
	public static Timer connectingTimer = null;
	
	public static boolean ServerAdresseResolved = false;
	public static boolean VerbundenZumServer = false;
	public static boolean socketCreated = false;
	public static boolean serverClosedConnection = false;
	public static int MaxVerbindungsVersuche = 3;
	public static int VerbindungsVersuche = 0;
	public static int connectingTryDelay = 0;
	
	public static Timer timeoutTimer = null;
	public static int timeoutDelayInSec = 35;
	public static int timeoutStatus = timeoutDelayInSec;
	
//==========================================================================================================
	/**
	 * Start to reach many times for a ServerConnection
	 * @param maxTrys - int - The count of the trys witch will be done
	 */
	public static void startToReachForAServerConnection() {
		
		if(connectingTimer != null) {
			connectingTimer.cancel();
			connectingTimer = null;
		}
		
		if(ServerAdresseResolved == false) {
			ConsoleOutput.printMessageInConsole("Unknown Address ("+ipAddressString+") or Port ("+port+")", true);
			return;
		}
		
		connectingTimer = new Timer();
		connectingTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				
				if(VerbindungsVersuche >= MaxVerbindungsVersuche) {
					ConsoleOutput.printMessageInConsole("No connection established after "+VerbindungsVersuche+" tries!", true);
					this.cancel();
				}else {
					if(connectingTryDelay <= 0) {
						connectingTryDelay = 100*2; // 2 SEK DELAY
						VerbindungsVersuche++;
						if(socketCreated == true) {
							//WENN VERBUNDEN WECHSEL ZUM MENU MIT DELAY
							connectingTimer.cancel();
							connectingTimer = null;
							serverClosedConnection = false;
							//START CONNECTION CHECK TIMER
							startConnectionCheckTimer(); //Timer which checks every X sek for connection - timeoutChecker
							//WENN VERBUNDEN SEND DATA
							sendData(100, getNewPacketId(), "Confirm Connection please!");
							//WENN VERBUNDEN START LISTENER
							startRecieveScanner();
							// !!! LOGIN SHOW BY THE CONNECT RECEIVE PART !!!
						}else {
							//WENN NICHT VERSUCHS WEITER
							establishServerConnection("89.247.60.198", port);
						}
					}else {
						connectingTryDelay--;
					}
					
				}
				
			}
		}, 100, 10);
		
	}
	
//==========================================================================================================
	/**
	 * Trys to initialise a server connection... (resolve ServerAddress)
	 * @param ipAdress - InetAddress - The InetAddress of the Server should be connected
	 * @param port - int - The Port of thze Server should be connected
	 */
	public static void initialiseServerConnection(String ipAdress, int portInput) {
		
		//ADDRESS
		try {
			ipAddress = InetAddress.getByName(ipAdress);
			ipAddressString = ipAddress.getHostAddress();
			ConsoleOutput.printMessageInConsole("Resolved Address "+ipAdress+" to "+ipAddress.getHostAddress(), true);
			ServerAdresseResolved = true;
		}catch (UnknownHostException error) {
			ipAddress = null;
			ipAddressString = null;
			ServerAdresseResolved = false;
		}
		
		//PORT
		if(portInput > 0 && portInput <= 999999) {
			port = portInput;
			//WENN BEI ADDRESS TRUE BLEIBT ES TRUE, BEI FALSE BLEIBT ES FALSE
		}else {
			port = -1;
			ServerAdresseResolved = false;
		}
		
//		establishServerConnection(ipAdress, port);
		
	}
	
//==========================================================================================================
	/**
	 * Trys to establish a server connection... (Create Server socket)
	 * @param ipAdress - InetAddress - The InetAddress of the Server should be connected
	 * @param port - int  - The Port of thze Server should be connected
	 */
	public static void establishServerConnection(String ipAdress, int port) {
		
		if(ServerAdresseResolved == true) {
			
			try {
				
				ConsoleOutput.printMessageInConsole("Connecting to "+ipAdress+":"+port+"... ", true);
				//socket = ( (SSLSocketFactory) SSLSocketFactory.getDefault() ).createSocket(ipAdress, port);
				socket = BattleProgress_StartMain_Client.getSSLContext().getSocketFactory().createSocket(ipAdress, port);
				socket.setKeepAlive(true);
				socket.setSoTimeout(0);
				socketCreated = true;
				sendData(0, getNewPacketId(), "Socket initialised!");
				return;
				
			}catch (SecurityException e) {
				e.printStackTrace();
				ConsoleOutput.printMessageInConsole("Your connection to the server has been called 'unsave'! You are using a wrong identification key!", true);
				return;
			}catch (ConnectException e) {
				ConsoleOutput.printMessageInConsole("Your connection timed out or was refused!", true);
//				e.printStackTrace();
				return; //CONNECTION TIMEOUT
			}catch (IOException e) {
				e.printStackTrace();
			}
			
		}else {
			ConsoleOutput.printMessageInConsole("Unknown or invalid server address or port: "+ipAdress+":"+port, true);
			return;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Read the Data from the server
	 * @param signal - int - The signal of the data
	 * @param id - int - The id of the data
	 * @param data - String - The real data
	 */
	public static void receiveData(int signal, int id, String data) {
		
		int[] signalBlackList = {997};
		List<Integer> noConsoleOutput = new ArrayList<>();
		for(int i : signalBlackList) {noConsoleOutput.add(i);}
		if(noConsoleOutput.contains(signal) == false) {
			ConsoleOutput.printMessageInConsole("Received Packet: [signal:"+signal+" ; id:"+id+" ; data:"+data+"]", false);
		}
		
		String[] content = null;
		if(data != null) {
			content = data.split(";");
		}
		
		switch(signal) {
//========================================================================
		//CONNECT
		case 100:
			VerbundenZumServer = true;
			ConsoleOutput.printMessageInConsole("Connected to server!", true);
			break;
//========================================================================
		//Unit container
		case 110:
			//Name, K�rzel, Kosten, Leben, EnergieVerbrauch, EnergieProduktion, MaterialProduktion, Schaden, ViewDistance, MoveDistance, ActionDistance, Heal, Repair, Research
			
			String unitName = content[0];
			String unitKürzel = content[1];
			int unitKosten = Integer.parseInt(content[2]);
			int unitLeben = Integer.parseInt(content[3]);
			int unitEnergieVerbrauch = Integer.parseInt(content[4]);
			int unitEnergieProduktion = Integer.parseInt(content[5]);
			int unitMaterialProduktion = Integer.parseInt(content[6]);
			int unitSchaden = Integer.parseInt(content[7]);
			int unitViewDistance = Integer.parseInt(content[8]);
			int unitMoveDistance = Integer.parseInt(content[9]);
			int unitActionDistance = Integer.parseInt(content[10]);
			int unitHeal = Integer.parseInt(content[11]);
			int unitRepair = Integer.parseInt(content[12]);
			int unitResearch = Integer.parseInt(content[13]);
			
			UnitStatsContainer container = new UnitStatsContainer(unitName, unitKürzel, unitKosten, unitLeben, unitEnergieVerbrauch, unitEnergieProduktion, unitMaterialProduktion, unitSchaden, unitViewDistance, unitMoveDistance, unitActionDistance, unitHeal, unitRepair, unitResearch);
			UnitData.units.add(container);
			
			break;
//========================================================================
		//UpgradeDataContainer
		case 111:
			//UpgradeType, cost, effectValue
			
			UpgradeType type = UpgradeType.valueOf(content[0]);
			int cost = Integer.parseInt(content[1]);
			int effectValue = Integer.parseInt(content[2]);
			
			UpgradeDataContainer dataContainer = new UpgradeDataContainer(type, cost, effectValue);
			ResearchData.upgradeDataContainer.add(dataContainer);
			
			break;
//========================================================================
		//Request Player DATA answer
		case 120:
			//Player Data
			
			int playerDataID = Integer.parseInt(content[0]);
			for(ClientPlayer clientPlayer : ProfilData.playerWaitingforDataReceive) {
				if(clientPlayer.getID() == playerDataID) {
					clientPlayer.acceptLoadedPlayerData(content);
					break;
				}
			}
			break;
//========================================================================
		//Request Player STATS answer
		case 121:
			//Player Stats
			
			//TODO
//			int playerDataID = Integer.parseInt(content[0]);
//			for(ClientPlayer clientPlayer : ProfilData.playerWaitingforDataReceive) {
//				if(clientPlayer.getID() == playerDataID) {
//					clientPlayer.acceptLoadedPlayerData(content);
//					break;
//				}
//			}
			
			break;
//========================================================================
		//Requested Player DATA update from server
		case 125:
			//Player ID
			int playerUpdateID = Integer.parseInt(data);
			for(ClientPlayer player : ProfilData.allCurrentClientPlayer) {
				if(player.getID() == playerUpdateID) {
					ProfilData.playerWaitingforDataReceive.add(player);
					ServerConnection.sendData(120, ServerConnection.getNewPacketId(), ""+player.getID());
					break;
				}
			}
			
			break;
//========================================================================
		//Update PlayerActivity
		case 126:
			//Player ID ; PlayerActivity
			int playerActivityUpdateID = Integer.parseInt(content[0]);
			String playerActivity = content[1];
			for(ClientPlayer player : ProfilData.allCurrentClientPlayer) {
				if(player.getID() == playerActivityUpdateID) {
					player.setCurrentActivity(playerActivity);
					break;
				}
			}
			
			break;
//========================================================================
		//Friend came online
		case 130:
			int friendID_1 = Integer.parseInt(data);
			for(ClientPlayer player : ProfilData.friendList_offline) {
				if(player.getID() == friendID_1) {
					player.setOnlineMin(0);
					player.setCurrentActivity("Online");
					ProfilData.friendList_offline.remove(player);
					break;
				}
			}
			ProfilData.friendList_online.add(ClientPlayerHandler.getNewClientPlayer(friendID_1));
			break;
//========================================================================
		//Friend gets offline
		case 131:
			int friendID_2 = Integer.parseInt(data);
			for(ClientPlayer player : ProfilData.friendList_online) {
				if(player.getID() == friendID_2) {
					player.setOnlineMin(-1);
					player.setCurrentActivity("Offline");
					ProfilData.friendList_online.remove(player);
					break;
				}
			}
			ProfilData.friendList_offline.add(ClientPlayerHandler.getNewClientPlayer(friendID_2));
			break;
//========================================================================
		//Every friendrequest
		case 135:
			// PlayerID ; PlayerName
			int friendRequestID = Integer.parseInt(content[0]);
			String friendRequestName = content[1];
			ProfilData.receivedFriendRequests.add(new FriendRequest(friendRequestID, friendRequestName));
			break;
//========================================================================
		//Result of the FriendAdd request
		case 136:
			// Success ; Result
			boolean requestSuccessfull = Boolean.parseBoolean(content[0]);
			String requestResult = content[1];
			if(requestSuccessfull == true) {
				TextFields.textField_friendAdd.setText("");
			}
			((OnTopWindow_FriendAdd) OnTopWindowData.onTopWindow).nameErrorMessage = requestResult;
			break;
//========================================================================
		//Friend Removed
		case 139:
			// PlayerID
			int friendRemovedID = Integer.parseInt(data);
			for(ClientPlayer friend : ProfilData.friendList_online) {
				if(friend.getID() == friendRemovedID) {
					ProfilData.friendList_online.remove(friend);
					break;
				}
			}
			for(ClientPlayer friend : ProfilData.friendList_offline) {
				if(friend.getID() == friendRemovedID) {
					ProfilData.friendList_offline.remove(friend);
					break;
				}
			}
			break;
//========================================================================
		//Chat Message
		case 140:
			int senderID = Integer.parseInt(content[0]);
			String message = content[1];
			ChatHandler.getChatHistoy(senderID).addMessage(new ChatMessage(senderID, message));
			break;
//========================================================================
		//Success Login
		case 200:
			//ID ; Name ; Level ; XP ; Online Min (-1 wenn offline) ; PIN ; BIN ; NCN ; SN ; RANKING ; RP ; currentActivity
			ProfilData.thisClient = ClientPlayerHandler.getNewClientPlayer(Integer.parseInt(content[0]), content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
					Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
					Integer.parseInt(content[7]), Integer.parseInt(content[8]),
					PlayerRanking.valueOf(content[9]), Integer.parseInt(content[10]), content[11]);
			OnTopWindowData.login_message = content[12];
			
			ProfilData.successlogin = true;
			OnTopWindowHandler.closeOTW();
			ConsoleOutput.printMessageInConsole("Logged in as "+ProfilData.thisClient.getID()+" - "+ProfilData.thisClient.getName()+"!", true);
			
			//GET FRIEND REQUESTs
			ProfilData.receivedFriendRequests.clear();
			ServerConnection.sendData(135, ServerConnection.getNewPacketId(), "Get FriendRequests");
			
			new Animation_ShowMainMenu();
			
			break;
//========================================================================
		//Error Login
		case 201:
			String message_2 = content[0];
			OnTopWindowData.login_message = message_2;
			break;
//========================================================================
		//Group Invite
		case 300:
			int inviterPlayerID = Integer.parseInt(data);
			ClientPlayer inviter = ClientPlayerHandler.getNewClientPlayer(inviterPlayerID);
			if(OnTopWindowData.onTopWindow != null) {
				if(OnTopWindowData.onTopWindow instanceof OnTopWindow_QueueWaiting || OnTopWindowData.onTopWindow instanceof OnTopWindow_GameAccept) {
					//NOT OVERWRITEABLE, SO CANT OPEN
				}else {
					OnTopWindowHandler.openOTW(new OnTopWindow_GroupInvitation(inviter));
				}
			}else {
				OnTopWindowHandler.openOTW(new OnTopWindow_GroupInvitation(inviter));
			}
			break;
//========================================================================
		//Group Join - JEDER DER ZUR ZEIT IN DER GRUPPE IST WIRD GESENDET
		case 301:
			int otherGroupPlayerID = Integer.parseInt(data);
			if(otherGroupPlayerID == ProfilData.thisClient.getID()) {
				return; //IS THIS CLIENT
			}
			if(ProfilData.otherGroupClient != null && otherGroupPlayerID == ProfilData.otherGroupClient.getID()) {
				return; //ALLREADY KNOWN
			}
			//TODO CHECK IF ALLREADY KNOWN IN CUST GAME etc
			//TODO JOIN WEITERLEITEN AN CUSTOM GAME HANDLER IF CUSTOM GAME IS ACTIV
			ClientPlayer otherGroupPlayer = ClientPlayerHandler.getNewClientPlayer(otherGroupPlayerID);
			ProfilData.otherGroupClient = otherGroupPlayer;
			ProfilData.sendGroupInvite.remove(otherGroupPlayer);
			MenuData.friendListOpened = false;
			MenuData.gameHistoryOpened = false;
			break;
//========================================================================
		//Group Invite Decline
		case 302:
//			int declinePlayerID = Integer.parseInt(data);
//			ClientPlayer declinePlayer = ClientPlayerHandler.getNewClientPlayer(declinePlayerID);
//			OnTopWindowHandler.openOTW(new OnTopWindow_InfoMessage("Group invitation declined!", "", declinePlayer.getName()+" has declined your group invitation!", "", false));
			break;
//========================================================================
		//Group Client Kick (ONLY IN CUSTOM GAME)
		case 303:
			int kickedPlayerID = Integer.parseInt(data);
//			ClientPlayer kickedPlayer = ClientPlayerHandler.getNewClientPlayer(kickedPlayerID);
			//TODO KICK WEITERLEITEN AN CUSTOM GAME HANDLER
			if(kickedPlayerID == ProfilData.thisClient.getID()) {
				//THIS CLIENT GOT KICKED
				OnTopWindowHandler.openOTW(new OnTopWindow_InfoMessage("You has been kicked!", "", "You were removed from the group by the host!", "", false));
			}
			break;
//========================================================================
		//Group Client Leave
		case 304:
//			int leavePlayerID = Integer.parseInt(data);
//			ClientPlayer leavedPlayer = ClientPlayerHandler.getNewClientPlayer(leavePlayerID);
			//TODO LEAVE WEITERLEITEN AN CUSTOM GAME HANDLER IF CUSTOM GAME IS ACTIV
			if(ProfilData.otherGroupClient != null) {
				ProfilData.otherGroupClient = null;
			}
			break;
//========================================================================
		//Join Queue
		case 400:
			// Type ; WaitedSeconds
			SpielModus queueType = SpielModus.valueOf(content[0]);
			int waitedSeconds = Integer.parseInt(content[1]);
			OnTopWindowHandler.openOTW(new OnTopWindow_QueueWaiting(queueType, waitedSeconds));
			break;
//========================================================================
		//Leave Queue
		case 401:
			if(OnTopWindowData.onTopWindow != null) {
				if(OnTopWindowData.onTopWindow instanceof OnTopWindow_QueueWaiting) {
					OnTopWindowHandler.closeOTW();
				}
			}
			break;
//========================================================================
		//Game Accept Request
		case 405:
			int gameID = Integer.parseInt(data);
			OnTopWindowHandler.openOTW(new OnTopWindow_GameAccept(gameID));
			break;
//========================================================================
		//ExecuteTask - ATTACK
		case 600:
			// playerID ; startX ; startY ; goalX ; goalY ; Count
			int playerID_1 = Integer.parseInt(content[0]);
			int startX_1 = Integer.parseInt(content[1]);
			int startY_1 = Integer.parseInt(content[2]);
			int goalX_1 = Integer.parseInt(content[3]);
			int goalY_1 = Integer.parseInt(content[4]);
			int count_1 = Integer.parseInt(content[5]);
			new ExecuteTask_Attack(playerID_1, count_1, new FieldCoordinates(startX_1, startY_1), new FieldCoordinates(goalX_1, goalY_1));
			Game_RoundHandler.receivedATask();
			break;
//========================================================================
		//ExecuteTask - HEAL / REPAIR
		case 601:
			// playerID ; startX ; startY ; goalX ; goalY ; Count
			int playerID_2 = Integer.parseInt(content[0]);
			int startX_2 = Integer.parseInt(content[1]);
			int startY_2 = Integer.parseInt(content[2]);
			int goalX_2 = Integer.parseInt(content[3]);
			int goalY_2 = Integer.parseInt(content[4]);
			int count_2 = Integer.parseInt(content[5]);
			new ExecuteTask_HealAndRepair(playerID_2, count_2, new FieldCoordinates(startX_2, startY_2), new FieldCoordinates(goalX_2, goalY_2));
			Game_RoundHandler.receivedATask();
			break;
//========================================================================
		//ExecuteTask - BUILD
		case 602:
			// playerID ; buildingName ; goalX ; goalY
			int playerID_3 = Integer.parseInt(content[0]);
			String buildingName = content[1];
			int goalX_3 = Integer.parseInt(content[2]);
			int goalY_3 = Integer.parseInt(content[3]);
			new ExecuteTask_Build(buildingName, playerID_3, new FieldCoordinates(goalX_3, goalY_3));
			Game_RoundHandler.receivedATask();
			break;
//========================================================================
		//ExecuteTask - PRODUCE
		case 603:
			// playerID ; troupName ; startX ; startY ; goalX ; goalY
			int playerID_4 = Integer.parseInt(content[0]);
			String troupName = content[1];
			int startX_4 = Integer.parseInt(content[2]);
			int startY_4 = Integer.parseInt(content[3]);
			int goalX_4 = Integer.parseInt(content[4]);
			int goalY_4 = Integer.parseInt(content[5]);
			new ExecuteTask_Produce(troupName, playerID_4, new FieldCoordinates(startX_4, startY_4), new FieldCoordinates(goalX_4, goalY_4));
			Game_RoundHandler.receivedATask();
			break;
//========================================================================
		//ExecuteTask - MOVE
		case 604:
			// playerID ; startX ; startY ; goalX ; goalY
			int playerID_5 = Integer.parseInt(content[0]);
			int startX_5 = Integer.parseInt(content[1]);
			int startY_5 = Integer.parseInt(content[2]);
			int goalX_5 = Integer.parseInt(content[3]);
			int goalY_5 = Integer.parseInt(content[4]);
			new ExecuteTask_Move(playerID_5, new FieldCoordinates(startX_5, startY_5), new FieldCoordinates(goalX_5, goalY_5));
			Game_RoundHandler.receivedATask();
			break;
//========================================================================
		//ExecuteTask - REMOVE
		case 605:
			// playerID ; goalX ; goalY
			int playerID_6 = Integer.parseInt(content[0]);
			int goalX_6 = Integer.parseInt(content[1]);
			int goalY_6 = Integer.parseInt(content[2]);
			new ExecuteTask_Remove(playerID_6, new FieldCoordinates(goalX_6, goalY_6));
			Game_RoundHandler.receivedATask();
			break;
//========================================================================
		//ExecuteTask - UPGRADE
		case 606:
			// playerID ; troupName ; fromX ; fromY ; goalX ; goalY
			int playerID_7 = Integer.parseInt(content[0]);
			String troupName_7 = content[1];
			int startX_7 = Integer.parseInt(content[2]);
			int startY_7 = Integer.parseInt(content[3]);
			int goalX_7 = Integer.parseInt(content[4]);
			int goalY_7 = Integer.parseInt(content[5]);
			new ExecuteTask_Upgrade(playerID_7, troupName_7, new FieldCoordinates(startX_7, startY_7), new FieldCoordinates(goalX_7, goalY_7));
			Game_RoundHandler.receivedATask();
			break;
//========================================================================
		//Game Start
		case 620:
			// GameID ; GameModus ; PID1 ; PID2 ; PID3 ; PID4 ; MapName ; MapData
			int startGameID = Integer.parseInt(content[0]);
			SpielModus startGameMode = SpielModus.valueOf(content[1]);
			int startPlayerID_1 = Integer.parseInt(content[2]);
			int startPlayerID_2 = Integer.parseInt(content[3]);
			int startPlayerID_3 = Integer.parseInt(content[4]);
			int startPlayerID_4 = Integer.parseInt(content[5]);
			String startMapName = content[6];
			String startMapData = content[7];
			GameHandler.startGame(startGameID, startGameMode, startPlayerID_1, startPlayerID_2, startPlayerID_3, startPlayerID_4, startMapName, startMapData);
			break;
//========================================================================
		//Create HeadQuarter
		case 621:
			// PlayerID ; buildingName ; X ; Y
			int playerID = Integer.parseInt(content[0]);
			String buildingName_HQ = content[1];
			int X1 = Integer.parseInt(content[2]);
			int Y1 = Integer.parseInt(content[3]);
			createBuilding(playerID, buildingName_HQ, X1, Y1);
			break;
//========================================================================
		//Client is ready for this round
		case 650:
			int playerID_8 = Integer.parseInt(content[0]);
			Game_RoundHandler.setPlayerReady(playerID_8);
			break;
//========================================================================
		//Client is UN ready for this round
		case 651:
			int playerID_9 = Integer.parseInt(content[0]);
			Game_RoundHandler.setPlayerNotReady(playerID_9);
			break;
//========================================================================
		//All clients are ready
		case 652:
			Game_RoundHandler.roundEnd();
			break;
//========================================================================
		//All tasks tranfered
		case 653:
			Game_RoundHandler.taskTransferComplete();
			break;
//========================================================================
		//All task executed
		case 654:
			Game_RoundHandler.startRoundMaterialUpdate(); //AFTER TASK... SHOW ENERGY AND MATERIAL PRODUCTION... THEN NEXT ROUND
			break;
//========================================================================
		//Chat Message
		case 660:
			// messageNumber ; message
			int messageNumber = Integer.parseInt(content[0]);
			String chatMessage = content[1];
			GameData.chatList.put(messageNumber, chatMessage);
			
			int runs = 1; int maxRuns = 100; int toMuchRuns = GameData.chatList.keySet().size() - maxRuns;
			String totalChat = "";
			for(int key : GameData.chatList.keySet()) {
				if(runs >= toMuchRuns) {
					totalChat = totalChat+" "+GameData.chatList.get(key)+"\n";
				}
				runs++;
			}
			TextAreas.textArea_Chat.setText(totalChat);
			TextAreas.textArea_Chat.setCaretPosition(totalChat.length()-1);
			
			//START NOTIFY ANIMATION
			if(GameData.gameIsRunning == true && GameData.chatIsShown == false) {
				Animation_GameChatNotification chatAnimation = (Animation_GameChatNotification) AnimationDisplay.getRunningAnimationOfType(AnimationType.Game_ChatNotification);
				if(chatAnimation == null) {
					new Animation_GameChatNotification();
				}else {
					chatAnimation.newMessagesCount++;
				}
				//TODO NOTIFICATION SOUND
			}
			break;
//========================================================================
		//Field Ping
		case 661:
			// PlayerID, x, y
			int playerPingID = Integer.parseInt(content[0]);
			int x = Integer.parseInt(content[1]);
			int y = Integer.parseInt(content[2]);
			
			if(GameHandler.checkPlayerIDForAllied(ProfilData.thisClient.getID(), playerPingID)) {
				//IS AN ALLIED PING
				new Animation_GamePing(GameData.gameMap_FieldList[x][y]);
			}
			break;
//========================================================================
//		//Switch FieldType
//		case 802: TODO
//			// X ; Y ; fieldTypeSignal
//			int X3 = Integer.parseInt(content[0]);
//			int Y3 = Integer.parseInt(content[1]);
//			FieldType fieldType = FieldType.getFieldTypeFromSignal(content[2]);
//			
//			if(fieldType == FieldType.Ressource) {
//				GameData.gameMap_FieldList[X3][Y3] = new Field_Ressource(X3, Y3);
//			}else {
//				GameData.gameMap_FieldList[X3][Y3].changeType(fieldType);
//			}
//			
//			break;
//========================================================================
		//CheckConnection request
		case 997:
			//CONNECTION STILL THERE - RESETT TIMEOUT
			timeoutStatus = timeoutDelayInSec;
			//UPDATE LAST RECEIVE TIME
			Label.updateServerAnswerDuration(System.currentTimeMillis());
			break;
//========================================================================
		//ClientPingRequest
		case 998:
			sendData(998, getNewPacketId(), data);
			break;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Starts the scanner witch checks all data sent to this client
	 */
	public static void startRecieveScanner() {
		
		BufferedReader input;
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (SocketException error) {
			ConsoleOutput.printMessageInConsole("Init inputStream found a closedSocket! Serverconnection could not be fully established!", true);
			return;
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		
		while(true) {
			
			try {
				
				String data = input.readLine();
				int signal = Integer.parseInt(data.substring(0, 3).trim());
				int id = Integer.parseInt(data.substring(4, PackageIdLength+4).trim());
				String answer = null;
				if(data.length() > PackageIdLength+1+4) {
					answer = data.substring(PackageIdLength+1+4, data.length()).trim();
				}
				
				receiveData(signal, id, answer);
				
			} catch (IOException e) {
				//e.printStackTrace();
				
				serverConnectionLost(); //CONNECTION LOST !!!!!
				return;
				
			}catch(NullPointerException error) {
				//IGNORE DATA WITH WRONG SYNTAX
			}
			
		}
		
	}
	
//==========================================================================================================
	/**
	 * Creates a Building
	 * @param playerID - {@link Integer} - The id of the player who build it
	 * @param buildingName - String - The name of the building, is used for identification which building will be created 
	 * @param X - int - The X-Coordinate of the field
	 * @param Y - int - The Y-Coordinate of the field
	 */
	public static void createBuilding(int playerID, String buildingName, int X, int Y) {
		
		Field targetField = GameData.gameMap_FieldList[X][Y];
		if(targetField == null) { 
			ConsoleOutput.printMessageInConsole("A createBuilding packet found no targetField for the coordinates '"+X+":"+Y+"'!", true);
			return; 
		}
		
		new InfoMessage_Located(buildingName+" has been build", ImportanceType.NORMAL, targetField.X, targetField.Y, true);
		
		switch (buildingName) {
		case "Headquarter":
			new Building_Headquarter(playerID, targetField);
			break;
		default:
			ConsoleOutput.printMessageInConsole("A createBuilding packet found no building for the buildingName '"+buildingName+"'!", true);
			break;
		}
		
		GameHandler.updateViewAndBuildArea();
		
	}
	
//==========================================================================================================
	/**
	 * Send Data to the Server (with signal and id)
	 * @param signal - int - The signal of the data
	 * @param id - int - The id of the data
	 * @param data - String - The Data which will be sended
	 */
	public static void sendData(int signal, int id, String data) {
		
		try {
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			
			output.println(signal+"-"+id+"-"+data);
			
			if(signal != 997) {
				sentDataList.add(signal+"-"+id+"-"+data);
			}	
			
		} catch (IOException | NullPointerException error) {
			error.printStackTrace();
		}
		
		if(VerbundenZumServer == false) {
			//ConsoleOutput.printMessageInConsole("The ServerConnection isn't confirmed! It could be, that no connection has been established!", true);
		}
		
	}
	
//==========================================================================================================
	/**
	 * Starts a repeating timer which checks every delay with a data if the server could be reached
	 */
	public static void startConnectionCheckTimer() {
		
		if(timeoutTimer != null) {
			timeoutTimer.cancel();
			timeoutTimer = null;
		}
		
		timeoutTimer = new Timer();
		timeoutTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				
				if(timeoutStatus <= 0) {
					//TIMEOUT!!!
					timeoutTimer.cancel();
					timeoutTimer = null;
					timeoutStatus = timeoutDelayInSec;
					ConsoleOutput.printMessageInConsole("Server connection timed out!", true);
					serverConnectionLost();
				}else if(timeoutStatus % 5 == 0) { //JEDE 3te SEK
					//CHECK IF CONNECTION STILL THERE
					//UPDATE LAST SEND TIME
					Label.updateDataSentTime(System.currentTimeMillis());
					sendData(997, getNewPacketId(), "This is a longer ping check packet, which is used to calculate this ping in ms!");
					timeoutStatus--;
				}else {
					//WAIT TIME
					timeoutStatus--;
				}
				
			}
		}, 0, 1000); //EVERY SEK
		
	}
	
//==========================================================================================================
	/**
	 * Called if the connection to the server is lost
	 */
	public static void serverConnectionLost() {
		
		//SERVER HAS BEEN CLOSED
		try {
			if(VerbundenZumServer == true) {
				ConsoleOutput.printMessageInConsole("Lost connection to server!", true);
				try{
					socket.close();
				}catch(NullPointerException error) {
					return;
				}
				socket = null;
				if(connectingTimer != null) {
					connectingTimer.cancel();
					connectingTimer = null;
				}
				VerbindungsVersuche = 0;
				timeoutStatus = timeoutDelayInSec;
				socketCreated = false;
				VerbundenZumServer = false;
				serverClosedConnection = true;
				
				TimeManager.tickList.clear(); //ALL TICKS REMOVED
				
				StandardData.spielStatus = SpielStatus.LoadingScreen;
				
				new OnTopWindow_InfoMessage("Lost connection", "The connection to the server timed out!", "For more information check out our discord...", "Please try again later", true);
				
			}else {
				//WAR NOCH NIE VERBUNDEN ALSO AUCH NICHT VERSUCHEN WIEDERHERZUSTELLEN
			}
		}catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
//==========================================================================================================
	/**
	 * Gives back a new PacketId
	 * @return int - The new PacketId
	 */
	public static int getNewPacketId() {
		
		//ALL NUMBERS BETWEEN MIN AND MAX COUNT
		return new Random().nextInt( (getMaxPacketIdCount()-getMinPacketIdCount()) )+getMinPacketIdCount();
		
	}
	
//==========================================================================================================
	/**
	 * Gives back the min PacketId count
	 * @return int - The min PacketId count
	 */
	public static int getMinPacketIdCount() {
		
		int number = 1;
		for(int i = PackageIdLength ; i > 1 ; i--) {
			number = number*10;
		}
		return number;
		
	}
	
//==========================================================================================================
	/**
	 * Gives back the max PacketId count
	 * @return int - The max PacketId count
	 */
	public static int getMaxPacketIdCount() {
		
		int number = 1;
		for(int i = PackageIdLength+1 ; i > 1 ; i--) {
			number = number*10;
		}
		return number-1;
		
	}
	
}
