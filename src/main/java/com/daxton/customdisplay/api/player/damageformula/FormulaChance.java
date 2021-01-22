package com.daxton.customdisplay.api.player.damageformula;

import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class FormulaChance {

    public FormulaChance(){

    }

    /**格檔公式**/
    public boolean setBlockRate(Player player, LivingEntity target){
        boolean block = false;

        int randomNumber = 100;
        int  r = (int)(Math.random()*randomNumber);
        String blockRateString = "0";
        if(target instanceof Player){
            blockRateString = PlayerDataMap.core_Formula_Map.get("Block_Rate_Player_Player");
        }else {
            blockRateString = PlayerDataMap.core_Formula_Map.get("Block_Rate_Player_Other");
        }
        blockRateString = new ConversionMain().valueOf(player,target,blockRateString);

        int blockRate = 0;
        try {
            double number = Arithmetic.eval(blockRateString);
            String numberDec = new NumberUtil(number,"#").getDecimalString();
            blockRate = Integer.valueOf(numberDec);
        }catch (Exception exception){
            blockRate = 0;
        }

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

        return block;
    }


    /**迴避公式**/
    public boolean setDodgeRate(Player player,LivingEntity target){
        boolean dodge = false;

        int randomNumber = 100;
        int  r = (int)(Math.random()*randomNumber);
        String dodgeRateString = "0";
        if(target instanceof Player){
            dodgeRateString = PlayerDataMap.core_Formula_Map.get("Dodge_Rate_Player_Player");
        }else {
            dodgeRateString = PlayerDataMap.core_Formula_Map.get("Dodge_Rate_Player_Other");
        }
        dodgeRateString = new ConversionMain().valueOf(player,target,dodgeRateString);

        int dodgeRate = 0;
        try {
            double number = Arithmetic.eval(dodgeRateString);
            String numberDec = new NumberUtil(number,"#").getDecimalString();
            dodgeRate = Integer.valueOf(numberDec);
        }catch (Exception exception){
            dodgeRate = 0;
        }

        int max_chance = 95;
        if(dodgeRate > max_chance){
            dodgeRate = max_chance;
        }
        if(dodgeRate < 0){
            dodgeRate = 0;
        }

        if(r<dodgeRate){
            dodge = true;
        }

        return dodge;
    }

    /**爆擊率公式**/
    public boolean setCritChange(Player player,LivingEntity target){
        boolean crit = false;
        int randomNumber2 = 100;
        int  r2 = (int)(Math.random()*randomNumber2);
        String crit_chanceString = "0";
        if(target instanceof Player){
            crit_chanceString = PlayerDataMap.core_Formula_Map.get("Critical_Strike_Chance_Player_Player");
        }else {
            crit_chanceString = PlayerDataMap.core_Formula_Map.get("Critical_Strike_Chance_Player_Other");
        }
        crit_chanceString = new ConversionMain().valueOf(player,target,crit_chanceString);
        int crit_chance = 0;
        try {
            double number = Arithmetic.eval(crit_chanceString);
            String numberDec = new NumberUtil(number,"#").getDecimalString();
            crit_chance = Integer.valueOf(numberDec);
        }catch (Exception exception){
            crit_chance = 0;
        }
        int max_chance = 95;
        if(crit_chance > max_chance){
            crit_chance = max_chance;
        }
        if(r2<crit_chance){
            crit = true;
        }
        return crit;
    }

    /**命中公式**/
    public boolean setHitRate(Player player,LivingEntity target){
        boolean hit = false;

        int randomNumber = 100;
        int  r = (int)(Math.random()*randomNumber);
        String hitRateString = "0";
        if(target instanceof Player){
            hitRateString = PlayerDataMap.core_Formula_Map.get("Hit_Rate_Player_Player");
        }else {
            hitRateString = PlayerDataMap.core_Formula_Map.get("Hit_Rate_Player_Other");
        }
        hitRateString = new ConversionMain().valueOf(player,target,hitRateString);
        int hitRate = 0;
        try {
            double number = Arithmetic.eval(hitRateString);
            String numberDec = new NumberUtil(number,"#").getDecimalString();
            hitRate = Integer.valueOf(numberDec);
        }catch (Exception exception){
            hitRate = 0;
        }
        if(hitRate < 0){
            hitRate = 0;
        }
        if(r<hitRate){
            hit = true;
        }
        return hit;
    }

}
