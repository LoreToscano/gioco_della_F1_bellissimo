package org.Models.FormulaUnoElements;

public enum PlayerType {
    EASY, 
    MEDIUM, 
    HARD, 
    CHAMPION,
    REAL;

    static public PlayerType getFromNumber(int n){
        switch (n) {
            case 0:
                return EASY;
            case 1:
                return MEDIUM;
            case 2:
                return HARD;
            case 3:
                return CHAMPION;
            case 4:
                return REAL;
            default:
                return MEDIUM;
        }
    }

    static public PlayerType getFromNumber(String s){
        try{
            int n = Integer.parseInt(s);
            return getFromNumber(n);
        }catch(Exception e){
            return MEDIUM;
        }
    }

    static public PlayerType[] getFromNumber(String[] a){
        PlayerType[] a2 = new PlayerType[a.length];
        for(int i = 0; i < a2.length; i++){
            a2[i] = getFromNumber(a[i]);
        }
        return a2;
    }

}
