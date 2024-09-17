package me.bejosch.battleprogress.client.Objects.Tasks.BuildMenuTasks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.BuildMenuType;
import me.bejosch.battleprogress.client.Enum.UpgradeType;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Game.Handler.Game_ResearchHandler;
import me.bejosch.battleprogress.client.Game.Handler.Game_UnitsHandler;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;

public class BuildMenuTask {

	public boolean isActiveTask = false;
	public FieldCoordinates executeCoordinate = null;
	public FieldCoordinates targetCoordinate = null;
	
	public BuildMenuType buildMenuType = null;
	public String name = null;
	private String[] hoverMessage = null;
	public UpgradeType researchDependency = null;
	
	public String kürzel = null;
	public int cost = 0;
	
//==========================================================================================================
	/**
	 * This objact represents a space in the BuildMenu for each building
	 * @param buildMenuType_ - {@link BuildMenuType} - The type category in the BuildMenu, this building is in
	 * @param name_ - String - The name of the building which is represented
	 * @param hoverMessage_ - String[] - The message which is displayed at mouse hover 
	 * @param researchDependency_ - {@link UpgradeType} - If not null this building is locked by the research, so it requieres this upgrade to be researched
	 */
	public BuildMenuTask(BuildMenuType buildMenuType_, String name_, String[] hoverMessage_, UpgradeType researchDependency_) {
		
		this.executeCoordinate = Funktions.getHQfieldCoordinatesByPlayerID(ProfilData.thisClient.getID());
		this.buildMenuType = buildMenuType_;
		this.name = name_;
		this.hoverMessage = hoverMessage_;
		this.researchDependency = researchDependency_;
		
		loadBuildingSettings();
		
	}
	
//==========================================================================================================
	/**
	 * Loads the settings for the given BuildingName
	 */
	public void loadBuildingSettings() {
		
		UnitStatsContainer container = Game_UnitsHandler.getUnitByName(this.name);
		
		if(container == null) {
			ConsoleOutput.printMessageInConsole("Could not load UnitDataContainer for Building "+this.name+" by a BuildMenuTask", true);
			this.kürzel = "?";
			this.cost = 9999;
		}else {
			this.kürzel = container.kürzel;
			this.cost = container.kosten;
		}
		
	}
	
//==========================================================================================================
	/**
	 * Draws the target Field (for move and attack and so on...) for this troup
	 */
	public void draw_targetField(Graphics g) {
		
		if(GameData.dragAndDropInputActive_BuildingMenu == true) {
			//WIRD NOCH BEWEGT
			if(GameData.hoveredField != null) {
				Point hqCords = GameHandler.getHQCoordinates();
				Funktions.drawLineBetweenFields(g, GameData.gameMap_FieldList[hqCords.x][hqCords.y], GameData.hoveredField, Color.WHITE);
			}
		}else {
			//WIRD NICHT MEHR BEWEGT
			Funktions.drawBorderAroundFields(g, this.targetCoordinate.getConnectedField(), Color.WHITE, 8, 2);
			Funktions.drawLineBetweenFields(g, this.executeCoordinate.getConnectedField(), this.targetCoordinate.getConnectedField(), Color.CYAN);
		}
		
	}
	
//==========================================================================================================
	/**
	 * Checks for the research criteria. If no limit (dependency=null) it is never locked, if not null it is checked for the researched status {@link Game_ResearchHandler}
	 * @return false if no dependency is set or the upgrade has been researched, true if dependency is not researched
	 */
	public boolean isLocked() {
		
		if(this.researchDependency == null) {
			return false;
		}else {
			return !Game_ResearchHandler.hasResearched(this.researchDependency);
		}
		
	}
	
//==========================================================================================================
	/**
	 * Get the displayed Hovermessage
	 * @return String[] - The list which represents the hovermessage
	 */
	public String[] getHoverMessage() {
		
		if(this.isLocked()) {
			String[] newHovermessage = {"You need to unlock the "+this.name+"!"};
			return newHovermessage;
		}else {
			return hoverMessage;
		}
		
	}
	
	//STATIC -------------------
//==========================================================================================================
	/**
	 * Gets the list of buildings for each category in the buildMenu
	 * @param type - {@link BuildMenuType} - The type of the category
	 * @return List(BuildMenuTask) - The list with the buildings in it for the given category
	 */
	public static List<BuildMenuTask> loadAllBuildingTasks(BuildMenuType type) {
		
		List<BuildMenuTask> output = new ArrayList<BuildMenuTask>();
		
		switch(type) {
		case Fight:
			String[] hovermessage_01 = {"The "+"Turret"+" is used to attack nearby enemy","Small range with singletarget damage"};
			output.add(new BuildMenuTask(BuildMenuType.Fight, "Turret", hovermessage_01, null));
			String[] hovermessage_02 = {"The "+"Artillery"+" is used to attack far away enemy","Huge range with multitarget damage"};
			output.add(new BuildMenuTask(BuildMenuType.Fight, "Artillery", hovermessage_02, null));
			break;
		case Economic:
			String[] hovermessage_10 = {"The "+"Mine"+" is used to produce mass","Can only be placed on ressource fields"};
			output.add(new BuildMenuTask(BuildMenuType.Economic, "Mine", hovermessage_10, null));
			String[] hovermessage_11 = {"The "+"Reactor"+" is used to produce energy"};
			output.add(new BuildMenuTask(BuildMenuType.Economic, "Reactor", hovermessage_11, null));
			String[] hovermessage_12 = {"The "+"Laboratory"+" is used to produce research points"};
			output.add(new BuildMenuTask(BuildMenuType.Economic, "Laboratory", hovermessage_12, null));
			String[] hovermessage_13 = {"The "+"Converter"+" is used to exchange energy into material","It only convertes if enough energy is available!"};
			output.add(new BuildMenuTask(BuildMenuType.Economic, "Converter", hovermessage_13, UpgradeType.UnlockConverter));
			break;
		case Production:
			String[] hovermessage_20 = {"The "+"Barracks"+" is used to produce LAND-Soldier troups"};
			output.add(new BuildMenuTask(BuildMenuType.Production, "Barracks", hovermessage_20, null));
			String[] hovermessage_21 = {"The "+"Garage"+" is used to produce LAND-Vehicle troups"};
			output.add(new BuildMenuTask(BuildMenuType.Production, "Garage", hovermessage_21, null));
			String[] hovermessage_22 = {"The "+"Airport"+" is used to produce AIR troups"};
			output.add(new BuildMenuTask(BuildMenuType.Production, "Airport", hovermessage_22, null));
			break;
		case Special:
			String[] hovermessage_30 = {"The "+"Hospital"+" is used to heal troups"};
			output.add(new BuildMenuTask(BuildMenuType.Special, "Hospital", hovermessage_30, null));
			String[] hovermessage_31 = {"The "+"Workshop"+" is used to repair buildings"};
			output.add(new BuildMenuTask(BuildMenuType.Special, "Workshop", hovermessage_31, null));
			break;
		default:
			ConsoleOutput.printMessageInConsole("A BuildMenuTask found no menuCategory of the name '"+type+"'", true);
			break;
		}
		
		return output;
		
	}
	
}
