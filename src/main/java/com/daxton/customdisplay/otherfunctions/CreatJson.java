package com.daxton.customdisplay.otherfunctions;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreatJson {


    public CreatJson(){


    }

    public static void createMain(){

        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("config.yml");
        Boolean en = fileConfiguration.getBoolean("CMDJosonCreate.enable");
        String item = fileConfiguration.getString("CMDJosonCreate.item");
        int amount = fileConfiguration.getInt("CMDJosonCreate.amount");
        if(en){
            File file = new File(cd.getDataFolder(),"Json");
            if(!file.exists()){
                file.mkdir();
            }
            File json_file = new File(cd.getDataFolder(),"Json/"+item+".json");

            try {
                if(!json_file.exists()){
                    json_file.createNewFile();
                    BufferedWriter out = new BufferedWriter(new FileWriter(json_file));
                    out.write("{");
                    out.newLine();
                    out.write("\"parent\": \"item/handheld\",");
                    out.newLine();
                    out.write("\"textures\": {");
                    out.newLine();
                    out.write("\"layer0\": \"item/"+item+"\"");
                    out.newLine();
                    out.write("},");
                    out.newLine();
                    out.write("\"overrides\": [");
                    out.newLine();
                    for(int i = 1 ; i <= amount ;i++){
                        if(i == amount){
                            out.write("{\"predicate\": {\"custom_model_data\":");
                            out.write(String.valueOf(i));
                            out.write("}, \"model\": \"item/"+item+"/");
                            out.write(String.valueOf(i));
                            out.write("\"}");
                            out.newLine();
                        }else {
                            out.write("{\"predicate\": {\"custom_model_data\":");
                            out.write(String.valueOf(i));
                            out.write("}, \"model\": \"item/"+item+"/");
                            out.write(String.valueOf(i));
                            out.write("\"},");
                            out.newLine();
                        }

                    }
                    out.write("]");
                    out.newLine();
                    out.write("}");
                    out.close();
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }


        }



    }

    public static void createAll(){


        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("config.yml");
        Boolean en = fileConfiguration.getBoolean("CMDJosonCreate.enable");
        String item = fileConfiguration.getString("CMDJosonCreate.item");
        int amount = fileConfiguration.getInt("CMDJosonCreate.amount");
        if(en){
            CustomDisplay cd = CustomDisplay.getCustomDisplay();
            File file = new File(cd.getDataFolder(),"Json/"+item);
            if(!file.exists()){
                file.mkdir();
            }
            for(int i = 1 ; i <=amount ;i++){
                File json_file = new File(cd.getDataFolder(),"Json/"+item+"/"+i+".json");
                try {
                    if(!json_file.exists()){
                        json_file.createNewFile();
                        BufferedWriter out = new BufferedWriter(new FileWriter(json_file));
                        out.write("{");
                        out.newLine();
                        out.write("    \"parent\": \"item/handheld\",");
                        out.newLine();
                        out.write("    \"textures\": {");
                        out.newLine();
                        out.write("        \"layer0\": \"item/"+item+"/"+i+"\"");
                        out.newLine();
                        out.write("    }");
                        out.newLine();
                        out.write("}");
                        out.close();
                    }
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }

    }


}
