package me.bejosch.battleprogress.client.Objects.OnTopWindow.ResearchConfirm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.ResearchData;
import me.bejosch.battleprogress.client.Enum.ImportanceType;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Game.Handler.Game_ResearchHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class MAA_OTW_ResearchConfirm_Research extends MouseActionArea {
	
	public MAA_OTW_ResearchConfirm_Research() {
		super(getX(), getY(), getX()+OnTopWindowData.researchConfirm_buttonWidth, getY()+OnTopWindowData.researchConfirm_buttonHeight, 
				"OTW_ResearchConfirm_Research", null, ShowBorderType.ShowAlways, Color.BLACK, Color.GREEN);
		this.OTWMMA = true;
	}
	
	private static int getX() {
		int i = 0;
		return WindowData.FrameWidth/2-OnTopWindowData.researchConfirm_width/2+OnTopWindowData.researchConfirm_textBorder+i*(OnTopWindowData.researchConfirm_buttonWidth+OnTopWindowData.researchConfirm_buttonBorderBetween);
	}
	private static int getY() {
		return WindowData.FrameHeight/2+OnTopWindowData.researchConfirm_height/2-OnTopWindowData.researchConfirm_buttonHeight-OnTopWindowData.researchConfirm_buttonBottomBorder;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_ResearchConfirm) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		if(OnTopWindowData.onTopWindow instanceof OnTopWindow_ResearchConfirm) {
			OnTopWindow_ResearchConfirm otw = (OnTopWindow_ResearchConfirm) OnTopWindowData.onTopWindow;
			if(Game_ResearchHandler.hasDependencyResearched(otw.upgrade) == false) {
				//NOT DEPENDENCY RESEARCHED
				List<String> message = new ArrayList<String>();
				message.add("You need to research "+Game_ResearchHandler.getUpgrade(otw.upgrade.getDependency()).getTitle()+" first!");
				new InfoMessage(message, ImportanceType.HIGH, true);
			}else if(Game_ResearchHandler.hasResearched(otw.upgrade) == true) {
				//ALREADY RESEARCHED
				List<String> message = new ArrayList<String>();
				message.add("You already researched "+otw.upgrade.getTitle()+"!");
				new InfoMessage(message, ImportanceType.HIGH, true);
			}else if(otw.enoughRPforResearch() == false) {
				//NOT ENOUGH RP
				List<String> message = new ArrayList<String>();
				message.add("You have not enought ResearchPoints for this research!");
				message.add("You're missing "+(otw.upgrade.getDataContainer().researchCost-ResearchData.researchPoints)+" RP to unlock "+otw.upgrade.getTitle()+"");
				new InfoMessage(message, ImportanceType.HIGH, true);
			}else {
				Game_ResearchHandler.researchUpgrade(otw.upgrade);
				OnTopWindowHandler.closeOTW();
			}
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {  
			//TEXT
			if(this.isAvailableForResearch() == true) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 26));
				g.drawString("Research", this.xTL+13, this.yTL+OnTopWindowData.researchConfirm_buttonHeight-15);
			}else {
				g.setColor(Color.LIGHT_GRAY);
				g.setFont(new Font("Arial", Font.BOLD, 26));
				g.drawString("Research", this.xTL+13, this.yTL+OnTopWindowData.researchConfirm_buttonHeight-15);
				g.setColor(Color.WHITE);
				g.drawLine(xTL, yTL, xBR, yBR);
				g.drawLine(xTL, yBR, xBR, yTL);
			}
		}
		
		super.draw(g);
		
	}
	
	private boolean isAvailableForResearch() {
		
		OnTopWindow_ResearchConfirm otw = (OnTopWindow_ResearchConfirm) OnTopWindowData.onTopWindow;
		if(Game_ResearchHandler.hasDependencyResearched(otw.upgrade) == false) {
			//NOT DEPENDENCY RESEARCHED
			return false;
		}else if(Game_ResearchHandler.hasResearched(otw.upgrade) == true) {
			//ALREADY RESEARCHED
			return false;
		}else if(otw.enoughRPforResearch() == false) {
			//NOT ENOUGH RP
			return false;
		}else {
			return true;
		}
		
	}
	
}
