package com.daxton.customdisplay.api.item;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.SetActionMap;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class PlayerEquipment2 {

    public PlayerEquipment2(){

    }

    public static void reloadEquipment(Player player, int key){

        loadAllEq2(player, key);
        PlayerTrigger.onPlayer(player, null, "~eqmcheck");

    }

    public static void loadAllEq2(Player player, int key){


        String uuidString = player.getUniqueId().toString();
        PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);
        if(playerData != null){
            //還原裝備自訂值
            playerData.setDfaultEqm();

            Map<String, Integer> equipment_Enchants_Map = playerData.equipment_Enchants_Map;

            List<Map<String, String>> player_Item_Action_List_Map  = playerData.player_Item_Action_List_Map;

            player_Item_Action_List_Map.clear();

            itemList(player);

            if(!(equipment_Enchants_Map.isEmpty())){
                equipment_Enchants_Map.clear();
            }

            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_equipment_type_mainhand>") != null){
                PlaceholderManager.getCd_Placeholder_Map().remove(uuidString+"<cd_player_equipment_type_mainhand>");
            }


            if(player.getInventory().getArmorContents().length > 0){
                for(ItemStack itemStack : player.getInventory().getArmorContents()){
                    if(itemStack != null){
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        if(itemMeta != null){
                            //找出裝備上的附魔屬性
                            itemMeta.getEnchants().forEach((enchantment, integer) -> printE(equipment_Enchants_Map, enchantment.getKey().getKey(), integer));
                            //設定物品動作
                            setItemActionList(player_Item_Action_List_Map, itemStack);
                            //找出物品Lore轉成屬性
                            setCustomAttrs(playerData, itemMeta);
                        }

                    }
                }
            }
            if(key > 8){
                if(player.getInventory().getItemInMainHand().getType() != Material.AIR){
                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_player_equipment_type_mainhand>",itemStack.getType().toString());
                    if(itemMeta != null){
                        //找出主手上的附魔屬性
                        itemMeta.getEnchants().forEach((enchantment, integer) -> printE(equipment_Enchants_Map, enchantment.getKey().getKey(), integer));
                        //設定物品動作
                        setItemActionList(player_Item_Action_List_Map, itemStack);
                        //找出物品Lore轉成屬性
                        setCustomAttrs(playerData, itemMeta);
                    }

                }
            }else {
                if(player.getInventory().getItem(key) != null){
                    ItemStack itemStack = player.getInventory().getItem(key);
                    if(itemStack != null){
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_player_equipment_type_mainhand>",itemStack.getType().toString());
                        if(itemMeta != null){
                            //找出主手上的附魔屬性
                            itemMeta.getEnchants().forEach((enchantment, integer) -> printE(equipment_Enchants_Map, enchantment.getKey().getKey(), integer));
                            //設定物品動作
                            setItemActionList(player_Item_Action_List_Map, itemStack);
                            //找出物品Lore轉成屬性
                            setCustomAttrs(playerData, itemMeta);
                        }

                    }
                }
            }

            if(player.getInventory().getItemInOffHand().getType() != Material.AIR){
                ItemStack itemStack = player.getInventory().getItemInOffHand();

                ItemMeta itemMeta = itemStack.getItemMeta();
                if(itemMeta != null){  //&& !(itemMeta.getDisplayName().isEmpty())
                    //找出副手上的附魔屬性
                    itemMeta.getEnchants().forEach((enchantment, integer) -> printE(equipment_Enchants_Map, enchantment.getKey().getKey(), integer));
                    //設定物品動作
                    setItemActionList(player_Item_Action_List_Map, itemStack);
                    //找出物品Lore轉成屬性
                    setCustomAttrs(playerData, itemMeta);
                }


            }
        }


    }

    //設定物品動作
    public static void setItemActionList(List<Map<String, String>> player_Item_Action_List_Map, ItemStack itemStack){
        if(itemStack != null){
            try {
                if(!itemStack.getItemMeta().getPersistentDataContainer().isEmpty()){
                    Set<NamespacedKey> k = itemStack.getItemMeta().getPersistentDataContainer().getKeys();
                    k.forEach(namespacedKey -> {
                        String ss = itemStack.getItemMeta().getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING);
                        if(ss != null){
                            player_Item_Action_List_Map.add(SetActionMap.setClassAction(ss));
                        }
                    });
                }
            }catch (NoSuchMethodError exception){
                //
            }
        }
    }
    //玩家身上物品量統計
    public static void itemList(Player player){
        PlayerData2 playerData = PlayerManager.player_Data_Map.get(player.getUniqueId().toString());
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        playerData.inventory_Item_Amount.clear();
        for(ItemStack itemStack : player.getInventory().getContents()){
            if(itemStack != null){
                String itemID = itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(cd, "itemID"), PersistentDataType.STRING);
                if(itemID != null){
                    if(playerData.inventory_Item_Amount.containsKey(itemID)){
                        int nowAmount = playerData.inventory_Item_Amount.get(itemID);
                        int addAmount = itemStack.getAmount();
                        playerData.inventory_Item_Amount.put(itemID, nowAmount+addAmount);
                    }else {
                        playerData.inventory_Item_Amount.put(itemID, itemStack.getAmount());
                    }

                    //player.sendMessage(itemID+" : "+itemStack.getAmount());

                }
            }
        }
        for(ItemStack itemStack : player.getInventory().getArmorContents()){
            if(itemStack != null){
                String itemID = itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(cd, "itemID"), PersistentDataType.STRING);
                if(itemID != null){
                    if(playerData.inventory_Item_Amount.containsKey(itemID)){
                        int nowAmount = playerData.inventory_Item_Amount.get(itemID);
                        int addAmount = itemStack.getAmount();
                        playerData.inventory_Item_Amount.put(itemID, nowAmount+addAmount);
                    }else {
                        playerData.inventory_Item_Amount.put(itemID, itemStack.getAmount());
                    }
                }
            }
        }

    }

    //找出物品附魔
    public static void printE(Map<String, Integer> equipment_Enchants_Map, String enchant, int eValue){
        if(equipment_Enchants_Map.get(enchant) == null){
            equipment_Enchants_Map.put(enchant, eValue);
        }else {
            int i = equipment_Enchants_Map.get(enchant);
            int x = i + eValue;
            equipment_Enchants_Map.put(enchant, x);
        }
    }
    //找出物品Lore轉成屬性
    public static void setCustomAttrs(PlayerData2 playerData, ItemMeta itemMeta){
        List<String> loreList = itemMeta.getLore();
        if(loreList != null){
            loreList.forEach(s -> {
                String[] loreArray = s.split(":");
                if(loreArray.length == 2){
                    if(playerData.name_Equipment_Map.containsKey(loreArray[0])){
                        String cName = playerData.name_Equipment_Map.get(loreArray[0]);
                        //cd.getLogger().info(loreArray[0]+" : "+cName);
                        if(playerData.equipment_Stats_Map.containsKey(cName)){
                            String nowValueString = playerData.equipment_Stats_Map.get(cName);
                            try {
                                double nowValue = Double.parseDouble(nowValueString);
                                double addValue = Double.parseDouble(loreArray[1].trim());
                                nowValue += addValue;
                                playerData.equipment_Stats_Map.put(cName, String.valueOf(nowValue));
                            }catch (NumberFormatException exception){
                                playerData.equipment_Stats_Map.put(cName, loreArray[1].trim());
                            }
                            String afterString = playerData.equipment_Stats_Map.get(cName);
                            //cd.getLogger().info(cName+" : "+nowValueString+" : "+afterString);


                        }
                    }
                }
            });
        }

    }

}
