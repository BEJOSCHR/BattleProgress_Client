package me.bejosch.battleprogress.client.Objects.OnTopWindow.ResearchConfirm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class MAA_OTW_ResearchConfirm_Close extends MouseActionArea {
	
	public MAA_OTW_ResearchConfirm_Close() {
		super(getX(), getY(), getX()+OnTopWindowData.researchConfirm_buttonWidth, getY()+OnTopWindowData.researchConfirm_buttonHeight, 
				"OTW_ResearchConfirm_Close", null, ShowBorderType.ShowAlways, Color.BLACK, Color.RED);
		this.OTWMMA = true;
	}
	
	private static int getX() {
		int i = 1;
		return WindowData.FrameWidth/2-OnTopWindowData.researchConfirm_width/2+OnTopWindowData.researchConfirm_textBorder+i*(OnTopWindowData.researchConfirm_buttonWidth+OnTopWindowData.researchConfirm_buttonBorderBetween);
	}
	private static int getY() {
		return WindowData.FrameHeight/2+OnTopWindowData.researchConfirm_height/2-OnTopWindowData.researchConfirm_buttonHeight-OnTopWindowData.researchConfirm_buttonBottomBorder;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_ResearchConfirm) {
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
			//TEXT
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 26));
			g.drawString("Cancel", this.xTL+27, this.yTL+OnTopWindowData.researchConfirm_buttonHeight-15);
		}
		
		super.draw(g);
		
	}
	
}
