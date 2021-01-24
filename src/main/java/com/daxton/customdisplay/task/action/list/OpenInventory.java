package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.config.LoadConfig;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.util.*;

public class OpenInventory {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";
    private Player playerTest = null;

    private String function = "";
    private String message = "";
    private String GuiID = "Default";
    private int amount = 27;


    private String skillNowName = "";
    private int next = 0;

    private Map<Integer,Integer> RawSlot = new HashMap<>();
    private Map<Integer,Boolean> Move = new HashMap<>();

    private Map<Integer,List<String>> left_Shift_Click = new HashMap<>();
    private Map<Integer,List<String>> right_Shift_Click = new HashMap<>();
    private Map<Integer,List<String>> left_Click = new HashMap<>();
    private Map<Integer,List<String>> right_Click = new HashMap<>();

    public OpenInventory(){


    }

    public void setInventory(LivingEntity self, LivingEntity target, String firstString, String taskID){

        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;


        setOther();
    }

    public void setOther(){
        for(String allString : new StringFind().getStringMessageList(firstString)){
            if(allString.toLowerCase().contains("function=") || allString.toLowerCase().contains("fc=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    function = strings[1];

                }
            }

            if(allString.toLowerCase().contains("message=") || allString.toLowerCase().contains("m=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    message = strings[1];

                }
            }

            if(allString.toLowerCase().contains("guiid=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    GuiID = strings[1];
                }
            }


            if(allString.toLowerCase().contains("amount=") || allString.toLowerCase().contains("a=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    try{
                        amount = Integer.valueOf(strings[1]);
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("amount=內只能放整數數字");
                    }
                }
            }
        }

        if(self instanceof Player){
            Player player = ((Player) self).getPlayer();
            playerTest = player;
            if(function.toLowerCase().contains("gui")){
                openGui(player);
            }else if(function.toLowerCase().contains("close")){
                player.closeInventory();
            }else if(function.toLowerCase().contains("bind")){
                openBindGui(player,0);
            }
            else {
                openInventory(player);
            }

        }
    }




    public void openInventory(Player player){
        if(ActionManager.getInventory_Map().get(taskID) == null){
            ActionManager.getInventory_Map().put(taskID,Bukkit.createInventory(null,amount , message));
            ActionManager.getInventory_name_Map().put(player.getUniqueId(),taskID);
        }
        if(ActionManager.getInventory_Map().get(taskID) != null){
            Inventory inventory = ActionManager.getInventory_Map().get(taskID);

            player.openInventory(inventory);
        }

    }

    public void openGui(Player player){
        if(ActionManager.getInventory_Map().get(taskID) == null){
            ActionManager.getInventory_Map().put(taskID,Bukkit.createInventory(null,amount , message));
            ActionManager.getInventory_name_Map().put(player.getUniqueId(),taskID);
        }
        if(ActionManager.getInventory_Map().get(taskID) != null){
            Inventory inventory = ActionManager.getInventory_Map().get(taskID);

            inventory = setInventory(inventory);


            player.openInventory(inventory);
        }

    }





    public Inventory setInventory(Inventory inventory){

        File itemFilePatch = new File(cd.getDataFolder(),"Gui/"+GuiID+".yml");
        FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(itemFilePatch);
        ConfigurationSection button = itemConfig.getConfigurationSection("Buttons");
        for(int i = 0; i < inventory.getSize() ;i++){

            inventory.setItem(i,null);
        }
        for(String key : button.getKeys(false)){

                int rawslot = itemConfig.getInt("Buttons."+key+".RawSlot");
                boolean move = itemConfig.getBoolean("Buttons."+key+".Move");
                int cmd = itemConfig.getInt("Buttons."+key+".CustomModelData");
                boolean flag = itemConfig.getBoolean("Buttons."+key+".RemoveItemFlags");
                String itemMaterial = itemConfig.getString("Buttons."+key+".Material");
                String itemName = itemConfig.getString("Buttons."+key+".Name");
                itemName = new ConversionMain().valueOf(self,target,itemName);
                List<String> itemLore = itemConfig.getStringList("Buttons."+key+".Lore");
                List<String> nextItemLore = new ArrayList<>();
                itemLore.forEach((line) -> {
                    nextItemLore.add(ChatColor.GRAY + line);
                });
                List<String> lastItemLore = new ArrayList<>();
                nextItemLore.forEach((line) -> {
                    lastItemLore.add(ChatColor.GRAY + new ConversionMain().valueOf(self,target,line));
                });
                List<String> leftClick = itemConfig.getStringList("Buttons."+key+".Left");
                List<String> leftShiftClick = itemConfig.getStringList("Buttons."+key+".Left_Shift");
                List<String> rightClick = itemConfig.getStringList("Buttons."+key+".Right");
                List<String> rightShiftClick = itemConfig.getStringList("Buttons."+key+".Right_Shift");


                Material material = Enum.valueOf(Material.class,itemMaterial.replace(" ","").toUpperCase());

                ItemStack customItem = new ItemStack(material);

                if(itemMaterial.contains("PLAYER_HEAD")){
                    SkullMeta skullMeta = (SkullMeta) customItem.getItemMeta();
                    skullMeta.setOwningPlayer(playerTest);
                    customItem.setItemMeta(skullMeta);
                }



                ItemMeta im = customItem.getItemMeta();
                im.setDisplayName(itemName);
                im.setLore(lastItemLore);
                im.setCustomModelData(cmd);



                if(flag){
                    im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    im.addItemFlags(ItemFlag.HIDE_DESTROYS);
                    im.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                    im.addItemFlags(ItemFlag.HIDE_DYE);
                }

                RawSlot.put(rawslot,rawslot);
                Move.put(rawslot,!move);
                right_Click.put(rawslot,rightClick);
                left_Click.put(rawslot,leftClick);
                right_Shift_Click.put(rawslot,rightShiftClick);
                left_Shift_Click.put(rawslot,leftShiftClick);

                customItem.setItemMeta(im);
                inventory.setItem(rawslot,customItem);



        }

        return inventory;
    }

    public void InventoryListener(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();


        int i = event.getRawSlot();
        if(RawSlot.get(i) != null && RawSlot.get(i) == i){
            event.setCancelled(Move.get(i));

            if(event.getClick().toString().contains("LEFT")){
                new PlayerTrigger(player).onGuiClick(player,left_Click.get(i));
            }
            if(event.getClick().toString().contains("SHIFT_LEFT")){
                new PlayerTrigger(player).onGuiClick(player,left_Shift_Click.get(i));
            }
            if(event.getClick().toString().contains("RIGHT")){
                new PlayerTrigger(player).onGuiClick(player,right_Click.get(i));
            }
            if(event.getClick().toString().contains("SHIFT_RIGHT")){
                new PlayerTrigger(player).onGuiClick(player,right_Shift_Click.get(i));
            }

        }


        if(function.toLowerCase().contains("bind")){
            event.setCancelled(true);
            if(i == 17){
                if(event.getClick().toString().contains("LEFT")){
                    openBindGui(player,1);
                }
                if(event.getClick().toString().contains("RIGHT")){
                    openBindGui(player,-1);
                }
            }
            if(i > 26 && i < 35){
                if(event.getClick().toString().contains("LEFT")){
                    setBind(player,i);
                }
                if(event.getClick().toString().contains("RIGHT")){
                    removeBind(player,i);
                }
            }

        }

    }
    /**設定綁定**/
    public void setBind(Player player,int first){
        UUID uuid = player.getUniqueId();
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(uuid);
        if(playerData != null){
            String uuidString = player.getUniqueId().toString();

            int use = Integer.valueOf(playerData.skills_Map.get(skillNowName+"_use"));
            int key = first - 26;
            
            playerData.binds_Map.put(key+"_skillName",skillNowName);
            playerData.binds_Map.put(key+"_use",String.valueOf(use));

            Inventory inventory = ActionManager.getInventory_Map().get(taskID);
            ItemStack customItem = inventory.getItem(13);
            ItemMeta bindMeta = customItem.getItemMeta();
            String itemName = bindMeta.getDisplayName().replace("(綁定1)","").replace("(綁定2)","").replace("(綁定3)","").replace("(綁定4)","").replace("(綁定5)","").replace("(綁定6)","").replace("(綁定7)","").replace("(綁定8)","");

            bindMeta.setDisplayName(itemName+"(綁定"+key+")");
            customItem.setItemMeta(bindMeta);
            inventory.setItem(first,customItem);

            int putkey = key - 1;
            File skillFile = new File(cd.getDataFolder(),"Class/Skill/Skills/"+skillNowName+".yml");
            FileConfiguration skillConfig = YamlConfiguration.loadConfiguration(skillFile);
            List<String> skillAction = skillConfig.getStringList(skillNowName+".Action");
            PlayerDataMap.skill_Key_Map.put(uuidString+"."+putkey,skillAction);
        }

    }
    /**移除綁定**/
    public void removeBind(Player player,int first){
        UUID uuid = player.getUniqueId();
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(uuid);
        if(playerData != null){
            String uuidString = player.getUniqueId().toString();

            int key = first - 26;

            playerData.binds_Map.put(key+"_skillName","null");
            playerData.binds_Map.put(key+"_use","0");

            Inventory inventory = ActionManager.getInventory_Map().get(taskID);
            ItemStack bindItem = new ItemStack(Material.BOOK);
            ItemMeta bindMeta = bindItem.getItemMeta();
            bindMeta.setDisplayName("§e綁定"+key);
            bindItem.setItemMeta(bindMeta);
            inventory.setItem(first,bindItem);

            PlayerDataMap.skill_Key_Map.remove(uuidString+"."+key);

        }
    }
    /**打開綁定Gui**/
    public void openBindGui(Player player, int first){

        ActionManager.getInventory_Map().put(taskID,Bukkit.createInventory(null,45 , message));
        ActionManager.getInventory_name_Map().put(player.getUniqueId(),taskID);
        Inventory inventory = ActionManager.getInventory_Map().get(taskID);
        String uuidString = player.getUniqueId().toString();

        UUID uuid = player.getUniqueId();
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(uuid);
        if(playerData != null){
            List<String> skillList = new ArrayList<>();
            Iterator skillKeyList = playerData.skills_Map.keySet().iterator();
            while(skillKeyList.hasNext()){
                String skillKey = (String) skillKeyList.next();
                if(skillKey.contains("_level")){
                    String skill = skillKey.replace("_level","");
                    skillList.add(skill);
                }
            }

            int count = 0;
            next = next + first;
            for(int i = 9; i <= 16 ; i++){
                count = next+(i-9);
                while (count >= skillList.size()){
                    count = count - skillList.size();
                }
                while (count < 0){
                    count = count + skillList.size();
                }
                String skillName = skillList.get(count);
                if(i == 13){
                    skillNowName = skillName;
                }
                File skillFile = new File(cd.getDataFolder(),"Class/Skill/Skills/"+skillName+".yml");
                FileConfiguration skillConfig = YamlConfiguration.loadConfiguration(skillFile);
                String itemMaterial = skillConfig.getString(skillName+".Material");
                Material material = Enum.valueOf(Material.class,itemMaterial.replace(" ","").toUpperCase());
                List<String> itemLore = skillConfig.getStringList(skillName+".Lore");
                List<String> lastItemLore = new ArrayList<>();
                itemLore.forEach((line) -> {
                    lastItemLore.add(new ConversionMain().valueOf(self,target,line));
                });
                int cmd = skillConfig.getInt(skillName+".CustomModelData");
                String itemName = skillConfig.getString(skillName+".Name");
                itemName = new ConversionMain().valueOf(self,target,itemName);
                ItemStack customItem = new ItemStack(material);
                ItemMeta im = customItem.getItemMeta();
                im.setDisplayName(itemName);
                im.setCustomModelData(cmd);
                im.setLore(lastItemLore);
                customItem.setItemMeta(im);
                inventory.setItem(i,customItem);
            }

            ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
            skullMeta.setOwningPlayer(player);
            playerHead.setItemMeta(skullMeta);

            ItemMeta headMeta = playerHead.getItemMeta();
            headMeta.setDisplayName("以下為目標");
            playerHead.setItemMeta(headMeta);

            inventory.setItem(4,playerHead);

            ItemStack selectItem = new ItemStack(Material.COAL);
            ItemMeta selectMeta = selectItem.getItemMeta();
            selectMeta.setDisplayName("§e選擇");
            List<String> selectLoreList = new ArrayList<>();
            selectLoreList.add("");
            selectLoreList.add("§e左鍵往左");
            selectLoreList.add("§e右鍵往右");
            selectMeta.setLore(selectLoreList);
            selectItem.setItemMeta(selectMeta);
            inventory.setItem(17,selectItem);

            for(int i = 1 ; i < 9 ; i++){
                String bind = playerData.binds_Map.get(i+"_skillName");
                if(bind != null){
                    if(bind.contains("null")){
                        ItemStack bindItem = new ItemStack(Material.BOOK);
                        ItemMeta bindMeta = bindItem.getItemMeta();
                        bindMeta.setDisplayName("§e綁定"+i);
                        bindItem.setItemMeta(bindMeta);
                        inventory.setItem(i+26,bindItem);
                    }else{
                        try{
                            File skillFile = new File(cd.getDataFolder(),"Class/Skill/Skills/"+bind+".yml");
                            FileConfiguration skillConfig = YamlConfiguration.loadConfiguration(skillFile);
                            String itemMaterial = skillConfig.getString(bind+".Material");
                            Material material = Enum.valueOf(Material.class,itemMaterial.replace(" ","").toUpperCase());
                            List<String> itemLore = skillConfig.getStringList(bind+".Lore");
                            List<String> lastItemLore = new ArrayList<>();
                            itemLore.forEach((line) -> {
                                lastItemLore.add(new ConversionMain().valueOf(self,target,line));
                            });
                            int cmd = skillConfig.getInt(bind+".CustomModelData");
                            String itemName = skillConfig.getString(bind+".Name");
                            itemName = new ConversionMain().valueOf(self,target,itemName);
                            ItemStack bindItem = new ItemStack(material);
                            ItemMeta bindMeta = bindItem.getItemMeta();
                            bindMeta.setDisplayName(itemName+"(綁定"+i+")");
                            bindMeta.setCustomModelData(cmd);
                            bindMeta.setLore(lastItemLore);
                            bindItem.setItemMeta(bindMeta);
                            inventory.setItem(i+26,bindItem);
                        }catch (Exception exception){
                            ItemStack bindItem = new ItemStack(Material.BOOK);
                            ItemMeta bindMeta = bindItem.getItemMeta();
                            bindMeta.setDisplayName("§e綁定"+i);
                            bindItem.setItemMeta(bindMeta);
                            inventory.setItem(i+26,bindItem);
                        }


                    }
                }

            }
        }


        player.openInventory(inventory);

    }

}
