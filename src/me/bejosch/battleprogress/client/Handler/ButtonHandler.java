package me.bejosch.battleprogress.client.Handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.CreateMapData;
import me.bejosch.battleprogress.client.Data.FileData;
import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Game.OverAllManager;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Window.Buttons.Buttons;
import me.bejosch.battleprogress.client.Window.ScrollPanes.ScrollPanes;
import me.bejosch.battleprogress.client.Window.TextAreas.TextAreas;
import me.bejosch.battleprogress.client.Window.TextFields.TextFields;

public class ButtonHandler implements ActionListener {

	public static boolean reloadCooldown = false;
	
	@Override
	public void actionPerformed(ActionEvent clicked) {
		
		//ANTI SPAM FUNCTION
		if(StandardData.antiSpam == true) {
			return;
		}
		//OTW BLOCK
		if(OnTopWindowData.onTopWindow != null) {
			return;
		}
					
//==================================================================================================================
		if(clicked.getSource() == Buttons.button_StartGame) {
			
			GameHandler.requestStartGame();
		
//==================================================================================================================
		}else if(clicked.getSource() == Buttons.button_QuitEditor) {
			
			CreateMapHandler.hideHUD();
			OverAllManager.switchTo_Menu_HauptMenu(true);
			
//==================================================================================================================
		}else if(clicked.getSource() == Buttons.button_NextField) {
			
			CreateMapData.currentFieldBuild = Funktions.getNextFieldTypeFromPreviousFieldType(CreateMapData.currentFieldBuild);
			
//==================================================================================================================
		}else if(clicked.getSource() == Buttons.button_LaterField) {
			
			CreateMapData.currentFieldBuild = Funktions.getPreviousFieldTypeFromPreviousFieldType(CreateMapData.currentFieldBuild);
			
//==================================================================================================================
		}else if(clicked.getSource() == Buttons.button_NextMap) {
			//+ MAP
			
			if(GameData.gameIsRunning == true) { return; }
			
			LobbyHandler.loadMapFromFile(true);
			Funktions.activateAntiSpam(1000);
		
//==================================================================================================================
		}else if(clicked.getSource() == Buttons.button_LaterMap) {
			//- MAP
			
			if(GameData.gameIsRunning == true) { return; }
			
			LobbyHandler.loadMapFromFile(false);
			Funktions.activateAntiSpam(1000);
			
//==================================================================================================================
		}else if(clicked.getSource() == Buttons.button_NextTeam) {
			//+ TEAM
			
			if(GameData.gameIsRunning == true) { return; }
			
			LobbyHandler.updateTeams(true);
			Funktions.activateAntiSpam(1000);
			
//==================================================================================================================
		}else if(clicked.getSource() == Buttons.button_LaterTeam) {
			//- TEAM
			
			if(GameData.gameIsRunning == true) { return; }
			
			LobbyHandler.updateTeams(false);
			Funktions.activateAntiSpam(1000);
			
//==================================================================================================================
		}else if(clicked.getSource() == Buttons.button_DeleteMap) {
			
			CreateMapData.showHUD = false;
			Buttons.hideButton(Buttons.button_NextField);
			Buttons.hideButton(Buttons.button_LaterField);
			Buttons.hideButton(Buttons.button_DeleteMap);
			Buttons.hideButton(Buttons.button_ClearMap);
			Buttons.hideButton(Buttons.button_LoadMap);
			Buttons.hideButton(Buttons.button_SaveMap);
			
			CreateMapData.overlayedInput = true;
			TextAreas.textArea_MapListDisplay.setText("MapList: "+FileHandler.readOutData(FileData.file_Maps, "MapList").replace("[", "").replace("]", ""));
			TextAreas.textArea_MapListDisplay.setVisible(true);
			ScrollPanes.showScrollPane(ScrollPanes.scrollPane_MapListDisplay, 14, CreateMapData.overlay_midX-(700/2), 670, 700, 35);
			TextFields.showTextField(TextFields.textField_createMapName, "", 14, CreateMapData.overlay_x+20, CreateMapData.overlay_y+40, 300, 25);
			Buttons.showButton(Buttons.button_ConfirmDeleteMap, "Delete", 14, CreateMapData.overlay_x+20, CreateMapData.overlay_y+90, 120, 25); //DELETE
			
//==================================================================================================================
		}else if(clicked.getSource() == Buttons.button_ClearMap) {
			
			CreateMapData.showHUD = false;
			Buttons.hideButton(Buttons.button_NextField);
			Buttons.hideButton(Buttons.button_LaterField);
			Buttons.hideButton(Buttons.button_DeleteMap);
			Buttons.hideButton(Buttons.button_ClearMap);
			Buttons.hideButton(Buttons.button_LoadMap);
			Buttons.hideButton(Buttons.button_SaveMap);
			
			CreateMapData.overlayedInput = true;
			CreateMapData.clearingMap = true; //SPECIAL 
			
			Buttons.showButton(Buttons.button_ConfirmClearMap, "Clear", 14, CreateMapData.overlay_x+20, CreateMapData.overlay_y+90, 120, 25); //SAVE
			
//==================================================================================================================
		}else if(clicked.getSource() == Buttons.button_LoadMap) {
			
			CreateMapData.showHUD = false;
			Buttons.hideButton(Buttons.button_NextField);
			Buttons.hideButton(Buttons.button_LaterField);
			Buttons.hideButton(Buttons.button_DeleteMap);
			Buttons.hideButton(Buttons.button_ClearMap);
			Buttons.hideButton(Buttons.button_LoadMap);
			Buttons.hideButton(Buttons.button_SaveMap);
			
			CreateMapData.overlayedInput = true;
			CreateMapData.loadingMap = true; //SPECIAL 
			TextAreas.textArea_MapListDisplay.setText("MapList: "+FileHandler.readOutData(FileData.file_Maps, "MapList").replace("[", "").replace("]", ""));
			TextAreas.textArea_MapListDisplay.setVisible(true);
			ScrollPanes.showScrollPane(ScrollPanes.scrollPane_MapListDisplay, 14, CreateMapData.overlay_midX-(700/2), 670, 700, 35);
			TextFields.showTextField(TextFields.textField_createMapName, "", 14, CreateMapData.overlay_x+20, CreateMapData.overlay_y+40, 300, 25);
			Buttons.showButton(Buttons.button_ConfirmLoadMap, "Load", 14, CreateMapData.overlay_x+20, CreateMapData.overlay_y+90, 120, 25); //LOAD
			
//==================================================================================================================
		}else if(clicked.getSource() == Buttons.button_SaveMap) {
			
			CreateMapData.showHUD = false;
			Buttons.hideButton(Buttons.button_NextField);
			Buttons.hideButton(Buttons.button_LaterField);
			Buttons.hideButton(Buttons.button_DeleteMap);
			Buttons.hideButton(Buttons.button_ClearMap);
			Buttons.hideButton(Buttons.button_LoadMap);
			Buttons.hideButton(Buttons.button_SaveMap);
			
			CreateMapData.overlayedInput = true;
			TextFields.showTextField(TextFields.textField_createMapName, "", 14, CreateMapData.overlay_x+20, CreateMapData.overlay_y+40, 300, 25);
			Buttons.showButton(Buttons.button_ConfirmSaveMap, "Save", 14, CreateMapData.overlay_x+20, CreateMapData.overlay_y+90, 120, 25); //SAVE
			
//==================================================================================================================
		}else if(clicked.getSource() == Buttons.button_ConfirmDeleteMap) {
			
			String NameInput = TextFields.textField_createMapName.getText();
			
			//
			if(NameInput.length() > 0) {

				try{
					Integer.parseInt(NameInput.substring(0, 1));
					//1 IST NE ZAHL - NICHT GUT
					
				}catch(NumberFormatException error) {
					//1 KEINE ZAHL - ALLES GUT
					if(NameInput.length() > 3 && NameInput.length() < 16 && !NameInput.contains(" ")) {
						//ALLES WEITERE GUT
						if(!NameInput.equals("Standard")) {
							boolean worked = CreateMapHandler.deleteMap(NameInput);
							CreateMapData.overlayInputFinished = true;
							if(worked == true) {
								CreateMapData.whySaveEnded = "Map deleted successfully!";
							}else {
								CreateMapData.whySaveEnded = "Map name not found!";
							}
						}else {
							CreateMapData.overlayInputFinished = true;
							CreateMapData.whySaveEnded = "This map can't be deleted!";
						}
						
						TextFields.hideTextField(TextFields.textField_createMapName);
						Buttons.hideButton(Buttons.button_ConfirmDeleteMap);
						new Timer().schedule(new TimerTask() {
							@Override
							public void run() {
								CreateMapData.overlayedInput = false;
								OverAllManager.switchTo_Menu_CreateMap();
							}
						}, 1000*1+500);
					}else if(NameInput.contains(" ")) {
						//FEHLERHAFT
					}else {
						//FEHLERHAFT
					}
				}
				
			}else {
				//FEHLERHAFT
			}
			//
			
//==================================================================================================================
		}else if(clicked.getSource() == Buttons.button_ConfirmClearMap) {
			
			boolean worked = CreateMapHandler.fillFieldList();
			CreateMapData.overlayInputFinished = true;
			if(worked == true) {
				CreateMapData.whySaveEnded = "Map cleared successfully!";
			}else {
				CreateMapData.whySaveEnded = "Something went wrong!";
			}
			
			Buttons.hideButton(Buttons.button_ConfirmClearMap);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					CreateMapData.overlayedInput = false;
					OverAllManager.switchTo_Menu_CreateMap();
				}
			}, 1000*1+500);
			
//==================================================================================================================
		}else if(clicked.getSource() == Buttons.button_ConfirmLoadMap) {
			
			String NameInput = TextFields.textField_createMapName.getText();
			
			//
			if(NameInput.length() > 0) {

				try{
					Integer.parseInt(NameInput.substring(0, 1));
					//1 IST NE ZAHL - NICHT GUT
					
				}catch(NumberFormatException error) {
					//1 KEINE ZAHL - ALLES GUT
					if(NameInput.length() > 3 && NameInput.length() < 16 && !NameInput.contains(" ")) {
						//ALLES WEITERE GUT
						boolean worked = CreateMapHandler.loadMap(NameInput);
						CreateMapData.overlayInputFinished = true;
						if(worked == true) {
							CreateMapData.whySaveEnded = "Map loaded successfully!";
						}else {
							CreateMapData.whySaveEnded = "Map name not found!";
						}
						
						TextFields.hideTextField(TextFields.textField_createMapName);
						Buttons.hideButton(Buttons.button_ConfirmLoadMap);
						new Timer().schedule(new TimerTask() {
							@Override
							public void run() {
								CreateMapData.overlayedInput = false;
								OverAllManager.switchTo_Menu_CreateMap();
							}
						}, 1000*1+500);
					}else if(NameInput.contains(" ")) {
						//FEHLERHAFT
					}else {
						//FEHLERHAFT
					}
				}
				
			}else {
				//FEHLERHAFT
			}
			//
			
//==================================================================================================================
		}else if(clicked.getSource() == Buttons.button_ConfirmSaveMap) {
			
			String NameInput = TextFields.textField_createMapName.getText();
			
			//
			if(NameInput.length() > 0) {

				try{
					Integer.parseInt(NameInput.substring(0, 1));
					//1 IST NE ZAHL - NICHT GUT
					
				}catch(NumberFormatException error) {
					//1 KEINE ZAHL - ALLES GUT
					if(NameInput.length() > 3 && NameInput.length() < 16 && !NameInput.contains(" ")) {
						//ALLES WEITERE GUT
						String worked = CreateMapHandler.saveNewMap(NameInput);
						CreateMapData.overlayInputFinished = true;
						if(worked.equalsIgnoreCase("worked")) { //true
							CreateMapData.whySaveEnded = "Map saved successfully!";
						}else {
							CreateMapData.whySaveEnded = worked;
						}
						ScrollPanes.hideAllScrollPanes();
						TextFields.hideTextField(TextFields.textField_createMapName);
						Buttons.hideButton(Buttons.button_ConfirmSaveMap);
						new Timer().schedule(new TimerTask() {
							@Override
							public void run() {
								CreateMapData.overlayedInput = false;
								OverAllManager.switchTo_Menu_CreateMap();
							}
						}, 1000*1+500);
					}else if(NameInput.contains(" ")) {
						//FEHLERHAFT
					}else {
						//FEHLERHAFT
					}
				}
				
			}else {
				//FEHLERHAFT
			}
			//
		}
		
	}

}
