package com.westre.nucleardawn;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerClick implements Listener {
	
	private Main main;
	
	public PlayerClick(Main main) {
		this.main = main;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(player.getItemInHand().getType() == Material.POTION) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(main, new CheckDrink(player, player.getInventory().getHeldItemSlot()), 33L);
		}
		
		if((e.getAction() == Action.RIGHT_CLICK_AIR || (e.getAction() == Action.RIGHT_CLICK_BLOCK))) {
            if(player.getItemInHand().getTypeId() == Material.WRITTEN_BOOK.getId()) {
            	User user = UserManager.getUser(player);
                player.getInventory().setItemInHand(null);
                player.updateInventory();
                user.giveStatsBook();
            }
        }
		
		
		/*
		Player player = e.getPlayer();
        if((e.getAction() == Action.RIGHT_CLICK_BLOCK) || (e.getAction() == Action.RIGHT_CLICK_AIR)) {
            if(player.getItemInHand().getType() == Material.POTION && player.getItemInHand().getDurability() == 0) {
            	player.getInventory().setItemInHand(null);
                player.sendMessage("You drink the potion");
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1000, 0));
            }
        }*/
	}
}
