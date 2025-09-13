package me.bejosch.battleprogress.client.Objects.Animations;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Data.Game.RoundData;
import me.bejosch.battleprogress.client.Enum.AnimationType;
import me.bejosch.battleprogress.client.Enum.MovingCircleDisplayTypes;
import me.bejosch.battleprogress.client.Enum.SpielModus;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Game.Handler.Game_RoundHandler;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;

public class Animation_MovingCircleDisplay extends Animation {

	public MovingCircleDisplayTypes displayType = null;
	
	public Color displayColor = null;
	public String calledName = "";
	public int value = 0;
	
	public FieldCoordinates goField = null;
	public FieldCoordinates toField = null;
	
	public int distanceTravledOnX = 0, yOffSet = 0;
	public double yOffSetFactor = 0.8D;
	public float distancePerTravelOnX = 1.5F;
	public int distancePerTravelOnY = 4; //ONLY USED IF THERE IS ONLY AN Y TRAVELING (SAME X LINE)
	public int waitingDelayAfterMovementEnd = 300; //IN MS
	
	public boolean repeating = false; //IF FALSE IT STOPPS IT SELF AFTER ONE ROUND, IF TRUE IT REPEATS UNTIL IT IS STOPPED MANUAL
	
	public Animation_MovingCircleDisplay(MovingCircleDisplayTypes displayType_, int value_, FieldCoordinates goField_, FieldCoordinates toField_, boolean repeating_) {
		super(AnimationType.MovingCircleDisplay);
		
		displayType = displayType_;
		
		value = value_;
		goField = goField_;
		toField = toField_;
		
		repeating = repeating_;
		
		//SET TYPE VALUES
		if(displayType != null) {
			switch (displayType) {
			case Repair:
				calledName = "Repair";
				displayColor = Color.GREEN;
				break;
			case Heal:
				calledName = "Healing";
				displayColor = Color.GREEN;
				break;
			case Damage:
				calledName = "Damage";
				displayColor = Color.RED;
				break;
			case Move:
				calledName = "Move";
				displayColor = Color.YELLOW;
				value = -1; //NO VALUE
				break;
			case Build:
				calledName = "Build";
				displayColor = Color.ORANGE;
				value = -1; //NO VALUE
				break;
			case Produce:
				calledName = "Produce";
				displayColor = Color.ORANGE;
				value = -1; //NO VALUE
				break;
			case Upgrade:
				calledName = "Upgrade";
				displayColor = new Color(255, 0, 255); //PURPLE
//				displayColor = new Color(160, 032, 240); //DARK PURPLE
				value = -1; //NO VALUE
				break;
			case Energy:
				calledName = "Energy";
				displayColor = new Color(102, 204, 255); //BLUE
				break;
			case Material:
				calledName = "Material";
				displayColor = Color.YELLOW;
				break;
			case Research:
				calledName = "Research";
				displayColor = new Color(255, 0, 255); //PURPLE
				break;
			case ProgressPoints:
				calledName = "ProgressPoints";
				displayColor = GameData.progressPoints_color;
				break;
			default:
				ConsoleOutput.printMessageInConsole("A movingCircleAnmiation has been created without a startTypeAction for this type [Type: "+displayType+"]", true);
				break;
			}
		}else {
			ConsoleOutput.printMessageInConsole("A movingCircleAnmiation has been created without a type [Type: "+displayType+"]", true);
		}
		
		if(this.goField == null || this.toField == null) {
			//ERROR - WRONG CALL
			this.cancle();
		}
		
	}
	
	@Override
	public void getParametersFromType() {
		
		speed = 10;
		
		super.getParametersFromType();
	}
	
	@Override
	public void changeAction() {
		
		if(cancled == true) { return; }
		if(this.goField == null || this.toField == null) { this.cancle(); } //Happens sometimes, no clue why
		
		int goPixleX = Funktions.getPixlesByCoordinate(goField.X, true, false);
		int goPixleY = Funktions.getPixlesByCoordinate(goField.Y, false, false);
		int toPixleX = Funktions.getPixlesByCoordinate(toField.X, true, false);
		int toPixleY = Funktions.getPixlesByCoordinate(toField.Y, false, false);
		
		if(goPixleX > toPixleX) {
			//GO ON THE RIGHT OF TO
			int movingFactor = (int) ( (float) distancePerTravelOnX / (float) getXTimeBalance() );
			if(distanceTravledOnX-movingFactor > ( toPixleX-goPixleX )) { //DISTANCE REACHED
				distanceTravledOnX -= movingFactor;
			}else {
				if(repeating == true) {
					//RESETT AND REPEAT
					distanceTravledOnX = 0;
				}else {
					//STOP - AFTER DELAY
					movmentFinished(waitingDelayAfterMovementEnd); //CANCLE DELAY
				}
			}
		}else if(goPixleX < toPixleX) {
			//GO ON THE LEFT OF TO
			int movingFactor = (int) ( (float) distancePerTravelOnX / (float) getXTimeBalance() );
			if(distanceTravledOnX+movingFactor < ( toPixleX-goPixleX )) { //DISTANCE REACHED
				distanceTravledOnX += movingFactor;
			}else {
				if(repeating == true) {
					//RESETT AND REPEAT
					distanceTravledOnX = 0;
				}else {
					//STOP - AFTER DELAY
					movmentFinished(waitingDelayAfterMovementEnd); //CANCLE DELAY
				}
			}
		}else {
			//ON THE SAME LEVEL AS TO ON THE X
			if(goPixleY > toPixleY) {
				//GO UNDER TO
				int movingFactor = (int) ( (float) distancePerTravelOnX / (float) getXTimeBalance() );
				if(getYValueByFunktion(distanceTravledOnX-movingFactor) > ( toPixleY-goPixleY )) { //DISTANCE REACHED
					distanceTravledOnX -= movingFactor;
				}else {
					if(repeating == true) {
						//RESETT AND REPEAT
						distanceTravledOnX = 0;
					}else {
						//STOP - AFTER DELAY
						movmentFinished(waitingDelayAfterMovementEnd); //CANCLE DELAY
					}
				}
			}else if(goPixleY < toPixleY) {
				//GO OVER TO
				int movingFactor = (int) ( (float) distancePerTravelOnX / (float) getXTimeBalance() );
				if(getYValueByFunktion(distanceTravledOnX+movingFactor) < ( toPixleY-goPixleY )) { //DISTANCE REACHED
					distanceTravledOnX += movingFactor;
				}else {
					if(repeating == true) {
						//RESETT AND REPEAT
						distanceTravledOnX = 0;
					}else {
						//STOP - AFTER DELAY
						movmentFinished(waitingDelayAfterMovementEnd); //CANCLE DELAY
					}
				}
			}else {
				//SAME FIELD (X==X AND Y==Y)
				yOffSet++;
				movmentFinished(1000*2+200); //DISPLAY DURATION
			}
		}
		
		super.changeAction();
	}
	
	private boolean movmentFinished = false;
	private void movmentFinished(int finishDelay) {
		
		if(movmentFinished == false) {
			movmentFinished = true;
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					
					//SHOWING - THEN CANCLE WITHOUT MOVEMENT
					cancle();
					
				}
			}, finishDelay);
		}
		
	}
	
	@Override
	public void drawPart(Graphics g) {
		
		if(cancled == true) { return; }
		
		int rand = (int) StandardData.fieldSize/2;
		int radius = 30;
		
		int goPixleX = Funktions.getPixlesByCoordinate(goField.X, true, false)+rand;
		int goPixleY = Funktions.getPixlesByCoordinate(goField.Y, false, false)+rand;
		int toPixleX = Funktions.getPixlesByCoordinate(toField.X, true, false)+rand;
		int toPixleY = Funktions.getPixlesByCoordinate(toField.Y, false, false)+rand;
		
		//SO RßCKEN DAS ES IN DER MITTE DER FELDER STARTET UND ENDET
		int pixleX = 0;
		int pixleY = 0;
		if(goPixleX-toPixleX == 0) {
			//ON THE SAME X LINE
			pixleX = goPixleX;
			pixleY = goPixleY+getYValueByFunktion(distanceTravledOnX)-((int)(yOffSet*yOffSetFactor));
		}else {
			//NORMAL FUNKTION
			pixleX = goPixleX+distanceTravledOnX;
			pixleY = goPixleY+getYValueByFunktion(distanceTravledOnX)-((int)(yOffSet*yOffSetFactor));
		}
		
		if(this.displayType != MovingCircleDisplayTypes.Material && this.displayType != MovingCircleDisplayTypes.Energy && this.displayType != MovingCircleDisplayTypes.Research && this.displayType != MovingCircleDisplayTypes.ProgressPoints) {
			//Dont highlight fields on ressource/progress animations (only troup/building related stuff)
			GameData.gameMap_FieldList[goField.X][goField.Y].drawHighlight(g, Color.YELLOW);
			GameData.gameMap_FieldList[toField.X][toField.Y].drawHighlight(g, Color.YELLOW);
		}
		g.setColor(Color.ORANGE);
		g.drawLine(goPixleX, goPixleY, toPixleX, toPixleY);
		
		g.setColor(Color.DARK_GRAY);
		g.fillOval(pixleX-radius/2, pixleY-radius/2, radius, radius);
		g.setColor(displayColor);
		g.drawOval(pixleX-radius/2, pixleY-radius/2, radius, radius);
		if(value >= 0) {
			//-1 INDICATES NO VALUE (used for movement task)
			g.setColor(displayColor);
			if(value >= 100) {
				//3 STELLIG
				g.setFont(new Font("Arial", Font.BOLD, 14));
				g.drawString(""+value, pixleX-11, pixleY+6);
			}else if(value >= 10) {
				//2 STELLIG
				g.setFont(new Font("Arial", Font.BOLD, 16));
				g.drawString(""+value, pixleX-8, pixleY+6);
			}else {
				//1 STELLIG
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString(""+value, pixleX-4, pixleY+6);
			}
		}
		/*g.setColor(Color.WHITE); //Player should be clever enough to understand the action without a name
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.drawString(""+calledName, pixleX-19, pixleY+28);*/
		
		super.drawPart(g);
	}
	
	@Override
	public void cancle() {
		
		if(cancled == false) {
			
			super.cancle();
			
			//FINISH EXECUTE TASK
			if(this.displayType == MovingCircleDisplayTypes.Energy || this.displayType == MovingCircleDisplayTypes.Material || this.displayType == MovingCircleDisplayTypes.Research) {
				Game_RoundHandler.endRoundEconomicsUpdate();
			}else if(this.displayType == MovingCircleDisplayTypes.ProgressPoints) {
				//Increase ProgressPointsDisplay
				int maxAmount = (SpielModus.isGameModus1v1() ? GameData.progressPoints_target_1v1 : GameData.progressPoints_target_2v2);
				GameData.progressPoints = Math.min(maxAmount, GameData.progressPoints+this.value); //Cap to maxAmount
			}else if(RoundData.currentExecuteTask != null) {
				RoundData.currentExecuteTask.performAction(); //PERFORM TASK
			}else {
				ConsoleOutput.printMessageInConsole("A movingCircleAnmiation ended and found no executeTask to perform action and is NOT Energy or Material or Research type [CurrentExecuteTask: "+RoundData.currentExecuteTask+"]", true);
			}
			
		}
		
	}
	
	public int getYValueByFunktion(int xValue) {
		
		int goPixleX = Funktions.getPixlesByCoordinate(goField.X, true, false);
		int goPixleY = Funktions.getPixlesByCoordinate(goField.Y, false, false);
		int toPixleX = Funktions.getPixlesByCoordinate(toField.X, true, false);
		int toPixleY = Funktions.getPixlesByCoordinate(toField.Y, false, false);
		
		if(goPixleX-toPixleX == 0) {
			if(goPixleY-toPixleY == 0) {
				//SAME FIELD SO SMALL MOVEMENT
				return 0;
			}
			//ON THE SAME X LINE
			return (distancePerTravelOnY)*xValue;
		}else {
			//NORMAL FUNKTION
			float steigung = (float) ((float) (goPixleY-toPixleY))/((float) (goPixleX-toPixleX));
			return (int)( ((float)steigung)*xValue );
		}
		
	}
	
	public float getXTimeBalance() {
		
		if(goField.X-toField.X == 0) {
			return 1;
		}
		
		int difference = goField.X-toField.X;
		if(difference < 0) { difference = difference*-1; } //MINUS ENTFERNEN
		float balanceFactor = ( ( (float) 1 / (int) difference) ) * ( (float) 1 ) ;
		return balanceFactor;
		
	}
	
}
