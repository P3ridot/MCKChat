package me.mckoxu.mckchat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import me.mckoxu.mckchat.command.ChatCmd;
import me.mckoxu.mckchat.listener.ChatListener;

public class MCKChat extends JavaPlugin{
	
	private static MCKChat instance;
	//AUTOMSG
	private int amt;
	private int time;
	private int size;
	private String type;
	
	public void onEnable(){
		instance = this;
	    PluginDescriptionFile p = getDescription();
	    if (!p.getName().contains("MCKChat")){
	    	Bukkit.getLogger().info("[MCKChat] Wykryto zmiane w pliku plugin.yml ,wylaczanie pluginu");
	    	Bukkit.getServer().getPluginManager().disablePlugin(this);
	    }
	    if (!p.getAuthors().contains("McKoxu")){
	    	Bukkit.getLogger().info("[MCKChat] Wykryto zmiane w pliku plugin.yml ,wylaczanie pluginu");
	    	Bukkit.getServer().getPluginManager().disablePlugin(this);
	    }
		saveDefaultConfig();
		time = getConfig().getInt("automsg.time")*1200;
		size = getConfig().getConfigurationSection("automsg.msgs").getKeys(false).size()+1;
		type = getConfig().getString("automsg.type");
		getCommand("chat").setExecutor(new ChatCmd());
		new ChatCmd();
	    Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
	    ChatCmd.chat = true;
	    autoMsg();
	}
	public static MCKChat getInst(){
		return instance;
	}
	private void autoMsg(){
		amt = 1;
		Bukkit.getScheduler().runTaskTimer(this, new Runnable(){
			public void run(){
			    for (String s : getConfig().getStringList("automsg.msgs."+amt)) {
			    	if(type.equalsIgnoreCase("player")){
			    		for(Player p : Bukkit.getOnlinePlayers()){
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
						}
					} else if(type.equalsIgnoreCase("console")){
				        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', s));
					}
			    }
				amt++;
				if(amt == size){
					amt = 1;
				}
			}
		}, 0, time);
	}	
}
