package de.crack.lp.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.crack.lp.main.Main;
import de.crack.lp.mysql.MySQL;
import net.md_5.bungee.api.ChatColor;

public class FileManager {

	public static File getConfigFile() {
		return new File("plugins/BanManager", "config.yml");
	}

	public static FileConfiguration getConfigFileConfiguration() {
		return YamlConfiguration.loadConfiguration(getConfigFile());
	}

	public static File getMySQLFile() {
		return new File("plugins/BanManager", "MySQL.yml");
	}

	public static FileConfiguration getMySQLFileConfiguration() {
		return YamlConfiguration.loadConfiguration(getMySQLFile());
	}

	public static void setStandardConfig() {
		FileConfiguration cfg = getConfigFileConfiguration();
		cfg.options().copyDefaults(true);
		cfg.addDefault("Prefix", "&8[&6BanManager&8]");
		cfg.addDefault("Kick-Message",
						"&7--------------------" +
						"\n&cDu wurdest von diesem Netzwerk geworfen!\n" +
						"\n" + 
						"&4Grund: &9&l%reason%\n" + "\n" + "&eDu wurdest von &6&l%staffName% &egekickt!\n" +  
						"&7--------------------");
		cfg.addDefault("Ban-Message",
						"&cDu wurdest von diesem Netzwerk gebannt!\n" +
						"\n" + "&3Grund: &e%reason%\n" +
						"\n" +
						"&3Du wirst am &e%time% &3entbannt\n" +
						"\n" +
						"&3Du kannst unter &c&nDeinServer.de&3 einen Entbannungsantrag stellen!");
		cfg.addDefault("Auto-Updater", true);
		try {
			cfg.save(getConfigFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readConfig() {
		FileConfiguration cfg = getConfigFileConfiguration();
		Main.getInstance().prefix = ChatColor.translateAlternateColorCodes('&', cfg.getString("Prefix")) + " §r";
	}

	public static void setStandardMySQL() {
		FileConfiguration cfg = getMySQLFileConfiguration();
		cfg.options().copyDefaults(true);
		cfg.addDefault("username", "localhost");
		cfg.addDefault("password", "password");
		cfg.addDefault("database", "database");
		cfg.addDefault("host", "localhost");
		cfg.addDefault("port", "3306");
		try {
			cfg.save(getMySQLFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readMySQL() {
		FileConfiguration cfg = getMySQLFileConfiguration();
		MySQL.username = cfg.getString("username");
		MySQL.password = cfg.getString("password");
		MySQL.database = cfg.getString("database");
		MySQL.host = cfg.getString("host");
		MySQL.port = cfg.getString("port");
	}
}
