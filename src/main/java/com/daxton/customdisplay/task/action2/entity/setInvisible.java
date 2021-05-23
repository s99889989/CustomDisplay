package com.daxton.customdisplay.task.action2.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Pair;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.nms.NMSVersion;
import net.minecraft.server.v1_16_R3.Chunk;
import net.minecraft.server.v1_16_R3.PacketPlayOutLightUpdate;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class setInvisible {




    public setInvisible(){


    }

    public static void setInvisible(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        boolean entityB = actionMapHandle.getBoolean(new String[]{"function","fc"},true);
        boolean mainHand = actionMapHandle.getBoolean(new String[]{"function","fc"},true);
        boolean offHand = actionMapHandle.getBoolean(new String[]{"function","fc"},true);
        boolean head = actionMapHandle.getBoolean(new String[]{"function","fc"},true);
        boolean chest = actionMapHandle.getBoolean(new String[]{"function","fc"},true);
        boolean legs = actionMapHandle.getBoolean(new String[]{"function","fc"},true);
        boolean feet = actionMapHandle.getBoolean(new String[]{"function","fc"},true);
        int duration = actionMapHandle.getInt(new String[]{"duration","dt"},40);

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListTarget();
        List<Entity> entityList = Arrays.asList(self.getChunk().getEntities());


        List<PacketContainer> packets = new ArrayList<>();
        livingEntityList.forEach(livingEntity -> {
            if(entityB){
                packets.add(setInvisible(livingEntity));
            }
            if(NMSVersion.getMinecraftVersion().equals("1.16.5")){
                packets.add(hideArmor16(livingEntity, mainHand, offHand, head, chest, legs, feet));
            }else {
                packets.add(hideArmor15MainHand(livingEntity));
                packets.add(hideArmor15OffHand(livingEntity));
                packets.add(hideArmor15Head(livingEntity));
                packets.add(hideArmor15Chest(livingEntity));
                packets.add(hideArmor15Legs(livingEntity));
                packets.add(hideArmor15Feet(livingEntity));
            }


        });
        List<PacketContainer> packets2 = new ArrayList<>();
        livingEntityList.forEach(livingEntity -> {
            if(entityB){
                packets2.add(setCrouching(livingEntity));
            }
            if(NMSVersion.getMinecraftVersion().equals("1.16.5")){
                packets2.add(showArmor16(livingEntity, mainHand, offHand, head, chest, legs, feet));
            }else {
                packets2.add(showArmor15MainHand(livingEntity));
                packets2.add(showArmor15OffHand(livingEntity));
                packets2.add(showArmor15Head(livingEntity));
                packets2.add(showArmor15Chest(livingEntity));
                packets2.add(showArmor15Legs(livingEntity));
                packets2.add(showArmor15Feet(livingEntity));
            }

        });

        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            int tickRun=0;

            @Override
            public void run() {
                tickRun+=10;
                if(tickRun > duration){
                    entityList.forEach(entity -> {
                        if(entity instanceof Player && entity != self){
                            Player player = (Player) entity;
                            packets2.forEach((packet)->sendPack(player,packet));

                        }
                    });
                    cancel();
                    return;
                }
                entityList.forEach(entity -> {
                    if(entity instanceof Player && entity != self){
                        Player player = (Player) entity;
                        packets.forEach((packet)->sendPack(player,packet));

                    }
                });
            }
        };
        bukkitRunnable.runTaskTimer(cd,0,10);




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
    public static PacketContainer hideArmor16(LivingEntity livingEntity, boolean mainHand, boolean offHand, boolean head, boolean chest, boolean legs, boolean feet){

        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);

        packet.getIntegers().write(0, livingEntity.getEntityId());

        List<Pair<EnumWrappers.ItemSlot, ItemStack>> pairList = new ArrayList<>();

        pairList.add(new Pair<>(EnumWrappers.ItemSlot.MAINHAND, null));
        pairList.add(new Pair<>(EnumWrappers.ItemSlot.OFFHAND, null));
        pairList.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, null));
        pairList.add(new Pair<>(EnumWrappers.ItemSlot.CHEST, null));
        pairList.add(new Pair<>(EnumWrappers.ItemSlot.LEGS, null));
        pairList.add(new Pair<>(EnumWrappers.ItemSlot.FEET, null));
        packet.getSlotStackPairLists().write(0, pairList);


        return packet;
    }

    //讓裝備隱形16以下-主手
    public static PacketContainer hideArmor15MainHand(LivingEntity livingEntity) {
        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);

        packet.getIntegers().write(0, livingEntity.getEntityId());
        packet.getItemSlots().write(0, EnumWrappers.ItemSlot.MAINHAND);
        packet.getItemModifier().write(0, null);

        return packet;

    }
    //讓裝備隱形16以下-副手
    public static PacketContainer hideArmor15OffHand(LivingEntity livingEntity) {
        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);

        packet.getIntegers().write(0, livingEntity.getEntityId());
        packet.getItemSlots().write(0, EnumWrappers.ItemSlot.OFFHAND);
        packet.getItemModifier().write(0, null);

        return packet;

    }
    //讓裝備隱形16以下-頭盔
    public static PacketContainer hideArmor15Head(LivingEntity livingEntity) {
        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);

        packet.getIntegers().write(0, livingEntity.getEntityId());
        packet.getItemSlots().write(0, EnumWrappers.ItemSlot.HEAD);
        packet.getItemModifier().write(0, null);

        return packet;

    }
    //讓裝備隱形16以下-護甲
    public static PacketContainer hideArmor15Chest(LivingEntity livingEntity) {
        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);

        packet.getIntegers().write(0, livingEntity.getEntityId());
        packet.getItemSlots().write(0, EnumWrappers.ItemSlot.CHEST);
        packet.getItemModifier().write(0, null);

        return packet;

    }
    //讓裝備隱形16以下-護腿
    public static PacketContainer hideArmor15Legs(LivingEntity livingEntity) {
        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);

        packet.getIntegers().write(0, livingEntity.getEntityId());
        packet.getItemSlots().write(0, EnumWrappers.ItemSlot.LEGS);
        packet.getItemModifier().write(0, null);

        return packet;

    }
    //讓裝備隱形16以下-靴子
    public static PacketContainer hideArmor15Feet(LivingEntity livingEntity) {
        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);

        packet.getIntegers().write(0, livingEntity.getEntityId());
        packet.getItemSlots().write(0, EnumWrappers.ItemSlot.FEET);
        packet.getItemModifier().write(0, null);

        return packet;

    }


    //讓裝備顯示16
    public static PacketContainer showArmor16(LivingEntity livingEntity, boolean mainHand, boolean offHand, boolean head, boolean chest, boolean legs, boolean feet){

        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);

        packet.getIntegers().write(0, livingEntity.getEntityId());

        List<Pair<EnumWrappers.ItemSlot, ItemStack>> pairList = new ArrayList<>();

        if(livingEntity.getEquipment() != null){
            ItemStack itemMainHand = livingEntity.getEquipment().getItemInMainHand();
            ItemStack itemOffHand = livingEntity.getEquipment().getItemInOffHand();
            ItemStack itemHead = livingEntity.getEquipment().getHelmet();
            ItemStack itemChest = livingEntity.getEquipment().getChestplate();
            ItemStack itemLegs = livingEntity.getEquipment().getLeggings();
            ItemStack itemFeet = livingEntity.getEquipment().getBoots();
            pairList.add(new Pair<>(EnumWrappers.ItemSlot.MAINHAND, itemMainHand));
            pairList.add(new Pair<>(EnumWrappers.ItemSlot.OFFHAND, itemOffHand));
            pairList.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, itemHead));
            pairList.add(new Pair<>(EnumWrappers.ItemSlot.CHEST, itemChest));
            pairList.add(new Pair<>(EnumWrappers.ItemSlot.LEGS, itemLegs));
            pairList.add(new Pair<>(EnumWrappers.ItemSlot.FEET, itemFeet));
        }

        packet.getSlotStackPairLists().write(0, pairList);

        return packet;
    }

    //讓裝備隱形16以下-主手
    public static PacketContainer showArmor15MainHand(LivingEntity livingEntity) {
        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        ItemStack itemMainHand = livingEntity.getEquipment().getItemInMainHand();

        packet.getIntegers().write(0, livingEntity.getEntityId());
        packet.getItemSlots().write(0, EnumWrappers.ItemSlot.MAINHAND);
        packet.getItemModifier().write(0, itemMainHand);

        return packet;

    }
    //讓裝備隱形16以下-副手
    public static PacketContainer showArmor15OffHand(LivingEntity livingEntity) {
        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        ItemStack itemOffHand = livingEntity.getEquipment().getItemInOffHand();

        packet.getIntegers().write(0, livingEntity.getEntityId());
        packet.getItemSlots().write(0, EnumWrappers.ItemSlot.OFFHAND);
        packet.getItemModifier().write(0, itemOffHand);

        return packet;

    }
    //讓裝備隱形16以下-頭盔
    public static PacketContainer showArmor15Head(LivingEntity livingEntity) {
        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        ItemStack itemHead = livingEntity.getEquipment().getHelmet();

        packet.getIntegers().write(0, livingEntity.getEntityId());
        packet.getItemSlots().write(0, EnumWrappers.ItemSlot.HEAD);
        packet.getItemModifier().write(0, itemHead);

        return packet;

    }
    //讓裝備隱形16以下-護甲
    public static PacketContainer showArmor15Chest(LivingEntity livingEntity) {
        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        ItemStack itemChest = livingEntity.getEquipment().getChestplate();

        packet.getIntegers().write(0, livingEntity.getEntityId());
        packet.getItemSlots().write(0, EnumWrappers.ItemSlot.CHEST);
        packet.getItemModifier().write(0, itemChest);

        return packet;

    }
    //讓裝備隱形16以下-護腿
    public static PacketContainer showArmor15Legs(LivingEntity livingEntity) {
        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        ItemStack itemLegs = livingEntity.getEquipment().getLeggings();

        packet.getIntegers().write(0, livingEntity.getEntityId());
        packet.getItemSlots().write(0, EnumWrappers.ItemSlot.LEGS);
        packet.getItemModifier().write(0, itemLegs);

        return packet;

    }
    //讓裝備隱形16以下-靴子
    public static PacketContainer showArmor15Feet(LivingEntity livingEntity) {
        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        ItemStack itemFeet = livingEntity.getEquipment().getBoots();
        packet.getIntegers().write(0, livingEntity.getEntityId());
        packet.getItemSlots().write(0, EnumWrappers.ItemSlot.FEET);
        packet.getItemModifier().write(0, itemFeet);

        return packet;

    }


    //發送封包
    public static void sendPack(Player player,PacketContainer packet){

        try {
            ActionManager.protocolManager.sendServerPacket(player, packet);
        } catch ( InvocationTargetException exception ) {
            exception.printStackTrace();
        }
    }




}
