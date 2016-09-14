package com.PazzwordAPI.plugin.util.player.data.handler;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import com.PazzwordAPI.plugin.util.player.data.DataHandler;
import com.PazzwordAPI.plugin.util.player.data.UserData;

public class StringListDataHandler implements DataHandler {

	@Override
	public UserData handle(UserData data) {
		
		Object o = data.getData();
		
		String type = data.getType();
		
		List<String> list = new ArrayList<String>();
		
		if(o instanceof String && type.equalsIgnoreCase("list"))
			for(String s : ((String) o).split(";;;")) {
				list.add(s);
			}
		
		data.overrideData(list);	
		
		return data;
		
	}

	@Override
	public boolean handles(String type) {
		
		return false;
	}

}
