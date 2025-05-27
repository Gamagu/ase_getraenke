package de.nyg.adapters.asegetraenke.console.utils;

public class ConsoleError {
    private final String NOPRODUKTMESSAGE = "There are no Product/s found";
    private final String NOPFANDWERTMESSAGE = "There are no Pfandwert/s found";
    private final String NOKUNDEMESSAGE = "There are no Customer/s found";


    public void errorNoPfandWert() {
        System.out.println(NOPFANDWERTMESSAGE);
    }
 
    public void errorNoKunden() {
        System.out.println(NOKUNDEMESSAGE);
    }
    
    public void errorNoProdukt() {
        System.out.println(NOPRODUKTMESSAGE);
    }
}
