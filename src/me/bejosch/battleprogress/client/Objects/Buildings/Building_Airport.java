package me.bejosch.battleprogress.client.Objects.Buildings;

import me.bejosch.battleprogress.client.Enum.TroupType;
import me.bejosch.battleprogress.client.Game.Handler.Game_UnitsHandler;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Destroy;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Produce;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Building_Airport extends Building {

//==========================================================================================================
	/**
	 * The constructor of the AIRPORT class, this building is producing troups
	 * */
	public Building_Airport(int playerID_, Field connectedField_) {
		super(playerID_, connectedField_);
	}

	@Override
	public void load_TypeSettings() {
		
		try {
			img = Images.building_airport;
		}catch(Exception error) {}
		
		//STANDARD
		UnitStatsContainer container = Game_UnitsHandler.getUnitByName("Airport");
		viewDistance = container.viewDistance;
		maxHealth = container.leben;
		totalHealth = container.leben;
		name = container.name;
		
		textSize_nameField = 12;
		textSize_nameActionbar = 12;
		hoverDescription = container.getDescription();
		
		//EXTRA
		actionRange = container.actionDistance;
		
		super.load_TypeSettings();
	}
	
	@Override
	public void load_ActionTasks() {
		
		//TROUPS - Only light ones
		UnitStatsContainer lightHeli = Game_UnitsHandler.getUnitByName("Light Heli");
		this.actionTasks.add(new Task_Building_Produce(this, TroupType.AIR, lightHeli.name, lightHeli.kosten, null));
		
		this.actionTasks.add(new Task_Building_Destroy(this));
		
		super.load_ActionTasks();
	}

}
