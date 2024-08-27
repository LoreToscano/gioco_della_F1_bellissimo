package org.View;

import java.util.stream.IntStream;

import org.Controller.FormulaUnoController;
import org.Controller.GameController;
import org.Models.DTO.FormulaUnoGameInfoDTO;
import org.Models.DTO.FormulaUnoPlayerInfoDTO;
import org.Models.DTO.TwoDimensionGameMapDTO;
import org.Models.FormulaUnoElements.FormulaUnoCommand;
import org.Models.GameElements.Section2D;

public class FormulaUnoConsoleController {
    
    private GameController<TwoDimensionGameMapDTO, FormulaUnoCommand, FormulaUnoGameInfoDTO> controller;
    private FormulaUnoStandardGameMapBuilder mapBuilder;
    private FormulaUnoSettingsBuilder settingsBuilder;

    public FormulaUnoConsoleController(){
        this.controller = new FormulaUnoController();
        this.mapBuilder = new FormulaUnoStandardGameMapBuilder();
        this.settingsBuilder = new FormulaUnoSettingsBuilder();
    }

    public void run() throws Exception {
        int mills = prepareGame();
        printMap();
        runGame(mills);
        printResult();
    }

    private int prepareGame() throws Exception {
        FormulaUnoViewSettings settings = settingsBuilder.getViewSettings();
        if(settings.isCustomSettings()) controller.applySettingsGame(settings.getPathSettings());
        else controller.applySettingsGame(settingsBuilder.getSettings());
        if(settings.isCustomMap()) controller.loadGameMap(settings.getPathMap());
        else controller.loadGameMap(this.mapBuilder.getStandardMapFromId(settings.getIdStandardMap()));
        controller.startGame();
        return settings.getMills();
    }

    private void printMap(){
        TwoDimensionGameMapDTO map = controller.getGameMap();
        for(Section2D section : map.getSections()){
            System.out.println("Direction: " + section.getDirection().name() + " - Length: " + section.getDistance());
        }
    }

    private void runGame(int mills) throws InterruptedException{
        while(true){
            FormulaUnoGameInfoDTO info = controller.getInfo();
            int players = info.playingPlayers();
            if(players == 0)
                break;
            System.out.println("\n\n");
            for(FormulaUnoPlayerInfoDTO i : info.getPlayers()){
                System.out.println(i.getName() + " - " + ((int)i.getPercent()) + "% - " + i.getState().name());
                System.out.println(i.getCoordinate());
            }
            IntStream.range(0, players).forEach(i -> controller.executeMove());
            Thread.sleep(mills);
        }
    }

    private void printResult(){
        FormulaUnoGameInfoDTO info = controller.getInfo();
        System.out.println("\n\n");
        for(FormulaUnoPlayerInfoDTO i : info.getPlayers()) {
            if(i.getWinnerPosition() == -1) System.out.println(i.getName() + " has been eliminated at time " + getTime(i.getWinnerTime()));
            else System.out.println(i.getName() + " arrived in posizion " + i.getWinnerPosition() + " at time " + getTime(i.getWinnerTime()));
        }
    }

    private String getTime(long num){
        String time = "";
        if(num > 3600){
            time += (num/3600) + "h";
            num %= 3600;
        }
        if(num > 60){
            time += (num/60) + "m";
            num %= 60;
        }
        time += num + "s";
        return time;
    }

}
