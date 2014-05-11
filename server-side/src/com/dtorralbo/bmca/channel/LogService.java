package com.dtorralbo.bmca.channel;

import com.dtorralbo.bmca.CanvasItem;
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class LogService {

	private static final String CLIENT_ID = "bmca_log";

	public static void addCanvasItemNotification(Long id, String userAgent) {
		try {
			JSONObject message = new JSONObject();
			message.append("action", "Added");
			message.append("id", id);
			message.append("user_agent", userAgent);

			ChannelService channelService = ChannelServiceFactory.getChannelService();
			ChannelMessage channelMessage = new ChannelMessage(CLIENT_ID, message.toString());

			channelService.sendMessage(channelMessage);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateCanvasItemNotification(Long id, String userAgent) {
		try {
			JSONObject message = new JSONObject();
			message.append("action", "Updated");
			message.append("id", id);
			message.append("user_agent", userAgent);

			ChannelService channelService = ChannelServiceFactory.getChannelService();
			ChannelMessage channelMessage = new ChannelMessage(CLIENT_ID, message.toString());

			channelService.sendMessage(channelMessage);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteCanvasItemNotification(CanvasItem canvasItem, String userAgent) {
		try {
			JSONObject message = new JSONObject();
			message.append("action", "Deleted");
			message.append("id", canvasItem.getId());
			message.append("category", canvasItem.getCategory());
			message.append("title", canvasItem.getTitle());
			message.append("description", canvasItem.getDescription());
			message.append("author", canvasItem.getAuthor());
			message.append("user_agent", userAgent);

			ChannelService channelService = ChannelServiceFactory.getChannelService();
			ChannelMessage channelMessage = new ChannelMessage(CLIENT_ID, message.toString());

			channelService.sendMessage(channelMessage);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
