package org.Models.GameElements;

import java.util.HashMap;
import java.util.Map;

public class GameSettings {

    private Map<String, String> settings;

    public GameSettings(Map<String, String> settings){
        this.settings = settings;
    }

    public GameSettings(){
        this.settings = new HashMap<String,String>();
    }

    public void setSetting(String name, String value){
        settings.put(name, value);
    }

    public boolean haveSetting(String name){
        return this.settings.containsKey(name);
    }

    public String getSetting(String name, String defaultValue){
        if(this.settings.containsKey(name))
            return this.settings.get(name);
        return defaultValue;
    }

    public boolean getBoolSetting(String name, boolean defaultValue){
        if(this.settings.containsKey(name)){
            String value = this.settings.get(name);
            if(value.toLowerCase().equals("true") || value.equals("1"))
                return true;
            if(value.toLowerCase().equals("false") || value.equals("0"))
                return false;
        }
        return defaultValue;
    }

    public int getSettingNumber(String name, int defaultValue){
        try{
            String value = getSetting(name, defaultValue + "");
            return Integer.parseInt(value);
        }catch( Exception e){
            return defaultValue;
        }
    }

    public int getSettingPositiveNumber(String name, int defaultValue){
        int value = getSettingNumber(name, defaultValue);
        return value < 1 ? defaultValue : value;
    }

}
