package com.daxton.customdisplay.gui.item.edititem;

import com.daxton.customdisplay.api.item.gui.ButtomSet;
import com.daxton.customdisplay.gui.item.ItemSet;
import com.daxton.customdisplay.api.item.MenuItem;
import com.daxton.customdisplay.api.item.gui.MenuSet;
import com.daxton.customdisplay.gui.item.OpenMenuGUI;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.EditorGUIManager;
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

public class ItemList {

    public Map<Integer,Integer> RawSlot = new HashMap<>();

    public Map<Integer,String> materialName = new HashMap<>();

    public String itemName;

    public String typeName;

    public int nextPageCount;
    public boolean haveNext;

    public int previousPageCount;
    public boolean havePrevioust;


    public ItemList(){

    }

    public void openMenu(Player player, String typeName, String itemName, int itemCount){
        this.nextPageCount = itemCount;

        String uuidString = player.getUniqueId().toString();
        if(EditorGUIManager.menu_ItemList_Inventory_Map.get(uuidString) == null){
            EditorGUIManager.menu_ItemList_Inventory_Map.put(uuidString, getInventory(typeName, itemName, itemCount));
            Inventory inventory = EditorGUIManager.menu_ItemList_Inventory_Map.get(uuidString);
            player.openInventory(inventory);
        }else{
            EditorGUIManager.menu_ItemList_Inventory_Map.remove(uuidString);
            EditorGUIManager.menu_ItemList_Inventory_Map.put(uuidString, getInventory(typeName, itemName, itemCount));
            Inventory inventory = EditorGUIManager.menu_ItemList_Inventory_Map.get(uuidString);
            player.openInventory(inventory);
        }

    }

    //按鈕監聽
    public static void onInventoryClick(InventoryClickEvent event){
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        String uuidString = player.getUniqueId().toString();
        if(EditorGUIManager.menu_ItemList_Map.get(uuidString) != null){
            int i = event.getRawSlot();
            Map<Integer,Integer> RawSlot = EditorGUIManager.menu_ItemList_Map.get(uuidString).RawSlot;
            Map<Integer,String> materialName = EditorGUIManager.menu_ItemList_Map.get(uuidString).materialName;
            String typeName = EditorGUIManager.menu_ItemList_Map.get(uuidString).typeName;
            String itemName = EditorGUIManager.menu_ItemList_Map.get(uuidString).itemName;
            int nextPageCount = EditorGUIManager.menu_ItemList_Map.get(uuidString).nextPageCount;
            boolean haveNext = EditorGUIManager.menu_ItemList_Map.get(uuidString).haveNext;
            int previousPageCount = EditorGUIManager.menu_ItemList_Map.get(uuidString).previousPageCount;
            boolean havePrevioust = EditorGUIManager.menu_ItemList_Map.get(uuidString).havePrevioust;

            ItemSet itemSet = new ItemSet(player, typeName, itemName);
            if(event.getClick() == ClickType.LEFT){

                if(RawSlot.get(i) != null && RawSlot.get(i) == i){
                    itemSet.setMaterial(materialName.get(i));
                    itemSet.openActionListMenu(nextPageCount-28);
                    return;
                }

                //給物品
                if(i == 4){
                    itemSet.giveItem();
                    return;
                }

                if(i == 8){
                    player.closeInventory();
                    return;
                }

                if(i == 0){
                    OpenMenuGUI.EditItem(player, typeName, itemName);
                    return;

                }
                if(havePrevioust && i == 45){
                    OpenMenuGUI.ItemList(player, typeName, itemName, previousPageCount);
                    return;
                }
                if(haveNext && i == 53){
                    OpenMenuGUI.ItemList(player, typeName, itemName, nextPageCount);
                }




            }

        }


    }


    public Inventory getInventory(String typeName, String itemName, int itemCount){
        this.typeName = typeName;
        this.itemName = itemName;
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+typeName+".yml");

        Inventory inventory = Bukkit.createInventory(null, 54 , MenuSet.getGuiTitle("ActionList"));

        int i = 10;
        this.havePrevioust = false;
        if(itemCount > 0){
            this.havePrevioust = true;
            this.previousPageCount = this.nextPageCount-28;
            inventory.setItem(45,  ButtomSet.getItemButtom("Buttom.ActionList.PreviousPage", ""));
        }
        this.RawSlot.clear();

        Material[] stringArray = Material.values();
        int itmeAmount = stringArray.length-112;
        for(int k = itemCount; k < stringArray.length ;k++){
            if(i == 44){
                break;
            }
            if(i == 17 || i == 26 || i == 35){
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
            inventory.setItem(53, ButtomSet.getItemButtom("Buttom.ActionList.NextPage", ""));
        }

        inventory.setItem(4, MenuItem.valueOf(itemMenuConfig, itemName));

        inventory.setItem(0,  ButtomSet.getItemButtom("Buttom.ActionList.ToEditItem", ""));
        inventory.setItem(8, ButtomSet.getItemButtom("Buttom.ActionList.Exit", ""));
        inventory.setItem(49, ButtomSet.getItemButtom("Buttom.ActionList.Description", ""));


        return inventory;
    }

}
