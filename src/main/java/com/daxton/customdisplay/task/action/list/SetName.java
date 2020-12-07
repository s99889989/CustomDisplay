package com.daxton.customdisplay.task.action.list;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.NumberUtil;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.ConfigMapManager;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class SetName {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String message = "";

    private String taskID = "";
    private Player player;
    private LivingEntity target;
    private String targetName = "";
    private String healthNumber = "";
    private String health_conversion = "health-monster";
    private String healthConversion = "";
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
        List<String> stringList = new StringFind().getStringMessageList(firstString);
        for(String allString : stringList){
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

            if(allString.toLowerCase().contains("healthconver=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    health_conversion = strings[1];
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


        //Bukkit.getScheduler().runTask(CustomDisplay.getCustomDisplay(), () -> {

            String maxHealth = String.valueOf(target.getAttribute(GENERIC_MAX_HEALTH).getValue());
            String nowHealth = new NumberUtil(target.getHealth(),"0.#").getDecimalString();
            healthNumber = nowHealth +"/"+ maxHealth;
            healthConversion = targetHealth();
            message = new StringConversion().getString("Character",message,this.player).replace("{target_name}", targetName).replace("{cd_health_conversion}", healthConversion).replace("{cd_target_health}", healthNumber);
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
        //});
    }

    public void colse(){
        if(ActionManager.getJudgment_SetName_Map().get(taskID) != null){
            ActionManager.getJudgment_SetName_Map().remove(taskID);
        }
    }


    /**設定怪物血量顯示**/
    public String targetHealth(){

        double maxhealth = target.getAttribute(GENERIC_MAX_HEALTH).getValue();
        double nowhealth = target.getHealth();
        int counthealth = (int) nowhealth*10/(int) maxhealth;
        String mhealth = new NumberUtil(counthealth, ConfigMapManager.getFileConfigurationMap().get("Character_System_Health.yml").getStringList(health_conversion+".conversion")).getTenString();
        return mhealth;
    }

}
