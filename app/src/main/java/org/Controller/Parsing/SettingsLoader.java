package org.Controller.Parsing;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.Models.GameElements.GameSettings;

public class SettingsLoader {

    public GameSettings LoadSettings(String pathOfSetting) throws Exception {
        GameSettings gameSet = new GameSettings();
        Files.readAllLines(Paths.get(pathOfSetting))
             .forEach(x -> {
                String[] words = x.split(" ");
                if(words.length == 3)
                    gameSet.setSetting(words[0], words[2]);
                else {
                    String value = Arrays.asList(Arrays.copyOfRange(words, 2, words.length))
                                         .stream().reduce("", (a, b) -> a + " " + b);
                    gameSet.setSetting(words[0], value);
                }
             });
        return gameSet;
    }
    
}
