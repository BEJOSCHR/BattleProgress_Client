package me.bejosch.battleprogress.client.Objects;

import me.bejosch.battleprogress.client.Data.StandardData;

public class DictonaryInfoDescription {

	public String titel;
	
	private String description_en;
	private String description_de;
	
	public DictonaryInfoDescription(String titel, String description_en, String description_de) {
		
		this.titel = titel;
		
		this.description_en = description_en;
		this.description_de = description_de;
		
	}
	
	public String getDescription() {
		
		switch(StandardData.selectedLanguage) {
		case DE:
			return this.description_de;
		case EN:
		default:
			return this.description_en;
		}
		
	}
	
}
