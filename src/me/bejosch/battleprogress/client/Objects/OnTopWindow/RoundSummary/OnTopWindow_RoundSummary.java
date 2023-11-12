package me.bejosch.battleprogress.client.Objects.OnTopWindow.RoundSummary;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Objects.RoundStatsContainer;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_ShowRoundSummary;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;

public class OnTopWindow_RoundSummary extends OnTopWindow {
	
	public int displayedRound;
	private boolean noAnimation = false;
	
	public Animation_ShowRoundSummary animation = null;
	
	public OnTopWindow_RoundSummary() {
		super("OTW_RoundSum", OnTopWindowData.roundSum_width, OnTopWindowData.roundSum_height);
		
		this.displayedRound = RoundData.currentRound-1;
		this.noAnimation = false;
		
	}
	public OnTopWindow_RoundSummary(int displayedRound, boolean noAnimation) {
		super("OTW_RoundSum", OnTopWindowData.roundSum_width, OnTopWindowData.roundSum_height);
		
		this.displayedRound = displayedRound;
		this.noAnimation = noAnimation;
		
	}

	@Override
	public void initOnOpen() {
		
		MovementHandler.cancleMovement();
		if(this.noAnimation == false) { 
			animation = new Animation_ShowRoundSummary(); 
		}
		
	}
	
	@Override
	public void performClose() {
		
		MovementHandler.allowMovement();
		if(this.noAnimation == false) { 
			animation.cancle();
			animation = null;
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		int x = WindowData.FrameWidth/2-this.width/2;
		int y = WindowData.FrameHeight/2-this.height/2;
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(x, y, this.width, this.height);
		
		if(this.noAnimation || animation != null) {
			
			RoundStatsContainer rsc = RoundData.statsContainer.get(this.displayedRound);
			Color positive = GameData.color_positiv;
			Color negative = GameData.color_negative;
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.CENTER_BASELINE, 30));
			g.drawString("Round "+rsc.getRoundNumber()+" Summary", x + 30, y + 45);
			
			//LEFT
			//MATERIALS
			if(this.noAnimation || this.animation.called > 1) {
				g.setColor(GameData.color_Material);
				g.setFont(new Font("Arial", Font.BOLD, 22));
				g.drawString("Total Materials: ", x+43, y+100+OnTopWindowData.roundSum_heightPerEco*0);
				g.setFont(new Font("Arial", Font.BOLD, 28));
				if(rsc.massBalance() >= 0) {
					g.setColor(positive);
					g.drawString("+"+rsc.massBalance(), x+220, y+103+OnTopWindowData.roundSum_heightPerEco*0);
				}else {
					g.setColor(negative);
					g.drawString(""+rsc.massBalance(), x+220, y+103+OnTopWindowData.roundSum_heightPerEco*0);
				}
				g.setColor(Color.LIGHT_GRAY);
				g.drawLine(x+50, y+110+OnTopWindowData.roundSum_heightPerEco*0, x+50, y+180+OnTopWindowData.roundSum_heightPerEco*0);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Produced: ", x+70, y+140+OnTopWindowData.roundSum_heightPerEco*0);
				g.setColor(positive);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("+"+rsc.massProduced(), x+190, y+140+OnTopWindowData.roundSum_heightPerEco*0);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Consumed: ", x+70, y+170+OnTopWindowData.roundSum_heightPerEco*0);
				g.setColor(negative);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("-"+rsc.massConsumed(), x+190, y+170+OnTopWindowData.roundSum_heightPerEco*0);
			}
			//ENERGY
			if(this.noAnimation || this.animation.called > 8) {
				g.setColor(GameData.color_Energy);
				g.setFont(new Font("Arial", Font.BOLD, 22));
				g.drawString("Total Energy: ", x+43, y+100+OnTopWindowData.roundSum_heightPerEco*1);
				g.setFont(new Font("Arial", Font.BOLD, 28));
				if(rsc.energyBalance() >= 0) {
					g.setColor(positive);
					g.drawString("+"+rsc.energyBalance(), x+220, y+103+OnTopWindowData.roundSum_heightPerEco*1);
				}else {
					g.setColor(negative);
					g.drawString(""+rsc.energyBalance(), x+220, y+103+OnTopWindowData.roundSum_heightPerEco*1);
				}
				g.setColor(Color.LIGHT_GRAY);
				g.drawLine(x+50, y+110+OnTopWindowData.roundSum_heightPerEco*1, x+50, y+180+OnTopWindowData.roundSum_heightPerEco*1);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Produced: ", x+70, y+140+OnTopWindowData.roundSum_heightPerEco*1);
				g.setColor(positive);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("+"+rsc.energyProduced(), x+190, y+140+OnTopWindowData.roundSum_heightPerEco*1);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Consumed: ", x+70, y+170+OnTopWindowData.roundSum_heightPerEco*1);
				g.setColor(negative);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("-"+rsc.energyConsumed(), x+190, y+170+OnTopWindowData.roundSum_heightPerEco*1);
			}
			//RESERACH
			if(this.noAnimation || this.animation.called > 15) {
				g.setColor(GameData.color_Research);
				g.setFont(new Font("Arial", Font.BOLD, 22));
				g.drawString("Total Research: ", x+43, y+100+OnTopWindowData.roundSum_heightPerEco*2);
				g.setFont(new Font("Arial", Font.BOLD, 28));
				if(rsc.researchBalance() >= 0) {
					g.setColor(positive);
					g.drawString("+"+rsc.researchBalance(), x+220, y+103+OnTopWindowData.roundSum_heightPerEco*2);
				}else {
					g.setColor(negative);
					g.drawString(""+rsc.researchBalance(), x+220, y+103+OnTopWindowData.roundSum_heightPerEco*2);
				}
				g.setColor(Color.LIGHT_GRAY);
				g.drawLine(x+50, y+110+OnTopWindowData.roundSum_heightPerEco*2, x+50, y+180+OnTopWindowData.roundSum_heightPerEco*2);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Produced: ", x+70, y+140+OnTopWindowData.roundSum_heightPerEco*2);
				g.setColor(positive);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("+"+rsc.researchProduced(), x+190, y+140+OnTopWindowData.roundSum_heightPerEco*2);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Consumed: ", x+70, y+170+OnTopWindowData.roundSum_heightPerEco*2);
				g.setColor(negative);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("-"+rsc.researchConsumed(), x+190, y+170+OnTopWindowData.roundSum_heightPerEco*2);
			}
			
			//CENTER LINE
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(x+this.width/2, y+50, x+this.width/2, y+this.height-90);
			//RIGHT
			
			int smallDistanceBetween = OnTopWindowData.roundSum_smallDistanceBetween, bigDistanceBetween = OnTopWindowData.roundSum_bigDistanceBetween;
			if(this.noAnimation || this.animation.called > 22) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Kills: ", x+this.width/2+40, y+100+smallDistanceBetween*0+bigDistanceBetween*0);
				g.setColor(positive);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString(""+rsc.getKills(), x+this.width/2+240, y+100+smallDistanceBetween*0+bigDistanceBetween*0);
			}
			if(this.noAnimation || this.animation.called > 23) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Deaths: ", x+this.width/2+40, y+100+smallDistanceBetween*1+bigDistanceBetween*0);
				g.setColor(negative);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString(""+rsc.getDeaths(), x+this.width/2+240, y+100+smallDistanceBetween*1+bigDistanceBetween*0);
			}
			if(this.noAnimation || this.animation.called > 24) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("KD total: ", x+this.width/2+40, y+100+smallDistanceBetween*2+bigDistanceBetween*0);
				if(rsc.getKDBalance() >= 0) { g.setColor(positive); }else { g.setColor(negative); }
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString(""+rsc.getKDBalance(), x+this.width/2+240, y+100+smallDistanceBetween*2+bigDistanceBetween*0);
			}
			if(this.noAnimation || this.animation.called > 25) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Damage dealt: ", x+this.width/2+40, y+100+smallDistanceBetween*2+bigDistanceBetween*1);
				g.setColor(positive);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString(""+rsc.getDamageDealt(), x+this.width/2+240, y+100+smallDistanceBetween*2+bigDistanceBetween*1);
			}
			if(this.noAnimation || this.animation.called > 26) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Damage received: ", x+this.width/2+40, y+100+smallDistanceBetween*3+bigDistanceBetween*1);
				g.setColor(negative);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("-"+rsc.getDamageReceived(), x+this.width/2+240, y+100+smallDistanceBetween*3+bigDistanceBetween*1);
			}
			if(this.noAnimation || this.animation.called > 27) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Damage total: ", x+this.width/2+40, y+100+smallDistanceBetween*4+bigDistanceBetween*1);
				if(rsc.damageBalance() >= 0) { g.setColor(positive); }else { g.setColor(negative); }
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString(""+rsc.damageBalance(), x+this.width/2+240, y+100+smallDistanceBetween*4+bigDistanceBetween*1);
			}
			if(this.noAnimation || this.animation.called > 28) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Buildings repaired: ", x+this.width/2+40, y+100+smallDistanceBetween*4+bigDistanceBetween*2);
				g.setColor(positive);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString(""+rsc.getRepaired(), x+this.width/2+240, y+100+smallDistanceBetween*4+bigDistanceBetween*2);
			}
			if(this.noAnimation || this.animation.called > 29) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Troups healed: ", x+this.width/2+40, y+100+smallDistanceBetween*5+bigDistanceBetween*2);
				g.setColor(positive);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString(""+rsc.getHealed(), x+this.width/2+240, y+100+smallDistanceBetween*5+bigDistanceBetween*2);
			}
			if(this.noAnimation || this.animation.called > 30) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Added HP total: ", x+this.width/2+40, y+100+smallDistanceBetween*6+bigDistanceBetween*2);
				g.setColor(positive);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString(""+rsc.addedHP(), x+this.width/2+240, y+100+smallDistanceBetween*6+bigDistanceBetween*2);
			}
			if(this.noAnimation || this.animation.called > 31) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Buildings build: ", x+this.width/2+40, y+100+smallDistanceBetween*6+bigDistanceBetween*3);
				g.setColor(positive);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString(""+rsc.getBuildAmount(), x+this.width/2+240, y+100+smallDistanceBetween*6+bigDistanceBetween*3);
			}
			if(this.noAnimation || this.animation.called > 32) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Troups produced: ", x+this.width/2+40, y+100+smallDistanceBetween*7+bigDistanceBetween*3);
				g.setColor(positive);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString(""+rsc.getProducedAmount(), x+this.width/2+240, y+100+smallDistanceBetween*7+bigDistanceBetween*3);
			}
			if(this.noAnimation || this.animation.called > 33) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Troups upgraded: ", x+this.width/2+40, y+100+smallDistanceBetween*8+bigDistanceBetween*3);
				g.setColor(positive);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString(""+rsc.getUpgradedAmount(), x+this.width/2+240, y+100+smallDistanceBetween*8+bigDistanceBetween*3);
			}
			
		}
		
	}
	
}
