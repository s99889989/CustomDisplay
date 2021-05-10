package com.daxton.customdisplay.task.action2.entity;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.skills.Skill;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MythicAction3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    Skill skill = null;

    public MythicAction3( ){

    }

    public void setMythicAction(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") == null){
            return;
        }



        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String skillName = actionMapHandle.getString(new String[]{"skill","s"},"SmashAttack");

        List<LivingEntity> targetList = actionMapHandle.getLivingEntityListTarget();

        setOther(self, targetList, skillName);
    }

    public void setOther(LivingEntity self,List<LivingEntity> targetList,String skillName){


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
