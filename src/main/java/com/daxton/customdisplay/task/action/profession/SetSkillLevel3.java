package com.daxton.customdisplay.task.action.profession;


import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class SetSkillLevel3 {


    public SetSkillLevel3(){

    }

    public static void setCoreSkill(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String skillName = actionMapHandle.getString(new String[]{"skillname"},"");

        int amount = actionMapHandle.getInt(new String[]{"amount","a"},1);

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListSelf();

        livingEntityList.forEach(livingEntity ->  {
            if(livingEntity instanceof Player){
                Player player = (Player) livingEntity;
                addSkill(player, skillName, amount);
            }
        });

    }

    public static void addSkill(Player player, String skillName, int amount){

        String uuidString = player.getUniqueId().toString();
        PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);
        if(playerData != null){
            int nowValue = playerData.getSkillLevel(skillName);
            if(nowValue != -1){
                int newValue = nowValue + amount;
                if(newValue >= 0){
                    playerData.setSkillLevel(skillName, newValue);
                    if(skillName.endsWith("_level")){
                        playerData.setSkillActionList();
                    }
                }
            }

        }


    }

}
