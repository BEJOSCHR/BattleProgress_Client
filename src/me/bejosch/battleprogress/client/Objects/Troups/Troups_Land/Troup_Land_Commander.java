package me.bejosch.battleprogress.client.Objects.Troups.Troups_Land;

import java.awt.Graphics;

import me.bejosch.battleprogress.client.Handler.UnitsHandler;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Remove;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Troup_Land_Commander extends Troup_Land {

	public Troup_Land_Commander(int playerID_, Field connectedField_) {
		super(playerID_, connectedField_);
	}
	
	@Override
	public void load_TypeSettings() {
		
		try {
			img = Images.troup_Land_Commander;
		}catch(Exception error) {}
		
		//STANDARD
		UnitStatsContainer container = UnitsHandler.getUnitByName("Commander");
		viewDistance = container.viewDistance;
		actionRange = container.actionDistance;
		maxHealth = container.leben;
		totalHealth = container.leben;
		name = container.name;
		
		textSize_nameField = 8;
		textSize_nameActionbar = 12;
		hoverDescription = container.getDescription();
		
		//EXTRA
		//###
		
		super.load_TypeSettings();
	}
	
	@Override
	public void load_ActionTasks() {
		
		this.actionTasks.add(new Task_Troup_Remove(this));
		
		super.load_ActionTasks();
	}
	
	@Override
	public void draw_Field(Graphics g, boolean createMapModus) {
		super.draw_Field(g, createMapModus);
	}

	
	
}
