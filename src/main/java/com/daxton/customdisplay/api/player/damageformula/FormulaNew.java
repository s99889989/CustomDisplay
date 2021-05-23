package com.daxton.customdisplay.api.player.damageformula;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.other.StringConversion;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FormulaNew {

    //攻擊速度
    public static void AttackSpeed(LivingEntity self, LivingEntity target, PlayerData2 playerData){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        playerData.attackSpeed = false;
        FileConfiguration coreConfig = ConfigMapManager.getFileConfigurationMap().get("Class_CustomCore.yml");
        String attackSpeedString = coreConfig.getString("CoreAttribute.Attack_Speed.formula");
        int attackSpeed = StringConversion.getInt(self, target, 0, attackSpeedString);
        attackSpeed = attackSpeed*2;
        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                playerData.attackSpeed = true;
            }
        };
        bukkitRunnable.runTaskLater(cd, attackSpeed);
    }

    //魔法攻擊
    public static double MagicAttack(LivingEntity self, LivingEntity target){
        double mAttack = 0;
        FileConfiguration coreConfig = ConfigMapManager.getFileConfigurationMap().get("Class_CustomCore.yml");

        String mAtt = coreConfig.getString("CoreAttribute.Magic_Attack_Player_Other.formula");
        if(target instanceof Player){
            mAtt = coreConfig.getString("CoreAttribute.Magic_Attack_Player_Player.formula");
        }
        if(mAtt != null){
            mAttack = StringConversion.getDouble(self, target, 0, mAtt);
        }

        return mAttack;
    }

    //近距離攻擊
    public static double MeleeAttack(LivingEntity self, LivingEntity target){
        double mAttack = 0;
        FileConfiguration coreConfig = ConfigMapManager.getFileConfigurationMap().get("Class_CustomCore.yml");

        String mAtt = coreConfig.getString("CoreAttribute.Melee_Physics_Player_Other.formula");
        if(target instanceof Player){
            mAtt = coreConfig.getString("CoreAttribute.Melee_Physics_Player_Player.formula");
        }
        if(mAtt != null){
            mAttack = StringConversion.getDouble(self, target, 0, mAtt);
        }


        return mAttack;
    }

    //遠距離攻擊
    public static double RangeAttack(LivingEntity self, LivingEntity target){
        double mAttack = 0;
        FileConfiguration coreConfig = ConfigMapManager.getFileConfigurationMap().get("Class_CustomCore.yml");

        String mAtt = coreConfig.getString("CoreAttribute.Range_Physics_Player_Other.formula");
        if(target instanceof Player){
            mAtt = coreConfig.getString("CoreAttribute.Range_Physics_Player_Player.formula");
        }
        if(mAtt != null){
            mAttack = StringConversion.getDouble(self, target, 0, mAtt);
        }


        return mAttack;
    }
    //箭矢攻擊(爆擊)
    public static double CriticalRangeAttack(LivingEntity self, LivingEntity target){
        double mAttack = 0;
        FileConfiguration coreConfig = ConfigMapManager.getFileConfigurationMap().get("Class_CustomCore.yml");

        String mAtt = coreConfig.getString("CoreAttribute.Range_Physics_Critical_Strike_Power_Player_Other.formula");
        if(target instanceof Player){
            mAtt = coreConfig.getString("CoreAttribute.Range_Physics_Critical_Strike_Power_Player_Player.formula");
        }
        if(mAtt != null){
            mAttack = StringConversion.getDouble(self, target, 0, mAtt);
        }


        return mAttack;
    }
    //普通攻擊(爆擊)
    public static double CriticalMeleeAttack(LivingEntity self, LivingEntity target){
        double mAttack = 0;
        FileConfiguration coreConfig = ConfigMapManager.getFileConfigurationMap().get("Class_CustomCore.yml");

        String mAtt = coreConfig.getString("CoreAttribute.Melee_Physics_Critical_Strike_Power_Player_Other.formula");
        if(target instanceof Player){
            mAtt = coreConfig.getString("CoreAttribute.Melee_Physics_Critical_Strike_Power_Player_Player.formula");
        }
        if(mAtt != null){
            mAttack = StringConversion.getDouble(self, target, 0, mAtt);
        }

        return mAttack;
    }

    //命中公式
    public static boolean HitRate(LivingEntity self, LivingEntity target){
        boolean hit = false;

        FileConfiguration coreConfig = ConfigMapManager.getFileConfigurationMap().get("Class_CustomCore.yml");

        int randomNumber = 100;
        int  r = (int)(Math.random()*randomNumber);
        String hitRateString = coreConfig.getString("CoreAttribute.Hit_Rate_Player_Other.formula");
        if(target instanceof Player){
            hitRateString = coreConfig.getString("CoreAttribute.Hit_Rate_Player_Player.formula");
        }
        if(hitRateString != null){
            double hitRate = StringConversion.getDouble(self,target, 0,hitRateString);

            //CustomDisplay.getCustomDisplay().getLogger().info(hitRateString+" : "+hitRate+" : "+StringConversion.getString(self,target,hitRateString));

            if(hitRate < 0){
                hitRate = 0;
            }
            if(r<hitRate){
                hit = true;
            }
        }

        return hit;
    }

    //格檔公式
    public static boolean BlockRate(LivingEntity self, LivingEntity target){
        boolean block = false;

        FileConfiguration coreConfig = ConfigMapManager.getFileConfigurationMap().get("Class_CustomCore.yml");

        int randomNumber = 100;
        int  r = (int)(Math.random()*randomNumber);
        String blockRateString = coreConfig.getString("CoreAttribute.Block_Rate_Player_Other.formula");
        if(target instanceof Player){
            blockRateString = coreConfig.getString("CoreAttribute.Block_Rate_Player_Player.formula");
        }
        if(blockRateString != null){

            double blockRate = StringConversion.getDouble(self,target, 0, blockRateString);

            int max_chance = 95;
            if(blockRate > max_chance){
                blockRate = max_chance;
            }
            if(blockRate < 0){
                blockRate = 0;
            }

            if(r<blockRate){
                block = true;
            }

        }

        return block;
    }

    //爆擊率公式
    public static boolean CritChange(LivingEntity self, LivingEntity target){
        boolean crit = false;

        FileConfiguration coreConfig = ConfigMapManager.getFileConfigurationMap().get("Class_CustomCore.yml");

        int randomNumber2 = 100;
        int  r2 = (int)(Math.random()*randomNumber2);
        String crit_chanceString = coreConfig.getString("CoreAttribute.Critical_Strike_Chance_Player_Other.formula");
        if(target instanceof Player){
            crit_chanceString = coreConfig.getString("CoreAttribute.Critical_Strike_Chance_Player_Player.formula");
        }
        if(crit_chanceString != null){

            double crit_chance = StringConversion.getDouble(self,target, 0, crit_chanceString);

            int max_chance = 95;
            if(crit_chance > max_chance){
                crit_chance = max_chance;
            }
            if(r2<crit_chance){
                crit = true;
            }

        }


        return crit;
    }

}
