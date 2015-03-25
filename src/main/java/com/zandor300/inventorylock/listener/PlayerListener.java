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
package com.zandor300.inventorylock.listener;

import com.zandor300.inventorylock.InventoryLock;
import net.minecraft.server.v1_8_R1.EnchantmentSlotType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * @author Zandor Smith
 * @since 1.0.0
 */
public class PlayerListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(((Player)event.getWhoClicked()).hasPermission("inventorylock.bypass"))
			return;
		if(InventoryLock.getLockedSlots().contains(event.getSlot()))
			event.setCancelled(true);
		else if(event.getSlotType().equals(InventoryType.SlotType.ARMOR) && InventoryLock.getLockedSlots().contains(36))
			event.setCancelled(true);
		else if(event.getSlotType().equals(InventoryType.SlotType.ARMOR) && InventoryLock.getLockedSlots().contains(37))
			event.setCancelled(true);
		else if(event.getSlotType().equals(InventoryType.SlotType.ARMOR) && InventoryLock.getLockedSlots().contains(38))
			event.setCancelled(true);
		else if(event.getSlotType().equals(InventoryType.SlotType.ARMOR) && InventoryLock.getLockedSlots().contains(39))
			event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		int slot = 0;
		for(int i = 0; i < 39; i++)
			if(event.getItemDrop().getItemStack().equals(event.getPlayer().getInventory().getItem(i))) {
				slot = i;
				break;
			}

		if(InventoryLock.getLockedSlots().contains(slot))
			event.setCancelled(true);
	}
}
