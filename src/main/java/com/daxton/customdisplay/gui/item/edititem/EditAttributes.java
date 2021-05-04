package com.daxton.customdisplay.gui.item.edititem;

import com.daxton.customdisplay.api.item.gui.ButtomSet;
import com.daxton.customdisplay.gui.item.ItemSet;
import com.daxton.customdisplay.api.item.MenuItem;
import com.daxton.customdisplay.api.item.gui.MenuSet;
import com.daxton.customdisplay.gui.item.OpenMenuGUI;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.EditorGUIManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;

public class EditAttributes {

    public String typeName;

    public String itemName;

    public String[] equipmentSlot = {"CHEST", "FEET", "HAND", "HEAD", "LEGS", "OFF_HAND", "ALL"};

    public String[] inherit = {"GENERIC_MAX_HEALTH", "GENERIC_KNOCKBACK_RESISTANCE", "GENERIC_MOVEMENT_SPEED", "GENERIC_ATTACK_DAMAGE", "GENERIC_ATTACK_SPEED", "GENERIC_ARMOR", "GENERIC_ARMOR_TOUGHNESS", "GENERIC_LUCK"};

    public String[] operation = {"ADD_NUMBER", "ADD_SCALAR", "MULTIPLY_SCALAR_1"};

    public int es;

    public int it;

    public int ot;

    public double attrAmount = 0;

    public EditAttributes(){

    }


    //聊天監聽
    public static void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String uuidString = event.getPlayer().getUniqueId().toString();
        if(EditorGUIManager.menu_EditAttributes_Map.get(uuidString) != null){
            event.setCancelled(true);

            String typeName = EditorGUIManager.menu_EditAttributes_Map.get(uuidString).typeName;
            String itemName = EditorGUIManager.menu_EditAttributes_Map.get(uuidString).itemName;
            int es = EditorGUIManager.menu_EditAttributes_Map.get(uuidString).es;
            int it = EditorGUIManager.menu_EditAttributes_Map.get(uuidString).it;
            int ot = EditorGUIManager.menu_EditAttributes_Map.get(uuidString).ot;
            ItemSet itemSet = new ItemSet(player, typeName, itemName);

            double amont = 0;
            try {
                amont = Double.parseDouble(event.getMessage());
            }catch (NumberFormatException exception){
                exception.printStackTrace();
            }
            EditorGUIManager.menu_EditAttributes_Map.get(uuidString).attrAmount = amont;
            itemSet.openAttributesMenu(es, it, ot);
        }
    }
    //按鈕監聽
    public static void onInventoryClick(InventoryClickEvent event){
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        String uuidString = player.getUniqueId().toString();
        if(EditorGUIManager.menu_EditAttributes_Map.get(uuidString) != null){
            String[] equipmentSlot = {"CHEST", "FEET", "HAND", "HEAD", "LEGS", "OFF_HAND", "ALL"};

            String[] inherit = {"GENERIC_MAX_HEALTH", "GENERIC_KNOCKBACK_RESISTANCE", "GENERIC_MOVEMENT_SPEED", "GENERIC_ATTACK_DAMAGE", "GENERIC_ATTACK_SPEED", "GENERIC_ARMOR", "GENERIC_ARMOR_TOUGHNESS", "GENERIC_LUCK"};

            String[] operation = {"ADD_NUMBER", "ADD_SCALAR", "MULTIPLY_SCALAR_1"};
            String typeName = EditorGUIManager.menu_EditAttributes_Map.get(uuidString).typeName;
            String itemName = EditorGUIManager.menu_EditAttributes_Map.get(uuidString).itemName;
            int es = EditorGUIManager.menu_EditAttributes_Map.get(uuidString).es;
            int it = EditorGUIManager.menu_EditAttributes_Map.get(uuidString).it;
            int ot = EditorGUIManager.menu_EditAttributes_Map.get(uuidString).ot;
            double amount = EditorGUIManager.menu_EditAttributes_Map.get(uuidString).attrAmount;
            ItemSet itemSet = new ItemSet(player, typeName, itemName);

            int i = event.getRawSlot();

            if(event.getClick() == ClickType.LEFT){
                //到物品類別
                if(i == 0){
                    OpenMenuGUI.EditItem(player, typeName, itemName);
                    return;
                }
                //給物品
                if(i == 4){
                    itemSet.giveItem();
                    return;
                }
                //關閉選單
                if(i == 8){
                    player.closeInventory();
                    return;
                }

                if(i == 10){
                    es++;
                    if(es > 6){
                        es = 0;
                    }
                    EditorGUIManager.menu_EditAttributes_Map.get(uuidString).openMenu(player, typeName, itemName, es, it, ot);
                    return;
                }
                if(i == 11){
                    it++;
                    if(it > 7){
                        it = 0;
                    }
                    EditorGUIManager.menu_EditAttributes_Map.get(uuidString).openMenu(player, typeName, itemName, es, it, ot);
                    return;
                }
                if(i == 12){
                    ot++;
                    if(ot > 2){
                        ot = 0;
                    }
                    EditorGUIManager.menu_EditAttributes_Map.get(uuidString).openMenu(player, typeName, itemName, es, it, ot);
                    return;
                }
                if(i == 13){
                    String ms1 = MenuSet.getItemMenuMessage("EditAttributes", "Amount", "Message");
                    String ms2 = MenuSet.getItemMenuMessage("EditAttributes","Amount","SubMessage");
                    itemSet.clickEditAttributes(ms1,ms2);
                    return;
                }
                if(i == 49){
                    itemSet.setAttributes(equipmentSlot[es], inherit[it], operation[ot], amount);
                    EditorGUIManager.menu_EditAttributes_Map.get(uuidString).openMenu(player, typeName, itemName, es, it, ot);
                    return;
                }
            }
            if(event.getClick() == ClickType.RIGHT){
                if(i == 10){
                    es--;
                    if(es < 0){
                        es = 6;
                    }
                    EditorGUIManager.menu_EditAttributes_Map.get(uuidString).openMenu(player, typeName, itemName, es, it, ot);
                    return;
                }
                if(i == 11){
                    it--;
                    if(it < 0){
                        it = 7;
                    }
                    EditorGUIManager.menu_EditAttributes_Map.get(uuidString).openMenu(player, typeName, itemName, es, it, ot);
                    return;
                }
                if(i == 12){
                    ot--;
                    if(ot < 0){
                        ot = 2;
                    }
                    EditorGUIManager.menu_EditAttributes_Map.get(uuidString).openMenu(player, typeName, itemName, es, it, ot);
                    return;
                }
                if(i == 49){
                    itemSet.removeAttributes();
                    EditorGUIManager.menu_EditAttributes_Map.get(uuidString).openMenu(player, typeName, itemName, es, it, ot);
                }
            }


        }
    }


    public void openMenu(Player player, String typeName, String itemName, int es, int it, int ot){
        String uuidString = player.getUniqueId().toString();

        if(EditorGUIManager.menu_EditAttributes_Inventory_Map.get(uuidString) == null){
            EditorGUIManager.menu_EditAttributes_Inventory_Map.put(uuidString, getInventory(typeName, itemName, es, it, ot));
            Inventory inventory = EditorGUIManager.menu_EditAttributes_Inventory_Map.get(uuidString);
            player.openInventory(inventory);

        }else {
            EditorGUIManager.menu_EditAttributes_Inventory_Map.remove(uuidString);
            EditorGUIManager.menu_EditAttributes_Inventory_Map.put(uuidString, getInventory(typeName, itemName, es, it, ot));
            Inventory inventory = EditorGUIManager.menu_EditAttributes_Inventory_Map.get(uuidString);
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
        Inventory inventory = Bukkit.createInventory(null, 54 , MenuSet.getGuiTitle("EditAttributes"));

        inventory.setItem(4, MenuItem.valueOf(itemMenuConfig, itemName));

        inventory.setItem(0,  ButtomSet.getItemButtom("Buttom.EditAttributes.ToEditItem", ""));
        inventory.setItem(8,  ButtomSet.getItemButtom("Buttom.EditAttributes.Exit", ""));

        inventory.setItem(10, ButtomSet.getItemButtom("Buttom.EditAttributes.EquipmentSlot", this.equipmentSlot[es]));
        inventory.setItem(11, ButtomSet.getItemButtom("Buttom.EditAttributes.Inherit", this.inherit[it]));
        inventory.setItem(12, ButtomSet.getItemButtom("Buttom.EditAttributes.Operation", this.operation[ot]));
        inventory.setItem(13, ButtomSet.getItemButtom("Buttom.EditAttributes.Amount", String.valueOf(this.attrAmount)));


        inventory.setItem(49, ButtomSet.getItemButtom("Buttom.EditAttributes.Application", ""));


        return inventory;
    }

}
