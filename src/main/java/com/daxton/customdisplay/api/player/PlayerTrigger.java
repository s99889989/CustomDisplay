package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion2;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ActionManager2;
import com.daxton.customdisplay.manager.PlayerDataMap;
import com.daxton.customdisplay.task.action.ClearAction;
import com.daxton.customdisplay.task.action.JudgmentAction2;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerTrigger {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player = null;
    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";

    private boolean stop = false;
    private String taskID = String.valueOf((int)(Math.random()*100000));

    /**觸發的動作列表**/
    private Map<String, List<String>> action_Trigger_Map = new HashMap<>();

    public PlayerTrigger(Player player){
        UUID playerUUID = player.getUniqueId();
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
        if(playerData != null){
            action_Trigger_Map = playerData.getAction_Trigger_Map();
        }
    }

    public void onAttack(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onattack") != null){
            for(String actionString : action_Trigger_Map.get("~onattack")){
                runExecute(actionString);
            }
        }
    }

    public void onCrit(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~oncrit") != null){
            for(String actionString : action_Trigger_Map.get("~oncrit")){
                runExecute(actionString);
            }
        }
    }

    public void onMagic(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onmagic") != null){
            for(String actionString : action_Trigger_Map.get("~onmagic")){
                runExecute(actionString);
            }
        }
    }

    public void onMCrit(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onmcrit") != null){
            for(String actionString : action_Trigger_Map.get("~onmcrit")){
                runExecute(actionString);
            }
        }
    }

    public void onDamaged(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~ondamaged") != null){
            for(String actionString : action_Trigger_Map.get("~ondamaged")){
                runExecute(actionString);
            }
        }
    }



    public void onRegainHealth(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onregainhealth") != null){
            for(String actionString : action_Trigger_Map.get("~onregainhealth")){
                runExecute(actionString);
            }
        }
    }

    public void onJoin(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onjoin") != null){
            for(String actionString : action_Trigger_Map.get("~onjoin")){
                runExecute(actionString);
            }
        }
    }

    public void onQuit(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onquit") != null){
            for(String actionString : action_Trigger_Map.get("~onquit")){
                runExecute(actionString);
            }
        }
    }

    public void onMove(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onmove") != null){
            cd.getLogger().info("~onmove");
            for(String actionString : action_Trigger_Map.get("~onmove")){
                runExecute(actionString);
            }
        }
    }

    public void onSneak(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onsneak") != null){
            for(String actionString : action_Trigger_Map.get("~onsneak")){
                runExecute(actionString);
            }
        }
    }

    public void onStandup(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onstandup") != null){
            for(String actionString : action_Trigger_Map.get("~onstandup")){
                runExecute(actionString);
            }
        }
    }

    public void onDeath(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~ondeath") != null){
            for(String actionString : action_Trigger_Map.get("~ondeath")){
                runExecute(actionString);
            }
        }
    }

    public void onSkillCastStart(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onskillcaststart") != null){
            for(String actionString : action_Trigger_Map.get("~onskillcaststart")){
                runExecute(actionString);
            }
        }
    }

    public void onSkillCastStop(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onskillcaststop") != null){
            for(String actionString : action_Trigger_Map.get("~onskillcaststop")){
                runExecute(actionString);
            }
        }
    }

    public void runExecute(String actionString){

        for(String allString : new StringFind().getStringList(actionString)){
            if(allString.toLowerCase().contains("mark=") || allString.toLowerCase().contains("m=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    taskID = new StringConversion2(self,target,strings[1],"Character").valueConv();
                }
            }
            if(allString.toLowerCase().contains("stop=") || allString.toLowerCase().contains("s=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    stop = Boolean.valueOf(strings[1]);
                }
            }
        }
        if(stop){
            new ClearAction(taskID);
        }else{
            new JudgmentAction2().execute(self,target,actionString,taskID);
//            if(ActionManager2.getOther_Judgment2_Map().get(taskID) == null){
//                new ClearAction(taskID);
//                ActionManager2.getOther_Judgment2_Map().put(taskID,new JudgmentAction2());
//                ActionManager2.getOther_Judgment2_Map().get(taskID).execute(self,target,actionString,taskID);
//                if(ActionManager2.getOther_Judgment2_Map().get(taskID) != null){
//                    ActionManager2.getOther_Judgment2_Map().get(taskID).getBukkitRunnable().cancel();
//                    ActionManager2.getOther_Judgment2_Map().remove(taskID);
//                }
//            }
        }
    }



    public Map<String, List<String>> getAction_Trigger_Map() {
        return action_Trigger_Map;
    }
}
