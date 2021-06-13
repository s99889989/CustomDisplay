package com.daxton.customdisplay.otherfunctions;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class CreateFontJson {

    public static void createMain(){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        Map<Integer, String> stringMap = new HashMap<>();
        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("config.yml");
        String key = fileConfiguration.getString("CMDJosonCreate.key");
        String H2 = "4105";
        if(key != null){
            H2 = key;
        }

        int D2 = Integer.parseInt(H2,16);

        int k = 1;
        for(int i = D2 ; i < D2+25 ; i++){
            stringMap.put(k, Integer.toHexString(i).toUpperCase());
            //System.out.println("Number: "+Integer.toHexString(i));
            k++;
        }

        File json_file = new File(cd.getDataFolder(),"Json/default.json");
        try {
            if(json_file.exists()){
                json_file.delete();
            }
            if(!json_file.exists()){
                json_file.createNewFile();
                BufferedWriter out = new BufferedWriter(new FileWriter(json_file));
                out.write("\t\t\t\"\\u"+stringMap.get(1)+"\\u"+stringMap.get(2)+"\\u"+stringMap.get(3)+"\\u"+stringMap.get(4)+"\\u"+stringMap.get(5)+"\",");
                out.newLine();
                out.write("\t\t\t\"\\u"+stringMap.get(6)+"\\u"+stringMap.get(7)+"\\u"+stringMap.get(8)+"\\u"+stringMap.get(9)+"\\u"+stringMap.get(10)+"\",");
                out.newLine();
                out.write("\t\t\t\"\\u"+stringMap.get(11)+"\\u"+stringMap.get(12)+"\\u"+stringMap.get(13)+"\\u"+stringMap.get(14)+"\\u"+stringMap.get(15)+"\",");
                out.newLine();
                out.write("\t\t\t\"\\u"+stringMap.get(16)+"\\u"+stringMap.get(17)+"\\u"+stringMap.get(18)+"\\u"+stringMap.get(19)+"\\u"+stringMap.get(20)+"\",");
                out.newLine();
                out.write("\t\t\t\"\\u"+stringMap.get(21)+"\\u"+stringMap.get(22)+"\\u"+stringMap.get(23)+"\\u"+stringMap.get(24)+"\\u"+stringMap.get(25)+"\"]");
                out.close();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }



    }

}
