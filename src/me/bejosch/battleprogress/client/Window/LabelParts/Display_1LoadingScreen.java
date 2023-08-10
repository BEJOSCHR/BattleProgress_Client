package me.bejosch.battleprogress.client.Window.LabelParts;

import java.awt.Color;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Display_1LoadingScreen {
	
//==========================================================================================================
	/**
	 * The methode, called by the Label for display this part
	 */
	public static void draw(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WindowData.FrameWidth+2*WindowData.rahmen, WindowData.FrameHeight+2*WindowData.rahmen);
		
		g.drawImage(Images.login_background, 0, 0, null);
		
	}
	
}
