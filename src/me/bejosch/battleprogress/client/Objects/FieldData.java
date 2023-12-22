package me.bejosch.battleprogress.client.Objects;

import me.bejosch.battleprogress.client.Data.StandardData;

public class FieldData {

	public String titel;
	
	private String[] description_en;
	private String[] description_de;
	
	public FieldData(String titel_, String[] description_en_, String[] description_de_) {
		
		this.titel = titel_;
		
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
