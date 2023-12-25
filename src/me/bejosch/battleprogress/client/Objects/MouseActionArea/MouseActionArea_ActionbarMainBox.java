package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Enum.SourceType;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.UnitDetailInfo.OnTopWindow_UnitDetailInfo;

public class MouseActionArea_ActionbarMainBox extends MouseActionArea{
	
	public SourceType sourceType = null;
	
//==========================================================================================================
	/**
	 * A spezial type of {@link MouseActionArea} for the main box of the troup/building/field display in the actionbar, called MainDisplayBox
	 **/
	public MouseActionArea_ActionbarMainBox() {
		super(GameData.displayBox_realX, GameData.displayBox_realY, GameData.displayBox_realX+GameData.displayBox_size, GameData.displayBox_realY+GameData.displayBox_size, "ActionBar_MainDisplayBox", null, ShowBorderType.ShowNever, null, null); //HOVER TEXT IS OVERWRITTEN
	}

	@Override
	public boolean isActiv() {
		
		if(GameData.clickedField != null) {
			
//			Building building = GameHandler.getBuildingByCoordinates(GameData.clickedField.X, GameData.clickedField.Y);
//			Troup troup = GameHandler.getTroupByCoordinates(GameData.clickedField.X, GameData.clickedField.Y);
			if(GameData.clickedField.building != null && GameData.clickedField.visible) {
				//BUILDING ON THIS FIELD
				this.sourceType = SourceType.FromBuilding;				//!!! UPDATE REQUIERED FOR EVERY NEW TYPE ADDED
			}else if(GameData.clickedField.troup != null && GameData.clickedField.visible) {
				//TROUP ON THIS FIELD
				this.sourceType = SourceType.FromTroup;					//!!! UPDATE REQUIERED FOR EVERY NEW TYPE ADDED
			}else {
				//NO BUILDING OR TROUP ON THIS FIELD!
				this.sourceType = SourceType.FromField; 				//!!! UPDATE REQUIERED FOR EVERY NEW TYPE ADDED
			}
			return true;
			
		}else {
			//NO FIELD CLICKED
			this.sourceType = null; //RESETT SOURCE TYPE
			return false;
		}
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		switch(sourceType) {
		case FromBuilding:
			OnTopWindowHandler.openOTW(new OnTopWindow_UnitDetailInfo(GameData.clickedField.building, false));
			break;
		case FromTroup:
			OnTopWindowHandler.openOTW(new OnTopWindow_UnitDetailInfo(GameData.clickedField.troup, false));
			break;
		case FromField:
			OnTopWindowHandler.openOTW(new OnTopWindow_UnitDetailInfo(GameData.clickedField, false));
			break;
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {
			String[] hoverText = {"Click for details"};
			this.hoverText = hoverText;
		}
		
		super.draw(g);
	}
	
	
}
