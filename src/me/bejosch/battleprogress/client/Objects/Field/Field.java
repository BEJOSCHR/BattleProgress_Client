package me.bejosch.battleprogress.client.Objects.Field;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Enum.SpielStatus;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Headquarter;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;

public class Field {

	public FieldType type = FieldType.Gras;
	public Image img = null;
	public int X = 0;
	public int Y = 0;
	
	public boolean visible = false; //True = in view range of troup or building ; false = in the "fog"
	
	public Building building = null;
	public Troup troup = null;
	
//==========================================================================================================
	/**
	 * Creates a new Map object witch representing the game area later will be played on
	 * @param type - {@link FieldType} - The type of the field
	 * @param X - int - The X coordinate of the field
	 * @param Y - int - The Y coordinate of the field
	 */
	public Field(FieldType type, int X, int Y) {
		
		this.type = type;
		this.X = X;
		this.Y = Y;
		
		this.img = Funktions.getFieldImageFromFieldType(type);
		
	}

//==========================================================================================================
	/**
	 * Draws this field
	 * @param g - {@link Graphics} - The graphic this field should be draw
	 */
	public void draw(Graphics g) {
		
		if(StandardData.spielStatus == SpielStatus.CreateMap) {
			
			int realX = Funktions.getPixlesByCoordinate(this.X, true, true);
			int realY = Funktions.getPixlesByCoordinate(this.Y, false, true);

			g.drawImage(img, realX, realY, null);
			
			if(StandardData.showGrid == true) {
				g.setColor(Color.WHITE);
				g.drawLine(realX+StandardData.fieldSize, realY+StandardData.fieldSize, realX+StandardData.fieldSize, realY);
				g.drawLine(realX, realY, realX+StandardData.fieldSize, realY);
				g.drawLine(realX+StandardData.fieldSize, realY+StandardData.fieldSize, realX, realY+StandardData.fieldSize);
				g.drawLine(realX, realY, realX, realY+StandardData.fieldSize);
			}
			
		}else if(StandardData.spielStatus == SpielStatus.Game) {
			
			int realX = Funktions.getPixlesByCoordinate(this.X, true, false);
			int realY = Funktions.getPixlesByCoordinate(this.Y, false, false);
			
			g.drawImage(img, realX, realY, null);
			
			if(visible == false) {
				g.setColor(new Color(100, 100, 100, 165));
				g.fillRect(realX, realY, StandardData.fieldSize+1, StandardData.fieldSize+1);
			}
			
			if(StandardData.showGrid == true) {
				g.setColor(Color.WHITE);
				g.drawLine(realX+StandardData.fieldSize, realY+StandardData.fieldSize, realX+StandardData.fieldSize, realY);
				g.drawLine(realX, realY, realX+StandardData.fieldSize, realY);
				g.drawLine(realX+StandardData.fieldSize, realY+StandardData.fieldSize, realX, realY+StandardData.fieldSize);
				g.drawLine(realX, realY, realX, realY+StandardData.fieldSize);
			}
			
		}
		
	}
	
//==========================================================================================================
	/**
	 * Draws this field as the choosen one
	 * @param g - {@link Graphics} - The graphic this field should be draw
	 * @param color - {@link Color} - The color in this field will be highlighted
	 */
	public void drawHighlight(Graphics g, Color color) {
		
		if(StandardData.spielStatus == SpielStatus.CreateMap) {
			
			int realX = Funktions.getPixlesByCoordinate(this.X, true, true);
			int realY = Funktions.getPixlesByCoordinate(this.Y, false, true);
			
			g.setColor(color);
			g.drawLine(realX, realY, realX+StandardData.fieldSize, realY);
			g.drawLine(realX, realY, realX, realY+StandardData.fieldSize);
			g.drawLine(realX+StandardData.fieldSize, realY, realX+StandardData.fieldSize, realY+StandardData.fieldSize);
			g.drawLine(realX, realY+StandardData.fieldSize, realX+StandardData.fieldSize, realY+StandardData.fieldSize);
			
		}else {
			
			int realX = Funktions.getPixlesByCoordinate(this.X, true, false);
			int realY = Funktions.getPixlesByCoordinate(this.Y, false, false);
			
			g.setColor(color);
			g.drawLine(realX, realY, realX+StandardData.fieldSize, realY);
			g.drawLine(realX, realY, realX, realY+StandardData.fieldSize);
			g.drawLine(realX+StandardData.fieldSize, realY, realX+StandardData.fieldSize, realY+StandardData.fieldSize);
			g.drawLine(realX, realY+StandardData.fieldSize, realX+StandardData.fieldSize, realY+StandardData.fieldSize);
			
		}
		
	}
	
//==========================================================================================================
	/**
	 * Destroys building
	 */
	public void destroyBuilding() {
		
		if(this.building == null) { return; /* Already destroyed */ }
		
		if(this.building instanceof Building_Headquarter) {
			//HQ could not be destroyed
			ConsoleOutput.printMessageInConsole("The Field "+X+":"+Y+" tried to destory a Headquarter!", true);
			return;
		}
		this.building.destroy();
		
	}
	
//==========================================================================================================
	/**
	 * Changes the type of the field and with it the drawed image
	 * @param newType - {@link FieldType} - The type this field should be changed to
	 */
	public void changeType(FieldType newType) {
		
		this.type = newType;
		this.updateImage();
		
	}
//==========================================================================================================
	/**
	 * Reloads the image from the type
	 */
	public void updateImage() {
		
		this.img = Funktions.getFieldImageFromFieldType(this.type);
		
	}
	
}
