package me.bejosch.battleprogress.client.PathFinding;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;

public class Path {

	public FieldCoordinates start, finish;
	public List<PathFinding_FieldObject> pathWay = null;
	
	public Path(FieldCoordinates start_, FieldCoordinates finish_, List<PathFinding_FieldObject> pathWay_) {
		
		this.start = start_;
		this.finish = finish_;
		this.pathWay = pathWay_;
		
	}
	
	public int getUsedSteps() {
		return pathWay.size();
	}
	
	public void drawWay(Graphics g, Color color) {
		
		PathFinding_FieldObject lastField = null;
		for(PathFinding_FieldObject newField : this.pathWay) {
			
			if(lastField == null) {
				lastField = newField;
			}else {
				int realX_last = Funktions.getPixlesByCoordinate(lastField.x, true, false)+StandardData.fieldSize/2;
				int realY_last = Funktions.getPixlesByCoordinate(lastField.y, false, false)+StandardData.fieldSize/2;
				int realX_new = Funktions.getPixlesByCoordinate(newField.x, true, false)+StandardData.fieldSize/2;
				int realY_new = Funktions.getPixlesByCoordinate(newField.y, false, false)+StandardData.fieldSize/2;
				
				g.setColor(color);
				g.drawLine(realX_last, realY_last, realX_new, realY_new);
				
//				if(StandardData.showGrid == true) {
//					g.setColor(Color.ORANGE);
//					g.setFont(new Font("Arial", Font.BOLD, 10));
//					g.drawString(""+newField.cost_G, realX_new-StandardData.fieldSize/3-12, realY_new-StandardData.fieldSize/4);
//					g.drawString(""+newField.cost_H, realX_new+StandardData.fieldSize/3-10, realY_new-StandardData.fieldSize/4);
//					g.drawString(""+newField.getCostF(), realX_new-8, realY_new-StandardData.fieldSize/4);
//				}
				
				lastField = newField;
			}
			
		}
		
	}
	
}
