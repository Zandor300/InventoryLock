/**
 * Copyright 2015 Zandor Smith
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zandor300.inventorylock;

import com.zandor300.inventorylock.commands.InventoryLockCommand;
import com.zandor300.inventorylock.listener.PlayerListener;
import com.zandor300.zsutilities.ZSUtilities;
import com.zandor300.zsutilities.commandsystem.CommandManager;
import com.zandor300.zsutilities.utilities.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Zandor Smith
 * @since 1.0.0
 */
public class InventoryLock extends JavaPlugin {

	private static Chat chat = new Chat("InventoryLock", ChatColor.RED);
	private static InventoryLock plugin;

	private static ArrayList<Integer> lockedSlots = new ArrayList<Integer>();

	public static Chat getChat() {
		return chat;
	}

	public static InventoryLock getPlugin() {
		return plugin;
	}

	public static ArrayList<Integer> getLockedSlots() {
		return lockedSlots;
	}

	@Override
	public void onEnable() {
		chat.sendConsoleMessage("Setting things up...");

		saveDefaultConfig();
		this.getConfig().options().copyDefaults(true);
		plugin = this;
		PluginManager pm = Bukkit.getPluginManager();

		ZSUtilities.addDependency(this);

		chat.sendConsoleMessage("Sending metrics...");
		try {
			new Metrics(this).start();
			chat.sendConsoleMessage("Submitted stats to MCStats.org.");
		} catch (IOException e) {
			chat.sendConsoleMessage("Couldn't submit stats to MCStats.org...");
		}

		chat.sendConsoleMessage("Getting locked slots...");
		for(Object locked : this.getConfig().getList("locked"))
			lockedSlots.add(Integer.valueOf(locked.toString()));
		chat.sendConsoleMessage("Saved locked slots.");

		chat.sendConsoleMessage("Registering events...");
		pm.registerEvents(new PlayerListener(), this);
		chat.sendConsoleMessage("Registered events.");

		CommandManager cm = new CommandManager();
		cm.registerCommand(new InventoryLockCommand(), this);

		chat.sendConsoleMessage("Everything is setup!");
		chat.sendConsoleMessage("Enabled.");
	}
}
