package com.daxton.customdisplay.task.action2.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.other.SetValue;
import com.daxton.customdisplay.task.action.ClearAction;
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

public class SendBossBar2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public Map<String, BossBar> bossBarMap = new HashMap<>();
    public Map<String, Player> playerMap = new HashMap<>();


    private LivingEntity self = null;
    private LivingEntity target = null;
    private CustomLineConfig customLineConfig;
    private String taskID = "";

    private BossBar bossBar;

    public SendBossBar2(){

    }

    public void setBossBar(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){

        this.self = self;
        this.target = target;
        this.taskID = taskID;
        this.customLineConfig = customLineConfig;
        setSelfOther();
    }

    public void setSelfOther(){

        String message = customLineConfig.getString(new String[]{"message","m"},"", self, target);

        String function = customLineConfig.getString(new String[]{"function","fc"},"", self, target);

        BarStyle style = customLineConfig.getBarStyle(new String[]{"style"},"SOLID", self, target);

        BarColor color = customLineConfig.getBarColor(new String[]{"color"},"BLUE", self, target);

        BarFlag flag = customLineConfig.getBarFlag(new String[]{"flag"}, self, target);

        double progress = customLineConfig.getDouble(new String[]{"progress"},0, self, target);

        if(self instanceof Player){
            Player player = (Player) self;
            if(function.toLowerCase().contains("create")){
                bossBar = create(player, message, color, style, flag, progress);
            }else if(function.toLowerCase().contains("set")) {
                change(bossBar, message, color, style, progress);
            }else if(function.toLowerCase().contains("delete")){
                remove(player, bossBar);
            }

        }


//        /**獲得目標**/
//        List<LivingEntity> targetList = new Aims().valueOf(self,target,firstString);
//
//            if(function.toLowerCase().contains("create")){
//                if(!(targetList.isEmpty())) {
//                    for (LivingEntity livingEntity : targetList) {
//                        if (self instanceof Player) {
//                            Player player = (Player) self;
//                            String uuidString = player.getUniqueId().toString();
//                            String message = new SetValue(player, livingEntity, firstString, "[];","", "m=", "message=").getString();
//
//                            if(bossBarMap.get(uuidString) == null){
//                                playerMap.put(uuidString,player);
//                                bossBarMap.put(uuidString, create(player, message, color, style, flag, progress));
//                            }
//                        }
//                    }
//                }
//            }else if(function.toLowerCase().contains("set")) {
//                BarColor color1 = color;
//                BarStyle style1 = style;
//                double progress1 = progress;
//                bossBarMap.forEach((s,bossBar1) -> {
//                    Player player = playerMap.get(s);
//                    String message = new SetValue(player, target, firstString, "[];","", "m=", "message=").getString();
//                    change(bossBar1, message, color1, style1, progress1);
//
//                });
//
//            }else if(function.toLowerCase().contains("delete")){
//                bossBarMap.forEach((s,bossBar1) -> {
//                    Player player = playerMap.get(s);
//                    remove(player, bossBar1);
//                });
//                bossBarMap.clear();
//                new ClearAction(taskID);
//            }


    }

    /**建立新的BossBar**/
    public BossBar create(Player player, String message, BarColor color, BarStyle style, BarFlag flag, double progress){

        BossBar bossBar = null;
        try{
            bossBar = Bukkit.createBossBar(message, color, style, flag);
            bossBar.setProgress(progress);
            bossBar.addPlayer(player);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return bossBar;
    }
    /**修改BossBar內容**/
    public void change(BossBar bossBar, String message, BarColor color, BarStyle style, double progress){
        try{
            bossBar.setProgress(progress);
            bossBar.setTitle(message);
            bossBar.setStyle(style);
            bossBar.setColor(color);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    /**移除BossBar**/
    public void remove(Player player ,BossBar bossBar){

        //bossBar.removePlayer(player);
        bossBar.removeAll();

    }

    public BossBar getBossBar() {
        return bossBar;
    }
}
