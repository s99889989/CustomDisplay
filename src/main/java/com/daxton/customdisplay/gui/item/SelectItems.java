package com.daxton.customdisplay.gui.item;

import com.daxton.customdisplay.api.item.CustomItem2;
import com.daxton.customdisplay.api.item.MenuItem;
import com.daxton.customdisplay.api.gui.ButtomSet;
import com.daxton.customdisplay.api.gui.MenuSet;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.EditorGUIManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SelectItems {

    public Map<Integer,Integer> RawSlot = new HashMap<>();

    public Map<Integer,String> itmeID = new HashMap<>();

    public String typeName;

    public int nextPageCount;
    public boolean haveNext;

    public int previousPageCount;
    public boolean havePrevioust;

    public FileConfiguration itemMenuConfig;

    public SelectItems(){

    }
    //聊天監聽
    public static void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String uuidString = event.getPlayer().getUniqueId().toString();
        if(EditorGUIManager.menu_SelectItems_Map.get(uuidString) != null){
            event.setCancelled(true);
            String typeName = EditorGUIManager.menu_SelectItems_Map.get(uuidString).typeName;

            String itemName = event.getMessage().replace("&", "§");

            ItemSet itemSet = new ItemSet(player, typeName, itemName);

            itemSet.createNewItem(itemName);


            itemSet.openItemMenuType();

        }
    }

    //按鈕監聽
    public static void onInventoryClick(InventoryClickEvent event){
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        String uuidString = player.getUniqueId().toString();

        int i = event.getRawSlot();
        if(EditorGUIManager.menu_SelectItems_Map.get(uuidString) != null){
            Map<Integer,Integer> RawSlot = EditorGUIManager.menu_SelectItems_Map.get(uuidString).RawSlot;
            String typeName = EditorGUIManager.menu_SelectItems_Map.get(uuidString).typeName;
            Map<Integer,String> itmeName = EditorGUIManager.menu_SelectItems_Map.get(uuidString).itmeID;
            FileConfiguration itemMenuConfig = EditorGUIManager.menu_SelectItems_Map.get(uuidString).itemMenuConfig;
            int nextPageCount = EditorGUIManager.menu_SelectItems_Map.get(uuidString).nextPageCount;
            boolean haveNext = EditorGUIManager.menu_SelectItems_Map.get(uuidString).haveNext;
            int previousPageCount = EditorGUIManager.menu_SelectItems_Map.get(uuidString).previousPageCount;
            boolean havePrevioust = EditorGUIManager.menu_SelectItems_Map.get(uuidString).havePrevioust;

            ItemSet itemSet = new ItemSet(player, typeName);

            if(event.getClick() == ClickType.LEFT){
                //到物品類別介面
                if(i == 0){
                    OpenMenuGUI.ItemCategorySelection(player);
                    return;
                }
                //關閉GUI
                if(i == 8){
                    player.closeInventory();
                    return;
                }
                //上一頁
                if(havePrevioust && i == 45){
                    OpenMenuGUI.SelectItems(player, typeName, previousPageCount);
                    return;
                }
                //建立新物品
                if(i == 51){
                    String ms1 = MenuSet.getItemMenuMessage("SelectItems","Create","Message");
                    String ms2 = MenuSet.getItemMenuMessage("SelectItems","Create","SubMessage");
                    itemSet.clickSelectItems(ms1, ms2);
                    return;
                }
                //下一頁
                if(haveNext && i == 53){
                    OpenMenuGUI.SelectItems(player, typeName, nextPageCount);
                    return;
                }
                //給物品
                if(RawSlot.get(i) != null && RawSlot.get(i) == i){
                    ItemStack itemStack = CustomItem2.valueOf(player, null, typeName+"."+itmeName.get(i), 1);
                    //ItemStack itemStack = MenuItem.valueOf(itemMenuConfig, itmeName.get(i));
                    player.getInventory().addItem(itemStack);
                    return;
                }
            }
            if(event.getClick() == ClickType.RIGHT){
                //編輯物品
                if(RawSlot.get(i) != null && RawSlot.get(i) == i){
                    OpenMenuGUI.EditItem(player, typeName, itmeName.get(i));

                }

            }
            if(event.getClick() == ClickType.SHIFT_RIGHT){
                //刪除物品
                if(RawSlot.get(i) != null && RawSlot.get(i) == i){
                    ItemSet itemSet2 = new ItemSet(player, typeName, itmeName.get(i));
                    itemSet2.deleteItem();
                    if(haveNext){
                        OpenMenuGUI.SelectItems(player, typeName, nextPageCount-1);
                    }else {
                        OpenMenuGUI.SelectItems(player, typeName, 0);
                    }

                    return;
                }
            }


        }

    }


    public void openMenu(Player player, String typeName, int itemCount){
        this.nextPageCount = itemCount;

        String uuidString = player.getUniqueId().toString();
        if(EditorGUIManager.menu_SelectItems_Inventory_Map.get(uuidString) == null){
            EditorGUIManager.menu_SelectItems_Inventory_Map.put(uuidString, getInventory(player, typeName, itemCount));
            Inventory inventory = EditorGUIManager.menu_SelectItems_Inventory_Map.get(uuidString);
            player.openInventory(inventory);
        }else{
            EditorGUIManager.menu_SelectItems_Inventory_Map.remove(uuidString);
            EditorGUIManager.menu_SelectItems_Inventory_Map.put(uuidString, getInventory(player, typeName, itemCount));
            Inventory inventory = EditorGUIManager.menu_SelectItems_Inventory_Map.get(uuidString);
            player.openInventory(inventory);
        }
    }


    public Inventory getInventory(Player player, String typeName, int itemCount){
        this.typeName = typeName;
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+ typeName +".yml");
        this.itemMenuConfig = itemMenuConfig;

        Inventory inventory = Bukkit.createInventory(null, 54 , MenuSet.getGuiTitle("SelectItems"));

        int i = 10;
        this.havePrevioust = false;
        if(itemCount > 0){
            this.havePrevioust = true;
            this.previousPageCount = this.nextPageCount-28;
            inventory.setItem(45, ButtomSet.getItemButtom("Buttom.ItemsButtom.PreviousPage", ""));
        }
        this.RawSlot.clear();
        this.itmeID.clear();

        String[] stringArray = MenuSet.getActionList(typeName);

        for(int k = itemCount; k < stringArray.length ;k++){
            if(i == 44){
                break;
            }
            if(i == 17 || i == 26 || i == 35){
                i+=2;
            }
            this.RawSlot.put(i,i);
            this.itmeID.put(i,stringArray[k]);
            //inventory.setItem(i, MenuItem.valueOf(itemMenuConfig, stringArray[k]));
            inventory.setItem(i, CustomItem2.valueOf(player, null, typeName+"."+stringArray[k], 1));
            i++;
            this.nextPageCount++;
        }

        this.haveNext = false;
        if(stringArray.length-itemCount > 28){
            this.haveNext = true;
            inventory.setItem(53, ButtomSet.getItemButtom("Buttom.SelectItems.NextPage", ""));
        }

        inventory.setItem(0,  ButtomSet.getItemButtom("Buttom.SelectItems.ToItemCategorySelection", ""));
        inventory.setItem(8, ButtomSet.getItemButtom("Buttom.SelectItems.Exit", ""));
        inventory.setItem(49, ButtomSet.getItemButtom("Buttom.SelectItems.Description", ""));
        inventory.setItem(51, ButtomSet.getItemButtom("Buttom.SelectItems.Create", ""));

        return inventory;
    }


}
