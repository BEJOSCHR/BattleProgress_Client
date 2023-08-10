package me.bejosch.battleprogress.client.Window.TextFields;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;

public class TextFields {

	public static JTextField textField_userName;
	public static JTextField textField_password;
	
	public static JTextField textField_playerChat;
	public static JTextField textField_friendAdd;
	
	public static JTextField textField_Chat;
	public static JTextField textField_createMapName;
	
//==========================================================================================================
	/**
	 * Loads all used textFields
	 **/
	public static void loadTextFields() {
		
		textField_userName = Funktions.createTextField();
		textField_password = Funktions.createTextField();
		textField_password.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					if(OnTopWindowData.login_password.length() > 0) {
						int currentLength = textField_password.getText().length();
						int diff = OnTopWindowData.login_password.length()-currentLength;
						OnTopWindowData.login_password = OnTopWindowData.login_password.substring(0, OnTopWindowData.login_password.length()-diff);
					}
				}else {
					OnTopWindowData.login_password = OnTopWindowData.login_password+e.getKeyChar();
				}
				textField_password.setText(getHiddenText(OnTopWindowData.login_password.length()));
			}
			
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		textField_password.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				if(textField_password.getCaretPosition() != textField_password.getText().length()) {
					textField_password.setCaretPosition(textField_password.getText().length());
				}
			}
		});
		
		textField_playerChat = Funktions.createTextField();
		textField_playerChat.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					GameHandler.getMouseActionAreaByName("OTW_PlayerChat_Send").performAction_LEFT_RELEASE();
				}else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					OnTopWindowHandler.closeOTW();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		
		textField_friendAdd = Funktions.createTextField();
		textField_friendAdd.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					GameHandler.getMouseActionAreaByName("OTW_FriendAdd_Add").performAction_LEFT_RELEASE();
				}else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					OnTopWindowHandler.closeOTW();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		
		textField_Chat = Funktions.createTextField();
		textField_Chat.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) { }

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					GameHandler.getMouseActionAreaByName("Chat_Send").performAction_LEFT_RELEASE();
				}else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					GameHandler.getMouseActionAreaByName("Chat_Hide").performAction_LEFT_RELEASE();
				}
			}
			
		});
		
		textField_createMapName = Funktions.createTextField();
		
	}
	
	private static String getHiddenText(int length) {
		String str = "";
		for(int i = 0 ; i < length ; i++) { str = str+"*"; }
		return str;
	}
	
//==========================================================================================================
	/**
	 * Shows the named textField
	 * @param textField - JtextField - The textField witch should be shown
	 * @param text - String - The text witch is displayed on the textField
	 * @param textSize - int - The size of the displayed text
	 * @param X - int - The X coordinate of the textField
	 * @param Y - int - The Y coordinate of the textField
	 * @param width - int - The width of the textField
	 * @param hight - int - The hight of the textField
	 **/
	public static void showTextField(JTextField textField, String text, int textSize, int X, int Y, int width, int hight) {
		
		if(text != null) {
			textField.setText(text);
		}
		textField.setBounds(X, Y, width, hight);
		textField.setFont(new Font("Arial", Font.BOLD, textSize));
		textField.setVisible(true);
		//textField.requestFocus();
		
	}
	
//==========================================================================================================
	/**
	 * Hide the named textField
	 * @param textField - JtextField - The textField witch should be hiden
	 **/
	public static void hideTextField(JTextField textField) {
		
		textField.setVisible(false);
		
	}
	
//==========================================================================================================
	/**
	 * Hide all textFields
	 **/
	public static void hideAlltextFields() {
		
		hideTextField(textField_userName);
		hideTextField(textField_password);
		
		hideTextField(textField_playerChat);
		hideTextField(textField_friendAdd);
		
		hideTextField(textField_Chat);
		hideTextField(textField_createMapName);
		
	}
	
}
