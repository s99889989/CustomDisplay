package com.daxton.customdisplay.task.action2.profession;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.api.player.data.set.PlayerAttributesPoint;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class AttributePoint3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public AttributePoint3(){

    }

    public void setAttributePoint(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String type = actionMapHandle.getString(new String[]{"type"},"minecraft");

        int amount = actionMapHandle.getInt(new String[]{"amount","a"},1);

        if(type.toLowerCase().equals("minecraft")){

        }else {
            if(self instanceof Player){
                Player player = (Player) self;
                addPoint(player, type, amount);
            }

        }
    }


    public void addPoint(Player player, String attrName, int amount){

        String uuidString = player.getUniqueId().toString();
        PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);
        if(playerData != null){
            int nowValue = playerData.getAttrPoint(attrName);
            int newValue = nowValue + amount;
            if(newValue >= 0){
                playerData.setAttrPoint(attrName, newValue);
            }

        }


    }


}
