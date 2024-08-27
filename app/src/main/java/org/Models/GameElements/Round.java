package org.Models.GameElements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Round {
    
    private int roundIndex, maxRound;
    private long timeCounter;
    private List<Integer> rounds;
    private boolean randomOrder;

    public Round(int playerNumber, boolean random){
        roundIndex = 0;
        timeCounter = 0;
        maxRound = playerNumber;
        rounds = new ArrayList<>(IntStream.range(0, playerNumber).boxed().toList());
        this.randomOrder = random;
        if(random)
            shuffleRounds();
    }

    private void shuffleRounds(){
        for(int i = 0; i < maxRound; i++){
            int from = (int)(Math.random() * maxRound);
            int to = (int)(Math.random() * maxRound);
            int temp = this.rounds.get(to);
            this.rounds.set(to, this.rounds.get(from));
            this.rounds.set(from, temp);
        }
    }

    public int getRound() {
        if(rounds.isEmpty())
            return -1;
        return rounds.get(roundIndex);
    }

    public int nextRound() {
        if(rounds.isEmpty())
            return -1;
        if(++roundIndex == maxRound){
            if(this.randomOrder)
                shuffleRounds();
            roundIndex = 0;
            timeCounter++;
        }
        return getRound();
    }

    public void deleteRound(int id){
        rounds.removeIf(x -> x == id);
        maxRound = rounds.size();
        if(roundIndex >= maxRound){
            if(this.randomOrder)
                shuffleRounds();
            roundIndex = 0;
        }
    }

    public long getTime(){
        return this.timeCounter;
    }

}
