package de.nyg.adapters.asegetraenke.console;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.nyg.application.asegetraenke.GetraenkeUsecases;
import de.nyg.application.asegetraenke.KundenUsecases;
import de.nyg.adapters.asegetraenke.console.Utils.ConsoleUtils;
import de.nyg.domain.asegetraenke.entities.Kunde;
import de.nyg.domain.asegetraenke.entities.Produkt;
import de.nyg.domain.asegetraenke.valueobjects.Pfandwert;

public class ConsoleUtilsTest {

    @Mock
    private GetraenkeUsecases mockedGetraenkeUseCases;

    @Mock 
    private KundenUsecases mockedKundeUseCases;

    private ConsoleUtils consoleUtils;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Disabled @Test 
    public void testPickOneProductFromAllProducts() {
        // Mocking the getAllProducts to return a specific list
        Produkt produkt1 = new Produkt("Produkt1", "Description1", "Test1");
        Produkt produkt2 = new Produkt("Produkt2", "Description2", "Test2");
        List<Produkt> produktList = Arrays.asList(produkt1, produkt2);
        when(mockedGetraenkeUseCases.getAllProducts()).thenReturn(produktList);
        //TODO ToString funktioniert nicht da Preis undefiniert ist

        // Simulate user entering choice "1"
        String simulatedInput = "1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        consoleUtils = new ConsoleUtils(scanner, mockedGetraenkeUseCases, null);

        // Execute the method
        Optional<Produkt> pickedProduct = consoleUtils.pickOneProductFromAllProducts();


        // Verify the result
        assertTrue(pickedProduct.isPresent(), "Expected a product to be returned");
        assertEquals(produkt1, pickedProduct.get(), "Expected Produkt1 to be chosen");

        // Verify console output (optional)
        String expectedOutput = "Chosen Produkt: " + produkt1.toString();
        assertTrue(outContent.toString().contains(expectedOutput));
    }

    @Test
    public void testPickOnePfandwertFromAllPfandwerts() {
        // Mocking getAllPfandwerte to return a specific list
        Pfandwert pfandwert1 = new Pfandwert(0.25,"Pfandwert1");
        Pfandwert pfandwert2 = new Pfandwert(0.50,"Pfandwert2");
        List<Pfandwert> pfandwertList = Arrays.asList(pfandwert1, pfandwert2);
        when(mockedGetraenkeUseCases.getAllPfandwerte()).thenReturn(pfandwertList);

        // Simulate user entering choice "1"
        String simulatedInput = "1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));

        consoleUtils = new ConsoleUtils(scanner, mockedGetraenkeUseCases, null); // Assuming no need for kundeUseCases in this test

        // Execute the method
        Optional<Pfandwert> pickedPfandwert = consoleUtils.pickOnePfandwertFromAllPfandwerts();

        // Verify the result
        assertTrue(pickedPfandwert.isPresent(), "Expected a pfandwert to be returned");
        assertEquals(pfandwert1, pickedPfandwert.get(), "Expected Pfandwert1 to be chosen");

        // Verify console output (optional)
        String expectedOutput = "Chosen Produkt: " + pfandwert1.toString();
        assertTrue(outContent.toString().contains(expectedOutput));
    }

    @Test
    public void testPickOneUserFromAllUsers() {
        // Mocking getAllKunden to return a specific list
        Kunde kunde1 = new Kunde("Kunde1", "Lastname1", "email1@example.com");
        Kunde kunde2 = new Kunde("Kunde2", "Lastname2", "email2@example.com");
        List<Kunde> kundenList = Arrays.asList(kunde1, kunde2);
        when(mockedKundeUseCases.getAllKunden()).thenReturn(kundenList);

        // Simulate user entering choice "1"
        String simulatedInput = "1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));

        consoleUtils = new ConsoleUtils(scanner, null, mockedKundeUseCases); // Assuming no need for getraenkeUseCases in this test

        // Execute the method
        Optional<Kunde> pickedKunde = consoleUtils.pickOneUserFromAllUsers();

        // Verify the result
        assertTrue(pickedKunde.isPresent(), "Expected a kunden to be returned");
        assertEquals(kunde1, pickedKunde.get(), "Expected Kunde1 to be chosen");
        // Output verification optional 
        String expectedOutputPrefix = "Which Customername do you want to change? Enter the Number: ";
        assertTrue(outContent.toString().contains(expectedOutputPrefix));   
    }

    @Test
    public void testAcceptInputYes() {
        // Simulate user entering "y"
        String simulatedInput = "y\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Instantiate ConsoleUtils with the simulated Scanner
        ConsoleUtils consoleUtils = new ConsoleUtils(scanner, null, null);

        // Assert that the method returns true for "y" input
        assertTrue(consoleUtils.acceptInput(), "Expected the process to finish with 'y' input");

        // Optionally, verify console output
        String expectedMessage = "Finish process yes[y] / no[n]";
        assertTrue(outContent.toString().contains(expectedMessage));
    }

    @Test
    public void testAcceptInputNo() {
        // Simulate user entering "n"
        String simulatedInput = "n\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Instantiate ConsoleUtils with the simulated Scanner
        ConsoleUtils consoleUtils = new ConsoleUtils(scanner, null, null);

        // Assert that the method returns false for "n" input
        assertFalse(consoleUtils.acceptInput(), "Expected the process to abort with 'n' input");

        // Optionally, verify console output
        String expectedMessage = "Process was aborted";
        assertTrue(outContent.toString().contains(expectedMessage));
    }

    @Test
    public void testReadIntInputWithPrompt() {
        String simulatedInput = "notAnInt\n42\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        consoleUtils = new ConsoleUtils(scanner, mockedGetraenkeUseCases, mockedKundeUseCases);

        int result = consoleUtils.readIntInputWithPrompt("Enter an integer: ");
        assertEquals(42, result);
    }

    @Test
    public void testReadDoubleInputWithPrompt() {
        String simulatedInput = "notADouble\n3.14\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        consoleUtils = new ConsoleUtils(scanner, mockedGetraenkeUseCases, mockedKundeUseCases);

        double result = consoleUtils.readDoubleInputWithPrompt("Enter a double: ");
        assertEquals(3.14, result);
    }

    @Test
    public void testReadStringInputWithPrompt() {
        String simulatedInput = "Hello\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        consoleUtils = new ConsoleUtils(scanner, mockedGetraenkeUseCases, mockedKundeUseCases);

        String result = consoleUtils.readStringInputWithPrompt("Enter a string: ");
        assertEquals("Hello", result);
    }
}