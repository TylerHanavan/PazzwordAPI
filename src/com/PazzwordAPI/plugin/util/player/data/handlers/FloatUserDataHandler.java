package com.PazzwordAPI.plugin.util.player.data.handlers;

import com.PazzwordAPI.plugin.util.player.User;
import com.PazzwordAPI.plugin.util.player.data.UserData;
import com.PazzwordAPI.plugin.util.player.data.UserDataHandler;

public class FloatUserDataHandler implements UserDataHandler {

	@Override
	public UserData handle(User user, String id, String name, Object o) {
		
		UserData userData = new UserData(id, name, o);
		
		return userData;
	}

	@Override
	public boolean handles(String type) {
		
		return type.equalsIgnoreCase("float");
	}
	
}
