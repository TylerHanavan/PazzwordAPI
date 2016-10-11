package com.PazzwordAPI.plugin.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.PazzwordAPI.plugin.Core;
import com.PazzwordAPI.plugin.api.complex.SignAPI;
import com.PazzwordAPI.plugin.api.complex.SignAPI.SignInstance;
import com.PazzwordAPI.plugin.events.SignInteractEvent;
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
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		
		Block block = event.getClickedBlock();
		
		if(block != null) {
			
			if(block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST) {
				
				BlockState state = block.getState();
				
				if(state != null) {
					
					if(state instanceof Sign) {
						
						Sign sign = (Sign) state;
						
						SignInteractEvent signEvent = new SignInteractEvent(this.core.getUser(event.getPlayer().getUniqueId()), block, sign.getLines(), event.getAction());
						
						this.core.getServer().getPluginManager().callEvent(signEvent);
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event){ 
		
		Block block = event.getBlock();
		
		if(block != null) {
			
			BlockState state = block.getState();
			
			if(state != null) {
				
				if(state instanceof Sign) {
					
					Sign sign = (Sign) state;
					
					SignAPI.getSignInstance(sign, true);
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		Block block = event.getBlock();
		
		if(block != null) {
			
			BlockState state = block.getState();
			
			if(state != null) {
				
				if(state instanceof Sign) {
					
					Sign sign = (Sign) state;
					
					SignAPI.triggerSignDestroyed(SignAPI.getSignInstance(sign, false));
					
				}
				
			}
			
		}
		
	}
	
}
