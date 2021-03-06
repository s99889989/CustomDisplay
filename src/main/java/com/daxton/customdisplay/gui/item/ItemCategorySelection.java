package com.daxton.customdisplay.gui.item;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.SaveConfig;
import com.daxton.customdisplay.api.gui.ButtomSet;
import com.daxton.customdisplay.api.gui.MenuSet;
import com.daxton.customdisplay.manager.player.EditorGUIManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class ItemCategorySelection {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public Map<Integer,Integer> RawSlot = new HashMap<>();

    public Map<Integer,String> typeName = new HashMap<>();


    public ItemCategorySelection(){

    }

    public void openMenu(Player player){
        String uuidString = player.getUniqueId().toString();
        if(EditorGUIManager.menu_ItemCategorySelection_Inventory_Map.get(uuidString) == null){
            EditorGUIManager.menu_ItemCategorySelection_Inventory_Map.put(uuidString, getInventory());
            Inventory inventory = EditorGUIManager.menu_ItemCategorySelection_Inventory_Map.get(uuidString);
            player.openInventory(inventory);
        }else {
            EditorGUIManager.menu_ItemCategorySelection_Inventory_Map.remove(uuidString);
            EditorGUIManager.menu_ItemCategorySelection_Inventory_Map.put(uuidString, getInventory());
            Inventory inventory = EditorGUIManager.menu_ItemCategorySelection_Inventory_Map.get(uuidString);
            player.openInventory(inventory);
        }

    }

    public Inventory getInventory(){

        Inventory inventory = Bukkit.createInventory(null, 54 , MenuSet.getGuiTitle("ItemCategorySelection"));

        int i = 10;
        String[] strings = MenuSet.getItemMenuButtomNameArray();
        if(strings != null){
            for(String key : strings){

                this.RawSlot.put(i,i);
                this.typeName.put(i,key);

                inventory.setItem(i,MenuSet.getItemTypeButtom("Items.Type."+key));
                i++;
            }
        }


        inventory.setItem(8, ButtomSet.getItemButtom("Buttom.ItemCategorySelection.Exit",""));
        inventory.setItem(49, ButtomSet.getItemButtom("Buttom.ItemCategorySelection.Save",""));
        inventory.setItem(51, ButtomSet.getItemButtom("Buttom.ItemCategorySelection.Load",""));

        return inventory;
    }

    //按鈕監聽
    public static void onInventoryClick(InventoryClickEvent event){
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        String uuidString = player.getUniqueId().toString();

        int i = event.getRawSlot();
        if(EditorGUIManager.menu_ItemCategorySelection_Map.get(uuidString) != null){
            Map<Integer,Integer> RawSlot = EditorGUIManager.menu_ItemCategorySelection_Map.get(uuidString).RawSlot;
            Map<Integer,String> typeName = EditorGUIManager.menu_ItemCategorySelection_Map.get(uuidString).typeName;
            if(RawSlot.get(i) != null && RawSlot.get(i) == i){
                if(event.getClick() == ClickType.LEFT){
                    OpenMenuGUI.SelectItems(player, typeName.get(i), 0);
                }
            }
            if(i == 8){
                player.closeInventory();
            }
            if(i == 49){
                //儲存物品資訊
                SaveConfig.saveItemFile();
            }
            if(i == 51){
                //讀取物品資訊
                SaveConfig.loadItemFile();
            }
        }

    }





}
