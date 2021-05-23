package com.daxton.customdisplay.task.action2.profession;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.api.player.data.set.PlayerPoint;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Point3 {

    public Point3(){

    }

    public void setPoint(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String type = actionMapHandle.getString(new String[]{"type"},"minecraft");

        int amount = actionMapHandle.getInt(new String[]{"amount","a"},1);

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListSelf();

        livingEntityList.forEach(livingEntity ->  {
            if(livingEntity instanceof Player){
                Player player = (Player) livingEntity;
                addPoint(player, type, amount);
            }
        });


    }



    public void addPoint(Player player, String attrName, int amount){

        String uuidString = player.getUniqueId().toString();

        if(PlayerManager.player_Data_Map.get(uuidString) != null){
            PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);

            int nowValue = playerData.getPoint(attrName);
            if(nowValue != -1){
                int newValue = nowValue + amount;
                if(newValue >= 0){
                    playerData.setPoint(attrName, newValue);
                }
            }

        }

    }



}
