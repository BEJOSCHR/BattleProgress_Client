package me.bejosch.battleprogress.client.Data.Game;

import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Objects.Research.UpgradeDataContainer;
import me.bejosch.battleprogress.client.Objects.Research.Upgrades.Upgrade;

public class ResearchData {

	//MAIN VALUE
	public static int researchPoints = 0;
	public static List<Upgrade> possibleUpgrades = new ArrayList<Upgrade>();
	public static List<Upgrade> researchedUpgrades = new ArrayList<Upgrade>();
	
	public static List<UpgradeDataContainer> upgradeDataContainer = new ArrayList<UpgradeDataContainer>();
	
}
