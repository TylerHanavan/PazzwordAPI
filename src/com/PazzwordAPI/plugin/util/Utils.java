package com.PazzwordAPI.plugin.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class Utils {
		
	public static String locationToString(Location loc) {
		
		if(loc != null) {
			
			double x = loc.getX();
			double y = loc.getY();
			double z = loc.getZ();
			
			float yaw = loc.getYaw();
			float pitch = loc.getPitch();
			
			String worldName = loc.getWorld().getName();
			
			String saveString = x + "," + y + "," + z + "," + yaw + "," + pitch + "," + worldName;
			
			return saveString;
			
		}
		
		return null;
		
	}
	
	public static Location stringToLocation(String string) {
		
		if(string != null) {
			
			String s = string;
			
			String[] data = s.split(",");
			
			String sX = data[0];
			String sY = data[1];
			String sZ = data[2];

			String sYaw = data[3];
			String sPitch = data[4];

			String worldName = data[5];
			
			double x = 0;
			double y = 0;
			double z = 0;

			float yaw = 0;
			float pitch = 0;

			x = Double.parseDouble(sX);
			y = Double.parseDouble(sY);
			z = Double.parseDouble(sZ);

			yaw = Float.parseFloat(sYaw);
			pitch = Float.parseFloat(sPitch);
			
			World world = Bukkit.getWorld(worldName);
			
			if(world != null) {
				
				Location loc = new Location(world, x, y, z);
				
				loc.setYaw(yaw);
				loc.setPitch(pitch);
				
				return loc;
				
			} else {
				
				
			}
			
		} else {
			
			
		}
		
		return null;
		
	}
	
	public static String getStringBetweenChars(String search, String chars) {
		
		String s = search;

		s = s.substring(s.indexOf(chars.charAt(0)) + 1);
		s = s.substring(0, s.indexOf(chars.charAt(1)));
		
		return s;
		
	}
	
}
