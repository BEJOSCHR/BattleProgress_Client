package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Enum.GenerelIconType;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Enum.SpielStatus;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.EnergyOverview.OnTopWindow_EnergyOverview;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.GameMenu.OnTopWindow_GameMenu;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.MaterialOverview.OnTopWindow_MaterialOverview;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Research.OnTopWindow_Research;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.RoundSummary.OnTopWindow_RoundSummary;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Settings.A_Gameplay.OnTopWindow_Settings_Gameplay;

public class MouseActionArea_generalIconButtons extends MouseActionArea {

	public Image displayImage;
	public GenerelIconType type;
	
	public MouseActionArea_generalIconButtons(int positionNumber_, String idName_, String[] hoverText_, GenerelIconType iconType, Image displayImage) {
		super(StandardData.gIcon_x+(positionNumber_*(StandardData.gIcon_maße+StandardData.gIcon_distanceBetween)), StandardData.gIcon_y
			, StandardData.gIcon_x+(positionNumber_*(StandardData.gIcon_maße+StandardData.gIcon_distanceBetween))+StandardData.gIcon_maße, StandardData.gIcon_y+StandardData.gIcon_maße
			, idName_, hoverText_, ShowBorderType.ShowAlways, StandardData.gIcon_defaultColor, StandardData.gIcon_hoverColor);
		
		this.displayImage = displayImage;
		this.type = iconType;
		
	}

	@Override
	public boolean isActiv() {
		
		if(StandardData.spielStatus == SpielStatus.Game) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		switch(this.type) {
		case HomeMenu:
			OnTopWindowHandler.openOTW(new OnTopWindow_GameMenu());
			break;
		case Settings:
			OnTopWindowHandler.openOTW(new OnTopWindow_Settings_Gameplay());
			break;
		case Dictionary:
			//TODO open dictonary
			break;
		case Material:
			if(RoundData.currentRound > 1) {
				//NOT FIRST ROUND
				OnTopWindowHandler.openOTW(new OnTopWindow_MaterialOverview());
			}
			break;
		case Energy:
			if(RoundData.currentRound > 1) {
				//NOT FIRST ROUND
				OnTopWindowHandler.openOTW(new OnTopWindow_EnergyOverview());
			}
			break;
		case Research:
			if(RoundData.currentRound > 1) {
				//NOT FIRST ROUND
				OnTopWindowHandler.openOTW(new OnTopWindow_Research());
			}
			break;
		case RoundSummary:
			if(RoundData.currentRound > 1) {
				//NOT FIRST ROUND
				OnTopWindowHandler.openOTW(new OnTopWindow_RoundSummary(RoundData.currentRound-1, true));
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {
			if( (this.type == GenerelIconType.RoundSummary || this.type == GenerelIconType.Material || this.type == GenerelIconType.Energy || this.type == GenerelIconType.Research) && RoundData.currentRound <= 1) {
				//DARK ON FIRST ROUND
				g.setColor(Color.DARK_GRAY.darker());
			}else {
				g.setColor(StandardData.gIcon_backgroundColor);
			}
			g.fillRect(xTL, yTL, xBR-xTL, yBR-yTL);
			
			g.drawImage(displayImage, xTL+StandardData.gIcon_imgBorder, yTL+StandardData.gIcon_imgBorder, null);
		}
		
		super.draw(g);
	}
	
}
