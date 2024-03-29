package com.daxton.customdisplay.api.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;



import com.daxton.customdisplay.api.item.CustomItem2;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.nms.NMSVersion;


import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.Material;


import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import org.bukkit.util.Vector;


import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class GuiseEntity {

    private int entityID;

    private String entityTypeName;

    private List<Player> playerList;

    private Location location;

    private boolean visible = true;

    private boolean dead = false;

    public GuiseEntity(){

    }

    public GuiseEntity(int entityID, String entityTypeName, Location location){
        this.location = location;
        this.entityTypeName = entityTypeName;
        this.entityID = entityID;
        Collection onlinePlayers = Bukkit.getOnlinePlayers();
        this.playerList = new ArrayList<>(onlinePlayers);

    }


    //在座標生出生物
    public GuiseEntity createPacketEntity(String entityType, Location inputLocation){
        if(inputLocation == null){
            return null;
        }

        PacketContainer packet = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);
        packet.getModifier().writeDefaults();
        int entityID = (int)(Math.random() * Integer.MAX_VALUE);
        packet.getIntegers().write(0, entityID);

        try {
            EntityType entityType1 = Enum.valueOf(EntityType.class ,entityType.toUpperCase());
            packet.getEntityTypeModifier().write(0, entityType1);

            if(!entityType.equalsIgnoreCase("ARMOR_STAND")){
                packet.getIntegers().write(4, (int) (inputLocation.getPitch() * 256.0F / 360.0F));
                packet.getIntegers().write(5, (int) (inputLocation.getYaw() * 256.0F / 360.0F));
            }
        }catch (NullPointerException exception){
            packet.getEntityTypeModifier().write(0, EntityType.ARMOR_STAND);

        }

        packet.getDoubles().write(0, inputLocation.getX());
        packet.getDoubles().write(1, inputLocation.getY());
        packet.getDoubles().write(2, inputLocation.getZ());

//        Vector vector = moveEntity(player1);
//
//        packet.getIntegers().write(1,(int)(MathHelper.a(vector.getX(), -3.9D, 3.9D) * 8000.0D));
//        packet.getIntegers().write(2,(int)(MathHelper.a(vector.getY(), -3.9D, 3.9D) * 8000.0D));
//        packet.getIntegers().write(3,(int)(MathHelper.a(vector.getZ(), -3.9D, 3.9D) * 8000.0D));

//        packet.getIntegers().write(1,(int)(MathHelper.a(player1.getLocation().getDirection().getX(), -3.9D, 3.9D) * 8000.0D));
//        packet.getIntegers().write(2,(int)(MathHelper.a(player1.getLocation().getDirection().getY(), -3.9D, 3.9D) * 8000.0D));
//        packet.getIntegers().write(3,(int)(MathHelper.a(player1.getLocation().getDirection().getZ(), -3.9D, 3.9D) * 8000.0D));

        //packet.getIntegers().write(4, MathHelper.d(location.getPitch() * 256.0F / 360.0F));
        //packet.getIntegers().write(5, MathHelper.d(location.getYaw() * 256.0F / 360.0F));

        GuiseEntity packetEntity = new GuiseEntity(entityID, entityType, inputLocation);

        Collection onlinePlayers = Bukkit.getOnlinePlayers();
        List<Player> playerList = new ArrayList<>(onlinePlayers);
        playerList.forEach(player -> sendPack(player, packet));

        return packetEntity;
    }

    //傳送目標生物
    public void teleport(Location location){
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_TELEPORT);
        packet.getIntegers().write(0, entityID);

        packet.getDoubles().write(0, location.getX());
        packet.getDoubles().write(1, location.getY());
        packet.getDoubles().write(2, location.getZ());
        this.location = location;
//        packet.getIntegers().write(1, MathHelper.d(location.getPitch() * 256.0F / 360.0F));
//        packet.getIntegers().write(2, MathHelper.d(location.getYaw() * 256.0F / 360.0F));

//        packet.getBytes().write(0, (byte) (player1.getLocation().getPitch()*256F / 360F));
//        packet.getBytes().write(1, (byte) (player1.getLocation().getYaw()*256F / 360F));
        if(!this.entityTypeName.equalsIgnoreCase("ARMOR_STAND")){
            packet.getBytes().write(0, (byte)((int)(location.getYaw() * 256.0F / 360.0F)));
            packet.getBytes().write(1, (byte)((int)(location.getPitch() * 256.0F / 360.0F)));
        }

        playerList.forEach(player -> sendPack(player, packet));

    }

    //調整盔甲架角度
    public void setArmorStandAngle(String type, float x, float y, float z){
        PacketContainer packet = null;
        String nmsVersion = NMSVersion.getNMSVersion();
        switch (nmsVersion){
            case "v1_13_R1":
                packet = com.daxton.customdisplay.nms.v1_13_R1.packet.ArmorStand.setArmorStandAngle(this.entityID, type, x, y, z);
                break;
            case "v1_13_R2":
                packet = com.daxton.customdisplay.nms.v1_13_R2.packet.ArmorStand.setArmorStandAngle(this.entityID, type, x, y, z);
                break;
            case "v1_14_R1":
                packet = com.daxton.customdisplay.nms.v1_14_R1.packet.ArmorStand.setArmorStandAngle(this.entityID, type, x, y, z);
                break;
            case "v1_15_R1":
                packet = com.daxton.customdisplay.nms.v1_15_R1.packet.ArmorStand.setArmorStandAngle(this.entityID, type, x, y, z);
                break;
            case "v1_16_R1":
                packet = com.daxton.customdisplay.nms.v1_16_R1.packet.ArmorStand.setArmorStandAngle(this.entityID, type, x, y, z);
                break;
            case "v1_16_R2":
                packet = com.daxton.customdisplay.nms.v1_16_R2.packet.ArmorStand.setArmorStandAngle(this.entityID, type, x, y, z);
                break;
            case "v1_16_R3":
                packet = com.daxton.customdisplay.nms.v1_16_R3.packet.ArmorStand.setArmorStandAngle(this.entityID, type, x, y, z);
                break;
            case "v1_17_R1":
                packet = com.daxton.customdisplay.nms.v1_17_R1.packet.ArmorStand.setArmorStandAngle(this.entityID, type, x, y, z);
                break;
        }

        if(packet != null){
            for(Player player : playerList){
                sendPack(player, packet);
            }
        }
    }

    //裝備目標生物
    public void appendItem(String itemID, String itemSlot){
        String minecraftVersion = NMSVersion.getMinecraftVersion();
        if(minecraftVersion.equals("1.16.5") || minecraftVersion.equals("1.17")){
            appendItem16(itemID, itemSlot);
        }else {
            appendItem15(itemID, itemSlot);
        }
    }
    //裝備目標生物16以上
    public void appendItem16(String itemID, String itemSlot){

        ItemStack itemStack = CustomItem2.valueOf(null, null, itemID, 1);
        ItemStack itemStack2 = new ItemStack(Material.STONE);
        EnumWrappers.ItemSlot itemSlot1 = Enum.valueOf(EnumWrappers.ItemSlot.class,itemSlot);
        if(itemStack != null){
            PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
            packet.getIntegers().write(0, entityID);


            List<Pair<EnumWrappers.ItemSlot, ItemStack>> pairList = new ArrayList<>();

            pairList.add(new Pair<>(itemSlot1, itemStack));
            packet.getSlotStackPairLists().write(0, pairList);
            playerList.forEach(player -> sendPack(player, packet));
        }

    }
    /**裝備目標生物15以下**/
    public void appendItem15(String itemID, String itemSlot){
        ItemStack itemStack = CustomItem2.valueOf(null, null, itemID, 1);
        EnumWrappers.ItemSlot slot = Enum.valueOf(EnumWrappers.ItemSlot.class,itemSlot);

        if(itemStack != null){
            PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
            packet.getIntegers().write(0, entityID);
            packet.getItemSlots().write(0, slot);
            packet.getItemModifier().write(0, itemStack);

            playerList.forEach(player -> sendPack(player, packet));
        }

    }
    //修改顯示名稱
    public void appendTextLine(String text){
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        packet.getModifier().writeDefaults();
        packet.getIntegers().write(0, entityID);

        WrappedDataWatcher metadata = new WrappedDataWatcher();
        Optional<?> opt = Optional.of(WrappedChatComponent.fromChatMessage(text)[0].getHandle());

        metadata.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt);
        metadata.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), true);

        packet.getWatchableCollectionModifier().write(0, metadata.getWatchableObjects());
        playerList.forEach(player -> sendPack(player, packet));
    }



    //改變目標生物向量
    public void velocity(Vector vector){
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_VELOCITY);
        packet.getIntegers().write(0, entityID);

        packet.getIntegers().write(1, (int) (vector.getX() * 8000.0));
        packet.getIntegers().write(2, (int) (vector.getY() * 8000.0));
        packet.getIntegers().write(3, (int) (vector.getZ() * 8000.0));
        playerList.forEach(player -> sendPack(player, packet));
    }

    //設置目標生物是否可見
    public void setVisible(boolean b){
        this.visible = b;
        PackEntity.entityInvisible(this.entityID);
//        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
//        packet.getModifier().writeDefaults();
//        packet.getIntegers().write(0, entityID);
//        WrappedDataWatcher dataWatcher = new WrappedDataWatcher(packet.getWatchableCollectionModifier().read(0));
//
//        WrappedDataWatcher.WrappedDataWatcherObject isInvisibleIndex = new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class));
//        if(!b){
//            dataWatcher.setObject(isInvisibleIndex, (byte) 0x20);
//        }else {
//            dataWatcher.setObject(isInvisibleIndex, (byte) 0x00);
//        }
//
//        packet.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());
//        playerList.forEach(player -> sendPack(player, packet));
    }

    //刪除目標生物
    public void delete(){
        if(!dead){
            dead = true;
            PackEntity.delete(this.entityID);
        }
    }

    /**發送封包**/
    public static void sendPack(Player player, PacketContainer packet){

        try {
            ActionManager.protocolManager.sendServerPacket( player, packet );
        } catch ( InvocationTargetException exception ) {
            exception.printStackTrace();
        }
    }





//    public void setLook(Player player1) {
//        if(player1 == null){
//            return;
//        }
//
//        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_LOOK);
//        packet.getModifier().writeDefaults();
//        packet.getIntegers().write(0, entityID);
//
//
//        packet.getIntegers().write(1, MathHelper.d(player1.getLocation().getPitch() * 256.0F / 360.0F));
//        packet.getIntegers().write(2, MathHelper.d(player1.getLocation().getYaw() * 256.0F / 360.0F));
//
//
//        playerList.forEach(player -> {
//            sendPack(player, packet);
//        });
//
//    }


//    public void sendYawChange(LivingEntity livingEntity) {
//
//
//        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
//        packet.getIntegers().writeSafely(0, entityID);
//        packet.getBytes().writeSafely(0, (byte) (livingEntity.getLocation().getYaw()*256F / 360F));
//
//        playerList.forEach(player -> {
//            sendPack(player, packet);
//        });
//
//        PacketContainer packet1 = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_LOOK);
//        packet1.getIntegers().writeSafely(0, entityID);
//        packet1.getBytes().writeSafely(0, (byte) (livingEntity.getLocation().getYaw()*256F / 360F));
//        packet1.getBytes().writeSafely(1, (byte) (livingEntity.getLocation().getPitch()*256F / 360F));
//
//        playerList.forEach(player -> {
//            sendPack(player, packet1);
//        });
//    }
//
//    public void setLook(LivingEntity livingEntity) {
//        if(livingEntity == null){
//            return;
//        }
//        float y = livingEntity.getLocation().getYaw();
//        float p = livingEntity.getLocation().getPitch();
//
//        double angle = 0;
//
//        double yaw  = ((livingEntity.getLocation().getYaw() + 90+angle)  * Math.PI) / 180;
//        double pitch = ((livingEntity.getLocation().getPitch() + 90) * Math.PI) / 180;
//        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
//        packet.getModifier().writeDefaults();
//        packet.getIntegers().write(0, entityID);
//        packet.getBytes().write(0, (byte) yaw);
//        //packet.getBytes().write(1, (byte) pitch);
//
//
//        playerList.forEach(player -> {
//            sendPack(player, packet);
//        });
//
//    }


    public boolean isDead() {
        return dead;
    }

    public Location getLocation() {
        return location;
    }

    public String getEntityTypeName() {
        return this.entityTypeName;
    }

    public int getEntityID() {
        return this.entityID;
    }

    public boolean isVisible() {
        return visible;
    }

    //反射
//    public void setArmorStandAngle2(String type, float x, float y, float z){
//        PacketContainer packet = null;
//
//        try {
//            Class clazz = ArmorStand.class;
//
//            Constructor cons = clazz.getConstructor(int.class, String.class, float.class, float.class, float.class);
//
//            Object obj = cons.newInstance(); //this.entityID, type, x, y, z
//
//            ArmorStand p = (ArmorStand) obj;
//
//            packet = (PacketContainer) obj;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
////        Object obj = null;
////        try {
////            Class clazz = Class.forName("com.daxton.customdisplay.nms." + NMSVersion.getNMSVersion() + ".packet.ArmorStand");
////            Method asNMSCopy = clazz.getMethod("setArmorStandAngle", ArmorStand.class);
////            obj = asNMSCopy.invoke(obj, new ArmorStand());
////            //packet = (PacketContainer) obj;
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//        if(packet != null){
//            for(Player player : playerList){
//                sendPack(player, packet);
//            }
//        }
//    }

}
