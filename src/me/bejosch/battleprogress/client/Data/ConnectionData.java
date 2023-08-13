package me.bejosch.battleprogress.client.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConnectionData {

	public static final String DEFAULT_IP = "ipcwup.no-ip.biz";
	public static final int DEFAULT_PORT = 8998;
	
	public static final String ENCODING = "UTF-8";
	public static final int BUFFER_SIZE = 32768;
	public static final int PACKAGEIDLENGTH = 8;
	public static final int TIMEOUT_DELAY = 10*1000;
	public static final int DELAY_BETWEN_TRIES = 1000;
	public static final int MAX_CONNECTION_TRIES = 3;

	public static boolean serverConnectionEstablished = false;
	public static List<String> sendedDataList = new ArrayList<String>();
	
	public static long getNewPacketId() {
		
		//ALL NUMBERS BETWEEN 10000000 AND 100000000 COUNT
		long min = (long) Math.pow(10, PACKAGEIDLENGTH-1);
		long max = (long) Math.pow(10, PACKAGEIDLENGTH);
		return new Random().nextInt( (int) (max-min) ) + min;
	}
	
}
