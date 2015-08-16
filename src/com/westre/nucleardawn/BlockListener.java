package com.westre.nucleardawn;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		User user = UserManager.getUser(player);
		
		if(player.getLevel() >= 25) {
			player.setLevel(player.getLevel() - 1);
			user.sendMessage("You broke a block, 1 level has been subtracted");
		}
		else {
			if(!player.isOp()) {
				event.setCancelled(true);
				user.sendMessage("You need to be level 25 to break blocks!");
			}
		}
			
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		User user = UserManager.getUser(event.getPlayer());
		if(event.getBlockPlaced().getType() == Material.NETHERRACK || event.getBlockPlaced().getType() == Material.FIRE ) {
			user.sendMessage("Go ahead! Light it up!");
		}
		else {
			if(!event.getPlayer().isOp()) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onFireSpread(BlockIgniteEvent event) {
		if(event.getCause() == IgniteCause.SPREAD) {
			event.setCancelled(true);
		}
	}
}
