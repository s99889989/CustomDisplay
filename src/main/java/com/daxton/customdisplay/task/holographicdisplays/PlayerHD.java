package com.daxton.customdisplay.task.holographicdisplays;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.HDMapManager;
import com.daxton.customdisplay.util.ContentUtil;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;


public class PlayerHD {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Hologram hologram;

    private BukkitRunnable bukkitRunnable;

    private String content;

    public PlayerHD(Player p){
        content = "";
        UUID uuid = p.getUniqueId();
        Location location = p.getLocation();
        double hight = p.getHeight();

        hologram = HologramsAPI.createHologram(cd, location.add(0,hight+cd.getConfigManager().player_top_display_hight,0));
        if(!cd.getConfigManager().player_top_display_see_self){
            VisibilityManager visiblityManager = hologram.getVisibilityManager();
            visiblityManager.hideTo(p);
        }

        hologram.appendTextLine("");
        bukkitRunnable = new BukkitRunnable() {
            int ticksRun;
            @Override
            public void run() {
                ticksRun++;
                hologram.clearLines();
                for(String string : cd.getConfigManager().player_top_display_content){
                    string = new ContentUtil(string,p,"Character").getOutputString();
                    hologram.appendTextLine(string);
                }

                if(ticksRun > 100){
                    hologram.delete();
                    cancel();
                    HDMapManager.getPlayerHDMap().remove(uuid);
                }
            }
        };
        bukkitRunnable.runTaskTimer(cd,1,cd.getConfigManager().player_top_display_refrsh);

    }

    public BukkitRunnable getBukkitRunnable() {
        return bukkitRunnable;
    }

    public Hologram getHologram() {
        return hologram;
    }
}
