package com.daxton.customdisplay.api.item;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.player.PlayerTrigger2;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.api.player.data.set.PlayerAction2;
import com.daxton.customdisplay.api.player.data.set.PlayerEquipmentStats;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class PlayerEquipment2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlayerEquipment2(){



    }

    public void reloadEquipment(Player player, int key){


        loadAllEq(player, key);
        new PlayerTrigger2(player).onTwo(player, null, "~eqmcheck");

    }


    public void loadAllEq(Player player, int key){

        UUID playerUUID = player.getUniqueId();
        String uuidString = playerUUID.toString();
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
        if(playerData != null){
            Map<String, Integer> equipment_Enchants_Map = playerData.equipment_Enchants_Map;//new HashMap<>();
            Map<String,List<CustomLineConfig>> action_Item_Trigger_Map = playerData.getAction_Item_Trigger_Map();
            if(!action_Item_Trigger_Map.isEmpty()){
                //cd.getLogger().info("清除");
                action_Item_Trigger_Map.clear();
            }

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
                        if(itemMeta != null){ // && !(itemMeta.getDisplayName().isEmpty())
                            /**找出裝備上的附魔屬性**/
                            itemMeta.getEnchants().forEach((enchantment, integer) -> {
                                printE(equipment_Enchants_Map, enchantment.getKey().getKey(), integer);

                            });
                            loadActionEq(player, action_Item_Trigger_Map, itemStack);
                        }

                    }
                }
            }
            if(key > 8){
                if(player.getInventory().getItemInMainHand() != null){
                    ItemStack itemStack = player.getInventory().getItemInMainHand();


                    if(itemStack != null){
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_player_equipment_type_mainhand>",itemStack.getType().toString());
                        if(itemMeta != null){  //&& !(itemMeta.getDisplayName().isEmpty())
                            /**找出主手上的附魔屬性**/
                            itemMeta.getEnchants().forEach((enchantment, integer) -> {
                                printE(equipment_Enchants_Map, enchantment.getKey().getKey(), integer);

                            });
                            loadActionEq(player, action_Item_Trigger_Map, itemStack);
                        }

                    }
                }
            }else {
                if(player.getInventory().getItem(key) != null){
                    ItemStack itemStack = player.getInventory().getItem(key);
                    if(itemStack != null){
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_player_equipment_type_mainhand>",itemStack.getType().toString());
                        if(itemMeta != null){  //&& !(itemMeta.getDisplayName().isEmpty())
                            /**找出主手上的附魔屬性**/
                            itemMeta.getEnchants().forEach((enchantment, integer) -> {
                                printE(equipment_Enchants_Map, enchantment.getKey().getKey(), integer);

                            });
                            loadActionEq(player, action_Item_Trigger_Map, itemStack);
                        }

                    }
                }
            }

            if(player.getInventory().getItemInOffHand() != null){
                ItemStack itemStack = player.getInventory().getItemInOffHand();
                if(itemStack != null){
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if(itemMeta != null){  //&& !(itemMeta.getDisplayName().isEmpty())
                        /**找出副手上的附魔屬性**/
                        itemMeta.getEnchants().forEach((enchantment, integer) -> {
                            printE(equipment_Enchants_Map, enchantment.getKey().getKey(), integer);

                        });
                        loadActionEq(player, action_Item_Trigger_Map, itemStack);
                    }

                }
            }
        }

//        equipment_Enchants_Map.forEach((s, integer) -> {
//            cd.getLogger().info(s+" : "+integer);
//        });
//        cd.getLogger().info("-------------------------------");
    }

    public void loadActionEq(Player player, Map<String,List<CustomLineConfig>> action_Item_Trigger_Map, ItemStack itemStack){
        if(itemStack != null){
            if(!itemStack.getItemMeta().getPersistentDataContainer().isEmpty()){
                Set<NamespacedKey> k = itemStack.getItemMeta().getPersistentDataContainer().getKeys();
                k.forEach(namespacedKey -> {
                    String ss = itemStack.getItemMeta().getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING);
                    //player.sendMessage("職: "+ss);
                    //cd.getLogger().info("職: "+ss);
                    new PlayerAction2(action_Item_Trigger_Map).setAction(ss);


                });
            }
        }


    }

    public void printE(Map<String, Integer> equipment_Enchants_Map, String enchant, int eValue){
        if(equipment_Enchants_Map.get(enchant) == null){
            equipment_Enchants_Map.put(enchant, eValue);
        }else {
            int i = equipment_Enchants_Map.get(enchant);
            int x = i + eValue;
            equipment_Enchants_Map.put(enchant, x);
        }
        //cd.getLogger().info(enchant+" : "+eValue);
    }

}
