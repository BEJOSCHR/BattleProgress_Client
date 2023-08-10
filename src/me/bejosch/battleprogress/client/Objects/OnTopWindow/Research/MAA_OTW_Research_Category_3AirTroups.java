package me.bejosch.battleprogress.client.Objects.OnTopWindow.Research;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ResearchCategory;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class MAA_OTW_Research_Category_3AirTroups extends MouseActionArea {
	
	private ResearchCategory thisCategory = ResearchCategory.AirTroups;
	
	public MAA_OTW_Research_Category_3AirTroups() {
		super(getX(), getY()
				, getX()+OnTopWindowData.research_categoryButton_width, getY()+getHeight()
				, "OTW_Research_Category_3AirTroups", null, ShowBorderType.ShowAlways, Color.LIGHT_GRAY, OnTopWindowData.research_categoryButton_highlightColor);
		this.OTWMMA = true;
	}
	
	private static int getX() {
		int pos = 2;
		return WindowData.FrameWidth/2-OnTopWindowData.research_width/2+OnTopWindowData.research_categoryButton_xOffset+(pos*(OnTopWindowData.research_categoryButton_borderBetween+OnTopWindowData.research_categoryButton_width));
	}
	private static int getY() {
		return WindowData.FrameHeight/2-OnTopWindowData.research_height/2+OnTopWindowData.research_devideLine_yOffset+OnTopWindowData.research_categoryButton_borderTop;
	}
	private static int getHeight() {
		return OnTopWindowData.research_devideLine_height-OnTopWindowData.research_categoryButton_borderTop;
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
		
		if(OnTopWindowData.research_category != thisCategory) {
			OnTopWindowData.research_category = thisCategory;
			OnTopWindowData.research_scrollPos = 0;
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {  
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true || OnTopWindowData.research_category == thisCategory) {
				//HOVER
				g.setColor(hoverColor);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				g.drawString("Air Troups", this.xTL+22, this.yTL+25);
			}else {
				//NO HOVER
				g.setColor(standardColor);
				g.setFont(new Font("Arial", Font.PLAIN, 19));
				g.drawString("Air Troups", this.xTL+21, this.yTL+25);
			}
		}
		
		super.draw(g);
		
	}
	
}
