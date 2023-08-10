package me.bejosch.battleprogress.client.Handler;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import me.bejosch.battleprogress.client.Data.CreateMapData;
import me.bejosch.battleprogress.client.Data.FileData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Headquarter;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Window.Buttons.Buttons;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class CreateMapHandler {
	
	
//==========================================================================================================
	//EVENTS
	/**
	 * Called if a key has been pressed
	 */
	public static void keyPressedEvent(int keyCode) {
		
		
		
	}
	
//==========================================================================================================
	//EVENTS
	/**
	 * Called if a key has been released
	 */
	public static void keyReleaseEvent(int keyCode) {
		
		if(keyCode == KeyEvent.VK_SPACE && CreateMapData.overlayedInput == false) {
			if(CreateMapData.showHUD == true) {
				CreateMapHandler.hideHUD();
			}else {
				CreateMapHandler.showHUD();
			}
		}else if(keyCode == KeyEvent.VK_R) {
			if(CreateMapData.choosenField != null) {
				CreateMapData.currentFieldBuild = CreateMapData.choosenField.type;
			}
		}
		
		if(keyCode == KeyEvent.VK_T) {
			
			Images.updateFieldImages(true);
			
		}
		
	}
	
//==========================================================================================================
	//EVENTS
	/**
	 * Called if the mouse has been clicked
	 */
	public static void mouseClickedEvent(int mX, int mY, int clickType) {
		
		StandardData.showGrid = false;
		
		if(eventBlocked(mX, mY)) {
			//FALLS BLOCKED TROTZDEM RESETT
			MouseHandler.mouseReleasePoint = null;
			MouseHandler.mousePressPoint = null;
			return;
		}
		
		MouseHandler.mouseReleasePoint = new Point(mX, mY);
		
		//RECHTSKLICK CANLCED
		if(clickType == MouseEvent.BUTTON3) {
			
			MouseHandler.mousePressPoint = null;
			MouseHandler.mouseReleasePoint = null;
			
			return;
		}
		
		if(MouseHandler.mousePressPoint != null && MouseHandler.mouseReleasePoint != null) { 
			if(CreateMapHandler.getFieldByCoordinates(MouseHandler.mousePressPoint.x, MouseHandler.mousePressPoint.y) != null && CreateMapHandler.getFieldByCoordinates(MouseHandler.mouseReleasePoint.x, MouseHandler.mouseReleasePoint.y) != null) {
				Field press = CreateMapHandler.getFieldByCoordinates(MouseHandler.mousePressPoint.x, MouseHandler.mousePressPoint.y);
				Field released = CreateMapHandler.getFieldByCoordinates(MouseHandler.mouseReleasePoint.x, MouseHandler.mouseReleasePoint.y);
				
				//ONLY ONE FIELD MARKED
				if(press == released) {
					press.changeType(CreateMapData.currentFieldBuild);
				//MORE FIELDS MARKED
				}else { 
					if(press.X >= released.X) {
						if(press.Y >= released.Y) {
							for(int varX = press.X ; varX >= released.X ; varX--) {
								for(int varY = press.Y ; varY >= released.Y ; varY--) {
									Field changed = CreateMapData.createMap_FieldList[varX][varY];
									changed.changeType(CreateMapData.currentFieldBuild);
								}
							}
						}else {
							for(int varX = press.X ; varX >= released.X ; varX--) {
								for(int varY = released.Y ; varY >= press.Y ; varY--) {
									Field changed = CreateMapData.createMap_FieldList[varX][varY];
									changed.changeType(CreateMapData.currentFieldBuild);
								}
							}
						}
					}else {
						if(press.Y >= released.Y) {
							for(int varX = released.X ; varX >= press.X ; varX--) {
								for(int varY = press.Y ; varY >= released.Y ; varY--) {
									Field changed = CreateMapData.createMap_FieldList[varX][varY];
									changed.changeType(CreateMapData.currentFieldBuild);
								}
							}
						}else {
							for(int varX = released.X ; varX >= press.X ; varX--) {
								for(int varY = released.Y ; varY >= press.Y ; varY--) {
									Field changed = CreateMapData.createMap_FieldList[varX][varY];
									changed.changeType(CreateMapData.currentFieldBuild);
								}
							}
						}
					}
				}
				
				MouseHandler.mouseReleasePoint = null;
				MouseHandler.mousePressPoint = null;
				
			}

		}
		
	}
	
//==========================================================================================================
	//EVENTS
	/**
	 * Called if the mouse has been pressed
	 */
	public static void mousePressedEvent(int mX, int mY, int clickType) {
		
		if(eventBlocked(mX, mY)) {
			return;
		}
		
		MouseHandler.mousePressPoint = new Point(mX, mY);
		
		StandardData.showGrid = true;
		
	}
	
//==========================================================================================================
	//EVENTS
	/**
	 * Called if the mouse has been moved
	 */
	public static void mouseMovedEvent(int mX, int mY) {
		
		if(eventBlocked(mX, mY)) {
			return;
		}
		
		Field choosenField = CreateMapHandler.getFieldByCoordinates(mX, mY);
		if(choosenField != null) {
			CreateMapData.choosenField = choosenField;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Shows the HUD of the map editor
	 */
	public static void showHUD() {
		
		CreateMapData.showHUD = true;
		int mid = CreateMapData.createMap_X+CreateMapData.seperateBorderDistance+((CreateMapData.createMap_width-CreateMapData.seperateBorderDistance)/2);
		Buttons.showButton(Buttons.button_NextField, ">>", 17, mid+(StandardData.fieldSize/2)+15, CreateMapData.createMap_Y+40+((StandardData.fieldSize-60)/2), 25, 60);
		Buttons.showButton(Buttons.button_LaterField, "<<", 17, mid-(StandardData.fieldSize/2)-15-25, CreateMapData.createMap_Y+40+((StandardData.fieldSize-60)/2), 25, 60);
		Buttons.showButton(Buttons.button_DeleteMap, "Delete", 16, CreateMapData.createMap_X+25, CreateMapData.createMap_Y+17+(0*CreateMapData.distanceBetweenButtons), 100, 25);
		Buttons.showButton(Buttons.button_ClearMap, "Clear", 16, CreateMapData.createMap_X+25, CreateMapData.createMap_Y+17+(1*CreateMapData.distanceBetweenButtons), 100, 25);
		Buttons.showButton(Buttons.button_LoadMap, "Load", 16, CreateMapData.createMap_X+25, CreateMapData.createMap_Y+17+(2*CreateMapData.distanceBetweenButtons), 100, 25);
		Buttons.showButton(Buttons.button_SaveMap, "Save", 16, CreateMapData.createMap_X+25, CreateMapData.createMap_Y+17+(3*CreateMapData.distanceBetweenButtons), 100, 25);
		Buttons.showButton(Buttons.button_QuitEditor, "Leave Editor", 16, mid-70, CreateMapData.createMap_Y+CreateMapData.createMap_height-35, 140, 25);

		
	}
//==========================================================================================================
	/**
	 * Hides the HUD of the map editor
	 */
	public static void hideHUD() {
		
		CreateMapData.showHUD = false;
		Buttons.hideButton(Buttons.button_NextField);
		Buttons.hideButton(Buttons.button_LaterField);
		Buttons.hideButton(Buttons.button_DeleteMap);
		Buttons.hideButton(Buttons.button_ClearMap);
		Buttons.hideButton(Buttons.button_LoadMap);
		Buttons.hideButton(Buttons.button_SaveMap);
		Buttons.hideButton(Buttons.button_QuitEditor);
		CreateMapHandler.mouseMovedEvent(MouseHandler.mouseX, MouseHandler.mouseY);
		
	}
	
//==========================================================================================================
	/**
	 * Returns wether the event should happen or not
	 */
	public static boolean eventBlocked(int mX, int mY) {
		
		if(CreateMapData.overlayedInput == true) {
			//OVERVIEW WINDOW OPEN
			return true;
		}
		return false;
	}
	
//==========================================================================================================
	/**
	 * Start all parts that are needed to create the new map
	 */
	public static void startCreateMapHandler() {
		
		if(CreateMapData.createMap_FieldList == null) {
			//ERSTES MAL EDITOR BETRETEN
			CreateMapData.createMap_FieldList = new Field[StandardData.mapWidth][StandardData.mapHight];
			fillFieldList();
			fillHQdisplayList();
		}
		if(CreateMapData.choosenField == null) {
			CreateMapData.choosenField = CreateMapData.createMap_FieldList[0][0];
		}
		MovementHandler.startMovementTimer();
		
	}
	
//==========================================================================================================
	/**
	 * First preset fill fieldList with Grass
	 */
	public static boolean fillFieldList() {
		
		for(int x = 0 ; x < StandardData.mapWidth ; x++) {
			for(int y = 0 ; y < StandardData.mapHight ; y++) {
				CreateMapData.createMap_FieldList[x][y] = new Field(FieldType.Gras, x, y);
			}
		}
		return true;
		
	}
//==========================================================================================================
	/**
	 * Fills the list which displays the HQs
	 */
	public static void fillHQdisplayList() {
		
		new Building_Headquarter(-1, CreateMapData.createMap_FieldList[StandardData.HQ_1_2vs2.x][StandardData.HQ_1_2vs2.y]);
		new Building_Headquarter(-1, CreateMapData.createMap_FieldList[StandardData.HQ_2_2vs2.x][StandardData.HQ_2_2vs2.y]);
		new Building_Headquarter(-1, CreateMapData.createMap_FieldList[StandardData.HQ_3_2vs2.x][StandardData.HQ_3_2vs2.y]);
		new Building_Headquarter(-1, CreateMapData.createMap_FieldList[StandardData.HQ_4_2vs2.x][StandardData.HQ_4_2vs2.y]);
		
	}
	
//==========================================================================================================
	/**
	 * Add a given MapName to the list in the config file
	 * @param newMapName - String - The mapName should be added
	 * @return boolean - true if add worked, false if mapName allready added or sth went wrong
	 */
	public static boolean addMapNameToConfigList(String newMapName) {
		
		List<String> currentMapList = Funktions.getStringListFromString(FileHandler.readOutData(FileData.file_Maps, "MapList"));
		if(currentMapList.contains("[]")) {
			currentMapList.remove("[]");
		}
		if(currentMapList.contains(newMapName)) { //NAME ALLREADY IN THE LIST
			return false;
		}
		currentMapList.add(newMapName);
		return FileHandler.saveDataInFile(FileData.file_Maps, "MapList", currentMapList.toString());
		
	}
//==========================================================================================================
	/**
	 * Removes a given MapName from the list in the config file
	 * @param mapName - String - The mapName should be removed
	 * @return boolean - true if remove worked, false if mapName not added or sth went wrong
	 */
	public static boolean removeMapNameToConfigList(String mapName) {
		
		List<String> currentMapList = Funktions.getStringListFromString(FileHandler.readOutData(FileData.file_Maps, "MapList"));
		if(currentMapList.contains("[]")) {
			currentMapList.remove("[]");
		}
		if(currentMapList.contains(mapName)) { //NAME IN THE LIST
			currentMapList.remove(mapName);
			return FileHandler.saveDataInFile(FileData.file_Maps, "MapList", currentMapList.toString());
		}else {
			return false;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Add a new Map to the config
	 * @param newMapName - String - The mapName should be added
	 * @return String - if equals "worked" it worked, else it returns why it not worked
	 */
	public static String saveNewMap(String newMapName) {
		
		if(convertFieldListIntoStringSyntax().equalsIgnoreCase("")) {
			//ONLY GRASS - EMPTY
			return "Can't save an empty map!";
		}
		
		newMapName = Funktions.checkStringForByteUse(newMapName);
		
		if(addMapNameToConfigList(newMapName) == true) { //NAME ACCEPTED
			FileHandler.saveDataInFile(FileData.file_Maps, newMapName+"_Fields", convertFieldListIntoStringSyntax()); //FIELDS
			return "worked"; //true
		}else {
			return "Map name allready in use!";	
		}
		
	}
	
//==========================================================================================================
	/**
	 * Load the Map from the config
	 * @param mapName - String - The mapName should be loaded
	 * @return boolean - true if remove worked, false if mapName not found or sth went wrong
	 */
	public static boolean loadMap(String mapName) {
		
		mapName = Funktions.checkStringForByteUse(mapName);
		List<String> currentMapList = Funktions.getStringListFromString(FileHandler.readOutData(FileData.file_Maps, "MapList"));
		
		if(currentMapList.contains(mapName)) { //MAP FOUND
			fillFieldList(); //REFILL MAP WITH GRASS
			readOutFieldDataToLoadedMap(FileHandler.readOutData(FileData.file_Maps, mapName+"_Fields")); //FIELDS
			
			return true;
			
		}
		
		return false;
		
	}
	
//==========================================================================================================
	/**
	 * Removes a Map from the config
	 * @param mapName - String - The mapName should be removed
	 * @return boolean - true if remove worked, false if mapName not added or sth went wrong
	 */
	public static boolean deleteMap(String mapName) {
		
		mapName = Funktions.checkStringForByteUse(mapName);
		
		if(removeMapNameToConfigList(mapName) == true) { //NAME ACCEPTED
			FileHandler.saveDataInFile(FileData.file_Maps, mapName+"_Fields", null); //FIELDS
			return true;
		}
		
		return false;
		
	}
	
//==========================================================================================================
	/**
	 * Load the field data out of the String
	 * @param fieldData - String - The data of the fields
	 */
	public static void readOutFieldDataToLoadedMap(String fieldData) {
		
		String[] data = fieldData.split("-");
		for(String field : data) {
			FieldType type = FieldType.getFieldTypeFromSignal(field.split(":")[0]);
			int X = Integer.parseInt(field.split(":")[1]);
			int Y = Integer.parseInt(field.split(":")[2]);
			CreateMapData.createMap_FieldList[X][Y] = new Field(type, X, Y);
		}
		
	}
	
//==========================================================================================================
	/**
	 * Converts the createMapFieldList with a syntax into a String
	 * @return String - The string with the field data
	 */
	public static String convertFieldListIntoStringSyntax() {
		
		String output = "";
		
		for(int x = 0 ; x < StandardData.mapWidth ; x++) {
			for(int y = 0 ; y < StandardData.mapHight ; y++) {
				
				Field field = CreateMapData.createMap_FieldList[x][y];
				
				if(field.type != FieldType.Gras) {
					if(output.length() > 2) { //NICHT DAS ERSTE FELD
						output = output+"-"+FieldType.getShortcutForFieldType(field.type)+":"+field.X+":"+field.Y;
					}else { //DAS ERSTE fELD OHNE -
						output = FieldType.getShortcutForFieldType(field.type)+":"+field.X+":"+field.Y;
					}
				}
				
			}
		}
		
		return output;
		
	}
	
//==========================================================================================================
	/**
	 * Get a field of the createMap by the given coordinates
	 * @param mouseX - int - The X coordinate of the mouse
	 * @param mouseY - int - The Y coordinate of the mouse
	 * @return {@link Field} - The found field or null
	 */
	public static Field getFieldByCoordinates(int mouseX, int mouseY) {
		
		int realX = (mouseX - CreateMapData.scroll_CM_LR_count) / (StandardData.fieldSize);
		int realY = (mouseY - CreateMapData.scroll_CM_UD_count) / (StandardData.fieldSize);
		
		try{
			return CreateMapData.createMap_FieldList[realX][realY];
		}catch(IndexOutOfBoundsException | NullPointerException  error) {
			return null;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Get TOP LEFT corner field
	 * @return {@link Field} - The field in this corner or null
	 */
	public static Field get_TOP_LEFT_CornerField() {
		
		Field field = getFieldByCoordinates(0, 0);
		
		if(field != null) {
			return field;
		}else {
			return CreateMapData.createMap_FieldList[0][0];
		}
	}
	
	private static Field last_midField = null;
//==========================================================================================================
	/**
	 * Get MID field
	 * @param couldReturnNull - boolean - if true, null could be returned if no midField could be found, if false the last midField will be returned in this case
	 * @return {@link Field} - The field in the middle or if null: 1) couldReturnNull==true -> null 2) if couldReturnNull==false -> The last midField, but remember that it could be still null if the last midField is null too
	 */
	public static Field get_MID_Field(boolean couldReturnNull) {
		
		Field field = getFieldByCoordinates(WindowData.FrameWidth/2, WindowData.FrameHeight/2);
		
		if(field != null) {
			last_midField = field;
			return field;
		}else {
			if(couldReturnNull == true || last_midField == null) {
				return null;
			}else {
				return last_midField;
			}
		}
	}
	
//==========================================================================================================
	/**
	 * Get BOTTOM RIGHT corner field
	 * @return {@link Field} - The field in this corner or null
	 */
	public static Field get_BOTTOM_RIGHT_CornerField() {
		
		Field field = getFieldByCoordinates(WindowData.FrameWidth, WindowData.FrameHeight);
		
		if(field != null) {
			return field;
		}else {
			return CreateMapData.createMap_FieldList[StandardData.mapWidth-1][StandardData.mapHight-1];
		}
	}
	
//==========================================================================================================
	/**
	 * Shows whether there is space to the LEFT or not
	 * @return boolean - true - there is space, false - there isn't
	 */
	public static boolean left_SpaceFree() {
		if(getFieldByCoordinates((WindowData.FrameWidth/2) - 10, (WindowData.FrameHeight/2)) == null) { return false; }else { return true; }
	}
	/**
	 * Shows whether there is space to the RIGHT or not
	 * @return boolean - true - there is space, false - there isn't
	 */
	public static boolean right_SpaceFree() {
		if(getFieldByCoordinates((WindowData.FrameWidth/2) + 10, (WindowData.FrameHeight/2)) == null) { return false; }else { return true; }
	}
	/**
	 * Shows whether there is space to the TOP or not
	 * @return boolean - true - there is space, false - there isn't
	 */
	public static boolean top_SpaceFree() {
		if(getFieldByCoordinates((WindowData.FrameWidth/2), (WindowData.FrameHeight/2) - 10) == null) { return false; }else { return true; }
	}
	/**
	 * Shows whether there is space to the BOTTOM or not
	 * @return boolean - true - there is space, false - there isn't
	 */
	public static boolean bottom_SpaceFree() {
		if(getFieldByCoordinates((WindowData.FrameWidth/2), (WindowData.FrameHeight/2) + 10) == null) { return false; }else { return true; }
	}
	
}
