package org.Exception;

public class ParsingException extends Exception {
    
    public ParsingException(int line){
        super("L'errore di parsing si e' verificato alla linea " + line);
    }

}
