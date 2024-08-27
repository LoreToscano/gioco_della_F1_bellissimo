package org.Controller.Parsing;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.Exception.ParsingException;

public class SettingsParser {

    public void parseSettingsFile(String pathOfSetting) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(pathOfSetting)).stream().toList();
        for(int i = 0; i < lines.size(); i++) {
            String[] words = lines.get(i).split(" ");
            if(words.length < 3 || words[1].equals("="))
                throw new ParsingException(i);
        }
    }
    
}