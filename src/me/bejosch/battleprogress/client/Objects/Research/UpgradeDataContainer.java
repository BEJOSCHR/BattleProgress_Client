package me.bejosch.battleprogress.client.Objects.Research;

import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Enum.UpgradeType;

public class UpgradeDataContainer {

	public UpgradeType upgradeType = null;
	
	public int researchCost = 9999;
	public int effectValue = 0; //THE AMOUNT BY THAT IT UPGRADES
	
	private String[] description_en;
	private String[] description_de;
	
	public UpgradeDataContainer(UpgradeType upgradeType, int cost, int effectValue, String[] description_en, String[] description_de) {
		
		this.upgradeType = upgradeType;
		
		this.researchCost = cost;
		this.effectValue = effectValue;
		
		this.description_en = description_en;
		this.description_de = description_de;
		
		for(int i = 0 ; i < 4 ; i++) {
			if(this.effectValue == 0) {
				this.description_en[i] = this.description_en[i].replace("%%", "?");
				this.description_de[i] = this.description_de[i].replace("%%", "?");
			}else {
				this.description_en[i] = this.description_en[i].replace("%%", ""+this.effectValue);
				this.description_de[i] = this.description_de[i].replace("%%", ""+this.effectValue);
			}
		}
		
	}
	
	public String[] getDescription() {
		
		switch(StandardData.selectedLanguage) {
		case DE:
			return this.description_de;
		case EN:
		default:
			return this.description_en;
		}
		
	}
	
}
