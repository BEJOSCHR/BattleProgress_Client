package me.bejosch.battleprogress.client.Objects.Buildings;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.SpectateData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.SpielStatus;
import me.bejosch.battleprogress.client.Enum.TroupType;
import me.bejosch.battleprogress.client.Game.Handler.Game_UnitsHandler;
import me.bejosch.battleprogress.client.Handler.ClientPlayerHandler;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Field.Field_Ressource;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building_Produce;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Building_Headquarter extends Building {

	private int energieProduktion, materialProduktion, researchProduktion;
	
//==========================================================================================================
	/**
	 * The constructor of the MINE class, this building is producing mass out of {@link Field_Ressource}
	 * */
	public Building_Headquarter(int playerID_, Field connectedField_) {
		super(playerID_, connectedField_);
		
		if(StandardData.spielStatus == SpielStatus.Game) {
			calculate_ViewRange();
			calculate_ShotRange();
			calculate_ProduceRange();
		}
		
	}

	@Override
	public void load_TypeSettings() {
		
		try {
			img = Images.building_headquarter;
		}catch(Exception error) {}

		//STANDARD
		UnitStatsContainer container = Game_UnitsHandler.getUnitByName("Headquarter");
		if(container == null) { return; } //IN THE MAP EDITOR THE CONTAINERS ARE NOT LOADED SO DEFAULT SETTINGS ARE USED (HQ)
		
		viewDistance = container.viewDistance;
		maxHealth = container.leben;
		totalHealth = container.leben;
		name = container.name;
		
		textSize_nameField = 12;
		textSize_nameActionbar = 12;
		hoverDescription = container.getDescription();
		
		//EXTRA
		energyCostPerAction = container.energieVerbrauch;
		actionRange = container.actionDistance;
		
		//PRIVAT EXTRA
		energieProduktion = container.energieProduktion;
		materialProduktion = container.materialProduktion;
		researchProduktion = container.research;
		
		super.load_TypeSettings();
	}

	@Override
	public void load_ActionTasks() {
		
		UnitStatsContainer commander = Game_UnitsHandler.getUnitByName("Commander");
		if(commander == null) { return; } //IN THE MAP EDITOR THE CONTAINERS ARE NOT LOADED SO DEFAULT SETTINGS ARE USED (HQ)
		this.actionTasks.add(new Task_Building_Produce(this, TroupType.LAND, commander.name, commander.kosten, null));
		//NOT DESTROY ABLE
		
		super.load_ActionTasks();
	}
	
	@Override
	public void draw_Field(Graphics g, boolean createMapModus) {
		
		super.draw_Field(g, createMapModus);
		
		//DRAW PLAYER NAME
		if(StandardData.spielStatus == SpielStatus.Spectate) {
			int realX_spec = (this.connectedField.X * StandardData.fieldSize)+SpectateData.scroll_LR_count;
			int realY_spec = (this.connectedField.Y * StandardData.fieldSize)+SpectateData.scroll_UD_count;
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 16));
			String name = ClientPlayerHandler.getNewClientPlayer(this.playerID).getName();
			int nameWidth = g.getFontMetrics().stringWidth(name);
			g.drawString(name, realX_spec+StandardData.fieldSize/2-nameWidth/2, realY_spec+StandardData.fieldSize+20);
		}else if(StandardData.spielStatus == SpielStatus.Spectate) {
			//TODO
		}
		
	}
	
	//SPECIAL FOR HQ ON START
	public void calculate_ViewRange_HQ() {
		
//		ConsoleOutput.printMessageInConsole("Calculating special HQ viewrange...", true);
		
		this.viewAbleFields.clear();
		for(int x = this.connectedField.X-viewDistance ; x <= this.connectedField.X+viewDistance ; x++) {
			for(int y = this.connectedField.Y-viewDistance ; y <= this.connectedField.Y+viewDistance ; y++) {
				try{
					Field targetField = GameData.gameMap_FieldList[x][y];
					this.viewAbleFields.add(new FieldCoordinates(targetField));
				}catch(NullPointerException | IndexOutOfBoundsException error) { error.printStackTrace(); }
			}
		}
		
	}	
	
//==========================================================================================================
	/**
	 * Called at the end of a round to get the produced mass - HQ
	 * @return int - The amount of mass witch was produced by the Headquarter
	 */
	public int produceMass_HQ() {
		return this.materialProduktion;
	}
	
//==========================================================================================================
	/**
	 * Called at the end of a round to get the produced energie - HQ
	 * @return int - The amount of energy witch was produced by the Headquarter
	 */
	public int produceEnergy_HQ() {
		return this.energieProduktion;
	}
	
//==========================================================================================================
	/**
	 * Called at the end of a round to get the produced research points - HQ
	 * @return int - The amount of research points witch was produced by the Headquarter
	 */
	public int produceResearch_HQ() {
		return this.researchProduktion;
	}
		
}
