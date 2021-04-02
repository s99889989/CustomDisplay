package com.daxton.customdisplay.api.player.damageformula;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.entity.LookTarget;
import com.daxton.customdisplay.api.item.CustomItem;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.profession.BossBarSkill;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.SoundCategory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class FormulaDelay {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private double attackSpeed;

    private Hologram hologram;

    public FormulaDelay(){

    }
    /**技能CD**/
    public void skillCD(Player player,String skillName, int key){
        String uuidString = player.getUniqueId().toString();
        StringBuilder newString = new StringBuilder(new BossBarSkill().getSkillMessage());
        /**技能設定檔**/
        FileConfiguration skillConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_Skills_"+skillName+".yml");
        /**技能獨立延遲時間**/
        int coolDown = skillConfig.getInt(skillName+".CoolDown");

        FileConfiguration skillStatusConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_Status.yml");
        /**技能獨立延遲覆蓋**/
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

        if(PlayerDataMap.skill_Cool_Down_Run_Map.get(uuidString+"."+key) == null){
            PlayerDataMap.skill_Cool_Down_Run_Map.put(uuidString+"."+key, new BukkitRunnable() {
                int costCount = 12;
                int start = 0;
                int end = 1;
                @Override
                public void run() {


                    start = (key)*2;
                    end = start+1;


                    if(costCount == 12){
                        new BossBarSkill().setSkillBar(newString.replace(start,end,cover12).toString());
                    }
                    if(costCount == 11){
                        new BossBarSkill().setSkillBar(newString.replace(start,end,cover11).toString());
                    }
                    if(costCount == 10){
                        new BossBarSkill().setSkillBar(newString.replace(start,end,cover10).toString());
                    }
                    if(costCount == 9){
                        new BossBarSkill().setSkillBar(newString.replace(start,end,cover9).toString());
                    }
                    if(costCount == 8){
                        new BossBarSkill().setSkillBar(newString.replace(start,end,cover8).toString());
                    }
                    if(costCount == 7){
                        new BossBarSkill().setSkillBar(newString.replace(start,end,cover7).toString());
                    }
                    if(costCount == 6){
                        new BossBarSkill().setSkillBar(newString.replace(start,end,cover6).toString());
                    }
                    if(costCount == 5){
                        new BossBarSkill().setSkillBar(newString.replace(start,end,cover5).toString());
                    }
                    if(costCount == 4){
                        new BossBarSkill().setSkillBar(newString.replace(start,end,cover4).toString());
                    }
                    if(costCount == 3){
                        new BossBarSkill().setSkillBar(newString.replace(start,end,cover3).toString());
                    }
                    if(costCount == 2){
                        new BossBarSkill().setSkillBar(newString.replace(start,end,cover2).toString());
                    }
                    if(costCount == 1){
                        new BossBarSkill().setSkillBar(newString.replace(start,end,cover1).toString());
                        PlayerDataMap.skill_Cool_Down_Boolean_Map.put(uuidString+"."+key,true);
                        PlayerDataMap.skill_Cool_Down_Run_Map.remove(uuidString+"."+key);
                        cancel();
                    }
                    costCount--;

                }
            });
            PlayerDataMap.skill_Cool_Down_Run_Map.get(uuidString+"."+key).runTaskTimer(cd,0,2*coolDown);
        }



    }

    /**施法**/
    public void setCost(Player player,LivingEntity inputTarget,String skillName, List<String> action,int targetDistance){

        String uuidString = player.getUniqueId().toString();
        /**技能設定檔**/
        FileConfiguration skillConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_Skills_"+skillName+".yml");
        /**技能施法時間**/
        int castTime = skillConfig.getInt(skillName+".CastTime");
        /**技能動作時間**/
        int castDelay = skillConfig.getInt(skillName+".CastDelay");

        /**施法時設定**/
        FileConfiguration skillStatusConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_Status.yml");
        /**聲音**/
        String cast_Sound = skillStatusConfig.getString("Skill_Cast_Time.Sound");
        /**HD**/
        boolean hd_Enable = skillStatusConfig.getBoolean("Skill_Cast_Time.Hologram.HDEnable");
        /**高度**/
        double cast_Hight = skillStatusConfig.getDouble("Skill_Cast_Time.Hologram.Hight");
        /**物品**/
        boolean item_Enable = skillStatusConfig.getBoolean("Skill_Cast_Time.Hologram.ItemEnble");
        String cast_Item = skillStatusConfig.getString("Skill_Cast_Time.Hologram.Item");
        /**內容**/
        boolean line_Enable = skillStatusConfig.getBoolean("Skill_Cast_Time.Hologram.LineEnble");

        String cast_Line = skillStatusConfig.getString("Skill_Cast_Time.Hologram.Line");

        /**技能是否需要目標**/
        boolean needTarget = skillConfig.getBoolean(skillName+".NeedTarget");

        if(castTime == 0){
            if(castDelay == 0){

                new PlayerTrigger(player).onSkill(player,inputTarget,action);

                PlayerDataMap.cost_Delay_Boolean_Map.put(uuidString,true);
            }else {

                new PlayerTrigger(player).onSkill(player,inputTarget,action);

                new BossBarSkill().setSkillBarProgress(1);
                PlayerDataMap.cost_Time_Map.put(uuidString, new BukkitRunnable() {
                    double costCount = 1.0;
                    @Override
                    public void run() {
                        if(costCount >= 0.0){
                            new BossBarSkill().setSkillBarProgress(costCount);
                            costCount = costCount-0.1;
                        }else {
                            PlayerDataMap.cost_Delay_Boolean_Map.put(uuidString,true);
                            cancel();
                        }

                    }
                });
                PlayerDataMap.cost_Time_Map.get(uuidString).runTaskTimer(cd,0,2*castDelay);
            }

        }else {

            player.getWorld().playSound(player.getLocation(), cast_Sound, Enum.valueOf(SoundCategory.class , "PLAYERS"), 1, 1);

            if(inputTarget != null){
                hologram = HologramsAPI.createHologram(cd, inputTarget.getLocation().add(0,cast_Hight,0));

                if(item_Enable){
                    ItemStack newItemStack = CustomItem.valueOf(player,inputTarget, cast_Item, 1);
                    hologram.appendItemLine(newItemStack);
                }
                if(line_Enable){
                    hologram.appendTextLine(cast_Line);
                }
            }


            PlayerDataMap.cost_Time_Map.put(uuidString, new BukkitRunnable() {
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

                            if(needTarget && inputTarget != null && target == inputTarget){
                                new PlayerTrigger(player).onSkill(player,inputTarget,action);
                            }else {
                                new PlayerTrigger(player).onSkill(player,inputTarget,action);
                            }
                            new BossBarSkill().setSkillBarProgress(0);
                            PlayerDataMap.cost_Delay_Boolean_Map.put(uuidString,true);
                        }else {
                            LivingEntity target = LookTarget.getLivingTarget(player,targetDistance);
                            if(hologram != null){
                                hologram.delete();
                            }

                            if(inputTarget != null && target == inputTarget){
                                new PlayerTrigger(player).onSkill(player,inputTarget,action);
                            }
                            PlayerDataMap.cost_Time_Map.put(uuidString, new BukkitRunnable() {
                                double costCount = 1.0;
                                @Override
                                public void run() {
                                    if(costCount >= 0.0){
                                        new BossBarSkill().setSkillBarProgress(costCount);
                                        costCount = costCount-0.1;
                                    }else {
                                        PlayerDataMap.cost_Delay_Boolean_Map.put(uuidString,true);
                                        cancel();
                                    }

                                }
                            });
                            PlayerDataMap.cost_Time_Map.get(uuidString).runTaskTimer(cd,0,2*castDelay);
                        }

                    }else {
                        if(hologram != null){
                            hologram.teleport(inputTarget.getLocation().add(0,0.6,0));
                        }

                        new BossBarSkill().setSkillBarProgress(costCount);
                        costCount = costCount+0.1;
                    }

                }
            });
            PlayerDataMap.cost_Time_Map.get(uuidString).runTaskTimer(cd,0,2*castTime);
        }



    }

    /**攻擊速度**/
    public void setAttackSpeed(Player player, LivingEntity target, String uuidString){


        if(PlayerDataMap.attack_Boolean3_Map.get(uuidString) == null){
            PlayerDataMap.attack_Boolean3_Map.put(uuidString,true);
        }

        if(PlayerDataMap.attack_Boolean3_Map.get(uuidString)){

            PlayerDataMap.attack_Boolean3_Map.put(uuidString,false);

            String tickRunString = PlayerDataMap.core_Formula_Map.get("Attack_Speed");
            tickRunString = new ConversionMain().valueOf(player,target,tickRunString);

            try {
                attackSpeed = Double.valueOf(tickRunString);
            }catch (Exception exception){
                attackSpeed = 10;
            }

            PlayerDataMap.attack_Speed_Map.put(uuidString, new BukkitRunnable() {

                int tickRun = 0;

                @Override
                public void run() {
                    tickRun++;
                    if(tickRun >= attackSpeed){

                        PlayerDataMap.attack_Boolean3_Map.put(uuidString,true);
                        PlayerDataMap.attack_Boolean2_Map.put(uuidString,true);
                        PlayerDataMap.attack_Boolean_Map.put(uuidString,false);
                        cancel();
                    }
                }
            });
            PlayerDataMap.attack_Speed_Map.get(uuidString).runTaskTimer(cd,0,2);
        }


    }


}
