package com.PazzwordAPI.plugin.util.player.inventory;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public abstract class UserSkullItemStack extends UserItemStack {
	
	public ItemStack getItemStack(UserInventory inventory){
		
		ItemStack item = new ItemStack(this.getMaterial(inventory), this.getAmount(inventory), (byte) 3);
		
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		
		meta.setOwner(this.getOwner(inventory));
		
		meta.setDisplayName(this.getTitle(inventory));
		
		meta.setLore(this.getLore(inventory));
		
		item.setItemMeta(meta);
		
		return item;
		
	}
	
	@Override
	public Material getMaterial(UserInventory inventory){
		
		return Material.SKULL_ITEM;
	}
	
	@Override
	public int getData(UserInventory inventory){
		
		return 3;
		
	}
	
	public abstract String getTitle(UserInventory inventory);
	public abstract String getOwner(UserInventory inventory);
	public abstract List<String> getLore(UserInventory inventory);
	public abstract int getPlace(UserInventory inventory);
	public abstract int getAmount(UserInventory inventory);
	public abstract HashMap<String, Integer> getEnchantments(UserInventory inventory);
	public abstract void onClick(UserInventory inventory);
	
}
