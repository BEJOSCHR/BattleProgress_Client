package me.bejosch.battleprogress.client.Objects.Tasks.Building;

import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Objects.Buildings.Building;
import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.PathFinding.Path;
import me.bejosch.battleprogress.client.PathFinding.PathFinding_FieldObject;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Task_Building_Destroy extends Task_Building{

	public Task_Building_Destroy(Building connectedBuilding) {
		super(connectedBuilding, Images.taskIcon_Building_Destroy, "Destroy", 11, 3, null);
		String[] hoverText = {"This task destroys this building", "You gain back 1/3 of the building material cost","All tasks are executed at the end of the round"};
		this.hoverMessage = hoverText;
	}
	
	@Override
	public void action_Left_Press() {
		
		if(this.building.activeTask == null) {
			//ONLY SET TASK IF THERE IS NO ACTIVE TASK YET
			this.setToActiveTask();
			
			this.targetCoordinates = new FieldCoordinates(this.building.connectedField);
			List<PathFinding_FieldObject> pathWay = new ArrayList<>();
			this.targetPath = new Path(new FieldCoordinates(this.building.connectedField), new FieldCoordinates(this.building.connectedField), pathWay);
			
		}
		
	}
	
}
