package com.daxton.customdisplay.listener.customdisplay;

import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {

    private LivingEntity target = null;

    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event){
        if(event.getEntity() instanceof Player){
            Player player = ((Player) event.getEntity()).getPlayer();
            new PlayerTrigger(player).onRegainHealth(player,target);

        }

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        new PlayerTrigger(player).onMove(player,target);
    }

    @EventHandler
    public void onPlayerMove(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        new PlayerTrigger(player).onDeath(player,target);
    }

}
