package com.daxton.customdisplay.api.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryAll {

    public InventoryAll(){

    }

    public void all(Player player){
        ItemStack[] contents = player.getInventory().getContents();
        contents[24] = null;
        for (ItemStack item : contents) {

        }
    }

}
