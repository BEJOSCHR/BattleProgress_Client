package me.bejosch.battleprogress.client.Data;

import java.util.LinkedList;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;

import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Objects.ClientPlayer;
import me.bejosch.battleprogress.client.Objects.GameAction;
import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;

public class SpectateData {

	public static boolean initSpectateMaas = false;
	
	public static String mapName = null;
	public static Field gameMap_FieldList[][] = null;
	
	public static int gameID = -1;
	public static int round = 1, targetExecuteID = -1;
	public static SpielModus gameMode = null;
	public static boolean gameIsRunning = false;
	
	public static ClientPlayer[] playingPlayer = null; //IN ORDER! get(0) == player1
	public static LinkedList<GameAction> actions = new LinkedList<>();
	public static CopyOnWriteArrayList<Building> buildings = new CopyOnWriteArrayList<>();
	public static CopyOnWriteArrayList<Troup> troups = new CopyOnWriteArrayList<>();
	
	public static Field hoveredField = null;
	public static Field clickedField = null;
	public static int scroll_LR_count = 0, scroll_UD_count = 0;
	
	public static boolean finishedInitLoading = false;
	public static int lastExecuteIndex = 0;
	
	public static Timer roundTimer = null;
	public static int roundTime = 0;
	
}
