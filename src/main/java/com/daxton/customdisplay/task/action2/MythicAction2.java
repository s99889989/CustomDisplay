package com.daxton.customdisplay.task.action2;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.other.SetValue;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.skills.Skill;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MythicAction2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    Skill skill = null;

    public MythicAction2( ){

    }

    public void setMythicAction(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){
        if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") == null){
            return;
        }

        String skillName = customLineConfig.getString(new String[]{"skill","s"},"SmashAttack",self,target);

        List<LivingEntity> targetList = customLineConfig.getLivingEntityList(self,target);

        setOther(self,skillName,targetList);
    }

    public void setOther(LivingEntity self,String skillName,List<LivingEntity> targetList){


        Optional<Skill> opt = MythicMobs.inst().getSkillManager().getSkill(skillName);

        if(!(opt.isPresent())){
            return;
        }

        skill = opt.get();

        useMythicAction(self,skill,targetList);
    }


    public void useMythicAction(LivingEntity self,Skill skill,List<LivingEntity> targetList){
        List<Entity> entityList = new ArrayList<>();
        targetList.forEach(livingEntity -> entityList.add(livingEntity));

        MythicMobs.inst().getAPIHelper().castSkill(self, skill.getInternalName(), self, self.getOrigin(), entityList, null, 1.0F);

    }



}
