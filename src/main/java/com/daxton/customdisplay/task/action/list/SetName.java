package com.daxton.customdisplay.task.action.list;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.other.SetValue;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public class SetName {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String taskID = "";
    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";


    public SetName(){

    }

    public void setName(LivingEntity self, LivingEntity target, String firstString,String taskID){
        this.taskID = taskID;
        this.self = self;
        this.target = target;
        this.firstString = firstString;

        setOther();

    }

    public void setOther(){

        /**獲得目標**/
        List<LivingEntity> targetList = new Aims().valueOf(self,target,firstString);
        if(!(targetList.isEmpty())){
            for (LivingEntity livingEntity : targetList){

                    /**獲得內容**/
                    String message = new SetValue(self,livingEntity,firstString,"[];","","m=","message=").getString();

                    boolean always = new SetValue(self,livingEntity,firstString,"[];","false","always=").getBoolean();
                    if(self instanceof Player){
                        Player player = (Player) self;
                        sendMetadataPacket(player, livingEntity, message, always);
                    }
            }
        }

//        if(target instanceof Player & aims.toLowerCase().contains("target")){
//            player = (Player) target;
//        }else {
//            if(self instanceof Player){
//                player = (Player) self;
//            }
//        }
//
//        if(player != null){
//            updateEntity();
//        }
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
