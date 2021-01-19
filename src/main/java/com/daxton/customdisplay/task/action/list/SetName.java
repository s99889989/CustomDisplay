package com.daxton.customdisplay.task.action.list;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class SetName {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String message = "";

    private String taskID = "";
    private Player player = null;
    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";

    private String aims = "self";
    private boolean always = false;

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

        for(String string : new StringFind().getStringList(firstString)){

            if(string.toLowerCase().contains("@=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    aims = strings[1];
                }
            }
        }

        for(String allString : new StringFind().getStringMessageList(firstString)){
            if(allString.toLowerCase().contains("message=") || allString.toLowerCase().contains("m=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    message = strings[1];
                }
            }

            if(allString.toLowerCase().contains("always=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    if(strings[1].toLowerCase().contains("true")){
                        always = true;
                    }else {
                        always = false;
                    }
                }

            }


        }

        if(target instanceof Player & aims.toLowerCase().contains("target")){
            player = (Player) target;
        }else {
            if(self instanceof Player){
                player = (Player) self;
            }
        }

        if(player != null){
            updateEntity();
        }
    }


    public void updateEntity() {

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
        sendMetadataPacket();
    }

    public void sendMetadataPacket() {

        message = new ConversionMain().valueOf(self,target,message);
        cd.getLogger().info(message);
        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, target.getEntityId());
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
