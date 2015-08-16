package com.westre.nucleardawn;

import org.bukkit.entity.Player;

public class UserManager {
	
	public static void addUser(Player player) {
		Main.players.put(player, new User(player));
	}
	
	public static void removeUser(Player player) {
		Main.players.remove(player);
	}
	
	public static User getUser(Player player) {
		return Main.players.get(player.getPlayer());
	}
}
