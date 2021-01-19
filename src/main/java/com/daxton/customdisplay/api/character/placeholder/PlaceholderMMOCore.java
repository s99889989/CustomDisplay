package com.daxton.customdisplay.api.character.placeholder;

import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.skill.Skill;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class PlaceholderMMOCore {

    public PlaceholderMMOCore(){}

    public String valueOf(LivingEntity entity){
        Player player = (Player) entity;
        StringBuilder str = new StringBuilder();

        PlayerData data = PlayerData.get(player);
        String ready = MMOCore.plugin.configManager.getSimpleMessage("casting.action-bar.ready", new String[0]).message();
        String onCooldown = MMOCore.plugin.configManager.getSimpleMessage("casting.action-bar.on-cooldown", new String[0]).message();
        String noMana = MMOCore.plugin.configManager.getSimpleMessage("casting.action-bar.no-mana", new String[0]).message();
        String split = MMOCore.plugin.configManager.getSimpleMessage("casting.split", new String[0]).message();
        for(int j = 0; j < data.getBoundSkills().size(); ++j) {
            Skill.SkillInfo skill = data.getBoundSkill(j);
            str.append(str.length() == 0 ? "" : split).append((onCooldown(data, skill) ? onCooldown.replace("{cooldown}", "" + data.getSkillData().getCooldown(skill) / 1000L) : (noMana(data, skill) ? noMana : ready)).replace("{index}", "" + (j + 1 + (data.getPlayer().getInventory().getHeldItemSlot() <= j ? 1 : 0))).replace("{skill}", data.getBoundSkill(j).getSkill().getName()));
        }


        return str.toString();
    }

    private boolean onCooldown(PlayerData data, Skill.SkillInfo skill) {
        return skill.getSkill().hasModifier("cooldown") && data.getSkillData().getCooldown(skill) > 0L;
    }
    private boolean noMana(PlayerData data, Skill.SkillInfo skill) {
        return skill.getSkill().hasModifier("mana") && skill.getModifier("mana", data.getSkillLevel(skill.getSkill())) > data.getMana();
    }

}
