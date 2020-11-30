package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.util.ArithmeticUtil;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class BossBar {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private org.bukkit.boss.BossBar bossBar;

    private String function = "";
    private String message = "";
    private BarStyle style = Enum.valueOf(BarStyle.class , "SOLID");
    private BarColor color = Enum.valueOf(BarColor.class , "BLUE");
    private BarFlag flag; // = Enum.valueOf(BarFlag.class , "")
    private double progress = 0.0;

    private Player player;

    public BossBar(){

    }

    public void set(Player player, LivingEntity target, String firstString, String task){
        this.player = player;
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(firstString,"[;] ");
        String targetName = target.getName();
        String maxHealth = String.valueOf(target.getAttribute(GENERIC_MAX_HEALTH).getValue());
        String nowHealth = String.valueOf(target.getHealth());
        while(stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        for(String allString : stringList){
            if(allString.toLowerCase().contains("function=") || allString.toLowerCase().contains("fc=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    function = strings[1];
                }
            }
            if(allString.toLowerCase().contains("message=") || allString.toLowerCase().contains("m=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    strings[1] = strings[1].replace("{target_name}",targetName).replace("{target_nhp}",nowHealth).replace("{target_mhp}",maxHealth);
                    message = new StringConversion().getString("Character",strings[1],player);
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
                    strings[1] = new ArithmeticUtil().valueof(strings[1]);
                    progress = Double.valueOf(strings[1]);
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
        bossBar = Bukkit.createBossBar(message, color, style, flag);
        bossBar.setProgress(progress);
        bossBar.addPlayer(player);
    }

    public void change(){
        bossBar.setProgress(progress);
        bossBar.setTitle(message);
        bossBar.setStyle(style);
        bossBar.setColor(color);
    }

    public void remove(){
        bossBar.removePlayer(player);
    }

    public void deleteBossBar(){
        bossBar.removeAll();
    }

}
