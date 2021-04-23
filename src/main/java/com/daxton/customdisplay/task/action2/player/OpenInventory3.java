package com.daxton.customdisplay.task.action2.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.config.CustomLineConfigList;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.player.EditorGUIManager;
import com.daxton.customdisplay.task.action.player.OpenInventoryBindGui2;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.util.*;

public class OpenInventory3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    public LivingEntity target = null;
    public String taskID = "";


    public String function = "";

    public String GuiID = "Default";
    public int amount = 27;


    public String skillNowName = "null";
    public int next = 0;

    private Map<Integer,Integer> RawSlot = new HashMap<>();
    private Map<Integer,Boolean> Move = new HashMap<>();

    private Map<Integer,List<CustomLineConfig>> left_Shift_Click = new HashMap<>();
    private Map<Integer,List<CustomLineConfig>> right_Shift_Click = new HashMap<>();
    private Map<Integer,List<CustomLineConfig>> left_Click = new HashMap<>();
    private Map<Integer,List<CustomLineConfig>> right_Click = new HashMap<>();

    public OpenInventory3(){


    }

    public void setInventory(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        //cd.getLogger().info("進入"+taskID);
        this.self = self;
        this.target = target;
        this.taskID = taskID;
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, this.self, this.target);
        /**獲得功能**/
        function = actionMapHandle.getString(new String[]{"function","fc"},"");

        /**獲得GUIID**/
        GuiID = actionMapHandle.getString(new String[]{"guiid"},"Default");

        /**獲得數量**/
        amount =  actionMapHandle.getInt(new String[]{"amount","a"},27);

        /**獲得內容**/
        String message = actionMapHandle.getString(new String[]{"message","m"},"");

        if(self instanceof Player){
            Player player = (Player) self;

            if(function.toLowerCase().contains("gui")){
                openGui(player,amount,message,GuiID,taskID);
            }else if(function.toLowerCase().contains("close")){
                player.closeInventory();
            }else if(function.toLowerCase().contains("bind")){
                next = next + 0;
                skillNowName = new OpenInventoryBindGui2().openBindGui(player,target,taskID,next);
            }
            else {
                openInventory(player,amount,message,taskID);
            }
        }


    }


    /**打開臨時背包**/
    public void openInventory(Player player,int amount,String message,String taskID){

        if(ActionManager.taskID_Inventory_Map.get(taskID) == null){
            ActionManager.taskID_Inventory_Map.put(taskID, Bukkit.createInventory(null, amount , message));
            ActionManager.playerUUID_taskID_Map.put(player.getUniqueId().toString(),taskID);
        }
        if(ActionManager.taskID_Inventory_Map.get(taskID) != null){
            Inventory inventory = ActionManager.taskID_Inventory_Map.get(taskID);
            player.openInventory(inventory);
        }

    }
    /**打開GUI**/
    public void openGui(Player player,int amount,String message,String guiID,String taskID){

        String uuidString = player.getUniqueId().toString();

        if(EditorGUIManager.open_Inventory_Map.get(uuidString) == null){
            EditorGUIManager.open_Inventory_Map.put(uuidString, setInventory(player,Bukkit.createInventory(null,amount , message),guiID));
            Inventory inventory = EditorGUIManager.open_Inventory_Map.get(uuidString);
            player.openInventory(inventory);

        }else {
            EditorGUIManager.open_Inventory_Map.remove(uuidString);
            EditorGUIManager.open_Inventory_Map.put(uuidString, setInventory(player,Bukkit.createInventory(null,amount , message),guiID));
            Inventory inventory = EditorGUIManager.open_Inventory_Map.get(uuidString);
            player.openInventory(inventory);

        }

    }

    /**設定GUI內容**/
    public Inventory setInventory(Player player, Inventory inventory,String guiID){

        File itemFilePatch = new File(cd.getDataFolder(),"Gui/"+guiID+".yml");
        FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(itemFilePatch);
        ConfigurationSection button = itemConfig.getConfigurationSection("Buttons");
        for(int i = 0; i < inventory.getSize() ;i++){

            inventory.setItem(i,null);
        }
        for(String key : button.getKeys(false)){

                int rawslot = itemConfig.getInt("Buttons."+key+".RawSlot");
                boolean move = itemConfig.getBoolean("Buttons."+key+".Move");
                int cmd = itemConfig.getInt("Buttons."+key+".CustomModelData");
                boolean flag = itemConfig.getBoolean("Buttons."+key+".RemoveItemFlags");
                String itemMaterial = itemConfig.getString("Buttons."+key+".Material");
                String itemName = itemConfig.getString("Buttons."+key+".Name");
                itemName = new ConversionMain().valueOf(self,target,itemName);
                List<String> itemLore = itemConfig.getStringList("Buttons."+key+".Lore");
                List<String> nextItemLore = new ArrayList<>();
                itemLore.forEach((line) -> {
                    nextItemLore.add(ChatColor.GRAY + line);
                });
                List<String> lastItemLore = new ArrayList<>();
                nextItemLore.forEach((line) -> {
                    lastItemLore.add(ChatColor.GRAY + new ConversionMain().valueOf(self,target,line));
                });
                List<String> leftClick = itemConfig.getStringList("Buttons."+key+".Left");
                List<String> leftShiftClick = itemConfig.getStringList("Buttons."+key+".Left_Shift");
                List<String> rightClick = itemConfig.getStringList("Buttons."+key+".Right");
                List<String> rightShiftClick = itemConfig.getStringList("Buttons."+key+".Right_Shift");

            List<CustomLineConfig> leftClickCustom = new CustomLineConfigList().valueOf(leftClick);
            List<CustomLineConfig> leftShiftClickCustom = new CustomLineConfigList().valueOf(leftShiftClick);
            List<CustomLineConfig> rightClickCustom = new CustomLineConfigList().valueOf(rightClick);
            List<CustomLineConfig> rightShiftClickCustom = new CustomLineConfigList().valueOf(rightShiftClick);



                Material material = Enum.valueOf(Material.class,itemMaterial.replace(" ","").toUpperCase());

                ItemStack customItem = new ItemStack(material);


            if(itemMaterial.contains("PLAYER_HEAD")){
                SkullMeta skullMeta = (SkullMeta) customItem.getItemMeta();
                String headValue = itemConfig.getString("Buttons."+key+".HeadValue");
                if(headValue != null){
                    if(headValue.length() < 50){
                        headValue = new ConversionMain().valueOf(self,target,headValue);
                        OfflinePlayer targetPlayer = player.getServer().getOfflinePlayer(headValue);
                        skullMeta.setOwningPlayer(targetPlayer);
                        customItem.setItemMeta(skullMeta);
                    }else {
                        try {
                            PlayerProfile playerProfile = Bukkit.createProfile(UUID.randomUUID(), null);
                            playerProfile.getProperties().add(new ProfileProperty("textures", headValue));
                            skullMeta.setPlayerProfile(playerProfile);
                            customItem.setItemMeta(skullMeta);
                        }catch (Exception exception){
                            cd.getLogger().info("頭的值只能在paper伺服器使用。");
                            cd.getLogger().info("The value of the header can only be used on the paper server.");
                        }
                    }

                }
            }


                ItemMeta im = customItem.getItemMeta();
                im.setDisplayName(itemName);
                im.setLore(lastItemLore);
                im.setCustomModelData(cmd);



                if(flag){
                    im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    im.addItemFlags(ItemFlag.HIDE_DESTROYS);
                    im.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                    im.addItemFlags(ItemFlag.HIDE_DYE);
                }

                RawSlot.put(rawslot,rawslot);
                Move.put(rawslot,!move);
                right_Click.put(rawslot,rightClickCustom);
                left_Click.put(rawslot,leftClickCustom);
                right_Shift_Click.put(rawslot,rightShiftClickCustom);
                left_Shift_Click.put(rawslot,leftShiftClickCustom);

                customItem.setItemMeta(im);
                inventory.setItem(rawslot,customItem);



        }

        return inventory;
    }

    /**GUI動作監聽**/
    public static void onInventoryClick(InventoryClickEvent event){


        Player player = (Player) event.getWhoClicked();
        String uuidString = player.getUniqueId().toString();
        if(EditorGUIManager.menu_OpenInventory_Map.get(uuidString) != null){

            Map<Integer,Integer> RawSlot = EditorGUIManager.menu_OpenInventory_Map.get(uuidString).RawSlot;
            Map<Integer,Boolean> Move = EditorGUIManager.menu_OpenInventory_Map.get(uuidString).Move;

            Map<Integer,List<CustomLineConfig>> left_Shift_Click = EditorGUIManager.menu_OpenInventory_Map.get(uuidString).left_Shift_Click;
            Map<Integer,List<CustomLineConfig>> right_Shift_Click = EditorGUIManager.menu_OpenInventory_Map.get(uuidString).right_Shift_Click;
            Map<Integer,List<CustomLineConfig>> left_Click = EditorGUIManager.menu_OpenInventory_Map.get(uuidString).left_Click;
            Map<Integer,List<CustomLineConfig>> right_Click = EditorGUIManager.menu_OpenInventory_Map.get(uuidString).right_Click;

            String function = EditorGUIManager.menu_OpenInventory_Map.get(uuidString).function;
            int next  = EditorGUIManager.menu_OpenInventory_Map.get(uuidString).next;
            String skillNowName = EditorGUIManager.menu_OpenInventory_Map.get(uuidString).skillNowName;
            String taskID = EditorGUIManager.menu_OpenInventory_Map.get(uuidString).taskID;
            LivingEntity target = EditorGUIManager.menu_OpenInventory_Map.get(uuidString).target;

            int i = event.getRawSlot();
            if(RawSlot.get(i) != null && RawSlot.get(i) == i){
                event.setCancelled(Move.get(i));

                if(event.getClick().toString().contains("LEFT")){

                    new PlayerTrigger(player).onSkill(player, null, left_Click.get(i));
                }
                if(event.getClick().toString().contains("SHIFT_LEFT")){
                    new PlayerTrigger(player).onSkill(player, null, left_Shift_Click.get(i));
                }
                if(event.getClick().toString().contains("RIGHT")){
                    new PlayerTrigger(player).onSkill(player, null, right_Click.get(i));
                }
                if(event.getClick().toString().contains("SHIFT_RIGHT")){
                    new PlayerTrigger(player).onSkill(player, null, right_Shift_Click.get(i));
                }

            }


            if(function.toLowerCase().contains("bind")){
                event.setCancelled(true);
                if(i == 17){
                    if(event.getClick().toString().contains("LEFT")){
                        next = next + 1;
                        skillNowName = new OpenInventoryBindGui2().openBindGui(player,target,taskID,next);
                        //openBindGui(player,next);
                    }
                    if(event.getClick().toString().contains("RIGHT")){
                        next = next - 1;
                        skillNowName = new OpenInventoryBindGui2().openBindGui(player,target,taskID,next);
                        //openBindGui(player,next);
                    }
                }
                if(i > 26 && i < 35){
                    if(event.getClick().toString().contains("LEFT")){
                        new OpenInventoryBindGui2().setBind(player,i,skillNowName,taskID);

                    }
                    if(event.getClick().toString().contains("RIGHT")){
                        new OpenInventoryBindGui2().removeBind(player,i,taskID);

                    }
                }

            }

        }




    }

}
