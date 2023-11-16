package me.bejosch.battleprogress.client.Objects.MouseActionArea.Checkbox;

import java.awt.Color;

import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;

public class MouseActionArea_Checkbox_SkipAllTaskDisplays extends MouseActionArea_Checkbox {
	
	public MouseActionArea_Checkbox_SkipAllTaskDisplays() {
		super(0, 0, 0, 0, "SkipAllTaskDisplays", null, Color.WHITE, Color.ORANGE, Color.RED, false, true, Color.LIGHT_GRAY);
		
		//MAIN INFO DISPLAY DATA
		int width = 400, height = 50, X = ((WindowData.FrameWidth+WindowData.rahmen*2)/2)-(width/2), Y = 120;
		int size = 25, borderLeft = 20;
		
		this.xTL = X+borderLeft;
		this.xBR = this.xTL+size;
		this.yTL = Y+(height-size)/2;
		this.yBR = this.yTL+size;
		
		String[] hovertext = {"Enable/Disable task animation skipping"};
		this.hoverText = hovertext;
		
	}

	@Override
	public boolean isActiv() {
		
		if(RoundData.currentlyPerformingTasks == true) {
			return true;
		}else {
			return false;
		}
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		super.performAction_LEFT_RELEASE();
		
	}
	
}
