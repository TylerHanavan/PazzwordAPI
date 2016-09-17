package com.PazzwordAPI.plugin.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

import com.PazzwordAPI.plugin.util.player.User;
import com.PazzwordAPI.plugin.util.player.data.DataHandler;
import com.PazzwordAPI.plugin.util.player.data.UserData;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

public class API {
	
	private List<DataHandler> dataHandlers = null;
	
	public API() {
		
		this.dataHandlers = new ArrayList<DataHandler>();
		
	}

	public UserData getUserData(User user, String id, String type, Object o, boolean save) {
		
		UserData data = new UserData(id, type, o, save);
	
		for(DataHandler handler : this.getUserDataHandlers())
			if(handler.handles(type)) {
				data = handler.handle(data);
			}
		
		return data;
		
	}
	
	public API addDataHandler(DataHandler handler) {
		
		this.dataHandlers.add(handler);
		
		return this;
		
	}
	
	public List<DataHandler> getUserDataHandlers() {
		
		return this.dataHandlers;
		
	}
	
}
