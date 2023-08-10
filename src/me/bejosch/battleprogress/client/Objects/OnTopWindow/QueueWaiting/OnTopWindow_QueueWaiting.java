package me.bejosch.battleprogress.client.Objects.OnTopWindow.QueueWaiting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;

public class OnTopWindow_QueueWaiting extends OnTopWindow {
	
	public SpielModus queueType;
	public int waited_Sek = 0;
	public int waited_Min = 0;
	
	private Timer timer;
	
	public OnTopWindow_QueueWaiting(SpielModus queueType, int waitedSeconds) {
		super("Menu_QueueWaiting", OnTopWindowData.queueWaiting_width, OnTopWindowData.queueWaiting_height);
		
		this.queueType = queueType;
		this.waited_Min = waitedSeconds/60;
		this.waited_Sek = waitedSeconds-(waited_Min*60);
		
	}

	private void startWaitingTimer() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				
				waited_Sek++;
				
				if(waited_Sek == 60) {
					waited_Sek = 0;
					waited_Min++;
					
					if(waited_Min == 61) {
						waited_Min = 60; //OVERFLOW
					}
				}
				
			}
		}, 0, 1000); //EVERY SEK
	}
	private void stopWaitingTimer() {
		timer.cancel();
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
		
		startWaitingTimer();
		
	}
	
	@Override
	public void performClose() {
		
		MovementHandler.allowMovement();
		
		stopWaitingTimer();
		
	}

	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(this.getX(), this.getY(), this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(this.getX(), this.getY(), this.width, this.height);
		
		//TYPE
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 18));
		String text2 = getNameByType(queueType);
		int width2 = g.getFontMetrics().stringWidth(text2);
		g.drawString(text2, this.getX()+this.width/2-width2/2, this.getY()+40);	
		
		//TITLE
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Arial", Font.BOLD, 28));
		String text = "Searching for a game";
		int width = g.getFontMetrics().stringWidth(text);
		g.drawString(text, this.getX()+this.width/2-width/2, this.getY()+90);	
		
		//WAITING TIME
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 22));
		String text3 = Funktions.getDoubleWritenNumber(waited_Min)+":"+Funktions.getDoubleWritenNumber(waited_Sek);
		int width3 = g.getFontMetrics().stringWidth(text3);
		g.drawString(text3, this.getX()+this.width/2-width3/2, this.getY()+140);		
		
	}
	
	private String getNameByType(SpielModus type) {
		switch(type) {
		case Normal_2v2: return " Normal - 2v2";
		case Normal_1v1: return " Normal - 1v1";
		case Ranked_1v1: return " Ranked - 1v1";
		default: return "";
		}
	}
	
}
