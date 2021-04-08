package com.daxton.customdisplay.task.action.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Title2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private CustomLineConfig customLineConfig;
    private String taskID = "";

    public Title2(){

    }

    public void setTitle(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){
        this.self = self;
        this.target = target;
        this.customLineConfig = customLineConfig;
        this.taskID = taskID;
        setOther();
    }

    public void setOther(){


        if(self instanceof Player){
            Player player = (Player) self;
            String title = customLineConfig.getString(new String[]{"title","t"},"",self,target);

            String subTitle = customLineConfig.getString(new String[]{"subtitle","s"},"",self,target);

            int fadeIn = customLineConfig.getInt(new String[]{"fadein","fi"},10,self,target);

            int duration = customLineConfig.getInt(new String[]{"duration","d"},70,self,target);

            int fadeOut = customLineConfig.getInt(new String[]{"fadeout","fo"},70,self,target);

            sendTitle(player, title, subTitle, fadeIn, duration, fadeOut);
        }


    }


    public void sendTitle(Player player, String title, String subTitle, int fadeIn, int duration, int fadeOut){
        player.sendTitle(title,subTitle,fadeIn,duration,fadeOut);
    }


}
