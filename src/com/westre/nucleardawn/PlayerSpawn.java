package com.westre.nucleardawn;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerSpawn implements Listener {

	@EventHandler
	public void onPlayerSpawn(PlayerRespawnEvent e) {
		User user = UserManager.getUser(e.getPlayer());
		user.reset();
		user.giveStatsBook();
	}
	
	@EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event){
        if (event.isCancelled()) return;
 
        Location location = event.getLocation();
        Entity entity = event.getEntity();
        Entity creatureType = event.getEntity();
        Random random = new Random();
        int rnd = random.nextInt(3);
        
        if(rnd == 1) {
        	World world = location.getWorld();
            net.minecraft.server.World mcWorld = ((CraftWorld) world).getHandle();
            net.minecraft.server.Entity mcEntity = (((CraftEntity) entity).getHandle());
     
            if (creatureType instanceof Zombie && mcEntity instanceof SuperZombie == false){
                SuperZombie bloodMoonEntityZombie = new SuperZombie(mcWorld);
     
                bloodMoonEntityZombie.setPosition(location.getX(), location.getY(), location.getZ());
     
                mcWorld.removeEntity((net.minecraft.server.EntityZombie) mcEntity);
                mcWorld.addEntity(bloodMoonEntityZombie, SpawnReason.CUSTOM);
            }
            
            if (creatureType instanceof Creeper && mcEntity instanceof SuperCreeper == false){
                SuperCreeper bloodMoonEntityCreeper = new SuperCreeper(mcWorld);
     
                bloodMoonEntityCreeper.setPosition(location.getX(), location.getY(), location.getZ());
     
                mcWorld.removeEntity((net.minecraft.server.EntityCreeper) mcEntity);
                mcWorld.addEntity(bloodMoonEntityCreeper, SpawnReason.CUSTOM);
            }
            
            if (creatureType instanceof Chicken && mcEntity instanceof SuperChicken == false){
            	SuperChicken bloodMoonEntityChicken = new SuperChicken(mcWorld);
            	bloodMoonEntityChicken.setPosition(location.getX(), location.getY(), location.getZ());
     
                mcWorld.removeEntity((net.minecraft.server.EntityChicken) mcEntity);
                mcWorld.addEntity(bloodMoonEntityChicken, SpawnReason.CUSTOM);
            }
        }
    }
}
