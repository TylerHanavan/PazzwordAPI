package com.PazzwordAPI.plugin.api;

import java.util.ArrayList;
import java.util.List;

import com.PazzwordAPI.plugin.util.player.User;
import com.PazzwordAPI.plugin.util.player.data.UserData;
import com.PazzwordAPI.plugin.util.player.data.UserDataHandler;

public class API {
	
	private List<UserDataHandler> dataHandlers = null;
	
	public API() {
		
		this.dataHandlers = new ArrayList<UserDataHandler>();
		
	}

	public UserData getUserData(User user, String id, String type, Object o) {
		
		for(UserDataHandler handler : this.getUserDataHandlers())
			if(handler.handles(type))
				return handler.handle(user, id, type, o);
		
		return new UserData(id, type, o);
		
	}
	
	public API addDataHandler(UserDataHandler handler) {
		
		this.dataHandlers.add(handler);
		
		return this;
		
	}
	
	public List<UserDataHandler> getUserDataHandlers() {
		
		return this.dataHandlers;
		
	}
	
}
