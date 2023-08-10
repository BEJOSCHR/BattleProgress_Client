package me.bejosch.battleprogress.client.Window.ScrollPanes;


import java.awt.Font;

import javax.swing.JScrollPane;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Window.TextAreas.TextAreas;

public class ScrollPanes {

	public static JScrollPane scrollPane_Chat;
	public static JScrollPane scrollPane_MapListDisplay;
	
//==========================================================================================================
	/**
	 * Loads all used scrollPanes
	 **/
	public static void loadScrollPanes() {
		
		scrollPane_Chat = Funktions.createScrollPane(TextAreas.textArea_Chat, true, false);
		scrollPane_MapListDisplay = Funktions.createScrollPane(TextAreas.textArea_MapListDisplay, false ,true);
		
	}
	
//==========================================================================================================
	/**
	 * Shows the named scrollPane
	 * @param scrollPane - JscrollPane - The scrollPane witch should be shown
	 * @param textSize - int - The size of the displayed text
	 * @param X - int - The X coordinate of the scrollPane
	 * @param Y - int - The Y coordinate of the scrollPane
	 * @param width - int - The width of the scrollPane
	 * @param hight - int - The hight of the scrollPane
	 **/
	public static void showScrollPane(JScrollPane scrollPane, int textSize, int X, int Y, int width, int hight) {
		
		scrollPane.setBounds(X, Y, width, hight);
		scrollPane.setFont(new Font("Arial", Font.BOLD, textSize));
		scrollPane.setVisible(true);
		scrollPane.requestFocus();
		
	}
	
//==========================================================================================================
	/**
	 * Hide the named scrollPane
	 * @param scrollPane - JscrollPane - The scrollPane witch should be hiden
	 **/
	public static void hideScrollPane(JScrollPane scrollPane) {
		
		scrollPane.setVisible(false);
		
	}
	
//==========================================================================================================
	/**
	 * Hide all scrollPanes
	 **/
	public static void hideAllScrollPanes() {
		
		hideScrollPane(scrollPane_Chat);
		hideScrollPane(scrollPane_MapListDisplay);
		
	}
	
}
