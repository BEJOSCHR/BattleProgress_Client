package me.bejosch.battleprogress.client.Game.Handler;

import me.bejosch.battleprogress.client.Data.Game.ResearchData;
import me.bejosch.battleprogress.client.Enum.ResearchCategory;
import me.bejosch.battleprogress.client.Enum.UpgradeType;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.Research.UpgradeDataContainer;
import me.bejosch.battleprogress.client.Objects.Research.Upgrades.Converter;
import me.bejosch.battleprogress.client.Objects.Research.Upgrades.ConverterEfficiency1;
import me.bejosch.battleprogress.client.Objects.Research.Upgrades.ConverterEfficiency2;
import me.bejosch.battleprogress.client.Objects.Research.Upgrades.ConverterProduction1;
import me.bejosch.battleprogress.client.Objects.Research.Upgrades.ConverterProduction2;
import me.bejosch.battleprogress.client.Objects.Research.Upgrades.MineProduction1;
import me.bejosch.battleprogress.client.Objects.Research.Upgrades.MineProduction2;
import me.bejosch.battleprogress.client.Objects.Research.Upgrades.MineProduction3;
import me.bejosch.battleprogress.client.Objects.Research.Upgrades.ReactorProduction1;
import me.bejosch.battleprogress.client.Objects.Research.Upgrades.ReactorProduction2;
import me.bejosch.battleprogress.client.Objects.Research.Upgrades.ReactorProduction3;
import me.bejosch.battleprogress.client.Objects.Research.Upgrades.Upgrade;
import me.bejosch.battleprogress.client.ServerConnection.MinaClient;

public class Game_ResearchHandler {
	
	public static void initUpgrades() {
		
		ResearchData.possibleUpgrades.add(new MineProduction1());
		ResearchData.possibleUpgrades.add(new MineProduction2());
		ResearchData.possibleUpgrades.add(new MineProduction3());
		
		ResearchData.possibleUpgrades.add(new ReactorProduction1());
		ResearchData.possibleUpgrades.add(new ReactorProduction2());
		ResearchData.possibleUpgrades.add(new ReactorProduction3());
		
		ResearchData.possibleUpgrades.add(new Converter());
		ResearchData.possibleUpgrades.add(new ConverterEfficiency1());
		ResearchData.possibleUpgrades.add(new ConverterEfficiency2());
		ResearchData.possibleUpgrades.add(new ConverterProduction1());
		ResearchData.possibleUpgrades.add(new ConverterProduction2());
		
	}
	
	//DATA CONTAINER
	
	public static void loadUpgradeDataContainer() {
		
		ResearchData.upgradeDataContainer.clear();
		MinaClient.sendData(111, "Request UpgradeDataContainer update");
		
	}
	
	public static UpgradeDataContainer getDataContainer(UpgradeType type) {
		
		for(UpgradeDataContainer container : ResearchData.upgradeDataContainer) {
			if(container.upgradeType == type) {
				return container;
			}
		}
		
		ConsoleOutput.printMessageInConsole("Could not find UpgradeDataContainer for type "+type+"! Is it loaded yet? - Continue with default Container!", true);
		return new UpgradeDataContainer(type, 9999, 0);
	}
	
	//GETTER / SETTER
	
	public static Upgrade getUpgrade(UpgradeType type) {
		
		for(Upgrade upgrade : ResearchData.possibleUpgrades) {
			if(upgrade.getType() == type) {
				return upgrade;
			}
		}
		for(Upgrade upgrade : ResearchData.researchedUpgrades) {
			if(upgrade.getType() == type) {
				return upgrade;
			}
		}
		
		return null;
	}
	
	public static Upgrade getUpgrade(int x, int y, ResearchCategory category) {
		
		for(Upgrade upgrade : ResearchData.possibleUpgrades) {
			if(upgrade.getCategory() == category && upgrade.getX() == x && upgrade.getY() == y) {
				return upgrade;
			}
		}
		for(Upgrade upgrade : ResearchData.researchedUpgrades) {
			if(upgrade.getCategory() == category && upgrade.getX() == x && upgrade.getY() == y) {
				return upgrade;
			}
		}
		
		return null;
	}
	
	public static void researchUpgrade(Upgrade upgrade) { researchUpgrade(upgrade.getType()); }
	public static void researchUpgrade(UpgradeType type) {
		
		Upgrade upgrade = null;
		for(Upgrade u1 : ResearchData.possibleUpgrades) {
			if(u1.getType() == type) {
				upgrade = u1;
				break;
			}
		}
		if(upgrade == null) { return; } //ALREADY RESEARCHED
		if(ResearchData.researchPoints < upgrade.getDataContainer().researchCost) { return; } //NOT ENOUGH RESEARCHPOINTS
		ResearchData.possibleUpgrades.remove(upgrade);
		ResearchData.researchedUpgrades.add(upgrade);
		ResearchData.researchPoints -= upgrade.getDataContainer().researchCost;
		
	}
	
	public static boolean hasResearched(Upgrade upgrade) { return hasResearched(upgrade.getType()); }
	public static boolean hasResearched(UpgradeType type) {
		
		for(Upgrade upgrade : ResearchData.researchedUpgrades) {
			if(upgrade.getType() == type) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean hasDependencyResearched(UpgradeType type) { return hasDependencyResearched(getUpgrade(type)); }
	public static boolean hasDependencyResearched(Upgrade upgrade) {
		
		if(upgrade.getDependency() == null) { return true; } //NO DEPENDENCY
		
		for(Upgrade upgrade_ : ResearchData.researchedUpgrades) {
			if(upgrade_.getType() == upgrade.getDependency()) {
				return true;
			}
		}
		
		return false;
	}
	
	//EXTRA UPGRADE EFFECTS
	
	public static int extra_Mine_Production() {
		if(hasResearched(UpgradeType.MineProduction3)) { return getUpgrade(UpgradeType.MineProduction3).getDataContainer().effectValue; }
		else if(hasResearched(UpgradeType.MineProduction2)) { return getUpgrade(UpgradeType.MineProduction2).getDataContainer().effectValue; }
		else if(hasResearched(UpgradeType.MineProduction1)) { return getUpgrade(UpgradeType.MineProduction1).getDataContainer().effectValue; }
		else { return 0; }
	}
	
	public static int extra_Reactor_Production() {
		if(hasResearched(UpgradeType.ReactorProduction3)) { return getUpgrade(UpgradeType.ReactorProduction3).getDataContainer().effectValue; }
		else if(hasResearched(UpgradeType.ReactorProduction2)) { return getUpgrade(UpgradeType.ReactorProduction2).getDataContainer().effectValue; }
		else if(hasResearched(UpgradeType.ReactorProduction1)) { return getUpgrade(UpgradeType.ReactorProduction1).getDataContainer().effectValue; }
		else { return 0; }
	}
	
	public static boolean extra_hasConverterUnlocked() {
		return hasResearched(UpgradeType.Converter);
	}
	
	public static int extra_Converter_Efficiency() {
		if(hasResearched(UpgradeType.ConverterEfficiency2)) { return getUpgrade(UpgradeType.ConverterEfficiency2).getDataContainer().effectValue; }
		else if(hasResearched(UpgradeType.ConverterEfficiency1)) { return getUpgrade(UpgradeType.ConverterEfficiency1).getDataContainer().effectValue; }
		else { return 0; }
	}
	
	public static int extra_Converter_Production() {
		if(hasResearched(UpgradeType.ConverterProduction2)) { return getUpgrade(UpgradeType.ConverterProduction2).getDataContainer().effectValue; }
		else if(hasResearched(UpgradeType.ConverterProduction1)) { return getUpgrade(UpgradeType.ConverterProduction1).getDataContainer().effectValue; }
		else { return 0; }
	}
	
}
 