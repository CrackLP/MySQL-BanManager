package de.crack.lp.updater;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import de.crack.lp.main.Main;
import de.crack.lp.util.FileManager;

public class Updater {

	public static void checkForUpdates() {
		System.out.println("[Updater] Checking for updates...");
		ReadableByteChannel channel = null;
		double newVersion = 0.0;

		File file = Main.pluginFile;

		try {
			URL versionURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=65544");
			BufferedReader reader = new BufferedReader(new InputStreamReader(versionURL.openStream()));
			newVersion = Double.valueOf(reader.readLine()).doubleValue();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (newVersion > Double.valueOf(Main.getPlugin(Main.class).getDescription().getVersion()).doubleValue()) {
			System.out.println("[Updater] Found a new version (" + newVersion + ")");

			if(FileManager.getConfigFileConfiguration().getBoolean("Auto-Updater")) {
				System.out.println("[Updater] Starting download...");
	
				try {
					HttpURLConnection downloadURL = (HttpURLConnection) new URL(
							String.format("http://api.spiget.org/v2/resources/%s/download", new Object[] { 65544 }))
									.openConnection();
					downloadURL.setRequestProperty("User-Agent", "SpigetResourceUpdater/Bukkit");
					channel = Channels.newChannel(downloadURL.getInputStream());
				} catch (IOException e) {
					throw new RuntimeException("Download failed", e);
				}
	
				try {
					FileOutputStream output = new FileOutputStream(file);
					output.getChannel().transferFrom(channel, 0L, Long.MAX_VALUE);
					output.flush();
					output.close();
				} catch (IOException e) {
					throw new RuntimeException("File could not be saved", e);
				}
	
				System.out.println("[Updater] Successfully updated plugin to version " + newVersion
						+ ". Please reload/restart your server now.");
			} else {
				System.out.println("[Updater] Download the update here: https://www.spigotmc.org/resources/spigot-banmanager-src-1-8-0-1-12-2.65544/");
			}
		} else {
			System.out.println("[Updater] No new version available");
		}
	}

}
