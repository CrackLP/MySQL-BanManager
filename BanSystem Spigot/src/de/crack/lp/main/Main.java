package de.crack.lp.main;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.crack.lp.commands.BanCommands;
import de.crack.lp.commands.KickCommand;
import de.crack.lp.listeners.JoinListener;
import de.crack.lp.listeners.PlayerLogin;
import de.crack.lp.mysql.MySQL;
import de.crack.lp.updater.Updater;
import de.crack.lp.util.FileManager;

public class Main extends JavaPlugin {

	public void onEnable() {
		instance = this;
		pluginFile = getFile();
		Updater.checkForUpdates();
		registerEvents();
		registerCommands();
		FileManager.setStandardConfig();
		FileManager.readConfig();
		FileManager.setStandardMySQL();
		FileManager.readMySQL();
		MySQL.connect();
		MySQL.createTable();
		
		Bukkit.getConsoleSender().sendMessage(prefix + "§4plugin enabled!");
	}

	public void onDisable() {
		MySQL.close();
	}

	private void registerEvents() {
		this.getServer().getPluginManager().registerEvents(new JoinListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerLogin(), this);
	}

	private void registerCommands() {
		BanCommands BanCMD = new BanCommands(this);
		KickCommand KickCMD = new KickCommand(this);
		getCommand("ban").setExecutor(BanCMD);
		getCommand("tempban").setExecutor(BanCMD);
		getCommand("check").setExecutor(BanCMD);
		getCommand("unban").setExecutor(BanCMD);
		getCommand("kick").setExecutor(KickCMD);
	}

	public String prefix;
	public static Main instance;
	public static File pluginFile;

	public static Main getInstance() {
		return instance;
	}

}
