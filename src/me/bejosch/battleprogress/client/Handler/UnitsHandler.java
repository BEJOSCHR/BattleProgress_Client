package me.bejosch.battleprogress.client.Handler;

import me.bejosch.battleprogress.client.Data.Game.UnitData;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.ServerConnection.MinaClient;

public class UnitsHandler {

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
	
}
