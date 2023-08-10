package me.bejosch.battleprogress.client.Objects.OnTopWindow.InfoMessage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;

public class OnTopWindow_InfoMessage extends OnTopWindow {

	public String title;
	public String line1, line2, line3;
	
	public boolean criticalErrorDisplay = false;
	
	public OnTopWindow_InfoMessage(String title, String line1, String line2, String line3, boolean criticalErrorDisplay) {
		super("OTW_InfoMessage", OnTopWindowData.infoMessage_width, OnTopWindowData.infoMessage_height);
		
		this.title = title;
		this.line1 = line1;
		this.line2 = line2;
		this.line3 = line3;
		
		this.criticalErrorDisplay = criticalErrorDisplay;
		
	}
	
	private int getX() {
		return WindowData.FrameWidth/2-this.width/2;
	}
	private int getY() {
		return WindowData.FrameHeight/2-this.height/2;
	}
	
	@Override
	public void initOnOpen() {
		
		MovementHandler.cancleMovement();
		
	}
	
	@Override
	public void performClose() {
		
		MovementHandler.allowMovement();
		
		if(this.criticalErrorDisplay == true) {
			//CRITICAL SO EXIT
			System.exit(0);
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(this.getX(), this.getY(), this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(this.getX(), this.getY(), this.width, this.height);
		
		//TITLE
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 34));
		g.drawString(this.title, this.getX()+30, this.getY()+45);
		
		//DEVIDER
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(getX(), getY()+60, getX()+this.width, getY()+60);
		
		//LINEs
		int heightPerLine = 30;
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
		g.drawString(this.line1, this.getX()+30, this.getY()+60+15+(1*heightPerLine));
		g.drawString(this.line2, this.getX()+30, this.getY()+60+15+(2*heightPerLine));
		g.drawString(this.line3, this.getX()+30, this.getY()+60+15+(3*heightPerLine));
		
	}
}
