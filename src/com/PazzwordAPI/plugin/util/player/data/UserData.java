package com.PazzwordAPI.plugin.util.player.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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
		
		Object o = this.o;
		
		if(o instanceof String && this.type.equalsIgnoreCase("int"))
			return Integer.parseInt((String) o);
		
		if(o instanceof String && this.type.equalsIgnoreCase("long"))
			return Long.parseLong((String) o);
		
		if(o instanceof String && this.type.equalsIgnoreCase("double"))
			return Double.parseDouble((String) o);
		
		List<String> list = new ArrayList<String>();
		
		if(o instanceof String && this.type.equalsIgnoreCase("list"))
			for(String s : ((String) o).split(";;;")) {
				list.add(s);
				Bukkit.broadcastMessage("list.add " + s);
			}
		
		if(list.size() > 0)
			return list;
				
		
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
		
		if(o instanceof List) {
			
			String ret = "";
			
			for(Object os : ((List) o)) {
				
				ret += os.toString() + ";;;";
				
			}
			
			return ret;
			
		}
		
		return o.toString();
		
	}
	
	public boolean shouldSave() {
		
		return this.save;
		
	}
	
}
