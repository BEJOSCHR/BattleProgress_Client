package me.bejosch.battleprogress.client.Objects.Buildings;

import me.bejosch.battleprogress.client.Data.Game.EconomicData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Game.Handler.Game_ResearchHandler;
import me.bejosch.battleprogress.client.Handler.UnitsHandler;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Destroy;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Building_Converter extends Building {

	private int produceMaterial;
	
//==========================================================================================================
	/**
	 * The constructor of the AIRPORT class, this building is producing troups
	 * */
	public Building_Converter(int playerID_, Field connectedField_) {
		super(playerID_, connectedField_);
	}

	@Override
	public void load_TypeSettings() {
		
		try {
			img = Images.building_converter;
		}catch(Exception error) {}
		
		//STANDARD
		UnitStatsContainer container = UnitsHandler.getUnitByName("Converter");
		viewDistance = container.viewDistance;
		maxHealth = container.leben;
		totalHealth = container.leben;
		name = container.name;
		
		textSize_nameField = 12;
		textSize_nameActionbar = 12;
		String[] hoverDescription_ = {"This is an economical building","It exchanges energy into material,", "but only if enough energy is available at the end of the round!"};
		hoverDescription = hoverDescription_;
		
		//EXTRA
		energyCostPerAction = container.energieVerbrauch;
		
		//PRIVATE EXTRA
		produceMaterial = container.materialProduktion;
		
		super.load_TypeSettings();
	}
	
	@Override
	public void load_ActionTasks() {
		
		this.actionTasks.add(new Task_Building_Destroy(this));
		
		super.load_ActionTasks();
	}

	public int getTotalNeededEnergy() {
		return this.energyCostPerAction-Game_ResearchHandler.extra_Converter_Efficiency();
	}
	public int getTotalProducedMaterial() {
		return this.produceMaterial+Game_ResearchHandler.extra_Converter_Production();
	}
	
//==========================================================================================================
	/**
	 * Called at the end of a round to produce material from energy if enough energy is left
	 * @return int - The amount of material which was converted, 0 if not enough energy is left
	 */
	public int convertMaterial() {
		if(EconomicData.energyAmount >= this.getTotalNeededEnergy()) {
			//ENOUGH ENERGY
			EconomicData.energyAmount -= this.getTotalNeededEnergy();
			RoundData.currentStatsContainer.addEnergyEntry(this, -this.getTotalNeededEnergy());
			return this.getTotalProducedMaterial();
		}else {
			return 0;
		}
	}
	
}
