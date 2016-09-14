package com.PazzwordAPI.plugin.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import com.PazzwordAPI.plugin.util.player.User;
import com.PazzwordAPI.plugin.util.player.data.DataHandler;
import com.PazzwordAPI.plugin.util.player.data.UserData;

public class API {
	
	private List<DataHandler> dataHandlers = null;
	
	public API() {
		
		this.dataHandlers = new ArrayList<DataHandler>();
		
	}

	public UserData getUserData(User user, String id, String type, Object o, boolean save) {
		
		UserData data = user.getUserDataById(id, type);
		
		if(data != null) {
		
		} else {
		
			data = new UserData(id, type, o, save);
			
			user.addData(data);
		
		}
	
		for(DataHandler handler : this.getUserDataHandlers())
			if(handler.handles(type)) {
				data = handler.handle(data);Bukkit.broadcastMessage("test");
			}
		
		return data;
		
	}
	
	public API addDataHandler(DataHandler handler) {
		
		this.dataHandlers.add(handler);
		
		return this;
		
	}
	
	public List<DataHandler> getUserDataHandlers() {
		
		Bukkit.broadcastMessage(this.dataHandlers.size() + " ");
		
		return this.dataHandlers;
		
	}
	
}
