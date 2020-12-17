package com.daxton.customdisplay.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.AutoConfig;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    CustomDisplay cd;

    public FileConfiguration config;

    public FileConfiguration language;


    public ConfigManager(CustomDisplay plugin){
        cd = plugin;



        config = new AutoConfig("resource/config.yml","config.yml").get();
        language = new AutoConfig("resource/Language/"+config.getString("Language")+".yml","Language/"+config.getString("Language")+".yml").get();

        new AutoConfig("resource/Players/Default.yml","Players/Default.yml").get();
        new AutoConfig("resource/Players/s99889989.yml","Players/s99889989.yml").get();

        new AutoConfig("resource/Mobs/Default.yml","Mobs/Default.yml").get();

        new AutoConfig("resource/Permission/ExamplePermission.yml","Permission/ExamplePermission.yml").get();

        new AutoConfig("resource/Actions/ExampleActionBar.yml","Actions/ExampleActionBar.yml").get();
        new AutoConfig("resource/Actions/ExampleBossBar.yml","Actions/ExampleBossBar.yml").get();
        new AutoConfig("resource/Actions/ExampleHD.yml","Actions/ExampleHD.yml").get();
        new AutoConfig("resource/Actions/ExampleMessage.yml","Actions/ExampleMessage.yml").get();
        new AutoConfig("resource/Actions/ExampleMythic.yml","Actions/ExampleMythic.yml").get();
        new AutoConfig("resource/Actions/ExampleName.yml","Actions/ExampleName.yml").get();
        new AutoConfig("resource/Actions/ExampleParticle.yml","Actions/ExampleParticle.yml").get();
        new AutoConfig("resource/Actions/ExampleTitle.yml","Actions/ExampleTitle.yml").get();

        new AutoConfig("resource/Character/ExampleCharacter.yml","Character/ExampleCharacter.yml").get();
        new AutoConfig("resource/Character/ExampleSelf.yml","Character/ExampleSelf.yml").get();
        new AutoConfig("resource/Character/ExampleTarget.yml","Character/ExampleTarget.yml").get();
        new AutoConfig("resource/Character/System/EntityTypeList.yml","Character/System/EntityTypeList.yml").get();

        new AutoConfig().actionConfig();
        new AutoConfig().characterConfig();
        new AutoConfig().playersConfig();
        new AutoConfig().permissionConfig();



    }

}
