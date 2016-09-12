package com.PazzwordAPI.plugin.util.player.data;

import org.bukkit.Location;

import com.PazzwordAPI.plugin.util.Utils;
import com.PazzwordAPI.plugin.util.player.User;

public class UserData {
	
	private String id;
	
	private String type;
	
	private Object o;
	
	private boolean save = false;

	public UserData(String id, String type, Object o) {
		
		this.id = id;
		
		this.type = type;
		
		this.o = o;
		
	}

	public UserData(String id, String type, Object o, boolean save) {
		
		this.id = id;
		
		this.type = type;
		
		this.o = o;
		
		this.save = save;
		
	}
	
	public String getId() {
		
		return this.id;
		
	}
	
	public String getType() {
		
		return this.type;
		
	}
	
	public Object getData() {
		
		return this.o;
		
	}
	
	public String getDataToString() {
		
		Object o = this.getData();
		
		if(o == null) return "UserData#getDataToString() is null";
		
		if(o instanceof Location) {
			
			return Utils.locationToString((Location) o);
			
		}
		
		if(o instanceof Integer) {
			
			return ((Integer) o).intValue() + "";
			
		}
		
		if(o instanceof Double) {
			
			return ((Double) o).doubleValue() + "";
			
		}
		
		if(o instanceof Float) {
			
			return ((Float) o).floatValue() + "";
			
		}
		
		if(o instanceof User) {
			
			User user = (User) o;
			
			return User.toString(user);
			
		}
		
		return o.toString();
		
	}
	
	public boolean shouldSave() {
		
		return this.save;
		
	}
	
}
