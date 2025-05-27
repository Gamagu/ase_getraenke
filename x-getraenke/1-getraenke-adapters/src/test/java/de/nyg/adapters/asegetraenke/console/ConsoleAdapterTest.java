package de.nyg.adapters.asegetraenke.console;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Map;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.nyg.adapters.asegetraenke.console.consolefunctionmapping.CommandRegistrar;
import de.nyg.adapters.asegetraenke.console.consolefunctionmapping.CommandRegistry;

public class ConsoleAdapterTest {

    @Mock
    private CommandRegistrar mockedCommandRegistrar;
    @Mock
    private CommandRegistry mockedCommandRegistry;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    private AutoCloseable mocks;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() throws Exception {
        System.setOut(originalOut);
        mocks.close();
    }

    @Test
    public void testHandleInputReturnsSplitArray() {
        String input = "kunde create";
        Scanner scanner = new Scanner(new StringReader(input));
        ConsoleAdapter adapter = new ConsoleAdapter(mockedCommandRegistrar, scanner);

        String[] result = adapterTestAccess_handleInput(adapter);

        assertArrayEquals(new String[]{"kunde", "create"}, result);
    }

    @Test
    public void testHandleInputReturnsNullOnEmpty() {
        String input = "   \n";
        Scanner scanner = new Scanner(new StringReader(input));
        ConsoleAdapter adapter = new ConsoleAdapter(mockedCommandRegistrar, scanner);

        String[] result = adapterTestAccess_handleInput(adapter);

        assertNull(result);
    }

    @Test
    public void testPrintAllCommands() {
        Runnable dummyCommand = () -> {};
        when(mockedCommandRegistrar.getCommandRegistry()).thenReturn(
            Map.of("kunde", mockedCommandRegistry)
        );
        when(mockedCommandRegistry.getCommandMap()).thenReturn(
            Map.of("create", dummyCommand, "delete", dummyCommand)
        );

        Scanner scanner = new Scanner(new StringReader("\n"));
        ConsoleAdapter adapter = new ConsoleAdapter(mockedCommandRegistrar, scanner);
        adapterTestAccess_printAllCommands(adapter);

        String output = outContent.toString();
        assertTrue(output.contains("kunde create"));
        assertTrue(output.contains("kunde delete"));
    }

    private String[] adapterTestAccess_handleInput(ConsoleAdapter adapter) {
        try {
            var method = ConsoleAdapter.class.getDeclaredMethod("handleInput");
            method.setAccessible(true);
            return (String[]) method.invoke(adapter);
        } catch (Exception e) {
            fail("Reflection call failed: " + e.getMessage());
            return null;
        }
    }

    private void adapterTestAccess_printAllCommands(ConsoleAdapter adapter) {
        try {
            var method = ConsoleAdapter.class.getDeclaredMethod("printAllCommands");
            method.setAccessible(true);
            method.invoke(adapter);
        } catch (Exception e) {
            fail("Reflection call failed: " + e.getMessage());
        }
    }
}
