package me.bejosch.battleprogress.client.Objects.OnTopWindow.Settings.A_Gameplay;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;

public class OnTopWindow_Settings_Gameplay extends OnTopWindow {
	
	
	public OnTopWindow_Settings_Gameplay() {
		super("Settings_Gameplay", OnTopWindowData.settings_width, OnTopWindowData.settings_height);
		// TODO Auto-generated constructor stub
	}

	//LINKS MENU ZUM SWITCHEN DER CATEGORIEN
	//UNTEN CLOSE UND SAVE BUTTON (rot und grün)
	//SAVE BUTTON RUFT IM OTW AUF... MAA STATUS WIRD TRANSFORMIERT IN ECHT GAME VARs
	
	@Override
	public void initOnOpen() {
		
		MovementHandler.cancleMovement();
		
		OnTopWindowHandler.performCancleResett();
		
	}
	
	@Override
	public void performClose() {
		
		MovementHandler.allowMovement();
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(WindowData.FrameWidth/2 - this.width/2, WindowData.FrameHeight/2 - this.height/2, width, height);
		
		g.setColor(Color.WHITE);
		g.drawRect(WindowData.FrameWidth/2 - this.width/2, WindowData.FrameHeight/2 - this.height/2, width, height);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 40));
		g.drawString("Settings", WindowData.FrameWidth/2 - this.width/2 + 20, WindowData.FrameHeight/2 - this.height/2 + 60);
		
		g.setColor(Color.WHITE);
		g.drawLine(WindowData.FrameWidth/2 - this.width/2+20+20+150, WindowData.FrameHeight/2 - this.height/2, WindowData.FrameWidth/2 - this.width/2+20+20+150, WindowData.FrameHeight/2 + this.height/2);
		
	}
	
}
