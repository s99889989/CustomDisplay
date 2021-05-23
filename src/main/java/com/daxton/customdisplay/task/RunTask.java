package com.daxton.customdisplay.task;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class RunTask {

    public static BukkitRunnable bukkitRunnable20;

    public static void run20(CustomDisplay cd){

        bukkitRunnable20 = new BukkitRunnable() {
            @Override
            public void run() {

                Bukkit.getOnlinePlayers().forEach(player -> {
                    String uuidString = player.getUniqueId().toString();
                    if(PlayerManager.player_Data_Map.get(uuidString) != null){
                        PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);
                        if(playerData.getMana() < playerData.getMaxMana()){
                            double setMane = playerData.getMana() + playerData.getMana_Regeneration();
                            if(setMane > playerData.getMaxMana()){
                                setMane = playerData.getMaxMana();
                            }
                            playerData.setMana(setMane);
                        }

                        //cd.getLogger().info("魔量: "+playerData.getMana()+" / "+playerData.getMaxMana());

                    }


                });



            }
        };

        bukkitRunnable20.runTaskTimer(cd, 0, 200);

    }

}
