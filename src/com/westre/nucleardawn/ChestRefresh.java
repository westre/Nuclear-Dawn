package com.westre.nucleardawn;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;

public class ChestRefresh implements Runnable {

	World defaultWorld;
	
	@Override
	public void run() {

		if(ChestLoot.firstTime == false) {
			ChestLoot.t1Chests = 0;
			ChestLoot.t2Chests = 0;
			ChestLoot.t3Chests = 0;
			ChestLoot.t4Chests = 0;
			ChestLoot.rcChests = 0;
			
			if(defaultWorld != null) {
				for(int row = 0; row < ChestLoot.chests.length; row++) {
					Location loc = new Location(defaultWorld, ChestLoot.chests[row][0], ChestLoot.chests[row][1], ChestLoot.chests[row][2]);
					Chest chest = (Chest)loc.getBlock().getState();
					chest.getInventory().clear();
					chest.update();
			        loc.getBlock().setType(Material.AIR);
		        }
			}
			
			if(defaultWorld != null) {
				System.out.println("Refreshing chests...");
				for(int row = 0; row < ChestLoot.chests.length; row++) {
					Random random = new Random();
					int randomIndex = random.nextInt(100);
					
					if(randomIndex <= 50)
						new ChestLoot(5, ChestLoot.chests[row][0], ChestLoot.chests[row][1], ChestLoot.chests[row][2], defaultWorld);
					else if(randomIndex > 50 && randomIndex <= 70)
						new ChestLoot(1, ChestLoot.chests[row][0], ChestLoot.chests[row][1], ChestLoot.chests[row][2], defaultWorld);
					else if(randomIndex > 71 && randomIndex <= 85)
						new ChestLoot(2, ChestLoot.chests[row][0], ChestLoot.chests[row][1], ChestLoot.chests[row][2], defaultWorld);
					else if(randomIndex > 85 && randomIndex <= 95)
						new ChestLoot(3, ChestLoot.chests[row][0], ChestLoot.chests[row][1], ChestLoot.chests[row][2], defaultWorld);
					else
						new ChestLoot(4, ChestLoot.chests[row][0], ChestLoot.chests[row][1], ChestLoot.chests[row][2], defaultWorld);
		        }
				ChestLoot.firstTime = false;
				Bukkit.broadcastMessage("T1(50%): " + ChestLoot.t1Chests + ", T2(30%): " + ChestLoot.t2Chests + ", T3(15%): " + ChestLoot.t3Chests + ", T4(5%): " + ChestLoot.t4Chests);
			}
		}	
	}

}
