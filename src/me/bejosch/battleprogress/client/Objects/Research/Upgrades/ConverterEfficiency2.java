package me.bejosch.battleprogress.client.Objects.Research.Upgrades;

import me.bejosch.battleprogress.client.Enum.ResearchCategory;
import me.bejosch.battleprogress.client.Enum.UpgradeType;

public class ConverterEfficiency2 extends Upgrade{

	public ConverterEfficiency2() {
		super(UpgradeType.ConverterEfficiency2, UpgradeType.ConverterEfficiency1, ResearchCategory.Economie, 2, 3, "ConverterEfficiency 2", "The consume of energy for each converter is decreased", "-%% energy per converter");
	}

	
	
}
