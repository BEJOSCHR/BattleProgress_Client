package me.bejosch.battleprogress.client.DiscordAPI;

import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class DiscordAPI {

	public static boolean apiLoaded = false;
	
	public static void initAPI() { 
		
		DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().build();
		DiscordRPC.discordInitialize(StandardData.DISCORD_API_KEY, handlers, true);
		apiLoaded = true;
		ConsoleOutput.printMessageInConsole("DiscordAPI initialised!", true);
		
	}
	
	public static void stopAPI() { 
		
		DiscordRPC.discordShutdown();
		ConsoleOutput.printMessageInConsole("DiscordAPI stopped!", true);
		
	}
	
	public static void setNewPresence(String state, String details, String bigImageName, String bigImageText, String smallImageName, String smallImageText, long timeStamp) {
		
		if(apiLoaded == false) { return; }
		
		DiscordRichPresence rich = new DiscordRichPresence.Builder(state)
				.setDetails(details)
				.setBigImage(bigImageName, bigImageText)
				.setSmallImage(smallImageName, smallImageText)
				.setStartTimestamps(timeStamp)
				.build();
		DiscordRPC.discordUpdatePresence(rich);
		
//		ConsoleOutput.printMessageInConsole("Update RichPresent! ("+state+", "+details+")", true);
		
	}
	
//	public static class ReadyEvent implements ReadyCallback {
//		@Override
//		public void apply(DiscordUser user) {
//			ConsoleOutput.printMessageInConsole("DC is ready!", true);
//		}
//	}
	
}
