package me.bejosch.battleprogress.client.Handler;

import me.bejosch.battleprogress.client.Data.Game.UnitData;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.ServerConnection.ServerConnection;

public class UnitsHandler {

	public static void requestUnitsUpdate() {
		
		UnitData.units.clear();
		ServerConnection.sendData(110, ServerConnection.getNewPacketId(), "Request Units update");
		
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
