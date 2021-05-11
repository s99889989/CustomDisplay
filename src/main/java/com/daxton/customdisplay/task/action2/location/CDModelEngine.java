package com.daxton.customdisplay.task.action2.location;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.ActionManager;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import com.ticxo.modelengine.api.util.ConfigManager;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CDModelEngine {

    private final CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Map<String, String> action_Map;
    private LivingEntity self;
    private LivingEntity target;
    private String taskID;

    private final Map<String, ModeledEntity> modeledEntity_Map = new ConcurrentHashMap<>();
    private final Map<String, Entity> entity_Map = new ConcurrentHashMap<>();
    private final Map<String, Location> location_Map = new ConcurrentHashMap<>();
    private final Map<String, String> modelid_Map = new ConcurrentHashMap<>();

    public void setGuise(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        this.taskID = taskID;
        this.self = self;
        this.target = target;
        this.action_Map = action_Map;

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String mark = actionMapHandle.getString(new String[]{"mark","mk"},"0");

        setOther(taskID+mark);

    }

    public void setOther(String livTaskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(this.action_Map, this.self, this.target);

        String modelid = actionMapHandle.getString(new String[]{"m", "mid", "model", "modelid"}, null);

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

        boolean invisible = actionMapHandle.getBoolean(new String[]{"i", "invis", "invisible"}, true);
        //顯示的時間
        int duration = actionMapHandle.getInt(new String[]{"duration","dt"},-1);

        String nametag = actionMapHandle.getString(new String[]{"n", "name", "nametag"}, null);

        boolean teleport = actionMapHandle.getBoolean(new String[]{"teleport","tp"}, false);

        boolean delete = actionMapHandle.getBoolean(new String[]{"delete","d"}, false);

        Location oldlocation = null;
        if(location_Map.get(livTaskID) != null){
            oldlocation = location_Map.get(livTaskID);
        }
        Location location = actionMapHandle.getLocation(oldlocation);



        if(modeledEntity_Map.get(livTaskID) == null){
            if(location != null){
                location_Map.put(livTaskID, location);
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
                /////////
                entity_Map.put(livTaskID, entity);
                modeledEntity_Map.put(livTaskID, modeledEntity);
                modelid_Map.put(livTaskID, modelid);
            }
        }else {
            ModeledEntity modeledEntity = modeledEntity_Map.get(livTaskID);
            if(location != null){
                if(teleport){
                    if(entity_Map.get(livTaskID) != null){
                        Entity entity =  entity_Map.get(livTaskID);
                        entity.teleport(location);
                        location_Map.put(livTaskID, location);
                    }
                }
            }
//            cd.getLogger().info(stateID);
            ActiveModel activeModel = modeledEntity.getActiveModel(modelid_Map.get(livTaskID));
            activeModel.addState(stateID, lerpIn, lerpOut, speed);
            modeledEntity.addActiveModel(activeModel);
            modeledEntity.detectPlayers();
        }
        if(modeledEntity_Map.get(livTaskID) != null){
            ModeledEntity modeledEntity = modeledEntity_Map.get(livTaskID);




//            if(nametag != null){
//                modeledEntity.setNametag(nametag);
//                modeledEntity.setNametagVisible(true);
//            }

            if(delete){
                modeledEntity.getAllActiveModel().forEach((s, activeModel) -> activeModel.clearModel());
                modeledEntity.getEntity().remove();
            }

//            if(duration > 0){
//                BukkitRunnable bukkitRunnable = new BukkitRunnable() {
//                    @Override
//                    public void run() {
//                        activeModel.clearModel();
//
//                        modeledEntity.getEntity().remove();
//                        if(ActionManager.judgment_ModelEngine_Map.get(taskID) != null){
//                            ActionManager.judgment_ModelEngine_Map.remove(taskID);
//                        }
//                    }
//                };
//                bukkitRunnable.runTaskLater(cd,duration);
//            }else if(duration < 0){
//                activeModel.clearModel();
//                modeledEntity.getEntity().remove();
//                if(ActionManager.judgment_ModelEngine_Map.get(this.taskID) != null){
//                    ActionManager.judgment_ModelEngine_Map.remove(this.taskID);
//                }
//            }

        }









    }



}
