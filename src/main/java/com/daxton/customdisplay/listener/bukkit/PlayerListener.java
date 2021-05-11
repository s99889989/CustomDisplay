package com.daxton.customdisplay.listener.bukkit;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.config.SaveConfig;
import com.daxton.customdisplay.api.entity.LookTarget;
import com.daxton.customdisplay.api.item.*;
import com.daxton.customdisplay.api.other.ResourcePackSend;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.api.player.profession.BossBarSkill2;
import com.daxton.customdisplay.api.player.profession.UseSkill;
import com.daxton.customdisplay.config.ConfigManager;
import com.daxton.customdisplay.gui.item.*;
import com.daxton.customdisplay.gui.item.edititem.*;
import com.daxton.customdisplay.gui.item.edititem.editaction.*;
import com.daxton.customdisplay.manager.*;
import com.daxton.customdisplay.manager.player.EditorGUIManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import com.daxton.customdisplay.task.action2.player.OpenInventory3;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.*;


public class PlayerListener implements Listener {

    private final CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private final ConfigManager configManager = cd.getConfigManager();



    private LivingEntity target = null;

    private BukkitRunnable bukkitRunnable;

    /**登入時**/
    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        //player.sendMessage("玩家登入");
        UUID playerUUID = player.getUniqueId();
        String uuidString = player.getUniqueId().toString();
        if(configManager.config.getBoolean("HealthScale.enable")){
            player.setHealthScale(configManager.config.getInt("HealthScale.scale"));
        }

        //材質包
        ResourcePackSend.send(player,null);

        PlayerManager.getPlayerDataMap().put(playerUUID,new PlayerData(player));

        PlayerTrigger.onPlayer(player, target, "~onjoin");

        //設定F
        ListenerManager.getCast_On_Stop().put(uuidString,false);


        //讀取屬性
//        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("config.yml");
//        boolean attr = fileConfiguration.getBoolean("Class.Skill");
//        if(attr){
            PlayerEquipment2.reloadEquipment(player,10);
//        }
    }
    //登出時
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        String uuidString = playerUUID.toString();
        //刪除玩家資料物件  和   刪除OnTime物件
        PlayerData playerData = PlayerManager.getPlayerDataMap().get(playerUUID);
        if(playerData != null){

            PlayerTrigger.onPlayer(player, target, "~onquit");

            String attackCore = cd.getConfigManager().config.getString("AttackCore");
            if(attackCore != null && attackCore.toLowerCase().contains("customcore")){
                playerData.getBukkitRunnable().cancel();
            }
            //儲存人物資料
            new SaveConfig().setConfig(player);
            PlayerManager.getPlayerDataMap().remove(playerUUID);


        }
        if(bukkitRunnable != null){
            bukkitRunnable.cancel();
        }

        ListenerManager.getCast_On_Stop().put(uuidString,false);

    }
    /**當打開背包時**/
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!(event.getWhoClicked() instanceof Player)){
            return;
        }
        Player player = (Player) event.getWhoClicked();
        UUID playerUUID = player.getUniqueId();
        String uuidString = playerUUID.toString();
        Inventory inventory = event.getInventory();
        ///////////////////////////////
        if(EditorGUIManager.menu_ItemCategorySelection_Inventory_Map.get(uuidString) != null){
            if(EditorGUIManager.menu_ItemCategorySelection_Inventory_Map.get(uuidString) == inventory){
                ItemCategorySelection.onInventoryClick(event);
            }
        }
        ///////////////////////////////
        if(EditorGUIManager.menu_SelectItems_Inventory_Map.get(uuidString) != null){
            if(EditorGUIManager.menu_SelectItems_Inventory_Map.get(uuidString) == inventory){
                SelectItems.onInventoryClick(event);
            }
        }
        ///////////////////////////////
        if(EditorGUIManager.menu_EditItem_Inventory_Map.get(uuidString) != null){
            if(EditorGUIManager.menu_EditItem_Inventory_Map.get(uuidString) == inventory){
                EditItem.onInventoryClick(event);
            }
        }
        ///////////////////////////////
        if(EditorGUIManager.menu_EditEnchantment_Inventory_Map.get(uuidString) != null){
            if(EditorGUIManager.menu_EditEnchantment_Inventory_Map.get(uuidString) == inventory){
                EditEnchantment.onInventoryClick(event);
            }
        }
        ///////////////////////////////
        if(EditorGUIManager.menu_EditAttributes_Inventory_Map.get(uuidString) != null){
            if(EditorGUIManager.menu_EditAttributes_Inventory_Map.get(uuidString) == inventory){
                EditAttributes.onInventoryClick(event);
            }
        }
        ///////////////////////////////
        if(EditorGUIManager.menu_ItemList_Inventory_Map.get(uuidString) != null){
            if(EditorGUIManager.menu_ItemList_Inventory_Map.get(uuidString) == inventory){
                ItemList.onInventoryClick(event);
            }
        }
        ///////////////////////////////
        if(EditorGUIManager.menu_EditFlags_Inventory_Map.get(uuidString) != null){
            if(EditorGUIManager.menu_EditFlags_Inventory_Map.get(uuidString) == inventory){
                EditFlags.onInventoryClick(event);
            }
        }
        ///////////////////////////////
        if(EditorGUIManager.menu_EditLore_Inventory_Map.get(uuidString) != null){
            if(EditorGUIManager.menu_EditLore_Inventory_Map.get(uuidString) == inventory){
                EditLore.onInventoryClick(event);
            }
        }
        ///////////////////////////////
        if(EditorGUIManager.menu_EditAction_Inventory_Map.get(uuidString) != null){
            if(EditorGUIManager.menu_EditAction_Inventory_Map.get(uuidString) == inventory){
                EditAction.onInventoryClick(event);
            }
        }
        ///////////////////////////////
        if(EditorGUIManager.menu_EditActionDetail_Inventory_Map.get(uuidString) != null){
            if(EditorGUIManager.menu_EditActionDetail_Inventory_Map.get(uuidString) == inventory){
                EditActionDetail.onInventoryClick(event);
            }
        }
        ///////////////////////////////
        if(EditorGUIManager.menu_ActionTypeList_Inventory_Map.get(uuidString) != null){
            if(EditorGUIManager.menu_ActionTypeList_Inventory_Map.get(uuidString) == inventory){
                ActionTypeList.onInventoryClick(event);
            }
        }
        ///////////////////////////////
        if(EditorGUIManager.menu_ActionList_Inventory_Map.get(uuidString) != null){
            if(EditorGUIManager.menu_ActionList_Inventory_Map.get(uuidString) == inventory){
                ActionList.onInventoryClick(event);
            }
        }
        ///////////////////////////////
        if(EditorGUIManager.menu_ActionTriggerList_Inventory_Map.get(uuidString) != null){
            if(EditorGUIManager.menu_ActionTriggerList_Inventory_Map.get(uuidString) == inventory){
                ActionTriggerList.onInventoryClick(event);
            }
        }
        ///////////////////////////////
        if(EditorGUIManager.menu_ActionTargetEdit_Inventory_Map.get(uuidString) != null){
            if(EditorGUIManager.menu_ActionTargetEdit_Inventory_Map.get(uuidString) == inventory){
                ActionTargetEdit.onInventoryClick(event);
            }
        }
        ///////////////////////////////
        if(EditorGUIManager.menu_ActionFiltersList_Inventory_Map.get(uuidString) != null){
            if(EditorGUIManager.menu_ActionFiltersList_Inventory_Map.get(uuidString) == inventory){
                ActionFiltersList.onInventoryClick(event);
            }
        }
        ///////////////////////////////
        if(EditorGUIManager.open_Inventory_Map.get(uuidString) != null){
            if(EditorGUIManager.open_Inventory_Map.get(uuidString) == inventory){
                OpenInventory3.onInventoryClick(event);
            }
        }
        ///////////////////////////////

        ///////////////////////////////


        if(ActionManager.playerUUID_taskID_Map.get(uuidString) != null){
            String taskID = ActionManager.playerUUID_taskID_Map.get(uuidString);

            if(ActionManager.taskID_Inventory_Map.get(taskID) != null){
                if(ActionManager.taskID_Inventory_Map.get(taskID) == inventory) {

                    if(ActionManager.judgment_Inventory_Map.get(taskID) != null){
                        ActionManager.judgment_Inventory_Map.get(taskID).InventoryListener(event);
                    }


                }
            }

        }


    }

    /**當玩家點擊時**/
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){


        Player player = event.getPlayer();

        LivingEntity target = null; //LookTarget.getLivingTarget(player,10);
        Action action = event.getAction();

        if(action == Action.LEFT_CLICK_AIR){
            PlayerTrigger.onPlayer(player, target, "~leftclickair");
        }
        if(action == Action.LEFT_CLICK_BLOCK){
            PlayerTrigger.onPlayer(player, target, "~leftclickblock");
        }
        if(action == Action.RIGHT_CLICK_AIR){

            if(getItemCoodDown(player)){
                event.setCancelled(true);
                return;
            }
            PlayerTrigger.onPlayer(player, target, "~rightclickair");

        }
        if(action == Action.RIGHT_CLICK_BLOCK){
            if(getItemCoodDown(player)){
                event.setCancelled(true);
                return;
            }
            PlayerTrigger.onPlayer(player, target, "~rightclickblock");

        }
        if(action == Action.PHYSICAL){
            PlayerTrigger.onPlayer(player, target, "~pressureplate");
        }

    }

    public boolean getItemCoodDown(Player player){
        boolean coodB = false;
        String uuidString = player.getUniqueId().toString();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        String coolDownString = itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(cd, "CoolDownRightClick"), PersistentDataType.STRING);
        int coolDown = 0;
        try {
            //player.sendMessage(coolDownString);
            coolDown = Integer.parseInt(coolDownString);
        }catch (NumberFormatException exception){

        }
        if(PlayerManager.item_Delay_Boolean_Map.get(uuidString) != null){
            coodB = PlayerManager.item_Delay_Boolean_Map.get(uuidString);
        }
        if(coolDown > 0){

            if(PlayerManager.item_Delay_Run_Map.get(uuidString) == null){
                player.setCooldown(itemStack.getType(),coolDown);
                PlayerManager.item_Delay_Boolean_Map.put(uuidString, true);

                PlayerManager.item_Delay_Run_Map.put(uuidString, new BukkitRunnable() {
                    @Override
                    public void run() {
                        cancel();
                        PlayerManager.item_Delay_Run_Map.remove(uuidString);
                        PlayerManager.item_Delay_Boolean_Map.put(uuidString, false);
                    }
                });

                PlayerManager.item_Delay_Run_Map.get(uuidString).runTaskLater(cd, coolDown);
            }

        }

        //player.sendMessage(""+coodB);

        return coodB;
    }

    /**當經驗值改變時**/
    @EventHandler
    public void onExpChange(PlayerExpChangeEvent event){
        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();
        int amount = event.getAmount();
        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_player_up_exp_type>","default");
        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_player_change_exp_amount>",String.valueOf(amount));

        PlayerTrigger.onPlayer(player, target, "~onexpup");
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
            PlayerTrigger.onPlayer(player, null, "~onleveldown");

            PlayerTrigger.onPlayer(player, target, "~onexpdown");
        }else {
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_up_level_type>","default");
            PlayerTrigger.onPlayer(player, null, "~onlevelup");
        }
    }

    /**當玩家聊天**/
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String uuidString = event.getPlayer().getUniqueId().toString();
        String message = event.getMessage();
        message = ConversionMain.valueOf(player,null,message);


        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_last_chat>",message);

        PlayerTrigger.onPlayer(player, null, "~onchat");


    }
    @EventHandler
    public void onChat(PlayerChatEvent event){
        String uuidString = event.getPlayer().getUniqueId().toString();
        //////////////////////////////////////////////////////////////////////////
        if(EditorGUIManager.menu_EditItem_Chat_Map.get(uuidString) == null){
            EditorGUIManager.menu_EditItem_Chat_Map.put(uuidString, false);
        }
        if(EditorGUIManager.menu_EditItem_Chat_Map.get(uuidString) != null){
            boolean b = EditorGUIManager.menu_EditItem_Chat_Map.get(uuidString);
            if(b){
                EditItem.onChat(event);
            }

        }
        //////////////////////////////////////////////////////////////////////////
        if(EditorGUIManager.menu_SelectItems_Chat_Map.get(uuidString) == null){
            EditorGUIManager.menu_SelectItems_Chat_Map.put(uuidString, false);
        }
        if(EditorGUIManager.menu_SelectItems_Chat_Map.get(uuidString) != null){
            boolean b = EditorGUIManager.menu_SelectItems_Chat_Map.get(uuidString);
            if(b){
                SelectItems.onChat(event);
            }

        }
        //////////////////////////////////////////////////////////////////////////
        if(EditorGUIManager.menu_EditEnchantment_Chat_Map.get(uuidString) == null){
            EditorGUIManager.menu_EditEnchantment_Chat_Map.put(uuidString, false);
        }
        if(EditorGUIManager.menu_EditEnchantment_Chat_Map.get(uuidString) != null){
            boolean b = EditorGUIManager.menu_EditEnchantment_Chat_Map.get(uuidString);
            if(b){
                EditEnchantment.onChat(event);
            }

        }
        //////////////////////////////////////////////////////////////////////////
        if(EditorGUIManager.menu_EditAttributes_Chat_Map.get(uuidString) == null){
            EditorGUIManager.menu_EditAttributes_Chat_Map.put(uuidString, false);
        }
        if(EditorGUIManager.menu_EditAttributes_Chat_Map.get(uuidString) != null){
            boolean b = EditorGUIManager.menu_EditAttributes_Chat_Map.get(uuidString);
            if(b){
                EditAttributes.onChat(event);
            }

        }
        //////////////////////////////////////////////////////////////////////////
        if(EditorGUIManager.menu_EditLore_Chat_Map.get(uuidString) == null){
            EditorGUIManager.menu_EditLore_Chat_Map.put(uuidString, false);
        }
        if(EditorGUIManager.menu_EditLore_Chat_Map.get(uuidString) != null){
            boolean b = EditorGUIManager.menu_EditLore_Chat_Map.get(uuidString);
            if(b){
                EditLore.onChat(event);
            }

        }
        //////////////////////////////////////////////////////////////////////////
        if(EditorGUIManager.menu_EditAction_Chat_Map.get(uuidString) == null){
            EditorGUIManager.menu_EditAction_Chat_Map.put(uuidString, false);
        }
        if(EditorGUIManager.menu_EditAction_Chat_Map.get(uuidString) != null){
            boolean b = EditorGUIManager.menu_EditAction_Chat_Map.get(uuidString);
            if(b){
                EditAction.onChat(event);
            }

        }
        //////////////////////////////////////////////////////////////////////////
        if(EditorGUIManager.menu_EditActionDetail_Chat_Map.get(uuidString) == null){
            EditorGUIManager.menu_EditActionDetail_Chat_Map.put(uuidString, false);
        }
        if(EditorGUIManager.menu_EditActionDetail_Map.get(uuidString) != null){
            boolean b = EditorGUIManager.menu_EditActionDetail_Chat_Map.get(uuidString);
            if(b){
                EditActionDetail.onChat(event);
            }

        }
        //////////////////////////////////////////////////////////////////////////
        if(EditorGUIManager.menu_ActionTargetEdit_Chat_Map.get(uuidString) == null){
            EditorGUIManager.menu_ActionTargetEdit_Chat_Map.put(uuidString, false);
        }
        if(EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString) != null){
            boolean b = EditorGUIManager.menu_ActionTargetEdit_Chat_Map.get(uuidString);
            if(b){
                ActionTargetEdit.onChat(event);
            }

        }

        //////////////////////////////////////////////////////////////////////////


    }


    /**當玩家回血**/
    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event){

        if(event.getEntity() instanceof Player){
            Player player = ((Player) event.getEntity()).getPlayer();

            PlayerTrigger.onPlayer(player, target, "~onregainhealth");
        }

    }
    /**當玩移動**/
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){

        Player player = event.getPlayer();

        PlayerTrigger.onPlayer(player, target, "~onmove");


    }
    /**當玩家死亡**/
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        String uuidString = player.getUniqueId().toString();

        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("config.yml");
        boolean skill = fileConfiguration.getBoolean("Class.Skill");
        if(skill){

            if(PlayerManager.keyF_BossBarSkill_Map.get(uuidString) == null){
                PlayerManager.keyF_BossBarSkill_Map.put(uuidString, new BossBarSkill2());
            }
            PlayerManager.keyF_BossBarSkill_Map.get(uuidString).closeSkill(player);

        }


        PlayerTrigger.onPlayer(player, target, "~ondeath");

    }
    /**當蹲下時**/
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        if(event.isSneaking()){

            PlayerTrigger.onPlayer(player, target, "~onsneak");
        }else {
            PlayerTrigger.onPlayer(player, target, "~onstandup");
        }

    }

    /**當重生時**/
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();

        UUID playerUUID = player.getUniqueId();
        String uuidString = player.getUniqueId().toString();
        if(PlayerManager.getPlayerDataMap().get(playerUUID) != null){
            PlayerTrigger.onPlayer(player, target, "~onkeyfoff");
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
            if(PlayerManager.getPlayerDataMap().get(playerUUID) != null){
                PlayerTrigger.onPlayer(player, target, "~onkeyfoff");
            }
            ListenerManager.getCast_On_Stop().put(uuidString,false);
        }else {
            if(PlayerManager.getPlayerDataMap().get(playerUUID) != null){
                PlayerTrigger.onPlayer(player, target, "~onkeyfon");
            }
            ListenerManager.getCast_On_Stop().put(uuidString,true);
        }


        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("config.yml");
        boolean skill = fileConfiguration.getBoolean("Class.Skill");
        if(skill){
            event.setCancelled(true);
            int key = player.getInventory().getHeldItemSlot();

            boolean b = ListenerManager.getCast_On_Stop().get(uuidString);

            if(PlayerManager.keyF_BossBarSkill_Map.get(uuidString) == null){
                PlayerManager.keyF_BossBarSkill_Map.put(uuidString, new BossBarSkill2());
            }

            if(b){
                PlayerManager.keyF_BossBarSkill_Map.get(uuidString).openSkill(player, key);
            }else {
                PlayerManager.keyF_BossBarSkill_Map.get(uuidString).closeSkill(player);
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
                    PlayerTrigger.onPlayer(player, target, "~onkey1");
                    break;
                case 1:
                    PlayerTrigger.onPlayer(player, target, "~onkey2");
                    break;
                case 2:
                    PlayerTrigger.onPlayer(player, target, "~onkey3");
                    break;
                case 3:
                    PlayerTrigger.onPlayer(player, target, "~onkey4");
                    break;
                case 4:
                    PlayerTrigger.onPlayer(player, target, "~onkey5");
                    break;
                case 5:
                    PlayerTrigger.onPlayer(player, target, "~onkey6");
                    break;
                case 6:
                    PlayerTrigger.onPlayer(player, target, "~onkey7");
                    break;
                case 7:
                    PlayerTrigger.onPlayer(player, target, "~onkey8");
                    break;
                case 8:
                    PlayerTrigger.onPlayer(player, target, "~onkey9");
                    break;
            }
        }
        /**讀取屬性**/
        //boolean attr = fileConfiguration.getBoolean("Class.Attributes");
        //if(attr){
            boolean fOn = ListenerManager.getCast_On_Stop().get(uuidString);
            if(!fOn){
                if(key != key2){
                    PlayerEquipment2.reloadEquipment(player,key);
                }
            }
        //}

    }

    /**關閉背包**/
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        String uuidString = player.getUniqueId().toString();

//        /**讀取屬性**/
////        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("config.yml");
////        boolean attr = fileConfiguration.getBoolean("Class.Skill");
////        if(attr){
////            PlayerEquipment2.reloadEquipment(player,10);
////        }
        if(ListenerManager.getCast_On_Stop().get(uuidString) != null){
            boolean fOn = ListenerManager.getCast_On_Stop().get(uuidString);
            if(!fOn){
                PlayerEquipment2.reloadEquipment(player,10);
            }
        }





    }

    /**材質包狀態**/
    @EventHandler
    public void onResourcePack(PlayerResourcePackStatusEvent event){
        Player player = event.getPlayer();

        ResourcePackSend.send(player,event.getStatus().toString());

    }


}
