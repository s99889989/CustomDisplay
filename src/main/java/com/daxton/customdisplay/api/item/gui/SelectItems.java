package com.daxton.customdisplay.api.item.gui;

import com.daxton.customdisplay.api.item.ItemSet;
import com.daxton.customdisplay.api.item.MenuItem;
import com.daxton.customdisplay.api.item.MenuSet;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlayerManager;
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
        if(PlayerManager.menu_SelectItems_Map.get(uuidString) != null){
            event.setCancelled(true);
            String typeName = PlayerManager.menu_SelectItems_Map.get(uuidString).typeName;

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
        if(PlayerManager.menu_SelectItems_Map.get(uuidString) != null){
            Map<Integer,Integer> RawSlot = PlayerManager.menu_SelectItems_Map.get(uuidString).RawSlot;
            String typeName = PlayerManager.menu_SelectItems_Map.get(uuidString).typeName;
            Map<Integer,String> itmeName = PlayerManager.menu_SelectItems_Map.get(uuidString).itmeID;
            FileConfiguration itemMenuConfig = PlayerManager.menu_SelectItems_Map.get(uuidString).itemMenuConfig;
            int nextPageCount = PlayerManager.menu_SelectItems_Map.get(uuidString).nextPageCount;
            boolean haveNext = PlayerManager.menu_SelectItems_Map.get(uuidString).haveNext;
            int previousPageCount = PlayerManager.menu_SelectItems_Map.get(uuidString).previousPageCount;
            boolean havePrevioust = PlayerManager.menu_SelectItems_Map.get(uuidString).havePrevioust;

            ItemSet itemSet = new ItemSet(player, typeName);

            if(event.getClick() == ClickType.LEFT){
                //到物品類別介面
                if(i == 0){
                    new OpenMenuGUI(player).ItemCategorySelection();
                    return;
                }
                //關閉GUI
                if(i == 8){
                    player.closeInventory();
                    return;
                }
                //上一頁
                if(havePrevioust && i == 45){
                    new OpenMenuGUI(player).SelectItems(typeName, previousPageCount);
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
                    new OpenMenuGUI(player).SelectItems(typeName, nextPageCount);
                    return;
                }
                //給物品
                if(RawSlot.get(i) != null && RawSlot.get(i) == i){
                    ItemStack itemStack = MenuItem.valueOf(itemMenuConfig, itmeName.get(i));
                    player.getInventory().addItem(itemStack);
                    return;
                }
            }
            if(event.getClick() == ClickType.RIGHT){
                //編輯物品
                if(RawSlot.get(i) != null && RawSlot.get(i) == i){
                    new OpenMenuGUI(player).EditItem(typeName, itmeName.get(i));

                }

            }
            if(event.getClick() == ClickType.SHIFT_RIGHT){
                //刪除物品
                if(RawSlot.get(i) != null && RawSlot.get(i) == i){
                    ItemSet itemSet2 = new ItemSet(player, typeName, itmeName.get(i));
                    itemSet2.deleteItem();
                    if(haveNext){
                        new OpenMenuGUI(player).SelectItems(typeName, nextPageCount-1);
                    }else {
                        new OpenMenuGUI(player).SelectItems(typeName, 0);
                    }

                    return;
                }
            }


        }

    }


    public void openMenu(Player player, String typeName, int itemCount){
        this.nextPageCount = itemCount;

        String uuidString = player.getUniqueId().toString();
        if(PlayerManager.menu_SelectItems_Inventory_Map.get(uuidString) == null){
            PlayerManager.menu_SelectItems_Inventory_Map.put(uuidString, getInventory(typeName, itemCount));
            Inventory inventory = PlayerManager.menu_SelectItems_Inventory_Map.get(uuidString);
            player.openInventory(inventory);
        }else{
            PlayerManager.menu_SelectItems_Inventory_Map.remove(uuidString);
            PlayerManager.menu_SelectItems_Inventory_Map.put(uuidString, getInventory(typeName, itemCount));
            Inventory inventory = PlayerManager.menu_SelectItems_Inventory_Map.get(uuidString);
            player.openInventory(inventory);
        }
    }


    public Inventory getInventory(String typeName, int itemCount){
        this.typeName = typeName;
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+ typeName +".yml");
        this.itemMenuConfig = itemMenuConfig;

        Inventory inventory = Bukkit.createInventory(null, 54 , MenuSet.getGuiTitle("SelectItems"));

        int i = 10;
        this.havePrevioust = false;
        if(itemCount > 0){
            this.havePrevioust = true;
            this.previousPageCount = this.nextPageCount-28;
            inventory.setItem(45,MenuSet.getItemButtom("Buttom", "ItemsButtom","PreviousPage"));
        }
        this.RawSlot.clear();
        this.itmeID.clear();

        String[] stringArray = MenuSet.getItemList(typeName);

        for(int k = itemCount; k < stringArray.length ;k++){
            if(i == 44){
                break;
            }
            if(i == 17 || i == 26 || i == 35){
                i+=2;
            }
            this.RawSlot.put(i,i);
            this.itmeID.put(i,stringArray[k]);
            inventory.setItem(i, MenuItem.valueOf(itemMenuConfig, stringArray[k]));
            i++;
            this.nextPageCount++;
        }

        this.haveNext = false;
        if(stringArray.length-itemCount > 28){
            this.haveNext = true;
            inventory.setItem(53,MenuSet.getItemButtom("Buttom", "SelectItems","NextPage"));
        }

        inventory.setItem(0, MenuSet.getItemButtom("Buttom", "SelectItems","ToItemCategorySelection"));
        inventory.setItem(8,MenuSet.getItemButtom("Buttom", "SelectItems","Exit"));
        inventory.setItem(49,MenuSet.getItemButtom("Buttom", "SelectItems","Description"));
        inventory.setItem(51,MenuSet.getItemButtom("Buttom", "SelectItems","Create"));

        return inventory;
    }


}
