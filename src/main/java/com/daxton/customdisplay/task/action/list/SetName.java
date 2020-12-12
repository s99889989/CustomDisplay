package com.daxton.customdisplay.task.action.list;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.NumberUtil;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class SetName {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String message = "";
    private String messageTarge = "self";

    private String taskID = "";
    private Player player;
    private LivingEntity target;
    private String targetName = "";
    private boolean always = false;

    public SetName(){

    }

    public void setName(Player player, String firstString,String taskID){
        this.taskID = taskID;
        this.player = player;
        setOther(firstString);

    }

    public void setName(Player player, LivingEntity target, String firstString,String taskID){
        this.taskID = taskID;
        this.player = player;
        this.target = target;

        targetName = target.getName();
        message = targetName;
        setOther(firstString);

    }

    public void setOther(String firstString){
        messageTarge = "self";
        for(String string : new StringFind().getStringList(firstString)){
            if(string.toLowerCase().contains("messagetarge=") || string.toLowerCase().contains("mt=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    if(strings[1].toLowerCase().contains("target")){
                        messageTarge = "target";
                    }else {
                        messageTarge = "self";
                    }
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
        updateEntity();
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


        if(messageTarge.toLowerCase().contains("target")){
            message = new StringConversion("Character",message,target).getResultString();
        }else {
            message = new StringConversion("Character",message,player).getResultString();
        }

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

    public void colse(){
        if(ActionManager.getJudgment_SetName_Map().get(taskID) != null){
            ActionManager.getJudgment_SetName_Map().remove(taskID);
        }
    }




}
