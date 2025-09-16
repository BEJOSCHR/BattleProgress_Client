package me.bejosch.battleprogress.client.Objects.Field;

import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Enum.ImportanceType;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Mine;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage_Located;

public class Field_Ressource extends Field {

	public int ressourceValue = GameData.ressourceFieldProducingRoundNumber; //The rounds left this ressourceField will be consumeable
	
//==========================================================================================================
	/**
	 * Creates a new Map object witch representing the game area later will be played on, specially this one produce ressources
	 * @param type - {@link FieldType} - The type of the field
	 * @param X - int - The X coordinate of the field
	 * @param Y - int - The Y coordinate of the field
	 */
	public Field_Ressource(int X, int Y) {
		super(FieldType.Ressource, X, Y);
	}

//==========================================================================================================
	/**
	 * Called if ressouces are consumed of the building on top of it
	 */
	public void consumeRessourceValue() {
		
		if(this.building instanceof Building_Mine) {
			if(ressourceValue > 0) {
				
				ressourceValue--;
				
			}else {
				
				List<String> message = new ArrayList<String>();
				message.add("Ressource field "+this.X+":"+this.Y+" has been dismantled!");
				message.add("The mining building was automatically removed");
				new InfoMessage_Located(message, ImportanceType.NORMAL, this.X, this.Y, false);
				this.building.totalHealth = 0;
				this.building.shouldBeDestroyedAtRoundEnd = true;
				//VERBRAUCHT
				//TODO PER PACKET LÖSEN!!! 
				this.changeType(FieldType.Consumed);
				
			}
		}else {
			//COULD NOT BE ... CONSUME OF RESSOURCE WITHOUT MINE
			ConsoleOutput.printMessageInConsole("Ressource consume on field "+this.X+":"+this.Y+" called without Mine building on it!", true);
		}
		
	}
	
//==========================================================================================================
	/**
	 * Returns the count of rounds this field will produce mass with a mine on it
	 */
	public int getProducingRounds() {
		return ressourceValue;
	}
	
	
}
