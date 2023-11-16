package me.bejosch.battleprogress.client.Objects.OnTopWindow.ResearchConfirm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.ResearchData;
import me.bejosch.battleprogress.client.Game.Handler.Game_ResearchHandler;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Research.OnTopWindow_Research;
import me.bejosch.battleprogress.client.Objects.Research.Upgrades.Upgrade;

public class OnTopWindow_ResearchConfirm extends OnTopWindow {

	public Upgrade upgrade;
	
	public OnTopWindow_ResearchConfirm(Upgrade upgrade) {
		super("OTW_ResearchConfirm", OnTopWindowData.researchConfirm_width, OnTopWindowData.researchConfirm_height);
		
		this.upgrade = upgrade;
		
	}

	@Override
	public void initOnOpen() {
		
		MovementHandler.cancleMovement();
		
	}
	
	private boolean closed = false;
	
	@Override
	public void performClose() {
		
		if(this.closed == true) { return; }
//		MovementHandler.allowMovement();
		
		//REOPEN RESEARCH
		this.closed = true;
		OnTopWindowHandler.openOTW(new OnTopWindow_Research(), true);

		
	}

	@Override
	public void draw(Graphics g) {
		
		int x = WindowData.FrameWidth/2-this.width/2;
		int y = WindowData.FrameHeight/2-this.height/2;
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(x, y, this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 35));
		g.drawString(upgrade.getTitle(), x + OnTopWindowData.researchConfirm_textBorder, y + 50);
		
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 35));
		String text;
		if(Game_ResearchHandler.hasResearched(this.upgrade)) {
			g.setColor(Color.YELLOW);
			text = ""+this.upgrade.getDataContainer().researchCost;
		}else {
			if(this.enoughRPforResearch()) {
				g.setColor(Color.GREEN);
			}else {
				g.setColor(Color.RED);
			}
			text = ResearchData.researchPoints+" / "+this.upgrade.getDataContainer().researchCost;
		}
		g.drawString(text, x + this.width - (15*text.length()) - OnTopWindowData.researchConfirm_textBorder, y + 50);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 22));
		g.drawString(upgrade.getGeneralDescription(), x + OnTopWindowData.researchConfirm_textBorder, y + 115);
		
		if(Game_ResearchHandler.hasResearched(this.upgrade)) {
			g.setColor(Color.GREEN);
		}else {
			g.setColor(Color.ORANGE);
		}
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 24));
		g.drawString(upgrade.getEffectDescription(), x + OnTopWindowData.researchConfirm_textBorder, y + 160);
		
		
	}
	
	public boolean enoughRPforResearch() {
		return ResearchData.researchPoints >= this.upgrade.getDataContainer().researchCost;
	}
	
}
