package com.daxton.customdisplay.task.action.profession;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class AttributePoint3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public AttributePoint3(){

    }

    public static void setAttributePoint(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String type = actionMapHandle.getString(new String[]{"type"},"minecraft");

        int amount = actionMapHandle.getInt(new String[]{"amount","a"},1);

        List<LivingEntity> targetList = actionMapHandle.getLivingEntityListSelf();

        targetList.forEach(livingEntity -> {
            if(livingEntity instanceof Player){
                Player player = (Player) livingEntity;
                addPoint(player, type, amount);
            }
        });

    }


    public static void addPoint(Player player, String attrName, int amount){

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
