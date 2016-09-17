package com.PazzwordAPI.plugin.util;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class ItemStacks {
	
	public static boolean compareLoose(ItemStack item1, ItemStack item2) {
		
		if(item1 == null && item2 == null) return true;
		if(item1 == null && item2 != null) return false;
		if(item1 != null && item2 == null) return false;
		
		if(item1.getType() != item2.getType()) return false;
		
		if(item1.getData().getData() != item2.getData().getData()) return false;
		
		return true;
		
	}

	public static boolean compare(ItemStack item1, ItemStack item2) {
		
		if(item1 == null && item2 == null) return true;
		if(item1 == null && item2 != null) return false;
		if(item1 != null && item2 == null) return false;
		
		Material type1 = item1.getType();
		Material type2 = item2.getType();
		
		if(type1 != type2) return false;
		
		int amount1 = item1.getAmount();
		int amount2 = item2.getAmount();
		
		if(amount1 != amount2) return false;
		
		MaterialData data1 = item1.getData();
		MaterialData data2 = item2.getData();
		
		if(data1.getData() != data2.getData()) return false;
		
		short damage1 = item1.getDurability();
		short damage2 = item2.getDurability();
		
		if(damage1 != damage2) return false;
		
		ItemMeta meta1 = item1.getItemMeta();
		ItemMeta meta2 = item2.getItemMeta();
		
		List<String> list1 = meta1.getLore();
		List<String> list2 = meta2.getLore();
		
		if(!compareLists(list1, list2)) return false;
		
		String title1 = meta1.getDisplayName();
		String title2 = meta2.getDisplayName();
		
		if(title1 == null && title2 != null) return false;
		if(title1 != null && title2 == null) return false;
		
		if(!title1.equals(title2)) return false;
		
		Map<Enchantment, Integer> enchantments1 = item1.getEnchantments();
		Map<Enchantment, Integer> enchantments2 = item2.getEnchantments();
		
		if(!compareEnchantments(enchantments1, enchantments2)) return false;
		
		return true;
		
	}
	
	private static boolean compareLists(List<String> list1, List<String> list2) {
		
		if(list1 == null && list2 == null) return true;
		
		if(list1 != null && list2 == null) return false;
		if(list1 == null && list2 != null) return false;
		
		if(list1.size() != list2.size()) return false;
		
		int c = 0;
		
		for(String s : list1) {
			
			if(s != null) {
				
				if(list2.get(c) == null || !list2.get(c).equals(s)) return false;
				
			}
			
			++c;
			
		}
		
		return true;
		
	}
	
	private static boolean compareEnchantments(Map<Enchantment, Integer> map1, Map<Enchantment, Integer> map2) {
		
		if(map1 == null && map2 == null) return true;
		
		if(map1 != null && map2 == null) return false;
		if(map1 == null && map2 != null) return false;
		
		for(Enchantment enchantment : map1.keySet()) {
			
			Integer i1 = map1.get(enchantment);
			Integer i2 = map2.get(enchantment);
			
			if(i2 == null || i1.intValue() != i2.intValue()) return false;
			
		}
			
		return true;
		
	}
	
}
