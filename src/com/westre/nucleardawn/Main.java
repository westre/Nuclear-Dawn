package com.westre.nucleardawn;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public final PlayerJoin joinListener = new PlayerJoin();
	public final PlayerQuit quitListener = new PlayerQuit();
	public final PlayerChat chatListener = new PlayerChat();
	public final PlayerMove moveListener = new PlayerMove();
	public final PlayerSpawn spawnListener = new PlayerSpawn();
	public final PlayerClick clickListener = new PlayerClick(this);
	public final ExplodeListener explodeListener = new ExplodeListener();
	public final DamageListener damageListener = new DamageListener();
	public final BlockListener blockListener = new BlockListener();
	
	public static HashMap<Player, User> players = new HashMap<Player, User>();
	public Commands executor;
	
	public static World defaultWorld;
	public static final String mysqlHost = "localhost";
	public static final String mysqlDatabase = "nucleardawn";
	public static final String mysqlUser = "root";
	public static final String mysqlPassword = "";
	
	public static final String website = "http://www.freetopian.com";
	public static Connection connection;
	
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(this.joinListener, this);
		pm.registerEvents(this.quitListener, this);
		pm.registerEvents(this.chatListener, this);
		pm.registerEvents(this.moveListener, this);
		pm.registerEvents(this.spawnListener, this);
		pm.registerEvents(this.clickListener, this);
		pm.registerEvents(this.explodeListener, this);
		pm.registerEvents(this.damageListener, this);
		pm.registerEvents(this.blockListener, this);
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://"+ Main.mysqlHost +"/"+ Main.mysqlDatabase, Main.mysqlUser, Main.mysqlPassword);
		}
		catch (SQLException ex) {
			System.out.println("NO DATABASE FOUND!!!");
		}
		
		executor = new Commands();
		getCommand("chest").setExecutor(executor);
		
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			UserManager.addUser(p.getPlayer());
			defaultWorld = p.getWorld();
		}
		
		if(ChestLoot.firstTime == true && defaultWorld != null) {
			System.out.println("A player has joined the server, initializing chests...");
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
		
		try{
            @SuppressWarnings("rawtypes")
            Class[] args = new Class[3];
            args[0] = Class.class;
            args[1] = String.class;
            args[2] = int.class;
 
            Method a = net.minecraft.server.EntityTypes.class.getDeclaredMethod("a", args);
            a.setAccessible(true);
 
            a.invoke(a, SuperZombie.class, "Zombie", 54);
            a.invoke(a, SuperCreeper.class, "Creeper", 50); //entitytype
            a.invoke(a, SuperChicken.class, "Chicken", 93); //entitytype
        }catch (Exception e){
            e.printStackTrace();
            this.setEnabled(false);
        }
		
		getServer().getScheduler().scheduleAsyncRepeatingTask(this, new PlayerLocation(), 60L, 20L); // seconden*20 (6000L = 5 min)
		getServer().getScheduler().scheduleAsyncRepeatingTask(this, new ChestRefresh(), 60L, 24000L); // seconden*20 (6000L = 5 min)
	}
	
	@Override
	public void onDisable() {
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			User user = UserManager.getUser(p);
			user.save();
			
			UserManager.removeUser(p.getPlayer());
		}
		
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			defaultWorld = p.getWorld();
			break;
		}
		
		if(defaultWorld != null) {
			for(int row = 0; row < ChestLoot.chests.length; row++) {
				Location loc = new Location(defaultWorld, ChestLoot.chests[row][0], ChestLoot.chests[row][1], ChestLoot.chests[row][2]);
				Chest chest = (Chest)loc.getBlock().getState();
				chest.getInventory().clear();
				chest.update();
		        loc.getBlock().setType(Material.AIR);
	        }
		}
	}
}
