package com.daxton.customdisplay.task.holographicdisplays;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.HDMapManager;
import com.daxton.customdisplay.util.NumberUtil;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class MonsterHD {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Hologram hologram;

    private BukkitRunnable bukkitRunnable;

    private Map<UUID, Double> healthMap = new HashMap<>();

    private Map<UUID, Location> locationMap = new HashMap<>();

    private UUID uuid;

    private double nowhealth;

    private double maxhealth;

    private Location location;

    public MonsterHD(LivingEntity monster){
        uuid = monster.getUniqueId();
        double monster_hight = monster.getHeight();
        location = monster.getLocation();
        maxhealth = monster.getAttribute(GENERIC_MAX_HEALTH).getValue();
        nowhealth = monster.getHealth();
        int counthealth = (int) nowhealth*10/(int) maxhealth;
        String mhealth = new NumberUtil(counthealth, cd.getConfigManager().monster_top_display_health_material).getTenString();
        hologram = HologramsAPI.createHologram(cd, location.add(0,monster_hight + cd.getConfigManager().monster_top_display_hight,0));

        String change_health_content = cd.getConfigManager().monster_top_display_content.replace("{sad_monster_health_number}", (int)nowhealth+"/"+(int)maxhealth);
        String change_health_content2 = change_health_content.replace("{sad_monster_health}", mhealth);
        hologram.appendTextLine(change_health_content2);

        bukkitRunnable = new BukkitRunnable() {
            int ticksRun;
            @Override
            public void run() {
                ticksRun++;
                nowhealth = (int) monster.getHealth();
                location = monster.getLocation();

                if(!locationMap.containsKey(uuid)){
                    locationMap.put(uuid, location);
                }
                if(locationMap.get(uuid) != location){
                    hologram.teleport(location.add(0,monster_hight + cd.getConfigManager().monster_top_display_hight,0));
                    locationMap.put(uuid, location);
                }

                if(!healthMap.containsKey(uuid)){
                    healthMap.put(uuid, maxhealth);
                }
                if(healthMap.get(uuid) != nowhealth){
                    int counthealth = (int) nowhealth*10/(int) maxhealth;
                    String mhealth = new NumberUtil(counthealth, cd.getConfigManager().monster_top_display_health_material).getTenString();
                    String change_health_content = cd.getConfigManager().monster_top_display_content.replace("{sad_monster_health_number}", (int)nowhealth+"/"+(int)maxhealth);
                    String change_health_content2 = change_health_content.replace("{sad_monster_health}", mhealth);
                    hologram.removeLine(0);
                    hologram.appendTextLine(change_health_content2);
                    healthMap.put(uuid, nowhealth);
                }


                if(ticksRun > 100){
                    hologram.delete();
                    healthMap.clear();
                    locationMap.clear();
                    cancel();
                    HDMapManager.getMonsterHDMap().remove(uuid);
                }
            }
        };
        bukkitRunnable.runTaskTimer(cd,1, cd.getConfigManager().pmonster_top_display_refrsh);
    }

    public Map<UUID, Double> getHealthMap() {
        return healthMap;
    }

    public Map<UUID, Location> getLocationMap() {
        return locationMap;
    }

    public BukkitRunnable getBukkitRunnable() {
        return bukkitRunnable;
    }

    public Hologram getHologram() {
        return hologram;
    }

}
