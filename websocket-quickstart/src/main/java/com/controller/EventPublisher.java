package com.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.websocket.Session;

public class EventPublisher implements Runnable {
	
	static
	{
		new Thread(new EventPublisher()).start();
	}
	
	static Map<String, Session> map = new HashMap<>();
	static void subscribe(Session session)
	{
		map.put(session.getId(), session);
	}
	
	@Override
	public void run() {
		
		int counter = 0;
		
		while(counter < 100)
		{
			Iterator<Entry<String, Session>> itr = map.entrySet().iterator();
			while(itr.hasNext())
			{
				Entry<String, Session> entry = itr.next();
				if(!entry.getValue().isOpen())
				{
					System.out.println("Removing as closed:" + entry.getValue().getId());
					itr.remove();
				}
				else
				{
					try {
						entry.getValue().getBasicRemote().sendText("Counter:"+ counter);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counter ++;
		}
		
	}

}


