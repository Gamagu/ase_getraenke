package de.nyg.adapters.asegetraenke.console.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class EntityPickerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testPickOneFromList_validChoice() {
        ConsoleReader reader = Mockito.mock(ConsoleReader.class);
        Mockito.when(reader.readIntInputWithPrompt("Bitte Nummer eingeben: ")).thenReturn(2);

        EntityPicker<String> picker = new EntityPicker<>(reader);
        List<String> options = List.of("Apfel", "Banane", "Citrus");

        Optional<String> result = picker.pickOneFromList(options, s -> s);

        assertTrue(result.isPresent());
        assertEquals("Banane", result.get());
        assertTrue(outContent.toString().contains("1: Apfel"));
        assertTrue(outContent.toString().contains("2: Banane"));
    }

    @Test
    public void testPickOneFromList_invalidChoice() {
        ConsoleReader reader = Mockito.mock(ConsoleReader.class);
        Mockito.when(reader.readIntInputWithPrompt("Bitte Nummer eingeben: ")).thenReturn(5);

        EntityPicker<String> picker = new EntityPicker<>(reader);
        List<String> options = List.of("Apfel", "Banane");

        Optional<String> result = picker.pickOneFromList(options, s -> s);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testPickOneFromList_emptyList() {
        ConsoleReader reader = Mockito.mock(ConsoleReader.class);
        EntityPicker<String> picker = new EntityPicker<>(reader);

        Optional<String> result = picker.pickOneFromList(List.of(), s -> s);

        assertTrue(result.isEmpty());
        assertTrue(outContent.toString().contains("Keine Einträge verfügbar."));
    }
}