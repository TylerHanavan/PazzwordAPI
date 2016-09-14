package com.PazzwordAPI.plugin.util.player.data;

public interface DataHandler {

	public UserData handle(UserData data);
	public boolean handles(String type);
	
}
