package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.*;
import com.daxton.customdisplay.manager.ActionManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
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
    private LivingEntity target;

    String targetName = "";
    String maxHealth = "";
    String nowHealth = "";

    private static Map<String, BossBar> bossBarMap = new HashMap<>();
    private String taskID = "";

    public SendBossBar(){

    }

    public void set(Player player, String firstString, String taskID){
        this.taskID = taskID;
        this.player = player;
        setSelfOther(firstString);
    }

    public void set(Player player, LivingEntity target, String firstString, String taskID){
        this.taskID = taskID;
        this.player = player;
        this.target = target;
        if(target.getHealth() < 1){
            return;
        }
        setOther(firstString);
    }

    public void setSelfOther(String firstString){
        List<String> stringList = new StringFind().getStringList(firstString);

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
                    if(target != null){
                        strings[1] = strings[1].replace("{target_nhp}",nowHealth).replace("{target_mhp}",maxHealth);
                    }
                    //strings[1] = new StringConversion().getString("Character",strings[1],player);
                    progress = Arithmetic.eval(strings[1]);
                }
            }
        }

        List<String> stringList2 = new StringFind().getStringMessageList(firstString);
        for(String string2 : stringList2){
            if(string2.toLowerCase().contains("message=") || string2.toLowerCase().contains("m=")){
                String[] strings2 = string2.split("=");
                if(strings2.length == 2){
                    //message = PlaceholderAPI.setPlaceholders(player,strings2[1]);
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


    public void setOther(String firstString){
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
                    if(target != null){
                        strings[1] = strings[1].replace("{target_nhp}",nowHealth).replace("{target_mhp}",maxHealth);
                    }
                    //strings[1] = new StringConversion().getString("Character",strings[1],player);

                    progress = Arithmetic.eval(strings[1]);
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
        try{
            if(ActionManager.getBossBar_Map().get(taskID) == null){
                ActionManager.getBossBar_Map().put(taskID, Bukkit.createBossBar(message, color, style, flag));
                ActionManager.getBossBar_Map().get(taskID).setProgress(progress);
                ActionManager.getBossBar_Map().get(taskID).addPlayer(player);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }


    }

    public void change(){
        try{
            if(ActionManager.getBossBar_Map().get(taskID) != null){
                ActionManager.getBossBar_Map().get(taskID).setProgress(progress);
                ActionManager.getBossBar_Map().get(taskID).setTitle(message);
                ActionManager.getBossBar_Map().get(taskID).setStyle(style);
                ActionManager.getBossBar_Map().get(taskID).setColor(color);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
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
