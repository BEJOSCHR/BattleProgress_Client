package me.bejosch.battleprogress.client.Objects.OnTopWindow.TabGameInfo;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.net.URL;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class MAA_OTW_TabGameInfo_BugReport extends MouseActionArea {

	private static int width = 130, height = 25;
	
	public MAA_OTW_TabGameInfo_BugReport() {
		super(getX(), getY(), getX()+width, getY()+height
				, "OTW_TabGameInfo_BugReport", null, ShowBorderType.ShowAlways, Color.RED.brighter().brighter(), Color.WHITE);
		this.OTWMMA = true;
	}
	
	private static int getX() {
		return WindowData.FrameWidth/2-width/2;
	}
	private static int getY() {
		return OnTopWindowData.tabGameInfo_y+OnTopWindowData.tabGameInfo_height-height-5;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_TabGameInfo) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		//OPEN LINK TO BUG REPORT WEBSITE
		try {
	        Desktop.getDesktop().browse(new URL("http://bejosch.net/bugreport").toURI());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		
	}
	
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 15));
			String text = "Report a bug";
			int textWidth = g.getFontMetrics().stringWidth(text);
			g.drawString(text, getX()+width/2-textWidth/2, getY()+height-7);
		}
		
		super.draw(g);
		
	}
	
}
