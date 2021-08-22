package com.daxton.customdisplay.listener.mythicmobs;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Convert;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.MobManager;
import com.daxton.customdisplay.manager.PlaceholderManager;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobLootDropEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.spawning.random.RandomSpawner;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MythicMobSpawnListener implements Listener {


    @EventHandler
    public void onMythicMobSpawn(MythicMobSpawnEvent event){

        try {
            ActiveMob activeMob = event.getMob();
            double mobLevel = event.getMobLevel();

            //CustomDisplay.getCustomDisplay().getLogger().info("LEVLE:"+event.getMobLevel()+" : "+activeMob.getLevel());
            if(activeMob != null){

                String mobID = activeMob.getMobType();
                String uuidString = activeMob.getUniqueId().toString();

                //用UUID字串儲存MMID
                MobManager.mythicMobs_mobID_Map.put(uuidString, mobID);
                //用UUID字串儲存MM等級
                MobManager.mythicMobs_Level_Map.put(uuidString, String.valueOf(mobLevel));
                //用UUID字串儲存MMID
                MobManager.mythicMobs_ActiveMob_Map.put(uuidString, activeMob);
            }
        }catch (NoSuchMethodError error){
            //
        }
    }

    @EventHandler
    public void onMythicMobDeath(MythicMobDeathEvent event){

        LivingEntity target = (LivingEntity) event.getEntity();
        LivingEntity killer = event.getKiller();

        Player player;
        player = Convert.convertKillerPlayer(killer);
        try {
            if(player != null){
                String mobID = event.getMob().getMobType();
                String item = "Air";
                if(event.getDrops().size() > 0){
                    item = "";
                    for(int i = 0;i < event.getDrops().size();i++){
                        if(i == 0){
                            if(!event.getDrops().get(i).getItemMeta().getDisplayName().isEmpty()){
                                item = event.getDrops().get(i).getItemMeta().getDisplayName();
                            }else {
                                item = event.getDrops().get(i).getI18NDisplayName();
                            }
                        }else {
                            if(!event.getDrops().get(i).getItemMeta().getDisplayName().isEmpty()){
                                item = item + "," + event.getDrops().get(i).getItemMeta().getDisplayName();
                            }else {
                                item = item + "," + event.getDrops().get(i).getI18NDisplayName();
                            }
                        }
                    }
                }
                String uuidString = player.getUniqueId().toString();

                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_player_kill_mythic_mob_item>",item);

                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_mythic_kill_mob_id>",mobID);

                PlayerTrigger.onPlayer(player, target, "~onmmobdeath");
                if(!(item.contains("Air"))){
                    PlayerTrigger.onPlayer(player, target, "~onmmobdropitem");
                }
            }
        }catch (NoSuchMethodError error){
            //
        }





    }

    @EventHandler
    public void onMythicMobLootDrop(MythicMobLootDropEvent event){
        LivingEntity target = (LivingEntity) event.getEntity();
        LivingEntity livingEntity = event.getKiller();


        if(livingEntity instanceof Player){
            Player player = (Player) livingEntity;
            String uuidString = livingEntity.getUniqueId().toString();

            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_player_kill_mythic_mob_exp>",String.valueOf(event.getExp()));
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_player_kill_mythic_mob_money>",String.valueOf(event.getMoney()));
            if(event.getExp() != 0){
                PlayerTrigger.onPlayer(player, target, "~onmmobdropexp");
            }
            if(event.getMoney() != 0){
                PlayerTrigger.onPlayer(player, target, "~onmmobdropmoney");
            }

        }


    }



}
