package com.daxton.customdisplay.api.item.gui;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.item.ItemSet;
import com.daxton.customdisplay.api.item.MenuItem;
import com.daxton.customdisplay.api.item.MenuSet;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemListMenu {

    public Map<Integer,Integer> RawSlot = new HashMap<>();

    public Map<Integer,String> materialName = new HashMap<>();

    public String itemName;

    public String typeName;

    public int nextPageCount;
    public boolean haveNext;

    public int previousPageCount;
    public boolean havePrevioust;


    public ItemListMenu(){

    }

    public void openMenu(Player player, String typeName, String itemName, int itemCount){
        this.nextPageCount = itemCount;

        String uuidString = player.getUniqueId().toString();
        if(PlayerManager.menu_Inventory6_Map.get(uuidString) == null){
            PlayerManager.menu_Inventory6_Map.put(uuidString, getInventory(typeName, itemName, itemCount));
            Inventory inventory = PlayerManager.menu_Inventory6_Map.get(uuidString);
            player.openInventory(inventory);
        }else{
            PlayerManager.menu_Inventory6_Map.remove(uuidString);
            PlayerManager.menu_Inventory6_Map.put(uuidString, getInventory(typeName, itemName, itemCount));
            Inventory inventory = PlayerManager.menu_Inventory6_Map.get(uuidString);
            player.openInventory(inventory);
        }

    }

    //按鈕監聽
    public static void onInventoryClick(InventoryClickEvent event){
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        String uuidString = player.getUniqueId().toString();
        if(PlayerManager.menu_Inventory_ItemListMenu_Map.get(uuidString) != null){
            int i = event.getRawSlot();
            Map<Integer,Integer> RawSlot = PlayerManager.menu_Inventory_ItemListMenu_Map.get(uuidString).RawSlot;
            Map<Integer,String> materialName = PlayerManager.menu_Inventory_ItemListMenu_Map.get(uuidString).materialName;
            String typeName = PlayerManager.menu_Inventory_ItemListMenu_Map.get(uuidString).typeName;
            String itemName = PlayerManager.menu_Inventory_ItemListMenu_Map.get(uuidString).itemName;
            int nextPageCount = PlayerManager.menu_Inventory_ItemListMenu_Map.get(uuidString).nextPageCount;
            boolean haveNext = PlayerManager.menu_Inventory_ItemListMenu_Map.get(uuidString).haveNext;
            int previousPageCount = PlayerManager.menu_Inventory_ItemListMenu_Map.get(uuidString).previousPageCount;
            boolean havePrevioust = PlayerManager.menu_Inventory_ItemListMenu_Map.get(uuidString).havePrevioust;

            ItemSet itemSet = new ItemSet(player, typeName, itemName);
            if(event.getClick() == ClickType.LEFT){

                if(RawSlot.get(i) != null && RawSlot.get(i) == i){
                    itemSet.setMaterial(materialName.get(i));
                    itemSet.openItemListMenu(nextPageCount-28);
                }


                //給物品
                if(i == 4){
                    itemSet.giveItem();
                }

                if(i == 8){
                    player.closeInventory();
                }

                if(i == 0){
                    PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString).openMenu(player, typeName, itemName);
                }
                if(havePrevioust && i == 45){
                    PlayerManager.menu_Inventory_ItemListMenu_Map.get(uuidString).openMenu(player, typeName, itemName, previousPageCount);
                }
                if(haveNext && i == 53){
                    PlayerManager.menu_Inventory_ItemListMenu_Map.get(uuidString).openMenu(player, typeName, itemName, nextPageCount);
                }




            }

        }


    }


    public Inventory getInventory(String typeName, String itemName, int itemCount){
        this.typeName = typeName;
        this.itemName = itemName;
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+typeName+".yml");

        Inventory inventory = Bukkit.createInventory(null, 54 , typeName);

        int i = 10;
        this.havePrevioust = false;
        if(itemCount > 0){
            this.havePrevioust = true;
            this.previousPageCount = this.nextPageCount-28;
            inventory.setItem(45, MenuSet.getItemMenuButtom("ItemsButtom","PreviousPage"));
        }
        this.RawSlot.clear();

        Material[] stringArray = Material.values();
        int itmeAmount = stringArray.length-112;
        for(int k = 0+itemCount; k < stringArray.length ;k++){
            if(i == 44){
                break;
            }
            if(i == 17 || i == 26 || i == 35 || i == 44){
                i+=2;
            }
            this.RawSlot.put(i,i);
            this.materialName.put(i,stringArray[k].getKey().getKey());
            inventory.setItem(i, new ItemStack(stringArray[k]));
            i++;
            this.nextPageCount++;
        }

        this.haveNext = false;
        if(itmeAmount-itemCount > 28){
            this.haveNext = true;
            inventory.setItem(53,MenuSet.getItemMenuButtom("ItemsButtom","NextPage"));
        }

        inventory.setItem(4, MenuItem.valueOf(itemMenuConfig, itemName));
        inventory.setItem(0, MenuSet.getItemMenuButtom("ItemListButtom","ToTypeMenu"));
        inventory.setItem(8,MenuSet.getItemMenuButtom("ItemsButtom","CloseMenu"));
        inventory.setItem(47,MenuSet.getItemMenuButtom("ItemsButtom","Delete"));
        inventory.setItem(49,MenuSet.getItemMenuButtom("ItemsButtom","Description"));
        inventory.setItem(51,MenuSet.getItemMenuButtom("ItemsButtom","Create"));

        return inventory;
    }

}
