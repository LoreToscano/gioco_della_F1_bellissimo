package org.Models.GameElements;

import java.util.ArrayList;
import java.util.List;

public class GameInfo {

    private List<String> info;

    public GameInfo(List<String> i) {
        this.info = new ArrayList<String>();
        i.forEach(s -> this.info.add(s));
    }

    public List<String> getInfo() {
        List<String> copy = new ArrayList<String>();
        this.info.forEach(s -> copy.add(s));
        return copy;
    }

}
