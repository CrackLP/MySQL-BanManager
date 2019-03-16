package de.crack.lp.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.crack.lp.util.BanManager;
import de.crack.lp.util.FileManager;

public class PlayerLogin implements Listener {
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		
		if(BanManager.isBanned(p.getUniqueId().toString())) {
			long current = System.currentTimeMillis();
			long end = BanManager.getEnd(p.getUniqueId().toString());
			
			if(current < end | end == -1) {
				e.disallow(Result.KICK_BANNED, ChatColor.translateAlternateColorCodes('&',
						FileManager.getConfigFileConfiguration().getString("Ban-Message")
						.replace("%reason%", BanManager.getReason(p.getUniqueId().toString())).replace("%time%", BanManager.getRemainingTime(p.getUniqueId().toString()))));
			}
		}
		
	}

}
