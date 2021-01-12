package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.task.action.ClearAction;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendBossBar {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private BossBar bossBar;

    private static BossBar skillBar0;
    private static BossBar skillBar;

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
                    message = new StringConversion(self,target,strings[1],"Character").valueConv();
                }
            }
            if(allString.toLowerCase().contains("progress=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    if(target != null){
                        try {
                            progress = Double.valueOf(new StringConversion(self,target,strings[1],"Character").valueConv());
                        }catch (NumberFormatException exception){
                            progress = 0;
                        }
                        if(progress > 1){
                            progress = 0;
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

    public void openSkill(Player player){
        String uuidString = player.getUniqueId().toString();
        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+"/"+uuidString+".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);

        String skillNameString = "";
        for(int i = 1 ; i < 9 ; i++){
             String skillName = playerConfig.getString(uuidString+".Binds."+i+".SkillName");
             if(!(skillName.contains("null"))){
                 if(i == 8){
                     File skillFile = new File(cd.getDataFolder(),"Class/Skill/Skills/"+skillName+".yml");
                     FileConfiguration skillConfig = YamlConfiguration.loadConfiguration(skillFile);
                     String barName = skillConfig.getString(skillName+".BarName");
                     skillNameString = skillNameString + barName;

                 }else {
                     File skillFile = new File(cd.getDataFolder(),"Class/Skill/Skills/"+skillName+".yml");
                     FileConfiguration skillConfig = YamlConfiguration.loadConfiguration(skillFile);
                     String barName = skillConfig.getString(skillName+".BarName");
                     skillNameString = skillNameString + barName + "\uF822";

                 }

             }else {
                 if(i == 8){
                     skillNameString = skillNameString + "䂶";
                 }else {
                     skillNameString = skillNameString + "䂶" + "\uF822";
                 }
             }

        }


        skillBar = Bukkit.createBossBar(skillNameString, Enum.valueOf(BarColor.class , "BLUE"), Enum.valueOf(BarStyle.class , "SEGMENTED_10"));
        skillBar.setProgress(0);
        skillBar.addPlayer(player);

        skillBar0 = Bukkit.createBossBar("䃍\uF822䃍\uF822䃍\uF822䃍\uF822䃍\uF822䃍\uF822䃍\uF822䃍", Enum.valueOf(BarColor.class , "BLUE"), Enum.valueOf(BarStyle.class , "SEGMENTED_20"));
        skillBar0.setProgress(0);
        skillBar0.addPlayer(player);

    }

    public void closeSkill(Player player){
        if(skillBar != null && skillBar0 != null){
            skillBar0.removePlayer(player);
            skillBar.removePlayer(player);
        }

    }

    public String getSkillMessage(){
        return skillBar0.getTitle();
    }

    public void setSkillBar(String message){
        skillBar0.setTitle(message);
    }

    public void setSkillBarProgress(double progress){
        if(skillBar0 != null){
            skillBar0.setProgress(progress);

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
