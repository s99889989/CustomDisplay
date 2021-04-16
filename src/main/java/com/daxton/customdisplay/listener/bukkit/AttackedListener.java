package com.daxton.customdisplay.listener.bukkit;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Convert;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.daxton.customdisplay.manager.PlayerManager;

import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.PacketPlayOutSetCooldown;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Material;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

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

            if(player.isBlocking()){ // && player.isBlocking() event.getFinalDamage() == 0 && material == Material.SHIELD

                onSheild(player, uuidString);

            }

            double damagedNumber = event.getFinalDamage();
            if (event.isCancelled()) {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_damaged_number>","Miss");
                new PlayerTrigger(player).onTwo(player, target, "~ondamagedmiss");
            }else {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_damaged_number>",String.valueOf(damagedNumber));

                new PlayerTrigger(player).onTwo(player, target, "~ondamaged");
            }



        }


    }

    public void setSheild(Player player){
        PacketContainer packet = new PacketContainer(PacketType.Play.Client.SET_COMMAND_BLOCK);
        packet.getIntegers().write(0, player.getEntityId());

        WrappedDataWatcher metadata = new WrappedDataWatcher();

        metadata.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(7, WrappedDataWatcher.Registry.get(Byte.class)), (byte)0x00);

        packet.getWatchableCollectionModifier().write(0, metadata.getWatchableObjects());

        try {
            ActionManager.protocolManager.sendServerPacket( player, packet );
        } catch ( InvocationTargetException exception ) {
            exception.printStackTrace();
        }

    }



    /**當拿盾牌格檔時**/
    public void onSheild(Player player, String uuidString){
        if(PlayerManager.shield_Delay_Boolean_Map.get(uuidString) == null){
            PlayerManager.shield_Delay_Boolean_Map.put(uuidString, true);
        }
        if(PlayerManager.shield_Delay_Boolean_Map.get(uuidString) != null){
            boolean sheildBoolean = PlayerManager.shield_Delay_Boolean_Map.get(uuidString);
            if(sheildBoolean){
                player.setCooldown(Material.SHIELD,100);
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
                PlayerManager.shield_Delay_Run_Map.get(uuidString).runTaskLater(cd,100);
            }
        }
    }


}
