package com.daxton.customdisplay.gui.item;

import com.daxton.customdisplay.gui.item.edititem.*;
import com.daxton.customdisplay.gui.item.edititem.editaction.*;
import com.daxton.customdisplay.manager.player.EditorGUIManager;
import org.bukkit.entity.Player;

public class OpenMenuGUI {

    public OpenMenuGUI(){

    }

    public static void ItemCategorySelection(Player player){
        if(player != null){
            String uuidString = player.getUniqueId().toString();
            if(EditorGUIManager.menu_ItemCategorySelection_Map.get(uuidString) == null){
                EditorGUIManager.menu_ItemCategorySelection_Map.put(uuidString, new ItemCategorySelection());
            }
            if(EditorGUIManager.menu_ItemCategorySelection_Map.get(uuidString) != null){
                EditorGUIManager.menu_ItemCategorySelection_Map.get(uuidString).openMenu(player);
            }

        }
    }

    public static void SelectItems(Player player, String typeName, int page){
        if(player != null){
            String uuidString = player.getUniqueId().toString();
            if(EditorGUIManager.menu_SelectItems_Map.get(uuidString) == null){
                EditorGUIManager.menu_SelectItems_Map.put(uuidString, new SelectItems());
            }
            if(EditorGUIManager.menu_SelectItems_Map.get(uuidString) != null){
                EditorGUIManager.menu_SelectItems_Map.get(uuidString).openMenu(player, typeName,page);
            }
        }
    }

    public static void EditItem(Player player, String typeName, String itemID){
        if(player != null){
            String uuidString = player.getUniqueId().toString();
            if(EditorGUIManager.menu_EditItem_Map.get(uuidString) == null){
                EditorGUIManager.menu_EditItem_Map.put(uuidString, new EditItem());
            }
            if(EditorGUIManager.menu_EditItem_Map.get(uuidString) != null){
                EditorGUIManager.menu_EditItem_Map.get(uuidString).openMenu(player, typeName, itemID);
            }
        }
    }

    public static void EditFlags(Player player, String typeName, String itemID){
        if(player != null){
            String uuidString = player.getUniqueId().toString();
            if(EditorGUIManager.menu_EditFlags_Map.get(uuidString) == null){
                EditorGUIManager.menu_EditFlags_Map.put(uuidString, new EditFlags());
            }
            if(EditorGUIManager.menu_EditFlags_Map.get(uuidString) != null){
                EditorGUIManager.menu_EditFlags_Map.get(uuidString).openMenu(player, typeName, itemID);
            }
        }
    }

    public static void EditLore(Player player, String typeName, String itemID){
        if(player != null){
            String uuidString = player.getUniqueId().toString();
            if(EditorGUIManager.menu_EditLore_Map.get(uuidString) == null){
                EditorGUIManager.menu_EditLore_Map.put(uuidString, new EditLore());
            }
            if(EditorGUIManager.menu_EditLore_Map.get(uuidString) != null){
                EditorGUIManager.menu_EditLore_Map.get(uuidString).openMenu(player, typeName, itemID);
            }
        }
    }

    public static void EditEnchantment(Player player, String typeName, String itemID, int page){
        if(player != null){
            String uuidString = player.getUniqueId().toString();
            if(EditorGUIManager.menu_EditEnchantment_Map.get(uuidString) == null){
                EditorGUIManager.menu_EditEnchantment_Map.put(uuidString, new EditEnchantment());
            }
            if(EditorGUIManager.menu_EditEnchantment_Map.get(uuidString) != null){
                EditorGUIManager.menu_EditEnchantment_Map.get(uuidString).openMenu(player, typeName, itemID, page);
            }
        }
    }

    public static void EditAttributes(Player player, String typeName, String itemID, int es, int it, int ot){
        if(player != null){
            String uuidString = player.getUniqueId().toString();
            if(EditorGUIManager.menu_EditAttributes_Map.get(uuidString) == null){
                EditorGUIManager.menu_EditAttributes_Map.put(uuidString, new EditAttributes());
            }
            if(EditorGUIManager.menu_EditAttributes_Map.get(uuidString) != null){
                EditorGUIManager.menu_EditAttributes_Map.get(uuidString).openMenu(player, typeName, itemID, es, it, ot);
            }
        }
    }

    public static void ItemList(Player player, String typeName, String itemID, int page){
        if(player != null){
            String uuidString = player.getUniqueId().toString();
            if(EditorGUIManager.menu_ItemList_Map.get(uuidString) == null){
                EditorGUIManager.menu_ItemList_Map.put(uuidString, new ItemList());
            }
            if(EditorGUIManager.menu_ItemList_Map.get(uuidString) != null){
                EditorGUIManager.menu_ItemList_Map.get(uuidString).openMenu(player, typeName, itemID, page);
            }
        }
    }

    //動作編輯行數
    public static void EditAction(Player player, String typeName, String itemID){
        if(player != null){
            String uuidString = player.getUniqueId().toString();
            if(EditorGUIManager.menu_EditAction_Map.get(uuidString) == null){
                EditorGUIManager.menu_EditAction_Map.put(uuidString, new EditAction());
            }
            if(EditorGUIManager.menu_EditAction_Map.get(uuidString) != null){
                EditorGUIManager.menu_EditAction_Map.get(uuidString).openMenu(player, typeName, itemID);
            }
        }
    }

    //動作細節設定
    public static void EditActionDetail(Player player, String typeName, String itemID, int actionOrder, String editType){
        if(player != null){
            String uuidString = player.getUniqueId().toString();
            if(EditorGUIManager.menu_EditActionDetail_Map.get(uuidString) == null){
                EditorGUIManager.menu_EditActionDetail_Map.put(uuidString, new EditActionDetail());
            }
            if(EditorGUIManager.menu_EditActionDetail_Map.get(uuidString) != null){
                EditorGUIManager.menu_EditActionDetail_Map.get(uuidString).openMenu(player, typeName, itemID, actionOrder, editType);
            }
        }
    }

    //動作文件清單
    public static void EditActionTypeList(Player player, String typeName, String itemID, int actionOrder, String editType){
        if(player != null){
            String uuidString = player.getUniqueId().toString();
            if(EditorGUIManager.menu_ActionTypeList_Map.get(uuidString) == null){
                EditorGUIManager.menu_ActionTypeList_Map.put(uuidString, new ActionTypeList());
            }
            if(EditorGUIManager.menu_ActionTypeList_Map.get(uuidString) != null){
                EditorGUIManager.menu_ActionTypeList_Map.get(uuidString).openMenu(player, typeName, itemID, actionOrder ,editType);
            }
        }
    }

    //動作文件清單
    public static void EditActionList(Player player, String typeName, String itemID, String actionType, int itemCount, int actionOrder, String editType){
        if(player != null){
            String uuidString = player.getUniqueId().toString();
            if(EditorGUIManager.menu_ActionList_Map.get(uuidString) == null){
                EditorGUIManager.menu_ActionList_Map.put(uuidString, new ActionList());
            }
            if(EditorGUIManager.menu_ActionList_Map.get(uuidString) != null){
                EditorGUIManager.menu_ActionList_Map.get(uuidString).openMenu(player, typeName, itemID, actionType, itemCount, actionOrder ,editType);
            }
        }
    }

    //動作文件清單
    public static void ActionTriggerList(Player player, String typeName, String itemID, int itemCount, int actionOrder, String editType){
        if(player != null){
            String uuidString = player.getUniqueId().toString();
            if(EditorGUIManager.menu_ActionTriggerList_Map.get(uuidString) == null){
                EditorGUIManager.menu_ActionTriggerList_Map.put(uuidString, new ActionTriggerList());
            }
            if(EditorGUIManager.menu_ActionTriggerList_Map.get(uuidString) != null){
                EditorGUIManager.menu_ActionTriggerList_Map.get(uuidString).openMenu(player, typeName, itemID, itemCount, actionOrder ,editType);
            }
        }
    }
    //動作目標清單
    public static void ActionTargetEdit(Player player, String typeName, String itemID, int tt, int actionOrder, String actionOrderType){
        if(player != null){
            String uuidString = player.getUniqueId().toString();
            if(EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString) == null){
                EditorGUIManager.menu_ActionTargetEdit_Map.put(uuidString, new ActionTargetEdit());
            }
            if(EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString) != null){
                EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).openMenu(player, typeName, itemID, tt, actionOrder, actionOrderType);
            }
        }
    }
    //動作目標清單
    public static void ActionFiltersList(Player player, String typeName, String itemID, int itemCount, int actionOrder, String actionOrderType){
        if(player != null){
            String uuidString = player.getUniqueId().toString();
            if(EditorGUIManager.menu_ActionFiltersList_Map.get(uuidString) == null){
                EditorGUIManager.menu_ActionFiltersList_Map.put(uuidString, new ActionFiltersList());
            }
            if(EditorGUIManager.menu_ActionFiltersList_Map.get(uuidString) != null){
                EditorGUIManager.menu_ActionFiltersList_Map.get(uuidString).openMenu(player, typeName, itemID, itemCount, actionOrder, actionOrderType);
            }
        }
    }

}
