package me.bejosch.battleprogress.client.Game.Handler;

import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.FieldType;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.FieldData;
import me.bejosch.battleprogress.client.ServerConnection.MinaClient;

public class Game_FieldDataHandler {
	
	//DATA CONTAINER
	
	public static void loadFieldData() {
		
		GameData.fieldData.clear();
		MinaClient.sendData(113, "Request FieldData update");
		
	}
	
	public static FieldData getFieldData(FieldType type) {
		
		for(FieldData df : GameData.fieldData) {
			if(df.titel.equals(type.toString())) {
				return df;
			}
		}
		
		ConsoleOutput.printMessageInConsole("Could not find FieldData for type "+type+"! Is it loaded yet?", true);
		//return new UpgradeDataContainer(type, 9999, 0);
		return null;
	}
	
}
