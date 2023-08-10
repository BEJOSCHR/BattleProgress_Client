package me.bejosch.battleprogress.client.Enum;

import java.awt.Color;

public enum FieldType {

	Gras,
	Water,
	Stone,
	Path,
	Ressource,
	RessourceVerbraucht;
	
	public static String getNameForFieldType(FieldType type) {
		
		switch(type) {
		case Gras:
			return "Flatland";
		case Water:
			return "Ocean";
		case Stone:
			return "Mountain";
		case Path:
			return "Path";
		case Ressource:
			return "Ressource";
		case RessourceVerbraucht:
			return "Consumed";
		default:
			break;
		}
		return "missingName";
		
	}
	
	public static String[] getDescriptionForFieldType(FieldType type) {
		
		switch(type) {
		case Gras:
			String[] gras = {"This '"+getNameForFieldType(type)+"' field is the standard type of field","Buildings can be placed on it","Every type of troup can go/fly on/over it","It gives no positiv or negative effect"};
			return gras;
		case Water:
			String[] water = {"This '"+getNameForFieldType(type)+"' field is a blocking field","Buildings can NOT be placed on it","Only AIR troups can fly over it","It gives no positiv or negative effect"};
			return water;
		case Stone:
			String[] stone = {"This '"+getNameForFieldType(type)+"' field is a blocking field","Buildings can NOT be placed on it","NO troup can go/fly on/over it"};
			return stone;
		case Path:
			String[] path = {"This '"+getNameForFieldType(type)+"' field is a boost field","Buildings can be placed on it","Every type of troup can go/fly on/over it","LAND troups getting a movement boost if they start moving on it"};
			return path;
		case Ressource:
			String[] ressource = {"This '"+getNameForFieldType(type)+"' field is an economical field","ONLY MINES can be placed on it","Every type of troup can go/fly on/over it","Mines on this field are producing materials"};
			return ressource;
		case RessourceVerbraucht:
			String[] ressourceVerbraucht = {"This '"+getNameForFieldType(type)+"' field is a destroyed field","NO buildings can be placed on it","Every type of troup can go/fly on/over it","The material of this field has been completely consumed"};
			return ressourceVerbraucht;
		default:
			break;
		}
		String[] failure = {"missingDescription!"};
		return failure;
		
	}
	
	public static FieldType getFieldTypeFromSignal(String number) {
		
		switch(number) {
		case "g":
			return Gras;
		case "w":
			return Water;
		case "s":
			return Stone;
		case "p":
			return Path;
		case "r":
			return Ressource;
		case "v":
			return RessourceVerbraucht;
		}
		return Gras;
		
	}
	
	public static String getShortcutForFieldType(FieldType type) {
		
		switch(type) {
		case Gras:
			return "g";
		case Water:
			return "w";
		case Stone:
			return "s";
		case Path:
			return "p";
		case Ressource:
			return "r";
		case RessourceVerbraucht:
			return "v";
		default:
			break;
		}
		return "g";
		
	}
	
	public static Color getMiniMapColorForFieldType(FieldType type) {
		
		switch(type) {
		case Gras:
			return new Color(34, 139, 34, 100);
		case Water:
			return new Color(0, 0, 238, 100);
		case Stone:
			return new Color(193, 205, 205, 100);
		case Path:
			return new Color(139, 71, 38, 100);
		case Ressource:
			return new Color(255, 215, 0, 100);
		case RessourceVerbraucht:
			return new Color(0, 0, 0, 100);
		default:
			break;
		}
		return new Color(50, 205, 50, 100);
		
	}
	
}
