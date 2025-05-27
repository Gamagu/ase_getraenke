package de.nyg.adapters.asegetraenke.console.Utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import de.nyg.application.asegetraenke.GetraenkeUsecases;
import de.nyg.application.asegetraenke.KundenUsecases;
import de.nyg.domain.asegetraenke.entities.Kunde;
import de.nyg.domain.asegetraenke.entities.Produkt;
import de.nyg.domain.asegetraenke.valueobjects.Pfandwert;


public class ConsoleError {
    private final String NOPRODUKTMESSAGE = "There are no Product/s found";
    private final String NOPFANDWERTMESSAGE = "There are no Pfandwert/s found";
    private final String NOKUNDEMESSAGE = "There are no Customer/s found";

    private final Scanner scanner;
    private final GetraenkeUsecases getraenkeUseCases;
    private final KundenUsecases kundeUseCases;

    public ConsoleError(Scanner scanner, GetraenkeUsecases getraenkeUseCases, KundenUsecases kundeUseCases) {
        this.scanner = scanner;
        this.getraenkeUseCases = getraenkeUseCases;
        this.kundeUseCases = kundeUseCases;
    }


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
