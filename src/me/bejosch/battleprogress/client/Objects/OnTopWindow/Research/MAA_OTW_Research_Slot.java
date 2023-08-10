package me.bejosch.battleprogress.client.Objects.OnTopWindow.Research;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.Game.ResearchData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Game.Handler.Game_ResearchHandler;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.ResearchConfirm.OnTopWindow_ResearchConfirm;
import me.bejosch.battleprogress.client.Objects.Research.Upgrades.Upgrade;

public class MAA_OTW_Research_Slot extends MouseActionArea {

	private int slotX, slotY;
	
	public MAA_OTW_Research_Slot(int slotX, int slotY) {
		super(OnTopWindow_Research.getSlotByPos_X(slotX), OnTopWindow_Research.getSlotByPos_Y(slotY),
				OnTopWindow_Research.getSlotByPos_X(slotX)+OnTopWindowData.research_slotWidth, OnTopWindow_Research.getSlotByPos_Y(slotY)+OnTopWindowData.research_slotHeight,
				"MAA_Research_Slot_"+slotX+"_"+slotY, null, ShowBorderType.ShowAlways, null, null);
		
		this.slotX = slotX;
		this.slotY = slotY;
		
		this.OTWMMA = true;
		
	}

	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_Research) {
				if(getRepresentedUpgrade(this.slotX, this.slotY) != null) {
					//HAS UPGRADE TO REPRESENT
					return true;
				}
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		OnTopWindowHandler.openOTW(new OnTopWindow_ResearchConfirm(getRepresentedUpgrade(this.slotX, this.slotY)));
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {
			
			Upgrade upgrade = getRepresentedUpgrade(this.slotX, this.slotY);
			
			g.setColor(Color.DARK_GRAY.brighter());
			g.fillRect(xTL, yTL, xBR-xTL, yBR-yTL);
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				//HOVER
				g.setColor(new Color(255,165,0)); //ORANGE
			}else if(Game_ResearchHandler.hasDependencyResearched(upgrade) == false) {
				//DEPENDENCIE NOT RESEARCHED
				g.setColor(OnTopWindowData.research_color_locked);
			}else if(Game_ResearchHandler.hasResearched(upgrade)) {
				//RESEARCHED
				g.setColor(OnTopWindowData.research_color_researched);
			}else {
				//NO HOVER BUT AVAILABLE
				g.setColor(OnTopWindowData.research_color_researchable); //LIGHT BLUE
			}
			g.drawRect(xTL, yTL, xBR-xTL, yBR-yTL);
			
			g.setFont(new Font("Arial", Font.PLAIN, 22));
			g.drawString(upgrade.getTitle(), this.xTL+20, this.yTL+40);
			
			//CONNECTION
			if(upgrade.getDependency() != null) {
				int depend_x = OnTopWindow_Research.getSlotByPos_X( Game_ResearchHandler.getUpgrade(upgrade.getDependency()).getX() ) + 1;
				int depend_y = OnTopWindow_Research.getSlotByPos_Y( Game_ResearchHandler.getUpgrade(upgrade.getDependency()).getY() - OnTopWindowData.research_scrollPos );
				g.drawLine(depend_x+OnTopWindowData.research_slotWidth, depend_y+OnTopWindowData.research_slotHeight/2, this.xTL, this.yTL+OnTopWindowData.research_slotHeight/2);
			}
			
			if(Game_ResearchHandler.hasResearched(upgrade)) {
				//RESEARCHED SO NO HIGHLIGHT
				g.setColor(Color.YELLOW);
			}else if(ResearchData.researchPoints >= upgrade.getDataContainer().researchCost) {
				//CAN EFFORD
				g.setColor(Color.GREEN);
			}else {
				g.setColor(Color.RED);
			}
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString(""+upgrade.getDataContainer().researchCost, this.xTL+OnTopWindowData.research_slotWidth-40, this.yTL+OnTopWindowData.research_slotHeight-10);
			
		}
		
	}
	
	//STATIC
	public static Upgrade getRepresentedUpgrade(int x, int y) {
		
		int realX = x;
		int realY = y+OnTopWindowData.research_scrollPos;
		
		return Game_ResearchHandler.getUpgrade(realX, realY, OnTopWindowData.research_category);
		
	}
	
}
