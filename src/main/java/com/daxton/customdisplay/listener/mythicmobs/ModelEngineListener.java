package com.daxton.customdisplay.listener.mythicmobs;

import com.daxton.customdisplay.CustomDisplay;


import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;


public class ModelEngineListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMythicMobSpawn(MythicMobSpawnEvent event){
        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                ModeledEntity modeledEntity = ModelEngineAPI.api.getModelManager().getModeledEntity(event.getEntity().getUniqueId());
                if (modeledEntity != null) {
                    for(ActiveModel activeModel : modeledEntity.getAllActiveModel().values()){
                        String modelID = activeModel.getModelId();
                        double eyeHight = modeledEntity.getEye();
                        double hitBoxHight = modeledEntity.getHeight();
                        double hitBoxWidth = modeledEntity.getWidth();
//                        if(!(modelID.isEmpty())){
//                            cd.getLogger().info(modelID+" : "+eyeHight+" : "+hitBoxHight+" : "+hitBoxWidth);
//                        }
                    }
                }
            }
        };
        bukkitRunnable.runTaskLater(cd,20);

    }




//    @EventHandler(priority = EventPriority.LOW)
//    public void onEntityDamage(EntityDamageEvent event) {
//        String uuidString = event.getEntity().getUniqueId().toString();
//        ModeledEntity modeledEntity = ModelEngineAPI.api.getModelManager().getModeledEntity(event.getEntity().getUniqueId());
//        if (modeledEntity != null) {
//
//            for(ActiveModel activeModel : modeledEntity.getAllActiveModel().values()){
//                String modelID = activeModel.getModelId();
//                double eyeHight = modeledEntity.getEye();
//                double hitBoxHight = modeledEntity.getHeight();
//                double hitBoxWidth = modeledEntity.getWidth();
//                if(!(modelID.isEmpty())){
//                    MobManager.modelengine_Map.put(uuidString,modelID);
//                    MobManager.modelengine_Map.put(uuidString+"eyeHight",String.valueOf(eyeHight));
//                    MobManager.modelengine_Map.put(uuidString+"hitBoxHight",String.valueOf(hitBoxHight));
//                    MobManager.modelengine_Map.put(uuidString+"hitBoxWidth",String.valueOf(hitBoxWidth));
//                    cd.getLogger().info(modelID+" : "+eyeHight+" : "+hitBoxHight+" : "+hitBoxWidth);
//                }
//
//            }
//
//        }
//    }

}
