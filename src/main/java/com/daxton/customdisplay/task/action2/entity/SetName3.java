package com.daxton.customdisplay.task.action2.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.manager.ActionManager;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SetName3 {


    public SetName3(){

    }

    public static void setName(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        boolean always = actionMapHandle.getBoolean(new String[]{"always"}, false);

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListTarget();

        List<Entity> entityList = new ArrayList<>(Bukkit.getOnlinePlayers());
        entityList.forEach(entity -> {
            if(entity instanceof Player){
                Player player = (Player) entity;
                livingEntityList.forEach(livingEntity -> {
                    ActionMapHandle actionMapHandle2 = new ActionMapHandle(action_Map, self, livingEntity);
                    //獲得內容
                    String message = actionMapHandle2.getString(new String[]{"message","m"},"");

                    sendMetadataPacket(player, livingEntity, message, always);
                    if (Bukkit.getServer().getPluginManager().getPlugin("ModelEngine") != null){
                        ModeledEntity modeledEntity = ModelEngineAPI.api.getModelManager().getModeledEntity(livingEntity.getUniqueId());
                        if (modeledEntity != null) {
                            modeledEntity.setNametag(message);
                        }
                    }
                });
            }
        });


    }




//    public static void updateEntity(Player player, LivingEntity livingEntity) {
//
//        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
//        packet.getIntegers().write(0, livingEntity.getEntityId());
//        packet.getWatchableCollectionModifier().write(0, WrappedDataWatcher.getEntityWatcher(livingEntity).getWatchableObjects());
//        if (player.getWorld().equals(livingEntity.getWorld())) {
//            try {
//                ActionManager.protocolManager.sendServerPacket(player, packet);
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        }
//        //sendMetadataPacket();
//    }

    public static void sendMetadataPacket(Player player, LivingEntity livingEntity, String message, Boolean always) {

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
