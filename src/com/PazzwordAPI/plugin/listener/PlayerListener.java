package com.PazzwordAPI.plugin.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.PazzwordAPI.plugin.Core;
import com.PazzwordAPI.plugin.util.player.User;

public class PlayerListener implements Listener {

	private Core core;
	
	public PlayerListener(Core core) {
		
		this.core = core;
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		
		this.core.addUser(event.getPlayer().getUniqueId());
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		
		this.core.removeUser(event.getPlayer().getUniqueId());
		
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		
		User user = this.core.getUser(event.getPlayer().getUniqueId());
		
		if(user.getInventory() != null) {
			
			user.getInventory().close();
			
		}
		
	}
	
	@EventHandler
	public void onItemClick(InventoryClickEvent event) {
		
		User user = this.core.getUser(event.getWhoClicked().getUniqueId());
		
		if(user.getInventory() != null && event.getClickedInventory() != user.getPlayer().getInventory()) {
			
			user.getInventory()._click(event.getSlot(), event.getClick());
			
			event.setCancelled(true);
			
		}
		
	}
	
}
