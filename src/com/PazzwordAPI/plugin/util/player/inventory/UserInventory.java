package com.PazzwordAPI.plugin.util.player.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.PazzwordAPI.plugin.util.ItemStacks;
import com.PazzwordAPI.plugin.util.player.User;

public class UserInventory {
	
	private User user = null;
	
	private List<UserItemStack> items = null;
	
	private int rows = 0;
	
	private int panes;
	
	private ItemStack redPane = null;
	
	private ItemStack limePane = null;
	
	private ItemStack greenPane = null;
	
	private String title = null;
	
	public UserInventory(User user){
		
		this.user = user;
		
		this.items = new ArrayList<UserItemStack>();
		
		this.rows = 0;
		
		this.panes = 1;
		
		this.redPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
		
		this.limePane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		
		this.greenPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 13);
		
	}
	
	public void setTitle(String title) {
		
		this.title = title;
		
	}
	
	public User getUser(){
		
		return this.user;
		
	}
	
	public UserItemStack getItemAt(int slot){
		
		UserItemStack item = null;
		
		/*if(item == null) {
			
			int c = 0;
			
			for(int i = 0; i < (this.rows == 0 ? 1 * 9 : this.rows * 9); i++) {
				
				UserItemStack curItemStack = this.items.get(c);
				
				if(i == slot)
					return curItemStack;
				
				++c;
				
			}
			
		}*/
		
		if(item == null) {
			
			for(int i = 0; i < this.getInventory().getSize(); i++) {
				
				ItemStack curItemStack = this.getInventory().getItem(i);
				
				if(curItemStack != null)
					if(i == slot)
						for(UserItemStack items : this.items)
							if(ItemStacks.compare(items.getItemStack(this), curItemStack))
								return items;
				
			}
			
		}
		
		return item;
		
	}
	
	public Inventory getInventory(){
		
		Inventory inv = null;
		
		try{
			
			if(this.title != null)
				inv = Bukkit.createInventory(this.user.getPlayer(), this.rows == 0 ? 1 * 9 : this.rows * 9, this.title);
			else
				inv = Bukkit.createInventory(this.user.getPlayer(), this.rows == 0 ? 1 * 9 : this.rows * 9);
		
		}catch(Exception e){
			
			this.user.getCore().debug("Could not create inventory");
			
		}
		
		for(UserItemStack items : this.getItems()){
			
			ItemStack item = items.getItemStack(this);
			
			int place = items.getPlace(this);
			
			if(place != -1)
				inv.setItem(items.getPlace(this), item);
			else
				inv.addItem(item);
			
		}
		
		if(this.getPanes()){
		
			int count = 0;
			
			int[] changes = new int[this.rows * 9];
			
			for(ItemStack item : inv.getContents()){
				
				if(item == null){
					
					changes[count] = 1;
					
				}else{
					
					changes[count] = 0;
					
				}
				
				count++;
				
			}
			
			ItemStack paneItemStack = this.getPaneItemStack();
			
			ItemMeta meta = paneItemStack.getItemMeta();
			
			meta.setDisplayName(" ");
			
			paneItemStack.setItemMeta(meta);
			
			for(int i = 0; i < changes.length; i++){
				
				if(this.panes == 1 && changes[i] == 1){
				
					inv.setItem(i, paneItemStack);
				
				}
				
			}
		
		}
		
		return inv;
		
	}
	
	public void close() {
		
		this.user.setInventory(null);
		
	}
	
	/**
	 * This method SHOULD NOT be overriden.
	 * @param slot
	 * @param type
	 */
	public void _click(int slot, ClickType type) {

		UserItemStack item = this.getItemAt(slot);
		
		if(item != null) {
		
			item.onClick(this);
			
			this.click(item, type);
		
		}
		
	}
	
	/**
	 * This method CAN be overriden.
	 * @param item
	 * @param type
	 */
	public void click(UserItemStack item, ClickType type) {
		
		
		
	}
	
	public List<UserItemStack> getItems(){
		
		return this.items;
		
	}
	
	public void addItem(UserItemStack item){
		
		if(!this.getItems().contains(item))
			this.items.add(item);
		
	}
	
	public boolean getPanes(){
		
		return this.panes != 0;
		
	}
	
	public ItemStack getPaneItemStack(){
		
		if(this.panes == 1){
			
			return this.redPane;
			
		}
		
		if(this.panes == 2){
			
			return this.limePane;
			
		}
		
		if(this.panes == 3){
			
			return this.greenPane;
			
		}
		
		return null;
		
	}
	
	public void setNoPanes(){
		
		this.panes = 0;
		
	}
	
	public void setLimePanes(){
		
		this.panes = 2;
		
	}
	
	public void setGreenPanes(){
		
		this.panes = 3;
		
	}
	
	public void setRows(int rows){
		
		this.rows = rows;
		
	}
	
}
