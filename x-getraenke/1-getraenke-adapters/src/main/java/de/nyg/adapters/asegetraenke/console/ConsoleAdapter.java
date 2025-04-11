package de.nyg.adapters.asegetraenke.console;

import java.util.Map;
import java.util.Scanner;

import de.nyg.adapters.asegetraenke.console.consolefunctionmapping.CommandRegistrar;
import de.nyg.adapters.asegetraenke.console.consolefunctionmapping.CommandRegistry;

public class ConsoleAdapter {
    private final String WELCOMEMESSAGE = "Welcome to the Geatrenkehandler";
    private final Scanner scanner;
    private final CommandRegistrar commandRegistrar;

    public ConsoleAdapter(CommandRegistrar commandRegistrar, Scanner scanner) {
        this.scanner = scanner;
        this.commandRegistrar = commandRegistrar;
    }

    public void start() {
        System.out.println(WELCOMEMESSAGE);
        printAllCommands();

        while (true) {
            String[] option = handleInput();
            if (option == null || option.length < 1) continue;

            String category = option[0];
            if (category.equalsIgnoreCase("help")) {
                printAllCommands();
                continue;
            }

            if (option.length < 2) {
                System.out.println("The second command is missing");
                continue;
            }

            String subcommand = option[1];

            if (commandRegistrar.hasCommand(category, subcommand)) {
                Runnable command = commandRegistrar.getCommand(category, subcommand);
                command.run();
            } else {
                System.out.println(subcommand + " is not a valid " + category + " command.");
            }
        }
    }

    private String[] handleInput() {
        String option = scanner.nextLine().toLowerCase();
        if (option.trim().isEmpty()) {
            return null;
        }
        return option.split(" ");
    }

private void printAllCommands() {
        for (Map.Entry<String, CommandRegistry> entry : commandRegistrar.getCommandRegistry().entrySet()) {
            String category = entry.getKey();
            CommandRegistry registry = entry.getValue();

            for (Map.Entry<String, Runnable> commandEntry : registry.getCommandMap().entrySet()) {
                String command = commandEntry.getKey();
                System.out.println(category + " " + command);
            }
        }
    }
}
