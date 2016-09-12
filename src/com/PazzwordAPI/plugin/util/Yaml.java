package com.PazzwordAPI.plugin.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import com.PazzwordAPI.plugin.Core;
import com.PazzwordAPI.plugin.util.player.User;

public class Yaml {
	
	private File file;
	
	private YamlConfiguration config;
	
	private String options = "0123456789abcdeflmnko";
	
	public Yaml(File file) {
		
		this.file = file;
		
		if(!this.file.exists())
			try {
				this.file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		YamlConfiguration config = new YamlConfiguration();
		
		this.config = config;
		
		try {
			this.config.load(this.file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.config = config;
		
	}
	
	public void save() {
		
		try {
			this.config.save(this.file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void load() {

		
		try {
			this.config.load(this.file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void reload() {
		this.save();
		this.load();
	}
	
	public void set(String node, Object o) {
		
		if(o instanceof Location) {
			
			this.setLocation(node, (Location) o);
			
			return;
			
		}
		
		this.config.set(node, o);
		
	}
	
	public void setLocation(String node, Location loc) {
		
		if(loc != null) {
			
			double x = loc.getX();
			double y = loc.getY();
			double z = loc.getZ();
			
			float yaw = loc.getYaw();
			float pitch = loc.getPitch();
			
			String worldName = loc.getWorld().getName();
			
			String saveString = x + "," + y + "," + z + "," + yaw + "," + pitch + "," + worldName;
			
			this.set(node + ".location", saveString);
			
		}
		
	}
	
	public Object get(String node) {
		
		if(this.config != null)
			return null;
		
		return this.config.get(node);
		
	}
	
	/**
	 * Gets location as String from node + ".location" and returns it as a Location
	 * @param node
	 * @return 
	 */
	public Location getLocation(String node) {
		
		ConfigurationSection section = this.getConfigurationSection(node);
		
		if(section != null) {
			
			String s = section.getString("location");
			
			if(s == null) return null;
			
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
				
				Bukkit.broadcastMessage("World was null from config");
				
			}
			
		} else {
			
			Bukkit.broadcastMessage("Location string is null");
			
		}
		
		return null;
		
	}
	
	public void setItemStack(String node, ItemStack item, int place) {
		
		if(item != null) {
		
			this.config.set(node + ".id", item.getType().getId());
			this.config.set(node + ".amount", item.getAmount());
			this.config.set(node + ".damage", item.getDurability());
			this.config.set(node + ".data", item.getData().getData());
			this.config.set(node + ".place", place);
			
			ItemMeta meta = item.getItemMeta();
			
			this.config.set(node + ".lore", null);
			
			if(meta != null){
				
				if(meta.getDisplayName() != null) {
					
					String title = meta.getDisplayName();
					
					this.config.set(node + ".title", title);
					
				}
				
				if(meta.getLore() != null) {
					
					List<String> lore = new ArrayList<String>();
					
					for(String s : meta.getLore()) {
						
						if(s != null)
							lore.add(s);
						
					}
					
					int c = 0;
					
					for(String s : lore) {
					
						this.config.set(node + ".lore." + c, s);
						
						++c;
					
					}
					
				} else {
					
				}
				
			} else {
				
			}
		
		} else {
			
			this.config.set(node, null);
			
		}
		
		this.save();
		
	}
	
	public ItemStack getItemStack(String node, int place) {
		
		if(node != null) {
			
			int id = this.config.getInt(node + ".id");
			int amount = this.config.getInt(node + ".amount");
			int damage = this.config.getInt(node + ".damage");
			int data = this.config.getInt(node + ".data");

			ConfigurationSection section = this.config.getConfigurationSection(node + ".lore");
			
			List<String> lore = null;

			ItemStack item = new ItemStack(id, amount);
			
			ItemMeta meta = item.getItemMeta();
			
			if(section != null) {
			
				lore = new ArrayList<String>();
				
				Set<String> set = section.getKeys(false);
				
				for(String s : set) {
					
					lore.add(this.config.getString(node + ".lore." + s));
					
				}
				
				meta.setLore(lore);
			
			}
			
			String title = this.config.getString(node + ".title");
			
			if(title != null) {
				
				meta.setDisplayName(title);
			
			}
			
			item.setItemMeta(meta);
				
			item.setDurability((short) damage);
			item.setData(new MaterialData(data));
			
			return item;
			
		}
		
		return null;
		
	}
	
	public void setItemStackInInventory(String node, ItemStack item, int place) {
		
		if(item != null) {
		
			this.config.set(node + ".items." + place + ".id", item.getType().getId());
			this.config.set(node + ".items." + place + ".amount", item.getAmount());
			this.config.set(node + ".items." + place + ".damage", item.getDurability());
			this.config.set(node + ".items." + place + ".data", item.getData().getData());
			this.config.set(node + ".items." + place + ".place", place);
			
			ItemMeta meta = item.getItemMeta();
			
			this.config.set(node + ".items." + place + ".lore", null);
			
			Map<Enchantment, Integer> enchantments = item.getEnchantments();
			
			if(enchantments != null) {
				
				for(Enchantment enchantment : enchantments.keySet()) { 
					
					this.config.set(node + ".items." + place + ".enchantment." + enchantment.getName(), enchantments.get(enchantment).intValue());
					
				}
				
			}
			
			if(meta != null){
				
				if(meta.getDisplayName() != null) {
					
					String title = meta.getDisplayName();
					
					this.config.set(node + ".items." + place + ".title", title);
					
				}
				
				if(meta.getLore() != null) {
					
					List<String> lore = new ArrayList<String>();
					
					for(String s : meta.getLore()) {
						
						if(s != null)
							lore.add(s);
						
					}
					
					int c = 0;
					
					for(String s : lore) {
					
						this.config.set(node + ".items." + place + ".lore." + c, s);
						
						++c;
					
					}
					
				} else {
					
				}
				
			} else {
				
			}
		
		} else {
			
			this.config.set(node + ".items." + place, null);
			
		}
		
		this.save();
		
	}
	
	public void setArmorItemStackInInventory(String node, ItemStack item, int place) {
		
		if(item != null) {
		
			this.config.set(node + ".armor." + place + ".id", item.getType().getId());
			this.config.set(node + ".armor." + place + ".amount", item.getAmount());
			this.config.set(node + ".armor." + place + ".damage", item.getDurability());
			this.config.set(node + ".armor." + place + ".data", item.getData().getData());
			this.config.set(node + ".armor." + place + ".place", place);
			
			ItemMeta meta = item.getItemMeta();
			
			this.config.set(node + ".armor." + place + ".lore", null);
			
			Map<Enchantment, Integer> enchantments = item.getEnchantments();
			
			if(enchantments != null) {
				
				for(Enchantment enchantment : enchantments.keySet()) { 
					
					this.config.set(node + ".armor." + place + ".enchantment." + enchantment.getName(), enchantments.get(enchantment).intValue());
					
				}
				
			}
			
			if(meta != null){
				
				if(meta.getDisplayName() != null) {
					
					String title = meta.getDisplayName();
					
					this.config.set(node + ".armor." + place + ".title", title);
					
				}
				
				if(meta.getLore() != null) {
					
					List<String> lore = new ArrayList<String>();
					
					for(String s : meta.getLore()) {
						
						if(s != null)
							lore.add(s);
						
					}
					
					int c = 0;
					
					for(String s : lore) {
					
						this.config.set(node + ".armor." + place + ".lore." + c, s);
						
						++c;
					
					}
					
				} else {
					
				}
				
			} else {
				
			}
		
		} else {
			
			this.config.set(node + ".armor." + place, null);
			
		}
		
		this.save();
		
	}
	
	public ItemStack getItemStackInInventory(String node, int place) {
		
		if(node != null) {
			
			int id = this.config.getInt(node + ".items." + place + ".id");
			int amount = this.config.getInt(node + ".items." + place + ".amount");
			int damage = this.config.getInt(node + ".items." + place + ".damage");
			int data = this.config.getInt(node + ".items." + place + ".data");

			ItemStack item = new ItemStack(id, amount);

			ConfigurationSection enchantments = this.config.getConfigurationSection(node + ".items." + place + ".enchantment");
			
			if(enchantments != null) {
				
				for(String s : enchantments.getKeys(false)) {
					
					Enchantment enchantment = Enchantment.getByName(s);
					
					if(enchantment != null) {
						
						int i = enchantments.getInt(s);
						
						item.addUnsafeEnchantment(enchantment, i);
						
					}
					
				}
				
			}

			ConfigurationSection section = this.config.getConfigurationSection(node + ".items." + place + ".lore");
			
			List<String> lore = null;
			
			ItemMeta meta = item.getItemMeta();
			
			if(section != null) {
			
				lore = new ArrayList<String>();
				
				Set<String> set = section.getKeys(false);
				
				for(String s : set) {
					
					lore.add(this.config.getString(node + ".items." + place + ".lore." + s));
					
				}
				
				meta.setLore(lore);
			
			}
			
			String title = this.config.getString(node + ".items." + place + ".title");
			
			if(title != null) {
				
				meta.setDisplayName(title);
			
			}
			
			item.setItemMeta(meta);
				
			item.setDurability((short) damage);
			item.setData(new MaterialData(data));
			
			return item;
			
		}
		
		return null;
		
	}
	
	public ItemStack getArmorItemStackInInventory(String node, int place) {
		
		if(node != null) {
			
			int id = this.config.getInt(node + ".armor." + place + ".id");
			int amount = this.config.getInt(node + ".armor." + place + ".amount");
			int damage = this.config.getInt(node + ".armor." + place + ".damage");
			int data = this.config.getInt(node + ".armor." + place + ".data");

			ItemStack item = new ItemStack(id, amount);

			ConfigurationSection enchantments = this.config.getConfigurationSection(node + ".armor." + place + ".enchantment");
			
			if(enchantments != null) {
				
				for(String s : enchantments.getKeys(false)) {
					
					Enchantment enchantment = Enchantment.getByName(s);
					
					if(enchantment != null) {
						
						int i = enchantments.getInt(s);
						
						item.addUnsafeEnchantment(enchantment, i);
						
					}
					
				}
				
			}

			ConfigurationSection section = this.config.getConfigurationSection(node + ".armor." + place + ".lore");
			
			List<String> lore = null;
			
			ItemMeta meta = item.getItemMeta();
			
			if(section != null) {
			
				lore = new ArrayList<String>();
				
				Set<String> set = section.getKeys(false);
				
				for(String s : set) {
					
					lore.add(this.config.getString(node + ".armor." + place + ".lore." + s));
					
				}
				
				meta.setLore(lore);
			
			}
			
			String title = this.config.getString(node + ".armor." + place + ".title");
			
			if(title != null) {
				
				meta.setDisplayName(title);
			
			}
			
			item.setItemMeta(meta);
				
			item.setDurability((short) damage);
			item.setData(new MaterialData(data));
			
			return item;
			
		}
		
		return null;
		
	}
	
	public void setInventory(String node, Inventory inventory) {
		
		if(node != null && inventory != null) {
			
			this.config.set(node + ".items", null);
			this.config.set(node + ".armor", null);
			
			for(int i = 0; i < inventory.getSize(); i++) {
				
				ItemStack item = inventory.getItem(i);
				
				if(item != null) {
				
					this.setItemStackInInventory(node, item, i);
				
				}
				
			}
			
			if(inventory instanceof PlayerInventory) {
				
				PlayerInventory inv = (PlayerInventory) inventory;
				
				this.setArmorItemStackInInventory(node, inv.getHelmet(), 0);
				this.setArmorItemStackInInventory(node, inv.getChestplate(), 1);
				this.setArmorItemStackInInventory(node, inv.getLeggings(), 2);
				this.setArmorItemStackInInventory(node, inv.getBoots(), 3);
				
			} else {
				
				for(int i = 0; i < 4; ++i) {
					
					this.setArmorItemStackInInventory(node, null, i);
					
				}
				
			}
			
		}
		
	}
	
	public void setPlayerInventory(String node, User user) {
		
		PlayerInventory inv = user.getPlayer().getInventory();

		ConfigurationSection armor = this.config.getConfigurationSection(node + ".armor");
		
		if(armor != null) {
			
			boolean del = true;
			
			for(String s : armor.getKeys(false)) {
				
				int i = -1;
				
				if(s != null) {
				
					try {
						i = Integer.parseInt(s);
					} catch(Exception e) {
						
					}
					
					if(i != -1) {
					
						ItemStack item = this.getArmorItemStackInInventory(node, i);
						
						if(del) {
						
							((PlayerInventory) inv).setHelmet(null);
							((PlayerInventory) inv).setChestplate(null);
							((PlayerInventory) inv).setLeggings(null);
							((PlayerInventory) inv).setBoots(null);
							
							del = false;
						
						}
						
						if(i == 0) {
							 
							((PlayerInventory) inv).setHelmet(item);
							
						}
						
						if(i == 1) {
							 
							((PlayerInventory) inv).setChestplate(item);
							
						}
						
						if(i == 2) {
							 
							((PlayerInventory) inv).setLeggings(item);
							
						}
						
						if(i == 3) {
							 
							((PlayerInventory) inv).setBoots(item);
							
						}
					
					}
				
				}
				
			}
						
		}
		
		if(node != null) {

			ConfigurationSection section = this.config.getConfigurationSection(node + ".items");
			
			if(section != null) {
			
				for(String s : section.getKeys(false)) {
					
					int i = -1;
					
					if(s != null) {
					
						try {
							i = Integer.parseInt(s);
						} catch(Exception e) {
							
						}
						
						if(i != -1) {
						
							ItemStack item = this.getItemStackInInventory(node, i);
							
							inv.setItem(i, item);
						
						}
					
					}
					
				}
			
			}
			
		}
		
	}
	
	public Inventory getInventory(String node) {
		
		Inventory inv = Bukkit.createInventory(null, 9 * 6);
		
		if(node != null) {

			ConfigurationSection section = this.config.getConfigurationSection(node + ".items");
			
			for(String s : section.getKeys(false)) {
				
				int i = -1;
				
				if(s != null) {
				
					try {
						i = Integer.parseInt(s);
					} catch(Exception e) {
						
					}
					
					if(i != -1) {
					
						ItemStack item = this.getItemStackInInventory(node, i);
						
						inv.setItem(i, item);
					
					}
				
				}
				
			}
			
		}
		
		return inv;
		
	}
	
	public int getInt(String node) {
		
		return (Integer) this.get(node);
		
	}
	
	public double getDouble(String node) {
		
		return (Double) this.get(node);
		
	}
	
	public String getString(String node) {
		
		return (String) this.config.getString(node);
		
	}
	
	public ConfigurationSection getConfigurationSection(String node) {
		
		return this.config.getConfigurationSection(node);
		
	}
	
	public boolean contains(String node) {
		
		return this.config.contains(node);
		
	}
	
	public boolean exists(String node) {
		
		return this.config.contains(node);
		
	}
	
	public File getFile() {
		
		return this.file;
		
	}
	
	public String removeChatColors(String s){
		
		for(int i = 0; i < this.options.length(); i++) {
			
			s = s.replaceAll("&" + this.options.charAt(i), "");
			
		}
		
		return s;
		
	}
	
}
