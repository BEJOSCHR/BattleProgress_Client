package me.bejosch.battleprogress.client.Objects.OnTopWindow.Dictionary;

import java.awt.Color;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class MAA_OTW_Dictionary_Close extends MouseActionArea {

	public MAA_OTW_Dictionary_Close() {
		super(WindowData.FrameWidth/2+OnTopWindowData.dictionary_width/2-OnTopWindowData.friendRequests_smallButtonMaße-10, WindowData.FrameHeight/2-OnTopWindowData.dictionary_height/2+10
				, WindowData.FrameWidth/2+OnTopWindowData.dictionary_width/2-10, WindowData.FrameHeight/2-OnTopWindowData.dictionary_height/2+OnTopWindowData.friendRequests_smallButtonMaße+10
				, "OTW_Dictionary_Close", null, ShowBorderType.ShowAlways, Color.WHITE, Color.LIGHT_GRAY.darker());
		this.OTWMMA = true;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_Dictionary) {
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
