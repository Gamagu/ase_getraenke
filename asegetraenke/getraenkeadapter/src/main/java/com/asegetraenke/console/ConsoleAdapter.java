package com.asegetraenke.console;

import java.util.Map;
import java.util.Scanner;

import com.asegetraenke.KundenUsecases;
import com.asegetraenke.console.consolefunktionmapping.CommandEnum;
import com.asegetraenke.console.consolefunktionmapping.GetraenkeCommand;
import com.asegetraenke.console.consolefunktionmapping.KundenCommand;

public class ConsoleAdapter {

    private final String WELCOMEMESSAGE = "Welcome to the Geatrenkehandler";

    private final Scanner scanner;
    private final boolean isRunning;
    private final GetraenkeInputHandler getraenkeInputHandler;
    private final KundenInputHandler kundenInputHandler;
    

    public ConsoleAdapter(KundenInputHandler kundenInputHandler, 
                          GetraenkeInputHandler getraenkeInputHandler, 
                          KundenUsecases kundenUseCases,
                          Scanner scanner) {

        this.scanner = scanner;
        this.isRunning = true;
        this.getraenkeInputHandler = getraenkeInputHandler;
        this.kundenInputHandler = kundenInputHandler;
    }

    public void start() {
        System.out.println(WELCOMEMESSAGE);
        printAllCommands();
        while (isRunning) {
            String[] option = handleInput();
            if (option == null || option.length < 1) continue;

            CommandEnum.fromString(option[0]).ifPresentOrElse(domain -> {
                if (domain == CommandEnum.HELP) {
                    printAllCommands();
                    return;
                }

                if (option.length < 2) {
                    System.out.println("The second command is missing");
                    return;
                }

                String subcommand = option[1];

                switch (domain) {
                    case GETRAENKE -> GetraenkeCommand.fromString(subcommand)
                        .ifPresentOrElse(cmd -> {
                            cmd.execute(getraenkeInputHandler);
                        }, () -> System.out.println(subcommand + " is not a valid Getränke-Command."));
                    case KUNDE -> KundenCommand.fromString(subcommand)
                        .ifPresentOrElse(cmd -> cmd.execute(kundenInputHandler), 
                            () -> System.out.println(subcommand + " is not a valid Kunden-Command."));
                    default -> System.out.println("Unknown command category.");
                }

            }, () -> System.out.println("The first command is unknown. Use help for all options."));
        }
    }
    private String[] handleInput() {
        String option = scanner.nextLine();
        option = option.toLowerCase();
        if(option.trim() == null){
            return null;
        }

        String[] optionList = option.split(" ");
        for(String op : optionList){
            op.trim();
        }
        return optionList;
    }

    private void printAllCommands() {
        for (GetraenkeCommand getraenkeCommand : GetraenkeCommand.values()) {
            System.out.println("getraenke " + getraenkeCommand.getName());
        }
    
        for (KundenCommand kundenCommand : KundenCommand.values()) {
            System.out.println("kunde " + kundenCommand.getName());
        }
    }
    
    
}
