package me.bejosch.battleprogress.client.Objects.InfoMessage;

import java.util.List;

import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.ImportanceType;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_GamePing;

public class InfoMessage_Located extends InfoMessage{

	public int locatedX, locatedY;
	
//==========================================================================================================
	/**
	 * This message is shown in the top right corner as notification and is LOCATED on the map - LONG MESSAGE
	 * @param locatedX_ - int - The refered X-Coordinate of a field on the map
	 * @param locatedY_ - int - The refered Y-Coordinate of a field on the map
	 * @see InfoMessage
	 **/
	public InfoMessage_Located(List<String> textLines_, ImportanceType importance_, int locatedX_, int locatedY_, boolean removeOverTime_) {
		super(textLines_, importance_, removeOverTime_);
		
		this.locatedX = locatedX_;
		this.locatedY = locatedY_;
		
	}
//==========================================================================================================
	/**
	 * This message is shown in the top right corner as notification and is LOCATED on the map - SHORT MESSAGE
	 * @param locatedX_ - int - The refered X-Coordinate of a field on the map
	 * @param locatedY_ - int - The refered Y-Coordinate of a field on the map
	 * @see InfoMessage
	 **/
	public InfoMessage_Located(String text, ImportanceType importance_, int locatedX_, int locatedY_, boolean removeOverTime_) {
		super(text, importance_, removeOverTime_);
		
		this.locatedX = locatedX_;
		this.locatedY = locatedY_;
		
	}
	
	
//==========================================================================================================
	/**
	 * Jumps on the map to the given coordinates of this object
	 */
	public void jumpToConnectedLocation() {
		
		Funktions.moveScreenToFieldCoordinates(locatedX, locatedY);
		new Animation_GamePing(GameData.gameMap_FieldList[locatedX][locatedY]);
		
	}
	
	
}
