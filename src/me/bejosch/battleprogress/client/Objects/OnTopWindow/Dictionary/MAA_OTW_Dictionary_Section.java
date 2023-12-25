package me.bejosch.battleprogress.client.Objects.OnTopWindow.Dictionary;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.DictonaryInfoDescription;
import me.bejosch.battleprogress.client.Objects.FieldData;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.Field_Ressource;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.UnitDetailInfo.OnTopWindow_UnitDetailInfo;
import me.bejosch.battleprogress.client.Objects.Research.UpgradeDataContainer;
import me.bejosch.battleprogress.client.Window.TextFields.TextFields;

public class MAA_OTW_Dictionary_Section extends MouseActionArea {

	private int pos;
	
	public MAA_OTW_Dictionary_Section(int pos) {
		super(0, 0, 0, 0, "OTW_Dictionary_Section_"+pos, null, ShowBorderType.ShowAlways, Color.LIGHT_GRAY, Color.ORANGE);
		this.OTWMMA = true;
		
		this.pos = pos;
		this.xTL = WindowData.FrameWidth/2-OnTopWindowData.dictionary_width/2;
		this.yTL = WindowData.FrameHeight/2-OnTopWindowData.dictionary_height/2+OnTopWindowData.dictionary_titelSectionHeight+(pos*OnTopWindowData.dictionary_sectionHeight)+1;
		this.xBR = WindowData.FrameWidth/2+OnTopWindowData.dictionary_width/2;
		this.yBR = WindowData.FrameHeight/2-OnTopWindowData.dictionary_height/2+OnTopWindowData.dictionary_titelSectionHeight+((pos+1)*OnTopWindowData.dictionary_sectionHeight);
		
	}
	
	@Override
	public boolean isActiv() {
		
		if(OnTopWindowData.onTopWindow != null) {
			if(OnTopWindowData.onTopWindow instanceof OnTopWindow_Dictionary) {
				//ALWAYS ACTIVE ON THIS OTW, BECAUSE WE HAVE MORE THEN ENOUGH DATA TO FILL ALL SECTIONS
				return true;
			}
		}
		return false;
		
	}
	
	public int getRepresentedPosition() {
		
		return this.pos+OnTopWindowData.dictionary_scrollPos;
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		//TODO: Add field info to dictionary section and fix this open mess (generate troup, building, field opening and new upgrade constructor to detailed info)
		
		if(getRepresentedPosition() == 0) {
		}else if(getRepresentedPosition() < OnTopWindowData.dictionary_usc_buildings.size()+1) {
			
			//BUILDING
			UnitStatsContainer usc_b = OnTopWindowData.dictionary_usc_buildings.get(getRepresentedPosition()-1);
			OnTopWindowHandler.openOTW(new OnTopWindow_UnitDetailInfo(usc_b, false, true), true);
			
		}else if(getRepresentedPosition() == OnTopWindowData.dictionary_usc_buildings.size()+1) {	
		}else if(getRepresentedPosition() < OnTopWindowData.dictionary_usc_buildings.size()+1+OnTopWindowData.dictionary_usc_troups.size()+1) {
			
			//TROUP
			UnitStatsContainer usc_t = OnTopWindowData.dictionary_usc_troups.get(getRepresentedPosition()-1-OnTopWindowData.dictionary_usc_buildings.size()-1);
			OnTopWindowHandler.openOTW(new OnTopWindow_UnitDetailInfo(usc_t, true, true), true);
			
		}else if(getRepresentedPosition() == OnTopWindowData.dictionary_usc_buildings.size()+1+OnTopWindowData.dictionary_usc_troups.size()+1) {
		}else if(getRepresentedPosition() < OnTopWindowData.dictionary_usc_buildings.size()+1+OnTopWindowData.dictionary_usc_troups.size()+1+OnTopWindowData.dictionary_udc_upgrades.size()+1) {
			
			//UPGRADE
			UpgradeDataContainer udc = OnTopWindowData.dictionary_udc_upgrades.get(getRepresentedPosition()-1-OnTopWindowData.dictionary_usc_buildings.size()-1-OnTopWindowData.dictionary_usc_troups.size()-1);
			OnTopWindowHandler.openOTW(new OnTopWindow_UnitDetailInfo(udc, true), true);
			
		}else if(getRepresentedPosition() == OnTopWindowData.dictionary_usc_buildings.size()+1+OnTopWindowData.dictionary_usc_troups.size()+1+OnTopWindowData.dictionary_udc_upgrades.size()+1) {
		}else if(getRepresentedPosition() < OnTopWindowData.dictionary_usc_buildings.size()+1+OnTopWindowData.dictionary_usc_troups.size()+1+OnTopWindowData.dictionary_udc_upgrades.size()+1+OnTopWindowData.dictionary_fd_fields.size()+1) {
			
			//FIELD DATA
			FieldData fd = OnTopWindowData.dictionary_fd_fields.get(getRepresentedPosition()-1-OnTopWindowData.dictionary_usc_buildings.size()-1-OnTopWindowData.dictionary_usc_troups.size()-1-OnTopWindowData.dictionary_udc_upgrades.size()-1);
			FieldType fd_type = FieldType.valueOf(fd.titel);
			if(fd_type == FieldType.Ressource) {
				OnTopWindowHandler.openOTW(new OnTopWindow_UnitDetailInfo(new Field_Ressource(-1, -1), true), true);
			}else {
				OnTopWindowHandler.openOTW(new OnTopWindow_UnitDetailInfo(new Field(fd_type, -1, -1), true), true);
			}
			
		}else if(getRepresentedPosition() == OnTopWindowData.dictionary_usc_buildings.size()+1+OnTopWindowData.dictionary_usc_troups.size()+1+OnTopWindowData.dictionary_udc_upgrades.size()+1) {
		}else {
			
			//DICTIONARY INFO
			DictonaryInfoDescription did = OnTopWindowData.dictionary_did_infos.get(getRepresentedPosition()-1-OnTopWindowData.dictionary_usc_buildings.size()-1-OnTopWindowData.dictionary_usc_troups.size()-1-OnTopWindowData.dictionary_udc_upgrades.size()-1-OnTopWindowData.dictionary_fd_fields.size()-1);
			OnTopWindowHandler.openOTW(new OnTopWindow_UnitDetailInfo(did ,true), true);
			
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		super.draw(g);
		
		if(this.isActiv()) {
			
			String highlightContext = TextFields.textField_Dictionary.getText().toLowerCase();
			
			g.setColor(Color.LIGHT_GRAY);
			g.setFont(new Font("Arial", Font.CENTER_BASELINE, 12));
			String number = (this.getRepresentedPosition()+1)+".";
			g.drawString(number, this.xBR-5-g.getFontMetrics().stringWidth(number), this.yBR-5);
			
			if(getRepresentedPosition() == 0) {
				
				g.setColor(OnTopWindowData.dictionary_sectionTitelColor);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 30));
				String text = "Buildings";
				int textWidth = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-textWidth/2, this.yBR-15);
				
			}else if(getRepresentedPosition() < OnTopWindowData.dictionary_usc_buildings.size()+1) {
				
				//BUILDING
				UnitStatsContainer usc_b = OnTopWindowData.dictionary_usc_buildings.get(getRepresentedPosition()-1);
				
				g.setColor( ( (highlightContext.length() > 0 && usc_b.name.toLowerCase().contains(highlightContext)) ? Color.RED : Color.WHITE) );
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 22));
				g.drawString(usc_b.name, this.xTL+5, this.yTL+32);
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 13));
				g.drawString(usc_b.getDescription()[0], this.xTL+5, this.yBR-20);
				g.drawString(usc_b.getDescription()[1], this.xTL+5, this.yBR-5);
				
				if(usc_b.kosten > 0) {
					g.setColor(GameData.color_Material);
					g.setFont(new Font("Arial", Font.CENTER_BASELINE, 22));
					String kosten_b = ""+usc_b.kosten;
					g.drawString(kosten_b, this.xBR-g.getFontMetrics().stringWidth(kosten_b)-5, this.yTL+30);
				}
				
			}else if(getRepresentedPosition() == OnTopWindowData.dictionary_usc_buildings.size()+1) {
				
				g.setColor(OnTopWindowData.dictionary_sectionTitelColor);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 30));
				String text = "Troups";
				int textWidth = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-textWidth/2, this.yBR-15);
				
			}else if(getRepresentedPosition() < OnTopWindowData.dictionary_usc_buildings.size()+1+OnTopWindowData.dictionary_usc_troups.size()+1) {
				
				//TROUP
				UnitStatsContainer usc_t = OnTopWindowData.dictionary_usc_troups.get(getRepresentedPosition()-1-OnTopWindowData.dictionary_usc_buildings.size()-1);
				
				g.setColor( ( (highlightContext.length() > 0 && usc_t.name.toLowerCase().contains(highlightContext)) ? Color.RED : Color.WHITE) );
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 22));
				g.drawString(usc_t.name, this.xTL+5, this.yTL+32);
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 13));
				g.drawString(usc_t.getDescription()[0], this.xTL+5, this.yBR-20);
				g.drawString(usc_t.getDescription()[1], this.xTL+5, this.yBR-5);
				
				g.setColor(GameData.color_Material);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 22));
				String kosten_t = ""+usc_t.kosten;
				g.drawString(kosten_t, this.xBR-g.getFontMetrics().stringWidth(kosten_t)-5, this.yTL+30);
				
			}else if(getRepresentedPosition() == OnTopWindowData.dictionary_usc_buildings.size()+1+OnTopWindowData.dictionary_usc_troups.size()+1) {
				
				g.setColor(OnTopWindowData.dictionary_sectionTitelColor);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 30));
				String text = "Upgrades";
				int textWidth = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-textWidth/2, this.yBR-15);
				
			}else if(getRepresentedPosition() < OnTopWindowData.dictionary_usc_buildings.size()+1+OnTopWindowData.dictionary_usc_troups.size()+1+OnTopWindowData.dictionary_udc_upgrades.size()+1) {
				
				//UPGRADE
				UpgradeDataContainer udc = OnTopWindowData.dictionary_udc_upgrades.get(getRepresentedPosition()-1-OnTopWindowData.dictionary_usc_buildings.size()-1-OnTopWindowData.dictionary_usc_troups.size()-1);
				
				g.setColor( ( (highlightContext.length() > 0 && udc.upgradeType.toString().toLowerCase().contains(highlightContext)) ? Color.RED : Color.WHITE) );
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 22));
				g.drawString(udc.upgradeType.toString(), this.xTL+5, this.yTL+32);
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 13));
				g.drawString(udc.getDescription()[1], this.xTL+5, this.yBR-20);
				g.drawString(udc.getDescription()[3], this.xTL+5, this.yBR-5);
				
				g.setColor(GameData.color_Research);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 22));
				String kosten_u = ""+udc.researchCost;
				g.drawString(kosten_u, this.xBR-g.getFontMetrics().stringWidth(kosten_u)-5, this.yTL+30);
				
			}else if(getRepresentedPosition() == OnTopWindowData.dictionary_usc_buildings.size()+1+OnTopWindowData.dictionary_usc_troups.size()+1+OnTopWindowData.dictionary_udc_upgrades.size()+1) {
				
				g.setColor(OnTopWindowData.dictionary_sectionTitelColor);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 30));
				String text = "Map Fields";
				int textWidth = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-textWidth/2, this.yBR-15);
				
			}else if(getRepresentedPosition() < OnTopWindowData.dictionary_usc_buildings.size()+1+OnTopWindowData.dictionary_usc_troups.size()+1+OnTopWindowData.dictionary_udc_upgrades.size()+1+OnTopWindowData.dictionary_fd_fields.size()+1) {
				
				//FIELD DATA
				FieldData fd = OnTopWindowData.dictionary_fd_fields.get(getRepresentedPosition()-1-OnTopWindowData.dictionary_usc_buildings.size()-1-OnTopWindowData.dictionary_usc_troups.size()-1-OnTopWindowData.dictionary_udc_upgrades.size()-1);
				
				g.setColor( ( (highlightContext.length() > 0 && fd.titel.toLowerCase().contains(highlightContext)) ? Color.RED : Color.WHITE) );
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 22));
				g.drawString(fd.titel, this.xTL+5, this.yTL+32);
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 13));
				g.drawString(fd.getDescription()[0], this.xTL+5, this.yBR-20);
				g.drawString(fd.getDescription()[1], this.xTL+5, this.yBR-5);
				
			}else if(getRepresentedPosition() == OnTopWindowData.dictionary_usc_buildings.size()+1+OnTopWindowData.dictionary_usc_troups.size()+1+OnTopWindowData.dictionary_udc_upgrades.size()+1+OnTopWindowData.dictionary_fd_fields.size()+1) {
				
				g.setColor(OnTopWindowData.dictionary_sectionTitelColor);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 30));
				String text = "Features";
				int textWidth = g.getFontMetrics().stringWidth(text);
				g.drawString(text, this.xTL+(this.xBR-this.xTL)/2-textWidth/2, this.yBR-15);
				
			}else {
				
				//DICTIONARY INFO
				DictonaryInfoDescription did = OnTopWindowData.dictionary_did_infos.get(getRepresentedPosition()-1-OnTopWindowData.dictionary_usc_buildings.size()-1-OnTopWindowData.dictionary_usc_troups.size()-1-OnTopWindowData.dictionary_udc_upgrades.size()-1-OnTopWindowData.dictionary_fd_fields.size()-1);
				
				g.setColor( ( (highlightContext.length() > 0 && did.titel.toLowerCase().contains(highlightContext)) ? Color.RED : Color.WHITE) );
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 22));
				g.drawString(did.titel, this.xTL+5, this.yTL+32);
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 13));
				g.drawString(did.getDescription().substring(0, (did.getDescription().length() <= 93 ? did.getDescription().length() : 93) )+"...", this.xTL+5, this.yBR-13);
				
			}
			
		}
		
	}
	
}
