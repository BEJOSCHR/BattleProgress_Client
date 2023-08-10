package me.bejosch.battleprogress.client.Objects.Buildings;

import me.bejosch.battleprogress.client.Game.Handler.Game_ResearchHandler;
import me.bejosch.battleprogress.client.Handler.UnitsHandler;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.Field_Ressource;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Destroy;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Building_Reactor extends Building {

	private int energyProduktion;
	
//==========================================================================================================
	/**
	 * The constructor of the MINE class, this building is producing mass out of {@link Field_Ressource}
	 * */
	public Building_Reactor(int playerID_, Field connectedField_) {
		super(playerID_, connectedField_);
	}

	@Override
	public void load_TypeSettings() {
		
		try {
			img = Images.building_reactor;
		}catch(Exception error) {}
		
		//STANDARD
		UnitStatsContainer container = UnitsHandler.getUnitByName("Reactor");
		viewDistance = container.viewDistance;
		maxHealth = container.leben;
		totalHealth = container.leben;
		name = container.name;
		
		textSize_nameField = 12;
		textSize_nameActionbar = 12;
		String[] hoverDescription_ = {"This is an economical building","It is the best way to PRODUCE ENERGY"};
		hoverDescription = hoverDescription_;
		
		//EXTRA
		
		//PRIVATE EXTRA
		energyProduktion = container.energieProduktion;
		
		super.load_TypeSettings();
		
	}
	
	@Override
	public void load_ActionTasks() {
		
		this.actionTasks.add(new Task_Building_Destroy(this));
	
		super.load_ActionTasks();
		
	}
	
//==========================================================================================================
	/**
	 * Called at the end of a round to get the produced energie
	 * @return int - The amount of energy which was produced by this reactor
	 */
	public int produceEnergy() {
		return energyProduktion+Game_ResearchHandler.extra_Reactor_Production();
	}
	
}
