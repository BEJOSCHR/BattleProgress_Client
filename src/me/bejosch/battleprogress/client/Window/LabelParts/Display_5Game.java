package me.bejosch.battleprogress.client.Window.LabelParts;

import java.awt.Graphics;

import me.bejosch.battleprogress.client.Game.Draw.Game_DrawMap;
import me.bejosch.battleprogress.client.Game.Draw.Game_DrawOverlay;

public class Display_5Game {

//==========================================================================================================
	/**
	 * The methode, called by the Label for display this part
	 */
	public static void draw(Graphics g) {
		
		Game_DrawMap.drawMap_MAP(g);
		Game_DrawOverlay.drawOverlay(g);
		
	}
	
}
