package com.PazzwordAPI.plugin.events;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.material.Attachable;
import org.bukkit.material.MaterialData;

import com.PazzwordAPI.plugin.util.player.User;

public final class SignInteractEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private User user = null;
	private Block block;
	private String[] messages;
	private Action action;

	public SignInteractEvent(User user, Block block, String[] messages, Action action) {
		this.user = user;
		this.block = block;
		this.messages = messages;
		this.action = action;
	}
	
	public User getUser() {
		
		return this.user;
		
	}
	
	public Block getBlock() {
		
		return this.block;
		
	}

	public String[] getMessages() {
		return messages;
	}

	public String getMessage(int i) {

		if (i >= 0 && 4 > i) {

			return this.getMessages()[i];

		}

		return null;

	}
	
	public Block getAttachedBlock() {
		
		Sign sign = (Sign) this.getBlock().getState();

        MaterialData m = sign.getData();
        BlockFace face = BlockFace.DOWN;
        if (m instanceof Attachable) {
            face = ((Attachable) m).getAttachedFace();
        }
        return this.getBlock().getRelative(face);
		
	}
	
	public Action getAction() {
		
		return this.action;
		
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}