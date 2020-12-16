package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.PlayerDataMap;
import com.daxton.customdisplay.task.action.JudgmentAction;
import net.mmogroup.mmolib.api.event.PlayerAttackEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.serverct.ersha.jd.event.AttrEntityCritEvent;
import org.serverct.ersha.jd.event.AttrEntityDamageEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerTrigger {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player = null;
    private LivingEntity target = null;
    private String firstString = "";
    private double damagerNumber = 0.0;
    private String taskID = "";

    /**觸發的動作列表**/
    private Map<String, List<String>> action_Trigger_Map = new HashMap<>();

    public PlayerTrigger(Player player){
        UUID playerUUID = player.getUniqueId();
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
        if(playerData != null){
            action_Trigger_Map = playerData.getAction_Trigger_Map();
        }
    }

    public void onAttack(Player player,LivingEntity target,double damageNumber){
        for(String actionString : action_Trigger_Map.get("~onattack")){
            new JudgmentAction().execute(player,target,actionString,damageNumber,String.valueOf((int)(Math.random()*100000)));
        }
    }

    public void onCrit(Player player,LivingEntity target,double damageNumber){
        for(String actionString : action_Trigger_Map.get("~oncrit")){
            new JudgmentAction().execute(player,target,actionString,damageNumber,"~oncrit"+(int)(Math.random()*100000));
        }
    }

    public void onMagic(Player player,LivingEntity target,double damageNumber){
        for(String actionString : action_Trigger_Map.get("~onmagic")){
            new JudgmentAction().execute(player,target,actionString,damageNumber,"~onmagic"+(int)(Math.random()*100000));
        }
    }

    public void onMCrit(Player player,LivingEntity target,double damageNumber){
        for(String actionString : action_Trigger_Map.get("~onmcrit")){
            new JudgmentAction().execute(player,target,actionString,damageNumber,"~onmcrit"+(int)(Math.random()*100000));
        }
    }

    public void onDamaged(Player player,double damageNumber){
        for(String actionString : action_Trigger_Map.get("~ondamaged")){
            new JudgmentAction().execute(player,actionString,String.valueOf((int)(Math.random()*100000)));
        }
    }

    public void onRegainHealth(Player player){

    }

    public void onJoin(Player player){
        for(String actionString : action_Trigger_Map.get("~onjoin")){
            new JudgmentAction().execute(player,actionString,String.valueOf((int)(Math.random()*100000)));
        }
    }

    public void onQuit(Player player){
        for(String actionString : action_Trigger_Map.get("~onquit")){
            new JudgmentAction().execute(player,actionString,String.valueOf((int)(Math.random()*100000)));
        }
    }

    public void onMove(Player player){

    }

    public void onSneak(Player player){
        for(String actionString : action_Trigger_Map.get("~onsneak")){
            new JudgmentAction().execute(player,actionString,String.valueOf((int)(Math.random()*100000)));
        }
    }

    public void onStandup(Player player){
        for(String actionString : action_Trigger_Map.get("~onstandup")){
            new JudgmentAction().execute(player,actionString,String.valueOf((int)(Math.random()*100000)));
        }
    }

    public void onDeath(Player player){

    }

    public void onSkillCastStart(Player player){
        for(String actionString : action_Trigger_Map.get("~onskillcaststart")){
            new JudgmentAction().execute(player,actionString,String.valueOf((int)(Math.random()*100000)));
        }
    }

    public void onSkillCastStop(Player player){
        for(String actionString : action_Trigger_Map.get("~onskillcaststop")){
            new JudgmentAction().execute(player,actionString,String.valueOf((int)(Math.random()*100000)));
        }
    }


    public Map<String, List<String>> getAction_Trigger_Map() {
        return action_Trigger_Map;
    }
}
