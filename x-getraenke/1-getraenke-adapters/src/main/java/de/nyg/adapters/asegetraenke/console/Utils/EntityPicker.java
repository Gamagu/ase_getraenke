package de.nyg.adapters.asegetraenke.console.Utils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class EntityPicker<T> {

    private final ConsoleReader consoleReader;
    public EntityPicker(ConsoleReader consoleReader) {
        this.consoleReader = consoleReader;
    }

    public Optional<T> pickOneFromList(List<T> list, Function<T, String> labelFunction) {
        if (list.isEmpty()) {
            System.out.println("Keine Einträge verfügbar.");
            return Optional.empty();
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ": " + labelFunction.apply(list.get(i)));
        }

        
        int choice = consoleReader.readIntInputWithPrompt("Bitte Nummer eingeben: ");
        if (choice >= 1 && choice <= list.size()) {
            return Optional.of(list.get(choice - 1));
        }

        return Optional.empty();
    }
}

