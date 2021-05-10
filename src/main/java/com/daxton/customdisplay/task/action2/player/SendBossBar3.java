package com.daxton.customdisplay.task.action2.player;

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
import java.util.concurrent.ConcurrentHashMap;

public class SendBossBar3 {

    private LivingEntity self = null;
    private LivingEntity target = null;
    private Map<String, String> action_Map;
    private String taskID = "";

    private static Map<String, BossBar> bossBar_Map = new ConcurrentHashMap<>();

    public SendBossBar3(){

    }

    public void setBossBar(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        this.self = self;
        this.target = target;
        this.taskID = taskID;
        this.action_Map = action_Map;

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String mark = actionMapHandle.getString(new String[]{"mark","mk"},"0");

        setOther(taskID+mark);
    }


    public void setOther(String livTaskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);



        BarStyle style = actionMapHandle.getBarStyle(new String[]{"style"},"SOLID");

        BarColor color = actionMapHandle.getBarColor(new String[]{"color"},"BLUE");

        BarFlag flag = actionMapHandle.getBarFlag(new String[]{"flag"});

        double progress = actionMapHandle.getDouble(new String[]{"progress"},-1);

        boolean delete = actionMapHandle.getBoolean(new String[]{"delete"},false);

        boolean deleteAll = actionMapHandle.getBoolean(new String[]{"deleteAll"},false);

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListSelf();

        if(!(livingEntityList.isEmpty())){
            for(LivingEntity livingEntity : livingEntityList){
                if(livingEntity instanceof Player){
                    Player player = (Player) livingEntity;

                    ActionMapHandle actionMapHandle2 = new ActionMapHandle(action_Map, player, target);

                    String message = actionMapHandle2.getString(new String[]{"message","m"},null);

                    if(bossBar_Map.get(livTaskID) == null){
                        if(message != null && color != null && style != null){
                            BossBar bossBar2 = Bukkit.createBossBar(message, color, style, flag);
                            bossBar2.addPlayer(player);
                            bossBar_Map.put(livTaskID, bossBar2);
                        }
                    }else {
                        BossBar bossBar1 = bossBar_Map.get(livTaskID);
                        if(message != null){
                            bossBar1.setTitle(message);
                        }
                    }

                    if(bossBar_Map.get(livTaskID) != null){
                        BossBar bossBar2 = bossBar_Map.get(livTaskID);

                        if(color != null){
                            bossBar2.setColor(color);
                        }
                        if(style != null){
                            bossBar2.setStyle(style);
                        }

                        if(progress > 0.0 && progress < 1.0000000001){
                            bossBar2.setProgress(progress);
                        }
                        if(delete){
                            bossBar2.removeAll();
                            bossBar_Map.remove(livTaskID);
                        }

                        if(deleteAll){
                            bossBar_Map.forEach((s, bossBar1) -> bossBar1.removeAll());
                            bossBar_Map.clear();
                            if(ActionManager.judgment_SendBossBar_Map2.get(taskID) != null){
                                ActionManager.judgment_SendBossBar_Map2.remove(taskID);
                            }
                        }

                    }

                }
            }

        }

    }

    public Map<String, BossBar> getBossBar_Map() {
        return bossBar_Map;
    }





}
