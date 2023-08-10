package me.bejosch.battleprogress.client.Objects.OnTopWindow.Research;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.ResearchData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class MAA_OTW_Research_RPDisplay extends MouseActionArea {
	
	private static String[] hovertext = {"ResearchPoints"};
	
	public MAA_OTW_Research_RPDisplay() {
		super(getX(), getY(), getX()+getWidth(), getY()+40, "OTW_Research_RPDisplay", hovertext, ShowBorderType.ShowAlways, Color.BLACK, Color.BLACK);
		this.OTWMMA = true;
	}
	
	private static int getX() {
		return WindowData.FrameWidth/2-OnTopWindowData.research_width/2 + OnTopWindowData.research_TopInfoMAA_sideBorder;
	}
	private static int getY() {
		return WindowData.FrameHeight/2-OnTopWindowData.research_height/2 + OnTopWindowData.research_TopInfoMAA_topBorder;
	}
	
	private static String getText() {
		return ResearchData.researchPoints+" RP";
	}
	
	private static int getWidth() {
		return 18*(getText().length())+20+20;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_Research) {
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
		
		if(this.isActiv() == true) {  
			//BACKGROUND
			g.setColor(Color.DARK_GRAY.brighter());
			g.fillRect(this.xTL, this.yTL, this.xBR-this.xTL, this.yBR-this.yTL);
			//TEXT
			g.setColor(new Color(255, 0, 255)); //PURPLE
			g.setFont(new Font("Arial", Font.BOLD, 26));
			g.drawString(getText(), this.xTL+20, this.yTL+30);
		}
		
		super.draw(g);
		
	}
	
}
