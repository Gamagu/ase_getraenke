package de.nyg.adapters.asegetraenke;

import java.util.Scanner;

import de.nyg.application.asegetraenke.GetraenkeUsecases;
import de.nyg.application.asegetraenke.KundenUsecases;
import de.nyg.domain.asegetraenke.entities.Kunde;
import de.nyg.domain.asegetraenke.entities.Produkt;
import de.nyg.domain.asegetraenke.valueobjects.Pfandwert;
import de.nyg.adapters.asegetraenke.console.ConsoleAdapter;
import de.nyg.adapters.asegetraenke.console.GetraenkeInputHandler;
import de.nyg.adapters.asegetraenke.console.KundenInputHandler;
import de.nyg.adapters.asegetraenke.console.Utils.ConsolePrinter;
import de.nyg.adapters.asegetraenke.console.Utils.ConsoleReader;
import de.nyg.adapters.asegetraenke.console.Utils.ConsoleError;
import de.nyg.adapters.asegetraenke.console.Utils.EntityPicker;
import de.nyg.adapters.asegetraenke.console.consolefunctionmapping.CommandRegistrar;
import de.nyg.adapters.asegetraenke.repository.CustomerRepositoryImpl;
import de.nyg.adapters.asegetraenke.repository.GetraenkeRepositoryImpl;
import de.nyg.adapters.asegetraenke.repository.RepositoryData;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        RepositoryData data = new RepositoryData();
        GetraenkeRepositoryImpl grepo = new GetraenkeRepositoryImpl(data);
        CustomerRepositoryImpl crepo = new CustomerRepositoryImpl(data);

        GetraenkeUsecases gusecases = new GetraenkeUsecases(grepo, crepo);
        KundenUsecases cusecases = new KundenUsecases(grepo, crepo); 

        CommandRegistrar registrar = new CommandRegistrar();

        Scanner scanner = new Scanner(System.in);
        ConsoleError consoleUtils = new ConsoleError(scanner, gusecases, cusecases); 
        ConsolePrinter consolePrinter = new ConsolePrinter();
        ConsoleReader consoleReader = new ConsoleReader(scanner);
        EntityPicker<Kunde> kundenPicker = new EntityPicker<>(consoleReader);
        EntityPicker<Produkt> productPicker = new EntityPicker<>(consoleReader);
        EntityPicker<Pfandwert> pfandwertPicker = new EntityPicker<>(consoleReader);

        new GetraenkeInputHandler(gusecases, cusecases, consoleUtils, registrar, kundenPicker, productPicker, pfandwertPicker, consoleReader, consolePrinter);
        new KundenInputHandler(cusecases, consoleUtils, registrar, kundenPicker, consoleReader, consolePrinter);

        ConsoleAdapter cAdapter = new ConsoleAdapter(registrar, scanner);
        cAdapter.start();
    }
}
