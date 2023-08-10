package me.bejosch.battleprogress.client.Objects.OnTopWindow.Login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.FileData;
import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Handler.FileHandler;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;
import me.bejosch.battleprogress.client.Window.TextFields.TextFields;

public class OnTopWindow_Login extends OnTopWindow {

	
	
	public OnTopWindow_Login() {
		super("OTW_Login", OnTopWindowData.login_width, OnTopWindowData.login_height);
		
	}
	
	private int getX() {
		return WindowData.FrameWidth/2-this.width/2;
	}
	private int getY() {
		return WindowData.FrameHeight/2-this.height/2;
	}
	
	@Override
	public void initOnOpen() {
		
		OnTopWindowData.login_message = "Login or create a new account for free!";
		
		TextFields.showTextField(TextFields.textField_userName, FileHandler.readOutData(FileData.file_Settings, "UserName"), 20, WindowData.FrameWidth/2-OnTopWindowData.login_textFieldWidth/2, getY()+OnTopWindowData.login_topSpacerHeight+(1*OnTopWindowData.login_textFieldSectionHeight), OnTopWindowData.login_textFieldWidth, OnTopWindowData.login_textFieldHeight);
		TextFields.showTextField(TextFields.textField_password, "*****", 20, WindowData.FrameWidth/2-OnTopWindowData.login_textFieldWidth/2, getY()+OnTopWindowData.login_topSpacerHeight+(2*OnTopWindowData.login_textFieldSectionHeight), OnTopWindowData.login_textFieldWidth, OnTopWindowData.login_textFieldHeight);
															//TODO ^^ REMOVE benno AND PRESET VAR STATE to ""
		MovementHandler.cancleMovement();
		
	}
	
	@Override
	public void performClose() {
		
		FileHandler.saveDataInFile(FileData.file_Settings, "Name", TextFields.textField_userName.getText());
		
		TextFields.hideTextField(TextFields.textField_userName);
		TextFields.hideTextField(TextFields.textField_password);
		
		MovementHandler.allowMovement();
		
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
		g.drawString("Please Login", this.getX()+30, this.getY()+45);
		
		//DEVIDER
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(getX(), getY()+OnTopWindowData.login_topSpacerHeight, getX()+this.width, getY()+OnTopWindowData.login_topSpacerHeight);
		
		//TEXT
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 18));
		g.drawString("Username", WindowData.FrameWidth/2-OnTopWindowData.login_textFieldWidth/2+5, getY()+OnTopWindowData.login_topSpacerHeight+(1*OnTopWindowData.login_textFieldSectionHeight)-5);
		g.drawString("Password", WindowData.FrameWidth/2-OnTopWindowData.login_textFieldWidth/2+5, getY()+OnTopWindowData.login_topSpacerHeight+(2*OnTopWindowData.login_textFieldSectionHeight)-5);
		
		//MESSAGE
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 18));
		int messageWidth = g.getFontMetrics().stringWidth(OnTopWindowData.login_message);
		g.drawString(OnTopWindowData.login_message, getX()+this.width/2-messageWidth/2, getY()+this.height-110);
		
	}
}
