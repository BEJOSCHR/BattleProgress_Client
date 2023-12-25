package me.bejosch.battleprogress.client.Objects.OnTopWindow.UnitDetailInfo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Game.Handler.Game_FieldDataHandler;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Handler.UnitsHandler;
import me.bejosch.battleprogress.client.Objects.DictonaryInfoDescription;
import me.bejosch.battleprogress.client.Objects.FieldData;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Mine;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.Field_Ressource;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;
import me.bejosch.battleprogress.client.Objects.Research.UpgradeDataContainer;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class OnTopWindow_UnitDetailInfo extends OnTopWindow {

	public boolean isResearch = false, isDictionaryCall = false;
	
	public String displayName;
	public String[] shortDescription;
	public Image displayImage;
	public int cost;
	public List<String> categories = new ArrayList<String>(); //EACH STRING IS ONE DISPLAYED CATEGORY LIKE: Health: xxx, Damage: yyy etc
	
	public OnTopWindow_UnitDetailInfo(UnitStatsContainer container, boolean isTroup, boolean isDictionaryCall) {
		super("OTW_UnitDetailInfo", OnTopWindowData.unitDetailInfo_width, OnTopWindowData.unitDetailInfo_height);
		
		this.displayName = ""+container.name;
		this.shortDescription = container.getDescription();
		this.displayImage = Images.dictionary_general_placeholder;
		this.cost = container.kosten;
		
		categories.add("Health: "+container.leben+"/"+container.leben);
		
		addCategory("ViewDistance: ", container.viewDistance);
		addCategory("MoveDistance: ", container.moveDistance);
		addCategory("ActionDistance: ", container.actionDistance);

		addCategory("Damage: ", container.schaden);
		addCategory("EnergyConsume: ", container.energieVerbrauch);
		addCategory("Heal: ", container.heal);
		addCategory("Repair: ", container.repair);
		addCategory("EnergyProduction: ", container.energieProduktion);
		addCategory("MaterialProduction: ", container.materialProduktion);
		addCategory("ResearchProduction: ", container.research);
		//...
		
		this.isDictionaryCall = isDictionaryCall;
		
	}
	
	public OnTopWindow_UnitDetailInfo(Troup troup, boolean isDictionaryCall) {
		super("OTW_UnitDetailInfo", OnTopWindowData.unitDetailInfo_width, OnTopWindowData.unitDetailInfo_height);
		UnitStatsContainer container = UnitsHandler.getUnitByName(troup.name);
		
		this.displayName = ""+troup.name;
		this.shortDescription = troup.hoverDescription;
		this.displayImage = troup.img;
		this.cost = container.kosten;
		
		categories.add("Health: "+troup.totalHealth+"/"+troup.maxHealth);
		
		if(troup.canFly) {
			//AIR
			categories.add("Type: AIR");
		}else {
			//LAND
			categories.add("Type: LAND");
		}
		
		addCategory("ViewDistance: ", troup.viewDistance);
		addCategory("MoveDistance: ", troup.moveDistance);
		addCategory("ActionDistance: ", troup.actionRange);

		addCategory("Damage: ", troup.damage);
		addCategory("EnergyConsume: ", troup.energyCostPerAction);
		addCategory("Heal: ", troup.heal);
		addCategory("Repair: ", troup.repair);
		//...
		
		this.isDictionaryCall = isDictionaryCall;
		
	}
	
	public OnTopWindow_UnitDetailInfo(Building building, boolean isDictionaryCall) {
		super("OTW_UnitDetailInfo", OnTopWindowData.unitDetailInfo_width, OnTopWindowData.unitDetailInfo_height);
		UnitStatsContainer container = UnitsHandler.getUnitByName(building.name);
		
		this.displayName = ""+building.name;
		this.shortDescription = building.hoverDescription;
		this.displayImage = building.img;
		this.cost = container.kosten;
		
		categories.add("Health: "+building.totalHealth+"/"+building.maxHealth);
		
		addCategory("ViewDistance: ", building.viewDistance);
		addCategory("ActionDistance: ", building.actionRange);
		
		addCategory("Damage: ", building.damage);
		addCategory("EnergyConsume: ", building.energyCostPerAction);
		addCategory("Heal: ", building.heal);
		addCategory("Repair: ", building.repair);
		addCategory("EnergyProduction: ", container.energieProduktion);
		addCategory("MaterialProduction: ", container.materialProduktion);
		addCategory("ResearchProduction: ", container.research);
		//...
		
		if(building instanceof Building_Mine) {
			Field_Ressource resField = (Field_Ressource) building.connectedField;
			addCategory("MiningRoundsRemaining: ", resField.getProducingRounds());
		}
		
		this.isDictionaryCall = isDictionaryCall;
		
	}

	public OnTopWindow_UnitDetailInfo(Field field, boolean isDictionaryCall) {
		super("OTW_UnitDetailInfo", OnTopWindowData.unitDetailInfo_width, OnTopWindowData.unitDetailInfo_height);
		
		FieldData fd = Game_FieldDataHandler.getFieldData(field.type);
		
		this.displayName = fd.titel;
		this.shortDescription = fd.getDescription();
		this.displayImage = field.img;
		this.cost = -1;
		
		if(field instanceof Field_Ressource) {
			Field_Ressource resField = (Field_Ressource) field;
			addCategory("MiningRoundsRemaining: ", resField.getProducingRounds());
		}
		
		this.isDictionaryCall = isDictionaryCall;
		
	}
	
	public OnTopWindow_UnitDetailInfo(UpgradeDataContainer udc, boolean isDictionaryCall) {
		super("OTW_UnitDetailInfo", OnTopWindowData.unitDetailInfo_width, OnTopWindowData.unitDetailInfo_height);
		
		this.displayName = udc.upgradeType.toString();
		this.shortDescription = udc.getDescription();
		this.displayImage = Images.dictionary_general_placeholder;
		this.cost = udc.researchCost;
		
		this.isResearch = true;
		this.isDictionaryCall = isDictionaryCall;
		
	}
	
	public OnTopWindow_UnitDetailInfo(DictonaryInfoDescription did, boolean isDictionaryCall) {
		super("OTW_UnitDetailInfo", OnTopWindowData.unitDetailInfo_width, OnTopWindowData.unitDetailInfo_height);
		
		this.displayName = did.titel;
		
		String description = did.getDescription();
		int maxLength = OnTopWindowData.unitDetailInfo_width-OnTopWindowData.unitDetailInfo_descriptionBorderLeft*2;
		
		int lines = (int) ( (description.length()*8)/maxLength ); //8 IS JUST A APPROXIMATION OF CHAR WIDTH
		String[] finalDescription = new String[lines+1];
		finalDescription[0] = ""; //INIT FIRST LINE
		
		int length = 0, i = 0;
		FontMetrics fm = new Canvas().getFontMetrics(OnTopWindowData.unitDetailInfo_descriptionFont);
		
		for(String word : description.trim().split(" ")) {
			
			int wordLength = fm.stringWidth(word+" ");
			if(length+wordLength >= maxLength) {
				//TO LONG
				i++;
				length = wordLength;
				finalDescription[i] = word+" ";
			}else {
				//FITS
				length += wordLength;
				finalDescription[i] = finalDescription[i]+word+" ";
			}
			
		}
		
		this.shortDescription = finalDescription;
		this.displayImage = Images.dictionary_general_placeholder;
		this.cost = -1;
		
		this.isDictionaryCall = isDictionaryCall;
		
	}
	
	private void addCategory(String text, int amount) {
		
		if(amount > 0) {
			categories.add(text+amount);
		}
		
	}
	
	private int getX() {
		return WindowData.FrameWidth/2-this.width/2;
	}
	private int getY() {
		return WindowData.FrameHeight/2-this.height/2;
	}
	
	@Override
	public void initOnOpen() {
		
		MovementHandler.cancleMovement();
		
	}
	
	@Override
	public void performClose() {
		
		MovementHandler.allowMovement();
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(this.getX(), this.getY(), this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(this.getX(), this.getY(), this.width, this.height);
		
		//IMAGE
		if(this.displayImage != null) {
			g.drawImage(this.displayImage, this.getX()+OnTopWindowData.unitDetailInfo_imageBorderLeft, this.getY()+OnTopWindowData.unitDetailInfo_imageBorderTopDown, null);
		}
		//TITEL
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 34));
		g.drawString(this.displayName, this.getX()+OnTopWindowData.unitDetailInfo_imageBorderLeft+OnTopWindowData.unitDetailInfo_imageWidthHeight+OnTopWindowData.unitDetailInfo_titelBorderToImage, this.getY()+OnTopWindowData.unitDetailInfo_titelSectionHeight-30);
		//COST
		if(this.cost != -1) {
			if(this.isResearch) {
				g.setColor(GameData.color_Research);
			}else {
				g.setColor(GameData.color_Material);
			}
			g.setFont(new Font("Arial", Font.BOLD, 36));
			g.drawString(""+this.cost, this.getX()+this.width-OnTopWindowData.unitDetailInfo_costBorderRight-(16*(""+this.cost).length()), this.getY()+OnTopWindowData.unitDetailInfo_titelSectionHeight-30);
		}
		//DEVIDER
		int descriptionY = getY()+OnTopWindowData.unitDetailInfo_titelSectionHeight;
		g.setColor(Color.WHITE);
		g.drawLine(getX(), descriptionY, getX()+this.width, descriptionY);
		//DESCRIPTION
		int linesDisplayed = 0;
		g.setColor(Color.WHITE);
		g.setFont(OnTopWindowData.unitDetailInfo_descriptionFont);
		for(String line : this.shortDescription) {
			linesDisplayed++;
			int y = descriptionY+OnTopWindowData.unitDetailInfo_descriptionBorderTopDown+(linesDisplayed*OnTopWindowData.unitDetailInfo_descriptionLineHeight);
			g.drawString(line, this.getX()+OnTopWindowData.unitDetailInfo_descriptionBorderLeft, y);
		}
		//DEVIDER
		int categoriesY = descriptionY+(OnTopWindowData.unitDetailInfo_descriptionBorderTopDown*2)+(linesDisplayed*OnTopWindowData.unitDetailInfo_descriptionLineHeight);
		if(this.categories.isEmpty() == false) {
			g.setColor(Color.WHITE);
			g.drawLine(getX(), categoriesY, getX()+this.width, categoriesY);
		}
		//DISPLAY CATEGORIES IN TWO ROWs
		int posX = 0, posY = 0;
		for(String category : this.categories) {
			int x = this.getX()+OnTopWindowData.unitDetailInfo_categoriesBorderLeft+(posX*OnTopWindowData.unitDetailInfo_categoriesWidth);
			int y = categoriesY+OnTopWindowData.unitDetailInfo_categoriesBorderTopDown+(posY*OnTopWindowData.unitDetailInfo_categoriesHeight);
			
			g.setColor(Color.LIGHT_GRAY);
			g.drawRect(x, y, OnTopWindowData.unitDetailInfo_categoriesWidth, OnTopWindowData.unitDetailInfo_categoriesHeight);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.CENTER_BASELINE, 18));
			g.drawString(category, x+10, y+OnTopWindowData.unitDetailInfo_categoriesHeight-8);
			
			if(posX == 1) { posX = 0; posY++; }else { posX = 1; }
		}
		
	}
}
