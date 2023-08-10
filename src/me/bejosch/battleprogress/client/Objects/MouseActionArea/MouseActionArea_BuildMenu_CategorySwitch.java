package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Color;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;

public class MouseActionArea_BuildMenu_CategorySwitch extends MouseActionArea {

	public int categoryNumber = 0;
	
	public MouseActionArea_BuildMenu_CategorySwitch(int categoryNumber) {
		super(0, 0, 0, 0, "BuildMenu_CategorySwitch_"+categoryNumber, null, ShowBorderType.ShowAlways, Color.WHITE, Color.GREEN);
		
		int typeX = GameData.switchArea_X+GameData.switchArea_border, typeY = GameData.switchArea_Y+GameData.switchArea_border+(categoryNumber*GameData.switchArea_size)+(categoryNumber*GameData.switchArea_spaceBetween);
		
		String[] hoverMessage = {"Category "+GameData.buildMenu_possibleCategory.get(categoryNumber).toString()};
		this.hoverText = hoverMessage;
		this.categoryNumber = categoryNumber;
		this.xTL = typeX; 
		this.yTL = typeY;
		this.xBR = typeX + GameData.switchArea_size;
		this.yBR = typeY + GameData.switchArea_size;
		
	}

	@Override
	public boolean isActiv() {
		if(GameData.buildMenu_activated == true) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		if(GameData.currentActive_MAA_BuildingTask == null) {
			GameHandler.switchBuildMenuCategory(GameData.buildMenu_possibleCategory.get(categoryNumber));
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		
		//RED CROSS IF THERE IS A BUILD MENU TASK
		if(isActiv() == true) {
			if(GameData.currentActive_MAA_BuildingTask != null) {
				//THERE IS A TASK
				g.setColor(Color.RED);
				g.drawLine(xTL, yTL, xBR, yBR);
				g.drawLine(xBR, yTL, xTL, yBR);
			}
		}
		
	}
	
}
