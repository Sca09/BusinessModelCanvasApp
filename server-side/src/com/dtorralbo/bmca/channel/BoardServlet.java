package com.dtorralbo.bmca.channel;

import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

@SuppressWarnings("serial")
public class BoardServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(BoardServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("init");
		
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		String token = channelService.createChannel("bmca_board");

		FileReader reader = new FileReader("board.html");
		CharBuffer buffer = CharBuffer.allocate(16384);
		reader.read(buffer);
		String board = new String(buffer.array());
		board = board.replaceAll("\\{\\{ token \\}\\}", token);
		
		reader.close();
		
		resp.setContentType("text/html");
		resp.getWriter().write(board);
	}
}
