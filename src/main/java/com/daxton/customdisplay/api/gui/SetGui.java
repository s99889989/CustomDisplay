package com.daxton.customdisplay.api.gui;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.Config;
import com.daxton.customdisplay.manager.player.EditorGUIManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

public class SetGui {


    public static void setGuiName(){

        Map<String, FileConfiguration> configTypeMap = Config.getTypeConfigMap2("Gui_Menu_");
        EditorGUIManager.custom_Inventory_Name_Map.clear();
        configTypeMap.forEach((typeString, fileConfiguration) -> {
            EditorGUIManager.custom_Inventory_Name_Map.put(typeString, fileConfiguration);
            //CustomDisplay.getCustomDisplay().getLogger().info(typeString);
        });

    }

    public static void setButtomMap(){

        Map<String, FileConfiguration> configTypeMap = Config.getTypeConfigMap2("Gui_Buttom_");
        EditorGUIManager.custom_Inventory_Buttom_Map.clear();
        configTypeMap.forEach((typeString, fileConfiguration) -> {
            EditorGUIManager.custom_Inventory_Buttom_Map.put(typeString, fileConfiguration);
            //CustomDisplay.getCustomDisplay().getLogger().info(typeString);
        });

    }

}
