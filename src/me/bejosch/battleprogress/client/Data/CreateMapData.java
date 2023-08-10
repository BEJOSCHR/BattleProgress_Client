package me.bejosch.battleprogress.client.Data;

import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Field.Field;

public class CreateMapData {

	//CREATE MAP - HUD
	public static int createMap_width = 350, createMap_height = 150, spaceToTheLeft = 0, spaceToTheBottom = 30, createMap_X = spaceToTheLeft, createMap_Y = WindowData.FrameHeight-spaceToTheBottom-createMap_height;
	public static int distanceBetweenButtons = 30, seperateBorderDistance = 150;
	
	//CREATE MAP - OverlayInput
	public static int overlay_width = 340, overlay_height = 130, overlay_x = (WindowData.FrameWidth/2)-(overlay_width/2), overlay_y = (WindowData.FrameHeight/2)-(overlay_height/2);
	public static int overlay_midX = overlay_x+(overlay_width/2);
	
	public static Field createMap_FieldList[][] = null;
	
	public static FieldType currentFieldBuild = FieldType.Water;
	
	public static List<Building> HQdisplayList = new ArrayList<>();
	
	public static String map_CM_Name = null;
	public static Field choosenField = null;
	
	public static int scroll_CM_LR_count = 0;
	public static int scroll_CM_UD_count = 0;
	
	public static boolean showHUD = false;
	public static boolean overlayedInput = false;
	public static boolean overlayInputFinished = false;
	public static boolean loadingMap = false;
	public static boolean clearingMap = false;
	public static String whySaveEnded = "";
	
}
