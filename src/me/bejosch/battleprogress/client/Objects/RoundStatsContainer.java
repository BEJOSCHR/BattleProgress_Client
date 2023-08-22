package me.bejosch.battleprogress.client.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Research.Upgrades.Upgrade;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;

public class RoundStatsContainer {

	private int roundNumber;
	
	//ECO PRODUCTION
	private int massProduced = 0;
	private Map<Building, Integer> massProduction_building = new HashMap<Building, Integer>();
	private Map<Troup, Integer> massProduction_troup = new HashMap<Troup, Integer>();
	private int energyProduced = 0;
	private Map<Building, Integer> energyProduction_building = new HashMap<Building, Integer>();
	private Map<Troup, Integer> energyProduction_troup = new HashMap<Troup, Integer>();
	private int researchProduced = 0;
	private Map<Building, Integer> researchProduction_building = new HashMap<Building, Integer>();
	private Map<Troup, Integer> researchProduction_troup = new HashMap<Troup, Integer>();
	
	//ECO CONSUMPTION
	private int massConsumed = 0;
	private Map<Building, Integer> massConsumption_building = new HashMap<Building, Integer>();
	private Map<Troup, Integer> massConsumption_troup = new HashMap<Troup, Integer>();
	private int energyConsumed = 0;
	private Map<Building, Integer> energyConsumption_building = new HashMap<Building, Integer>();
	private Map<Troup, Integer> energyConsumption_troup = new HashMap<Troup, Integer>();
	private int researchConsumed = 0;
	private List<Upgrade> researchConsumption = new ArrayList<Upgrade>();
	
	//DAMAGE DEALT
	private int damageDealt = 0;
	private Map<Building, Integer> damageDealt_building = new HashMap<Building, Integer>();
	private Map<Troup, Integer> damageDealt_troup = new HashMap<Troup, Integer>();
	
	//DAMAGE RECEIVED
	private int damageReceived = 0;
	private Map<Building, Integer> damageReceived_building = new HashMap<Building, Integer>();
	private Map<Troup, Integer> damageReceived_troup = new HashMap<Troup, Integer>();
	
	//HEALED/REPAIRED
	private int healed = 0, repaired = 0;
	private Map<Building, Integer> repaired_building = new HashMap<Building, Integer>();
	private Map<Troup, Integer> healed_troup = new HashMap<Troup, Integer>();
	
	//KILLS
	private List<Building> killedBuildings = new ArrayList<Building>();
	private List<Troup> killedTroups= new ArrayList<Troup>();
	
	//DEATHS
	private List<Building> deadBuildings = new ArrayList<Building>();
	private List<Troup> deadTroups= new ArrayList<Troup>();
	
	//BUILD
	private List<Building> buildBuildings = new ArrayList<Building>();
	
	//PRODUCED
	private List<Troup> producedTroups = new ArrayList<Troup>();
	
	//UPGRADED
	private List<Troup> upgradedTroups = new ArrayList<Troup>();
	
	public RoundStatsContainer(int roundNumber) {
		
		this.roundNumber = roundNumber;
		
	}
	
	//SETTER (kinda)
	
	//MASS
	public void addMassEntry(Building b, int amount) {
		if(amount > 0) {
			//PRODUCTION
			this.massProduction_building.put(b, amount);
			this.massProduced += amount;
		}else {
			//CONSUMPTION
			this.massConsumption_building.put(b, amount);
			this.massConsumed -= amount;
		}
	}
	public void addMassEntry(Troup t, int amount) {
		if(amount > 0) {
			//PRODUCTION
			this.massProduction_troup.put(t, amount);
			this.massProduced += amount;
		}else {
			//CONSUMPTION
			this.massConsumption_troup.put(t, amount);
			this.massConsumed -= amount;
		}
	}
	
	//ENERGY
	public void addEnergyEntry(Building b, int amount) {
		if(amount > 0) {
			//PRODUCTION
			this.energyProduction_building.put(b, amount);
			this.energyProduced += amount;
		}else {
			//CONSUMPTION
			this.energyConsumption_building.put(b, amount);
			this.energyConsumed -= amount;
		}
	}
	public void addEnergyEntry(Troup t, int amount) {
		if(amount > 0) {
			//PRODUCTION
			this.energyProduction_troup.put(t, amount);
			this.energyProduced += amount;
		}else {
			//CONSUMPTION
			this.energyConsumption_troup.put(t, amount);
			this.energyConsumed -= amount;
		}
	}
	
	//RESEARCH
	public void addResearchEntry(Building b, int amount) {
		//AMOUNT HAS TO BE POSITIVE
		this.researchProduction_building.put(b, amount);
		this.researchProduced += amount;
	}
	public void addResearchEntry(Troup t, int amount) {
		//AMOUNT HAS TO BE POSITIVE
		this.researchProduction_troup.put(t, amount);
		this.researchProduced += amount;
	}
	public void addResearchEntry(Upgrade u) {
		this.researchConsumption.add(u);
		this.researchConsumed += u.getDataContainer().researchCost;
	}
	
	//DAMAGE DEALT
	public void registerDamageDealt(Building b, int amount) {
		this.damageDealt_building.put(b, amount);
		this.damageDealt += amount;
	}
	public void registerDamageDealt(Troup t, int amount) {
		this.damageDealt_troup.put(t, amount);
		this.damageDealt += amount;
	}
	
	//DAMAGE RECEIVED
	public void registerDamageReceived(Building b, int amount) {
		this.damageReceived_building.put(b, amount);
		this.damageReceived += amount;
	}
	public void registerDamageReceived(Troup t, int amount) {
		this.damageReceived_troup.put(t, amount);
		this.damageReceived += amount;
	}
	
	//DAMAGE HEALED/REPAIRED
	public void registerBuildingRepair(Building b, int amount) {
		this.repaired_building.put(b, amount);
		this.repaired += amount;
	}
	public void registerTroupHeal(Troup t, int amount) {
		this.healed_troup.put(t, amount);
		this.healed += amount;
	}
	
	//KILL
	public void registerKill(Building b) {
		this.killedBuildings.add(b);
	}
	public void registerKill(Troup t) {
		this.killedTroups.add(t);
	}
	
	//DEATH
	public void registerDeath(Building b) {
		this.deadBuildings.add(b);
	}
	public void registerDeath(Troup t) {
		this.deadTroups.add(t);
	}
	
	//BUILD
	public void registerBuild(Building b) {
		this.buildBuildings.add(b);
	}
	
	//PRODUCED
	public void registerProduced(Troup t) {
		this.producedTroups.add(t);
	}
	
	//UPGRADE
	public void registerUpgrade(Troup t) {
		this.upgradedTroups.add(t);
	}
	
	//CUSTOM GETTER
	
	public int massProduced() {
		return massProduced;
	}
	public int energyProduced() {
		return energyProduced;
	}
	public int researchProduced() {
		return researchProduced;
	}
	
	public int massConsumed() {
		return massConsumed;
	}
	public int energyConsumed() {
		return energyConsumed;
	}
	public int researchConsumed() {
		return researchConsumed;
	}
	
	public int massBalance() {
		return this.massProduced-this.massConsumed;
	}
	public int energyBalance() {
		return this.energyProduced-this.energyConsumed;
	}
	public int researchBalance() {
		return this.researchProduced-this.researchConsumed;
	}
	
	public int getDamageDealt() {
		return damageDealt;
	}
	public int getDamageReceived() {
		return damageReceived;
	}
	public int damageBalance() {
		return this.damageDealt-this.damageReceived;
	}
	public int getHealed() {
		return healed;
	}
	public int getRepaired() {
		return repaired;
	}
	public int addedHP() {
		return this.healed+this.repaired;
	}
	public int getKills() {
		return this.killedBuildings.size()+this.killedTroups.size();
	}
	public int getDeaths() {
		return this.deadBuildings.size()+this.deadTroups.size();
	}
	public int getKDBalance() {
		return this.getKills()-this.getDeaths();
	}
	public int getBuildAmount() {
		return this.buildBuildings.size();
	}
	public int getProducedAmount() {
		return producedTroups.size();
	}
	public int getUpgradedAmount() {
		return this.upgradedTroups.size();
	}
	
	public int getRoundNumber() {
		return roundNumber;
	}

	//LIST GETTER

	public Map<Building, Integer> getMassProduction_building() {
		return massProduction_building;
	}

	public Map<Troup, Integer> getMassProduction_troup() {
		return massProduction_troup;
	}

	public Map<Building, Integer> getEnergyProduction_building() {
		return energyProduction_building;
	}

	public Map<Troup, Integer> getEnergyProduction_troup() {
		return energyProduction_troup;
	}

	public Map<Building, Integer> getResearchProduction_building() {
		return researchProduction_building;
	}

	public Map<Troup, Integer> getResearchProduction_troup() {
		return researchProduction_troup;
	}

	public Map<Building, Integer> getMassConsumption_building() {
		return massConsumption_building;
	}

	public Map<Troup, Integer> getMassConsumption_troup() {
		return massConsumption_troup;
	}

	public Map<Building, Integer> getEnergyConsumption_building() {
		return energyConsumption_building;
	}

	public Map<Troup, Integer> getEnergyConsumption_troup() {
		return energyConsumption_troup;
	}

	public List<Upgrade> getResearchConsumption() {
		return researchConsumption;
	}

	public Map<Building, Integer> getDamageDealt_building() {
		return damageDealt_building;
	}

	public Map<Troup, Integer> getDamageDealt_troup() {
		return damageDealt_troup;
	}

	public Map<Building, Integer> getDamageReceived_building() {
		return damageReceived_building;
	}

	public Map<Troup, Integer> getDamageReceived_troup() {
		return damageReceived_troup;
	}

	public Map<Building, Integer> getRepaired_building() {
		return repaired_building;
	}

	public Map<Troup, Integer> getHealed_troup() {
		return healed_troup;
	}

	public List<Building> getKilledBuildings() {
		return killedBuildings;
	}

	public List<Troup> getKilledTroups() {
		return killedTroups;
	}

	public List<Building> getDeadBuildings() {
		return deadBuildings;
	}

	public List<Troup> getDeadTroups() {
		return deadTroups;
	}
	
	public List<Building> getBuildBuildings() {
		return buildBuildings;
	}
	
	public List<Troup> getProducedTroups() {
		return producedTroups;
	}
	
	public List<Troup> getUpgradedTroups() {
		return upgradedTroups;
	}
	
	
}
