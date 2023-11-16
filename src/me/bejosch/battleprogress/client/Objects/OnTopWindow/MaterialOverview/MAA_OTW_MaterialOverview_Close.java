package me.bejosch.battleprogress.client.Objects.OnTopWindow.MaterialOverview;

import java.awt.Color;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class MAA_OTW_MaterialOverview_Close extends MouseActionArea {

	public MAA_OTW_MaterialOverview_Close() {
		super(WindowData.FrameWidth/2+OnTopWindowData.materialOverview_width/2-OnTopWindowData.friendRequests_smallButtonMaße-20, WindowData.FrameHeight/2-OnTopWindowData.materialOverview_height/2+15
				, WindowData.FrameWidth/2+OnTopWindowData.materialOverview_width/2-20, WindowData.FrameHeight/2-OnTopWindowData.materialOverview_height/2+OnTopWindowData.friendRequests_smallButtonMaße+15
				, "OTW_MaterialOverview_Close", null, ShowBorderType.ShowAlways, Color.WHITE, Color.LIGHT_GRAY.darker());
		this.OTWMMA = true;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_MaterialOverview) {
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
			g.drawImage(Images.menuIcon_friendRequest_Decline, this.xTL+1, this.yTL+1, null);
		}
		
		super.draw(g);
		
	}
	
}
