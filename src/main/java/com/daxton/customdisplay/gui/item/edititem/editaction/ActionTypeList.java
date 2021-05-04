package com.daxton.customdisplay.gui.item.edititem.editaction;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.Config;
import com.daxton.customdisplay.api.item.gui.ButtomSet;
import com.daxton.customdisplay.gui.item.ItemSet;
import com.daxton.customdisplay.api.item.MenuItem;
import com.daxton.customdisplay.api.item.gui.MenuSet;
import com.daxton.customdisplay.gui.item.OpenMenuGUI;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.EditorGUIManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionTypeList{

    public Map<Integer, Integer> rawSlot = new HashMap<>();

    public Map<Integer, String> rawSlotActionType = new HashMap<>();

    public String itemName;

    public String typeName;

    public int actionOrder;

    public String actionOrderType = "";

    public ActionTypeList(){

    }


    //按鈕監聽

    public static void onInventoryClick(InventoryClickEvent event){
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        String uuidString = player.getUniqueId().toString();
        if(EditorGUIManager.menu_ActionTypeList_Map.get(uuidString) != null){
            int i = event.getRawSlot();
            Map<Integer,Integer> rawSlot = EditorGUIManager.menu_ActionTypeList_Map.get(uuidString).rawSlot;
            Map<Integer, String> rawSlotActionType = EditorGUIManager.menu_ActionTypeList_Map.get(uuidString).rawSlotActionType;
            String typeName = EditorGUIManager.menu_ActionTypeList_Map.get(uuidString).typeName;
            String itemName = EditorGUIManager.menu_ActionTypeList_Map.get(uuidString).itemName;


            int actionOrder = EditorGUIManager.menu_EditActionDetail_Map.get(uuidString).actionOrder;
            String actionOrderType = EditorGUIManager.menu_EditActionDetail_Map.get(uuidString).actionOrderType;

            ItemSet itemSet = new ItemSet(player, typeName, itemName);
            if(event.getClick() == ClickType.LEFT){

                if(rawSlot.get(i) != null && rawSlot.get(i) == i){
                    //player.sendMessage(player+" : "+typeName+" : "+itemName+" : "+rawSlotActionType.get(i)+" : "+0+" : "+actionOrder+" : "+actionOrderType);
                    OpenMenuGUI.EditActionList(player, typeName, itemName, rawSlotActionType.get(i), 0, actionOrder, actionOrderType);
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
                    OpenMenuGUI.EditActionDetail(player, typeName, itemName, actionOrder, actionOrderType);
                    return;

                }

            }

        }


    }

    public void openMenu(Player player, String typeName, String itemName, int itemCount, String actionOrderType){

        String uuidString = player.getUniqueId().toString();
        if(EditorGUIManager.menu_ActionTypeList_Inventory_Map.get(uuidString) == null){
            EditorGUIManager.menu_ActionTypeList_Inventory_Map.put(uuidString, getInventory(typeName, itemName, itemCount, actionOrderType));
            Inventory inventory = EditorGUIManager.menu_ActionTypeList_Inventory_Map.get(uuidString);
            player.openInventory(inventory);
        }else{
            EditorGUIManager.menu_ActionTypeList_Inventory_Map.remove(uuidString);
            EditorGUIManager.menu_ActionTypeList_Inventory_Map.put(uuidString, getInventory(typeName, itemName, itemCount, actionOrderType));
            Inventory inventory = EditorGUIManager.menu_ActionTypeList_Inventory_Map.get(uuidString);
            player.openInventory(inventory);
        }

    }

    public Inventory getInventory(String typeName, String itemName, int actionOrder, String actionOrderType){
        this.typeName = typeName;
        this.itemName = itemName;
        this.actionOrder = actionOrder;
        this.actionOrderType = actionOrderType;

        this.rawSlot.clear();
        this.rawSlotActionType.clear();

        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+typeName+".yml");

        Inventory inventory = Bukkit.createInventory(null, 54 , MenuSet.getGuiTitle("ActionTypeList"));

        int i = 10;


        List<String> actionList = Config.getFileList("Actions_");

        for(int k = 0; k < actionList.size() ;k++){
            if(i == 44){
                break;
            }
            if(i == 17 || i == 26 || i == 35){
                i+=2;
            }
            this.rawSlot.put(i, i);
            this.rawSlotActionType.put(i, actionList.get(k));

            inventory.setItem(i,  ButtomSet.actionType(actionList.get(k)));
            i++;

        }


        inventory.setItem(4, MenuItem.valueOf(itemMenuConfig, itemName));

        inventory.setItem(0, ButtomSet.getItemButtom("Buttom.ActionTypeList.ToEditItem", ""));
        inventory.setItem(8, ButtomSet.getItemButtom("Buttom.ActionTypeList.Exit", ""));
        inventory.setItem(49, ButtomSet.getItemButtom("Buttom.ActionTypeList.Description", ""));


        return inventory;
    }

}
