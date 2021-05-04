package com.daxton.customdisplay.task.condition.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class EntityTypeList {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";

    private String entityType = "";
    private String aims = "";

    public EntityTypeList(){

    }

    public EntityTypeList(LivingEntity self,LivingEntity target,String firstString,String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;
        setOther();
    }

    public void setOther(){
        for(String string : new StringFind().getStringList(firstString)){

            if(string.toLowerCase().contains("@=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    aims = strings[1];
                }
            }

            if(string.toLowerCase().contains("entitytypelist=")){
                String[] strings = string.replace(" ","").split("=");
                entityType = ConversionMain.valueOf(self,target,strings[1]);
            }
        }
    }

    public boolean get(){

        boolean b = false;
        List<String> stringList2 = ConfigMapManager.getFileConfigurationMap().get("Character_System_EntityTypeList.yml").getStringList(entityType+".entityTypeList");

        for(String string : stringList2){

            if(aims.toLowerCase().contains("target")){
                if(string.toLowerCase().contains(target.getType().toString().toLowerCase())){
                    b = true;
                }
            }else {
                if(string.toLowerCase().contains(self.getType().toString().toLowerCase())){
                    b = true;
                }
            }

        }
        return b;
    }
}
