package me.Wannes001.warpcompass;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


public class Utils {
	public static ItemStack getItem(String s) {
		try{
			String[] l = s.split("/");
			return new ItemStack(Material.getMaterial(l[0]), Integer.parseInt(l[1]), Short.parseShort(l[2]));
		} catch (Exception ex) {
			return new ItemStack(Material.BEDROCK);
		}
	}
	
	public static Location getLocation(String s) {
		try {
			String[] l = s.split("/");
			Location loc = new Location(Bukkit.getWorld(l[0]), Double.parseDouble(l[1]), Double.parseDouble(l[2]), Double.parseDouble(l[3]));
			loc.setPitch(Float.parseFloat(l[4]));
			loc.setYaw(Float.parseFloat(l[5]));
			return loc;
		} catch (Exception ex) {
			return null;
		}
	}

	public static int getInventorySize(int size) {
		if (size%9 == 0) {
			return size;
		}
		return 9 + (size - (size % 9));
	}
	
	public static String getString(ItemStack is) {
		return is.getType().name() + "/" + is.getAmount() + "/" + is.getDurability();
	
	}
	
	public static String getString(Location loc) {
		return loc.getWorld() + "/" + loc.getX() + "/" + loc.getY() + "/" + loc.getZ() + "/" + loc.getPitch() + "/" + loc.getYaw(); 
	}
	
}
