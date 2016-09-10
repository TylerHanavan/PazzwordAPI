package com.PazzwordAPI.plugin.util.player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.PazzwordAPI.plugin.Core;
import com.PazzwordAPI.plugin.util.Utils;
import com.PazzwordAPI.plugin.util.Yaml;
import com.PazzwordAPI.plugin.util.player.data.UserData;
import com.PazzwordAPI.plugin.util.player.inventory.UserInventory;

public class User {
	
	private Core core = null;

	private UUID uid = null;
	
	private File file = null;
	
	private Yaml yaml = null;
	
	private long timeJoined = 0L;
	
	private long lastChatted = 0L;
	
	private UserInventory inventory = null;
	
	private List<UserData> userData = null;
	
	public User(Core core, UUID uid){
		
		this.core = core;
		
		this.uid = uid;
		
		this.file = (new File(core.getDataFolder().getAbsolutePath() + File.separator + "users" + File.separator + uid + ".yml"));
		
		this.yaml = new Yaml(this.file);
		
		this.timeJoined = new Date().getTime();
		
		this.userData = new ArrayList<UserData>();
		
		ConfigurationSection custom = this.yaml.getConfigurationSection("custom");
		
		if(custom != null) {
		
			for(String s : custom.getKeys(false)) {
				
				UserData data = this.core.getAPI().getUserData(this, s, custom.getString("type"), custom.getString("data"));
				
				this.addData(data);
				
			}
		
		}
		
	}
	
	public void save() {
		
		Date date = new Date();
		
		this.yaml.set("internal.lastlogout", date.getTime());
		
		this.yaml.set("internal.lastlogin", this.timeJoined);
		
		/*long elapsed = (long) this.yaml.get("internal.timeplayed");
		
		elapsed += date.getTime() - this.timeJoined;
		
		this.yaml.set("internal.timeplayed", elapsed);*/
		
		for(UserData data : this.userData) {
			
			if(data.shouldSave()) {
				this.yaml.set("custom." + data.getId().toLowerCase() + ".data", data.getDataToString());
				this.yaml.set("custom." + data.getId().toLowerCase() + ".type", data.getType());
			}
			
		}
		
		this.yaml.save();
		
	}
	
	public Yaml getYaml() {
		
		return this.yaml;
		
	}
	
	public Core getCore(){
		
		return this.core;
		
	}
	
	public UUID getUUID(){
		
		return this.uid;
		
	}
	
	public Player getPlayer(){
		
		return this.core.getServer().getPlayer(this.uid);
		
	}
	
	public boolean hasPermission(String permission){
		
		return this.getPlayer() != null && this.getPlayer().hasPermission(permission);
		
	}
	
	public User chat(String message){
		
		this.getPlayer().chat(message);
		
		return this;
		
	}
	
	public User sendMessage(String message) {
		
		this.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
		
		return this;
		
	}
	
	public void hasChatted() {
		
		this.lastChatted = new Date().getTime();
		
	}
	
	public void setInventory(UserInventory inventory) {
		
		if(inventory == null) {
			
			this.inventory = null;
			
			return;
			
		}
		
		if(this.getInventory() != null) {
			this.getInventory().close();
		}
		
		this.getPlayer().openInventory(inventory.getInventory());
		
		this.inventory = inventory;
		
	}
	
	public UserInventory getInventory() {
		
		return this.inventory;
		
	}
	
	public boolean isInventoryOpen() {
		
		return this.inventory != null;
		
	}
	
	public void addData(UserData data) {
		
		this.userData.add(data);
		
	}
	
	/* Static Methods */
	
	public static String toString(User user) {
		
		if(user == null) return "u{UNKNOWN}";
		
		return "u{" + user.getUUID().toString() + "}";
		
	}
	
}
