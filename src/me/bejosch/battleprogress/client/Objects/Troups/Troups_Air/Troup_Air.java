package me.bejosch.battleprogress.client.Objects.Troups.Troups_Air;

import me.bejosch.battleprogress.client.Enum.TroupType;
import me.bejosch.battleprogress.client.Objects.Field.Field;
import me.bejosch.battleprogress.client.Objects.Troups.Troup;

public class Troup_Air extends Troup{

	public Troup_Air(int playerID_, Field connectedField_) {
		super(playerID_, connectedField_, TroupType.AIR);
	}
	
	@Override
	public void load_TypeSettings() {
		canFly = true;
		super.load_TypeSettings();
	}	
	
}
