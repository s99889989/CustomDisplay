package com.daxton.customdisplay.api.item.gui;

import com.daxton.customdisplay.manager.player.EditorGUIManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.entity.Player;

public class OpenMenuGUI {

    private Player player;

    public OpenMenuGUI(){

    }

    public OpenMenuGUI(Player player){
        this.player = player;
    }

    public void ItemCategorySelection(){
        if(this.player != null){
            String uuidString = this.player.getUniqueId().toString();
            if(EditorGUIManager.menu_ItemCategorySelection_Map.get(uuidString) == null){
                EditorGUIManager.menu_ItemCategorySelection_Map.put(uuidString, new ItemCategorySelection());
            }
            if(EditorGUIManager.menu_ItemCategorySelection_Map.get(uuidString) != null){
                EditorGUIManager.menu_ItemCategorySelection_Map.get(uuidString).openMenu(player);
            }

        }
    }

    public void SelectItems(String typeName, int page){
        if(this.player != null){
            String uuidString = this.player.getUniqueId().toString();
            if(EditorGUIManager.menu_SelectItems_Map.get(uuidString) == null){
                EditorGUIManager.menu_SelectItems_Map.put(uuidString, new SelectItems());
            }
            if(EditorGUIManager.menu_SelectItems_Map.get(uuidString) != null){
                EditorGUIManager.menu_SelectItems_Map.get(uuidString).openMenu(player, typeName,page);
            }
        }
    }

    public void EditItem(String typeName, String itemID){
        if(this.player != null){
            String uuidString = this.player.getUniqueId().toString();
            if(EditorGUIManager.menu_EditItem_Map.get(uuidString) == null){
                EditorGUIManager.menu_EditItem_Map.put(uuidString, new EditItem());
            }
            if(EditorGUIManager.menu_EditItem_Map.get(uuidString) != null){
                EditorGUIManager.menu_EditItem_Map.get(uuidString).openMenu(player, typeName, itemID);
            }
        }
    }

    public void EditFlags(String typeName, String itemID){
        if(this.player != null){
            String uuidString = this.player.getUniqueId().toString();
            if(EditorGUIManager.menu_EditFlags_Map.get(uuidString) == null){
                EditorGUIManager.menu_EditFlags_Map.put(uuidString, new EditFlags());
            }
            if(EditorGUIManager.menu_EditFlags_Map.get(uuidString) != null){
                EditorGUIManager.menu_EditFlags_Map.get(uuidString).openMenu(player, typeName, itemID);
            }
        }
    }

    public void EditLore(String typeName, String itemID){
        if(this.player != null){
            String uuidString = this.player.getUniqueId().toString();
            if(EditorGUIManager.menu_EditLore_Map.get(uuidString) == null){
                EditorGUIManager.menu_EditLore_Map.put(uuidString, new EditLore());
            }
            if(EditorGUIManager.menu_EditLore_Map.get(uuidString) != null){
                EditorGUIManager.menu_EditLore_Map.get(uuidString).openMenu(player, typeName, itemID);
            }
        }
    }

    public void EditEnchantment(String typeName, String itemID, int page){
        if(this.player != null){
            String uuidString = this.player.getUniqueId().toString();
            if(EditorGUIManager.menu_EditEnchantment_Map.get(uuidString) == null){
                EditorGUIManager.menu_EditEnchantment_Map.put(uuidString, new EditEnchantment());
            }
            if(EditorGUIManager.menu_EditEnchantment_Map.get(uuidString) != null){
                EditorGUIManager.menu_EditEnchantment_Map.get(uuidString).openMenu(player, typeName, itemID, page);
            }
        }
    }

    public void EditAttributes(String typeName, String itemID, int es, int it, int ot){
        if(this.player != null){
            String uuidString = this.player.getUniqueId().toString();
            if(EditorGUIManager.menu_EditAttributes_Map.get(uuidString) == null){
                EditorGUIManager.menu_EditAttributes_Map.put(uuidString, new EditAttributes());
            }
            if(EditorGUIManager.menu_EditAttributes_Map.get(uuidString) != null){
                EditorGUIManager.menu_EditAttributes_Map.get(uuidString).openMenu(player, typeName, itemID, es, it, ot);
            }
        }
    }

    public void ItemList(String typeName, String itemID, int page){
        if(this.player != null){
            String uuidString = this.player.getUniqueId().toString();
            if(EditorGUIManager.menu_ItemList_Map.get(uuidString) == null){
                EditorGUIManager.menu_ItemList_Map.put(uuidString, new ItemList());
            }
            if(EditorGUIManager.menu_ItemList_Map.get(uuidString) != null){
                EditorGUIManager.menu_ItemList_Map.get(uuidString).openMenu(player, typeName, itemID, page);
            }
        }
    }

}
