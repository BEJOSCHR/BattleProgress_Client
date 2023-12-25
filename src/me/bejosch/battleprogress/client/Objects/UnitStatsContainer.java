package me.bejosch.battleprogress.client.Objects;

import me.bejosch.battleprogress.client.Data.StandardData;

public class UnitStatsContainer {

	//LOADS UNITS STATS FROM SERVER INTO THESE CONTAINERS
	//THEN IN THE UNITS LOAD VIA NAME
	
	public String name;
	public String kürzel;
	public int kosten;
	public int leben;
	public int energieVerbrauch;
	public int energieProduktion;
	public int materialProduktion;
	public int schaden;
	public int viewDistance;
	public int moveDistance; //FOR BUILDINGS -1
	public int actionDistance;
	public int heal;
	public int repair;
	public int research;
	
	private String[] description_en;
	private String[] description_de;
	
	//CLIENT
	public UnitStatsContainer(String name_, String kürzel_, int kosten_, int leben_, int energieVerbrauch_, int energieProduktion_, int materialProduktion_, int schaden_, int viewDistance_, int moveDistance_, int actionDistance_, int heal_, int repair_, int research_, String[] description_en_, String[] description_de_) {
		
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
		
		this.description_en = description_en_;
		this.description_de = description_de_;
		
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
