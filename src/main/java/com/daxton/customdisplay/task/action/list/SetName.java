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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class SetName {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();



    public SetName(Player player, LivingEntity target){
        //player.sendMessage(target.getName());
        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, target.getEntityId());
        packet.getWatchableCollectionModifier().write(0, WrappedDataWatcher.getEntityWatcher(target).getWatchableObjects());
        double targetEntityId = target.getEntityId();

        newName(player,target);
        //sendName(player,target);
    }

    public void newName(Player player, LivingEntity target){
        ProtocolManager pm = ProtocolLibrary.getProtocolManager();

        WrappedDataWatcher watcher = new WrappedDataWatcher();
        watcher.setEntity(target);

        WrappedDataWatcher.Serializer booleanSerializer = WrappedDataWatcher.Registry.get(Boolean.class);
        WrappedDataWatcher.Serializer stringSerializer = WrappedDataWatcher.Registry.get(String.class);

        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, stringSerializer), "喵喵犬"); // Set custom name
        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, booleanSerializer), true); // Set custom name visible

        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, target.getEntityId());
        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
        try {
            pm.sendServerPacket(player, packet);
        } catch (InvocationTargetException exception) {
            exception.printStackTrace();
        }
    }


    public void sendName(Player player, LivingEntity target){

        String json = "Good名稱";
        Bukkit.getScheduler().runTask(cd, () -> {

            PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);

            packet.getIntegers().write(0, target.getEntityId()); //Set packet's entity id
            WrappedDataWatcher watcher = new WrappedDataWatcher(); //Create data watcher, the Entity Metadata packet requires this

            if (json != null) {
                if (true) {
                    WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(String.class);
                    WrappedDataWatcher.WrappedDataWatcherObject object = new WrappedDataWatcher.WrappedDataWatcherObject(2, serializer);
                    watcher.setObject(object, ComponentSerializer.parse(json)[0].toLegacyText());
                } else {
                    Optional<?> opt = Optional.of(WrappedChatComponent.fromJson(json).getHandle());
                    watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt);
                }
            } else {
                if(true) {
                    WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(String.class);
                    WrappedDataWatcher.WrappedDataWatcherObject object = new WrappedDataWatcher.WrappedDataWatcherObject(2, serializer);
                    watcher.setObject(object, "");
                } else {
                    Optional<?> opt = Optional.empty();
                    watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt);
                }
            }


           watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), true);

            packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

            try {
                ActionManager.protocolManager.sendServerPacket(player, packet, !true);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });


    }

}
