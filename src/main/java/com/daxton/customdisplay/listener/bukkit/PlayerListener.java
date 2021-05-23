package com.daxton.customdisplay.listener.bukkit;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.config.SaveConfig;
import com.daxton.customdisplay.api.gui.CustomGuiSet;
import com.daxton.customdisplay.api.item.*;
import com.daxton.customdisplay.api.other.ResourcePackSend;
import com.daxton.customdisplay.api.other.StringConversion;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.api.player.profession.BossBarSkill2;
import com.daxton.customdisplay.api.player.profession.UseSkill;
import com.daxton.customdisplay.config.ConfigManager;
import com.daxton.customdisplay.gui.item.*;
import com.daxton.customdisplay.gui.item.edititem.*;
import com.daxton.customdisplay.gui.item.edititem.editaction.*;
import com.daxton.customdisplay.manager.*;
import com.daxton.customdisplay.manager.player.EditorGUIManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.*;


public class PlayerListener implements Listener {

    private final CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private final ConfigManager configManager = cd.getConfigManager();

    /**登入時**/
    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();


        //設定玩家血量顯示愛心數
        if(configManager.config.getBoolean("HealthScale.enable")){
            player.setHealthScale(configManager.config.getInt("HealthScale.scale"));
        }

        PlayerManager.player_Data_Map.putIfAbsent(uuidString, new PlayerData2(player));

        //玩家登入發送材質包
        ResourcePackSend.send(player,null);
        //設定按鍵F
        ListenerManager.getCast_On_Stop().put(uuidString,false);
        //讀取物品屬性
        PlayerEquipment2.reloadEquipment(player,10);
        //登入觸發動作
        PlayerTrigger.onPlayer(player, null, "~onjoin");
    }
    //登出時
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        String uuidString = playerUUID.toString();
        EditorGUIManager.custom_Inventory_Boolean_Map.put(uuidString, false);
        EditorGUIManager.custom_Inventory_GuiID_Map.remove(uuidString);
        //儲存玩家設定檔
        SaveConfig.savePlayerConfig(player);
        PlayerTrigger.onPlayer(player, null, "~onquit");
        ListenerManager.getCast_On_Stop().put(uuidString,false);

    }
    //當打開背包時
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
        if(EditorGUIManager.custom_Inventory_Boolean_Map.get(uuidString) != null){
            if(EditorGUIManager.custom_Inventory_Boolean_Map.get(uuidString)){
                CustomGuiSet.onInventoryClick(event);
            }
        }
//        if(EditorGUIManager.custom_Inventory_Map.get(uuidString) != null){
//            if(EditorGUIManager.custom_Inventory_Map.get(uuidString) == inventory){
//                CustomInventory3.onInventoryClick(event);
//            }
//        }
        ///////////////////////////////

        ///////////////////////////////


//        if(ActionManager.playerUUID_taskID_Map.get(uuidString) != null){
//            String taskID = ActionManager.playerUUID_taskID_Map.get(uuidString);
//
//            if(ActionManager.taskID_Inventory_Map.get(taskID) != null){
//                if(ActionManager.taskID_Inventory_Map.get(taskID) == inventory) {
//
//                    if(ActionManager.judgment_Inventory_Map.get(taskID) != null){
//                        ActionManager.judgment_Inventory_Map.get(taskID).InventoryListener(event);
//                    }
//
//
//                }
//            }
//
//        }


    }

    /**當玩家點擊時**/
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){

        EquipmentSlot equipmentSlot = event.getHand();

        Player player = event.getPlayer();
        Action action = event.getAction();

        if(action == Action.LEFT_CLICK_AIR){
            if(getItemLeftCoodDown(player)){
                event.setCancelled(true);
                return;
            }
            PlayerTrigger.onPlayer(player, null, "~leftclickair");
        }
        if(action == Action.LEFT_CLICK_BLOCK){
            if(getItemLeftCoodDown(player)){
                event.setCancelled(true);
                return;
            }
            PlayerTrigger.onPlayer(player, null, "~leftclickblock");
        }

        if(equipmentSlot == EquipmentSlot.HAND){
            if(action == Action.RIGHT_CLICK_AIR){

                if(getItemRightCoodDown(player)){
                    event.setCancelled(true);
                    return;
                }
                PlayerTrigger.onPlayer(player, null, "~rightclickair");
                //CustomDisplay.getCustomDisplay().getLogger().info(equipmentSlot.toString()+"~rightclickair");
            }
            if(action == Action.RIGHT_CLICK_BLOCK){
                if(getItemRightCoodDown(player)){
                    event.setCancelled(true);
                    return;
                }
                PlayerTrigger.onPlayer(player, null, "~rightclickblock");

            }
        }


        if(action == Action.PHYSICAL){
            PlayerTrigger.onPlayer(player, null, "~pressureplate");
        }


    }
    //左鍵CD
    public boolean getItemLeftCoodDown(Player player){
        boolean coodB = false;

        String uuidString = player.getUniqueId().toString();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if(itemStack.getType() != Material.AIR){
            String coolDownString = itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(cd, "CoolDownLeftClick"), PersistentDataType.STRING);

            if(coolDownString != null){
                int coolDown = StringConversion.getInt(player, null, 0, coolDownString);
                //player.sendMessage("左CD: "+coolDown);

                if(PlayerManager.item_Delay_Left_Boolean_Map.get(uuidString) != null){
                    coodB = PlayerManager.item_Delay_Left_Boolean_Map.get(uuidString);
                }
                if(coolDown > 0){

                    if(PlayerManager.item_Delay_Left_Run_Map.get(uuidString) == null){
                        player.setCooldown(itemStack.getType(),coolDown);
                        PlayerManager.item_Delay_Left_Boolean_Map.put(uuidString, true);

                        PlayerManager.item_Delay_Left_Run_Map.put(uuidString, new BukkitRunnable() {
                            @Override
                            public void run() {
                                cancel();
                                PlayerManager.item_Delay_Left_Run_Map.remove(uuidString);
                                PlayerManager.item_Delay_Left_Boolean_Map.put(uuidString, false);
                            }
                        });

                        PlayerManager.item_Delay_Left_Run_Map.get(uuidString).runTaskLater(cd, coolDown);
                    }

                }
            }

        }

        return coodB;
    }
    //右鍵CD
    public boolean getItemRightCoodDown(Player player){
        boolean coodB = false;

        String uuidString = player.getUniqueId().toString();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if(itemStack.getType() != Material.AIR){
            String coolDownString = itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(cd, "CoolDownRightClick"), PersistentDataType.STRING);

            if(coolDownString != null){
                int coolDown = StringConversion.getInt(player, null, 0, coolDownString);
                //player.sendMessage("右CD: "+coolDown);

                if(PlayerManager.item_Delay_Right_Boolean_Map.get(uuidString) != null){
                    coodB = PlayerManager.item_Delay_Right_Boolean_Map.get(uuidString);
                }
                if(coolDown > 0){

                    if(PlayerManager.item_Delay_Right_Run_Map.get(uuidString) == null){
                        player.setCooldown(itemStack.getType(),coolDown);
                        PlayerManager.item_Delay_Right_Boolean_Map.put(uuidString, true);

                        PlayerManager.item_Delay_Right_Run_Map.put(uuidString, new BukkitRunnable() {
                            @Override
                            public void run() {
                                cancel();
                                PlayerManager.item_Delay_Right_Run_Map.remove(uuidString);
                                PlayerManager.item_Delay_Right_Boolean_Map.put(uuidString, false);
                            }
                        });

                        PlayerManager.item_Delay_Right_Run_Map.get(uuidString).runTaskLater(cd, coolDown);
                    }

                }
            }

        }

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

        PlayerTrigger.onPlayer(player, null, "~onexpup");
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

            PlayerTrigger.onPlayer(player, null, "~onexpdown");
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
        EditorGUIManager.menu_EditItem_Chat_Map.putIfAbsent(uuidString, false);
        if(EditorGUIManager.menu_EditItem_Chat_Map.get(uuidString) != null){
            boolean b = EditorGUIManager.menu_EditItem_Chat_Map.get(uuidString);
            if(b){
                EditItem.onChat(event);
            }

        }
        //////////////////////////////////////////////////////////////////////////
        EditorGUIManager.menu_SelectItems_Chat_Map.putIfAbsent(uuidString, false);
        if(EditorGUIManager.menu_SelectItems_Chat_Map.get(uuidString) != null){
            boolean b = EditorGUIManager.menu_SelectItems_Chat_Map.get(uuidString);
            if(b){
                SelectItems.onChat(event);
            }

        }
        //////////////////////////////////////////////////////////////////////////
        EditorGUIManager.menu_EditEnchantment_Chat_Map.putIfAbsent(uuidString, false);
        if(EditorGUIManager.menu_EditEnchantment_Chat_Map.get(uuidString) != null){
            boolean b = EditorGUIManager.menu_EditEnchantment_Chat_Map.get(uuidString);
            if(b){
                EditEnchantment.onChat(event);
            }

        }
        //////////////////////////////////////////////////////////////////////////
        EditorGUIManager.menu_EditAttributes_Chat_Map.putIfAbsent(uuidString, false);
        if(EditorGUIManager.menu_EditAttributes_Chat_Map.get(uuidString) != null){
            boolean b = EditorGUIManager.menu_EditAttributes_Chat_Map.get(uuidString);
            if(b){
                EditAttributes.onChat(event);
            }

        }
        //////////////////////////////////////////////////////////////////////////
        EditorGUIManager.menu_EditLore_Chat_Map.putIfAbsent(uuidString, false);
        if(EditorGUIManager.menu_EditLore_Chat_Map.get(uuidString) != null){
            boolean b = EditorGUIManager.menu_EditLore_Chat_Map.get(uuidString);
            if(b){
                EditLore.onChat(event);
            }

        }
        //////////////////////////////////////////////////////////////////////////
        EditorGUIManager.menu_EditAction_Chat_Map.putIfAbsent(uuidString, false);
        if(EditorGUIManager.menu_EditAction_Chat_Map.get(uuidString) != null){
            boolean b = EditorGUIManager.menu_EditAction_Chat_Map.get(uuidString);
            if(b){
                EditAction.onChat(event);
            }

        }
        //////////////////////////////////////////////////////////////////////////
        EditorGUIManager.menu_EditActionDetail_Chat_Map.putIfAbsent(uuidString, false);
        if(EditorGUIManager.menu_EditActionDetail_Map.get(uuidString) != null){
            boolean b = EditorGUIManager.menu_EditActionDetail_Chat_Map.get(uuidString);
            if(b){
                EditActionDetail.onChat(event);
            }

        }
        //////////////////////////////////////////////////////////////////////////
        EditorGUIManager.menu_ActionTargetEdit_Chat_Map.putIfAbsent(uuidString, false);
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
        Entity entity = event.getEntity();
        if(entity instanceof Player){
            Player player = (Player) entity;
            PlayerTrigger.onPlayer(player, null, "~onregainhealth");
        }

    }
    /**當玩移動**/
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){

        Player player = event.getPlayer();

        PlayerTrigger.onPlayer(player, null, "~onmove");


    }
    /**當玩家死亡**/
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        if(player != null){
            String uuidString = player.getUniqueId().toString();

            FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("config.yml");
            boolean skill = fileConfiguration.getBoolean("Class.Skill");
            if(skill){

                if(PlayerManager.keyF_BossBarSkill_Map.get(uuidString) == null){
                    PlayerManager.keyF_BossBarSkill_Map.put(uuidString, new BossBarSkill2());
                }
                PlayerManager.keyF_BossBarSkill_Map.get(uuidString).closeSkill(player);

            }


            PlayerTrigger.onPlayer(player, null, "~ondeath");
        }


    }
    /**當蹲下時**/
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        if(event.isSneaking()){

            PlayerTrigger.onPlayer(player, null, "~onsneak");
        }else {
            PlayerTrigger.onPlayer(player, null, "~onstandup");
        }

    }

    /**當重生時**/
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();

        UUID playerUUID = player.getUniqueId();
        String uuidString = player.getUniqueId().toString();
        if(PlayerManager.getPlayerDataMap().get(playerUUID) != null){
            PlayerTrigger.onPlayer(player, null, "~onkeyfoff");
            ListenerManager.getCast_On_Stop().put(uuidString,false);
        }

    }


    //當按下F鍵
    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event){
        Player player = event.getPlayer();

        String uuidString = player.getUniqueId().toString();
        if(ListenerManager.getCast_On_Stop().get(uuidString)){

            PlayerTrigger.onPlayer(player, null, "~onkeyfoff");

            ListenerManager.getCast_On_Stop().put(uuidString,false);
        }else {

            PlayerTrigger.onPlayer(player, null, "~onkeyfon");

            ListenerManager.getCast_On_Stop().put(uuidString,true);
        }


        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("config.yml");
        String skill = fileConfiguration.getString("AttackCore");
        if(skill != null && skill.toLowerCase().contains("customcore")){
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
        String skill = fileConfiguration.getString("AttackCore");
        if(skill != null && skill.toLowerCase().contains("customcore")){
            boolean fOn = ListenerManager.getCast_On_Stop().get(uuidString);
            if(fOn){
                event.setCancelled(true);
                //player.sendMessage(key+" : "+key2);
                if(key != key2){
                    new UseSkill().use(player, key, key2);
                }
            }

        }else {
            switch(key){
                case 0:
                    PlayerTrigger.onPlayer(player, null, "~onkey1");
                    break;
                case 1:
                    PlayerTrigger.onPlayer(player, null, "~onkey2");
                    break;
                case 2:
                    PlayerTrigger.onPlayer(player, null, "~onkey3");
                    break;
                case 3:
                    PlayerTrigger.onPlayer(player, null, "~onkey4");
                    break;
                case 4:
                    PlayerTrigger.onPlayer(player, null, "~onkey5");
                    break;
                case 5:
                    PlayerTrigger.onPlayer(player, null, "~onkey6");
                    break;
                case 6:
                    PlayerTrigger.onPlayer(player, null, "~onkey7");
                    break;
                case 7:
                    PlayerTrigger.onPlayer(player, null, "~onkey8");
                    break;
                case 8:
                    PlayerTrigger.onPlayer(player, null, "~onkey9");
                    break;
            }
        }


        //讀取屬性
        boolean fOn = ListenerManager.getCast_On_Stop().get(uuidString);
        if(!fOn){
            if(key != key2){
                PlayerEquipment2.reloadEquipment(player,key);
            }
        }

    }

    /**關閉背包**/
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        String uuidString = player.getUniqueId().toString();
        EditorGUIManager.custom_Inventory_Boolean_Map.put(uuidString, false);
        EditorGUIManager.custom_Inventory_GuiID_Map.remove(uuidString);
        //讀取屬性
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
