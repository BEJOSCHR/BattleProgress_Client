package me.bejosch.battleprogress.client.Objects;

import me.bejosch.battleprogress.client.Enum.MovingCircleDisplayTypes;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;

public class ResourceProductionContainer {

	private FieldCoordinates cords;
	private int amount;
	private MovingCircleDisplayTypes type;
	
	public ResourceProductionContainer(FieldCoordinates cords, int amount, MovingCircleDisplayTypes type) {
		
		this.cords = cords;
		this.amount = amount;
		this.type = type;
		
	}
	
	public FieldCoordinates getCords() {
		return cords;
	}
	public int getAmount() {
		return amount;
	}
	public MovingCircleDisplayTypes getType() {
		return type;
	}
	
}
