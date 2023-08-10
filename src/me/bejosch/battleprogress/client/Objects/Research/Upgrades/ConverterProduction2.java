package me.bejosch.battleprogress.client.Objects.Research.Upgrades;

import me.bejosch.battleprogress.client.Enum.ResearchCategory;
import me.bejosch.battleprogress.client.Enum.UpgradeType;

public class ConverterProduction2 extends Upgrade{

	public ConverterProduction2() {
		super(UpgradeType.ConverterProduction2, UpgradeType.ConverterProduction1, ResearchCategory.Economie, 2, 2, "ConverterProduction 2", "The production of material for each converter is increased", "+%% material per converter");
	}

	
	
}
