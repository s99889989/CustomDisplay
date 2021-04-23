package com.daxton.customdisplay.task.action2.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.manager.ActionManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

public class SetName3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String taskID = "";
    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private CustomLineConfig customLineConfig;


    public SetName3(){

    }

    public void setName(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        this.taskID = taskID;
        this.self = self;
        this.target = target;

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        /**獲得內容**/
        String message = actionMapHandle.getString(new String[]{"message","m"},"");

        boolean always = actionMapHandle.getBoolean(new String[]{"always"}, false);

        if(self instanceof Player){
            Player player = (Player) self;
            sendMetadataPacket(player, target, message, always);
        }

    }




    public void updateEntity(Player player) {

        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, target.getEntityId());
        packet.getWatchableCollectionModifier().write(0, WrappedDataWatcher.getEntityWatcher(target).getWatchableObjects());
        if (player.getWorld().equals(target.getWorld())) {
            try {
                ActionManager.protocolManager.sendServerPacket(player, packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        //sendMetadataPacket();
    }

    public void sendMetadataPacket(Player player, LivingEntity livingEntity, String message, Boolean always) {

        //message = new ConversionMain().valueOf(self,target,message);

        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, livingEntity.getEntityId());
        WrappedDataWatcher watcher = new WrappedDataWatcher();

        Optional<?> opt = Optional.of(WrappedChatComponent.fromChatMessage(message)[0].getHandle());
        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt);

        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), always);

        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

        try {
            ActionManager.protocolManager.sendServerPacket(player, packet,false);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


}
