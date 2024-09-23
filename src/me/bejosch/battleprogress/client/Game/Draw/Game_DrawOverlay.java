package me.bejosch.battleprogress.client.Game.Draw;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ConcurrentModificationException;

import me.bejosch.battleprogress.client.Data.ProfilData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.EconomicData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Data.Game.ResearchData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Enum.AnimationType;
import me.bejosch.battleprogress.client.Enum.BuildMenuType;
import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Game.TimeManager;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Game.Handler.Game_FieldDataHandler;
import me.bejosch.battleprogress.client.Game.Handler.Game_RoundHandler;
import me.bejosch.battleprogress.client.Objects.Animations.Animation;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_MovingCircleDisplay;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Buildings.Building_Headquarter;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_InfoMessages;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.Checkbox.MouseActionArea_Checkbox;
import me.bejosch.battleprogress.client.Objects.Tasks.BuildMenuTasks.BuildMenuTask;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;
import me.bejosch.battleprogress.client.Window.Label;
import me.bejosch.battleprogress.client.Window.Animations.AnimationDisplay;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Game_DrawOverlay {

//==========================================================================================================
	/**
	 * The methode, called by the Label for display this part
	 * This part only draws the overlay
	 */
	public static void drawOverlay(Graphics g) {
		
		//FieldMessage
		draw_FieldMessage(g);
		//Minimap
		try{
			if( ((MouseActionArea_Checkbox) GameHandler.getMouseActionAreaByName("Checkbox_MiniMap_ShowAll")).getCurrentState() == true ) {
				draw_MiniMap_Fields(g);
				draw_MiniMap_Buildings(g);
				draw_MiniMap_Troups(g);
				draw_MiniMap_CurrentView(g);
			}
		}catch(NullPointerException error) {} //NULLPOINTER HAPPENS AT THE START BECAUSE DRAW COME BEFOR GAME INITIALISE
		//Headline Info
		draw_HeadlineInfo(g);
		//Round change Info
		draw_RoundChangeInfo(g);
		//Notifications
		draw_Notifications(g);
		//ChatHandler
		draw_ChatHandler(g);
		//ActionBar
		draw_Actionbar(g);
		//ReadyButton
		draw_ReadyButton(g);
		//BuildMenu
		draw_BuildMenu(g);
		//ECONOMIC INFO
		draw_EconomicDisplay(g);
		//MouseActionAreas
		draw_MouseActionAreas(g, false);
		
	}
	
	
	//TODO ECONOMIC DISPLAY
	
//==========================================================================================================
	/**
	 * Draws the Economic Infos like Ressource and Energy
	 */
	public static void draw_EconomicDisplay(Graphics g) {
		
		int x = 0, y = 0;
		if( GameHandler.getMouseActionAreaByName("Checkbox_MiniMap_ShowAll") == null || ((MouseActionArea_Checkbox) GameHandler.getMouseActionAreaByName("Checkbox_MiniMap_ShowAll")).getCurrentState() == true ) {
			//MINIMAP SHOWN
			x = WindowData.FrameWidth-GameData.ecoDisp_width-GameData.ecoDisp_rightBorder  -StandardData.maße-StandardData.rahmen;
		}else {
			//NO MINIMAP SHOWN
			x = WindowData.FrameWidth-GameData.ecoDisp_width-GameData.ecoDisp_rightBorder;
		}
		y = WindowData.FrameHeight-GameData.ecoDisp_totalDownBorder;
		
		//This makes the display value only go up then the animation finished - the real amount got already added to value on eco round end end (close to the animation start as well)
		int holdbackMaterial = 0, holdbackEnergy = 0, holdbackRP = 0;
		for(Animation a : AnimationDisplay.getRunningAnimationOfType(AnimationType.MovingCircleDisplay)) {
			Animation_MovingCircleDisplay amcd = (Animation_MovingCircleDisplay) a;
			switch(amcd.displayType) {
			case Material:
				holdbackMaterial += amcd.value;
				continue;
			case Energy:
				holdbackEnergy += amcd.value;
				continue;
			case Research:
				holdbackRP += amcd.value;
				continue;
			default:
				continue;
			}
		}
		
		//MATERIAL
		int displayMaterials = EconomicData.materialAmount - holdbackMaterial;
		g.setColor(Color.DARK_GRAY);
		g.fillRoundRect(x, y, GameData.ecoDisp_width, GameData.ecoDisp_height, GameData.ecoDisp_cornerRound, GameData.ecoDisp_cornerRound);
		g.setColor(GameData.color_Material);
		g.drawRoundRect(x, y, GameData.ecoDisp_width, GameData.ecoDisp_height, GameData.ecoDisp_cornerRound, GameData.ecoDisp_cornerRound);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString(""+displayMaterials, x+GameData.ecoDisp_insideLeftBorder, y+GameData.ecoDisp_height-GameData.ecoDisp_insideDownBorder);
		y += GameData.ecoDisp_distanceTotalSegment;
		
		//ENERGY
		int displayEnergy = EconomicData.energyAmount - holdbackEnergy;
		g.setColor(Color.DARK_GRAY);
		g.fillRoundRect(x, y, GameData.ecoDisp_width, GameData.ecoDisp_height, GameData.ecoDisp_cornerRound, GameData.ecoDisp_cornerRound);
		g.setColor(GameData.color_Energy);
		g.drawRoundRect(x, y, GameData.ecoDisp_width, GameData.ecoDisp_height, GameData.ecoDisp_cornerRound, GameData.ecoDisp_cornerRound);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString(""+displayEnergy, x+GameData.ecoDisp_insideLeftBorder, y+GameData.ecoDisp_height-GameData.ecoDisp_insideDownBorder);
		y += GameData.ecoDisp_distanceTotalSegment;
		
		//RESEARCH
		int displayRP = ResearchData.researchPoints - holdbackRP;
		g.setColor(Color.DARK_GRAY);
		g.fillRoundRect(x, y, GameData.ecoDisp_width, GameData.ecoDisp_height, GameData.ecoDisp_cornerRound, GameData.ecoDisp_cornerRound);
		g.setColor(GameData.color_Research);
		g.drawRoundRect(x, y, GameData.ecoDisp_width, GameData.ecoDisp_height, GameData.ecoDisp_cornerRound, GameData.ecoDisp_cornerRound);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString(""+displayRP, x+GameData.ecoDisp_insideLeftBorder, y+GameData.ecoDisp_height-GameData.ecoDisp_insideDownBorder);
		y += GameData.ecoDisp_distanceTotalSegment;
		
	}
	
	//TODO HEADLINE INFO DISPLAY
	
//==========================================================================================================
	/**
	 * Draws the info in the top Headline
	 */
	public static void draw_HeadlineInfo(Graphics g) {
		
		// GAMEID ; FPS ; SPIELDAUER
		
		int gameHeaderWidth = 135, fpsWidth = 45, durationWidth = 55;
		int totalWidth = gameHeaderWidth+fpsWidth+durationWidth;
		int distanceBorder = 5;
		
		int totalHeight = 20, textHeight = totalHeight-6;
		
		//BACKGROUND
		g.setColor(Color.DARK_GRAY);
		g.fillRect(WindowData.FrameWidth-totalWidth, 0-WindowData.rahmen, totalWidth+WindowData.rahmen, totalHeight);
		
		g.setColor(Color.WHITE);
		g.drawRect(WindowData.FrameWidth-totalWidth, 0-WindowData.rahmen, totalWidth+WindowData.rahmen, totalHeight);
		
		g.drawLine(WindowData.FrameWidth-totalWidth+gameHeaderWidth, 0-WindowData.rahmen, WindowData.FrameWidth-totalWidth+gameHeaderWidth, totalHeight);
		g.drawLine(WindowData.FrameWidth-totalWidth+gameHeaderWidth+fpsWidth, 0-WindowData.rahmen, WindowData.FrameWidth-totalWidth+gameHeaderWidth+fpsWidth, totalHeight);
		
		//CONTENT
		g.setFont(new Font("Arial", Font.BOLD, 10));
		
		String gameHeader = GameData.gameMode.toString()+" ("+GameData.gameID+")";
		g.drawString(gameHeader, WindowData.FrameWidth-totalWidth+distanceBorder, textHeight);
		
		String fps = Label.getCurrentFPSValue()+" FPS";
		g.drawString(fps, WindowData.FrameWidth-totalWidth+gameHeaderWidth+distanceBorder, textHeight);
		
		String duration = ""+Funktions.getDoubleWritenNumber(TimeManager.gameDuration_Hour)+":"+Funktions.getDoubleWritenNumber(TimeManager.gameDuration_Min)+":"+Funktions.getDoubleWritenNumber(TimeManager.gameDuration_Sec);
		g.drawString(duration, WindowData.FrameWidth-totalWidth+gameHeaderWidth+fpsWidth+distanceBorder, textHeight);
		
		g.setColor(Color.RED);
		String alphaMessage = "Alpha version! Please report all bugs and give some feedback, thanks!";
		int messageWidth = g.getFontMetrics().stringWidth(alphaMessage);
		g.drawString(alphaMessage, WindowData.FrameWidth-totalWidth-messageWidth-10, textHeight);
		
	}
	
//==========================================================================================================
	/**
	 * Draws the info in the top Headline
	 */
	public static void draw_RoundChangeInfo(Graphics g) {
		
		if(Game_RoundHandler.blockedInput() == true) {
			
			int width = 400, height = 50, X = ((WindowData.FrameWidth+WindowData.rahmen*2)/2)-(width/2), Y = 120;
			int skipButton_distanceToRight = 50, skipButton_width = 40, skipButton_topBorder = 13, skipButton_height = height-(skipButton_topBorder*2);
			int skipButton_X = X+(width-skipButton_distanceToRight);
			int skipButton_Y = Y+skipButton_topBorder;
			
			g.setColor(Color.DARK_GRAY);
			g.fillRect(X, Y, width, height);
			g.setColor(Color.WHITE);
			g.drawRect(X, Y, width, height);
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.BOLD, 17));
			int infoWidth = g.getFontMetrics().stringWidth(RoundData.roundStatusInfo);
			g.drawString(RoundData.roundStatusInfo, X+(width/2)-(infoWidth/2), Y+22);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 12));
			String round = "Round "+RoundData.currentRound;
			int roundWidth = g.getFontMetrics().stringWidth(round);
			g.drawString(round, X+(width/2)-(roundWidth/2), Y+40);
			if(RoundData.currentExecuteTask != null) {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(skipButton_X, skipButton_Y, skipButton_width, skipButton_height);
				g.setColor(Color.BLACK);
				g.drawString("SKIP", skipButton_X+8, skipButton_Y+skipButton_height-8);
			}
			
			//CURRENT EXECUTE PLAYER
			if(SpielModus.isGameModus1v1()) {
				int border = 20, distanceBetweenNames = (width/2);
				g.setColor(Color.DARK_GRAY);
				g.fillRect(X, Y+1+height, width, 25);
				for(int durchlauf = 0 ; durchlauf < 2 ; durchlauf++) {
					int nameX = X+border+durchlauf*distanceBetweenNames+(distanceBetweenNames/4), nameY = Y+1+height+16;
					int playerID = GameData.playingPlayer[durchlauf].getID();
					String playerName = GameData.playingPlayer[durchlauf].getName();
					if(RoundData.currentActivePlayer != null) {
						if(RoundData.currentlyPerformingTasks == true && playerID == RoundData.currentActivePlayer.getID()) {
							g.setColor(Color.ORANGE);
						}else { g.setColor(Color.WHITE); }
					}else if(RoundData.currentlyPerformingTasks == false && Game_RoundHandler.checkPlayerReadyByNumber(durchlauf+1)) {
						//NO TASKS EXECUTED YET - SO DISPLAY WHO IS READY
						g.setColor(Color.GREEN);
					}else { g.setColor(Color.WHITE); }
					g.setFont(new Font("Arial", Font.BOLD, 11));
					g.drawString(playerName, nameX, nameY);
				}
			}else {
				int border = 10, distanceBetweenNames = (width/4);
				g.setColor(Color.DARK_GRAY);
				g.fillRect(X, Y+1+height, width, 25);
				for(int durchlauf = 0 ; durchlauf < 4 ; durchlauf++) {
					int nameX = X+border+durchlauf*distanceBetweenNames, nameY = Y+1+height+16;
					int playerID = GameData.playingPlayer[durchlauf].getID();
					String playerName = GameData.playingPlayer[durchlauf].getName();
					if(RoundData.currentActivePlayer != null) {
						if(RoundData.currentlyPerformingTasks == true && playerID == RoundData.currentActivePlayer.getID()) {
							g.setColor(Color.ORANGE);
						}else { g.setColor(Color.WHITE); }
					}else if(RoundData.currentlyPerformingTasks == false && Game_RoundHandler.checkPlayerReadyByNumber(durchlauf+1)) {
						//NO TASKS EXECUTED YET - SO DISPLAY WHO IS READY
						g.setColor(Color.GREEN);
					}else { g.setColor(Color.WHITE); }
					g.setFont(new Font("Arial", Font.BOLD, 11));
					g.drawString(playerName, nameX, nameY);
				}
			}
			
			
		}
		
	}
	
	//TODO CHAT HANDLER
	
//==========================================================================================================
	/**
	 * Draws the ChatButton (for hide/show)
	 */
	public static void draw_ChatHandler(Graphics g) {
		
		int buttonX = 0, buttonY = 0;
		
		if(GameData.chatIsShown == true) {
			//SHOWN
			buttonX = GameData.chatX_show+GameData.chatbutton_showXausgleich; //AUSGLEICH WEIL ZUWEIT LINKS
			buttonY = GameData.chatY_show+GameData.chat_height;
			
			//DRAW BACKGROUND
			g.setColor(Color.DARK_GRAY);
			g.fillRect(GameData.chatX_show, GameData.chatY_show-5, GameData.chat_width, 30+5);
			
		}else {
			//HIDDEN
			buttonX = GameData.chatX_hide;
			buttonY = GameData.chatY_hide+GameData.chat_height;
		}
		
		g.setColor(Color.DARK_GRAY);
		g.fillRoundRect(buttonX-GameData.chatButton_width, buttonY-GameData.chatButton_height, GameData.chatButton_roundPartWidth, GameData.chatButton_height, 10, 10);
		g.fillRect(buttonX-GameData.chatButton_width+(GameData.chatButton_roundPartWidth/2), buttonY-GameData.chatButton_height, GameData.chatButton_width-(GameData.chatButton_roundPartWidth/2), GameData.chatButton_height);
		
		int midX = buttonX-( (int) (GameData.chatButton_width-((int) GameData.chatButton_roundPartWidth/2) )/2 ) -14 , midY = buttonY - (int) (GameData.chatButton_height/2);
		g.setColor(Color.WHITE);
		if(GameData.chatIsShown == true) {
			//>>
			g.drawLine(midX, midY, midX-8, midY-8); g.drawLine(midX, midY, midX-8, midY+8);
			g.drawLine(midX+5, midY, midX-8+5, midY-8); g.drawLine(midX+5, midY, midX-8+5, midY+8);
		}else {
			//<<
			g.drawLine(midX-5, midY, midX+8-5, midY-8); g.drawLine(midX-5, midY, midX+8-5, midY+8);
			g.drawLine(midX, midY, midX+8, midY-8); g.drawLine(midX, midY, midX+8, midY+8);
		}
		
	}
	
	
	//TODO NOTIFICATIONS
	
//==========================================================================================================
	/**
	 * Draws the Notifivations of the Game at the TopRight corner
	 */
	public static void draw_Notifications(Graphics g) {
		
		int durchlauf = 0, extraSpace = 0;
		
		try{
			for(InfoMessage infoMessage : GameData.notificationList) {
				infoMessage.draw(g, GameData.startX, GameData.startY + (GameData.space_MAArea*durchlauf) + extraSpace );
				extraSpace += infoMessage.getHeight();
				durchlauf++;
				if(durchlauf == GameData.maxDisplayedInfoMessages-1) { break; }
			}
		}catch(ConcurrentModificationException error) {}
		
	}
	
	
	//TODO ACTION BAR
	
//==========================================================================================================
	/**
	 * Draws the ActionBar for the clicked Building/Troup
	 */
	public static void draw_Actionbar(Graphics g) {
		
		if(GameData.clickedField != null) {
			//Building/Troup test:
			Building building = GameHandler.getBuildingByCoordinates(GameData.clickedField.X, GameData.clickedField.Y);
			Troup troup = GameHandler.getTroupByCoordinates(GameData.clickedField.X, GameData.clickedField.Y);
			Image img = null; int imgMoveFactor = 0; String name = null; int textSize = 12;
			if(building != null && GameData.clickedField.visible == true) {
				//BUILDING ON THIS FIELD and visible
				//displayBox
				img = building.img;
				imgMoveFactor = Images.buildingFactor;
				name = building.name;
				textSize = building.textSize_nameActionbar;
				if(building.playerID == ProfilData.thisClient.getID()) {
					//Actionbar - ONLY IF IT IS YOURS
					g.setColor(Color.DARK_GRAY);
					g.fillRect(GameData.actionbar_X-GameData.actionbar_backgroundOverlappingSize, GameData.actionbar_Y-GameData.actionbar_backgroundOverlappingSize, (GameData.actionbar_WidthPerTask+GameData.actionbar_SpaceBetweenTask)*building.actionTasks.size()+(GameData.actionbar_backgroundOverlappingSize*2)-GameData.actionbar_SpaceBetweenTask, GameData.actionbar_Height+(GameData.actionbar_backgroundOverlappingSize*2));
					building.draw_ActionBar(g, GameData.actionbar_X, GameData.actionbar_Y);
				}
			}else if(troup != null && GameData.clickedField.visible == true) {
				//TROUP ON THIS FIELD and visible
				//displayBox
				img = troup.img;
				imgMoveFactor = Images.troupFactor;
				name = troup.name;
				textSize = troup.textSize_nameActionbar;
				if(troup.playerID == ProfilData.thisClient.getID()) {
					//Actionbar - ONLY IF IT IS YOURS
					g.setColor(Color.DARK_GRAY);
					g.fillRect(GameData.actionbar_X-GameData.actionbar_backgroundOverlappingSize, GameData.actionbar_Y-GameData.actionbar_backgroundOverlappingSize, (GameData.actionbar_WidthPerTask+GameData.actionbar_SpaceBetweenTask)*troup.actionTasks.size()+(GameData.actionbar_backgroundOverlappingSize*2)-GameData.actionbar_SpaceBetweenTask, GameData.actionbar_Height+(GameData.actionbar_backgroundOverlappingSize*2));
					troup.draw_ActionBar(g, GameData.actionbar_X, GameData.actionbar_Y);
				}
			}else {
				//NO BUILDING OR TROUP ON THIS FIELD!
				//displayBox
				img = GameData.clickedField.img;
				imgMoveFactor = 0;
				name = Game_FieldDataHandler.getFieldData(GameData.clickedField.type).titel;
			}
			//MAIN DISPLAY BOX (ActionBar)
			//1. Title box
			g.setColor(Color.DARK_GRAY);
			g.fillRect(GameData.displayBox_realX, GameData.displayBox_realY, GameData.displayBox_size, GameData.displayBox_size);
			g.setColor(Color.WHITE);
			g.drawRoundRect(GameData.displayBox_realX, GameData.displayBox_realY, GameData.displayBox_size, GameData.displayBox_size, 7, 7);
			//2. Image
			g.drawImage(img, GameData.displayBox_realX+GameData.displayBox_border + imgMoveFactor, GameData.displayBox_realY+GameData.displayBox_border, null);
			//3. Name
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, textSize)); //STD 12
			g.drawString(name, GameData.displayBox_realX+(GameData.displayBox_size/2)-(name.length()*GameData.displayBox_textYMoving), GameData.displayBox_realY+((GameData.displayBox_size/7)*6) );
			//4. Hover hint
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 9));
			g.drawString("(Click for details)", GameData.displayBox_realX+(GameData.displayBox_size/2)-37, GameData.displayBox_realY+10);
		}
		
	}
	
	
	//TODO READY BUTTON
	
//==========================================================================================================
	/**
	 * Draws the ReadyButton in the bottom left corner
	 */
	public static void draw_ReadyButton(Graphics g) {
		
		int X = GameData.readyButton_X, Y = GameData.readyButton_Y, maße = GameData.readyButton_maße, circleBorder = GameData.readyButton_circleBorder;
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(X, Y, maße, maße);
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval(X+circleBorder, Y+circleBorder, maße-circleBorder*2, maße-circleBorder*2);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		if(RoundData.clientIsReadyForThisRound == true) {
			g.setColor(Color.BLACK);
			g.drawString("READY", X+(maße/2)-22, Y+70);
			g.setColor(Color.GREEN);
			g.drawOval(X+circleBorder, Y+circleBorder, maße-circleBorder*2, maße-circleBorder*2);
		}else {
			g.setColor(Color.BLACK);
			g.drawString("UNREADY", X+(maße/2)-35, Y+70);
			g.setColor(Color.RED);
			g.drawOval(X+circleBorder, Y+circleBorder, maße-circleBorder*2, maße-circleBorder*2);
		}
		int timeLeft = RoundData.roundTime_Left;
		int min = 9, sec = 59;
		if(timeLeft >= 240) {
			min = 4; sec = timeLeft-240;
		}else if(timeLeft >= 180) {
			min = 3; sec = timeLeft-180;
		}else if(timeLeft >= 120) {
			min = 2; sec = timeLeft-120;
		}else if(timeLeft >= 60) {
			min = 1; sec = timeLeft-60;
		}else {
			min = 0; sec = timeLeft;
		}
		String time = min+":"+Funktions.getDoubleWritenNumber(sec);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.drawString(time, X+(maße/2)-19, Y+96);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString(RoundData.readyPlayerCount+"/"+Game_RoundHandler.getMaxPlayerSwitchedPerTaskType(), X+(maße/2)-10, Y+117);
		
	}
	
	
	//TODO BUILD MENU
	
//==========================================================================================================
	/**
	 * Draws the BuildMenu on the left side
	 */
	public static void draw_BuildMenu(Graphics g) {
		
		if(GameData.buildMenu_activated == false || GameData.buildMenu_displayedType == null) { return; }
		
		int X = GameData.buildMenu_X, Y = GameData.buildMenu_Y;
		int width = GameData.buildMenu_width;
		int height = GameData.buildMenu_height;
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(X, Y, width, height);
		g.setColor(Color.WHITE);
		g.drawRect(X, Y, width, height);
		
		//TITLE
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		String title = ""+GameData.buildMenu_displayedType.toString();
		g.drawString(title, X+(width/2)-(title.length()*4), Y+GameData.buildMenu_YdistanceToFirstBuilding-9);
		
		//Category Switch area
		int switchArea_numberOfCategorys = GameData.buildMenu_possibleCategory.size();
		int switchArea_width = (GameData.switchArea_border*2)+GameData.switchArea_size;
		int switchArea_height = (GameData.switchArea_border*2)+((switchArea_numberOfCategorys-1)*GameData.switchArea_spaceBetween)+(switchArea_numberOfCategorys*GameData.switchArea_size);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(GameData.switchArea_X, GameData.switchArea_Y, switchArea_width, switchArea_height);
		g.setColor(Color.WHITE);
		g.drawRect(GameData.switchArea_X, GameData.switchArea_Y, switchArea_width, switchArea_height);
		
		int durchlauf = 0;
		for(BuildMenuType type : GameData.buildMenu_possibleCategory) {
			
			String kßrzel = BuildMenuType.getKürzelByType(type);
			int typeX = GameData.switchArea_X+GameData.switchArea_border, typeY = GameData.switchArea_Y+GameData.switchArea_border+(durchlauf*GameData.switchArea_size)+(durchlauf*GameData.switchArea_spaceBetween);
			
			if(GameData.buildMenu_displayedType == type) {
				//CURRENT DISPLAYED TYPE
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(typeX, typeY, GameData.switchArea_size, GameData.switchArea_size);
				g.setColor(Color.BLACK);
			}else {
				//NORMAL TYPE
				g.setColor(Color.WHITE);
			}
			g.setFont(new Font("Arial", Font.BOLD, 15));
			g.drawString(""+kßrzel, typeX+(GameData.switchArea_size/2)-5, typeY+GameData.switchArea_size-4);
			
			durchlauf++;
		}
		
		//Current Buildings of the Category
		int Xcords = 1, Ycords = 1;
		for(BuildMenuTask buildMenuTask : GameData.buildMenu_displayedBuildings) {
			
			int realX = X+GameData.buildMenu_border+((Xcords-1)*GameData.buildMenu_sizePerBuilding)+((Xcords-1)*GameData.buildmenu_spaceBetweenBuildings);
			int realY = Y + GameData.buildMenu_YdistanceToFirstBuilding + GameData.buildMenu_border+((Ycords-1)*GameData.buildMenu_sizePerBuilding)+((Ycords-1)*GameData.buildmenu_spaceBetweenBuildings);
			
			if(buildMenuTask.isLocked()) {
				//NOT RESERACHED - HIDE
				g.drawImage(Images.noResearchLock, realX+GameData.buildMenu_lockedImgBorder, realY+GameData.buildMenu_lockedImgBorder, null);
			}else {
				//DISPLAY
				String buildingName = buildMenuTask.name;
				String buildingKßrzel = buildMenuTask.kürzel;
				int buildingCost = buildMenuTask.cost;
				
				//NAME
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 9));
				g.drawString(buildingName, realX+3, realY+15);
				//KßRZEL
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 14));
				g.drawString(buildingKßrzel, realX+5, realY+GameData.buildMenu_sizePerBuilding-12);
				//COST
				if(GameHandler.hasEnoughtMaterial(buildingCost)) { g.setColor(Color.GREEN); }else { g.setColor(Color.RED); } //ENOUGHT MASS?
				g.setFont(new Font("Arial", Font.BOLD, 14));
				g.drawString(""+buildingCost, realX+GameData.buildMenu_sizePerBuilding-30, realY+GameData.buildMenu_sizePerBuilding-12);
			}
			
			if(Xcords == 1) {
				Xcords = 2;
			}else if(Xcords == 2) {
				Xcords = 1;
				Ycords++;
			}
			
		}
		
	}
		
	
	//TODO MOUSE ACTION AREAS

//==========================================================================================================
	/**
	 * Draws the MouseActionAreas (Buttons)
	 */
	public static void draw_MouseActionAreas(Graphics g, boolean OTWactive) {
		
		try{
			for(MouseActionArea actionArea : GameData.mouseActionAreas) {
				if(OTWactive == true) {
					if(actionArea.OTWMMA == true) {
						actionArea.draw(g);
					}
				}else {
					if(actionArea.OTWMMA == false) {
						actionArea.draw(g);
						if(actionArea instanceof MouseActionArea_InfoMessages) {
							if(GameData.coordsUpdatedNeeded == true) {
								((MouseActionArea_InfoMessages) actionArea).updateCoordinates();
							}
						}
					}
				}
			}
			//UPDATE DONE
			if(GameData.coordsUpdatedNeeded == true) {
				GameData.coordsUpdatedNeeded = false;
			}
		}catch(ConcurrentModificationException error) {}
		
	}
	
	
	//TODO FIELD MESSAGE

//==========================================================================================================
	/**
	 * Draws the FieldMessage
	 */
	public static void draw_FieldMessage(Graphics g) {
		
		if(GameData.activeMessage != null) {
			GameData.activeMessage.draw(g);
		}
		
	}
	
	
	//TODO MINIMAP
	
//==========================================================================================================
	/**
	 * Draws the MiniMap of the Game - FIELDS
	 */
	public static void draw_MiniMap_Fields(Graphics g) {
		
		//MINIMAP - BACKGROUND
		g.setColor(Color.DARK_GRAY); //!!!!!! VERHINDERT DAS DURCHSICHTIGE !!!!!!!!
		g.fillRect(StandardData.X-StandardData.rahmen/2, StandardData.Y-StandardData.rahmen/2, StandardData.maße+StandardData.rahmen, StandardData.maße+StandardData.rahmen);
		g.setColor(Funktions.getColorByPlayerID(ProfilData.thisClient.getID()));
		g.drawRect(StandardData.X-2, StandardData.Y-2, StandardData.maße+4, StandardData.maße+4);
		g.drawRect(StandardData.X-3, StandardData.Y-3, StandardData.maße+6, StandardData.maße+6);
		g.setColor(Color.WHITE);
		g.drawRect(StandardData.X-1, StandardData.Y-1, StandardData.maße+2, StandardData.maße+2);
		
		//MINIMAP - FIELDS
		if( ((MouseActionArea_Checkbox) GameHandler.getMouseActionAreaByName("Checkbox_MiniMap_ShowMap")).getCurrentState() == true ) {
			//REAL MAP
			for(int x = 0 ; x < StandardData.mapWidth ; x += 2) {
				for(int y = 0 ; y < StandardData.mapHight ; y += 2) {
					if(x > StandardData.mapWidth || y > StandardData.mapHight) { break; }
					Field field = GameData.gameMap_FieldList[x][y];
					int mmX = field.X*StandardData.normaliseFactorX; int mmY = field.Y*StandardData.normaliseFactorY;
					if(field.X <= StandardData.ßberschussX && field.Y <= StandardData.ßberschussY) {
						//MUSS X & Y - GESTRECKT WERDEN
						if(field.visible == true && ((MouseActionArea_Checkbox) GameHandler.getMouseActionAreaByName("Checkbox_MiniMap_ShowVisible")).getCurrentState() == true) {
							g.setColor(Color.LIGHT_GRAY);
							g.drawRect(StandardData.X+mmX+(field.X*StandardData.ausgleichsValue), StandardData.Y+mmY+(field.Y*StandardData.ausgleichsValue), StandardData.normaliseFactorX+StandardData.ausgleichsValue, StandardData.normaliseFactorY+StandardData.ausgleichsValue);
						}
						g.setColor(FieldType.getMiniMapColorForFieldType(field.type));
						g.fillRect(StandardData.X+mmX+(field.X*StandardData.ausgleichsValue), StandardData.Y+mmY+(field.Y*StandardData.ausgleichsValue), StandardData.normaliseFactorX+StandardData.ausgleichsValue, StandardData.normaliseFactorY+StandardData.ausgleichsValue);
					}else if(field.X <= StandardData.ßberschussX) {
						//MUSS NUR X - GESTRECKT WERDEN
						if(field.visible == true && ((MouseActionArea_Checkbox) GameHandler.getMouseActionAreaByName("Checkbox_MiniMap_ShowVisible")).getCurrentState() == true) {
							g.setColor(Color.LIGHT_GRAY);
							g.drawRect(StandardData.X+mmX+(field.X*StandardData.ausgleichsValue), StandardData.Y+mmY+((StandardData.ßberschussY+1)*StandardData.ausgleichsValue), StandardData.normaliseFactorX+StandardData.ausgleichsValue, StandardData.normaliseFactorY);
						}
						g.setColor(FieldType.getMiniMapColorForFieldType(field.type));
						g.fillRect(StandardData.X+mmX+(field.X*StandardData.ausgleichsValue), StandardData.Y+mmY+((StandardData.ßberschussY+1)*StandardData.ausgleichsValue), StandardData.normaliseFactorX+StandardData.ausgleichsValue, StandardData.normaliseFactorY);
					}else if(field.Y <= StandardData.ßberschussY) {
						//MUSS NUR Y - GESTRECKT WERDEN
						if(field.visible == true && ((MouseActionArea_Checkbox) GameHandler.getMouseActionAreaByName("Checkbox_MiniMap_ShowVisible")).getCurrentState() == true) {
							g.setColor(Color.LIGHT_GRAY);
							g.drawRect(StandardData.X+mmX+((StandardData.ßberschussX+1)*StandardData.ausgleichsValue), StandardData.Y+mmY+(field.Y*StandardData.ausgleichsValue), StandardData.normaliseFactorX, StandardData.normaliseFactorY+StandardData.ausgleichsValue);
						}
						g.setColor(FieldType.getMiniMapColorForFieldType(field.type));
						g.fillRect(StandardData.X+mmX+((StandardData.ßberschussX+1)*StandardData.ausgleichsValue), StandardData.Y+mmY+(field.Y*StandardData.ausgleichsValue), StandardData.normaliseFactorX, StandardData.normaliseFactorY+StandardData.ausgleichsValue);
					}else {
						//MUSS GAR NICHT GESTRECKT WERDEN
						if(field.visible == true && ((MouseActionArea_Checkbox) GameHandler.getMouseActionAreaByName("Checkbox_MiniMap_ShowVisible")).getCurrentState() == true) {
							g.setColor(Color.LIGHT_GRAY);
							g.drawRect(StandardData.X+mmX+((StandardData.ßberschussX+1)*StandardData.ausgleichsValue), StandardData.Y+mmY+((StandardData.ßberschussY+1)*StandardData.ausgleichsValue), StandardData.normaliseFactorX, StandardData.normaliseFactorY);
						}
						g.setColor(FieldType.getMiniMapColorForFieldType(field.type));
						g.fillRect(StandardData.X+mmX+((StandardData.ßberschussX+1)*StandardData.ausgleichsValue), StandardData.Y+mmY+((StandardData.ßberschussY+1)*StandardData.ausgleichsValue), StandardData.normaliseFactorX, StandardData.normaliseFactorY);
					}
				}
			}
		}else {
			//POSSIBLE AN (RADAR-)IMAGE
		}
		
	}
//==========================================================================================================
	/**
	 * Draws the MiniMap of the Game - BUILDINGS
	 */
	public static void draw_MiniMap_Buildings(Graphics g) {
		
		if( ((MouseActionArea_Checkbox) GameHandler.getMouseActionAreaByName("Checkbox_MiniMap_ShowBuildings")).getCurrentState() == true ) {
			for(Building building : Funktions.getAllBuildingList()) {
				if(building.connectedField.visible == true || building instanceof Building_Headquarter) {
					//ONLY HQ DRAW AND VISIBLE FIELDS
					int realX = (building.connectedField.X+1)*StandardData.normaliseFactorX; int realY = (building.connectedField.Y+1)*StandardData.normaliseFactorY;  
					if( building.connectedField.X <= StandardData.ßberschussX) { realX += building.connectedField.X*StandardData.ausgleichsValue; } else { realX += StandardData.ßberschussX*StandardData.ausgleichsValue; }
					if( building.connectedField.Y <= StandardData.ßberschussY) { realY += building.connectedField.Y*StandardData.ausgleichsValue; } else { realY += StandardData.ßberschussY*StandardData.ausgleichsValue; }
					
					g.setColor(Funktions.getColorByPlayerID(building.playerID));
					g.fillRect(StandardData.X+realX-1, StandardData.Y+realY-1, 3, 3);
				}
			}
		}
		
	}
//==========================================================================================================
	/**
	 * Draws the MiniMap of the Game - TROUPS
	 */
	public static void draw_MiniMap_Troups(Graphics g) {
		
		if( ((MouseActionArea_Checkbox) GameHandler.getMouseActionAreaByName("Checkbox_MiniMap_ShowTroups")).getCurrentState() == true ) {
			for(Troup troup : Funktions.getAllTroupList()) {
				if(troup.connectedField.visible == true) {
					//ONLY DRAW VISIBLE FIELDS
					int realX = (troup.connectedField.X+1)*StandardData.normaliseFactorX; int realY = (troup.connectedField.Y+1)*StandardData.normaliseFactorY;  
					if( troup.connectedField.X <= StandardData.ßberschussX) { realX += troup.connectedField.X*StandardData.ausgleichsValue; } else { realX += StandardData.ßberschussX*StandardData.ausgleichsValue; }
					if( troup.connectedField.Y <= StandardData.ßberschussY) { realY += troup.connectedField.Y*StandardData.ausgleichsValue; } else { realY += StandardData.ßberschussY*StandardData.ausgleichsValue; }
					
					g.setColor(Funktions.getColorByPlayerID(troup.playerID));
					g.fillRect(StandardData.X+realX-1, StandardData.Y+realY-1, 3, 3);
				}
			}
		}
		
	}
//==========================================================================================================
	/**
	 * Draws the MiniMap of the Game - CURRENTVIEW
	 */
	public static void draw_MiniMap_CurrentView(Graphics g) {
		
		int maße = StandardData.minimapMaße; int rahmen = 10;
		int X = WindowData.FrameWidth-maße-rahmen/2-10; int Y = WindowData.FrameHeight-maße-rahmen/2-30;
		int normaliseFactorX = maße/(StandardData.mapWidth-1); int normaliseFactorY = maße/(StandardData.mapHight-1);
		int ßberschussX = maße-(normaliseFactorX*StandardData.mapWidth); int ßberschussY = maße-(normaliseFactorY*StandardData.mapHight);
		int ausgleichsValue = 1;
		
		Field midField = GameHandler.get_MID_Field(false);
		if(midField != null) {
			int sizeX = 120; int sizeY = 65;
			int midX = midField.X*normaliseFactorX; int midY = midField.Y*normaliseFactorY;  
			if( midField.X <= ßberschussX) { midX += midField.X*ausgleichsValue; } else { midX += ßberschussX*ausgleichsValue; }
			if( midField.Y <= ßberschussY) { midY += midField.Y*ausgleichsValue; } else { midY += ßberschussY*ausgleichsValue; }
			g.setColor(Color.WHITE);
			g.drawRect(X+midX-(sizeX/2), Y+midY-(sizeY/2), sizeX, sizeY);
		}
		
	}
	
}
