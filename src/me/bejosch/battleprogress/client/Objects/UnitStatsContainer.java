package me.bejosch.battleprogress.client.Objects;

public class UnitStatsContainer {

	//LOADS UNITS STATS FROM SSERVER INTO THESE CONTAINERS
	//THEN IN THE UNITS LOAD VIA NAME THE CONTAINER AND FRIN IT THE STATS
	
	//TODO DIESE STATD WERDEN VOR JEDEM SPIEL START AKTUALISIERT, SO DASS W�REND DEM SPIEL IMMER DIE GLEICHEN STATS GELTEN AUCH WENN DANACH �NDERUNGEN STATTFINDEN
	
	public String name;
	public String kürzel;
	public int kosten;
	public int leben;
	public int energieVerbrauch;
	public int energieProduktion;
	public int materialProduktion;
	public int schaden;
	public int viewDistance;
	public int moveDistance; //FOR BUILDINGS 0
	public int actionDistance;
	public int heal;
	public int repair;
	public int research;
	
	//CLIENT
	public UnitStatsContainer(String name_, String kürzel_, int kosten_, int leben_, int energieVerbrauch_, int energieProduktion_, int materialProduktion_, int schaden_, int viewDistance_, int moveDistance_, int actionDistance_, int heal_, int repair_, int research_) {
		
		this.name = name_;
		this.kürzel = kürzel_;
		this.kosten = kosten_;
		this.leben = leben_;
		this.energieVerbrauch = energieVerbrauch_;
		this.energieProduktion = energieProduktion_;
		this.materialProduktion = materialProduktion_;
		this.schaden = schaden_;
		this.viewDistance = viewDistance_;
		this.moveDistance = moveDistance_;
		this.actionDistance = actionDistance_;
		this.heal = heal_;
		this.repair = repair_;
		this.research = research_;;
		
	}
	
}