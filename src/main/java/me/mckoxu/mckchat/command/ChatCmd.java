package me.mckoxu.mckchat.command;

import me.mckoxu.mckchat.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mckoxu.mckchat.MCKChat;

public class ChatCmd implements CommandExecutor{

	public static Boolean chat;
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("chat")){
			if(s.hasPermission("chat.admin")){
				if(args.length >= 1){
					if(args[0].equalsIgnoreCase("clear")){
						for(int i = 0; i < 105; i++){
							for(Player p : Bukkit.getOnlinePlayers()){
								p.sendMessage(" ");
							}
						}
						for(String msg : MCKChat.getInst().getConfig().getStringList("msgs.chatclear")){
							Bukkit.broadcastMessage(ChatUtil.color(msg).replace("{ADMIN}", s.getName()));
						}
						return true;
					} if(args[0].equalsIgnoreCase("on")){
						for(String msg : MCKChat.getInst().getConfig().getStringList("msgs.chaton")){
							Bukkit.broadcastMessage(ChatUtil.color(msg).replace("{ADMIN}", s.getName()));
						}
						chat = true;
						return true;
					} if(args[0].equalsIgnoreCase("off")){
						for(String msg : MCKChat.getInst().getConfig().getStringList("msgs.chatoff")){
							Bukkit.broadcastMessage(ChatUtil.color(msg).replace("{ADMIN}", s.getName()));
						}
						chat = false;
						return true;
					} else{
						for(String msg : MCKChat.getInst().getConfig().getStringList("error.correctusage")){
							s.sendMessage(ChatUtil.color(msg));
						}
						return false;
					}
				} else{
					for(String msg : MCKChat.getInst().getConfig().getStringList("error.correctusage")){
						s.sendMessage(ChatUtil.color(msg));
					}
					return true;
				}
			} else{
				for(String msg : MCKChat.getInst().getConfig().getStringList("error.noperm")){
					s.sendMessage(ChatUtil.color(msg));
				}
				return true;
			}
		}
		return true;
	}
}
