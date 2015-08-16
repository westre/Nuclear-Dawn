package com.westre.nucleardawn;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerMove implements Listener {
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		User user = UserManager.getUser(player);
		
		user.tempThirstCounter++;
		user.tempAttritionCounter++;
		
		if(user.tempThirstCounter == 500) { //500
			user.setThirstLevel(user.getThirstLevel() - 1);
			user.tempThirstCounter = 0;
			
			if(user.getThirstLevel() == 11) {
				user.sendMessage("You're starting to feel like you need some water...");
			}
			else if(user.getThirstLevel() == 6) {
				user.sendMessage("You're starting to feel some headaches.");
			}
			else if(user.getThirstLevel() == 4) {
				user.sendMessage("Your mouth is getting sore...");
			}
			else if(user.getThirstLevel() == 2) {
				user.sendMessage("You're having hallicunations.");
				player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 500, 0));
			}
			else if(user.getThirstLevel() == 1) {
				user.sendMessage("You're having hallicunations, dizziness and all the good things of dehydration.");
			}
			else if(user.getThirstLevel() == 0) {
				user.sendMessage(ChatColor.RED + "You died of dehydration.");
				player.setHealth(0);
			}
		}

		if(user.tempAttritionCounter == 1500) { // 150000
			//World w = player.getWorld();
			int attrition = 100;
			String attstr = "";
			
			ItemStack helm = player.getInventory().getHelmet();
			ItemStack chest = player.getInventory().getChestplate();
			ItemStack pants = player.getInventory().getLeggings();
			ItemStack boots = player.getInventory().getBoots();
			
			if(boots != null && boots.getType() == Material.LEATHER_BOOTS) {
			    attrition -= 5;
			    attstr += ChatColor.GREEN + "-5 ATR: " + ChatColor.GRAY + "Wearing Boots\n";
			}
			if(chest != null && chest.getType() == Material.LEATHER_CHESTPLATE) {
			    attrition -= 15;
			    attstr += ChatColor.GREEN + "-15 ATR: " + ChatColor.GRAY + "Wearing Chestplate\n";
			}
			if(helm != null && helm.getType() == Material.LEATHER_HELMET) {
				attstr += ChatColor.GREEN + "-8 ATR: " + ChatColor.GRAY + "Wearing Helmet\n";
			    attrition -= 8;
			}
			if(pants != null && pants.getType() == Material.LEATHER_LEGGINGS) {
				attstr += ChatColor.GREEN + "-10 ATR: " + ChatColor.GRAY + "Wearing Pants\n";
			    attrition -= 10;
			}
			/*if(w.isThundering()) {
				attrition += 20;
				attstr += ChatColor.RED + "+20 ATR: " + ChatColor.GRAY + "Snowing\n";
			}*/
			if(user.isInDownfall()) {
				attrition += 20;
				attstr += ChatColor.RED + "+20 ATR: " + ChatColor.GRAY + "Snowing\n";
			}
			attstr += ChatColor.RED + "+" + attrition + " ATR: " + ChatColor.GRAY + "Total Attrition";
			user.sendMessage(attstr);
			user.setAttritionLevel(user.getAttritionLevel() + attrition);
			user.tempAttritionCounter = 0;
		}
		if(player.getLocation().getY() >= 80) {
			user.setAttritionLevel(user.getAttritionLevel() + 1);
		}
	}
}
