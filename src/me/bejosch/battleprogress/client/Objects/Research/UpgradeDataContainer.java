package me.bejosch.battleprogress.client.Objects.Research;

import me.bejosch.battleprogress.client.Enum.UpgradeType;

public class UpgradeDataContainer {

	public UpgradeType upgradeType = null;
	
	public int researchCost = 9999;
	public int effectValue = 0; //THE AMOUNT BY THAT IT UPGRADES
	
	public UpgradeDataContainer(UpgradeType upgradeType, int cost, int effectValue) {
		
		this.upgradeType = upgradeType;
		
		this.researchCost = cost;
		this.effectValue = effectValue;
		
	}
	
}
