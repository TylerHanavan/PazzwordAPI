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
	
	private Object ret = null;
	
	private boolean save = true;

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
	
	public Object getOverridenData() {
		
		return this.ret;
		
	}
	
	public boolean isBoolean() {
		
		return this.getOverridenData() != null && this.getOverridenData() instanceof Boolean;
		
	}
	
	public boolean isInteger() {
		
		return this.getOverridenData() != null && this.getOverridenData() instanceof Integer;
		
	}
	
	public boolean isDouble() {
		
		return this.getOverridenData() != null && this.getOverridenData() instanceof Double;
		
	}
	
	public boolean isLong() {
		
		return this.getOverridenData() != null && this.getOverridenData() instanceof Float;
		
	}
	
	public boolean isFloat() {
		
		return this.getOverridenData() != null && this.getOverridenData() instanceof Float;
		
	}
	
	public Object getData() {
		
		return this.o;
		
	}
	
	public String getDataToString() {
		
		Object o = this.getData();
		
		return o.toString();
		
	}
	
	public void overrideData(Object o) {
		
		this.ret = o;
		
	}
	
	public boolean shouldSave() {
		
		return this.save;
		
	}
	
}
