package com.daxton.customdisplay.task.action.player;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.ActionManager;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class SendBossBar4 {

    public SendBossBar4(){

    }

    public static void setBossBar(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String mark = actionMapHandle.getString(new String[]{"mark","mk"},"0");

        BarStyle style = actionMapHandle.getBarStyle(new String[]{"s", "style"},"SOLID");

        BarColor color = actionMapHandle.getBarColor(new String[]{"c", "color"},"BLUE");

        BarFlag flag = actionMapHandle.getBarFlag(new String[]{"f", "flag"});

        double progress = actionMapHandle.getDouble(new String[]{"p", "progress"},-1);

        boolean delete = actionMapHandle.getBoolean(new String[]{"d","delete","deleteAll"},false);

        String message = actionMapHandle.getString(new String[]{"message","m"}, null);

        if(ActionManager.bossBar_Map.get(taskID+mark) == null){

            if(message != null && color != null && style != null){

                BossBar bossBar = Bukkit.createBossBar(message, color, style, flag);

                List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListSelf();
                livingEntityList.forEach(livingEntity -> {
                    if(livingEntity instanceof Player){
                        Player player = (Player) livingEntity;
                        bossBar.addPlayer(player);
                    }
                });

                ActionManager.bossBar_Map.put(taskID+mark, bossBar);
            }

        }

        if(ActionManager.bossBar_Map.get(taskID+mark) != null){
            BossBar bossBar = ActionManager.bossBar_Map.get(taskID+mark);

            if(message != null){
                bossBar.setTitle(message);
            }

            if(color != null){
                bossBar.setColor(color);
            }

            if(style != null){
                bossBar.setStyle(style);
            }

            if(progress > 0.0 && progress < 1.0000000001){
                bossBar.setProgress(progress);
            }

            if(delete){
                bossBar.removeAll();
                ActionManager.bossBar_Map.remove(taskID+mark);
                return;
            }

            ActionManager.bossBar_Map.put(taskID+mark, bossBar);
        }


    }

}
