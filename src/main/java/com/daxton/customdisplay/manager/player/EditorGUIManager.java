package com.daxton.customdisplay.manager.player;

import com.daxton.customdisplay.gui.item.*;
import com.daxton.customdisplay.gui.item.edititem.*;
import com.daxton.customdisplay.gui.item.edititem.editaction.*;
import com.daxton.customdisplay.task.action2.player.OpenInventory3;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class EditorGUIManager {

    public static Map<String , Inventory> open_Inventory_Map = new HashMap<>();
    public static Map<String , OpenInventory3> menu_OpenInventory_Map = new HashMap<>();

    //------------------------------------------------------------------------------------------------------
    //物品主Menu
    public static Map<String , Inventory> menu_ItemCategorySelection_Inventory_Map = new HashMap<>();
    public static Map<String , ItemCategorySelection> menu_ItemCategorySelection_Map = new HashMap<>();
    //物品分列表
    public static Map<String , Inventory> menu_SelectItems_Inventory_Map = new HashMap<>();
    public static Map<String , SelectItems> menu_SelectItems_Map = new HashMap<>();
    public static Map<String , Boolean> menu_SelectItems_Chat_Map = new HashMap<>();
    //編輯物品
    public static Map<String , Inventory> menu_EditItem_Inventory_Map = new HashMap<>();
    public static Map<String , EditItem> menu_EditItem_Map = new HashMap<>();
    public static Map<String , Boolean> menu_EditItem_Chat_Map = new HashMap<>();
    //編輯物品附魔
    public static Map<String , Inventory> menu_EditEnchantment_Inventory_Map = new HashMap<>();
    public static Map<String , EditEnchantment> menu_EditEnchantment_Map = new HashMap<>();
    public static Map<String , Boolean> menu_EditEnchantment_Chat_Map = new HashMap<>();
    //編輯物品屬性
    public static Map<String , Inventory> menu_EditAttributes_Inventory_Map = new HashMap<>();
    public static Map<String , EditAttributes> menu_EditAttributes_Map = new HashMap<>();
    public static Map<String , Boolean> menu_EditAttributes_Chat_Map = new HashMap<>();
    //物品材質清單
    public static Map<String , Inventory> menu_ItemList_Inventory_Map = new HashMap<>();
    public static Map<String , ItemList> menu_ItemList_Map = new HashMap<>();
    //編輯物品Flag
    public static Map<String , Inventory> menu_EditFlags_Inventory_Map = new HashMap<>();
    public static Map<String , EditFlags> menu_EditFlags_Map = new HashMap<>();
    //編輯物品Lore
    public static Map<String , Inventory> menu_EditLore_Inventory_Map = new HashMap<>();
    public static Map<String , EditLore> menu_EditLore_Map = new HashMap<>();
    public static Map<String , Boolean> menu_EditLore_Chat_Map = new HashMap<>();
    //編輯物品Action
    public static Map<String , Inventory> menu_EditAction_Inventory_Map = new HashMap<>();
    public static Map<String , EditAction> menu_EditAction_Map = new HashMap<>();
    public static Map<String , Boolean> menu_EditAction_Chat_Map = new HashMap<>();
    //編輯物品Action細節
    public static Map<String , Inventory> menu_EditActionDetail_Inventory_Map = new HashMap<>();
    public static Map<String , EditActionDetail> menu_EditActionDetail_Map = new HashMap<>();
    public static Map<String , Boolean> menu_EditActionDetail_Chat_Map = new HashMap<>();
    //編輯物品Action類型清單
    public static Map<String , Inventory> menu_ActionTypeList_Inventory_Map = new HashMap<>();
    public static Map<String , ActionTypeList> menu_ActionTypeList_Map = new HashMap<>();
    //編輯物品Action清單
    public static Map<String , Inventory> menu_ActionList_Inventory_Map = new HashMap<>();
    public static Map<String , ActionList> menu_ActionList_Map = new HashMap<>();
    //編輯物品Action觸發清單
    public static Map<String , Inventory> menu_ActionTriggerList_Inventory_Map = new HashMap<>();
    public static Map<String , ActionTriggerList> menu_ActionTriggerList_Map = new HashMap<>();
    //編輯物品Action目標
    public static Map<String , Inventory> menu_ActionTargetEdit_Inventory_Map = new HashMap<>();
    public static Map<String , ActionTargetEdit> menu_ActionTargetEdit_Map = new HashMap<>();
    public static Map<String , Boolean> menu_ActionTargetEdit_Chat_Map = new HashMap<>();
    //編輯物品Action篩選清單
    public static Map<String , Inventory> menu_ActionFiltersList_Inventory_Map = new HashMap<>();
    public static Map<String , ActionFiltersList> menu_ActionFiltersList_Map = new HashMap<>();

    //------------------------------------------------------------------------------------------------------

}
