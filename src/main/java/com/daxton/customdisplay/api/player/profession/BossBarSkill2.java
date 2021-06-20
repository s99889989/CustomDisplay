package com.daxton.customdisplay.api.player.profession;


import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class BossBarSkill2 {

    private BossBar skillBar1;
    private BossBar skillBar2;

    private final String[] skillBarShow1 = new String[9];
    private final String[] skillBarShow2 = new String[9];

    public BossBarSkill2(){

    }

    public void openSkill(Player player, int mainKey){
        String uuidString = player.getUniqueId().toString();

        FileConfiguration skillStatusConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_Status.yml");

        //主手顯示
        String skill_Main_Hand = skillStatusConfig.getString("BossBar1.Skill_Show.Main_Hand");
        //技能間隔顯示
        String skill_Blank = skillStatusConfig.getString("BossBar1.Skill_Show.Blank");
        //技能空顯示
        String skill_Null = skillStatusConfig.getString("BossBar1.Skill_Show.Skill_Null");

        PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);

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

                if(skillName.equals("null")){
                    skillBarShow1[i-1] = skill_Null;
                }else {
                    try {
                        String[] skillNameArray = skillName.split("\\|");
                        if(skillNameArray.length == 2){
                            FileConfiguration skillConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_"+skillNameArray[0]+".yml");
                            String barName = skillConfig.getString("Skills."+skillNameArray[1]+".BarName");
                            int useLevel = playerData.getSkillLevel(skillName+"_use");
                            if(useLevel > 0){
                                skillBarShow1[i-1] = barName;
                            }else {
                                skillBarShow1[i-1] = skill_Null;
                            }

                        }

                    }catch (NullPointerException exception){
                        skillBarShow1[i-1] = skill_Null;
                    }
                }

            }

        }

            //技能第一行樣式
            //顏色
            String bossBar1_BarColor = skillStatusConfig.getString("BossBar1.BarColor");
            //樣式
            String bossBar1_BarStyle = skillStatusConfig.getString("BossBar1.BarStyle");
            //進度條
            double bossBar1_Progress = skillStatusConfig.getDouble("BossBar1.Progress");
            String skillNameString =  skillBarShow1[0]+skill_Blank+ skillBarShow1[1]+skill_Blank+ skillBarShow1[2]+skill_Blank+ skillBarShow1[3]+skill_Blank+ skillBarShow1[4]+skill_Blank+ skillBarShow1[5]+skill_Blank+ skillBarShow1[6]+skill_Blank+ skillBarShow1[7]+skill_Blank+ skillBarShow1[8];
            if(bossBar1_BarColor != null && bossBar1_BarStyle != null){
                try {
                    skillBar1 = Bukkit.createBossBar(skillNameString, Enum.valueOf(BarColor.class , bossBar1_BarColor), Enum.valueOf(BarStyle.class , bossBar1_BarStyle));
                }catch (Exception exception){
                    skillBar1 = Bukkit.createBossBar(skillNameString, BarColor.BLUE, BarStyle.SEGMENTED_10);
                }
            }else {
                skillBar1 = Bukkit.createBossBar(skillNameString, BarColor.BLUE, BarStyle.SEGMENTED_10);
            }


            skillBar1.setProgress(bossBar1_Progress);
            skillBar1.addPlayer(player);
            //技能第二行樣式
            //內容
            String bossBar2_BackGround = skillStatusConfig.getString("BossBar2.BackGround_Show.BackGround");
            String bossBar2_Blank = skillStatusConfig.getString("BossBar2.BackGround_Show.Blank");
            //顏色
            String bossBar2_BarColor = skillStatusConfig.getString("BossBar2.BarColor");
            //樣式
            String bossBar2_BarStyle = skillStatusConfig.getString("BossBar2.BarStyle");
            for (int k = 0;k < 9;k++){
                if(skillBarShow2[k] == null){
                    skillBarShow2[k] = bossBar2_BackGround;
                }
            }
            String skillName0String =  skillBarShow2[0]+bossBar2_Blank+ skillBarShow2[1]+bossBar2_Blank+ skillBarShow2[2]+bossBar2_Blank+ skillBarShow2[3]+bossBar2_Blank+ skillBarShow2[4]+bossBar2_Blank+ skillBarShow2[5]+bossBar2_Blank+ skillBarShow2[6]+bossBar2_Blank+ skillBarShow2[7]+bossBar2_Blank+ skillBarShow2[8];
            if(bossBar2_BarColor != null && bossBar2_BarStyle != null){
                try {
                    skillBar2 = Bukkit.createBossBar(skillName0String, Enum.valueOf(BarColor.class , bossBar2_BarColor), Enum.valueOf(BarStyle.class , bossBar2_BarStyle));
                }catch (Exception exception){
                    skillBar2 = Bukkit.createBossBar(skillNameString, BarColor.BLUE, BarStyle.SEGMENTED_10);
                }
            }else {
                skillBar2 = Bukkit.createBossBar(skillNameString, BarColor.BLUE, BarStyle.SEGMENTED_10);
            }


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
