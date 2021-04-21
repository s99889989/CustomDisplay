package com.daxton.customdisplay.api.item.gui;

import com.daxton.customdisplay.api.item.MenuItem;
import com.daxton.customdisplay.api.item.ItemSet;
import com.daxton.customdisplay.api.item.MenuSet;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlayerManager;
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
        if(PlayerManager.menu_EditEnchantment_Map.get(uuidString) != null){
            event.setCancelled(true);
            String enchantmentType = PlayerManager.menu_EditEnchantment_Map.get(uuidString).enchantmentType;
            String typeName = PlayerManager.menu_EditEnchantment_Map.get(uuidString).typeName;
            String itemName = PlayerManager.menu_EditEnchantment_Map.get(uuidString).itemID;

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

        if(PlayerManager.menu_EditEnchantment_Map.get(uuidString) != null){
            int page = PlayerManager.menu_EditEnchantment_Map.get(uuidString).page;
            Map<Integer,Integer> RawSlot = PlayerManager.menu_EditEnchantment_Map.get(uuidString).RawSlot;
            Map<Integer,String> enchantmentName = PlayerManager.menu_EditEnchantment_Map.get(uuidString).enchantmentName;
            String typeName = PlayerManager.menu_EditEnchantment_Map.get(uuidString).typeName;
            String itemName = PlayerManager.menu_EditEnchantment_Map.get(uuidString).itemID;
            int i = event.getRawSlot();
            if(event.getClick() == ClickType.LEFT){
                //到物品類別
                if(i == 0){
                    new OpenMenuGUI(player).EditItem(typeName, itemName);
                    return;
                }
                //關閉選單
                if(i == 8){
                    player.closeInventory();
                    return;
                }

                if(RawSlot.get(i) != null && RawSlot.get(i) == i){
                    ItemSet itemSet = new ItemSet(player, typeName, itemName);
                    PlayerManager.menu_EditEnchantment_Map.get(uuidString).enchantmentType = enchantmentName.get(i);
                    String ms1 = MenuSet.getItemMenuMessage("EditEnchantment", "Description", "Message");
                    String ms2 = MenuSet.getItemMenuMessage("EditEnchantment", "Description", "SubMessage");
                    itemSet.clickEditEnchantment(ms1, ms2);
                    return;
                }


                if(page == 1 && i == 45){
                    new OpenMenuGUI(player).EditEnchantment(typeName, itemName, 0);
                    return;
                }

                if(page == 0 && i == 53){
                    new OpenMenuGUI(player).EditEnchantment(typeName, itemName, 1);
                }

            }



        }

    }

    public void openMenu(Player player, String typeName, String itemName, int page){
        String uuidString = player.getUniqueId().toString();

        if(PlayerManager.menu_EditEnchantment_Inventory_Map.get(uuidString) == null){
            PlayerManager.menu_EditEnchantment_Inventory_Map.put(uuidString, getInventory(typeName, itemName, page));
            Inventory inventory = PlayerManager.menu_EditEnchantment_Inventory_Map.get(uuidString);
            player.openInventory(inventory);

        }else {
            PlayerManager.menu_EditEnchantment_Inventory_Map.remove(uuidString);
            PlayerManager.menu_EditEnchantment_Inventory_Map.put(uuidString, getInventory(typeName, itemName, page));
            Inventory inventory = PlayerManager.menu_EditEnchantment_Inventory_Map.get(uuidString);
            player.openInventory(inventory);

        }

    }

    public Inventory getInventory(String typeName, String itemName, int page) {
        this.typeName = typeName;
        this.itemID = itemName;
        this.page = page;
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_" + typeName + ".yml");
        Inventory inventory = Bukkit.createInventory(null, 54, MenuSet.getGuiTitle("EditEnchantment"));

        inventory.setItem(4, MenuItem.valueOf(itemMenuConfig, itemName));

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

            inventory.setItem(i, MenuSet.getItemButtom("Buttom", "EditEnchantment", enchantments[k].getKey().getKey()));
            i++;
        }

        inventory.setItem(0, MenuSet.getItemButtom("Buttom", "EditEnchantment", "ToEditItem"));
        inventory.setItem(8, MenuSet.getItemButtom("Buttom", "EditEnchantment", "Exit"));
        inventory.setItem(49, MenuSet.getItemButtom("Buttom", "EditEnchantment", "Description"));

        if (page == 1) {
            inventory.setItem(45, MenuSet.getItemButtom("Buttom", "EditEnchantment", "PreviousPage"));
        }

        if (page == 0) {
            inventory.setItem(53, MenuSet.getItemButtom("Buttom", "EditEnchantment", "NextPage"));
        }


            return inventory;
    }


}
