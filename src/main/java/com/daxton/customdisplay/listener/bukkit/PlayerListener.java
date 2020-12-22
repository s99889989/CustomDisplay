package com.daxton.customdisplay.listener.bukkit;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.ListenerManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;


import static net.mmogroup.mmolib.api.stat.SharedStat.SPELL_CRITICAL_STRIKE_POWER;

public class PlayerListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity target = null;

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        PlayerDataMap.getPlayerDataMap().put(playerUUID,new PlayerData(player));

        new PlayerTrigger(player).onJoin(player);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        /**刪除玩家資料物件  和   刪除OnTime物件**/
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
        if(playerData != null){
            new PlayerTrigger(player).onQuit(player);
            PlayerDataMap.getPlayerDataMap().remove(playerUUID);

        }
        if(ListenerManager.getCast_On_Stop().get(playerUUID) != null){
            ListenerManager.getCast_On_Stop().put(playerUUID,false);
        }

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        String message = event.getMessage();
        //cd.getLogger().info("聊天訊息: "+message);
    }


    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event){
        if(event.getEntity() instanceof Player){
            Player player = ((Player) event.getEntity()).getPlayer();
            new PlayerTrigger(player).onRegainHealth(player);

        }

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        new PlayerTrigger(player).onMove(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        new PlayerTrigger(player).onDeath(player);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        if(event.isSneaking()){
            new PlayerTrigger(player).onSneak(player);
        }else {
            new PlayerTrigger(player).onStandup(player);
        }

    }


    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event){
        Player player = event.getPlayer();

        UUID playerUUID = player.getUniqueId();
        if(ListenerManager.getCast_On_Stop().get(playerUUID) == null){
            ListenerManager.getCast_On_Stop().put(playerUUID,true);
            if(PlayerDataMap.getPlayerDataMap().get(playerUUID) != null){
                new PlayerTrigger(player).onKeyFON(player);
            }
        }else {
            if(ListenerManager.getCast_On_Stop().get(playerUUID) == true){
                if(PlayerDataMap.getPlayerDataMap().get(playerUUID) != null){
                    new PlayerTrigger(player).onKeyFOFF(player);
                }
                ListenerManager.getCast_On_Stop().put(playerUUID,false);
            }else {
                if(PlayerDataMap.getPlayerDataMap().get(playerUUID) != null){
                    new PlayerTrigger(player).onKeyFON(player);
                }
                ListenerManager.getCast_On_Stop().put(playerUUID,true);
            }
        }

    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        int key = event.getNewSlot();
        switch(key){
            case 0:
                new PlayerTrigger(player).onKey1(player);
                break;
            case 1:
                new PlayerTrigger(player).onKey2(player);
                break;
            case 2:
                new PlayerTrigger(player).onKey3(player);
                break;
            case 3:
                new PlayerTrigger(player).onKey4(player);
                break;
            case 4:
                new PlayerTrigger(player).onKey5(player);
                break;
            case 5:
                new PlayerTrigger(player).onKey6(player);
                break;
            case 6:
                new PlayerTrigger(player).onKey7(player);
                break;
            case 7:
                new PlayerTrigger(player).onKey8(player);
                break;
            case 8:
                new PlayerTrigger(player).onKey9(player);
                break;
        }



    }



    public static Entity[]  getNearbyEntities(Location l, int radius){
        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16))/16;
        HashSet<Entity> radiusEntities = new HashSet<Entity>();
        for (int chX = 0 -chunkRadius; chX <= chunkRadius; chX ++){
            for (int chZ = 0 -chunkRadius; chZ <= chunkRadius; chZ++){
                int x=(int) l.getX(),y=(int) l.getY(),z=(int) l.getZ();
                for (Entity e : new Location(l.getWorld(),x+(chX*16),y,z+(chZ*16)).getChunk().getEntities()){
                    if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock()) radiusEntities.add(e);
                }
            }
        }

        return radiusEntities.toArray(new Entity[radiusEntities.size()]);
    }

    public LivingEntity getTarget(Player player) {
        Entity[] targetList = getNearbyEntities(player.getLocation(), 10);
        //List<Entity> nearbyE = player.getNearbyEntities(20, 20, 20);
        ArrayList<LivingEntity> nearPlayers = new ArrayList<>();
        for (Entity e : targetList) {
            if (e instanceof LivingEntity) {
                nearPlayers.add((LivingEntity) e);
            }
        }
        LivingEntity target = player;
        BlockIterator bItr = new BlockIterator(player, 20);
        Block block;
        Location loc;
        int bx, by, bz;
        double ex, ey, ez;
        while (bItr.hasNext()) {

            block = bItr.next();
            bx = block.getX();
            by = block.getY();
            bz = block.getZ();
            for (LivingEntity e : nearPlayers) {
                loc = e.getLocation();
                ex = loc.getX();
                ey = loc.getY();
                ez = loc.getZ();
                if ((bx - .75 <= ex && ex <= bx + 1.75) && (bz - .75 <= ez && ez <= bz + 1.75) && (by - 1 <= ey && ey <= by + 2.5)) {
                    target = e;
                    break;

                }
            }

        }
        return target;

    }

}
