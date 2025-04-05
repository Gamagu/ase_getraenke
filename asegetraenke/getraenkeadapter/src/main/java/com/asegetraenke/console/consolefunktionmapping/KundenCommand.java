package com.asegetraenke.console.consolefunktionmapping;

import java.util.Arrays;
import java.util.Optional;

import com.asegetraenke.console.KundenInputHandler;

public enum KundenCommand {
    CREATE_KUNDE("createkunde", (handler) -> handler.handleCreateKundeInput()),
    GET_ALL_KUNDEN("getallkunden", (handler) -> handler.handleGetAllKundenInput()),
    SET_NAME("setname", (handler) -> handler.handleSetNameInput()),
    GET_KUNDE("getkunde", (handler) -> handler.handleGetKundeInput()),
    GET_KUNDEN_BALANCE("getkundenbalance", (handler) -> handler.handleGetKundenBalanceInput()),
    GET_ALL_BESTELLUNGEN("getallbestellungen", (handler) -> handler.handleGetAllBestellungenInput());

    private final String name;
    private final CommandExecutor executor;

    KundenCommand(String name, CommandExecutor executor) {
        this.name = name;
        this.executor = executor;
    }

    public String getName() {
        return name;
    }

    public static Optional<KundenCommand> fromString(String input) {
        return Arrays.stream(values())
                .filter(cmd -> cmd.name.equalsIgnoreCase(input))
                .findFirst();
    }

    public void execute(KundenInputHandler handler) {
        executor.execute(handler);
    }

    // Functional interface to execute the commands with handler
    @FunctionalInterface
    public interface CommandExecutor {
        void execute(KundenInputHandler handler);
    }
}

