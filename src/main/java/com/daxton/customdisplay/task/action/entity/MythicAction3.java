package com.daxton.customdisplay.task.action.entity;

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

    public MythicAction3( ){

    }

    public static void setMythicAction(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") == null){
            return;
        }
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);
        String skillName = actionMapHandle.getString(new String[]{"skill","s"},"SmashAttack");
        List<LivingEntity> targetList = actionMapHandle.getLivingEntityListTarget();
        setOther(self, targetList, skillName);
    }

    public static void setOther(LivingEntity self,List<LivingEntity> targetList,String skillName){
        Optional<Skill> opt = MythicMobs.inst().getSkillManager().getSkill(skillName);
        if(!(opt.isPresent())){
            return;
        }
        Skill skill = opt.get();
        useMythicAction(self,skill,targetList);
    }

    public static void useMythicAction(LivingEntity self,Skill skill,List<LivingEntity> targetList){
        List<Entity> entityList = new ArrayList<>(targetList);
        MythicMobs.inst().getAPIHelper().castSkill(self, skill.getInternalName(), self, self.getOrigin(), entityList, null, 1.0F);
    }

}
