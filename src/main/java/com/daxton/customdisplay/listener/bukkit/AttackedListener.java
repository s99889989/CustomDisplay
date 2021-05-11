package com.daxton.customdisplay.listener.bukkit;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Convert;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.daxton.customdisplay.manager.player.PlayerManager;

import org.bukkit.EntityEffect;
import org.bukkit.Material;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;


import java.lang.reflect.InvocationTargetException;

import static com.daxton.customdisplay.api.entity.Convert.convertLivingEntity;


public class AttackedListener implements Listener {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();



    /**玩家被攻擊**/
    @EventHandler
    public void onAttacked(EntityDamageByEntityEvent event){



        Player player = Convert.convertPlayer(event.getEntity());
        if(player != null){

            LivingEntity target = convertLivingEntity(event.getDamager());
            String uuidString = player.getUniqueId().toString();

//            Material offMaterial = player.getInventory().getItemInOffHand().getType();
//            if(player.isBlocking() && offMaterial == Material.SHIELD){ // && player.isBlocking() event.getFinalDamage() == 0 && material == Material.SHIELD
//
//                onSheild(player, uuidString);
//
//            }

            double damagedNumber = event.getFinalDamage();
            if (event.isCancelled()) {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_damaged_number>","Miss");
                PlayerTrigger.onPlayer(player, target, "~ondamagedmiss");
            }else {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_damaged_number>",String.valueOf(damagedNumber));

                PlayerTrigger.onPlayer(player, target, "~ondamaged");
            }



        }


    }


    //當拿盾牌格檔時
    public void onSheild(Player player, String uuidString){
        if(PlayerManager.shield_Delay_Boolean_Map.get(uuidString) == null){
            PlayerManager.shield_Delay_Boolean_Map.put(uuidString, true);
        }
        if(PlayerManager.shield_Delay_Boolean_Map.get(uuidString) != null){
            boolean sheildBoolean = PlayerManager.shield_Delay_Boolean_Map.get(uuidString);
            ItemStack offitemStack = player.getInventory().getItemInOffHand();
            int cool = 0;
            if(offitemStack != null){
                String ss = offitemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(cd, "CoolDown"), PersistentDataType.STRING);

                if(offitemStack.getType() == Material.SHIELD && ss != null){
                    try {
                        cool = Integer.parseInt(ss);
                    }catch (NumberFormatException exception){

                    }
                }
            }


            if(sheildBoolean && cool > 0){
                //player.sendMessage("時間: "+cool);
                player.setCooldown(Material.SHIELD,cool);
                player.playEffect(EntityEffect.SHIELD_BREAK);
                ItemStack itemStackOFF = player.getInventory().getItemInOffHand();
                ItemStack itemStack = new ItemStack(Material.AIR);
                player.getInventory().setItemInOffHand(itemStack);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.getInventory().setItemInOffHand(itemStackOFF);
                    }
                }.runTaskLater(cd,1);
                PlayerManager.shield_Delay_Boolean_Map.put(uuidString, false);
                PlayerManager.shield_Delay_Run_Map.put(uuidString, new BukkitRunnable() {
                    @Override
                    public void run() {
                        PlayerManager.shield_Delay_Boolean_Map.put(uuidString, true);
                    }
                });
                PlayerManager.shield_Delay_Run_Map.get(uuidString).runTaskLater(cd,cool);
            }
        }
    }


}
