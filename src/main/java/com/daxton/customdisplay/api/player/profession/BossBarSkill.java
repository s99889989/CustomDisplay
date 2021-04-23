package com.daxton.customdisplay.api.player.profession;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class BossBarSkill {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private static BossBar skillBar0;
    private static BossBar skillBar;

    public BossBarSkill(){

    }

    public void openSkill(Player player, int mainKey){
        String uuidString = player.getUniqueId().toString();
        UUID playerUUID = player.getUniqueId();

        FileConfiguration skillStatusConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_Status.yml");
        /**主手顯示**/
        String main_Hand_1_8 = skillStatusConfig.getString("BossBar1.Main_Hand.18");
        String main_Hand_9 = skillStatusConfig.getString("BossBar1.Main_Hand.9");
        /**技能間隔顯示**/
        String skill_Blank = skillStatusConfig.getString("BossBar1.Skill_Blank");
        /**技能空顯示**/
        String skill_Null_18 = skillStatusConfig.getString("BossBar1.Skill_Null.18");
        String skill_Null_9 = skillStatusConfig.getString("BossBar1.Skill_Null.9");

        PlayerData playerData = PlayerManager.getPlayerDataMap().get(playerUUID);
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
                            PlayerManager.skill_Name_Map.put(uuidString+"."+x,skillName);
                            PlayerManager.skill_Key_Map.put(uuidString+"."+x,skillAction);
                        }

                    }else {
                        File skillFile = new File(cd.getDataFolder(),"Class/Skill/Skills/"+skillName+".yml");
                        FileConfiguration skillConfig = YamlConfiguration.loadConfiguration(skillFile);
                        String barName = skillConfig.getString(skillName+".BarName");
                        skillNameString = skillNameString + barName + skill_Blank;

                        /**把Skill動作存到Map**/
                        List<String> skillAction = skillConfig.getStringList(skillName+".Action");
                        if(skillAction != null){
                            PlayerManager.skill_Name_Map.put(uuidString+"."+x,skillName);
                            PlayerManager.skill_Key_Map.put(uuidString+"."+x,skillAction);
                        }
                    }

                }else {
                    if(i == 8){
                        skillNameString = skillNameString + skill_Null_9;
                    }else {
                        skillNameString = skillNameString + skill_Null_18;
                    }
                }

                if(i == 8 && mainKey == 8){

                    skillNameString = skillNameString + main_Hand_9;

                }

            }

            /**技能第一行樣式**/
            /**顏色**/
            String bossBar1_BarColor = skillStatusConfig.getString("BossBar1.BarColor");
            /**樣式**/
            String bossBar1_BarStyle = skillStatusConfig.getString("BossBar1.BarStyle");
            /**進度條**/
            double bossBar1_Progress = skillStatusConfig.getDouble("BossBar1.Progress");

            skillBar = Bukkit.createBossBar(skillNameString, Enum.valueOf(BarColor.class , bossBar1_BarColor), Enum.valueOf(BarStyle.class , bossBar1_BarStyle));
            skillBar.setProgress(bossBar1_Progress);
            skillBar.addPlayer(player);
            /**技能第二行樣式**/
            /**內容**/
            String bossBar2_Title = skillStatusConfig.getString("BossBar2.Title");
            /**顏色**/
            String bossBar2_BarColor = skillStatusConfig.getString("BossBar2.BarColor");
            /**樣式**/
            String bossBar2_BarStyle = skillStatusConfig.getString("BossBar2.BarStyle");
            skillBar0 = Bukkit.createBossBar(bossBar2_Title, Enum.valueOf(BarColor.class , bossBar2_BarColor), Enum.valueOf(BarStyle.class , bossBar2_BarStyle));
            skillBar0.setProgress(0);
            skillBar0.addPlayer(player);
        }


    }

    public void closeSkill(Player player){
        if(skillBar != null){

            skillBar.removePlayer(player);
        }
        if(skillBar0 != null){
            skillBar0.removePlayer(player);
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

}
