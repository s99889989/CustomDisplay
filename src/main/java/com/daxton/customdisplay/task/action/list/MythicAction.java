package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.other.SetValue;
import com.daxton.customdisplay.api.other.StringFind;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.skills.Skill;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import java.util.*;

public class MythicAction {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public MythicAction( ){

    }

    public void setMythicAction(LivingEntity self, LivingEntity target, String firstString,String taskID){
        if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") == null){
            return;
        }

        String skillName = new SetValue(self,target,firstString,"[];","SmashAttack","skill=","s=").getString();

        List<LivingEntity> targetList = new Aims().valueOf(self,target,firstString);

        setOther(self,skillName,targetList);
    }

    public void setOther(LivingEntity self,String skillName,List<LivingEntity> targetList){


        Optional<Skill> opt = MythicMobs.inst().getSkillManager().getSkill(skillName);
        if(!(opt.isPresent())){
            return;
        }
        Skill skill = opt.get();

        if(!(targetList.isEmpty())){

            useMythicAction(self,skill,targetList);
        }

    }


    public void useMythicAction(LivingEntity self,Skill skill,List<LivingEntity> targetList){
        List<Entity> entityList = new ArrayList<>();
        targetList.forEach(livingEntity -> entityList.add(livingEntity));
        if(!(entityList.isEmpty())){
            MythicMobs.inst().getAPIHelper().castSkill(self, skill.getInternalName(), self, self.getOrigin(), entityList, null, 1.0F);
        }
    }



}
