package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class CoreAttribute {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String uuidString = "";

    private static Map<String,Double> attribute = new HashMap<>();

    private static Map<String,Double> Health_Regeneration = new HashMap<>();
    private static Map<String,Double> Max_Mana = new HashMap<>();
    private static Map<String,Double> Mana_Regeneration = new HashMap<>();
    private static Map<String,Double> Dodge_Rate = new HashMap<>();
    private static Map<String,Double> Hit_Rate = new HashMap<>();
    private static Map<String,Double> Critical_Strike_Chance = new HashMap<>();
    private static Map<String,Double> Critical_Strike_Protection = new HashMap<>();
    private static Map<String,Double> Critical_Strike_Power = new HashMap<>();
    private static Map<String,Double> Critical_Strike_Reduction = new HashMap<>();


    public CoreAttribute(){



    }

    public CoreAttribute(Player player){
        uuidString = player.getUniqueId().toString();
    }

    public void setDefault(){
        File file = new File(cd.getDataFolder(),"Class/CustomCore.yml");
        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
        Health_Regeneration.put(uuidString+"Health_Regeneration.first",fileConfig.getDouble("CoreAttribute.Health_Regeneration.amount"));
        Max_Mana.put(uuidString+"Health_Regeneration.first",fileConfig.getDouble("CoreAttribute.Max_Mana.amount"));
        Mana_Regeneration.put(uuidString+"Health_Regeneration.first",fileConfig.getDouble("CoreAttribute.Mana_Regeneration.amount"));
        Dodge_Rate.put(uuidString+"Health_Regeneration.first",fileConfig.getDouble("CoreAttribute.Dodge_Rate.amount"));
        Hit_Rate.put(uuidString+"Health_Regeneration.first",fileConfig.getDouble("CoreAttribute.Hit_Rate.amount"));
        Critical_Strike_Chance.put(uuidString+"Health_Regeneration.first",fileConfig.getDouble("CoreAttribute.Critical_Strike_Chance.amount"));
        Critical_Strike_Protection.put(uuidString+"Health_Regeneration.first",fileConfig.getDouble("CoreAttribute.Critical_Strike_Protection.amount"));
        Critical_Strike_Power.put(uuidString+"Health_Regeneration.first",fileConfig.getDouble("CoreAttribute.Critical_Strike_Power.amount"));
        Critical_Strike_Reduction.put(uuidString+"Health_Regeneration.first",fileConfig.getDouble("CoreAttribute.Critical_Strike_Reduction.amount"));

    }

    public void setAttribute(String key,String operation, double number) {
        if(key.contains("Health_Regeneration")){
            double firstNumber = Health_Regeneration.get(uuidString+"Health_Regeneration.first");
            double newNumber = 0;
            if(operation.contains("ADD_NUMBER")){
                Health_Regeneration.put(uuidString+"Health_Regeneration.EntityStats",number);
            }

        }


    }

    public double getAttribute(String key){

        double value = 0;
        if(key.contains("Health_Regeneration")){
            Iterator keys = Health_Regeneration.keySet().iterator();
            while(keys.hasNext()){
                String key2 = (String)keys.next();
                if(key2.contains(uuidString+"Health_Regeneration")){
                    value = value + Health_Regeneration.get(key2);
                }
            }
        }
        return value;
    }



}
