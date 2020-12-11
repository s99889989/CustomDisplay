package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PermissionManager;
import com.daxton.customdisplay.task.action.JudgmentAction;
import net.mmogroup.mmolib.api.event.PlayerAttackEvent;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.serverct.ersha.jd.event.AttrEntityCritEvent;
import org.serverct.ersha.jd.event.AttrEntityDamageEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerData {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private FileConfiguration fileConfiguration;

    private List<String> playerActionList;

    private List<String> onAttakList = new ArrayList<>();
    private List<String> onCrit = new ArrayList<>();
    private List<String> onDamagedList = new ArrayList<>();
    private List<String> onJoinList = new ArrayList<>();
    private List<String> onQuitList = new ArrayList<>();
    private List<String> onSkillCastStart = new ArrayList<>();
    private List<String> onSkillCastStop = new ArrayList<>();
    private List<String> onSneak = new ArrayList<>();
    private List<String> onStandup = new ArrayList<>();

    public PlayerData(Player player){
        this.player = player;
        setPlayerActionList();
        setActionList();
    }
    /**獲取動作列表**/
    public void setPlayerActionList() {
        for(String configName : ConfigMapManager.getFileConfigurationNameMap().values()){

            if(configName.contains("Players_"+player.getName()+".yml")){
                fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("Players_"+player.getName()+".yml");
                break;
            }else{
                fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("Players_Default.yml");
            }
        }
        playerActionList = fileConfiguration.getStringList("Action");

        for(String stringConfig : PermissionManager.getPermission_String_Map().values()){

            if(player.hasPermission(stringConfig)){
                for(String list : PermissionManager.getPermission_FileConfiguration_Map().get(stringConfig).getStringList("Action")){
                    playerActionList.add(list);
                }
            }

        }

    }

    public void setActionList(){
        if(playerActionList.size() > 0){
            for(String actionString : playerActionList){
                if(actionString.toLowerCase().contains("~onattack")){
                    onAttakList.add(actionString);
                }
                if(actionString.toLowerCase().contains("~ondamaged")){
                    onDamagedList.add(actionString);
                }
                if(actionString.toLowerCase().contains("~onjoin")){
                    onJoinList.add(actionString);
                }
                if(actionString.toLowerCase().contains("~onquit")){
                    onQuitList.add(actionString);
                }
                if(actionString.toLowerCase().contains("~oncrit")){
                    onCrit.add(actionString);
                }
                if(actionString.toLowerCase().contains("~onskillcaststart")){
                    onSkillCastStart.add(actionString);
                }
                if(actionString.toLowerCase().contains("~onskillcaststop")){
                    onSkillCastStop.add(actionString);
                }
                if(actionString.toLowerCase().contains("~onsneak")){
                    onSneak.add(actionString);
                }
                if(actionString.toLowerCase().contains("~onstandup")){
                    onStandup.add(actionString);
                }

            }
        }
    }

    public void runAction(String type, Event event){
        if(type.toLowerCase().contains("~onattack") && onAttakList.size() > 0){
            onAttack(event);
        }
        if(type.toLowerCase().contains("~oncrit") && onCrit.size() > 0){
            onCrit(event);
        }
        if(type.toLowerCase().contains("~ondamaged") && onDamagedList.size() > 0){
            onDamaged(event);
        }
        if(type.toLowerCase().contains("~onjoin") && onJoinList.size() > 0){
            onJoin(event);
        }
        if(type.toLowerCase().contains("~onquit") && onQuitList.size() > 0){
            onQuit(event);
        }
        if(type.toLowerCase().contains("~onskillcaststart") && onSkillCastStart.size() > 0){
            onSkillCastStart(event);
        }
        if(type.toLowerCase().contains("~onskillcaststop") && onSkillCastStop.size() > 0){
            onSkillCastStop(event);
        }
        if(type.toLowerCase().contains("~onsneak") && onSneak.size() > 0){
            onSneak(event);
        }
        if(type.toLowerCase().contains("~onstandup") && onStandup.size() > 0){
            onStandup(event);
        }
    }

    public void onAttack(Event event){
        for(String actionString : onAttakList){
            LivingEntity target = null;
            double damageNumber = 0;
            if(Bukkit.getPluginManager().isPluginEnabled("MMOLib")){
                PlayerAttackEvent playerAttackEvent = (PlayerAttackEvent) event;
                target = playerAttackEvent.getEntity();
                damageNumber = playerAttackEvent.getAttack().getDamage();
            }else if(Bukkit.getPluginManager().isPluginEnabled("AttributePlus")){
                AttrEntityDamageEvent attrEntityDamageEvent = (AttrEntityDamageEvent) event;
                target = (LivingEntity) attrEntityDamageEvent.getEntity();
                damageNumber = attrEntityDamageEvent.getDamage();
            }else {
                EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) event;
                target = (LivingEntity) entityDamageByEntityEvent.getEntity();
                damageNumber = entityDamageByEntityEvent.getFinalDamage();
            }

            new JudgmentAction().execute(player,target,actionString,damageNumber,String.valueOf((int)(Math.random()*100000)));
        }
    }

    public void onCrit(Event event){
        for(String actionString : onCrit){
            LivingEntity target = null;
            double damageNumber = 0;
            if(Bukkit.getPluginManager().isPluginEnabled("MMOLib")){
                PlayerAttackEvent playerAttackEvent = (PlayerAttackEvent) event;
                target = playerAttackEvent.getEntity();

                damageNumber = playerAttackEvent.getAttack().getDamage();
            }else if(Bukkit.getPluginManager().isPluginEnabled("AttributePlus")){
                AttrEntityCritEvent attrEntityDamageEvent = (AttrEntityCritEvent) event;
                target = (LivingEntity) attrEntityDamageEvent.getEntity();
                damageNumber = attrEntityDamageEvent.getCritDamage();
            }else {
                EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) event;
                target = (LivingEntity) entityDamageByEntityEvent.getEntity();
                damageNumber = entityDamageByEntityEvent.getFinalDamage();
            }

            new JudgmentAction().execute(player,target,actionString,damageNumber,"~oncrit"+(int)(Math.random()*100000));
        }
    }

    public void onDamaged(Event event){
        for(String actionString : onDamagedList){
            new JudgmentAction().execute(player,actionString,String.valueOf((int)(Math.random()*100000)));
        }
    }

    public void onRegainHealth(Event event){

    }

    public void onJoin(Event event){
        for(String actionString : onJoinList){
            new JudgmentAction().execute(player,actionString,String.valueOf((int)(Math.random()*100000)));
        }
    }

    public void onQuit(Event event){
        for(String actionString : onQuitList){
            new JudgmentAction().execute(player,actionString,String.valueOf((int)(Math.random()*100000)));
        }
    }

    public void onMove(Event event){

    }

    public void onSneak(Event event){
        for(String actionString : onSneak){
            new JudgmentAction().execute(player,actionString,String.valueOf((int)(Math.random()*100000)));
        }
    }

    public void onStandup(Event event){
        for(String actionString : onStandup){
            new JudgmentAction().execute(player,actionString,String.valueOf((int)(Math.random()*100000)));
        }
    }

    public void onDeath(Event event){

    }

    public void onSkillCastStart(Event event){
        for(String actionString : onSkillCastStart){
            new JudgmentAction().execute(player,actionString,String.valueOf((int)(Math.random()*100000)));
        }
    }

    public void onSkillCastStop(Event event){
        for(String actionString : onSkillCastStop){
            new JudgmentAction().execute(player,actionString,String.valueOf((int)(Math.random()*100000)));
        }
    }

    public Player getPlayer() {
        return player;
    }

    public List<String> getPlayerActionList() {
        return playerActionList;
    }
}
