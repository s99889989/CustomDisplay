package com.daxton.customdisplay.task.action2.player;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.player.data.set.PlayerLevel;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class Level3 {

    public Level3(){

    }

    public static void setLevel(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String type = actionMapHandle.getString(new String[]{"type"},"minecraft");

        String function = actionMapHandle.getString(new String[]{"function","fc"},"minecraft");



        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListSelf();

        livingEntityList.forEach(livingEntity -> {
            if(livingEntity instanceof Player){
                Player player = (Player) livingEntity;

                ActionMapHandle actionMapHandle2 = new ActionMapHandle(action_Map, self, player);

                int amount = actionMapHandle2.getInt(new String[]{"amount","a"},1);

                if(type.contains("minecraft")){
                    if(function.toLowerCase().contains("set")){
                        //設定原版等級
                        player.setLevel(amount);
                    }else {
                        //增加原版等級
                        player.giveExpLevels(amount);
                    }
                }else {
                    if(function.toLowerCase().contains("set")){
                        //設定自訂等級
                        new PlayerLevel().setLevelMap(player,type,amount);
                    }else {
                        //增加自訂等級
                        new PlayerLevel().addLevelMap(player,type,amount);
                    }
                }

            }
        });


    }






}
