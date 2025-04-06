package com.asegetraenke;

import java.util.Scanner;

import com.asegetraenke.console.ConsoleAdapter;
import com.asegetraenke.console.GetraenkeInputHandler;
import com.asegetraenke.console.KundenInputHandler;
import com.asegetraenke.console.Utils.ConsoleUtils;
import com.asegetraenke.console.Utils.IConsoleUtils;
import com.asegetraenke.console.consolefunctionmapping.ICommandRegistrar;
import com.asegetraenke.console.consolefunctionmapping.CommandRegistrar;
import com.asegetraenke.console.consolefunctionmapping.CommandRegistry;
import com.asegetraenke.repository.CustomerRepositoryImpl;
import com.asegetraenke.repository.GetraenkeRepositoryImpl;
import com.asegetraenke.repository.RepositoryData;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        RepositoryData data = new RepositoryData();
        GetraenkeRepositoryImpl grepo = new GetraenkeRepositoryImpl(data);
        CustomerRepositoryImpl crepo = new CustomerRepositoryImpl(data);

        IGetraenkeUsecases gusecases = new GetraenkeUsecases(grepo, crepo);
        IKundenUsecases cusecases = new KundenUsecases(grepo, crepo); 

        ICommandRegistrar registrar = new CommandRegistrar();

        Scanner scanner = new Scanner(System.in);
        IConsoleUtils consoleUtils = new ConsoleUtils(scanner, gusecases, cusecases); 

        new GetraenkeInputHandler(gusecases, cusecases, consoleUtils, registrar);
        new KundenInputHandler(cusecases, consoleUtils, registrar);

        ConsoleAdapter cAdapter = new ConsoleAdapter(registrar, scanner);
        cAdapter.start();
    }
}
