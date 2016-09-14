package com.PazzwordAPI.plugin.api.complex;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Sign;

import com.PazzwordAPI.plugin.util.Yaml;

public class SignAPI {

	private static List<SignInstance> signs = null;
	private static List<SignLoader> signLoaders = null;
	
	private static Yaml signYaml = null;
	
	private static void init() {
		
		if(hasInit()) return;
		
		signs = new ArrayList<SignInstance>();
		
		signLoaders = new ArrayList<SignLoader>();
		
	}
	
	private static boolean hasInit() {
		
		return signs != null;
		
	}
	
	public static void addSignLoader(SignLoader loader) {
		
		init();
		
		signLoaders.add(loader);
		
	}
	
	public static SignInstance getSignInstance(Sign sign, boolean load, boolean create) {
		
		for(SignInstance instance : signs)
			if(instance.getSign().equals(sign) || instance.getSign().getLocation().equals(sign.getLocation()))
				return instance;
		if(create) {
			
			SignInstance instance = new SignInstance(sign);
			
			if(load)
				load(instance);
			
			signs.add(instance);
			
			return instance;
		
		}
		
		return null;
		
	}
	
	private static void load(SignInstance instance) {
		
		for(SignLoader loader : signLoaders)
			if(loader.load(instance))
				loader.handle(instance);
		
	}
	
	private static void save(SignInstance instance) {
		
		
		
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
			
			if(i >= 0 && i < 4)
				this.sign.setLine(i, s);
			
		}
		
	}

	
}
