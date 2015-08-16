package com.westre.nucleardawn;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerLocation implements Runnable {

	@Override
	public void run() {
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			if(!p.isDead()) {
				User user = UserManager.getUser(p);
				if(user.isNearSafeBlock(10)) {
					if(user.isInSafeZone() == false) {
						user.sendMessage("You are in a 'safe' zone. This zone makes your attrition level go down.");
						if(user.getAttritionLevel() > 50)
							user.setAttritionLevel(user.getAttritionLevel() - 10);
						user.setInSafeZone(true);
					}
				}	
				else {
					if(user.isInSafeZone())
						user.sendMessage("You have left the 'safe' zone. Good luck!");
					user.setInSafeZone(false);
				}
				
				if(!user.isInValidArea()) {
					user.sendMessage("Radiation detected... return to area.");
					p.setHealth(p.getHealth() - 1);
				}
			}
		}
	}

}
