package me.bejosch.battleprogress.client.Objects.OnTopWindow.FriendAdd;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;
import me.bejosch.battleprogress.client.Window.TextFields.TextFields;

public class OnTopWindow_FriendAdd extends OnTopWindow {
	
	public String nameErrorMessage = "Enter your friends username";
	
	public OnTopWindow_FriendAdd() {
		super("Menu_FriendAdd", OnTopWindowData.friendAdd_width, OnTopWindowData.friendAdd_height);
		
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
		
		TextFields.showTextField(TextFields.textField_friendAdd, "", 22, WindowData.FrameWidth/2-OnTopWindowData.friendAdd_textField_width/2, getY()+this.height-OnTopWindowData.friendAdd_textFieldDownBorder-OnTopWindowData.friendAdd_textField_height, OnTopWindowData.friendAdd_textField_width, OnTopWindowData.friendAdd_textField_height);
		TextFields.textField_friendAdd.requestFocus();
		
	}
	
	@Override
	public void performClose() {
		
		MovementHandler.allowMovement();
		
		TextFields.hideTextField(TextFields.textField_friendAdd);
		WindowData.Frame.requestFocus();
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(this.getX(), this.getY(), this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(this.getX(), this.getY(), this.width, this.height);
		
		//TITLE
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Arial", Font.BOLD, 32));
		String text = "Add your friend";
		int width = g.getFontMetrics().stringWidth(text);
		g.drawString(text, this.getX()+this.width/2-width/2, this.getY()+50);
		
		//TEXTFIELD
		
		//NAME EROR MESSAGE
		if(nameErrorMessage != null) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.PLAIN, 18));
			int width2 = g.getFontMetrics().stringWidth(nameErrorMessage);
			g.drawString(nameErrorMessage, this.getX()+this.width/2-width2/2, getY()+this.height-OnTopWindowData.friendAdd_textFieldDownBorder+20);
		}
		
	}
	
}
