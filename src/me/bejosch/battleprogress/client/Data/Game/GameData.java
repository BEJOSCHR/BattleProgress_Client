package me.bejosch.battleprogress.client.Data.Game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Enum.BuildMenuType;
import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Objects.ClientPlayer;
import me.bejosch.battleprogress.client.Objects.DictonaryInfoDescription;
import me.bejosch.battleprogress.client.Objects.FieldData;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Field.FieldMessage;
import me.bejosch.battleprogress.client.Objects.InfoMessage.InfoMessage;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea_BuildMenu_BuildingField;
import me.bejosch.battleprogress.client.Objects.Tasks.BuildMenuTasks.BuildMenuTask;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;

public class GameData {

	public static String mapName = null;
	public static Field gameMap_FieldList[][] = null;
	
	public static int gameID = 0;
	public static SpielModus gameMode = null;
	public static boolean gameIsRunning = false;
	
	public static ClientPlayer[] playingPlayer = null; //IN ORDER! get(0) == player1
	
	public static Map<Integer, String> chatList = new HashMap<Integer, String>();
	
	public static Field hoveredField = null;
	public static Field clickedField = null;
	public static int scroll_LR_count = 0;
	public static int scroll_UD_count = 0;
	
	public static List<Field> visibleFields = new ArrayList<Field>();
	public static List<FieldCoordinates> buildArea = new ArrayList<FieldCoordinates>();
	
	//Tasks
	public static final int maxActionbarTasks = 9; //count start with 0, so +1
	public static boolean dragAndDropTaskInputActive = false;
	public static int task_lockImageBorder = 8;
	
	//ActionBar
	public static final int actionbar_ImgSize = 30;
	public static final int actionbar_Height = 60, actionbar_WidthPerTask = 60, actionbar_BottomBorder = 35, actionbar_SpaceBetweenTask = 10, actionbar_backgroundOverlappingSize = 5;
	public static final int actionbar_X = 300, actionbar_Y = WindowData.FrameHeight-actionbar_Height-actionbar_backgroundOverlappingSize-actionbar_BottomBorder;
	
	//HealthBar
	public static final int healthBar_abstandX = 9, healthBar_abstandY = StandardData.fieldSize-12, healthBar_height = 4;
	
	//MainDisplayBox (Actionbar)
	public static int displayBox_border = 40, displayBox_size = StandardData.fieldSize+displayBox_border*2, displayBox_textYMoving = 3;
	public static int displayBox_realX = GameData.actionbar_X-GameData.actionbar_backgroundOverlappingSize-1-displayBox_size, displayBox_realY = WindowData.FrameHeight-displayBox_size-GameData.actionbar_BottomBorder;
	
	//ReadyButton
	public static int readyButton_maße = displayBox_realX+WindowData.rahmen-GameData.actionbar_backgroundOverlappingSize*2;
	public static int readyButton_circleBorder = 30;
	public static int readyButton_X = -WindowData.rahmen, readyButton_Y = WindowData.FrameHeight-actionbar_BottomBorder-readyButton_maße;
	
	//BuildMenu - Changing Data
	public static boolean dragAndDropInputActive_BuildingMenu = false;
	public static MouseActionArea_BuildMenu_BuildingField currentActive_MAA_BuildingTask = null;
	public static List<BuildMenuType> buildMenu_possibleCategory = new ArrayList<BuildMenuType>();
	public static List<BuildMenuTask> buildMenu_displayedBuildings = new ArrayList<BuildMenuTask>();
	public static BuildMenuType buildMenu_displayedType = null;
	public static boolean buildMenu_activated = true;
	//BuildMenu - Main Building fields
	public static int buildMenu_YdistanceToFirstBuilding = 35;
	public static int buildMenu_buildingsPerHeight = 7, buildMenu_buildingsPerWidth = 2;
	public static int buildMenu_border = 6, buildmenu_spaceBetweenBuildings = 5, buildMenu_sizePerBuilding = 65, buildMenu_lockedImgBorder = 10;
	public static int buildMenu_width = (GameData.buildMenu_border*2)+(GameData.buildMenu_buildingsPerWidth*GameData.buildMenu_sizePerBuilding)+( (GameData.buildMenu_buildingsPerWidth-1)*GameData.buildmenu_spaceBetweenBuildings);
	public static int buildMenu_height = GameData.buildMenu_YdistanceToFirstBuilding+(GameData.buildMenu_border*2)+(GameData.buildMenu_buildingsPerHeight*GameData.buildMenu_sizePerBuilding)+( (GameData.buildMenu_buildingsPerHeight-1)*GameData.buildmenu_spaceBetweenBuildings);
	public static int buildMenu_spaceToReadyButton = 10;
	public static int buildMenu_X = 0, buildMenu_Y = readyButton_Y-1 - buildMenu_spaceToReadyButton - buildMenu_height;
	//BuildMenu - CategorySwitch
	public static int switchArea_X = buildMenu_X+buildMenu_width, switchArea_Y = buildMenu_Y;
	public static int switchArea_border = 5, switchArea_size = 20, switchArea_spaceBetween = 5;
	
	//ActiveTask
	public static final int activeTask_width = 200, activeTask_height = 30;
	public static final int activeTask_X = actionbar_X-actionbar_backgroundOverlappingSize, activeTask_Y = actionbar_Y-activeTask_height-actionbar_backgroundOverlappingSize;
	public static final int activeTask_textSize = 16;
	public static final Color activeTask_textColor = Color.RED;
	
	//MouseActionAreas
	public static CopyOnWriteArrayList<MouseActionArea> mouseActionAreas = new CopyOnWriteArrayList<MouseActionArea>();
	//DisplayMessages
	public static int startX = WindowData.FrameWidth-20, startY = StandardData.gIcon_y+StandardData.gIcon_maße+StandardData.gIcon_y, space_MAArea = 7, maxDisplayedInfoMessages = 10;
	public static boolean coordsUpdatedNeeded = true;
	
	//InfoMessages
	public static CopyOnWriteArrayList<InfoMessage> notificationList = new CopyOnWriteArrayList<InfoMessage>();
	
	//FieldMessage
	public static FieldMessage activeMessage = null;
	
	//ChateHandler
	public static boolean chatIsShown = false;
	public static int chat_height = 400, chat_width = 480;
	public static int chatX_show = WindowData.FrameWidth-chat_width, chatY_show = WindowData.FrameHeight-chat_height-380;
	public static int chatX_hide = WindowData.FrameWidth+WindowData.rahmen*2, chatY_hide = WindowData.FrameHeight-chat_height-380;
	public static int chatButton_width = 50, chatButton_height = 60, chatButton_roundPartWidth = 16, chatbutton_showXausgleich = 14;
	
	//ECONOMIC DISPLAY
	public static final Color color_Material = Color.YELLOW, color_Energy = new Color(102, 204, 255), color_Research = new Color(255, 0, 255);
	public static final Color color_positiv = Color.GREEN, color_negative = Color.RED, color_highlight = Color.ORANGE;
	public static int ecoDisp_width = 120, ecoDisp_height = 40;
	public static int ecoDisp_distanceTotalSegment = ecoDisp_height+20, ecoDisp_cornerRound = 15;
	public static int ecoDisp_rightBorder = 50, ecoDisp_totalDownBorder = ecoDisp_distanceTotalSegment* 2 +GameData.ecoDisp_height+StandardData.rahmen/2+50;
	public static int ecoDisp_insideLeftBorder = 15, ecoDisp_insideDownBorder = 8;
	
	//PLAYER VALUES:
	public static List<Building> player1_buildings = new ArrayList<>();
	public static List<Troup> player1_troups = new ArrayList<>();
	public static List<Building> player2_buildings = new ArrayList<>();
	public static List<Troup> player2_troups = new ArrayList<>();
	public static List<Building> player3_buildings = new ArrayList<>();
	public static List<Troup> player3_troups = new ArrayList<>();
	public static List<Building> player4_buildings = new ArrayList<>();
	public static List<Troup> player4_troups = new ArrayList<>();
	
	//INGAME VALUES:
	public static int roundDuration = 3*60; //IN SEC
	public static int ressourceFieldProducingRoundNumber = 20; //How many round a ressourceField should be producing after a mine was build on it (stops if the mine gets destroyed, but don't reset)
	
	//PROGRESS POINTS
	//	Keep synced with server side! on the target and kill amounts, otherwise the client display will be not the same as server is counting
	public static int progressPoints = 0;
	public static final int progressPoints_target_1v1 = 100;
	public static final int progressPoints_target_2v2 = 200;
	public static final int progressPoints_kill_building = 10;
	public static final int progressPoints_kill_troup = 5;
	public static final int progressPoints_kill_hq = 100;
	//	Client side only
	public static final int progressPoints_borderRight = 20/*StandardData.gIcon_x*/, progressPoints_borderTop = StandardData.gIcon_y;
	public static final int progressPoints_width = 135-progressPoints_borderRight, progressPoints_height = StandardData.gIcon_maße;
	public static final int progressPoints_x = WindowData.FrameWidth-progressPoints_width-progressPoints_borderRight, progressPoints_y = 0+progressPoints_borderTop;
	public static final Color progressPoints_color = new Color(28, 189, 47); //new Color(23, 255, 50); //Green
	
	//DICTIONARY
	public static List<DictonaryInfoDescription> dictonaryInfoDescriptions = new ArrayList<DictonaryInfoDescription>();
	
	//FIELDDATA
	public static List<FieldData> fieldData = new ArrayList<FieldData>();
	
}
