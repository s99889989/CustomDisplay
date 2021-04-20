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
import org.bukkit.inventory.Inventory;

public class ItemFlagsEdit {

    public String typeName;

    public String itemName;

    public ItemFlagsEdit(){

    }

    //按鈕監聽
    public static void onInventoryClick(InventoryClickEvent event){
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        String uuidString = player.getUniqueId().toString();
        if(PlayerManager.menu_Inventory_ItemFlagsEdit_Map.get(uuidString) != null){

            String typeName = PlayerManager.menu_Inventory_ItemFlagsEdit_Map.get(uuidString).typeName;
            String itemName = PlayerManager.menu_Inventory_ItemFlagsEdit_Map.get(uuidString).itemName;

            int i = event.getRawSlot();
            ItemSet itemSet = new ItemSet(player, typeName, itemName);

            //左鍵
            if(event.getClick() == ClickType.LEFT){

                //給物品
                if(i == 4){
                    itemSet.giveItem();
                }
                //到物品類別
                if(i == 0){
                    if(PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString) == null){
                        PlayerManager.menu_Inventory_ItemMenuEdit_Map.put(uuidString, new ItemMenuEdit());
                    }
                    if(PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString) != null){
                        PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString).openMenu(player, typeName, itemName);
                    }
                }

                //關閉選單
                if(i == 8){
                    itemSet.closeEditMenu();
                }

                if(i == 10){
                    itemSet.setHideAttributes();
                    itemSet.openItemFlagsEdit();
                }
                if(i == 11){
                    itemSet.setHideDestroys();
                    itemSet.openItemFlagsEdit();
                }
                if(i == 12){
                    itemSet.setHideDye();
                    itemSet.openItemFlagsEdit();
                }
                if(i == 13){
                    itemSet.setHideEnchants();
                    itemSet.openItemFlagsEdit();
                }
                if(i == 14){
                    itemSet.setHidePlacedOn();
                    itemSet.openItemFlagsEdit();
                }
                if(i == 15){
                    itemSet.setHidePotionEffects();
                    itemSet.openItemFlagsEdit();
                }
                if(i == 16){
                    itemSet.setHideUnbreakable();
                    itemSet.openItemFlagsEdit();
                }

            }
            //右鍵
            if(event.getClick() == ClickType.RIGHT){
                if(i == 10){
                    itemSet.setUnHideAttributes();
                    itemSet.openItemFlagsEdit();
                }
                if(i == 11){
                    itemSet.setUnHideDestroys();
                    itemSet.openItemFlagsEdit();
                }
                if(i == 12){
                    itemSet.setUnHideDye();
                    itemSet.openItemFlagsEdit();
                }
                if(i == 13){
                    itemSet.setUnHideEnchants();
                    itemSet.openItemFlagsEdit();
                }
                if(i == 14){
                    itemSet.setUnHidePlacedOn();
                    itemSet.openItemFlagsEdit();
                }
                if(i == 15){
                    itemSet.setUnHidePotionEffects();
                    itemSet.openItemFlagsEdit();
                }
                if(i == 16){
                    itemSet.setUnHideUnbreakable();
                    itemSet.openItemFlagsEdit();
                }
            }

        }
    }

    public void openMenu(Player player, String typeName, String itemName){
        String uuidString = player.getUniqueId().toString();

        if(PlayerManager.menu_Inventory7_Map.get(uuidString) == null){
            PlayerManager.menu_Inventory7_Map.put(uuidString, getInventory(typeName, itemName));
            Inventory inventory = PlayerManager.menu_Inventory7_Map.get(uuidString);
            player.openInventory(inventory);

        }else {
            PlayerManager.menu_Inventory7_Map.remove(uuidString);
            PlayerManager.menu_Inventory7_Map.put(uuidString, getInventory(typeName, itemName));
            Inventory inventory = PlayerManager.menu_Inventory7_Map.get(uuidString);
            player.openInventory(inventory);

        }

    }

    public Inventory getInventory(String typeName, String itemName){
        this.typeName = typeName;
        this.itemName = itemName;

        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+typeName+".yml");

        String menuTitle = MenuSet.getMenuTitle("ItemFlagsEdit");

        Inventory inventory = Bukkit.createInventory(null, 54 , menuTitle);

        inventory.setItem(4, MenuItem.valueOf(itemMenuConfig, itemName));

        inventory.setItem(0, MenuSet.getItemMenuButtom("ItemFlagsEdit","ToTypeMenu"));
        inventory.setItem(8, MenuSet.getItemMenuButtom("ItemsButtom","CloseMenu"));

        inventory.setItem(10, MenuSet.getItemMenuButtom("ItemFlagsEdit","HideAttributes"));
        inventory.setItem(11, MenuSet.getItemMenuButtom("ItemFlagsEdit","HideDestroys"));
        inventory.setItem(12, MenuSet.getItemMenuButtom("ItemFlagsEdit","HideDye"));
        inventory.setItem(13, MenuSet.getItemMenuButtom("ItemFlagsEdit","HideEnchants"));
        inventory.setItem(14, MenuSet.getItemMenuButtom("ItemFlagsEdit","HidePlacedOn"));
        inventory.setItem(15, MenuSet.getItemMenuButtom("ItemFlagsEdit","HidePotionEffects"));
        inventory.setItem(16, MenuSet.getItemMenuButtom("ItemFlagsEdit","HideUnbreakable"));

        return inventory;
    }

}
