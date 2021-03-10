package com.daxton.customdisplay.listener.mythicmobs;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Convert;
import com.daxton.customdisplay.api.entity.EntityFind;
import com.daxton.customdisplay.api.mob.MobConfig;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.ticxo.modelengine.model.MEModeledEntity;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobLootDropEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MythicMobSpawnListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();



    @EventHandler
    public void onMythicMobSpawn(MythicMobSpawnEvent event){
        ActiveMob activeMob = event.getMob();
        new MobConfig().setMod(activeMob,event.getMobLevel());




    }

    @EventHandler
    public void onMythicMobDeath(MythicMobDeathEvent event){

        LivingEntity target = (LivingEntity) event.getEntity();
        LivingEntity killer = event.getKiller();

        Player player = null;
        player = Convert.convertKillerPlayer(killer);

        if(player != null){
            String mobID = event.getMob().getMobType();
            String item = "Air";
            if(event.getDrops().size() > 0){
                item = "";
                for(int i = 0;i < event.getDrops().size();i++){
                    if(i == 0){
                        if(event.getDrops().get(i).getItemMeta().getDisplayName().isEmpty() == false){
                            item = event.getDrops().get(i).getItemMeta().getDisplayName();
                        }else {
                            item = event.getDrops().get(i).getI18NDisplayName();
                        }
                    }else {
                        if(event.getDrops().get(i).getItemMeta().getDisplayName().isEmpty() == false){
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
            new PlayerTrigger(player).onMythicMobDeath(player,target);
            if(!(item.contains("Air"))){
                new PlayerTrigger(player).onMMobDropItem(player,target);
            }
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
                new PlayerTrigger(player).onMMobDropExp(player,target);
            }
            if(event.getMoney() != 0){
                new PlayerTrigger(player).onMMobDropMoney(player,target);
            }

        }


    }



}
