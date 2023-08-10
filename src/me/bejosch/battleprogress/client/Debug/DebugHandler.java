package me.bejosch.battleprogress.client.Debug;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class DebugHandler {

	public static final String consolePrefix = "-> ";
	
	public static int LastListLength = 0;
	
	public static List<String> DebugList = new ArrayList<String>();
	
//==========================================================================================================
	/**
	 * Updates the content of the displayed TextArea
	 */
	public static void updateTextArea() {
		
		if(LastListLength != DebugList.size()) {
			String data = "";
			try{
				for(String value : DebugList) {
					data = data+value+"\n";
				}
			}catch (ConcurrentModificationException e) { }
			DebugWindow.DebugTextArea.setText(data);
			LastListLength = DebugList.size();
		}
		
	}
	
//==========================================================================================================
	/**
	 * Send a console message in the debug console
	 * @param text - String - The text which should be debugged
	 */
	public static void printConsole(String text) {
		
		DebugList.add(consolePrefix+text);
		
	}
	
}
