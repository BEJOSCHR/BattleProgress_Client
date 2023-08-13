package me.bejosch.battleprogress.client.Window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Debug.DebugWindow;
import me.bejosch.battleprogress.client.Handler.HoverHandler;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Window.Animations.AnimationDisplay;
import me.bejosch.battleprogress.client.Window.LabelParts.Display_1LoadingScreen;
import me.bejosch.battleprogress.client.Window.LabelParts.Display_2Menu;
import me.bejosch.battleprogress.client.Window.LabelParts.Display_3CreateMap;
import me.bejosch.battleprogress.client.Window.LabelParts.Display_4GameLobby;
import me.bejosch.battleprogress.client.Window.LabelParts.Display_5Game;
import me.bejosch.battleprogress.client.Window.LabelParts.Display_6GameFinish;
import me.bejosch.battleprogress.client.Window.OnTopWindow.OnTopWindowDisplay;

@SuppressWarnings("serial")
public class Label extends JLabel {

	//COORDS
	public int X = 0;
	public int Y = 0;
	
	//FPS
	public static int displayedFPS = 0;
	long nextSecond = System.currentTimeMillis() + 1000;
	int FramesInCurrentSecond = 0;
	int FramesInLastSecond = 0;
	
	static long nextRepaintDelay = 0;
	static int maxFPS = 60;
	
	//PING
	public static int displayedPING = 0;
	public static long lastSend_MiliSeconds = 0;
	static int durationBetweenServerAnswer = 0;
	static boolean newDataSent = false;
	
//==========================================================================================================
	/**
	 * The first settings of the new label - Constructor
	 * @param X - int - The x-coordinate in the frame this label should be
	 * @param Y - int - The y-coordinate in the frame this label should be
	 */
	public Label(int X, int Y) {
		
		this.X = X;
		this.Y = Y;
		
		setSize();
		this.setVisible(true);
		WindowData.Frame.add(this, BorderLayout.CENTER);
		
	}
	
//==========================================================================================================
	/**
	 * The methode sets the sice
	 */
	public void setSize() {		
		this.setBounds(X, Y, WindowData.FrameWidth, WindowData.FrameHeight);
	}
	
//==========================================================================================================
	/**
	 * The methode, called by the JLabel for the display parts
	 */
	protected void paintComponent(Graphics g) {
		
		//MAX FPS GRENZE SCHAFFEN
		long now = System.currentTimeMillis();
		try {
		   if (nextRepaintDelay > now) {
			   Thread.sleep(nextRepaintDelay - now);
		   }
		   nextRepaintDelay = now + 1000/(maxFPS-20);
		} catch (InterruptedException e) { }
		
		try{
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			switch(StandardData.spielStatus) {
//==============================================================
			case LoadingScreen:
				Display_1LoadingScreen.draw(g);
				break;
//==============================================================
			case Menu:
				Display_2Menu.draw(g);
				break;
//==============================================================
			case CreateMap:
				Display_3CreateMap.draw(g);
				break;
//==============================================================
			case GameLobby:
				Display_4GameLobby.draw(g);
				break;
//==============================================================
			case Game:
				Display_5Game.draw(g);
				break;
//==============================================================
			case GameFinish:
				Display_6GameFinish.draw(g);
				break;
			}
			
		}catch(NumberFormatException error) {}
		
		//OVERLAY PARTS
		
		//OTW
		if(OnTopWindowData.otwAnimationRunning == false) {
			//ONLY DRAW IF NO OPEN/CLOSE ANIMATION IS RUNNING
			OnTopWindowDisplay.drawOTW(g);
		}
		
		//HOVER MESSAGE
		HoverHandler.draw_HoverMessage(g);
		
		//ANIMATIONS
		AnimationDisplay.drawAnimations(g);
		
		//COORDINATES DEBUG
		if(DebugWindow.coordinatesDebug == true) {
			showMouseCoorindates(g, MouseHandler.mouseX, MouseHandler.mouseY);
		}
		
		//CALCULATE FPS
		calculateFPS();
		
		repaint();
	}
	
	
//==========================================================================================================
	/**
	 * Shows the mouse coordinates as debug
	 */
	private void showMouseCoorindates(Graphics g, int mouseX, int mouseY) {
		
		g.setColor(Color.RED);
		g.drawLine(0, mouseY, WindowData.FrameWidth, mouseY);
		g.drawLine(mouseX, 0, mouseX, WindowData.FrameHeight);
		
		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString(mouseX+":"+mouseY, mouseX+10, mouseY-20);
		
	}
	
//==========================================================================================================
	/**
	 * Updates the FPS
	 */
	private void calculateFPS() {
		long currentTime = System.currentTimeMillis();
		if(currentTime > nextSecond) {
			nextSecond += 1000;
			FramesInLastSecond = FramesInCurrentSecond;
			FramesInCurrentSecond = 0;
		}
		FramesInCurrentSecond++;
		displayedFPS = FramesInLastSecond;
	}
	
//==========================================================================================================
	/**
	 * Updates the Ping, so the answer time of the server - SEND
	 * @param sentMiliSeconds - long - The time in miliseconds when the data was sent
	 */
	public static void updatePingSent(long sentMiliSeconds) {
		
		if(newDataSent == false) {
			lastSend_MiliSeconds = sentMiliSeconds;
			newDataSent = true;
		}
		
	}
//==========================================================================================================
	/**
	 * Updates the Ping, so the answer time of the server - RECEIVE
	 * @param receiveMiliSeconds - long - The time in miliseconds when the answer was received
	 */
	public static void updatePingReceive(long receiveMiliSeconds) {
		
		if(lastSend_MiliSeconds <= receiveMiliSeconds && newDataSent == true) { 
			//SEND MUSS FR�HER ALS RECEIVE SEIN UND NUR UPDATE WENN DATA VON DIESEM CLIENT GESENDET WURDE
			displayedPING = (int) (receiveMiliSeconds-lastSend_MiliSeconds);
			newDataSent = false;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Get the currentPingValue
	 * @return int - The current Ping to display
	 */
	public static int getCurrentPingValue() {
		return displayedPING;
	}
//==========================================================================================================
	/**
	 * Get the currentFPSValue
	 * @return int - The current FPS to display
	 */
	public static int getCurrentFPSValue() {
		return displayedFPS;
	}
	
}
