package me.bejosch.battleprogress.client.PathFinding;

import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;

public class PathFinding_FieldObject {
	
	public PathFinding_Algorithmus algorithm = null;
	
	public PathFinding_FieldObject parent = null;
	
	public int x = 0, y = 0;
	
	public int cost_G = 1000; //TO START
	public int cost_H = 1000; //TO END
	
	private int cost_F = 1000; //GESAMT
	private boolean calculatedCostF = false;
	
	public PathFinding_FieldObject(PathFinding_Algorithmus algorithm_, int x_, int y_, int cost_G_) {
		
		this.algorithm = algorithm_;
		
		this.x = x_;
		this.y = y_;
		
		this.cost_G = cost_G_;
		
	}
	
	public void setCostF(int newCostF) {
		
		calculatedCostF = true;
		this.cost_F = newCostF;
		
	}
	
	public int getCostF() {
		if(calculatedCostF == false) {
			calculatedCostF = true;
			
			this.cost_H = algorithm.calculateMovecost_End(new FieldCoordinates(x, y));
			this.cost_F = this.cost_G + this.cost_H;
			
		}
		return this.cost_F;
	}
	
	public FieldCoordinates getReferencedFieldCoordinates() {
		
		return new FieldCoordinates(this.x, this.y);
		
	}
	
}
