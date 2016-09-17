package com.PazzwordAPI.plugin.api;

import java.util.UUID;

import org.bukkit.entity.Entity;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

public class NPCAPI {

	private static NPCRegistry registry = null;
	
	private static NPCAPI api = null;
	
	public static NPCAPI getNPCAPI() {
		
		if(api == null) {
			api = new NPCAPI();
			registry = CitizensAPI.getNPCRegistry();
		}
		
		return api;
		
	}
	
	/* End static methods */
	
	public NPC getNPC(Object o) {
		
		UUID uuid = null;
		
		if(o instanceof UUID)
			uuid = (UUID) o;
		
		if(uuid != null)
			return registry.getByUniqueId(uuid);
			
		if(o instanceof Integer) 
			return registry.getById((Integer) o);
		
		if(o instanceof Entity)
			return registry.getNPC((Entity) o);
		
		return null;
		
	}
	
}
