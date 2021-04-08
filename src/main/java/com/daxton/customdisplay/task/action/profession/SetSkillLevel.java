package com.daxton.customdisplay.task.action.profession;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.player.data.set.PlayerSkills;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;


public class SetSkillLevel {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private CustomLineConfig customLineConfig;

    public SetSkillLevel(){

    }

    public void setCoreSkill(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){
        this.self = self;
        this.target = target;
        this.customLineConfig = customLineConfig;
        setOther();

    }

    public void setOther(){

        String skillName = customLineConfig.getString(new String[]{"skillname"},"minecraft",self,target);

        int amount = customLineConfig.getInt(new String[]{"amount","a"},1,self,target);


        if(self != null && self instanceof Player && !(skillName.equals("minecraft"))){
            Player player = (Player) self;
            addSkill(player, skillName, amount);

        }



    }

    public void addSkill(Player player, String skillName, int amount){

        new PlayerSkills().setOneMap(player,skillName,amount);


    }

}
