package de.nyg.adapters.asegetraenke.console.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConsoleReaderTest {

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
    public void testReadIntInputWithPrompt_validInput() {
        Scanner scanner = new Scanner(new StringReader("42\n"));
        ConsoleReader reader = new ConsoleReader(scanner);

        int result = reader.readIntInputWithPrompt("Enter number: ");

        assertEquals(42, result);
        assertTrue(outContent.toString().contains("Enter number:"));
    }

    @Test
    public void testReadIntInputWithPrompt_invalidThenValid() {
        Scanner scanner = new Scanner(new StringReader("abc\n99\n"));
        ConsoleReader reader = new ConsoleReader(scanner);

        int result = reader.readIntInputWithPrompt("Enter int: ");

        assertEquals(99, result);
        assertTrue(outContent.toString().contains("can not be translated into a number"));
    }

    @Test
    public void testReadDoubleInputWithPrompt_validInput() {
        Scanner scanner = new Scanner(new StringReader("3.14\n"));
        ConsoleReader reader = new ConsoleReader(scanner);

        double result = reader.readDoubleInputWithPrompt("Enter double: ");

        assertEquals(3.14, result);
    }

    @Test
    public void testReadStringInputWithPrompt() {
        Scanner scanner = new Scanner(new StringReader("TestString\n"));
        ConsoleReader reader = new ConsoleReader(scanner);

        String result = reader.readStringInputWithPrompt("Enter text: ");

        assertEquals("TestString", result);
        assertTrue(outContent.toString().contains("Enter text:"));
    }

    @Test
    public void testAcceptInput_yes() {
        Scanner scanner = new Scanner(new StringReader("y\n"));
        ConsoleReader reader = new ConsoleReader(scanner);

        boolean result = reader.acceptInput();

        assertTrue(result);
    }

    @Test
    public void testAcceptInput_no() {
        Scanner scanner = new Scanner(new StringReader("n\n"));
        ConsoleReader reader = new ConsoleReader(scanner);

        boolean result = reader.acceptInput();

        assertFalse(result);
        assertTrue(outContent.toString().contains("Process was aborted"));
    }
}