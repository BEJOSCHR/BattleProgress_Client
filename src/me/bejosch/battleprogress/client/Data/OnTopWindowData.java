package me.bejosch.battleprogress.client.Data;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import me.bejosch.battleprogress.client.Enum.ResearchCategory;
import me.bejosch.battleprogress.client.Objects.DictonaryInfoDescription;
import me.bejosch.battleprogress.client.Objects.FieldData;
import me.bejosch.battleprogress.client.Objects.UnitStatsContainer;
import me.bejosch.battleprogress.client.Objects.Chat.ChatHistory;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.OnTopWindow;
import me.bejosch.battleprogress.client.Objects.Research.UpgradeDataContainer;

public class OnTopWindowData {

	public static double animationSpeed = 0.03;
	public static boolean otwAnimationRunning = false; //CLOSE OR OPEN
	public static OnTopWindow onTopWindow = null;
	
	//MENU
	public static int menu_width = 300, menu_height = 400;
	public static int menu_buttonHeight = 50, menu_buttonWidth = (int) (menu_width*0.75);
	public static int menu_distanceBetweenButton = menu_buttonHeight+30;
	public static int menu_distanceToTopBorder = 80;
	
	//INFO MESSAGE
	public static int infoMessage_width = 725, infoMessage_height = 270;
	
	//LOGIN
	public static int login_width = 550, login_height = 400;
	public static int login_button_width = 130, login_button_spaceBetween = 30*2;
	public static int login_topSpacerHeight = 60, login_textFieldWidth = 350, login_textFieldHeight = 30, login_textFieldSectionHeight = 70;
	public static String login_message = "";
	public static String login_password = "benno"; //TODO!!!
	
	//CONFIRM SURRENDER
	public static int confSur_width = 577, confSur_height = 120;
	public static int confSur_MAA_yOffSet = 5, confSur_MAA_height = 45;
	
	//TABGAMEINFO
	public static int tabGameInfo_y = 150;
	public static int tabGameInfo_border = 20, tabGameInfo_borderBetween = 70, tabGameInfo_extraCenterWidth = 120;
	public static int tabGameInfo_colorBlockWidth = 30, tabGameInfo_sectionWidth = MenuData.mpd_profileImage_width+tabGameInfo_colorBlockWidth;
	public static int tabGameInfo_infoBlockHeight = 30, tabGameInfo_sectionHeight = MenuData.mpd_profileImage_height+tabGameInfo_infoBlockHeight;
	public static int tabGameInfo_gameInfoSectionHeight = 35;
	public static int tabGameInfo_width = tabGameInfo_border*2+tabGameInfo_borderBetween+2*tabGameInfo_sectionWidth+tabGameInfo_extraCenterWidth, tabGameInfo_height = tabGameInfo_border*2+tabGameInfo_borderBetween+2*tabGameInfo_sectionHeight+tabGameInfo_gameInfoSectionHeight;
	
	//ROUND SUMMARY
	public static int roundSum_width = 700, roundSum_height = 550;
	public static int roundSum_heightPerEco = 130;
	public static int roundSum_smallDistanceBetween = 25, roundSum_bigDistanceBetween = 45;
	public static int roundSum_details_width = 320, roundSum_details_udBorder = 20, roundSum_details_leftAmountBorder = 80;
	public static int roundSum_details_smallHeight = 30, roundSum_details_bigHeight = 40, roundSum_details_titelHeight = 20;
	
		//GENERAL CONFIRM BUTTON WIDTH
		public static int generalConfirm_MAA_width = 160, generallConfirm_MAA_height = 45;
	
	//SETTINGS
	public static int settings_width = 700, settings_height = 550;
	public static int settings_buttonBorder = 20, settings_buttonWidth = 130, settings_buttonHeight = 40;
	public static boolean settingsHasBeenModified = false;
	
	//MATERIAL OVERVIEW
	public static int materialOverview_width = 700, materialOverview_height = 550;
	
	//ENERGY OVERVIEW
	public static int energyOverview_width = 700, energyOverview_height = 550;
	
	//RESEARCH
	public static int research_width = WindowData.FrameWidth, research_height = WindowData.FrameHeight;
	
	public static int research_devideLine_yOffset = 55, research_devideLine_height = 50;
	public static int research_categoryButton_width = 135, research_categoryButton_borderBetween = 30, research_categoryButton_borderTop = 15, research_categoryButton_xOffset = 30;
	public static int research_TopInfoMAA_topBorder = 8, research_TopInfoMAA_sideBorder = 30;
	
	public static Color research_color_researched = Color.WHITE, research_color_researchable = new Color(68,216,255), research_color_locked = Color.BLACK;
	
	public static int research_slotStartX = (WindowData.FrameWidth/2)-(research_width/2)+90;
	public static int research_slotStartY = (WindowData.FrameHeight/2)-(research_height/2)+OnTopWindowData.research_devideLine_yOffset+OnTopWindowData.research_devideLine_height;
	public static int research_slotWidth = 260, research_slotHeight = 100;
	public static int research_slotBorderBetween = 70, research_slotBorderUpDown = 25;
	
	public static Color research_categoryButton_highlightColor = Color.WHITE;
	public static final int research_maxShownLines = 5;
	public static int research_scrollPos = 0;
	public static ResearchCategory research_category = ResearchCategory.Economie;
	
	//RESEARCH CONFIRM
	public static int researchConfirm_width = 820, researchConfirm_height = 300;
	public static int researchConfirm_buttonWidth = 140, researchConfirm_buttonHeight = 50;
	public static int researchConfirm_textBorder = 20;
	public static int researchConfirm_buttonBorderBetween = 15, researchConfirm_buttonBottomBorder = 20;
	
	//DICTIONARY
	public static int dictionary_scrollPos = 0;
	public static LinkedList<UnitStatsContainer> dictionary_usc_buildings = new LinkedList<UnitStatsContainer>();
	public static LinkedList<UnitStatsContainer> dictionary_usc_troups = new LinkedList<UnitStatsContainer>();
	public static LinkedList<UpgradeDataContainer> dictionary_udc_upgrades = new LinkedList<UpgradeDataContainer>();
	public static LinkedList<FieldData> dictionary_fd_fields = new LinkedList<FieldData>();
	public static LinkedList<DictonaryInfoDescription> dictionary_did_infos = new LinkedList<DictonaryInfoDescription>();
	public static int dictionary_titelSectionHeight = 50;
	public static int dictionary_sectionCount = 10, dictionary_sectionHeight = 70;
	public static int dictionary_width = 700, dictionary_height = (dictionary_sectionCount*dictionary_sectionHeight)+dictionary_titelSectionHeight;
	public static int dictionary_border = 10, dictionary_borderSmall = 10;
	public static int dictionary_textSize = 20, dictionary__textSizeSmall = 15;
	public static Color dictionary_titelColor = Color.ORANGE;
	public static Color dictionary_sectionTitelColor = Color.WHITE;
	
	//UNITDETAILINFO
	public static int unitDetailInfo_width = 700, unitDetailInfo_height = 600;
	public static int unitDetailInfo_imageBorderLeft = 20, unitDetailInfo_imageBorderTopDown = 20;
	public static int unitDetailInfo_imageWidthHeight = StandardData.fieldSize;
	public static int unitDetailInfo_titelSectionHeight = unitDetailInfo_imageBorderTopDown*2+unitDetailInfo_imageWidthHeight;
	public static int unitDetailInfo_titelBorderToImage = 50, unitDetailInfo_costBorderRight = 40;
	public static Font unitDetailInfo_descriptionFont = new Font("Arial", Font.CENTER_BASELINE, 18);
	public static int unitDetailInfo_descriptionBorderLeft = 20, unitDetailInfo_descriptionBorderTopDown = 20, unitDetailInfo_descriptionLineHeight = 20;
	public static int unitDetailInfo_categoriesBorderLeft = 20, unitDetailInfo_categoriesBorderTopDown = 20;
	public static int unitDetailInfo_categoriesHeight = 40, unitDetailInfo_categoriesWidth = (unitDetailInfo_width-unitDetailInfo_categoriesBorderLeft*2)/2;
	
	//GROUPINVITATION
	public static int groupInvitation_width = 850, groupInvitation_height = 150;
	public static int groupInvitation_buttonWidth = 140, groupInvitation_buttonHeight = 50;
	public static int groupInvitation_buttonBorderBetween = 50, groupInvitation_buttonBottomBorder = 20;
	
	//CHAT
	public static List<ChatHistory> cachedChats = new ArrayList<>();
	public static int chat_width = 700, chat_height = 550;
	public static int chat_sideBorders = 20, chat_downBorder = 100, chat_middleBorder = 2*20;
	public static int chat_messageArea_width = chat_width-(2*chat_sideBorders), chat_messageArea_height = chat_height-chat_downBorder;
	public static int chat_heightPerMessageRow = 25, chat_borderBetweenMessages = 10, chat_widthPerMessage = (chat_messageArea_width-chat_middleBorder)/2;
	public static int chat_messageFieldWidth = 330, chat_messageFieldBorders = 70, chat_sideButtonsWidth = (chat_width-chat_messageFieldWidth-chat_messageFieldBorders*2)/2;
	public static int chat_messageFieldHeight = 30, chat_messageFieldDownBorder = 15, chat_sideButtonsBorder = chat_sideBorders;
	
	//FRIENDADD
	public static int friendAdd_width = 820, friendAdd_height = 230;
	public static int friendAdd_buttonWidth = 140, friendAdd_buttonHeight = 40;
	public static int friendAdd_buttonBorderBetween = 50, friendAdd_buttonBottomBorder = 20;
	public static int friendAdd_textField_width = 400, friendAdd_textField_height = 40;
	public static int friendAdd_textFieldDownBorder = friendAdd_buttonBottomBorder+88;
	
	//FRIENDREQUESTS
	public static int friendRequests_width = 400, friendRequests_height = 500;
	public static int friendRequests_topBorder = 90, friendRequests_sideBorder = 20, friendRequests_totalSectionCount = 8;
	public static int friendRequests_sectionWidth = friendRequests_width-(2*friendRequests_sideBorder), friendRequests_sectionHeight = 50;
	public static int friendRequests_smallButtonMaße = 30, friendRequests_smallButtonBorder = (friendRequests_sectionHeight-friendRequests_smallButtonMaße)/2;
	public static int friendRequests_buttonWidth = 140, friendRequests_buttonHeight = 40, friendRequests_buttonDownBorder = 20;
	
	//FRIEND REMOVE - Uses same data as GroupInvitation
	
	//QUEUE WAITING
	public static int queueWaiting_width = 420, queueWaiting_height = 250;
	public static int queueWaiting_buttonWidth = 160, queueWaiting_buttonHeight = 40;
	public static int queueWaiting_buttonDownBorder = 20;
	
	//GAME ACCEPT
	public static int gameAccept_width = 650, gameAccept_height = 200;
	public static int gameAccept_buttonWidth = 140, gameAccept_buttonHeight = 40;
	public static int gameAccept_buttonBorderBetween = 50, gameAccept_buttonBottomBorder = 20;
	
}
