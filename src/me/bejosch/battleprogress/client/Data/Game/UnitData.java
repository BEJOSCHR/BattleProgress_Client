package me.bejosch.battleprogress.client.Data.Game;

import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;

public class UnitData {

	public static List<UnitStatsContainer> units = new ArrayList<UnitStatsContainer>();
	
	public static long lastDataContainerReceived = 0; //Used by reconnect to mark receive finish
	
}
