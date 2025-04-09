package adapter.asegetraenke;

import java.util.Scanner;

import adapter.asegetraenke.console.ConsoleAdapter;
import adapter.asegetraenke.console.GetraenkeInputHandler;
import adapter.asegetraenke.console.KundenInputHandler;
import adapter.asegetraenke.console.Utils.ConsoleUtils;
import adapter.asegetraenke.console.consolefunctionmapping.CommandRegistrar;
import adapter.asegetraenke.console.consolefunctionmapping.CommandRegistry;
import adapter.asegetraenke.repository.CustomerRepositoryImpl;
import adapter.asegetraenke.repository.GetraenkeRepositoryImpl;
import adapter.asegetraenke.repository.RepositoryData;
import application.asegetraenke.GetraenkeUsecases;
import application.asegetraenke.KundenUsecases;

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
