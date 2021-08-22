package com.daxton.customdisplay.api.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class AutoConfig2 {


    public AutoConfig2(){


    }

    public static void config(){
        String name = CustomDisplay.getCustomDisplay().getClass().getResource("").getPath();
        name = name.substring(name.indexOf("plugins/"),name.indexOf(".jar!"));

        //CustomDisplay.getCustomDisplay().getLogger().info("路徑 "+name+".jar");
        File testFile = new File(name+".jar");
        //File testFile = new File("plugins/CustomDisplay-1.16.13.2.jar");
        try {
            List<String> testList = readZipFile(testFile.toString());

            for(String patch : testList){

                new AutoConfig(patch,patch.replace("resource/","")).get();

            }
        }catch (Exception exception){
            //
        }
        autoNewConfig();
    }

    public static void autoNewConfig(){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        File patch1 = new File(cd.getDataFolder(),"");
        String[] fileNameList1 = patch1.list();
        if (fileNameList1 != null) {
            for(String fileName1 : fileNameList1){
                if(!(fileName1.contains("."))){
                    File patch2 = new File(cd.getDataFolder(),fileName1);
                    String[] fileNameList2 = patch2.list();
                    if (fileNameList2 != null) {
                        for(String fileName2 : fileNameList2){
                            if(fileName2.contains(".yml")){
                                String fileMap = fileName1+"_"+fileName2;

                                File saveFilePatch2 = new File(cd.getDataFolder(),fileName1+"/"+fileName2);
                                FileConfiguration saveFileConfig2 = YamlConfiguration.loadConfiguration(saveFilePatch2);
                                ConfigMapManager.getFileConfigurationMap().put(fileMap, saveFileConfig2);
                                ConfigMapManager.getFileConfigurationNameMap().put(fileMap,fileMap);


                            }else if(!fileName2.contains(".")){
                                File patch3 = new File(cd.getDataFolder(),fileName1+"/"+fileName2);
                                String[] fileNameList3 = patch3.list();
                                if (fileNameList3 != null) {
                                    for(String fileName3 : fileNameList3){
                                        if(fileName3.contains(".yml")){
                                            String fileMap3 = fileName1+"_"+fileName2+"_"+fileName3;

                                            File saveFilePatch3 = new File(cd.getDataFolder(),fileName1+"/"+fileName2+"/"+fileName3);
                                            FileConfiguration saveFileConfig3 = YamlConfiguration.loadConfiguration(saveFilePatch3);
                                            ConfigMapManager.getFileConfigurationMap().put(fileMap3, saveFileConfig3);
                                            ConfigMapManager.getFileConfigurationNameMap().put(fileMap3,fileMap3);


                                        }else if(!(fileName3.contains("."))){

                                            File patch4 = new File(cd.getDataFolder(),fileName1+"/"+fileName2+"/"+fileName3);
                                            String[] fileNameList4 = patch4.list();
                                            if (fileNameList4 != null) {
                                                for(String fileName4 : fileNameList4){

                                                    if(fileName4.contains(".yml")){
                                                        String fileMap4 = fileName1+"_"+fileName2+"_"+fileName3+"_"+fileName4;

                                                        File saveFilePatch4 = new File(cd.getDataFolder(),fileName1+"/"+fileName2+"/"+fileName3+"/"+fileName4);
                                                        FileConfiguration saveFileConfig4 = YamlConfiguration.loadConfiguration(saveFilePatch4);
                                                        ConfigMapManager.getFileConfigurationMap().put(fileMap4, saveFileConfig4);
                                                        ConfigMapManager.getFileConfigurationNameMap().put(fileMap4,fileMap4);

                                                    }else if(!(fileName4.contains("."))){
                                                        File patch5 = new File(cd.getDataFolder(),fileName1+"/"+fileName2+"/"+fileName3+"/"+fileName4);
                                                        String[] fileNameList5 = patch5.list();
                                                        if (fileNameList5 != null) {
                                                            for(String fileName5 : fileNameList5){
                                                                if(fileName5.contains(".yml")){
                                                                    String fileMap5 = fileName1+"_"+fileName2+"_"+fileName3+"_"+fileName4+"_"+fileName5;

                                                                    File saveFilePatch5 = new File(cd.getDataFolder(),fileName1+"/"+fileName2+"/"+fileName3+"/"+fileName4+"/"+fileName5);
                                                                    FileConfiguration saveFileConfig5 = YamlConfiguration.loadConfiguration(saveFilePatch5);
                                                                    ConfigMapManager.getFileConfigurationMap().put(fileMap5, saveFileConfig5);
                                                                    ConfigMapManager.getFileConfigurationNameMap().put(fileMap5,fileMap5);
                                                                }
                                                            }
                                                        }


                                                    }

                                                }
                                            }
                                        }



                                    }
                                }
                            }


                        }
                    }


                }


            }
        }

    }
    //讀取jar內的檔案名稱
    public static List<String> readZipFile(String file) throws Exception {
        ZipFile zf = new ZipFile(file);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze;
        List<String> stringList = new ArrayList<>();
        while ((ze = zin.getNextEntry()) != null) {
            if (!ze.isDirectory()) {
                long size = ze.getSize();
                if(ze.getName().contains(".yml") && !(ze.getName().contains("plugin"))){
                    stringList.add(ze.getName());
                }
                if (size > 0) {
                    BufferedReader br = new BufferedReader( new InputStreamReader(zf.getInputStream(ze)));
//                    String line;
//                    while ((line = br.readLine()) != null) {
//                    }
                    br.close();
                }
            }
        }
        zin.closeEntry();
        return stringList;
    }



}
