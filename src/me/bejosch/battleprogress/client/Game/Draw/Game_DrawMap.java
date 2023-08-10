package me.bejosch.battleprogress.client.Game.Draw;

import java.awt.Color;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Headquarter;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;

public class Game_DrawMap {

//==========================================================================================================
	/**
	 * The methode, called by the Label for display this part
	 * This part only draws the map
	 */
	public static void drawMap_MAP(Graphics g) {
		
		draw_Fields(g);
		draw_Buildings(g);
		draw_Troups(g);
		
		draw_BuildArea(g);
		
	}
	
//==========================================================================================================
	/**
	 * Draws the fields of the map
	 */
	public static void draw_Fields(Graphics g) {
		
		Field midField = GameHandler.get_MID_Field(false);
		
		if(MovementHandler.press_a) {
			//VON RECHTS NACH LINKS MALEN
			if(MovementHandler.press_w) {
				//VON UNTEN NACH OBEN
				for(int x = midField.X+(StandardData.fielDraw_fieldCountX/2)+StandardData.fieldDraw_sicherheitsFaktor ; x > midField.X-(StandardData.fielDraw_fieldCountX/2)-StandardData.fieldDraw_sicherheitsFaktor ; x--) {
					for(int y = midField.Y+(StandardData.fielDraw_fieldCountY/2)+StandardData.fieldDraw_sicherheitsFaktor ; y > midField.Y-(StandardData.fielDraw_fieldCountY/2)-StandardData.fieldDraw_sicherheitsFaktor ; y--) {
						try{
							GameData.gameMap_FieldList[x][y].draw(g);
						}catch(ArrayIndexOutOfBoundsException error) { }
					}
				}
			}else {
				//VON OBEN NACH UNTEN
				for(int x = midField.X+(StandardData.fielDraw_fieldCountX/2)+StandardData.fieldDraw_sicherheitsFaktor ; x > midField.X-(StandardData.fielDraw_fieldCountX/2)-StandardData.fieldDraw_sicherheitsFaktor ; x--) {
					for(int y = midField.Y-(StandardData.fielDraw_fieldCountY/2)-StandardData.fieldDraw_sicherheitsFaktor ; y < midField.Y+(StandardData.fielDraw_fieldCountY/2)+StandardData.fieldDraw_sicherheitsFaktor ; y++) {
						try{
							GameData.gameMap_FieldList[x][y].draw(g);
						}catch(ArrayIndexOutOfBoundsException error) { }
					}
				}
			}
		}else {
			//VON LINKS NACH RECHTS MALEN
			if(MovementHandler.press_w) {
				//VON UNTEN NACH OBEN
				for(int x = midField.X-(StandardData.fielDraw_fieldCountX/2)-StandardData.fieldDraw_sicherheitsFaktor ; x < midField.X+(StandardData.fielDraw_fieldCountX/2)+StandardData.fieldDraw_sicherheitsFaktor ; x++) {
					for(int y = midField.Y+(StandardData.fielDraw_fieldCountY/2)+StandardData.fieldDraw_sicherheitsFaktor ; y > midField.Y-(StandardData.fielDraw_fieldCountY/2)-StandardData.fieldDraw_sicherheitsFaktor ; y--) {
						try{
							GameData.gameMap_FieldList[x][y].draw(g);
						}catch(ArrayIndexOutOfBoundsException error) { }
					}
				}
			}else {
				//VON OBEN NACH UNTEN
				for(int x = midField.X-(StandardData.fielDraw_fieldCountX/2)-StandardData.fieldDraw_sicherheitsFaktor ; x < midField.X+(StandardData.fielDraw_fieldCountX/2)+StandardData.fieldDraw_sicherheitsFaktor ; x++) {
					for(int y = midField.Y-(StandardData.fielDraw_fieldCountY/2)-StandardData.fieldDraw_sicherheitsFaktor ; y < midField.Y+(StandardData.fielDraw_fieldCountY/2)+StandardData.fieldDraw_sicherheitsFaktor ; y++) {
						try{
							GameData.gameMap_FieldList[x][y].draw(g);
						}catch(ArrayIndexOutOfBoundsException error) { }
					}
				}
			}
		}
		
		if(GameData.hoveredField != null) {
			GameData.hoveredField.drawHighlight(g, Color.BLACK);
		}
		if(GameData.clickedField != null) {
			GameData.clickedField.drawHighlight(g, Color.ORANGE);
		}
		
		//ActiveBuildingTask
		if(GameData.currentActive_MAA_BuildingTask != null) {
			GameData.currentActive_MAA_BuildingTask.getConnectedBuildingTask().draw_targetField(g);
		}
		
	}
	
//==========================================================================================================
	/**
	 * Draws the buildings of the map
	 */
	public static void draw_Buildings(Graphics g) {
		
		Field midField = GameHandler.get_MID_Field(false);
		
		int minX = midField.X-(StandardData.fielDraw_fieldCountX/2)-StandardData.fieldDraw_sicherheitsFaktor, maxX = midField.X+(StandardData.fielDraw_fieldCountX/2)+StandardData.fieldDraw_sicherheitsFaktor;
		int minY = midField.Y-(StandardData.fielDraw_fieldCountY/2)-StandardData.fieldDraw_sicherheitsFaktor, maxY = midField.Y+(StandardData.fielDraw_fieldCountY/2)+StandardData.fieldDraw_sicherheitsFaktor;
		
		for(Building building : Funktions.getAllBuildingList()) {
			try{
				if(building.connectedField.X >= minX && building.connectedField.X <= maxX && building.connectedField.Y >= minY && building.connectedField.Y <= maxY) {
					//IN RANGE ON THE SCREEN
					if(building.connectedField.visible == true || building instanceof Building_Headquarter) {
						//ONLY HQ DRAW AND VISIBLE FIELDS
						building.draw_Field(g, false);
					}
				}
			}catch(NullPointerException | IndexOutOfBoundsException | IllegalArgumentException error) {}
		}
		
	}
	
//==========================================================================================================
	/**
	 * Draws the troups of the map
	 */
	public static void draw_Troups(Graphics g) {
		
		Field midField = GameHandler.get_MID_Field(false);
		
		int minX = midField.X-(StandardData.fielDraw_fieldCountX/2)-StandardData.fieldDraw_sicherheitsFaktor, maxX = midField.X+(StandardData.fielDraw_fieldCountX/2)+StandardData.fieldDraw_sicherheitsFaktor;
		int minY = midField.Y-(StandardData.fielDraw_fieldCountY/2)-StandardData.fieldDraw_sicherheitsFaktor, maxY = midField.Y+(StandardData.fielDraw_fieldCountY/2)+StandardData.fieldDraw_sicherheitsFaktor;
		
		for(Troup troup : Funktions.getAllTroupList()) {
			try{
				if(troup.connectedField.X >= minX && troup.connectedField.X <= maxX && troup.connectedField.Y >= minY && troup.connectedField.Y <= maxY) {
					//IN RANGE ON THE SCREEN
					if(troup.connectedField.visible == true) {
						//ONLY DRAW VISIBLE FIELDS
						troup.draw_Field(g, false);
					}
				}
			}catch(NullPointerException | IndexOutOfBoundsException | IllegalArgumentException error) {}
		}
		
	}
	
//==========================================================================================================
	/**
	 * Draws the border of the build area
	 */
	public static void draw_BuildArea(Graphics g) {
		
		Funktions.drawBorderAroundFieldArea(g, GameData.buildArea, Color.LIGHT_GRAY, 2);
		
	}
	
}
