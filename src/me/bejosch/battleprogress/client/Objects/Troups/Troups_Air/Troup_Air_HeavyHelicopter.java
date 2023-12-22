package me.bejosch.battleprogress.client.Objects.Troups.Troups_Air;

import java.awt.Graphics;

import me.bejosch.battleprogress.client.Handler.UnitsHandler;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Attack;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Move;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Remove;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Troup_Air_HeavyHelicopter extends Troup_Air {

	public Troup_Air_HeavyHelicopter(int playerID_, Field connectedField_) {
		super(playerID_, connectedField_);
	}
	
	@Override
	public void load_TypeSettings() {
		
		try {
			img = Images.troup_Air_HeavyHelicopter;
		}catch(Exception error) {}
		
		//STANDARD
		UnitStatsContainer container = UnitsHandler.getUnitByName("Heavy Heli");
		viewDistance = container.viewDistance;
		moveDistance = container.moveDistance;
		actionRange = container.actionDistance;
		energyCostPerAction = container.energieVerbrauch;
		maxHealth = container.leben;
		totalHealth = container.leben;
		damage = container.schaden;
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
		
		this.actionTasks.add(new Task_Troup_Attack(this));
		this.actionTasks.add(new Task_Troup_Move(this));
		this.actionTasks.add(new Task_Troup_Remove(this));
		
		super.load_ActionTasks();
	}
	
	@Override
	public void draw_Field(Graphics g, boolean createMapModus) {
		super.draw_Field(g, createMapModus);
	}
	
}
