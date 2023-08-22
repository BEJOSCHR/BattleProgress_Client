package me.bejosch.battleprogress.client.Objects.OnTopWindow.RoundSummary;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Objects.RoundStatsContainer;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;

public class MAA_OTW_RoundSum_Details_BuildProduceUpgrade extends MouseActionArea {

	public MAA_OTW_RoundSum_Details_BuildProduceUpgrade() {
		super(WindowData.FrameWidth/2+5, WindowData.FrameHeight/2-OnTopWindowData.roundSum_height/2+100-25+OnTopWindowData.roundSum_smallDistanceBetween*6+OnTopWindowData.roundSum_bigDistanceBetween*3
				, WindowData.FrameWidth/2+OnTopWindowData.roundSum_width/2-5, WindowData.FrameHeight/2-OnTopWindowData.roundSum_height/2+100+10+OnTopWindowData.roundSum_smallDistanceBetween*8+OnTopWindowData.roundSum_bigDistanceBetween*3
				, "OTW_RoundSum_Details_BuildProduceUpgrade", null, ShowBorderType.ShowAlways, Color.WHITE, Color.ORANGE);
		this.OTWMMA = true;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_RoundSummary) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isHovered()) {
			int targetRound = ((OnTopWindow_RoundSummary) OnTopWindowData.onTopWindow).displayedRound;
			RoundStatsContainer rsc = RoundData.statsContainer.get(targetRound);
			
			int x = WindowData.FrameWidth/2+OnTopWindowData.roundSum_width/2;
			int y = WindowData.FrameHeight/2-OnTopWindowData.roundSum_height/2;
			
			int buildRows = rsc.getBuildBuildings().size();
			int produceRows = rsc.getProducedTroups().size();
			int upgradeRows = rsc.getUpgradedTroups().size();
			int rowHeight = (buildRows+produceRows+upgradeRows)*OnTopWindowData.roundSum_details_smallHeight;
			
			g.setColor(Color.DARK_GRAY);
			g.fillRect(x, y, OnTopWindowData.roundSum_details_width, rowHeight+OnTopWindowData.roundSum_details_titelHeight+OnTopWindowData.roundSum_details_bigHeight*3+OnTopWindowData.roundSum_details_udBorder*2);
			g.setColor(Color.WHITE);
			g.drawRect(x, y, OnTopWindowData.roundSum_details_width, rowHeight+OnTopWindowData.roundSum_details_titelHeight+OnTopWindowData.roundSum_details_bigHeight*3+OnTopWindowData.roundSum_details_udBorder*2);
			
			y += OnTopWindowData.roundSum_details_udBorder+OnTopWindowData.roundSum_details_titelHeight;
			g.setColor(GameData.color_highlight);
			g.setFont(new Font("Arial", Font.CENTER_BASELINE, 24));
			g.drawString("B/P/U details", x + 20, y);
			
			y += OnTopWindowData.roundSum_details_bigHeight;
			g.setColor(Color.LIGHT_GRAY);
			g.setFont(new Font("Arial", Font.CENTER_BASELINE, 22));
			g.drawString("Build:", x + 20, y);
			
			for(Building b : rsc.getBuildBuildings()) {
				y += OnTopWindowData.roundSum_details_smallHeight;
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 18));
				g.drawString(b.name+"", x + 20, y);
			}
			
			y += OnTopWindowData.roundSum_details_bigHeight;
			g.setColor(Color.LIGHT_GRAY);
			g.setFont(new Font("Arial", Font.CENTER_BASELINE, 22));
			g.drawString("Produced:", x + 20, y);
			
			for(Troup t : rsc.getProducedTroups()) {
				y += OnTopWindowData.roundSum_details_smallHeight;
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 18));
				g.drawString(t.name+"", x + 20, y);
			}
			
			y += OnTopWindowData.roundSum_details_bigHeight;
			g.setColor(Color.LIGHT_GRAY);
			g.setFont(new Font("Arial", Font.CENTER_BASELINE, 22));
			g.drawString("Upgraded:", x + 20, y);
			
			for(Troup t : rsc.getUpgradedTroups()) {
				y += OnTopWindowData.roundSum_details_smallHeight;
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 18));
				g.drawString(t.name+"", x + 20, y);
			}
			
		}
		
		//super.draw(g); //SHOW BORDER FOR DEV DISPLAY
		
	}
	
}
