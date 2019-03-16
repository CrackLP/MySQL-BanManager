package de.crack.lp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.crack.lp.util.BanManager;

public class JoinListener implements Listener{
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(BanManager.isBanned(p.getUniqueId().toString())) {
			BanManager.unban(p.getUniqueId().toString());
		}
	}

}
