package com.daxton.customdisplay.api.player.data;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.SetActionMap;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringConversion;
import com.daxton.customdisplay.api.player.config.PlayerConfig2;
import com.daxton.customdisplay.api.player.data.set.PlayerAttributeCore;
import com.daxton.customdisplay.api.player.data.set.PlayerAttributesStats;
import com.daxton.customdisplay.api.player.data.set.PlayerEquipmentStats;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerData2{

    private Player player;

    private String uuidString;

    private FileConfiguration playerConfig;

    //玩家資訊
    //身上物品數量
    public final Map<String, Integer> inventory_Item_Amount = new HashMap<>();

    //職業資訊
    //裝備附魔加總
    public Map<String, Integer> equipment_Enchants_Map = new HashMap<>();
    //玩家設定動作
    public List<Map<String, String>> player_Action_List_Map = new ArrayList<>();
    //物品動作
    public List<Map<String, String>> player_Item_Action_List_Map = new ArrayList<>();
    //玩家被動技能動作
    public List<Map<String, String>> player_Skill_Action_List_Map = new ArrayList<>();
    //玩家自訂屬性
    public Map<String,String> attributes_Stats_Map = new HashMap<>();
    public Map<String,String> attributes_Stats_Map2 = new HashMap<>();
    public Map<String,Map<String,String>> attributes_Stats_Map3 = new HashMap<>();
    //玩家裝備屬性
    public Map<String,String> equipment_Stats_Map = new HashMap<>();
    public Map<String,String> equipment_Stats_Map2 = new HashMap<>();
    public Map<String,Map<String,String>> equipment_Stats_Map3 = new HashMap<>();
    public Map<String,String> name_Equipment_Map = new HashMap<>();
    //屬性點數
    public Map<String, Map<String, Integer>> attributes_Point_Map = new HashMap<>();
    //綁定
    public Map<Integer, String> skill_Bind_Map = new HashMap<>();
    public String switchSkillName = "";
    //魔量
    public double mana;
    //最高魔量
    public double maxMana;
    //回魔速度
    public double mana_Regeneration;
    //攻擊速度用
    public boolean attackSpeed = true;

    private final CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlayerData2(){

    }

    public PlayerData2(Player player){


        this.player = player;
        this.uuidString = player.getUniqueId().toString();

        //設定讀取設定檔
        PlayerConfig2.setConfig(player);
        this.playerConfig = ConfigMapManager.getFileConfigurationMap().get("Players_"+this.uuidString+".yml");
        //設定自訂屬性
        PlayerAttributesStats.setMap(player, this.attributes_Stats_Map, this.playerConfig);
        PlayerEquipmentStats.setMap(player, this.equipment_Stats_Map, this.playerConfig, this.name_Equipment_Map);
        //設定動作
        setPlayerActionList();
        //設定技能動作
        setSkillActionList();
        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                //設置血量
                PlayerAttributeCore.setBukkitAttribute(player);
                //設置初始魔量、回魔量
                setMana(PlayerDataMethod.setDefaultMana(player));
                setMaxMana(PlayerDataMethod.setDefaultMana(player));
                setMana_Regeneration(PlayerDataMethod.setDefaultManaReg(player));
            }
        };
        bukkitRunnable.runTaskLater(cd,20);

    }
    //------------------------------------------------------------------------//
    public void setDfaultEqm(){
        PlayerEquipmentStats.setDefault(this.player, this.equipment_Stats_Map, this.playerConfig);
    }
    //------------------------------------------------------------------------//
    //設置最高魔量
    public double getMaxMana() {
        return maxMana;
    }
    //設置最高魔量
    public void setMaxMana(double maxMana) {
        this.maxMana = maxMana;
    }
    //設置魔量
    public void setMana(double value){
        this.mana = value;
    }
    //獲取魔量
    public double getMana() {
        return mana;
    }
    //獲取回魔量
    public double getMana_Regeneration() {
        return mana_Regeneration;
    }
    //設定回魔量
    public void setMana_Regeneration(double mana_Regeneration) {
        this.mana_Regeneration = mana_Regeneration;
    }
    //------------------------------------------------------------------------//
    //獲取等級
    public int getLevel(String type){
        if(this.playerConfig.contains(this.uuidString+".Level."+type)){
            return this.playerConfig.getInt(this.uuidString+".Level."+type);
        }
        return -1;
    }
    //設定等級
    public void setLevel(String type, int amount){
        if(this.playerConfig.contains(this.uuidString+".Level."+type)){
            this.playerConfig.set(this.uuidString+".Level."+type, amount);
        }

    }
    //------------------------------------------------------------------------//
    //獲取點數
    public int getPoint(String type){
        int po = -1;
        if(this.playerConfig.contains(this.uuidString+".Point."+type)){
            po = this.playerConfig.getInt(this.uuidString+".Point."+type);
        }
        return po;
    }
    //設置點數
    public void setPoint(String type, int amount){
        if(this.playerConfig.contains(this.uuidString+".Point."+type)){
            this.playerConfig.set(this.uuidString+".Point."+type, amount);
        }
    }

    //------------------------------------------------------------------------//
    //獲取屬性點數
    public int getAttrPoint(String type){
        int po = -1;
        if(this.playerConfig.contains(this.uuidString+".Attributes_Point."+type)){
            po = this.playerConfig.getInt(this.uuidString+".Attributes_Point."+type);
            if(this.attributes_Point_Map.containsKey(type)){
                Map<String, Integer> ponitSnap = this.attributes_Point_Map.get(type);
                for(int vv : ponitSnap.values()){
                    po += vv;
                }
            }
        }
        return po;
    }
    //設置屬性點數
    public void setAttrPoint(String type, int amount){
        if(this.playerConfig.contains(this.uuidString+".Attributes_Point."+type)){
            this.playerConfig.set(this.uuidString+".Attributes_Point."+type, amount);
        }
    }
    //設置臨時點數
    public void setSnapAttrPoint(String type, String label, String amount){
        if(this.playerConfig.contains(this.uuidString+".Attributes_Point."+type)){
            if(amount == null && this.attributes_Point_Map.containsKey(type)){
                Map<String, Integer> ponitSnap = this.attributes_Point_Map.get(type);
                ponitSnap.remove(label);
                this.attributes_Point_Map.put(type, ponitSnap);
            }else {
                if(this.attributes_Point_Map.containsKey(type)){
                    Map<String, Integer> ponitSnap = this.attributes_Point_Map.get(type);
                    int aa = StringConversion.getInt(this.player, null, 0, amount);
                    ponitSnap.put(label, aa);
                    this.attributes_Point_Map.put(type, ponitSnap);
                }else {
                    Map<String, Integer> ponitSnap = new HashMap<>();
                    int aa = StringConversion.getInt(this.player, null, 0, amount);
                    ponitSnap.put(label, aa);
                    this.attributes_Point_Map.put(type, ponitSnap);
                }
            }
        }
    }
    //------------------------------------------------------------------------//
    //獲得人物狀態屬性
    public String getState(String value){
        String out = "0";
        if(this.attributes_Stats_Map.containsKey(value)){
            out = this.attributes_Stats_Map.get(value);
            if(this.attributes_Stats_Map3.containsKey(value)){
                Map<String, String> attrStateSnap = this.attributes_Stats_Map3.get(value);
                try {
                    double defaultValue = Double.parseDouble(out);
                    for(String vv : attrStateSnap.values()){
                        defaultValue += Double.parseDouble(vv);
                    }
                    out = String.valueOf(defaultValue);
                }catch (NumberFormatException exception){
                    for(String vv : attrStateSnap.values()){
                        out = vv;
                    }
                }
            }
        }
        return out;
    }
    //設定臨時人物狀態屬性
    public void setSnapState(String value, String label, String amount){
        if(this.attributes_Stats_Map.containsKey(value)){
            if(amount == null && this.attributes_Stats_Map3.containsKey(value)){
                Map<String, String> attrStateSnap = this.attributes_Stats_Map3.get(value);
                attrStateSnap.remove(label);
                this.attributes_Stats_Map3.put(value, attrStateSnap);
            }else {
                if(this.attributes_Stats_Map3.containsKey(value)){
                    Map<String, String> attrStateSnap = this.attributes_Stats_Map3.get(value);
                    attrStateSnap.put(label, amount);
                    this.attributes_Stats_Map3.put(value, attrStateSnap);
                }else {
                    Map<String, String> attrStateSnap = new HashMap<>();
                    attrStateSnap.put(label, amount);
                    this.attributes_Stats_Map3.put(value, attrStateSnap);
                }
            }
        }
    }
    //------------------------------------------------------------------------//
    //獲得人物裝備狀態屬性
    public String getEqmState(String value){
        String out = "0";
        if(this.equipment_Stats_Map.containsKey(value)){
            out = this.equipment_Stats_Map.get(value);
            if(this.equipment_Stats_Map3.containsKey(value)){
                Map<String, String> attrStateSnap = this.equipment_Stats_Map3.get(value);
                try {
                    double defaultValue = Double.parseDouble(out);
                    for(String vv : attrStateSnap.values()){
                        defaultValue += Double.parseDouble(vv);
                    }
                    out = String.valueOf(defaultValue);
                }catch (NumberFormatException exception){
                    for(String vv : attrStateSnap.values()){
                        out = vv;
                    }
                }
            }
        }
        return out;
    }
    //設定臨時人物裝備狀態屬性
    public void setSnapEqmState(String value, String label, String amount){
        if(this.equipment_Stats_Map.containsKey(value)){
            if(amount == null && this.equipment_Stats_Map3.containsKey(value)){
                Map<String, String> attrStateSnap = this.equipment_Stats_Map3.get(value);
                attrStateSnap.remove(label);
                this.equipment_Stats_Map3.put(value, attrStateSnap);
            }else {
                if(this.equipment_Stats_Map3.containsKey(value)){
                    Map<String, String> attrStateSnap = this.equipment_Stats_Map3.get(value);
                    attrStateSnap.put(label, amount);
                    this.equipment_Stats_Map3.put(value, attrStateSnap);
                }else {
                    Map<String, String> attrStateSnap = new HashMap<>();
                    attrStateSnap.put(label, amount);
                    this.equipment_Stats_Map3.put(value, attrStateSnap);
                }
            }
        }
    }

    //------------------------------------------------------------------------//
    //獲取技能等級
    public int getSkillLevel(String type){
        if(type.endsWith("_use")){
            if(this.playerConfig.contains(this.uuidString+".Skills."+type.replace("_use", "")+".use")){
                return this.playerConfig.getInt(this.uuidString+".Skills."+type.replace("_use", "")+".use");
            }else {
                return -1;
            }
        }
        if(type.endsWith("_level")){
            if(this.playerConfig.contains(this.uuidString+".Skills."+type.replace("_level", "")+".level")){
                return this.playerConfig.getInt(this.uuidString+".Skills."+type.replace("_level", "")+".level");
            }else {
                return -1;
            }
        }
        return -1;
    }
    public void setSkillLevel(String type, int amount){
        if(type.endsWith("_use")){
            if(this.playerConfig.contains(this.uuidString+".Skills."+type.replace("_use", "")+".use")){
                this.playerConfig.set(this.uuidString+".Skills."+type.replace("_use", "")+".use", amount);
            }

        }
        if(type.endsWith("_level")){
            if(this.playerConfig.contains(this.uuidString+".Skills."+type.replace("_level", "")+".level")){
                this.playerConfig.set(this.uuidString+".Skills."+type.replace("_level", "")+".level", amount);
            }
        }

    }
    //------------------------------------------------------------------------//
    //獲取綁定技能名稱
    public String getBindName(String type){
        if(this.playerConfig.contains(this.uuidString+".Binds."+type+".SkillName")){
            return this.playerConfig.getString(this.uuidString+".Binds."+type+".SkillName");
        }
        return "null";
    }
    //獲取綁定技能等級
    public int getBindUseLevel(String type){
        if(this.playerConfig.contains(this.uuidString+".Binds."+type+".UseLevel")){
            return this.playerConfig.getInt(this.uuidString+".Binds."+type+".UseLevel");
        }
        return -1;
    }
    //設定綁定技能
    public boolean setBind(String type, String skillName, int skillLevel){
        if(this.playerConfig.contains(this.uuidString+".Skills."+skillName+".level")){
            for(int i = 1 ; i < 9 ; i++){
                String key = this.playerConfig.getString(this.uuidString+".Binds."+i+".SkillName");
                if(key.equals(skillName)){
                    return false;
                }
                //player.sendMessage(i+" : "+key);
            }
//            List<String> bindList = new ArrayList<>(this.playerConfig.getConfigurationSection(this.uuidString+".Skills").getKeys(false));
//            bindList.forEach(s -> player.sendMessage(s));
            this.playerConfig.set(this.uuidString+".Binds."+type+".SkillName", skillName);
            this.playerConfig.set(this.uuidString+".Binds."+type+".UseLevel", skillLevel);
            return true;
        }
        return false;
    }

    public void clearBind(int type){
        this.playerConfig.set(this.uuidString+".Binds."+type+".SkillName", "null");
        this.playerConfig.set(this.uuidString+".Binds."+type+".UseLevel", 0);
    }
    //------------------------------------------------------------------------//
    //獲取職業顯示名稱
    public String getClassName(){
        if(this.playerConfig.contains(this.uuidString+".Class_Name")){
            return this.playerConfig.getString(this.uuidString+".Class_Name");
        }
        return "null";
    }
    //------------------------------------------------------------------------//
    //儲存修改後的玩家資料
    public void savePlayerFile(){
        //ConfigMapManager.getFileConfigurationMap().put("Players_"+this.uuidString+".yml", this.playerConfig);
        File file = new File(CustomDisplay.getCustomDisplay().getDataFolder(),"Players/"+this.uuidString+".yml");
        try {
            this.playerConfig.save(file);
        }catch (IOException exception){

        }
        String fileName = "Players_"+uuidString+".yml";
        ConfigMapManager.getFileConfigurationMap().put(fileName, this.playerConfig);
        ConfigMapManager.getFileConfigurationNameMap().put(fileName, fileName);
    }
    //------------------------------------------------------------------------//
    //設定動作
    public void setPlayerActionList() {
        List<String> actionStringList = this.playerConfig.getStringList(this.uuidString+".Action");
        this.player_Action_List_Map.clear();
        ActionManager.class_Action_Map.forEach((s, maps) -> actionStringList.forEach(s1 -> {
            if(s1.equals(s)){
                this.player_Action_List_Map.addAll(maps);
            }
        }));

    }
    //------------------------------------------------------------------------//
    //設定技能動作
    public void setSkillActionList() {
        PlayerDataMethod.setSkillActionList(this.player, this.playerConfig, this.player_Skill_Action_List_Map);
    }
    //------------------------------------------------------------------------//
}
