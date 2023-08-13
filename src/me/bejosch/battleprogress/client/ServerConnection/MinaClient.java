package me.bejosch.battleprogress.client.ServerConnection;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import me.bejosch.battleprogress.client.Data.ConnectionData;
import me.bejosch.battleprogress.client.Data.MenuData;
import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Data.Game.ResearchData;
import me.bejosch.battleprogress.client.Data.Game.UnitData;
import me.bejosch.battleprogress.client.Enum.AnimationType;
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
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.ClientPlayer;
import me.bejosch.battleprogress.client.Objects.FriendRequest;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_GameChatNotification;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_GamePing;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_ShowMainMenu;
import me.bejosch.battleprogress.client.Objects.Chat.ChatMessage;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Attack;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Build;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_HealAndRepair;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Move;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Produce;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Remove;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Upgrade;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
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

public class MinaClient {
	
	private static NioSocketConnector connector;
	private static IoSession session;
	
	private static Timer pingCalcTimer;
	
	public static boolean connectToServer(String ip, int port) {
		
		boolean valid = checkIpAndPort(ip, port);
		if(valid) {
			
			try {
				
				connector = new NioSocketConnector();
				connector.setConnectTimeoutMillis(ConnectionData.TIMEOUT_DELAY);
				connector.setHandler(new MinaClientEvents());
	//			connector.getFilterChain().addLast("logger", new LoggingFilter()); //USED FOR DEBUGGING IN CONSOLE
				TextLineCodecFactory factory = new TextLineCodecFactory(Charset.forName(ConnectionData.ENCODING));
				factory.setDecoderMaxLineLength(ConnectionData.BUFFER_SIZE);
				factory.setEncoderMaxLineLength(ConnectionData.BUFFER_SIZE);
				IoFilter codec = new ProtocolCodecFilter(factory);
				connector.getFilterChain().addLast("codec", codec);
				
				int tryCount = 1;
				for(;;) {
					ConsoleOutput.printMessageInConsole_NOLN("("+tryCount+"/"+ConnectionData.MAX_CONNECTION_TRIES+") Try conneting to '"+ip+":"+port+"'... ", true);
				    try {
				        ConnectFuture future = connector.connect(new InetSocketAddress(ip, port));
				        future.awaitUninterruptibly();
				        session = future.getSession();
				        ConsoleOutput.printMessageInConsole("connected!", false);
				        startPingCalculationTimer();
				        return true;
				    } catch (RuntimeIoException e) {
				    	ConsoleOutput.printMessageInConsole("failed!", false);
				        Thread.sleep(ConnectionData.DELAY_BETWEN_TRIES);
				    }
				    if(tryCount == ConnectionData.MAX_CONNECTION_TRIES) {
				    	//ABORT
				    	ConsoleOutput.printMessageInConsole("Aborting connection after "+ConnectionData.MAX_CONNECTION_TRIES+" failed attempts!", true);
				    	return false;
				    }
				    tryCount++;
				}
				
			} catch (InterruptedException error) {
				ConsoleOutput.printMessageInConsole("Connecting to server got interrupted! Can't reach server...", true);
				return false;
			}
			
		}else {
			ConsoleOutput.printMessageInConsole("Invalid port or ipAddress given! ("+ip+":"+port+") Can't reach server...", true);
			return false;
		}
		
	}
	
	private static boolean checkIpAndPort(String ipAdress, int portInput) {
		
		//ADDRESS
		try {
			InetAddress ipAddress = InetAddress.getByName(ipAdress);
			String ipAddressString = ipAddress.getHostAddress();
			ConsoleOutput.printMessageInConsole("Resolved Address "+ipAdress+" to "+ipAddressString, true);
		}catch (UnknownHostException error) {
			return false;
		}
		
		//PORT
		if(portInput > 0 && portInput <= 999999) {
			int port = portInput;
			ConsoleOutput.printMessageInConsole("Port "+port+" is valid", true);
		}else {
			return false;
		}
		
		return true;
		
	}
	
	public static void sendData(int signal, String data) {
		
		long id = ConnectionData.getNewPacketId();
		session.write(signal+"-"+id+"-"+data);
		
		if(signal != 997) {
			ConnectionData.sendedDataList.add(signal+"-"+id+"-"+data);
		}
		
	}
	
	public static void handlePackage(int signal, int id, String data) {
		
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
					MinaClient.sendData(120, ""+player.getID());
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
			MinaClient.sendData(135, "Get FriendRequests");
			
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
			GameHandler.createBuilding(playerID, buildingName_HQ, X1, Y1);
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
			//UPDATE LAST RECEIVE TIME
			Label.updatePingReceive(System.currentTimeMillis());
			break;
//========================================================================
		//ClientPingRequest
		case 998:
			MinaClient.sendData(998, data);
			break;
		}
		
	}
	
	public static void handleConnectionLoss() {
		
		session = null;
		if(pingCalcTimer != null) {
			pingCalcTimer.cancel();
			pingCalcTimer = null;
		}
		
		TimeManager.tickList.clear(); //ALL TICKS REMOVED
		
		StandardData.spielStatus = SpielStatus.Menu;
		
		OnTopWindowHandler.openOTW(new OnTopWindow_InfoMessage("Lost connection", "The connection to the server timed out!", "For more information check out our discord...", "Please try again later", true));
		
	}
	
	public static void startPingCalculationTimer() {
		
		if(pingCalcTimer != null) {
			pingCalcTimer.cancel();
			pingCalcTimer = null;
		}
		
		pingCalcTimer = new Timer();
		pingCalcTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				
				Label.updatePingSent(System.currentTimeMillis());
				sendData(997, "This is a longer ping check packet, which is used to calculate this ping in ms!");
				
			}
		}, 0, 1000); //EVERY SEK
		
	}
	
	public static void disconnectFromServer() {
		
		if(session != null) {
			session.closeNow();
		}
		
	}
	
}
