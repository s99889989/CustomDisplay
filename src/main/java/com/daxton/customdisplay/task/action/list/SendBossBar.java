package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
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
import java.util.*;

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


        function = new StringFind().getKeyValue(self,target,firstString,"[];","fc=","function=");


        aims = new StringFind().getKeyValue(self,target,firstString,"[]; ","@=");
        try {
            style = Enum.valueOf(BarStyle.class , new StringFind().getKeyValue(self,target,firstString,"[];","style="));
        }catch (Exception exception){
            style = Enum.valueOf(BarStyle.class , "SOLID");
        }

        try {
            color = Enum.valueOf(BarColor.class , new StringFind().getKeyValue(self,target,firstString,"[];","color="));
        }catch (Exception exception){
            color = Enum.valueOf(BarColor.class , "BLUE");
        }
        try {
            flag = Enum.valueOf(BarFlag.class , new StringFind().getKeyValue(self,target,firstString,"[];","flag="));
        }catch (Exception exception){
            flag = null;
        }

        message = new StringFind().getKeyValue(self,target,firstString,"[];","m=","message=");

        try {
            progress = Double.valueOf(new StringFind().getKeyValue(self,target,firstString,"[];","progress="));
        }catch (NumberFormatException exception){
            progress = 0;
        }
        if(progress > 1){
            progress = 0;
        }

        //cd.getLogger().info(function+" : "+aims+" : "+style+" : "+color+" : "+flag+" : "+message+" : "+progress);

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

    public void openSkill(Player player,int mainKey){
        String uuidString = player.getUniqueId().toString();
        UUID playerUUID = player.getUniqueId();

        FileConfiguration skillStatusConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_Status.yml");
        /**主手顯示**/
        String main_Hand_1_8 = skillStatusConfig.getString("Skill_Status.Main_Hand.18");
        String main_Hand_9 = skillStatusConfig.getString("Skill_Status.Main_Hand.9");
        /**技能空格顯示**/
        String skill_Blank = skillStatusConfig.getString("Skill_Status.Skill_Blank");
        /**技能背景顯示**/
        String skill_Back_18 = skillStatusConfig.getString("Skill_Status.Skill_Back.18");
        String skill_Back_9 = skillStatusConfig.getString("Skill_Status.Skill_Back.9");

        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
        if(playerData != null){
            String skillNameString = "";
            for(int i = 1 ; i < 9 ; i++){
                int x = 0;
                if(i-1 >= mainKey){
                    x = i;
                }else {
                    x = i-1;
                }

                String skillName = playerData.binds_Map.get(i+"_skillName");

                if(mainKey != 8 && i == mainKey+1){

                    skillNameString = skillNameString + main_Hand_1_8;

                }

                if(!(skillName.contains("null"))){
                    if(i == 8){
                        File skillFile = new File(cd.getDataFolder(),"Class/Skill/Skills/"+skillName+".yml");
                        FileConfiguration skillConfig = YamlConfiguration.loadConfiguration(skillFile);
                        String barName = skillConfig.getString(skillName+".BarName");
                        skillNameString = skillNameString + barName;

                        /**把Skill動作存到Map**/

                        List<String> skillAction = skillConfig.getStringList(skillName+".Action");
                        if(skillAction != null){
                            PlayerDataMap.skill_Name_Map.put(uuidString+"."+x,skillName);
                            PlayerDataMap.skill_Key_Map.put(uuidString+"."+x,skillAction);
                        }

                    }else {
                        File skillFile = new File(cd.getDataFolder(),"Class/Skill/Skills/"+skillName+".yml");
                        FileConfiguration skillConfig = YamlConfiguration.loadConfiguration(skillFile);
                        String barName = skillConfig.getString(skillName+".BarName");
                        skillNameString = skillNameString + barName + skill_Blank;

                        /**把Skill動作存到Map**/
                        List<String> skillAction = skillConfig.getStringList(skillName+".Action");
                        if(skillAction != null){
                            PlayerDataMap.skill_Name_Map.put(uuidString+"."+x,skillName);
                            PlayerDataMap.skill_Key_Map.put(uuidString+"."+x,skillAction);
                        }
                    }

                }else {
                    if(i == 8){
                        skillNameString = skillNameString + skill_Back_9;
                    }else {
                        skillNameString = skillNameString + skill_Back_18;
                    }
                }

                if(i == 8 && mainKey == 8){

                    skillNameString = skillNameString + main_Hand_9;

                }

            }


            skillBar = Bukkit.createBossBar(skillNameString, Enum.valueOf(BarColor.class , "BLUE"), Enum.valueOf(BarStyle.class , "SEGMENTED_10"));
            skillBar.setProgress(0);
            skillBar.addPlayer(player);

            skillBar0 = Bukkit.createBossBar("䃍\uF822䃍\uF822䃍\uF822䃍\uF822䃍\uF822䃍\uF822䃍\uF822䃍\uF822䃍", Enum.valueOf(BarColor.class , "BLUE"), Enum.valueOf(BarStyle.class , "SEGMENTED_20"));
            skillBar0.setProgress(0);
            skillBar0.addPlayer(player);
        }


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
