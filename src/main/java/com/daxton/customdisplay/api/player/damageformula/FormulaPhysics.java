package com.daxton.customdisplay.api.player.damageformula;

import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.manager.PlayerManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class FormulaPhysics {

    public FormulaPhysics(){

    }

    /**近距離暴擊物理傷害公式**/
    public double setMeleePhysicalCriticalDamageNumber(Player player, LivingEntity target){

        double attackNumber = 0;

        String plysical = "0";
        if(target instanceof Player){
            plysical = PlayerManager.core_Formula_Map.get("Melee_Physics_Critical_Strike_Power_Player_Player");
        }else {
            plysical = PlayerManager.core_Formula_Map.get("Melee_Physics_Critical_Strike_Power_Player_Other");
        }

        plysical = new ConversionMain().valueOf(player,target,plysical);

        try {
            double number = Arithmetic.eval(plysical);
            attackNumber = Double.valueOf(number);
        }catch (Exception exception){
            attackNumber = 0;
        }
        return attackNumber;
    }

    /**近距離物理傷害公式**/
    public double setMeleePhysicalDamageNumber(Player player, LivingEntity target){

        double attackNumber = 0;

        String plysical = "0";
        if(target instanceof Player){
            plysical = PlayerManager.core_Formula_Map.get("Melee_Physics_Player_Player");
        }else {
            plysical = PlayerManager.core_Formula_Map.get("Melee_Physics_Player_Other");
        }

        plysical = new ConversionMain().valueOf(player,target,plysical);

        try {
            double number = Arithmetic.eval(plysical);

            attackNumber = Double.valueOf(number);
        }catch (Exception exception){
            attackNumber = 0;
        }
        return attackNumber;
    }


    /**弓箭物理暴擊傷害公式**/
    public double setRangePhysicalCriticalDamageNumber(Player player,LivingEntity target){

        double attackNumber = 0;

        String plysical = "0";
        if(target instanceof Player){
            plysical = PlayerManager.core_Formula_Map.get("Range_Physics_Critical_Strike_Power_Player_Player");
        }else {
            plysical = PlayerManager.core_Formula_Map.get("Range_Physics_Critical_Strike_Power_Player_Other");
        }
        plysical = new ConversionMain().valueOf(player,target,plysical);
        try {
            double number = Arithmetic.eval(plysical);
            attackNumber = Double.valueOf(number);
        }catch (Exception exception){
            attackNumber = 0;
        }
        return attackNumber;
    }

    /**弓箭物理傷害公式**/
    public double setRangePhysicalDamageNumber(Player player,LivingEntity target){

        double attackNumber = 0;

        String plysical = "0";
        if(target instanceof Player){
            plysical = PlayerManager.core_Formula_Map.get("Range_Physics_Player_Player");
        }else {
            plysical = PlayerManager.core_Formula_Map.get("Range_Physics_Player_Other");
        }
        plysical = new ConversionMain().valueOf(player,target,plysical);
        try {
            double number = Arithmetic.eval(plysical);
            attackNumber = Double.valueOf(number);
        }catch (Exception exception){
            attackNumber = 0;
        }
        return attackNumber;
    }


}
