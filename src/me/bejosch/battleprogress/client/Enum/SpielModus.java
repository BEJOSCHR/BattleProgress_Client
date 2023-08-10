package me.bejosch.battleprogress.client.Enum;

import me.bejosch.battleprogress.client.Data.Game.GameData;

public enum SpielModus {

	Ranked_1v1,
	Normal_1v1,
	Normal_2v2,
	Custom_1v1,
	Custom_2v2;
	
	public static boolean isGameModus1v1() {
		
		if(GameData.gameMode == SpielModus.Custom_1v1 || GameData.gameMode == SpielModus.Normal_1v1 || GameData.gameMode == SpielModus.Ranked_1v1) {
			return true;
		}else {
			return false;
		}
		
	}
	public static boolean isModus1v1(SpielModus modus) {
		
		if(modus == SpielModus.Custom_1v1 || modus == SpielModus.Normal_1v1 || modus == SpielModus.Ranked_1v1) {
			return true;
		}else {
			return false;
		}
		
	}
	
}
