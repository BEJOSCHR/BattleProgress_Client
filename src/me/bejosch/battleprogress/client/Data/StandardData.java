package me.bejosch.battleprogress.client.Data;

import java.awt.Color;
import java.awt.Point;

import me.bejosch.battleprogress.client.Enum.SpielStatus;

public class StandardData {

	public static final String clientVersion = "1.0.4 [Alpha]";
	
	public static final int mapWidth = 60;
	public static final int mapHight = 60;
	//MAP GRßßE UND MINIMAP MAßE SOLLTEN GERADE TEILBAR SEIN, DAMIT DIE MINIMAP SICH NICHT VERZIEHT!
	public static final int minimapMaße = mapHight*4;
	
	public static final int fieldsPerScreenHeight = 22;
	public static int fieldSize = 0; //SET ON WINDOW CREATE - DEPENDS ON RESOLUTION
	public static final int fieldDraw_sicherheitsFaktor = 3, fielDraw_fieldCountX = 40, fielDraw_fieldCountY = 21;
	private static final int HQwallDistance = 15;
	
	public static final Point HQ_1_1vs1 = new Point(HQwallDistance-1, HQwallDistance-1), HQ_2_1vs1 = new Point(mapWidth-HQwallDistance, mapHight-HQwallDistance);
							  //TEAM 1 - Links														//TEAM 2 - Rechts
	public static final Point HQ_1_2vs2 = new Point(HQwallDistance-1, HQwallDistance-1), 			HQ_3_2vs2 = new Point(mapWidth-HQwallDistance, mapHight-HQwallDistance), 
							  HQ_2_2vs2 = new Point(HQwallDistance-1, mapHight-HQwallDistance) , 	HQ_4_2vs2 = new Point(mapWidth-HQwallDistance, HQwallDistance-1);
	
	public static final String messagePrefix = "[BP] ";
	public static boolean antiSpam = false;
	
	public static SpielStatus spielStatus = SpielStatus.LoadingScreen;
	
	public static boolean cancleMovement = false;
	public static boolean showGrid = false;
	
	public static boolean discordAPIloaded = false;
	
	public static int taskCalculateReachableFieldsSaveAdditionFactor = 0;
	
	//MINIMAP
	public static int maße = StandardData.minimapMaße, rahmen = 10; //RAHMEN MUSS GERADE ZAHL SEIN
	public static int X = WindowData.FrameWidth-maße-rahmen/2-30, Y = WindowData.FrameHeight-maße-rahmen/2-25;
	public static int normaliseFactorX = maße/(StandardData.mapWidth-1), normaliseFactorY = maße/(StandardData.mapHight-1);
	public static int ßberschussX = maße-(normaliseFactorX*StandardData.mapWidth), ßberschussY = maße-(normaliseFactorY*StandardData.mapHight);
	public static int ausgleichsValue = 1;
	
	//GENERAL ICONs
	public static int gIcon_x = 30, gIcon_y = 30;
	public static int gIcon_maße = 50, gIcon_imgBorder = 5;
	public static int gIcon_distanceBetween = 30;
	public static Color gIcon_defaultColor = Color.BLACK, gIcon_backgroundColor = Color.LIGHT_GRAY, gIcon_hoverColor = Color.WHITE;
	
	
	
	
	//TODO DOWN BELOW KEEP HIDDEN!!! 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static final String DISCORD_API_KEY = "825697002410737686";
}
