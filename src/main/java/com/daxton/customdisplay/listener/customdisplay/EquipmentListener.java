package com.daxton.customdisplay.listener.customdisplay;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerAttribute;
import com.daxton.customdisplay.api.player.PlayerConfig;
import com.daxton.customdisplay.api.player.PlayerEquipment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;


public class EquipmentListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();
    /**登入時**/
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(@NotNull final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        new PlayerEquipment().reloadEquipment(player,10);


    }

    /**切換手上物品時**/
    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        int key = event.getNewSlot();

        new PlayerEquipment().reloadEquipment(player,key);
        new PlayerAttribute(player);

        //new PlayerConfig(player).setAttrStats(player);
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
