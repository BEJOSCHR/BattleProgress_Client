package me.bejosch.battleprogress.client.Window.Buttons;

import java.awt.Font;

import javax.swing.JButton;

import me.bejosch.battleprogress.client.Funktions.Funktions;

public class Buttons {
	
	public static JButton button_DeleteMap;
	public static JButton button_ConfirmDeleteMap;
	public static JButton button_ClearMap;
	public static JButton button_ConfirmClearMap;
	public static JButton button_LoadMap;
	public static JButton button_ConfirmLoadMap;
	public static JButton button_SaveMap;
	public static JButton button_ConfirmSaveMap;
	
	public static JButton button_QuitEditor;
	
	public static JButton button_NextField;
	public static JButton button_LaterField;
	public static JButton button_NextMap;
	public static JButton button_LaterMap;
	public static JButton button_NextTeam;
	public static JButton button_LaterTeam;
	
	public static JButton button_StartGame;
	
//==========================================================================================================
	/**
	 * Loads all used buttons
	 **/
	public static void loadButtons() {
		
		button_DeleteMap = Funktions.createButton();
		button_ConfirmDeleteMap = Funktions.createButton();
		button_ClearMap = Funktions.createButton();
		button_ConfirmClearMap = Funktions.createButton();
		button_LoadMap = Funktions.createButton();
		button_ConfirmLoadMap = Funktions.createButton();
		button_SaveMap = Funktions.createButton();
		button_ConfirmSaveMap = Funktions.createButton();
		
		button_QuitEditor = Funktions.createButton();
		
		button_NextField = Funktions.createButton();
		button_LaterField = Funktions.createButton();
		button_NextMap = Funktions.createButton();
		button_LaterMap = Funktions.createButton();
		button_NextTeam = Funktions.createButton();
		button_LaterTeam = Funktions.createButton();
		
		button_StartGame = Funktions.createButton();
		
	}
	
//==========================================================================================================
	/**
	 * Shows the named button
	 * @param button - JButton - The button witch should be shown
	 * @param text - String - The text witch is displayed on the button
	 * @param textSize - int - The size of the displayed text
	 * @param X - int - The X coordinate of the button
	 * @param Y - int - The Y coordinate of the button
	 * @param width - int - The width of the button
	 * @param hight - int - The hight of the button
	 **/
	public static void showButton(JButton button, String text, int textSize, int X, int Y, int width, int hight) {
		
		button.setText(text);
		button.setBounds(X, Y, width, hight);
		button.setFont(new Font("Arial", Font.BOLD, textSize));
		button.setVisible(true);
		button.requestFocus();
		
	}
	
//==========================================================================================================
	/**
	 * Hide the named button
	 * @param button - JButton - The button witch should be hiden
	 **/
	public static void hideButton(JButton button) {
		
		button.setVisible(false);
		
	}
	
//==========================================================================================================
	/**
	 * Hide all buttons
	 **/
	public static void hideAllButtons() {
		
		hideButton(button_DeleteMap);
		hideButton(button_ConfirmDeleteMap);
		hideButton(button_ClearMap);
		hideButton(button_ConfirmClearMap);
		hideButton(button_LoadMap);
		hideButton(button_ConfirmLoadMap);
		hideButton(button_SaveMap);
		hideButton(button_ConfirmSaveMap);
		
		hideButton(button_QuitEditor);
		
		hideButton(button_NextField);
		hideButton(button_LaterField);
		hideButton(button_NextMap);
		hideButton(button_LaterMap);
		hideButton(button_NextTeam);
		hideButton(button_LaterTeam);
		
		hideButton(button_StartGame);
		
	}

}
