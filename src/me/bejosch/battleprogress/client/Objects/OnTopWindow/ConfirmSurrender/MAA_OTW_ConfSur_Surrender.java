package me.bejosch.battleprogress.client.Objects.OnTopWindow.ConfirmSurrender;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class MAA_OTW_ConfSur_Surrender extends MouseActionArea {

	private static int partWidth = OnTopWindowData.confSur_width/3;
	
	public MAA_OTW_ConfSur_Surrender() {
		super(WindowData.FrameWidth/2-partWidth-partWidth/4, WindowData.FrameHeight/2+OnTopWindowData.confSur_MAA_yOffSet
				, WindowData.FrameWidth/2-partWidth/4, WindowData.FrameHeight/2+OnTopWindowData.confSur_MAA_yOffSet+OnTopWindowData.confSur_MAA_height
				, "OTW_ConfSur_Surrender", null, ShowBorderType.ShowAlways, Color.WHITE, Color.RED);
		this.OTWMMA = true;
	}

	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_ConfirmSurrender) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		//SURRENDER BZW LEAVE THE GAME
		System.exit(0);
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {  
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				//HOVER
				g.setColor(hoverColor);
				g.setFont(new Font("Arial", Font.PLAIN, 21));
				g.drawString("Surrender", this.xTL+53, this.yTL+29);
			}else {
				//NO HOVER
				g.setColor(hoverColor); //HOVERCOLOR FOR WARNING
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				g.drawString("Surrender", this.xTL+54, this.yTL+30);
			}
		}
		
		super.draw(g);
		
	}
	
}
