package me.bejosch.battleprogress.client.Game.Handler;

import me.bejosch.battleprogress.client.Data.Game.UnitData;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Airport;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Artillery;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Barracks;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Converter;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Garage;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Headquarter;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Hospital;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Laboratory;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Mine;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Reactor;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Turret;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Workshop;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Air.Troup_Air_HeavyHelicopter;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Air.Troup_Air_LightHelicopter;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Air.Troup_Air_MediumHelicopter;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Land.Troup_Land_Commander;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Land.Soldier.Troup_Land_HeavySoldier;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Land.Soldier.Troup_Land_LightSoldier;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Land.Soldier.Troup_Land_MediumSoldier;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Land.Vehicle.Troup_Land_HeavyTank;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Land.Vehicle.Troup_Land_LightTank;
import me.bejosch.battleprogress.client.Objects.Troups.Troups_Land.Vehicle.Troup_Land_MediumTank;
import me.bejosch.battleprogress.client.ServerConnection.MinaClient;

public class Game_UnitsHandler {

	public static void requestUnitsUpdate() {
		
		UnitData.units.clear();
		MinaClient.sendData(110, "Request Units update");
		
	}
	
	public static UnitStatsContainer getUnitByName(String name) {
		
		for(UnitStatsContainer container : UnitData.units) {
			if(container.name.equals(name)) {
				return container;
			}
		}
		
		return null;
	}
	
	public static UnitStatsContainer getUnitByKürzel(String kürzel) {
		
		for(UnitStatsContainer container : UnitData.units) {
			if(container.kürzel.equals(kürzel)) {
				return container;
			}
		}
		
		return null;
	}
	
	
	
	public static Building createNewBuilding(int playerID, FieldCoordinates targetCoordinate, String name) {
		
		Building newBuilding = null;
		
		switch (name) {
		case "Headquarter":
			newBuilding = new Building_Headquarter(playerID, targetCoordinate.getConnectedField());
			break;
		case "Mine":
			newBuilding = new Building_Mine(playerID, targetCoordinate.getConnectedField());
			break;
		case "Reactor":
			newBuilding = new Building_Reactor(playerID, targetCoordinate.getConnectedField());
			break;
		case "Turret":
			newBuilding = new Building_Turret(playerID, targetCoordinate.getConnectedField());
			break;
		case "Artillery":
			newBuilding = new Building_Artillery(playerID, targetCoordinate.getConnectedField());
			break;
		case "Hospital":
			newBuilding = new Building_Hospital(playerID, targetCoordinate.getConnectedField());
			break;
		case "Workshop":
			newBuilding = new Building_Workshop(playerID, targetCoordinate.getConnectedField());
			break;
		case "Barracks":
			newBuilding = new Building_Barracks(playerID, targetCoordinate.getConnectedField());
			break;
		case "Garage":
			newBuilding = new Building_Garage(playerID, targetCoordinate.getConnectedField());
			break;
		case "Airport":
			newBuilding = new Building_Airport(playerID, targetCoordinate.getConnectedField());
			break;
		case "Laboratory":
			newBuilding = new Building_Laboratory(playerID, targetCoordinate.getConnectedField());
			break;
		case "Converter":
			newBuilding = new Building_Converter(playerID, targetCoordinate.getConnectedField());
			break;
		default:
			ConsoleOutput.printMessageInConsole("A building create found no building for the given buildingName [BuildingName: "+name+"]", true);
			break;
		}
		
		return newBuilding;
		
	}
	
	public static Troup createNewTroup(int playerID, FieldCoordinates targetCoordinate, String name) {
		
		Troup newTroup = null;
		
		switch (name) {
		//================= LAND
		case "Commander":
			newTroup = new Troup_Land_Commander(playerID, targetCoordinate.getConnectedField());
			break;
		//================= VEHICLE
		case "Light Tank":
			newTroup = new Troup_Land_LightTank(playerID, targetCoordinate.getConnectedField());
			break;
		//================= SOLDIER
		case "Light Soldier":
			newTroup = new Troup_Land_LightSoldier(playerID, targetCoordinate.getConnectedField());
			break;
		//================= AIR
		case "Light Heli":
			newTroup = new Troup_Air_LightHelicopter(playerID, targetCoordinate.getConnectedField());
			break;
		default:
			ConsoleOutput.printMessageInConsole("A troup create found no troup for the given troupName [TroupName: "+name+"]", true);
			break;
		}
		
		return newTroup;
		
	}
	
	public static Troup createNewUpgradeTroup(int playerID, FieldCoordinates targetCoordinate, String name) {
		
		Troup troup = null;
		
		switch(name) {
		//================= VEHICLE
		case "Medium Tank":
			troup = new Troup_Land_MediumTank(playerID, targetCoordinate.getConnectedField());
			break;
		case "Heavy Tank":
			troup = new Troup_Land_HeavyTank(playerID, targetCoordinate.getConnectedField());
			break;
		//================= SOLDIER
		case "Medium Soldier":
			troup = new Troup_Land_MediumSoldier(playerID, targetCoordinate.getConnectedField());
			break;
		case "Heavy Soldier":
			troup = new Troup_Land_HeavySoldier(playerID, targetCoordinate.getConnectedField());
			break;
		//================= AIR
		case "Medium Heli":
			troup = new Troup_Air_MediumHelicopter(playerID, targetCoordinate.getConnectedField());
			break;
		case "Heavy Heli":
			troup = new Troup_Air_HeavyHelicopter(playerID, targetCoordinate.getConnectedField());
			break;
		default:
			ConsoleOutput.printMessageInConsole("An upgrade troup create found no upgrade troup for the given troupName [TroupName: "+name+"]", true);
			break;
		}
		
		return troup;
		
	}
	
}
