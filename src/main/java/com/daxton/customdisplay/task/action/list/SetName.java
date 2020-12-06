package com.daxton.customdisplay.task.action.list;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ActionManager;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class SetName {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public SetName(Player player, LivingEntity target){


        updateEntity(player,target);

    }

    public static void updateEntity(Player player, LivingEntity entity) {


        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, entity.getEntityId());
        packet.getWatchableCollectionModifier().write(0, WrappedDataWatcher.getEntityWatcher(entity).getWatchableObjects());
        if (player.getWorld().equals(entity.getWorld())) {
            try {
                ActionManager.protocolManager.sendServerPacket(player, packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        sendMetadataPacket(player,entity);
    }

    public static void sendMetadataPacket(Player player, LivingEntity entity) {
        String json = "測試";
        Bukkit.getScheduler().runTask(CustomDisplay.getCustomDisplay(), () -> {

            PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
            packet.getIntegers().write(0, entity.getEntityId()); //Set packet's entity id
            WrappedDataWatcher watcher = new WrappedDataWatcher(); //Create data watcher, the Entity Metadata packet requires this

//            if (json != null) {
//                if (false) {
//                    WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(String.class);
//                    WrappedDataWatcher.WrappedDataWatcherObject object = new WrappedDataWatcher.WrappedDataWatcherObject(2, serializer);
//                    watcher.setObject(object, ComponentSerializer.parse(json)[0].toLegacyText());
//                } else {
                    Optional<?> opt = Optional.of(WrappedChatComponent.fromJson(json+entity.getHealth()).getHandle());
                    watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt);
//                }
//            }

            watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), true);

            packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

            try {
                 ActionManager.protocolManager.sendServerPacket(player, packet, false);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }


    public void sendName(Player player, LivingEntity target){



    }

}
