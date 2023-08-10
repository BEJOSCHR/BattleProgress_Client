package me.bejosch.battleprogress.client.Window.Images;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.CreateMapData;
import me.bejosch.battleprogress.client.Data.MenuData;
import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;

public class Images {

	//MENU
	public static Image intro_BejoschGaming;
	public static Image intro_BattleProgress;
	public static Image login_background;
	
	//GAME
	public static Image noResearchLock;
	
	//GENERAL ICONS
	public static Image generalIcon_Energy;
	public static Image generalIcon_Material;
	public static Image generalIcon_Gear;
	public static Image generalIcon_Home;
	public static Image generalIcon_PowerButton;
	public static Image generalIcon_ResearchGlas;
	
	//MENU ICONS
	public static Image menuIcon_friendInvite;
	public static Image menuIcon_friendSpectate;
	public static Image menuIcon_friendChat;
	public static Image menuIcon_friendProfile;
	public static Image menuIcon_friendRemove;
	public static Image menuIcon_friendRequest_Accept;
	public static Image menuIcon_friendRequest_Decline;
	
	//PLAYER IMAGES
	public static List<Image> profileImages = new ArrayList<Image>();
	public static List<Image> backgroundImages = new ArrayList<Image>();
	
	//RANKING
	public static Image ranking_Unranked;
	//TODO ...
	
	//FIELDS
	public static Image field_Gras;
	public static Image field_Water;
	public static Image field_Stone;
	public static Image field_Path;
	public static Image field_Ressource;
	public static Image field_RessourceVerbraucht;
	
	//BUILDINGS - VIERECK
	public static Image building_missingTexture;
	public static Image building_headquarter;
	public static Image building_mine;
	public static Image building_reactor;
	public static Image building_lightTurret;
	public static Image building_lightArtillery;
	public static Image building_hospital;
	public static Image building_workshop;
	public static Image building_barracks;
	public static Image building_garage;
	public static Image building_airport;
	public static Image building_laboratory;
	public static Image building_converter;
	
	//TROUPS - KREIS + DREIECK
		//LAND - KREIS
		public static Image troup_Land_missingTexture;
		public static Image troup_Land_Commander;
		public static Image troup_Land_LightTank;
		public static Image troup_Land_MediumTank;
		public static Image troup_Land_HeavyTank;
		public static Image troup_Land_LightSoldier;
		public static Image troup_Land_MediumSoldier;
		public static Image troup_Land_HeavySoldier;
		//AIR - DREIECK
		public static Image troup_Air_missingTexture;
		public static Image troup_Air_LightHelicopter;
		public static Image troup_Air_MediumHelicopter;
		public static Image troup_Air_HeavyHelicopter;
	
	//TASK ICONS
	//QUELLE: https://www.flaticon.com/ 							<<<	!!!!!!!!!!!!!!!!!!!!
		//BUILDING TASKS
		public static Image taskIcon_Building_Destroy;
		public static Image taskIcon_Building_Attack;
		public static Image taskIcon_Building_Produce;
		public static Image taskIcon_Building_Heal;
		public static Image taskIcon_Building_Repair;
		//TROUP TASKS
		public static Image taskIcon_Troup_Remove;
		public static Image taskIcon_Troup_Move;
		public static Image taskIcon_Troup_Attack;
		public static Image taskIcon_Troup_Heal;
		public static Image taskIcon_Troup_Repair;
		public static Image taskIcon_Troup_Upgrade;
	
	
	public static final int maxChoosenTextureVariante = 2;
	public static int choosenTextureVariante = 2; //1,2
//	private static int choosenMenuImage = 1;
		
	public static final int addFaktor = 1;
	public static final int buildingFactor = 5; // nur eine Seite (zb nur linker abstand)
	public static final int troupFactor = 5; // nur eine Seite (zb nur linker abstand)
	
	
//==========================================================================================================
	/**
	 * Loads and creats all Images which are needed before any other loading
	 */
	public static void preLoadImages() {
		
		//INTRO
		loadImages_Intro();
		
	}
	
//==========================================================================================================
	/**
	 * Loads and creats all Images which are needed. Attention: The loading can took some seconds!
	 */
	public static void loadImages() {
		
		//MENU
		loadImages_Menu();
		
		//GENERAL
		loadImages_GeneralGame();
		
		//FIELDS
		loadImages_Fields();
		
		//BUILDINGS - VIERECK
		loadImages_Buildings();
		
		//TROUPS - KREIS + DREIECK
			//LAND - KREIS
			loadImages_Troups_Land();
			//AIR - DREIECK
			loadImages_Troups_Air();
				
		//TASK ICONS
		loadImages_taskIcons();
		
	}
	
//==========================================================================================================
	/**
	 * Loads and creats the INTRO images
	 */
	private static void loadImages_Intro() {
		
		intro_BejoschGaming = Funktions.loadImageFromName("BejoschGaming.png").getScaledInstance(WindowData.FrameWidth, WindowData.FrameWidth/3, Image.SCALE_DEFAULT);
		intro_BattleProgress = Funktions.loadImageFromName("BattleProgress.png").getScaledInstance(WindowData.FrameWidth, WindowData.FrameWidth/3, Image.SCALE_DEFAULT);
		login_background = Funktions.loadImageFromName("LoginBackground.png").getScaledInstance(WindowData.FrameWidth, WindowData.FrameHeight, Image.SCALE_DEFAULT);;
		
	}
	
//==========================================================================================================
	/**
	 * Loads and creats the MENU images
	 */
	private static void loadImages_Menu() {
		
		//MENU ICONS
		menuIcon_friendInvite = Funktions.loadImageFromName("MenuIcon_friendInvite.png").getScaledInstance(MenuData.rfl_button_maße-2, MenuData.rfl_button_maße-2, Image.SCALE_DEFAULT);
		menuIcon_friendSpectate = Funktions.loadImageFromName("MenuIcon_friendSpectate.png").getScaledInstance(MenuData.rfl_button_maße-2, MenuData.rfl_button_maße-2, Image.SCALE_DEFAULT);
		menuIcon_friendChat = Funktions.loadImageFromName("MenuIcon_friendChat.png").getScaledInstance(MenuData.rfl_button_maße-2, MenuData.rfl_button_maße-2, Image.SCALE_DEFAULT);
		menuIcon_friendProfile = Funktions.loadImageFromName("MenuIcon_friendProfile.png").getScaledInstance(MenuData.rfl_button_maße-2, MenuData.rfl_button_maße-2, Image.SCALE_DEFAULT);
		menuIcon_friendRemove = Funktions.loadImageFromName("MenuIcon_friendRemove.png").getScaledInstance(MenuData.rfl_button_maße-2, MenuData.rfl_button_maße-2, Image.SCALE_DEFAULT);
		menuIcon_friendRequest_Accept = Funktions.loadImageFromName("MenuIcon_friendRequest_Accept.png").getScaledInstance(OnTopWindowData.friendRequests_smallButtonMaße-2, OnTopWindowData.friendRequests_smallButtonMaße-2, Image.SCALE_DEFAULT);
		menuIcon_friendRequest_Decline = Funktions.loadImageFromName("MenuIcon_friendRequest_Decline.png").getScaledInstance(OnTopWindowData.friendRequests_smallButtonMaße-2, OnTopWindowData.friendRequests_smallButtonMaße-2, Image.SCALE_DEFAULT);
		
		//PROFILE IMAGES
		int i = 0;
		Image img = null;
		profileImages.clear();
		do {
			img = Funktions.loadImageFromName("ProfileImage_"+i+".png", false); //NO ERROR MESSAGE
			if(img != null) {
				profileImages.add(img.getScaledInstance(MenuData.mpd_profileImage_width-(MenuData.mpd_generalBorderInside*2), MenuData.mpd_profileImage_height-(MenuData.mpd_generalBorderInside*2), Image.SCALE_DEFAULT));
			}
			i++;
		}while(img != null);
		ConsoleOutput.printMessageInConsole("Loaded "+profileImages.size()+" profile images", true);
		
		//BACKGROUND IMAGES
		int j = 0;
		Image img1 = null;
		backgroundImages.clear();
		do {
			img1 = Funktions.loadImageFromName("BackgroundImage_"+j+".png", false); //NO ERROR MESSAGE
			if(img1 != null) {
				backgroundImages.add(img1.getScaledInstance(MenuData.mpd_background_width, MenuData.mpd_background_height, Image.SCALE_DEFAULT));
			}
			j++;
		}while(img1 != null);
		ConsoleOutput.printMessageInConsole("Loaded "+backgroundImages.size()+" background images", true);
		
		//RANKING IMAGES
		ranking_Unranked = Funktions.loadImageFromName("Ranking_Unranked.png").getScaledInstance(MenuData.mpd_underSection_maße-(MenuData.mpd_generalBorderInside*2), MenuData.mpd_underSection_maße-(MenuData.mpd_generalBorderInside*2), Image.SCALE_DEFAULT);
		
		intro_BejoschGaming = Funktions.loadImageFromName("BejoschGaming.png").getScaledInstance(WindowData.FrameWidth, WindowData.FrameWidth/3, Image.SCALE_DEFAULT);
		intro_BattleProgress = Funktions.loadImageFromName("BattleProgress.png").getScaledInstance(WindowData.FrameWidth, WindowData.FrameWidth/3, Image.SCALE_DEFAULT);
		
	}
	
//==========================================================================================================
	/**
	 * Loads and creats the GENERAL INGAME images
	 */
	private static void loadImages_GeneralGame() {
		
		//GENERAL
		noResearchLock = Funktions.loadImageFromName("noResearchLock.png").getScaledInstance(GameData.buildMenu_sizePerBuilding-GameData.buildMenu_lockedImgBorder*2, GameData.buildMenu_sizePerBuilding-GameData.buildMenu_lockedImgBorder*2, Image.SCALE_DEFAULT);
		
		//GENERAL ICONS
		int maße = StandardData.gIcon_maße-(StandardData.gIcon_imgBorder*2);
		generalIcon_Energy = Funktions.loadImageFromName("generalIcon_Energy.png").getScaledInstance(maße, maße, Image.SCALE_DEFAULT);
		generalIcon_Material = Funktions.loadImageFromName("generalIcon_Material.png").getScaledInstance(maße, maße, Image.SCALE_DEFAULT);
		generalIcon_Gear = Funktions.loadImageFromName("generalIcon_Gear.png").getScaledInstance(maße, maße, Image.SCALE_DEFAULT);
		generalIcon_Home = Funktions.loadImageFromName("generalIcon_Home.png").getScaledInstance(maße, maße, Image.SCALE_DEFAULT);
		generalIcon_PowerButton = Funktions.loadImageFromName("generalIcon_PowerButton.png").getScaledInstance(maße, maße, Image.SCALE_DEFAULT);
		generalIcon_ResearchGlas = Funktions.loadImageFromName("generalIcon_Glas.png").getScaledInstance(maße, maße, Image.SCALE_DEFAULT);
	}
//==========================================================================================================
	/**
	 * Loads and creats the FIELDS images
	 */
	public static void loadImages_Fields() {
		field_Gras = Funktions.loadImageFromName("Grass_"+choosenTextureVariante+".png").getScaledInstance(StandardData.fieldSize+addFaktor, StandardData.fieldSize+addFaktor, Image.SCALE_DEFAULT);
		field_Water = Funktions.loadImageFromName("Wasser_"+choosenTextureVariante+".png").getScaledInstance(StandardData.fieldSize+addFaktor, StandardData.fieldSize+addFaktor, Image.SCALE_DEFAULT);
		field_Stone = Funktions.loadImageFromName("Stone_"+choosenTextureVariante+".png").getScaledInstance(StandardData.fieldSize+addFaktor, StandardData.fieldSize+addFaktor, Image.SCALE_DEFAULT);
		field_Path = Funktions.loadImageFromName("Dirt_"+choosenTextureVariante+".png").getScaledInstance(StandardData.fieldSize+addFaktor, StandardData.fieldSize+addFaktor, Image.SCALE_DEFAULT);
		field_Ressource = Funktions.loadImageFromName("Gold.png").getScaledInstance(StandardData.fieldSize+addFaktor, StandardData.fieldSize+addFaktor, Image.SCALE_DEFAULT);
		field_RessourceVerbraucht = Funktions.loadImageFromName("GoldAbgebaut.png").getScaledInstance(StandardData.fieldSize+addFaktor, StandardData.fieldSize+addFaktor, Image.SCALE_DEFAULT);
	}
//==========================================================================================================
	/**
	 * Loads and creats the BUILDINGS images
	 */
	private static void loadImages_Buildings() {
		building_missingTexture = Funktions.loadImageFromName("building_missingTexture.png").getScaledInstance(StandardData.fieldSize+addFaktor-buildingFactor*2, StandardData.fieldSize+addFaktor-buildingFactor*2, Image.SCALE_DEFAULT);
		building_headquarter = Funktions.loadImageFromName("building_headquarter.png").getScaledInstance(StandardData.fieldSize+addFaktor-buildingFactor*2, StandardData.fieldSize+addFaktor-buildingFactor*2, Image.SCALE_DEFAULT);
		building_mine = Funktions.loadImageFromName("building_mine.png").getScaledInstance(StandardData.fieldSize+addFaktor-buildingFactor*2, StandardData.fieldSize+addFaktor-buildingFactor*2, Image.SCALE_DEFAULT);
		building_reactor = Funktions.loadImageFromName("building_reactor.png").getScaledInstance(StandardData.fieldSize+addFaktor-buildingFactor*2, StandardData.fieldSize+addFaktor-buildingFactor*2, Image.SCALE_DEFAULT);
		building_lightTurret = Funktions.loadImageFromName("building_lightTurret.png").getScaledInstance(StandardData.fieldSize+addFaktor-buildingFactor*2, StandardData.fieldSize+addFaktor-buildingFactor*2, Image.SCALE_DEFAULT);
		building_lightArtillery = Funktions.loadImageFromName("building_lightArtillery.png").getScaledInstance(StandardData.fieldSize+addFaktor-buildingFactor*2, StandardData.fieldSize+addFaktor-buildingFactor*2, Image.SCALE_DEFAULT);
		building_hospital = Funktions.loadImageFromName("building_hospital.png").getScaledInstance(StandardData.fieldSize+addFaktor-buildingFactor*2, StandardData.fieldSize+addFaktor-buildingFactor*2, Image.SCALE_DEFAULT);
		building_workshop = Funktions.loadImageFromName("building_workshop.png").getScaledInstance(StandardData.fieldSize+addFaktor-buildingFactor*2, StandardData.fieldSize+addFaktor-buildingFactor*2, Image.SCALE_DEFAULT);
		building_barracks = Funktions.loadImageFromName("building_barracks.png").getScaledInstance(StandardData.fieldSize+addFaktor-buildingFactor*2, StandardData.fieldSize+addFaktor-buildingFactor*2, Image.SCALE_DEFAULT);
		building_garage = Funktions.loadImageFromName("building_garage.png").getScaledInstance(StandardData.fieldSize+addFaktor-buildingFactor*2, StandardData.fieldSize+addFaktor-buildingFactor*2, Image.SCALE_DEFAULT);
		building_airport = Funktions.loadImageFromName("building_airport.png").getScaledInstance(StandardData.fieldSize+addFaktor-buildingFactor*2, StandardData.fieldSize+addFaktor-buildingFactor*2, Image.SCALE_DEFAULT);
		building_laboratory = Funktions.loadImageFromName("building_laboratory.png").getScaledInstance(StandardData.fieldSize+addFaktor-buildingFactor*2, StandardData.fieldSize+addFaktor-buildingFactor*2, Image.SCALE_DEFAULT);
		building_converter = Funktions.loadImageFromName("building_converter.png").getScaledInstance(StandardData.fieldSize+addFaktor-buildingFactor*2, StandardData.fieldSize+addFaktor-buildingFactor*2, Image.SCALE_DEFAULT);
	}
//==========================================================================================================
	/**
	 * Loads and creats the TROUPS_LAND images
	 */
	private static void loadImages_Troups_Land() {
		troup_Land_missingTexture = Funktions.loadImageFromName("troup_land_missingTexture.png").getScaledInstance(StandardData.fieldSize+addFaktor-troupFactor*2, StandardData.fieldSize+addFaktor-troupFactor*2, Image.SCALE_DEFAULT);
		troup_Land_Commander = Funktions.loadImageFromName("troup_land_Commander.png").getScaledInstance(StandardData.fieldSize+addFaktor-troupFactor*2, StandardData.fieldSize+addFaktor-troupFactor*2, Image.SCALE_DEFAULT);
		troup_Land_LightTank = Funktions.loadImageFromName("troup_land_LightTank.png").getScaledInstance(StandardData.fieldSize+addFaktor-troupFactor*2, StandardData.fieldSize+addFaktor-troupFactor*2, Image.SCALE_DEFAULT);
		troup_Land_MediumTank = Funktions.loadImageFromName("troup_land_MediumTank.png").getScaledInstance(StandardData.fieldSize+addFaktor-troupFactor*2, StandardData.fieldSize+addFaktor-troupFactor*2, Image.SCALE_DEFAULT);
		troup_Land_HeavyTank = Funktions.loadImageFromName("troup_land_HeavyTank.png").getScaledInstance(StandardData.fieldSize+addFaktor-troupFactor*2, StandardData.fieldSize+addFaktor-troupFactor*2, Image.SCALE_DEFAULT);
		troup_Land_LightSoldier = Funktions.loadImageFromName("troup_land_LightSoldier.png").getScaledInstance(StandardData.fieldSize+addFaktor-troupFactor*2, StandardData.fieldSize+addFaktor-troupFactor*2, Image.SCALE_DEFAULT);
		troup_Land_MediumSoldier = Funktions.loadImageFromName("troup_land_MediumSoldier.png").getScaledInstance(StandardData.fieldSize+addFaktor-troupFactor*2, StandardData.fieldSize+addFaktor-troupFactor*2, Image.SCALE_DEFAULT);
		troup_Land_HeavySoldier = Funktions.loadImageFromName("troup_land_HeavySoldier.png").getScaledInstance(StandardData.fieldSize+addFaktor-troupFactor*2, StandardData.fieldSize+addFaktor-troupFactor*2, Image.SCALE_DEFAULT);
		
	}
//==========================================================================================================
	/**
	 * Loads and creats the TROUPS_AIR images
	 */
	private static void loadImages_Troups_Air() {
		troup_Air_missingTexture = Funktions.loadImageFromName("troup_air_missingTexture.png").getScaledInstance(StandardData.fieldSize+addFaktor-troupFactor*2, StandardData.fieldSize+addFaktor-troupFactor*2, Image.SCALE_DEFAULT);
		troup_Air_LightHelicopter = Funktions.loadImageFromName("troup_air_LightHelicopter.png").getScaledInstance(StandardData.fieldSize+addFaktor-troupFactor*2, StandardData.fieldSize+addFaktor-troupFactor*2, Image.SCALE_DEFAULT);
		troup_Air_MediumHelicopter = Funktions.loadImageFromName("troup_air_MediumHelicopter.png").getScaledInstance(StandardData.fieldSize+addFaktor-troupFactor*2, StandardData.fieldSize+addFaktor-troupFactor*2, Image.SCALE_DEFAULT);
		troup_Air_HeavyHelicopter = Funktions.loadImageFromName("troup_air_HeavyHelicopter.png").getScaledInstance(StandardData.fieldSize+addFaktor-troupFactor*2, StandardData.fieldSize+addFaktor-troupFactor*2, Image.SCALE_DEFAULT);
		
	}
//==========================================================================================================
	/**
	 * Loads and creats the TASK_ICON images
	 */
	private static void loadImages_taskIcons() {
		//BUILDINGS
		taskIcon_Building_Destroy = Funktions.loadImageFromName("taskIcon_building_destroy.png").getScaledInstance(GameData.actionbar_ImgSize, GameData.actionbar_ImgSize, Image.SCALE_DEFAULT);
		taskIcon_Building_Attack = Funktions.loadImageFromName("taskIcon_building_attack.png").getScaledInstance(GameData.actionbar_ImgSize, GameData.actionbar_ImgSize, Image.SCALE_DEFAULT);
		taskIcon_Building_Heal = Funktions.loadImageFromName("taskIcon_building_heal.png").getScaledInstance(GameData.actionbar_ImgSize, GameData.actionbar_ImgSize, Image.SCALE_DEFAULT);
		taskIcon_Building_Repair = Funktions.loadImageFromName("taskIcon_building_repair.png").getScaledInstance(GameData.actionbar_ImgSize, GameData.actionbar_ImgSize, Image.SCALE_DEFAULT);
		taskIcon_Building_Produce = Funktions.loadImageFromName("taskIcon_building_produce.png").getScaledInstance(GameData.actionbar_ImgSize, GameData.actionbar_ImgSize, Image.SCALE_DEFAULT);
		
		//TROUPS
		taskIcon_Troup_Remove = Funktions.loadImageFromName("taskIcon_troup_remove.png").getScaledInstance(GameData.actionbar_ImgSize, GameData.actionbar_ImgSize, Image.SCALE_DEFAULT);
		taskIcon_Troup_Move = Funktions.loadImageFromName("taskIcon_troup_move.png").getScaledInstance(GameData.actionbar_ImgSize, GameData.actionbar_ImgSize, Image.SCALE_DEFAULT);
		taskIcon_Troup_Attack = Funktions.loadImageFromName("taskIcon_troup_attack.png").getScaledInstance(GameData.actionbar_ImgSize, GameData.actionbar_ImgSize, Image.SCALE_DEFAULT);
		taskIcon_Troup_Heal = Funktions.loadImageFromName("taskIcon_troup_heal.png").getScaledInstance(GameData.actionbar_ImgSize, GameData.actionbar_ImgSize, Image.SCALE_DEFAULT);
		taskIcon_Troup_Repair = Funktions.loadImageFromName("taskIcon_troup_repair.png").getScaledInstance(GameData.actionbar_ImgSize, GameData.actionbar_ImgSize, Image.SCALE_DEFAULT);
		taskIcon_Troup_Upgrade = Funktions.loadImageFromName("taskIcon_troup_upgrade.png").getScaledInstance(GameData.actionbar_ImgSize, GameData.actionbar_ImgSize, Image.SCALE_DEFAULT);
		
	}
	
//==========================================================================================================
	/**
	 * Updates and reloades all field immages and updates them
	 * @param createMap - if true the createMapFields get updated, else the ingame fields
	 */
	public static void updateFieldImages(boolean createMap) {
		
		if(Images.choosenTextureVariante >= Images.maxChoosenTextureVariante) {
			Images.choosenTextureVariante = 1;
		}else {
			Images.choosenTextureVariante++;
		}
		
		Images.loadImages_Fields();
		
		for(int x = 0 ; x < StandardData.mapWidth ; x++) {
			for(int y = 0 ; y < StandardData.mapHight ; y++) {
				if(createMap == false) {
					//INGAME
					try{
						GameData.gameMap_FieldList[x][y].updateImage();
					}catch(ArrayIndexOutOfBoundsException error) { }
				}else {
					//CREATE MAP
					try{
						CreateMapData.createMap_FieldList[x][y].updateImage();
					}catch(ArrayIndexOutOfBoundsException error) { }
				}
			}
		}
		
	}
	
}
