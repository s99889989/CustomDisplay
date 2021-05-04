package com.daxton.customdisplay.gui.item.edititem.editaction;

import com.daxton.customdisplay.api.action.ActionMapHandle;
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

import java.util.Map;

public class EditActionDetail {

    public String typeName;

    public String itemName;

    public String[] equipmentSlot = {"CHEST", "FEET", "HAND", "HEAD", "LEGS", "OFF_HAND", "ALL"};

    public String[] inherit = {"GENERIC_MAX_HEALTH", "GENERIC_KNOCKBACK_RESISTANCE", "GENERIC_MOVEMENT_SPEED", "GENERIC_ATTACK_DAMAGE", "GENERIC_ATTACK_SPEED", "GENERIC_ARMOR", "GENERIC_ARMOR_TOUGHNESS", "GENERIC_LUCK"};

    public String[] operation = {"ADD_NUMBER", "ADD_SCALAR", "MULTIPLY_SCALAR_1"};

    public int es;

    public int it;

    public int ot;

    public double attrAmount = 0;

    public String editType = "";

    public int actionOrder;

    public String actionOrderType = "";

    public EditActionDetail(){}


    //聊天監聽
    public static void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String uuidString = event.getPlayer().getUniqueId().toString();
        if(EditorGUIManager.menu_EditActionDetail_Map.get(uuidString) != null){
            event.setCancelled(true);

            String typeName = EditorGUIManager.menu_EditActionDetail_Map.get(uuidString).typeName;
            String itemName = EditorGUIManager.menu_EditActionDetail_Map.get(uuidString).itemName;

            int actionOrder = EditorGUIManager.menu_EditActionDetail_Map.get(uuidString).actionOrder;
            String actionOrderType = EditorGUIManager.menu_EditActionDetail_Map.get(uuidString).actionOrderType;

            String editType = EditorGUIManager.menu_EditActionDetail_Map.get(uuidString).editType;

            ItemActionSet itemActionSet = new ItemActionSet(player, typeName, itemName);

            String chatString = event.getMessage().replace("&", "§");
            switch (editType){
                case "ActionShow":
                    itemActionSet.setEditActionShow(new String[]{"actiontype"}, actionOrder, actionOrderType,chatString);
                    itemActionSet.clickEditActionEnd(actionOrder, actionOrderType);
                    break;
                case "Count":
                    itemActionSet.setEditActionShow(new String[]{"count","c"}, actionOrder, actionOrderType,chatString);
                    itemActionSet.clickEditActionEnd(actionOrder, actionOrderType);
                    break;
                case "CountPeriod":
                    itemActionSet.setEditActionShow(new String[]{"countperiod","cp"}, actionOrder, actionOrderType,chatString);
                    itemActionSet.clickEditActionEnd(actionOrder, actionOrderType);
                    break;
                case "Mark":
                    itemActionSet.setEditActionShow(new String[]{"mark","m"}, actionOrder, actionOrderType,chatString);
                    itemActionSet.clickEditActionEnd(actionOrder, actionOrderType);
                    break;
            }


        }
    }
    //按鈕監聽
    public static void onInventoryClick(InventoryClickEvent event){
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        String uuidString = player.getUniqueId().toString();
        if(EditorGUIManager.menu_EditActionDetail_Map.get(uuidString) != null){
            String[] equipmentSlot = {"CHEST", "FEET", "HAND", "HEAD", "LEGS", "OFF_HAND", "ALL"};

            String[] inherit = {"GENERIC_MAX_HEALTH", "GENERIC_KNOCKBACK_RESISTANCE", "GENERIC_MOVEMENT_SPEED", "GENERIC_ATTACK_DAMAGE", "GENERIC_ATTACK_SPEED", "GENERIC_ARMOR", "GENERIC_ARMOR_TOUGHNESS", "GENERIC_LUCK"};

            String[] operation = {"ADD_NUMBER", "ADD_SCALAR", "MULTIPLY_SCALAR_1"};
            String typeName = EditorGUIManager.menu_EditActionDetail_Map.get(uuidString).typeName;
            String itemName = EditorGUIManager.menu_EditActionDetail_Map.get(uuidString).itemName;
            int es = EditorGUIManager.menu_EditActionDetail_Map.get(uuidString).es;
            int it = EditorGUIManager.menu_EditActionDetail_Map.get(uuidString).it;
            int ot = EditorGUIManager.menu_EditActionDetail_Map.get(uuidString).ot;
            double amount = EditorGUIManager.menu_EditActionDetail_Map.get(uuidString).attrAmount;

            int actionOrder = EditorGUIManager.menu_EditActionDetail_Map.get(uuidString).actionOrder;
            String actionOrderType = EditorGUIManager.menu_EditActionDetail_Map.get(uuidString).actionOrderType;

            ItemSet itemSet = new ItemSet(player, typeName, itemName);

            int i = event.getRawSlot();

            ItemActionSet itemActionSet = new ItemActionSet(player, typeName, itemName);

            if(event.getClick() == ClickType.LEFT){
                //到物品類別
                if(i == 0){
                    OpenMenuGUI.EditAction(player, typeName, itemName);
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
                    String ms1 = MenuSet.getItemMenuMessage("EditActionDetail", "ActionShow", "Message");
                    String ms2 = MenuSet.getItemMenuMessage("EditActionDetail","ActionShow","SubMessage");
                    itemActionSet.clickEditAction(ms1, ms2, "ActionShow");
                    return;

                }
                if(i == 11){
                    //打開動作分類清單
                    OpenMenuGUI.EditActionTypeList(player, typeName, itemName, actionOrder, actionOrderType);
                    return;
                }
                if(i == 12){
                    String ms1 = MenuSet.getItemMenuMessage("EditActionDetail", "Count", "Message");
                    String ms2 = MenuSet.getItemMenuMessage("EditActionDetail","Count","SubMessage");
                    itemActionSet.clickEditAction(ms1, ms2, "Count");
                    return;
                }
                if(i == 13){
                    String ms1 = MenuSet.getItemMenuMessage("EditActionDetail", "CountPeriod", "Message");
                    String ms2 = MenuSet.getItemMenuMessage("EditActionDetail","CountPeriod","SubMessage");
                    itemActionSet.clickEditAction(ms1, ms2, "CountPeriod");
                    return;
                }
                if(i == 14){
                    String ms1 = MenuSet.getItemMenuMessage("EditActionDetail", "Mark", "Message");
                    String ms2 = MenuSet.getItemMenuMessage("EditActionDetail","Mark","SubMessage");
                    itemActionSet.clickEditAction(ms1, ms2, "Mark");
                    return;
                }
                if(i == 15){
                    itemActionSet.setEditActionShow(new String[]{"stop","s"}, actionOrder, actionOrderType,"true");
                    itemActionSet.clickEditActionEnd(actionOrder, actionOrderType);
                    return;
                }
                if(i == 16){
                    OpenMenuGUI.ActionTargetEdit(player, typeName, itemName, 0, actionOrder, actionOrderType);
                    return;
                }
                if(i == 19){
                    OpenMenuGUI.ActionTriggerList(player, typeName, itemName, 0, actionOrder, actionOrderType);
                    return;
                }

            }
            if(event.getClick() == ClickType.RIGHT){
                if(i == 10){
                    itemActionSet.setEditActionShow(new String[]{"actiontype"}, actionOrder, actionOrderType, "null");
                    OpenMenuGUI.EditActionDetail(player, typeName, itemName, actionOrder, actionOrderType);
                    return;
                }
                if(i == 11){
                    itemActionSet.setEditActionShow(new String[]{"action","a"},actionOrder, actionOrderType, "null");
                    OpenMenuGUI.EditActionDetail(player, typeName, itemName, actionOrder, actionOrderType);
                    return;
                }
                if(i == 12){
                    itemActionSet.setEditActionShow(new String[]{"count","c"}, actionOrder, actionOrderType,"1");
                    OpenMenuGUI.EditActionDetail(player, typeName, itemName, actionOrder, actionOrderType);
                    return;
                }
                if(i == 13){
                    itemActionSet.setEditActionShow(new String[]{"countperiod","cp"}, actionOrder, actionOrderType,"10");
                    OpenMenuGUI.EditActionDetail(player, typeName, itemName, actionOrder, actionOrderType);
                    return;
                }
                if(i == 14){
                    itemActionSet.setEditActionShow(new String[]{"mark","m"}, actionOrder, actionOrderType,"null");
                    OpenMenuGUI.EditActionDetail(player, typeName, itemName, actionOrder, actionOrderType);
                    return;
                }
                if(i == 15){
                    itemActionSet.setEditActionShow(new String[]{"stop","s"}, actionOrder, actionOrderType,"false");
                    itemActionSet.clickEditActionEnd(actionOrder, actionOrderType);
                    return;
                }
                if(i == 16){
                    itemActionSet.setEditActionShow(new String[]{"targetkey"}, actionOrder, actionOrderType,"null");
                    return;
                }
                if(i == 19){
                    itemActionSet.setEditActionShow(new String[]{"triggerkey"}, actionOrder, actionOrderType,"RightClickAir");
                    return;
                }

            }


        }
    }


    public void openMenu(Player player, String typeName, String itemName, int actionOrder, String actionOrderType){
        String uuidString = player.getUniqueId().toString();
        //player.sendMessage(actionOrder+" : "+actionOrderType);
        if(EditorGUIManager.menu_EditActionDetail_Inventory_Map.get(uuidString) == null){
            EditorGUIManager.menu_EditActionDetail_Inventory_Map.put(uuidString, getInventory(typeName, itemName, actionOrder, actionOrderType));
            Inventory inventory = EditorGUIManager.menu_EditActionDetail_Inventory_Map.get(uuidString);
            player.openInventory(inventory);

        }else {
            EditorGUIManager.menu_EditActionDetail_Inventory_Map.remove(uuidString);
            EditorGUIManager.menu_EditActionDetail_Inventory_Map.put(uuidString, getInventory(typeName, itemName, actionOrder, actionOrderType));
            Inventory inventory = EditorGUIManager.menu_EditActionDetail_Inventory_Map.get(uuidString);
            player.openInventory(inventory);

        }

    }

    public Inventory getInventory(String typeName, String itemName, int actionOrder, String actionOrderType){
        this.typeName = typeName;
        this.itemName = itemName;
        this.actionOrder = actionOrder;
        this.actionOrderType = actionOrderType;


        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+typeName+".yml");
        Inventory inventory = Bukkit.createInventory(null, 54 , MenuSet.getGuiTitle("EditActionDetail"));

        inventory.setItem(4, MenuItem.valueOf(itemMenuConfig, itemName));

        inventory.setItem(0,  ButtomSet.getItemButtom("Buttom.EditActionDetail.ToEditItem",""));
        inventory.setItem(8,  ButtomSet.getItemButtom("Buttom.EditActionDetail.Exit",""));

        ItemActionSet itemActionSet = new ItemActionSet(typeName, itemName);
        Map<String, String> action_map = itemActionSet.get_Action_Map(actionOrder);
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_map);


        inventory.setItem(10, ButtomSet.getItemButtom("Buttom.EditActionDetail.ActionShow", itemActionSet.get_Action_Show(actionOrder)));
        inventory.setItem(11, ButtomSet.getItemButtom("Buttom.EditActionDetail.Action", actionMapHandle.getString(new String[]{"Action","a"}, "null")));
        inventory.setItem(12, ButtomSet.getItemButtom("Buttom.EditActionDetail.Count", actionMapHandle.getString(new String[]{"Count","c"}, "1")));
        inventory.setItem(13, ButtomSet.getItemButtom("Buttom.EditActionDetail.CountPeriod", actionMapHandle.getString(new String[]{"CountPeriod","cp"}, "10")));
        inventory.setItem(14, ButtomSet.getItemButtom("Buttom.EditActionDetail.Mark", actionMapHandle.getString(new String[]{"Mark","m"}, "null")));
        inventory.setItem(15, ButtomSet.getItemButtom("Buttom.EditActionDetail.Stop", actionMapHandle.getString(new String[]{"stop","s"}, "false")));
        inventory.setItem(16, ButtomSet.getItemButtom("Buttom.EditActionDetail.TargetKey", actionMapHandle.getString(new String[]{"targetkey"}, "null")));
        inventory.setItem(19, ButtomSet.getItemButtom("Buttom.EditActionDetail.TriggerKey", actionMapHandle.getString(new String[]{"triggerkey"}, "null")));


        //inventory.setItem(49, ButtomSet.getItemButtom("Buttom.EditActionDetail.Application",""));


        return inventory;
    }

}
