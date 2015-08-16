package com.westre.nucleardawn;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.SpawnEgg;

public class ChestLoot {
	private int tier;
	
	public static int[][] chests = new int[][] {
		{-799, 64, -118},
		{-977, 67, -169},
		{-1065, 71, -34},
		{-1036, 67, 11},
		{-841, 61, 29},
		{-863, 76, -51},
		{-1237, 102, -158},
		{-1500, 68, -211},
		{-770, 59, -45},
		{-863, 63, 120},
		{-1069, 65, -122},
		{-1303, 88, -325},
		{-1483, 71, -210},
		{-1032, 64, -113},
		{-1108, 64, 10},
		{-854, 67, 201},
		{-435, 64, 3},
		{-690, 72, -83},
		{-452, 64, -26},
		{-815, 70, 233},
		{-580, 63, 572},
		{-552, 40, 559},
		{-1144, 60, -172},
		{-381, 74, 450},
		{-430, 63, 686},
		{-372, 63, 506},
		{-439, 61, 831},
		{-1058, 72, -13},
		{-903, 70, -309},
		{-689, 69, -459},
		{-873, 63, -215},
		{-1019, 67, -161},
		{-1193, 65, -408},
		{-1152, 58, -417},
	};
	
	public static boolean firstTime = true;
	public static int t1Chests, t2Chests, t3Chests, t4Chests, rcChests;
	
	public static String[][] itemPrefix = {
		//prefix, lvl, dmg mp
		{"Cursed", "1", "0"},
		{"Torn", "3", "1"},
		{"Stock", "4", "1"},
		{"Solid", "5", "1"},
		{"Polished", "7", "1"},
		{"Shining", "9", "1"},
		{"Powerful", "11", "1"},
		{"Amazing", "13", "2"},
		{"Epic", "14", "2"},
		{"Supreme", "15", "2"},
		{"Legendary", "17", "2"},
		{"Ultimate", "20", "2"},
		{"Transcendant", "22", "2"},
		{"Godly", "26", "3"},
		{"Mythical", "29", "3"},
	};
	
	private String[] itemSets = {
		"Topaz Set",
		"Sapphire Set",
		"Ruby Set",
		"Emerald Set",
		"Amethyst Set"
	};
	
	Random itemPrefixRandom = new Random();
	private int randomItemPrefix = itemPrefixRandom.nextInt(itemPrefix.length);
	
	Random itemSetRandom = new Random();
	private int randomItemSet = itemSetRandom.nextInt(itemSets.length);
	
	private ItemStack[] baseItems = {
		Namer.setName(new ItemStack(Material.STONE_HOE, 1), "§r" + itemPrefix[randomItemPrefix][0] + " Stone Hoe", "§f1 Base Damage", "§b+" + itemPrefix[randomItemPrefix][2] + " Damage Modifier", "§6Requires Level " + itemPrefix[randomItemPrefix][1], "§7§o'well, it's better than nothing...'", "§5" + itemSets[randomItemSet]),
		Namer.setName(new ItemStack(Material.WOOD_AXE, 1), "§r" + itemPrefix[randomItemPrefix][0] + " Wooden Axe", "§f3 Base Damage", "§b+" + itemPrefix[randomItemPrefix][2] + " Damage Modifier", "§6Requires Level " + itemPrefix[randomItemPrefix][1], "§5" + itemSets[randomItemSet]),
	    Namer.setName(new ItemStack(Material.BOW, 1), "§r" + itemPrefix[randomItemPrefix][0] + " Wooden Bow", "§cRequires Blunt Arrow", "§f4.5 Base Damage", "§b+" + itemPrefix[randomItemPrefix][2] + " Damage Modifier", "§6Requires Level " + itemPrefix[randomItemPrefix][1], "§5" + itemSets[randomItemSet]),
	    Namer.setName(new ItemStack(Material.ARROW, 3), "§r" + itemPrefix[randomItemPrefix][0] + " Blunt Arrow", "§cRequires Rusty Wooden Bow"),
	    Namer.setName(new ItemStack(Material.WOOD_SWORD, 1), "§r" + itemPrefix[randomItemPrefix][0] + " Old Sword", "§f4 Base Damage", "§b+" + itemPrefix[randomItemPrefix][2] + " Damage Modifier", "§6Requires Level " + itemPrefix[randomItemPrefix][1], "§5" + itemSets[randomItemSet]),
	    Namer.setName(new ItemStack(Material.WOOD_SWORD, 1), "§r" + itemPrefix[randomItemPrefix][0] + " Old Sword", "§f4 Base Damage", "§b+" + itemPrefix[randomItemPrefix][2] + " Damage Modifier", "§2+1 Additional Damage to Creepers", "§6Requires Level " + itemPrefix[randomItemPrefix][1], "§5" + itemSets[randomItemSet]),
	    Namer.setName(new ItemStack(Material.WOOD_SWORD, 1), "§r" + itemPrefix[randomItemPrefix][0] + " Old Sword", "§f4 Base Damage", "§b+" + itemPrefix[randomItemPrefix][2] + " Damage Modifier", "§2+1 Additional Damage to Zombies", "§6Requires Level " + itemPrefix[randomItemPrefix][1], "§5" + itemSets[randomItemSet]),
	    Namer.setName(new ItemStack(Material.WOOD_SWORD, 1), "§r" + itemPrefix[randomItemPrefix][0] + " Old Sword", "§f4 Base Damage", "§b+" + itemPrefix[randomItemPrefix][2] + " Damage Modifier", "§2+1 Additional Damage to Cows", "§6Requires Level " + itemPrefix[randomItemPrefix][1], "§5" + itemSets[randomItemSet]),
	    Namer.setName(new ItemStack(Material.STONE_SWORD, 1), "§r" + itemPrefix[randomItemPrefix][0] + " Broadsword", "§f5 Base Damage", "§cOnly useable as weapon", "§b" + itemPrefix[randomItemPrefix][2] + " Damage Modifier", "§6Requires Level " + itemPrefix[randomItemPrefix][1], "§5" + itemSets[randomItemSet]),
	    Namer.setName(new ItemStack(Material.NETHERRACK, 1), "§rCampfire", "§cRequires Lighter"),
	    Namer.setName(new ItemStack(Material.FLINT_AND_STEEL, 1), "§rLighter", "§cRequires Campfire"),
	    Namer.setName(new ItemStack(Material.MUSHROOM_SOUP, 1), "§rSoup, yay!", "§2+3 Food", "§7§o'makes me all comfy inside :)'"),
	    Namer.setName(new ItemStack(Material.EGG, 1), "§rChicken Egg", "§6Useless... or not?"),
	    Namer.setName(new ItemStack(Material.BONE, 1), "§rBone"),
	    Namer.setName(new ItemStack(373, 1, (short)0), "§rWater", "§2+5 Water"), // water
	    Namer.setName(new ItemStack(373, 1, (short)32), "§rJuice", "§2+8 Water"), // thick
	    Namer.setName(new ItemStack(Material.PAPER, 1), "§b[1] Passive Skill", "§2+2 Experience per kill", "§cDoes not stack"),
	    Namer.setName(new ItemStack(Material.PAPER, 1), "§b[2] Passive Skill", "§2-3 Attrition", "§cDoes not stack"),
	    Namer.setName(new ItemStack(Material.PAPER, 1), "§b[3] Passive Skill", "§2+1 Water per water bottle", "§cDoes not stack"),
	    Namer.setName(new ItemStack(Material.PAPER, 1), "§5[1] Active Skill", "§710 minute duration", "§6No chance of infection", "§cDoes not stack")
	};
	
	Random rndCraftItems = new Random();
	int randomCraftItems = rndCraftItems.nextInt(6) + 1;
	private ItemStack[] craftItems = {
		new ItemStack(Material.ANVIL, 1),
		new ItemStack(Material.BIRCH_WOOD_STAIRS, randomCraftItems),
		new ItemStack(Material.BOAT, 1),
		new ItemStack(Material.BOWL, randomCraftItems),
		new ItemStack(Material.BREWING_STAND, 1),
		new ItemStack(Material.CLAY_BRICK, randomCraftItems),
		new ItemStack(Material.DETECTOR_RAIL, 1),
		new ItemStack(Material.DIRT, randomCraftItems),
		new ItemStack(Material.EGG, randomCraftItems),
		new ItemStack(Material.FLINT, randomCraftItems),
		new ItemStack(Material.FISHING_ROD, 1),
		new ItemStack(Material.ICE, randomCraftItems),
		new ItemStack(Material.GRAVEL, randomCraftItems),
		new ItemStack(Material.MINECART, 1),
		new ItemStack(Material.RECORD_3, 1),
		new ItemStack(Material.YELLOW_FLOWER, randomCraftItems),
		new ItemStack(Material.WOOD, randomCraftItems),
		new ItemStack(Material.WEB, randomCraftItems),
		new ItemStack(Material.TORCH, randomCraftItems),
		new ItemStack(Material.SNOW, randomCraftItems),
		new ItemStack(Material.WORKBENCH, 1),
		new ItemStack(Material.SAPLING, randomCraftItems),
		new ItemStack(Material.SANDSTONE, randomCraftItems),
		new ItemStack(Material.SANDSTONE_STAIRS, randomCraftItems),
		new ItemStack(Material.SAND, randomCraftItems),
		new ItemStack(Material.WOOD_STEP, randomCraftItems),
		new ItemStack(Material.COBBLESTONE, randomCraftItems),
		new ItemStack(Material.COBBLESTONE_STAIRS, randomCraftItems),
		new ItemStack(Material.STONE, randomCraftItems),
	};
	
	Random random = new Random();
	private int randomIndex = random.nextInt(3);
	
	Random baseRandom = new Random();
	private int randomBase = baseRandom.nextInt(baseItems.length);
	
	Random craftRandom = new Random();
	private int randomCraft = craftRandom.nextInt(craftItems.length);
	
	private ItemStack[] tier1Items = {Namer.setName(new ItemStack(Material.BREAD, randomIndex), "§rOld Bread", "§2+2.5 Food"), Namer.setName(new ItemStack(Material.APPLE, 1), "§rApple", "§2+2 Food"), baseItems[randomBase]};
	private ItemStack[] tier2Items = {Namer.setName(new ItemStack(Material.APPLE, randomIndex), "§rApple Without Worms", "§2+2 Food"), Namer.setName(new ItemStack(Material.LEATHER_BOOTS, 1), "§rBoots", "§2-5 Attrition", "§2+0.5 Armor", "§5" + itemSets[randomItemSet]), Namer.setName(new ItemStack(373, 1, (short)0), "§rWater", "§2+5 Water"), baseItems[randomBase]};
	private ItemStack[] tier3Items = {Namer.setName(new ItemStack(Material.LEATHER_HELMET, 1), "§rComfy Hat", "§2-8 Attrition", "§2+0.5 Armor", "§5" + itemSets[randomItemSet]), Namer.setName(new ItemStack(Material.LEATHER_LEGGINGS, 1), "§rPants", "§2-10 Attrition", "§2+1 Armor", "§5" + itemSets[randomItemSet]), Namer.setName(new ItemStack(Material.BAKED_POTATO, randomIndex), "§rLonely Potato"), Namer.setName(new ItemStack(373, randomIndex, (short)0), "§rWater", "§2+5 Water"), baseItems[randomBase], baseItems[randomBase]}; // fix this
	private ItemStack[] tier4Items = {Namer.setName(new ItemStack(Material.LEATHER_LEGGINGS, 1), "§rPants", "§2-10 Attrition", "§+1 Armor", "§5" + itemSets[randomItemSet]), Namer.setName(new ItemStack(373, randomIndex, (short)32), "§rJuice", "§2+8 Water"), Namer.setName(new ItemStack(Material.LEATHER_CHESTPLATE, 1), "§rChestplate", "§2-15 Attrition", "§2+1.5 Armor", "§5" + itemSets[randomItemSet]), Namer.setName(new ItemStack(Material.BED, 1), "§rLucky Bastard", "§6Respawn on location"), baseItems[randomIndex], Namer.setName(new SpawnEgg(EntityType.WOLF).toItemStack(1), "§rWolf Companion", "§6Friend or Foe?")};
	private ItemStack[] tier5Items = {craftItems[randomCraft]};
	
	public ChestLoot(int tier, int x, int y, int z, World w) {
		Location loc = new Location(w, x, y, z);
		loc.getBlock().setType(Material.AIR);
		loc.getBlock().setType(Material.CHEST);
        this.tier = tier;
        
        if(loc.getBlock().getState() instanceof Chest) {
        	Chest chest = (Chest)loc.getBlock().getState();
        	int stringTier = tier;
        	if(stringTier == 5) stringTier = 0;
        	
        	String author = "your predecessors";
            String title = "Tier " + stringTier + " chest";
            String[] pages = new String[] {
            	"You found a tier " + stringTier + " chest. Jolly good!",
            	"Pssssst......\nYou might want to place this book back or burn it. You don't want to litter, do you? DO YOU?",
            	"DEV DEBUG\nTier " + stringTier + " chest created, randomIndex: " + randomIndex + ", randomBase: " + randomBase,
            };
            BookItem bi = new BookItem(new ItemStack(387,1));
            bi.setPages(pages);
            bi.setAuthor(author);
            bi.setTitle(title);
            ItemStack writtenBook = bi.getItemStack();
            chest.getInventory().addItem(writtenBook);
            
        	chest.getInventory().addItem(getTierItems());
        }
	}
	
	private ItemStack[] getTierItems() {
		if(tier == 1) {
			t1Chests++;
			return tier1Items;
		}
		else if(tier == 2) {
			t2Chests++;
			return tier2Items;
		}
		else if(tier == 3) {
			t3Chests++;
			return tier3Items;
		}
		else if(tier == 4) {
			t4Chests++;
			return tier4Items;
		}
		else if(tier == 5) {
			rcChests++;
			return tier5Items;
		}
		return null;
	}
}