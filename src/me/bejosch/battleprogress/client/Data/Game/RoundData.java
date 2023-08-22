package me.bejosch.battleprogress.client.Data.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import me.bejosch.battleprogress.client.Enum.ExecuteTaskType;
import me.bejosch.battleprogress.client.Objects.ClientPlayer;
import me.bejosch.battleprogress.client.Objects.RoundStatsContainer;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Attack;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Build;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_HealAndRepair;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Move;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Produce;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Remove;
import me.bejosch.battleprogress.client.Objects.ExecuteTasks.ExecuteTask_Upgrade;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Tasks.Building.Task_Building;
import me.bejosch.battleprogress.client.Objects.Tasks.Troup.Task_Troup;

public class RoundData {

	public static int currentRound = 1;
	
	public static boolean roundIsChanging = false;
	public static boolean clientIsReadyForThisRound = false;
	
	public static int readyPlayerCount = 0;
	public static boolean player_1_isReady = false;
	public static boolean player_2_isReady = false;
	public static boolean player_3_isReady = false;
	public static boolean player_4_isReady = false;
	
	public static int clientSentBuildingTasks = 0;
	public static int clientSentTroupTasks = 0;
	public static int clientSentBuildMenuTasks = 0;
	
	//PLAYER EXECUTE ORDER
	public static ClientPlayer currentActivePlayer = null;
	public static ClientPlayer firstPlayerForThisRound = null;
	public static ExecuteTaskType currentExecutedTasks = null;
	public static int switchedPlayerForThisTask = 0;
	
	//ROUND TIMER
	public static int roundTime_Left = GameData.roundDuration;
	public static Timer roundTime_Timer = null;
	
	//DISPLAYED ROUND CHANGING INFO STATUS
	public static String roundStatusInfo = "missingStatus";
	
	//LOCATION ON SCREEN WHERE HE WAS AT THE END OF THE LAST ROUND
	public static FieldCoordinates lastRoundMidField = null;
	
	//CURRENT PERFORMED TASK
	public static boolean currentlyPerformingTasks = false;
	public static ExecuteTask currentExecuteTask = null;
	
	//THIS CLIENT - LOCAL TASK
	public static List<Task_Building> clientTasks_BuildingTasks = new ArrayList<Task_Building>();
	public static List<Task_Troup> clientTasks_TroupTasks = new ArrayList<Task_Troup>();
	//IM HANDLER IM RESET CLEAR ADDEN! \/
	//public static List<Task_BuildMenu> clientTasks_BuildmenuTasks = new ArrayList<Task_BuildMenu>();
	
	//ALL PLAYER - GLOBAL EXECUTE TASK
	public static int totalAttackTasks = 0;
	public static List<ExecuteTask_Attack> allTasks_Attack = new ArrayList<ExecuteTask_Attack>();
	public static int totalHealAndRepairTasks = 0;
	public static List<ExecuteTask_HealAndRepair> allTasks_HealAndRepair = new ArrayList<ExecuteTask_HealAndRepair>();
	public static int totalBuildTasks = 0;
	public static List<ExecuteTask_Build> allTasks_Build = new ArrayList<ExecuteTask_Build>();
	public static int totalProduceTasks = 0;
	public static List<ExecuteTask_Produce> allTasks_Produce = new ArrayList<ExecuteTask_Produce>();
	public static int totalUpgradeTasks = 0;
	public static List<ExecuteTask_Upgrade> allTasks_Upgrade = new ArrayList<ExecuteTask_Upgrade>();
	public static int totalMoveTasks = 0;
	public static List<ExecuteTask_Move> allTasks_Move = new ArrayList<ExecuteTask_Move>();
	public static int totalRemoveTasks = 0;
	public static List<ExecuteTask_Remove> allTasks_Remove = new ArrayList<ExecuteTask_Remove>();
	
	//ROUND STATS
	public static RoundStatsContainer currentStatsContainer = null;
	public static Map<Integer, RoundStatsContainer> statsContainer = new HashMap<Integer, RoundStatsContainer>();
	
	//BLOCKED TASKS
	public static int blockedTasks = 0;
	
}
