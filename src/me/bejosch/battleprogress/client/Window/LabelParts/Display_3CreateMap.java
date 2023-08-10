package me.bejosch.battleprogress.client.Window.LabelParts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.CreateMapData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Game.Draw.Game_DrawOverlay;
import me.bejosch.battleprogress.client.Handler.CreateMapHandler;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Headquarter;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Window.TextFields.TextFields;

public class Display_3CreateMap {

//==========================================================================================================
	/**
	 * The methode, called by the Label for display this part
	 */
	public static void draw(Graphics g) {
		
		draw_CreateMap(g);
		
		Game_DrawOverlay.draw_MouseActionAreas(g, false);
		
	}
	
//==========================================================================================================
	/**
	 * A subheading of the draw methode
	 */
	public static void draw_CreateMap(Graphics g) {
		
		draw_fields(g);
		draw_buildings(g);
		
		draw_CreateMap_CornerInfo(g, 0, 0);
		
		draw_CreateMap_HighlightArea(g);
		
		draw_CreateMap_Hud(g, CreateMapData.createMap_X, CreateMapData.createMap_Y);
		
		draw_CreateMap_OverlayInput(g, CreateMapData.overlay_x, CreateMapData.overlay_y);
		
	}
	
	private static void draw_CreateMap_HighlightArea(Graphics g) {
		
		if(MouseHandler.mousePressPoint != null && CreateMapData.overlayedInput == false) {
			if(CreateMapHandler.getFieldByCoordinates(MouseHandler.mousePressPoint.x, MouseHandler.mousePressPoint.y) != null && CreateMapHandler.getFieldByCoordinates(MouseHandler.mouseX, MouseHandler.mouseY) != null) {
				Field press = CreateMapHandler.getFieldByCoordinates(MouseHandler.mousePressPoint.x, MouseHandler.mousePressPoint.y);
				Field released = CreateMapData.choosenField;
				
				//ONLY ONE FIELD MARKED
				if(press == released) {
					press.drawHighlight(g, Color.red);
				//MORE FIELDS MARKED
				}else { 
					if(press.X >= released.X) {
						if(press.Y >= released.Y) {
							for(int varX = press.X ; varX >= released.X ; varX--) {
								for(int varY = press.Y ; varY >= released.Y ; varY--) {
									Field changed = CreateMapData.createMap_FieldList[varX][varY];
									changed.drawHighlight(g, Color.red);
								}
							}
						}else {
							for(int varX = press.X ; varX >= released.X ; varX--) {
								for(int varY = released.Y ; varY >= press.Y ; varY--) {
									Field changed = CreateMapData.createMap_FieldList[varX][varY];
									changed.drawHighlight(g, Color.red);
								}
							}
						}
					}else {
						if(press.Y >= released.Y) {
							for(int varX = released.X ; varX >= press.X ; varX--) {
								for(int varY = press.Y ; varY >= released.Y ; varY--) {
									Field changed = CreateMapData.createMap_FieldList[varX][varY];
									changed.drawHighlight(g, Color.red);
								}
							}
						}else {
							for(int varX = released.X ; varX >= press.X ; varX--) {
								for(int varY = released.Y ; varY >= press.Y ; varY--) {
									Field changed = CreateMapData.createMap_FieldList[varX][varY];
									changed.drawHighlight(g, Color.red);
								}
							}
						}
					}
				}
				
				if(CreateMapData.choosenField != null) {
					g.setColor(Color.WHITE);
					g.setFont(new Font("Arial", Font.BOLD, 14));
					g.drawString("Right-Click to cancel", CreateMapData.choosenField.X*StandardData.fieldSize+CreateMapData.scroll_CM_LR_count+30, CreateMapData.choosenField.Y*StandardData.fieldSize+CreateMapData.scroll_CM_UD_count+20);
				}
				
			}
		}
		
	}

	private static void draw_CreateMap_CornerInfo(Graphics g, int x, int y) {
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		if(CreateMapData.choosenField != null) {
			g.drawString("Mouse: "+CreateMapData.choosenField.X+" - "+CreateMapData.choosenField.Y, x+10, y+20);
		}else {
			g.drawString("Mouse: "+"#"+" - "+"#", x+10, y+20);
		}
		if(CreateMapHandler.get_MID_Field(true) != null) {
			Field midField = CreateMapHandler.get_MID_Field(true);
			g.drawString("Middle: "+midField.X+" - "+midField.Y, x+10, y+37);
		}else {
			g.drawString("Middle: "+"#"+" - "+"#", x+10, y+37);
		}
		
	}
	
	private static void draw_CreateMap_Hud(Graphics g, int x, int y) {
		
		if(CreateMapData.showHUD == true) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(x, y, CreateMapData.createMap_width, CreateMapData.createMap_height);
			g.setColor(Color.WHITE);
			g.drawLine(x+CreateMapData.seperateBorderDistance, y, x+CreateMapData.seperateBorderDistance, y+CreateMapData.createMap_height);
			g.drawLine(x+CreateMapData.seperateBorderDistance, y+CreateMapData.createMap_height-45, x+CreateMapData.createMap_width, y+CreateMapData.createMap_height-45);
								//640
			g.drawImage(Funktions.getFieldImageFromFieldType(CreateMapData.currentFieldBuild), x+CreateMapData.seperateBorderDistance+((CreateMapData.createMap_width-CreateMapData.seperateBorderDistance)/2)-(StandardData.fieldSize/2), y+40, StandardData.fieldSize, StandardData.fieldSize, null);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 12));
			g.drawString("Replace with "+FieldType.getNameForFieldType(CreateMapData.currentFieldBuild)+" on "+CreateMapData.choosenField.X+":"+CreateMapData.choosenField.Y, x+CreateMapData.seperateBorderDistance+6, y+24);
			
			draw_MiniMap(g);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 18));
			g.drawString("Toggle Space to hide the Overlay", (WindowData.FrameWidth/2)-100, WindowData.FrameHeight-CreateMapData.spaceToTheBottom);
			
		}else {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 18));
			g.drawString("Toggle Space to show the Overlay", (WindowData.FrameWidth/2)-100, WindowData.FrameHeight-CreateMapData.spaceToTheBottom);
		}
		
	}
	
	//TODO REPLACE WITH OTWs !!!
	
	private static void draw_CreateMap_OverlayInput(Graphics g, int x, int y) {
		
		if(CreateMapData.overlayedInput == true) {
			
			g.setColor(new Color(100, 100, 100, 170));
			g.fillRect(0-10, 0-10, WindowData.FrameWidth+20, WindowData.FrameHeight+20);
			g.setColor(Color.DARK_GRAY);
			g.fillRect(x, y, CreateMapData.overlay_width, CreateMapData.overlay_height);
					//430   410
			if(CreateMapData.overlayInputFinished == false) {
				//WAITING FOR INPUT
				
				if(CreateMapData.clearingMap == false) {
					
					//ALLES AUßER CLEAR
					g.setColor(Color.WHITE);
					g.setFont(new Font("Arial", Font.BOLD, 16));
					g.drawString("Type the name of the map:", x+22, y+27);
					String NameInput = TextFields.textField_createMapName.getText();
					if(NameInput.length() > 0) {
	
						try{
							Integer.parseInt(NameInput.substring(0, 1));
							//1 IST NE ZAHL - NICHT GUT
							g.setColor(Color.RED);
							g.setFont(new Font("Arial", Font.BOLD, 12));
							g.drawString("The first letter can not be a number!", x+23, y+77);
						}catch(NumberFormatException error) {
							//1 KEINE ZAHL - ALLES GUT
							if(NameInput.length() > 3 && NameInput.length() < 16 && !NameInput.contains(" ")) {
								//ALLES WEITERE GUT
								g.setColor(Color.GREEN);
								g.setFont(new Font("Arial", Font.BOLD, 12));
								g.drawString("Mapname without errors!", x+23, y+77);
							}else if(NameInput.contains(" ")) {
								//FEHLERHAFT
								g.setColor(Color.RED);
								g.setFont(new Font("Arial", Font.BOLD, 12));
								g.drawString("No spaces allowed!", x+23, y+77);
							}else {
								//FEHLERHAFT
								g.setColor(Color.RED);
								g.setFont(new Font("Arial", Font.BOLD, 12));
								g.drawString("Min. 4 and max. 15 letters required!", x+23, y+77);
							}
						}
						
					}else {
						//FEHLERHAFT
						g.setColor(Color.RED);
						g.setFont(new Font("Arial", Font.BOLD, 12));
						g.drawString("Min. 4 and max. 15 letters required!", x+23, y+77);
					}
					
					if(CreateMapData.loadingMap == true) { //LOADING
						
						g.setColor(Color.ORANGE);
						g.setFont(new Font("Arial", Font.BOLD, 22));
						g.drawString("Your current progress will be lost!", x-3, y+150);
						
					}
				}else {
					//CLEAR DONT NEED A NAME INPUT
					g.setColor(Color.WHITE);
					g.setFont(new Font("Arial", Font.BOLD, 16));
					g.drawString("Do you realy want to clear the map?", x+22, y+27);
					g.setColor(Color.ORANGE);
					g.setFont(new Font("Arial", Font.BOLD, 22));
					g.drawString("Your current progress will be lost!", x-3, y+150);
					
				}
				
			}else {
				//INPUT GIVEN - NOW DISPLAY FEEDBACK
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 20));
				g.drawString(""+CreateMapData.whySaveEnded, x+18, y+70);
				
			}
			
		}
		
	}
	
//==========================================================================================================
	/**
	 * Draws the MiniMap of the CreatMap
	 */
	public static void draw_MiniMap(Graphics g) {
		
		//MINIMAP - BACKGROUND
		g.setColor(Color.DARK_GRAY); //!!!!!! VERHINDERT DAS DURCHSICHTIGE !!!!!!!!
		g.fillRect(StandardData.X-StandardData.rahmen/2, StandardData.Y-StandardData.rahmen/2, StandardData.maße+StandardData.rahmen, StandardData.maße+StandardData.rahmen);
		g.setColor(Color.WHITE);
		g.drawRect(StandardData.X-1, StandardData.Y-1, StandardData.maße+2, StandardData.maße+2);
//		g.setColor(FieldType.getMiniMapColorForFieldType(FieldType.Gras));
//		g.fillRect(X, Y, maße+1, maße+1);
		//MINIMAP - FIELDS
		for(int x = 0 ; x < StandardData.mapWidth ; x += 2) {
			for(int y = 0 ; y < StandardData.mapHight ; y += 2) {
				if(x > StandardData.mapWidth || y > StandardData.mapHight) { break; }
				Field field = CreateMapData.createMap_FieldList[x][y];
				int mmX = field.X*StandardData.normaliseFactorX; int mmY = field.Y*StandardData.normaliseFactorY;
				g.setColor(FieldType.getMiniMapColorForFieldType(field.type));
				if(field.X <= StandardData.ßberschussX && field.Y <= StandardData.ßberschussY) {
					//MUSS X & Y - GESTRECKT WERDEN
					g.fillRect(StandardData.X+mmX+(field.X*StandardData.ausgleichsValue), StandardData.Y+mmY+(field.Y*StandardData.ausgleichsValue), StandardData.normaliseFactorX+StandardData.ausgleichsValue, StandardData.normaliseFactorY+StandardData.ausgleichsValue);
				}else if(field.X <= StandardData.ßberschussX) {
					//MUSS NUR X - GESTRECKT WERDEN
					g.fillRect(StandardData.X+mmX+(field.X*StandardData.ausgleichsValue), StandardData.Y+mmY+((StandardData.ßberschussY+1)*StandardData.ausgleichsValue), StandardData.normaliseFactorX+StandardData.ausgleichsValue, StandardData.normaliseFactorY);
				}else if(field.Y <= StandardData.ßberschussY) {
					//MUSS NUR Y - GESTRECKT WERDEN
					g.fillRect(StandardData.X+mmX+((StandardData.ßberschussX+1)*StandardData.ausgleichsValue), StandardData.Y+mmY+(field.Y*StandardData.ausgleichsValue), StandardData.normaliseFactorX, StandardData.normaliseFactorY+StandardData.ausgleichsValue);
				}else {
					//MUSS GAR NICHT GESTRECKT WERDEN
					g.fillRect(StandardData.X+mmX+((StandardData.ßberschussX+1)*StandardData.ausgleichsValue), StandardData.Y+mmY+((StandardData.ßberschussY+1)*StandardData.ausgleichsValue), StandardData.normaliseFactorX, StandardData.normaliseFactorY);
				}
			}
		}
		//MINIMAP - CURRENTVIEW
		Field midField = CreateMapHandler.get_MID_Field(false);
		if(midField != null) {
			int sizeX = 120; int sizeY = 65;
			int midX = midField.X*StandardData.normaliseFactorX; int midY = midField.Y*StandardData.normaliseFactorY;  
			if( midField.X <= StandardData.ßberschussX) { midX += midField.X*StandardData.ausgleichsValue; } else { midX += StandardData.ßberschussX*StandardData.ausgleichsValue; }
			if( midField.Y <= StandardData.ßberschussY) { midY += midField.Y*StandardData.ausgleichsValue; } else { midY += StandardData.ßberschussY*StandardData.ausgleichsValue; }
			g.setColor(Color.WHITE);
			g.drawRect(StandardData.X+midX-(sizeX/2), StandardData.Y+midY-(sizeY/2), sizeX, sizeY);
		}
		//MINIMAP - BUILDINGS (4 HQ)
		for(Building building : CreateMapData.HQdisplayList) {
			int realX = (building.connectedField.X+1)*StandardData.normaliseFactorX; int realY = (building.connectedField.Y+1)*StandardData.normaliseFactorY;  
			if( building.connectedField.X <= StandardData.ßberschussX) { realX += building.connectedField.X*StandardData.ausgleichsValue; } else { realX += StandardData.ßberschussX*StandardData.ausgleichsValue; }
			if( building.connectedField.Y <= StandardData.ßberschussY) { realY += building.connectedField.Y*StandardData.ausgleichsValue; } else { realY += StandardData.ßberschussY*StandardData.ausgleichsValue; }
			
			g.setColor(Color.YELLOW);
			g.fillRect(StandardData.X+realX-1, StandardData.Y+realY-1, 3, 3);
		}
		
	}
	
//==========================================================================================================
	/**
	 * Draws the map which could be edited
	 */
	public static void draw_fields(Graphics g) {
		
		Field midField = CreateMapHandler.get_MID_Field(false);
		
		if(MovementHandler.press_a) {
			//VON RECHTS NACH LINKS MALEN
			if(MovementHandler.press_w) {
				//VON UNTEN NACH OBEN
				for(int x = midField.X+(StandardData.fielDraw_fieldCountX/2)+StandardData.fieldDraw_sicherheitsFaktor ; x > midField.X-(StandardData.fielDraw_fieldCountX/2)-StandardData.fieldDraw_sicherheitsFaktor ; x--) {
					for(int y = midField.Y+(StandardData.fielDraw_fieldCountY/2)+StandardData.fieldDraw_sicherheitsFaktor ; y > midField.Y-(StandardData.fielDraw_fieldCountY/2)-StandardData.fieldDraw_sicherheitsFaktor ; y--) {
						try{
							CreateMapData.createMap_FieldList[x][y].draw(g);
						}catch(ArrayIndexOutOfBoundsException error) { }
					}
				}
			}else {
				//VON OBEN NACH UNTEN
				for(int x = midField.X+(StandardData.fielDraw_fieldCountX/2)+StandardData.fieldDraw_sicherheitsFaktor ; x > midField.X-(StandardData.fielDraw_fieldCountX/2)-StandardData.fieldDraw_sicherheitsFaktor ; x--) {
					for(int y = midField.Y-(StandardData.fielDraw_fieldCountY/2)-StandardData.fieldDraw_sicherheitsFaktor ; y < midField.Y+(StandardData.fielDraw_fieldCountY/2)+StandardData.fieldDraw_sicherheitsFaktor ; y++) {
						try{
							CreateMapData.createMap_FieldList[x][y].draw(g);
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
							CreateMapData.createMap_FieldList[x][y].draw(g);
						}catch(ArrayIndexOutOfBoundsException error) { }
					}
				}
			}else {
				//VON OBEN NACH UNTEN
				for(int x = midField.X-(StandardData.fielDraw_fieldCountX/2)-StandardData.fieldDraw_sicherheitsFaktor ; x < midField.X+(StandardData.fielDraw_fieldCountX/2)+StandardData.fieldDraw_sicherheitsFaktor ; x++) {
					for(int y = midField.Y-(StandardData.fielDraw_fieldCountY/2)-StandardData.fieldDraw_sicherheitsFaktor ; y < midField.Y+(StandardData.fielDraw_fieldCountY/2)+StandardData.fieldDraw_sicherheitsFaktor ; y++) {
						try{
							CreateMapData.createMap_FieldList[x][y].draw(g);
						}catch(ArrayIndexOutOfBoundsException error) { }
					}
				}
			}
		}
		if(CreateMapData.choosenField != null) {
			CreateMapData.choosenField.drawHighlight(g, Color.BLACK);
		}
		
	}
	
//==========================================================================================================
	/**
	 * Draws the buildings of the map
	 */
	public static void draw_buildings(Graphics g) {
		
		Field midField = CreateMapHandler.get_MID_Field(false);
		
		int minX = midField.X-(StandardData.fielDraw_fieldCountX/2)-StandardData.fieldDraw_sicherheitsFaktor, maxX = midField.X+(StandardData.fielDraw_fieldCountX/2)+StandardData.fieldDraw_sicherheitsFaktor;
		int minY = midField.Y-(StandardData.fielDraw_fieldCountY/2)-StandardData.fieldDraw_sicherheitsFaktor, maxY = midField.Y+(StandardData.fielDraw_fieldCountY/2)+StandardData.fieldDraw_sicherheitsFaktor;
		
		for(Building building : CreateMapData.HQdisplayList) {
			if(building instanceof Building_Headquarter) {
				
				if(building.connectedField.X >= minX && building.connectedField.X <= maxX && building.connectedField.Y >= minY && building.connectedField.Y <= maxY) {
					//IN RANGE -> Should be drawn
					building.draw_Field(g, true);
				}
				
			}else {
				ConsoleOutput.printMessageInConsole("A non HQ building should be drawn on the createMapEditor!", true);
			}
		}
		
	}
	
}
