package me.bejosch.battleprogress.client.Objects.OnTopWindow.Research;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;

public class OnTopWindow_Research extends OnTopWindow {

	public OnTopWindow_Research() {
		super("OTW_Research", OnTopWindowData.research_width, OnTopWindowData.research_height);
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
	public void onMouseWheelTurned(boolean scrollUp) {
		
		if(OnTopWindowData.research_scrollPos > 0 && scrollUp == true) {
			OnTopWindowData.research_scrollPos--;
		}else if(OnTopWindowData.research_scrollPos+OnTopWindowData.research_maxShownLines < getMaxScrollLinesByCurrentCategory() && scrollUp == false) {
			OnTopWindowData.research_scrollPos++;
		}
		
	}

	@Override
	public void draw(Graphics g) {
		
		int x = WindowData.FrameWidth/2-this.width/2;
		int y = WindowData.FrameHeight/2-this.height/2;
		
		//BACKGROUND
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, this.width, this.height);
		//NO BORDER BECAUSE IT IS FULLSCREEN
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 35));
		g.drawString("Research", WindowData.FrameWidth/2 - 70, y + 40);
		
		//DEVIDER
		
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(x, y+OnTopWindowData.research_devideLine_yOffset, x+this.width, y+OnTopWindowData.research_devideLine_yOffset);
		g.drawLine(x, y+OnTopWindowData.research_devideLine_yOffset+OnTopWindowData.research_devideLine_height, x+this.width, y+OnTopWindowData.research_devideLine_yOffset+OnTopWindowData.research_devideLine_height);
		
		//LEGENDE
		
		int legendeX = WindowData.FrameWidth/2+this.width/2-150, borderTopDown = 10, maße = OnTopWindowData.research_devideLine_height-borderTopDown*2, 
				legendeY = y+OnTopWindowData.research_devideLine_yOffset+borderTopDown, spaceBetween = 200;
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 18));
		
		g.setColor(OnTopWindowData.research_color_researched);
		g.fillRect(legendeX-(2*spaceBetween), legendeY, maße, maße);
		g.setColor(Color.BLACK);
		g.drawRect(legendeX-(2*spaceBetween), legendeY, maße, maße);
		g.setColor(Color.WHITE);
		g.drawString("Researched", legendeX-(2*spaceBetween)+maße+10, legendeY+maße-7);
		
		g.setColor(OnTopWindowData.research_color_researchable);
		g.fillRect(legendeX-(1*spaceBetween), legendeY, maße, maße);
		g.setColor(Color.BLACK);
		g.drawRect(legendeX-(1*spaceBetween), legendeY, maße, maße);
		g.setColor(Color.WHITE);
		g.drawString("Researchable", legendeX-(1*spaceBetween)+maße+10, legendeY+maße-7);
		
		g.setColor(OnTopWindowData.research_color_locked);
		g.fillRect(legendeX-(0*spaceBetween), legendeY, maße, maße);
		g.setColor(Color.BLACK);
		g.drawRect(legendeX-(0*spaceBetween), legendeY, maße, maße);
		g.setColor(Color.WHITE);
		g.drawString("Locked", legendeX-(0*spaceBetween)+maße+10, legendeY+maße-7);
		
		//SLOTS DEVIDER
		
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 22));
		//Erste ist DevideLine oben
		for(int i = 1 ; i <= getMaxScrollLinesByCurrentCategory() ; i++) {
			g.drawString((i+OnTopWindowData.research_scrollPos)+".", x+20, OnTopWindowData.research_slotStartY+(getSegmentHeight()*i)-10);
			g.drawLine(x, OnTopWindowData.research_slotStartY+(getSegmentHeight()*i), x+this.width, OnTopWindowData.research_slotStartY+(getSegmentHeight()*i));
		}
		
		
	}
	
//STATIC ----------------------------------------------------------------------------------------------------
	public static int getMaxScrollLinesByCurrentCategory() {
		
		switch(OnTopWindowData.research_category) {
		case AirTroups:
			return 7;
		case Economie:
			return 7;
		case LandTroups:
			return 7;
		}
		
		return 0;	
	}
	
	public static int getSlotByPos_X(int x) {
		return OnTopWindowData.research_slotStartX+x*(OnTopWindowData.research_slotWidth+OnTopWindowData.research_slotBorderBetween);
	}
	public static int getSlotByPos_Y(int y) {
		return OnTopWindowData.research_slotStartY+y*getSegmentHeight() + OnTopWindowData.research_slotBorderUpDown;
	}
	public static int getSegmentHeight() {
		return OnTopWindowData.research_slotBorderUpDown+OnTopWindowData.research_slotHeight+OnTopWindowData.research_slotBorderUpDown;
	}
	
}
