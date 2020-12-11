package com.daxton.customdisplay.api.character;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class FolderConfigUtil{

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public FolderConfigUtil(){
        try{
        URI uri = getClass().getResource("/resource").toURI();
        String uriString = uri.toString().replace("jar:file:/", "").replace(":/", ":\\").replace("/", "\\").replace("!", "").replace("\\resource", "");
        List<String> list = readZipFile(uriString);
        for (String st : list) {
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

    public static List<String> readZipFile(String file) throws Exception {
        ZipFile zf = new ZipFile(file);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze;
        List<String> stringList = new ArrayList<>();
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.isDirectory()) {
            } else {
                long size = ze.getSize();
                if(ze.getName().contains(".yml") && !(ze.getName().contains("plugin"))){
                    stringList.add(ze.getName());
                }
                if (size > 0) {
                    BufferedReader br = new BufferedReader( new InputStreamReader(zf.getInputStream(ze)));
                    String line;
                    while ((line = br.readLine()) != null) {
                    }
                    br.close();
                }
            }
        }
        zin.closeEntry();
        return stringList;
    }


}
