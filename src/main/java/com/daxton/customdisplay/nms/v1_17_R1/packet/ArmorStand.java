package com.daxton.customdisplay.nms.v1_17_R1.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
//import com.daxton.customdisplay.nms.v1_17_R1.unit.Vector3;
//import net.minecraft.core.Vector3f;
//import org.bukkit.util.EulerAngle;

public class ArmorStand {

    public ArmorStand(){

    }

    //調整盔甲架角度
    public static PacketContainer setArmorStandAngle(int entityID, String type, float x, float y, float z){
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        packet.getModifier().writeDefaults();
        packet.getIntegers().write(0, entityID);

        WrappedDataWatcher dataWatcher = new WrappedDataWatcher(packet.getWatchableCollectionModifier().read(0));
//        WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Vector3f.class);

//        EulerAngle eulerAngle = new EulerAngle(Math.toRadians(x), Math.toRadians(y), Math.toRadians(z));
//
//        Vector3f vector3f = Vector3.getVector3f(eulerAngle);
//
//        switch (type.toLowerCase()){
//            case "head":
//                dataWatcher.setObject(15, serializer, vector3f);
//                break;
//            case "body":
//                dataWatcher.setObject(16, serializer, vector3f);
//                break;
//            case "leftarm":
//                dataWatcher.setObject(17, serializer, vector3f);
//                break;
//            case "rightarm":
//                dataWatcher.setObject(18, serializer, vector3f);
//                break;
//            case "leftleg":
//                dataWatcher.setObject(19, serializer, vector3f);
//                break;
//            case "rightleg":
//                dataWatcher.setObject(20, serializer, vector3f);
//                break;
//        }

        packet.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

        return packet;
    }

}
