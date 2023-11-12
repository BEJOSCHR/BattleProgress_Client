package me.bejosch.battleprogress.client.Window.OnTopWindow;

import java.awt.Color;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Game.Draw.Game_DrawOverlay;

public class OnTopWindowDisplay {

	public static void drawOTW(Graphics g) {
		
		if(OnTopWindowData.onTopWindow != null) {
			
			//DARK BACKGROUND
			g.setColor(new Color(0, 0, 0, 200));
			g.fillRect(0, 0, WindowData.FrameWidth+2*WindowData.rahmen, WindowData.FrameHeight+2*WindowData.rahmen);
			
			OnTopWindowData.onTopWindow.draw(g);
			Game_DrawOverlay.draw_MouseActionAreas(g, true);
			
		}
		
	}
	
}
