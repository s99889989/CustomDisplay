package com.daxton.customdisplay.listener.bukkit;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.config.SaveConfig;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.config.ConfigManager;
import com.daxton.customdisplay.manager.*;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;

import java.util.*;


public class PlayerListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private ConfigManager configManager = cd.getConfigManager();

    private FileConfiguration config = configManager.config;

    private FileConfiguration info = configManager.language;

    private LivingEntity target = null;

    private BukkitRunnable bukkitRunnable;
    /**登入時**/
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        UUID playerUUID = player.getUniqueId();
        String uuidString = player.getUniqueId().toString();
        if(configManager.config.getBoolean("HealthScale.enable")){
            player.setHealthScale(configManager.config.getInt("HealthScale.scale"));
        }
        if(config.getBoolean("ResourcePack.enable")){
            onResourcePackSend(player,null);
        }

        PlayerDataMap.getPlayerDataMap().put(playerUUID,new PlayerData(player));
        new PlayerTrigger(player).onJoin(player);

        /**設定F**/
        ListenerManager.getCast_On_Stop().put(uuidString,false);

    }
    /**登出時**/
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        String uuidString = playerUUID.toString();
        /**刪除玩家資料物件  和   刪除OnTime物件**/
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
        if(playerData != null){
            new PlayerTrigger(player).onQuit(player);

            String attackCore = cd.getConfigManager().config.getString("AttackCore");
            if(attackCore.toLowerCase().contains("customcore")){
                playerData.getBukkitRunnable().cancel();
            }
            /**儲存人物資料**/
            new SaveConfig().setConfig(player);
            PlayerDataMap.getPlayerDataMap().remove(playerUUID);


        }
        if(bukkitRunnable != null){
            bukkitRunnable.cancel();
        }

        ListenerManager.getCast_On_Stop().put(uuidString,false);

    }
    /**當打開背包時**/
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getWhoClicked() instanceof Player == false){
            return;
        }
        Player player = (Player) event.getWhoClicked();
        UUID playerUUID = player.getUniqueId();
        Inventory inventory = event.getInventory();

        if(ActionManager.getInventory_name_Map().get(playerUUID) != null){
            String taskID = ActionManager.getInventory_name_Map().get(playerUUID);
            if(ActionManager.getInventory_Map().get(taskID) == inventory) {
                if(ActionManager.getJudgment2_OpenInventory_Map().get(taskID) != null){
                    ActionManager.getJudgment2_OpenInventory_Map().get(taskID).InventoryListener(event);
                }
                //player.sendMessage("移動GUI");
//                event.setCancelled(true);
//                if(event.getRawSlot() == 0){
//
//                    player.sendMessage(event.getClick().toString());
//                }


            }
        }

    }

//    @EventHandler
//    public void anvilCost(PrepareAnvilEvent event){
//        Player player = (Player) event.getViewers().get(0);
//        ConfigMapManager.getFileConfigurationNameMap().values().forEach(s -> {
//            if(s.contains("Items_")){
//                FileConfiguration itemConfig = ConfigMapManager.getFileConfigurationMap().get(s);
//                itemConfig.getKeys(false).forEach(s1 -> {
//                    AnvilInventory inv = event.getInventory();
//                    ItemStack itemStack = inv.getFirstItem();
//                    if(itemStack != null){
//                        ItemMeta itemMeta = itemStack.getItemMeta();
//                        if(itemMeta != null){
//                            String itemName = itemConfig.getString(s1+".DisplayName");
//                            itemName = new ConversionMain().valueOf(player,null,itemName);
//                            String itemName2 = itemMeta.getDisplayName();
//                            if(itemName.equals(itemName2)){
//                                //inv.setRepairCost(100);
//                                //player.sendMessage(inv.getRepairCost()+" : "+itemMeta.getDisplayName());
//                            }
//                        }
//                    }
//                });
//
//            }
//        });
//
//
//    }


    /**當經驗值改變時**/
    @EventHandler
    public void onExpChange(PlayerExpChangeEvent event){
        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();
        int amount = event.getAmount();
        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_player_up_exp_type>","default");
        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_player_change_exp_amount>",String.valueOf(amount));
        new PlayerTrigger(player).onExpUp(player);
    }

    /**當等級改變時**/
    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent event){
        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();
        int oldLevel = event.getOldLevel();
        int newLevel = event.getNewLevel();
        if(oldLevel > newLevel){
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_down_level_type>","default");
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_down_exp_type>","default");
            new PlayerTrigger(player).onLevelDown(player);
            new PlayerTrigger(player).onExpDown(player);
        }else {
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_up_level_type>","default");
            new PlayerTrigger(player).onLevelUp(player);
        }
    }

    /**當玩家聊天**/
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String uuidString = event.getPlayer().getUniqueId().toString();
        String message = event.getMessage();
        message = new ConversionMain().valueOf(player,null,message);

        new PlaceholderManager().getCd_Placeholder_Map().put(uuidString+"<cd_last_chat>",message);
        new PlayerTrigger(player).onChat(player);
        //event.setCancelled(true);
    }

    /**當玩家回血**/
    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event){
        if(event.getEntity() instanceof Player){
            Player player = ((Player) event.getEntity()).getPlayer();
            new PlayerTrigger(player).onRegainHealth(player);

        }

    }
    /**當玩移動**/
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        new PlayerTrigger(player).onMove(player);
    }
    /**當玩家死亡**/
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
//        //player.sendMessage("值: ");
//        int maxExp = player.getExpToLevel();
//        float ExpB = player.getExp();
//        double newExp = maxExp/ExpB*0.3;
//
//        player.setExp((float) newExp);
        new PlayerTrigger(player).onDeath(player);

    }
    /**當蹲下時**/
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        if(event.isSneaking()){
            new PlayerTrigger(player).onSneak(player);
        }else {
            new PlayerTrigger(player).onStandup(player);
        }

    }

    /**當重生時**/
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();

        UUID playerUUID = player.getUniqueId();
        String uuidString = player.getUniqueId().toString();
        if(PlayerDataMap.getPlayerDataMap().get(playerUUID) != null){
            new PlayerTrigger(player).onKeyFOFF(player);
            ListenerManager.getCast_On_Stop().put(uuidString,false);
        }

    }


    /**當按下F鍵**/
    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event){
        Player player = event.getPlayer();

        UUID playerUUID = player.getUniqueId();
        String uuidString = player.getUniqueId().toString();
        if(ListenerManager.getCast_On_Stop().get(uuidString) == true){
            if(PlayerDataMap.getPlayerDataMap().get(playerUUID) != null){
                new PlayerTrigger(player).onKeyFOFF(player);
            }
            ListenerManager.getCast_On_Stop().put(uuidString,false);
        }else {
            if(PlayerDataMap.getPlayerDataMap().get(playerUUID) != null){
                new PlayerTrigger(player).onKeyFON(player);
            }
            ListenerManager.getCast_On_Stop().put(uuidString,true);
        }


    }



    /**當按下切換1~9時**/
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

    @EventHandler
    public void onResourcePack(PlayerResourcePackStatusEvent event){
        Player player = event.getPlayer();
        if(config.getBoolean("ResourcePack.enable")){
            onResourcePackSend(player,event.getStatus().toString());
        }

    }

    public void onResourcePackSend(Player player,String status){

        /**發送材質包**/
        if(status == null){
            int time = 2;
            String timeString = "";
            try{
                time = config.getInt("ResourcePack.download-delay");
                timeString = String.valueOf(time);
            }catch (NumberFormatException exception){
                time = 1;
            }
            player.sendMessage(info.getString("Language.ResourcePack.join").replace("{time}",timeString));

            bukkitRunnable = new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        player.setResourcePack(config.getString("ResourcePack.url"),config.getString("ResourcePack.hash"));
                    }catch (NoSuchMethodError exception){

                    }



                }
            };
            bukkitRunnable.runTaskLater(cd,time*20);



        }
        if(status !=null){
            /**發送材質包成功**/
            if(status.contains("SUCCESSFULLY_LOADED")){
                player.sendMessage(info.getString("Language.ResourcePack.successfully-loaded"));
                return;
            }

            /**材質包下載失敗**/
            if(status.contains("FAILED_DOWNLOAD")){
                int time = 1;
                String timeString = "";
                try{
                    time = config.getInt("ResourcePack.kick-error-delay");
                    timeString = String.valueOf(time);
                }catch (NumberFormatException exception){
                    time = 1;
                }
                if(config.getBoolean("ResourcePack.kick-error-download")){
                    player.sendMessage(info.getString("Language.ResourcePack.download-error").replace("{time}",timeString));
                    bukkitRunnable = new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.kickPlayer(info.getString("Language.ResourcePack.download-error-kick"));
                        }
                    };
                    bukkitRunnable.runTaskLater(cd,time*20);
                    return;
                }else {
                    player.sendMessage(info.getString("Language.ResourcePack.download-error-pass"));
                }
                return;
            }

            /**拒絕接受材質包**/
            if(status.contains("DECLINED")){
                int time = 1;
                String timeString = "";
                try{
                    time = config.getInt("ResourcePack.kick-no-delay");
                    timeString = String.valueOf(time);
                }catch (NumberFormatException exception){
                    time = 1;
                }
                if(config.getBoolean("ResourcePack.kick-no-download")){
                    player.sendMessage(info.getString("Language.ResourcePack.kick-delay").replace("{time}",timeString));
                    bukkitRunnable = new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.kickPlayer(info.getString("Language.ResourcePack.kick"));
                        }
                    };
                    bukkitRunnable.runTaskLater(cd,time*20);


                }else {
                    player.sendMessage(info.getString("Language.ResourcePack.kick-pass"));
                }
                return;
            }
        }


    }


}
