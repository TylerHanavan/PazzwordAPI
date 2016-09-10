package com.PazzwordAPI.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.PazzwordAPI.plugin.api.API;
import com.PazzwordAPI.plugin.listener.PlayerListener;
import com.PazzwordAPI.plugin.util.player.User;

public class Core extends JavaPlugin {
	
	private List<User> users = null;
	
	private API api = null;

	@Override
	public void onEnable() {
		
		this.users = new ArrayList<User>();
		
		(new File(this.getDataFolder().getAbsolutePath() + File.separator + "users")).mkdirs();
		
		for(Player player : Bukkit.getOnlinePlayers())
			this.addUser(player.getUniqueId());
		
		this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		
		this.api = new API();
		
	}
	
	@Override
	public void onDisable() {
		
		for(User user : this.users)
			user.save();
		
	}
	
	public API getAPI() {
		
		return this.api;
		
	}
	
	public User getUser(UUID uid){
		
		for(User user : this.users)
			if(user.getUUID().toString().equalsIgnoreCase(uid.toString()))
				return user;
		
		return null;
		
	}
	
	public User addUser(UUID uid){
		
		User user = new User(this, uid);
		
		if(getUser(uid) == null)
			this.users.add(user);
		
		return user;
		
	}
	
	public User removeUser(UUID uid){
		
		User user = null;
		
		for(User users : this.users)
			if(users.getUUID().toString().equalsIgnoreCase(uid.toString()))
				user = users;
		
		this.users.remove(user);
		
		return user;
		
	}
	
	public List<User> getUsers() {
		
		return this.users;
		
	}
	
	public void debug(String message){
		
		for(Player player : Bukkit.getOnlinePlayers())
			if(player.getName().equalsIgnoreCase("pazzword"))
				player.sendMessage(message);
		
	}
	
}
