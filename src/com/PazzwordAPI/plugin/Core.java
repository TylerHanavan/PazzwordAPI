package com.PazzwordAPI.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.PazzwordAPI.plugin.api.API;
import com.PazzwordAPI.plugin.api.complex.SignAPI;
import com.PazzwordAPI.plugin.listener.PlayerListener;
import com.PazzwordAPI.plugin.thread.SignAPIThread;
import com.PazzwordAPI.plugin.util.player.User;
import com.PazzwordAPI.plugin.util.player.data.handler.JavaNativeDataHandler;

public class Core extends JavaPlugin {
	
	private List<User> users = null;
	
	private API api = null;

	/**
	 * Called when the plugin enables
	 */
	@Override
	public void onEnable() {
		
		this.api = new API();
		
		User.doYaml(this);
		
		this.users = new ArrayList<User>();
		
		(new File(this.getDataFolder().getAbsolutePath() + File.separator + "users")).mkdirs();
		
		for(Player player : Bukkit.getOnlinePlayers())
			this.addUser(player.getUniqueId());
		
		this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		
		this.api.addDataHandler(new JavaNativeDataHandler());
		
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new SignAPIThread(), 20L, 10L);
		
		SignAPI.init(this);
		
	}
	
	/**
	 * Called when the plugin disables
	 */
	@Override
	public void onDisable() {
		
		for(User user : this.users)
			user.save();
		
		SignAPI.save();
		
	}
	
	/**
	 * Returns an instance of the API class used by external plugins
	 * @return The API
	 */
	public API getAPI() {
		
		return this.api;
		
	}
	
	/**
	 * Gets a user based on their UUID
	 * @param uid The UUID of the Player
	 * @return The User
	 */
	public User getUser(UUID uid){
		
		for(User user : this.users)
			if(user.getUUID().toString().equalsIgnoreCase(uid.toString()))
				return user;
		
		return null;
		
	}
	
	/**
	 * Adds a User based on UUID
	 * @param uid UUID of the player
	 * @return The User
	 */
	public User addUser(UUID uid){
		
		User user = new User(this, uid);
		
		if(getUser(uid) == null)
			this.users.add(user);
		
		return user;
		
	}
	
	/**
	 * Removes a User based on UUID
	 * @param uid UUID of the player
	 * @return The User (should only call further methods  on User if absolutely necessary)
	 */
	public User removeUser(UUID uid){
		
		User user = null;
		
		for(User users : this.users)
			if(users.getUUID().toString().equalsIgnoreCase(uid.toString()))
				user = users;
		
		this.users.remove(user);
		
		return user;
		
	}
	
	/**
	 * Returns a list of all online Users
	 * @return List of Users
	 */
	public List<User> getUsers() {
		
		return this.users;
		
	}
	
	/**
	 * Sends a message to pazzword if online
	 * @param message The message to send
	 */
	public static void debug(String message){
		
		for(Player player : Bukkit.getOnlinePlayers())
			if(player.getName().equalsIgnoreCase("pazzword"))
				player.sendMessage(message);
		
	}
	
}
