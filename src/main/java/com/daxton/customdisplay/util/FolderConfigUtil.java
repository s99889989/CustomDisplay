package com.daxton.customdisplay.util;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class FolderConfigUtil{

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public FolderConfigUtil() {
    try{
        cd.getLogger().info("文件讀取生成");
        URI uri = getClass().getResource("/resource").toURI(); ///resource
        String uriString = uri.toString().replace("jar:file:/", "").replace(":/", ":\\").replace("/", "\\").replace("!", "").replace("\\resource", "");
        List<String> list = readZipFile(uriString);
        for (String st : list) {
            cd.getLogger().info(st.replace("resource/","").replace("/", "_").replace(".yml",""));
                File configFile = new File(cd.getDataFolder(), st);
                if (!configFile.exists()) {
                    cd.saveResource(st, false);
                }
                FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
                String fileMap = st.replace("resource/","").replace("/", "_");
                ConfigMapManager.getFileConfigurationMap().put(fileMap, config);
                ConfigMapManager.getStringStringMap().put(fileMap,fileMap);
        }
    }catch (Exception exception){
      System.out.println(exception.toString());
    }
        cd.getLogger().info("Map");
    for(String string : ConfigMapManager.getStringStringMap().values()){
        cd.getLogger().info(string);
    }

    }

    public void test(){
//        try{
//
//            URI uri = getClass().getResource("/resource").toURI();
//            String uriString = uri.toString();
//            player.sendMessage(uriString.replace(":/",":\\").replace("/","\\"));
//            player.sendMessage( new File(cd.getDataFolder(),"").toString());
//            //"jar:file:\\C:\\Users\\Gary\\Desktop\\1.16.4\\plugins\\CustomDisplay-1.9.1.jar!\\resource"
//
//            configFile = new File(uri);
//            configFile2 = new File(cd.getDataFolder(),"");
//            player.sendMessage(configFile.toString());
//
//            readZipFile("C:\\Users\\Gary\\Desktop\\1.16.4\\plugins\\CustomDisplay-1.9.1.jar",player);
//
//        }catch (Exception exception){
//            player.sendMessage(exception.toString());
//        }
    }

    public static List<String> readZipFile(String file) throws Exception {
        ZipFile zf = new ZipFile(file);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze;
        List<String> stringList = new ArrayList<>();
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.isDirectory()) {
            } else {
                //System.err.println("file - " + ze.getName() + " : "+ ze.getSize() + " bytes");
                long size = ze.getSize();
                if(ze.getName().contains(".yml") && !(ze.getName().contains("plugin"))){
                    stringList.add(ze.getName());
                }
                if (size > 0) {
                    BufferedReader br = new BufferedReader( new InputStreamReader(zf.getInputStream(ze)));
                    String line;
                    while ((line = br.readLine()) != null) {
                        //System.out.println(line);
                    }
                    br.close();
                }
                //System.out.println();
            }
        }
        zin.closeEntry();
        return stringList;
    }


}
