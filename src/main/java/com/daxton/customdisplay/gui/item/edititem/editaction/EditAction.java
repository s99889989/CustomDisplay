package com.daxton.customdisplay.gui.item.edititem.editaction;

import com.daxton.customdisplay.api.action.SetActionMap;
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
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditAction {

    public String typeName;

    public String itemName;

    public String editType = "";

    public int orderKey = 0;

    public Map<Integer,Integer> rawSlot = new HashMap<>();

    public Map<Integer,Integer> order = new HashMap<>();

    public Map<Integer,Integer> rawSlot2 = new HashMap<>();

    public Map<Integer,Integer> lastOrder = new HashMap<>();

    public EditAction(){

    }


    //聊天監聽
    public static void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String uuidString = event.getPlayer().getUniqueId().toString();
        if(EditorGUIManager.menu_EditAction_Map.get(uuidString) != null){
            event.setCancelled(true);
            String typeName = EditorGUIManager.menu_EditAction_Map.get(uuidString).typeName;
            String itemName = EditorGUIManager.menu_EditAction_Map.get(uuidString).itemName;
            String editType = EditorGUIManager.menu_EditAction_Map.get(uuidString).editType;
            int orderKey = EditorGUIManager.menu_EditAction_Map.get(uuidString).orderKey;
            String chatString = event.getMessage().replace("&", "§");
            ItemSet itemSet = new ItemSet(player, typeName, itemName);
            switch (editType){
                case "AddOrderLore":
                    itemSet.addOrderLore(chatString, orderKey);
                    itemSet.openEditLore();
                    break;
                case "EditLore":
                    itemSet.editLore(chatString, orderKey);
                    itemSet.openEditLore();
                    break;
                case "AddLore":
                    itemSet.addLore(chatString);
                    itemSet.openEditLore();
                    break;
                default:
                    itemSet.openEditLore();
            }


        }
    }

    //按鈕監聽
    public static void onInventoryClick(InventoryClickEvent event){
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        String uuidString = player.getUniqueId().toString();
        if(EditorGUIManager.menu_EditAction_Map.get(uuidString) != null){
            String typeName = EditorGUIManager.menu_EditAction_Map.get(uuidString).typeName;
            String itemName = EditorGUIManager.menu_EditAction_Map.get(uuidString).itemName;
            Map<Integer,Integer> rawSlot = EditorGUIManager.menu_EditAction_Map.get(uuidString).rawSlot;
            Map<Integer,Integer> order = EditorGUIManager.menu_EditAction_Map.get(uuidString).order;
            Map<Integer,Integer> rawSlot2 = EditorGUIManager.menu_EditAction_Map.get(uuidString).rawSlot2;
            Map<Integer,Integer> lastOrder = EditorGUIManager.menu_EditAction_Map.get(uuidString).lastOrder;
            int i = event.getRawSlot();


            ItemActionSet itemActionSet = new ItemActionSet(player, typeName, itemName);

            //往前一行增加動作
            if(event.getClick() == ClickType.SHIFT_LEFT){
                if(rawSlot.get(i) != null && rawSlot.get(i) == i){

                    itemActionSet.setDefaultAction(order.get(i));
                    OpenMenuGUI.EditActionDetail(player, typeName, itemName, order.get(i), "AddOrderAction");
                }
            }
            //左鍵
            if(event.getClick() == ClickType.LEFT){
                //給物品
                if(i == 4){
                    itemActionSet.giveItem();
                    return;
                }
                //到物品類別
                if(i == 0){
                    OpenMenuGUI.EditItem(player, typeName, itemName);
                    return;
                }

                //關閉選單
                if(i == 8){
                    itemActionSet.closeEditMenu();
                    return;
                }

                //編輯動作
                if(rawSlot.get(i) != null && rawSlot.get(i) == i){

                    OpenMenuGUI.EditActionDetail(player, typeName, itemName, order.get(i), "EditAction");
                }
                //最後面增加動作
                if(rawSlot2.get(i) != null && rawSlot2.get(i) == i){

                    itemActionSet.setDefaultAction(lastOrder.get(i));
                    OpenMenuGUI.EditActionDetail(player, typeName, itemName, lastOrder.get(i), "AddAction");
                }

            }
            //移除指定順序Action
            if(event.getClick() == ClickType.RIGHT){
                if(rawSlot.get(i) != null && rawSlot.get(i) == i){
                    itemActionSet.removeOrderAction(order.get(i));
                    OpenMenuGUI.EditAction(player, typeName, itemName);
                }
            }

        }
    }

    public void openMenu(Player player, String typeName, String itemName){
        String uuidString = player.getUniqueId().toString();

        if(EditorGUIManager.menu_EditAction_Inventory_Map.get(uuidString) == null){
            EditorGUIManager.menu_EditAction_Inventory_Map.put(uuidString, getInventory(typeName, itemName));
            Inventory inventory = EditorGUIManager.menu_EditAction_Inventory_Map.get(uuidString);
            player.openInventory(inventory);

        }else {
            EditorGUIManager.menu_EditAction_Inventory_Map.remove(uuidString);
            EditorGUIManager.menu_EditAction_Inventory_Map.put(uuidString, getInventory(typeName, itemName));
            Inventory inventory = EditorGUIManager.menu_EditAction_Inventory_Map.get(uuidString);
            player.openInventory(inventory);

        }

    }

    public Inventory getInventory(String typeName, String itemName){
        this.typeName = typeName;
        this.itemName = itemName;
        this.rawSlot.clear();
        this.rawSlot2.clear();
        this.order.clear();
        this.lastOrder.clear();

        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+typeName+".yml");

        Inventory inventory = Bukkit.createInventory(null, 54 , MenuSet.getGuiTitle("EditAction"));

        inventory.setItem(4, MenuItem.valueOf(itemMenuConfig, itemName));

        inventory.setItem(0,  ButtomSet.getItemButtom("Buttom.EditAction.ToEditItem", ""));
        inventory.setItem(8,  ButtomSet.getItemButtom("Buttom.EditAction.Exit", ""));

        List<String> itemLore = itemMenuConfig.getStringList(itemName+".Action");
        List<Map<String, String>> player_Item_Action_List_Map = new ArrayList<>();
        itemLore.forEach(s -> {
            player_Item_Action_List_Map.add(SetActionMap.setClassAction(s));
        });

        int i = 10;
        for(int k = 0; k <= player_Item_Action_List_Map.size() ;k++){
            if(i == 44){
                break;
            }
            if(i == 17 || i == 26 || i == 35){
                i+=2;
            }


            ItemStack itemStack = new ItemStack(Material.ACACIA_DOOR);
            if(k < player_Item_Action_List_Map.size()) {
                this.rawSlot.put(i,i);
                this.order.put(i,k);
                if (player_Item_Action_List_Map.get(k).isEmpty()) {
                    itemStack = ButtomSet.getActionButtom("Buttom.EditAction.ActionDisplay", k,player_Item_Action_List_Map.get(k));
                } else {
                    itemStack = ButtomSet.getActionButtom("Buttom.EditAction.ActionDisplay", k, player_Item_Action_List_Map.get(k));
                }
            }else {
                this.rawSlot2.put(i,i);
                this.lastOrder.put(i,k);
                itemStack = ButtomSet.getActionButtom("Buttom.EditAction.ActionAdd", k, null);
            }

            inventory.setItem(i, itemStack);
            if(k == player_Item_Action_List_Map.size()){
                break;
            }
            i++;
        }

        return inventory;
    }

}
