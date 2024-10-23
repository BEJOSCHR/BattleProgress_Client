package me.bejosch.battleprogress.client.Funktions;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import me.bejosch.battleprogress.client.Data.CreateMapData;
import me.bejosch.battleprogress.client.Data.FileData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Handler.ButtonHandler;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Funktions {
	
	
	//TODO FORMELN:
	// FieldXY = (screenCoordinate - LRUD_count) / fiedlSize 	//FORMEL F�R FIELD COORDINATES
	// screenCoordinate = (FieldXY * fieldSize) + LRUD_count 	//FORMEL F�R SCREEN COORDINATES
	// LRUD_count = (FieldXY * fieldSize) 						//FORMEL F�R LR/UD COUNT
	
	private static Comparator<Integer> sorter = new Comparator<Integer>() {

		@Override
		public int compare(Integer i1, Integer i2) {
			
			if(i1 > i2) return +1;
			if(i1 < i2) return -1;
			
			return 0;
		}
	};
	
	public static String getDoubleWritenNumber(int number) {
		if(number <= 9) {
			return "0"+number;
		}else {
			return ""+number;
		}
	}
	
//==========================================================================================================
	/**
	 * Move the screen to the given Field Coordinates, so that this field is shown in the center of this field
	 * @param X - int x-coordinate of the new center field
	 * @param Y - int y-coordinate of the new center field
	 */
	public static void moveScreenToFieldCoordinates(int fieldX, int fieldY) {
		
		// LRUD_count = -(FieldXY * fieldSize) + mitteVerschiebungXY      [BEI Y +1 BEIM FIELD DAMIT DAS MENU NICHT �BERDECKT USW]
		int mitteVerschiebungX = ( (WindowData.FrameWidth/StandardData.fieldSize)/2)*StandardData.fieldSize; //ZAHL AN FELDERN AUF DEM SCREEN UND DAVON DIE H�LFTE UND DAS DANN MAL FIELDSIZE
		int mitteVerschiebungY = ( (WindowData.FrameHeight/StandardData.fieldSize)/2)*StandardData.fieldSize; //ZAHL AN FELDERN AUF DEM SCREEN UND DAVON DIE H�LFTEUND DAS DANN MAL FIELDSIZE
		int newLR_count = -(fieldX * StandardData.fieldSize) + mitteVerschiebungX;
		int newUD_count = -( (fieldY+1) * StandardData.fieldSize) + mitteVerschiebungY;
		GameData.scroll_LR_count = newLR_count;
		GameData.scroll_UD_count = newUD_count;
		
	}
	
//==========================================================================================================
	/**
	 * Draws a healthbar with the given values
	 * @param g - {@link Graphics} - The graphic where everything should be drawn on
	 * @param X - int x-coordinate of the top-left-corner in pixels on the screen
	 * @param Y - int y-coordinate of the top-left-corner in pixels on the screen
	 * @param width - int - The width of the healthbar
	 * @param height - int - The height of the healthbar
	 * @param maxHealth - int - The max health value of the referenced object
	 * @param totalHealth - int - The total health value of the referenced object
	 */
	public static void drawHealthbar(Graphics g, int X, int Y, int width, int height, int maxHealth, int totalHealth) {
		
		Color green = new Color(50, 205, 50, 255), red = new Color(238, 0, 0, 255);
		float greenAnteil = (float)((double)totalHealth/(double)maxHealth);
		int greenWidth = Math.round((float)(width*greenAnteil));
		int redWidth = width-greenWidth;
		
		//BALKEN + MITTEL STRICH
		g.setColor(green);
		g.fillRect(X+1, Y+1, greenWidth-1, height-1);
		g.setColor(Color.DARK_GRAY);
		g.drawLine(X+greenWidth, Y, X+greenWidth, Y+height);
		g.setColor(red);
		g.fillRect(X+1+greenWidth, Y+1, redWidth-1, height-1);
		
		//RAHMEN
		g.setColor(Color.DARK_GRAY);
		g.drawRect(X, Y, width, height);
		
	}
	
//==========================================================================================================
	/**
	 * Draws a line between the center of field one to the center of field two
	 * @param field_1 - {@link Field} - The first field
	 * @param field_2 - {@link Field} - The second field
	 * @param color - {@link Color} - The color of the line
	 */
	public static void drawLineBetweenFields(Graphics g, Field field_1, Field field_2, Color color) {
		
		int fieldPixleX_1 = Funktions.getPixlesByCoordinate(field_1.X, true, false);
		int fieldPixleY_1 = Funktions.getPixlesByCoordinate(field_1.Y, false, false);
		int fieldPixleX_2 = Funktions.getPixlesByCoordinate(field_2.X, true, false);
		int fieldPixleY_2 = Funktions.getPixlesByCoordinate(field_2.Y, false, false);
		int halfSize = StandardData.fieldSize/2;
		
		g.setColor(color);
		g.drawLine(fieldPixleX_1+halfSize, fieldPixleY_1+halfSize, fieldPixleX_2+halfSize, fieldPixleY_2+halfSize);
		
	}
	
//==========================================================================================================
	/**
	 * Draws a border around the given field
	 * @param field_1 - {@link Field} - The first fiel
	 * @param color - {@link Color} - The color of the line
	 * @param spaceToTheSides - int - The distance/border to the normal field borders
	 */
	public static void drawBorderAroundFields(Graphics g, Field field, Color color, int spaceToTheSides, int thickness) {
		
		int fieldPixleX = Funktions.getPixlesByCoordinate(field.X, true, false)-thickness/2;
		int fieldPixleY = Funktions.getPixlesByCoordinate(field.Y, false, false)-thickness/2;
		
		g.setColor(color);
		int width = StandardData.fieldSize-(spaceToTheSides*2);
		g.fillRect(fieldPixleX+spaceToTheSides, fieldPixleY+spaceToTheSides, thickness, width);
		g.fillRect(fieldPixleX+spaceToTheSides, fieldPixleY+spaceToTheSides, width, thickness);
		g.fillRect(fieldPixleX+spaceToTheSides+width, fieldPixleY+spaceToTheSides, thickness, width+thickness);
		g.fillRect(fieldPixleX+spaceToTheSides, fieldPixleY+spaceToTheSides+width, width+thickness, thickness);
		
	}
	
//==========================================================================================================
	/**
	 * Draws a line around the given fields
	 */
	public static void drawBorderAroundFieldArea(Graphics g, List<FieldCoordinates> availableFields, Color color, int width) {
		
		for(FieldCoordinates fieldCoordinates_1 : availableFields) {
			boolean drawLEFTLine = true, drawTOPLine = true, drawRIGHTLine = true, drawBOTTOMLine = true;
			for(FieldCoordinates fieldCoordinates_2 : availableFields) {
				if(fieldCoordinates_1.checkFor_LEFT_partner(fieldCoordinates_2)) {
					drawLEFTLine = false;
				}else if(fieldCoordinates_1.checkFor_TOP_partner(fieldCoordinates_2)) {
					drawTOPLine = false;
				}else if(fieldCoordinates_1.checkFor_RIGHT_partner(fieldCoordinates_2)) {
					drawRIGHTLine = false;
				}else if(fieldCoordinates_1.checkFor_BOTTOM_partner(fieldCoordinates_2)) {
					drawBOTTOMLine = false;
				}
			}
			g.setColor(color);
			int markerWidth = width;
			int fieldPixleX = Funktions.getPixlesByCoordinate(fieldCoordinates_1.X, true, false);
			int fieldPixleY = Funktions.getPixlesByCoordinate(fieldCoordinates_1.Y, false, false);
			if(drawLEFTLine == true) {
				g.fillRect(fieldPixleX, fieldPixleY, markerWidth, StandardData.fieldSize);
			}
			if(drawTOPLine == true) {
				g.fillRect(fieldPixleX, fieldPixleY, StandardData.fieldSize, markerWidth);
			}
			if(drawRIGHTLine == true) {
				g.fillRect(fieldPixleX+StandardData.fieldSize-markerWidth, fieldPixleY, markerWidth, StandardData.fieldSize);
			}
			if(drawBOTTOMLine == true) {
				g.fillRect(fieldPixleX, fieldPixleY+StandardData.fieldSize-markerWidth, StandardData.fieldSize, markerWidth);
			}
		}
		
	}
	
//==========================================================================================================
	/**
	 * Calculate the HQ {@link FieldCoordinates} for the given player
	 * @param playerID - {@link Integer} - The ID of the HQ owner
	 * @return {@link FieldCoordinates} - The coordinates of the HQ field
	 */
	public static FieldCoordinates getHQfieldCoordinatesByPlayerID(int playerID) {
		
		//CALCULATE EXECUTE COORDINATES
		if(SpielModus.isGameModus1v1()) {
			//1VS1
			if(playerID == GameData.playingPlayer[0].getID()) {
				//Player 1
				return new FieldCoordinates(StandardData.HQ_1_1vs1.x, StandardData.HQ_1_1vs1.y);
			}else if(playerID == GameData.playingPlayer[1].getID()) {
				//Player 2
				return new FieldCoordinates(StandardData.HQ_2_1vs1.x, StandardData.HQ_2_1vs1.y);
			}else {
				ConsoleOutput.printMessageInConsole("The getHQcoordinates function found no player for the given playerID - 1vs1 [ID: "+playerID+"]", true);
				return null;
			}
		}else {
			//2VS2
			if(playerID == GameData.playingPlayer[0].getID()) {
				//Player 1
				return new FieldCoordinates(StandardData.HQ_1_2vs2.x, StandardData.HQ_1_2vs2.y);
			}else if(playerID == GameData.playingPlayer[1].getID()) {
				//Player 2
				return new FieldCoordinates(StandardData.HQ_2_2vs2.x, StandardData.HQ_2_2vs2.y);
			}else if(playerID == GameData.playingPlayer[2].getID()) {
				//Player 3
				return new FieldCoordinates(StandardData.HQ_3_2vs2.x, StandardData.HQ_3_2vs2.y);
			}else if(playerID == GameData.playingPlayer[3].getID()) {
				//Player 4
				return new FieldCoordinates(StandardData.HQ_4_2vs2.x, StandardData.HQ_4_2vs2.y);
			}else {
				ConsoleOutput.printMessageInConsole("The getHQcoordinates function found no player for the given playerID - 2vs2 [ID: "+playerID+"]", true);
				return null;
			}
		}
		
	}
	
//==========================================================================================================
	/**
	 * Get the pixles on the screen witch representing the fieldCoordinate
	 * @param fieldCoordinate - int - The fieldCoordinate, witch should be transleted into pixles on the screen
	 * @param isItX - boolean - if true its transleted to an X coordinate, if false to a Y coordinate
	 * @param isItCreateMap - if true its relative to the createMapValues, if false the normalGameValues are used
	 * @return int - The transleted Pixles
	 */
	public static int getPixlesByCoordinate(int fieldCoordinate, boolean isItX, boolean isItCreateMap) {
		
		if(isItCreateMap == true) {
			//CREATE MAP
			if(isItX == true) {
				//X
				return (fieldCoordinate * StandardData.fieldSize)+CreateMapData.scroll_CM_LR_count;
			}else {
				//Y
				return (fieldCoordinate * StandardData.fieldSize)+CreateMapData.scroll_CM_UD_count;
			}
		}else {
			//NORMAL GAME
			if(isItX == true) {
				//X
				return (fieldCoordinate * StandardData.fieldSize)+GameData.scroll_LR_count;
			}else {
				//Y
				return (fieldCoordinate * StandardData.fieldSize)+GameData.scroll_UD_count;
			}
		}
		
	}
	
//==========================================================================================================
	/**
	 * Get the building list of the player
	 * @param playerID - {@link integer} - The ID of the player
	 * @return List(Building) - The building list of the player or null if not found 
	 */
	public static List<Building> getBuildingListByPlayerID(int playerID) {
		
		if(playerID == GameData.playingPlayer[0].getID()) {
			return GameData.player1_buildings;
		}else if(playerID == GameData.playingPlayer[1].getID()) {
			return GameData.player2_buildings;
		}else if(playerID == GameData.playingPlayer[2].getID()) {
			return GameData.player3_buildings;
		}else if(playerID == GameData.playingPlayer[3].getID()) {
			return GameData.player4_buildings;
		}else {
			return null;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Get the building list of the player team
	 * @param playerID - {@link Integer} - The ID of the player of the team
	 * @return List(Building) - The building list of the player team (is empty if no team found)
	 */
	public static List<Building> getBuildingListByPlayerTeam(int playerID) {
		
		List<Building> output = new ArrayList<>();
		
		for(int playerTeamIDs : GameHandler.getAlliedPlayersByID(playerID)) {
			output.addAll( getBuildingListByPlayerID(playerTeamIDs) );
		}
		
		return output;
		
	}

//==========================================================================================================
	/**
	 * Get a building list with ALL buildings
	 * @return List(Building) - The list of all buildings in the game
	 */
	public static List<Building> getAllBuildingList() {
		
		List<Building> allBuildings = new ArrayList<>();
		allBuildings.addAll(GameData.player1_buildings);
		allBuildings.addAll(GameData.player2_buildings);
		allBuildings.addAll(GameData.player3_buildings);
		allBuildings.addAll(GameData.player4_buildings);
		return allBuildings;
		
	}
	
//==========================================================================================================
	/**
	 * Get the troup list of the player
	 * @param playerID - {@link Integer} - The ID of the player
	 * @return List(Troup) - The troup list of the player or null if not found 
	 */
	public static List<Troup> getTroupListByPlayerID(int playerID) {
		
		if(playerID == GameData.playingPlayer[0].getID()) {
			return GameData.player1_troups;
		}else if(playerID == GameData.playingPlayer[1].getID()) {
			return GameData.player2_troups;
		}else if(playerID == GameData.playingPlayer[2].getID()) {
			return GameData.player3_troups;
		}else if(playerID == GameData.playingPlayer[3].getID()) {
			return GameData.player4_troups;
		}else {
			return null;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Get the Troup list of the player team
	 * @param playerID - {@link Integer} - The ID of the player of the team
	 * @return List(Troup) - The Troup list of the player team (is empty if no team found)
	 */
	public static List<Troup> getTroupListByPlayerTeam(int playerID) {
		
		List<Troup> output = new ArrayList<>();
		
		for(int playerTeamIDs : GameHandler.getAlliedPlayersByID(playerID)) {
			output.addAll( getTroupListByPlayerID(playerTeamIDs) );
		}
		
		return output;
		
	}
	
//==========================================================================================================
	/**
	 * Get a troup list with ALL troups
	 * @return List(Troup) - The list of all troups in the game
	 */
	public static List<Troup> getAllTroupList() {
		
		List<Troup> allTroups = new ArrayList<>();
		allTroups.addAll(GameData.player1_troups);
		allTroups.addAll(GameData.player2_troups);
		allTroups.addAll(GameData.player3_troups);
		allTroups.addAll(GameData.player4_troups);
		return allTroups;
		
	}
	
//==========================================================================================================
	/**
	 * Get the Color of a player
	 * @param playerNumber - int - The number of the player
	 * @return Color - The color which represents this player
	 */
	public static Color getColorByPlayerNumber(int playerNumber) {
		
		if(playerNumber == 1) {
			return Color.RED;
		}else if(playerNumber == 2) {
			return Color.BLUE;
		}else if(playerNumber == 3) {
			return Color.GREEN;
		}else if(playerNumber == 4) {
			return Color.YELLOW;
		}else {
			return Color.PINK;
		}
		
	}

	
//==========================================================================================================
	/**
	 * Get the Color of a player by his ID
	 * @param playerID - int - The ID of the player
	 * @return Color - The color which represents this player
	 */
	public static Color getColorByPlayerID(int playerID) {
		
		if(playerID == GameData.playingPlayer[0].getID()) {
			return Color.RED;
		}else if(playerID == GameData.playingPlayer[1].getID()) {
			return Color.BLUE;
		}else if(playerID == GameData.playingPlayer[2].getID()) {
			return Color.GREEN;
		}else if(playerID == GameData.playingPlayer[3].getID()) {
			return Color.YELLOW;
		}else {
			return Color.ORANGE;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Convert an String Pattern into an String ArrayList
	 * @param Input - String[] - The String Pattern should be converted
	 * @return ArrayList(String) - The converted ArrayList
	 */
	public static List<String> ArrayFromPattern(String[] Input) {
		List<String> Output = new ArrayList<String>();
		for(String Inhalt : Input) {
			Output.add(Inhalt);
		}
		return Output;
	}
	
//==========================================================================================================
	/**
	 * Convert an set(integer) into an integer ArrayList
	 * @param Input - set(integer) - The set(integer) should be converted
	 * @return ArrayList(Integer) - The converted ArrayList
	 */
	public static List<Integer> ArrayFromSet(Set<Integer> input) {
		List<Integer> Output = new ArrayList<Integer>();
		for(int Inhalt : input) {
			Output.add(Inhalt);
		}
		return Output;
	}
	
//==========================================================================================================
	/**
	 * Sort an given Set of Integers from high to low
	 * @param Input - Set(Integer) - The Integer Set witch should be sorted
	 * @return ArrayList(Integer) - The sorted ArrayList
	 */
	public static List<Integer> sortIntList(Set<Integer> input) {
		List<Integer> Output = new ArrayList<Integer>();
		for(int number : input) {
			Output.add(number);
		}
		Output.sort(sorter); //SORT WITH SORTER
		return Output;
	}
	
//==========================================================================================================
	/**
	 * Cuts the end of a List to a given length
	 * @param Input - List() - The List this should be cutted to the length
	 * @param targetLength - int - The target length the list should have at the end
	 * @return ArrayList() - The cutted ArrayList
	 */
	public static List<Integer> cutListToLength(List<Integer> input, int targetLength) {
		while(input.size() > targetLength) {
			input.remove(input.size()-1);
		}
		return input;
	}
	
//==========================================================================================================
	/**
	 * Load an Image from the path
	 * @param name - String - The name of the image with ending!
	 * @return Image - The loaded Image, null - if no image found with this name!
	 */
	public static Image loadImageFromName(String name) { return loadImageFromName(name, true); }
	public static Image loadImageFromName(String name, boolean errorMessage) {
		try {
			Image img = ImageIO.read(new File(FileData.Ordner+"/Images/"+name));
			return img;
		}catch(IOException | IllegalArgumentException e) {
			if(errorMessage) { e.printStackTrace(); }
			if(errorMessage) { ConsoleOutput.printMessageInConsole("The image '"+name+"' could not be loaded!", true); }
		}
		return null;
	}
	
//==========================================================================================================
	/**
	 * Get an Image from the connected FieldType
	 * @param type - {@link FieldType} - The type of the field
	 * @return Image - The image found for the type or null of not
	 */
	public static Image getFieldImageFromFieldType(FieldType type) {
		
		switch(type) {
		case Flatland:
			return Images.field_Gras;
		case Path:
			return Images.field_Path;
		case Mountain:
			return Images.field_Stone;
		case Ocean:
			return Images.field_Water;
		case Ressource:
			return Images.field_Ressource;
		case Consumed:
			return Images.field_RessourceVerbraucht;
		default:
			break;
		}
		return null;
		
	}
	
//==========================================================================================================
	/**
	 * Get the following FieldType from a previous FieldType
	 * @param type - {@link FieldType} - The type of the field
	 * @return {@link FieldType} - The fFieldType found for the type or null of not
	 */
	public static FieldType getNextFieldTypeFromPreviousFieldType(FieldType type) {
		
		switch(type) {
		case Flatland:
			return FieldType.Ocean;
		case Path:
			return FieldType.Ressource;
		case Mountain:
			return FieldType.Path;
		case Ocean:
			return FieldType.Mountain;
		case Ressource:
			return FieldType.Flatland;
		case Consumed:
			break;
		default:
			break;
		}
		return null;
		
	}
//==========================================================================================================
	/**
	 * Get previous FieldType from the next FieldType
	 * @param type - {@link FieldType} - The type of the field
	 * @return {@link FieldType} - The fFieldType found for the type or null of not
	 */
	public static FieldType getPreviousFieldTypeFromPreviousFieldType(FieldType type) {
		
		switch(type) {
		case Flatland:
			return FieldType.Ressource;
		case Path:
			return FieldType.Mountain;
		case Mountain:
			return FieldType.Ocean;
		case Ocean:
			return FieldType.Flatland;
		case Ressource:
			return FieldType.Path;
		case Consumed:
			break;
		default:
			break;
		}
		return null;
		
	}
	
//==========================================================================================================
	/**
	 * Closes all actions witch can spam for the given delay
	 * @param delayMiliSec - int - The delay should be closed in MiliSeconds
	 */
	public static void activateAntiSpam(int delayMiliSec) {
		
		StandardData.antiSpam = true;
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				StandardData.antiSpam = false;
			}
		}, delayMiliSec);
		
	}
	
//==========================================================================================================
	/**
	 * Create a new button
	 * @return {@link JButton} - The created button
	 */
	public static JButton createButton() {
		
		final JButton button = new JButton();
		button.setVisible(false);
		button.addActionListener(new ButtonHandler());
		button.setBackground(Color.LIGHT_GRAY);
		button.setForeground(Color.BLACK);
		button.setBorder(null);
		button.setFocusable(false);
		button.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				button.setForeground(Color.WHITE);
				button.setBackground(new Color(100, 0, 0, 150));
			}
			public void mouseExited(MouseEvent evt) {
				button.setForeground(Color.BLACK);
				button.setBackground(Color.LIGHT_GRAY);
			}
		});
		
		WindowData.Label_Main.add(button);
		
		return button;
	}
	
//==========================================================================================================
	/**
	 * Create a new JTextField
	 * @return {@link JTextField} - The created textField
	 */
	public static JTextField createTextField() {
		
		final JTextField textField = new JTextField();
		textField.setVisible(false);
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setForeground(Color.BLACK);
		
		WindowData.Label_Main.add(textField);
		
		return textField;
	}
	
//==========================================================================================================
	/**
	 * Create a new JTextArea
	 * @return {@link JTextArea} - The created textArea
	 */
	public static JTextArea createTextArea() {
		
		final JTextArea textArea = new JTextArea();
		textArea.setVisible(false);
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBackground(Color.DARK_GRAY);
		textArea.setForeground(Color.WHITE);
		
		WindowData.Label_Main.add(textArea);
		
		return textArea;
	}
	
//==========================================================================================================
	/**
	 * Create a new JScrollPane
	 * @return {@link JScrollPane} - The created scrollPane
	 */
	public static JScrollPane createScrollPane(Component component, boolean UPDOWN_SB, boolean RIGHTLEFT_SB) {
		
		int UPDOWN, LEFTRIGHT;
		if(UPDOWN_SB == true) {
			UPDOWN = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS;
		}else {
			UPDOWN = JScrollPane.VERTICAL_SCROLLBAR_NEVER;
		}
		if(RIGHTLEFT_SB == true) {
			LEFTRIGHT = JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS;
		}else {
			LEFTRIGHT = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER;
		}
		
		final JScrollPane scrollPane = new JScrollPane(component, UPDOWN, LEFTRIGHT);
		scrollPane.setVisible(false);
		
		WindowData.Label_Main.add(scrollPane);
		
		return scrollPane;
	}
	
//==========================================================================================================
	/**
	 * Convert a String into a List of Strings with a special Syntax
	 * @param input - String - The string should be converted
	 * @return List(String) - The List read out of the Strings, could be empty!
	 */
	public static List<String> getStringListFromString(String input) {
		
		input = input.replace("[", "");
		input = input.replace("]", "");
		
		List<String> list = new ArrayList<String>();
		if(input.contains(", ")) {
			for(String result : input.split(", ")) {
				list.add(result);
			}
		}else {
			list.add(input);
		}
		return list;
		
	}
	
//==========================================================================================================
	/**
	 * Checks i a given string is a valid username
	 * @param input - String - The String witch should be checked
	 * @return String - null if the input is valid; An error message if not valid
	 */
	public static String checkForUsernameValidation(String input) {
		
		if(input.length() < 4) {
			return "The username is to short!";
		}else if(input.length() > 12) {
			return "The username is to long!";
		}else if(input.contains(" ") || input.contains(";") || input.contains(":") || input.contains("-") || input.contains("[") || input.contains("]")) {
			return "The username is invalid!";
		}
		
		return null;
		
	}
	
//==========================================================================================================
	/**
	 * Checks i a given string is a valid password
	 * @param input - String - The String witch should be checked
	 * @return String - null if the input is valid; An error message if not valid
	 */
	public static String checkForPaswordValidation(String input) {
		
		if(input.length() < 4) {
			return "The password is to short!";
		}else if(input.length() > 12) {
			return "The password is to long!";
		}else if(input.contains(" ") || input.contains(";") || input.contains(":") || input.contains("-") || input.contains("[") || input.contains("]")) {
			return "The password is invalid!";
		}
		
		return null;
		
	}

//==========================================================================================================
	/**
	 * Replace every part of a string witch can not be send as byte data
	 * @param input - String - The String witch should be checked/replaced
	 * @return String - The checked/replaced input
	 */
	public static String checkStringForByteUse(String input) {
		
		input = input.replace(";", ":");
		input = input.replace("-", "_");
		input = input.replace("�", "oe");
		input = input.replace("�", "ae");
		input = input.replace("�", "ue");
		input = input.replace("�", "ss");
		input = input.replace("�", "'");
		input = input.replace("`", "'");
		input = input.replace("�", "?");
		input = input.replace("�", "?");
		
		return input;
		
	}
	
}
