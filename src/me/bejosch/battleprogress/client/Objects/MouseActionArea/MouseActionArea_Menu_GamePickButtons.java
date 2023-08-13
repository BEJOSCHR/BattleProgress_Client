package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.MenuData;
import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Enum.SpielStatus;
import me.bejosch.battleprogress.client.ServerConnection.MinaClient;

public class MouseActionArea_Menu_GamePickButtons extends MouseActionArea {

	public int position = -1;
	
//==========================================================================================================
	/**
	 * A spacial ActionArea for the Chat handeling (hide/show)
	 * @param positionAsShowButton - boolean - if this is true it works as show button, if false it is the hide button
	 **/
	public MouseActionArea_Menu_GamePickButtons(int position) {
		super(0, 0, 0, 0, null, null, ShowBorderType.ShowAlways, null, Color.ORANGE);
		
		this.position = position;
		
		this.idName = "Menu_GamePickButton_"+position;
		
		setStandardHoverText();
		
		int x = WindowData.FrameWidth/2-(MenuData.gpm_width/2), y = MenuData.til_height+1;
		this.xTL = x+MenuData.gpm_buttonBorderBetween*(position+1)+MenuData.gpm_buttonWidth*position;
		this.xBR = this.xTL+MenuData.gpm_buttonWidth;
		this.yTL = y+MenuData.gpm_buttonBorderTopDown;
		this.yBR = this.yTL+MenuData.gpm_buttonHeight;
		
	}
	
	private void setStandardHoverText() {
		
		this.hoverText = null;
		
//		if(position != 3) {
//			//QUEUE
//			String[] hoverText_ = {"Join the"+getNameByPos(position)+" queue"};
//			this.hoverText = hoverText_;
//		}else {
//			//CUSTOM
//			String[] hoverText_ = {"Create your own "+getNameByPos(position)};
//			this.hoverText = hoverText_;
//		}
	}
	
	private String getNameByPos(int pos) {
		switch(pos) {
		case 0: return " Normal - 2v2";
		case 1: return " Normal - 1v1";
		case 2: return " Ranked - 1v1";
		case 3: return "Custom Game";
		}
		return "";
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		switch(this.position) {
		case 0:
			MinaClient.sendData(400, ""+SpielModus.Normal_2v2);
			break;
		case 1:
			if(ProfilData.otherGroupClient == null) {
				//STILL A 1er GROUP
				MinaClient.sendData(400, ""+SpielModus.Normal_1v1);
			}
			break;
		case 2:
			if(ProfilData.otherGroupClient == null) {
				//STILL A 1er GROUP
				MinaClient.sendData(400, ""+SpielModus.Ranked_1v1);
			}
			break;
		case 3: 
			//TODO SWITCH TO CUSTOM GAME LOBBY
			break;
		}
		
		super.performAction_LEFT_RELEASE();
	}
	
	@Override
	public boolean isActiv() {
		
		if(StandardData.spielStatus == SpielStatus.Menu) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {
			g.setColor(Color.DARK_GRAY.brighter());
			g.fillRect(xTL, yTL, xBR-xTL, yBR-yTL);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString(getNameByPos(position), this.xTL+12, this.yTL+(this.yBR-this.yTL)/2+8);
			
			if( (this.position == 1 || this.position == 2) && ProfilData.otherGroupClient != null ) {
				//1v1 - QUEUES WITH A 2 MAN GROUP
				this.standardColor = Color.LIGHT_GRAY;
				g.setColor(standardColor);
				g.drawLine(xTL, yTL, xBR, yBR);
				g.drawLine(xTL, yBR, xBR, yTL);
				String[] hoverText_ = {"You can't join this queue with 2 players in the group!"};
				this.hoverText = hoverText_;
			}else {
				this.standardColor = Color.WHITE;
				setStandardHoverText();
			}
		}
		
		super.draw(g);
	}
	
}
