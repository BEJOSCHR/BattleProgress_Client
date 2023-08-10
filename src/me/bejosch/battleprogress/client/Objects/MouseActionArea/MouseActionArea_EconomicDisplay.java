package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Color;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Enum.SpielStatus;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.Checkbox.MouseActionArea_Checkbox;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.EnergyOverview.OnTopWindow_EnergyOverview;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.MaterialOverview.OnTopWindow_MaterialOverview;
import me.bejosch.battleprogress.client.Objects.OnTopWindow.Research.OnTopWindow_Research;

public class MouseActionArea_EconomicDisplay extends MouseActionArea {

	private int position = 0;
	
	/**
	 * Showing the choosen type of ressource next to the minimap
	 * @param position - int - 1=top=Material, 2=mid=Energy, 3=bottom=Research
	 */
	public MouseActionArea_EconomicDisplay(int position) {
		super(0, 0, 0, 0, null, null, ShowBorderType.ShowOnHover, null, Color.WHITE);
		
		this.position = position;
		
		//------------------------
		int x = 0, y = 0;
		if( ((MouseActionArea_Checkbox) GameHandler.getMouseActionAreaByName("Checkbox_MiniMap_ShowAll")).getCurrentState() == true ) {
			//MINIMAP SHOWN
			x = WindowData.FrameWidth-GameData.ecoDisp_width-GameData.ecoDisp_rightBorder-StandardData.maße-StandardData.rahmen;
		}else {
			//NO MINIMAP SHOWN
			x = WindowData.FrameWidth-GameData.ecoDisp_width-GameData.ecoDisp_rightBorder;
		}
		y = WindowData.FrameHeight-GameData.ecoDisp_totalDownBorder;
		this.xTL = x;
		this.xBR = x+GameData.ecoDisp_width;
		this.yTL = y+(this.position-1)*GameData.ecoDisp_distanceTotalSegment;
		this.yBR = y+(this.position-1)*GameData.ecoDisp_distanceTotalSegment+GameData.ecoDisp_height;
		//------------------------
		
		switch(position) {
		case 1: //MATERIAL
			this.idName = "MAA_ecoDisp_Material";
			String[] hoverText1 = {"Material"};
			this.hoverText = hoverText1;
			break;
		case 2: //ENERGY
			this.idName = "MAA_ecoDisp_Energy";
			String[] hoverText2 = {"Energy"};
			this.hoverText = hoverText2;
			break;
		case 3: //RESEARCH
			this.idName = "MAA_ecoDisp_Research";
			String[] hoverText3 = {"ResearchPoints"};
			this.hoverText = hoverText3;
			break;
		}
		
	}

	@Override
	public void draw(Graphics g) {
		
		//UPDATE X CORD
		//------------------------
		int x = 0;
		if( ((MouseActionArea_Checkbox) GameHandler.getMouseActionAreaByName("Checkbox_MiniMap_ShowAll")).getCurrentState() == true ) {
			//MINIMAP SHOWN
			x = WindowData.FrameWidth-GameData.ecoDisp_width-GameData.ecoDisp_rightBorder-StandardData.maße-StandardData.rahmen;
		}else {
			//NO MINIMAP SHOWN
			x = WindowData.FrameWidth-GameData.ecoDisp_width-GameData.ecoDisp_rightBorder;
		}
		this.xTL = x;
		this.xBR = x+GameData.ecoDisp_width;
		//------------------------
		
		super.draw(g);
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		switch(position) {
		case 1: //MATERIAL
			OnTopWindowHandler.openOTW(new OnTopWindow_MaterialOverview());
			break;
		case 2: //ENERGY
			OnTopWindowHandler.openOTW(new OnTopWindow_EnergyOverview());
			break;
		case 3: //RESERACH
			OnTopWindowHandler.openOTW(new OnTopWindow_Research());
			break;
		}
		
	}
	
	@Override
	public boolean isActiv() {
		if(StandardData.spielStatus == SpielStatus.Game) {
			return true;
		}
		return false;
	}
	
}
