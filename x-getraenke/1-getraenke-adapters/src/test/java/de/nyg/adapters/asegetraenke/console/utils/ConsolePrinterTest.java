package de.nyg.adapters.asegetraenke.console.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.nyg.domain.asegetraenke.entities.Kunde;
import de.nyg.domain.asegetraenke.entities.Produkt;
import de.nyg.domain.asegetraenke.valueobjects.Pfandwert;

public class ConsolePrinterTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private ConsolePrinter printer;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        printer = new ConsolePrinter();
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testPrintProduktWithNumber() {
        Produkt produkt = mock(Produkt.class);
        when(produkt.toString()).thenReturn("Cola");
    
        printer.printProduktWithNumber(produkt, 1);
    
        String output = outContent.toString().trim();
        assertTrue(output.contains("1. "));
        assertTrue(output.contains("Cola"));
    }
    
    @Test
    public void testPrintPfandwertWithNumber() {
        Pfandwert pfandwert = mock(Pfandwert.class);
        when(pfandwert.toString()).thenReturn("Einweg");

        printer.printPfandwertWithNumber(pfandwert, 2);

        String output = outContent.toString().trim();
        assertTrue(output.contains("2. "));
        assertTrue(output.contains("Einweg"));
    }


    @Test
    public void testPrintKundeWithNumber() {
        Kunde kunde = new Kunde("Max", "Mustermann", "max@example.com");
        printer.printKundeWithNumber(kunde, 3);

        String output = outContent.toString().trim();
        assertTrue(output.contains("3. "));
        assertTrue(output.contains("Max"));
    }
}