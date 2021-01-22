package com.daxton.customdisplay.api.player.damageformula;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlayerDataMap;
import com.daxton.customdisplay.task.action.list.SendBossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class FormulaDelay {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private double attackSpeed;

    public FormulaDelay(){

    }
    /**技能CD**/
    public void skillCD(Player player, int key){
        String uuidString = player.getUniqueId().toString();
        StringBuilder newString = new StringBuilder(new SendBossBar().getSkillMessage());


        PlayerDataMap.skill_Delay_Time_Map.put(uuidString, new BukkitRunnable() {
            int costCount = 12;
            int start = 0;
            int end = 1;
            @Override
            public void run() {


                    start = (key)*2;
                    end = start+1;


                if(costCount == 12){
                    new SendBossBar().setSkillBar(newString.replace(start,end,"䃘").toString());
                }
                if(costCount == 11){
                    new SendBossBar().setSkillBar(newString.replace(start,end,"䃗").toString());
                }
                if(costCount == 10){
                    new SendBossBar().setSkillBar(newString.replace(start,end,"䃖").toString());
                }
                if(costCount == 9){
                    new SendBossBar().setSkillBar(newString.replace(start,end,"䃕").toString());
                }
                if(costCount == 8){
                    new SendBossBar().setSkillBar(newString.replace(start,end,"䃔").toString());
                }
                if(costCount == 7){
                    new SendBossBar().setSkillBar(newString.replace(start,end,"䃓").toString());
                }
                if(costCount == 6){
                    new SendBossBar().setSkillBar(newString.replace(start,end,"䃒").toString());
                }
                if(costCount == 5){
                    new SendBossBar().setSkillBar(newString.replace(start,end,"䃑").toString());
                }
                if(costCount == 4){
                    new SendBossBar().setSkillBar(newString.replace(start,end,"䃐").toString());
                }
                if(costCount == 3){
                    new SendBossBar().setSkillBar(newString.replace(start,end,"䃏").toString());
                }
                if(costCount == 2){
                    new SendBossBar().setSkillBar(newString.replace(start,end,"䃎").toString());
                }
                if(costCount == 1){
                    new SendBossBar().setSkillBar(newString.replace(start,end,"䃍").toString());
                    PlayerDataMap.skill_Delay_Boolean_Map.put(uuidString+"."+key,true);
                    cancel();
                }
                costCount--;

            }
        });
        PlayerDataMap.skill_Delay_Time_Map.get(uuidString).runTaskTimer(cd,0,2);


    }

    /**施法**/
    public boolean setCost(Player player, List<String> action){
        boolean attack_speed = false;
        String uuidString = player.getUniqueId().toString();
        PlayerDataMap.cost_Time_Map.put(uuidString, new BukkitRunnable() {
            //int count = PlayerDataMap.cost_Count_Map.get(uuidString);
            double costCount = 0.0;
            @Override
            public void run() {
                if(costCount >= 1.0){
                    new PlayerTrigger(player).onGuiClick(player,action);

                    cancel();
                    PlayerDataMap.cost_Time_Map.put(uuidString, new BukkitRunnable() {
                        double costCount = 1.0;
                        @Override
                        public void run() {
                            if(costCount >= 0.0){
                                new SendBossBar().setSkillBarProgress(costCount);
                                costCount = costCount-0.1;
                            }else {
                                PlayerDataMap.cost_Delay_Boolean_Map.put(uuidString,true);
                                cancel();
                            }

                        }
                    });
                    PlayerDataMap.cost_Time_Map.get(uuidString).runTaskTimer(cd,0,2);
                }else {

                    new SendBossBar().setSkillBarProgress(costCount);
                    costCount = costCount+0.1;
                }

            }
        });
        PlayerDataMap.cost_Time_Map.get(uuidString).runTaskTimer(cd,0,2);
        return attack_speed;
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
