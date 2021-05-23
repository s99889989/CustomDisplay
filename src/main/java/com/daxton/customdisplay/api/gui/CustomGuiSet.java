package com.daxton.customdisplay.api.gui;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.SetActionMap;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.EditorGUIManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CustomGuiSet {

    public CustomGuiSet(){

    }

    public static Inventory setInventory(LivingEntity self, LivingEntity target, String guiID){
        String uuidString = self.getUniqueId().toString();
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        Inventory inventory = null;
        if(EditorGUIManager.custom_Inventory_Name_Map.get(guiID) != null){
            FileConfiguration itemConfig = EditorGUIManager.custom_Inventory_Name_Map.get(guiID);
            String title = itemConfig.getString("Title");
            boolean skillBind = itemConfig.getBoolean("SkillBind");
            List<String> sort = itemConfig.getStringList("Sort");
            int amount;
            switch (sort.size()){
                case  5:
                    amount = 45;
                    break;
                case  4:
                    amount = 36;
                    break;
                case  3:
                    amount = 27;
                    break;
                case  2:
                    amount = 18;
                    break;
                case  1:
                    amount = 9;
                    break;
                default:
                    amount = 54;
            }
            if(skillBind){
                amount = 54;
            }
            if(EditorGUIManager.custom_Inventory_Map.get(uuidString) == null){
                if(title != null){
                    inventory = Bukkit.createInventory(null, amount, title);
                }

            }else {
                inventory = EditorGUIManager.custom_Inventory_Map.get(uuidString);
                inventory.clear();
                if(title != null){
                    inventory = Bukkit.createInventory(null, amount, title);
                }
            }

            if(inventory != null){

                if(skillBind){

                    setBindInventory(self, target, itemConfig, sort, inventory);

                }else {
                    for(int s = 0 ; s < sort.size() && s < 6 ; s++){

                        String[] so = sort.get(s).split("\\.");
                        int count = s * 9;
                        for(int o = 0  ; o < so.length && o < 9 ; o++){
                            int rawslot = count + o;
                            if(!so[o].equals(" ") && !so[o].isEmpty()){

                                ItemStack itemStack = CustomButtomSet.setButtom(self, target, itemConfig, so[o]);
                                if(itemStack != null){
                                    inventory.setItem(rawslot, itemStack);
                                }
                            }
                        }


                    }
                }

            }

        }

        if(inventory != null){
            EditorGUIManager.custom_Inventory_Map.put(uuidString, inventory);
        }

        return inventory;

    }


    public static void setBindInventory(LivingEntity self, LivingEntity target, FileConfiguration itemConfig, List<String> sort, Inventory inventory){

        String uuidString = self.getUniqueId().toString();

        if(ConfigMapManager.getFileConfigurationMap().get("Players_"+uuidString+".yml") != null && PlayerManager.player_Data_Map.get(uuidString) != null){
            FileConfiguration playerConfig = ConfigMapManager.getFileConfigurationMap().get("Players_"+uuidString+".yml");
            PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);
            List<String> skillNameList = new ArrayList<>(playerConfig.getConfigurationSection(uuidString+".Skills").getKeys(false));
            List<String> useSkillList = new ArrayList<>();

            skillNameList.forEach(skillName -> {
                if(playerConfig.getInt(uuidString+".Skills."+skillName+".use") > 0){
                    useSkillList.add(skillName);
                }

            });

            //CustomDisplay.getCustomDisplay().getLogger().info("大小: "+skillNameList.size());
            List<Integer> pass = new ArrayList<>();
            pass.add(10);pass.add(11);pass.add(12);pass.add(13);pass.add(14);pass.add(15);pass.add(16);
            pass.add(19);pass.add(20);pass.add(21);pass.add(22);pass.add(23);pass.add(24);pass.add(25);
            pass.add(28);pass.add(29);pass.add(30);pass.add(31);pass.add(32);pass.add(33);pass.add(34);
            pass.add(37);pass.add(38);pass.add(39);pass.add(40);pass.add(41);pass.add(42);pass.add(43);

            List<Integer> pass2 = new ArrayList<>();
            pass2.add(45);pass2.add(46);pass2.add(47);pass2.add(48);pass2.add(49);pass2.add(50);pass2.add(51);pass2.add(52);

            int skillCount = 0;
            int skillBindCount = 0;
            for(int s = 0 ; s < 6 ; s++){

                String[] so = sort.get(s).split("\\.");
                int count = s * 9;

                for(int o = 0  ; o < 9 ; o++){
                    int rawslot = count + o;

                    if(rawslot == 4){
                        continue;
                    }

                    if(pass2.contains(rawslot)){
                        skillBindCount++;
                        String bindN = playerData.getBindName(String.valueOf(skillBindCount));
                        if(!bindN.equals("null")){
                            ItemStack itemStack = CustomButtomSet.setSkillButtom(self, target, bindN);
                            inventory.setItem(rawslot, itemStack);
                            continue;
                        }

                    }

                    if(pass.contains(rawslot)){

                        if(useSkillList.size() > 0 && skillCount < useSkillList.size() && skillCount < 44){
                            //CustomDisplay.getCustomDisplay().getLogger().info(""+rawslot+" : "+skillCount+" : "+skillNameList.size());
                            ItemStack itemStack = CustomButtomSet.setSkillButtom(self, target, useSkillList.get(skillCount));
                            inventory.setItem(rawslot, itemStack);
                            playerData.skill_Bind_Map.put(rawslot, useSkillList.get(skillCount));
                            skillCount++;
                        }

                        continue;
                    }

                    if(!so[o].equals(" ") && !so[o].isEmpty()){

                        ItemStack itemStack = CustomButtomSet.setButtom(self, target, itemConfig, so[o]);
                        if(itemStack != null){
                            inventory.setItem(rawslot, itemStack);
                        }
                    }
                }


            }
        }

    }

    public static String getKey(FileConfiguration itemConfig, int inputRawSlot){
        List<String> sort = itemConfig.getStringList("Sort");

        String output = null;

        for(int s = 0 ; s < sort.size() && s < 6 ; s++){
            String[] so = sort.get(s).split("\\.");
            int count = s * 9;
            for(int o = 0  ; o < so.length && o < 9 ; o++){
                int rawslot = count + o;
                if(!so[o].equals(" ") && !so[o].isEmpty()){
                    if(rawslot == inputRawSlot){
                        output = so[o];
                    }
                }
            }
        }

        return output;
    }

    //**GUI動作監聽
    public static void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        String uuidString = player.getUniqueId().toString();
        Inventory inventory = event.getInventory();
        String guiID = EditorGUIManager.custom_Inventory_GuiID_Map.get(uuidString);

        FileConfiguration itemConfig = EditorGUIManager.custom_Inventory_Name_Map.get(guiID);
        int rawSlot = event.getRawSlot();
        boolean skillBind = itemConfig.getBoolean("SkillBind");

        List<Integer> pass = bindInvKey();

        ClickType clickType = event.getClick();

        String key = getKey(itemConfig, rawSlot);

        if(key != null){
            if(key.contains("|")){
                String[] strings = key.split("\\|");
                if(strings.length == 2){
                    if(ConfigMapManager.getFileConfigurationMap().get("Gui_Buttom_"+strings[0]+".yml") != null){
                        FileConfiguration buttomConfig = ConfigMapManager.getFileConfigurationMap().get("Gui_Buttom_"+strings[0]+".yml");
                        boolean move = buttomConfig.getBoolean("Buttons."+strings[1]+".Move");

                        if(skillBind && pass.contains(rawSlot)){
                            event.setCancelled(true);
                            onBindInv(player, clickType, rawSlot, inventory, buttomConfig, strings[1]);
                        }else {
                            event.setCancelled(!move);
                        }
                        List<String> leftClick = buttomConfig.getStringList("Buttons."+strings[1]+".Left");
                        List<String> leftShiftClick = buttomConfig.getStringList("Buttons."+strings[1]+".Left_Shift");
                        List<String> rightClick = buttomConfig.getStringList("Buttons."+strings[1]+".Right");
                        List<String> rightShiftClick = buttomConfig.getStringList("Buttons."+strings[1]+".Right_Shift");

                        List<Map<String, String>> leftClickCustom = SetActionMap.setClassActionList(leftClick);
                        List<Map<String, String>> leftShiftClickCustom = SetActionMap.setClassActionList(leftShiftClick);
                        List<Map<String, String>> rightClickCustom = SetActionMap.setClassActionList(rightClick);
                        List<Map<String, String>> rightShiftClickCustom = SetActionMap.setClassActionList(rightShiftClick);

                        if(clickType == ClickType.LEFT){
                            PlayerTrigger.onSkillList(player, null, leftClickCustom);
                        }
                        if(clickType == ClickType.SHIFT_LEFT){
                            PlayerTrigger.onSkillList(player, null, leftShiftClickCustom);
                        }
                        if(clickType == ClickType.RIGHT){
                            PlayerTrigger.onSkillList(player, null, rightClickCustom);
                        }
                        if(clickType == ClickType.SHIFT_RIGHT){
                            PlayerTrigger.onSkillList(player, null, rightShiftClickCustom);
                        }
                    }
                }
            }else {
                boolean move = itemConfig.getBoolean("Buttons."+key+".Move");

                if(skillBind && pass.contains(rawSlot)){
                    event.setCancelled(true);
                    onBindInv(player, clickType, rawSlot, inventory, itemConfig, key);
                }else {
                    event.setCancelled(!move);
                }
                List<String> leftClick = itemConfig.getStringList("Buttons."+key+".Left");
                List<String> leftShiftClick = itemConfig.getStringList("Buttons."+key+".Left_Shift");
                List<String> rightClick = itemConfig.getStringList("Buttons."+key+".Right");
                List<String> rightShiftClick = itemConfig.getStringList("Buttons."+key+".Right_Shift");

                List<Map<String, String>> leftClickCustom = SetActionMap.setClassActionList(leftClick);
                List<Map<String, String>> leftShiftClickCustom = SetActionMap.setClassActionList(leftShiftClick);
                List<Map<String, String>> rightClickCustom = SetActionMap.setClassActionList(rightClick);
                List<Map<String, String>> rightShiftClickCustom = SetActionMap.setClassActionList(rightShiftClick);

                if(clickType == ClickType.LEFT){
                    PlayerTrigger.onSkillList(player, null, leftClickCustom);
                }
                if(clickType == ClickType.SHIFT_LEFT){
                    PlayerTrigger.onSkillList(player, null, leftShiftClickCustom);
                }
                if(clickType == ClickType.RIGHT){
                    PlayerTrigger.onSkillList(player, null, rightClickCustom);
                }
                if(clickType == ClickType.SHIFT_RIGHT){
                    PlayerTrigger.onSkillList(player, null, rightShiftClickCustom);
                }
            }



        }

    }

    public static List<Integer> bindInvKey(){
        List<Integer> pass = new ArrayList<>();
        pass.add(4);
        pass.add(10);pass.add(11);pass.add(12);pass.add(13);pass.add(14);pass.add(15);pass.add(16);
        pass.add(19);pass.add(20);pass.add(21);pass.add(22);pass.add(23);pass.add(24);pass.add(25);
        pass.add(28);pass.add(29);pass.add(30);pass.add(31);pass.add(32);pass.add(33);pass.add(34);
        pass.add(37);pass.add(38);pass.add(39);pass.add(40);pass.add(41);pass.add(42);pass.add(43);
        pass.add(45);pass.add(46);pass.add(47);pass.add(48);pass.add(49);pass.add(50);pass.add(51);pass.add(52);
        return pass;
    }

    public static void onBindInv(Player player, ClickType clickType, int rawSlot, Inventory inventory, FileConfiguration itemConfig, String key){
        String uuidString = player.getUniqueId().toString();
        if(PlayerManager.player_Data_Map.get(uuidString) != null){
            PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);

            if(clickType == ClickType.LEFT){

                if(playerData.skill_Bind_Map.get(rawSlot) != null){
                    playerData.switchSkillName = playerData.skill_Bind_Map.get(rawSlot);
                    ItemStack itemStack = CustomButtomSet.setSkillButtom(player, null, playerData.skill_Bind_Map.get(rawSlot));
                    inventory.setItem(4, itemStack);
                    //player.sendMessage(playerData.skill_Bind_Map.get(rawSlot));
                }
                if(rawSlot == 45){
                    int useLevel = playerData.getSkillLevel(playerData.switchSkillName+"_use");
                    if(playerData.setBind("1", playerData.switchSkillName, useLevel)){
                        ItemStack itemStack = CustomButtomSet.setSkillButtom(player, null, playerData.switchSkillName);
                        inventory.setItem(45, itemStack);
                    }

                }
                if(rawSlot == 46){
                    int useLevel = playerData.getSkillLevel(playerData.switchSkillName+"_use");

                    if(playerData.setBind("2", playerData.switchSkillName, useLevel)){
                        ItemStack itemStack = CustomButtomSet.setSkillButtom(player, null, playerData.switchSkillName);
                        inventory.setItem(46, itemStack);
                    }

                }
                if(rawSlot == 47){
                    int useLevel = playerData.getSkillLevel(playerData.switchSkillName+"_use");
                    if(playerData.setBind("3", playerData.switchSkillName, useLevel)){
                        ItemStack itemStack = CustomButtomSet.setSkillButtom(player, null, playerData.switchSkillName);
                        inventory.setItem(47, itemStack);
                    }

                }
                if(rawSlot == 48){
                    int useLevel = playerData.getSkillLevel(playerData.switchSkillName+"_use");
                    if(playerData.setBind("4", playerData.switchSkillName, useLevel)){
                        ItemStack itemStack = CustomButtomSet.setSkillButtom(player, null, playerData.switchSkillName);
                        inventory.setItem(48, itemStack);
                    }

                }
                if(rawSlot == 49){
                    int useLevel = playerData.getSkillLevel(playerData.switchSkillName+"_use");
                    if(playerData.setBind("5", playerData.switchSkillName, useLevel)){
                        ItemStack itemStack = CustomButtomSet.setSkillButtom(player, null, playerData.switchSkillName);
                        inventory.setItem(49, itemStack);
                    }

                }
                if(rawSlot == 50){
                    int useLevel = playerData.getSkillLevel(playerData.switchSkillName+"_use");
                    if(playerData.setBind("6", playerData.switchSkillName, useLevel)){
                        ItemStack itemStack = CustomButtomSet.setSkillButtom(player, null, playerData.switchSkillName);
                        inventory.setItem(50, itemStack);
                    }

                }
                if(rawSlot == 51){
                    int useLevel = playerData.getSkillLevel(playerData.switchSkillName+"_use");
                    if(playerData.setBind("7", playerData.switchSkillName, useLevel)){
                        ItemStack itemStack = CustomButtomSet.setSkillButtom(player, null, playerData.switchSkillName);
                        inventory.setItem(51, itemStack);
                    }
                }
                if(rawSlot == 52){
                    int useLevel = playerData.getSkillLevel(playerData.switchSkillName+"_use");
                    if(playerData.setBind("8", playerData.switchSkillName, useLevel)){
                        ItemStack itemStack = CustomButtomSet.setSkillButtom(player, null, playerData.switchSkillName);
                        inventory.setItem(52, itemStack);
                    }
                }




            }
            if(clickType == ClickType.SHIFT_LEFT){


            }
            if(clickType == ClickType.RIGHT){

                if(rawSlot == 45){
                    playerData.clearBind(1);

                    ItemStack itemStack = new ItemStack(Material.BOOK);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName("Bind"+1);
                    itemStack.setItemMeta(itemMeta);

                    if(itemConfig.contains("Buttons."+key+".Material")){
                        itemStack = CustomButtomSet.setButtom(player, null, itemConfig, key);
                    }

                    inventory.setItem(45, itemStack);
                }
                if(rawSlot == 46){
                    playerData.clearBind(2);
                    ItemStack itemStack = new ItemStack(Material.BOOK);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName("Bind"+2);
                    itemStack.setItemMeta(itemMeta);

                    if(itemConfig.contains("Buttons."+key+".Material")){
                        itemStack = CustomButtomSet.setButtom(player, null, itemConfig, key);
                    }

                    inventory.setItem(46, itemStack);
                }
                if(rawSlot == 47){
                    playerData.clearBind(3);
                    ItemStack itemStack = new ItemStack(Material.BOOK);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName("Bind"+3);
                    itemStack.setItemMeta(itemMeta);
                    if(itemConfig.contains("Buttons."+key+".Material")){
                        itemStack = CustomButtomSet.setButtom(player, null, itemConfig, key);
                    }
                    inventory.setItem(47, itemStack);
                }
                if(rawSlot == 48){
                    playerData.clearBind(4);
                    ItemStack itemStack = new ItemStack(Material.BOOK);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName("Bind"+4);
                    itemStack.setItemMeta(itemMeta);
                    if(itemConfig.contains("Buttons."+key+".Material")){
                        itemStack = CustomButtomSet.setButtom(player, null, itemConfig, key);
                    }
                    inventory.setItem(48, itemStack);
                }
                if(rawSlot == 49){
                    playerData.clearBind(5);
                    ItemStack itemStack = new ItemStack(Material.BOOK);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName("Bind"+5);
                    itemStack.setItemMeta(itemMeta);
                    if(itemConfig.contains("Buttons."+key+".Material")){
                        itemStack = CustomButtomSet.setButtom(player, null, itemConfig, key);
                    }
                    inventory.setItem(49, itemStack);
                }
                if(rawSlot == 50){
                    playerData.clearBind(6);
                    ItemStack itemStack = new ItemStack(Material.BOOK);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName("Bind"+6);
                    itemStack.setItemMeta(itemMeta);
                    if(itemConfig.contains("Buttons."+key+".Material")){
                        itemStack = CustomButtomSet.setButtom(player, null, itemConfig, key);
                    }
                    inventory.setItem(50, itemStack);
                }
                if(rawSlot == 51){
                    playerData.clearBind(7);
                    ItemStack itemStack = new ItemStack(Material.BOOK);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName("Bind"+7);
                    itemStack.setItemMeta(itemMeta);
                    if(itemConfig.contains("Buttons."+key+".Material")){
                        itemStack = CustomButtomSet.setButtom(player, null, itemConfig, key);
                    }
                    inventory.setItem(51, itemStack);
                }
                if(rawSlot == 52){
                    playerData.clearBind(8);
                    ItemStack itemStack = new ItemStack(Material.BOOK);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName("Bind"+8);
                    itemStack.setItemMeta(itemMeta);
                    if(itemConfig.contains("Buttons."+key+".Material")){
                        itemStack = CustomButtomSet.setButtom(player, null, itemConfig, key);
                    }
                    inventory.setItem(52, itemStack);
                }


            }
            if(clickType == ClickType.SHIFT_RIGHT){


            }


        }



    }



}
