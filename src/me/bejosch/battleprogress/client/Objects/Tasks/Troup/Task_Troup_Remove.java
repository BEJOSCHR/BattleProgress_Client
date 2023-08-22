package me.bejosch.battleprogress.client.Objects.Tasks.Troup;

import java.util.ArrayList;
import java.util.List;

import me.bejosch.battleprogress.client.Objects.Field.FieldCoordinates;
import me.bejosch.battleprogress.client.Objects.Field.FieldMessage;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;
import me.bejosch.battleprogress.client.PathFinding.Path;
import me.bejosch.battleprogress.client.PathFinding.PathFinding_FieldObject;
import me.bejosch.battleprogress.client.Window.Images.Images;

public class Task_Troup_Remove extends Task_Troup{

	public Task_Troup_Remove(Troup connectedTroup) {
		super(connectedTroup, Images.taskIcon_Troup_Remove, "Remove", 11, false, false, 3, null);
		String[] hoverText = {"This task removes this troup from the game", "You gain back 1/3 of the troup producing cost", "All tasks are executed at the end of the round"};
		this.hoverMessage = hoverText;
	}
	
	@Override
	public void action_Left_Press() {
		
		if(this.troup.activeTask != null) { this.troup.activeTask.action_Right_Release(); }
		
		if(this.troup.targetUpgradePosition == null) {
			//NOT TARGETED AS AN UPGRADE PARTNER
			this.setToActiveTask();
			
			this.targetCoordinates = new FieldCoordinates(this.troup.connectedField);
			List<PathFinding_FieldObject> pathWay = new ArrayList<>();
			this.targetPath = new Path(new FieldCoordinates(this.troup.connectedField), new FieldCoordinates(this.troup.connectedField), pathWay);
		}else {
			//TARGET OF AN UPGRADE
			new FieldMessage("Blocked by an upgrade", this.troup.targetUpgradePosition.X, this.troup.targetUpgradePosition.Y, 3);
			this.removeFromActiveTask();
		}
		
	}
	
}
