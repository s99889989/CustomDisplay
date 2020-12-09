package com.daxton.customdisplay.listener.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.manager.PlayerDataMap;
import com.daxton.customdisplay.task.action.JudgmentAction;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDespawnEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import io.lumine.xikage.mythicmobs.skills.placeholders.GenericPlaceholderMeta;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpellCastEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import net.Indyuce.mmocore.listener.SpellCast;

import java.util.Collection;
import java.util.UUID;

public class JoinListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private UUID playerUUID;

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        this.player = event.getPlayer();
        this.playerUUID = player.getUniqueId();

        PlayerDataMap.getPlayerDataMap().put(playerUUID,new PlayerData(player));

        if(PlayerDataMap.getPlayerDataMap().get(playerUUID) != null){
            PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
            playerData.runAction("~onjoin",event);
        }

    }
    @EventHandler
    public void onMythicMobSpawn(MythicMobSpawnEvent event){
        //GenericPlaceholderMeta();
        //cd.getLogger().info("MythicMobSpawnEvent");

    }

}
