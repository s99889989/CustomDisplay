package com.daxton.customdisplay.api.item.gui;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.item.MenuSet;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.*;

public class ItemMenuMain {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public Map<Integer,Integer> RawSlot = new HashMap<>();

    public Map<Integer,String> typeName = new HashMap<>();

    public List<String> dataNameList = new ArrayList<>();

    public ItemMenuMain(){

    }

    public void openMenu(Player player){
        String uuidString = player.getUniqueId().toString();
        if(PlayerManager.menu_Inventory_Map.get(uuidString) == null){
            PlayerManager.menu_Inventory_Map.put(uuidString, getInventory());
        }
        if(PlayerManager.menu_Inventory_Map.get(uuidString) != null){
            Inventory inventory = PlayerManager.menu_Inventory_Map.get(uuidString);
            player.openInventory(inventory);

        }

    }

    public Inventory getInventory(){
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_ItemMenu.yml");
        String menuName = itemMenuConfig.getString("MenuName");
        Inventory inventory = Bukkit.createInventory(null, 54 , menuName);

        int i = 10;
        String[] strings = MenuSet.getItemMenuButtomNameArray(itemMenuConfig, "Items");
        for(String key : strings){

            this.RawSlot.put(i,i);
            this.typeName.put(i,key);

            inventory.setItem(i,MenuSet.getItemMenuButtom("Items",key));
            i++;
        }
        inventory.setItem(8, MenuSet.getItemMenuButtom("ItemsButtom","CloseMenu"));
        inventory.setItem(49, MenuSet.getItemMenuButtom("ItemsButtom","SaveData"));

        return inventory;
    }

    //按鈕監聽
    public static void onInventoryClick(InventoryClickEvent event){
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        String uuidString = player.getUniqueId().toString();

        int i = event.getRawSlot();
        if(PlayerManager.menu_Inventory_ItemMenu_Map.get(uuidString) != null){
            Map<Integer,Integer> RawSlot = PlayerManager.menu_Inventory_ItemMenu_Map.get(uuidString).RawSlot;
            Map<Integer,String> typeName = PlayerManager.menu_Inventory_ItemMenu_Map.get(uuidString).typeName;
            if(RawSlot.get(i) != null && RawSlot.get(i) == i){

                if(event.getClick() == ClickType.LEFT){
                    if(PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString) == null){
                        PlayerManager.menu_Inventory_ItemMenuType_Map.put(uuidString, new ItemMenuType());
                    }
                    if(PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString) != null){
                        PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString).openMenu(player,typeName.get(i),0);
                    }
                }


            }
            if(i == 8){
                player.closeInventory();
            }
            if(i == 49){
                //儲存物品資訊
                FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_ItemMenu.yml");
                String[] strings = MenuSet.getItemMenuButtomNameArray(itemMenuConfig, "Items");
                for(String name : strings){

                    FileConfiguration itemConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+ name +".yml");

                    File itemPatch = new File(CustomDisplay.getCustomDisplay().getDataFolder(),"Items/item/"+ name +".yml");
                    try {
                        itemConfig.save(itemPatch);
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }
                }


            }
        }

    }





}
