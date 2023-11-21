package me.bejosch.battleprogress.client.Objects.OnTopWindow;

import java.awt.Graphics;

public class OnTopWindow {

	public String name = "Missing";
	public int width = 0, height = 0;
	public boolean darkBackground = true;
	
	public OnTopWindow(String name_, int width_, int height_) {
		
		this.name = name_;
		this.width = width_;
		this.height = height_;
		
		//INIT ON OPEN CALLED BY HANDLER OPENING
		
	}
	
	public void initOnOpen() {}
	public void performClose() {}
	
	public void draw(Graphics g) {}
	
	public void onMousePress_LEFT(int x, int y) {}
	public void onMousePress_RIGHT(int x, int y) {}
	public void onMouseRelease_LEFT(int x, int y) {}
	public void onMouseRelease_RIGHT(int x, int y) {}
	
	public void onMouseMove(int x, int y) {}
	public void onMouseWheelTurned(boolean scrollUp) {}
	
	public void onKeyPress(int keyCode) {}
	public void onKeyRelease(int keyCode) {}
	
}
