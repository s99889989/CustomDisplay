package com.daxton.customdisplay.listener.customdisplay;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerAttribute;
import com.daxton.customdisplay.api.player.PlayerConfig;
import com.daxton.customdisplay.api.player.PlayerEquipment;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class EquipmentListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();
    /**登入時**/
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(@NotNull final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        new PlayerEquipment().reloadEquipment(player,10);


    }

    /**切換手上物品時**/
    @EventHandler(priority = EventPriority.LOW )
    public void onItemHeld(PlayerItemHeldEvent event){




        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();
        int key = event.getNewSlot();
        int old = event.getPreviousSlot();
        new PlayerEquipment().reloadEquipment(player,key);
        new PlayerAttribute(player);

        List<String> action = PlayerDataMap.skill_Key_Map.get(uuidString+"."+key);
        if(action != null && action.size() > 0){
            new PlayerTrigger(player).onGuiClick(player,action);
        }

        if(PlayerDataMap.even_Cancel_Map.get(uuidString) != null){
            boolean cc = PlayerDataMap.even_Cancel_Map.get(uuidString);
            if(cc == true){
                event.setCancelled(true);
            }
        }

        //new PlayerConfig(player).setAttrStats(player);
    }

    /**按下F時**/
    @EventHandler(priority = EventPriority.LOW )
    public void onSwapHand(PlayerSwapHandItemsEvent event){
        event.setCancelled(true);
//        Player player = event.getPlayer();
//        String uuidString = player.getUniqueId().toString();
//        if(PlayerDataMap.even_Cancel_Map.get(uuidString) != null){
//            boolean cc = PlayerDataMap.even_Cancel_Map.get(uuidString);
//            if(cc == true){
//                event.setCancelled(true);
//            }
//        }
    }

    /**關閉背包**/
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        new PlayerEquipment().reloadEquipment(player,10);
        new PlayerAttribute(player);
        //new PlayerConfig(player).setAttrStats(player);
    }

//    /**移動物品時**/
//    @EventHandler(
//            priority = EventPriority.HIGH,
//            ignoreCancelled = true
//    )
//    public void registerClicks(InventoryClickEvent event) {
//        if(event.getWhoClicked() instanceof Player){
//
//            Player player = ((Player) event.getWhoClicked()).getPlayer();
//
//        }
//
//    }


//    @EventHandler
//    public void onAction(PlayerInteractEvent event){
//        Player player = event.getPlayer();
//        //cd.getLogger().info(player.getName()+" : "+event.getAction().toString());
//        //player.sendMessage();
//
//
//
////        int[] arrau = {8,4,6};
////        List<String> s = new ArrayList<>();
////        Arrays.stream(s.toArray()).forEach(System.out::println);
//
//    }


}
