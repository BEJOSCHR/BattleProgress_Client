package me.bejosch.battleprogress.client.Objects;

import java.awt.Color;
import java.awt.Image;

import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Enum.PlayerRanking;
import me.bejosch.battleprogress.client.ServerConnection.ServerConnection;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class ClientPlayer {

	private int ID;
	private String name = "";

	private int level = 1;
	private int XP = 0;
	
	private int onlineMin = 0; //-1 = offline
	private String currentActivity = "";
	
	private PlayerRanking ranking = PlayerRanking.UNRANKED;
	private int rankingPoints = -1;
	
	private int profileImageNumber = 0;
	private int backgroundImageNumber = 0;
	private int nameColorNumber = 0;
	private int statusNumber = 0;
	
	//MANUEL LOAD
	public ClientPlayer(int ID, String name, int level, int XP, int onlineMin, int PIN, int BIN, int NCN, int SN, PlayerRanking ranking, int RP, String currentActivity) {
		
		this.ID = ID;
		
		this.name = name;
		this.level = level;
		this.XP = XP;
		this.profileImageNumber = PIN;
		this.backgroundImageNumber = BIN;
		this.nameColorNumber = NCN;
		this.statusNumber = SN;
		this.ranking = ranking;
		this.rankingPoints = RP;
		this.currentActivity = currentActivity;
		
	}
	
	//AUTO
	public ClientPlayer(int ID) {
		
		this.ID = ID;
		ProfilData.playerWaitingforDataReceive.add(this);
		ServerConnection.sendData(120, ServerConnection.getNewPacketId(), ""+ID);
		
	}
	
	//CALLED BY SERVER AFTER DATA LOAD REQUEST
	public void acceptLoadedPlayerData(String[] data) {
		
		//ID ; Name ; Level ; XP ; Online Min (-1 wenn offline) ; PIN ; BIN ; NCN ; SN ; RANKING ; RP ; currentActivity
		//this.ID = data[0];
		this.name = data[1];
		this.level = Integer.parseInt(data[2]);
		this.XP = Integer.parseInt(data[3]);
		this.onlineMin = Integer.parseInt(data[4]);
		this.profileImageNumber = Integer.parseInt(data[5]);
		this.backgroundImageNumber = Integer.parseInt(data[6]);
		this.nameColorNumber = Integer.parseInt(data[7]);
		this.statusNumber = Integer.parseInt(data[8]);
		this.ranking = PlayerRanking.valueOf(data[9]);
		this.rankingPoints = Integer.parseInt(data[10]);
		this.currentActivity = data[11];
		
		ProfilData.playerWaitingforDataReceive.remove(this);
		
	}
	
	public Image getProfileImg() {
		if(Images.profileImages.isEmpty()) {
			return Images.building_missingTexture;
		}
		if(Images.profileImages.size() > profileImageNumber) {
			return Images.profileImages.get(profileImageNumber);
		}else {
			return Images.profileImages.get(0);
		}
	}
	public Image getBackgroundImg() {
		if(Images.backgroundImages.isEmpty()) {
			return Images.building_missingTexture;
		}
		if(Images.backgroundImages.size() > backgroundImageNumber) {
			 return Images.backgroundImages.get(backgroundImageNumber);
		}else {
			return Images.backgroundImages.get(0);
		}
	}
	public Color getNameColor() {
		switch(nameColorNumber) {
		case 0:
			return Color.WHITE;
		default:
			return Color.WHITE;
		}
	}
	public String getStatus() {
		switch(statusNumber) {
		case 0:
			return "The Commander"; //""
		default:
			return "";
		}
	}
	public Image getRankImg() {
		switch(ranking) {
		case UNRANKED:
			return Images.ranking_Unranked;
		default:
			return Images.ranking_Unranked;
		}
	}


	public int getID() {
		return ID;
	}
	public String getName() {
		return name;
	}
	public int getLevel() {
		return level;
	}
	public int getXP() {
		return XP;
	}
	
	public int getProfileImageNumber() {
		return profileImageNumber;
	}
	public int getBackgroundImageNumber() {
		return backgroundImageNumber;
	}
	public int getNameColorNumber() {
		return nameColorNumber;
	}
	public int getStatusNumber() {
		return statusNumber;
	}
	public int getOnlineMin() {
		return onlineMin;
	}
	public int getRankingPoints() {
		return rankingPoints;
	}
	public PlayerRanking getRanking() {
		return ranking;
	}
	public String getCurrentActivity() {
		return currentActivity;
	}
	
	public void onlineMinHasPassed() {
		if(this.onlineMin != -1) {
			//SO NOT OFFLINE
			this.onlineMin++;
		}
	}
	
	public boolean isInGame() {
		return this.getCurrentActivity().contains("Game"); //TODO Works? Right implemented?
	}
	
	public void setOnlineMin(int onlineMin) {
		this.onlineMin = onlineMin;
	}
	public void setProfileImageNumber(int profileImageNumber) {
		this.profileImageNumber = profileImageNumber;
	}
	public void setBackgroundImageNumber(int backgroundImageNumber) {
		this.backgroundImageNumber = backgroundImageNumber;
	}
	public void setNameColorNumber(int nameColorNumber) {
		this.nameColorNumber = nameColorNumber;
	}
	public void setStatusNumber(int statusNumber) {
		this.statusNumber = statusNumber;
	}
	public void setRanking(PlayerRanking ranking) {
		this.ranking = ranking;
	}
	public void setRankingPoints(int rankingPoints) {
		this.rankingPoints = rankingPoints;
	}
	public void setCurrentActivity(String currentActivity) {
		this.currentActivity = currentActivity;
	}
	
}
