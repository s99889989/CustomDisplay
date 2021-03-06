package com.daxton.customdisplay.api.player.profession;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.SetActionMap;
import com.daxton.customdisplay.api.entity.LookTarget;
import com.daxton.customdisplay.api.item.CustomItem2;
import com.daxton.customdisplay.api.location.DirectionLocation;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;

public class UseSkill {

    private final CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Hologram hologram;

    public UseSkill(){

    }

    //綁案件轉為綁定順序
    public static int getUseKey(int key, int okey){
        int usekey = 1;
        if(key > okey){
            usekey = key;
        }
        if(key < okey){
            usekey = key + 1;
        }
        return usekey;
    }

    public void use(Player player,int key, int okey){
        String uuidString = player.getUniqueId().toString();
        int useKey = getUseKey(key, okey);

        PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);

        String skillName = playerData.getBindName(String.valueOf(useKey));
        int useLevel = playerData.getSkillLevel(skillName+"_use");
        if(useLevel > 0){
            String[] skillNameArray = skillName.split("\\|");
            if(skillNameArray.length == 2){

                if(!skillNameArray[1].equals("null") && ConfigMapManager.getFileConfigurationMap().get("Class_Skill_"+skillNameArray[0]+".yml") != null){
                    //技能設定檔**/
                    FileConfiguration skillConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_"+skillNameArray[0]+".yml");
                    //技能是否需要目標**/
                    boolean needTarget = skillConfig.getBoolean("Skills."+skillNameArray[1]+".NeedTarget");
                    //目標最遠距離**/
                    int targetDistance = skillConfig.getInt("Skills."+skillNameArray[1]+".TargetDistance");
                    //需要魔量**/
                    double needMand = skillConfig.getDouble("Skills."+skillNameArray[1]+".Mana");
                    //動作列表
                    List<String> actionStringList = skillConfig.getStringList("Skills."+skillNameArray[1]+".Action");

                    List<Map<String, String>> actionCustom = SetActionMap.setClassActionList(actionStringList);

                    double nowMans = playerData.getMana();

                    if(actionCustom.size() > 0){
                        LivingEntity target = LookTarget.getLivingTarget(player, targetDistance);

                        if(needTarget){
                            if(target != null){

                                if(nowMans >= needMand) {
                                    nowMans = nowMans - needMand;
                                    playerData.setMana(nowMans);
                                    runSKill(player, target, uuidString, useKey, okey, targetDistance, skillName, actionCustom);
                                }

                            }
                        }else {

                            if(nowMans >= needMand) {
                                nowMans = nowMans - needMand;
                                playerData.setMana(nowMans);
                                runSKill(player, target, uuidString, useKey , okey, targetDistance, skillName, actionCustom);
                            }
                        }
                    }

                }
            }
        }




    }

    public void runSKill(Player player,LivingEntity target, String uuidString, int key, int mainKey, int targetDistance, String skillName, List<Map<String, String>> actionCustom){

        //技能獨立延遲初始化
        PlayerManager.skill_Cool_Down_Boolean_Map.putIfAbsent(uuidString+"."+key,true);

        //技能動作延遲初始化
        PlayerManager.cost_Delay_Boolean_Map.putIfAbsent(uuidString,true);

        //技能動作延遲**/


        //技能獨立延遲**/
        if(PlayerManager.cost_Delay_Boolean_Map.get(uuidString) != null && PlayerManager.skill_Cool_Down_Boolean_Map.get(uuidString+"."+key) != null){
            boolean costDelay = PlayerManager.cost_Delay_Boolean_Map.get(uuidString);
            boolean coolDown = PlayerManager.skill_Cool_Down_Boolean_Map.get(uuidString+"."+key);
            if(costDelay && coolDown){
                PlayerManager.cost_Delay_Boolean_Map.put(uuidString,false);
                PlayerManager.skill_Cool_Down_Boolean_Map.put(uuidString+"."+key,false);
                setCost(player,target, skillName, actionCustom, targetDistance);
                skillCD(uuidString, skillName, key, mainKey);
            }
        }
    }


    //施法**/
    public void setCost(Player player,LivingEntity inputTarget,String skillName, List<Map<String, String>> customLineConfigList,int targetDistance){

        String uuidString = player.getUniqueId().toString();

        String[] skillNameArray = skillName.split("\\|");
        if(skillNameArray.length == 2){

            //技能設定檔
            FileConfiguration skillConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_"+skillNameArray[0]+".yml");
            //技能施法時間
            int castTime = skillConfig.getInt("Skills."+skillNameArray[1]+".CastTime");
            //技能動作時間
            int castDelay = skillConfig.getInt("Skills."+skillNameArray[1]+".CastDelay");

            //技能是否需要目標
            boolean needTarget = skillConfig.getBoolean("Skills."+skillNameArray[1]+".NeedTarget");

            if(castTime == 0){

                castTime0(player, uuidString, castDelay, inputTarget, customLineConfigList);

            }else {

                castTime(player, uuidString, targetDistance, needTarget, castDelay, castTime, inputTarget, customLineConfigList);

            }

        }





    }
    //沒有施法時間
    public void castTime0(Player player, String uuidString, int castDelay, LivingEntity inputTarget, List<Map<String, String>> customLineConfigList){
        if(castDelay == 0){

            PlayerTrigger.onSkillList(player,inputTarget,customLineConfigList);

            PlayerManager.cost_Delay_Boolean_Map.put(uuidString,true);
        }else {

            PlayerTrigger.onSkillList(player,inputTarget,customLineConfigList);

            new BossBarSkill().setSkillBarProgress(1);
            PlayerManager.cost_Time_Map.put(uuidString, new BukkitRunnable() {
                double costCount = 1.0;
                @Override
                public void run() {
                    if(costCount >= 0.0){
                        new BossBarSkill().setSkillBarProgress(costCount);
                        costCount = costCount-0.1;
                    }else {
                        PlayerManager.cost_Delay_Boolean_Map.put(uuidString,true);
                        cancel();
                    }

                }
            });
            PlayerManager.cost_Time_Map.get(uuidString).runTaskTimer(cd,0, 2L *castDelay);
        }
    }
    //有施法時間
    public void castTime(Player player, String uuidString, int targetDistance, boolean needTarget, int castDelay, int castTime, LivingEntity inputTarget, List<Map<String, String>> customLineConfigList){

        //施法時設定
        FileConfiguration skillStatusConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_Status.yml");
        //聲音
        String cast_Sound = skillStatusConfig.getString("Skill_Cast_Time.Sound");
        //HD
        boolean hd_Enable = skillStatusConfig.getBoolean("Skill_Cast_Time.Hologram.HDEnable");
        //高度
        double cast_Hight = skillStatusConfig.getDouble("Skill_Cast_Time.Hologram.Hight");
        //物品
        boolean item_Enable = skillStatusConfig.getBoolean("Skill_Cast_Time.Hologram.ItemEnble");
        String cast_Item = skillStatusConfig.getString("Skill_Cast_Time.Hologram.Item");
        //內容
        boolean line_Enable = skillStatusConfig.getBoolean("Skill_Cast_Time.Hologram.LineEnble");

        String cast_Line = skillStatusConfig.getString("Skill_Cast_Time.Hologram.Line");
        if(cast_Sound != null){
            player.getWorld().playSound(player.getLocation(), cast_Sound, Enum.valueOf(SoundCategory.class , "PLAYERS"), 1, 1);
        }

        BossBarSkill2 bossBarSkill2 = PlayerManager.keyF_BossBarSkill_Map.get(uuidString);

        if(hd_Enable && inputTarget != null){
            hologram = HologramsAPI.createHologram(cd, inputTarget.getLocation().add(0,cast_Hight,0));

            if(item_Enable){
                ItemStack newItemStack = null;
                if (cast_Item != null) {
                    newItemStack = CustomItem2.valueOf(player,inputTarget, cast_Item, 1);
                }
                hologram.appendItemLine(newItemStack);
            }
            if(line_Enable){
                hologram.appendTextLine(cast_Line);
            }
        }

        PlayerManager.cost_Time_Map.put(uuidString, new BukkitRunnable() {
            //int count = PlayerDataMap.cost_Count_Map.get(uuidString);
            double costCount = 0.0;
            @Override
            public void run() {
                if(costCount >= 1.0){
                    cancel();
                    if(castDelay == 0){
                        LivingEntity target = LookTarget.getLivingTarget(player,targetDistance);
                        if(hologram != null){
                            hologram.delete();
                        }
                        if(needTarget){
                            if(target != null){
                                if(target == inputTarget){
                                    PlayerTrigger.onSkillList(player,target,customLineConfigList);
                                }

                            }
                        }else {
                            PlayerTrigger.onSkillList(player,target,customLineConfigList);
                        }

                        bossBarSkill2.setSkillBar2Progress(0);
                        PlayerManager.cost_Delay_Boolean_Map.put(uuidString,true);
                    }else {
                        LivingEntity target = LookTarget.getLivingTarget(player,targetDistance);
                        if(hologram != null){
                            hologram.delete();
                        }

                        if(needTarget){
                            if(target != null){
                                if(target == inputTarget){
                                    PlayerTrigger.onSkillList(player,target,customLineConfigList);
                                }
                            }
                        }else {
                            PlayerTrigger.onSkillList(player,target,customLineConfigList);
                        }


                        PlayerManager.cost_Time_Map.put(uuidString, new BukkitRunnable() {
                            double costCount = 1.0;
                            @Override
                            public void run() {
                                if(costCount >= 0.0){
                                    bossBarSkill2.setSkillBar2Progress(costCount);
                                    costCount = costCount-0.1;
                                }else {
                                    PlayerManager.cost_Delay_Boolean_Map.put(uuidString,true);
                                    cancel();
                                }

                            }
                        });
                        PlayerManager.cost_Time_Map.get(uuidString).runTaskTimer(cd,0, 2L *castDelay);
                    }

                }else {
                    if(hologram != null){
                        LivingEntity target = LookTarget.getLivingTarget(player,targetDistance);
                        if(target != null){
                            hologram.teleport(target.getLocation().add(0,0.6,0));
                        }else {
                            Location ll = new DirectionLocation().getLook(player,targetDistance);
                            hologram.teleport(ll.add(0,0.6,0));
                        }
                    }
                    bossBarSkill2.setSkillBar2Progress(costCount);
                    costCount = costCount+0.1;
                }

            }
        });
        PlayerManager.cost_Time_Map.get(uuidString).runTaskTimer(cd,0, 2L *castTime);
    }

    //技能CD
    public static void skillCD(String uuidString,String skillName, int key, int mainKey){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        if(PlayerManager.keyF_BossBarSkill_Map.get(uuidString) != null){
            FileConfiguration skillStatusConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_Status.yml");
            //技能獨立延遲覆蓋
            String cover12 = skillStatusConfig.getString("BossBar2.Skill_CD.12");
            String cover11 = skillStatusConfig.getString("BossBar2.Skill_CD.11");
            String cover10 = skillStatusConfig.getString("BossBar2.Skill_CD.10");
            String cover9 = skillStatusConfig.getString("BossBar2.Skill_CD.9");
            String cover8 = skillStatusConfig.getString("BossBar2.Skill_CD.8");
            String cover7 = skillStatusConfig.getString("BossBar2.Skill_CD.7");
            String cover6 = skillStatusConfig.getString("BossBar2.Skill_CD.6");
            String cover5 = skillStatusConfig.getString("BossBar2.Skill_CD.5");
            String cover4 = skillStatusConfig.getString("BossBar2.Skill_CD.4");
            String cover3 = skillStatusConfig.getString("BossBar2.Skill_CD.3");
            String cover2 = skillStatusConfig.getString("BossBar2.Skill_CD.2");
            String cover1 = skillStatusConfig.getString("BossBar2.Skill_CD.1");

            String bossBar2_Blank = skillStatusConfig.getString("BossBar2.BackGround_Show.Blank");

            String[] skillNameArray = skillName.split("\\|");
            if(skillNameArray.length == 2){
                //技能設定檔
                FileConfiguration skillConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_"+skillNameArray[0]+".yml");
                //技能獨立延遲時間
                int coolDown = skillConfig.getInt("Skills."+skillNameArray[1]+".CoolDown");
                //技能顯示
                String[] skillBarShow2 = PlayerManager.keyF_BossBarSkill_Map.get(uuidString).getSkillBarShow2();
                BossBarSkill2 bossBarSkill2 = PlayerManager.keyF_BossBarSkill_Map.get(uuidString);

                int bossKey = getBossKey(key, mainKey);

                if(PlayerManager.skill_Cool_Down_Run_Map.get(uuidString+"."+key) == null){
                    PlayerManager.skill_Cool_Down_Run_Map.put(uuidString+"."+key, new BukkitRunnable() {

                        int costCount = 12;
                        @Override
                        public void run() {


                            if(costCount == 12){

                                skillBarShow2[bossKey] = cover12;

                                bossBarSkill2.setSkillBar2Message(skillBarShow2, bossBar2_Blank);
                            }
                            if(costCount == 11){
                                skillBarShow2[bossKey] = cover11;

                                bossBarSkill2.setSkillBar2Message(skillBarShow2, bossBar2_Blank);
                            }
                            if(costCount == 10){
                                skillBarShow2[bossKey] = cover10;
                                bossBarSkill2.setSkillBar2Message(skillBarShow2, bossBar2_Blank);
                            }
                            if(costCount == 9){
                                skillBarShow2[bossKey] = cover9;
                                bossBarSkill2.setSkillBar2Message(skillBarShow2, bossBar2_Blank);
                            }
                            if(costCount == 8){
                                skillBarShow2[bossKey] = cover8;
                                bossBarSkill2.setSkillBar2Message(skillBarShow2, bossBar2_Blank);
                            }
                            if(costCount == 7){
                                skillBarShow2[bossKey] = cover7;
                                bossBarSkill2.setSkillBar2Message(skillBarShow2, bossBar2_Blank);
                            }
                            if(costCount == 6){
                                skillBarShow2[bossKey] = cover6;
                                bossBarSkill2.setSkillBar2Message(skillBarShow2, bossBar2_Blank);
                            }
                            if(costCount == 5){
                                skillBarShow2[bossKey] = cover5;
                                bossBarSkill2.setSkillBar2Message(skillBarShow2, bossBar2_Blank);
                            }
                            if(costCount == 4){
                                skillBarShow2[bossKey] = cover4;
                                bossBarSkill2.setSkillBar2Message(skillBarShow2, bossBar2_Blank);
                            }
                            if(costCount == 3){
                                skillBarShow2[bossKey] = cover3;
                                bossBarSkill2.setSkillBar2Message(skillBarShow2, bossBar2_Blank);
                            }
                            if(costCount == 2){
                                skillBarShow2[bossKey] = cover2;
                                bossBarSkill2.setSkillBar2Message(skillBarShow2, bossBar2_Blank);
                            }
                            if(costCount == 1){
                                skillBarShow2[bossKey] = cover1;
                                bossBarSkill2.setSkillBar2Message(skillBarShow2, bossBar2_Blank);
                                PlayerManager.skill_Cool_Down_Boolean_Map.put(uuidString+"."+key,true);
                                cancel();
                                PlayerManager.skill_Cool_Down_Run_Map.remove(uuidString+"."+key);
                            }
                            costCount--;

                        }
                    });
                    PlayerManager.skill_Cool_Down_Run_Map.get(uuidString+"."+key).runTaskTimer(cd,0, 2L *coolDown);
                }
            }

        }


    }

    public static int getBossKey(int key, int mainKey){
        int bossKey;
        if(key <= mainKey){
            bossKey = key - 1;
        }else {
            bossKey = key;
        }

        return bossKey;
    }

}
