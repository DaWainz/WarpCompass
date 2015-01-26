package me.Wannes001.warpcompass;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Warp {

	private ItemStack item;
	private Location location;
	private String color;
	private String creator;
	
	public Warp(String name, String color, String creator, ItemStack item, Location location) {
		
		
		
		this.location = location;
		this.item = item;
		this.color = color;
		this.creator = creator;
		ItemMeta im = this.item.getItemMeta();
		im.setDisplayName(color + name);
		this.item.setItemMeta(im);
	}
	
	public String getColor() {
		return this.color;
	}
	
	public String getName() {
		return this.item.getItemMeta().getDisplayName();
	}
	
	public String getCreator() {
		return this.creator;
	}
	
	public ItemStack getItem() {
		return this.item;
	}
	public Location getLocation() {
		return this.location;
	}
}
