package com.westre.nucleardawn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoin implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerConnect(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		String playerName = e.getPlayer().getName();
		UserManager.addUser(e.getPlayer());
		PreparedStatement ps;
		
		try {
			ps = Main.connection.prepareStatement( "SELECT * FROM User WHERE name=?" );
			ps.setString(1, playerName);
			try {
				ResultSet rs = ps.executeQuery();

				boolean hasAccount = false;
				int id = -1;
				int firstjoined = -1;
				int thirst = -1;
				int attritionlevel = -1;
				int minutesplayed = -1;
				int timesdied = -1;
				int adminid = -1;
				String rank = "Undefined";

				while (rs.next()) {     
					id = rs.getInt("id");
					firstjoined = rs.getInt("firstjoined");
					thirst = rs.getInt("thirst");
					attritionlevel = rs.getInt("attritionlevel");
					minutesplayed = rs.getInt("minutesplayed");
					timesdied = rs.getInt("timesdied");
					adminid = rs.getInt("adminid");
					rank = rs.getString("rank");
					
					User user = UserManager.getUser(e.getPlayer());
					user.setID(id);
					user.setFirstJoined(firstjoined);
					user.setThirstLevel(thirst);
					user.setAttritionLevel(attritionlevel);
					user.setMinutesPlayed(minutesplayed);
					user.setTimesDied(timesdied);
					user.setAdminID(adminid);
					user.setRank(rank);
					user.sendMessage("Welcome back to Nuclear Dawn.");	
					
					hasAccount = true;
		        }
				rs.close();
				ps.close();
				
				if(!hasAccount) { 			
					player.sendMessage(ChatColor.RED + "No account found! Attempting to create account...");	
					
					try {
						ps = Main.connection.prepareStatement("INSERT INTO User (name, firstjoined, food, thirst, attritionlevel, minutesplayed, timesdied, adminid, rank, level) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
						long timestamp = System.currentTimeMillis() / 1000L;
						ps.setString(1, playerName);
						ps.setLong(2, timestamp);
						ps.setInt(3, -1);
						ps.setInt(4, 20);
						ps.setInt(5, -1);
						ps.setInt(6, -1);
						ps.setInt(7, -1);
						ps.setInt(8, 0);
						ps.setString(9, "Undefined");
						ps.setInt(10, -1);
						ps.executeUpdate();
						
						User user = UserManager.getUser(e.getPlayer());
						user.sendMessage("Welcome to Nuclear Dawn. Check your inventory for a book.");
						user.setThirstLevel(20);
						
						String author = "your predecessors";
				        String title = "Survival Guide";
				        String[] pages = new String[] {
						  "NUCLEAR DAWN ALPHA\n\nHello there!\nFigured you might not read the general guidelines and rules so we created a book!\nIf you continue reading this book, you might get a bonus!",
						  "Alright, some basic rules here. If you like being kicked or even banned, you might want to spam, show racist/sexist behavior, exploit bugs, being disrespectful towards other players, disobeying admins and deleting your brain.",
						  "Good, that's that. There are no gameplay rules, meaning you are free to kill everyone you see, team up, betray other people etc. This is a survival world, you die - you lose all your progress.",
						  "Watch who you trust, some people might appear to be your friend and eventually kill you for no reason. However, if you died, it's entirely your fault so don't whine about it. (or it's the server doing funny, in that case it's still your fault!)",
						  "Chests appear throughout the world, chests have 4 tiers, ranging from very low grade equipment to low grade equipment. Attrition plays a factor here. Since we play on a snow biome, your attrition level increases per fixed minutes.",
						  "You can keep your attrition level low by wearing clothing, staying near campfire and staying near/in buildings. One thing to keep in mind is that buildings are hotspots. If your attrition level reaches a critical level, you die.",
						  "Oh yeah, also eat and drink etc. You know the basic stuff. Beds give you a 1 time respawn on that location.\nThat's it.\n\nWelcome to Nuclear Dawn - your story begins here.",
						  "Oh, did you really think you would get that bonus?\nHa..Ha..HAHAHAHAHAHA."
				        };
				        BookItem bi = new BookItem(new ItemStack(387,1));
				        bi.setPages(pages);
				        bi.setAuthor(author);
				        bi.setTitle(title);
				        ItemStack writtenbook = bi.getItemStack();
				       
				        player.sendMessage("Giving finished book");
				        
				        player.getInventory().addItem(writtenbook);
				        player.updateInventory();
						ps.close();
						rs.close();
					}
					catch (SQLException se) {
						se.printStackTrace();
					}
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		} catch(SQLException ex) {
			
		}
		
		
		if(ChestLoot.firstTime == true) {
			System.out.println("A player has joined the server, initializing chests...");
			for(int row = 0; row < ChestLoot.chests.length; row++) {
				Random random = new Random();
				int randomIndex = random.nextInt(4) + 1;
				
				new ChestLoot(randomIndex, ChestLoot.chests[row][0], ChestLoot.chests[row][1], ChestLoot.chests[row][2], player.getWorld());
	        }
			Main.defaultWorld = player.getWorld();
			ChestLoot.firstTime = false;
		}
		
		player.sendMessage("T0: " + ChestLoot.rcChests + " T1: " + ChestLoot.t1Chests + ", T2: " + ChestLoot.t2Chests + ", T3: " + ChestLoot.t3Chests + ", T4: " + ChestLoot.t4Chests);
	}
}
