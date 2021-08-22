package com.daxton.customdisplay.listener.bukkit;

import com.daxton.customdisplay.api.item.PlayerEquipment2;
import com.daxton.customdisplay.manager.ListenerManager;
import com.daxton.customdisplay.manager.player.EditorGUIManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class EquipmentListener implements Listener {

    //登入時
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        //讀取物品屬性
        PlayerEquipment2.reloadEquipment(player,10);
    }

    //當按下切換1~9時
    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();

        int key = event.getNewSlot();
        int key2 = event.getPreviousSlot();


        //讀取屬性
        boolean fOn = ListenerManager.getCast_On_Stop().get(uuidString);
        if(!fOn){
            if(key != key2){
                PlayerEquipment2.reloadEquipment(player,key);
            }
        }

    }

    //關閉背包
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        String uuidString = player.getUniqueId().toString();

        //讀取屬性
        if(ListenerManager.getCast_On_Stop().get(uuidString) != null){
            boolean fOn = ListenerManager.getCast_On_Stop().get(uuidString);
            if(!fOn){
                PlayerEquipment2.reloadEquipment(player,10);
            }
        }


    }

}
