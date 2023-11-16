package me.bejosch.battleprogress.client.Objects.OnTopWindow.RoundSummary;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class MAA_OTW_RoundSum_PrevSum extends MouseActionArea {

	public MAA_OTW_RoundSum_PrevSum() {
		super(WindowData.FrameWidth/2-OnTopWindowData.roundSum_width/2+20, WindowData.FrameHeight/2+OnTopWindowData.roundSum_height/2-OnTopWindowData.generallConfirm_MAA_height-20
				, WindowData.FrameWidth/2-OnTopWindowData.roundSum_width/2+OnTopWindowData.generallConfirm_MAA_height+20, WindowData.FrameHeight/2+OnTopWindowData.roundSum_height/2-20
				, "OTW_RoundSum_PrevSum", null, ShowBorderType.ShowAlways, Color.WHITE, Color.ORANGE);
		this.OTWMMA = true;
		String[] hoverText = {"Show previous round summary"};
		this.hoverText = hoverText;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_RoundSummary) {
				OnTopWindow_RoundSummary otw = (OnTopWindow_RoundSummary) OnTopWindowData.onTopWindow;
				int currentShownRound = otw.displayedRound;
				if(currentShownRound > 1) {
					return true;
				}
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		OnTopWindow_RoundSummary otw = (OnTopWindow_RoundSummary) OnTopWindowData.onTopWindow;
		int currentShownRound = otw.displayedRound;
		OnTopWindowHandler.openOTW(new OnTopWindow_RoundSummary(currentShownRound-1, true), true);
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {  
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				//HOVER
				g.setColor(hoverColor);
				g.setFont(new Font("Arial", Font.PLAIN, 41));
				g.drawString("<", this.xTL+11, this.yBR-9);
			}else {
				//NO HOVER
				g.setColor(standardColor);
				g.setFont(new Font("Arial", Font.PLAIN, 40));
				g.drawString("<", this.xTL+12, this.yBR-8);
			}
		}
		
		super.draw(g);
		
	}
	
}
