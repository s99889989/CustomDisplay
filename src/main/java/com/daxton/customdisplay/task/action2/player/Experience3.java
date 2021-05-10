package com.daxton.customdisplay.task.action2.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.data.set.PlayerLevel;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Experience3 {

    public Experience3(){

    }

    public static void setExp(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String type = actionMapHandle.getString(new String[]{"type"},"minecraft");

        int amount = actionMapHandle.getInt(new String[]{"amount","a"},10);

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListSelf();

        if(!(livingEntityList.isEmpty())){
            livingEntityList.forEach(livingEntity -> {
                if(livingEntity instanceof Player){
                    Player player = (Player) livingEntity;

                    if(type.contains("minecraft")){
                        expSet(player, target, amount);
                    }else {
                        expOtherSet(player, type, amount);
                    }

                }
            });

        }


    }



    public static void expSet(Player player, LivingEntity target, int amount){
        String uuidString = player.getUniqueId().toString();
        if(amount < 0){
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_down_exp_type>","default");
            new PlayerTrigger(player).onTwo(player, target, "~onexpdown");
        }

        player.giveExp(amount);
    }

    public static void expOtherSet(Player player, String type, int amount){

        new PlayerLevel().addExpMap(player,type,amount);


    }


}
