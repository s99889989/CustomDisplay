package com.daxton.customdisplay.api.item.gui;

import com.daxton.customdisplay.manager.PlayerManager;
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
            if(PlayerManager.menu_ItemCategorySelection_Map.get(uuidString) == null){
                PlayerManager.menu_ItemCategorySelection_Map.put(uuidString, new ItemCategorySelection());
            }
            if(PlayerManager.menu_ItemCategorySelection_Map.get(uuidString) != null){
                PlayerManager.menu_ItemCategorySelection_Map.get(uuidString).openMenu(player);
            }

        }
    }

    public void SelectItems(String typeName, int page){
        if(this.player != null){
            String uuidString = this.player.getUniqueId().toString();
            if(PlayerManager.menu_SelectItems_Map.get(uuidString) == null){
                PlayerManager.menu_SelectItems_Map.put(uuidString, new SelectItems());
            }
            if(PlayerManager.menu_SelectItems_Map.get(uuidString) != null){
                PlayerManager.menu_SelectItems_Map.get(uuidString).openMenu(player, typeName,page);
            }
        }
    }

    public void EditItem(String typeName, String itemID){
        if(this.player != null){
            String uuidString = this.player.getUniqueId().toString();
            if(PlayerManager.menu_EditItem_Map.get(uuidString) == null){
                PlayerManager.menu_EditItem_Map.put(uuidString, new EditItem());
            }
            if(PlayerManager.menu_EditItem_Map.get(uuidString) != null){
                PlayerManager.menu_EditItem_Map.get(uuidString).openMenu(player, typeName, itemID);
            }
        }
    }

    public void EditFlags(String typeName, String itemID){
        if(this.player != null){
            String uuidString = this.player.getUniqueId().toString();
            if(PlayerManager.menu_EditFlags_Map.get(uuidString) == null){
                PlayerManager.menu_EditFlags_Map.put(uuidString, new EditFlags());
            }
            if(PlayerManager.menu_EditFlags_Map.get(uuidString) != null){
                PlayerManager.menu_EditFlags_Map.get(uuidString).openMenu(player, typeName, itemID);
            }
        }
    }

    public void EditLore(String typeName, String itemID){
        if(this.player != null){
            String uuidString = this.player.getUniqueId().toString();
            if(PlayerManager.menu_EditLore_Map.get(uuidString) == null){
                PlayerManager.menu_EditLore_Map.put(uuidString, new EditLore());
            }
            if(PlayerManager.menu_EditLore_Map.get(uuidString) != null){
                PlayerManager.menu_EditLore_Map.get(uuidString).openMenu(player, typeName, itemID);
            }
        }
    }

    public void EditEnchantment(String typeName, String itemID, int page){
        if(this.player != null){
            String uuidString = this.player.getUniqueId().toString();
            if(PlayerManager.menu_EditEnchantment_Map.get(uuidString) == null){
                PlayerManager.menu_EditEnchantment_Map.put(uuidString, new EditEnchantment());
            }
            if(PlayerManager.menu_EditEnchantment_Map.get(uuidString) != null){
                PlayerManager.menu_EditEnchantment_Map.get(uuidString).openMenu(player, typeName, itemID, page);
            }
        }
    }

    public void EditAttributes(String typeName, String itemID, int es, int it, int ot){
        if(this.player != null){
            String uuidString = this.player.getUniqueId().toString();
            if(PlayerManager.menu_EditAttributes_Map.get(uuidString) == null){
                PlayerManager.menu_EditAttributes_Map.put(uuidString, new EditAttributes());
            }
            if(PlayerManager.menu_EditAttributes_Map.get(uuidString) != null){
                PlayerManager.menu_EditAttributes_Map.get(uuidString).openMenu(player, typeName, itemID, es, it, ot);
            }
        }
    }

    public void ItemList(String typeName, String itemID, int page){
        if(this.player != null){
            String uuidString = this.player.getUniqueId().toString();
            if(PlayerManager.menu_ItemList_Map.get(uuidString) == null){
                PlayerManager.menu_ItemList_Map.put(uuidString, new ItemList());
            }
            if(PlayerManager.menu_ItemList_Map.get(uuidString) != null){
                PlayerManager.menu_ItemList_Map.get(uuidString).openMenu(player, typeName, itemID, page);
            }
        }
    }

}
