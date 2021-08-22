package com.daxton.customdisplay.nms.v1_17_R1.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.daxton.customdisplay.manager.ActionManager;
import net.minecraft.core.Vector3f;
import org.bukkit.util.EulerAngle;

public class ArmorStand {

    public ArmorStand(){

    }

    //調整盔甲架角度
    public static PacketContainer setArmorStandAngle(int entityID, String type, float x, float y, float z){
        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getModifier().writeDefaults();
        packet.getIntegers().write(0, entityID);

        WrappedDataWatcher metadata = new WrappedDataWatcher();
        EulerAngle eulerAngle = new EulerAngle(Math.toRadians(x), Math.toRadians(y), Math.toRadians(z));
        Vector3f vector3f = getVector3f(eulerAngle);

        switch (type.toLowerCase()){
            case "head":
                metadata.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(16, WrappedDataWatcher.Registry.get(Vector3f.class)), vector3f);
                break;
            case "body":
                metadata.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(17, WrappedDataWatcher.Registry.get(Vector3f.class)), vector3f);
                break;
            case "leftarm":
                metadata.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(18, WrappedDataWatcher.Registry.get(Vector3f.class)), vector3f);
                break;
            case "rightarm":
                metadata.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(19, WrappedDataWatcher.Registry.get(Vector3f.class)), vector3f);
                break;
            case "leftleg":
                metadata.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(20, WrappedDataWatcher.Registry.get(Vector3f.class)), vector3f);
                break;
            case "rightleg":
                metadata.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(21, WrappedDataWatcher.Registry.get(Vector3f.class)), vector3f);
                break;
        }

        packet.getWatchableCollectionModifier().write(0, metadata.getWatchableObjects());

        return packet;
    }

    public static Vector3f getVector3f(EulerAngle eulerAngle) {
        return new Vector3f((float)Math.toDegrees(eulerAngle.getX()), (float)Math.toDegrees(eulerAngle.getY()), (float)Math.toDegrees(eulerAngle.getZ()));
    }

}
