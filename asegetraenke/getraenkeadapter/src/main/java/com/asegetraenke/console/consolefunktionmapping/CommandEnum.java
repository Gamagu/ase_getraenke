package com.asegetraenke.console.consolefunktionmapping;

import java.util.Arrays;
import java.util.Optional;

public enum CommandEnum {
    GETRAENKE("getraenke"),
    KUNDE("kunde"),
    HELP("help");

    private final String command;

    CommandEnum(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static Optional<CommandEnum> fromString(String input) {
        return Arrays.stream(values())
                .filter(cmd -> cmd.command.equalsIgnoreCase(input))
                .findFirst();
    }
}
