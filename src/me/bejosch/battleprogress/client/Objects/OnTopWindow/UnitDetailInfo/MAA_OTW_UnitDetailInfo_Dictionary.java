package me.bejosch.battleprogress.client.Objects.OnTopWindow.UnitDetailInfo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Dictionary.OnTopWindow_Dictionary;

public class MAA_OTW_UnitDetailInfo_Dictionary extends MouseActionArea {

	public MAA_OTW_UnitDetailInfo_Dictionary() {
		super(WindowData.FrameWidth/2-20-OnTopWindowData.generalConfirm_MAA_width, WindowData.FrameHeight/2+OnTopWindowData.unitDetailInfo_height/2-OnTopWindowData.generallConfirm_MAA_height-20
				, WindowData.FrameWidth/2-20, WindowData.FrameHeight/2+OnTopWindowData.unitDetailInfo_height/2-20
				, "OTW_UnitDetailInfo_Dictionary", null, ShowBorderType.ShowAlways, Color.WHITE, Color.ORANGE);
		this.OTWMMA = true;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_UnitDetailInfo) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		OnTopWindowHandler.openOTW(new OnTopWindow_Dictionary(), true);
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {  
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				//HOVER
				g.setColor(hoverColor);
				g.setFont(new Font("Arial", Font.PLAIN, 21));
				g.drawString("Dictionary", this.xTL+33, this.yBR-16);
			}else {
				//NO HOVER
				g.setColor(standardColor);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				g.drawString("Dictionary", this.xTL+34, this.yBR-15);
			}
		}
		
		super.draw(g);
		
	}
	
}
