package com.westre.nucleardawn;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player)sender;
		
		if(label.equalsIgnoreCase("chest")) {
			User user = UserManager.getUser(player);
			int[] location = user.getLocation();
	        World w = player.getWorld();
	        
	        new ChestLoot(1, location[0], location[1], location[2], w);
	        new ChestLoot(2, location[0], location[1]+2, location[2], w);
	        new ChestLoot(3, location[0], location[1]+4, location[2], w);
	        new ChestLoot(4, location[0], location[1]+6, location[2], w);
		}
		return false;
	}
}
