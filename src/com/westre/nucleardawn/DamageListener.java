package com.westre.nucleardawn;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DamageListener implements Listener {
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		net.minecraft.server.Entity mcEntity = (((CraftEntity)damager).getHandle());
		
		if(damager instanceof Player) {
			Player player = (Player) damager;
			User user = UserManager.getUser(player);

			ArrayList<String> lores = Namer.getLores(player.getItemInHand());
		    
		    String weaponName = Namer.getName(player.getItemInHand());
		    int slot = -1;
		    if(weaponName.contains("Cursed")) 
		    	slot = 0;
		    else if(weaponName.contains("Torn")) 
		    	slot = 1;
		    else if(weaponName.contains("Stock")) 
		    	slot = 2;
		    else if(weaponName.contains("Solid")) 
		    	slot = 3;
		    else if(weaponName.contains("Polished")) 
		    	slot = 4;
		    else if(weaponName.contains("Shining")) 
		    	slot = 5;
		    else if(weaponName.contains("Powerful")) 
		    	slot = 6;
		    else if(weaponName.contains("Amazing")) 
		    	slot = 7;
		    else if(weaponName.contains("Epic")) 
		    	slot = 8;
		    else if(weaponName.contains("Supreme")) 
		    	slot = 9;
		    else if(weaponName.contains("Legendary")) 
		    	slot = 10;
		    else if(weaponName.contains("Ultimate")) 
		    	slot = 11;
		    else if(weaponName.contains("Transcendant")) 
		    	slot = 12;
		    else if(weaponName.contains("Godly")) 
		    	slot = 13;
		    else if(weaponName.contains("Mythical")) 
		    	slot = 14;
		    
		    int levelReq, dmgModifier;
		    if(slot >= 0) {
		    	levelReq = Integer.parseInt(ChestLoot.itemPrefix[slot][1]);
		    	dmgModifier = Integer.parseInt(ChestLoot.itemPrefix[slot][2]);
		    } 
		    else {
		    	return;
		    }
		    
		    if(player.getLevel() < levelReq) {
		    	if(!player.isOp()) {
		    		user.sendMessage("You need to be level " + levelReq + " to use this weapon.");
			    	event.setCancelled(true);
		    	}
		    }
		    
		    int damage = event.getDamage();
		    int addDamage = 0;
		    if(weaponName.contains("Old Sword")) {
		    	String additionalDamage = lores.get(2).substring(5, 7);
		    	int additionalDmg = Integer.parseInt(additionalDamage);
		    	if(event.getEntity() instanceof Cow || event.getEntity() instanceof Creeper || event.getEntity() instanceof Zombie)
		    		addDamage = additionalDmg;
		    }
		    event.setDamage(damage + dmgModifier + addDamage); 
		    LivingEntity le = (LivingEntity) event.getEntity();
		    
		    if((damage + dmgModifier + addDamage) < le.getHealth()) {
		    	user.sendMessage("You hit a " + event.getEntity().toString() + " with " + ChatColor.DARK_GREEN + damage + ChatColor.GRAY + " base damage, " 
	    				+ ChatColor.BLUE + dmgModifier + ChatColor.GRAY + " damage modifier and " + ChatColor.BLUE + addDamage + ChatColor.GRAY + " additional damage. "
	    				+ event.getEntity().toString() + " has " + ChatColor.RED + le.getHealth() + ChatColor.GRAY + " health left.");
		    }
		}
		
		if(damager instanceof Zombie && event.getEntity() instanceof Player) {

		}
		
		if(mcEntity instanceof SuperZombie && event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			User user = UserManager.getUser(player);
			
			Random rnd = new Random();
			int random = rnd.nextInt(9);
			
			if(random == 1) {
				user.sendMessage("You have been infected by a zombie host.");
				random = rnd.nextInt(3);
				if(random == 0)
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000, 1));
				else if(random == 1)
					player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1000, 1));
				else if(random == 2) {
					user.sendMessage("The infection didn't go as planned.. instead it has given you increased damage.");
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000, 1));
				}
				else if(random == 3)
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000, 1));
			}	
		}
	}
	
	@EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
		if(e.getEntity().getKiller() instanceof Player) {
			Entity damager = e.getEntity().getKiller();
			Player player = (Player)damager;
			User user = UserManager.getUser(player);
			
	        if(e.getEntity() instanceof LivingEntity) {
	        	user.sendMessage("It's dead. Yay!");
	        	user.tempKilledEntity = 1;
	        }
		}
    }
	
	@EventHandler
	public void onExperienceChange(PlayerExpChangeEvent e) {
		Player player = e.getPlayer();
		User user = UserManager.getUser(player);
		
		if(user.tempKilledEntity == 1) {
			user.sendMessage("You have received " + e.getAmount() + " experience.");
			user.tempKilledEntity = 0;
		}
	}
	
	@EventHandler
	public void onEntityBurn(EntityCombustEvent e) {
		e.setCancelled(true);
	}
}
