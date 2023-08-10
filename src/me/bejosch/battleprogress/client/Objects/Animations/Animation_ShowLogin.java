package me.bejosch.battleprogress.client.Objects.Animations;

import java.awt.Color;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.AnimationType;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.InfoMessage.OnTopWindow_InfoMessage;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Login.OnTopWindow_Login;
import me.bejosch.battleprogress.client.ServerConnection.ServerConnection;

public class Animation_ShowLogin extends Animation {
	
	public Animation_ShowLogin() {
		super(AnimationType.ShowLogin);
		
		//START MENU INIT
		if(ServerConnection.VerbundenZumServer == true) {
			//CONNECTED - OPEN LOGIN
			OnTopWindowHandler.openOTW(new OnTopWindow_Login());
		}else {
			//OPEN ERROR MESSAGE
			OnTopWindowHandler.openOTW(new OnTopWindow_InfoMessage("No connection!", "A connection to the server could not be established!", "For more information check out our discord...", "Please try again later", true));
		}
		
	}

	@Override
	public void getParametersFromType() {
		speed = 15;
		faktor = 255;
		super.getParametersFromType();
	}
	
	@Override
	public void changeAction() {
		if(called == 0) {
			faktor = 255;
		}else if(called <= 40) {
			faktor -= 7;
		}else if(called <= 60) {
			faktor = 0;
		}else if(called <= 95) {
			//finished
			this.cancle();
		}
		super.changeAction();
	}
	
	@Override
	public void drawPart(Graphics g) {
		
		if(cancled == true) { return; }
		
		if(faktor > 255) { faktor = 255; }
		else if(faktor < 0) { faktor = 0; }
		
		//ABDUNKELN
		g.setColor(new Color(0, 0, 0, faktor));
		g.fillRect(0, 0, WindowData.FrameWidth+2*WindowData.rahmen, WindowData.FrameHeight+2*WindowData.rahmen);
		
		super.drawPart(g);
	}
	
	@Override
	public void cancle() {
		super.cancle();
	}
	
}
