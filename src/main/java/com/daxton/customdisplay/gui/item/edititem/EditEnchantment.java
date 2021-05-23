package com.daxton.customdisplay.gui.item.edititem;

import com.daxton.customdisplay.api.item.CustomItem2;
import com.daxton.customdisplay.api.item.MenuItem;
import com.daxton.customdisplay.api.gui.ButtomSet;
import com.daxton.customdisplay.gui.item.ItemSet;
import com.daxton.customdisplay.api.gui.MenuSet;
import com.daxton.customdisplay.gui.item.OpenMenuGUI;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.EditorGUIManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class EditEnchantment {

    public Map<Integer,Integer> RawSlot = new HashMap<>();

    public Map<Integer,String> enchantmentName = new HashMap<>();

    public String typeName;

    public String itemID;

    public String enchantmentType = "";

    public int page;

    public EditEnchantment(){

    }

    //聊天監聽
    public static void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String uuidString = event.getPlayer().getUniqueId().toString();
        if(EditorGUIManager.menu_EditEnchantment_Map.get(uuidString) != null){
            event.setCancelled(true);
            String enchantmentType = EditorGUIManager.menu_EditEnchantment_Map.get(uuidString).enchantmentType;
            String typeName = EditorGUIManager.menu_EditEnchantment_Map.get(uuidString).typeName;
            String itemName = EditorGUIManager.menu_EditEnchantment_Map.get(uuidString).itemID;

            String chatString = event.getMessage().replace("&", "§");

            ItemSet itemSet = new ItemSet(player, typeName, itemName);
            //player.sendMessage(enchantmentType+":"+chatString);
            itemSet.setEnchantment(enchantmentType+":"+chatString);

            itemSet.openEnchantmentMenu();
        }


    }

    //按鈕監聽
    public static void onInventoryClick(InventoryClickEvent event){
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        String uuidString = player.getUniqueId().toString();

        if(EditorGUIManager.menu_EditEnchantment_Map.get(uuidString) != null){
            int page = EditorGUIManager.menu_EditEnchantment_Map.get(uuidString).page;
            Map<Integer,Integer> RawSlot = EditorGUIManager.menu_EditEnchantment_Map.get(uuidString).RawSlot;
            Map<Integer,String> enchantmentName = EditorGUIManager.menu_EditEnchantment_Map.get(uuidString).enchantmentName;
            String typeName = EditorGUIManager.menu_EditEnchantment_Map.get(uuidString).typeName;
            String itemName = EditorGUIManager.menu_EditEnchantment_Map.get(uuidString).itemID;
            int i = event.getRawSlot();
            if(event.getClick() == ClickType.LEFT){
                //到物品類別
                if(i == 0){
                    OpenMenuGUI.EditItem(player, typeName, itemName);
                    return;
                }
                //關閉選單
                if(i == 8){
                    player.closeInventory();
                    return;
                }

                if(RawSlot.get(i) != null && RawSlot.get(i) == i){
                    ItemSet itemSet = new ItemSet(player, typeName, itemName);
                    EditorGUIManager.menu_EditEnchantment_Map.get(uuidString).enchantmentType = enchantmentName.get(i);
                    String ms1 = MenuSet.getItemMenuMessage("EditEnchantment", "Description", "Message");
                    String ms2 = MenuSet.getItemMenuMessage("EditEnchantment", "Description", "SubMessage");
                    itemSet.clickEditEnchantment(ms1, ms2);
                    return;
                }


                if(page == 1 && i == 45){
                    OpenMenuGUI.EditEnchantment(player, typeName, itemName, 0);
                    return;
                }

                if(page == 0 && i == 53){
                    OpenMenuGUI.EditEnchantment(player, typeName, itemName, 1);
                }

            }



        }

    }

    public void openMenu(Player player, String typeName, String itemName, int page){
        String uuidString = player.getUniqueId().toString();

        if(EditorGUIManager.menu_EditEnchantment_Inventory_Map.get(uuidString) == null){
            EditorGUIManager.menu_EditEnchantment_Inventory_Map.put(uuidString, getInventory(player, typeName, itemName, page));
            Inventory inventory = EditorGUIManager.menu_EditEnchantment_Inventory_Map.get(uuidString);
            player.openInventory(inventory);

        }else {
            EditorGUIManager.menu_EditEnchantment_Inventory_Map.remove(uuidString);
            EditorGUIManager.menu_EditEnchantment_Inventory_Map.put(uuidString, getInventory(player, typeName, itemName, page));
            Inventory inventory = EditorGUIManager.menu_EditEnchantment_Inventory_Map.get(uuidString);
            player.openInventory(inventory);

        }

    }

    public Inventory getInventory(Player player, String typeName, String itemName, int page) {
        this.typeName = typeName;
        this.itemID = itemName;
        this.page = page;
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_" + typeName + ".yml");
        Inventory inventory = Bukkit.createInventory(null, 54, MenuSet.getGuiTitle("EditEnchantment"));

        inventory.setItem(4, CustomItem2.valueOf(player, null, typeName+"."+itemName, 1));
        //inventory.setItem(4, MenuItem.valueOf(itemMenuConfig, itemName));

        int i = 10;
        Enchantment[] enchantments = Enchantment.values();
        for (int k = (page * 28); k < enchantments.length; k++) {
            if (i == 44) {
                break;
            }
            if (i == 17 || i == 26 || i == 35) {
                i += 2;
            }
            this.RawSlot.put(i, i);
            this.enchantmentName.put(i, enchantments[k].getKey().getKey());

            inventory.setItem(i,  ButtomSet.getItemButtom("Buttom.EditEnchantment."+enchantments[k].getKey().getKey(), ""));
            i++;
        }

        inventory.setItem(0,  ButtomSet.getItemButtom("Buttom.EditEnchantment.ToEditItem", ""));
        inventory.setItem(8,  ButtomSet.getItemButtom("Buttom.EditEnchantment.Exit", ""));
        inventory.setItem(49,  ButtomSet.getItemButtom("Buttom.EditEnchantment.Description", ""));

        if (page == 1) {
            inventory.setItem(45,  ButtomSet.getItemButtom("Buttom.EditEnchantment.PreviousPage", ""));
        }

        if (page == 0) {
            inventory.setItem(53,  ButtomSet.getItemButtom("Buttom.EditEnchantment.NextPage", ""));
        }


            return inventory;
    }


}
