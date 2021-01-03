package com.daxton.customdisplay.api.other;

import com.daxton.customdisplay.CustomDisplay;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;

public class ArithmeticUtil {

    private static CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

    public static String valueof(String intput){
        String strs = intput;
        Object out = "";
        try {
            out = jse.eval(strs);
        } catch (Exception t) {

        }
        return out.toString();
    }

}
