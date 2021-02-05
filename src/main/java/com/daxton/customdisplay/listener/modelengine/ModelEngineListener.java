package com.daxton.customdisplay.listener.modelengine;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.MobManager;
import com.ticxo.modelengine.api.events.ModeledEntityLandEvent;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import com.ticxo.modelengine.api.model.component.StateProperty;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ModelEngineListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    /**模型生物落地時**/
    @EventHandler
    public void onME(ModeledEntityLandEvent event){

        ModeledEntity modeledEntity = event.getModeledEntity();
        String uuidString = event.getEntity().getUniqueId().toString();
        for(ActiveModel activeModel : modeledEntity.getModels().values()){
            String modelID = activeModel.getModelId();
            double eyeHight = modeledEntity.getEyeHeight(modelID);
            double hitBoxHight = modeledEntity.getSize(modelID).getHeight();
            double hitBoxWidth = modeledEntity.getSize(modelID).getWidth();
            MobManager.modelengine_Map.put(uuidString,modelID);
            MobManager.modelengine_Map.put(uuidString+"eyeHight",String.valueOf(eyeHight));
            MobManager.modelengine_Map.put(uuidString+"hitBoxHight",String.valueOf(hitBoxHight));
            MobManager.modelengine_Map.put(uuidString+"hitBoxWidth",String.valueOf(hitBoxWidth));


            //cd.getLogger().info(event.getEntity().getUniqueId().toString()+" : "+modelID+" : "+eyeHight+" : "+hitBoxHight+" : "+hitBoxWidth);
        }


    }

}
