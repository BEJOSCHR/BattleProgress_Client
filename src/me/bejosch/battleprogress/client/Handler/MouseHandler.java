package me.bejosch.battleprogress.client.Handler;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import me.bejosch.battleprogress.client.Data.OnTopWindowData;
import me.bejosch.battleprogress.client.Data.SpectateData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Enum.SpielStatus;
import me.bejosch.battleprogress.client.Game.Handler.GameHandler;
import me.bejosch.battleprogress.client.Objects.MouseActionArea.MouseActionArea;

public class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

	public static int mouseX = 0;
	public static int mouseY = 0;
	
	public static Point mousePressPoint = null;
	public static Point mouseReleasePoint = null;
	
	static int mouseXausgleich = 0;
	static int mouseYausgleich = 0;
//	static int mouseXausgleich = -3;
//	static int mouseYausgleich = -26;
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		int turns = e.getWheelRotation();
		if(turns > 0) {
			//WHEEL TURNED DOWN
			mouseWheelTurned(false);
		}else {
			//WHEEL TURNED UP
			mouseWheelTurned(true);
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
//		mousePressPoint = new Point(e.getX()+mouseXausgleich, e.getY()+mouseYausgleich);
		
		mousePressed(e.getX()+mouseXausgleich, e.getY()+mouseYausgleich, e.getButton());
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
//		mouseReleasePoint = new Point(e.getX()+mouseXausgleich, e.getY()+mouseYausgleich);
		
		mouseClick(e.getX()+mouseXausgleich, e.getY()+mouseYausgleich, e.getButton());
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMove(e.getX()+mouseXausgleich, e.getY()+mouseYausgleich);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseMove(e.getX()+mouseXausgleich, e.getY()+mouseYausgleich);
	}

//==========================================================================================================
	/**
	 * Called if the mouse is released from a click
	 */
	public static void mouseClick(int mX, int mY, int clickType) {
		
		if(OnTopWindowData.onTopWindow != null) { //GENERAL OTW  PRIO
			//HAS OTW OPEN
			
			if(clickType == MouseEvent.BUTTON1) {
				//LEFT
				OnTopWindowData.onTopWindow.onMouseRelease_LEFT(mX, mY);
				for(MouseActionArea OTW_actionArea : GameHandler.getOnlyOTWMouseActionAreasFromScreenCoordinates(mX, mY)) {
					OTW_actionArea.performAction_LEFT_RELEASE();
				}
			}else if(clickType == MouseEvent.BUTTON3) {
				//RIGHT
				OnTopWindowData.onTopWindow.onMouseRelease_RIGHT(mX, mY);
				for(MouseActionArea OTW_actionArea : GameHandler.getOnlyOTWMouseActionAreasFromScreenCoordinates(mX, mY)) {
					OTW_actionArea.performAction_RIGHT_RELEASE();
				}
			}
			
		}else if(StandardData.spielStatus == SpielStatus.Menu) {
			
			for(MouseActionArea actionArea : GameHandler.getMouseActionAreasFromScreenCoordinates(mX, mY)) {
				if(clickType == MouseEvent.BUTTON1) {
					actionArea.performAction_LEFT_RELEASE();
				}else if(clickType == MouseEvent.BUTTON3) {
					actionArea.performAction_RIGHT_RELEASE();
				}
			}
			
			MenuHandler.mouseClickedEvent(mX, mY, clickType);
			
		}else if(StandardData.spielStatus == SpielStatus.CreateMap) {
			
			for(MouseActionArea actionArea : GameHandler.getMouseActionAreasFromScreenCoordinates(mX, mY)) {
				if(clickType == MouseEvent.BUTTON1) {
					actionArea.performAction_LEFT_RELEASE();
				}else if(clickType == MouseEvent.BUTTON3) {
					actionArea.performAction_RIGHT_RELEASE();
				}
			}
			
			CreateMapHandler.mouseClickedEvent(mX, mY, clickType);
			
		}else if(StandardData.spielStatus == SpielStatus.Game) {
			
			//MAA are updated later deep in the game click handle sequence to allow better leveling
			
			GameHandler.mouseClickedEvent(mX, mY, clickType);
			
		}else if(StandardData.spielStatus == SpielStatus.Spectate) {
			
			for(MouseActionArea actionArea : GameHandler.getMouseActionAreasFromScreenCoordinates(mX, mY)) {
				if(clickType == MouseEvent.BUTTON1) {
					actionArea.performAction_LEFT_RELEASE();
				}else if(clickType == MouseEvent.BUTTON3) {
					actionArea.performAction_RIGHT_RELEASE();
				}
			}
			
			if(clickType == MouseEvent.BUTTON1) {
				SpectateData.clickedField = SpectateHandler.getFieldByScreenCoordinates(mX, mY);
			}else {
				SpectateData.clickedField = null;
			}
			
		}else if(StandardData.spielStatus == SpielStatus.Replay) {
			
			for(MouseActionArea actionArea : GameHandler.getMouseActionAreasFromScreenCoordinates(mX, mY)) {
				if(clickType == MouseEvent.BUTTON1) {
					actionArea.performAction_LEFT_RELEASE();
				}else if(clickType == MouseEvent.BUTTON3) {
					actionArea.performAction_RIGHT_RELEASE();
				}
			}
			
			//TODO
			
		}
		
	}
	
//==========================================================================================================
	/**
	 * Called if the mouse is pressed
	 */
	public static void mousePressed(int mX, int mY, int clickType) {
		
		if(OnTopWindowData.onTopWindow != null) {
			//HAS OTW OPEN
			
			if(clickType == MouseEvent.BUTTON1) {
				//LEFT
				OnTopWindowData.onTopWindow.onMousePress_LEFT(mX, mY);
				for(MouseActionArea OTW_actionArea : GameHandler.getOnlyOTWMouseActionAreasFromScreenCoordinates(mX, mY)) {
					OTW_actionArea.performAction_LEFT_PRESS();
				}
			}else if(clickType == MouseEvent.BUTTON3) {
				//RIGHT
				OnTopWindowData.onTopWindow.onMousePress_RIGHT(mX, mY);
				for(MouseActionArea OTW_actionArea : GameHandler.getOnlyOTWMouseActionAreasFromScreenCoordinates(mX, mY)) {
					OTW_actionArea.performAction_RIGHT_PRESS();
				}
			}
			
		}else if(StandardData.spielStatus == SpielStatus.Menu) {
			
			for(MouseActionArea actionArea : GameHandler.getMouseActionAreasFromScreenCoordinates(mX, mY)) {
				if(clickType == MouseEvent.BUTTON1) {
					actionArea.performAction_LEFT_PRESS();
				}else if(clickType == MouseEvent.BUTTON3) {
					actionArea.performAction_RIGHT_PRESS();
				}
			}
			
			MenuHandler.mousePressedEvent(mX, mY, clickType);
			
		}else if(StandardData.spielStatus == SpielStatus.CreateMap) {
			
			for(MouseActionArea actionArea : GameHandler.getMouseActionAreasFromScreenCoordinates(mX, mY)) {
				if(clickType == MouseEvent.BUTTON1) {
					actionArea.performAction_LEFT_PRESS();
				}else if(clickType == MouseEvent.BUTTON3) {
					actionArea.performAction_RIGHT_PRESS();
				}
			}
			
			CreateMapHandler.mousePressedEvent(mX, mY, clickType);
				
		}else if(StandardData.spielStatus == SpielStatus.Game) {
			
			GameHandler.mousePressedEvent(mX, mY, clickType);
			
		}else if(StandardData.spielStatus == SpielStatus.Spectate) {
			
			
			
		}else if(StandardData.spielStatus == SpielStatus.Replay) {
			
			
			
		}
		
	}
	
//==========================================================================================================
	/**
	 * Called if the mouse is moved
	 */
	public static void mouseMove(int mX, int mY) {
		
		mouseX = mX;
		mouseY = mY;
		
		if(OnTopWindowData.onTopWindow != null) {
			
			OnTopWindowData.onTopWindow.onMouseMove(mX, mY);
			
			for(MouseActionArea OTW_actionArea : GameHandler.getOnlyOTWMouseActionAreasFromScreenCoordinates(mX, mY)) {
				OTW_actionArea.performAction_HOVER();;
			}
			
		}else if(StandardData.spielStatus == SpielStatus.Menu) {
			
			for(MouseActionArea actionArea : GameHandler.getMouseActionAreasFromScreenCoordinates(mX, mY)) {
				actionArea.performAction_HOVER();
			}
			
			MenuHandler.mouseMovedEvent(mX, mY);
			
		}else if(StandardData.spielStatus == SpielStatus.CreateMap) {
			
			for(MouseActionArea actionArea : GameHandler.getMouseActionAreasFromScreenCoordinates(mX, mY)) {
				actionArea.performAction_HOVER();
			}
			
			CreateMapHandler.mouseMovedEvent(mX, mY);
			
		}else if(StandardData.spielStatus == SpielStatus.Game) {
			
			GameHandler.mouseMovedEvent(mX, mY);
			
		}else if(StandardData.spielStatus == SpielStatus.Spectate) {
			
			for(MouseActionArea actionArea : GameHandler.getMouseActionAreasFromScreenCoordinates(mX, mY)) {
				actionArea.performAction_HOVER();
			}
			
			SpectateData.hoveredField = SpectateHandler.getFieldByScreenCoordinates(mX, mY);
			
		}else if(StandardData.spielStatus == SpielStatus.Replay) {
			
			for(MouseActionArea actionArea : GameHandler.getMouseActionAreasFromScreenCoordinates(mX, mY)) {
				actionArea.performAction_HOVER();
			}
			
			//TODO
			
		}
		
	}
	
//==========================================================================================================
	/**
	 * Called if the mouseWheel has been moved
	 */
	public static void mouseWheelTurned(boolean scrollUp) {
		
		if(OnTopWindowData.onTopWindow != null) {
			
			OnTopWindowData.onTopWindow.onMouseWheelTurned(scrollUp);
			
		}else if(StandardData.spielStatus == SpielStatus.Menu) {
			
			MenuHandler.mouseWheelTurnEvent(scrollUp);
			
		}else if(StandardData.spielStatus == SpielStatus.Game) {
			
			
			
		}else if(StandardData.spielStatus == SpielStatus.Spectate) {
			
			
			
		}else if(StandardData.spielStatus == SpielStatus.Replay) {
			
			
			
		}
		
	}
	
}
