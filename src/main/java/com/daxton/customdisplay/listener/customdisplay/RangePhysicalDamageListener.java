package com.daxton.customdisplay.listener.customdisplay;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.EntityFind;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.api.other.NumberUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.io.File;

public class RangePhysicalDamageListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    @EventHandler(priority = EventPriority.LOW)
    public void onPhysical(PhysicalDamageEvent event) {
        if(!(event.getDamager() instanceof Arrow)){
            return;
        }
        Player player = EntityFind.convertPlayer(event.getDamager());
        if(player != null){
            String playerUUIDString = player.getUniqueId().toString();
            File playerFilePatch = new File(cd.getDataFolder(),"Players/"+playerUUIDString+"/"+playerUUIDString+".yml");
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);

            String plysical = "0";
            if(event.getTarget() instanceof Player){
                plysical = playerConfig.getString(playerUUIDString+".Range_physics_formula.Player");
            }else {
                plysical = playerConfig.getString(playerUUIDString+".Range_physics_formula.Other");
            }
            plysical = new StringConversion(player,event.getTarget(),plysical,"Character").valueConv();
            int attackNumber = 0;
            try {
                double number = Arithmetic.eval(plysical);
                String numberDec = new NumberUtil(number,"#").getDecimalString();
                attackNumber = Integer.valueOf(numberDec);
            }catch (Exception exception){
                attackNumber = 0;
            }
            int  r = (int)(Math.random()*100);
            if(r>50){
                event.setCancelled(true);
                return;
            }
            event.setDamage(attackNumber);


        }


    }

}
