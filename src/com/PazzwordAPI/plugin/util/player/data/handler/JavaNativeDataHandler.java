package com.PazzwordAPI.plugin.util.player.data.handler;

import org.bukkit.Bukkit;

import com.PazzwordAPI.plugin.util.player.data.DataHandler;
import com.PazzwordAPI.plugin.util.player.data.UserData;

public class JavaNativeDataHandler implements DataHandler {

	@Override
	public UserData handle(UserData data) {

		String s = data.getDataToString();
		String type = data.getType();
		
		if(type.equalsIgnoreCase("int"))
			data.overrideData(Integer.parseInt(s));

		if(type.equalsIgnoreCase("float"))
			data.overrideData(Float.parseFloat(s));

		if(type.equalsIgnoreCase("long"))
			data.overrideData(Long.parseLong(s));

		if(type.equalsIgnoreCase("double"))
			data.overrideData(Double.parseDouble(s));

		if(type.equalsIgnoreCase("boolean"))
			data.overrideData(Boolean.parseBoolean(s));
		
		return data;
			
	}

	@Override
	public boolean handles(String type) {
		
		return type.equalsIgnoreCase("int") || type.equalsIgnoreCase("long") || type.equalsIgnoreCase("double") || type.equalsIgnoreCase("float") || type.equalsIgnoreCase("boolean");
	}

}
