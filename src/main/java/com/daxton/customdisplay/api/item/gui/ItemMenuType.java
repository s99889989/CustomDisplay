package com.daxton.customdisplay.api.item.gui;

import com.daxton.customdisplay.CustomDisplay;
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

public class ItemMenuType {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public Map<Integer,Integer> RawSlot = new HashMap<>();

    public Map<Integer,String> itmeName = new HashMap<>();

    public String typeName;

    //public String itemStringName;

    public int nextPageCount;
    public boolean haveNext;

    public int previousPageCount;
    public boolean havePrevioust;

    public FileConfiguration itemMenuConfig;

    public ItemMenuType(){

    }
    //聊天監聽
    public static void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String uuidString = event.getPlayer().getUniqueId().toString();
        if(PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString) != null){
            event.setCancelled(true);
            String typeName = PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString).typeName;

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
        if(PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString) != null){
            Map<Integer,Integer> RawSlot = PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString).RawSlot;
            String typeName = PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString).typeName;
            Map<Integer,String> itmeName = PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString).itmeName;
            FileConfiguration itemMenuConfig = PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString).itemMenuConfig;
            int nextPageCount = PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString).nextPageCount;
            boolean haveNext = PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString).haveNext;
            int previousPageCount = PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString).previousPageCount;
            boolean havePrevioust = PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString).havePrevioust;

            ItemSet itemSet = new ItemSet(player, typeName);

            if(event.getClick() == ClickType.LEFT){
                if(i == 0){
                    if(PlayerManager.menu_Inventory_ItemMenu_Map.get(uuidString) != null){
                        PlayerManager.menu_Inventory_ItemMenu_Map.get(uuidString).openMenu(player);
                    }
                    return;
                }
                if(i == 8){
                    player.closeInventory();
                    return;
                }
                if(havePrevioust && i == 45){
                    if(PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString) == null){
                        PlayerManager.menu_Inventory_ItemMenuType_Map.put(uuidString, new ItemMenuType());
                    }
                    if(PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString) != null){
                        PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString).openMenu(player, typeName, previousPageCount);
                    }
                    return;
                }

                if(i == 51){
                    String ms1 = MenuSet.getItemMenuMessage("ItemsButtom","Create","Message");
                    itemSet.clickItemMenuType(ms1);

                    return;

                }

                if(haveNext && i == 53){
                    if(PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString) == null){
                        PlayerManager.menu_Inventory_ItemMenuType_Map.put(uuidString, new ItemMenuType());
                    }
                    if(PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString) != null){
                        PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString).openMenu(player, typeName, nextPageCount);
                    }
                    return;
                }
                if(RawSlot.get(i) != null && RawSlot.get(i) == i){
                    ItemStack itemStack = MenuItem.valueOf(itemMenuConfig, itmeName.get(i));
                    player.getInventory().addItem(itemStack);
                    return;
                }
            }
            if(event.getClick() == ClickType.RIGHT){
                if(RawSlot.get(i) != null && RawSlot.get(i) == i){
                    if(PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString) == null){
                        PlayerManager.menu_Inventory_ItemMenuEdit_Map.put(uuidString, new ItemMenuEdit());
                    }
                    if(PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString) != null){
                        PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString).openMenu(player, typeName,itmeName.get(i));
                    }
                    return;
                }

            }



        }

    }


    public void openMenu(Player player, String typeName, int itemCount){
        this.nextPageCount = itemCount;

        String uuidString = player.getUniqueId().toString();
        if(PlayerManager.menu_Inventory2_Map.get(uuidString) == null){
            PlayerManager.menu_Inventory2_Map.put(uuidString, getInventory(typeName, itemCount));
            Inventory inventory = PlayerManager.menu_Inventory2_Map.get(uuidString);
            player.openInventory(inventory);
        }else{
            PlayerManager.menu_Inventory2_Map.remove(uuidString);
            PlayerManager.menu_Inventory2_Map.put(uuidString, getInventory(typeName, itemCount));
            Inventory inventory = PlayerManager.menu_Inventory2_Map.get(uuidString);
            player.openInventory(inventory);
        }
    }


    public Inventory getInventory(String typeName, int itemCount){
        this.typeName = typeName;
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+ typeName +".yml");
        this.itemMenuConfig = itemMenuConfig;

        Inventory inventory = Bukkit.createInventory(null, 54 , typeName);

        int i = 10;
        this.havePrevioust = false;
        if(itemCount > 0){
            this.havePrevioust = true;
            this.previousPageCount = this.nextPageCount-28;
            inventory.setItem(45,MenuSet.getItemMenuButtom("ItemsButtom","PreviousPage"));
        }
        this.RawSlot.clear();
        this.itmeName.clear();

        String[] stringArray = MenuSet.getItemMenuButtomNameArray(itemMenuConfig, "");

        for(int k = 0+itemCount; k < stringArray.length ;k++){
            if(i == 44){
                break;
            }
            if(i == 17 || i == 26 || i == 35 || i == 44){
                i+=2;
            }
            this.RawSlot.put(i,i);
            this.itmeName.put(i,stringArray[k]);
            inventory.setItem(i, MenuItem.valueOf(itemMenuConfig, stringArray[k]));
            i++;
            this.nextPageCount++;
        }

        this.haveNext = false;
        if(stringArray.length-itemCount > 28){
            this.haveNext = true;
            inventory.setItem(53,MenuSet.getItemMenuButtom("ItemsButtom","NextPage"));
        }



        inventory.setItem(0, MenuSet.getItemMenuButtom("ItemsButtom","ToMainMenu"));
        inventory.setItem(8,MenuSet.getItemMenuButtom("ItemsButtom","CloseMenu"));
        inventory.setItem(47,MenuSet.getItemMenuButtom("ItemsButtom","Delete"));
        inventory.setItem(49,MenuSet.getItemMenuButtom("ItemsButtom","Description"));
        inventory.setItem(51,MenuSet.getItemMenuButtom("ItemsButtom","Create"));

        return inventory;
    }


}
