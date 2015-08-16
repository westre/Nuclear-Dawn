package com.westre.nucleardawn;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class User {
    private	Player player;
	private int ID, firstJoined, thirstLevel, attritionLevel, minutesPlayed, timesDied, adminID;
	private String rank;
	private BookItem bi;
	private boolean isInSafeZone = false;
	
	public int tempThirstCounter, tempAttritionCounter, tempKilledEntity;
	
	public User(Player player) {
		Main.players.put(player.getPlayer(), this);
		this.player = player;
	}
	
	public void getFoodLevel() {
		int fl = player.getFoodLevel();
		if(fl < 3) {
			sendMessage("Your food level is getting critical! Eat or you will starve to death.");
		}
		else if(fl >= 3 && fl < 8) {
			sendMessage("Your food level is sufficient.");
		}
		else {
			sendMessage("You have enough food in your belly. Lucky you.");
		}
	}
	
	public int[] getLocation() {
		int[] location = {player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ()};
		return location;
	}
	
	public void save() {
		try {
			PreparedStatement ps = Main.connection.prepareStatement("UPDATE User SET food = ?, thirst = ?, attritionlevel = ?, timesdied = ?, adminid = ?, rank = ?, level = ? WHERE name = ?");
			
			ps.setInt(1, player.getFoodLevel());
			ps.setInt(2, getThirstLevel());
			ps.setInt(3, getAttritionLevel());
			ps.setInt(4, getTimesDied());
			ps.setInt(5, getAdminID());
			ps.setString(6, "Undefined"); //???
			if(player.getLevel() < 1) ps.setInt(7, 0); else ps.setInt(7, player.getLevel());
			ps.setString(8, player.getName());
			
		    ps.executeUpdate();
		    ps.close();
		} catch (SQLException se) {
			Bukkit.broadcastMessage(se.getMessage());
		}
	}
	
	@SuppressWarnings("deprecation")
	public void giveStatsBook() {
		bi = new BookItem(new ItemStack(387,1));
		ItemStack writtenbook = bi.getItemStack();
		String[] pages = new String[] {
			"Your stats:\nHL: " + player.getFoodLevel() + "/20\nTL: " + getThirstLevel() + "/20\nAL: " + getAttritionLevel() + "/10000",
			"HL = Hunger Level\nTL = Thirst Level\nAL = Attrition Level"
		};
		bi.setPages(pages);
		bi.setAuthor(player.getName() + " (DONT REMOVE)");
		bi.setTitle("My Stats");
		player.getInventory().addItem(writtenbook);
	    player.updateInventory();
	}
	
	public boolean isNearSafeBlock(int radius) {
        World world = player.getLocation().getWorld();
        int x = (int) player.getLocation().getX(), y = (int) player.getLocation().getY(), z = (int) player.getLocation().getZ();
        for (int ox = 0; ox > -radius; ox--) {
        	for (int oy = 0; oy > -5; oy--) { // 5 up or down Y
	            for (int oz = 0; oz > -radius; oz--) {
	                if (world.getBlockAt(x + ox, y + oy, z + oz).getType() == Material.EMERALD_BLOCK) {
	                    return true;
	                }
	                if (world.getBlockAt(x + ox, y + 2 + oy, z + oz).getType() == Material.FIRE) {
	                    return true;
	                }
	                if (world.getBlockAt(x + ox, y + oy, z + oz).getType() == Material.STATIONARY_LAVA) {
	                    return true;
	                }
	            }
        	}
        }
        return false;
    }
	
	public boolean isInValidArea() {
		World world = player.getWorld();
		Location loc = player.getLocation();
        Biome biome = world.getBiome(loc.getBlockX(), loc.getBlockZ());
        
        if(biome == Biome.FROZEN_OCEAN || biome == Biome.FROZEN_RIVER || biome == Biome.ICE_MOUNTAINS || biome == Biome.ICE_PLAINS
        		|| biome == Biome.TAIGA || biome == Biome.TAIGA_HILLS) return true;
        
		return false;
	}
	
	public boolean isInDownfall() {
        World world = player.getWorld();
        
        if (world.hasStorm()){
            Location loc = player.getLocation();
            Biome biome = world.getBiome(loc.getBlockX(), loc.getBlockZ());
            
            if (biome != Biome.DESERT && biome != Biome.DESERT_HILLS && world.getHighestBlockYAt(loc) <= loc.getBlockY()){
                return true;
            }
        }
        
        return false;
    }
	
	public BookItem getStatsBook() {
		return this.bi;
	}
	
	public void sendMessage(String message) {
		player.sendMessage(ChatColor.GRAY + message);
	}
	
	public void sendMessage(ChatColor color, String message) {
		player.sendMessage(color + message);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getFirstJoined() {
		return firstJoined;
	}

	public void setFirstJoined(int firstJoined) {
		this.firstJoined = firstJoined;
	}

	public int getThirstLevel() {
		return thirstLevel;
	}

	public void setThirstLevel(int thirstLevel) {
		this.thirstLevel = thirstLevel;
	}

	public int getAttritionLevel() {
		return attritionLevel;
	}

	public void setAttritionLevel(int attritionLevel) {
		this.attritionLevel = attritionLevel;
	}

	public int getMinutesPlayed() {
		return minutesPlayed;
	}

	public void setMinutesPlayed(int minutesPlayed) {
		this.minutesPlayed = minutesPlayed;
	}

	public int getTimesDied() {
		return timesDied;
	}

	public void setTimesDied(int timesDied) {
		this.timesDied = timesDied;
	}

	public int getAdminID() {
		return adminID;
	}

	public void setAdminID(int adminID) {
		this.adminID = adminID;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
	
	public String getRank() {
		return rank;
	}

	public void reset() {
		setThirstLevel(20);
		setAttritionLevel(0);
		tempThirstCounter = 0;
	}

	public boolean isInSafeZone() {
		return isInSafeZone;
	}

	public void setInSafeZone(boolean isInSafeZone) {
		this.isInSafeZone = isInSafeZone;
	}
}
