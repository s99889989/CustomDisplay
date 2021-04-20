package com.daxton.customdisplay.api.item.gui;

import com.daxton.customdisplay.CustomDisplay;
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

public class ItemEnchantmentEdit {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public Map<Integer,Integer> RawSlot = new HashMap<>();

    public Map<Integer,String> enchantmentName = new HashMap<>();

    public String typeName;

    public String itemName;

    public String enchantmentType = "";

    public int page;

    public ItemEnchantmentEdit(){

    }

    //聊天監聽
    public static void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String uuidString = event.getPlayer().getUniqueId().toString();
        if(PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString) != null){
            event.setCancelled(true);
            String enchantmentType = PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString).enchantmentType;
            String typeName = PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString).typeName;
            String itemName = PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString).itemName;

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

        if(PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString) != null){
            int page = PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString).page;
            Map<Integer,Integer> RawSlot = PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString).RawSlot;
            Map<Integer,String> enchantmentName = PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString).enchantmentName;
            String typeName = PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString).typeName;
            String itemName = PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString).itemName;
            int i = event.getRawSlot();
            if(event.getClick() == ClickType.LEFT){
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
                    player.closeInventory();
                }

                if(RawSlot.get(i) != null && RawSlot.get(i) == i){
                    ItemSet itemSet = new ItemSet(player, typeName, itemName);
                    PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString).enchantmentType = enchantmentName.get(i);
                    String m1 = MenuSet.getItemMenuMessage("EnchantmentEditButtom", "Description", "Message");
                    itemSet.clickEnchantment(m1);

                }


                if(page == 1 && i == 45){
                    if(PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString) == null){
                        PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.put(uuidString, new ItemEnchantmentEdit());
                    }
                    if(PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString) != null){
                        PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString).openMenu(player, typeName, itemName, 0);
                    }
                }

                if(page == 0 && i == 53){
                    if(PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString) == null){
                        PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.put(uuidString, new ItemEnchantmentEdit());
                    }
                    if(PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString) != null){
                        PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString).openMenu(player, typeName, itemName, 1);
                    }
                }

            }



        }

    }

    public void openMenu(Player player, String typeName, String itemName, int page){
        String uuidString = player.getUniqueId().toString();

        if(PlayerManager.menu_Inventory4_Map.get(uuidString) == null){
            PlayerManager.menu_Inventory4_Map.put(uuidString, getInventory(typeName, itemName, page));
            Inventory inventory = PlayerManager.menu_Inventory4_Map.get(uuidString);
            player.openInventory(inventory);

        }else {
            PlayerManager.menu_Inventory4_Map.remove(uuidString);
            PlayerManager.menu_Inventory4_Map.put(uuidString, getInventory(typeName, itemName, page));
            Inventory inventory = PlayerManager.menu_Inventory4_Map.get(uuidString);
            player.openInventory(inventory);

        }

    }

    public Inventory getInventory(String typeName, String itemName, int page){
        this.typeName = typeName;
        this.itemName = itemName;
        this.page = page;
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+typeName+".yml");
        Inventory inventory = Bukkit.createInventory(null, 54 , "物品附魔編輯");

        inventory.setItem(4, MenuItem.valueOf(itemMenuConfig, itemName));

        inventory.setItem(0, MenuSet.getItemMenuButtom("EnchantmentEditButtom","ToTypeMenu"));
        inventory.setItem(8, MenuSet.getItemMenuButtom("ItemsButtom","CloseMenu"));

        int i = 10;
        Enchantment[] enchantments = Enchantment.values();
        for(int k = 0 +(page*28); k < enchantments.length ; k++){
            if(i == 44){
                break;
            }
            if(i == 17 || i == 26 || i == 35 || i == 44){
                i+=2;
            }
            this.RawSlot.put(i, i);
            this.enchantmentName.put(i, enchantments[k].getKey().getKey());
            //cd.getLogger().info(enchantments[k].getKey().getKey());
            inventory.setItem(i, MenuSet.getItemMenuButtom("EnchantmentEditButtom",enchantments[k].getKey().getKey()));
            i++;
        }
//        for(Enchantment enchantment : Enchantment.values()){
//
//        }
        inventory.setItem(49,MenuSet.getItemMenuButtom("EnchantmentEditButtom","Description"));

        if(page == 1){
            inventory.setItem(45,MenuSet.getItemMenuButtom("ItemsButtom","PreviousPage"));
        }

        if(page == 0){
            inventory.setItem(53,MenuSet.getItemMenuButtom("ItemsButtom","NextPage"));
        }


        return inventory;
    }


}
