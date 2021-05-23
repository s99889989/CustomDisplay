package com.daxton.customdisplay.task.action2.profession;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.api.player.data.set.PlayerSkills;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public class SetSkillLevel3 {


    public SetSkillLevel3(){

    }

    public void setCoreSkill(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String skillName = actionMapHandle.getString(new String[]{"skillname"},"minecraft");

        int amount = actionMapHandle.getInt(new String[]{"amount","a"},1);


        if(self != null && self instanceof Player && !(skillName.equals("minecraft"))){
            Player player = (Player) self;
            addSkill(player, skillName, amount);

        }

    }

    public void addSkill(Player player, String skillName, int amount){

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
