package com.daxton.customdisplay.gui.item.edititem.editaction;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.action.SetActionMap;
import com.daxton.customdisplay.api.item.CustomItem2;
import com.daxton.customdisplay.api.item.MenuItem;
import com.daxton.customdisplay.gui.item.OpenMenuGUI;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.EditorGUIManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemActionSet {

    private FileConfiguration itemConfig;

    private String typeName;

    private String itemID;

    private Player player;

    public ItemActionSet(){

    }

    public ItemActionSet(Player player, String typeName, String itemID){
        this.player = player;
        this.typeName = typeName;
        this.itemID = itemID;
        this.itemConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+typeName+".yml");
    }

    public ItemActionSet(String typeName, String itemID){
        this.typeName = typeName;
        this.itemID = itemID;
        this.itemConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+typeName+".yml");
    }

////////////////////////////////////////
    //設定動作顯示在Lore上的名稱
    public void setEditActionShow(String[] keyArray, int order, String orderType, String setValue){
        Map<String, String> action_Map = get_Action_Map(order);
        for(String key : keyArray){
            if(action_Map.get(key) != null){
                action_Map.remove(key);
            }
        }
        action_Map.put(keyArray[0],setValue);
        setALL(action_Map, order, orderType);
    }
//////////////////////////////////////

    public void setDefaultAction(int order){
        Map<String, String> action_Map = new HashMap<>();


        setALL(action_Map, order, "Default");
    }

    //獲取指定行數動作顯示名稱
    public String get_Action_Show(int order){
        String output = "null";
        List<String> itemLore = this.itemConfig.getStringList(this.itemID+".Action");
        if(itemLore.size() > order){
            String[] strings = itemLore.get(order).split(":");
            if(strings.length == 2){
                output = strings[0];
            }
        }
        return output;
    }

    //獲取指定行數ActionMap
    public Map<String, String> get_Action_Map(int order){
        List<String> itemLore = this.itemConfig.getStringList(this.itemID+".Action");
        List<Map<String, String>> player_Item_Action_List_Map = new ArrayList<>();
        itemLore.forEach(s -> {

            player_Item_Action_List_Map.add(SetActionMap.setClassAction(s));
        });
        Map<String, String> action_Map = new HashMap<>();
        if(player_Item_Action_List_Map.size() > order){
            action_Map = player_Item_Action_List_Map.get(order);
        }
        return action_Map;
    }

    //
    public void setALL(Map<String, String> player_Item_Action_Map, int order, String orderType){

        String allString = "";

        ActionMapHandle actionMapHandle = new ActionMapHandle(player_Item_Action_Map);

        String actionShowName = actionMapHandle.getString(new String[]{"actiontype"}, "null");
        allString += actionShowName.replace("&","§").replace(":Action","")+":Action[";

        String useAction = actionMapHandle.getString(new String[]{"Action","a"}, "null");
        allString += "Action="+useAction+";";

        String count = actionMapHandle.getString(new String[]{"Count","c"}, "1");
        allString += "Count="+count+";";

        String countCp = actionMapHandle.getString(new String[]{"CountPeriod","cp"}, "10");
        allString += "CountPeriod="+countCp+";";

        String mark = actionMapHandle.getString(new String[]{"Mark","m"}, "null");
        if(!mark.equals("null")){
            allString += "Mark="+mark+";";
        }

        String stop = actionMapHandle.getString(new String[]{"stop","s"}, "false");
        allString += "Stop="+stop+"]";

        String targetkey = actionMapHandle.getString(new String[]{"targetkey"}, "null");
        if(!targetkey.equals("null")){
            allString += " @"+targetkey.replace("@","")+" ";
        }

        String triggerkey = actionMapHandle.getString(new String[]{"triggerkey"}, "null");
        if(!triggerkey.equals("null")){
            allString += " ~"+triggerkey.replace("~","")+" ";
        }else {
            allString += " ~RightClickAir"+" ";
        }

        if(orderType.equals("Default")){
            addOrderAction(allString, order);
        }else {
            editAction(allString, order);
        }

    }

    //在指定行數增加Action
    public void addOrderAction(String setValue, int order){
        List<String> lore = this.itemConfig.getStringList(this.itemID+".Action");
        if(lore.size() >= order){
            //
            lore.add(order, setValue);
            this.itemConfig.set(this.itemID+".Action", lore);
        }
    }

    //編輯指定行數Action
    public void editAction(String setValue, int order){
        List<String> lore = this.itemConfig.getStringList(this.itemID+".Action");
        if(lore.size() >= order){
            lore.remove(order);
            lore.add(order, setValue);
            this.itemConfig.set(this.itemID+".Action", lore);
        }
    }

    //移除指定位置Action
    public void removeOrderAction(int order){
        List<String> lore = this.itemConfig.getStringList(this.itemID+".Action");
        if(lore.size() > 0){
            lore.remove(order);
            this.itemConfig.set(this.itemID+".Action", lore);
        }
    }

    //點擊功能
    public void clickEditAction(String message, String subMessage, String editType){
        String uuidString = this.player.getUniqueId().toString();

        this.player.closeInventory();
        sendTitle(message, subMessage);
        EditorGUIManager.menu_EditActionDetail_Map.get(uuidString).editType = editType;
        EditorGUIManager.menu_EditActionDetail_Chat_Map.put(uuidString, true);
    }
    //點擊功能
    public void clickEditTarget(String message, String subMessage, String editType){
        String uuidString = this.player.getUniqueId().toString();

        this.player.closeInventory();
        sendTitle(message, subMessage);
        EditorGUIManager.menu_ActionTargetEdit_Map.get(uuidString).editType = editType;
        EditorGUIManager.menu_ActionTargetEdit_Chat_Map.put(uuidString, true);
    }

    public void clickEditTargetEnd(int tt, int order, String orderType){
        String uuidString = this.player.getUniqueId().toString();
        EditorGUIManager.menu_ActionTargetEdit_Chat_Map.put(uuidString, false);

        OpenMenuGUI.ActionTargetEdit(this.player, this.typeName, this.itemID, tt, order, orderType);
    }

    public void clickEditActionEnd(int order, String orderType){
        String uuidString = this.player.getUniqueId().toString();
        EditorGUIManager.menu_EditActionDetail_Chat_Map.put(uuidString, false);

        OpenMenuGUI.EditActionDetail(this.player, this.typeName, this.itemID, order, orderType);
    }

    //發送訊息
    public void sendTitle(String title, String subtitle){
        this.player.sendTitle(title,subtitle,10,40,40);
    }

    //給物品
    public void giveItem(){

        //ItemStack itemStack = MenuItem.valueOf(this.itemConfig, this.itemID);
        ItemStack itemStack = CustomItem2.valueOf(this.player, null, this.typeName+"."+this.itemID, 1);
        this.player.getInventory().addItem(itemStack);
    }

    //關閉
    public void closeEditMenu(){
        this.player.closeInventory();
    }

}
