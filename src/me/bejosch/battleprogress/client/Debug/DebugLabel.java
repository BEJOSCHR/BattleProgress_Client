package me.bejosch.battleprogress.client.Debug;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class DebugLabel extends JLabel {

	long nextSecond = System.currentTimeMillis() + 1000;
	int FramesInCurrentSecond = 0;
	int FramesInLastSecond = 0;
	
	static long nextRepaintDelay = 0;
	static int maxFPS = 200;
	
//==========================================================================================================
	/**
	 * The first settings of the new label - Constructor
	 */
	public DebugLabel() {
		
		this.setBounds(0, 0, DebugWindow.DebugFrame.getWidth(), DebugWindow.DebugFrame.getHeight());
		this.setVisible(true);
		DebugWindow.DebugFrame.add(this);
		
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
			
			this.setBounds(0, 0, DebugWindow.DebugFrame.getWidth(), DebugWindow.DebugFrame.getHeight());
			if(DebugWindow.DebugScrollPane != null) {
				DebugWindow.DebugScrollPane.setBounds(0, 0, DebugWindow.DebugFrame.getWidth()-17, DebugWindow.DebugFrame.getHeight()-40);
			}
			
			DebugHandler.updateTextArea();
			
//			g.setColor(Color.DARK_GRAY);
//			g.fillRect(0, 0, DebugWindow.DebugFrame.getWidth(), DebugWindow.DebugFrame.getHeight());
			
		}catch(NumberFormatException error) {}
		
		//OVERLAY PARTS
		
		//FPS ANZEIGE
		//FPS_Anzeige(g);
		
		repaint();
	}
	
	
	
	
	@SuppressWarnings("unused")
	private void FPS_Anzeige(Graphics g) {
		long currentTime = System.currentTimeMillis();
		if(currentTime > nextSecond) {
			nextSecond += 1000;
			FramesInLastSecond = FramesInCurrentSecond;
			FramesInCurrentSecond = 0;
		}
		FramesInCurrentSecond++;
		String Nachricht = "FPS: "+FramesInLastSecond;
		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.drawString(Nachricht, DebugWindow.DebugFrame.getWidth()-(Nachricht.length()*6)-32, 25);
	}
	
}
