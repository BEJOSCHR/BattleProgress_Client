package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Game.Handler.Game_RoundHandler;

public class MouseActionArea_ReadyButton extends MouseActionArea{
	
	public int spamDelayInMS = 500;
	public boolean spamProtectionActive = false;
	
//==========================================================================================================
	/**
	 * A spezial type of {@link MouseActionArea} for a ready button in the top bottom corner
	 **/
	public MouseActionArea_ReadyButton() {
		super(GameData.readyButton_X, GameData.readyButton_Y, GameData.readyButton_X+GameData.readyButton_maße, GameData.readyButton_Y+GameData.readyButton_maße, "ActionBar_ReadyButton", null, ShowBorderType.ShowAlways, Color.WHITE, Color.WHITE);
	
		//SET HOVER MESSAGE
		String[] message = {"This button sets you ready for this round","You can undo this by clicking again","After all player are ready the round will go on","After the timer run out the round is ending too"};
		this.hoverText = message;
		
	}

	@Override
	public boolean isActiv() {
		
		if(RoundData.roundIsChanging == false) {
			return true;
		}
		return false;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		if(spamProtectionActive == false) {
			//SPAM PROTECTION
			spamProtectionActive = true;
			
			if(RoundData.roundIsChanging == false) {
				if(RoundData.clientIsReadyForThisRound == false) {
					Game_RoundHandler.setClientReady();
				}else {
					Game_RoundHandler.setClientNotReady();
				}
			}
			
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					spamProtectionActive = false;
				}
			}, spamDelayInMS);
			
		}
		
	}
	
}
