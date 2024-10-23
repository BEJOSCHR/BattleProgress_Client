package me.bejosch.battleprogress.client.Objects.Field;

import me.bejosch.battleprogress.client.Data.SpectateData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.SpielStatus;

public class FieldCoordinates {

	public int X = 0, Y = 0;
	
//==========================================================================================================
	/**
	 * This object just contains the X and Y coordinates of a field
	 * @param x - int - The x-coordinate
	 * @param y - int - The y-coordinate
	 */
	public FieldCoordinates(int x, int y) {
		
		this.X = x;
		this.Y = y;
		
	}
//==========================================================================================================
	/**
	 * This object just contains the X and Y coordinates of a field
	 * @param field - {@link Field} - The field the coordinates are taken from
	 */
	public FieldCoordinates(Field field) {
		
		this.X = field.X;
		this.Y = field.Y;
		
	}
	
//==========================================================================================================
	/**
	 * This compares the fields
	 * @param compareField - {@link Field} - The field which should be compared
	 * @return boolean - true if they have the same coordinates, false if not
	 */
	public boolean compareToOtherField(Field compareField) {
		
		if(compareField.X == this.X && compareField.Y == this.Y) {
			return true;
		}
		return false;
		
	}
//==========================================================================================================
	/**
	 * Check if the given field is the LEFT partner field
	 * @param compareFieldCoordinates - {@link FieldCoordinates} - The fieldCoordinates which should be compared
	 * @return boolean - true if they have the same coordinates, false if not
	 */
	public boolean checkFor_LEFT_partner(FieldCoordinates compareFieldCoordinates) {
		if(compareFieldCoordinates.X == this.X - 1 && compareFieldCoordinates.Y == this.Y) {
			return true;
		}
		return false;
	}
//==========================================================================================================
	/**
	 * Check if the given field is the TOP partner field
	 * @param compareFieldCoordinates - {@link FieldCoordinates} - The fieldCoordinates which should be compared
	 * @return boolean - true if they have the same coordinates, false if not
	 */
	public boolean checkFor_TOP_partner(FieldCoordinates compareFieldCoordinates) {
		if(compareFieldCoordinates.X == this.X && compareFieldCoordinates.Y == this.Y - 1) {
			return true;
		}
		return false;
	}
//==========================================================================================================
	/**
	 * Check if the given field is the RIGHT partner field
	 * @param compareFieldCoordinates - {@link FieldCoordinates} - The fieldCoordinates which should be compared
	 * @return boolean - true if they have the same coordinates, false if not
	 */
	public boolean checkFor_RIGHT_partner(FieldCoordinates compareFieldCoordinates) {
		if(compareFieldCoordinates.X == this.X + 1 && compareFieldCoordinates.Y == this.Y) {
			return true;
		}
		return false;
	}
//==========================================================================================================
	/**
	 * Check if the given field is the BOTTOM partner field
	 * @param compareFieldCoordinates - {@link FieldCoordinates} - The fieldCoordinates which should be compared
	 * @return boolean - true if they have the same coordinates, false if not
	 */
	public boolean checkFor_BOTTOM_partner(FieldCoordinates compareFieldCoordinates) {
		if(compareFieldCoordinates.X == this.X && compareFieldCoordinates.Y == this.Y + 1) {
			return true;
		}
		return false;
	}
	
//==========================================================================================================
	/**
	 * This return the field of the coordinates in this object
	 * @return The found field or null
	 */
	public Field getConnectedField() {
		
		if(StandardData.spielStatus == SpielStatus.Game) {
			try{
				return GameData.gameMap_FieldList[X][Y];
			}catch(NullPointerException | IndexOutOfBoundsException error) {
				error.printStackTrace();
				return null;
			}
		}else if(StandardData.spielStatus == SpielStatus.CreateMap) {
			//TODO
			return null;
		}else if(StandardData.spielStatus == SpielStatus.Spectate) {
			try{
				return SpectateData.gameMap_FieldList[X][Y];
			}catch(NullPointerException | IndexOutOfBoundsException error) {
				error.printStackTrace();
				return null;
			}
		}
		
		return null;
		
	}
	
}
