package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Color;

import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask;

public class MouseActionArea_ActiveTaskSkip extends MouseActionArea{

	public MouseActionArea_ActiveTaskSkip() {
		super(0, 0, 0, 0, "ActiveTaskSkip", null, ShowBorderType.ShowAlways, Color.WHITE, Color.ORANGE);
		
		//MAIN INFO DISPLAY DATA
		int width = 400, height = 50, X = ((WindowData.FrameWidth+WindowData.rahmen*2)/2)-(width/2), Y = 120;
		//SKIP BUTTON DATA
		int skipButton_distanceToRight = 50, skipButton_width = 40, skipButton_topBorder = 13, skipButton_height = height-(skipButton_topBorder*2);
		
		int skipButton_X = X+(width-skipButton_distanceToRight);
		int skipButton_Y = Y+skipButton_topBorder;
		
		this.xTL = skipButton_X;
		this.yTL = skipButton_Y;
		this.xBR = skipButton_X + skipButton_width;
		this.yBR = skipButton_Y + skipButton_height;
		
		String[] hovermessage = {"Click to skip the animation of the current task"};
		this.hoverText = hovermessage;
		
	}

	@Override
	public boolean isActiv() {
		
		if(RoundData.currentExecuteTask != null) {
			return true;
		}else {
			return false;
		}
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		if(RoundData.currentExecuteTask != null) {
			ExecuteTask currentActiveTask = RoundData.currentExecuteTask;
			currentActiveTask.skipAnimation();
		}
		
	}
	
}
