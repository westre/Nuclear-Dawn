package com.westre.nucleardawn;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CheckDrink implements Runnable {
    private Player p;
    private int slot;
 
    public CheckDrink(Player p, int slot){
        this.p = p;
        this.slot = slot;
    }
 
    @SuppressWarnings("deprecation")
	@Override
    public void run() {
        if(p.getInventory().getHeldItemSlot() == slot && p.getItemInHand().getType().equals(Material.GLASS_BOTTLE)) {
        	short potion = p.getItemInHand().getDurability();
        	if(potion == 0) {
        		p.sendMessage(ChatColor.AQUA + "You drank water! Your thirst has been replenished!");
        		User user = UserManager.getUser(p);
        		user.setThirstLevel(user.getThirstLevel() + 5);
        	}
        	else if(potion == 32) {
        		p.sendMessage(ChatColor.AQUA + "You drank some juice! Your thirst has been replenished!");
        		User user = UserManager.getUser(p);
        		user.setThirstLevel(user.getThirstLevel() + 8);
        	}
        	p.getInventory().setItemInHand(null);
    		p.updateInventory();
        }
    }
}
