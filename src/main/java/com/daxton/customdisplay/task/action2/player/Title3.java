package com.daxton.customdisplay.task.action2.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class Title3 {

    public Title3(){

    }

    public static void setTitle(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        int fadeIn = actionMapHandle.getInt(new String[]{"fadein","fi"},10);

        int duration = actionMapHandle.getInt(new String[]{"duration","d"},70);

        int fadeOut = actionMapHandle.getInt(new String[]{"fadeout","fo"},70);

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListTarget();

        livingEntityList.forEach(livingEntity -> {

            if(livingEntity instanceof Player){
                Player player = (Player) livingEntity;
                ActionMapHandle actionMapHandle2 = new ActionMapHandle(action_Map, self, livingEntity);
                String title = actionMapHandle2.getString(new String[]{"title","t"},"");

                String subTitle = actionMapHandle2.getString(new String[]{"subtitle","s"},"");
                sendTitle(player, title, subTitle, fadeIn, duration, fadeOut);

            }

        });


    }



    public static void sendTitle(Player player, String title, String subTitle, int fadeIn, int duration, int fadeOut){
        player.sendTitle(title,subTitle,fadeIn,duration,fadeOut);
    }


}
