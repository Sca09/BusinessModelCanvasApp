package com.dtorralbo.bmca.channel;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class BoardUpdateService {

	public static void addCanvasItemNotification(Long id) {
		try {
			JSONObject message = new JSONObject();
			message.append("action", "add");
			message.append("id", id);

			ChannelService channelService = ChannelServiceFactory.getChannelService();
			ChannelMessage channelMessage = new ChannelMessage("bmca_board", message.toString());

			channelService.sendMessage(channelMessage);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateCanvasItemNotification(Long id) {
		try {
			JSONObject message = new JSONObject();
			message.append("action", "update");
			message.append("id", id);

			ChannelService channelService = ChannelServiceFactory.getChannelService();
			ChannelMessage channelMessage = new ChannelMessage("bmca_board", message.toString());

			channelService.sendMessage(channelMessage);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteCanvasItemNotification(Long id) {
		try {
			JSONObject message = new JSONObject();
			message.append("action", "delete");
			message.append("id", id);

			ChannelService channelService = ChannelServiceFactory.getChannelService();
			ChannelMessage channelMessage = new ChannelMessage("bmca_board", message.toString());

			channelService.sendMessage(channelMessage);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
