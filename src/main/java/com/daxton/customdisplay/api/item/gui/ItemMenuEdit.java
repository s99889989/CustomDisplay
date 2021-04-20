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


import java.util.HashMap;
import java.util.Map;


public class ItemMenuEdit {

    public Map<Integer,Integer> RawSlot = new HashMap<>();

    public String typeName;

    public String itemName;

    public String editType = "";

    public ItemMenuEdit(){

    }

    //聊天監聽
    public static void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String uuidString = event.getPlayer().getUniqueId().toString();
        if(PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString) != null){
            event.setCancelled(true);
            String editType = PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString).editType;
            String typeName = PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString).typeName;
            String itemName = PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString).itemName;
            String chatString = event.getMessage().replace("&", "§");
            ItemSet itemSet = new ItemSet(player, typeName, itemName);
            switch (editType){
                case "EditDisplayName":
                    itemSet.setDisplayName(chatString);
                    itemSet.openEditMenu();
                    break;
                case "EditMaerial":
                    itemSet.setMaterial(chatString);
                    itemSet.openEditMenu();
                    break;
                case "EditData":
                    itemSet.setData(chatString);
                    itemSet.openEditMenu();
                    break;
                case "EditCustomModelData":
                    itemSet.setCustomModelData(chatString);
                    itemSet.openEditMenu();
                    break;
                case "EditLore":
                    itemSet.setLore(chatString);
                    itemSet.openEditMenu();
                    break;
                case "EditHeadValue":
                    itemSet.setHeadValue(chatString);
                    itemSet.openEditMenu();
                    break;
                case "EditCoolDown":
                    itemSet.setCoolDown(chatString);
                    itemSet.openEditMenu();
                    break;
                default:
                    itemSet.openEditMenu();
            }


        }
    }

    //按鈕監聽
    public static void onInventoryClick(InventoryClickEvent event){
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        String uuidString = player.getUniqueId().toString();
        if(PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString) != null){

            String typeName = PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString).typeName;
            String itemName = PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString).itemName;

            ItemSet itemSet = new ItemSet(player, typeName, itemName);

            int i = event.getRawSlot();

            if(event.getClick() == ClickType.SHIFT_LEFT){
                //修改物品類型
                if(i == 11){
                    String m1 = MenuSet.getItemMenuMessage("ItemsEditButtom", "EditMaerial", "Message");
                    itemSet.click("EditMaerial", m1);
                }
            }
            //左鍵
            if(event.getClick() == ClickType.LEFT){
                //給物品
                if(i == 4){
                    itemSet.giveItem();
                }

                //修改物品名稱
                if(i == 10){
                    String m1 = MenuSet.getItemMenuMessage("ItemsEditButtom", "EditDisplayName", "Message");
                    itemSet.click("EditDisplayName", m1);
                }
                //修改物品類型
                if(i == 11){
                    if(PlayerManager.menu_Inventory_ItemListMenu_Map.get(uuidString) == null){
                        PlayerManager.menu_Inventory_ItemListMenu_Map.put(uuidString, new ItemListMenu());
                    }
                    if(PlayerManager.menu_Inventory_ItemListMenu_Map.get(uuidString) != null){
                        PlayerManager.menu_Inventory_ItemListMenu_Map.get(uuidString).openMenu(player, typeName, itemName, 0);
                    }
                }
                //修改物品耐久Data
                if(i == 12){
                    String m1 = MenuSet.getItemMenuMessage("ItemsEditButtom", "EditData", "Message");
                    itemSet.click("EditData", m1);
                }
                //修改物品CustomModelData
                if(i == 13){
                    String m1 = MenuSet.getItemMenuMessage("ItemsEditButtom", "EditCustomModelData", "Message");
                    itemSet.click("EditCustomModelData", m1);
                }
                //修改物品Lore
                if(i == 14){
                    String m1 = MenuSet.getItemMenuMessage("ItemsEditButtom", "EditLore", "Message");
                    itemSet.click("EditLore", m1);
                }
                //設置不可破壞
                if(i == 15){
                    itemSet.setUnbreakable();
                    itemSet.openEditMenu();
                }
                //設置隱藏Flag
                if(i == 16){
                    if(PlayerManager.menu_Inventory_ItemFlagsEdit_Map.get(uuidString) == null){
                        PlayerManager.menu_Inventory_ItemFlagsEdit_Map.put(uuidString, new ItemFlagsEdit());
                    }
                    if(PlayerManager.menu_Inventory_ItemFlagsEdit_Map.get(uuidString) != null){
                        PlayerManager.menu_Inventory_ItemFlagsEdit_Map.get(uuidString).openMenu(player, typeName, itemName);
                    }
                }
                //設置物品附魔
                if(i == 19){
                    if(PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString) == null){
                        PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.put(uuidString, new ItemEnchantmentEdit());
                    }
                    if(PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString) != null){
                        PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString).openMenu(player, typeName, itemName, 0);
                    }
                }
                //設置物品屬性
                if(i == 20){
                    if(PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString) == null){
                        PlayerManager.menu_Inventory_ItemAttributesEdit_Map.put(uuidString, new ItemAttributesEdit());
                    }
                    if(PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString) != null){
                        PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).openMenu(player, typeName, itemName, 0, 0, 0);
                    }
                }
                //設置頭值
                if(i == 21){
                    String m1 = MenuSet.getItemMenuMessage("ItemsEditButtom", "HeadValue", "Message");
                    itemSet.click("EditHeadValue", m1);
                }
                //設置冷卻值
                if(i == 22){
                    String m1 = MenuSet.getItemMenuMessage("ItemsEditButtom", "CoolDown", "Message");
                    itemSet.click("EditCoolDown", m1);
                }

                //到物品類別
                if(i == 0){
                    if(PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString) == null){
                        PlayerManager.menu_Inventory_ItemMenuType_Map.put(uuidString, new ItemMenuType());
                    }
                    if(PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString) != null){
                        PlayerManager.menu_Inventory_ItemMenuType_Map.get(uuidString).openMenu(player, typeName, 0);
                    }
                }

                //關閉選單
                if(i == 8){
                    player.closeInventory();
                }
            }

            if(event.getClick() == ClickType.RIGHT){
                //移除物品名稱
                if(i == 10){
                    itemSet.removeDisplayName();
                    itemSet.openEditMenu();
                }
                //物品類型改為預設
                if(i == 11){
                    itemSet.setDefaultMaterial();
                    itemSet.openEditMenu();
                }
                //移除物品耐久Data
                if(i == 12){
                    itemSet.removeData();
                    itemSet.openEditMenu();
                }
                //清除物品CustomModelData
                if(i == 13){
                    itemSet.setCustomModelData("0");
                    itemSet.openEditMenu();
                }
                //移除物品Lore
                if(i == 14){
                    itemSet.removeLore();
                    itemSet.openEditMenu();
                }
                //設置可破壞
                if(i == 15){
                    itemSet.setBreakable();
                    itemSet.openEditMenu();
                }
                //設置不隱藏Flag
                if(i == 16){
                    itemSet.setUnHideAttributes();
                    itemSet.setUnHideDestroys();
                    itemSet.setUnHideDye();
                    itemSet.setUnHideEnchants();
                    itemSet.setUnHidePlacedOn();
                    itemSet.setUnHidePotionEffects();
                    itemSet.setUnHideUnbreakable();
                    itemSet.openEditMenu();
                }
                //移除物品附魔
                if(i == 19){
                    itemSet.removeEnchantment();
                    itemSet.openEditMenu();
                }
                //移除物品屬性
                if(i == 20){
                    itemSet.removeAttributes();
                    itemSet.openEditMenu();
                }
                //移除頭值
                if(i == 21){
                    itemSet.removeHeadValue();
                    itemSet.openEditMenu();
                }
                //移除冷卻值
                if(i == 22){
                    itemSet.removeCoolDown();
                    itemSet.openEditMenu();
                }
            }

        }
    }

    public void openMenu(Player player, String typeName, String itemName){
        String uuidString = player.getUniqueId().toString();

        if(PlayerManager.menu_Inventory3_Map.get(uuidString) == null){
            PlayerManager.menu_Inventory3_Map.put(uuidString, getInventory(typeName, itemName));
            Inventory inventory = PlayerManager.menu_Inventory3_Map.get(uuidString);
            player.openInventory(inventory);

        }else {
            PlayerManager.menu_Inventory3_Map.remove(uuidString);
            PlayerManager.menu_Inventory3_Map.put(uuidString, getInventory(typeName, itemName));
            Inventory inventory = PlayerManager.menu_Inventory3_Map.get(uuidString);
            player.openInventory(inventory);

        }

    }

    public Inventory getInventory(String typeName, String itemName){
        this.typeName = typeName;
        this.itemName = itemName;
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+typeName+".yml");
        //FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_Default.yml");
        Inventory inventory = Bukkit.createInventory(null, 54 , "物品編輯");

        if(itemName != null){ //typeName != null &&
            inventory.setItem(4, MenuItem.valueOf(itemMenuConfig, itemName));
        }

        inventory.setItem(0, MenuSet.getItemMenuButtom("ItemsEditButtom","ToTypeMenu"));
        inventory.setItem(8, MenuSet.getItemMenuButtom("ItemsButtom","CloseMenu"));

        inventory.setItem(10, MenuSet.getItemMenuButtom("ItemsEditButtom","EditDisplayName"));
        inventory.setItem(11, MenuSet.getItemMenuButtom("ItemsEditButtom","EditMaerial"));
        inventory.setItem(12, MenuSet.getItemMenuButtom("ItemsEditButtom","EditData"));
        inventory.setItem(13, MenuSet.getItemMenuButtom("ItemsEditButtom","EditCustomModelData"));
        inventory.setItem(14, MenuSet.getItemMenuButtom("ItemsEditButtom","EditLore"));
        inventory.setItem(15, MenuSet.getItemMenuButtom("ItemsEditButtom","Unbreakable"));
        inventory.setItem(16, MenuSet.getItemMenuButtom("ItemsEditButtom","HideItemFlags"));
        inventory.setItem(19, MenuSet.getItemMenuButtom("ItemsEditButtom","Enchantments"));
        inventory.setItem(20, MenuSet.getItemMenuButtom("ItemsEditButtom","Attributes"));
        inventory.setItem(21, MenuSet.getItemMenuButtom("ItemsEditButtom","HeadValue"));
        inventory.setItem(22, MenuSet.getItemMenuButtom("ItemsEditButtom","CoolDown"));
        inventory.setItem(23, MenuSet.getItemMenuButtom("ItemsEditButtom","Action"));

        inventory.setItem(49,MenuSet.getItemMenuButtom("ItemsEditButtom","Description"));

        return inventory;
    }


}
