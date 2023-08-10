package me.bejosch.battleprogress.client.Enum;

import java.awt.Color;

public enum ImportanceType {

	HIGH,
	NORMAL,
	LOW;
	
	public static Color getColor(ImportanceType type) {
		
		switch(type) {
		case HIGH:
			return new Color(220, 30, 30, 255); //DARK RED
		case NORMAL:
			return new Color(250, 180, 30, 255); //ORANGE
		case LOW:
			return new Color(20, 210, 20, 255); //DARK GREEN
		default:
			break;
		}
		return new Color(45, 45, 45, 255);
		
	}
	
	public static int getShowTimeInMS(ImportanceType type) {
		
		switch(type) {
		case HIGH:
			return 1000*8; //SEK
		case NORMAL:
			return 1000*6; //SEK
		case LOW:
			return 1000*5; //SEK
		default:
			break;
		}
		return 1000*4;
		
	}
	
}
