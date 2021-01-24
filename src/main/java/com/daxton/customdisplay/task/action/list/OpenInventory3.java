package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenInventory3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";
    private Player playerTest = null;

    private String function = "";
    private String message = "";
    private String GuiID = "Default";
    private int amount = 27;


    private String skillNowName = "";
    private int next = 0;

    private Map<Integer,Integer> RawSlot = new HashMap<>();
    private Map<Integer,Boolean> Move = new HashMap<>();

    private Map<Integer,List<String>> left_Shift_Click = new HashMap<>();
    private Map<Integer,List<String>> right_Shift_Click = new HashMap<>();
    private Map<Integer,List<String>> left_Click = new HashMap<>();
    private Map<Integer,List<String>> right_Click = new HashMap<>();

    public OpenInventory3(){


    }

    public void setInventory(LivingEntity self, LivingEntity target, String firstString, String taskID){

        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;


        setOther();
    }

    public void setOther(){
        for(String allString : new StringFind().getStringMessageList(firstString)){
            if(allString.toLowerCase().contains("function=") || allString.toLowerCase().contains("fc=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    function = strings[1];

                }
            }

            if(allString.toLowerCase().contains("message=") || allString.toLowerCase().contains("m=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    message = strings[1];

                }
            }

            if(allString.toLowerCase().contains("guiid=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    GuiID = strings[1];
                }
            }


            if(allString.toLowerCase().contains("amount=") || allString.toLowerCase().contains("a=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    try{
                        amount = Integer.valueOf(strings[1]);
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("amount=內只能放整數數字");
                    }
                }
            }
        }

        if(self instanceof Player){
            Player player = ((Player) self).getPlayer();
            playerTest = player;
            if(function.toLowerCase().contains("gui")){
                openGui(player);
            }else if(function.toLowerCase().contains("close")){
                player.closeInventory();
            }else if(function.toLowerCase().contains("bind")){
                openBind(player,taskID);
            }else if(function.toLowerCase().contains("inv")){
                openInventory(player);
            }else {
                player.closeInventory();
            }

        }

    }

    public void openBind(Player player,String taskID){
        if(ActionManager.getInventory_Map().get(taskID) == null){
            ActionManager.getInventory_Map().put(taskID,Bukkit.createInventory(null,54 , "綁定介面"));
            Inventory inventory = ActionManager.getInventory_Map().get(taskID);
            player.openInventory(inventory);
        }else {
            Inventory inventory = ActionManager.getInventory_Map().get(taskID);
            player.openInventory(inventory);
        }
    }

    /**打開空背包**/
    public void openInventory(Player player){
        if(ActionManager.getInventory_Map().get(taskID) == null){
            ActionManager.getInventory_Map().put(taskID,Bukkit.createInventory(null,amount , message));
            Inventory inventory = ActionManager.getInventory_Map().get(taskID);
            player.openInventory(inventory);
        }else {
            Inventory inventory = ActionManager.getInventory_Map().get(taskID);
            player.openInventory(inventory);
        }
    }
    /**打開Gui背包**/
    public void openGui(Player player){
        if(ActionManager.getInventory_Map().get(taskID) == null){
            ActionManager.getInventory_Map().put(taskID,Bukkit.createInventory(null,amount , message));
            ActionManager.getInventory_name_Map().put(player.getUniqueId(),taskID);
            Inventory inventory = ActionManager.getInventory_Map().get(taskID);
            inventory = setInventory(inventory);
            player.openInventory(inventory);
        }else {
            Inventory inventory = ActionManager.getInventory_Map().get(taskID);
            inventory = setInventory(inventory);
            player.openInventory(inventory);
        }
    }

    public Inventory setInventory(Inventory inventory){

        File itemFilePatch = new File(cd.getDataFolder(),"Gui/"+GuiID+".yml");
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


            Material material = Enum.valueOf(Material.class,itemMaterial.replace(" ","").toUpperCase());

            ItemStack customItem = new ItemStack(material);

            if(itemMaterial.contains("PLAYER_HEAD")){
                SkullMeta skullMeta = (SkullMeta) customItem.getItemMeta();
                skullMeta.setOwningPlayer(playerTest);
                customItem.setItemMeta(skullMeta);
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
            right_Click.put(rawslot,rightClick);
            left_Click.put(rawslot,leftClick);
            right_Shift_Click.put(rawslot,rightShiftClick);
            left_Shift_Click.put(rawslot,leftShiftClick);

            customItem.setItemMeta(im);
            inventory.setItem(rawslot,customItem);



        }

        return inventory;
    }

    public void InventoryListener(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();


        int i = event.getRawSlot();
        if(RawSlot.get(i) != null && RawSlot.get(i) == i){
            event.setCancelled(Move.get(i));
            if(event.getClick().toString().contains("LEFT")){
                new PlayerTrigger(player).onGuiClick(player,left_Click.get(i));
            }
            if(event.getClick().toString().contains("SHIFT_LEFT")){
                new PlayerTrigger(player).onGuiClick(player,left_Shift_Click.get(i));
            }
            if(event.getClick().toString().contains("RIGHT")){
                new PlayerTrigger(player).onGuiClick(player,right_Click.get(i));
            }
            if(event.getClick().toString().contains("SHIFT_RIGHT")){
                new PlayerTrigger(player).onGuiClick(player,right_Shift_Click.get(i));
            }

        }



    }


}
