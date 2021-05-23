package com.daxton.customdisplay.api.player.profession;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.SetActionMap;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.config.CustomLineConfigList;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BossBarSkill2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();


    private BossBar skillBar1;
    private BossBar skillBar2;


    private String[] skillBarShow1 = new String[9];
    private String[] skillBarShow2 = new String[9];

    public BossBarSkill2(){

    }

    public void openSkill(Player player, int mainKey){
        String uuidString = player.getUniqueId().toString();


        FileConfiguration skillStatusConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_Status.yml");

        /**主手顯示**/
        String skill_Main_Hand = skillStatusConfig.getString("BossBar1.Skill_Show.Main_Hand");
        /**技能間隔顯示**/
        String skill_Blank = skillStatusConfig.getString("BossBar1.Skill_Show.Blank");
        /**技能空顯示**/
        String skill_Null = skillStatusConfig.getString("BossBar1.Skill_Show.Skill_Null");

        PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);

//        if(!playerData.skill_Name_Map.isEmpty()){
//            playerData.skill_Name_Map.clear();
//        }
//        if(!playerData.skill_Custom_Map.isEmpty()){
//            playerData.skill_Custom_Map.clear();
//        }

        if(playerData != null){
        int binds = 0;
        for(int i = 1; i < 10 ; i++){

            if(i == mainKey+1){
                skillBarShow1[i-1] = skill_Main_Hand;
            }else {
                if(binds < 9){
                    binds++;
                }
                String skillName = playerData.getBindName(String.valueOf(binds));
                //playerData.binds_Map.get(binds+"_skillName");

                if(skillName.equals("null")){
                    skillBarShow1[i-1] = skill_Null;
                }else {
                    try {
                        FileConfiguration skillConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_Skills_"+skillName+".yml");
                        String barName = skillConfig.getString(skillName+".BarName");

                        /**把Skill動作存到Map**/
                        List<String> skillAction = skillConfig.getStringList(skillName+".Action");

                        //List<CustomLineConfig> skillCustom = new CustomLineConfigList().valueOf(skillAction);
                        List<Map<String, String>> skillCustom = SetActionMap.setClassActionList(skillAction);

//                        if(skillAction != null){
//                            playerData.skill_Name_Map.put(uuidString+"."+i,skillName);
//                            //PlayerDataMap.skill_Key_Map.put(uuidString+"."+i,skillAction);
//                            playerData.skill_Custom_Map.put(uuidString+"."+i, skillCustom);
//                        }

                        skillBarShow1[i-1] = barName;
                    }catch (NullPointerException exception){
                        skillBarShow1[i-1] = skill_Null;
                    }
                }


            }


        }

//            playerData.binds_Map.forEach((s, s2) -> {
//                cd.getLogger().info(s+" : "+s2);
//            });

            /**技能第一行樣式**/
            /**顏色**/
            String bossBar1_BarColor = skillStatusConfig.getString("BossBar1.BarColor");
            /**樣式**/
            String bossBar1_BarStyle = skillStatusConfig.getString("BossBar1.BarStyle");
            /**進度條**/
            double bossBar1_Progress = skillStatusConfig.getDouble("BossBar1.Progress");
            String skillNameString =  skillBarShow1[0]+skill_Blank+ skillBarShow1[1]+skill_Blank+ skillBarShow1[2]+skill_Blank+ skillBarShow1[3]+skill_Blank+ skillBarShow1[4]+skill_Blank+ skillBarShow1[5]+skill_Blank+ skillBarShow1[6]+skill_Blank+ skillBarShow1[7]+skill_Blank+ skillBarShow1[8];
            skillBar1 = Bukkit.createBossBar(skillNameString, Enum.valueOf(BarColor.class , bossBar1_BarColor), Enum.valueOf(BarStyle.class , bossBar1_BarStyle));
            skillBar1.setProgress(bossBar1_Progress);
            skillBar1.addPlayer(player);
            /**技能第二行樣式**/
            /**內容**/
            String bossBar2_BackGround = skillStatusConfig.getString("BossBar2.BackGround_Show.BackGround");
            String bossBar2_Blank = skillStatusConfig.getString("BossBar2.BackGround_Show.Blank");
            /**顏色**/
            String bossBar2_BarColor = skillStatusConfig.getString("BossBar2.BarColor");
            /**樣式**/
            String bossBar2_BarStyle = skillStatusConfig.getString("BossBar2.BarStyle");
            for (int k = 0;k < 9;k++){
                if(skillBarShow2[k] == null){
                    skillBarShow2[k] = bossBar2_BackGround;
                }
            }
            String skillName0String =  skillBarShow2[0]+bossBar2_Blank+ skillBarShow2[1]+bossBar2_Blank+ skillBarShow2[2]+bossBar2_Blank+ skillBarShow2[3]+bossBar2_Blank+ skillBarShow2[4]+bossBar2_Blank+ skillBarShow2[5]+bossBar2_Blank+ skillBarShow2[6]+bossBar2_Blank+ skillBarShow2[7]+bossBar2_Blank+ skillBarShow2[8];
            skillBar2 = Bukkit.createBossBar(skillName0String, Enum.valueOf(BarColor.class , bossBar2_BarColor), Enum.valueOf(BarStyle.class , bossBar2_BarStyle));
            skillBar2.setProgress(0);
            skillBar2.addPlayer(player);
        }


    }

    public void closeSkill(Player player){
        if(skillBar1 != null){

            skillBar1.removePlayer(player);
        }
        if(skillBar2 != null){
            skillBar2.removePlayer(player);
        }

    }

    public void setSkillBar2Progress(double progress) {
        this.skillBar2.setProgress(progress);
    }

    public String[] getSkillBarShow2() {
        return skillBarShow2;
    }

    public void setSkillBar2Message(String[] skillBarShow2, String bossBar2_Blank){
        String skillName2String =  skillBarShow2[0]+bossBar2_Blank+ skillBarShow2[1]+bossBar2_Blank+ skillBarShow2[2]+bossBar2_Blank+ skillBarShow2[3]+bossBar2_Blank+ skillBarShow2[4]+bossBar2_Blank+ skillBarShow2[5]+bossBar2_Blank+ skillBarShow2[6]+bossBar2_Blank+ skillBarShow2[7]+bossBar2_Blank+ skillBarShow2[8];
        skillBar2.setTitle(skillName2String);
    }


}
