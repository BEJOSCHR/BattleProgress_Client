package me.bejosch.battleprogress.client.Objects.OnTopWindow.UnitDetailInfo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Handler.UnitsHandler;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Mine;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.Field_Ressource;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;

public class OnTopWindow_UnitDetailInfo extends OnTopWindow {

	public String displayName;
	public String[] shortDescription;
	public Image displayImage;
	public int cost;
	public List<String> categories = new ArrayList<String>(); //EACH STRING IS ONE DISPLAYED CATEGORY LIKE: Health: xxx, Damage: yyy etc
	
	public OnTopWindow_UnitDetailInfo(Troup troup) {
		super("OTW_UnitDetailInfo", OnTopWindowData.unitDetailInfo_width, OnTopWindowData.unitDetailInfo_height);
		UnitStatsContainer container = UnitsHandler.getUnitByName(troup.name);
		
		this.displayName = ""+troup.name;
		this.shortDescription = troup.hoverDescription; //(Change it away from this Bad/Good descriptions to short summary over the use, details in this otw not by hover)
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
		
		categories.add("ViewDistance: "+troup.viewDistance);
		categories.add("MoveDistance: "+troup.moveDistance);
		categories.add("ActionDistance: "+troup.actionRange);

		categories.add("Damage: "+troup.damage);
		categories.add("EnergyConsume: "+troup.energyCostPerAction);
		categories.add("Heal: "+troup.heal);
		categories.add("Repair: "+troup.repair);
		//...
		
	}
	
	public OnTopWindow_UnitDetailInfo(Building building) {
		super("OTW_UnitDetailInfo", OnTopWindowData.unitDetailInfo_width, OnTopWindowData.unitDetailInfo_height);
		UnitStatsContainer container = UnitsHandler.getUnitByName(building.name);
		
		this.displayName = ""+building.name;
		this.shortDescription = building.hoverDescription; //(Change it away from this Bad/Good descriptions to short summary over the use, details in this otw not by hover)
		this.displayImage = building.img;
		this.cost = container.kosten;
		
		categories.add("Health: "+building.totalHealth+"/"+building.maxHealth);
		
		categories.add("ViewDistance: "+building.viewDistance);
		categories.add("ActionDistance: "+building.actionRange);
		
		categories.add("Damage: "+building.damage);
		categories.add("EnergyConsume: "+building.energyCostPerAction);
		if(container.energieProduktion != -1) { categories.add("EnergyProduction: "+container.energieProduktion); }else { categories.add("EnergyProduction: 0"); }
		if(container.materialProduktion != -1) { categories.add("MaterialProduction: "+container.materialProduktion); }else { categories.add("MaterialProduction: 0"); }
		categories.add("Heal: "+building.heal);
		categories.add("Repair: "+building.repair);
		categories.add("Research: "+building.research);
		//...
		
		if(building instanceof Building_Mine) {
			Field_Ressource resField = (Field_Ressource) building.connectedField;
			categories.add("ProducingRoundsLeft: "+resField.getProducingRounds());
		}
		
	}

	public OnTopWindow_UnitDetailInfo(Field field) {
		super("OTW_UnitDetailInfo", OnTopWindowData.unitDetailInfo_width, OnTopWindowData.unitDetailInfo_height);
		
		this.displayName = ""+FieldType.getNameForFieldType(field.type);
		this.shortDescription = FieldType.getDescriptionForFieldType(field.type); //(Change it away from this Bad/Good descriptions to short summary over the use, details in this otw not by hover)
		this.displayImage = field.img;
		this.cost = -1;
		
		if(field instanceof Field_Ressource) {
			Field_Ressource resField = (Field_Ressource) field;
			categories.add("ProducingLeft: "+resField.getProducingRounds());
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
		g.drawImage(this.displayImage, this.getX()+OnTopWindowData.unitDetailInfo_imageBorderLeft, this.getY()+OnTopWindowData.unitDetailInfo_imageBorderTopDown, null);
		//TITEL
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 34));
		g.drawString(this.displayName, this.getX()+OnTopWindowData.unitDetailInfo_imageBorderLeft+OnTopWindowData.unitDetailInfo_imageWidthHeight+OnTopWindowData.unitDetailInfo_titelBorderToImage, this.getY()+OnTopWindowData.unitDetailInfo_titelSectionHeight-30);
		//COST
		if(this.cost != -1) {
			g.setColor(Color.YELLOW);
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
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 18));
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
