package me.bejosch.battleprogress.client.Enum;

import me.bejosch.battleprogress.client.Main.ConsoleOutput;

public enum BuildMenuType {

	Fight, //ATTACKING BUILDINGS AS TURRETS OR ARTILLERY
	Production, //PRODUCING PLACES FOR TROUPS
	Economic, //PRODUCING RESSOURCES
	Special; //EVERYTHING ELSE
	
	public static String getKürzelByType(BuildMenuType type) {
		
		switch (type) {
		case Fight:
			return "F";
		case Economic:
			return "E";
		case Production:
			return "P";
		case Special:
			return "S";
		default:
			ConsoleOutput.printMessageInConsole("A BuildMenuType can not find a 'Kßrzel' for the type '"+type+"'", true);
			break;
		}
		
		return "#";
		
	}
	
}
