package com.PazzwordAPI.plugin.util.player.data;

import com.PazzwordAPI.plugin.util.player.User;

public interface UserDataHandler {

	public UserData handle(User user, String id, String name, Object o);
	public boolean handles(String type);
	
}
