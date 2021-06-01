package com.daxton.customdisplay.task.action.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Pair;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.ActionManager;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.*;


public class setEnitty {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();



    public setEnitty(){


    }

    public static void setEnitty(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String function = actionMapHandle.getString(new String[]{"function","fc"},"WHITE").toLowerCase();

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListTarget();
        List<Entity> entityList = Arrays.asList(self.getChunk().getEntities());

        entityList.forEach(entity -> {
            if(entity instanceof Player && entity != self){
                Player player = (Player) entity;

                List<PacketContainer> packets = new ArrayList<>();
                livingEntityList.forEach(livingEntity -> {
                    switch (function){
                        case "fire":
                            packets.add(setFire(livingEntity));
                            break;
                        case "crouching":
                            packets.add(setCrouching(livingEntity));
                            break;
                        case "riding":
                            packets.add(setRiding(livingEntity));
                            break;
                        case "sprinting":
                            packets.add(setSprinting(livingEntity));
                            break;
                        case "swimming":
                            packets.add(setSwimming(livingEntity));
                            break;
                        case "invisible":
                            packets.add(setInvisible(livingEntity));
                            packets.add(hideArmor16(livingEntity));
                            break;
                        case "flying":
                            packets.add(setFlying(livingEntity));
                            break;
                    }



                });
                packets.forEach((packet)->sendPack(player,packet));
                BukkitRunnable bukkitRunnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        packets.forEach((packet)->sendPack(player,packet));
                    }
                };
                bukkitRunnable.runTaskLater(cd,10);
            }
        });


    }



    //燃燒目標
    public static PacketContainer setFire(LivingEntity livingEntity) {

        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, livingEntity.getEntityId());

        WrappedDataWatcher watcher = new WrappedDataWatcher();
        WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);
        watcher.setEntity(livingEntity);
        watcher.setObject(0, serializer, (byte) (0x01));
        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

        return packet;

    }

    //讓目標蹲下
    public static PacketContainer setCrouching(LivingEntity livingEntity) {

        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, livingEntity.getEntityId());

        WrappedDataWatcher watcher = new WrappedDataWatcher();
        WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);
        watcher.setEntity(livingEntity);
        watcher.setObject(0, serializer, (byte) (0x02));
        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

        return packet;

    }

    //讓目標騎乘
    public static PacketContainer setRiding(LivingEntity livingEntity) {

        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, livingEntity.getEntityId());

        WrappedDataWatcher watcher = new WrappedDataWatcher();
        WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);
        watcher.setEntity(livingEntity);
        watcher.setObject(0, serializer, (byte) (0x04));
        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

        return packet;

    }

    //讓目標衝刺
    public static PacketContainer setSprinting(LivingEntity livingEntity) {

        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, livingEntity.getEntityId());

        WrappedDataWatcher watcher = new WrappedDataWatcher();
        WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);
        watcher.setEntity(livingEntity);
        watcher.setObject(0, serializer, (byte) (0x08));
        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

        return packet;

    }

    //讓目標游泳
    public static PacketContainer setSwimming(LivingEntity livingEntity) {

        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, livingEntity.getEntityId());

        WrappedDataWatcher watcher = new WrappedDataWatcher();
        WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);
        watcher.setEntity(livingEntity);
        watcher.setObject(0, serializer, (byte) (0x10));
        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

        return packet;

    }

    //讓目標隱形
    public static PacketContainer setInvisible(LivingEntity livingEntity) {

        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, livingEntity.getEntityId());

        WrappedDataWatcher watcher = new WrappedDataWatcher();
        WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);
        watcher.setEntity(livingEntity);
        watcher.setObject(0, serializer, (byte) (0x20));
        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

        return packet;

    }
    //讓裝備隱形16
    public static PacketContainer hideArmor16(LivingEntity livingEntity){

        ItemStack itemStack = new ItemStack(Material.AIR);

        EnumWrappers.ItemSlot itemSlot1 = Enum.valueOf(EnumWrappers.ItemSlot.class,"MAINHAND");

        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);

        packet.getIntegers().write(0, livingEntity.getEntityId());

        List<Pair<EnumWrappers.ItemSlot, ItemStack>> pairList = new ArrayList<>();

        pairList.add(new Pair<>(EnumWrappers.ItemSlot.MAINHAND, null));
        pairList.add(new Pair<>(EnumWrappers.ItemSlot.OFFHAND, null));
        pairList.add(new Pair<>(EnumWrappers.ItemSlot.CHEST, null));
        pairList.add(new Pair<>(EnumWrappers.ItemSlot.FEET, null));
        pairList.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, null));
        pairList.add(new Pair<>(EnumWrappers.ItemSlot.LEGS, null));
        packet.getSlotStackPairLists().write(0, pairList);


        return packet;
    }
    //讓裝備隱形16以下
    public static PacketContainer hideArmor(LivingEntity livingEntity) {
        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);

        packet.getIntegers().write(0, livingEntity.getEntityId());
        packet.getItemSlots().write(0, EnumWrappers.ItemSlot.MAINHAND);
        packet.getItemModifier().write(0, null);

        return packet;

//        Bukkit.getOnlinePlayers().forEach(pl -> {
//            try {
//                main.getProtocolManager().sendServerPacket(pl, packet);
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        });
    }

    //讓目標飛行
    public static PacketContainer setFlying(LivingEntity livingEntity) {

        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, livingEntity.getEntityId());

        WrappedDataWatcher watcher = new WrappedDataWatcher();
        WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);
        watcher.setEntity(livingEntity);
        watcher.setObject(0, serializer, (byte) (0x80));
        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

        return packet;

    }

    /**發送封包**/
    public static void sendPack(Player player,PacketContainer packet){

        try {
            ActionManager.protocolManager.sendServerPacket(player, packet);
        } catch ( InvocationTargetException exception ) {
            exception.printStackTrace();
        }
    }




}
