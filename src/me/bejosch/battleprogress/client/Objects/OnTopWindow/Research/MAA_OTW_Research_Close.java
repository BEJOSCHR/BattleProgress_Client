package me.bejosch.battleprogress.client.Objects.OnTopWindow.Research;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class MAA_OTW_Research_Close extends MouseActionArea {
	
	public MAA_OTW_Research_Close() {
		super(getX(), getY(), getX()+getWidth(), getY()+40, "OTW_Research_Close", null, ShowBorderType.ShowAlways, Color.BLACK, null);
		this.OTWMMA = true;
	}
	
	private static int getX() {
		return WindowData.FrameWidth/2+OnTopWindowData.research_width/2 - OnTopWindowData.research_TopInfoMAA_sideBorder - getWidth();
	}
	private static int getY() {
		return WindowData.FrameHeight/2-OnTopWindowData.research_height/2 + OnTopWindowData.research_TopInfoMAA_topBorder;
	}
	
	private static String getText() {
		return "Close";
	}
	
	private static int getWidth() {
		return 15*(getText().length())+20+20;
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
		
		OnTopWindowHandler.closeOTW();
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {  
			//BACKGROUND
			g.setColor(Color.DARK_GRAY.brighter());
			g.fillRect(this.xTL, this.yTL, this.xBR-this.xTL, this.yBR-this.yTL);
			//TEXT
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 26));
			g.drawString(getText(), this.xTL+20, this.yTL+30);
		}
		
		super.draw(g);
		
	}
	
}
