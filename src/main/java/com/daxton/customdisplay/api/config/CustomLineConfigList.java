package com.daxton.customdisplay.api.config;

import java.util.ArrayList;
import java.util.List;

public class CustomLineConfigList {

    public CustomLineConfigList(){

    }

    public List<CustomLineConfig> valueOf(List<String> stringList){
        List<CustomLineConfig> customLineConfigList = new ArrayList<>();
        for(String s : stringList){
            customLineConfigList.add(new CustomLineConfig(s));
        }
        return customLineConfigList;
    }

}
