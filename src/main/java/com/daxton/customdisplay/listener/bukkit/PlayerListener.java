package com.daxton.customdisplay.listener.bukkit;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.config.SaveConfig;
import com.daxton.customdisplay.api.entity.LookTarget;
import com.daxton.customdisplay.api.item.PlayerEquipment2;
import com.daxton.customdisplay.api.player.PlayerTrigger2;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.api.player.profession.BossBarSkill2;
import com.daxton.customdisplay.api.player.profession.UseSkill;
import com.daxton.customdisplay.config.ConfigManager;
import com.daxton.customdisplay.manager.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

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

        new PlayerTrigger2(player).onTwo(player, target, "~onjoin");

        /**設定F**/
        ListenerManager.getCast_On_Stop().put(uuidString,false);


        /**讀取屬性**/
        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("config.yml");
        boolean attr = fileConfiguration.getBoolean("Class.Skill");
        if(attr){
            new PlayerEquipment2().reloadEquipment(player,10);
        }
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

            new PlayerTrigger2(player).onTwo(player, target, "~onquit");

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
        String uuidString = playerUUID.toString();
        Inventory inventory = event.getInventory();



        if(ActionManager2.playerUUID_taskID_Map.get(uuidString) != null){
            String taskID = ActionManager2.playerUUID_taskID_Map.get(uuidString);

            if(ActionManager2.taskID_Inventory_Map.get(taskID) != null){
                if(ActionManager2.taskID_Inventory_Map.get(taskID) == inventory) {

                    if(ActionManager2.judgment_Inventory_Map.get(taskID) != null){
                        ActionManager2.judgment_Inventory_Map.get(taskID).InventoryListener(event);
                    }


                }
            }

        }

//        if(ActionManager.getInventory_name_Map().get(playerUUID) != null){
//            String taskID = ActionManager.getInventory_name_Map().get(playerUUID);
//            if(ActionManager.getInventory_Map().get(taskID) == inventory) {
//                if(ActionManager.getJudgment2_OpenInventory_Map().get(taskID) != null){
//                    ActionManager.getJudgment2_OpenInventory_Map().get(taskID).InventoryListener(event);
//                }
//
//            }
//        }

    }

    /**當玩家點擊時**/
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();

        String actionName = event.getAction().name();

        LivingEntity target = LookTarget.getLivingTarget(player,10);

        if(actionName.equals("LEFT_CLICK_AIR")){
            new PlayerTrigger2(player).onTwo(player, target, "~leftclickair");
        }
        if(actionName.equals("LEFT_CLICK_BLOCK")){
            new PlayerTrigger2(player).onTwo(player, target, "~leftclickblock");
        }
        if(actionName.equals("RIGHT_CLICK_AIR")){
            new PlayerTrigger2(player).onTwo(player, target, "~rightclickair");
        }
        if(actionName.equals("RIGHT_CLICK_BLOCK")){
            new PlayerTrigger2(player).onTwo(player, target, "~rightclickblock");
        }
        if(actionName.equals("PHYSICAL")){
            new PlayerTrigger2(player).onTwo(player, target, "~pressureplate");
        }

    }

    /**當經驗值改變時**/
    @EventHandler
    public void onExpChange(PlayerExpChangeEvent event){
        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();
        int amount = event.getAmount();
        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_player_up_exp_type>","default");
        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_player_change_exp_amount>",String.valueOf(amount));

        new PlayerTrigger2(player).onTwo(player, target, "~onexpup");
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
            new PlayerTrigger2(player).onTwo(player, null, "~onleveldown");

            new PlayerTrigger2(player).onTwo(player, target, "~onexpdown");
        }else {
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_up_level_type>","default");
            new PlayerTrigger2(player).onTwo(player, null, "~onlevelup");
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

        new PlayerTrigger2(player).onTwo(player, null, "~onchat");
        //event.setCancelled(true);
    }

    /**當玩家回血**/
    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event){
        if(event.getEntity() instanceof Player){
            Player player = ((Player) event.getEntity()).getPlayer();

            new PlayerTrigger2(player).onTwo(player, target, "~onregainhealth");
        }

    }
    /**當玩移動**/
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();


        new PlayerTrigger2(player).onTwo(player, target, "~onmove");
    }
    /**當玩家死亡**/
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        String uuidString = player.getUniqueId().toString();

        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("config.yml");
        boolean skill = fileConfiguration.getBoolean("Class.Skill");
        if(skill){

            if(PlayerDataMap.keyF_BossBarSkill_Map.get(uuidString) == null){
                PlayerDataMap.keyF_BossBarSkill_Map.put(uuidString, new BossBarSkill2());
            }
            PlayerDataMap.keyF_BossBarSkill_Map.get(uuidString).closeSkill(player);

        }


        new PlayerTrigger2(player).onTwo(player, target, "~ondeath");

    }
    /**當蹲下時**/
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        if(event.isSneaking()){

            new PlayerTrigger2(player).onTwo(player, target, "~onsneak");
        }else {
            new PlayerTrigger2(player).onTwo(player, target, "~onstandup");
        }

    }

    /**當重生時**/
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();

        UUID playerUUID = player.getUniqueId();
        String uuidString = player.getUniqueId().toString();
        if(PlayerDataMap.getPlayerDataMap().get(playerUUID) != null){
            new PlayerTrigger2(player).onTwo(player, target, "~onkeyfoff");
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
                new PlayerTrigger2(player).onTwo(player, target, "~onkeyfoff");
            }
            ListenerManager.getCast_On_Stop().put(uuidString,false);
        }else {
            if(PlayerDataMap.getPlayerDataMap().get(playerUUID) != null){
                new PlayerTrigger2(player).onTwo(player, target, "~onkeyfon");
            }
            ListenerManager.getCast_On_Stop().put(uuidString,true);
        }


        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("config.yml");
        boolean skill = fileConfiguration.getBoolean("Class.Skill");
        if(skill){
            event.setCancelled(true);
            int key = player.getInventory().getHeldItemSlot();

            boolean b = ListenerManager.getCast_On_Stop().get(uuidString);

            if(PlayerDataMap.keyF_BossBarSkill_Map.get(uuidString) == null){
                PlayerDataMap.keyF_BossBarSkill_Map.put(uuidString, new BossBarSkill2());
            }

            if(b){
                PlayerDataMap.keyF_BossBarSkill_Map.get(uuidString).openSkill(player, key);
            }else {
                PlayerDataMap.keyF_BossBarSkill_Map.get(uuidString).closeSkill(player);
            }

        }


    }



    /**當按下切換1~9時**/
    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();

        int key = event.getNewSlot();
        int key2 = event.getPreviousSlot();

        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("config.yml");
        boolean skill = fileConfiguration.getBoolean("Class.Skill");
        if(skill){
            boolean fOn = ListenerManager.getCast_On_Stop().get(uuidString);
            if(fOn){
                event.setCancelled(true);
                if(key != key2){
                    new UseSkill().use(player, key);
                }
            }

        }else {
            LivingEntity target = LookTarget.getLivingTarget(player,10);
            switch(key){
                case 0:
                    new PlayerTrigger2(player).onTwo(player, target, "~onkey1");
                    break;
                case 1:
                    new PlayerTrigger2(player).onTwo(player, target, "~onkey2");
                    break;
                case 2:
                    new PlayerTrigger2(player).onTwo(player, target, "~onkey3");
                    break;
                case 3:
                    new PlayerTrigger2(player).onTwo(player, target, "~onkey4");
                    break;
                case 4:
                    new PlayerTrigger2(player).onTwo(player, target, "~onkey5");
                    break;
                case 5:
                    new PlayerTrigger2(player).onTwo(player, target, "~onkey6");
                    break;
                case 6:
                    new PlayerTrigger2(player).onTwo(player, target, "~onkey7");
                    break;
                case 7:
                    new PlayerTrigger2(player).onTwo(player, target, "~onkey8");
                    break;
                case 8:
                    new PlayerTrigger2(player).onTwo(player, target, "~onkey9");
                    break;
            }
        }
        /**讀取屬性**/
        //boolean attr = fileConfiguration.getBoolean("Class.Attributes");
        //if(attr){
            boolean fOn = ListenerManager.getCast_On_Stop().get(uuidString);
            if(!fOn){
                if(key != key2){
                    new PlayerEquipment2().reloadEquipment(player,key);
                }
            }
        //}

    }

    /**關閉背包**/
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();

        /**讀取屬性**/
        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("config.yml");
        boolean attr = fileConfiguration.getBoolean("Class.Skill");
        if(attr){
            new PlayerEquipment2().reloadEquipment(player,10);
        }





    }

    /**材質包狀態**/
    @EventHandler
    public void onResourcePack(PlayerResourcePackStatusEvent event){
        Player player = event.getPlayer();
        if(config.getBoolean("ResourcePack.enable")){
            onResourcePackSend(player,event.getStatus().toString());
        }

    }
    /**材質包**/
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
