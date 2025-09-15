 package me.bejosch.battleprogress.client.PathFinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;

public class PathFinding_Algorithmus {
	
	public static final int cost_straight = 10, cost_diagonal = 14;
	
	public FieldCoordinates start, finish;
	public LinkedList<PathFinding_FieldObject> open = new LinkedList<PathFinding_FieldObject>();
	public LinkedList<PathFinding_FieldObject> close = new LinkedList<PathFinding_FieldObject>();
	
	boolean cancled = false;
	
	public PathFinding_Algorithmus(FieldCoordinates start_, FieldCoordinates finish_, boolean cancleIfTargetNotVisible) {
		
		this.start = start_;
		this.finish = finish_;
		
		if(this.finish.getConnectedField().visible == false && cancleIfTargetNotVisible == true) {
			cancled = true;
		}
		
	}
	
	public PathFinding_Algorithmus(Field start_, Field finish_, boolean cancleIfTargetNotVisible) {
		
		this.start = new FieldCoordinates(start_);
		this.finish = new FieldCoordinates(finish_);
		
		if(this.finish.getConnectedField().visible == false && cancleIfTargetNotVisible == true) {
			cancled = true;
		}
		
	}

	public Path getPath(int maxSichtweite, boolean ignoreOtherBuildingOrTroup, boolean canGoOverWater) {
		
		if(cancled == true) {
			LinkedList<PathFinding_FieldObject> pathWay = new LinkedList<>();
			pathWay.add(new PathFinding_FieldObject(this, this.start.X, this.start.Y, 0));
			return new Path(this.start, this.finish, pathWay);
		}
		
		//START - ADD START NOTE
		open.add(new PathFinding_FieldObject(this, start.X, start.Y, 0));
		
		//CALCULATE FIELDS
		while(true) {
			
			PathFinding_FieldObject current = getFieldWithLowestFcost();
			
			if(current == null) {
				//INCOMPLETE / NO WAY FOUND
				LinkedList<PathFinding_FieldObject> pathWay2 = new LinkedList<>();
				return new Path(this.start, this.finish, pathWay2);
			}
			this.open.remove(current);
			this.close.add(current);
			
			if(this.finish.X == current.x && this.finish.Y == current.y) {
				break; //FOUND TARGET - ENDE
			}else {
				
				for(PathFinding_FieldObject neighbour : getFieldNeighbours(current)) {
					//FOR EACH NEIGHBOUR
					
					if(getFieldOutOfList(this.close, neighbour) != null || fieldIsMoveable(neighbour, ignoreOtherBuildingOrTroup, canGoOverWater) == false) {
						//UNMOVEABLE or closed - SKIP
					}else {
						//CALCULATE
						PathFinding_FieldObject neigbourInOpenList = getFieldOutOfList(this.open, neighbour);
						if(neigbourInOpenList == null) {
							//NOCH NICHT IN OPEN - ADDEN
							neighbour.parent = current;
							this.open.add(neighbour);
						}else if(neighbour.getCostF() < neigbourInOpenList.getCostF()) {
							//GERINGERE F COST ALS DER ALTE WEG - AKTUALIESIEREN
							neigbourInOpenList.parent = current;
							neigbourInOpenList.cost_G = neighbour.cost_G;
							neigbourInOpenList.setCostF(neighbour.getCostF());
						}
					}
					
				}
				
			}
			
		}
		
		//RECREATE PATH
		LinkedList<PathFinding_FieldObject> pathWay = new LinkedList<>();
		PathFinding_FieldObject lastField = this.close.get(this.close.size()-1);
		while(true) {
			//SOLANGE BIS START ERREICHT IST
			pathWay.add(lastField);
			lastField = lastField.parent;
			if(lastField == null) {
				//INCOMPLETE PATH
				LinkedList<PathFinding_FieldObject> pathWay1 = new LinkedList<>();
				pathWay1.add(new PathFinding_FieldObject(this, this.start.X, this.start.Y, 0)); // FILLED FOR SHOWING IT SELF
				return new Path(this.start, this.finish, pathWay1);
			}
			if(lastField.x == this.start.X && lastField.y == this.start.Y) {
				pathWay.add(lastField);
				break;
			}
		}
		
		return new Path(this.start, this.finish, pathWay);
		
	}
	
	public PathFinding_FieldObject getFieldOutOfList(List<PathFinding_FieldObject> list, PathFinding_FieldObject target) {
		
		for(PathFinding_FieldObject field : list) {
			if(field.x == target.x && field.y == target.y) {
				return field;
			}
		}
		
		return null;
		
	}
	
	public List<PathFinding_FieldObject> getFieldNeighbours(PathFinding_FieldObject center) {
		
		List<PathFinding_FieldObject> output = new ArrayList<>();
		
		if(center.x > 0) {
			//LINKS
			if(center.y > 0) {
				//LINKS UNTEN
				output.add(new PathFinding_FieldObject(this, center.x-1, center.y-1, center.cost_G+PathFinding_Algorithmus.cost_diagonal));
			}
			if(center.y < StandardData.mapHight-1) {
				//LINKS OBEN
				output.add(new PathFinding_FieldObject(this, center.x-1, center.y+1, center.cost_G+PathFinding_Algorithmus.cost_diagonal));
			}
			//LINKS
			output.add(new PathFinding_FieldObject(this, center.x-1, center.y, center.cost_G+PathFinding_Algorithmus.cost_straight));
			
		}
		if(center.x < StandardData.mapWidth-1) {
			//RECHTS
			if(center.y > 0) {
				//RECHTS UNTEN
				output.add(new PathFinding_FieldObject(this, center.x+1, center.y-1, center.cost_G+PathFinding_Algorithmus.cost_diagonal));
			}
			if(center.y < StandardData.mapHight-1) {
				//RECHTS OBEN
				output.add(new PathFinding_FieldObject(this, center.x+1, center.y+1, center.cost_G+PathFinding_Algorithmus.cost_diagonal));
			}
			//RECHTS
			output.add(new PathFinding_FieldObject(this, center.x+1, center.y, center.cost_G+PathFinding_Algorithmus.cost_straight));
			
		}
		if(center.y < StandardData.mapHight-1) {
			//OBEN
			output.add(new PathFinding_FieldObject(this, center.x, center.y+1, center.cost_G+PathFinding_Algorithmus.cost_straight));
		}
		if(center.y > 0) {
			//UNTEN
			output.add(new PathFinding_FieldObject(this, center.x, center.y-1, center.cost_G+PathFinding_Algorithmus.cost_straight));
		}
		
		return output;
		
	}
	
	
	public PathFinding_FieldObject getFieldWithLowestFcost() {
		
		PathFinding_FieldObject lowest = null;
		for(PathFinding_FieldObject field : this.open) {
			if(lowest == null) {
				lowest = field;
			}else if(field.getCostF() < lowest.getCostF()) {
				lowest = field;
			}
		}
		return lowest;
	}
	
	
	public boolean fieldIsMoveable(PathFinding_FieldObject field, boolean ignoreOtherBuildingOrTroup, boolean canGoOverWater) {
		Field realField = new FieldCoordinates(field.x, field.y).getConnectedField();
		
		if(realField.type == FieldType.Mountain) {
			//MOUNTAIN
			return false;
		}else if(canGoOverWater == false && realField.type == FieldType.Ocean) {
			//WATER
			return false;
		}else {
			//BLOCKED PLACE
			if(ignoreOtherBuildingOrTroup == false) {
				if(realField.building != null || realField.troup != null) {
					return false;
				}else {
					return true;
				}
			}else {
				return true;	
			}
		}
	}
	
	
	//TODO: NUR END DISTANCE BERECHNEN WEIL START VON VORHERIGEN FIELDS ABGELEITET WERDEN KANN
	public int calculateMovecost_End(FieldCoordinates target) {
		return calculateMoveCost(target, this.finish);
	}
	
	private int calculateMoveCost(FieldCoordinates from, FieldCoordinates to) {
		
		int difX = getPositiveNumber(from.X-to.X);
		int difY = getPositiveNumber(from.Y-to.Y);
		
		int diagonaleSchritte = 0;
		int geradeSchritte = 0;
		
		while(true) {
			
			if(difX <= 0 && difY <= 0) {
				//X UND Y FERTIG
				geradeSchritte = 0;
				break;
			}else if(difX <= 0) {
				//X FERTIG
				geradeSchritte = difY;
				break;
			}else if(difY <= 0) {
				//Y FERTIG
				geradeSchritte = difX;
				break;
			}
			
			//NOCH NICHT DURCH
			difX--;
			difY--;
			
			diagonaleSchritte++;
			
		}
		
		int count = (diagonaleSchritte*PathFinding_Algorithmus.cost_diagonal) + (geradeSchritte*PathFinding_Algorithmus.cost_straight);
		return count;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public int getPositiveNumber(int i) {
		if(i < 0) {
			return i*-1;
		}else {
			return i;
		}
	}
	
}
