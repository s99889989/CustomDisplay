package com.daxton.customdisplay.gui.item.edititem.editaction;

import com.daxton.customdisplay.api.item.CustomItem2;
import com.daxton.customdisplay.api.item.MenuItem;
import com.daxton.customdisplay.api.gui.ButtomSet;
import com.daxton.customdisplay.api.gui.MenuSet;
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

public class ActionTargetEdit {

    public String typeName;

    public String itemName;

    public int tt;

    public double radius;

    public String filters = "null";

    public int actionOrder;
    public String actionOrderType = "";

    public String editType = "";

    public ActionTargetEdit(){

    }

    //聊天監聽
    public static void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String uuidString = event.getPlayer().getUniqueId().toString();
        if(EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString) != null){
            event.setCancelled(true);

            String typeName = EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).typeName;
            String itemName = EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).itemName;
            int tt = EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).tt;

            int actionOrder = EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).actionOrder;
            String actionOrderType = EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).actionOrderType;

            ItemActionSet itemActionSet = new ItemActionSet(player, typeName, itemName);

            double amont = 0;
            try {
                amont = Double.parseDouble(event.getMessage());
            }catch (NumberFormatException exception){
                exception.printStackTrace();
            }
            EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).radius = amont;
            itemActionSet.clickEditTargetEnd(tt, actionOrder, actionOrderType);
        }
    }
    //按鈕監聽
    public static void onInventoryClick(InventoryClickEvent event){
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        String uuidString = player.getUniqueId().toString();
        if(EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString) != null){
            String[] targetTypeArray = {"@Self", "@Target", "@SelfRadius", "@TargetRadius"}; //, "@SelfInWorld", "@Server"

            String typeName = EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).typeName;
            String itemName = EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).itemName;
            int tt = EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).tt;

            int actionOrder = EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).actionOrder;
            String actionOrderType = EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).actionOrderType;

            double radius = EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).radius;

            String filters = EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).filters;

            ItemActionSet itemActionSet = new ItemActionSet(player, typeName, itemName);
            int i = event.getRawSlot();

            if(event.getClick() == ClickType.LEFT){
                //到物品類別
                if(i == 0){
                    OpenMenuGUI.EditActionDetail(player, typeName, itemName, actionOrder, actionOrderType);
                    return;
                }
                //給物品
                if(i == 4){
                    itemActionSet.giveItem();
                    return;
                }
                //關閉選單
                if(i == 8){
                    itemActionSet.closeEditMenu();
                    return;
                }

                if(i == 10){
                    tt++;
                    if(tt > 5){
                        tt = 0;
                    }
                    EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).tt = tt;
                    OpenMenuGUI.ActionTargetEdit(player, typeName, itemName, tt, actionOrder, actionOrderType);
                    return;
                }
                if(i == 11){
                    String ms1 = MenuSet.getItemMenuMessage("ActionTargetEdit", "Radius", "Message");
                    String ms2 = MenuSet.getItemMenuMessage("ActionTargetEdit","Radius","SubMessage");
                    itemActionSet.clickEditTarget(ms1,ms2,"Radius");
                    return;
                }
                if(i == 12){
                    OpenMenuGUI.ActionFiltersList(player, typeName, itemName, 0, actionOrder, actionOrderType);
                    return;
                }

                if(i == 49){
                    String filter = targetTypeArray[tt]+"{Filters="+filters+";R="+radius+"}";
                    itemActionSet.setEditActionShow(new String[]{"targetkey"}, actionOrder, actionOrderType,filter);
                    OpenMenuGUI.EditActionDetail(player, typeName, itemName, actionOrder, actionOrderType);
                    return;
                }
            }
            if(event.getClick() == ClickType.RIGHT){
                if(i == 10){
                    tt--;
                    if(tt < 0){
                        tt = 5;
                    }
                    EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).tt = tt;
                    OpenMenuGUI.ActionTargetEdit(player, typeName, itemName, tt, actionOrder, actionOrderType);
                    return;
                }
                if(i == 11){
                    EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).radius = 1;
                    OpenMenuGUI.ActionTargetEdit(player, typeName, itemName, tt, actionOrder, actionOrderType);
                    return;
                }
                if(i == 12){
                    EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).filters = "null";
                    OpenMenuGUI.ActionTargetEdit(player, typeName, itemName, tt, actionOrder, actionOrderType);
                    return;
                }
                if(i == 49){


                }
            }


        }
    }


    public void openMenu(Player player, String typeName, String itemName, int tt, int actionOrder, String actionOrderType){
        String uuidString = player.getUniqueId().toString();

        if(EditorGUIManager.menu_ActionTargetEdit_Inventory_Map.get(uuidString) == null){
            EditorGUIManager.menu_ActionTargetEdit_Inventory_Map.put(uuidString, getInventory(player, typeName, itemName, tt, actionOrder, actionOrderType));
            Inventory inventory = EditorGUIManager.menu_ActionTargetEdit_Inventory_Map.get(uuidString);
            player.openInventory(inventory);

        }else {
            EditorGUIManager.menu_ActionTargetEdit_Inventory_Map.remove(uuidString);
            EditorGUIManager.menu_ActionTargetEdit_Inventory_Map.put(uuidString, getInventory(player, typeName, itemName, tt, actionOrder, actionOrderType));
            Inventory inventory = EditorGUIManager.menu_ActionTargetEdit_Inventory_Map.get(uuidString);
            player.openInventory(inventory);

        }

    }

    public Inventory getInventory(Player player, String typeName, String itemName, int tt, int actionOrder, String actionOrderType){
        this.typeName = typeName;
        this.itemName = itemName;
        this.tt = tt;
        this.actionOrder = actionOrder;
        this.actionOrderType = actionOrderType;

        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+typeName+".yml");
        Inventory inventory = Bukkit.createInventory(null, 54 , MenuSet.getGuiTitle("ActionTargetEdit"));

        inventory.setItem(4, CustomItem2.valueOf(player, null, typeName+"."+itemName, 1));
        //inventory.setItem(4, MenuItem.valueOf(itemMenuConfig, itemName));

        inventory.setItem(0, ButtomSet.getItemButtom("Buttom.ActionTargetEdit.ToEditItem", ""));
        inventory.setItem(8, ButtomSet.getItemButtom("Buttom.ActionTargetEdit.Exit", ""));

        inventory.setItem(49, ButtomSet.getItemButtom("Buttom.ActionTargetEdit.Application", ""));

        String[] targetTypeArray = {"@Self", "@Target", "@SelfRadius", "@TargetRadius"}; //, "@SelfInWorld", "@Server"

        inventory.setItem(10, ButtomSet.getItemButtom("Buttom.ActionTargetEdit.ActionTargetType", targetTypeArray[this.tt]));
        inventory.setItem(11, ButtomSet.getItemButtom("Buttom.ActionTargetEdit.Radius", String.valueOf(this.radius)));
        inventory.setItem(12, ButtomSet.getItemButtom("Buttom.ActionTargetEdit.Filters", filters));

        return inventory;
    }

}
