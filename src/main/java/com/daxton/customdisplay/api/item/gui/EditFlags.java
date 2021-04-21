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

public class EditFlags {

    public String typeName;

    public String itemName;

    public EditFlags(){

    }

    //按鈕監聽
    public static void onInventoryClick(InventoryClickEvent event){
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        String uuidString = player.getUniqueId().toString();
        if(PlayerManager.menu_EditFlags_Map.get(uuidString) != null){

            String typeName = PlayerManager.menu_EditFlags_Map.get(uuidString).typeName;
            String itemName = PlayerManager.menu_EditFlags_Map.get(uuidString).itemName;

            int i = event.getRawSlot();
            ItemSet itemSet = new ItemSet(player, typeName, itemName);

            //左鍵
            if(event.getClick() == ClickType.LEFT){

                //給物品
                if(i == 4){
                    itemSet.giveItem();
                    return;
                }
                //到物品類別
                if(i == 0){
                    new OpenMenuGUI(player).EditItem(typeName, itemName);
                    return;
                }

                //關閉選單
                if(i == 8){
                    itemSet.closeEditMenu();
                    return;
                }

                if(i == 10){
                    itemSet.setHideAttributes();
                    itemSet.openItemFlagsEdit();
                    return;
                }
                if(i == 11){
                    itemSet.setHideDestroys();
                    itemSet.openItemFlagsEdit();
                    return;
                }
                if(i == 12){
                    itemSet.setHideDye();
                    itemSet.openItemFlagsEdit();
                    return;
                }
                if(i == 13){
                    itemSet.setHideEnchants();
                    itemSet.openItemFlagsEdit();
                    return;
                }
                if(i == 14){
                    itemSet.setHidePlacedOn();
                    itemSet.openItemFlagsEdit();
                    return;
                }
                if(i == 15){
                    itemSet.setHidePotionEffects();
                    itemSet.openItemFlagsEdit();
                    return;
                }
                if(i == 16){
                    itemSet.setHideUnbreakable();
                    itemSet.openItemFlagsEdit();
                    return;
                }

            }
            //右鍵
            if(event.getClick() == ClickType.RIGHT){
                if(i == 10){
                    itemSet.setUnHideAttributes();
                    itemSet.openItemFlagsEdit();
                    return;
                }
                if(i == 11){
                    itemSet.setUnHideDestroys();
                    itemSet.openItemFlagsEdit();
                    return;
                }
                if(i == 12){
                    itemSet.setUnHideDye();
                    itemSet.openItemFlagsEdit();
                    return;
                }
                if(i == 13){
                    itemSet.setUnHideEnchants();
                    itemSet.openItemFlagsEdit();
                    return;
                }
                if(i == 14){
                    itemSet.setUnHidePlacedOn();
                    itemSet.openItemFlagsEdit();
                    return;
                }
                if(i == 15){
                    itemSet.setUnHidePotionEffects();
                    itemSet.openItemFlagsEdit();
                    return;
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

        if(PlayerManager.menu_EditFlags_Inventory_Map.get(uuidString) == null){
            PlayerManager.menu_EditFlags_Inventory_Map.put(uuidString, getInventory(typeName, itemName));
            Inventory inventory = PlayerManager.menu_EditFlags_Inventory_Map.get(uuidString);
            player.openInventory(inventory);

        }else {
            PlayerManager.menu_EditFlags_Inventory_Map.remove(uuidString);
            PlayerManager.menu_EditFlags_Inventory_Map.put(uuidString, getInventory(typeName, itemName));
            Inventory inventory = PlayerManager.menu_EditFlags_Inventory_Map.get(uuidString);
            player.openInventory(inventory);

        }

    }

    public Inventory getInventory(String typeName, String itemName){
        this.typeName = typeName;
        this.itemName = itemName;

        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+typeName+".yml");

        Inventory inventory = Bukkit.createInventory(null, 54 , MenuSet.getGuiTitle("EditFlags"));

        inventory.setItem(4, MenuItem.valueOf(itemMenuConfig, itemName));

        inventory.setItem(0, MenuSet.getItemButtom("Buttom", "EditFlags","ToEditItem"));
        inventory.setItem(8, MenuSet.getItemButtom("Buttom", "EditFlags","Exit"));

        inventory.setItem(10, MenuSet.getItemButtom("Buttom", "EditFlags","HideAttributes"));
        inventory.setItem(11, MenuSet.getItemButtom("Buttom", "EditFlags","HideDestroys"));
        inventory.setItem(12, MenuSet.getItemButtom("Buttom", "EditFlags","HideDye"));
        inventory.setItem(13, MenuSet.getItemButtom("Buttom", "EditFlags","HideEnchants"));
        inventory.setItem(14, MenuSet.getItemButtom("Buttom", "EditFlags","HidePlacedOn"));
        inventory.setItem(15, MenuSet.getItemButtom("Buttom", "EditFlags","HidePotionEffects"));
        inventory.setItem(16, MenuSet.getItemButtom("Buttom", "EditFlags","HideUnbreakable"));

        return inventory;
    }

}
