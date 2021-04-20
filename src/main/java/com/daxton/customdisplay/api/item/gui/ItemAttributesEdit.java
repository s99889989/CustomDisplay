package com.daxton.customdisplay.api.item.gui;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.item.ItemSet;
import com.daxton.customdisplay.api.item.MenuItem;
import com.daxton.customdisplay.api.item.MenuSet;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class ItemAttributesEdit {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public String typeName;

    public String itemName;

    public String[] equipmentSlot = {"CHEST", "FEET", "HAND", "HEAD", "LEGS", "OFF_HAND", "ALL"};

    public String[] inherit = {"GENERIC_MAX_HEALTH", "GENERIC_KNOCKBACK_RESISTANCE", "GENERIC_MOVEMENT_SPEED", "GENERIC_ATTACK_DAMAGE", "GENERIC_ATTACK_SPEED", "GENERIC_ARMOR", "GENERIC_ARMOR_TOUGHNESS", "GENERIC_LUCK"};

    public String[] operation = {"ADD_NUMBER", "ADD_SCALAR", "MULTIPLY_SCALAR_1"};

    public int es;

    public int it;

    public int ot;

    public double attrAmount = 0;

    public ItemAttributesEdit(){

    }


    //聊天監聽
    public static void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String uuidString = event.getPlayer().getUniqueId().toString();
        if(PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString) != null){

            String typeName = PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).typeName;
            String itemName = PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).itemName;
            int es = PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).es;
            int it = PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).it;
            int ot = PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).ot;
            ItemSet itemSet = new ItemSet(player, typeName, itemName);
            event.setCancelled(true);
            double amont = 0;
            try {
                amont = Double.parseDouble(event.getMessage());
            }catch (NumberFormatException exception){

            }
            PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).attrAmount = amont;
            itemSet.openAttributesMenu(es, it, ot);
        }
    }
    //按鈕監聽
    public static void onInventoryClick(InventoryClickEvent event){
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        String uuidString = player.getUniqueId().toString();
        if(PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString) != null){
            String[] equipmentSlot = {"CHEST", "FEET", "HAND", "HEAD", "LEGS", "OFF_HAND", "ALL"};

            String[] inherit = {"GENERIC_MAX_HEALTH", "GENERIC_KNOCKBACK_RESISTANCE", "GENERIC_MOVEMENT_SPEED", "GENERIC_ATTACK_DAMAGE", "GENERIC_ATTACK_SPEED", "GENERIC_ARMOR", "GENERIC_ARMOR_TOUGHNESS", "GENERIC_LUCK"};

            String[] operation = {"ADD_NUMBER", "ADD_SCALAR", "MULTIPLY_SCALAR_1"};
            String typeName = PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).typeName;
            String itemName = PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).itemName;
            int es = PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).es;
            int it = PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).it;
            int ot = PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).ot;
            double amount = PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).attrAmount;
            ItemSet itemSet = new ItemSet(player, typeName, itemName);

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
                //給物品
                if(i == 4){
                    itemSet.giveItem();
                }
                //關閉選單
                if(i == 8){
                    player.closeInventory();
                }

                if(i == 10){
                    es++;
                    if(es > 6){
                        es = 0;
                    }
                    PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).openMenu(player, typeName, itemName, es, it, ot);
                }
                if(i == 11){
                    it++;
                    if(it > 7){
                        it = 0;
                    }
                    PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).openMenu(player, typeName, itemName, es, it, ot);
                }
                if(i == 12){
                    ot++;
                    if(ot > 2){
                        ot = 0;
                    }
                    PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).openMenu(player, typeName, itemName, es, it, ot);
                }
                if(i == 13){
                    String m1 = MenuSet.getItemMenuMessage("AttributesEditButtom", "Amount", "Message");
                    itemSet.clickAttributes(m1);
                }
                if(i == 49){
                    itemSet.setAttributes(equipmentSlot[es], inherit[it], operation[ot], amount);
                    PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).openMenu(player, typeName, itemName, es, it, ot);
                }
            }
            if(event.getClick() == ClickType.RIGHT){
                if(i == 10){
                    es--;
                    if(es < 0){
                        es = 6;
                    }
                    PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).openMenu(player, typeName, itemName, es, it, ot);
                }
                if(i == 11){
                    it--;
                    if(it < 0){
                        it = 7;
                    }
                    PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).openMenu(player, typeName, itemName, es, it, ot);
                }
                if(i == 12){
                    ot--;
                    if(ot < 0){
                        ot = 2;
                    }
                    PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).openMenu(player, typeName, itemName, es, it, ot);
                }
                if(i == 49){
                    itemSet.removeAttributes();
                    PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).openMenu(player, typeName, itemName, es, it, ot);
                }
            }


        }
    }


    public void openMenu(Player player, String typeName, String itemName, int es, int it, int ot){
        String uuidString = player.getUniqueId().toString();

        if(PlayerManager.menu_Inventory5_Map.get(uuidString) == null){
            PlayerManager.menu_Inventory5_Map.put(uuidString, getInventory(typeName, itemName, es, it, ot));
            Inventory inventory = PlayerManager.menu_Inventory5_Map.get(uuidString);
            player.openInventory(inventory);

        }else {
            PlayerManager.menu_Inventory5_Map.remove(uuidString);
            PlayerManager.menu_Inventory5_Map.put(uuidString, getInventory(typeName, itemName, es, it, ot));
            Inventory inventory = PlayerManager.menu_Inventory5_Map.get(uuidString);
            player.openInventory(inventory);

        }

    }

    public Inventory getInventory(String typeName, String itemName, int es, int it, int ot){
        this.typeName = typeName;
        this.itemName = itemName;
        this.es = es;
        this.it = it;
        this.ot = ot;
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+typeName+".yml");
        Inventory inventory = Bukkit.createInventory(null, 54 , "物品附魔編輯");

        inventory.setItem(4, MenuItem.valueOf(itemMenuConfig, itemName));

        inventory.setItem(0, MenuSet.getItemMenuButtom("EnchantmentEditButtom","ToTypeMenu"));
        inventory.setItem(8, MenuSet.getItemMenuButtom("ItemsButtom","CloseMenu"));

        inventory.setItem(10, MenuSet.getItemMenuButtom2("AttributesEditButtom","EquipmentSlot", this.equipmentSlot[es]));
        inventory.setItem(11, MenuSet.getItemMenuButtom2("AttributesEditButtom","Inherit", this.inherit[it]));
        inventory.setItem(12, MenuSet.getItemMenuButtom2("AttributesEditButtom","Operation", this.operation[ot]));
        inventory.setItem(13, MenuSet.getItemMenuButtom2("AttributesEditButtom","Amount", String.valueOf(this.attrAmount)));


        inventory.setItem(49,MenuSet.getItemMenuButtom("AttributesEditButtom","Application"));


        return inventory;
    }

}
