package com.dtorralbo.bmca.channel;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class BoardUpdateService {

	private static final String CLIENT_ID = "bmca_board";

	public static void addCanvasItemNotification(Long id) {
		try {
			JSONObject message = new JSONObject();
			message.append("action", "Added");
			message.append("id", id);

			ChannelService channelService = ChannelServiceFactory.getChannelService();
			ChannelMessage channelMessage = new ChannelMessage(CLIENT_ID, message.toString());

			channelService.sendMessage(channelMessage);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateCanvasItemNotification(Long id) {
		try {
			JSONObject message = new JSONObject();
			message.append("action", "Updated");
			message.append("id", id);

			ChannelService channelService = ChannelServiceFactory.getChannelService();
			ChannelMessage channelMessage = new ChannelMessage(CLIENT_ID, message.toString());

			channelService.sendMessage(channelMessage);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteCanvasItemNotification(Long id) {
		try {
			JSONObject message = new JSONObject();
			message.append("action", "Deleted");
			message.append("id", id);

			ChannelService channelService = ChannelServiceFactory.getChannelService();
			ChannelMessage channelMessage = new ChannelMessage(CLIENT_ID, message.toString());

			channelService.sendMessage(channelMessage);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
