package me.bejosch.battleprogress.client.Window.LabelParts;

import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.SpectateData;
import me.bejosch.battleprogress.client.Game.Draw.Game_DrawMap;
import me.bejosch.battleprogress.client.Handler.SpectateHandler;
import me.bejosch.battleprogress.client.Objects.Field.Field;

public class Display_5Spectate {

//==========================================================================================================
	/**
	 * The methode, called by the Label for display this part
	 */
	public static void draw(Graphics g) {
		
		Field midField = SpectateHandler.get_MID_Field(false);
		
		Game_DrawMap.draw_Fields(g, SpectateData.gameMap_FieldList, midField, SpectateData.clickedField, SpectateData.hoveredField, null);
		Game_DrawMap.draw_Buildings(g, SpectateData.buildings, midField);
		Game_DrawMap.draw_Troups(g, SpectateData.troups, midField);
		
	}
	
}
