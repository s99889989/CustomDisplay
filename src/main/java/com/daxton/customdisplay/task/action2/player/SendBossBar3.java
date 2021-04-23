package com.daxton.customdisplay.task.action2.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.manager.ActionManager;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendBossBar3 {

    private LivingEntity self = null;
    private LivingEntity target = null;
    private Map<String, String> action_Map;
    private String taskID = "";

    private BossBar bossBar;

    public SendBossBar3(){

    }

    public void setBossBar(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        this.self = self;
        this.target = target;
        this.taskID = taskID;
        this.action_Map = action_Map;
        setSelfOther();
    }

    public void setSelfOther(){

        ActionMapHandle actionMapHandle = new ActionMapHandle(this.action_Map, this.self, this.target);

        String message = actionMapHandle.getString(new String[]{"message","m"},null);

        BarStyle style = actionMapHandle.getBarStyle(new String[]{"style"},"SOLID");

        BarColor color = actionMapHandle.getBarColor(new String[]{"color"},"BLUE");

        BarFlag flag = actionMapHandle.getBarFlag(new String[]{"flag"});

        double progress = actionMapHandle.getDouble(new String[]{"progress"},-1);

        boolean delete = actionMapHandle.getBoolean(new String[]{"delete"},false);

//        if(this.self instanceof Player){
//            Player player = (Player) this.self;
//            if(this.bossBar == null){
//
//                if(message != null && color != null && style != null){
//                    this.bossBar = Bukkit.createBossBar(message, color, style, flag);
//                    this.bossBar.addPlayer(player);
//                }
//
//
//            }
//            if(this.bossBar != null){
//                if(message != null){
//                    this.bossBar.setTitle(message);
//
//                }
//                if(color != null){
//                    this.bossBar.setColor(color);
//                }
//                if(style != null){
//                    this.bossBar.setStyle(style);
//                }
//
//                if(progress > 0.0 && progress < 1.0000000001){
//                    this.bossBar.setProgress(progress);
//                }
//                if(delete){
//                    this.bossBar.removeAll();
//                    this.bossBar = null;
//                    if(ActionManager.judgment_SendBossBar_Map2.get(taskID) != null){
//                        ActionManager.judgment_SendBossBar_Map2.remove(taskID);
//                    }
//                }
//            }
//        }

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityList();

        if(!(livingEntityList.isEmpty())){
            for(LivingEntity livingEntity : livingEntityList){
                if(livingEntity instanceof Player){
                    Player player = (Player) livingEntity;
                    if(bossBar == null){

                        if(message != null && color != null && style != null){
                            bossBar = Bukkit.createBossBar(message, color, style, flag);
                            bossBar.addPlayer(player);
                        }


                    }
                    if(bossBar != null){
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
                            bossBar = null;
                        }
                    }
                }
            }

        }

    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public void setBossBar(BossBar bossBar) {
        this.bossBar = bossBar;
    }
}
