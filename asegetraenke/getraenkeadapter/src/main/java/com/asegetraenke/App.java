package com.asegetraenke;

import java.util.Scanner;

import com.asegetraenke.console.ConsoleAdapter;
import com.asegetraenke.console.ConsoleUtils;
import com.asegetraenke.console.GetraenkeInputHandler;
import com.asegetraenke.console.KundenInputHandler;
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

        GetraenkeUsecases gusecases = new GetraenkeUsecases(grepo, crepo);
        KundenUsecases cusecases = new KundenUsecases(grepo, crepo);
        

        Scanner scanner = new Scanner(System.in); 
        ConsoleUtils consoleUtils = new ConsoleUtils(scanner, gusecases, cusecases);
        GetraenkeInputHandler gInputHandler = new GetraenkeInputHandler(gusecases,cusecases,consoleUtils);
        KundenInputHandler kInputHandler = new KundenInputHandler(cusecases,consoleUtils);

        ConsoleAdapter cAdapter = new ConsoleAdapter(kInputHandler, gInputHandler, cusecases, scanner);

        cAdapter.start();
    }
}
