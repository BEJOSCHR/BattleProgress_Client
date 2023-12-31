package me.bejosch.battleprogress.client.Objects.OnTopWindow.TabGameInfo;

import java.awt.Color;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class MAA_OTW_TabGameInfo_JumpToHQ extends MouseActionArea {

	private int playerNumber;
	
	public MAA_OTW_TabGameInfo_JumpToHQ(int playerNumber) {
		super(getX(playerNumber), getY(playerNumber), getX(playerNumber)+OnTopWindowData.tabGameInfo_sectionWidth, getY(playerNumber)+OnTopWindowData.tabGameInfo_sectionHeight
				, "OTW_TabGameInfo_JumpToHQ_"+playerNumber, null, ShowBorderType.ShowOnHover, null, Color.ORANGE);
		this.OTWMMA = true;
		this.playerNumber = playerNumber;
		String[] hovertext = {"Ping: 0 ms"};
		this.hoverText = hovertext;
	}
	
	private static int getX(int playerNumber) {
		return OnTopWindow_TabGameInfo.getPlayerDisplayCoords(playerNumber).x;
	}
	private static int getY(int playerNumber) {
		return OnTopWindow_TabGameInfo.getPlayerDisplayCoords(playerNumber).y;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_TabGameInfo && GameData.playingPlayer.length >= this.getRealDisplayedPlayerNumber()+1 && GameData.playingPlayer[this.getRealDisplayedPlayerNumber()] != null) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		//IS NOT NULL BECAUSE ON ACTIVE CHECK
		FieldCoordinates hq = Funktions.getHQfieldCoordinatesByPlayerID(GameData.playingPlayer[this.getRealDisplayedPlayerNumber()].getID());
		Funktions.moveScreenToFieldCoordinates(hq.X, hq.Y);;
		
	}
	
	private int getRealDisplayedPlayerNumber() {
		
		//REORDER SO PLAYER 2 IS AT POS 1
		int useNumber = this.playerNumber;
		if(SpielModus.isGameModus1v1() && useNumber == 1) { 
			useNumber = 2; 
		}else if(SpielModus.isGameModus1v1() && useNumber == 2) { 
			useNumber = 1; 
		}
		return useNumber;
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {
			String[] hovertext = {"Ping: "+GameData.playingPlayer[this.getRealDisplayedPlayerNumber()].getPing()+" ms"};
			this.hoverText = hovertext;
		}
		
		super.draw(g);
		
	}
	
}
