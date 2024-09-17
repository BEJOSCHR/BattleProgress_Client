package me.bejosch.battleprogress.client.Objects;

import me.bejosch.battleprogress.client.Enum.GameActionType;

public class GameAction {

	public int playerId;
	public GameActionType type;
	public int round;
	public int x, y, newX, newY, amount;
	public String text; 
	
	public int executeID = -1;
	
	public GameAction(String[] content) {
		
		//playerID;type;round;x;y;newX;newY;amount;text;executeID
		
		this.playerId = Integer.parseInt(content[0]);
		this.type = GameActionType.valueOf(content[1]);
		this.round = Integer.parseInt(content[2]);
		
		this.x = Integer.parseInt(content[3]);
		this.y = Integer.parseInt(content[4]);
		this.newX = Integer.parseInt(content[5]);
		this.newY = Integer.parseInt(content[6]);
		this.amount = Integer.parseInt(content[7]);
		
		this.text = content[8];
		this.executeID = Integer.parseInt(content[9]);
		
	}
	
}
