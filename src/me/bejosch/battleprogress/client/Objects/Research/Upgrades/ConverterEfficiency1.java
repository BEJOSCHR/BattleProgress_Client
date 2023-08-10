package me.bejosch.battleprogress.client.Objects.Research.Upgrades;

import me.bejosch.battleprogress.client.Enum.ResearchCategory;
import me.bejosch.battleprogress.client.Enum.UpgradeType;

public class ConverterEfficiency1 extends Upgrade{

	public ConverterEfficiency1() {
		super(UpgradeType.ConverterEfficiency1, UpgradeType.Converter, ResearchCategory.Economie, 1, 3, "ConverterEfficiency 1", "The consume of energy for each converter is decreased", "-%% energy per converter");
	}

	
	
}
