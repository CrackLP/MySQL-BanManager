package de.crack.lp.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.crack.lp.main.Main;
import de.crack.lp.util.BanManager;
import de.crack.lp.util.Units;

public class BanCommands implements CommandExecutor {

	private Main plugin;

	public BanCommands(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ban")) {
			if (args.length >= 2) {
				String playername = args[0];
				if (BanManager.isBanned(getUUID(playername))) {
					sender.sendMessage(plugin.prefix + "§cDieser Spieler ist bereits gebannt!");
					return true;
				}
				String reason = "";
				for (int i = 1; i < args.length; i++) {
					reason += args[i] + " ";
				}
				BanManager.ban(getUUID(playername), playername, reason, -1);
				sender.sendMessage(plugin.prefix + "§7Du hast §e" + playername + " §4PERMANENT §7gebannt!");
				return true;
			}
			sender.sendMessage(plugin.prefix + "§c/Ban <Spieler> <Grund>");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("tempban")) {
			if (args.length >= 4) {
				String playername = args[0];
				if (BanManager.isBanned(getUUID(playername))) {
					sender.sendMessage(plugin.prefix + "§cDieser Spieler ist bereits gebannt!");
					return true;
				}
				long value;
				try {
					value = Integer.valueOf(args[1]);
				} catch (NumberFormatException e) {
					sender.sendMessage(plugin.prefix + "§cDer <Zahlenwert> muss eine Zahl sein!");
					return true;
				}
				if (value >= 500) {
					sender.sendMessage(plugin.prefix + "§cDie Zahl muss unter 500 liegen!");
					return true;
				}
				String unitString = args[2];
				String reason = "";
				for (int i = 3; i < args.length; i++) {
					reason += args[i] + " ";
				}
				List<String> unitList = Units.getUnitsAsString();
				if (unitList.contains(unitString.toLowerCase())) {
					Units unit = Units.getUnit(unitString);
					long seconds = value * unit.getToSecond();
					BanManager.ban(getUUID(playername), playername, reason, seconds);
					sender.sendMessage(plugin.prefix + "§7Du hast §e" + playername + " §7f§r §c " + value
							+ unit.getName() + " §7gebannt!");
					return true;
				}
				sender.sendMessage(plugin.prefix + "§cDiese <Einheit> existiert nicht!");
				return true;
			}
			sender.sendMessage(plugin.prefix + "§cBitte benutze: §e/tempban <Spieler> <Zahlenwert> <Einheit> <Grund>");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("check")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("list")) {
					List<String> list = BanManager.getBannedPlayers();
					if (list.size() == 0) {
						sender.sendMessage(plugin.prefix + "§7---------- §6§lBan-Liste §7----------");
						sender.sendMessage(plugin.prefix + "§cEs sind aktuell keine Spieler gebannt!");
					}
					sender.sendMessage(plugin.prefix + "§7---------- §6§lBan-Liste §7----------");
					for (String allBanned : BanManager.getBannedPlayers()) {
						sender.sendMessage(plugin.prefix + "§e" + allBanned + " §7(Grund: §r"
								+ BanManager.getReason(getUUID(allBanned)) + " §7)");
					}
					return true;
				}
				String playername = args[0];
				sender.sendMessage(plugin.prefix + "§7---------- §6§lBan-Infos §7----------");
				sender.sendMessage(plugin.prefix + "§eName: §r" + playername);
				sender.sendMessage(plugin.prefix + "§eGebannt: §r"
						+ (BanManager.isBanned(getUUID(playername)) ? "§aJa" : "§cNein"));
				if (BanManager.isBanned(getUUID(playername))) {
					sender.sendMessage(plugin.prefix + "§eGrund: §r" + BanManager.getReason(getUUID(playername)));
					sender.sendMessage(plugin.prefix + "§eVerbleibende Zeit: §r"
							+ BanManager.getRemainingTime(getUUID(playername)));
				}
				sender.sendMessage(plugin.prefix + "§eBans: §r" + BanManager.getBanCount(getUUID(playername)));
				sender.sendMessage(plugin.prefix + "§7---------- §6§lBan-Infos §7----------");
				return true;
			}
			sender.sendMessage(plugin.prefix + "§cBitte benutze /check (list) <Spieler>");
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("unban")) {
			if (args.length == 1) {
				String playername = args[0];
				if (!BanManager.isBanned(getUUID(playername))) {
					sender.sendMessage(plugin.prefix + "§cDer Spieler §e" + playername + " §7ist nicht gebannt");
					return true;
				}
				BanManager.unban(getUUID(playername));
				sender.sendMessage(plugin.prefix + "§7Du hast §e" + playername + " §7entbannt!");
				return true;
			}
			sender.sendMessage(plugin.prefix + "§cBitte benutze /unban <Spieler>");
			return true;
		}

		return true;
	}

	@SuppressWarnings("deprecation")
	private String getUUID(String playername) {
		return Bukkit.getOfflinePlayer(playername).getUniqueId().toString();
	}
}
