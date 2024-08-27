package org.Models.DTO;

import java.util.Arrays;

import org.Models.FormulaUnoElements.FormulaUnoGameState;

public class FormulaUnoGameInfoDTO {
    private FormulaUnoPlayerInfoDTO[] players;
    private int round;
    
    public FormulaUnoGameInfoDTO(FormulaUnoPlayerInfoDTO[] players, int round) {
        this.players = players;
        this.round = round;
    }

    public FormulaUnoPlayerInfoDTO[] getPlayers() {
        FormulaUnoPlayerInfoDTO[] copy = new FormulaUnoPlayerInfoDTO[players.length];
        for(int i = 0; i < players.length; i++)
            copy[i] = players[i];
        return copy;
    }

    public int getRound() {
        return round;
    }

    public int playingPlayers(){
        return (int) Arrays.asList(getPlayers()).stream().filter(p -> p.getState() != FormulaUnoGameState.FINISHED 
                                                                    && p.getState() != FormulaUnoGameState.ELIMINATED).count();
    }

    
}
