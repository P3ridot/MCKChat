package me.mckoxu.mckchat.listener;

import java.util.HashMap;

import me.mckoxu.mckchat.MCKChat;
import me.mckoxu.mckchat.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.mckoxu.mckchat.command.ChatCmd;
import org.bukkit.scheduler.BukkitRunnable;

public class ChatListener implements Listener{

	private HashMap<Player, Integer> cooldownTime = new HashMap<Player, Integer>();
	private HashMap<Player, BukkitRunnable> cooldownTask = new HashMap<Player, BukkitRunnable>();
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		if(!e.isCancelled()){
			if (!ChatCmd.chat) {
				if (!p.hasPermission("chat.bypass")) {
					e.setCancelled(true);
					for(String msg : MCKChat.getInst().getConfig().getStringList("error.chatblock")) {
						p.sendMessage(ChatUtil.color(msg));
					}
					return;
				}
			}
			if (!p.hasPermission("chat.noslowmode")) {
				if (!cooldownTime.containsKey(p)) {
					cooldownTime.put(p, MCKChat.getInst().getConfig().getInt("slowmode.time"));
					cooldownTask.put(p, new BukkitRunnable() {
						public void run() {
							cooldownTime.put(p, cooldownTime.get(p) - 1);
							if (cooldownTime.get(p) == 0) {
								cooldownTime.remove(p);
								cooldownTask.remove(p);
								cancel();
							}
						}
					});
					cooldownTask.get(p).runTaskTimer(MCKChat.getInst(), 20, 20);
				} else {
					for(String msg : MCKChat.getInst().getConfig().getStringList("slowmode.msg")){
						p.sendMessage(ChatUtil.color(msg.replace("{TIME}", String.valueOf(cooldownTime.get(p)))));
					}
					e.setCancelled(true);
				}
			}
		}
	}
}
