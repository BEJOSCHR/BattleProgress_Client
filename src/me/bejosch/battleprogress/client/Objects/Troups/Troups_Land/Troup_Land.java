package me.bejosch.battleprogress.client.Objects.Troups.Troups_Land;

import me.bejosch.battleprogress.client.Enum.TroupType;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;

public class Troup_Land extends Troup {

	
	public Troup_Land(int playerID_, Field connectedField_) {
		super(playerID_, connectedField_, TroupType.LAND);
	}
	
	@Override
	public void load_TypeSettings() {
		canFly = false;
		super.load_TypeSettings();
	}	
	
}
