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

public class MAA_OTW_RoundSum_Details_Energy extends MouseActionArea {

	public MAA_OTW_RoundSum_Details_Energy() {
		super(WindowData.FrameWidth/2-OnTopWindowData.roundSum_width/2+5, WindowData.FrameHeight/2-OnTopWindowData.roundSum_height/2+100-30+OnTopWindowData.roundSum_heightPerEco*1
				, WindowData.FrameWidth/2-5, WindowData.FrameHeight/2-OnTopWindowData.roundSum_height/2+170+20+OnTopWindowData.roundSum_heightPerEco*1
				, "OTW_RoundSum_Details_Energy", null, ShowBorderType.ShowAlways, Color.WHITE, Color.ORANGE);
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
			
			int produceRows = rsc.getEnergyProduction_building().size()+rsc.getEnergyProduction_troup().size();
			int consumeRows = rsc.getEnergyConsumption_building().size()+rsc.getEnergyConsumption_troup().size();
			int rowHeight = (produceRows+consumeRows)*OnTopWindowData.roundSum_details_smallHeight;
			
			g.setColor(Color.DARK_GRAY);
			g.fillRect(x, y, OnTopWindowData.roundSum_details_width, rowHeight+OnTopWindowData.roundSum_details_titelHeight+OnTopWindowData.roundSum_details_bigHeight*2+OnTopWindowData.roundSum_details_udBorder*2);
			g.setColor(Color.WHITE);
			g.drawRect(x, y, OnTopWindowData.roundSum_details_width, rowHeight+OnTopWindowData.roundSum_details_titelHeight+OnTopWindowData.roundSum_details_bigHeight*2+OnTopWindowData.roundSum_details_udBorder*2);
			
			y += OnTopWindowData.roundSum_details_udBorder+OnTopWindowData.roundSum_details_titelHeight;
			g.setColor(GameData.color_Energy);
			g.setFont(new Font("Arial", Font.CENTER_BASELINE, 24));
			g.drawString("Energy details", x + 20, y);
			
			y += OnTopWindowData.roundSum_details_bigHeight;
			g.setColor(Color.LIGHT_GRAY);
			g.setFont(new Font("Arial", Font.CENTER_BASELINE, 22));
			g.drawString("Produced:", x + 20, y);
			
			for(Building b : rsc.getEnergyProduction_building().keySet()) {
				int amount = rsc.getEnergyProduction_building().get(b);
				y += OnTopWindowData.roundSum_details_smallHeight;
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 18));
				g.drawString(b.name+":", x + 20, y);
				g.setColor(GameData.color_positiv);
				g.drawString("+"+amount, x + OnTopWindowData.roundSum_details_width-OnTopWindowData.roundSum_details_leftAmountBorder, y);
			}
			for(Troup t : rsc.getEnergyProduction_troup().keySet()) {
				int amount = rsc.getEnergyProduction_troup().get(t);
				y += OnTopWindowData.roundSum_details_smallHeight;
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 18));
				g.drawString(t.name+":", x + 20, y);
				g.setColor(GameData.color_positiv);
				g.drawString("+"+amount, x + OnTopWindowData.roundSum_details_width-OnTopWindowData.roundSum_details_leftAmountBorder, y);
			}
			
			y += OnTopWindowData.roundSum_details_bigHeight;
			g.setColor(Color.LIGHT_GRAY);
			g.setFont(new Font("Arial", Font.CENTER_BASELINE, 22));
			g.drawString("Consumed:", x + 20, y);
			
			for(Building b : rsc.getEnergyConsumption_building().keySet()) {
				int amount = rsc.getEnergyConsumption_building().get(b);
				y += OnTopWindowData.roundSum_details_smallHeight;
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 18));
				g.drawString(b.name+":", x + 20, y);
				g.setColor(GameData.color_negative);
				g.drawString(""+amount, x + OnTopWindowData.roundSum_details_width-OnTopWindowData.roundSum_details_leftAmountBorder, y);
			}
			for(Troup t : rsc.getEnergyConsumption_troup().keySet()) {
				int amount = rsc.getEnergyConsumption_troup().get(t);
				y += OnTopWindowData.roundSum_details_smallHeight;
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 18));
				g.drawString(t.name+":", x + 20, y);
				g.setColor(GameData.color_negative);
				g.drawString(""+amount, x + OnTopWindowData.roundSum_details_width-OnTopWindowData.roundSum_details_leftAmountBorder, y);
			}
			
		}
		
		//super.draw(g); //SHOW BORDER FOR DEV DISPLAY
		
	}
	
}
