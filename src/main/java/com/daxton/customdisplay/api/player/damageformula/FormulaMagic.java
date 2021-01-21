package com.daxton.customdisplay.api.player.damageformula;

import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class FormulaMagic {

    public FormulaMagic(){

    }

    /**魔法傷害公式**/
    public double setMagicDamageNumber(Player player, LivingEntity target){

        double attackNumber = 0;

        String plysical = "0";
        if(target instanceof Player){
            plysical = PlayerDataMap.core_Formula_Map.get("Magic_Attack_Player_Player");
        }else {
            plysical = PlayerDataMap.core_Formula_Map.get("Magic_Attack_Player_Player");
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
