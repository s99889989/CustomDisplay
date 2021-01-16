package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.StringConversion;
import com.daxton.customdisplay.api.character.stringconversion.StringConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.File;

import static org.bukkit.Color.fromRGB;

public class Message{

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String message = "";

    private Player player= null;
    private LivingEntity self = null;
    private LivingEntity target = null;


    private String aims = "self";

    public Message(){


    }

    public void setMessage(LivingEntity self,LivingEntity target, String firstString,String taskID){
        this.self = self;
        this.target = target;

        for(String string : new StringFind().getStringList(firstString)){
            if(string.toLowerCase().contains("@=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    aims = strings[1];
                }
            }
        }

        for(String allString : new StringFind().getStringMessageList(firstString)){
            if(allString.toLowerCase().contains("message=") || allString.toLowerCase().contains("m=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    message = new StringConversionMain().valueOf(self,target,strings[1]);
                }
            }

        }
        if(target instanceof Player & aims.toLowerCase().contains("target")){
            player = (Player) target;
        }else {
            if(self instanceof Player){
                player = (Player) self;
            }
        }
        if(player != null){
            sendMessage();
        }
    }

    public void sendMessage(){

        if(message.contains("{") && message.contains("}")){
            TextComponent all = new TextComponent("");
            for(String me : new StringFind().getBlockList(message,"{}")){
                all.addExtra(getKey(me));
            }
            player.spigot().sendMessage(all);
        }else {
            player.sendMessage(message);
        }


    }

    public TextComponent getKey(String messageString){
        TextComponent textComponent = new TextComponent(messageString);
        File filePatch = new File(cd.getDataFolder(),"Message");
        for(String ymlName : filePatch.list()){
            if(ymlName.contains(".yml")){

                File fileYmlPatch = new File(cd.getDataFolder(),"Message/"+ymlName);
                FileConfiguration messageConfig = YamlConfiguration.loadConfiguration(fileYmlPatch);
                ConfigurationSection buttonNameList = messageConfig.getConfigurationSection("");
                if(buttonNameList.getKeys(false).contains(messageString)){
                    String text = messageConfig.getString(messageString+".Text");
                    boolean bold = messageConfig.getBoolean(messageString+".Bold");
                    if(text.contains("&")){
                        text = new StringConversionMain().valueOf(self,target,text);
                    }
                    String color = messageConfig.getString(messageString+".Color");
                    String click_action = messageConfig.getString(messageString+".ClickEvent.Action");
                    String click_value = messageConfig.getString(messageString+".ClickEvent.Text");
                    if(click_value.contains("&")){
                        click_value = new StringConversionMain().valueOf(self,target,click_value);
                    }

                    String hover_action = messageConfig.getString(messageString+".HoverEvent.Action");
                    String hover_value = messageConfig.getString(messageString+".HoverEvent.Text");
                    if(hover_value.contains("&")){
                        hover_value = new StringConversionMain().valueOf(self,target,hover_value);
                    }
                    textComponent.setBold(bold);
                    if(color != null){
//                        BigInteger bigint = new BigInteger(color, 16);
//                        int numb = bigint.intValue();
//                        fromRGB(numb)
                        textComponent.setColor(ChatColor.valueOf(color));
                    }

                    if(click_action != null && click_value != null){

                        textComponent.setClickEvent(new ClickEvent(Enum.valueOf(ClickEvent.Action.class,click_action),click_value));
                    }

                    if(hover_action != null && hover_value != null){

                        textComponent.setHoverEvent( new HoverEvent(Enum.valueOf(HoverEvent.Action.class,hover_action), new Text(hover_value)));
                    }
                    if(text != null){
                        textComponent.setText(text);
                    }

                    //player.sendMessage("包含: "+click_action);
                }
                //buttonNameList.getKeys(false).forEach((line)->{player.sendMessage(line);});


            }

        }
        return textComponent;
    }


}
