package me.bejosch.battleprogress.client.Objects.MouseActionArea;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bejosch.battleprogress.client.Data.Game.GameData;
import me.bejosch.battleprogress.client.Enum.ShowBorderType;
import me.bejosch.battleprogress.client.Funktions.Funktions;
import me.bejosch.battleprogress.client.ServerConnection.MinaClient;
import me.bejosch.battleprogress.client.Window.TextFields.TextFields;

public class MouseActionArea_Chat_Send extends MouseActionArea {
	
//==========================================================================================================
	public MouseActionArea_Chat_Send() {
		super(GameData.chatX_show+5, GameData.chatY_show,
				GameData.chatX_show+5+50, GameData.chatY_show+20,
				"Chat_Send", null, ShowBorderType.ShowAlways, Color.WHITE, Color.GREEN.darker());
		
		
		
	}
	
	@Override
	public void performAction_LEFT_RELEASE() {
		
		if(TextFields.textField_Chat.getText().replace(" ", "").length() > 0) {
			
			String message = TextFields.textField_Chat.getText();
			if(message.length() > 100) { //MAX ZEICHEN
				message = message.substring(0, 99);
			}
			message = Funktions.checkStringForByteUse(message);
			MinaClient.sendData(660, message);
			TextFields.textField_Chat.setText("");
			
		}
		
	}
	
	@Override
	public boolean isActiv() {
		
		if(GameData.chatIsShown == true) {
			return true;
		}
		
		return false;
	}

	@Override
	public void draw(Graphics g) {
		
		if(this.isActiv() == true) {
			g.setColor(Color.DARK_GRAY.brighter());
			g.fillRect(xTL, yTL, xBR-xTL, yBR-yTL);
			
			g.setColor(this.standardColor);
			g.setFont(new Font("Arial", Font.BOLD, 14));
			g.drawString("Send", this.xTL+7, this.yTL+(this.yBR-this.yTL)/2+6);
		}
		
		super.draw(g);
	}
	
}
