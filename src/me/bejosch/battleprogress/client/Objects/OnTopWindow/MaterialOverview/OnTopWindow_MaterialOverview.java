package me.bejosch.battleprogress.client.Objects.OnTopWindow.MaterialOverview;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Enum.AnimationType;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Objects.RoundStatsContainer;
import me.bejosch.battleprogress.client.Objects.Animations.Animation;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;

public class OnTopWindow_MaterialOverview extends OnTopWindow{

	Animation animation = null;
	
	public OnTopWindow_MaterialOverview() {
		super("OTW_MaterialOverview", OnTopWindowData.materialOverview_width, OnTopWindowData.materialOverview_height);
	}

	@Override
	public void initOnOpen() {
	
		MovementHandler.cancleMovement();
		animation = new Animation(AnimationType.Game_EcoOverview) {
			@Override
			public void getParametersFromType() {
				this.speed = 1;
				this.faktor = 0;
			}
			@Override
			public void changeAction() {
				this.faktor++;
				if(this.faktor == 100) {
					this.cancle();
				}
			}
		};
		
	}
	
	@Override
	public void performClose() {
		
		MovementHandler.allowMovement();
		if(this.animation != null) {
			this.animation.cancle();
		}
		
	}
	
	int x = WindowData.FrameWidth/2-this.width/2;
	int y = WindowData.FrameHeight/2-this.height/2;
	
	int tableBorder = 30, sectionBorder = 5;
	int topBorder = 60, botBorder = 15;
	int tableHeight = this.height-topBorder-botBorder-tableBorder*2;
	int tableX = x+tableBorder, tableY = y+topBorder+tableBorder+tableHeight/2;
	int tableWidth = this.width-tableBorder*2;
	int sections = 10;
	int sectionWidth = (int) (tableWidth/ (double) sections);
	
	@Override
	public void draw(Graphics g) {
		
		int maxMassDisplayed = 1;
		for(int i = 0 ; i < sections ; i++) {
			int roundNumber = RoundData.currentRound-1-(sections-1-i);
			if(RoundData.statsContainer.containsKey(roundNumber)) {
				RoundStatsContainer stats = RoundData.statsContainer.get(roundNumber);
				if(Math.abs(stats.massBalance()) > maxMassDisplayed) {
					maxMassDisplayed = Math.abs(stats.massBalance())+20;
				}
			}
		}
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(x, y, this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 30));
		g.drawString("Material Overview", x + 30, y + 45);
		
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(tableX, tableY, tableX+tableWidth, tableY);
		g.drawLine(tableX, tableY+tableHeight/2, tableX, tableY-tableHeight/2);
		
		if(animation != null) {
			for(int i = 0 ; i < sections ; i++) {
				int roundNumber = RoundData.currentRound-1-(sections-1-i);
				if(RoundData.statsContainer.containsKey(roundNumber)) {
					//STATS FOR THIS ROUND EXIST
					
					RoundStatsContainer stats = RoundData.statsContainer.get(roundNumber);
					int massBalance = stats.massBalance();
					if(massBalance > maxMassDisplayed) { massBalance = maxMassDisplayed; }
					int sectionHeight = (int) ((massBalance/(double) maxMassDisplayed)*(tableHeight/2));
					int displayedHeight = (int) (sectionHeight*(animation.faktor/100D));
					
					g.setColor(GameData.color_Material);
					g.setFont(new Font("Arial", Font.CENTER_BASELINE, 22));
					if(massBalance >= 0) {
						g.fillRect(tableX+sectionWidth*i+sectionBorder, tableY-displayedHeight, sectionWidth-sectionBorder*2, displayedHeight-1);
						g.drawString(""+massBalance, tableX+sectionWidth*i+sectionBorder, tableY-displayedHeight-5);
					}else {
						g.fillRect(tableX+sectionWidth*i+sectionBorder, tableY+1, sectionWidth-sectionBorder*2, (displayedHeight*-1)-1);
						g.drawString(""+massBalance, tableX+sectionWidth*i+sectionBorder, tableY-displayedHeight+20);
					}
					
					g.setColor(Color.WHITE);
					g.setFont(new Font("Arial", Font.CENTER_BASELINE, 30));
					g.drawString(""+roundNumber+".", tableX+sectionWidth*i+sectionBorder+10, tableY+tableHeight/2+25);
				}
			}
		}
		
		
	}
	
}
