package me.bejosch.battleprogress.client.Game.Handler;

import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import me.bejosch.battleprogress.client.Objects.DictonaryInfoDescription;
import me.bejosch.battleprogress.client.ServerConnection.MinaClient;

public class Game_DictionaryHandler {
	
	//DATA CONTAINER
	
	public static void loadDictionaryInfoDescriptions() {
		
		GameData.dictonaryInfoDescriptions.clear();
		MinaClient.sendData(112, "Request DictionaryInfoDescription update");
		
	}
	
	public static DictonaryInfoDescription getDictionaryInfoDescription(String titel) {
		
		for(DictonaryInfoDescription did : GameData.dictonaryInfoDescriptions) {
			if(did.titel.equals(titel)) {
				return did;
			}
		}
		
		ConsoleOutput.printMessageInConsole("Could not find DictionaryInfoDescriptions for titel "+titel+"! Is it loaded yet?", true);
		return null;
	}
	
}
