package de.nyg.adapters.asegetraenke;

import java.util.Scanner;

import de.nyg.application.asegetraenke.GetraenkeUsecases;
import de.nyg.application.asegetraenke.KundenUsecases;
import de.nyg.adapters.asegetraenke.console.ConsoleAdapter;
import de.nyg.adapters.asegetraenke.console.GetraenkeInputHandler;
import de.nyg.adapters.asegetraenke.console.KundenInputHandler;
import de.nyg.adapters.asegetraenke.console.Utils.ConsoleUtils;
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
        ConsoleUtils consoleUtils = new ConsoleUtils(scanner, gusecases, cusecases); 

        new GetraenkeInputHandler(gusecases, cusecases, consoleUtils, registrar);
        new KundenInputHandler(cusecases, consoleUtils, registrar);

        ConsoleAdapter cAdapter = new ConsoleAdapter(registrar, scanner);
        cAdapter.start();
    }
}
