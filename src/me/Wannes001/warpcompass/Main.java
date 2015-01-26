package me.Wannes001.warpcompass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin implements Listener {
	public Inventory warpInv;
	
	public HashMap<String, Warp> warps = new HashMap<String, Warp>();
	
	public int max;

	public void onEnable() {
		FileConfiguration config = this.getConfig();
		config.options().copyDefaults(true);
		this.max = config.getInt("max");
		for (String s : config.getStringList("warps")) {	
			String path = "data." + s +  ".";
			String color = config.getString(path + "p");
			String author = config.getString(path + "a");
			ItemStack item = Utils.getItem(config.getString(path + "i"));
			Location loc = Utils.getLocation(config.getString(path + "l"));
			
			this.warps.put(s, new Warp(s, color, author, item, loc));
			
		}
		
		Bukkit.getPluginManager().registerEvents(this,  this);
		updateInventory();
		
	}
	
	public void OnDisable() {
		save();
	}
	public void save() {
		FileConfiguration config = this.getConfig();
		List<String> wl = new ArrayList<String>();
		for (String s : warps.keySet()) {
			wl.add(s);
			String path = "data." + s + ".";
			Warp w = this.warps.get(s);
			config.set(path + "p", w.getColor ().replaceAll("§", "~"));
			config.set(path + "a", w.getCreator());
			config.set(path + "i", Utils.getString(w.getItem()));
			config.set(path + "l", Utils.getString(w.getLocation()));
			
		}
		config.set("warps",wl);
		
		try {
			config.save(this.getFile());
		} catch(IOException ex) {
			this.getLogger().info("Failed to save config.yml");
			}
		}
	
	public void updateInventory() {
		this.warpInv = Bukkit.createInventory(null, Utils.getInventorySize(this.warps.size()), "§b§Warps");
		for (Warp w : this.warps.values()) {
			this.warpInv.addItem(w.getItem());
		}
	}
	
	public void onDisable() {
		
	}
	// cmd args[0] args[1]
	// /wc, add wanneswc
	
	// /wc [add/edit/remove] [name]
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("warpcompass")) {
			
			if (sender instanceof Player) {
			
				if (args.length > 0 && sender.hasPermission("warpcompass.create")) {
					
					if (args[0].equalsIgnoreCase("add")) {
						
						if (args.length > 1) {
							
							if (warps.size() < max) {
								
								if (warps.get(args[1]) == null) {
									
								    if (((Player) sender).getItemInHand() !=null) {
								    	String color = "";
								    	if (args.length > 2) {color = args[2].replaceAll("&", "§");}
								    	this.warps.put(args[1], new Warp(args[1], color, sender.getName(), ((Player) sender).getItemInHand(), ((Player) sender).getLocation()));
								    	updateInventory();
								    	save();
								    	sender.sendMessage("§Succesfully added the warp: §5" + args[1] + " §a!");

								    } else {
								    	sender.sendMessage("§cError 404 No item in hand found!");
								    }
								
								} else {
									
									sender.sendMessage("§cA warp with the name §5" + args[1] + " §calready exists!");
									
								}
									
							} else {
								
								sender.sendMessage("§cYou reached the amount limit for warps!");
							}
						} else {
							sender.sendMessage("§cUsage: /warpcompass add <name>");
							
						}
						return true;
					} else if (args[0].equalsIgnoreCase("edit")) {
						
						if (args.length > 1) {
							
						} else {
							sender.sendMessage("§cUsage: /warpcompass edit <name>");
						}
						return true;
					} else if (args[0].equalsIgnoreCase("remove")) {
						
						if (args.length > 1) {
							
						} else {
							sender.sendMessage("§cUsage: /warpcompass remove <name>");
						}
						return true;
					}	
				
				}
				((Player) sender).openInventory(this.warpInv);
			} else {
				sender.sendMessage("§cOnly players may do this!");
			}
		}
	return false;
	}
	
	public Warp getWarp(ItemStack is) {
		if (is !=null) {
			for(Warp w : this.warps.values()) {
				if (w.getItem().equals(is)) {
					return w;
				}
			}
		}
		return null;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().getName().equals("§b§Warps")) {
			e.setCancelled(true);
			Warp w = getWarp(e.getCurrentItem());
			if (w !=null) {
				e.getWhoClicked().teleport(w.getLocation());
			}
		}
	}
}