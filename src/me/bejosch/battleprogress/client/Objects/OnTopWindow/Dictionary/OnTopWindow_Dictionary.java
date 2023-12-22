package me.bejosch.battleprogress.client.Objects.OnTopWindow.Dictionary;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;

public class OnTopWindow_Dictionary extends OnTopWindow {

	
	public OnTopWindow_Dictionary() {
		super("OTW_Dictionary", OnTopWindowData.dictionary_width, OnTopWindowData.dictionary_height);
		
		
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
		
	}
	
	@Override
	public void performClose() {
		
		MovementHandler.allowMovement();
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(this.getX(), this.getY(), this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(this.getX(), this.getY(), this.width, this.height);
		
		//TITEL
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 34));
		g.drawString("Dictionary", this.getX()+OnTopWindowData.dictionary_border, this.getY()+OnTopWindowData.dictionary_titelSectionHeight-10);
		g.setColor(Color.WHITE);
		g.drawLine(getX(), getY()+OnTopWindowData.dictionary_titelSectionHeight, getX()+this.width, getY()+OnTopWindowData.dictionary_titelSectionHeight);
		
		//SECTIONS
		
		//TODO CREATE SCROLLABLE AND SEARCHABLE/FILTERABLE VIEW OF EACH CATERGORIES STACKED ONTOP OF EACHOTHER (LIKE FRIENDLIST)
		
		//TODO EXTRA OTW TO DISPLAY / SHOW DictionaryInfoDescriptions IF THEY ARE CLICKED IN THE OVERVIEW
		
	}
}
