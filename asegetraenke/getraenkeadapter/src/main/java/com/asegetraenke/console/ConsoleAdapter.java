package com.asegetraenke.console;

import java.util.Map;
import java.util.Scanner;

import com.asegetraenke.kundeusecases;

public class ConsoleAdapter {

    private final String KUNDENCOMMAND = "kunde";
    private final String GETREANKECOMMAND = "getraenke";
    private final String HELPCOMMAND = "help";
    private final String WELCOMEMESSAGE = "TODO add welcome with short explaination";

    private final Scanner scanner;
    private final boolean isRunning;
    private final Map<String, Runnable> getraenkeCommandMap;
    private final Map<String, Runnable> kundeCommandMap;
    private final GetraenkeInputHandler getraenkeInputHandler;
    private final KundenInputHandler kundenInputHandler;
    

    public ConsoleAdapter(KundenInputHandler kundenInputHandler, 
                          GetraenkeInputHandler getraenkeInputHandler, 
                          kundeusecases kundenUseCases,
                          Scanner scanner) {

        this.scanner = scanner;
        this.isRunning = true;
        this.getraenkeInputHandler = getraenkeInputHandler;
        this.kundenInputHandler = kundenInputHandler;
        this.getraenkeCommandMap = this.getraenkeInputHandler.getGetrankeCommandMap();
        this.kundeCommandMap = this.kundenInputHandler.getKundeCommandMap();
    }

    public void start() {
        System.out.println(WELCOMEMESSAGE);
        printGetraenkeOptionTopLevel();
        printKundenOptionTopLevel();
        Map <String,Runnable> commandMap = null;
        while(isRunning) {
            commandMap = null;
            String[] option = handleInput();
            
            if(option == null) {
                continue;
            }

            if(option[0].equals(HELPCOMMAND)) {
                printGetraenkeOptionTopLevel();
                printKundenOptionTopLevel();
                continue;
            }
            
            if (option.length < 2 ) {
                System.out.println( "The second command is missing");
                continue;
            }

            
            if(option[0].equals(KUNDENCOMMAND)) {
                commandMap = this.kundeCommandMap;
            }

            if(option[0].equals(GETREANKECOMMAND)) {
                commandMap = this.getraenkeCommandMap;
            }

            if(commandMap == null) {
                System.out.println("The first command is unknown. Use help for all options");
                continue;
            }

            Runnable action = commandMap.get(option[1]);
            if (action == null) {
                System.out.println(option + " is not a viable option, please refer to the possible options: \n");
                printGetraenkeOptionTopLevel();
                continue;
            }
            action.run();
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

    private void printGetraenkeOptionTopLevel() {
        for (String command : getraenkeCommandMap.keySet()) {
            System.out.println(GETREANKECOMMAND+ " " + command);
        }
    }
    
    private void printKundenOptionTopLevel() {
        for (String command : kundeCommandMap.keySet()) {
            System.out.println(KUNDENCOMMAND + " " + command);
        }
    }
}
