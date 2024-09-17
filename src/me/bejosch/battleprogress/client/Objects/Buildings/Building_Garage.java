package me.bejosch.battleprogress.client.Objects.Buildings;

import me.bejosch.battleprogress.client.Enum.TroupType;
import me.bejosch.battleprogress.client.Game.Handler.Game_UnitsHandler;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Destroy;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Produce;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Building_Garage extends Building {

//==========================================================================================================
	/**
	 * The constructor of the BARRACKS class, this building is producing troups
	 * */
	public Building_Garage(int playerID_, Field connectedField_) {
		super(playerID_, connectedField_);
	}

	@Override
	public void load_TypeSettings() {
		
		try {
			img = Images.building_garage;
		}catch(Exception error) {}
		
		//STANDARD
		UnitStatsContainer container = Game_UnitsHandler.getUnitByName("Garage");
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
		UnitStatsContainer lightTank = Game_UnitsHandler.getUnitByName("Light Tank");
		this.actionTasks.add(new Task_Building_Produce(this, TroupType.LAND, lightTank.name, lightTank.kosten, null));
		
		this.actionTasks.add(new Task_Building_Destroy(this));
		
		super.load_ActionTasks();
	}

}
