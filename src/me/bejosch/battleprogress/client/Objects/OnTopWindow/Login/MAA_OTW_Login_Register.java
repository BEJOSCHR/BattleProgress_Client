package me.bejosch.battleprogress.client.Objects.OnTopWindow.Login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.ServerConnection.ServerConnection;
import me.bejosch.battleprogress.client.Window.TextFields.TextFields;

public class MAA_OTW_Login_Register extends MouseActionArea {

	public MAA_OTW_Login_Register() {
		super(WindowData.FrameWidth/2+OnTopWindowData.login_button_spaceBetween/2, WindowData.FrameHeight/2+OnTopWindowData.login_height/2-OnTopWindowData.generallConfirm_MAA_height-20
				, WindowData.FrameWidth/2+OnTopWindowData.login_button_spaceBetween/2+OnTopWindowData.login_button_width, WindowData.FrameHeight/2+OnTopWindowData.login_height/2-20
				, "OTW_Login_Register", null, ShowBorderType.ShowAlways, Color.WHITE, Color.ORANGE);
		this.OTWMMA = true;
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_Login) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		String username = TextFields.textField_userName.getText();
		String password = OnTopWindowData.login_password;
		
		String userNameValidation = Funktions.checkForUsernameValidation(username);
		if(userNameValidation != null) {
			OnTopWindowData.login_message = userNameValidation;
			return;
		}
		
		String passwordValidation = Funktions.checkForPaswordValidation(password);
		if(passwordValidation != null) {
			OnTopWindowData.login_message = passwordValidation;
			return;
		}
		
		ServerConnection.sendData(200, ServerConnection.getNewPacketId(), username+";"+password);
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {  
			if(checkArea(MouseHandler.mouseX, MouseHandler.mouseY) == true) {
				//HOVER
				g.setColor(hoverColor);
				g.setFont(new Font("Arial", Font.PLAIN, 21));
				g.drawString("Register", this.xTL+30, this.yBR-16);
			}else {
				//NO HOVER
				g.setColor(standardColor);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				g.drawString("Register", this.xTL+31, this.yBR-15);
			}
		}
		
		super.draw(g);
		
	}
	
}
