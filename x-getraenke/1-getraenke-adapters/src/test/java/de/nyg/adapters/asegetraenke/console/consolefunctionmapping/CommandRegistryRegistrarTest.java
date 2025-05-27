package de.nyg.adapters.asegetraenke.console.consolefunctionmapping;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandRegistryRegistrarTest {

    private CommandRegistry registry;
    private CommandRegistrar registrar;

    @BeforeEach
    public void setUp() {
        registry = new CommandRegistry();
        registrar = new CommandRegistrar();
    }

    // ----- Tests for CommandRegistry -----

    @Test
    public void testRegisterAndRetrieveCommand() {
        Runnable command = () -> {};
        registry.registerCommand("test", command);

        assertTrue(registry.hasCommand("test"));
        assertEquals(command, registry.getCommand("test"));
    }

    @Test
    public void testHasCommandReturnsFalseWhenNotPresent() {
        assertFalse(registry.hasCommand("not_existing"));
    }

    @Test
    public void testGetCommandReturnsNullWhenNotPresent() {
        assertNull(registry.getCommand("not_existing"));
    }

    @Test
    public void testGetCommandMapReturnsAllCommands() {
        Runnable r1 = () -> {};
        Runnable r2 = () -> {};
        registry.registerCommand("eins", r1);
        registry.registerCommand("zwei", r2);

        Map<String, Runnable> map = registry.getCommandMap();
        assertEquals(2, map.size());
        assertTrue(map.containsKey("eins"));
        assertTrue(map.containsKey("zwei"));
    }

    // ----- Tests for CommandRegistrar -----

    @Test
    public void testRegisterCommands_registersAllAnnotatedMethods() {
        TestCommandObject testObject = new TestCommandObject();
        registrar.registerCommands(testObject);

        assertTrue(registrar.hasCommand("test", "first"));
        assertTrue(registrar.hasCommand("test", "second"));

        Runnable command = registrar.getCommand("test", "first");
        assertNotNull(command);

        // Command execution should set a flag
        assertFalse(testObject.firstExecuted);
        command.run();
        assertTrue(testObject.firstExecuted);
    }

    static class TestCommandObject {
        boolean firstExecuted = false;

        @ICommand(value = "first", category = "test")
        public void executeFirst() {
            firstExecuted = true;
        }

        @ICommand(value = "second", category = "test")
        public void executeSecond() {
        }
    }
}