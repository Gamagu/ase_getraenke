package de.nyg.adapters.asegetraenke.console.consolefunctionmapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CommandRegistrar{
    private Map<String, CommandRegistry> commandRegistry = new HashMap<>();

    public CommandRegistrar() {}

    public void registerCommands(Object handler) {
        for (Method method : handler.getClass().getMethods()) {
            ICommand commandAnnotation = method.getAnnotation(ICommand.class);
            if (commandAnnotation != null) {
                String category = commandAnnotation.category();
                String commandName = commandAnnotation.value();

                commandRegistry.putIfAbsent(category, new CommandRegistry());

                Runnable commandHandler = () -> {
                    try {
                        method.invoke(handler);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };

                commandRegistry.get(category).registerCommand(commandName, commandHandler);
            }
        }
    }

    public Runnable getCommand(String category, String commandName) {
        if (commandRegistry.containsKey(category)) {
            return commandRegistry.get(category).getCommand(commandName);
        }
        return null;
    }

    public boolean hasCommand(String category, String commandName) {
        return commandRegistry.containsKey(category) && 
               commandRegistry.get(category).hasCommand(commandName);
    }

    public Map<String, CommandRegistry> getCommandRegistry() {
        return commandRegistry;
    }
}
