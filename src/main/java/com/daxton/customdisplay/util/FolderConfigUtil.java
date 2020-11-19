package com.daxton.customdisplay.util;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.FileUtil;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.apache.commons.io.FileUtils.getFile;

public class FolderConfigUtil{

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

    public FolderConfigUtil(){
        try{
        URI uri = getClass().getResource("/resource").toURI(); ///resource
        String uriString = uri.toString().replace("jar:file:/", "").replace(":/", ":\\").replace("/", "\\").replace("!", "").replace("\\resource", "");
        List<String> list = readZipFile(uriString);
        for (String st : list) {
                File configFile = new File(cd.getDataFolder(), st);
            String stt = st.replace("resource/","");
            File finalConfigFile = new File(cd.getDataFolder(), stt);
                if (!finalConfigFile.exists()) {
                    cd.saveResource(st,false);
                }
                FileConfiguration config = YamlConfiguration.loadConfiguration(finalConfigFile);
                String fileMap = st.replace("resource/","").replace("/", "_");
                ConfigMapManager.getFileConfigurationMap().put(fileMap, config);
                ConfigMapManager.getFileConfigurationNameMap().put(fileMap,fileMap);

            }
        }catch (Exception exception){
        System.out.println(exception.toString());
        }



    }

//    private void saveFiles() {
//        try (var is = new ZipInputStream(new FileInputStream(getFile()))) {
//            ZipEntry entry;
//            while ((entry = is.getNextEntry()) != null) {
//                if (entry.isDirectory() || !entry.getName().startsWith("resource")) continue;
//                var input = getClass().getResourceAsStream("/"+entry.getName());
//                var f = new File(cd.getDataFolder(), entry.getName().replace("\\resource", ""));
//                cd.getLogger().info("copying "+entry.getName()+" into "+f.getPath());
//                FileUtils.copyInputStreamToFile(input, f);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

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
