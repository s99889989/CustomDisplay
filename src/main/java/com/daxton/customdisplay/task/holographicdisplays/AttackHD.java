package com.daxton.customdisplay.task.holographicdisplays;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.util.ContentUtil;
import com.daxton.customdisplay.util.NumberUtil;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AttackHD {

    private static CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private static double hdx;

    private static double hdz;

    private  static double hdy;

    private String content;

    public AttackHD(LivingEntity target, Entity damager, double damageNumber){

        Player p = (Player) damager;
        String snumber = new NumberUtil(damageNumber, cd.getConfigManager().attack_display_player_decimal).getDecimalString();
        snumber = new NumberUtil(snumber, cd.getConfigManager().attack_display_player_conversion).getNineString();

        Location location = target.getLocation();
        double targetHight = target.getHeight();

        Hologram hologram = HologramsAPI.createHologram(cd,location.add(0,targetHight+cd.getConfigManager().attack_display_player_hight,0));

        for(String string : cd.getConfigManager().attack_display_player_content){
            content = new ContentUtil(string,p,"Character").getOutputString();
            content = content.replace("{sad_damage}",snumber);
            hologram.appendTextLine(content);
        }

        //String msg = cd.getConfigManager().attack_display_player_content.replace("{sad_damage}",snumber);

        if(cd.getConfigManager().attack_display_player_form.equalsIgnoreCase("up")){

            upHD(hologram, location);
        }
        if(cd.getConfigManager().attack_display_player_form.equalsIgnoreCase("throw")){

            double hhdx = damager.getLocation().getDirection().getX();
            double hhdz = damager.getLocation().getDirection().getZ();
            if(hhdx > 0){
                hdx = 0.2;
            }else{
                hdx = -0.2;
            }
            if(hhdz > 0){
                hdz = 0.2;
            }else{
                hdz = -0.2;
            }
            throwHD(hologram, location);
        }
    }

    public static void upHD(Hologram hologram, Location location){
        new BukkitRunnable(){
            int ticksRun;
            @Override
            public void run() {
                ticksRun++;
                hologram.teleport(location.add(0,0.05,0));
                if(ticksRun > 20){
                    hologram.delete();
                    cancel();
                }
            }
        }.runTaskTimer(cd,1,1);
    }

    public static void throwHD(Hologram hologram, Location location){
        new BukkitRunnable() {
            int ticksRun;
            @Override
            public void run() {
                if(ticksRun < 3){
                    hdy = 0.6;
                }else if(ticksRun > 5){
                    hdy = -0.8;
                }else{
                    hdy = 0;
                }
                ticksRun++;
                hologram.teleport(location.add(hdx,hdy,hdz));
                if (ticksRun > 10) {
                    hologram.delete();
                    cancel();
                }
            }
        }.runTaskTimer(cd, 1, 3);
    }

}
