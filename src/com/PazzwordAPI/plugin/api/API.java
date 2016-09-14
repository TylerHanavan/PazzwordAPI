package com.PazzwordAPI.plugin.api;

import java.util.ArrayList;
import java.util.List;

import com.PazzwordAPI.plugin.util.player.User;
import com.PazzwordAPI.plugin.util.player.data.DataHandler;
import com.PazzwordAPI.plugin.util.player.data.UserData;

public class API {
	
	private List<DataHandler> dataHandlers = null;
	
	public API() {
		
		this.dataHandlers = new ArrayList<DataHandler>();
		
	}

	public UserData getUserData(User user, String id, String type, Object o, boolean save) {
		
		UserData data = user.getUserDataById(id);
		
		if(data != null) {
			
			if(data.getOverridenData() == null) {
		
				for(DataHandler handler : this.getUserDataHandlers())
					if(handler.handles(type))
						return handler.handle(data);
			
			}
			
			return data;
		
		}
		
		return new UserData(id, type, o, save);
		
	}
	
	public API addDataHandler(DataHandler handler) {
		
		this.dataHandlers.add(handler);
		
		return this;
		
	}
	
	public List<DataHandler> getUserDataHandlers() {
		
		return this.dataHandlers;
		
	}
	
}
