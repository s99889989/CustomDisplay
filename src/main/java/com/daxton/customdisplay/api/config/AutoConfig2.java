package com.daxton.customdisplay.api.config;

import com.daxton.customdisplay.CustomDisplay;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class AutoConfig2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public AutoConfig2(){
        File testFile = new File("plugins/CustomDisplay-1.11.17.jar");
        try {
            List<String> testList = readZipFile(testFile.toString());
            for(String patch : testList){
                new AutoConfig(patch,patch.replace("resource/","")).get();
                //cd.getLogger().info("路徑:　"+testS);
            }
        }catch (Exception exception){

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
