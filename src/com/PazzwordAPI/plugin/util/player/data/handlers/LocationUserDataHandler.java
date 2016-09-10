/*
 * Apologies in advanced for the seemingly redundant "UserUser". UserDataHandler is a interface to handle data for users, but UserUserDataHandler is a sub-interfaced to handle User data for Users
 */

package com.PazzwordAPI.plugin.util.player.data.handlers;

import com.PazzwordAPI.plugin.util.player.User;
import com.PazzwordAPI.plugin.util.player.data.UserData;
import com.PazzwordAPI.plugin.util.player.data.UserDataHandler;

public class LocationUserDataHandler implements UserDataHandler {

	@Override
	public UserData handle(User user, String id, String name, Object o) {
		
		UserData userData = new UserData(id, name, o);
		
		return userData;
	}

	@Override
	public boolean handles(String type) {
		
		return type.equalsIgnoreCase("location");
	}
	
}
