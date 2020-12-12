package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.skills.Skill;
import net.mmogroup.mmolib.api.MMOLineConfig;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MythicAction {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Skill skill;

    private Player player;

    private Entity target;

    private String skillName;

    private Optional<Skill> opt;

    public MythicAction( Player player, LivingEntity target, String firstString){
        this.target = target;
        this.player = player;
        setOther(firstString);

        opt = MythicMobs.inst().getSkillManager().getSkill(skillName);
        if(!(opt.isPresent())){
            return;
        }
        this.skill = opt.get();



    }

    public void setOther(String firstString){
        List<String> stringList = new StringFind().getStringMessageList(firstString);
        for(String allString : stringList){
            if(allString.toLowerCase().contains("skill=") || allString.toLowerCase().contains("s=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    skillName = strings[1];
                }
            }
        }

    }


    public void useMythicAction(){
        List<Entity> targets = new ArrayList();
        targets.add(target);
        targets.add(player.getPlayer());
        if(!(opt.isPresent())){
            return;
        }
        MythicMobs.inst().getAPIHelper().castSkill(player, skill.getInternalName(), player, player.getEyeLocation(), targets, null, 1.0F);
    }


}
