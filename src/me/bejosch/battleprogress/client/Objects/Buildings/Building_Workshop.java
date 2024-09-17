package me.bejosch.battleprogress.client.Objects.Buildings;

import me.bejosch.battleprogress.client.Game.Handler.Game_UnitsHandler;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Destroy;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Repair;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Building_Workshop extends Building {

//==========================================================================================================
	/**
	 * The constructor of the WORKSHOP class, this building is repair nearby buildings
	 * */
	public Building_Workshop(int playerID_, Field connectedField_) {
		super(playerID_, connectedField_);
	}

	@Override
	public void load_TypeSettings() {
		
		try {
			img = Images.building_workshop;
		}catch(Exception error) {}
		
		//STANDARD
		UnitStatsContainer container = Game_UnitsHandler.getUnitByName("Workshop");
		viewDistance = container.viewDistance;
		maxHealth = container.leben;
		totalHealth = container.leben;
		name = container.name;
		
		textSize_nameField = 12;
		textSize_nameActionbar = 12;
		hoverDescription = container.getDescription();
		
		//EXTRA
		repair = container.repair;
		actionRange = container.actionDistance;
		energyCostPerAction = container.energieVerbrauch;
		
		super.load_TypeSettings();
	}
	
	@Override
	public void load_ActionTasks() {
		
		this.actionTasks.add(new Task_Building_Repair(this));
		
		this.actionTasks.add(new Task_Building_Destroy(this));
		
		super.load_ActionTasks();
	}

}
