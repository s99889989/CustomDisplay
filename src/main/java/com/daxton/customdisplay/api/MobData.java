package com.daxton.customdisplay.api;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class MobData {

    private FileConfiguration mobConfig;

    private String mobID;

    private String faction = "";

    public Map<String, Map<String,String>> attributes_Stats_Map = new HashMap<>();

    public MobData(){

    }

    public MobData(String mobID, FileConfiguration mobConfig){
        this.mobID = mobID;
        this.mobConfig = mobConfig;
        //設定派系
        setFaction();
    }
    //獲取固定屬性
    public String getCustomState(String key){
        String state = "0";
        if(this.mobConfig.contains(this.mobID+".Custom."+key)){
            state = this.mobConfig.getString(this.mobID+".Custom."+key);
            if(attributes_Stats_Map.containsKey(key)){
                try {
                    double stateNumber = 0;
                    if(state != null){
                        stateNumber = Double.parseDouble(state);
                    }
                    for(String valueString : attributes_Stats_Map.get(key).values()){
                        stateNumber += Double.parseDouble(valueString);
                    }
                    state = String.valueOf(stateNumber);
                }catch (NumberFormatException exception){
                    attributes_Stats_Map.get(key);
                    for(String valueString : attributes_Stats_Map.get(key).values()){
                        state = valueString;
                    }
                }
            }
        }
        return state;
    }
    //設置臨時屬性
    public void setCustomState(String key, String label, String amount){

        if(this.mobConfig.contains(this.mobID+".Custom."+key)){
            if(amount == null){
                if(attributes_Stats_Map.containsKey(key)){
                    Map<String, String> stateMap = attributes_Stats_Map.get(key);
                    stateMap.remove(label);
                }
            }else {
                if(attributes_Stats_Map.containsKey(key)){
                    Map<String, String> stateMap = attributes_Stats_Map.get(key);
                    stateMap.put(label, amount);
                    attributes_Stats_Map.put(key, stateMap);
                }else {
                    Map<String, String> stateMap = new HashMap<>();
                    stateMap.put(label, amount);
                    attributes_Stats_Map.put(key, stateMap);
                }
            }
        }

    }
    //獲取派系
    public String getFaction(){
        return this.faction;
    }
    //設定派系
    public void setFaction() {
        this.faction = "";
        if(this.mobConfig.contains(this.mobID+".Faction")){
            this.faction = this.mobConfig.getString(this.mobID+".Faction");
        }
    }
}
