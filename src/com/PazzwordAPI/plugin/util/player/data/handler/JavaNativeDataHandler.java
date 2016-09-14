package com.PazzwordAPI.plugin.util.player.data.handler;

import com.PazzwordAPI.plugin.util.player.data.DataHandler;
import com.PazzwordAPI.plugin.util.player.data.UserData;

public class JavaNativeDataHandler implements DataHandler {
	
	private String types[] = {
		"int", "double", "float", "char"
	};

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
		
		return data;
			
	}

	@Override
	public boolean handles(String type) {

		for(int i = 0; i < this.types.length; i++)
			if(this.types[i].equalsIgnoreCase(type))
				return true;
		
		return false;
	}

}
