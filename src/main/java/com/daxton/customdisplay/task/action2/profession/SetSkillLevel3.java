package com.daxton.customdisplay.task.action2.profession;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.player.data.set.PlayerSkills;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Map;


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

        new PlayerSkills().setOneMap(player,skillName,amount);


    }

}
