package com.daxton.customdisplay.task.action.list;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Pair;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ItemEntity {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ItemEntity(){

    }

    public void setItemEntity(LivingEntity self, LivingEntity target, String firstString, String taskID){
        if(self instanceof Player){
            Player player = (Player) self;
            int entityID = (int)(Math.random() * Integer.MAX_VALUE);
            setEntity(entityID,player,target);
            setSlotStackPair(entityID,player);
            BukkitRunnable bukkitRunnable = new BukkitRunnable() {
                @Override
                public void run() {
                    killEntity(entityID,player);
                }
            };
            bukkitRunnable.runTaskLater(cd,40);

        }

    }


    public void setEntity(int entityID,Player player,LivingEntity livingEntity){
        Location loc = livingEntity.getLocation();
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);
        packet.getModifier().writeDefaults();

        packet.getIntegers().write(0, entityID);
        //packet.getUUIDs().write(0, UUID.randomUUID());
        packet.getEntityTypeModifier().write(0, EntityType.ARMOR_STAND);
        packet.getDoubles().write(0, loc.getX());
        packet.getDoubles().write(1, loc.getY());
        packet.getDoubles().write(2, loc.getZ());

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }




    public void setSlotStackPair(int entityID,Player player) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
        packet.getIntegers().write(0, entityID);

        List<Pair<EnumWrappers.ItemSlot, ItemStack>> pairList = new ArrayList<>();
        pairList.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, new ItemStack(Material.STONE)));
        packet.getSlotStackPairLists().write(0, pairList);

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void killEntity(int entityID,Player player){
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        //packet.getIntegers().write(0, 1);
        packet.getIntegerArrays().write(0, new int[]{ entityID });
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setName(int entityID,Player player){
        PacketContainer packet2 = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        packet2.getModifier().writeDefaults();
        packet2.getIntegers().write(0, entityID);

        WrappedDataWatcher metadata = new WrappedDataWatcher();
        Optional<?> opt = Optional.of(WrappedChatComponent.fromChatMessage("displayName")[0].getHandle());
        metadata.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt);
        metadata.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), true);

        packet2.getWatchableCollectionModifier().write(0, metadata.getWatchableObjects());
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet2);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void setEqm(int entityID,Player player){
        ItemStack itemStack = new ItemStack(Material.WOODEN_AXE);
        EnumWrappers.ItemSlot slot = EnumWrappers.ItemSlot.HEAD;



        PacketContainer packet3 = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
        packet3.getIntegers().write(0, entityID);
        packet3.getItemSlots().write(0, EnumWrappers.ItemSlot.HEAD);
        packet3.getItemModifier().write(0, itemStack);


        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet3);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
