package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import com.daxton.customdisplay.task.action.ClearAction;
import com.daxton.customdisplay.task.action.JudgmentAction;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerTrigger {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player = null;
    private LivingEntity self = null;
    private LivingEntity target = null;
    private List<LivingEntity> targetList = new ArrayList<>();
    private String firstString = "";

    private boolean stop = false;
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
    /**當攻擊時**/
    public void onAttack(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onattack") != null){
            for(String actionString : action_Trigger_Map.get("~onattack")){
                actionString = actionString.replace("~onAttack","");
                runExecute(actionString);
            }
        }
    }
    /**當爆擊時**/
    public void onCrit(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~oncrit") != null){
            for(String actionString : action_Trigger_Map.get("~oncrit")){
                actionString = actionString.replace("~onCrit","");
                runExecute(actionString);
            }
        }
    }
    /**當魔法攻擊時**/
    public void onMagic(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onmagic") != null){
            for(String actionString : action_Trigger_Map.get("~onmagic")){
                actionString = actionString.replace("~onMagic","");
                runExecute(actionString);
            }
        }
    }
    /**當魔法攻擊爆擊時**/
    public void onMCrit(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onmcrit") != null){
            for(String actionString : action_Trigger_Map.get("~onmcrit")){
                actionString = actionString.replace("~onMCrit","");
                runExecute(actionString);
            }
        }
    }
    /**當魔法攻擊爆擊時**/
    public void onAtkMiss(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onatkmiss") != null){
            for(String actionString : action_Trigger_Map.get("~onatkmiss")){
                actionString = actionString.replace("~onAtkMiss","");
                runExecute(actionString);
            }
        }
    }
    /**被攻擊時**/
    public void onDamaged(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~ondamaged") != null){
            for(String actionString : action_Trigger_Map.get("~ondamaged")){
                actionString = actionString.replace("~onDamaged","");
                runExecute(actionString);
            }
        }
    }
    /**被攻擊時**/
    public void onDamagedMiss(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~ondamagedmiss") != null){
            for(String actionString : action_Trigger_Map.get("~ondamagedmiss")){
                actionString = actionString.replace("~onDamagedMiss","");
                runExecute(actionString);
            }
        }
    }
    /**當回血時**/
    public void onRegainHealth(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onregainhealth") != null){
            for(String actionString : action_Trigger_Map.get("~onregainhealth")){
                actionString = actionString.replace("~onRegainHealth","");
                runExecute(actionString);
            }
        }
    }
    /**當登入時**/
    public void onJoin(LivingEntity self){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onjoin") != null){
            for(String actionString : action_Trigger_Map.get("~onjoin")){
                actionString = actionString.replace("~onJoin","");
                runExecute(actionString);
            }
        }
    }
    /**當登出時**/
    public void onQuit(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onquit") != null){
            for(String actionString : action_Trigger_Map.get("~onquit")){
                actionString = actionString.replace("~onQuit","");
                runExecute(actionString);
            }
        }
    }
    /**移動時**/
    public void onMove(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onmove") != null){
            for(String actionString : action_Trigger_Map.get("~onmove")){
                actionString = actionString.replace("~onMove","");
                runExecute(actionString);
            }
        }
    }
    /**蹲下時**/
    public void onSneak(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onsneak") != null){
            for(String actionString : action_Trigger_Map.get("~onsneak")){
                actionString = actionString.replace("~onSneak","");
                runExecute(actionString);
            }
        }
    }
    /**站起來時**/
    public void onStandup(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onstandup") != null){
            for(String actionString : action_Trigger_Map.get("~onstandup")){
                actionString = actionString.replace("~onStandup","");
                runExecute(actionString);
            }
        }
    }
    /**當死亡時**/
    public void onDeath(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~ondeath") != null){
            for(String actionString : action_Trigger_Map.get("~ondeath")){
                actionString = actionString.replace("~onDeath","");
                runExecute(actionString);
            }
        }
    }
    /**當按下案件F時，一開始會觸發為ON，登出重新計算**/
    public void onKeyFON(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onkeyfon") != null){
            for(String actionString : action_Trigger_Map.get("~onkeyfon")){
                actionString = actionString.replace("~onKeyFON","");
                runExecute(actionString);
            }
        }
    }
    /**再按下案件F時，觸發為OFF，登出重新計算**/
    public void onKeyFOFF(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onkeyfoff") != null){
            for(String actionString : action_Trigger_Map.get("~onkeyfoff")){
                actionString = actionString.replace("~onKeyFOFF","");
                runExecute(actionString);
            }
        }
    }
    /**當切換到物品欄1時**/
    public void onKey1(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onkey1") != null){
            for(String actionString : action_Trigger_Map.get("~onkey1")){
                actionString = actionString.replace("~onKey1","");
                runExecute(actionString);
            }
        }
    }
    /**當切換到物品欄2時**/
    public void onKey2(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onkey2") != null){
            for(String actionString : action_Trigger_Map.get("~onkey2")){
                actionString = actionString.replace("~onKey2","");
                runExecute(actionString);
            }
        }
    }
    /**當切換到物品欄3時**/
    public void onKey3(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onkey3") != null){
            for(String actionString : action_Trigger_Map.get("~onkey3")){
                actionString = actionString.replace("~onKey3","");
                runExecute(actionString);
            }
        }
    }
    /**當切換到物品欄4時**/
    public void onKey4(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onkey4") != null){
            for(String actionString : action_Trigger_Map.get("~onkey4")){
                actionString = actionString.replace("~onKey4","");
                runExecute(actionString);
            }
        }
    }
    /**當切換到物品欄5時**/
    public void onKey5(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onkey5") != null){
            for(String actionString : action_Trigger_Map.get("~onkey5")){
                actionString = actionString.replace("~onKey5","");
                runExecute(actionString);
            }
        }
    }
    /**當切換到物品欄6時**/
    public void onKey6(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onkey6") != null){
            for(String actionString : action_Trigger_Map.get("~onkey6")){
                actionString = actionString.replace("~onKey6","");
                runExecute(actionString);
            }
        }
    }
    /**當切換到物品欄7時**/
    public void onKey7(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onkey7") != null){
            for(String actionString : action_Trigger_Map.get("~onkey7")){
                actionString = actionString.replace("~onKey7","");
                runExecute(actionString);
            }
        }
    }
    /**當切換到物品欄8時**/
    public void onKey8(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onkey8") != null){
            for(String actionString : action_Trigger_Map.get("~onkey8")){
                actionString = actionString.replace("~onKey8","");
                runExecute(actionString);
            }
        }
    }
    /**當切換到物品欄9時**/
    public void onKey9(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onkey9") != null){
            for(String actionString : action_Trigger_Map.get("~onkey9")){
                actionString = actionString.replace("~onKey9","");
                runExecute(actionString);
            }
        }
    }
    /**當說話時**/
    public void onChat(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onchat") != null){
            for(String actionString : action_Trigger_Map.get("~onchat")){
                actionString = actionString.replace("~onChat","");
                runExecute(actionString);
            }
        }
    }
    /**當輸入指令時**/
    public void onCommand(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~oncommand") != null){
            for(String actionString : action_Trigger_Map.get("~oncommand")){
                actionString = actionString.replace("~onCommand","");
                runExecute(actionString);
            }
        }
    }
    /**當等級提升時**/
    public void onLevelUp(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onlevelup") != null){
            for(String actionString : action_Trigger_Map.get("~onlevelup")){
                actionString = actionString.replace("~onLevelUp","");
                runExecute(actionString);
            }
        }
    }
    /**當等級降低時**/
    public void onLevelDown(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onleveldown") != null){
            for(String actionString : action_Trigger_Map.get("~onleveldown")){
                actionString = actionString.replace("~onLevelDown","");
                runExecute(actionString);
            }
        }
    }
    /**當獲得經驗值時**/
    public void onExpUp(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onexpup") != null){
            for(String actionString : action_Trigger_Map.get("~onexpup")){
                actionString = actionString.replace("~onExpUp","");

                runExecute(actionString);
            }
        }
    }
    /**當失去經驗值時**/
    public void onExpDown(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onexpdown") != null){
            for(String actionString : action_Trigger_Map.get("~onexpdown")){
                actionString = actionString.replace("~onExpDown","");
                runExecute(actionString);
            }
        }
    }

    /**當怪物死亡時.(死亡原因必須是該玩家)**/
    public void onMobDeath(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onmobdeath") != null){
            for(String actionString : action_Trigger_Map.get("~onmobdeath")){
                actionString = actionString.replace("~onMobDeath","");
                runExecute(actionString);
            }
        }
    }
    /**當MythicMobs怪物死亡時.(死亡原因必須是該玩家)**/
    public void onMythicMobDeath(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onmmobdeath") != null){
            for(String actionString : action_Trigger_Map.get("~onmmobdeath")){
                actionString = actionString.replace("~onMMobDeath","");
                runExecute(actionString);
            }
        }
    }
    /**當MythicMobs的怪物掉落經驗**/
    public void onMMobDropExp(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onmmobdropexp") != null){
            for(String actionString : action_Trigger_Map.get("~onmmobdropexp")){
                actionString = actionString.replace("~onMMobDropExp","");
                runExecute(actionString);
            }
        }
    }
    /**當MythicMobs的怪物掉落金錢**/
    public void onMMobDropMoney(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onmmobdropmoney") != null){
            for(String actionString : action_Trigger_Map.get("~onmmobdropmoney")){
                actionString = actionString.replace("~onMMobDropMoney","");
                runExecute(actionString);
            }
        }
    }
    /**當MythicMobs的怪物掉落物品**/
    public void onMMobDropItem(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onmmobdropitem") != null){
            for(String actionString : action_Trigger_Map.get("~onmmobdropitem")){
                actionString = actionString.replace("~onMMobDropItem","");
                runExecute(actionString);
            }
        }
    }
    /**使用Gui動作**/
    public void onGuiClick(LivingEntity self,List<String> action){
        this.self = self;
        if(action.size() > 0){
            for(String actionString : action){
                runExecute(actionString);
            }
        }
    }
    /**使用技能**/
    public void onSkill(LivingEntity self,LivingEntity target,List<String> action){
        this.self = self;
        this.target = target;
        if(action.size() > 0){
            for(String actionString : action){
                runExecute(actionString);
            }
        }
    }

    public void runExecute(String actionString){
        stop = false;
        taskID = String.valueOf((int)(Math.random()*100000));
        for(String allString : new StringFind().getStringList(actionString)){
            if(allString.toLowerCase().contains("mark=") || allString.toLowerCase().contains("m=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    taskID = new ConversionMain().valueOf(self,target,strings[1]);
                }
            }
            if(allString.toLowerCase().contains("stop=") || allString.toLowerCase().contains("s=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    stop = Boolean.valueOf(strings[1]);
                }
            }
        }

        if(stop){ //actionString.toLowerCase().contains("stop=true")
            if(ActionManager.getOther_Judgment2_Map().get(taskID) != null){
                new ClearAction(taskID);
                new ClearAction(self,target);
                ActionManager.getOther_Judgment2_Map().remove(taskID);
            }
        }else{

            //new JudgmentAction().execute(self,target,actionString,taskID);

            if(ActionManager.getOther_Judgment2_Map().get(taskID) == null){
                ActionManager.getOther_Judgment2_Map().put(taskID,new JudgmentAction());
                ActionManager.getOther_Judgment2_Map().get(taskID).execute(self,target,actionString,taskID);
            }
        }
    }



    public Map<String, List<String>> getAction_Trigger_Map() {
        return action_Trigger_Map;
    }
}
