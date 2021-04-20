package com.daxton.customdisplay.api.item;

import com.daxton.customdisplay.api.item.gui.ItemMenuEdit;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlayerManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Random;

public class ItemSet {

    private FileConfiguration itemConfig;

    private String typeName;

    private String itemID;

    private Player player;

    public ItemSet(){

    }

    public ItemSet(Player player){
        this.player = player;

    }

    public ItemSet(Player player, String typeName){
        this.player = player;
        this.typeName = typeName;
        this.itemConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+typeName+".yml");
    }

    public ItemSet(Player player, String typeName, String itemID){
        this.player = player;
        this.typeName = typeName;
        this.itemID = itemID;
        this.itemConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+typeName+".yml");
    }

    //建立新物品
    public void createNewItem(String itemName){


        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_ItemMenu.yml");
        String displayMaterial = itemMenuConfig.getString("Items."+ this.typeName +".DisplayMaterial");
        String[] displayMaterialArray = displayMaterial.split(":");

        String materialString = "STONE";

        if(displayMaterialArray.length == 2){
            try {
                Material material = Enum.valueOf(Material.class,materialString);
                materialString = displayMaterialArray[0].toUpperCase();
            }catch (Exception exception){

            }

        }

        this.itemConfig.set(itemName+".Material",materialString);
        this.itemID = itemName;
    }

    //物品顯示名稱
    public void setDisplayName(String setValue){
        this.itemConfig.set(this.itemID+".DisplayName",setValue);
    }
    //移除物品顯示名稱
    public void removeDisplayName(){
        this.itemConfig.set(this.itemID+".DisplayName",null);
    }

    //物品使用材質
    public void setMaterial(String setValue){
        try {
            Material material = Enum.valueOf(Material.class,setValue.toUpperCase());
            this.itemConfig.set(this.itemID+".Material", setValue.toUpperCase());
        }catch (Exception exception){
        }

    }
    //使用預設物品使用材質
    public void setDefaultMaterial(){
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_ItemMenu.yml");
        String displayMaterial = itemMenuConfig.getString("Items."+ this.typeName +".DisplayMaterial");
        String[] displayMaterialArray = displayMaterial.split(":");

        String materialString = "STONE";

        if(displayMaterialArray.length == 2){
            materialString = displayMaterialArray[0].toUpperCase();
        }
        try {
            Material material = Enum.valueOf(Material.class,materialString);
            this.itemConfig.set(this.itemID+".Material", materialString);

        }catch (Exception exception){

        }

    }

    //物品的CustomModelData
    public void setCustomModelData(String setValue){
        try {
            int cmd = Integer.parseInt(setValue);
            this.itemConfig.set(this.itemID+".CustomModelData", cmd);

        }catch (NumberFormatException exception){

        }
    }

    //設定Lore
    public void setLore(String setValue){
        List<String> lore = this.itemConfig.getStringList(this.itemID+".Lore");
        lore.add(setValue);
        this.itemConfig.set(this.itemID+".Lore", lore);
    }

    //設定Lore
    public void removeLore(){
        List<String> lore = this.itemConfig.getStringList(this.itemID+".Lore");
        if(lore.size() > 0){
            lore.remove(0);
            this.itemConfig.set(this.itemID+".Lore", lore);
        }
    }

    //設置頭值
    public void setHeadValue(String setValue){
        this.itemConfig.set(this.itemID+".HeadValue", setValue);
    }
    //移除頭值
    public void removeHeadValue(){
        this.itemConfig.set(this.itemID+".HeadValue", null);
    }

    //設置冷卻值
    public void setCoolDown(String setValue){
        int tick = 0;
        try {
            tick = Integer.parseInt(setValue);
        }catch (NumberFormatException exception){

        }
        this.itemConfig.set(this.itemID+".CoolDown", tick);
    }
    //設置冷卻值
    public void removeCoolDown(){
        this.itemConfig.set(this.itemID+".CoolDown", null);
    }

    //設置損壞值
    public void setData(String setValue){
        try {
            int data = Integer.parseInt(setValue);
            this.itemConfig.set(this.itemID+".Data", data);

        }catch (NumberFormatException exception){

        }
    }
    //移除損壞值
    public void removeData(){
        this.itemConfig.set(this.itemID+".Data", null);
    }

    //設置附魔
    public void setEnchantment(String setValue){

        List<String> lore = this.itemConfig.getStringList(this.itemID+".Enchantments");
        lore.add(setValue);
        this.itemConfig.set(this.itemID+".Enchantments", lore);

    }

    //設置附魔
    public void removeEnchantment(){
        List<String> lore = this.itemConfig.getStringList(this.itemID+".Enchantments");
        if(lore.size() > 0){
            lore.remove(0);
            this.itemConfig.set(this.itemID+".Enchantments", lore);
        }
    }


    //設置不可破壞
    public void setUnbreakable(){
        this.itemConfig.set(this.itemID+".Unbreakable", true);

    }
    //設置可破壞
    public void setBreakable(){
        this.itemConfig.set(this.itemID+".Unbreakable", false);

    }
    //設置隱藏Flag
    public void setHideItemFlags(){
        this.itemConfig.set(this.itemID+".HideItemFlags", true);

    }
    public void setHideAttributes(){
        this.itemConfig.set(this.itemID+".ItemFlags.HideAttributes", true);
    }
    public void setHideDestroys(){
        this.itemConfig.set(this.itemID+".ItemFlags.HideDestroys", true);
    }
    public void setHideDye(){
        this.itemConfig.set(this.itemID+".ItemFlags.HideDye", true);
    }
    public void setHideEnchants(){
        this.itemConfig.set(this.itemID+".ItemFlags.HideEnchants", true);
    }
    public void setHidePlacedOn(){
        this.itemConfig.set(this.itemID+".ItemFlags.HidePlacedOn", true);
    }
    public void setHidePotionEffects(){
        this.itemConfig.set(this.itemID+".ItemFlags.HidePotionEffects", true);
    }
    public void setHideUnbreakable(){
        this.itemConfig.set(this.itemID+".ItemFlags.HideUnbreakable", true);
    }
    //設置不隱藏Flag
    public void setUnHideItemFlags(){
        this.itemConfig.set(this.itemID+".HideItemFlags", false);

    }
    public void setUnHideAttributes(){
        this.itemConfig.set(this.itemID+".ItemFlags.HideAttributes", false);
    }
    public void setUnHideDestroys(){
        this.itemConfig.set(this.itemID+".ItemFlags.HideDestroys", false);
    }
    public void setUnHideDye(){
        this.itemConfig.set(this.itemID+".ItemFlags.HideDye", false);
    }
    public void setUnHideEnchants(){
        this.itemConfig.set(this.itemID+".ItemFlags.HideEnchants", false);
    }
    public void setUnHidePlacedOn(){
        this.itemConfig.set(this.itemID+".ItemFlags.HidePlacedOn", false);
    }
    public void setUnHidePotionEffects(){
        this.itemConfig.set(this.itemID+".ItemFlags.HidePotionEffects", false);
    }
    public void setUnHideUnbreakable(){
        this.itemConfig.set(this.itemID+".ItemFlags.HideUnbreakable", false);
    }


    //設置屬性
    public void setAttributes(String equipmentSlot, String inherit, String operation, double amount){
        List<String> lore = this.itemConfig.getStringList(this.itemID+".Attributes");
        lore.add(equipmentSlot+":"+inherit+":"+operation+":"+amount);
        this.itemConfig.set(this.itemID+".Attributes", lore);
    }
    //移除屬性
    public void removeAttributes(){
        List<String> lore = this.itemConfig.getStringList(this.itemID+".Attributes");
        if(lore.size() > 0){
            lore.remove(0);
        }
        this.itemConfig.set(this.itemID+".Attributes", lore);

    }



    //給物品
    public void giveItem(){

        ItemStack itemStack = MenuItem.valueOf(this.itemConfig, this.itemID);
        this.player.getInventory().addItem(itemStack);
    }

    //點擊功能
    public void click(String clickType, String message){
        String uuidString = this.player.getUniqueId().toString();

        this.player.closeInventory();
        sendTitle(message);

        PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString).editType = clickType;
        PlayerManager.menu_Chat_ItemMenuEdit_Map.put(uuidString, true);
    }

    //點擊功能
    public void clickItemMenuType(String message){
        String uuidString = this.player.getUniqueId().toString();

        this.player.closeInventory();
        sendTitle(message);

        PlayerManager.menu_Chat_ItemMenuType_Map.put(uuidString, true);
    }

    //點擊功能
    public void clickEnchantment(String message){
        String uuidString = this.player.getUniqueId().toString();

        this.player.closeInventory();
        sendTitle(message);

        PlayerManager.menu_Chat_ItemEnchantmentEdit_Map.put(uuidString, true);
    }

    //點擊功能
    public void clickAttributes(String message){
        String uuidString = this.player.getUniqueId().toString();

        this.player.closeInventory();
        sendTitle(message);

        PlayerManager.menu_Chat_ItemAttributesEdit_Map.put(uuidString, true);
    }


    //發送訊息
    public void sendTitle(String title){
        this.player.sendTitle("",title,10,40,40);
    }

    public void closeEditMenu(){
        this.player.closeInventory();
    }

    //打開物品編輯介面
    public void openEditMenu(){
        String uuidString = this.player.getUniqueId().toString();
        PlayerManager.menu_Chat_ItemMenuEdit_Map.put(uuidString, false);
        PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString).openMenu(this.player, this.typeName, this.itemID);
    }

    //打開物品編輯介面
    public void openItemMenuType(){
        String uuidString = this.player.getUniqueId().toString();
        PlayerManager.menu_Chat_ItemMenuType_Map.put(uuidString, false);
        if(PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString) == null){
            PlayerManager.menu_Inventory_ItemMenuEdit_Map.put(uuidString, new ItemMenuEdit());
        }
        if(PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString) != null){
            PlayerManager.menu_Inventory_ItemMenuEdit_Map.get(uuidString).openMenu(this.player, this.typeName, this.itemID);
        }
    }

    //打開物品Flags介面
    public void openItemFlagsEdit(){
        String uuidString = this.player.getUniqueId().toString();
        PlayerManager.menu_Inventory_ItemFlagsEdit_Map.get(uuidString).openMenu(this.player, this.typeName, this.itemID);
    }

    //打開物品列表介面
    public void openItemListMenu(int page){
        String uuidString = this.player.getUniqueId().toString();
        PlayerManager.menu_Inventory_ItemListMenu_Map.get(uuidString).openMenu(this.player, this.typeName, this.itemID,page);
    }

    //打開物品編輯介面
    public void openEnchantmentMenu(){
        String uuidString = this.player.getUniqueId().toString();
        PlayerManager.menu_Chat_ItemEnchantmentEdit_Map.put(uuidString, false);
        PlayerManager.menu_Inventory_ItemEnchantmentEdit_Map.get(uuidString).openMenu(this.player, this.typeName, this.itemID, 0);
    }
    //打開物品編輯介面
    public void openAttributesMenu(int es, int it, int ot){
        String uuidString = this.player.getUniqueId().toString();
        PlayerManager.menu_Chat_ItemAttributesEdit_Map.put(uuidString, false);
        PlayerManager.menu_Inventory_ItemAttributesEdit_Map.get(uuidString).openMenu(this.player, this.typeName, this.itemID, es, it, ot);
    }
}
