package com.PazzwordAPI.plugin.util.player.inventory;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class UserItemStack {
	
	public ItemStack getItemStack(UserInventory inventory){
		
		ItemStack item = new ItemStack(this.getMaterial(inventory), this.getAmount(inventory), (byte) this.getData(inventory));
		
		item.setDurability(this.getDamage(inventory));
		
		ItemMeta meta = item.getItemMeta();
		
		String title = this.getTitle(inventory);
		
		meta.setDisplayName(title);
		
		meta.setLore(this.getLore(inventory));
		
		item.setItemMeta(meta);
		
		return item;
		
	}
	
	public abstract String getTitle(UserInventory inventory);
	public abstract List<String> getLore(UserInventory inventory);
	public abstract Material getMaterial(UserInventory inventory);
	public abstract int getPlace(UserInventory inventory);
	public abstract int getAmount(UserInventory inventory);
	public abstract int getData(UserInventory inventory);
	public abstract short getDamage(UserInventory inventory);
	public abstract HashMap<String, Integer> getEnchantments(UserInventory inventory);
	public abstract void onClick(UserInventory inventory);
	
}
