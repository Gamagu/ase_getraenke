package com.asegetraenke.console.Utils;

import java.util.Optional;
import com.asegetraenke.entities.Kunde;
import com.asegetraenke.entities.Produkt;
import com.asegetraenke.valueobjects.Pfandwert;

public interface IConsoleUtils {
    
    Optional<Produkt> pickOneProductFromAllProducts();
    Optional<Pfandwert> pickOnePfandwertFromAllPfandwerts();
    Optional<Kunde> pickOneUserFromAllUsers();

    void printProduktWithNumber(Produkt produkt, int number);
    void printPfandwertWithNumber(Pfandwert pfandwert, int number);
    void printKundeWithNumber(Kunde kunde, int number);

    int readIntInputWithPrompt(String prompt);
    Double readDoubleInputWithPrompt(String prompt);
    String readStringInputWithPrompt(String promptString);
    boolean acceptInput();

    void errorNoPfandWert();
    void errorNoKunden();
    void errorNoProdukt();
}