package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion2;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ActionManager2;
import com.daxton.customdisplay.task.action.ClearAction;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SendBossBar2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private BossBar bossBar;

    private String aims = "self";
    private String function = "";
    private String message = "";
    private BarStyle style = Enum.valueOf(BarStyle.class , "SOLID");
    private BarColor color = Enum.valueOf(BarColor.class , "BLUE");
    private BarFlag flag = null;
    private double progress = 0.0;

    private Player player = null;
    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";

    private static Map<String, BossBar> bossBarMap = new HashMap<>();


    public SendBossBar2(){

    }

    public void setBossBar(LivingEntity self, LivingEntity target, String firstString, String taskID){

        this.self = self;
        this.target = target;
        this.taskID = taskID;
        this.firstString = firstString;
//        if(target != null && target.getHealth() < 1){
//            return;
//        }
        setSelfOther();
    }

    public void setSelfOther(){

        for(String allString : new StringFind().getStringList(firstString)){
            if(allString.toLowerCase().contains("function=") || allString.toLowerCase().contains("fc=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    function = strings[1];
                }
            }

            if(allString.toLowerCase().contains("@=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    aims = strings[1];
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

        }


        for(String allString: new StringFind().getStringMessageList(firstString)){
            if(allString.toLowerCase().contains("message=") || allString.toLowerCase().contains("m=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    message = new StringConversion2(self,target,strings[1],"Character").valueConv();
                }
            }
            if(allString.toLowerCase().contains("progress=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    if(target != null){
                        try {
                            progress = Double.valueOf(new StringConversion2(self,target,strings[1],"Character").valueConv());
                        }catch (NumberFormatException exception){
                            //cd.getLogger().info("不是數字");
                        }
                    }

                }
            }
        }

        if(target instanceof Player & aims.toLowerCase().contains("target")){
            player = (Player) target;
        }else {
            if(self instanceof Player){
                player = (Player) self;
            }
        }


        if(player != null){
            if(bossBar == null & function.toLowerCase().contains("create")){
                create();
            }
            if(bossBar != null & function.toLowerCase().contains("set")) {
                change();
            }
            if(bossBar != null & function.toLowerCase().contains("delete")){
                remove();
            }
        }

    }

    public void create(){
        try{
            bossBar = Bukkit.createBossBar(message, color, style, flag);
            bossBar.setProgress(progress);
            bossBar.addPlayer(player);
        }catch (Exception exception){
            exception.printStackTrace();
        }


    }

    public void change(){
        try{
            bossBar.setProgress(progress);
            bossBar.setTitle(message);
            bossBar.setStyle(style);
            bossBar.setColor(color);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public void remove(){
        new ClearAction(taskID);
        bossBar.removePlayer(player);
        bossBar.removeAll();

    }

    public BossBar getBossBar() {
        return bossBar;
    }
}
