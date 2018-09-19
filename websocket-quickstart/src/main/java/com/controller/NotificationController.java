package com.controller;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/events")
public class NotificationController {
	@OnOpen
	public void onOpen(Session session)
	{
		System.out.println("On Open:" + session.getId());
		EventPublisher.subscribe(session);
	}
	
	//For Server push this is not required. This will be invoked when client sends message. 
	//So use full for bidirectional communication like chat.
	@OnMessage
	public void onMessage(Session session, String msg) throws IOException
	{
		System.out.println("On Message:" + session.getId());
		session.getBasicRemote().sendText("Hello from Server to client:" + session.getId());
	}
	
	@OnClose
	public void onClose(Session session)
	{
		System.out.println("On Close:" + session.getId());
	}
	
	//Say browser is closed. It will trigger onclose too.
	@OnError
	public void onError(Throwable t)
	{
		System.out.println("On error:" + t.getLocalizedMessage());
	}
}
