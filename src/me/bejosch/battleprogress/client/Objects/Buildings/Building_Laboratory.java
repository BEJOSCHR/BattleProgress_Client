package me.bejosch.battleprogress.client.Objects.Buildings;

import me.bejosch.battleprogress.client.Handler.UnitsHandler;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Destroy;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Building_Laboratory extends Building {
	
//==========================================================================================================
	/**
	 * The constructor of the LABORATORY class, this building is producing research points
	 * */
	public Building_Laboratory(int playerID_, Field connectedField_) {
		super(playerID_, connectedField_);
	}

	@Override
	public void load_TypeSettings() {
		
		try {
			img = Images.building_laboratory;
		}catch(Exception error) {}
		
		//STANDARD
		UnitStatsContainer container = UnitsHandler.getUnitByName("Laboratory");
		viewDistance = container.viewDistance;
		maxHealth = container.leben;
		totalHealth = container.leben;
		name = container.name;
		
		textSize_nameField = 12;
		textSize_nameActionbar = 12;
		String[] hoverDescription_ = {"This is a research building","It produces ResearchPoints per round"};
		hoverDescription = hoverDescription_;
		
		//EXTRA
		research = container.research;
		
		super.load_TypeSettings();
	}
	
	@Override
	public void load_ActionTasks() {
		
		this.actionTasks.add(new Task_Building_Destroy(this));
		
		super.load_ActionTasks();
	}

//==========================================================================================================
	/**
	 * Called at the end of a round to get the researched points
	 * @return int - The amount of research points which was produced by this laboratory
	 */
	public int produceResearch() {
		return this.research;
	}
	
}
