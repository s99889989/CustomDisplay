package com.daxton.customdisplay.api.item.gui;

import com.daxton.customdisplay.api.item.ItemSet;
import com.daxton.customdisplay.api.item.MenuItem;
import com.daxton.customdisplay.api.item.MenuSet;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.EditorGUIManager;
import com.daxton.customdisplay.manager.player.PlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;


public class EditItem {

    public String typeName;

    public String itemID;

    public String editType = "";

    public EditItem(){

    }

    //聊天監聽
    public static void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String uuidString = event.getPlayer().getUniqueId().toString();
        if(EditorGUIManager.menu_EditItem_Map.get(uuidString) != null){
            event.setCancelled(true);
            String editType = EditorGUIManager.menu_EditItem_Map.get(uuidString).editType;
            String typeName = EditorGUIManager.menu_EditItem_Map.get(uuidString).typeName;
            String itemName = EditorGUIManager.menu_EditItem_Map.get(uuidString).itemID;
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
        if(EditorGUIManager.menu_EditItem_Map.get(uuidString) != null){

            String typeName = EditorGUIManager.menu_EditItem_Map.get(uuidString).typeName;
            String itemName = EditorGUIManager.menu_EditItem_Map.get(uuidString).itemID;

            ItemSet itemSet = new ItemSet(player, typeName, itemName);

            int i = event.getRawSlot();

            if(event.getClick() == ClickType.SHIFT_LEFT){
                //修改物品類型
                if(i == 11){
                    String ms1 = MenuSet.getItemMenuMessage("EditItem", "EditMaerial","Message");
                    String ms2 = MenuSet.getItemMenuMessage("EditItem", "EditMaerial","SubMessage");
                    itemSet.clickEditItem("EditMaerial", ms1, ms2);
                    return;
                }
            }

            //左鍵
            if(event.getClick() == ClickType.LEFT){
                //給物品
                if(i == 4){
                    itemSet.giveItem();
                    return;
                }

                //修改物品名稱
                if(i == 10){
                    String ms1 = MenuSet.getItemMenuMessage("EditItem", "EditDisplayName","Message");
                    String ms2 = MenuSet.getItemMenuMessage("EditItem", "EditDisplayName","SubMessage");
                    itemSet.clickEditItem("EditDisplayName", ms1, ms2);
                    return;
                }
                //修改物品類型
                if(i == 11){
                    new OpenMenuGUI(player).ItemList(typeName, itemName, 0);
                    return;
                }
                //修改物品耐久Data
                if(i == 12){
                    String ms1 = MenuSet.getItemMenuMessage("EditItem", "EditData","Message");
                    String ms2 = MenuSet.getItemMenuMessage("EditItem", "EditData","SubMessage");
                    itemSet.clickEditItem("EditData", ms1, ms2);
                    return;
                }
                //修改物品CustomModelData
                if(i == 13){
                    String ms1 = MenuSet.getItemMenuMessage("EditItem", "EditCustomModelData","Message");
                    String ms2 = MenuSet.getItemMenuMessage("EditItem", "EditCustomModelData","SubMessage");
                    itemSet.clickEditItem("EditCustomModelData", ms1, ms2);
                    return;
                }
                //修改物品Lore
                if(i == 14){
                    new OpenMenuGUI(player).EditLore(typeName, itemName);
                    return;
                }
                //設置不可破壞
                if(i == 15){
                    itemSet.setUnbreakable();
                    itemSet.openEditMenu();
                    return;
                }
                //設置隱藏Flag
                if(i == 16){
                    new OpenMenuGUI(player).EditFlags(typeName, itemName);
                    return;
                }
                //設置物品附魔
                if(i == 19){
                    new OpenMenuGUI(player).EditEnchantment(typeName, itemName, 0);
                    return;
                }
                //設置物品屬性
                if(i == 20){
                    new OpenMenuGUI(player).EditAttributes(typeName, itemName, 0, 0, 0);
                    return;
                }
                //設置頭值
                if(i == 21){
                    String ms1 = MenuSet.getItemMenuMessage("EditItem", "EditHeadValue","Message");
                    String ms2 = MenuSet.getItemMenuMessage("EditItem", "EditHeadValue","SubMessage");
                    itemSet.clickEditItem("EditHeadValue", ms1, ms2);
                    return;
                }
                //設置冷卻值
//                if(i == 22){
//                    String m1 = MenuSet.getItemMenuMessage("ItemsEditButtom", "CoolDown", "Message");
//                    itemSet.click("EditCoolDown", m1);
//                    return;
//                }

                //到物品類別
                if(i == 0){
                    new OpenMenuGUI(player).SelectItems(typeName, 0);
                    return;
                }

                //關閉選單
                if(i == 8){
                    player.closeInventory();
                    return;
                }
            }

            if(event.getClick() == ClickType.RIGHT){
                //移除物品名稱
                if(i == 10){
                    itemSet.removeDisplayName();
                    itemSet.openEditMenu();
                    return;
                }
                //物品類型改為預設
                if(i == 11){
                    itemSet.setDefaultMaterial();
                    itemSet.openEditMenu();
                    return;
                }
                //移除物品耐久Data
                if(i == 12){
                    itemSet.removeData();
                    itemSet.openEditMenu();
                    return;
                }
                //清除物品CustomModelData
                if(i == 13){
                    itemSet.setCustomModelData("0");
                    itemSet.openEditMenu();
                    return;
                }
                //移除物品Lore
                if(i == 14){
                    itemSet.removeLore();
                    itemSet.openEditMenu();
                    return;
                }
                //設置可破壞
                if(i == 15){
                    itemSet.setBreakable();
                    itemSet.openEditMenu();
                    return;
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
                    return;
                }
                //移除物品附魔
                if(i == 19){
                    itemSet.removeEnchantment();
                    itemSet.openEditMenu();
                    return;
                }
                //移除物品屬性
                if(i == 20){
                    itemSet.removeAttributes();
                    itemSet.openEditMenu();
                    return;
                }
                //移除頭值
                if(i == 21){
                    itemSet.removeHeadValue();
                    itemSet.openEditMenu();

                }
                //移除冷卻值
//                if(i == 22){
//                    itemSet.removeCoolDown();
//                    itemSet.openEditMenu();
//                }
            }

        }
    }

    public void openMenu(Player player, String typeName, String itemName){
        String uuidString = player.getUniqueId().toString();

        if(EditorGUIManager.menu_EditItem_Inventory_Map.get(uuidString) == null){
            EditorGUIManager.menu_EditItem_Inventory_Map.put(uuidString, getInventory(typeName, itemName));
            Inventory inventory = EditorGUIManager.menu_EditItem_Inventory_Map.get(uuidString);
            player.openInventory(inventory);

        }else {
            EditorGUIManager.menu_EditItem_Inventory_Map.remove(uuidString);
            EditorGUIManager.menu_EditItem_Inventory_Map.put(uuidString, getInventory(typeName, itemName));
            Inventory inventory = EditorGUIManager.menu_EditItem_Inventory_Map.get(uuidString);
            player.openInventory(inventory);

        }

    }

    public Inventory getInventory(String typeName, String itemName){
        this.typeName = typeName;
        this.itemID = itemName;
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+typeName+".yml");

        Inventory inventory = Bukkit.createInventory(null, 54 , MenuSet.getGuiTitle("EditItem"));


        inventory.setItem(4, MenuItem.valueOf(itemMenuConfig, itemName));


        inventory.setItem(0, MenuSet.getItemButtom("Buttom", "EditItem","ToSelectItems"));
        inventory.setItem(8, MenuSet.getItemButtom("Buttom", "EditItem","Exit"));

        inventory.setItem(10, MenuSet.getItemButtom("Buttom", "EditItem","EditDisplayName"));
        inventory.setItem(11, MenuSet.getItemButtom("Buttom", "EditItem","EditMaerial"));
        inventory.setItem(12, MenuSet.getItemButtom("Buttom", "EditItem","EditData"));
        inventory.setItem(13, MenuSet.getItemButtom("Buttom", "EditItem","EditCustomModelData"));
        inventory.setItem(14, MenuSet.getItemButtom("Buttom", "EditItem","EditLore"));
        inventory.setItem(15, MenuSet.getItemButtom("Buttom", "EditItem","EditUnbreakable"));
        inventory.setItem(16, MenuSet.getItemButtom("Buttom", "EditItem","EditItemFlags"));
        inventory.setItem(19, MenuSet.getItemButtom("Buttom", "EditItem","EditEnchantments"));
        inventory.setItem(20, MenuSet.getItemButtom("Buttom", "EditItem","EditAttributes"));
        inventory.setItem(21, MenuSet.getItemButtom("Buttom", "EditItem","EditHeadValue"));
//        inventory.setItem(22, MenuSet.getItemButtom("Buttom", "EditItem","CoolDown"));
//        inventory.setItem(23, MenuSet.getItemButtom("Buttom", "EditItem","Action"));

        inventory.setItem(49,MenuSet.getItemButtom("Buttom", "EditItem","Description"));

        return inventory;
    }


}
