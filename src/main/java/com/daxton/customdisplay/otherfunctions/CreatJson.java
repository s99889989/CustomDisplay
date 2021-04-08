package com.daxton.customdisplay.otherfunctions;

import com.daxton.customdisplay.CustomDisplay;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreatJson {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public CreatJson(){




    }

//    public void createMain(){
//
//        File json_file = new File(cd.getDataFolder(),"Json/coal.json");
//
//
//        try {
//            if(!json_file.exists()){
//                json_file.createNewFile();
//                BufferedWriter out = new BufferedWriter(new FileWriter(json_file));
//                out.write("{");
//                out.newLine();
//                out.write("\"parent\": \"item/handheld\",");
//                out.newLine();
//                out.write("\"textures\": {");
//                out.newLine();
//                out.write("\"layer0\": \"item/coal\"");
//                out.newLine();
//                out.write("},");
//                out.newLine();
//                out.write("\"overrides\": [");
//                out.newLine();
//                for(int i = 1 ; i <=500 ;i++){
//                    if(i == 500){
//                        out.write("{\"predicate\": {\"custom_model_data\":");
//                        out.write(String.valueOf(i));
//                        out.write("}, \"model\": \"item/coal/");
//                        out.write(String.valueOf(i));
//                        out.write("\"}");
//                        out.newLine();
//                    }else {
//                        out.write("{\"predicate\": {\"custom_model_data\":");
//                        out.write(String.valueOf(i));
//                        out.write("}, \"model\": \"item/coal/");
//                        out.write(String.valueOf(i));
//                        out.write("\"},");
//                        out.newLine();
//                    }
//
//                }
//                out.write("]");
//                out.newLine();
//                out.write("}");
//                out.close();
//            }
//        }catch (Exception exception){
//            exception.printStackTrace();
//        }
//
//
//
//    }
//
//    public void createAll(){
//        for(int i = 301 ; i <=500 ;i++){
//            File json_file = new File(cd.getDataFolder(),"Json/"+i+".json");
//            try {
//                if(!json_file.exists()){
//                    json_file.createNewFile();
//                    BufferedWriter out = new BufferedWriter(new FileWriter(json_file));
//                    out.write("{");
//                    out.newLine();
//                    out.write("    \"parent\": \"item/handheld\",");
//                    out.newLine();
//                    out.write("    \"textures\": {");
//                    out.newLine();
//                    out.write("        \"layer0\": \"item/coal/"+i+"\"");
//                    out.newLine();
//                    out.write("    }");
//                    out.newLine();
//                    out.write("}");
//                    out.close();
//                }
//            }catch (Exception exception){
//                exception.printStackTrace();
//            }
//        }
//    }


}
