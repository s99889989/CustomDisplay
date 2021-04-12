package com.daxton.customdisplay.task.action.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
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

public class SendBossBar {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public Map<String, BossBar> bossBarMap = new HashMap<>();
    public Map<String, Player> playerMap = new HashMap<>();


    private LivingEntity self = null;
    private LivingEntity target = null;
    private CustomLineConfig customLineConfig;
    private String taskID = "";

    private BossBar bossBar;

    public SendBossBar(){

    }

    public void setBossBar(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){

        this.self = self;
        this.target = target;
        this.taskID = taskID;
        this.customLineConfig = customLineConfig;
        setSelfOther();
    }

    public void setSelfOther(){

        String message = customLineConfig.getString(new String[]{"message","m"},null, self, target);

        BarStyle style = customLineConfig.getBarStyle(new String[]{"style"},"SOLID", self, target);

        BarColor color = customLineConfig.getBarColor(new String[]{"color"},"BLUE", self, target);

        BarFlag flag = customLineConfig.getBarFlag(new String[]{"flag"}, self, target);

        double progress = customLineConfig.getDouble(new String[]{"progress"},-1, self, target);

        boolean delete = customLineConfig.getBoolean(new String[]{"function","fc"},false, self, target);


        List<LivingEntity> livingEntityList = customLineConfig.getLivingEntityList(self,target);

        if(!(livingEntityList.isEmpty())){
            for(LivingEntity livingEntity : livingEntityList){
                if(livingEntity instanceof Player){
                    Player player = (Player) livingEntity;
                    if(bossBar == null){

                        if(message != null && color != null && style != null){
                            bossBar = Bukkit.createBossBar(message, color, style, flag);
                        }

                        bossBar.addPlayer(player);
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

//        if(self instanceof Player){
//            Player player = (Player) self;
//            if(bossBar == null){
//
//                if(message != null && color != null && style != null){
//                    bossBar = Bukkit.createBossBar(message, color, style, flag);
//                }
//
//                bossBar.addPlayer(player);
//            }
//            if(bossBar != null){
//                if(message != null){
//                    bossBar.setTitle(message);
//                }
//                if(color != null){
//                    bossBar.setColor(color);
//                }
//                if(style != null){
//                    bossBar.setStyle(style);
//                }
//
//                if(progress > 0.0 && progress < 1.0000000001){
//                    bossBar.setProgress(progress);
//                }
//                if(delete){
//                    bossBar.removeAll();
//                    bossBar = null;
//                }
//            }
//
//        }

    }

    public BossBar getBossBar() {
        return bossBar;
    }
}
