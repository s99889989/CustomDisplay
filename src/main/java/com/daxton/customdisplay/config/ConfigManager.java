package com.daxton.customdisplay.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.AutoConfig;
import com.daxton.customdisplay.api.config.AutoConfig2;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

public class ConfigManager {

    CustomDisplay cd;

    public FileConfiguration config;

    public FileConfiguration language;


    public ConfigManager(CustomDisplay plugin){
        cd = plugin;


//        File actions_file = new File(cd.getDataFolder(),"Actions");
//        if(!actions_file.exists()){
//            actions_file.mkdir();
//        }

        config = new AutoConfig("resource/config.yml","config.yml").get();
        language = new AutoConfig("resource/Language/"+config.getString("Language")+".yml","Language/"+config.getString("Language")+".yml").get();

        File players_file = new File(cd.getDataFolder(),"Players");
        if(!players_file.exists()){
            players_file.mkdir();
        }
        File mobs_file = new File(cd.getDataFolder(),"Mobs");
        if(!mobs_file.exists()){
            mobs_file.mkdir();
        }

        new AutoConfig2();


//        new AutoConfig("resource/Class/Action/Default.yml","Class/Action/Default.yml").get();
//        new AutoConfig("resource/Class/Action/預設.yml","Class/Action/預設.yml").get();
//
//        new AutoConfig("resource/Class/Level/base.yml","Class/Level/base.yml").get();
//        new AutoConfig("resource/Class/Level/job.yml","Class/Level/job.yml").get();
//
//        new AutoConfig("resource/Class/Main/Default.yml","Class/Main/Default.yml").get();
//
//
//        new AutoConfig("resource/Permission/ExamplePermission.yml","Permission/ExamplePermission.yml").get();
//
//        new AutoConfig("resource/Actions/ExampleActionBar.yml","Actions/ExampleActionBar.yml").get();
//        new AutoConfig("resource/Actions/ExampleBossBar.yml","Actions/ExampleBossBar.yml").get();
//        new AutoConfig("resource/Actions/ExampleHD.yml","Actions/ExampleHD.yml").get();
//        new AutoConfig("resource/Actions/ExampleLoggerInfo.yml","Actions/ExampleLoggerInfo.yml").get();
//        new AutoConfig("resource/Actions/ExampleMessage.yml","Actions/ExampleMessage.yml").get();
//        new AutoConfig("resource/Actions/ExampleMythic.yml","Actions/ExampleMythic.yml").get();
//        new AutoConfig("resource/Actions/ExampleName.yml","Actions/ExampleName.yml").get();
//        new AutoConfig("resource/Actions/ExampleParticle.yml","Actions/ExampleParticle.yml").get();
//        new AutoConfig("resource/Actions/ExampleTitle.yml","Actions/ExampleTitle.yml").get();
//
//        new AutoConfig("resource/Character/ExampleCharacter.yml","Character/ExampleCharacter.yml").get();
//        new AutoConfig("resource/Character/ExampleSelf.yml","Character/ExampleSelf.yml").get();
//        new AutoConfig("resource/Character/ExampleTarget.yml","Character/ExampleTarget.yml").get();
//        new AutoConfig("resource/Character/System/EntityTypeList.yml","Character/System/EntityTypeList.yml").get();
//
//        new AutoConfig().actionConfig();
//        new AutoConfig().characterConfig();
//        //new AutoConfig().playersConfig();
//        new AutoConfig().permissionConfig();



    }

}
