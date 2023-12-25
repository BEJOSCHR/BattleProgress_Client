package me.bejosch.battleprogress.client.Objects.OnTopWindow.Dictionary;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Comparator;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Data.Game.ResearchData;
import me.bejosch.battleprogress.client.Data.Game.UnitData;
import me.bejosch.battleprogress.client.Handler.MovementHandler;
import me.bejosch.battleprogress.client.Objects.DictonaryInfoDescription;
import me.bejosch.battleprogress.client.Objects.FieldData;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;
import me.bejosch.battleprogress.client.Objects.Research.UpgradeDataContainer;
import me.bejosch.battleprogress.client.Window.TextFields.TextFields;

public class OnTopWindow_Dictionary extends OnTopWindow {

	
	
	public OnTopWindow_Dictionary() {
		super("OTW_Dictionary", OnTopWindowData.dictionary_width, OnTopWindowData.dictionary_height);
		
		//CHECK FOR FIRST INIT
		if(OnTopWindowData.dictionary_usc_buildings.isEmpty()) {
			loadStats();
			sortStats();
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
		TextFields.showTextField(TextFields.textField_Dictionary, "", 16, this.getX()+this.width-120-OnTopWindowData.friendRequests_smallButtonMaße-30, this.getY()+10, 120, OnTopWindowData.friendRequests_smallButtonMaße);
		//TextFields.textField_Dictionary.setToolTipText("Highlight search");
		TextFields.textField_Dictionary.requestFocus();
		
	}
	
	@Override
	public void performClose() {
		
		MovementHandler.allowMovement();
		TextFields.hideTextField(TextFields.textField_Dictionary);
		WindowData.Frame.requestFocus();
		
	}
	
	@Override
	public void onMouseWheelTurned(boolean scrollUp) {
		
		if(scrollUp == true) {
			OnTopWindowData.dictionary_scrollPos -= 2;
		}else {
			OnTopWindowData.dictionary_scrollPos += 2;
		}
		
		if(OnTopWindowData.dictionary_scrollPos < 0) {
			OnTopWindowData.dictionary_scrollPos = 0;
		}else if(OnTopWindowData.dictionary_scrollPos > getTotalSections()-OnTopWindowData.dictionary_sectionCount) {
			OnTopWindowData.dictionary_scrollPos = getTotalSections()-OnTopWindowData.dictionary_sectionCount;
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		//BACKGROUND
		g.setColor(Color.DARK_GRAY);
		g.fillRect(this.getX(), this.getY(), this.width, this.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(this.getX(), this.getY(), this.width, this.height);
		
		//TITEL
		g.setColor(OnTopWindowData.dictionary_titelColor);
		g.setFont(new Font("Arial", Font.CENTER_BASELINE, 34));
		g.drawString("Dictionary", this.getX()+OnTopWindowData.dictionary_border, this.getY()+OnTopWindowData.dictionary_titelSectionHeight-12);
		g.setColor(Color.WHITE);
		g.drawLine(getX(), getY()+OnTopWindowData.dictionary_titelSectionHeight, getX()+this.width, getY()+OnTopWindowData.dictionary_titelSectionHeight);
		
		//SECTIONS VIA MAAs
		
		
	}
	
	public static int getTotalSections() {
		return OnTopWindowData.dictionary_usc_buildings.size()+OnTopWindowData.dictionary_usc_troups.size()
			   +OnTopWindowData.dictionary_udc_upgrades.size()+OnTopWindowData.dictionary_did_infos.size()
			   +OnTopWindowData.dictionary_fd_fields.size()+5;
	}
	
	//FIRST INIT METHODS:
	
	private void loadStats() {
		
		//UNITS
		for(UnitStatsContainer usc : UnitData.units) {
			if(usc.moveDistance < 0) {
				//ONLY BUILDINGS HAVE MOVE = -1
				OnTopWindowData.dictionary_usc_buildings.add(usc);
			}else {
				//TROUP
				OnTopWindowData.dictionary_usc_troups.add(usc);
			}
		}
		
		//UPGRADES
		for(UpgradeDataContainer udc : ResearchData.upgradeDataContainer) {
			OnTopWindowData.dictionary_udc_upgrades.add(udc);
		}
		
		//FIELD DATA
		for(FieldData fd : GameData.fieldData) {
			OnTopWindowData.dictionary_fd_fields.add(fd);
		}
		
		//DICTIONARY INFO DESCRIPTION
		for(DictonaryInfoDescription did : GameData.dictonaryInfoDescriptions) {
			OnTopWindowData.dictionary_did_infos.add(did);
		}
		
	}
	
	private void sortStats() {
		
		Comparator<UnitStatsContainer> usc_comparator = new Comparator<UnitStatsContainer>() {
			@Override
			public int compare(UnitStatsContainer usc1, UnitStatsContainer usc2) {
				return usc1.name.compareToIgnoreCase(usc2.name);
			}
		};
		
		OnTopWindowData.dictionary_usc_buildings.sort(usc_comparator);
		OnTopWindowData.dictionary_usc_troups.sort(usc_comparator);
		
		Comparator<UpgradeDataContainer> udc_comparator = new Comparator<UpgradeDataContainer>() {
			@Override
			public int compare(UpgradeDataContainer udc1, UpgradeDataContainer udc2) {
				return udc1.upgradeType.toString().compareToIgnoreCase(udc2.upgradeType.toString());
			}
		};
		
		OnTopWindowData.dictionary_udc_upgrades.sort(udc_comparator);
		
		Comparator<FieldData> fd_comparator = new Comparator<FieldData>() {
			@Override
			public int compare(FieldData fd1, FieldData fd2) {
				return fd1.titel.compareToIgnoreCase(fd2.titel);
			}
		};
		
		OnTopWindowData.dictionary_fd_fields.sort(fd_comparator);
		
		Comparator<DictonaryInfoDescription> did_comparator = new Comparator<DictonaryInfoDescription>() {
			@Override
			public int compare(DictonaryInfoDescription did1, DictonaryInfoDescription did2) {
				return did1.titel.compareToIgnoreCase(did2.titel);
			}
		};
		
		OnTopWindowData.dictionary_did_infos.sort(did_comparator);
		
	}
	
}
