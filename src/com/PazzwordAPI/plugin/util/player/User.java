package com.PazzwordAPI.plugin.util.player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

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
		
		this.yaml.load();
		
		this.timeJoined = new Date().getTime();
		
		this.userData = new ArrayList<UserData>();
		
		ConfigurationSection custom = this.yaml.getConfigurationSection("custom");
		
		if(custom != null) {
		
			for(String s : custom.getKeys(true)) {
				
				if(s != null && !s.endsWith("data") && !s.endsWith("type")) {
				
					String type = custom.getString(s + ".type");
					
					if(type != null) {
					
						if(type.equalsIgnoreCase("list")) {
						
							UserData data = this.core.getAPI().getUserData(this, s, type, custom.getStringList(s + ".data"), true);
							
							this.addData(data);
							
						} else {
						
							UserData data = this.core.getAPI().getUserData(this, s, type, custom.getString(s + ".data"), true);
							
							this.addData(data);
							
						}
					
					}
					
				}
				
			}
		
		}
		
		User.setUsernameForUUID(this.getPlayer().getName(), this.uid);
		
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
				
				if(data.getType().equalsIgnoreCase("list")) {
					
					List<Object> list = (List<Object>) data.getData();
					
					this.yaml.set("custom." + data.getId().toLowerCase() + ".data", list);
					this.yaml.set("custom." + data.getId().toLowerCase() + ".type", data.getType());
					
				} else {
				
					this.yaml.set("custom." + data.getId().toLowerCase() + ".data", data.getOverridenData() != null ? data.getOverridenData() : data.getData());
					this.yaml.set("custom." + data.getId().toLowerCase() + ".type", data.getType());
				
				}
				
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
	
	public UserData getUserDataByType(String type) {
		
		for(UserData data : this.userData)
			if(data != null)
				if(data.getType().equalsIgnoreCase(type))
					return data;
		
		return null;
		
	}
	
	public UserData getUserDataById(String id) {
		
		for(UserData data : this.userData)
			if(data != null)
				if(data.getId().equalsIgnoreCase(id))
					return data;
		
		return null;
		
	}
	
	public void removeDataById(String id) {
		
		UserData data = this.getUserDataById(id);
		
		this.userData.remove(data);
		
	}
	
	public List<UserData> getUserData() {
		
		return this.userData;
		
	}
	
	/* Static Methods */
	
	public static String toString(User user) {
		
		if(user == null) return "u{UNKNOWN}";
		
		return "u{" + user.getUUID().toString() + "}";
		
	}
	
	private static Yaml usersYaml = null;
	
	private static void setUsernameForUUID(String username, UUID uid) {
		
		usersYaml.load();
		
		String uuid = usersYaml.getString(username.toLowerCase());
		
		if(uuid != null && !uuid.equalsIgnoreCase(uid.toString())) {
		
			usersYaml.set(username.toLowerCase(), uid.toString());
		
		} else {
			
			usersYaml.set(username.toLowerCase(), uid.toString());
			
		}
		
		usersYaml.save();
		
	}
	
	public static UUID getUUIDFromUsername(String username) {
		
		usersYaml.load();
		
		String s = usersYaml.getString(username.toLowerCase());
		
		if(s != null) {
			
			UUID uuid = UUID.fromString(s);
			
			return uuid;
			
		}
		
		Logger.getLogger("Minecraft").info("UUID null from config");
		
		return null;
		
	}
	
	public static void doYaml(Core core) {
		
		if(usersYaml == null) {
			
			File file = new File(core.getDataFolder().getAbsolutePath() + File.separator + "users.yml");
			
			if(!file.exists())
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			usersYaml = new Yaml(file);
			
			usersYaml.load();
			
		}
		
	}
	
}
