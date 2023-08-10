package me.bejosch.battleprogress.client.Window.TextAreas;

import java.awt.Font;

import javax.swing.JTextArea;
import me.bejosch.battleprogress.client.Funktions.Funktions;

public class TextAreas {

	public static JTextArea textArea_Chat;
	public static JTextArea textArea_MapListDisplay;
	
//==========================================================================================================
	/**
	 * Load all needed TextAreas
	 */
	public static void loadTextAreas() {
		
		textArea_Chat = Funktions.createTextArea();
		textArea_MapListDisplay = Funktions.createTextArea();
		
	}
	
//==========================================================================================================
	/**
	 * Shows the named textArea
	 * @param textArea - JtextArea - The textArea witch should be shown
	 * @param textSize - int - The size of the displayed text
	 * @param X - int - The X coordinate of the textArea
	 * @param Y - int - The Y coordinate of the textArea
	 * @param width - int - The width of the textArea
	 * @param hight - int - The hight of the textArea
	 **/
	public static void showTextArea(JTextArea textArea, int textSize, int X, int Y, int width, int hight) {
		
		textArea.setBounds(X, Y, width, hight);
		textArea.setFont(new Font("Arial", Font.BOLD, textSize));
		textArea.setVisible(true);
		
	}
//==========================================================================================================
	/**
	 * Just shows the named textArea
	 * @param textArea - JtextArea - The textArea witch should be shown
	 **/
	public static void showTextArea(JTextArea textArea) {
		
		textArea.setVisible(true);
		textArea.requestFocus();
		
	}
	
//==========================================================================================================
	/**
	 * Hide the named textArea
	 * @param textArea - JtextArea - The textArea witch should be hiden
	 **/
	public static void hideTextArea(JTextArea textArea) {
		
		textArea.setVisible(false);
		
	}
	
//==========================================================================================================
	/**
	 * Hide all textAreas
	 **/
	public static void hideAlltextAreas() {
		
		hideTextArea(textArea_Chat);
		hideTextArea(textArea_MapListDisplay);
		
	}
	
}
