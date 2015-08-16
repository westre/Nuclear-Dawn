package com.westre.nucleardawn;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;


public class PlayerQuit implements Listener {
	
	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent e) {
		User user = UserManager.getUser(e.getPlayer());
		user.save();
		
		UserManager.removeUser(e.getPlayer());
	}
}
