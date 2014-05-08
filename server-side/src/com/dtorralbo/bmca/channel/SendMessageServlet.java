package com.dtorralbo.bmca.channel;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class SendMessageServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(SendMessageServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("Testing sending message");

		try {
			JSONObject message = new JSONObject();
			message.append("action", "update");
			message.append("id", 1L);
		
			ChannelService channelService = ChannelServiceFactory.getChannelService();
			ChannelMessage channelMessage = new ChannelMessage("bmca_board", message.toString());
		
			channelService.sendMessage(channelMessage);
		
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
