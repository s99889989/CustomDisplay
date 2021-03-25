package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.other.SetValue;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import com.daxton.customdisplay.task.action.ClearAction;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class SendBossBar {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public Map<String, BossBar> bossBarMap = new HashMap<>();
    public Map<String, Player> playerMap = new HashMap<>();


    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";




    public SendBossBar(){

    }

    public void setBossBar(LivingEntity self, LivingEntity target, String firstString, String taskID){
        this.self = self;
        this.target = target;
        this.taskID = taskID;
        this.firstString = firstString;
        setSelfOther();
    }

    public void setSelfOther(){

        String function = new SetValue(self,target,firstString,"[];","","fc=","function=").getString();

        BarStyle style = new SetValue(self,target,firstString,"[];","SOLID","style=").getBarStyle("SOLID");

        BarColor color = new SetValue(self,target,firstString,"[];","BLUE","color=").getBarColor("BLUE");

        BarFlag flag = new SetValue(self,target,firstString,"[];","","flag=").getBarFlag();

        double progress = new SetValue(self,target,firstString,"[];","0","progress=").getDouble(0);

        /**獲得目標**/
        List<LivingEntity> targetList = new Aims().valueOf(self,target,firstString);


            if(function.toLowerCase().contains("create")){
                if(!(targetList.isEmpty())) {
                    for (LivingEntity livingEntity : targetList) {
                        if (livingEntity instanceof Player) {
                            Player player = (Player) livingEntity;
                            String uuidString = player.getUniqueId().toString();
                            String message = new SetValue(player, target, firstString, "[];","", "m=", "message=").getString();
                            if(bossBarMap.get(uuidString) == null){
                                playerMap.put(uuidString,player);
                                bossBarMap.put(uuidString, create(player, message, color, style, flag, progress));
                            }
                        }
                    }
                }
            }else if(function.toLowerCase().contains("set")) {
                BarColor color1 = color;
                BarStyle style1 = style;
                double progress1 = progress;
                bossBarMap.forEach((s,bossBar1) -> {
                    Player player = playerMap.get(s);
                    String message = new SetValue(player, target, firstString, "[];","", "m=", "message=").getString();
                    change(bossBar1, message, color1, style1, progress1);

                });

            }else if(function.toLowerCase().contains("delete")){
                bossBarMap.forEach((s,bossBar1) -> {
                    Player player = playerMap.get(s);
                    remove(player, bossBar1);
                });
                bossBarMap.clear();
                new ClearAction(taskID);
            }


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
        bossBar.removePlayer(player);
        bossBar.removeAll();
    }

    public Map<String, BossBar> getBossBarMap() {
        return bossBarMap;
    }

    public Map<String, Player> getPlayerMap() {
        return playerMap;
    }
}
