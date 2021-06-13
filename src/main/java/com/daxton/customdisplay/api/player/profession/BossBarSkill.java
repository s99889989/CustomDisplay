package com.daxton.customdisplay.api.player.profession;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.boss.BossBar;

public class BossBarSkill {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private static BossBar skillBar0;
    private static BossBar skillBar;

    public BossBarSkill(){

    }





    public void setSkillBarProgress(double progress){
        if(skillBar0 != null){
            skillBar0.setProgress(progress);

        }
    }

}
