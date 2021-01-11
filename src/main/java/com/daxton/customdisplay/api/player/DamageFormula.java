package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DamageFormula {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public DamageFormula(){

    }

    /**攻擊速度**/
    public boolean setAttackSpeed(Player player, LivingEntity target, FileConfiguration customCoreConfig, String uuidString){
        boolean attack_speed = false;
        int count = PlayerDataMap.attack_Count_Map.get(uuidString);
        int first = setCount(player,target,customCoreConfig)-1;
        if(count > first){
            PlayerDataMap.attack_Speed_Map.put(uuidString, new BukkitRunnable() {
                int count = PlayerDataMap.attack_Count_Map.get(uuidString);
                @Override
                public void run() {

                    count--;
                    PlayerDataMap.attack_Count_Map.put(uuidString,count);
                    //player.sendMessage("數字: "+count);
                    if(count == 0){
                        PlayerDataMap.attack_Count_Map.put(uuidString,setCount(player,target,customCoreConfig));
                        count = 4;
                        cancel();
                    }
                }
            });
            PlayerDataMap.attack_Speed_Map.get(uuidString).runTaskTimer(cd,0,2);
        }else {
            attack_speed = true;
        }

        return attack_speed;
    }

    /**攻擊速度設定**/
    public int setCount(Player player,LivingEntity target,FileConfiguration customCoreConfig){
        int  attackSpeed = 10;
        String attackSpeedString = customCoreConfig.getString("Formula.Attack_Speed.Speed");
        attackSpeedString = new StringConversion(player,target,attackSpeedString,"Character").valueConv();

        try {
            double number = Arithmetic.eval(attackSpeedString);
            String numberDec = new NumberUtil(number,"#").getDecimalString();
            attackSpeed = Integer.valueOf(numberDec);
        }catch (Exception exception){
            attackSpeed = 10;
        }
        return attackSpeed;
    }

    /**暴擊傷害**/
    public double setCritPower(Player player,LivingEntity target,FileConfiguration customCoreConfig){
        double crit_power = 0;
        String crit_powerString = "0";
        if(target instanceof Player){
            crit_powerString = customCoreConfig.getString("Formula.Critical_Strike_Power.Player_Player");
        }else {
            crit_powerString = customCoreConfig.getString("Formula.Critical_Strike_Power.Player_Other");
        }
        crit_powerString = new StringConversion(player,target,crit_powerString,"Character").valueConv();

        try {
            double number = Arithmetic.eval(crit_powerString);
            crit_power = Double.valueOf(number);
        }catch (Exception exception){
            crit_power = 0;
        }
        double max_power = customCoreConfig.getDouble("Formula.Critical_Strike_Power.Max_Power");
        if(crit_power > max_power){
            crit_power = max_power;
        }

        return crit_power;
    }


    /**爆擊率公式**/
    public boolean setCritChange(Player player,LivingEntity target,FileConfiguration customCoreConfig){
        boolean crit = false;
        int randomNumber2 = customCoreConfig.getInt("Formula.Critical_Strike_Chance.RrndomNumber");
        int  r2 = (int)(Math.random()*randomNumber2);
        String crit_chanceString = "0";
        if(target instanceof Player){
            crit_chanceString = customCoreConfig.getString("Formula.Critical_Strike_Chance.Player_Player");
        }else {
            crit_chanceString = customCoreConfig.getString("Formula.Critical_Strike_Chance.Player_Other");
        }
        crit_chanceString = new StringConversion(player,target,crit_chanceString,"Character").valueConv();
        int crit_chance = 0;
        try {
            double number = Arithmetic.eval(crit_chanceString);
            String numberDec = new NumberUtil(number,"#").getDecimalString();
            crit_chance = Integer.valueOf(numberDec);
        }catch (Exception exception){
            crit_chance = 0;
        }
        int max_chance = customCoreConfig.getInt("Formula.Critical_Strike_Chance.Max_Chance");
        if(crit_chance > max_chance){
            crit_chance = max_chance;
        }

        if(r2<crit_chance){
            crit = true;
        }
        return crit;
    }

    /**命中公式**/
    public boolean setHitRate(Player player,LivingEntity target,FileConfiguration customCoreConfig){
        boolean hit = false;

        int randomNumber = customCoreConfig.getInt("Formula.Hit_Rate.RrndomNumber");
        int  r = (int)(Math.random()*randomNumber);
        String hitRateString = "0";
        if(target instanceof Player){
            hitRateString = customCoreConfig.getString("Formula.Hit_Rate.Player_Player");
        }else {
            hitRateString = customCoreConfig.getString("Formula.Hit_Rate.Player_Other");
        }
        hitRateString = new StringConversion(player,target,hitRateString,"Character").valueConv();
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

    /**迴避公式**/
    public boolean setDodgeRate(Player player,LivingEntity target,FileConfiguration customCoreConfig){
        boolean dodge = false;

        int randomNumber = customCoreConfig.getInt("Formula.Dodge_Rate.RrndomNumber");
        int  r = (int)(Math.random()*randomNumber);
        String dodgeRateString = "0";
        if(target instanceof Player){
            dodgeRateString = customCoreConfig.getString("Formula.Dodge_Rate.Player_Player");
        }else {
            dodgeRateString = customCoreConfig.getString("Formula.Dodge_Rate.Player_Other");
        }
        dodgeRateString = new StringConversion(player,target,dodgeRateString,"Character").valueConv();

        int dodgeRate = 0;
        try {
            double number = Arithmetic.eval(dodgeRateString);
            String numberDec = new NumberUtil(number,"#").getDecimalString();
            dodgeRate = Integer.valueOf(numberDec);
        }catch (Exception exception){
            dodgeRate = 0;
        }

        int max_chance = customCoreConfig.getInt("Formula.Dodge_Rate.Max_Chance");
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

    /**格檔公式**/
    public boolean setBlockRate(Player player,LivingEntity target,FileConfiguration customCoreConfig){
        boolean block = false;

        int randomNumber = customCoreConfig.getInt("Formula.Block_Rate.RrndomNumber");
        int  r = (int)(Math.random()*randomNumber);
        String blockRateString = "0";
        if(target instanceof Player){
            blockRateString = customCoreConfig.getString("Formula.Block_Rate.Player_Player");
        }else {
            blockRateString = customCoreConfig.getString("Formula.Block_Rate.Player_Other");
        }
        blockRateString = new StringConversion(player,target,blockRateString,"Character").valueConv();

        int blockRate = 0;
        try {
            double number = Arithmetic.eval(blockRateString);
            String numberDec = new NumberUtil(number,"#").getDecimalString();
            blockRate = Integer.valueOf(numberDec);
        }catch (Exception exception){
            blockRate = 0;
        }

        int max_chance = customCoreConfig.getInt("Formula.Block_Rate.Max_Chance");
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

    /**近距離暴擊物理傷害公式**/
    public double setMeleePhysicalCriticalDamageNumber(Player player, LivingEntity target, FileConfiguration customCoreConfig){

        double attackNumber = 0;

        String plysical = "0";
        if(target instanceof Player){
            plysical = customCoreConfig.getString("Formula.Melee_Physics_Critical_Strike_Power.Player_Player");
        }else {
            plysical = customCoreConfig.getString("Formula.Melee_Physics_Critical_Strike_Power.Player_Other");
        }

        plysical = new StringConversion(player,target,plysical,"Character").valueConv();

        try {
            double number = Arithmetic.eval(plysical);
            attackNumber = Double.valueOf(number);
        }catch (Exception exception){
            attackNumber = 0;
        }
        return attackNumber;
    }

    /**近距離物理傷害公式**/
    public double setMeleePhysicalDamageNumber(Player player, LivingEntity target, FileConfiguration customCoreConfig){

        double attackNumber = 0;

        String plysical = "0";
        if(target instanceof Player){
            plysical = customCoreConfig.getString("Formula.Melee_Physics.Player_Player");
        }else {
            plysical = customCoreConfig.getString("Formula.Melee_Physics.Player_Other");
        }

        plysical = new StringConversion(player,target,plysical,"Character").valueConv();

        try {
            double number = Arithmetic.eval(plysical);

            attackNumber = Double.valueOf(number);
        }catch (Exception exception){
            attackNumber = 0;
        }
        return attackNumber;
    }


    /**弓箭物理暴擊傷害公式**/
    public double setRangePhysicalCriticalDamageNumber(Player player,LivingEntity target,FileConfiguration customCoreConfig){

        double attackNumber = 0;

        String plysical = "0";
        if(target instanceof Player){
            plysical = customCoreConfig.getString("Formula.Range_Physics_Critical_Strike_Power.Player_Player");
        }else {
            plysical = customCoreConfig.getString("Formula.Range_Physics_Critical_Strike_Power.Player_Other");
        }
        plysical = new StringConversion(player,target,plysical,"Character").valueConv();
        try {
            double number = Arithmetic.eval(plysical);
            attackNumber = Double.valueOf(number);
        }catch (Exception exception){
            attackNumber = 0;
        }
        return attackNumber;
    }

    /**弓箭物理傷害公式**/
    public double setRangePhysicalDamageNumber(Player player,LivingEntity target,FileConfiguration customCoreConfig){

        double attackNumber = 0;

        String plysical = "0";
        if(target instanceof Player){
            plysical = customCoreConfig.getString("Formula.Range_Physics.Player_Player");
        }else {
            plysical = customCoreConfig.getString("Formula.Range_Physics.Player_Other");
        }
        plysical = new StringConversion(player,target,plysical,"Character").valueConv();
        try {
            double number = Arithmetic.eval(plysical);
            attackNumber = Double.valueOf(number);
        }catch (Exception exception){
            attackNumber = 0;
        }
        return attackNumber;
    }

    /**魔法傷害公式**/
    public double setMagicDamageNumber(Player player, LivingEntity target, FileConfiguration customCoreConfig){

        double attackNumber = 0;

        String plysical = "0";
        if(target instanceof Player){
            plysical = customCoreConfig.getString("Formula.Magic_Attack.Player_Player");
        }else {
            plysical = customCoreConfig.getString("Formula.Magic_Attack.Player_Other");
        }
        plysical = new StringConversion(player,target,plysical,"Character").valueConv();
        try {
            double number = Arithmetic.eval(plysical);

            attackNumber = Double.valueOf(number);
        }catch (Exception exception){
            attackNumber = 0;
        }
        return attackNumber;
    }


}
