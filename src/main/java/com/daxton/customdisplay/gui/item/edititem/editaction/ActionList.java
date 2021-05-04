package com.daxton.customdisplay.gui.item.edititem.editaction;

import com.daxton.customdisplay.api.config.Config;
import com.daxton.customdisplay.api.item.MenuItem;
import com.daxton.customdisplay.api.item.gui.ButtomSet;
import com.daxton.customdisplay.api.item.gui.MenuSet;
import com.daxton.customdisplay.gui.item.ItemSet;
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

public class ActionList {

    public Map<Integer,Integer> RawSlot = new HashMap<>();

    public Map<Integer,String> actionName = new HashMap<>();

    public String itemName;

    public String typeName;

    public int nextPageCount;
    public boolean haveNext;

    public int previousPageCount;
    public boolean havePrevioust;

    public int actionOrder;

    public String actionOrderType = "";

    public ActionList(){

    }




    //按鈕監聽
    public static void onInventoryClick(InventoryClickEvent event){
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        String uuidString = player.getUniqueId().toString();
        if(EditorGUIManager.menu_ActionList_Map.get(uuidString) != null){
            int i = event.getRawSlot();
            Map<Integer,Integer> RawSlot = EditorGUIManager.menu_ActionList_Map.get(uuidString).RawSlot;
            Map<Integer,String> actionName = EditorGUIManager.menu_ActionList_Map.get(uuidString).actionName;
            String typeName = EditorGUIManager.menu_ActionList_Map.get(uuidString).typeName;
            String itemName = EditorGUIManager.menu_ActionList_Map.get(uuidString).itemName;
            int nextPageCount = EditorGUIManager.menu_ActionList_Map.get(uuidString).nextPageCount;
            boolean haveNext = EditorGUIManager.menu_ActionList_Map.get(uuidString).haveNext;
            int previousPageCount = EditorGUIManager.menu_ActionList_Map.get(uuidString).previousPageCount;
            boolean havePrevioust = EditorGUIManager.menu_ActionList_Map.get(uuidString).havePrevioust;

            int actionOrder = EditorGUIManager.menu_ActionList_Map.get(uuidString).actionOrder;
            String actionOrderType = EditorGUIManager.menu_ActionList_Map.get(uuidString).actionOrderType;

            ItemSet itemSet = new ItemSet(player, typeName, itemName);

            ItemActionSet itemActionSet = new ItemActionSet(player, typeName, itemName);

            if(event.getClick() == ClickType.LEFT){

                if(RawSlot.get(i) != null && RawSlot.get(i) == i){
//                    itemSet.setMaterial(materialName.get(i));
//                    itemSet.openActionListMenu(nextPageCount-28);

                    itemActionSet.setEditActionShow(new String[]{"action","a"},actionOrder, actionOrderType, actionName.get(i));
                    OpenMenuGUI.EditActionDetail(player, typeName, itemName, actionOrder, actionOrderType);
                    return;
                }

                //給物品
                if(i == 4){
                    itemActionSet.giveItem();
                    return;
                }

                if(i == 8){
                    player.closeInventory();
                    return;
                }

                if(i == 0){
                    OpenMenuGUI.EditActionTypeList(player, typeName, itemName, actionOrder, actionOrderType);
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

    public void openMenu(Player player, String typeName, String itemName, String actionType, int itemCount, int actionOrder, String editType){
        this.nextPageCount = itemCount;

        String uuidString = player.getUniqueId().toString();
        if(EditorGUIManager.menu_ActionList_Inventory_Map.get(uuidString) == null){
            EditorGUIManager.menu_ActionList_Inventory_Map.put(uuidString, getInventory(typeName, itemName, actionType, itemCount, actionOrder, editType));
            Inventory inventory = EditorGUIManager.menu_ActionList_Inventory_Map.get(uuidString);
            player.openInventory(inventory);
        }else{
            EditorGUIManager.menu_ActionList_Inventory_Map.remove(uuidString);
            EditorGUIManager.menu_ActionList_Inventory_Map.put(uuidString, getInventory(typeName, itemName, actionType, itemCount, actionOrder, editType));
            Inventory inventory = EditorGUIManager.menu_ActionList_Inventory_Map.get(uuidString);
            player.openInventory(inventory);
        }

    }

    public Inventory getInventory(String typeName, String itemName, String actionType, int itemCount, int actionOrder, String editType){
        this.typeName = typeName;
        this.itemName = itemName;
        this.actionOrder = actionOrder;
        this.actionOrderType = editType;

        //CustomDisplay.getCustomDisplay().getLogger().info(typeName+" : "+itemName+" : "+actionType+" : "+itemCount+" : "+actionOrder+" : "+editType);
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+typeName+".yml");

        Inventory inventory = Bukkit.createInventory(null, 54 , MenuSet.getGuiTitle("ActionList"));

        int i = 10;
        this.havePrevioust = false;
        if(itemCount > 0){
            this.havePrevioust = true;
            this.previousPageCount = this.nextPageCount-28;
            inventory.setItem(45,  ButtomSet.getItemButtom("Buttom.ActionList.PreviousPage",""));
        }
        this.RawSlot.clear();

        List<String> actionList = Config.configHeadKey("Actions_"+actionType+".yml");


        for(int k = itemCount; k < actionList.size() ;k++){
            if(i == 44){
                break;
            }
            if(i == 17 || i == 26 || i == 35){
                i+=2;
            }
            this.RawSlot.put(i,i);
            this.actionName.put(i,actionList.get(k));
            inventory.setItem(i,  ButtomSet.action(actionList.get(k), actionType));
            i++;
            this.nextPageCount++;
        }

        this.haveNext = false;
        if(actionList.size()-itemCount > 28){
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
