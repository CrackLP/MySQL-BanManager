package de.crack.lp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.crack.lp.main.Main;
import de.crack.lp.util.FileManager;

public class KickCommand implements CommandExecutor {

	private Main plugin;

	public KickCommand(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args) {

		Player p = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("kick")) {
			if (p.hasPermission("banmanager.kick")) {
				if (args.length == 0) {
					p.sendMessage(plugin.prefix + "§7Nutze /kick <Spieler> <Grund>");
				} else if (args.length == 1) {
					p.sendMessage(plugin.prefix + "§7Nutze /kick <Spieler> <Grund>");
				} else if (args.length == 2) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null) {
						String Grund = " ";

						for (int i = 1; i < args.length; i++) {
							Grund += args[i] + " ";
						}

						Grund = Grund.trim();

						target.kickPlayer(ChatColor.translateAlternateColorCodes('&',
								FileManager.getConfigFileConfiguration().getString("Kick-Message")
										.replace("%reason%", Grund).replace("%staffName%", p.getDisplayName())));
						p.sendMessage(plugin.prefix + "§cDu hast den Spieler §9" + target.getDisplayName()
								+ "§cerfolgreich vom Server gekickt!");

						for (Player team : Bukkit.getOnlinePlayers()) {
							if (team.hasPermission("banmanager.global")) {
								team.sendMessage(plugin.prefix + "§7Der Spieler §c" + target.getDisplayName()
										+ "§7 wurde von§c " + p.getDisplayName() + " §7mit dem Grund §c" + Grund + Grund
										+ "§7vom Server geworfen!");
							}

						}
					}
				}
			}

		}
		return false;
	}

}
