package me.bejosch.battleprogress.client.Objects.Buildings;

import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Game.Handler.Game_ResearchHandler;
import me.bejosch.battleprogress.client.Handler.UnitsHandler;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.Field_Ressource;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Destroy;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Building_Mine extends Building {

	private int produceMaterial;
	
//==========================================================================================================
	/**
	 * The constructor of the MINE class, this building is producing mass out of {@link Field_Ressource}
	 * */
	public Building_Mine(int playerID_, Field connectedField_) {
		super(playerID_, connectedField_);
		
		if(connectedField_.type != FieldType.Ressource) {
			ConsoleOutput.printMessageInConsole("A Mine was placed on a non Ressource Field!", true);
		}
		
	}

	@Override
	public void load_TypeSettings() {
		
		try {
			img = Images.building_mine;
		}catch(Exception error) {}
		
		//STANDARD
		UnitStatsContainer container = UnitsHandler.getUnitByName("Mine");
		viewDistance = container.viewDistance;
		maxHealth = container.leben;
		totalHealth = container.leben;
		name = container.name;
		
		textSize_nameField = 12;
		textSize_nameActionbar = 12;
		hoverDescription = container.getDescription();
		
		//EXTRA
		
		//PRIVATE EXTRA
		produceMaterial = container.materialProduktion;
		
		super.load_TypeSettings();
	}
	
	@Override
	public void load_ActionTasks() {
		
		this.actionTasks.add(new Task_Building_Destroy(this));
		
		super.load_ActionTasks();
	}
	
//==========================================================================================================
	/**
	 * Called at the end of a round to get the produced mass
	 * @return int - The amount of mass which was produced by this mine
	 */
	public int produceMass() {
		try{
			Field_Ressource field_Ressource = (Field_Ressource) connectedField;
			field_Ressource.consumeRessourceValue();
			return produceMaterial + Game_ResearchHandler.extra_Mine_Production();  
		}catch(ClassCastException error) {
			ConsoleOutput.printMessageInConsole("A Mine tried to produce mass from a non Ressource Field", true);
			return 0;
		}
	}

}
