package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.*;
import com.daxton.customdisplay.manager.ActionManager;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class SendBossBar {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private BossBar bossBar;

    private String function = "";
    private String message = "";
    private BarStyle style = Enum.valueOf(BarStyle.class , "SOLID");
    private BarColor color = Enum.valueOf(BarColor.class , "BLUE");
    private BarFlag flag;
    private double progress = 0.0;

    private Player player;

    private static Map<String, BossBar> bossBarMap = new HashMap<>();
    private String taskID = "";

    public SendBossBar(){

    }

    public void set(Player player, LivingEntity target, String firstString, String taskID){
        this.taskID = taskID;
        this.player = player;
        if(target.getHealth() < 1){
            return;
        }
        List<String> stringList = new StringFind().getStringList(firstString);
        String targetName = target.getName();
        String maxHealth = String.valueOf(target.getAttribute(GENERIC_MAX_HEALTH).getValue());
        String nowHealth = new NumberUtil(target.getHealth(),"0.#").getDecimalString();
        for(String allString : stringList){
            if(allString.toLowerCase().contains("function=") || allString.toLowerCase().contains("fc=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    function = strings[1];
                }
            }
            if(allString.toLowerCase().contains("style=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    style = Enum.valueOf(BarStyle.class , strings[1]);
                }
            }
            if(allString.toLowerCase().contains("color=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    color = Enum.valueOf(BarColor.class , strings[1]);
                }
            }
            if(allString.toLowerCase().contains("flag=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    flag = Enum.valueOf(BarFlag.class , strings[1]);
                }
            }
            if(allString.toLowerCase().contains("progress=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    strings[1] = strings[1].replace("{target_nhp}",nowHealth).replace("{target_mhp}",maxHealth);
                    //strings[1] = new ArithmeticUtil().valueof(strings[1]);
                    progress = Calculator.conversion(strings[1]);
                    //progress = Double.valueOf(strings[1]);
                }
            }
        }

        List<String> stringList2 = new StringFind().getStringMessageList(firstString);
        for(String string2 : stringList2){
            if(string2.toLowerCase().contains("message=") || string2.toLowerCase().contains("m=")){
                String[] strings2 = string2.split("=");
                if(strings2.length == 2){
                    strings2[1] = strings2[1].replace("{target_name}",targetName).replace("{target_nhp}",nowHealth).replace("{target_mhp}",maxHealth);
                    message = new StringConversion().getString("Character",strings2[1],player);
                }
            }
        }

        if(function.toLowerCase().contains("create")){
            create();
        }
        if(function.toLowerCase().contains("set")){
            change();
        }
        if(function.toLowerCase().contains("delete")){
            remove();
        }
    }

    public void create(){
        if(ActionManager.getBossBar_Map().get(taskID) == null){
            ActionManager.getBossBar_Map().put(taskID, Bukkit.createBossBar(message, color, style, flag));
            ActionManager.getBossBar_Map().get(taskID).setProgress(progress);
            ActionManager.getBossBar_Map().get(taskID).addPlayer(player);
        }
//        if(bossBarMap.get(taskID) == null){
//            bossBarMap.put(taskID, Bukkit.createBossBar(message, color, style, flag));
//            bossBarMap.get(taskID).setProgress(progress);
//            bossBarMap.get(taskID).addPlayer(player);
//        }
    }

    public void change(){
        if(ActionManager.getBossBar_Map().get(taskID) != null){
            ActionManager.getBossBar_Map().get(taskID).setProgress(progress);
            ActionManager.getBossBar_Map().get(taskID).setTitle(message);
            ActionManager.getBossBar_Map().get(taskID).setStyle(style);
            ActionManager.getBossBar_Map().get(taskID).setColor(color);
        }
//        if(bossBarMap.get(taskID) != null){
//            bossBarMap.get(taskID).setProgress(progress);
//            bossBarMap.get(taskID).setTitle(message);
//            bossBarMap.get(taskID).setStyle(style);
//            bossBarMap.get(taskID).setColor(color);
//        }
    }

    public void remove(){
        if(ActionManager.getAction_Judgment_Map().get(taskID) != null){
            ActionManager.getAction_Judgment_Map().remove(taskID);
        }
        if(ActionManager.getJudgment_BossBar_Map().get(taskID) != null){
            ActionManager.getJudgment_BossBar_Map().remove(taskID);
        }
        if(ActionManager.getBossBar_Map().get(taskID) != null){
            ActionManager.getBossBar_Map().get(taskID).removePlayer(player);
            ActionManager.getBossBar_Map().remove(taskID);
        }
//        if(bossBarMap.get(taskID) != null){
//            bossBarMap.get(taskID).removePlayer(player);
//            bossBarMap.remove(taskID);
//        }
    }

    public void removeOldAction(){
        if(ActionManager.getBossBar_Map().get(taskID) != null){
            ActionManager.getBossBar_Map().get(taskID).removePlayer(player);
            ActionManager.getBossBar_Map().remove(taskID);
        }
        if(ActionManager.getJudgment_BossBar_Map().get(taskID) != null){
            ActionManager.getJudgment_BossBar_Map().remove(taskID);
        }
    }

}
