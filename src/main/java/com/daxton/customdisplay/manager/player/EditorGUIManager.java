package com.daxton.customdisplay.manager.player;

import com.daxton.customdisplay.api.item.gui.*;
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
    //------------------------------------------------------------------------------------------------------

}
