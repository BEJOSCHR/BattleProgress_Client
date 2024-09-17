package me.bejosch.battleprogress.client.Objects.Troups.Troups_Land.Vehicle;

import java.awt.Graphics;

import me.bejosch.battleprogress.client.Game.Handler.Game_UnitsHandler;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Attack;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Move;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Remove;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup_Upgrade;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Land.Troup_Land;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Troup_Land_MediumTank extends Troup_Land {

	public Troup_Land_MediumTank(int playerID_, Field connectedField_) {
		super(playerID_, connectedField_);
	}
	
	@Override
	public void load_TypeSettings() {
		
		try {
			img = Images.troup_Land_MediumTank;
		}catch(Exception error) {}
		
		//STANDARD
		UnitStatsContainer container = Game_UnitsHandler.getUnitByName("Medium Tank");
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
		String[] hoverDescription_ = {"The main purpose is dealing damage to enemies", "It should be used as a frontline and can survive some hits", "To increase the attackdistance a spotting ally is needed"};
		hoverDescription = hoverDescription_;
		
		//EXTRA
		//###
		
		super.load_TypeSettings();
	}
	
	@Override
	public void load_ActionTasks() {
		
		this.actionTasks.add(new Task_Troup_Attack(this));
		this.actionTasks.add(new Task_Troup_Move(this));
		UnitStatsContainer heavyTank = Game_UnitsHandler.getUnitByName("Heavy Tank");
		this.actionTasks.add(new Task_Troup_Upgrade(this, heavyTank.name, heavyTank.kosten, null));
		this.actionTasks.add(new Task_Troup_Remove(this));
		
		super.load_ActionTasks();
	}
	
	@Override
	public void draw_Field(Graphics g, boolean createMapModus) {
		super.draw_Field(g, createMapModus);
	}

	
	
}
