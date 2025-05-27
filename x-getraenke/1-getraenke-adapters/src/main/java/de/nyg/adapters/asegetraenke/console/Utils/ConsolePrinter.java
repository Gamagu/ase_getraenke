package de.nyg.adapters.asegetraenke.console.Utils;

import de.nyg.domain.asegetraenke.entities.Kunde;
import de.nyg.domain.asegetraenke.entities.Produkt;
import de.nyg.domain.asegetraenke.valueobjects.Pfandwert;

public class ConsolePrinter {
    public void printProduktWithNumber(Produkt produkt, int number){
        System.out.println(number + ". "+ produkt.toString());
    }

    public void printPfandwertWithNumber(Pfandwert pfandwert, int number){
        System.out.println(number + ". "+ pfandwert.toString());
    }

    public void printKundeWithNumber(Kunde kunde, int number){
        System.out.println(number + ". "+ kunde.toString());
    } 
}
