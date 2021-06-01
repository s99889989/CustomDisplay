package com.daxton.customdisplay.task.action.location;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.ActionManager;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import com.ticxo.modelengine.api.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Silverfish;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CDModelEngine2 {

    public static void setGuise(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID, Location inputLocation){
        if (Bukkit.getServer().getPluginManager().getPlugin("ModelEngine") != null){
            return;
        }

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String mark = actionMapHandle.getString(new String[]{"mark","mk"},"0");

        //模型ID
        String modelid = actionMapHandle.getString(new String[]{"m", "mid", "model", "modelid"}, null);

        //模型姿態
        String[] state = actionMapHandle.getStringList(new String[]{"s","state"},new String[]{"idle","0","1","1"},"\\|",4);
        String stateID = state[0];
        int lerpIn = 0;
        int lerpOut = 1;
        double speed = 1;
        try {
            lerpIn = Integer.parseInt(state[1]);
            lerpOut = Integer.parseInt(state[2]);
            speed = Double.parseDouble(state[3]);
        }catch (NumberFormatException exception){
            lerpIn = 0;
            lerpOut = 1;
            speed = 1;
        }

        //是否要移動
        boolean teleport = actionMapHandle.getBoolean(new String[]{"teleport","tp"}, false);
        //是否要刪除
        boolean delete = actionMapHandle.getBoolean(new String[]{"delete","d"}, false);

        if(ActionManager.modelEngine_Map.get(taskID+mark) == null){
            Location location;
            if(inputLocation != null){
                location = actionMapHandle.getLocation(inputLocation);
            }else {
                location = actionMapHandle.getLocation(null);
            }
            if(location != null){
                LivingEntity entity = (Silverfish) location.getWorld().spawnEntity(location, EntityType.SILVERFISH);

                entity.setCollidable(false);
                entity.setAI(false);
                entity.setCustomName("ModleEngine");
                ModeledEntity modeledEntity = ModelEngineAPI.api.getModelManager().getModeledEntity(entity.getUniqueId());
                if (modeledEntity == null) {
                    modeledEntity = ModelEngineAPI.api.getModelManager().createModeledEntity(entity);
                }
                float width = modeledEntity.getWidth();
                float height = modeledEntity.getHeight();
                float eye = modeledEntity.getEye();
                modeledEntity.overrideHitbox(width, height, eye);
                ///////
                ConfigManager.AnimationMode animationMode = ConfigManager.AnimationMode.get(stateID);

                ActiveModel activeModel = ModelEngineAPI.api.getModelManager().createActiveModel(modelid);
                activeModel.setAnimationMode(animationMode);
                activeModel.setDamageTint(false);

                modeledEntity.addActiveModel(activeModel);
                modeledEntity.detectPlayers();
                modeledEntity.setInvisible(true);

                ActionManager.modelEngine_Entity_Map.put(taskID+mark, entity);
                ActionManager.modelEngine_Map.put(taskID+mark, modeledEntity);
                ActionManager.modelEngine_Modelid_Map.put(taskID+mark, modelid);
                ActionManager.modelEngine_Stateid_Map.put(taskID+mark, stateID);
                ActionManager.modelEngine_Location_Map.put(taskID+mark, location);
            }

        }


        if(ActionManager.modelEngine_Map.get(taskID+mark) != null){
            ModeledEntity modeledEntity = ActionManager.modelEngine_Map.get(taskID+mark);

            Entity entity =  ActionManager.modelEngine_Entity_Map.get(taskID+mark);

            Location location;
            if(inputLocation != null){
                location = actionMapHandle.getLocation(inputLocation);
            }else {
                location = actionMapHandle.getLocation(ActionManager.modelEngine_Location_Map.get(taskID+mark));
            }

            if(!ActionManager.modelEngine_Stateid_Map.get(taskID+mark).equals(stateID)){
                ActiveModel activeModel = modeledEntity.getActiveModel(ActionManager.modelEngine_Modelid_Map.get(taskID+mark));
                activeModel.addState(stateID, lerpIn, lerpOut, speed);
                modeledEntity.addActiveModel(activeModel);
                modeledEntity.detectPlayers();
                ActionManager.modelEngine_Stateid_Map.put(taskID+mark, stateID);
            }

            if(teleport){
                entity.teleport(location);
                ActionManager.modelEngine_Location_Map.put(taskID+mark, location);
            }

            if(delete){
                modeledEntity.getAllActiveModel().forEach((s, activeModel) -> activeModel.clearModel());
                modeledEntity.getEntity().remove();
            }


        }

    }


}
