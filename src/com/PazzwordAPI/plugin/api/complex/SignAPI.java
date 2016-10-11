package com.PazzwordAPI.plugin.api.complex;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;

import com.PazzwordAPI.plugin.Core;
import com.PazzwordAPI.plugin.util.Yaml;

public class SignAPI {

	private static List<SignInstance> signs = null;
	private static List<SignLoader> signLoaders = null;
	
	private static Yaml signYaml = null;
	
	public static void init(Core core) {
		
		if(hasInit()) return;
		
		signs = new ArrayList<SignInstance>();
		
		signLoaders = new ArrayList<SignLoader>();
		
		File file = new File(core.getDataFolder().getAbsolutePath() + File.separator + "signs.yml");
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Yaml yaml = new Yaml(file);
		
		yaml.load();
		
		signYaml = yaml;
		
		ConfigurationSection section = signYaml.getConfigurationSection("signs");
		
		if(section != null) {
			
			Set<String> keys = section.getKeys(false);
			
			if(keys != null) {
				
				for(String key : keys) {
					
					Location loc = yaml.getLocation("signs." + key);
					
					if(loc != null && loc.getBlock() != null) {
					
						if(loc.getBlock().getType() == Material.WALL_SIGN || loc.getBlock().getType() == Material.SIGN_POST) {
							
							if(loc.getBlock().getState() != null && loc.getBlock().getState() instanceof Sign) {
								
								SignInstance instance = getSignInstance(((Sign) loc.getBlock().getState()), true);
								
								if(instance != null) {
									
									signs.add(instance);
									
								}
								
							}
							
						}
					
					}
					
				}
				
			}
			
		}
		
	}
	
	private static boolean hasInit() {
		
		return signs != null;
		
	}
	
	public static void addSignLoader(SignLoader loader) {
		
		signLoaders.add(loader);
		
	}
	
	public static SignInstance getSignInstance(Sign sign, boolean create) {
		
		for(SignInstance instance : signs)
			if(instance.getSign().equals(sign) || instance.getSign().getLocation().equals(sign.getLocation()))
				return instance;
		
		if(create) {
			
			SignInstance instance = new SignInstance(sign);
			
			signs.add(instance);
			
			Core.debug("YES");
			
			return instance;
		
		}
		
		return null;
		
	}
	
	private static void handle(SignInstance instance) {
		
		for(SignLoader loader : signLoaders)
			if(loader.handles(instance))
				loader.handle(instance);
		
	}
	
	public static void save() {
		
		int c = 0;
		
		ConfigurationSection section = signYaml.getConfigurationSection("signs");
		
		if(section != null) {
			
			Set<String> keys = section.getKeys(false);
			
			if(keys != null) {
				
				for(String key : keys) {
				
					signYaml.set("signs." + key, null);
				
				}
				
			}
			
		}
		
		for(SignInstance instance : signs) {
			
			signYaml.setLocation("signs." + c, instance.getSign().getLocation());
			
			++c;
			
		}
		
		signYaml.save();
		
	}
	
	public static void tick() {
		
		for(SignInstance instance : signs) {
			
			handle(instance);
			
		}
		
	}
	
	public static void triggerSignDestroyed(SignInstance sign) {
		
		signs.remove(sign);
		
	}
	
	public static class SignInstance {

		private Sign sign = null;
		
		public SignInstance(Sign sign) {
			
			this.sign = sign;
			
		}
		
		public Sign getSign() {
			
			return this.sign;
			
		}
		
		public String[] getLines() {
			
			return this.sign.getLines();
			
		}
		
		public String getLine(int i) {
			
			if(i >= 0 && i < 4)
				return this.sign.getLine(i);
			
			return null;
			
		}
		
		public void setLine(int i, String s) {
			
			if(i >= 0 && i < 4) {
				this.sign.setLine(i, s);
				this.sign.update();
			}
			
		}
		
	}

	
}
